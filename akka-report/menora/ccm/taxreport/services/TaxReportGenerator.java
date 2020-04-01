/**
 * 
 */
package menora.ccm.taxreport.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import menora.ccm.taxreport.actors.FtpActor;
import menora.ccm.taxreport.actors.ReportMessage;
import menora.ccm.taxreport.entities.TaxReportInsured;
import menora.ccm.taxreport.entities.TaxReportPolicy;
import menora.ccm.taxreport.entities.TaxReports;
import menora.ccm.utils.PropertyReader;


/**
 * @author Oded Nissan
 *
 */
public class TaxReportGenerator {

	private static String PROPS_FILE = "taxreport.properties";
	private static String PUNIT_KEY = "taxreport.persistenceUnit";
	private static String OUTPUT_DIR = "taxreport.outputDir";
	private Properties props;
	private Logger log = Logger.getLogger(TaxReportGenerator.class);
	private EntityManagerFactory factory = null;

	
	/**
	 * constructor.
	 */
	public TaxReportGenerator()
	{
		try {
			props = PropertyReader.read(PROPS_FILE);
			String punit = props.getProperty(PUNIT_KEY);
			log.info("perssitence unit = " + punit);
			factory = Persistence.createEntityManagerFactory(punit);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			props = null;
			log.error("Cannot read properties file: " + PROPS_FILE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate for a single id
	 * @param id insured id.
	 * @return TaxReportResponse
	 * @throws Exception in case of error.
	 */
	public TaxReportResponse generateForId(long id, int popCode) throws Exception
	{
		log.debug("in generate id = " + id +" popCode=" + popCode);
		/*
		 * get the object.
		 */
		EntityManager em = factory.createEntityManager();
		TaxReportInsured insured = (TaxReportInsured) em.createNamedQuery("TaxReportInsured.findByIdPopulation").setParameter("id", id).setParameter("popCode",popCode).getSingleResult();
		log.debug("got insured = " + insured.getFirstName() + " " + insured.getLastName());
		
		TaxReports reports = new TaxReports();
		reports.addReport(insured);
		reports.setReportYear("2014");

		TaxReportResponse resp = new TaxReportResponse();
		generateFile(reports, resp);
		resp.setRetCode(0);
		return(resp);
	}
	
	/**
	 * Generate for a whole population
	 * @param popCode population code
	 * @return TaxReportResponse
	 * @throws Exception in case of error.
	 */
	
	public TaxReportResponse generateForPop(int popCode) throws Exception
	{
		log.debug("in generateForPop popCode = " + popCode);
		TaxReportResponse resp = new TaxReportResponse();
		/*
		EntityManager em = factory.createEntityManager();
		List<TaxReportInsured> listReports = em.createNamedQuery("TaxReportInsured.findByPopulation",TaxReportInsured.class ).
				setParameter("popCode", popCode).getResultList();
		TaxReports reports = new TaxReports();
		reports.setReportYear("2014");
		reports.setReportList(listReports);
		generateFile(reports, resp);
		*/
		long executionId = generateBatchExecutionId();
		resp.setRetCode(0);
		resp.setBatchExecutionNumber(executionId);
		return(resp);
	}
	
	public TaxReportResponse generateForPolicy(long id, int popCode,long policyId) throws Exception
	{
		log.debug("int generate for policy id = " + id +" popCode=" + popCode + " policyId= " + policyId);
		/*
		 * get the object.
		 */
		EntityManager em = factory.createEntityManager();
		TaxReportInsured insured = (TaxReportInsured) em.createNamedQuery("TaxReportInsured.findByIdPopulation").setParameter("id", id).setParameter("popCode",popCode).getSingleResult();
		log.debug("got insured = " + insured.getFirstName() + " " + insured.getLastName());
		/*
		 * remove all policies and add just the specific policy.
		 */
		List<TaxReportPolicy> policies = insured.getTaxReportPolicies();
		TaxReportPolicy policy = null;
		for(TaxReportPolicy p : policies) {
			if(p.getPolicySequence() == policyId) { 
				policy = p;
				break;
			}
		}
		insured.getTaxReportPolicies().clear();
		insured.getTaxReportPolicies().add(policy);
		
		
		TaxReports reports = new TaxReports();
		reports.addReport(insured);
		reports.setReportYear("2014");

		TaxReportResponse resp = new TaxReportResponse();
		generateFile(reports, resp);
		resp.setRetCode(0);
		return(resp);
		
	}
	
	private void generateFile(TaxReports reports, TaxReportResponse resp) throws Exception
	{
		/*
		 * marshall the object to an xml file.
		 */
		JAXBContext ctx = JAXBContext.newInstance(TaxReports.class);
		javax.xml.bind.Marshaller m = ctx.createMarshaller();
		m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING,"UTF-8");
		int size = reports.getReportList().size();
		long first = reports.getReportList().get(0).getInsuredSequence();
		log.info("Generating report for : " + size  + " reports..");
		log.info("report start: " + System.currentTimeMillis());
		String outputDir = props.getProperty(OUTPUT_DIR);
		String path = outputDir + '/' + "TaxReport-" + "Y-" + first + '-' + size + ".xml";
		File f = new File(path);
		resp.setPdfUrl(path);
		PrintWriter writer = new PrintWriter(f,"UTF-8");
		m.marshal(reports,writer);
		log.info("report end: " + System.currentTimeMillis());
		
	}
	
	
	/**
	 * complete the ftp operation 
	 * pass the excutionNumber and complete the ftp transfer by
	 * calling the ftpActor. 
	 * @param executionNumber the xecutionNumber to complete
	 * @return TaxReportResponse.
	 */
	public TaxReportResponse ftpTransfer(long executionNumber)
	{
	
		log.debug("sending FTP_START message.");
		this.props.put("taxreport.executionNumber", executionNumber);
		ActorSystem system = ActorSystem.create("TaxtReportActorSystem");
		Props p = FtpActor.mkProps(this.props);
		ActorRef actor = system.actorOf(p);
		actor.tell(new ReportMessage(ReportMessage.TYPE_FTP_START), actor);
		TaxReportResponse resp = new TaxReportResponse();
		resp.setRetCode(0);
		resp.setBatchExecutionNumber(executionNumber);
		return(resp);
	}
	
	public long generateBatchExecutionId()
	{
		return(System.currentTimeMillis());
	}
	
}
