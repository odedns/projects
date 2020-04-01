/**
 * File: ReportMessage.java
 * Date: 22 בספט 2014
 * Author: ODEDNI
 */
package menora.ccm.taxreport.actors;

import java.io.Serializable;

/**
 * @author Oded Nissan
 *
 */
public class ReportMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * message types.
	 */
	public static final int TYPE_WORK = 1;
	public static final int TYPE_REPLY = 2;
	public static final int TYPE_STOP = 3;
	public static final int TYPE_START = 4;
	public static final int TYPE_FTP_START = 5;
	public static final int TYPE_FTP_DONE = 6;
	private int type;
	private long offset;
	private int popCode;
	
	
	/**
	 * empty constructor.
	 */
	public ReportMessage()
	{
		
	}
	
	public ReportMessage(int type)
	{
		this.type = type;
	}
	/**
	 * constructor
	 * @param type the message type
	 * @param offset the offset to work from
	 * @param chunk the chunk size.
	 */
	public ReportMessage(int type, long offset, int popCode)
	{
		this.type = type;
		this.offset = offset;
		this.setPopCode(popCode);
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public long getOffset() {
		return offset;
	}


	public void setOffset(long offset) {
		this.offset = offset;
	}

	public int getPopCode() {
		return popCode;
	}

	public void setPopCode(int popCode) {
		this.popCode = popCode;
	}


}
