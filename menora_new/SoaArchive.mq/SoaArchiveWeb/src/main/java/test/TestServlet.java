package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import il.co.menora.soaarchive.ejb.GeneralBean;
import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.entities.Outgoing;
import il.co.menora.soaarchive.entities.Payload;
import il.co.menora.soaarchive.shared.ApplicationInfoDto;

public class TestServlet extends HttpServlet {
	@PersistenceContext
	EntityManager em;
	@EJB
	GeneralBean genBean;
	@Resource
	UserTransaction tx;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in doGet TestServlet");
		PrintWriter pw = resp.getWriter();
		pw.println("<h2>This is test servlet</h2>");
		ApplicationInfoDto dto = genBean.getApplicationInfo();
		pw.println("dto =" + dto.toString());
		
		generateData(em, tx, pw);
		generateOutgoing(em, tx, pw);
		
		
		
	}
	
	
	
	void generateData(EntityManager em,UserTransaction ut, PrintWriter pw)
	{
		try {
			tx.begin();
			for(int i=0; i < 10000; ++i) {
				Incoming inc = new Incoming();
				inc.setBipMsg("bipMsh");
				inc.setCloseTime(new Date());
				inc.setErrorCode("errCode");
				inc.setErrorMessage("Error message");
				inc.setErrorTime(new Date());
				inc.setFromOutgoingId(11);
				inc.setFromQMgr("Qmgr");
				inc.setFromQueue("fromQurur");
				inc.setFromServerIp("10.10.0.10");
				inc.setFromServerName("datapower-srv");
				inc.setFullException("exception");
				inc.setGetTime(new Date());
				inc.setIncomingTime(new Date());
				inc.setLastUser("Oded");
				inc.setMqBackoutCount(0);
				inc.setMqId(1000);
				inc.setMqMessageId(1001);
				inc.setMqRfh2("<some data headers >");
				inc.setRerunsNum(0);
				inc.setServiceName("dp-service-1");
				inc.setServiceType("dp");
				inc.setStatus("NEW");
				inc.setStatusTime(new Date());
				Payload p = new Payload();
				p.setPayload("some payload ....");
				p.setPayloadSize(100);
				inc.setPayload(p);
				em.persist(inc);
			}
			tx.commit();
			pw.println("Saved payload");
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}
	
	void generateOutgoing(EntityManager em,UserTransaction ut, PrintWriter pw)
	{
		try {
			tx.begin();
			for(int i=0; i < 150; ++i) {
				Outgoing out = new Outgoing();
				out.setIncomingId(i);
				out.setMqId(1000);
				out.setMqRfh2("mqmh2");
				out.setResendTime(new Date());
				out.setToQMgr("qmgr1");
				out.setToQueue("queue1");
				Payload p = new Payload();
				p.setPayload("some payload ....");
				p.setPayloadSize(100);
				out.setPayload(p);
				em.persist(out);
			}
			tx.commit();
			pw.println("Saved payload");
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}

}
