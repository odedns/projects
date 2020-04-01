/**
 * 
 */
package menora.ccm.taxreport.services;

import java.io.Serializable;

/**
 * @author Oded Nissan
 *
 */
public class TaxReportResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long batchExecutionNumber;
	private String msg;
	private int retCode;
	private String pdfUrl;
	
	public TaxReportResponse()
	{
		
	}

	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}



	public long getBatchExecutionNumber() {
		return batchExecutionNumber;
	}



	public void setBatchExecutionNumber(long batchExecutionNumber) {
		this.batchExecutionNumber = batchExecutionNumber;
	}



	public String getPdfUrl() {
		return pdfUrl;
	}



	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	
}
