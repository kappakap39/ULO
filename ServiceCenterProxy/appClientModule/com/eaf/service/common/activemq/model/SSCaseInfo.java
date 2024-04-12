package com.eaf.service.common.activemq.model;

import java.io.Serializable;
import java.util.Calendar;

public class SSCaseInfo implements Serializable {

	private static final long serialVersionUID = 204452323316213510L;
	
	private String CaseID;
	private String SourceAppID;
	private String ServiceGroupLV1ID;
	private String ServiceGroupLV1Name;
	private String ServiceTypeLV2ID;
	private String ServiceTypeLV2Name;
	private String ServiceNameLV3ID;
	private String ServiceNameLV3Name;
	private String ProductTypeID;
	private String ProductTypeName;
	private Calendar CreateDate;
	private String CreatorID;
	private String CreatorName;
	private SSFulfillmentInfo FullfillmentInfo;

	private String MQCustomFlag;
	private String MQCustomChannel;
	
	public String getMQCustomFlag() {
		return MQCustomFlag;
	}

	public void setMQCustomFlag(String mQCustomFlag) {
		MQCustomFlag = mQCustomFlag;
	}

	public String getMQCustomChannel() {
		return MQCustomChannel;
	}

	public void setMQCustomChannel(String mQCustomChannel) {
		MQCustomChannel = mQCustomChannel;
	}

	public String getCaseID() {
		return CaseID;
	}

	public void setCaseID(String caseID) {
		CaseID = caseID;
	}

	public String getSourceAppID() {
		return SourceAppID;
	}

	public void setSourceAppID(String sourceAppID) {
		SourceAppID = sourceAppID;
	}

	public String getServiceGroupLV1ID() {
		return ServiceGroupLV1ID;
	}

	public void setServiceGroupLV1ID(String serviceGroupLV1ID) {
		ServiceGroupLV1ID = serviceGroupLV1ID;
	}

	public String getServiceGroupLV1Name() {
		return ServiceGroupLV1Name;
	}

	public void setServiceGroupLV1Name(String serviceGroupLV1Name) {
		ServiceGroupLV1Name = serviceGroupLV1Name;
	}

	public String getServiceTypeLV2ID() {
		return ServiceTypeLV2ID;
	}

	public void setServiceTypeLV2ID(String serviceTypeLV2ID) {
		ServiceTypeLV2ID = serviceTypeLV2ID;
	}

	public String getServiceTypeLV2Name() {
		return ServiceTypeLV2Name;
	}

	public void setServiceTypeLV2Name(String serviceTypeLV2Name) {
		ServiceTypeLV2Name = serviceTypeLV2Name;
	}

	public String getServiceNameLV3ID() {
		return ServiceNameLV3ID;
	}

	public void setServiceNameLV3ID(String serviceNameLV3ID) {
		ServiceNameLV3ID = serviceNameLV3ID;
	}

	public String getServiceNameLV3Name() {
		return ServiceNameLV3Name;
	}

	public void setServiceNameLV3Name(String serviceNameLV3Name) {
		ServiceNameLV3Name = serviceNameLV3Name;
	}

	public String getProductTypeID() {
		return ProductTypeID;
	}

	public void setProductTypeID(String productTypeID) {
		ProductTypeID = productTypeID;
	}

	public String getProductTypeName() {
		return ProductTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		ProductTypeName = productTypeName;
	}

	public Calendar getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(Calendar createDate) {
		CreateDate = createDate;
	}

	public String getCreatorID() {
		return CreatorID;
	}

	public void setCreatorID(String creatorID) {
		CreatorID = creatorID;
	}

	public String getCreatorName() {
		return CreatorName;
	}

	public void setCreatorName(String creatorName) {
		CreatorName = creatorName;
	}
	
	public SSFulfillmentInfo getFullfillmentInfo() {
		return FullfillmentInfo;
	}

	public void setFullfillmentInfo(SSFulfillmentInfo fullfillmentInfo) {
		FullfillmentInfo = fullfillmentInfo;
	}
}
