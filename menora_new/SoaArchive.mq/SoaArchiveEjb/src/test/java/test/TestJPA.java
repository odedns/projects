package test;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Init;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;

import il.co.menora.soaarchive.ejb.GeneralBean;
import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.entities.Payload;
import il.co.menora.soaarchive.entities.QMgrConnData;
import il.co.menora.soaarchive.mq.MQRequestsHandler;
import il.co.menora.soaarchive.mq.SendMQRequest;
import il.co.menora.soaarchive.shared.MQDto;

public class TestJPA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
//		Properties p = new Properties();
//		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
//		p.put("java.naming.provider.url", "http://127.0.0.1:8080/tomee/ejb");
//
//
//		//InitialContext ctx;
//		try {
//			//ctx = new InitialContext(p);
//			EJBContainer container = EJBContainer.createEJBContainer();
//			Context ctx = (Context) container.getContext();
//			GeneralBean myBean = (GeneralBean) ctx.lookup("java:global/archive/GeneralBean");
//			System.out.println("got general bean");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String qmgr = "QMIIBQA1";
		String queue = "TEST2";
		
		// MQEnvironment.sslCipherSuite = "SSL_RSA_WITH_3DES_EDE_CBC_SHA";//"TLS_RSA_WITH_3DES_EDE_CBC_SHA";
		
		
		List<QMgrConnData> list = new ArrayList<QMgrConnData>();
		list.add(new QMgrConnData(1,qmgr,"EMPLOYERS","172.22.5.49",1475));
		MQRequestsHandler.init(list);
		
		List<Incoming> incomingRows = new ArrayList<Incoming>();
		Incoming incomingRow = new Incoming();
		incomingRow.setMqMessageId("0fBAB32");
//		incomingRow.setMQMD("<MQMD>   <SourceQueue>TEST.ROEI.IN</SourceQueue>   <Transactional>true</Transactional>   <Encoding>546</Encoding>   <CodedCharSetId>1208</CodedCharSetId>   <Format>MQSTR   </Format>   <Version>2</Version>   <Report>0</Report>   <MsgType>8</MsgType>   <Expiry>-1</Expiry>   <Feedback>0</Feedback>   <Priority>0</Priority>   <Persistence>0</Persistence>   <MsgId>414d5120514d49494244312020202020c4b5a05702c6ae20</MsgId>   <CorrelId>000000000000000000000000000000000000000000000000</CorrelId>   <BackoutCount>0</BackoutCount>   <ReplyToQ>                                                </ReplyToQ>   <ReplyToQMgr>QMIIBD1                                         </ReplyToQMgr>   <UserIdentifier>mqm         </UserIdentifier>   <AccountingToken>0000000000000000000000000000000000000000000000000000000000000000</AccountingToken>   <ApplIdentityData>                                </ApplIdentityData>   <PutApplType>28</PutApplType>   <PutApplName>WebSphere MQ Client for Java</PutApplName>   <PutDate>2016-09-11</PutDate>   <PutTime>14:00:58.370</PutTime>   <ApplOriginData>    </ApplOriginData>   <GroupId>000000000000000000000000000000000000000000000000</GroupId>   <MsgSeqNumber>1</MsgSeqNumber>   <Offset>0</Offset>   <MsgFlags>0</MsgFlags>   <OriginalLength>-1</OriginalLength> </MQMD>");
		incomingRow.setMQMD("<MQMD>   <SourceQueue>TEST.ROEI.IN</SourceQueue>   <Transactional>true</Transactional>   <Encoding>546</Encoding>   <CodedCharSetId>862</CodedCharSetId>   <Format>MQSTR   </Format>   <Version>2</Version>   <Report>0</Report>   <MsgType>8</MsgType>   <Expiry>-1</Expiry>   <Feedback>0</Feedback>   <Priority>0</Priority>   <Persistence>1</Persistence>   <MsgId>555577777777777777777777777777777777777777777777</MsgId>   <CorrelId>444400000000000000000000000000000000000000000077</CorrelId>   <BackoutCount>77</BackoutCount>   <ReplyToQ>GIDI                                            </ReplyToQ>   <ReplyToQMgr>QMIIBD2                                         </ReplyToQMgr>   <UserIdentifier>m7m         </UserIdentifier>   <AccountingToken>5555000000000000000000000000000000000000000000000000000000000000</AccountingToken>   <ApplIdentityData>                                </ApplIdentityData>   <PutApplType>28</PutApplType>   <PutApplName>WebSphere MQ Client for GIDI</PutApplName>   <PutDate>2016-09-11</PutDate>   <PutTime>14:00:58.370</PutTime>   <ApplOriginData>    </ApplOriginData>   <GroupId>000000000000000000000000000000000000000000000000</GroupId>   <MsgSeqNumber>1</MsgSeqNumber>   <Offset>0</Offset>   <MsgFlags>1</MsgFlags>   <OriginalLength>-1</OriginalLength> </MQMD>");  
		incomingRow.setFromQMgr(qmgr);
		incomingRow.setFromQueue(queue);
		incomingRow.setMqRfh2("<MQRFH2><Encoding>2</Encoding><CodedCharSetId>918</CodedCharSetId><Format>MQFMT_RF_HEADER_2</Format><Flags>2048</Flags><NameValueCCSID>816</NameValueCCSID><usr><story>Once upon a time there was a kushi, he was pretty bright for a kushi -like real smart</story></usr></MQRFH2>");
		
		Payload payload = new Payload();
		payload.setPayload("This is a test from GIDI");
		incomingRow.setPayload(payload);
		
		incomingRows.add(incomingRow);
		
		MQDto clientrequest = new MQDto();
		
		
		
		SendMQRequest mqrequest = new SendMQRequest(incomingRows, clientrequest);
		MQRequestsHandler.processMQRequest(mqrequest);
	}

}
