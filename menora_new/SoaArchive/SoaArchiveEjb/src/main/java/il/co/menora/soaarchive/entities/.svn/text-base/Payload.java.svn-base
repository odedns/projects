package il.co.menora.soaarchive.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Payload {

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO, generator="uuid-hex")
	private String id;
	@Lob
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

	


	public String getPayload() {
		return payload;
	}


	public void setPayload(String payload) {
		this.payload = payload;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	}
