package il.co.menora.soaarchive.mq;

import il.co.menora.soaarchive.client.app.SoaArchiveClient;
import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.entities.Outgoing;
import il.co.menora.soaarchive.entities.Payload;
import il.co.menora.soaarchive.entities.QMgrConnData;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.james.mime4j.field.datetime.DateTime;
import org.eclipse.jetty.xml.XmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gwt.xml.client.XMLParser;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMD;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.headers.*;

public class MQRequestsHandler {

	private static boolean isInitiated=  false;
	private static HashMap<String, QMgrConnData> mQueueManagers;
	private static Logger logger = Logger.getLogger(SoaArchiveClient.class.getSimpleName());
	private static HashMap<String, MQQueueManager> connectedQmgrs;
	private static HashMap<String, MQQueue> connectedQueues;
	private static final int COMMIT_BATCH_SIZE = 50;
	private static DocumentBuilder xmlParser;
	private static Unmarshaller jaxbMQMDUnmarshaller;
	/**
	 * Initiate the handler with all the queue managers configurations
	 * @param list - List of queue managers details of the organizations that are available for the system
	 */
	public static void init(List<QMgrConnData> list){
		mQueueManagers = new HashMap<String, QMgrConnData>();
		// TODO: fill mQueueManagers with QMgrConnData from Database
		//
		for (QMgrConnData qMgrConnData : list) {
			mQueueManagers.put(qMgrConnData.getqMgrName(), qMgrConnData);	
		}
		connectedQmgrs = new HashMap<String, MQQueueManager>();
		connectedQueues = new HashMap<String, MQQueue>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			xmlParser = factory.newDocumentBuilder();
			JAXBContext jaxbMQMDInstance = JAXBContext.newInstance(MQMD.class);
			jaxbMQMDUnmarshaller = jaxbMQMDInstance.createUnmarshaller();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't init " + MQRequestsHandler.class.getName() + " error: " + e.getMessage());
		}
		
