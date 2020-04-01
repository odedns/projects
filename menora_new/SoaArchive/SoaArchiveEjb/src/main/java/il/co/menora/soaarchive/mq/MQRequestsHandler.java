package il.co.menora.soaarchive.mq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.jcabi.xml.XMLDocument;

import il.co.menora.soaarchive.client.rpc.SoaArchiveService;
import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.entities.Outgoing;
import il.co.menora.soaarchive.entities.QMgrConnData;
import il.co.menora.soaarchive.shared.Statuses;

/**
 * 
 */
public class MQRequestsHandler {

	private static boolean isInitiated=  false;
	private static HashMap<String, QMgrConnData> QueueManagers;
	private static HashMap<String, String> mAliases;
	private static Logger logger = LogManager.getLogger(SoaArchiveService.class.getSimpleName());
	private static volatile HashMap<String, MQQueueManager> connectedQmgrs;
	private static volatile HashMap<String, MQQueue> connectedQueues;
	private static final int COMMIT_BATCH_SIZE = 50;
	private static DocumentBuilder xmlParser;
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

	/**
	 * Initiate the handler with all the alias queue managers configurations
	 * @param pList - List of queue managers details of the organizations that are available for the system
	 * @param pAliases - Aliases for the given queue managers in the list
	 */
	public static void init(List<QMgrConnData> pList, HashMap<String,String> pAliases){
		// Init hash map members
		connectedQmgrs = new HashMap<String, MQQueueManager>();
		connectedQueues = new HashMap<String, MQQueue>();
		QueueManagers = new HashMap<String, QMgrConnData>();
		
		mAliases = pAliases;

		for (QMgrConnData qMgrConnData : pList) {
			QueueManagers.put(qMgrConnData.getqMgrName(), qMgrConnData);	
		}
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			xmlParser = factory.newDocumentBuilder();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Couldn't init " + MQRequestsHandler.class.getName() + " error: " + e.getMessage());
			return;
		}
		
