package com.eaf.service.common.activemq.model;

import java.io.Serializable;

public class SSCisInfo implements Serializable {

	private static final long serialVersionUID = -1016685068485117758L;

	private String CisId;
	private String CustomerFullName;
	private String CustomerType;
	private String Relationship;
	private String IdNo;
	private String DocTypeCode;

	public String getCisId() {
		return CisId;
	}

	public void setCisId(String cisId) {
		CisId = cisId;
	}

	public String getCustomerFullName() {
		return CustomerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		CustomerFullName = customerFullName;
	}

	public String getCustomerType() {
		return CustomerType;
	}

	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}

	public String getRelationship() {
		return Relationship;
	}

	public void setRelationship(String relationship) {
		Relationship = relationship;
	}

	public String getIdNo() {
		return IdNo;
	}

	public void setIdNo(String idNo) {
		IdNo = idNo;
	}

	public String getDocTypeCode() {
		return DocTypeCode;
	}

	public void setDocTypeCode(String docTypeCode) {
		DocTypeCode = docTypeCode;
	}
}
