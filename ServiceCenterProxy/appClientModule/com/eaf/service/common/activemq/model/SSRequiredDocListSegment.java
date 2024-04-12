package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SSRequiredDocListSegment implements Serializable {

	private static final long serialVersionUID = 3172942054301586802L;

	@XStreamImplicit(itemFieldName = "RequiredDoc")
	private List<SSRequiredDoc> RequiredDocList;

	public List<SSRequiredDoc> getRequiredDocList() {
		return RequiredDocList;
	}

	public void setRequiredDocList(List<SSRequiredDoc> requiredDocList) {
		RequiredDocList = requiredDocList;
	}
}
