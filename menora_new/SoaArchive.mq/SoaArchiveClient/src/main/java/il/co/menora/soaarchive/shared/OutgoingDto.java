package il.co.menora.soaarchive.shared;

import java.io.Serializable;


public class OutgoingDto implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long incomingId;
	private String resendTime;
	private String toQueue;
	private String toQMgr;
	private int mqId;
	private String mqRfh2;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIncomingId() {
		return incomingId;
	}
	public void setIncomingId(long incomingId) {
		this.incomingId = incomingId;
	}
	public String getResendTime() {
		return resendTime;
	}
	public void setResendTime(String resendTime) {
		this.resendTime = resendTime;
	}
	public String getToQueue() {
		return toQueue;
	}
	public void setToQueue(String toQueue) {
		this.toQueue = toQueue;
	}
	public String getToQMgr() {
		return toQMgr;
	}
	public void setToQMgr(String toQMgr) {
		this.toQMgr = toQMgr;
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
}
