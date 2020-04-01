/**
 * 
 */
package com.mts.nrtrde.client;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * @author C5132784
 *
 */
public class FileDeliveryDetails extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String FDRfileName;
	private String VPMN;
	private Date startDate;
	private Date endDate;
	private String NRTRDEfileName;
	private Date ReceivedDate;
	private int recordNum;
	
	public String getFDRfileName() {
		return FDRfileName;
	}
	public void setFDRfileName(String fDRfileName) {
		FDRfileName = fDRfileName;
	}
	public String getVPMN() {
		return VPMN;
	}
	public void setVPMN(String vPMN) {
		VPMN = vPMN;
	}
	
	public String getNRTRDEfileName() {
		return NRTRDEfileName;
	}
	public void setNRTRDEfileName(String nRTRDEfileName) {
		NRTRDEfileName = nRTRDEfileName;
	}
	public Date getReceivedDate() {
		return ReceivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		ReceivedDate = receivedDate;
	}
	public int getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	
}
