package funkey.dao;

import java.io.Serializable;
import java.util.List;

public class Command implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long commandId;
	private int type;
	private long repeat;
	private List<String> groups;
	
	public Command()
	{
		
	}


	public long getCommandId() {
		return commandId;
	}


	public void setCommandId(long commandId) {
		this.commandId = commandId;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public long getRepeat() {
		return repeat;
	}


	public void setRepeat(long repeat) {
		this.repeat = repeat;
	}


	public List<String> getGroups() {
		return groups;
	}


	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	
}
