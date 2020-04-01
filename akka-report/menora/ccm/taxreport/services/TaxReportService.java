/**
 * 
 */
package menora.ccm.taxreport.services;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author Oded Nissan
 *
 */
@WebService(name = "TaxReportService", targetNamespace = "http://services.taxreport.ccm.menora/")
public interface TaxReportService {
	/**
	 * Generate for a single id
	 * @param id insured id.
	 * @param popCode population code
	 * @return TaxReportResponse
	 * @throws Exception in case of error.
	 */
	@WebMethod(operationName = "generateForId", action = "urn:GenerateForId")
	public TaxReportResponse generateForId(long id, int popCode) throws Exception;
	/**
	 * Generate for a whole population
	 * @param popCode population code
	 * @return TaxReportResponse
	 * @throws Exception in case of error.
	 */
	@WebMethod(operationName = "generateForPop", action = "urn:GenerateForPop")
	public TaxReportResponse generateForPop(int popCode) throws Exception;

	/**
	 * Generate for a whole population
	 * @param popCode population code
	 * @return TaxReportResponse
	 * @throws Exception in case of error.
	 */
	@WebMethod(operationName = "generateForPolicy", action = "urn:GenerateForPop")
	public TaxReportResponse generateForPolicy(long id, int popCode, long policy) throws Exception;
	

	/**
	 * complete the ftp operation 
	 * pass the excutionNumber and complete the ftp transfer by
	 * calling the ftpActor. 
	 * @param executionNumber the xecutionNumber to complete
	 * @return TaxReportResponse.
	 */
	@WebMethod(operationName = "ftpTransfer", action = "urn:FtpTransfer")
	public TaxReportResponse ftpTransfer(long executionNumber);



}
