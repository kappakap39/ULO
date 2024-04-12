package com.kbank.eappu.dummymodel;

import com.google.gson.annotations.Expose;

public class Payload {
	@Expose
	private String desc;
	@Expose
	private String payloadVal;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPayloadVal() {
		return payloadVal;
	}
	public void setPayloadVal(String payloadVal) {
		this.payloadVal = payloadVal;
	}
}
