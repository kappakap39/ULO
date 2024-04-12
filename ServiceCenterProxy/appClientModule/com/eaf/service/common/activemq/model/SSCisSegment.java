package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SSCisSegment implements Serializable {

	private static final long serialVersionUID = -3746089098676040125L;

	@XStreamImplicit(itemFieldName = "CisInfo")
	private List<SSCisInfo> CisList;

	public List<SSCisInfo> getCisList() {
		return CisList;
	}

	public void setCisList(List<SSCisInfo> cisList) {
		CisList = cisList;
	}

}
