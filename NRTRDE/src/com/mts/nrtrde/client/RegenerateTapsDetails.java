/**
 * 
 */
package com.mts.nrtrde.client;

import java.io.Serializable;
import java.util.Date;

/**
 * @author C5132784
 *
 */
public class RegenerateTapsDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int keyNum;
	String eventType;
	Date eventDate;
	Date billingDate;
	String ratingStatus;
	double price;
	Date callStartTime;
	Date callEndTime;
	public int getKeyNum() {
		return keyNum;
	}
	public void setKeyNum(int keyNum) {
		this.keyNum = keyNum;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Date getBillingDate() {
		return billingDate;
	}
	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}
	public String getRatingStatus() {
		return ratingStatus;
	}
	public void setRatingStatus(String ratingStatus) {
		this.ratingStatus = ratingStatus;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getCallStartTime() {
		return callStartTime;
	}
	public void setCallStartTime(Date callStartTime) {
		this.callStartTime = callStartTime;
	}
	public Date getCallEndTime() {
		return callEndTime;
	}
	public void setCallEndTime(Date callEndTime) {
		this.callEndTime = callEndTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
