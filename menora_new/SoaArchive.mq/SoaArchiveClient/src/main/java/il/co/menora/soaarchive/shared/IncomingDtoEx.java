package il.co.menora.soaarchive.shared;

import java.util.Date;


public class IncomingDtoEx extends IncomingDto {

	
	private String closeTime;
	private String lastUser;
	private String getTime;
	private String incomingTime;
	private String fromServerName;
	private String fromServerIp;
	private long mqMessageId;
	private int mqId;
	private String mqRfh2;
	private long mqBackoutCount;
	private String bipMsg;
	private String expiryTime;
	
	
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public String getLastUser() {
		return lastUser;
	}
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}
	public String getIncomingTime() {
		return incomingTime;
	}
	public void setIncomingTime(String incomingTime) {
		this.incomingTime = incomingTime;
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
	public long getMqMessageId() {
		return mqMessageId;
	}
	public void setMqMessageId(long mqMessageId) {
		this.mqMessageId = mqMessageId;
	}
	public int getMqId() {
		return mqId;
	}
	public void setMqId(int mqId) {
		this.mqId = mqId;
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
	public String getBipMsg() {
		return bipMsg;
	}
	public void setBipMsg(String bipMsg) {
		this.bipMsg = bipMsg;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
		
}
