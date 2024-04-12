package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class SSAttachDoc implements Serializable {

	private static final long serialVersionUID = -2952846524324434589L;
	
	private String FLPRefId;
	private String LHRefId;
	private String DocTypeCode;
	private String DocTypeName;
	@XStreamImplicit(itemFieldName = "CisIdList")
	private List<SSCisIdSegment> CisIdList;

	public String getFLPRefId() {
		return FLPRefId;
	}

	public void setFLPRefId(String fLPRefId) {
		FLPRefId = fLPRefId;
	}

	public String getLHRefId() {
		return LHRefId;
	}

	public void setLHRefId(String lHRefId) {
		LHRefId = lHRefId;
	}

	public String getDocTypeCode() {
		return DocTypeCode;
	}

	public void setDocTypeCode(String docTypeCode) {
		DocTypeCode = docTypeCode;
	}

	public String getDocTypeName() {
		return DocTypeName;
	}

	public void setDocTypeName(String docTypeName) {
		DocTypeName = docTypeName;
	}

	public List<SSCisIdSegment> getCisIdList() {
		return CisIdList;
	}

	public void setCisIdList(List<SSCisIdSegment> cisIdList) {
		CisIdList = cisIdList;
	}

}
