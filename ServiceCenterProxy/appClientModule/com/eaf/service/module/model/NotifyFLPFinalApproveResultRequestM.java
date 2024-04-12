package com.eaf.service.module.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class NotifyFLPFinalApproveResultRequestM implements Serializable, Cloneable {
	
	NotifyFLPFinalApproveResultRequestHeaderM header;
	NotifyFLPFinalApproveResultRequestBodyM body;
	
	public NotifyFLPFinalApproveResultRequestHeaderM getHeader() {
		return header;
	}
	public void setHeader(NotifyFLPFinalApproveResultRequestHeaderM header) {
		this.header = header;
	}
	public NotifyFLPFinalApproveResultRequestBodyM getBody() {
		return body;
	}
	public void setBody(NotifyFLPFinalApproveResultRequestBodyM body) {
		this.body = body;
	}
	
}
