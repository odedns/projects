/**
 * 
 */
package menora.ccm.taxreport.services;

import javax.jws.WebService;

/**
 * @author Oded Nissan
 *
 */
@WebService(targetNamespace = "http://services.taxreport.ccm.menora/", endpointInterface = "menora.ccm.taxreport.services.TaxReportService", portName = "TaxReportServiceImplPort", serviceName = "TaxReportServiceImplService")
public class TaxReportServiceImpl implements TaxReportService {

	/* (non-Javadoc)
	 * @see menora.ccm.taxreport.services.TaxReportService#generateForId(int)
	 */
	@Override
	public TaxReportResponse generateForId(long id, int popCode) throws Exception {
		// TODO Auto-generated method stub
		TaxReportGenerator generator = new TaxReportGenerator();
		TaxReportResponse resp = generator.generateForId(id,popCode);
		return(resp);
	}

	/* (non-Javadoc)
	 * @see menora.ccm.taxreport.services.TaxReportService#generateForPop(int)
	 */
	@Override
	public TaxReportResponse generateForPop(int popCode) throws Exception {
		// TODO Auto-generated method stub
		TaxReportGenerator generator = new TaxReportGenerator();
		TaxReportResponse resp = generator.generateForPop(popCode);
		return(resp);
	}

	@Override
	public TaxReportResponse generateForPolicy(long id, int popCode, long policy) throws Exception {
		// TODO Auto-generated method stub
		TaxReportGenerator generator = new TaxReportGenerator();
		TaxReportResponse resp = generator.generateForPolicy(id,popCode,policy);
		return(resp);	}

	@Override
	public TaxReportResponse ftpTransfer(long executionNumber) {
		// TODO Auto-generated method stub
		TaxReportGenerator generator = new TaxReportGenerator();
		TaxReportResponse resp = generator.ftpTransfer(executionNumber);
		return(resp);
	}

}
