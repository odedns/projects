package il.co.menora.soaarchive.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Payload {

	@Id
	@GeneratedValue
	//@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	private String payload;
	private int payloadSize;
	
	public Payload()
	{
		
	}
	
	
	public int getPayloadSize() {
		return payloadSize;
	}
	public void setPayloadSize(int payloadSize) {
		this.payloadSize = payloadSize;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getPayload() {
		return payload;
	}


	public void setPayload(String payload) {
		this.payload = payload;
	}
}
