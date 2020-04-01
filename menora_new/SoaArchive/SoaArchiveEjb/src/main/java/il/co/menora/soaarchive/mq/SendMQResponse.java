package il.co.menora.soaarchive.mq;
import java.util.ArrayList;
import java.util.List;

import il.co.menora.soaarchive.entities.Outgoing;

public class SendMQResponse {
	private List<Outgoing> mOutgoing;
	private boolean result;
	private String errorMessage;
	private int numExpired;
	
	public SendMQResponse(){
		result = true;
		mOutgoing = new ArrayList<Outgoing>();
	}
	
	public List<Outgoing> getOutgoing() {
		return mOutgoing;
	}
	public void setOutgoing(List<Outgoing> mOutgoing) {
		this.mOutgoing = mOutgoing;
	}
	public boolean isResultSuccess() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
	
	public void setErrorMessage(String error){
		errorMessage = error;
	}

	public int getNumExpired() {
		return numExpired;
	}

	public void setNumExpired(int numExpired) {
		this.numExpired = numExpired;
	}
	
}