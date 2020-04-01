package il.co.menora.soaarchive.shared;

import java.io.Serializable;


public class IncomingDto implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String fromOutgoingId;
	private String status;
	private String serviceName;
	private String serviceType;
	private String lastUser;
	private String statusTime;
	private String errorTime;
	private String incomingTime;
	private int rerunsNum;
	private String fromQueue;
	private String fromQMgr;
	private String errorCode;
	private String errorMessage;
	private String payloadSize;
	private String additionalId;
	private String mqMD;

	
	public IncomingDto()
	{
		
	}


	

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}



	public int getRerunsNum() {
		return rerunsNum;
	}


	public void setRerunsNum(int rerunsNum) {
		this.rerunsNum = rerunsNum;
	}


	public String getFromQueue() {
		return fromQueue;
	}


	public void setFromQueue(String fromQueue) {
		this.fromQueue = fromQueue;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getLastUser() {
		return lastUser;
	}


	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}


	public String getFromQMgr() {
		return fromQMgr;
	}


	public void setFromQMgr(String fromQMgr) {
		this.fromQMgr = fromQMgr;
	}



	public String getStatusTime() {
		return statusTime;
	}


	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}


	public String getErrorTime() {
		return errorTime;
	}


	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}


	public String getIncomingTime() {
		return incomingTime;
	}


	public void setIncomingTime(String incomingTime) {
		this.incomingTime = incomingTime;
	}


	public String getMqMD() {
		return mqMD;
	}


	public void setMqMD(String mqMD) {
		this.mqMD = mqMD;
	}




	

	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String getAdditionalId() {
		return additionalId;
	}




	public void setAdditionalId(String additionalId) {
		this.additionalId = additionalId;
	}




	public String getFromOutgoingId() {
		return fromOutgoingId;
	}




	public void setFromOutgoingId(String fromOutgoingId) {
		this.fromOutgoingId = fromOutgoingId;
	}




	public String getPayloadSize() {
		return payloadSize;
	}




	public void setPayloadSize(String payloadSize) {
		this.payloadSize = payloadSize;
	}






	
}
