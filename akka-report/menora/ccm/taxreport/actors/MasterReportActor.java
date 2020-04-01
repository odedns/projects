/**
 * File: MasterReportActor.java
 * Date: 28 בספט 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.actors;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import menora.ccm.utils.EntityManagerFactoryCache;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author Oded Nissan
 * This is the master actor which is responsible for coordinating the batch process.
 * It first receives the start message and then activate the worker actors that 
 * are responsible for generating an xml file for each chunk of records.
 * After all work is done the ftpActor is activated to ftp the generated files.
 *
 */
public class MasterReportActor extends UntypedActor {
	private static EntityManagerFactory emf = EntityManagerFactoryCache.getEntityManagerFactory("taxreport.dev");

	private static Logger log = Logger.getLogger(MasterReportActor.class); 
	private List<Long> queue;
	private StopWatch watch;
	private int chunk;
	private Properties props;
	private int popCode;
	private DB mapDb;
	private Map<Long, Long> workMap;
	private long count;
	
	
	/**
	 * empty constructor.
	 */
	public MasterReportActor()
	{
		
	}
	
	/**
	 * constructor for MasterReportActor.
	 * @param props Properties from file.
	 */
	public MasterReportActor(Properties props)
	{
		this.setProps(props);
	}
	
	/**
	 * create a MasterReportActor
	 * @param p Properties.
	 * @return Props.
	 */
	public static Props mkProps(Properties p)
	{
		return(Props.create(MasterReportActor.class,p));
	}
	
	/* (non-Javadoc)
	 * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
	 */
	@Override
	public void onReceive(Object obj)  throws Exception {
		// TODO Auto-generated method stub
		
		if(obj instanceof ReportMessage) {
			ReportMessage msg = (ReportMessage)obj;
			switch(msg.getType()) {
				case ReportMessage.TYPE_START:
					handleStartMessage(msg);
					break;
				case ReportMessage.TYPE_REPLY:
					handleReplyMessage(msg);
					break;
				case ReportMessage.TYPE_FTP_DONE:
					handleFtpReplyMessage(msg);
					break;
				default:
					break;
			
			} // switch
			
			
		} else {			
			unhandled(obj);
		}

	}
	
	
	/**
	 * handle the start message sent to the master actor.
	 * create the queue and distribute work to the ReportActors.
	 * @param msg the ReportMessage received.
	 */
	private void handleStartMessage(ReportMessage msg) 
	{
		int numActors =  Integer.parseInt(props.getProperty("taxreport.numActors"));
		this.chunk = Integer.parseInt(props.getProperty("taxreport.chunkSize"));
		log.info("MasterActor got start message numActors=" + numActors + " chunk =" + chunk);
		this.popCode = msg.getPopCode();
		watch = new StopWatch();
		watch.start();
		this.count = getNumReports();
		log.info("Number of report to generate : " + count);
		Object o[] = getMinMax();
		long min = (long) o[0];
		long max = (long) o[1];
		queue =createQueue(min,max,chunk);
		for(int i=0; i < numActors; ++i) {
			Props p = ReportActor.mkProps(this.props);
			ActorRef actor = getContext().actorOf(p);
			if(queue.size() > 0) {
				Long n = queue.remove(0);
				actor.tell(new ReportMessage(ReportMessage.TYPE_WORK,n,popCode), getSelf());
			} else {
				log.warn("queue is empty additional actor not needed..");
			}
			

		}
	}

