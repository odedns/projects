package il.co.menora.soaarchive.shared;

import java.io.Serializable;

public class MQResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean status;
	private String msg;
	private int numProcessed;
	private int numExpired;
	
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getNumProcessed() {
		return numProcessed;
	}
	public void setNumProcessed(int numProcessed) {
		this.numProcessed = numProcessed;
	}
	public int getNumExpired() {
		return numExpired;
	}
	public void setNumExpired(int numExpired) {
		this.numExpired = numExpired;
	}
	
	

}
