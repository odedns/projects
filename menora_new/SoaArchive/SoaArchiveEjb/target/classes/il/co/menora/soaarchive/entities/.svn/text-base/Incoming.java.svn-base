package il.co.menora.soaarchive.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
public class Incoming {

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO, generator="uuid-hex")
	private String id;
	private String fromOutgoingId;
	private String status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date statusTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date closeTime;
	private String lastUser;
	private String serviceName;
	private String serviceType;
	@Temporal(TemporalType.TIMESTAMP)
	private Date getTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date errorTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date incomingTime;
	private int rerunsNum;
	private String fromQueue;
	private String fromQMgr;
	private String fromServerName;
	private String fromServerIp;
	private String mqMessageId;
	@Lob
	private String mqMD;
	@Lob
	private String mqRfh2;
	private long mqBackoutCount;
	private String errorCode;
	@Lob
	private String errorMessage;
	private String bipText;
	private String bipCode;
	@Lob
	private String fullException;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryTime;
	private String dpSubCode;
	
	

	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Payload payload;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}

	public Date getIncomingTime() {
		return incomingTime;
	}

	public void setIncomingTime(Date incomingTime) {
		this.incomingTime = incomingTime;
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

	public String getFromQMgr() {
		return fromQMgr;
	}

	public void setFromQMgr(String fromQMgr) {
		this.fromQMgr = fromQMgr;
	}

	public String getFromServerName() {
		return fromServerName;
	}

	public void setFromServerName(String fromServerName) {
		this.fromServerName = fromServerName;
	}

	public String getFromServerIp() {
		return fromServerIp;
	}

	public void setFromServerIp(String fromServerIp) {
		this.fromServerIp = fromServerIp;
	}

	
	public String getMqRfh2() {
		return mqRfh2;
	}

	public void setMqRfh2(String mqRfh2) {
		this.mqRfh2 = mqRfh2;
	}

	public long getMqBackoutCount() {
		return mqBackoutCount;
	}

	public void setMqBackoutCount(long mqBackoutCount) {
		this.mqBackoutCount = mqBackoutCount;
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

	
	public String getFullException() {
		return fullException;
	}

	public void setFullException(String fullException) {
		this.fullException = fullException;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getMqMessageId() {
		return mqMessageId;
	}

	public void setMqMessageId(String mqMessageId) {
		this.mqMessageId = mqMessageId;
	}

	public String getMqMD() {
		return mqMD;
	}

	public void setMqMD(String mqMD) {
		this.mqMD = mqMD;
	}

	public String getDpSubCode() {
		return dpSubCode;
	}

	public void setDpSubCode(String dpSubCode) {
		this.dpSubCode = dpSubCode;
	}


	public String getBipText() {
		return bipText;
	}

	public void setBipText(String bipText) {
		this.bipText = bipText;
	}

	public String getBipCode() {
		return bipCode;
	}

	public void setBipCode(String bipCode) {
		this.bipCode = bipCode;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromOutgoingId() {
		return fromOutgoingId;
	}

	public void setFromOutgoingId(String fromOutgoingId) {
		this.fromOutgoingId = fromOutgoingId;
	}

	
	
}
