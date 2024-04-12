package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SSContactPersonInfoSegment implements Serializable {

	private static final long serialVersionUID = 8625211604666980158L;

	@XStreamImplicit(itemFieldName = "ContactPerson")
	private List<SSContactPerson> ContactPersonInfo;

	public List<SSContactPerson> getContactPersonInfo() {
		return ContactPersonInfo;
	}

	public void setContactPersonInfo(List<SSContactPerson> contactPersonInfo) {
		ContactPersonInfo = contactPersonInfo;
	}

}
