/**
 * 
 */
package menora.ccm.taxreport.tests;

import static org.junit.Assert.*;
import menora.ccm.taxreport.services.TaxReportGenerator;
import menora.ccm.taxreport.services.TaxReportResponse;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author ODEDNI
 * Test for the generator.
 *
 */
public class TaxReportGeneratorTest {

	private static TaxReportGenerator generator;
	/**
	 * Test method for {@link menora.ccm.taxreport.services.TaxReportGenerator#generateForId(int)}.
	 */
	@Test
	public void testGenerateForId() {
		int id = 345083;
		int popCode = 300;
		TaxReportResponse resp= null;
		try {
			resp = generator.generateForId(id,popCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertEquals(0, resp.getRetCode());
		
	}
	@Test
	public void testGenerateForPolicy() {
		int id = 345083;
		int popCode = 300;
		int policy = 178522;
		TaxReportResponse resp= null;
		try {
			resp = generator.generateForPolicy(id,popCode,policy);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertEquals(0, resp.getRetCode());
		
	}

	/**
	 * Test method for {@link menora.ccm.taxreport.services.TaxReportGenerator#generateForPop(int)}.
	 */
	@Test
	public void testGenerateForPop() {
		
		int popCode = 300;
		TaxReportResponse resp= null;
		try {
			resp = generator.generateForPop(popCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertEquals(0, resp.getRetCode());
	}
	
	@Test
	public void testFtptransfer()
	{
		TaxReportResponse resp= null;
		long executionNumber = generator.generateBatchExecutionId();
		try {
			resp = generator.ftpTransfer(executionNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertEquals(0, resp.getRetCode());
		
	}

	@BeforeClass
	public static void init()
	{
		generator = new TaxReportGenerator();
	}
	
}