		isInitiated = true;
	}
	
	/**
	 * Tries to put mq messages back to the relevant queues and queue managers set by the request
	 * @param request - The request with the mq messages to put to request
	 * @return if ALL messages were successfully put
	 */
	public static boolean processMQRequest(SendMQRequest request){
		
		
		if(!isInitiated){
			throw new RuntimeException(MQRequestsHandler.class.getName() + " init must be called first");
		}
		boolean totalResult = true;
		
		for(int nMQMessage = 0; nMQMessage<request.getIncoming().size();nMQMessage++){
			Incoming msg = request.getIncoming().get(nMQMessage);
			// Decide destination based on request parameters, if user chose queue\queue manager, use this, otherwise, use as set on messages
			String targetQueueManager = request.getClientRequest().getQueueManager() == "" || request.getClientRequest().getQueueManager() == null  ? msg.getFromQMgr() : request.getClientRequest().getQueueManager();
			String targetQueue = request.getClientRequest().getQueueName() == "" || request.getClientRequest().getQueueName() == null ? msg.getFromQueue() : request.getClientRequest().getQueueName();
			
			QMgrConnData connData = mQueueManagers.get(targetQueueManager);
			if(connData == null){
				logger.log(Level.WARNING, "Message was trying to be sent to a queue manager that doesn't exist in configuration");
				msg.setPutStatus(false);
			}
			else{
				MQEnvironment.hostname = connData.getIp();
				MQEnvironment.port = connData.getPort();
				MQEnvironment.channel = connData.getChlName();
				MQQueueManager qmgr;
				try {
					qmgr = getCachedQmgr(targetQueueManager);
					
					MQMessage newMessage = new MQMessage();
					
					// Construct the new message
					createMQMessageFromIncoming(msg,newMessage);
					
					
					MQQueue queue = getCachedQueue(qmgr, targetQueue); 
					queue.put(newMessage);
					
					if(nMQMessage % COMMIT_BATCH_SIZE == 0){
						commitCachedQmgrs();
					}
					
				} catch (MQException e) {
					e.printStackTrace();
				}
				finally{
					commitCachedQmgrs();
					clearCachedQmgrs();
				}
			}
		}
		
		return totalResult;
	}

	private static MQQueue getCachedQueue(MQQueueManager qmgr, String targetQueue) {
		// Create queue object only if needed
		if(connectedQueues.containsKey(targetQueue)){
			return connectedQueues.get(targetQueue);
		} else {
			MQQueue queue;
			try {
				//
				queue = qmgr.accessQueue(targetQueue,
						CMQC.MQOO_OUTPUT | CMQC.MQOO_INQUIRE | CMQC.MQPMO_SYNCPOINT | CMQC.MQPMO_SET_ALL_CONTEXT);
			} catch (MQException e) {
				throw new RuntimeException(e);
			}
			connectedQueues.put(targetQueue,queue);
			return queue;
		}
	}

	private static void commitCachedQmgrs() {
		// Close all connected qmgrs
		for (MQQueueManager currQmgr : connectedQmgrs.values()) {
			 try {
				currQmgr.commit();
			} catch (MQException e) {
				e.printStackTrace();
				
			}
		}
	}
	
	private static void clearCachedQmgrs() {
		// Close all connected qmgrs
		for (MQQueueManager currQmgr : connectedQmgrs.values()) {
			 try {
				currQmgr.close();
			} catch (MQException e) {
				e.printStackTrace();
			}
		}
		connectedQueues.clear();
		connectedQmgrs.clear();
	}

	/**
	 * Deploys the data of the incoming into the mesasge
	 * @param pData - The data of the message to set in the msg
	 * @param pMsg - The mq message that will be updated with the new data
	 * @return Whether the operation was succesfull
	 */
	private static boolean createMQMessageFromIncoming(Incoming data, MQMessage msg){
		if(data.getMqMessageId() != null){
			msg.messageId = data.getMqMessageId().getBytes();
		}
		// TODO: Get data from data.getMQMD()
		
		try{
		// Try to parse the mqmd as xml	
		Document mqmdXML = xmlParser.parse(new ByteArrayInputStream(data.getMQMD().toString().getBytes("UTF-8")));
		
		int MQMDVersion = Integer.parseInt(mqmdXML.getElementsByTagName("Version").item(0).getTextContent());
		if (MQMDVersion == 2) {
			msg.setVersion(CMQC.MQMD_VERSION_2);
		}
		else {
			msg.setVersion(CMQC.MQMD_VERSION_1);
		}
		
		msg.accountingToken = mqmdXML.getElementsByTagName("AccountingToken").item(0).getTextContent().getBytes();
		msg.applicationIdData = mqmdXML.getElementsByTagName("ApplIdentityData").item(0).getTextContent();
		msg.applicationOriginData = mqmdXML.getElementsByTagName("ApplOriginData").item(0).getTextContent();
		msg.backoutCount = Integer.parseInt(mqmdXML.getElementsByTagName("BackoutCount").item(0).getTextContent());
		msg.characterSet = Integer.parseInt(mqmdXML.getElementsByTagName("CodedCharSetId").item(0).getTextContent());
		msg.correlationId = mqmdXML.getElementsByTagName("CorrelId").item(0).getTextContent().getBytes();;
		msg.encoding = Integer.parseInt(mqmdXML.getElementsByTagName("Encoding").item(0).getTextContent());
		msg.expiry = Integer.parseInt(mqmdXML.getElementsByTagName("Expiry").item(0).getTextContent());
		msg.feedback = Integer.parseInt(mqmdXML.getElementsByTagName("Feedback").item(0).getTextContent());
		msg.format = mqmdXML.getElementsByTagName("Format").item(0).getTextContent();
		msg.groupId = mqmdXML.getElementsByTagName("GroupId").item(0).getTextContent().getBytes();
		msg.messageFlags = Integer.parseInt(mqmdXML.getElementsByTagName("MsgFlags").item(0).getTextContent());
		msg.messageId = mqmdXML.getElementsByTagName("MsgId").item(0).getTextContent().getBytes();
		msg.messageSequenceNumber = Integer.parseInt(mqmdXML.getElementsByTagName("MsgSeqNumber").item(0).getTextContent());
		msg.messageType = Integer.parseInt(mqmdXML.getElementsByTagName("MsgType").item(0).getTextContent());
		msg.offset = Integer.parseInt(mqmdXML.getElementsByTagName("Offset").item(0).getTextContent());
		msg.originalLength = Integer.parseInt(mqmdXML.getElementsByTagName("OriginalLength").item(0).getTextContent());
		msg.persistence = Integer.parseInt(mqmdXML.getElementsByTagName("Persistence").item(0).getTextContent());
		msg.priority = Integer.parseInt(mqmdXML.getElementsByTagName("Priority").item(0).getTextContent());
		msg.putApplicationName = mqmdXML.getElementsByTagName("PutApplName").item(0).getTextContent();
		msg.putApplicationType = Integer.parseInt(mqmdXML.getElementsByTagName("PutApplType").item(0).getTextContent());
		
		// Time handling - wasn't checked yet
		SimpleDateFormat DateMQMDformatting = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeMQMDformatting = new SimpleDateFormat("hh:mm:ss.SSS");
		Date putDate = DateMQMDformatting.parse(mqmdXML.getElementsByTagName("PutDate").item(0).getTextContent());
		Date putTime = timeMQMDformatting.parse(mqmdXML.getElementsByTagName("PutTime").item(0).getTextContent());
		GregorianCalendar summedDateTime = new GregorianCalendar();
		summedDateTime.setTimeInMillis(putDate.getTime() + putTime.getTime());
		msg.putDateTime = summedDateTime;
		
		
		
		msg.replyToQueueManagerName = mqmdXML.getElementsByTagName("ReplyToQMgr").item(0).getTextContent();
		msg.replyToQueueName = mqmdXML.getElementsByTagName("ReplyToQ").item(0).getTextContent();
		msg.report = Integer.parseInt(mqmdXML.getElementsByTagName("Report").item(0).getTextContent());
		msg.userId = mqmdXML.getElementsByTagName("UserIdentifier").item(0).getTextContent();
		
		// Try to parse the RFH2 as xml
		Document RfhXML = xmlParser.parse(new ByteArrayInputStream(data.getMqRfh2().toString().getBytes("UTF-8")));

		if (RfhXML.toString().length() > 0) {    // if there is an RFH2
			
			msg.format = CMQC.MQFMT_RF_HEADER_2; // Msg Format
			
			// GIDI : 	i found a class for RFH2 header, with seters and geters , but didnt find a way to add it to the MQMessage object  
			MQRFH2 mqrfh2 = new MQRFH2();
			// mqrfh2.setEncoding(Integer.parseInt(RfhXML.getElementsByTagName("Encoding").item(0).getTextContent()));
			// mqrfh2.setCodedCharSetId(Integer.parseInt(RfhXML.getElementsByTagName("CodedCharSetId").item(0).getTextContent()));
			// mqrfh2.setFormat(RfhXML.getElementsByTagName("Format").item(0).getTextContent());
			// mqrfh2.setFlags(Integer.parseInt(RfhXML.getElementsByTagName("Flags").item(0).getTextContent()));
			// mqrfh2.setNameValueCCSID(Integer.parseInt(RfhXML.getElementsByTagName("NameValueCCSID").item(0).getTextContent()));
			String usr = "<usr>" + RfhXML.getElementsByTagName("usr").item(0).getTextContent() + "</usr>";
			
			// GIDI : 	so this is the only way i found on the internet to add RFH2 fields to a msg. 
			//			since in MQ , the RFH2 is part of the payload, this code actually writes the fields of RFH2 as payload before the actual payload.		
			msg.writeString(CMQC.MQRFH_STRUC_ID); // StrucId 
			msg.writeInt4(CMQC.MQRFH_VERSION_2); // Version 
			msg.writeInt4(CMQC.MQRFH_STRUC_LENGTH_FIXED_2 +  4 + usr.getBytes("UTF-8").length); // StrucLength 
			msg.writeInt4(CMQC.MQENC_NATIVE); // Encoding 
			msg.writeInt4(CMQC.MQCCSI_DEFAULT); // CodedCharacterSetId
			msg.writeString(CMQC.MQFMT_NONE); // Format (content) 
			msg.writeInt4(CMQC.MQRFH_NO_FLAGS); // Flags 
			msg.writeInt4(1208); // NameValueCCSID = UTF-8
			msg.writeInt4(usr.getBytes("UTF-8").length); 
			msg.writeString(usr); 
		}
		
		msg.writeString(data.getPayload().getPayload()); 

		
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
//		msg.backoutCount = (int)data.getMqBackoutCount();
				
		return true;
		
	}
	
	
	
	/**
	 * A queue manager with a connection opened to it,
	 * if one was already open, it will be reused
	 */
	private static MQQueueManager getCachedQmgr(String qmgrName) {
		// Create queue manager object only if needed
		if(connectedQmgrs.containsKey(qmgrName)){
			return connectedQmgrs.get(qmgrName);
		} else {
			MQQueueManager qmgr;
			try {
				qmgr = new MQQueueManager(qmgrName);
			} catch (MQException e) {
				throw new RuntimeException(e);
			}
			connectedQmgrs.put(qmgrName,qmgr);
			return qmgr;
		}
	}
}
