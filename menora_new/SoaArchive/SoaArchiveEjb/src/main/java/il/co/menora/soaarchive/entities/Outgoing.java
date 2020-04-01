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

@Entity
public class Outgoing {

	@Id
//	@GeneratedValue (strategy=GenerationType.AUTO, generator="uuid-hex")
	private String id;
	private String incomingId;
	@Temporal(TemporalType.TIMESTAMP)
	private Date resendTime;
	private String toQueue;
	private String toQMgr;
	private String mqId;
	@Lob
	private String mqRfh2;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Payload payload;

	
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

	public String getMqId() {
		return mqId;
	}

	public void setMqId(String mqId) {
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIncomingId() {
		return incomingId;
	}

	public void setIncomingId(String incomingId) {
		this.incomingId = incomingId;
	}

	
	
	
}
