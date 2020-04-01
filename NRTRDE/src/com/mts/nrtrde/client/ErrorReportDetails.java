/**
 * 
 */
package com.mts.nrtrde.client;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * @author Oded Nissan
 *
 */
public class ErrorReportDetails extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errFileName;
	String VPMN;
	String errorCode;
	String recordType;
	Date EREventDate;
	String NRTRDEFileName;
	int recordNum;
	
	
	public String getErrFileName() {
		return errFileName;
	}
	public void setErrFileName(String errFileName) {
		this.errFileName = errFileName;
	}
	public String getVPMN() {
		return VPMN;
	}
	public void setVPMN(String vPMN) {
		VPMN = vPMN;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public Date getEREventDate() {
		return EREventDate;
	}
	public void setEREventDate(Date eREventDate) {
		EREventDate = eREventDate;
	}
	public String getNRTRDEFileName() {
		return NRTRDEFileName;
	}
	public void setNRTRDEFileName(String nRTRDEFileName) {
		NRTRDEFileName = nRTRDEFileName;
	}
	public int getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
	

}
