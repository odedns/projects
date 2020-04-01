package il.co.menora.soaarchive.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QMgrConnData {

	@Id
	@GeneratedValue

	private long id;
	private String qMgrName;
	private String chlName;
	private String ip;
	private int port;
	
	public QMgrConnData(){
		
	}
	
	public QMgrConnData(long id, String qMgrName, String chlName, String ip,int port){
		super();
		this.id = id;
		this.qMgrName = qMgrName;
		this.chlName = chlName;
		this.ip = ip;
		this.port = port;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
}
