package il.co.menora.soaarchive.shared;

public class IncomingDtoEx extends IncomingDto {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String closeTime;
	private String lastUser;
	private String getTime;
	private String fromServerName;
	private String fromServerIp;
	private long mqMessageId;
	private String mqRfh2;
	private long mqBackoutCount;
	private String bipText;
	private String bipCode;
	private String expiryTime;
	private String uuid;
	private String dpSubCode;
	
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
	
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getDpSubCode() {
		return dpSubCode;
	}
	public void setDpSubCode(String dbSubCode) {
		this.dpSubCode = dbSubCode;
	}
		
}
