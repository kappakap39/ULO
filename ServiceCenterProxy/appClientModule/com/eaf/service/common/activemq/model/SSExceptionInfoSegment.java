package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SSExceptionInfoSegment implements Serializable {

	private static final long serialVersionUID = -445463268489184410L;

	@XStreamImplicit(itemFieldName = "ExceptionInfo")
	private List<SSExceptionInfo> ExceptionList;

	public List<SSExceptionInfo> getExceptionList() {
		return ExceptionList;
	}

	public void setExceptionList(List<SSExceptionInfo> exceptionList) {
		ExceptionList = exceptionList;
	}

}
