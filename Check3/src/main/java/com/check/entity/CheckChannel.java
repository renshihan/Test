package com.check.entity;

public class CheckChannel {
	private String payChannelId;
	private String payChannelName;
	private String address;
	private String means;
	private Integer number;
	private Integer thresholdVal;
	private String contacts;
	private Integer time;
	public String getPayChannelId() {
		return payChannelId;
	}
	public void setPayChannelId(String payChannelId) {
		this.payChannelId = payChannelId;
	}
	public String getPayChannelName() {
		return payChannelName;
	}
	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMeans() {
		return means;
	}
	public void setMeans(String means) {
		this.means = means;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getThresholdVal() {
		return thresholdVal;
	}
	public void setThresholdVal(Integer thresholdVal) {
		this.thresholdVal = thresholdVal;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "CheckChannel{" +
				"payChannelId='" + payChannelId + '\'' +
				", payChannelName='" + payChannelName + '\'' +
				", address='" + address + '\'' +
				", means='" + means + '\'' +
				", number=" + number +
				", thresholdVal=" + thresholdVal +
				", contacts='" + contacts + '\'' +
				", time=" + time +
				'}';
	}
}
