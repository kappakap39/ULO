package com.eaf.service.common.activemq.model;

import java.io.Serializable;

interface SSFulfillmentInfo extends Serializable {
	
	public SSAttachDocListSegment getAttachDocList();

	public void setAttachDocList(SSAttachDocListSegment attachDocList);

	public SSRequiredDocListSegment getRequiredDocList();

	public void setRequiredDocList(SSRequiredDocListSegment requiredDocList);

	public SSOptionalDocListSegment getOptionalDocList();

	public void setOptionalDocList(SSOptionalDocListSegment optionalDocList);

	public String getFLPAppRefNo();

	public void setFLPAppRefNo(String fLPAppRefNo);

	public SSContactPersonInfoSegment getContactPersonInfo();

	public void setContactPersonInfo(SSContactPersonInfoSegment contactPersonInfo);

	public SSCisSegment getCisList();

	public void setCisList(SSCisSegment cisList);

	public String getLPMNo();

	public void setLPMNo(String lPMNo);
}