	/**
	 * handle the reply message sent from the ReportActor.
	 * remove the offset from the map and send the actor more work.
	 * @param msg the message received.
	 */
	private void handleReplyMessage(ReportMessage msg)
	{
		log.debug("MasterActor got reply message");
		Long n = null;
		if(workMap.size() > 0) {
			log.debug("removing from map: " + msg.getOffset());
			workMap.remove(msg.getOffset());
			mapDb.commit();
			
		} 
		if(queue.size() > 0) {
			n = queue.remove(0);
		}
		if(n != null) {
			log.debug("sending actor more work offset: " + n);
			getSender().tell(new ReportMessage(ReportMessage.TYPE_WORK,n,popCode), getSelf());
		} else {
			/*
			 * queue is empty stop the actor.
			 */
			log.info("queue is empty stopping actor....map size : " + workMap.size());
			getContext().stop(getSender());
			/*
			 * if the workqueue is empty it means that all work is done.
			 * We can now activate the ftpActor and shutdown after its done.
			 */
			if(workMap.size() == 0) {
				log.info("both queues are empty - generation is done..time elapsed for report: " +watch.toString());
				mapDb.close();
				sendFtpMessage();
				//getContext().system().shutdown();
			}
		}
		
	}
	
	/**
	 * handle the ftp reply message. shutdown the container.
	 * @param msg ReportMessage
	 */
	private void handleFtpReplyMessage(ReportMessage msg)
	{
		log.debug("got FtpReply message...shutting down ...");
		getContext().system().shutdown();
	}
	
	/**
	 * create a queue containing the offsets for the count of records.
	 * @param count the total record count.
	 * @param chunk the size of each chunk which determined the offset.
	 * @return the queue
	 */
	public List<Long> createQueue(long min, long max,int chunk)
	{
		log.info("create queue min = " + min + " max = " + max);
		String dbFileName = props.getProperty("taxreport.mapdb.file");
		String mapName = props.getProperty("taxreport.mapdb.map");
		String retrieveStrategy = (String) props.get("taxreport.retrieveStrategy");

		File f = new File(dbFileName);
		mapDb = DBMaker.newFileDB(f).make();
		workMap = mapDb.getTreeMap(mapName);
		List<Long> queue  = Collections.synchronizedList(new LinkedList<Long>());
		/*
		 * if there are remaining entries in the map 
		 * create the queu from the map
		 */
		if(workMap.size() > 0 ) {
			log.debug("creatQueueFromMap");
			creatQueueFromMap(workMap, queue);			
		} else {
			log.debug("creatMapFromQueue");
			/*
			 * create queue based on retrieveStrategy
			 */
			if(!retrieveStrategy.equals("JPA")) {
				/*
				 * create list and put the entries in the map too.
				 */				
				for(long i=min; i < max; i+=chunk) {
					queue.add(i);								
				}
			} else { // JPA retreiveStrategy
			
				for(long i=0; i < this.count; i+=chunk) {
					queue.add(i);				
				}
			}
			creatMapFromQueue(workMap, queue);
			mapDb.commit();
		}
		return(queue);
	}
	
	/**
	 * create a queue from the map in case the map contains data
	 * @param m the map
	 * @param l the queue
	 */
	private void creatQueueFromMap(Map<Long,Long> m,List<Long> l)
	{
		Set <Long>keys =  m.keySet();
		for(Long n : keys) {
			l.add(n);
		}
	}
	
	/**
	 * create map from queue in case we are doing a fresh run and map is empty.
	 * @param m the map
	 * @param l the queue.
	 */
	private void creatMapFromQueue(Map<Long,Long> m,List<Long> l)
	{
		for(Long n : l) {
			m.put(n, n);
		}		
	}

	/**
	 * get the total number of reports from the table TaxReportInsured.
	 * @return the total number of reports.
	 */
	public long getNumReports()
	{
		
		EntityManager em = emf.createEntityManager();
		long count = (long) em.createNamedQuery("TaxReportInsured.count").setParameter("popCode", this.popCode).getSingleResult();
		return(count);
	}
	
	
	public Object[] getMinMax()
	{
		EntityManager em = emf.createEntityManager();
		Object o[] = (Object[]) em.createNamedQuery("TaxReportInsured.min_max").setParameter("popCode", this.popCode).getSingleResult();
		return(o);
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public void sendFtpMessage()
	{
		log.debug("sending FTP_START message.");
		Props p = FtpActor.mkProps(this.props);
		ActorRef actor = getContext().actorOf(p);
		actor.tell(new ReportMessage(ReportMessage.TYPE_FTP_START), getSelf());
	}

	
}
