package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SSAttachDocListSegment implements Serializable {

	private static final long serialVersionUID = -2221751590693688907L;

	@XStreamImplicit(itemFieldName = "AttachDoc")
	private List<SSAttachDoc> AttachDocList;

	public List<SSAttachDoc> getAttachDocList() {
		return AttachDocList;
	}

	public void setAttachDocList(List<SSAttachDoc> attachDocList) {
		AttachDocList = attachDocList;
	}

}
