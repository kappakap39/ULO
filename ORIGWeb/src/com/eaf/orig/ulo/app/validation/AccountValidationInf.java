package com.eaf.orig.ulo.app.validation;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.model.ErrorData;

public interface AccountValidationInf {
	abstract public String getAccountName();
	abstract public void setAccountNo(String acctNo);
	abstract public void setAdditionalAccountNo(String acctNo);
	abstract public String validateField(HttpServletRequest request,Object objectForm);
	abstract public String getErrorCode();
	abstract public void setErrorCode(String errorCode);
	abstract public void setErrorData(ErrorData errorData);
	abstract public ErrorData getErrorData();
}