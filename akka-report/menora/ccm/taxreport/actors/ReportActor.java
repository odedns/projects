/**
 * File: ReportActor.java
 * Date: 28 בספט 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.actors;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import menora.ccm.taxreport.entities.TaxReportInsured;
import menora.ccm.taxreport.entities.TaxReports;
import menora.ccm.utils.CcmTrace;
import menora.ccm.utils.EntityManagerFactoryCache;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

import akka.actor.ActorInitializationException;
import akka.actor.Props;
import akka.actor.UntypedActor;



/**
 * @author Oded Nissan
 *
 */
public class ReportActor extends UntypedActor {

	private static Logger log = Logger.getLogger(ReportActor.class);
	private JAXBContext ctx;
	private int chunk;
	private String outputDir;
	private long executionNumber;
	private String persistenceUnit;
	private String retrieveStrategy;
	private CcmTrace tracer;
	
	
	/**
	 * constructor.
	 * @param chunk the chunk size.
	 * @param outputDir the outputDir for the xml files.
	 */
	public ReportActor(Properties props)  {
		// TODO Auto-generated constructor stub
		log.debug("props = " + props.toString());
		this.chunk = Integer.parseInt(props.getProperty("taxreport.chunkSize"));
		this.outputDir = props.getProperty("taxreport.outputDir");
		log.info("execution num = " + props.get("taxreport.executionNumber"));
		this.executionNumber = (long) props.get("taxreport.executionNumber");
		this.persistenceUnit = (String) props.get("taxreport.persistenceUnit");
		this.retrieveStrategy = (String) props.get("taxreport.retrieveStrategy");
		this.tracer = new CcmTrace(this.persistenceUnit);
		
		try {
			ctx = JAXBContext.newInstance(TaxReports.class);
		} catch(JAXBException je) {
			log.error(je);
			throw new ActorInitializationException(getSelf(), "Error creating JAXBContext", je);
		}

	}
	
	public static Props mkProps(Properties props)
	{
		return(Props.create(ReportActor.class, props));
	}
	
	/* (non-Javadoc)
	 * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
	 */
	@Override
	public void onReceive(Object obj) throws Exception {
		// TODO Auto-generated method stub
		if(obj instanceof ReportMessage) {
			ReportMessage msg = (ReportMessage) obj;
			log.debug("working with offset : " + msg.getOffset());
			handleWorkMessage(msg);
			getSender().tell(new ReportMessage(ReportMessage.TYPE_REPLY,msg.getOffset(),msg.getPopCode()), getSelf() );
			log.debug("ReportActor sent reply message");
		} else {
			unhandled(obj);
		}

	}
	
	/**
	 * handle the work message passed from the master actor.
	 * @param msg ReportMessage containing offset to process.
	 * @throws Exception in case of error.
	 */
	private void handleWorkMessage(ReportMessage msg) throws Exception
	{
		log.debug("handle work message...offset: " + msg.getOffset());
		StopWatch watch = new StopWatch();
		watch.start();
		EntityManager em = EntityManagerFactoryCache.getEntityManagerFactory(this.persistenceUnit).createEntityManager();
		Query query = null;
		if(this.retrieveStrategy.equalsIgnoreCase("JPA")) {
		
			query = em.createNamedQuery("TaxReportInsured.findByPopulation",TaxReportInsured.class );
			query.setFirstResult((int) msg.getOffset());
			query.setMaxResults(this.chunk);
		} else {
			
			query = em.createNamedQuery("TaxReportInsured.findByPopulationEx",TaxReportInsured.class );
			long last = msg.getOffset() + this.chunk;
			query.setParameter("first", msg.getOffset());
			query.setParameter("last", last);
		}
		@SuppressWarnings("unchecked")
		List<TaxReportInsured> listReports = query.setParameter("popCode", msg.getPopCode()).getResultList();
		log.debug("query for offset: "+ msg.getOffset() + " returned size=" + listReports.size());
		if(listReports.size() ==0) {
			log.debug("no result skipping ...:");
			return;
		}
		TaxReports reports = new TaxReports();
		reports.setReportYear("2014");
		reports.setReportList(listReports);
		em.close();
		generateFile(reports);
		watch.stop();
		log.debug("get list with offset : " + msg.getOffset() + " took: " + watch.toString());;
		
	}
	
	/**
	 * generate the xml file
	 * @param reports The collection of TaxReport objects.
	 * @throws Exception in case of error.
	 */
	private void generateFile(TaxReports reports) throws Exception 
	{
		/*
		 * marshall the object to an xml file.
		 */
		
		javax.xml.bind.Marshaller m;
		long first = 0;
		long last =0; 
		String path = null;
		
		try {
			m = ctx.createMarshaller();
			m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING,"UTF-8");
			int size = reports.getReportList().size();
			log.debug("report size = " + size);
			first = reports.getReportList().get(0).getInsuredSequence();
			last = reports.getReportList().get(size-1).getInsuredSequence();
			log.info("Generating reports for : " + size + " reports..");
			path = outputDir + '/' + "TaxReport-" + "Y-" + first + '-' + size + ".xml";
			File f = new File(path);
			PrintWriter writer = new PrintWriter(f,"UTF-8");
			m.marshal(reports,writer);
			tracer.trace(this.executionNumber, first, last, CcmTrace.TRACE_STATUS_OK,CcmTrace.TRACE_XML, path, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			tracer.trace(this.executionNumber, first, last, CcmTrace.TRACE_STATUS_ERROR, CcmTrace.TRACE_XML,path, e.getMessage());
			throw e;
		}

	}
	
	
			
	
	

}
