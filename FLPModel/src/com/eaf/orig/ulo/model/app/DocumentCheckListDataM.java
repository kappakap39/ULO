package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class DocumentCheckListDataM implements Serializable,Cloneable{
	public DocumentCheckListDataM(){
		super();
	}
	private String applicationGroupId;	//ORIG_DOC_CHECK_LIST.APPLICATION_GROUP_ID(VARCHAR2)
	private String docCheckListId;	//ORIG_DOC_CHECK_LIST.DOC_CHECK_LIST_ID(VARCHAR2)
//	private String personalId;	//ORIG_DOC_CHECK_LIST.PERSONAL_ID(VARCHAR2)
	private String receive;	//ORIG_DOC_CHECK_LIST.RECEIVE(VARCHAR2)
	private String documentCode;	//ORIG_DOC_CHECK_LIST.DOCUMENT_CODE(VARCHAR2)
	private String createBy;	//ORIG_DOC_CHECK_LIST.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_DOC_CHECK_LIST.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_DOC_CHECK_LIST.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_DOC_CHECK_LIST.UPDATE_DATE(DATE)
//	private String applicationRecordId;//APPLICATION_RECORD_ID
	private String jobState;//ORIG_DOC_CHECK_LIST.JOB_STATE
	private String applicantType;//ORIG_DOC_CHECK_LIST.APPLICANT_TYPE
	private String docTypeId;//ORIG_DOC_CHECK_LIST.DOC_TYPE_ID
	private String docTypeDesc;//ORIG_DOC_CHECK_LIST.DOC_TYPE_DESC
	private String required;//ORIG_DOC_CHECK_LIST.REQUIRED
	private String role;//ORIG_DOC_CHECK_LIST.ROLE
	private String remark;//ORIG_DOC_CHECK_LIST.REMARK
	private String waive;//ORIG_DOC_CHECK_LIST.WAIVE
	private String requestDoc;//ORIG_DOC_CHECK_LIST.REQUEST_DOC
	private String applicantTypeIM;//ORIG_DOC_CHECK_LIST.APPLICANT_TYPE_IM
	private String manualFlag;//ORIG_DOC_CHECK_LIST.MANUAL_FLAG
	private int seq;

	private ArrayList<DocumentCheckListReasonDataM> documentCheckListReasons;
	private ArrayList<DocumentRelationDataM> documentRelations;
		
//	public String getPersonalId() {
//		return personalId;
//	}
//	public void setPersonalId(String personalId) {
//		this.personalId = personalId;
//	}
	
	public String getDocCheckListId() {
		return docCheckListId;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public void setDocCheckListId(String docCheckListId) {
		this.docCheckListId = docCheckListId;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
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
	public ArrayList<DocumentCheckListReasonDataM> getDocumentCheckListReasons() {
		return documentCheckListReasons;
	}
	public ArrayList<String> getDocumentCheckListDocReasonCodes() {
		ArrayList<String> docReasons = new ArrayList<String>();
		if(null!=documentCheckListReasons){
			for(DocumentCheckListReasonDataM  documentCheckListReason : documentCheckListReasons){
				String reason = documentCheckListReason.getDocReason();
				if(null!=reason && !docReasons.contains(reason)){
					docReasons.add(reason);
				}
			}			
		}
		return docReasons;
	}
	public void setDocumentCheckListReasons(ArrayList<DocumentCheckListReasonDataM> documentCheckListReasons) {
		this.documentCheckListReasons = documentCheckListReasons;
	}

	public DocumentCheckListReasonDataM getDocumentCheckListReason(String reasonCode) {
		if(documentCheckListReasons != null) {
			for(DocumentCheckListReasonDataM reasonM : documentCheckListReasons) {
				if(reasonM.getDocReason() != null && reasonM.getDocReason().equals(reasonCode)) {
					return reasonM;
				}
			}
		}
		return null;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}
	public String getDocTypeId() {
		return docTypeId;
	}
	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}
	public String getDocTypeDesc() {
		return docTypeDesc;
	}
	public void setDocTypeDesc(String docTypeDesc) {
		this.docTypeDesc = docTypeDesc;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWaive() {
		return waive;
	}
	public void setWaive(String waive) {
		this.waive = waive;
	}
	public String getRequestDoc() {
		return requestDoc;
	}
	public void setRequestDoc(String requestDoc) {
		this.requestDoc = requestDoc;
	}
	public String getApplicantTypeIM() {
		return applicantTypeIM;
	}
	public void setApplicantTypeIM(String applicantTypeIM) {
		this.applicantTypeIM = applicantTypeIM;
	}
	public ArrayList<DocumentRelationDataM> getDocumentRelations() {
		return documentRelations;
	}
	public void setDocumentRelations(ArrayList<DocumentRelationDataM> documentRelations) {
		this.documentRelations = documentRelations;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public void add(DocumentCheckListReasonDataM  documentCheckListReasonDataM){
		if(null == this.documentCheckListReasons){
			this.documentCheckListReasons = new ArrayList<DocumentCheckListReasonDataM>();
		}
		documentCheckListReasons.add(documentCheckListReasonDataM);
	}
	
	public void add(DocumentRelationDataM  documentRelationDataM){
		if(null == this.documentRelations){
			this.documentRelations = new ArrayList<DocumentRelationDataM>();
		}
		documentRelations.add(documentRelationDataM);
	}
	public String getManualFlag() {
		return manualFlag;
	}
	public void setManualFlag(String manualFlag) {
		this.manualFlag = manualFlag;
	}
	
}
