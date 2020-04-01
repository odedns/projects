package il.co.menora.soaarchive.mq;

import il.co.menora.soaarchive.entities.Incoming;
import il.co.menora.soaarchive.entities.Outgoing;
import il.co.menora.soaarchive.shared.MQDto;

import java.util.List;

/**
 * The following class represent a request to send a bulk of data into MQ server
 * @author Isaac 01/09/2016
 *
 */
public class SendMQRequest {

	/**
	 * Messages to send to different MQ servers
	 */
	private List<Incoming> incoming;
	
	/**
	 * The request data as it was received from the client
	 */
	private MQDto clientRequest;
	
	/**
	 * The user name which created the request
	 */
	private String username;
	

	public SendMQRequest(List<Incoming> incoming, MQDto clientRequest,String pUserName) {
		this.incoming = incoming;
		this.clientRequest = clientRequest;
		this.username = pUserName;
	}

	/**
	 * Getter for {@link SendMQRequest#Incoming}
	 */
	public List<Incoming> getIncoming() {
		return incoming;
	}
	
	/**
	 * Getter for {@link SendMQRequest#clientRequest}
	 */
	public MQDto getClientRequest() {
		return clientRequest;
	}
	
	public String getUserName(){
		return username;
	}
}
