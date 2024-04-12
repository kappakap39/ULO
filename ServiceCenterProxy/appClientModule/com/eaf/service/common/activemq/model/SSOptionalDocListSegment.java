package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SSOptionalDocListSegment implements Serializable {

	private static final long serialVersionUID = 6216541178638992810L;

	@XStreamImplicit(itemFieldName = "OptionalDoc")
	private List<SSOptionalDoc> OptionalDocList;

	public List<SSOptionalDoc> getOptionalDocList() {
		return OptionalDocList;
	}

	public void setOptionalDocList(List<SSOptionalDoc> optionalDocList) {
		OptionalDocList = optionalDocList;
	}

}