		isInitiated = true;
	}
	
	/**
	 * Tries to put mq messages back to the relevant queues and queue managers set by the request
	 * @param request - The request with the mq messages to put back to queues
	 * @return if ALL messages were successfully put, {@link SendMQResponse} will indicate result = true
	 */
	public static SendMQResponse processMQRequest(SendMQRequest request){
		if(!isInitiated){
			throw new RuntimeException(MQRequestsHandler.class.getName() + " init must be called first");
		}
		
		SendMQResponse response = new SendMQResponse(); 
		
		for(int nMQMessage = 0; nMQMessage < request.getIncoming().size();nMQMessage++){
			Incoming msg = request.getIncoming().get(nMQMessage);
			Date today = new Date();
			
			
			if(!request.getClientRequest().isSendInvalid() && msg.getExpiryTime() != null && msg.getExpiryTime().before(today)) {
				logger.info("Message : " + msg.getId() + " is expired...skipped");
				continue;
			}
			// if send expired is checked. See if we update all messages witht the 
			// new expiry date or only update the expired messages.
			if(request.getClientRequest().isSendInvalid()) {
				if(msg.getExpiryTime() != null && msg.getExpiryTime().before(today) || request.getClientRequest().isUpdateMsgDate()) {
					msg.setExpiryTime(request.getClientRequest().getValidityDate());
					
				}
			}
			// Decide destination based on request parameters, if user chose queue\queue manager, use this, otherwise, use as set on messages
			String targetQueueManager = request.getClientRequest().getQueueMgr() == "" || request.getClientRequest().getQueueMgr() == null  ? msg.getFromQMgr() : request.getClientRequest().getQueueMgr();
			String targetQueue = request.getClientRequest().getQueueName() == "" || request.getClientRequest().getQueueName() == null ? msg.getFromQueue() : request.getClientRequest().getQueueName();
			
			Document mqmdXML;
			try {
				mqmdXML = xmlParser.parse(new ByteArrayInputStream(msg.getMqMD().toString().getBytes("UTF-8")));
			} catch (Exception e1) {
				logger.debug("MQMD is invalid xml for message (Incoming) ID: " + msg.getId());
				response.setErrorMessage("MQMD is invalid xml for message (Incoming) ID: " + msg.getId());
				response.setResult(false);
				return response;
			}

			// User can decide to override reply to queue and priority
			String replyToQueue = request.getClientRequest().getReplyToQueue() == "" || request.getClientRequest().getReplyToQueue() == null ? mqmdXML.getElementsByTagName("ReplyToQ").item(0).getTextContent() : request.getClientRequest().getReplyToQueue();
			int priority = request.getClientRequest().getPriority() > 0 ? request.getClientRequest().getPriority() : Integer.parseInt(mqmdXML.getElementsByTagName("Priority").item(0).getTextContent());
			
			QMgrConnData connData = QueueManagers.get(targetQueueManager);
			// If mq conn data was not found, try to find it using it's aliases
			if(connData == null) {
				connData = QueueManagers.get(mAliases.get(targetQueueManager));
			}
			// If conn data still null it means that there's no queue manager found for this message, return error
			if(connData == null){
				logger.error("Message was trying to be sent to a queue manager that doesn't exist in configuration (QMGR: " + targetQueueManager + ")");
				response.setErrorMessage("Message was trying to be sent to a queue manager that doesn't exist in configuration - " + targetQueueManager + " msgID " + msg.getId());
				response.setResult(false);
				return response;
			}
			else{
				MQEnvironment.hostname = connData.getIp();
				MQEnvironment.port = connData.getPort();
				MQEnvironment.channel = connData.getChlName();
				MQQueueManager qmgr;
				try {
					qmgr = getCachedQmgr(connData.getqMgrName());
					
					MQMessage newMessage = new MQMessage();

					// Construct the new message
					String outgoingGUID = UUID.randomUUID().toString();
					String returnedRFH = createMQMessageFromIncoming(msg,newMessage,outgoingGUID,request.getUserName(), targetQueueManager);
					if(returnedRFH == null){ 
						logger.error("Could not build RFH from Incoming Object message, incoming ID: " + msg.getId());
						response.setErrorMessage("Could not build RFH from Incoming Object message,incoming ID: " + msg.getId());
						response.setResult(false);
						return response;
					}else{
						newMessage.replyToQueueName = replyToQueue;
						if(priority > 0){
							newMessage.priority = priority;
						}
						if(request.getClientRequest().isCreateMsgId()){
							newMessage.messageId = null;
						}
						
						MQQueue queue = getCachedQueue(qmgr, targetQueue); 
						queue.put(newMessage);
						
						if(nMQMessage % COMMIT_BATCH_SIZE == 0){
							commitCachedQmgrs();
						}
						int numRR = msg.getRerunsNum() + 1;
						
						// Create new outgoing data
						Outgoing outgoing = new Outgoing();
						outgoing.setId(outgoingGUID);
						outgoing.setIncomingId(msg.getId());
						outgoing.setMqId(msg.getMqMessageId());
						outgoing.setMqRfh2(returnedRFH);
						outgoing.setPayload(msg.getPayload());
						outgoing.setResendTime(new Date());
						outgoing.setToQMgr(targetQueueManager);
						outgoing.setToQueue(targetQueue);
						response.getOutgoing().add(outgoing);
						msg.setRerunsNum(numRR);
						msg.setLastUser(request.getUserName());
						msg.setStatus(Statuses.STATUS_RR);
						logger.info("updated reruns to : " + numRR);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					response.setErrorMessage(msg.getId() + ": Error occured for MQMSGID " + msg.getMqMessageId() +  " : " + e.getMessage());
					response.setResult(false);
					logger.error(e);
					return response;
				} 
				finally{
					commitCachedQmgrs();
					clearCachedQmgrs();
				}
			}
		}
		
		return response;
	}

	private static MQQueue getCachedQueue(MQQueueManager qmgr, String targetQueue) throws MQException {
		// Create queue object only if needed
		if(connectedQueues.containsKey(targetQueue)){
			return connectedQueues.get(targetQueue);
		} else {

			MQQueue queue = qmgr.accessQueue(targetQueue,
						CMQC.MQOO_OUTPUT | CMQC.MQOO_SET_ALL_CONTEXT | CMQC.MQPMO_SYNCPOINT | CMQC.MQPMO_SET_ALL_CONTEXT | CMQC.MQPMO_NEW_MSG_ID | CMQC.MQPMO_NEW_CORREL_ID);
		
			connectedQueues.put(targetQueue,queue);
			return queue;
		}
	}

	private static void commitCachedQmgrs() {
		// Commit all connected qmgrs
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
				currQmgr.disconnect();
			} catch (MQException e) {
				e.printStackTrace();
			}
		}
		connectedQueues.clear();
		connectedQmgrs.clear();
	}

	/**
	 * Sets the data of the incoming into the message
	 * @param pData - The data of the message to set in the msg
	 * @param pMsg - The mq message that will be updated with the new data
	 * @return The new rfh header that was deployed into the message, null if there was error
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws UnsupportedEncodingException 
	 * @throws MQException 
	 * @throws ParseException 
	 * @throws DOMException 
	 */
	private static String createMQMessageFromIncoming(Incoming data, MQMessage msg,String pOutGoingGUID,String pUserName, String pTargetQueueManager) throws UnsupportedEncodingException, SAXException, IOException, MQException, DOMException, ParseException{
		
		String editedRFH = null;
		// logger.debug("inc createMQMessageFromIncoming msq = " +msg.toString());
		if(data.getMqMessageId() != null){
			msg.messageId = data.getMqMessageId().getBytes();
		}
		
		// Try to parse the mqmd as xml	
		Document mqmdXML = xmlParser.parse(new ByteArrayInputStream(data.getMqMD().toString().getBytes("UTF-8")));
		
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
		
		// Time handling
		SimpleDateFormat DateMQMDformatting = new SimpleDateFormat("yyyy-MM-dd");
		Date putDate = DateMQMDformatting.parse(mqmdXML.getElementsByTagName("PutDate").item(0).getTextContent());
		String  formats[] = {"hh:mm:ss","hh:mm:ss.SSS"};
		Date putTime = DateUtils.parseDate(mqmdXML.getElementsByTagName("PutTime").item(0).getTextContent(), formats);
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
			
			Document cloneRfhXML =  (Document) RfhXML.cloneNode(true);
			NodeList nl = cloneRfhXML.getElementsByTagName("MQRFH2");
		   	Node n = nl.item(0);
		   	
		   	// Remove all elements except the NameValue folders
		   	for (int i =0; i < 6; i++) {
		   		n.removeChild(n.getFirstChild());
		   	}
		   	
		   	//GIDI: Add ResentFromArchive folder

		   	
	        
// add expiry
	        NodeList usrList = cloneRfhXML.getElementsByTagName("usr");
	        Node usr = null;
	        if(usrList.getLength() > 0){
	        	usr = usrList.item(0);
	        }else{
	        	usr = cloneRfhXML.createElement("usr");
	        	n.appendChild(cloneRfhXML.importNode(usr, true));
	        }
	        
	        NodeList menoraList = cloneRfhXML.getElementsByTagName("MenoraInfra");
	        Node MenoraInfra = null;
	        if(menoraList.getLength() > 0){
	        	MenoraInfra = menoraList.item(0);
	        }else{
	        	MenoraInfra = cloneRfhXML.createElement("MenoraInfra");
	        	usr.appendChild(MenoraInfra);
	        }
	        long expiryTs = -1;
	        if(data.getExpiryTime() != null) {
	        	expiryTs = data.getExpiryTime().getTime();
	        }
	        if(expiryTs > 0 ) {
	        
		        NodeList expiryNodeExist = cloneRfhXML.getElementsByTagName("mnrExpiryTime");
		        Node expiryNode = null;
		        if(expiryNodeExist.getLength() > 0){
		        	expiryNode = expiryNodeExist.item(0);
		        }else{
		        	expiryNode = cloneRfhXML.createElement("mnrExpiryTime");
		        }
		        String expiryStr = df.format(expiryTs);
		        logger.debug("expiryStr =" + expiryStr);
		        expiryNode.setTextContent(expiryStr);
		        usr.appendChild(expiryNode);
	        }
	        // Create the resend from archive data
	        Element ResentFromArchive = cloneRfhXML.createElement("ResentFromArchive");

	        // -------------------------    Handle FromOutgoingID Field
	        Node FromOutgoingID = cloneRfhXML.createElement("FromOutgoingId");
	        FromOutgoingID.appendChild(cloneRfhXML.createTextNode(pOutGoingGUID));
	        ResentFromArchive.appendChild(FromOutgoingID);
	        
	        // -------------------------    Handle ResendTime Field
	        SimpleDateFormat DateResendformatting = new SimpleDateFormat("dd-MMM-yy-dd hh:mm:ss.SSS a",Locale.ENGLISH);
	        Date now = new Date();
	        Node ResendTime = cloneRfhXML.createElement("ResendTime");
	        ResendTime.appendChild(cloneRfhXML.createTextNode(DateResendformatting.format(now)));
	        ResentFromArchive.appendChild(ResendTime);
	        
	        // -------------------------    Handle ResenByUser Field
	        Node ResentByUser = cloneRfhXML.createElement("ResentByUser");
	        ResentByUser.appendChild(cloneRfhXML.createTextNode(pUserName));
	        ResentFromArchive.appendChild(ResentByUser);

	        // -------------------------    Handle ResentByUser Field
	        Node wasEdited = cloneRfhXML.createElement("wasEdited");
	        // used for future version, supposely user can edit some content of the RFH,
	        boolean isEdited = false;
	        wasEdited.appendChild(cloneRfhXML.createTextNode(String.valueOf(isEdited)));
	        ResentFromArchive.appendChild(wasEdited);
	        
	        // -------------------------    Handle ToQueue Field
	        Node ToQueue = cloneRfhXML.createElement("ToQueue");
	        ToQueue.appendChild(cloneRfhXML.createTextNode(data.getFromQueue()));
	        ResentFromArchive.appendChild(ToQueue);
	        
	        // -------------------------    Handle ToQmgr Field
	        Node ToQmgr = cloneRfhXML.createElement("ToQmgr");
	        
	        String qmgrName = pTargetQueueManager;
	        
	        if(StringUtils.isEmpty(qmgrName)){
	        	throw new RuntimeException("No alias found for queue manager " + data.getFromQMgr());
        	}
	        
	        ToQmgr.appendChild(cloneRfhXML.createTextNode(qmgrName));
	        ResentFromArchive.appendChild(ToQmgr);
	       
	        
	        MenoraInfra.appendChild(ResentFromArchive);
	        //xmlDoc.appendChild(ResentFromArchive);

	        // n.appendChild(cloneRfhXML.importNode(ResentFromArchive, true));
	        editedRFH = new XMLDocument(n).toString();
	        // logger.info("ResentFromArchive: " + editedRFH);
	
	      //GIDI:  end of   ResentFromArchive folder creation
	        
		 // get an array of padded value of each folder
		   	LinkedList<String> ll = new LinkedList<String>();
		   	int sumOfLengths = 0;
		   	int FolderCounter= 0;
		   	while (n.hasChildNodes()) {		   		 
		   		String RFH2Folder = new XMLDocument(n.getFirstChild()).toString().trim();
		   		if(RFH2Folder.length() > 0){
			   		// pad the folders to be a multiple of 4 bytes length
			   		while ((RFH2Folder.getBytes("UTF-8").length) % 4 != 0) {
				   		RFH2Folder = RFH2Folder + " ";
					}
			   		// System.out.println("padded RFH2Folder is: " + RFH2Folder);
			   		sumOfLengths = sumOfLengths + RFH2Folder.length();
			   		FolderCounter++;
			   		ll.add(RFH2Folder);
		   		}
		   		n.removeChild(n.getFirstChild());
		   	}
		   	
		   	
			// write the fields of RFH2 to the MQMessage (as part of the data)
		   	// this is the only way, since there is no built-in way in Java MQ classes to write RFH2 headers into a message.		
			msg.writeString(CMQC.MQRFH_STRUC_ID); // StrucId 
			msg.writeInt4(CMQC.MQRFH_VERSION_2); // Version 
			msg.writeInt4(CMQC.MQRFH_STRUC_LENGTH_FIXED_2 +  4*FolderCounter + sumOfLengths); // StrucLength
			Integer encoding = null;
			try{
				encoding = Integer.parseInt(RfhXML.getElementsByTagName("Encoding")	.item(0).getTextContent());
			}catch(Exception e){
				encoding = 546;
			}
			msg.writeInt4(encoding); 
			Integer CodedCharSetId = null;
			try{
				CodedCharSetId = Integer.parseInt(RfhXML.getElementsByTagName("CodedCharSetId").item(0).getTextContent());
			}catch(Exception e){
				CodedCharSetId = 862;
			}
			msg.writeInt4(CodedCharSetId); // CodedCharacterSetId
			if(RfhXML.getElementsByTagName("Format") != null){
				msg.writeString(RfhXML.getElementsByTagName("Format").item(0).getTextContent()); //Format (content)
			}
			Integer Flags = null;
			try{
				Flags = Integer.parseInt(RfhXML.getElementsByTagName("Flags").item(0).getTextContent());
			}
			catch(Exception e){
				Flags = 0;
			}
			msg.writeInt4(Flags); // Flags
			
			Integer NameValueCCSID = null;
			try{
				NameValueCCSID = Integer.parseInt(RfhXML.getElementsByTagName("NameValueCCSID").item(0).getTextContent());
			}catch(Exception e){
				NameValueCCSID = 1208;
			}
			
			msg.writeInt4(NameValueCCSID); // CodedCharacterSetId
			
			//write folders of RFH2
			for(String s : ll) {
				msg.writeInt4(s.getBytes("UTF-8").length);
				// System.out.println("writing RFH2 folder of length: " + s.getBytes("UTF-8").length);
				msg.writeString(s);	
				// System.out.println("the folder value: " + s);
			}
		}
		
		if (data.getPayload().getPayloadSize() > 0) {
			msg.writeString(data.getPayload().getPayload());
		} 
		
		
		
//		msg.backoutCount = (int)data.getMqBackoutCount();
		 logger.info("editedRfhXML=" + new XMLDocument(editedRFH).toString());

		return editedRFH;
		
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
