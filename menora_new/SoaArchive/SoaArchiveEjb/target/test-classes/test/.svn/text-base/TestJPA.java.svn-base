package test;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import il.co.menora.soaarchive.ejb.GeneralBean;

public class TestJPA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Properties p = new Properties();
		p.put("java.naming.factory.initial", "org.apache.openejb.client.RemoteInitialContextFactory");
		p.put("java.naming.provider.url", "http://127.0.0.1:8080/tomee/ejb");


		//InitialContext ctx;
		try {
			//ctx = new InitialContext(p);
			EJBContainer container = EJBContainer.createEJBContainer();
			Context ctx = (Context) container.getContext();
			GeneralBean myBean = (GeneralBean) ctx.lookup("java:global/archive/GeneralBean");
			System.out.println("got general bean");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
