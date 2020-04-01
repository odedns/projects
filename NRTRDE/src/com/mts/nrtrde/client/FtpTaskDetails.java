/**
 * 
 */
package com.mts.nrtrde.client;

import java.io.Serializable;
import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 * @author Oded Nissan
 *
 */
public class FtpTaskDetails extends BaseModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	
	public String getTaskName() {
		return get("taskName");
	}
	public void setTaskName(String taskName) {
		set("taskName",taskName);
	}
	public String getHostIp() {
		return get("hostIp");
	}
	public void setHostIp(String hostIp) {
		set("hostIp",hostIp);
	}
	public String getFtpType() {
		return get("ftpType");
	}
	public void setFtpType(String ftpType) {
		set("ftpType",ftpType);
	}
	public String getRemotePath() {
		return get("remotePath");
	}
	public void setRemotePath(String remotePath) {
		set("remotePath", remotePath);
	}
	public String getLocalPath() {
		return get("localPath");
	}
	public void setLocalPath(String localPath) {
		set("localPath",localPath);
	}
	public String getLastRuntime() {
		return get("lastRuntime").toString();
	}
	public void setLastRuntime(Date lastRuntime) {
		set("lastRuntime",lastRuntime);
	}
	

}
