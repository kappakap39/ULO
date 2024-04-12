package com.eaf.orig.ulo.model.dm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DocumentManagementDataM implements Serializable,Cloneable{
	public DocumentManagementDataM(){
		super();
	}
	private String dmId;	//DM_DOC.DM_ID(VARCHAR2)
	private String locationId;	//DM_DOC.LOCATION_ID(VARCHAR2)
	private String policyId;	//DM_DOC.POLICY_ID(VARCHAR2)
	private String refNo1;	//DM_DOC.REF_NO1(VARCHAR2)
	private String thFirstName;	//DM_DOC.TH_FIRST_NAME(VARCHAR2)
	private String idno;	//DM_DOC.IDNO(VARCHAR2)
	private String enFirstName;	//DM_DOC.EN_FIRST_NAME(VARCHAR2)
	private String thLastName;	//DM_DOC.TH_LAST_NAME(VARCHAR2)
	private String idType;	//DM_DOC.ID_TYPE(VARCHAR2)
	private String refNo2;	//DM_DOC.REF_NO2(VARCHAR2)
	private String containerNo;	//DM_DOC.CONTAINER_NO(VARCHAR2)
	private String docSetType;	//DM_DOC.DOC_SET_TYPE(VARCHAR2)
	private String status;	//DM_DOC.STATUS(VARCHAR2)
	private String enTitleName;	//DM_DOC.EN_TITLE_NAME(VARCHAR2)
	private String systemId;	//DM_DOC.SYSTEM_ID(VARCHAR2)
	private String boxNo;	//DM_DOC.BOX_NO(VARCHAR2)
	private String thTitleName;	//DM_DOC.TH_TITLE_NAME(VARCHAR2)
	private String enLastName;	//DM_DOC.EN_LAST_NAME(VARCHAR2)
	private String createBy;	//DM_DOC.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//DM_DOC.CREATE_DATE(DATE)
	private String updateBy;	//DM_DOC.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//DM_DOC.UPDATE_DATE(DATE)
	private String remark;
	private String docSetName;	//DM_DOC.DOC_SET_NAME
	private String docSetDesc;	//DM_DOC.DOC_DESC
	private String branchId;	//DM_DOC.BRANCH_ID
	private String docSetId; //DM_DOC.DOC_SET_ID
	
	private String uniqueId;
	private Timestamp lastUpdateDate;
	private String param1; //DM_DOC.PARAM1
	private String param2; //DM_DOC.PARAM2
	private String param3; //DM_DOC.PARAM3
	private String param4; //DM_DOC.PARAM4
	private String param5; //DM_DOC.PARAM5
	private String param6; //DM_DOC.PARAM6
	private String param7; //DM_DOC.PARAM7
	private String param8; //DM_DOC.PARAM8
	private String param9; //DM_DOC.PARAM9
	
	private HistoryDataM history;
	private ArrayList<HistoryDataM> historyLog;
	private ArrayList<DocumentDataM> document;
	private ArrayList<DMApplicationInfoDataM> applicationInfos;	
	
	private String applicationStatus;
	
	public String getDmId() {
		return dmId;
	}
	public void setDmId(String dmId) {
		this.dmId = dmId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getRefNo1() {
		return refNo1;
	}
	public void setRefNo1(String refNo1) {
		this.refNo1 = refNo1;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getEnFirstName() {
		return enFirstName;
	}
	public void setEnFirstName(String enFirstName) {
		this.enFirstName = enFirstName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getRefNo2() {
		return refNo2;
	}
	public void setRefNo2(String refNo2) {
		this.refNo2 = refNo2;
	}	
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getDocSetType() {
		return docSetType;
	}
	public void setDocSetType(String docSetType) {
		this.docSetType = docSetType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEnTitleName() {
		return enTitleName;
	}
	public void setEnTitleName(String enTitleName) {
		this.enTitleName = enTitleName;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getBoxNo() {
		return boxNo;
	}
	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	public String getThTitleName() {
		return thTitleName;
	}
	public void setThTitleName(String thTitleName) {
		this.thTitleName = thTitleName;
	}
	public String getEnLastName() {
		return enLastName;
	}
	public void setEnLastName(String enLastName) {
		this.enLastName = enLastName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}	
	public ArrayList<DocumentDataM> getDocument() {
		return document;
	}
	public void setDocument(ArrayList<DocumentDataM> document) {
		this.document = document;
	}
	public HistoryDataM getHistory() {
		return history;
	}
	public void setHistory(HistoryDataM history) {
		this.history = history;
	}
	public ArrayList<HistoryDataM> getHistoryLog() {
		return historyLog;
	}
	public void setHistoryLog(ArrayList<HistoryDataM> historyLog) {
		this.historyLog = historyLog;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	public String getDocSetName() {
		return docSetName;
	}
	public void setDocSetName(String docSetName) {
		this.docSetName = docSetName;
	}
	public String getDocSetDesc() {
		return docSetDesc;
	}
	public void setDocSetDesc(String docSetDesc) {
		this.docSetDesc = docSetDesc;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getDocSetId() {
		return docSetId;
	}
	public void setDocSetId(String docSetId) {
		this.docSetId = docSetId;
	}
	public Timestamp getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Timestamp lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public ArrayList<DMApplicationInfoDataM> getApplicationInfos() {
		return applicationInfos;
	}
	public void setApplicationInfos(ArrayList<DMApplicationInfoDataM> applicationInfos) {
		this.applicationInfos = applicationInfos;
	}
	
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	public String getParam5() {
		return param5;
	}
	public void setParam5(String param5) {
		this.param5 = param5;
	}
	public String getParam6() {
		return param6;
	}
	public void setParam6(String param6) {
		this.param6 = param6;
	}
	public String getParam7() {
		return param7;
	}
	public void setParam7(String param7) {
		this.param7 = param7;
	}
	public String getParam8() {
		return param8;
	}
	public void setParam8(String param8) {
		this.param8 = param8;
	}
	public String getParam9() {
		return param9;
	}
	public void setParam9(String param9) {
		this.param9 = param9;
	}
	public HistoryDataM  getLastActionHistory() {
		Timestamp createDate = null;
		HistoryDataM historyLast = new HistoryDataM();
		if(null!=historyLog){
			for(HistoryDataM history : historyLog ){
				if(null==createDate){
					createDate = history.getCreateDate();
				}
				
				if(history.getCreateDate().compareTo(createDate)==1){
					historyLast = history;
					createDate = history.getCreateDate();
				} 	
			}
		}
		return historyLast;
	}
	
}

