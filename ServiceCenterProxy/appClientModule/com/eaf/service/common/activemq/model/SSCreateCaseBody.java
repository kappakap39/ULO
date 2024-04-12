package com.eaf.service.common.activemq.model;

import java.io.Serializable;

public class SSCreateCaseBody implements Serializable {

	private static final long serialVersionUID = 3747637300285390752L;
	
	private SSCaseInfo CaseInfo;
	private SSCustInfo CustInfo;

	public SSCaseInfo getCaseInfo() {
		return CaseInfo;
	}

	public void setCaseInfo(SSCaseInfo caseInfo) {
		CaseInfo = caseInfo;
	}

	public SSCustInfo getCustInfo() {
		return CustInfo;
	}

	public void setCustInfo(SSCustInfo custInfo) {
		CustInfo = custInfo;
	}
}
