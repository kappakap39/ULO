package com.eaf.service.common.activemq.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("KMBCaseCreationQueue")
public class SSCreateCaseRoot implements Serializable {

	private static final long serialVersionUID = -7608650311591246934L;

	private SSHeaderRequest HeaderRequest;
	private SSCreateCaseBody BodyRequest;

	public SSCreateCaseRoot() {
	}

	public SSCreateCaseRoot(SSHeaderRequest headerRequest) {
		HeaderRequest = headerRequest;
	}

	public SSHeaderRequest getHeaderRequest() {
		return HeaderRequest;
	}

	public void setHeaderRequest(SSHeaderRequest headerRequest) {
		HeaderRequest = headerRequest;
	}

	public SSCreateCaseBody getBodyRequest() {
		return BodyRequest;
	}

	public void setBodyRequest(SSCreateCaseBody bodyRequest) {
		BodyRequest = bodyRequest;
	}
}
