package il.co.menora.soaarchive.shared;

/**
 * Represents the MQ Resent request recieved from the client as-is
 */
public class MQDto {
	/**
	 * The queue manager to put the messages to
	 */
	private String queueManager;
	
	/**
	 * queue name to put the messages to
	 */
	private String queueName;

	/**
	 * How many messages to send per batch
	 */
	private int batchSize;
	
	/**
	 * The batch interval to set for the MQ Client
	 */
	private int batchInterval;
	
	/**
	 * The MQ priority for the messages
	 */
	private int priority;
	
	/**
	 * Whether to generate new message id
	 */
	private boolean createMsgId;

	public String getQueueManager() {
		return queueManager;
	}

	public String getQueueName() {
		return queueName;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public int getBatchInterval() {
		return batchInterval;
	}

	public int getPriority() {
		return priority;
	}

	public boolean isCreateMsgId() {
		return createMsgId;
	}
	
	
	
}
