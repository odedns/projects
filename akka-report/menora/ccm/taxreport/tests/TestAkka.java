/**
 * File: TestAkka.java
 * Date: 15 בספט 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.tests;

import java.io.IOException;
import java.util.Properties;

import menora.ccm.taxreport.actors.MasterReportActor;
import menora.ccm.taxreport.actors.ReportMessage;
import menora.ccm.utils.PropertyReader;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * @author ODEDNI
 *
 */
public class TestAkka {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Test Akka...");
		Properties p = null;
		try {
			p = PropertyReader.read("taxreport.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.put("taxreport.executionNumber", new Long(100));
		ActorSystem system = ActorSystem.create("TaxtReportActorSystem");
		final ActorRef actor = system.actorOf(MasterReportActor.mkProps(p));
		System.out.println("path = " + actor.path().toString());
		
		actor.tell(new ReportMessage(ReportMessage.TYPE_START,0,300), actor);
		
		
	}
	

}
