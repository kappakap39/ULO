package com.eaf.service.module.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotifyFLPFinalApproveResultResponseM implements Serializable,Cloneable {
	
	NotifyFLPFinalApproveResultResponseHeaderM header;

	public NotifyFLPFinalApproveResultResponseHeaderM getHeader() {
		return header;
	}

	public void setHeader(NotifyFLPFinalApproveResultResponseHeaderM header) {
		this.header = header;
	}
	
}
