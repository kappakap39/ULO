package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.Objects;

public class SSExceptionInfo implements Serializable {

	private static final long serialVersionUID = 183558974342266021L;
	
	private String ExceptionCode;
	private String CreditType;
	private String LimitAmount;
	private String CreditAmount;

	public String getExceptionCode() {
		return ExceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		ExceptionCode = exceptionCode;
	}

	public String getCreditType() {
		return CreditType;
	}

	public void setCreditType(String creditType) {
		CreditType = creditType;
	}

	public String getLimitAmount() {
		return LimitAmount;
	}

	public void setLimitAmount(String limitAmount) {
		LimitAmount = limitAmount;
	}

	public String getCreditAmount() {
		return CreditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		CreditAmount = creditAmount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SSExceptionInfo inputO = (SSExceptionInfo) o;
		return Objects.equals(this.ExceptionCode, inputO.getExceptionCode());
	}
}
