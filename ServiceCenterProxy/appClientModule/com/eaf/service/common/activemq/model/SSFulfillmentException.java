package com.eaf.service.common.activemq.model;

public class SSFulfillmentException extends SSFulfillmentInfoDefault {

	private static final long serialVersionUID = 6302823068334691885L;

	private SSExceptionInfoSegment ExceptionList;

	public SSExceptionInfoSegment getExceptionList() {
		return ExceptionList;
	}

	public void setExceptionList(SSExceptionInfoSegment exceptionList) {
		ExceptionList = exceptionList;
	}

}
