/**
 * File: CtrlTableTest.java
 * Date: 7 баеч 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.tests;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.EntityManager;

import menora.ccm.taxreport.entities.TaxReportRunControl;
import menora.ccm.taxreport.entities.TaxReportXmlFile;
import menora.ccm.utils.CcmTrace;
import menora.ccm.utils.EntityManagerFactoryCache;

/**
 * @author ODEDNI
 *
 */
public class CtrlTableTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("trying to create TaxReportXmlFile");
		CcmTrace tracer = new CcmTrace("taxreport.dev");
		long executionNumber = System.currentTimeMillis();
		tracer.trace(executionNumber,(long)1, (long)100, CcmTrace.TRACE_STATUS_OK, CcmTrace.TRACE_XML, "c:/somefile", "Some message");
		System.out.println("taxreportxml saved ...");
	}

}
