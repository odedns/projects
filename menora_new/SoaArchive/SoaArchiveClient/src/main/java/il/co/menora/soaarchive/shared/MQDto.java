package il.co.menora.soaarchive.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MQDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String queueMgr;
	private String queueName;
	private String replyToQueue;
	private int priority;
	private Date validityDate;
	private boolean sendInvalid;
	private int batchInterval;
	private boolean createMsgId;
	private boolean updateMsgDate;
	private List<String> ids;
	
	
	public MQDto()
	{
		queueMgr = null;
		queueName = null;
		replyToQueue = null;
		priority = 0;
		validityDate = null;
		sendInvalid = false;
		batchInterval = 50;
		createMsgId = false;
		
	}


	public String getQueueMgr() {
		return queueMgr;
	}


	public void setQueueMgr(String queueMgr) {
		this.queueMgr = queueMgr;
	}


	public String getQueueName() {
		return queueName;
	}


	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}


	public String getReplyToQueue() {
		return replyToQueue;
	}


	public void setReplyToQueue(String replyToQueue) {
		this.replyToQueue = replyToQueue;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public Date getValidityDate() {
		return validityDate;
	}


	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}


	public boolean isSendInvalid() {
		return sendInvalid;
	}


	public void setSendInvalid(boolean sendInvalid) {
		this.sendInvalid = sendInvalid;
	}


	public int getBatchInterval() {
		return batchInterval;
	}


	public void setBatchInterval(int batchInterval) {
		this.batchInterval = batchInterval;
	}



	
	public boolean isCreateMsgId() {
		return createMsgId;
	}


	public void setCreateMsgId(boolean createMsgId) {
		this.createMsgId = createMsgId;
	}


	public List<String> getIds() {
		return ids;
	}


	public void setIds(List<String> ids) {
		this.ids = ids;
	}


	public boolean isUpdateMsgDate() {
		return updateMsgDate;
	}


	public void setUpdateMsgDate(boolean updateMsgDate) {
		this.updateMsgDate = updateMsgDate;
	}
	

}
