/**
 * File: CcmTrace.java
 * Date: 16 бреб 2014
 * Author: ODEDNI
 */
package menora.ccm.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import menora.ccm.taxreport.actors.ReportActor;
import menora.ccm.taxreport.entities.TaxReportRunControl;

/**
 * @author Oded Nissan
 *
 */
public class CcmTrace {
	public static int TRACE_CCM =1;
	public static int TRACE_XML =2;
	public static int TRACE_PDF =3;
	public static int TRACE_VAULT =4;
	public static int TRACE_PRINT =5;
	public static short TRACE_STATUS_OK = 0;
	public static short TRACE_STATUS_ERROR = 1;
	private String persistenceUnit;
	private static Logger log = Logger.getLogger(CcmTrace.class);

	
	public CcmTrace(String persistenceUnit)
	{
		this.persistenceUnit = persistenceUnit;
	}
	
	
		
	public void trace(long executionNumber ,long fromId, long toId,short status,int code,String fileName,String msg)
	{
		try  {
			EntityManager em = EntityManagerFactoryCache.getEntityManagerFactory(this.persistenceUnit).createEntityManager();
			String sql = "select QGPL.MAINMRT('TRXML') from sysibm.sysdummy1";
			em.getTransaction().begin();
			BigDecimal seq = (BigDecimal) em.createNativeQuery(sql).getSingleResult();
			log.debug("got generated value = " + seq);
			TaxReportRunControl ctrlTable = new TaxReportRunControl();
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			ctrlTable.setControlNumber(seq.longValue());
			ctrlTable.setCcmFromId( new BigDecimal(fromId));
			ctrlTable.setCcmToId(new BigDecimal(toId));
			ctrlTable.setExecutionNumber(new BigDecimal(executionNumber));
			ctrlTable.setXmlFileName(fileName);
			ctrlTable.setControlStationCode(new BigDecimal(code));
			ctrlTable.setControlStationMessage(msg);
			ctrlTable.setControlStationStatus(status);
			ctrlTable.setControlStationTimestamp(ts);
			em.persist(ctrlTable);
			em.getTransaction().commit();
			em.close();
			log.debug("logged record to control table..");
		} catch(Exception e) {
			log.error(e);
		}
			
	}

}
