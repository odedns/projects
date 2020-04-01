package il.co.menora.soaarchive.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Outgoing {

	@Id
	@GeneratedValue
//	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	private long incomingId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date resendTime;
	private String toQueue;
	private String toQMgr;
	private int mqId;
	private String mqRfh2;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Payload payload;

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

	public Date getResendTime() {
		return resendTime;
	}

	public void setResendTime(Date resendTime) {
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

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}	
}
