package il.co.menora.soaarchive.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QMgrConnData {

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO, generator="uuid-hex")
	private String id;
	private String qMgrName;
	private String chlName;
	private String ip;
	private int port;
	
	
	public String getqMgrName() {
		return qMgrName;
	}
	public void setqMgrName(String qMgrName) {
		this.qMgrName = qMgrName;
	}
	public String getChlName() {
		return chlName;
	}
	public void setChlName(String chlName) {
		this.chlName = chlName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
