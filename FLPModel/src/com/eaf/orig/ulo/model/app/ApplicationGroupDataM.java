package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import com.eaf.orig.model.util.ModelUtil;
import com.eaf.orig.ulo.model.app.FinalVerifyResultDataM.VerifyId;
import com.eaf.orig.ulo.model.compare.CompareDataM;
//import java.util.Iterator;
//import sun.util.logging.resources.logging;
//import java.util.Map.Entry;

@SuppressWarnings("serial")
public class ApplicationGroupDataM implements Serializable, Cloneable ,Comparator<Object> {
	public ApplicationGroupDataM() {
		super();
		personalInfos = new ArrayList<PersonalInfoDataM>();
		applications = new ArrayList<ApplicationDataM>();
	}
	public class DECISION{
		public static final String DECISION_ESCALATE = "DECISION_ESCALATE";
		public static final String DECISION_REJECT = "REJECT";
		public static final String DDECISION_APPROVE = "APPROVE";
		public static final String DECISION_ERROR = "ERROR";
	}
	public class DOCUMENT_RELATIONS{
		public static final String DOCUMENT_RELATIONS_A = "DOCUMENT_RELATIONS_APPLICANT";
		public static final String DOCUMENT_RELATIONS_S = "DOCUMENT_RELATIONS_SUPPLEMENTARY";
	}
	public class DecisionLog{
		public static final String REQUIRE_VERIFY_INCOME = "REQUIRE_VERIFY_INCOME";
		public static final String NOT_REQUIRE_VERIFY_INCOME = "NOT_REQUIRE_VERIFY_INCOME";
	}
	public class PRODUCT{
		public static final String PRODUCT_CRADIT_CARD="CC";
		public static final String PRODUCT_K_EXPRESS_CASH="KEC";
		public static final String PRODUCT_K_PERSONAL_LOAN="KPL";
	}
	
	private String applicationGroupId; // ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID(VARCHAR2)
	private String jobState; // ORIG_APPLICATION_GROUP.JOB_STATE(VARCHAR2)
	private String vetoRemark; // ORIG_APPLICATION_GROUP.VETO_REMARK(VARCHAR2)
	private Date applyDate; // ORIG_APPLICATION_GROUP.APPLY_DATE(DATE)
	private String caRemark; // ORIG_APPLICATION_GROUP.CA_REMARK(VARCHAR2)
	private String fraudRemark; // ORIG_APPLICATION_GROUP.FRAUD_REMARK(VARCHAR2)
	private String applicationStatus; // ORIG_APPLICATION_GROUP.APPLICATION_STATUS(VARCHAR2)
	private String applicationGroupNo; // ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO(VARCHAR2)
	private String approverRemark; // ORIG_APPLICATION_GROUP.APPROVER_REMARK(VARCHAR2)
	private Integer instantId; // ORIG_APPLICATION_GROUP.INSTANT_ID(NUMBER)
	private String lastDecision; // ORIG_APPLICATION_GROUP.LAST_DECISION(VARCHAR2)
	private String coverpageType; // ORIG_APPLICATION_GROUP.COVERPAGE_TYPE(VARCHAR2)
	private Date applicationDate; // ORIG_APPLICATION_GROUP.APPLICATION_DATE(DATE)
	private String branchNo; // ORIG_APPLICATION_GROUP.BRANCH_NO(VARCHAR2)
	private String applicationTemplate; // ORIG_APPLICATION_GROUP.APPLICATION_TEMPLATE(VARCHAR2)
	private int priority; // ORIG_APPLICATION_GROUP.PRIORITY(NUMBER)
	private String applyChannel; // ORIG_APPLICATION_GROUP.APPLY_CHANNEL(VARCHAR2)
	private String refId; // ORIG_APPLICATION_GROUP.REF_ID(VARCHAR2)
	private String bundleFlag; // ORIG_APPLICATION_GROUP.BUNDLE_FLAG(VARCHAR2)
	private String createBy; // ORIG_APPLICATION_GROUP.CREATE_BY(VARCHAR2)
	private Timestamp createDate; // ORIG_APPLICATION_GROUP.CREATE_DATE(DATE)
	private String updateBy; // ORIG_APPLICATION_GROUP.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate; // ORIG_APPLICATION_GROUP.UPDATE_DATE(DATE)
	private String productId;
	private String bundingId;
	private Date lastDecisionDate; // ORIG_APPLICATION_GROUP.LAST_DECISION_DATE(DATE)
	private String taskId;
	private String applicationType; // ORIG_APPLICATION_GROUP.APPLICATION_TYPE(VARCHAR2)
	private String branchName; // ORIG_APPLICATION_GROUP.BRANCH_NAME(VARCHAR2)
	private String decisionAction;
	private String formAction;
	private String auditLogFlag; //ORIG_APPLICATION_GROUP.AUDIT_LOG_FLAG(VARCHAR2)
	private String isVetoEligible; //ORIG_APPLICATION_GROUP.IS_VETO_ELIGIBLE(VARCHAR2)
	private String docSetNo; //ORIG_APPLICATION_GROUP.DOC_SET_NO(VARCHAR2)
	private BigDecimal legtime1; //ORIG_APPLICATION_GROUP.LEG_TIME1(NUMBER)
	private BigDecimal legtime2; //ORIG_APPLICATION_GROUP.LEG_TIME2(NUMBER)
	private BigDecimal legtime3; //ORIG_APPLICATION_GROUP.LEG_TIME3(NUMBER)
	private BigDecimal legtime4; //ORIG_APPLICATION_GROUP.LEG_TIME4(NUMBER)
	private String claimBy; //ORIG_APPLICATION_GROUP.CLAIM_BY(VARCHAR)
	private String mainCardNo;
	private String mainCardHolderName;
	private String branchRegion;  //ORIG_APPLICATION_GROUP.BRANCH_REGION(VARCHAR)
	private String branchZone;   //ORIG_APPLICATION_GROUP.BRANCH_ZONE(VARCHAR)
	private String rcCode;   //ORIG_APPLICATION_GROUP.RC_CODE(VARCHAR)
	private Date policyExSignOffDate; //ORIG_APPLICATION_GROUP.POLICY_EX_SIGN_OFF_DATE(DATE)	
	private String policyExSignOffBy; //ORIG_APPLICATION_GROUP.POLICY_EX_SIGN_OFF_BY(VARCHAR2)
	private String prevJobState; //ORIG_APPLICATION_GROUP.PREV_JOB_STATE(VARCHAR2)
	private String userId;
	private int randomNo1;//ORIG_APPLICATION_GROUP.RANDOM_NO1
	private int randomNo2;//ORIG_APPLICATION_GROUP.RANDOM_NO2
	private int randomNo3;//ORIG_APPLICATION_GROUP.RANDOM_NO3
	private boolean supplementaryApplicant = false;
	private String callFicoFlag;
	private String fraudFlag;
	private String cancelFlag;
	private String fraudApplicantFlag;
	private String fraudCompanyFlag;
	private String fraudDecision;
	private String fraudActionType;
	private String flipType;
	private String documentSLAType;
	private String blockedFlag;
	private String coverpagePriority;
	private String callOperator;
	private String calledEscalateFlag;
	private String alreadyFollowFlag;
	private String overPercentORFlag;
	private String priorityMode;//ORIG_APPLICATION_GROUP.PRIORITY_MODE
	private boolean clearCompareData = false;
	private String webScanUser; //ORIG_APPLICATION_GROUP.WEB_SCAN_USER
	private String executeFlag;
	private String transactionId;
	private String fraudFinalDecision;
	private int lifeCycle;
	private ArrayList<ApplicationDataM> applications;
	private ArrayList<ApplicationImageDataM> applicationImages;
	private ArrayList<ApplicationLogDataM> applicationLogs;
	private ArrayList<ApplicationPointDataM> applicationPoints;
	private ArrayList<AttachmentDataM> attachments;
	private ArrayList<AuditTrailDataM> auditTrails;
	private ArrayList<AuditTrailDataM> auditTrailLogs;
	private ArrayList<ContactLogDataM> contactLogs;
	private ArrayList<EnquiryLogDataM> enquiryLogs;
	private ArrayList<NotePadDataM> notePads;
	private ArrayList<PersonalInfoDataM> personalInfos;
	private ArrayList<ProjectCodeDataM> projectCodes;
	private ArrayList<ReferencePersonDataM> referencePersons;
	private ArrayList<SaleInfoDataM> saleInfos;
	private ArrayList<SMSLogDataM> smsLogs;
	private VerificationResultDataM verificationResult;
	private ArrayList<DocumentCheckListDataM> documentCheckLists;
	private ArrayList<DocumentCheckListDataM> documentCheckListManuals;
	private ArrayList<DocumentCheckListDataM> documentCheckListManualAdditionals;
	private ArrayList<WorkFlowDecisionM> workFlowDecisions;
	private ArrayList<PaymentMethodDataM> paymentMethods = new ArrayList<PaymentMethodDataM>();
	private ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
	private ArrayList<DocumentCommentDataM> documentComments;
	private ArrayList<ComparisonGroupDataM> comparisonGroups;
	private ArrayList<ApplicationIncreaseDataM> applicationIncreases = new ArrayList<ApplicationIncreaseDataM>();
	private HistoryDataM historyData;
//	private ArrayList<DocumentRelationDataM> documentRelations;
	private String sortType;
	private ReasonDataM reason;
	private ArrayList<FinalVerifyResultDataM> finalVerifys = new ArrayList<FinalVerifyResultDataM>();
	private String decisionLog; //ORIG_APPLICATION_GROUP.DECISION_LOG
	private ArrayList<ReasonDataM> reasons;
	private ArrayList<ReasonLogDataM> reasonLogs;
	private String wfState;
	private String roleId;
	private ArrayList<String> overSlaDocumentType;
	private String productElementFlag;
	private String applyTypeStatus;
	private String employeeName;
	private String employeeNo;
	private String transactNo;
	private String pegaEventFlag;
	private String reprocessFlag;
	private String callCompleteTaskFlag;
	private String fullFraudFlag;	// ORIG_APPLICATION_GROUP.FULL_FRAUD_FLAG
	private String reCalculateActionFlag;
	private String source;
	private String requireVerify;
	private String sourceAction;
	private String lhRefId;
	private String sourceUserId;
	
	public String getFullFraudFlag() {
		return fullFraudFlag;
	}
	public void setFullFraudFlag(String fullFraudFlag) {
		this.fullFraudFlag = fullFraudFlag;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getTransactNo() {
		return transactNo;
	}
	public void setTransactNo(String transactNo) {
		this.transactNo = transactNo;
	}	
	public String getApplyTypeStatus() {
		return applyTypeStatus;
	}
	public void setApplyTypeStatus(String applyTypeStatus) {
		this.applyTypeStatus = applyTypeStatus;
	}
	public String getProductElementFlag() {
		return productElementFlag;
	}
	public void setProductElementFlag(String productElementFlag) {
		this.productElementFlag = productElementFlag;
	}
	public ArrayList<String> getOverSlaDocumentType() {
		return overSlaDocumentType;
	}
	public void setOverSlaDocumentType(ArrayList<String> overSlaDocumentType) {
		this.overSlaDocumentType = overSlaDocumentType;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleIdSaveApplication) {
		this.roleId = roleIdSaveApplication;
	}
	public ArrayList<ReasonDataM> getReasons() {
		return reasons;
	}
	public void setReasons(ArrayList<ReasonDataM> reasons) {
		this.reasons = reasons;
	}
	public ArrayList<String> getReasonCodes(){
		ArrayList<String> reasonCodes = new ArrayList<String>();
		if(null != this.reasons && this.reasons.size() > 0){
			for(ReasonDataM reason : this.reasons){
				if(null != reason.getReasonCode()){
					reasonCodes.add(reason.getReasonCode());
				}
			}
		}
		return reasonCodes;
	}
	public ArrayList<ReasonLogDataM> getReasonLogs() {
		return reasonLogs;
	}
	public void setReasonLogs(ArrayList<ReasonLogDataM> reasonLogs) {
		this.reasonLogs = reasonLogs;
	}
	public ApplicationIncreaseDataM getApplicationIncrease(int index) {
		return applicationIncreases.get(index);
	}
	public void setApplicationIncrease(ApplicationIncreaseDataM applicationIncrease) {
		this.applicationIncreases.add(applicationIncrease);
	}
	public ArrayList<ApplicationIncreaseDataM> getApplicationIncreases() {
		return applicationIncreases;
	}
	public void setApplicationIncreases(
			ArrayList<ApplicationIncreaseDataM> applicationIncreases) {
		this.applicationIncreases = applicationIncreases;
	}
	public String getOverPercentORFlag() {
		return overPercentORFlag;
	}
	public void setOverPercentORFlag(String overPercentORFlag) {
		this.overPercentORFlag = overPercentORFlag;
	}
	public void clearFinalVerify(){
		finalVerifys.clear();
		finalVerifys = new ArrayList<FinalVerifyResultDataM>();
	}
	public void createFinalVerifyResult(VerifyId verifyId,String verifyResultCode){
		FinalVerifyResultDataM finalVerifyResult = new FinalVerifyResultDataM();
		finalVerifyResult.setVerifyId(verifyId);
		finalVerifyResult.setVerifyResultCode(verifyResultCode);
		finalVerifys.add(finalVerifyResult);
	}
	public class SORT_TYPE{
		public static final String ASC = "ASC";
		public static final String DESC = "DESC";
	}
	
	public String getAuditLogFlag() {
		return auditLogFlag;
	}
	public void setAuditLogFlag(String auditLogFlag) {
		this.auditLogFlag = auditLogFlag;
	}
	public Date getLastDecisionDate() {
		return lastDecisionDate;
	}
	public void setLastDecisionDate(Date lastDecisionDate) {
		this.lastDecisionDate = lastDecisionDate;
	}
	public VerificationResultDataM getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(VerificationResultDataM verificationResult) {
		this.verificationResult = verificationResult;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getVetoRemark() {
		return vetoRemark;
	}
	public void setVetoRemark(String vetoRemark) {
		this.vetoRemark = vetoRemark;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	public String getApproverRemark() {
		return approverRemark;
	}
	public void setApproverRemark(String approverRemark) {
		this.approverRemark = approverRemark;
	}
	public String getBundleFlag() {
		return bundleFlag;
	}
	public void setBundleFlag(String bundleFlag) {
		this.bundleFlag = bundleFlag;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public Integer getInstantId() {
		return instantId;
	}
	public void setInstantId(Integer instantId) {
		this.instantId = instantId;
	}
	public String getLastDecision() {
		return lastDecision;
	}
	public void setLastDecision(String lastDecision) {
		this.lastDecision = lastDecision;
	}
	public String getCoverpageType() {
		return coverpageType;
	}
	public void setCoverpageType(String coverpageType) {
		this.coverpageType = coverpageType;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getApplicationTemplate() {
		return applicationTemplate;
	}
	public void setApplicationTemplate(String applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}
//	public boolean getTEMPLATE_INCREASE_FLIP(ArrayList<String> TEMPLATE_CODE,String TEMPLATE_INCREASEs , String APPLICATION_TYPE_INCREASE ){
//		for(String template : TEMPLATE_CODE){
//			if(!TEMPLATE_INCREASEs.contains(template) && APPLICATION_TYPE_INCREASE.equals(this.applicationType)){ //Flip Type INC
//				return true;
//			}
//		}		
//		return false;
//	}
	public String getApplyChannel() {
		return applyChannel;
	}
	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
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
	public ArrayList<ApplicationDataM> getApplications() {
		return applications;
	}
	public void setApplications(ArrayList<ApplicationDataM> applications) {
		this.applications = applications;
	}
	public void addAll(ArrayList<ApplicationDataM> _applications){
		if(null == applications){
			applications = new ArrayList<ApplicationDataM>();
		}
		applications.addAll(_applications);
	}
	public ArrayList<ApplicationImageDataM> getApplicationImages() {
		return applicationImages;
	}
	public void setApplicationImages(ArrayList<ApplicationImageDataM> applicationImages) {
		this.applicationImages = applicationImages;
	}
	public ArrayList<ApplicationLogDataM> getApplicationLogs() {
		return applicationLogs;
	}
	public void setApplicationLogs(ArrayList<ApplicationLogDataM> applicationLogs) {
		this.applicationLogs = applicationLogs;
	}
	public ArrayList<ApplicationPointDataM> getApplicationPoints() {
		return applicationPoints;
	}
	public void setApplicationPoints(ArrayList<ApplicationPointDataM> applicationPoints) {
		this.applicationPoints = applicationPoints;
	}
	public ArrayList<AttachmentDataM> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<AttachmentDataM> attachments) {
		this.attachments = attachments;
	}
	public ArrayList<AuditTrailDataM> getAuditTrails() {
		return auditTrails;
	}
	public void setAuditTrails(ArrayList<AuditTrailDataM> auditTrails) {
		this.auditTrails = auditTrails;
	}
	public ArrayList<ContactLogDataM> getContactLogs() {
		return contactLogs;
	}
	public void setContactLogs(ArrayList<ContactLogDataM> contactLogs) {
		this.contactLogs = contactLogs;
	}
	public ArrayList<EnquiryLogDataM> getEnquiryLogs() {
		return enquiryLogs;
	}
	public void setEnquiryLogs(ArrayList<EnquiryLogDataM> enquiryLogs) {
		this.enquiryLogs = enquiryLogs;
	}
	public ArrayList<NotePadDataM> getNotePads() {
		return notePads;
	}
	public void setNotePads(ArrayList<NotePadDataM> notePads) {
		this.notePads = notePads;
	}
	public ArrayList<PersonalInfoDataM> getPersonalInfos() {
		return personalInfos;
	}	
	public String getCallFicoFlag() {
		return callFicoFlag;
	}
	public void setCallFicoFlag(String callFicoFlag) {
		this.callFicoFlag = callFicoFlag;
	}	
	public String getCalledEscalateFlag() {
		return calledEscalateFlag;
	}
	public void setCalledEscalateFlag(String calledEscalateFlag) {
		this.calledEscalateFlag = calledEscalateFlag;
	}
	public String getPriorityMode() {
		return priorityMode;
	}
	public void setPriorityMode(String priorityMode) {
		this.priorityMode = priorityMode;
	}
	public String getFraudFinalDecision() {
		return fraudFinalDecision;
	}
	public void setFraudFinalDecision(String fraudFinalDecision) {
		this.fraudFinalDecision = fraudFinalDecision;
	}
	public int getPersonalIndex(String personalId){
		int personalIndex = -1;
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				++personalIndex;
				if(null != personalId && personalId.equals(personalInfo.getPersonalId())){
					return personalIndex;
				}
			}
		}
		return -1;
	}
	public int personalSize(String personalType){
		int personalSize = 0;
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if(null != personalType && personalType.equals(personalInfo.getPersonalType())){
					personalSize++;
				}
			}
		}
		return personalSize;
	}
	public int getApplicationIndex(String applicationRecordId){
		int applicationIndex = -1;
		if(null != applications){
			for (ApplicationDataM application : applications) {
				++applicationIndex;
				if(null != applicationRecordId && applicationRecordId.equals(application.getApplicationRecordId())){
					return applicationIndex;
				}
			}
		}
		return -1;
	}
	public int getPaymentMethodIndex(String paymentMethodId){
		int paymentMethodIndex = -1;
		if(null != paymentMethods){
			for (PaymentMethodDataM paymentMethod : paymentMethods) {
				++paymentMethodIndex;
				if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
					return paymentMethodIndex;
				}
			}
		}
		return -1;
	}
	public int getSpecialAdditionalServicesIndex(String serviceId){
		int specialAdditionalServicesIndex = -1;
		if(null != specialAdditionalServices){
			for (SpecialAdditionalServiceDataM specialAdditionalServicesM : specialAdditionalServices) {
				++specialAdditionalServicesIndex;
				if(null != serviceId && serviceId.equals(specialAdditionalServicesM.getServiceId())){
					return specialAdditionalServicesIndex;
				}
			}
		}
		return -1;
	}
	public void setPersonalInfos(ArrayList<PersonalInfoDataM> personalInfos) {
		this.personalInfos = personalInfos;
	}
	public ArrayList<ProjectCodeDataM> getProjectCodes() {
		return projectCodes;
	}
	public void setProjectCodes(ArrayList<ProjectCodeDataM> projectCodes) {
		this.projectCodes = projectCodes;
	}
//	public ReferencePersonDataM getReferencePerson() {
//		return referencePerson;
//	}
//	public void setReferencePerson(ReferencePersonDataM referencePerson) {
//		this.referencePerson = referencePerson;
//	}	
	public ArrayList<ReferencePersonDataM> getReferencePersons() {
		return referencePersons;
	}
	public void setReferencePersons(ArrayList<ReferencePersonDataM> referencePersons) {
		this.referencePersons = referencePersons;
	}
	public ReferencePersonDataM getReferencePersons(int refPersonSeq){
		if(null != referencePersons){
			for (ReferencePersonDataM referencePerson: referencePersons) {
				if(null != referencePerson && refPersonSeq == referencePerson.getSeq()){
					return referencePerson;
				}
			}
		}
		return null;
	}
	public ArrayList<SaleInfoDataM> getSaleInfos() {
		return saleInfos;
	}
	public void setSaleInfos(ArrayList<SaleInfoDataM> saleInfos) {
		this.saleInfos = saleInfos;
	}
	public ArrayList<SMSLogDataM> getSmsLogs() {
		return smsLogs;
	}
	public void setSmsLogs(ArrayList<SMSLogDataM> smsLogs) {
		this.smsLogs = smsLogs;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getCaRemark() {
		return caRemark;
	}
	public void setCaRemark(String caRemark) {
		this.caRemark = caRemark;
	}
	public String getFraudRemark() {
		return fraudRemark;
	}
	public void setFraudRemark(String fraudRemark) {
		this.fraudRemark = fraudRemark;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public PersonalInfoDataM getPersonalInfoById(String personalId){
		if(null != personalInfos){
			for(PersonalInfoDataM personalInfo : personalInfos){
				if(null != personalId && personalId.equals(personalInfo.getPersonalId())){
					return personalInfo;
				}
			}
		}
		return null;
	}
	public ArrayList<PersonalInfoDataM> getPersonalInfos(String personalType) {
		ArrayList<PersonalInfoDataM> _personalInfos = new ArrayList<PersonalInfoDataM>();
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (null != personalType && personalType.equals(personalInfo.getPersonalType())) {
					_personalInfos.add(personalInfo);
				}
			}
		}
		return _personalInfos;
	}	
	public ArrayList<PersonalInfoDataM> getPersonalInfos(ArrayList<String> personalTypes) {
		ArrayList<PersonalInfoDataM> filterpersonalInfos = new ArrayList<PersonalInfoDataM>();
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (null != personalInfos &&  personalTypes.contains(personalInfo.getPersonalType())) {
					filterpersonalInfos.add(personalInfo);
				}
			}
		}
		return filterpersonalInfos;
	}
	public PersonalInfoDataM getPersonalInfo(String personalType) {
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (null != personalType && personalType.equals(personalInfo.getPersonalType())) {
					return personalInfo;
				}
			}
		}
		return null;
	}	
	public ArrayList<PersonalRelationDataM> getPersonalRelation(String applicationRecordId,String relationLevel){
		ArrayList<PersonalRelationDataM> _personalRelations = new ArrayList<PersonalRelationDataM>();
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
				if(null != personalRelations){
					for (PersonalRelationDataM personalRelation : personalRelations) {
						if(personalRelation.getRefId().equals(applicationRecordId) && personalRelation.getRelationLevel().equals(relationLevel)){
							_personalRelations.add(personalRelation);
						}
					}
				}
			}
		}
		return _personalRelations;
	}
	
	
	public ArrayList<PersonalRelationDataM> getPersonalRelation(String applicationRecordId){
		ArrayList<PersonalRelationDataM> filterPersonalRelations = new ArrayList<PersonalRelationDataM>();
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
				if(null != personalRelations){
					for (PersonalRelationDataM personalRelation : personalRelations) {
						if(personalRelation.getRefId().equals(applicationRecordId)){
							filterPersonalRelations.add(personalRelation);
						}
					}
				}
			}
		}
		return filterPersonalRelations;
	}
//	public PersonalInfoDataM getPersonalInfo(int seq) {
//		if (null != personalInfos) {
//			for (PersonalInfoDataM personal : personalInfos) {
//				if (seq == personal.getSeq()) {
//					return personal;
//				}
//			}
//		}
//		return null;
//	}
	public void addPersonalInfo(PersonalInfoDataM personalInfo) {
		if (null == personalInfos) {
			personalInfos = new ArrayList<>();
		}
//		int seq = personalInfos.size() + 1;
//		personalInfo.setSeq(seq);
		personalInfos.add(personalInfo);
	}
//	private ProjectCodeDataM getProjectCodeByCode(String projectCode) {
//		if (projectCode != null && projectCodes != null) {
//			for (ProjectCodeDataM projCodeM : projectCodes) {
//				if (projectCode.equals(projCodeM.getProjectCode())) {
//					return projCodeM;
//				}
//			}
//		}
//		return null;
//	}
	public PersonalInfoDataM getPersonalById(String personalId) {
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (null != personalId && personalId.equals(personalInfo.getPersonalId())) {
					return personalInfo;
				}
			}
		}
		return null;
	}
	public ApplicationDataM getApplicationProduct(String product) {
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())) {
					return application;
				}
			}
		}
		return null;
	}
	public ApplicationDataM filterApplicationProductLifeCycle(String product) {
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())
						&& application.getLifeCycle() == maxLifeCycle) {
					return application;
				}
			}
		}
		return null;
	}
	public int countApplicationProductLifeCycle(String product){
		int maxLifeCycle = getMaxLifeCycle();
		int item = 0;
		if(null != applications){
			for(ApplicationDataM application:applications){
				if (null != product && product.equals(application.getProduct())
						&& application.getLifeCycle() == maxLifeCycle) {
					item++;
				}
			}
		}
		return item;
	}
	public ArrayList<ApplicationDataM> filterListApplicationProductLifeCycle(String product) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())
						&& application.getLifeCycle() == maxLifeCycle) {
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> getApplicationsProduct(String product) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())) {
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<String> getApplicationApplyTypes(ArrayList<String> appRecordIds) {
		ArrayList<String> filterApplicationTypes = new ArrayList<String>();
		if(null!=appRecordIds && null!=applications){
			for (ApplicationDataM application : applications) {
				if (null!=application.getApplicationType() &&!filterApplicationTypes.contains(application.getApplicationType()) 
						&& appRecordIds.contains(application.getApplicationRecordId())){
					filterApplicationTypes.add(application.getApplicationType());
				}
			}
		}		 
		return filterApplicationTypes;
	}
	public ArrayList<ApplicationDataM> filterApplicationsProduct(String product) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();		
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())) {
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterDisplayApplicationsProduct(String product,String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();		
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())){
					if(application.getLifeCycle()==getMaxLifeCycle()){
						if(null!=applicationCardType && !"".equals(applicationCardType)){
							CardDataM card = application.getCard();
							if (null != card) {
								String applicationType = card.getApplicationType();
								if (null != applicationType && applicationType.equals(applicationCardType)) {
									filterApplication.add(application);
								}
							}
						}else{
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterDisplayEnquiryApplicationsProduct(String product,String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();		
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && isHeaderApplcation(application.getApplicationRecordId())){
						if(null!=applicationCardType && !"".equals(applicationCardType)){
							CardDataM card = application.getCard();
							if (null != card) {
								String applicationType = card.getApplicationType();
								if (null != applicationType && applicationType.equals(applicationCardType)) {
									filterApplication.add(application);
								}
							}
						}else{
							filterApplication.add(application);
						}
				}
			}
		}
		return filterApplication;
	}	
	public ArrayList<ApplicationDataM> filterDisplayEnquiryApplicationsProduct() {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();		
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (isHeaderApplcation(application.getApplicationRecordId())){
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterDisplayEnquiryApplicationsProductMaxLifeCycle(String product,String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();		
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && isHeaderApplcation(application.getApplicationRecordId())){
					if(application.getLifeCycle()==getMaxLifeCycle()){
						if(null!=applicationCardType && !"".equals(applicationCardType)){
							CardDataM card = application.getCard();
							if (null != card) {
								String applicationType = card.getApplicationType();
								if (null != applicationType && applicationType.equals(applicationCardType)) {
									filterApplication.add(application);
								}
							}
						}else{
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public boolean isHeaderApplcation(String applicationRecordId){
		ArrayList<String> isContainReferUniqueId = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
					String refApplicationRecordId = application.getRefApplicationRecordId();
				if(null!=refApplicationRecordId && refApplicationRecordId.equals(applicationRecordId)){
					isContainReferUniqueId.add(application.getApplicationRecordId());
				  }
				}
			}
		return isContainReferUniqueId.size()==0;
	}
	public ArrayList<ApplicationDataM> filterApplicationDecision(String product,int maxLifeCycle,String[] decision) {
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(decision));
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())
						&& conditions.contains(application.getFinalAppDecision())
							&& (maxLifeCycle==application.getLifeCycle())) {
					filterApplications.add(application);
				}
			}
		}
		return filterApplications;
	}
	public ArrayList<ApplicationDataM> filterApplicationDecision(String product,String[] decision) {
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(decision));
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxlifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && conditions.contains(application.getFinalAppDecision())&& (maxlifeCycle==application.getLifeCycle())) {
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationDecision(String product,int maxLifeCycle,String[] decision,String applicationCardType) {
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(decision));
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && conditions.contains(application.getFinalAppDecision())) {
					CardDataM card = application.getCard();
					if(null != card){
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType) && (maxLifeCycle==application.getLifeCycle())) {
							filterApplications.add(application);
						}
					}
				}
			}
		}
		return filterApplications;
	}
	public ArrayList<ApplicationDataM> filterApplicationDecision(String product,String[] decision,String applicationCardType) {
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(decision));
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && conditions.contains(application.getFinalAppDecision())) {
					CardDataM card = application.getCard();
					if(null != card){
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType) && (maxLifeCycle==application.getLifeCycle())) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	
	public ArrayList<ApplicationDataM> filterFinalAppDecisionLifeCycle(String productCode,String applicationCardType){
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != productCode && productCode.equals(application.getProduct()) && maxLifeCycle == application.getLifeCycle()
						&& null != application.getFinalAppDecision() && !"".equals(application.getFinalAppDecision())){
					CardDataM card = application.getCard();
					if(null != card){
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	
	public ArrayList<ApplicationDataM> filterLinkApplicationDecision(String product,String[] decision,String applicationCardType,String applicationRecordId) {
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(decision));
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) 
						&& conditions.contains(application.getFinalAppDecision())
							&& null != applicationRecordId && applicationRecordId.equals(application.getMaincardRecordId()) && (maxLifeCycle==application.getLifeCycle())) {
					CardDataM card = application.getCard();
					if(null != card){
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterLinkApplicationDecision(String applicationRecordId,String[] decision,String applicationCardType) {
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(decision));
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (conditions.contains(application.getFinalAppDecision())
							&& null != applicationRecordId && applicationRecordId.equals(application.getMaincardRecordId())) {
					CardDataM card = application.getCard();
					if(null != card){
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplications.add(application);
						}
					}
				}
			}
		}
		return filterApplications;
	}
	public ArrayList<ApplicationDataM> filterApplicationCardType(String product, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationProductLifeCycles(String product, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationProductLifeCyclesByFinalAppDecision(String product, String applicationCardType,String finalAppDecision) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)
								&& null != finalAppDecision && finalAppDecision.equals(application.getFinalAppDecision())) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationProductLifeCycle(String product, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationProductLifeCyclesByFinalAppDecision(String product, String applicationCardType
			,String uniqueId,String finalAppDecision) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()
						&& null != uniqueId && uniqueId.equals(application.getMaincardRecordId())
							&& null != finalAppDecision && finalAppDecision.equals(application.getFinalAppDecision())) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationProductLifeCycles(String product) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()){
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}	
	public ArrayList<ApplicationDataM> filterApplicationProductLifeCyclesByFinalAppDecision(String product,String finalAppDecision) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()
						&& null != finalAppDecision && finalAppDecision.equals(application.getFinalAppDecision())){
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}	
	public ArrayList<ApplicationDataM> filterApplicationCardTypeLifeCycle(String product, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationCardTypeLifeCycle(String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (maxLifeCycle==application.getLifeCycle()) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationCardType(String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();		
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				CardDataM card = application.getCard();
				if (null != card) {
					String applicationType = card.getApplicationType();
					if (null != applicationType && applicationType.equals(applicationCardType)) {
						filterApplication.add(application);
					}
				}
			}
		}
		return filterApplication;
	}
	public ApplicationDataM getApplicationCardType(String applicationCardType){
		if(null != applications){
			for (ApplicationDataM application : applications) {
				CardDataM card = application.getCard();
				if(null != card){
					String applicationType = card.getApplicationType();
					if (null != applicationType && applicationType.equals(applicationCardType)) {
						return application;
					}
				}
			}
		}
		return null;
	}
	public ArrayList<ApplicationDataM> allApplicationCardType(String product) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())) {
							filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationIdCardType(String applicationId, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != applicationId && applicationId.equals(application.getApplicationRecordId())) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public String getProduct(ApplicationDataM application) {
		if (null != application.getBusinessClassId()) {
			return application.getProduct();
		}
		return null;
	}
	public void addApplications(ApplicationDataM application) {
		if (null == applications) {
			applications = new ArrayList<>();
		}
		applications.add(application);
	}
	public ArrayList<LoanDataM> getLoanByProduct(String productType) {
		ApplicationDataM application = getApplicationProduct(productType);
		if (null != application) {
			return application.getLoans();
		}
		return null;
	}
	public SaleInfoDataM getSaleInfoByType(String saleType) {
		if (null != saleInfos && 0 < saleInfos.size()) {
			for (SaleInfoDataM saleInfoM : saleInfos) {
				if (null!=saleInfoM && null!=saleInfoM.getSalesType() &&null != saleType && saleType.equals(saleInfoM.getSalesType())) {
					return saleInfoM;
				}
			}
		}
		return null;
	}
	public void addSaleInfos(SaleInfoDataM saleInfoM) {
		if (null == saleInfos) {
			this.saleInfos = new ArrayList<SaleInfoDataM>();
		}
		this.saleInfos.add(saleInfoM);
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBundingId() {
		return bundingId;
	}
	public void setBundingId(String bundingId) {
		this.bundingId = bundingId;
	}	
	public String getDecisionAction() {
		return decisionAction;
	}
	public void setDecisionAction(String decisionAction) {
		this.decisionAction = decisionAction;
	}
	public ArrayList<String> getProducts() {
		ArrayList<String> products = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				if (null != product && !products.contains(product)) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}	
	public ArrayList<String> filterProductLifeCycle() {
		int maxLifeCycle = getMaxLifeCycle();
		ArrayList<String> products = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				if (null != product && !products.contains(product)&& maxLifeCycle == applicationM.getLifeCycle()) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}	
	public ArrayList<String> filterProductLifeCycleByFinalAppDecision(String finalAppDecision) {
		int maxLifeCycle = getMaxLifeCycle();
		ArrayList<String> products = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				if (null != product && !products.contains(product)&& maxLifeCycle == applicationM.getLifeCycle()
						&& null != finalAppDecision && finalAppDecision.equals(applicationM.getFinalAppDecision())) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}	
	public ArrayList<String> getProducts(int maxLifeCycle){
		ArrayList<String> products = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				String product = application.getProduct();
				if (null != product && !products.contains(product) && maxLifeCycle == application.getLifeCycle()) {
					products.add(product);
				}
			}
		}
		return products;
	}
	public ArrayList<String> getProducts(String filterProduct[]) {
		ArrayList<String> compare = new ArrayList<String>(Arrays.asList(filterProduct));
		ArrayList<String> products = new ArrayList<String>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				if (null != product && !products.contains(product) && compare.contains(product) && maxLifeCycle == applicationM.getLifeCycle()) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}
	public ArrayList<String> getProductApplicationRecordId(String filterProduct[],ArrayList<String> applicationRecordIds) {
		ArrayList<String> compare = new ArrayList<String>(Arrays.asList(filterProduct));
		ArrayList<String> products = new ArrayList<String>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications && null!=applicationRecordIds) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				String applicationRecordId = applicationM.getApplicationRecordId();
				if (null != product && !products.contains(product) && compare.contains(product) && maxLifeCycle == applicationM.getLifeCycle() && applicationRecordIds.contains(applicationRecordId)) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}
	public ArrayList<String> getMaxLifeCycleProducts(String filterProduct[]) {
		ArrayList<String> compare = new ArrayList<String>(Arrays.asList(filterProduct));
		ArrayList<String> products = new ArrayList<String>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				if (null != product && !products.contains(product) && compare.contains(product) && maxLifeCycle == applicationM.getLifeCycle()) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}
	public ArrayList<String> filterProductFinalAppDecisionLifeCycle(String filterProduct[]) {
		ArrayList<String> compare = new ArrayList<String>(Arrays.asList(filterProduct));
		ArrayList<String> products = new ArrayList<String>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				String product = application.getProduct();
				if (null != product && !products.contains(product) && compare.contains(product) && maxLifeCycle == application.getLifeCycle()
						&& null != application.getFinalAppDecision() && !"".equals(application.getFinalAppDecision())) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}
	public ArrayList<String> getDisplayEnquiryProducts(String filterProduct[]) {
		ArrayList<String> compare = new ArrayList<String>(Arrays.asList(filterProduct));
		ArrayList<String> products = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				String product = applicationM.getProduct();
				if (null != product && !products.contains(product) && compare.contains(product)) {
					products.add(product);
				}
			}
		}
		Collections.sort(products);
		return products;
	}
	public ArrayList<ApplicationDataM> filterApplicationLifeCycle(){
		ArrayList<ApplicationDataM> applicationLifeCycles = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if(maxLifeCycle == applicationM.getLifeCycle()){
					applicationLifeCycles.add(applicationM);
				}
			}
		}
		return applicationLifeCycles;
	}
	public ArrayList<ApplicationDataM> filterApplicationSortApplicationTypeLifeCycle(ArrayList<ApplicationDataM> borrowerApplication,ArrayList<ApplicationDataM> supplementApplication){
		ArrayList<ApplicationDataM> applicationSortLifeCycles = new ArrayList<ApplicationDataM>();
		if (null != borrowerApplication && borrowerApplication.size() > 0) {
			for (ApplicationDataM applicationM : borrowerApplication) {
					applicationSortLifeCycles.add(applicationM);
			}
		}
		
		if (null != supplementApplication && supplementApplication.size() > 0) {
			for (ApplicationDataM applicationM : supplementApplication) {
					applicationSortLifeCycles.add(applicationM);
			}
		}
		return applicationSortLifeCycles;
	}
	
	public ArrayList<ApplicationDataM> filterApplicationLifeCycleByFinalAppDecision(String finalAppDecision){
		ArrayList<ApplicationDataM> applicationLifeCycles = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if(maxLifeCycle == applicationM.getLifeCycle() && null != finalAppDecision && finalAppDecision.equals(applicationM.getFinalAppDecision())){
					applicationLifeCycles.add(applicationM);
				}
			}
		}
		return applicationLifeCycles;
	}
	
	public ArrayList<String> filterApplicationIdsLifeCycleByFinalAppDecision(String finalAppDecision){
		ArrayList<String> applicationLifeCycles = new ArrayList<>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if(maxLifeCycle == applicationM.getLifeCycle() && null != finalAppDecision && finalAppDecision.equals(applicationM.getFinalAppDecision())){
					applicationLifeCycles.add(applicationM.getApplicationRecordId());
				}
			}
		}
		return applicationLifeCycles;
	}
	
//	public void removeApplicationDeleteFlag(){
//		if (null != applications) {
//			for(Iterator<ApplicationDataM> iterator = applications.iterator(); iterator.hasNext();){
//				ApplicationDataM application = iterator.next();
//				if(application.isDeleteFlag()){
//					iterator.remove();
//				}
//			}
//		}
//	}
	public ApplicationDataM filterApplicationItemLifeCycle(){
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if(maxLifeCycle == applicationM.getLifeCycle()){
					return applicationM;
				}
			}
		}
		return null;
	}
	public ArrayList<ApplicationDataM> filterApplicationLifeCycle(String productCode){
		ArrayList<ApplicationDataM> applicationLifeCycles = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if(maxLifeCycle == applicationM.getLifeCycle() && null != applicationM.getProduct() && applicationM.getProduct().equals(productCode)){
					applicationLifeCycles.add(applicationM);
				}
			}
		}
		return applicationLifeCycles;
	}	
//	public ArrayList<ApplicationDataM> filterApplicationLifeCycleByCardTypeOfPersonalId(String personalId,String product,String cardType){
//		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
//		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
//		if (null != applications) {
//			for (ApplicationDataM application : applications){
//				PersonalRelationDataM personalRelation = getPersonalReation(application.getApplicationRecordId());
//				if(null != personalRelation &&  null != personalRelation.getPersonalId() && personalRelation.getPersonalId().equals(personalId)){
//					if(maxLifeCycle == application.getLifeCycle() && null != application.getProduct() && application.getProduct().equals(product)){
//						CardDataM card = application.getCard();
//						if(null != card && null != card.getApplicationType() && card.getApplicationType().equals(cardType)){
//							filterApplications.add(application);
//						}
//					}
//				}
//			}
//		}
//		return filterApplications;
//	}	
	public ArrayList<ApplicationDataM> filterApplicationLifeCycleByPersonalId(String personalId,String product){
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				PersonalRelationDataM personalRelation = getPersonalReation(application.getApplicationRecordId());
				if(null != personalRelation && null != personalRelation.getPersonalId() && personalRelation.getPersonalId().equals(personalId)){
					if(maxLifeCycle == application.getLifeCycle() 
							&& null != application.getProduct() && application.getProduct().equals(product)){
						filterApplications.add(application);
					}
				}
			}
		}
		return filterApplications;
	}	
//	public ArrayList<ApplicationDataM> filterApplicationLifeCycleByProductCardType(String product,String cardType){
//		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
//		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
//		if (null != applications) {
//			for (ApplicationDataM application : applications) {
//				if(maxLifeCycle == application.getLifeCycle() && null != application.getProduct() && application.getProduct().equals(product)){
//					filterApplications.add(application);
//				}
//			}
//		}
//		return filterApplications;
//	}	
	public ArrayList<ApplicationDataM> filterApplicationLifeCycle(String productCode, String cardType){
		ArrayList<ApplicationDataM> applicationLifeCycles = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("cardType >> "+cardType);
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
//				if(!applicationM.isDeleteFlag()){
					CardDataM cardM = applicationM.getCard();
					if(maxLifeCycle == applicationM.getLifeCycle()
							&& null != applicationM.getProduct() && applicationM.getProduct().equals(productCode)
							&& null != cardM.getApplicationType() && cardM.getApplicationType().equals(cardType)){
						applicationLifeCycles.add(applicationM);
					}
//				}
			}
		}
		return applicationLifeCycles;
	}	
	public ArrayList<ApplicationDataM> filterFinalAppDecisionLifeCycle(String productCode){
		ArrayList<ApplicationDataM> applicationLifeCycles = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
//		System.out.println("maxLifeCycle >> "+maxLifeCycle);
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if(maxLifeCycle == application.getLifeCycle()
						&& null != application.getProduct() && application.getProduct().equals(productCode)
							&& null != application.getFinalAppDecision() && !"".equals(application.getFinalAppDecision())){
					applicationLifeCycles.add(application);
				}
			}
		}
		return applicationLifeCycles;
	}
	public ArrayList<ApplicationDataM> getRefApplication(String applicationRecordId) {
		ArrayList<ApplicationDataM> refApplications = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if (null != applicationM.getRefApplicationRecordId() && applicationM.getRefApplicationRecordId().equals(applicationRecordId)) {
					refApplications.add(applicationM);
				}
			}
		}
		return refApplications;
	}
	public ApplicationDataM getApplicationById(String applicationRecordId) {
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if (null != applicationM.getApplicationRecordId() && applicationM.getApplicationRecordId().equals(applicationRecordId)) {
					return applicationM;
				}
			}
		}
		return null;
	}
	public ApplicationDataM getApplicationById(String applicationRecordId,String product) {
		if (null != applications) {
			for (ApplicationDataM applicationM : applications) {
				if (null != applicationM.getApplicationRecordId() && applicationM.getApplicationRecordId().equals(applicationRecordId) && product.equals(applicationM.getProduct())) {
					return applicationM;
				}
			}
		}
		return null;
	}
	public ArrayList<String> getImageSplitsDocType() {
		ArrayList<String> appImgSplitsDocType = new ArrayList<String>();
		if (null != applicationImages) {
			for (ApplicationImageDataM applicationImg : applicationImages) {
				ArrayList<ApplicationImageSplitDataM> appImgSplits = applicationImg.getApplicationImageSplits();
				if (null != appImgSplits) {
					for (ApplicationImageSplitDataM appImgSplit : appImgSplits) {
						String docType = appImgSplit.getDocType();
						if (!"".equals(docType) && null != docType && !appImgSplitsDocType.contains(docType)) {
							appImgSplitsDocType.add(docType);
						}
					}
				}
			}
		}
		return appImgSplitsDocType;
	}
	public ArrayList<String> getImageSplitsDocType(String personalId,String IMPersonalType) {
		ArrayList<String> appImgSplitsDocType = new ArrayList<String>();
		if (null != applicationImages) {
			for (ApplicationImageDataM applicationImg : applicationImages) {
				ArrayList<ApplicationImageSplitDataM> appImgSplits = applicationImg.getApplicationImageSplits();
				if (null != appImgSplits) {
					for (ApplicationImageSplitDataM appImgSplit : appImgSplits) {
						String docType = appImgSplit.getDocType();
						if((null != personalId && personalId.equals(appImgSplit.getPersonalId()))
								|| (null != IMPersonalType && IMPersonalType.equals(appImgSplit.getApplicantTypeIM()))){
							if (!"".equals(docType) && null != docType && !appImgSplitsDocType.contains(docType)) {
								appImgSplitsDocType.add(docType);
							}
						}
					}
				}
			}
		}
		return appImgSplitsDocType;
	}
	public ArrayList<ApplicationImageSplitDataM> getImageSplits() {
		ArrayList<ApplicationImageSplitDataM> ImgSplits = new ArrayList<ApplicationImageSplitDataM>();
		if (null != applicationImages) {
			for (ApplicationImageDataM applicationImage : applicationImages) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
				if (null != applicationImageSplits) {
					ImgSplits.addAll(applicationImageSplits);
				}
			}
		}
		return ImgSplits;
	}
	public ApplicationImageSplitDataM getImageSplit(String imgPageId){
		if (null != applicationImages) {
			for (ApplicationImageDataM applicationImage : applicationImages) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
				if(null != applicationImageSplits){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImageSplits) {
						if(null != imgPageId && imgPageId.equals(applicationImageSplit.getImgPageId())){
							return applicationImageSplit;
						}
					}
				}
			}
		}
		return null;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public CardDataM getCardApplicationRecordId(String applicationRecordId){
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if(null != applicationRecordId && applicationRecordId.equals(application.getApplicationRecordId())){
					return application.getCard();
				}
			}
		}
		return null;
	}
	public ArrayList<CardDataM> getCards() {
		ArrayList<CardDataM> cards = new ArrayList<CardDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
//				if (application.isDeleteFlag())
//					continue;
				ArrayList<LoanDataM> loans = application.getLoans();
				if (null != loans) {
					for (LoanDataM loan : loans) {
						CardDataM card = loan.getCard();
						if(card != null){
							cards.add(card);
						}
					}
				}
			}
		}
		return cards;
	}
	public int getMaxLifeCycleFromApplication() {
		return getMaxLifeCycle();
	}
	public int itemLifeCycle(){
		int lifeCycleSize = 0;
		int lifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if(application.getLifeCycle() == lifeCycle){
					lifeCycleSize++;
				}
			}
		}
		return lifeCycleSize;
	}
	public int getMaxLifeCycle() {
		int maxLifeCycle = 1;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				maxLifeCycle = Math.max(application.getLifeCycle(), maxLifeCycle);
			}
		}
		return maxLifeCycle;
	}
	public ApplicationDataM getApplication(int index) {
		if (null != applications && applications.size() > 0) {
			return applications.get(index);
		}
		return null;
	}
	public int sizePersonalInfo() {
		return (null != personalInfos) ? personalInfos.size() : 0;
	}
	public int sizeApplication(){
		return (null != applications) ? applications.size() : 0;
	}
	public PersonalInfoDataM getPersonalInfoId(String personalId) {
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (null != personalId && personalId.equals(personalInfo.getPersonalId())) {
					return personalInfo;
				}
			}
		}
		return null;
	}
	public ArrayList<String> getPersonalIds() {
		ArrayList<String> personalIds  = new ArrayList<String>();
		if (null != personalInfos) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if(!PersonalInfoDataM.PersonalType.INFO.equals(personalInfo.getPersonalType())){
					personalIds.add(personalInfo.getPersonalId());
				}
			}
		}
		return personalIds;
	}
	public ArrayList<String> getApplicationIds() {
		ArrayList<String> applicationIds  = new ArrayList<String>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				applicationIds.add(application.getApplicationRecordId());
			}
		}
		return applicationIds;
	}
	public PersonalInfoDataM getPersonalInfoByIDNo(String idNo) {
		if (null != personalInfos && null != idNo) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (idNo.equals(personalInfo.getIdno())) {
					return personalInfo;
				}
			}
		}
		return null;
	}
	public PersonalInfoDataM getPersonalInfoByIDNo(String idNo,String personalTypeException) {
		if (null != personalInfos && null != idNo) {
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if (idNo.equals(personalInfo.getIdno()) && !personalTypeException.equals(personalInfo.getPersonalType())) {
					return personalInfo;
				}
			}
		}
		return null;
	}
	public void addNotePad(NotePadDataM notepad) {
		if (null == notePads) {
			notePads = new ArrayList<NotePadDataM>();
		}
		notePads.add(notepad);
	}
	public void addAuditTrail(AuditTrailDataM auditTrail) {
		if (null == auditTrails) {
			auditTrails = new ArrayList<AuditTrailDataM>();
		}
		auditTrails.add(auditTrail);
	}
	public ArrayList<DocumentCheckListDataM> getDocumentCheckLists() {
		return documentCheckLists;
	}
	public void setDocumentCheckLists(ArrayList<DocumentCheckListDataM> documentCheckLists) {
		this.documentCheckLists = documentCheckLists;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}	
	public PersonalInfoDataM getPersonalInfoRelation(String refId,String personalType,String relationLevel){
		PersonalRelationDataM personalRelation = getPersonalRelation(refId,personalType,relationLevel);
		if(null != personalRelation){
			return getPersonalById(personalRelation.getPersonalId());
		}
		return null;
	}
	public PersonalInfoDataM getPersonalInfoByRelation(String refId){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId);
				if(null != personalRelation){
					return getPersonalById(personalRelation.getPersonalId());
				}
			}
		}
		return null;
	}
	public PersonalRelationDataM getPersonalRelationByRefId(String refId){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId);
				if(null != personalRelation){
					return personalRelation;
				}
			}
		}
		return null;
	}	
	public PersonalRelationDataM getPersonalRelation(String refId,String personalType,String relationLevel){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId,personalType,relationLevel);
				if(null != personalRelation){
					return personalRelation;
				}
			}
		}
		return null;
	}
	public PersonalRelationDataM getPersonalRefApplication(String refId,String relationLevel){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId,relationLevel);
				if(null != personalRelation){
					return personalRelation;
				}
			}
		}
		return null;
	}
	public ApplicationDataM getApplication(String personalId, String personalType, String relationLevel) {
		if (null == personalId || null == personalType || null == relationLevel) {
			return null;
		}
		ArrayList<ApplicationDataM> applications = getApplications();
		if (null == applications) {
			return null;
		}
		for (ApplicationDataM application : applications) {
			ArrayList<PersonalRelationDataM> personalRelations = getPersonalRelation(application.getApplicationRecordId(), relationLevel);
			if (null != personalRelations) {
				for (PersonalRelationDataM personalRelation : personalRelations) {
					if (personalId.equals(personalRelation.getPersonalId())) {
						return application;
					}
				}
			}
		}
		
		return null;
	}
	public ArrayList<ApplicationDataM> getApplications(String personalId, String personalType, String relationLevel) {
		if (null == personalId || null == personalType || null == relationLevel) {
			return null;
		}
		ArrayList<ApplicationDataM> applications = getApplications();
		if (null == applications) {
			return null;
		}
		ArrayList<ApplicationDataM> personalApplications = new ArrayList<>();
		for (ApplicationDataM application : applications) {
			ArrayList<PersonalRelationDataM> personalRelations = getPersonalRelation(application.getApplicationRecordId(), relationLevel);
			if (null != personalRelations) {
				for (PersonalRelationDataM personalRelation : personalRelations) {
					if (personalId.equals(personalRelation.getPersonalId())) {
						personalApplications.add(application);
					}
				}
			}
		}
		
		return personalApplications;
	}
	public ArrayList<ApplicationDataM> filterApplicationRelationLifeCycle(String personalId, String personalType, String relationLevel) {
		if (null == personalId || null == personalType || null == relationLevel) {
			return null;
		}
		ArrayList<ApplicationDataM> applications = getApplications();
		if (null == applications) {
			return null;
		}
		int maxLifeCycle = getMaxLifeCycle();
		ArrayList<ApplicationDataM> personalApplications = new ArrayList<>();
		for (ApplicationDataM application : applications) {
			ArrayList<PersonalRelationDataM> personalRelations = getPersonalRelation(application.getApplicationRecordId(), relationLevel);
			if (null != personalRelations) {
				for (PersonalRelationDataM personalRelation : personalRelations) {
					if (personalId.equals(personalRelation.getPersonalId()) 
							&& application.getLifeCycle() == maxLifeCycle) {
						personalApplications.add(application);
					}
				}
			}
		}		
		return personalApplications;
	}
	public ArrayList<String> filterProductPersonal(String personalId,String relationLevel){
		ArrayList<String> products = new ArrayList<String>();
		ArrayList<String> uniqueIds = getPersonalApplication(personalId,relationLevel);
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for(ApplicationDataM application : applications){
				String applicationRecordId = application.getApplicationRecordId();
				if(uniqueIds.contains(applicationRecordId)){
					String product = application.getProduct();
					if (null != product && !products.contains(product)&& maxLifeCycle == application.getLifeCycle()) {
						products.add(product);
					}
				}
			}
		}
		return products;
	}
	public ArrayList<ApplicationDataM> filterApplicationPersonal(String personalId,String relationLevel) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		ArrayList<String> uniqueIds = getPersonalApplication(personalId,relationLevel);
		if (null != applications) {
			for(ApplicationDataM application : applications){
				String applicationRecordId = application.getApplicationRecordId();
				if(uniqueIds.contains(applicationRecordId)){
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterApplicationPersonalLifeCycle(String personalId,String relationLevel) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		ArrayList<String> uniqueIds = getPersonalApplication(personalId,relationLevel);
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for(ApplicationDataM application : applications){
				String applicationRecordId = application.getApplicationRecordId();
				if(uniqueIds.contains(applicationRecordId) && maxLifeCycle==application.getLifeCycle()){
					filterApplication.add(application);
				}
			}
		}
		return filterApplication;
	}
	
	public ArrayList<String> getPersonalApplication(String personalId,String relationLevel){
		ArrayList<String> uniqueIds = new ArrayList<String>();
		if(null != personalInfos){
			for(PersonalInfoDataM personalInfo: personalInfos){
				ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
				if(null != personalRelations){
					for (PersonalRelationDataM personalRelation : personalRelations) {
						if(null != personalRelation){
							if(null != personalId && personalId.equals(personalRelation.getPersonalId()) && relationLevel.equals(personalRelation.getRelationLevel())){
								uniqueIds.add(personalRelation.getRefId());
							}
						}
					}
				}
			}
		}
		return uniqueIds;
	}
	public PersonalRelationDataM getPersonalRelationByRefId(String refId,String personalId){
		if(null != personalInfos){
			for(PersonalInfoDataM personalInfo: personalInfos){
				ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
				if(null != personalRelations){
					for (PersonalRelationDataM personalRelation : personalRelations) {
						if(null != personalRelation){
							if(null != personalId && personalId.equals(personalRelation.getPersonalId()) 
									&& null != refId && refId.equals(personalRelation.getRefId())){
								return personalRelation;
							}
						}
					}
				}
			}
		}
		return null;
	}
	public ArrayList<String> filterPersonalApplicationIdLifeCycle(String personalId,String relationLevel){
		int maxLifeCycle = getMaxLifeCycle();
		ArrayList<String> uniqueIds = new ArrayList<String>();
		if(null != personalInfos){
			for(PersonalInfoDataM personalInfo: personalInfos){
				ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
				if(null != personalRelations){
					for (PersonalRelationDataM personalRelation : personalRelations) {
						if(null != personalRelation){
							if(null != personalId && personalId.equals(personalRelation.getPersonalId()) && relationLevel.equals(personalRelation.getRelationLevel())){
								String refId = personalRelation.getRefId();
								ApplicationDataM application = getApplicationById(refId);
								if(null != application && application.getLifeCycle() == maxLifeCycle){
									uniqueIds.add(refId);
								}
							}
						}
					}
				}
			}
		}
		return uniqueIds;
	}
	public ArrayList<String> filterPersonalApplicationLifeCycleProduct(String personalId,String relationLevel, String product) {
		ArrayList<String> uniqueIds = filterPersonalApplicationIdLifeCycle(personalId, relationLevel);
		ArrayList<String> appRecordIds = new ArrayList<String>();
		if (null != applications) {
			for(ApplicationDataM application : applications){
				String applicationRecordId = application.getApplicationRecordId();
				if(uniqueIds.contains(applicationRecordId)){
					String appProduct = application.getProduct();
					if (null != appProduct && null != product && product.equals(appProduct)) {
						appRecordIds.add(applicationRecordId);
					}
				}
			}
		}
		return appRecordIds;
	}
	public ArrayList<ApplicationDataM> filterMaincardRecordIdCardType(String maincardRecordId, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != maincardRecordId && maincardRecordId.equals(application.getMaincardRecordId())) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterMaincardRecordIdCardTypeLifeCycle(String maincardRecordId, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != maincardRecordId && maincardRecordId.equals(application.getMaincardRecordId()) && maxLifeCycle == application.getLifeCycle()) {
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ArrayList<ApplicationDataM> filterLinkMainCardFinalAppDecisionLifeCycle(String applicationRecordId, String applicationCardType) {
		ArrayList<ApplicationDataM> filterApplication = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != applicationRecordId && applicationRecordId.equals(application.getMaincardRecordId())
						&& maxLifeCycle == application.getLifeCycle()
							&& null != application.getFinalAppDecision() && !"".equals(application.getFinalAppDecision())){
					CardDataM card = application.getCard();
					if (null != card) {
						String applicationType = card.getApplicationType();
						if (null != applicationType && applicationType.equals(applicationCardType)) {
							filterApplication.add(application);
						}
					}
				}
			}
		}
		return filterApplication;
	}
	public ApplicationDataM filterFirstApplicationFinalDecision(String product, ArrayList<String> notInFinalDecisions) {		 
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && !notInFinalDecisions.contains(application.getFinalAppDecision())  ) {
					 return application;
				}
			}
		}
		return null;
	}
	public ArrayList<ApplicationDataM> getApplicationReason(String action) {
		ArrayList<ApplicationDataM> applicationReson = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if ((null != action && action.equals(application.getFinalAppDecision()))) {
					applicationReson.add(application);
				}
			}
		}
		return applicationReson;
	}

	public ArrayList<ApplicationDataM> getApplicationLifeCycle(BigDecimal lifeCycle) {
		ArrayList<ApplicationDataM> applicationLifeCycle = new ArrayList<ApplicationDataM>();
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				int ApplogLifeCycle = lifeCycle.intValue();
				if ((null != lifeCycle && application.getLifeCycle()==ApplogLifeCycle)) {
					applicationLifeCycle.add(application);
				}
			}
		}
		return applicationLifeCycle;
	}
	public String getIsVetoEligible() {
		return isVetoEligible;
	}
	public void setIsVetoEligible(String isVetoEligible) {
		this.isVetoEligible = isVetoEligible;
	}
	public String getDocSetNo() {
		return docSetNo;
	}
	public void setDocSetNo(String docSetNo) {
		this.docSetNo = docSetNo;
	}
	public BigDecimal getLegtime1() {
		return legtime1;
	}
	public void setLegtime1(BigDecimal legtime1) {
		this.legtime1 = legtime1;
	}
	public BigDecimal getLegtime2() {
		return legtime2;
	}
	public void setLegtime2(BigDecimal legtime2) {
		this.legtime2 = legtime2;
	}
	public BigDecimal getLegtime3() {
		return legtime3;
	}
	public void setLegtime3(BigDecimal legtime3) {
		this.legtime3 = legtime3;
	}
	public BigDecimal getLegtime4() {
		return legtime4;
	}
	public void setLegtime4(BigDecimal legtime4) {
		this.legtime4 = legtime4;
	}
//	public HashMap<String, CompareDataM> getCompareSensitiveFields() {
//		return compareSensitiveFields;
//	}
//	public void setCompareSensitiveFields(HashMap<String, CompareDataM> compareSensitiveFields) {
//		this.compareSensitiveFields = compareSensitiveFields;
//	}
	public String getClaimBy() {
		return claimBy;
	}
	public void setClaimBy(String claimBy) {
		this.claimBy = claimBy;
		
	}
	public String getBranchRegion() {
		return branchRegion;
	}
	public void setBranchRegion(String branchRegion) {
		this.branchRegion = branchRegion;
	}
	public String getBranchZone() {
		return branchZone;
	}
	public void setBranchZone(String branchZone) {
		this.branchZone = branchZone;
	}
	public String getRcCode() {
		return rcCode;
	}
	public void setRcCode(String rcCode) {
		this.rcCode = rcCode;
	}
	public Date getPolicyExSignOffDate() {
		return policyExSignOffDate;
	}
	public void setPolicyExSignOffDate(Date policyExSignOffDate) {
		this.policyExSignOffDate = policyExSignOffDate;
	}
	public String getPolicyExSignOffBy() {
		return policyExSignOffBy;
	}
	public void setPolicyExSignOffBy(String policyExSignOffBy) {
		this.policyExSignOffBy = policyExSignOffBy;
	}
	public String getPrevJobState() {
		return prevJobState;
	}
	public void setPrevJobState(String prevJobState) {
		this.prevJobState = prevJobState;
	}
	public ArrayList<WorkFlowDecisionM> getWorkFlowDecisions() {
		return workFlowDecisions;
	}
	public void setWorkFlowDecisions(ArrayList<WorkFlowDecisionM> workFlowDecisions) {
		this.workFlowDecisions = workFlowDecisions;
	}	
//	public HashMap<String,ArrayList<PaymentMethodDataM>> getPaymentMethods() {
//		return paymentMethods;
//	}
//	public void setPaymentMethods(HashMap<String,ArrayList<PaymentMethodDataM>> paymentMethods) {
//		this.paymentMethods = paymentMethods;
//	}	
	public String getCoverpagePriority() {
		return coverpagePriority;
	}
	public void setCoverpagePriority(String coverpagePriority) {
		this.coverpagePriority = coverpagePriority;
	}
	public ArrayList<ORPolicyRulesDataM> getORPolicyRules(String applicationRecordId,String[] switchPolicyCode){		
		ArrayList<ORPolicyRulesDataM> filterOrPolicyRules = new ArrayList<ORPolicyRulesDataM>();
		ArrayList<String> ListSwitchPolicyCode = new ArrayList<String>(Arrays.asList(switchPolicyCode));
		ArrayList<String> uniqueIds = searchReferAppRecordId(applicationRecordId);
		if(null!=applications){
			for (ApplicationDataM application : applications) {
				String uniqueId = application.getApplicationRecordId();				
				if(uniqueIds.contains(uniqueId)){
					VerificationResultDataM verificationResult = application.getVerificationResult();
					if(null != verificationResult){
						ArrayList<PolicyRulesDataM> policyRules = verificationResult.getPolicyRules();
						if(null != policyRules){
							for (PolicyRulesDataM policyRule : policyRules) {
								String policyRulesCode = policyRule.getPolicyCode();
								int policyRoleRank = policyRule.getRank();
								ArrayList<ORPolicyRulesDataM>  orPolicyRules = policyRule.getOrPolicyRules();
								if(null != orPolicyRules){
									for(ORPolicyRulesDataM orPolicyRule : orPolicyRules){
										String policyCode = orPolicyRule.getPolicyCode();
										ORPolicyRulesDataM filterOrPolicyRule = getORPolicyRules(policyCode,filterOrPolicyRules);
										if(null == filterOrPolicyRule){
											filterOrPolicyRule = new ORPolicyRulesDataM();
											filterOrPolicyRule.setPolicyCode(policyCode);
											filterOrPolicyRules.add(filterOrPolicyRule);
										}
										ArrayList<ORPolicyRulesDetailDataM> filterOrPolicyRulesDetails = filterOrPolicyRule.getOrPolicyRulesDetails();
										if(null == filterOrPolicyRulesDetails){
											filterOrPolicyRulesDetails = new ArrayList<ORPolicyRulesDetailDataM>();
											filterOrPolicyRule.setOrPolicyRulesDetails(filterOrPolicyRulesDetails);
										}
										if(ListSwitchPolicyCode.contains(policyCode)){
											ORPolicyRulesDetailDataM filterOrPolicyRulesDetail = getOrPolicyRulesDetail(policyRulesCode, filterOrPolicyRulesDetails);
											if(null == filterOrPolicyRulesDetail){
												filterOrPolicyRulesDetail = new ORPolicyRulesDetailDataM();
												filterOrPolicyRulesDetail.setGuidelineCode(policyRulesCode);
												filterOrPolicyRulesDetail.setRank(policyRoleRank);
												filterOrPolicyRulesDetails.add(filterOrPolicyRulesDetail);
											}
										}else{
											ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDetails = orPolicyRule.getOrPolicyRulesDetails();
											if(null != orPolicyRulesDetails){
												for (ORPolicyRulesDetailDataM orPolicyRulesDetail : orPolicyRulesDetails) {
													String guidelineCode = orPolicyRulesDetail.getGuidelineCode();
													int rank = orPolicyRulesDetail.getRank();
													ORPolicyRulesDetailDataM filterOrPolicyRulesDetail = getOrPolicyRulesDetail(guidelineCode, filterOrPolicyRulesDetails);
													if(null == filterOrPolicyRulesDetail){
														filterOrPolicyRulesDetail = new ORPolicyRulesDetailDataM();
														filterOrPolicyRulesDetail.setGuidelineCode(guidelineCode);
														filterOrPolicyRulesDetail.setRank(rank);
														filterOrPolicyRulesDetails.add(filterOrPolicyRulesDetail);
													}
												}
											}
										}										
									}
								}								
							}							
						}
					}					
				}
			}
		}
		return filterOrPolicyRules;
	}
	public ArrayList<String> searchReferAppRecordId(String uniqueId){
		ArrayList<String> uniqueIds = new ArrayList<String>();
		int index = 1;
		searchReferAppRecordId(uniqueId,uniqueIds,index);
		return uniqueIds;
	}	
	public void searchReferAppRecordId(String uniqueId,ArrayList<String> uniqueIds,int index){
		if(null != applications){
			if(index <= applications.size()){
				for (ApplicationDataM application : applications) {
					String applicationRecordId = application.getApplicationRecordId();
					String referApplicaitonRecordId = application.getRefApplicationRecordId();
					if(null != applicationRecordId && applicationRecordId.equals(uniqueId)){
						uniqueIds.add(uniqueId);
						if(null != referApplicaitonRecordId && !"".equals(referApplicaitonRecordId)){ 
							searchReferAppRecordId(referApplicaitonRecordId,uniqueIds,index++);
						}
					}
				}
			}
		}
	}	
	public ApplicationDataM findApplication(String uniqueId,int lifeCycle){
		ArrayList<String> uniqueIds = searchReferAppRecordId(uniqueId);
		if(null != applications){
			for (ApplicationDataM application : applications) {
				if(uniqueIds.contains(application.getApplicationRecordId())&&application.getLifeCycle()==lifeCycle)return application;
			}
		}
		return null;
	}
	public ORPolicyRulesDetailDataM getOrPolicyRulesDetail(String guidelineCode,ArrayList<ORPolicyRulesDetailDataM> filterOrPolicyRulesDetails){
		if(null != filterOrPolicyRulesDetails && null!=guidelineCode){
			for (ORPolicyRulesDetailDataM orPolicyRulesDetail : filterOrPolicyRulesDetails) {
				if(guidelineCode.equals(orPolicyRulesDetail.getGuidelineCode())){
					return orPolicyRulesDetail;
				}
			}
		}
		return null;
	}
	public ORPolicyRulesDataM getORPolicyRules(String policyCode,ArrayList<ORPolicyRulesDataM> filterOrPolicyRules){
		if(null != filterOrPolicyRules && null!=policyCode){
			for (ORPolicyRulesDataM orPolicyRules : filterOrPolicyRules) {
				if(policyCode.equals(orPolicyRules.getPolicyCode())){
					return orPolicyRules;
				}
			}
		}
		return null;
	}
	public ORPolicyRulesDataM getORPolicyRulesLifcycle(String orPolicyCode,int lifeCycle,String applicationRecordId){
		ArrayList<String> uniqueIds = searchReferAppRecordId(applicationRecordId);
		if(null!=applications){
			for (ApplicationDataM application : applications) {
				String uniqueId = application.getApplicationRecordId();				
				if(uniqueIds.contains(uniqueId) && application.getLifeCycle()==lifeCycle){
					VerificationResultDataM verificationResult = application.getVerificationResult();
					if(null != verificationResult){
						ArrayList<PolicyRulesDataM>  verifpolicyRules = verificationResult.getPolicyRules();
						if(null!=verifpolicyRules){
							for(PolicyRulesDataM policyRule :verifpolicyRules){
								ArrayList<ORPolicyRulesDataM> orPolicyRules = policyRule.getOrPolicyRules();
								if(null!=orPolicyRules){
									for(ORPolicyRulesDataM orPolicy :orPolicyRules){
										if(orPolicy.getPolicyCode().equals(orPolicyCode)){
											return orPolicy;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public PolicyRulesDataM getPolicyRulesLifcycle(String policyCode,String orPolicyCode,int lifeCycle,String applicationRecordId){
		ArrayList<String> uniqueIds = searchReferAppRecordId(applicationRecordId);
		if(null!=applications){
			for (ApplicationDataM application : applications) {
				String uniqueId = application.getApplicationRecordId();	
				if(uniqueIds.contains(uniqueId) && application.getLifeCycle()==lifeCycle){
					VerificationResultDataM verificationResult = application.getVerificationResult();
					if(null != verificationResult){
						ArrayList<PolicyRulesDataM>  verifpolicyRules = verificationResult.getPolicyRules();
						if(null!=verifpolicyRules){
							for(PolicyRulesDataM policyRule :verifpolicyRules){
								ArrayList<ORPolicyRulesDataM> orPolicyRules = policyRule.getOrPolicyRules();
								if(null!=orPolicyRules&&null!=policyRule.getPolicyCode()&&policyRule.getPolicyCode().equals(policyCode)){
									for(ORPolicyRulesDataM orPolicy :orPolicyRules){
										if(null!=orPolicy.getPolicyCode()&&orPolicy.getPolicyCode().equals(orPolicyCode)){
											return policyRule;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public PolicyRulesDataM getPolicyRulesApplication(String policyCode,String orPolicyCode,String applicationRecordId){
		if(null!=applications){
			ArrayList<String> uniqueIds = searchReferAppRecordId(applicationRecordId);
			for(ApplicationDataM application : applications){
				if(application.getApplicationRecordId().equals(applicationRecordId)
						|| uniqueIds.contains(application.getApplicationRecordId())){
					VerificationResultDataM verificationResult = application.getVerificationResult();
					if(null != verificationResult){
						ArrayList<PolicyRulesDataM>  verifpolicyRules = verificationResult.getPolicyRules();
						if(null!=verifpolicyRules){
							for(PolicyRulesDataM policyRule :verifpolicyRules){
								ArrayList<ORPolicyRulesDataM> orPolicyRules = policyRule.getOrPolicyRules();
								if(null!=orPolicyRules&&null!=policyRule.getPolicyCode()&&policyRule.getPolicyCode().equals(policyCode)){
									for(ORPolicyRulesDataM orPolicy :orPolicyRules){
										if(null!=orPolicy.getPolicyCode()&&orPolicy.getPolicyCode().equals(orPolicyCode)){
											return policyRule;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public ORPolicyRulesDetailDataM getORPolicyRulesDetailLifcycle(String orPolicyCode,String guidelineCode,int lifeCycle,String applicationRecordId){
		ArrayList<String> uniqueIds = searchReferAppRecordId(applicationRecordId);
		if(null!=applications){
			for (ApplicationDataM application : applications) {
				String uniqueId = application.getApplicationRecordId();	
				if(uniqueIds.contains(uniqueId) && application.getLifeCycle()==lifeCycle){
					VerificationResultDataM verificationResult = application.getVerificationResult();
					if(null != verificationResult){
						ArrayList<PolicyRulesDataM>  verifpolicyRules = verificationResult.getPolicyRules();
						if(null!=verifpolicyRules){
							for(PolicyRulesDataM policyRule :verifpolicyRules){
								ArrayList<ORPolicyRulesDataM> orPolicyRules = policyRule.getOrPolicyRules();
								if(null!=orPolicyRules){
									for(ORPolicyRulesDataM orPolicy :orPolicyRules){
										if(null!=orPolicy.getPolicyCode()&&orPolicy.getPolicyCode().equals(orPolicyCode)){
											ArrayList<ORPolicyRulesDetailDataM> orpolicyRuleDetails =orPolicy.getOrPolicyRulesDetails();
											if(null!=orpolicyRuleDetails){
												for(ORPolicyRulesDetailDataM policyRuleDetail :orpolicyRuleDetails){
													if(null!=policyRuleDetail.getGuidelineCode()&&policyRuleDetail.getGuidelineCode().equals(guidelineCode)){
														return policyRuleDetail;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	public boolean isReferApplication(ApplicationDataM application,String applicationRecordId){
		String refApplicationRecordId =application.getRefApplicationRecordId();
		refApplicationRecordId = null==refApplicationRecordId?"":refApplicationRecordId;
		return (application.getApplicationRecordId().equals(applicationRecordId) || refApplicationRecordId.equals(applicationRecordId));
	}
	public ArrayList<ComparisonGroupDataM> getComparisonGroups() {
		return comparisonGroups;
	}
	public ComparisonGroupDataM getComparisonGroups(String srcOfData) {
		if(null != comparisonGroups){
			for (ComparisonGroupDataM comparisonGroup: comparisonGroups) {
				if(comparisonGroup.getSrcOfData().equals(srcOfData)){
					return comparisonGroup;
				}
			}
		}
		return null;
	}
	public ArrayList<String> getProductCompareData(String srcOfData,ArrayList<String> productList) {
		ComparisonGroupDataM filterComparisonGroup = getComparisonGroups(srcOfData);
		ArrayList<String> filterProduct = new ArrayList<String>();
		if(null!=filterComparisonGroup && null!=productList){
			HashMap<String, CompareDataM> filterCompareData = filterComparisonGroup.getComparisonFields();
			if(null!=filterCompareData){
				for(CompareDataM compareData : filterCompareData.values()){
					String fieldNameType  = compareData.getFieldNameType();
					if(productList.contains(fieldNameType) && !filterProduct.contains(fieldNameType)){
						filterProduct.add(compareData.getFieldNameType());
					}
				}
			}
		}
		return filterProduct;
	}
	public void addComparisonGroups(ComparisonGroupDataM comparisonGroup){
		if(null == comparisonGroups){
			comparisonGroups = new ArrayList<ComparisonGroupDataM>();
		}
		comparisonGroups.add(comparisonGroup);
	}
	public void setComparisonGroups(ArrayList<ComparisonGroupDataM> comparisonGroups) {
		this.comparisonGroups = comparisonGroups;
	}
	public HashMap<String, CompareDataM> getComparisonField(String srcOfData){
		if(null != comparisonGroups){
			for (ComparisonGroupDataM comprisonGroup : comparisonGroups) {
				if(comprisonGroup.getSrcOfData().equals(srcOfData)){
					return comprisonGroup.getComparisonFields();
				}
			}
		}
		return null;
	}
	public void removeComparisonField(String srcOfData){
		if(null != comparisonGroups){
			Iterator<ComparisonGroupDataM> compareGroups = comparisonGroups.iterator();
			while(compareGroups.hasNext()) {
				ComparisonGroupDataM comprisonGroup = compareGroups.next();
				if(null != comprisonGroup.getSrcOfData() && comprisonGroup.getSrcOfData().equals(srcOfData)){
					compareGroups.remove();
				}
			}
		}
	}
	public CompareDataM getComparisonField(String srcOfData,String elementFieldName){
		HashMap<String, CompareDataM> comparisonFields = getComparisonField(srcOfData);
		if(null != comparisonFields){
			return comparisonFields.get(elementFieldName);
		}
		return null;
	}
	public void setComparisonField(String srcOfData,CompareDataM elementData){
		ComparisonGroupDataM comparisonGroup = getComparisonGroups(srcOfData);
		if(null == comparisonGroup){
			comparisonGroup = new ComparisonGroupDataM(srcOfData) ;
			comparisonGroups.add(comparisonGroup);
		}
		HashMap<String, CompareDataM> comparisonFields = comparisonGroup.getComparisonFields();
		if(comparisonFields == null) {
			comparisonFields = new HashMap<String, CompareDataM>();
			comparisonGroup.setComparisonFields(comparisonFields);
		}
		comparisonFields.put(elementData.getFieldName(), elementData);
	}	
	public String getFormAction() {
		return formAction;
	}
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	public int countApplications(){
		return sizeApplication();
	}
	public String getMainCardNo() {
		return mainCardNo;
	}
	public void setMainCardNo(String mainCardNo) {
		this.mainCardNo = mainCardNo;
	}
	public String getMainCardHolderName() {
		return mainCardHolderName;
	}
	public void setMainCardHolderName(String mainCardHolderName) {
		this.mainCardHolderName = mainCardHolderName;
	}	
//	public PaymentMethodDataM getPaymentMethod(String applicantType, String paymentMethodType) {
//		if(null != paymentMethods){
//			ArrayList<PaymentMethodDataM> paymentList = paymentMethods.get(applicantType);
//			if(null != paymentList){
//				for (PaymentMethodDataM paymentMethod : paymentList) {
//					if(null != paymentMethod && null != paymentMethodType && paymentMethodType.equals(paymentMethod.getPaymentMethod())){
//						return paymentMethod;
//					}
//				}
//			}
//		}
//		return null;
//	}	
//	public void removePaymentMethod(String applicantType, String paymentMethodType) {
//		if(null != paymentMethods){
//			ArrayList<PaymentMethodDataM> paymentList = paymentMethods.get(applicantType);
//			if(null != paymentList){
//				Iterator<PaymentMethodDataM> iter = paymentList.iterator();
//				while(iter.hasNext()){
//					PaymentMethodDataM paymentMethod = iter.next();
//					if(null != paymentMethod && null != paymentMethodType && paymentMethodType.equals(paymentMethod.getPaymentMethod())){
//						iter.remove();
//					}
//				}
//			}
//		}
//	}
//	public void addPaymentMethod(String applicantType, PaymentMethodDataM paymentMethod){
//		if(null == paymentMethods){
//			paymentMethods = new HashMap<String,ArrayList<PaymentMethodDataM>>();
//		}
//		ArrayList<PaymentMethodDataM> paymentList = paymentMethods.get(applicantType);
//		if(null == paymentList){
//			paymentList = new ArrayList<PaymentMethodDataM>();
//			paymentMethods.put(applicantType, paymentList);
//		}
//		paymentList.add(paymentMethod);
//	}
//	public void setPaymentMethod(String personalType, PaymentMethodDataM paymentMethod){
//		if(null == paymentMethods){
//			paymentMethods = new HashMap<String,ArrayList<PaymentMethodDataM>>();
//		}
//		ArrayList<PaymentMethodDataM> paymentList = paymentMethods.get(personalType);
//		if(null == paymentList){
//			paymentList = new ArrayList<PaymentMethodDataM>();
//			paymentMethods.put(personalType, paymentList);
//		}
//		
//		int paymentIndex = -1;
//		boolean paymentFound = false;
//		for (PaymentMethodDataM paymentM : paymentList) {
//			++paymentIndex;
//			if(null != paymentMethod && paymentM.getPaymentMethodId().equals(paymentM.getPaymentMethod())){
//				paymentFound = true;
//				break;
//			}
//		}
//		if(!paymentFound){
//			paymentList.add(paymentMethod);
//		}else{
//			paymentList.set(paymentIndex, paymentMethod);
//		}
//	}
//	public void addPaymentMethod(String applicantType, ArrayList<PaymentMethodDataM> paymentMethodList){
//		if(null == paymentMethods){
//			paymentMethods = new HashMap<String,ArrayList<PaymentMethodDataM>>();
//		}
//		ArrayList<PaymentMethodDataM> paymentList = paymentMethods.get(applicantType);
//		if(null == paymentList){
//			paymentMethods.put(applicantType, paymentMethodList);
//		} else {
//			paymentList.addAll(paymentMethodList);
//		}
//	}
//	public PaymentMethodDataM getPaymentMethodByProduct(String applicantType, String productType){
//		if(null != paymentMethods){
//			ArrayList<PaymentMethodDataM> paymentList = paymentMethods.get(applicantType);
//			if(null != paymentList){
//				for (PaymentMethodDataM paymentMethod : paymentList) {
//					if(null != paymentMethod && null != productType && productType.equals(paymentMethod.getProductType())){
//						return paymentMethod;
//					}
//				}
//			}
//		}
//			
//		return null;
//	}
//	public PaymentMethodDataM getPaymentMethodByID(String paymentMethodId) {
//		if(paymentMethods != null && !paymentMethods.isEmpty() && null != paymentMethodId) {
//			for(ArrayList<PaymentMethodDataM> paymentMethodList: paymentMethods.values()) {
//				if(paymentMethodList != null && !paymentMethodList.isEmpty()) {
//					for(PaymentMethodDataM paymentMethodM : paymentMethodList) {
//						if(paymentMethodM.getPaymentMethodId() != null && paymentMethodM.getPaymentMethodId().equals(paymentMethodId)) {
//							return paymentMethodM;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}	
	public ApplicationDataM getApplicationByPaymentMethodId(String paymentMethodId) {
		ArrayList<ApplicationDataM> listApplications = filterApplicationLifeCycle();
		if(null != listApplications){
			for (ApplicationDataM application : listApplications) {
				LoanDataM loan = application.getLoan();
				if(null != loan){
					if(null != loan.getPaymentMethodId() && loan.getPaymentMethodId().equals(paymentMethodId)){
						return application;
					}
				}
			}
		}
		return null;
	}	
//	public HashMap<String, ArrayList<SpecialAdditionalServiceDataM>> getSpecialAdditionalServices() {
//		return specialAdditionalServices;
//	}
//	public void setSpecialAdditionalServices(
//			HashMap<String, ArrayList<SpecialAdditionalServiceDataM>> specialAdditionalServices) {
//		this.specialAdditionalServices = specialAdditionalServices;
//	}

//	public SpecialAdditionalServiceDataM getSpecialAdditionalService(String applicantType, String product, String serviceType){
//		if(null != specialAdditionalServices && !specialAdditionalServices.isEmpty()){
//			if(null != applicantType && null != product){
//				String qualifier = applicantType+"_"+product;
//				ArrayList<SpecialAdditionalServiceDataM> servicesList = specialAdditionalServices.get(qualifier);
//				if(servicesList != null && !servicesList.isEmpty()) {
//					for(SpecialAdditionalServiceDataM specialAdditionalService : servicesList) {
//						if(null != serviceType && serviceType.equals(specialAdditionalService.getServiceType())){
//							return specialAdditionalService;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}	
//	public ArrayList<SpecialAdditionalServiceDataM> getSpecialAdditionalService(String applicantType, String product){
//		if(null != specialAdditionalServices && !specialAdditionalServices.isEmpty()){
//			if(null != applicantType && null != product){
//				String qualifier = applicantType+"_"+product;
//				ArrayList<SpecialAdditionalServiceDataM> servicesList = specialAdditionalServices.get(qualifier);
//				return servicesList;
//			}
//		}
//		return null;
//	}
	public void addSpecialAdditionalService(SpecialAdditionalServiceDataM specialAdditionalService){
		if(null == this.specialAdditionalServices){
			this.specialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
		}
		this.specialAdditionalServices.add(specialAdditionalService);
	}	
//	public void setSpecialAdditionalService(String personalType, String additionalServiceUniqueId, SpecialAdditionalServiceDataM specialAdditionalService){
//		if(null == specialAdditionalServices){
//			specialAdditionalServices = new HashMap<String,ArrayList<SpecialAdditionalServiceDataM>>(); 
//		}
//		if(null != personalType && null != additionalServiceUniqueId){
////			String qualifier = applicantType+"_"+product;
//			ArrayList<SpecialAdditionalServiceDataM> servicesList = specialAdditionalServices.get(additionalServiceUniqueId);
//			if(servicesList == null) {
//				servicesList = new ArrayList<SpecialAdditionalServiceDataM>();
//				specialAdditionalServices.put(additionalServiceUniqueId, servicesList);
//			}
//			int sericeIndex = -1;
//			boolean serviceFound = false;
//			for (SpecialAdditionalServiceDataM serviceM : servicesList) {
//				++sericeIndex;
//				if(null != specialAdditionalService && specialAdditionalService.getServiceId() != null
//						 && specialAdditionalService.getServiceId().equals(serviceM.getServiceId())){
//					serviceFound = true;
//					break;
//				}
//			}
//			if(!serviceFound) {
//				servicesList.add(specialAdditionalService);
//			} else {
//				servicesList.set(sericeIndex, specialAdditionalService);
//			}
//		}
//	}	
//	public void removeSpecialAdditionalService(String applicantType, String product, String serviceType){
//		if(null != specialAdditionalServices && !specialAdditionalServices.isEmpty()){
//			if(null != applicantType && null != product){
//				String qualifier = applicantType+"_"+product;
//				ArrayList<SpecialAdditionalServiceDataM> servicesList = specialAdditionalServices.get(qualifier);
//				if(servicesList != null && !servicesList.isEmpty()) {
//					Iterator<SpecialAdditionalServiceDataM> iter = servicesList.iterator();
//					while(iter.hasNext()){
//						SpecialAdditionalServiceDataM specialAdditionalService = iter.next();
//						if(null != specialAdditionalService.getServiceType() && specialAdditionalService.getServiceType().equals(serviceType)){
//							iter.remove();
//						}
//					}
//				}
//			}
//		}
//	}	
//	public SpecialAdditionalServiceDataM getSpecialAdditionalServiceById(String id){
//		if(id == null || id.isEmpty())return null;
//		if(specialAdditionalServices == null || specialAdditionalServices.isEmpty())return null;
//		
//		for(Entry<String, ArrayList<SpecialAdditionalServiceDataM>> entry : specialAdditionalServices.entrySet()){
//			if(entry == null)continue;
//			List<SpecialAdditionalServiceDataM> list = entry.getValue();
//			if(list == null || list.isEmpty())continue;
//			for(SpecialAdditionalServiceDataM ele : list){
//				if(id.equals(ele.getServiceId())){
//					return ele;
//				}
//			}
//		}
//		return null;
//	}
	public ArrayList<DocumentCommentDataM> getDocumentComments() {
		return documentComments;
	}
	public void setDocumentComments(ArrayList<DocumentCommentDataM> documentComments) {
		this.documentComments = documentComments;
	}	
	public PersonalRelationDataM getPersonalReation(String refId ,String relationLevel){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId,relationLevel);
				if(null != personalRelation){
					return personalRelation;
				}
			}
		}
		return null;
	}	
	public PersonalRelationDataM getPersonalReation(String refId){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId);
				if(null != personalRelation){
					return personalRelation;
				}
			}
		}
		return null;
	}	
//	public PaymentMethodDataM getPaymentMethodByType(String paymentMethodId, String paymentMethodType) {
//		if(paymentMethods != null && !paymentMethods.isEmpty() && null != paymentMethodId) {
//			for(ArrayList<PaymentMethodDataM> paymentMethodList: paymentMethods.values()) {
//				if(paymentMethodList != null && !paymentMethodList.isEmpty()) {
//					for(PaymentMethodDataM paymentMethodM : paymentMethodList) {
//						if(paymentMethodM.getPaymentMethodId() != null && paymentMethodM.getPaymentMethodId().equals(paymentMethodId) &&  null != paymentMethodType && paymentMethodType.equals(paymentMethodM.getPaymentMethod())) {
//							return paymentMethodM;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
	public DocumentCheckListDataM getDocumentCheckList(String applicantTypeIM, String documentCode) {
		if(documentCheckLists != null && applicantTypeIM != null && documentCode != null) {
			for(DocumentCheckListDataM docM : documentCheckLists) {
				if(documentCode.equals(docM.getDocumentCode())
						&& applicantTypeIM.equals(docM.getApplicantTypeIM())) {
					return docM;
				}
			}
		}
		return null;
	}	
	public DocumentCheckListDataM getDocumentCheckList(String documentCode) {
		if(documentCheckLists != null && documentCode != null) {
			for(DocumentCheckListDataM docM : documentCheckLists) {
				if(documentCode.equals(docM.getDocumentCode())) {
					return docM;
				}
			}
		}
		return null;
	}
	public ArrayList<DocumentCheckListDataM> getDocumentCheckListApplicantTypeIM(String applicanTypeIM) {
		ArrayList<DocumentCheckListDataM> filterDocumentCheckList = new ArrayList<DocumentCheckListDataM>();
		if(documentCheckLists != null && applicanTypeIM != null) {
			for(DocumentCheckListDataM docM : documentCheckLists) {
				if(applicanTypeIM.equals(docM.getApplicantTypeIM())) {
					filterDocumentCheckList.add(docM);
				}
			}
		}
		return filterDocumentCheckList;
	}
	public  ArrayList<DocumentCheckListDataM> getDocumentCheckListIds(ArrayList<String> documentCheckListIds) {
		ArrayList<DocumentCheckListDataM> filterDocumentCheckList = new ArrayList<DocumentCheckListDataM>();
		if(documentCheckLists != null && documentCheckListIds != null) {
			for(DocumentCheckListDataM docM : documentCheckLists) {
				if(documentCheckListIds.contains(docM.getDocCheckListId())) {					
					filterDocumentCheckList.add(docM);
				}
			}
		}
		return filterDocumentCheckList;
	}	
	public void addDocumentCheckList(DocumentCheckListDataM docM) {
		if(documentCheckLists == null) {
			documentCheckLists = new ArrayList<DocumentCheckListDataM>();
		}
		documentCheckLists.add(docM);
	}	
/*	public void addDocumentRelation(String applicantType, DocumentRelationDataM relationM) {
		if(documentRelations == null) {
			documentRelations = new HashMap<String,ArrayList<DocumentRelationDataM>>();
		}
		ArrayList<DocumentRelationDataM> relations = documentRelations.get(applicantType);
		if(relations == null) {
			relations = new ArrayList<DocumentRelationDataM>();
			documentRelations.put(applicantType, relations);
		}
		relations.add(relationM);
	}*/
/*	public void addDocumentRelation(String applicantType, ArrayList<DocumentRelationDataM> relationList) {
		if(documentRelations == null) {
			documentRelations = new HashMap<String,ArrayList<DocumentRelationDataM>>();
		}
		ArrayList<DocumentRelationDataM> relations = documentRelations.get(applicantType);
		if(relations == null) {
			documentRelations.put(applicantType, relationList);
		} else {
			relations.addAll(relationList);
		}
	}*/
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRandomNo1() {
		return randomNo1;
	}
	public void setRandomNo1(int randomNo1) {
		this.randomNo1 = randomNo1;
	}	
	public boolean matchesPersonalType(String personalType){
		if(null != personalInfos){
			int allPersonalSize = personalInfos.size();
			int personalSize = 0;
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if(personalType.equals(personalInfo.getPersonalType())){
					personalSize++;
				}
			}
			if(personalSize != 0 && personalSize == allPersonalSize){
				return true;
			}
		}
		return false;
	}
	public boolean matchesCardLinkByproduct(){	
		if(null != applications){
		 	for(ApplicationDataM application :applications){
		 		String product = application.getProduct();
		 		if(ApplicationDataM.PRODUCT.PRODUCT_CRADIT_CARD.equals(product) || ApplicationDataM.PRODUCT.PRODUCT_K_EXPRESS_CASH.equals(product)){
//		 			if(ApplicationDataM.STATUS.APPLICATION_STATUS_APPROVE.equals(applicationStatus)){
//		 				return true;
//		 			}
		 			if(ApplicationDataM.STATUS.APPLICATION_STATUS_APPROVE.equals(applicationStatus) || ApplicationDataM.STATUS.APPLICATION_STATUS_APPROVED.equals(applicationStatus) || ApplicationDataM.STATUS.APPLICATION_STATUS_POST_APPROVE.equals(applicationStatus)){
		 				return true;
		 			}
		 		}
		 	}
		 }
		
		return false;
	}
	public ArrayList<PersonalInfoDataM> getUsingPersonalInfo(){
		ArrayList<PersonalInfoDataM> personalInfoList = new ArrayList<PersonalInfoDataM>();
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if(!PersonalInfoDataM.PersonalType.INFO.equals(personalInfo.getPersonalType())){
					personalInfoList.add(personalInfo);
				}
			}
		}
		return personalInfoList;
	}
	public PersonalInfoDataM  getIMPersonal(String filterPersonalType,int perSeq){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				String personalType =personalInfo.getPersonalType();
				int seq =personalInfo.getSeq();
				if(!PersonalInfoDataM.PersonalType.INFO.equals(personalType) && filterPersonalType.equals(personalType) && perSeq==seq){
					return personalInfo;
				}
			}
		}
		return null;
	}
	
	public PersonalInfoDataM  getPersonalInfoPersonalTypeSeq(String personalType,int seq){
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if(personalType.equals(personalInfo.getPersonalType()) && seq ==personalInfo.getSeq()){
					return personalInfo;
				}
			}
		}
		return null;
	}
	
	public String getDocCompletedFlag(){	
		ArrayList<PersonalInfoDataM> personalInfoList = getUsingPersonalInfo();
		if(null != personalInfoList){
			int size = personalInfoList.size();
			int complateSize = 0;
			for (PersonalInfoDataM personalInfo : personalInfoList) {
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if(null != verificationResult){
					if("Y".equals(verificationResult.getDocCompletedFlag())){
						complateSize++;
					}
				}
			}
			if(size == complateSize){
				return "Y";
			}
		}
		return "N";
	}
	public boolean isSupplementaryApplicant() {
		return supplementaryApplicant;
	}
	public void setSupplementaryApplicant(boolean supplementaryApplicant) {
		this.supplementaryApplicant = supplementaryApplicant;
	}
	@Override
	public int compare(Object object1, Object object2) {
		int compareNum=0;
		if(SORT_TYPE.DESC.equals(sortType)){
	    	 compareNum = compareObjectValue(object2,object1);
	    }else{
	    	compareNum = compareObjectValue(object1,object2);
	    }			   
        if (compareNum != 0) return compareNum;
        return compareNum;
		
	}
	private  int compareObjectValue(Object obj1 ,Object obj2){ 
		if(null != obj1 && null !=  obj2 ){
			if(obj1 instanceof PersonalInfoDataM && obj2 instanceof PersonalInfoDataM){
				PersonalInfoDataM personalInfo1 = (PersonalInfoDataM)obj1;
				PersonalInfoDataM personalInfo2 = (PersonalInfoDataM)obj2;
				return personalInfo1.getPersonalType().compareTo(personalInfo2.getPersonalType());		
			}else if(obj1 instanceof ORPolicyRulesDataM && obj2 instanceof ORPolicyRulesDataM){
				ORPolicyRulesDataM orPolicyrulesdata1 = (ORPolicyRulesDataM)obj1;
				ORPolicyRulesDataM orPolicyrulesdata2 = (ORPolicyRulesDataM)obj2;
				BigDecimal  poricyCode1 =  new BigDecimal(orPolicyrulesdata1.getPolicyCode());
				BigDecimal  poricyCode2 = new BigDecimal(orPolicyrulesdata2.getPolicyCode());
				return  poricyCode1.compareTo(poricyCode2);
			}else if(obj1 instanceof ORPolicyRulesDetailDataM && obj2 instanceof ORPolicyRulesDetailDataM){
				ORPolicyRulesDetailDataM oRPolicyRulesDetail1 = (ORPolicyRulesDetailDataM)obj1;
				ORPolicyRulesDetailDataM oRPolicyRulesDetail2 = (ORPolicyRulesDetailDataM)obj2;
				Integer orRank1 = new Integer(oRPolicyRulesDetail1.getRank());
				Integer orRank2 = new Integer(oRPolicyRulesDetail2.getRank());
				return  orRank1.compareTo(orRank2);
			}
		}
		return 0;
	}
	public ArrayList<String> getIdNos(){
		ArrayList<String> idNos = new ArrayList<String>();
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				if(null != personalInfo.getIdno()&& !"".equals(personalInfo.getIdno())){
					idNos.add(personalInfo.getIdno());
				}
			}
		}
		return idNos;
	}
	
	/**
	 * An active application is application that runs on top of other applications.<br/>
	 * On the other hand, the applications of which have max life cycle are the active ones.
	 * @return active application list
	 */
	public boolean foundProduct(String productCode){
		ArrayList<ApplicationDataM> applicationLifeCycles = filterApplicationLifeCycle(productCode);
		return (null!=applicationLifeCycles&&applicationLifeCycles.size()>0);
	}
	
	public List<ApplicationDataM> getActiveApplications(){
		//Start validation & data preparation
		if(applications == null || applications.isEmpty())return null;
		int maxLifeCycle = getMaxLifeCycle();
		List<ApplicationDataM> maxLifeCycleApp = new ArrayList<ApplicationDataM>();		
		//Start logic
		for(int i = 0, size = applications.size(); i < size; i++){
			ApplicationDataM currentApp = applications.get(i);
			if(currentApp == null)continue;
			if(maxLifeCycle == currentApp.getLifeCycle()){
				maxLifeCycleApp.add(currentApp);//Active app here
			}
		}
		return maxLifeCycleApp;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getFraudFlag() {
		return fraudFlag;
	}
	public void setFraudFlag(String fraudFlag){
		this.fraudFlag = fraudFlag;
	}
	public String getFraudDecision() {
		return fraudDecision;
	}
	public void setFraudDecision(String fraudDecision) {
		this.fraudDecision = fraudDecision;
	}
	public String getFraudActionType() {
		return fraudActionType;
	}
	public void setFraudActionType(String fraudActionType) {
		this.fraudActionType = fraudActionType;
	}
	public int getRandomNo2() {
		return randomNo2;
	}
	public void setRandomNo2(int randomNo2) {
		this.randomNo2 = randomNo2;
	}
	public int getRandomNo3() {
		return randomNo3;
	}
	public void setRandomNo3(int randomNo3) {
		this.randomNo3 = randomNo3;
	}
	public void addReferencePerson(ReferencePersonDataM referencePerson) {
		if(null == referencePersons){
			referencePersons = new ArrayList<ReferencePersonDataM>(); 
		}
		if(null != referencePerson){
			referencePersons.add(referencePerson);
		}
	}
	 
	public String getFraudApplicantFlag() {
		return fraudApplicantFlag;
	}
	public void setFraudApplicantFlag(String fraudApplicantFlag) {
		this.fraudApplicantFlag = fraudApplicantFlag;
	}
	public String getFraudCompanyFlag() {
		return fraudCompanyFlag;
	}
	public void setFraudCompanyFlag(String fraudCompanyFlag) {
		this.fraudCompanyFlag = fraudCompanyFlag;
	}
	public ArrayList<AuditTrailDataM> getAuditTrailLogs() {
		return auditTrailLogs;
	}
	public void setAuditTrailLogs(ArrayList<AuditTrailDataM> auditTrailLogs) {
		this.auditTrailLogs = auditTrailLogs;
	}	
	public HistoryDataM getHistoryData() {
		return historyData;
	}
	public void setHistoryData(HistoryDataM historyData) {
		this.historyData = historyData;
	}
	public boolean isVeto() {
		return (getMaxLifeCycle()>1);
	}
	public String getFlipType() {
		return flipType;
	}
	public void setFlipType(String flipType) {
		this.flipType = flipType;
	}
/*	public HashMap<String, ArrayList<DocumentRelationDataM>> getDocumentRelations() {
		return documentRelations;
	}
	public void setDocumentRelations(
			HashMap<String, ArrayList<DocumentRelationDataM>> documentRelations) {
		this.documentRelations = documentRelations;
	}*/
	
	public String getBlockedFlag() {
		return blockedFlag;
	}
	public void setBlockedFlag(String blockedFlag) {
		this.blockedFlag = blockedFlag;
	}	
	public String getAlreadyFollowFlag() {
		return alreadyFollowFlag;
	}
	public void setAlreadyFollowFlag(String alreadyFollowFlag) {
		this.alreadyFollowFlag = alreadyFollowFlag;
	}
/*	public DocumentRelationDataM getDocumentRelation(String docCheckListId,
			String refId, String refLevel) {
		if(documentRelations != null && !documentRelations.isEmpty() && null != docCheckListId) {
			for(ArrayList<DocumentRelationDataM> docRelationList: documentRelations.values()) {
				if(docRelationList != null && !docRelationList.isEmpty()) {
					for(DocumentRelationDataM docRelationM : docRelationList) {
						if(docRelationM.getDocCheckListId() != null && docRelationM.getDocCheckListId().equals(docCheckListId) 
						&& refId != null && refId.equals(docRelationM.getRefId()) 
						&& refLevel != null && refLevel.equals(docRelationM.getRefLevel())) {
							return docRelationM;
						}
					}
				}
			}
		}
		return null;
	}
	public ArrayList<String> getDocumentRelationDocCheckListIds(String refId, String refLevel) {
		ArrayList<String> documentCheckListIds = new ArrayList<String>();
		if(documentRelations != null && !documentRelations.isEmpty() && null != refId) {
			for(ArrayList<DocumentRelationDataM> docRelationList: documentRelations.values()) {
				if(docRelationList != null && !docRelationList.isEmpty()) {
					for(DocumentRelationDataM docRelationM : docRelationList) {
						if(docRelationM.getRefId() != null && docRelationM.getRefId().equals(refId) 
							&& refLevel != null && refLevel.equals(docRelationM.getRefLevel())) {
							documentCheckListIds.add(docRelationM.getDocCheckListId());
						}
					}
				}
			}
		}
		return documentCheckListIds;
	}*/	
	public ArrayList<String> getFinalDecisionLifeCycle() {
		ArrayList<String> finalAppDicisionList = new ArrayList<String>();
		int maxLifeCycle = getMaxLifeCycle();
		if(applications != null && !applications.isEmpty()) {
			for(ApplicationDataM  applicationDataM : applications) {
				String finalDecision = applicationDataM.getFinalAppDecision();
				if(maxLifeCycle == applicationDataM.getLifeCycle() && !finalAppDicisionList.contains(finalDecision) && null!=finalDecision){
					finalAppDicisionList.add(finalDecision);
				}
			}
		}
		return finalAppDicisionList;
	}
	public ArrayList<String> getVetoEligibleFlagLifeCycle() {
		ArrayList<String> vetoEligibleFlags = new ArrayList<String>();
		int maxLifeCycle = getMaxLifeCycle();
		if(applications != null && !applications.isEmpty()) {
			for(ApplicationDataM  applicationDataM : applications) {
				String vetoEligibleFlag = applicationDataM.getIsVetoEligibleFlag();
				if(maxLifeCycle == applicationDataM.getLifeCycle() && !vetoEligibleFlags.contains(vetoEligibleFlag) && null!=vetoEligibleFlag){
					vetoEligibleFlags.add(vetoEligibleFlag);
				}
			}
		}
		return vetoEligibleFlags;
	}
	public String getDocumentSLAType() {
		return documentSLAType;
	}
	public void setDocumentSLAType(String documentSLAType) {
		this.documentSLAType = documentSLAType;
	}
	public String getCallOperator() {
		return callOperator;
	}
	public void setCallOperator(String callOperator) {
		this.callOperator = callOperator;
	}
	public ArrayList<PaymentMethodDataM> getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(ArrayList<PaymentMethodDataM> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public ArrayList<SpecialAdditionalServiceDataM> getSpecialAdditionalServices() {
		return specialAdditionalServices;
	}
	public void setSpecialAdditionalServices(ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices) {
		this.specialAdditionalServices = specialAdditionalServices;
	}
	public PaymentMethodDataM getPaymentMethodLifeCycleByPersonalType(String personalType,String product){
		String personalId = getPersonalId(personalType);
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != paymentMethods){
					String paymentMethodId = loan.getPaymentMethodId();
					for (PaymentMethodDataM paymentMethod : paymentMethods) {
						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
							return paymentMethod;
						}
					}
				}
			}
		}
		return null;
	}
	public void addPaymentMethod(PaymentMethodDataM paymentMethod){
		if(null == paymentMethods){
			this.paymentMethods = new ArrayList<PaymentMethodDataM>();
		}
		this.paymentMethods.add(paymentMethod);
	}
//	public PaymentMethodDataM getPaymentMethodLifeCycle(String personalId,String product){
//		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
//		if(null != filterApplications && filterApplications.size() > 0){
//			for(ApplicationDataM application : filterApplications){
//				LoanDataM loan = application.getLoan();
//				if(null != loan && null != paymentMethods){
//					String paymentMethodId = loan.getPaymentMethodId();
//					for (PaymentMethodDataM paymentMethod : paymentMethods) {
//						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
//							return paymentMethod;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
	public PaymentMethodDataM getPaymentMethodLifeCycleByPersonalId(String personalId,String product){
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != paymentMethods){
					String paymentMethodId = loan.getPaymentMethodId();
					for (PaymentMethodDataM paymentMethod : paymentMethods) {
						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
							return paymentMethod;
						}
					}
				}
			}
		}
		return null;
	}
	public ArrayList<PaymentMethodDataM> getPaymentMethodLifeCycle(){
		ArrayList<PaymentMethodDataM> filterPaymentMethods = new ArrayList<PaymentMethodDataM>();
		ArrayList<ApplicationDataM> filterApplications = filterApplicationLifeCycle();
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != paymentMethods){
					String paymentMethodId = loan.getPaymentMethodId();
					for (PaymentMethodDataM paymentMethod : paymentMethods) {
						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())
								&& !containsPaymentMethod(paymentMethodId, filterPaymentMethods)){
							filterPaymentMethods.add(paymentMethod);
						}
					}
				}
			}
		}
		return filterPaymentMethods;
	}
	public PaymentMethodDataM getPaymentMethodById(String paymentMethodId){
		if(null != paymentMethods){
			for (PaymentMethodDataM paymentMethod : paymentMethods) {
				if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
					return paymentMethod;
				}
			}
		}
		return null;
	}
//	public PaymentMethodDataM getPaymentMethodLifeCycleByCardTypeOfPersonalId(String personalId,String product,String cardType){
//		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycleCardType(personalId,product,cardType);
//		if(null != filterApplications && filterApplications.size() > 0){
//			for(ApplicationDataM application : filterApplications){
//				LoanDataM loan = application.getLoan();
//				if(null != loan && null != paymentMethods){
//					String paymentMethodId = loan.getPaymentMethodId();
//					for (PaymentMethodDataM paymentMethod : paymentMethods) {
//						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
//							return paymentMethod;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
	public String getPersonalId(String personalType){
		PersonalInfoDataM personalInfo = getPersonalInfo(personalType);		
		return (null != personalInfo)?personalInfo.getPersonalId():null;
	}
	public ArrayList<ApplicationDataM> filterApplicationRelationLifeCycle(String personalId,String product) {
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
					String applicationRecordId = application.getApplicationRecordId();
					PersonalRelationDataM personalRelation = getPersonalRelationByRefId(applicationRecordId, personalId);
					if(null != personalRelation && personalRelation.getRefId() != null && personalRelation.getRefId().equals(applicationRecordId)){
						filterApplications.add(application);
					}
				}
			}
		}
		return filterApplications;
	}
	public ArrayList<ApplicationDataM> filterApplicationRelationLifeCycleCardType(String personalId,String product,String cardType) {
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct()) && maxLifeCycle==application.getLifeCycle()) {
					String applicationRecordId = application.getApplicationRecordId();
					PersonalRelationDataM personalRelation = getPersonalRelationByRefId(applicationRecordId, personalId);
					if(null != personalRelation && personalRelation.getRefId() != null && personalRelation.getRefId().equals(applicationRecordId)){
						CardDataM card = application.getCard();
						if(null != card && null != card.getApplicationType() && card.getApplicationType().equals(cardType)){
							filterApplications.add(application);
						}
					}
				}
			}
		}
		return filterApplications;
	}
	public ArrayList<DocumentRelationDataM> getAllDocumentRelations(){
		ArrayList<DocumentRelationDataM> filterDocumentRelations = new ArrayList<DocumentRelationDataM>();
		if(null!=documentCheckLists){
			for(DocumentCheckListDataM documentCheckList : documentCheckLists){
				ArrayList<DocumentRelationDataM>  docRelations =documentCheckList.getDocumentRelations();
				if(null!=docRelations && docRelations.size()>0){
					filterDocumentRelations.addAll(docRelations);
				}
			}
		}
		return filterDocumentRelations;
	}
	public SpecialAdditionalServiceDataM getSpecialAdditionalService(ArrayList<String> specialAdditionalServiceIds,String serviceType){
		if(null != specialAdditionalServices){
			for(SpecialAdditionalServiceDataM specialAdditionalService : specialAdditionalServices){
				if(null != specialAdditionalService){
				String serviceId = specialAdditionalService.getServiceId();
					if(specialAdditionalServiceIds.contains(serviceId)){
						if(null != serviceType && serviceType.equals(specialAdditionalService.getServiceType())){
							return specialAdditionalService;
						}
					}
				}
			}
		}
		return null;
	}
	public SpecialAdditionalServiceDataM getSpecialAdditionalService(String specialAdditionalServiceId){
		if(null != specialAdditionalServices){
			for(SpecialAdditionalServiceDataM specialAdditionalService : specialAdditionalServices){
				if(null != specialAdditionalServiceId && specialAdditionalServiceId.equals(specialAdditionalService.getServiceId())){
					return specialAdditionalService;
				}
			}
		}
		return null;
	}
	public ArrayList<SpecialAdditionalServiceDataM> getSpecialAdditionalServiceLoan(String loanId){
		ArrayList<SpecialAdditionalServiceDataM> specialAdditionals = new ArrayList<SpecialAdditionalServiceDataM>();
		if(null != specialAdditionalServices && null!=loanId){
			for(SpecialAdditionalServiceDataM specialAdditionalService : specialAdditionalServices){
				ArrayList<SpecialAdditionalServiceMapDataM> specialAdditionalMaps =specialAdditionalService.getSpecialAdditionalServiceMaps();
				if(null!=specialAdditionalMaps){
					for(SpecialAdditionalServiceMapDataM specialAdditinalMap : specialAdditionalMaps){
						if(loanId.equals(specialAdditinalMap.getLoanId())){
							specialAdditionals.add(specialAdditionalService);
							break;
						}
					}
				}
			}
		}
		return specialAdditionals;
	}
	public SpecialAdditionalServiceDataM getSpecialAdditionalServiceLifeCycleByPersonalId(String personalId, String product, String serviceType){
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					ArrayList<String> specialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
					if(null != specialAdditionalServiceIds){
						return getSpecialAdditionalService(specialAdditionalServiceIds,serviceType);
					}
				}
			}
		}
		return null;
	}
	public ArrayList<SpecialAdditionalServiceDataM> getSpecialAdditionalServiceLifeCycleByPersonalId(String personalId, String product){
		ArrayList<SpecialAdditionalServiceDataM> filterSpecialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					ArrayList<String> specialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
					if(null != specialAdditionalServiceIds){
						for(String specialAdditionalServiceId : specialAdditionalServiceIds){
							if(!containsSpecialAdditionalService(specialAdditionalServiceId, filterSpecialAdditionalServices)){
								SpecialAdditionalServiceDataM specialAdditionalService = getSpecialAdditionalService(specialAdditionalServiceId);
								if(null != specialAdditionalService){
									filterSpecialAdditionalServices.add(specialAdditionalService);
								}
							}
						}
					}
				}
			}
		}
		return filterSpecialAdditionalServices;
	}
	public ArrayList<SpecialAdditionalServiceDataM> getSpecialAdditionalServiceLifeCycleByPersonalId(String personalId){
		ArrayList<SpecialAdditionalServiceDataM> filterSpecialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
		ArrayList<String> serviceIds = getServiceIdLastLifeCycle();
		if(null != specialAdditionalServices){
			for (SpecialAdditionalServiceDataM specialAdditionalService : filterSpecialAdditionalServices) {
				String serviceId = specialAdditionalService.getServiceId();
				if(null != personalId && personalId.equals(specialAdditionalService.getPersonalId())
						&& !serviceIds.contains(serviceId)){
					filterSpecialAdditionalServices.add(specialAdditionalService);
				}
			}
		}
		return filterSpecialAdditionalServices;
	}
	
	public ArrayList<String> getServiceIdLastLifeCycle(){
		int lifeCycle = getMaxLifeCycle();
		ArrayList<String> serviceIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				if(application.getLifeCycle() != lifeCycle ){
					LoanDataM loan = application.getLoan();					
					if(null != loan){
						if(null != loan.getSpecialAdditionalServiceIds()){
							serviceIds.addAll(loan.getSpecialAdditionalServiceIds());
						}
					}
				}				
			}
		}
		return serviceIds;
	}
	public void removeSpecialAdditionalLifeCycleByPersonalId(String personalId,String product,String serviceType){
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId,product);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					ArrayList<String> specialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
					if(null != specialAdditionalServiceIds){
						SpecialAdditionalServiceDataM specialAdditionalService =  getSpecialAdditionalService(specialAdditionalServiceIds,serviceType);
						if(null != specialAdditionalService){
							String serviceId = specialAdditionalService.getServiceId();
							if(null != specialAdditionalServices){
								for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
									SpecialAdditionalServiceDataM specialAdditionalServiceM = iterator.next();
									if(null != specialAdditionalServiceM && null != specialAdditionalServiceM.getServiceId() 
											&& specialAdditionalServiceM.getServiceId().equals(serviceId)){
										iterator.remove();
									}
								}
							}
						}
					}
				}
			}
		}
	}	
	public ArrayList<PaymentMethodDataM> getListPaymentMethodLifeCycleByPersonalId(String personalId){
		ArrayList<PaymentMethodDataM> filterPaymentMethods = new ArrayList<PaymentMethodDataM>();
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != paymentMethods){
					String paymentMethodId = loan.getPaymentMethodId();
					for (PaymentMethodDataM paymentMethod : paymentMethods) {
						if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())
								&& !containsPaymentMethod(paymentMethodId, filterPaymentMethods)){
							filterPaymentMethods.add(paymentMethod);
						}
					}
				}
			}
		}
		return filterPaymentMethods;
	}
	public ArrayList<PaymentMethodDataM> getListPaymentMethodByPersonalId(String personalId){
		ArrayList<PaymentMethodDataM> filterPaymentMethods = new ArrayList<PaymentMethodDataM>();
		if(null != paymentMethods){
			for(PaymentMethodDataM paymentMethod:paymentMethods) {
				String paymentMethodId = paymentMethod.getPaymentMethodId();
				if(null != personalId && personalId.equals(paymentMethod.getPersonalId())
						&& !containsPaymentMethod(paymentMethodId, filterPaymentMethods)){
					filterPaymentMethods.add(paymentMethod);
				}
			}
		}
		return filterPaymentMethods;
	}
	public boolean containsPaymentMethod(String paymentMethodId,ArrayList<PaymentMethodDataM> filterPaymentMethods){
		if(null != filterPaymentMethods){
			for (PaymentMethodDataM paymentMethod : filterPaymentMethods) {
				if(null != paymentMethodId && paymentMethodId.equals(paymentMethod.getPaymentMethodId())){
					return true;
				}
			}
		}
		return false;
	}
	public ArrayList<ApplicationDataM> filterApplicationRelationLifeCycle(String personalId) {
		ArrayList<ApplicationDataM> filterApplications = new ArrayList<ApplicationDataM>();
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (maxLifeCycle==application.getLifeCycle()) {
					String applicationRecordId = application.getApplicationRecordId();
					PersonalRelationDataM personalRelation = getPersonalRelationByRefId(applicationRecordId, personalId);
					if(null != personalRelation && personalRelation.getRefId() != null && personalRelation.getRefId().equals(applicationRecordId)){
						filterApplications.add(application);
					}
				}
			}
		}
		return filterApplications;
	}
	public ApplicationDataM getApplicationRelationLifeCycleByProduct(String personalId,String product) {
		int maxLifeCycle = getMaxLifeCycle() ;
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (maxLifeCycle==application.getLifeCycle()) {
					String applicationRecordId = application.getApplicationRecordId();
					PersonalRelationDataM personalRelation = getPersonalRelationByRefId(applicationRecordId, personalId);
					if(null != personalRelation && personalRelation.getRefId() != null && personalRelation.getRefId().equals(applicationRecordId)){
						if(null != product && product.equals(application.getProduct())){
							return application;
						}
					}
				}
			}
		}
		return null;
	}
	public ArrayList<SpecialAdditionalServiceDataM> getListSpecialAdditionalServiceLifeCycleByPersonalId(String personalId){
		ArrayList<SpecialAdditionalServiceDataM> filterSpecialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
		ArrayList<ApplicationDataM> filterApplications = filterApplicationRelationLifeCycle(personalId);
		if(null != filterApplications && filterApplications.size() > 0){
			for(ApplicationDataM application : filterApplications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					ArrayList<String> specialAdditionalServiceIds = loan.getSpecialAdditionalServiceIds();
					if(null != specialAdditionalServiceIds){
						for(String specialAdditionalServiceId : specialAdditionalServiceIds){
							if(!containsSpecialAdditionalService(specialAdditionalServiceId, filterSpecialAdditionalServices)){
								SpecialAdditionalServiceDataM specialAdditionalService = getSpecialAdditionalService(specialAdditionalServiceId);
								if(null != specialAdditionalService){
									filterSpecialAdditionalServices.add(specialAdditionalService);
								}
							}
						}
					}
				}
			}
		}
		return filterSpecialAdditionalServices;
	}
	
	public ArrayList<SpecialAdditionalServiceDataM> getListSpecialAdditionalServiceByPersonalId(String personalId){
		ArrayList<SpecialAdditionalServiceDataM> filterSpecialAdditionalServices = new ArrayList<SpecialAdditionalServiceDataM>();
		if(null != specialAdditionalServices){
			for (SpecialAdditionalServiceDataM specialAdditionalService : specialAdditionalServices) {	
//				System.out.println("specialAdditionalService >> "+specialAdditionalService);				
				if(null != personalId && null != specialAdditionalService && personalId.equals(specialAdditionalService.getPersonalId())){
					String specialAdditionalServiceId = specialAdditionalService.getServiceId();
					if(!containsSpecialAdditionalService(specialAdditionalServiceId, filterSpecialAdditionalServices)){
						SpecialAdditionalServiceDataM specialAdditionalServiceM = getSpecialAdditionalService(specialAdditionalServiceId);
						if(null != specialAdditionalServiceM){
							filterSpecialAdditionalServices.add(specialAdditionalServiceM);
						}
					}
				}
			}
		}
		return filterSpecialAdditionalServices;
	}
	
	public boolean containsSpecialAdditionalService(String specialAdditionalServiceId,ArrayList<SpecialAdditionalServiceDataM> filterSpecialAdditionalServices){
		if(null != filterSpecialAdditionalServices){
			for (SpecialAdditionalServiceDataM specialAdditionalService : filterSpecialAdditionalServices) {
				if(null != specialAdditionalServiceId && specialAdditionalServiceId.equals(specialAdditionalService.getServiceId())){
					return true;
				}
			}
		}
		return false;
	}
	public String getDocumentCategory(String documentCode){
		 if(null!=applicationImages && null!=documentCode && !"".equals(documentCode)){
			 for(ApplicationImageDataM  applicationImageData : applicationImages){
				 ArrayList<ApplicationImageSplitDataM> applicationImageSplits  = applicationImageData.getApplicationImageSplits();
				 if(null!=applicationImageSplits){
					 for(ApplicationImageSplitDataM applicationImageSplit : applicationImageSplits){
						 String docType = applicationImageSplit.getDocType();
						 String docGroup = applicationImageSplit.getDocTypeGroup();
						 if(null!= docType && !"".equals(docType) && null!=docGroup && !"".equals(docGroup)&& documentCode.equals(docType) ){
							 return docGroup;
						 }
					 }
				 }
			 }
		 }
		return "";
	}
	public PaymentMethodDataM getPaymentMethodLifeCycleByPersonalId(String personalId){
		ArrayList<String> paymentMethodIds = getPaymentMethodIdLastLifeCycle();
		if(null != paymentMethods){
			for (PaymentMethodDataM paymentMethod : paymentMethods) {
				if(null != personalId && personalId.equals(paymentMethod.getPersonalId())){
					if(!paymentMethodIds.contains(paymentMethod.getPaymentMethodId())){
						return paymentMethod;
					}
				}
			}
		}
		return null;
	}
	public ArrayList<String> getPaymentMethodIds(){
		ArrayList<String> paymentMethodIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					paymentMethodIds.add(loan.getPaymentMethodId());
				}
			}
		}
		return paymentMethodIds;
	}
	public ArrayList<String> getSpecialAdditionalServiceIds(){
		ArrayList<String> specialAdditionalServiceIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				LoanDataM loan = application.getLoan();
				if(null != loan && null != loan.getSpecialAdditionalServiceIds()){
					specialAdditionalServiceIds.addAll( loan.getSpecialAdditionalServiceIds());
				}
			}
		}
		return specialAdditionalServiceIds;
	}	
	public ArrayList<String> getPaymentMethodIdLastLifeCycle(){
		int lifeCycle = getMaxLifeCycle();
		ArrayList<String> paymentMethodIds = new ArrayList<String>();
		if(null != applications){
			for(ApplicationDataM application : applications){
				if(application.getLifeCycle() != lifeCycle ){
					LoanDataM loan = application.getLoan();
					if(null != loan){
						paymentMethodIds.add(loan.getPaymentMethodId());
					}
				}				
			}
		}
		return paymentMethodIds;
	}
	public AddressDataM getAddressByAddressId(String addressId){
		 
		if(null != personalInfos){
			for(PersonalInfoDataM personalInfo : personalInfos){
				ArrayList<AddressDataM> addresses   = personalInfo.getAddresses();
				if(null!=addresses){
					for(AddressDataM addrs :addresses){
						if(addressId.equals(addrs.getAddressId())){
							return addrs;
						}
					}
				}				
			}
		}
		return null;
	}
	
	public PersonalInfoDataM getPersonalInfoApplication(String refId ,String relationLevel){
		if(null != personalInfos &&  null!=applications){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				PersonalRelationDataM personalRelation = personalInfo.getPersonalRelation(refId,relationLevel);
				if(null != personalRelation){
					 return getPersonalById(personalRelation.getPersonalId());
				}
			}
		}
		return null;
	}
	
	public boolean isClearCompareData() {
		return clearCompareData;
	}
	public void setClearCompareData(boolean clearCompareData) {
		this.clearCompareData = clearCompareData;
	}
	public ReasonDataM getReason() {
		return reason;
	}
	public void setReason(ReasonDataM reason) {
		this.reason = reason;
	}
	
	public boolean existSrcOfDataNotRemove(String srcOfData){
		if(null != comparisonGroups){
			for (ComparisonGroupDataM comparisonGroup: comparisonGroups) {
				if(null != comparisonGroup && comparisonGroup.getSrcOfData().equals(srcOfData)){
					if(null!=personalInfos){
						System.out.println("personalInfos-"+personalInfos);
						
						
						for(PersonalInfoDataM personalInfo:personalInfos){
							System.out.println("getPersonalType-"+personalInfo.getPersonalType());
							if(!"I".equals(personalInfo.getPersonalType())){
								String uniqueId=personalInfo.getPersonalId();
								if(null!=uniqueId && null != comparisonGroup.getComparisonFields() && comparisonGroup.getComparisonFields().size()>0){
									Collection<CompareDataM> comparisonFields = comparisonGroup.getComparisonFields().values();
									if(null != comparisonFields){
										for (CompareDataM compare : comparisonFields) {
											if(null != compare && null != compare.getUniqueId() && compare.getUniqueId().equals(uniqueId)){
												return true;
											}
										}
									}
								}
							}
							
						}
					}
					
				}
			}
		}
		return false;
	}
	
	public boolean existSrcOfData(String srcOfData){
		if(null != comparisonGroups){
			for (ComparisonGroupDataM comparisonGroup: comparisonGroups) {
				if(null != comparisonGroup && comparisonGroup.getSrcOfData().equals(srcOfData)){
					if(null != comparisonGroup.getComparisonFields() && comparisonGroup.getComparisonFields().size()>0){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean existSrcOfData(String srcOfData,String uniqueId){
		if(null != comparisonGroups){
			for (ComparisonGroupDataM comparisonGroup: comparisonGroups) {
				if(null != comparisonGroup && comparisonGroup.getSrcOfData().equals(srcOfData)){
					if(null != comparisonGroup.getComparisonFields() && comparisonGroup.getComparisonFields().size()>0){
						Collection<CompareDataM> comparisonFields = comparisonGroup.getComparisonFields().values();
						if(null != comparisonFields){
							for (CompareDataM compare : comparisonFields) {
								if(null != compare && null != compare.getUniqueId() && compare.getUniqueId().equals(uniqueId)){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public String getDocumentChecklistFlag(){		
		return (null == documentCheckLists || documentCheckLists.size() > 0)?"Y":"N";
	}
	public ArrayList<FinalVerifyResultDataM> getFinalVerifys() {
		return finalVerifys;
	}
	public void setFinalVerifys(ArrayList<FinalVerifyResultDataM> finalVerifys) {
		this.finalVerifys = finalVerifys;
	}
	public boolean getRequiredDocFlag(){
		if(null != personalInfos &&  null!=applications){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if(null != verificationResult){
					if(null != verificationResult.getRequiredDocs() && verificationResult.getRequiredDocs().size() > 0){
						return true;
					}
				}
			}
		}
		return false;
	}
	public String getWebScanUser() {
		return webScanUser;
	}
	public void setWebScanUser(String webScanUser) {
		this.webScanUser = webScanUser;
	}	
	
	public ArrayList<CashTransferDataM> getCashTransfers(){
		ArrayList<CashTransferDataM> cashTransfers = new ArrayList<CashTransferDataM>();
		if(null != applications){
			for (ApplicationDataM application : applications) {
				LoanDataM loan = application.getLoan();
				if(null != loan && null != loan.getCashTransfers()){
					cashTransfers.addAll(loan.getCashTransfers());
				}
			}
		}
		return cashTransfers;
	}
	public String getExecuteFlag() {
		return executeFlag;
	}
	public void setExecuteFlag(String executeFlag) {
		this.executeFlag = executeFlag;
	}
	public String getDecisionLog() {
		return decisionLog;
	}
	public void setDecisionLog(String decisionLog) {
		this.decisionLog = decisionLog;
	}	
	public boolean foundReExecuteKeyProgramFlag(String reExecuteKeyProgramFlag){
		int maxLifeCycle = getMaxLifeCycle();
		if(null != applications){
			for (ApplicationDataM application : applications) {
				if(maxLifeCycle==application.getLifeCycle()
						&&null!=reExecuteKeyProgramFlag&&reExecuteKeyProgramFlag.equals(application.getReExecuteKeyProgramFlag()))return true;
			}
		}
		return false;
	}
	public WisdomDataM findWisdom(String product){
		int maxLifeCycle = getMaxLifeCycle();
		if(null != applications){
			for (ApplicationDataM application : applications) {
				if(maxLifeCycle==application.getLifeCycle()&&null!=application.getProduct()&&application.getProduct().equals(product)){
					try{
						return application.getLoan().getCard().getWisdom();
					}catch(Exception e){						
					}
				}
			}
		}
		return null;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getWfState() {
		return wfState;
	}
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	private ArrayList<ApplicationLogDataM> getSortDateApplicationLog(){
		try{
			if(!ModelUtil.empty(this.applicationLogs)){
				Collections.sort(this.applicationLogs, new Comparator<ApplicationLogDataM>() {
					@Override
					public int compare(ApplicationLogDataM applicationLog1,ApplicationLogDataM applicationLog2) {
						Timestamp updateDate1 = applicationLog1.getUpdateDate();
						Timestamp updateDate2 = applicationLog2.getUpdateDate();
						 return updateDate1.compareTo(updateDate2);
					}
				});
			}
		}catch(Exception e){ }
		return this.applicationLogs;
	}
	public ArrayList<ApplicationLogDataM> getSortDateAscendingApplicationLog(){
		return getSortDateApplicationLog();
	}
	public ArrayList<ApplicationLogDataM> getSortDateDescendingApplicationLog(){
		try{
			Collections.reverse(getSortDateApplicationLog());
		}catch(Exception e){ }
		return this.applicationLogs;
	}
	public String getPegaEventFlag() {
		return pegaEventFlag;
	}
	public void setPegaEventFlag(String pegaEventFlag) {
		this.pegaEventFlag = pegaEventFlag;
	}
	public String getReprocessFlag() {
		return reprocessFlag;
	}
	public void setReprocessFlag(String reprocessFlag) {
		this.reprocessFlag = reprocessFlag;
	}
	public String getCallComplateTaskFlag() {
		return callCompleteTaskFlag;
	}
	public void setCallCompleteTaskFlag(String callCompleteTaskFlag) {
		this.callCompleteTaskFlag = callCompleteTaskFlag;
	}
	public ArrayList<ApplicationDataM> getSortLifeCycleApplication(){
		try{
			if(!ModelUtil.empty(this.applications)){
				Collections.sort(this.applications, new Comparator<ApplicationDataM>() {
					@Override
					public int compare(ApplicationDataM application1,ApplicationDataM application2) {
						int lifeCycle1 = application1.getLifeCycle();
						int lifeCycle2 = application2.getLifeCycle();
						 return Integer.valueOf(lifeCycle1).compareTo(Integer.valueOf(lifeCycle2));
					}
				});
			}
		}catch(Exception e){ }
		return this.applications;
	}
	public ArrayList<ApplicationDataM> getSortLifeCycleApplicationDesc(){
		try{
			if(!ModelUtil.empty(this.applications)){
				Collections.sort(this.applications, new Comparator<ApplicationDataM>() {
					@Override
					public int compare(ApplicationDataM application1,ApplicationDataM application2) {
						int lifeCycle1 = application1.getLifeCycle();
						int lifeCycle2 = application2.getLifeCycle();
						 return -Integer.valueOf(lifeCycle1).compareTo(Integer.valueOf(lifeCycle2));
					}
				});
			}
		}catch(Exception e){ }
		return this.applications;
	}
	// How to Search Product Factor
	public ArrayList<ProductFactor> getProductFactor(){
		
		// get application of application group
		ArrayList<ApplicationDataM> applicationTmpList = this.filterApplicationLifeCycle();
		ArrayList<ProductFactor> productFactor = new ArrayList<ProductFactor>();
		HashSet<String> hashMap = new HashSet<String>();
		if(!ModelUtil.empty(applicationTmpList)){
			// create group
			for(ApplicationDataM app : applicationTmpList){
				if(hashMap.contains(app.getProduct())) // has in arrayList
				{
					System.out.println("has arrayList :"+ app);
				}else{ // not has arrayList
					System.out.println("has not has :"+ app);
					ProductFactor instance = new ProductFactor();
					instance.Product = app.getProduct();
					instance.loans = new ArrayList<LoanDataM>();
					productFactor.add(instance);
					hashMap.add( app.getProduct());
				}
			}
		
			
			// specify member in group
			for(ApplicationDataM app : applicationTmpList){
				// productFactor not empty
				if(!ModelUtil.empty(productFactor)){
					// loop of productFactor 
					for(ProductFactor pro : productFactor){
						
						if(pro.Product.equals(app.getProduct())){ // has Product in productFactor arrayList
							// loan
							ArrayList<LoanDataM> loans = app.getLoans();
							if(!ModelUtil.empty(loans)){
								ArrayList<LoanDataM> loansObject = pro.loans;
								HashSet<BigDecimal> hashSet = new HashSet<BigDecimal>();
								for(LoanDataM loan:loansObject ){
									
									hashSet.add(loan.getProductFactor());
								}
								for(LoanDataM loan : loans){
									// data in hashSet
									System.out.println("product Factor"+ loan.getProductFactor());
									if(hashSet.contains(loan.getProductFactor())){
										continue;
									}// data no in hashSet
									else{
										// not have 0
										if(!ModelUtil.empty(loan.getProductFactor())){
											if (loan.getProductFactor().compareTo(BigDecimal.ZERO) != 0){
										
												pro.loans.add(loan);
												hashSet.add(loan.getProductFactor());
											}
										}
									}
									
								}
							}
							break;
						} else {// has not't Product in productFactor arrayList
							continue;
						}
						
					}
					
				}else{ // empty
					ProductFactor instance = new ProductFactor();
					instance.Product = app.getProduct();
					instance.loans = app.getLoans();
					productFactor.add(instance);
					
				}	
			

			}
			   
		}
		return productFactor;
	}
	
	public boolean chkCardDub(ApplicationDataM addApplication){
		CardDataM addCard = addApplication.getCard();
		for(ApplicationDataM application:applications){
			CardDataM card = application.getCard();
			if(card.getApplicationType()!=null&&card.getCardType()!=null){
				if(application.getLifeCycle()==addApplication.getLifeCycle()
						&&card.getApplicationType().equals(addCard.getApplicationType())
						&&card.getCardType().equals(addCard.getCardType())){
					return false;
				}
			}
		}
		return true;
	}
	public String getReCalculateActionFlag() {
		return reCalculateActionFlag;
	}
	public void setReCalculateActionFlag(String reCalculateActionFlag) {
		this.reCalculateActionFlag = reCalculateActionFlag;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean foundApplicationCardType(String findCardType){
		if(null != applications){
			for(ApplicationDataM application: applications){
				String cardType = application.getCard().getCardType();
				if(findCardType.equals(cardType)){
					return true;
				}
			}
		}
		return false;
	}
	public String getRequireVerify() {
		return requireVerify;
	}
	public void setRequireVerify(String requireVerify) {
		this.requireVerify = requireVerify;
	}
	public String getSourceAction() {
		return sourceAction;
	}
	public void setSourceAction(String sourceAction) {
		this.sourceAction = sourceAction;
	}
	public String getLhRefId() {
		return lhRefId;
	}
	public void setLhRefId(String lhRefId) {
		this.lhRefId = lhRefId;
	}
	public String getSourceUserId() {
		return sourceUserId;
	}
	public void setSourceUserId(String sourceUserId) {
		this.sourceUserId = sourceUserId;
	}
	public boolean foundRecommendDecisionByProduct(String product, String recommendDecision) {
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != product && product.equals(application.getProduct())
						&& null != application.getRecommendDecision() && application.getRecommendDecision().equals(recommendDecision)) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean foundRecommendDecision(String recommendDecision) {
		if (null != applications) {
			for (ApplicationDataM application : applications) {
				if (null != application.getRecommendDecision() && application.getRecommendDecision().equals(recommendDecision)) {
					return true;
				}
			}
		}
		return false;
	}
	public String foundLowIncome() {
		ArrayList<ApplicationDataM> listApplications = filterListApplicationProductLifeCycle(PRODUCT.PRODUCT_K_EXPRESS_CASH);
		if (null != listApplications) {
			for (ApplicationDataM application : listApplications) {
				if (null != application.getLowIncomeFlag() && "Y".equals(application.getLowIncomeFlag())) {
					return "Y";
				}
			}
		}
		return "N";
	}
	public ArrayList<DocumentCheckListDataM> getDocumentCheckListManuals() {
		return documentCheckListManuals;
	}
	public void setDocumentCheckListManuals(
			ArrayList<DocumentCheckListDataM> documentCheckListManuals) {
		this.documentCheckListManuals = documentCheckListManuals;
	}
	public ArrayList<DocumentCheckListDataM> getDocumentCheckListManualAdditionals() {
		return documentCheckListManualAdditionals;
	}
	public void setDocumentCheckListManualAdditionals(
			ArrayList<DocumentCheckListDataM> documentCheckListManualAdditionals) {
		this.documentCheckListManualAdditionals = documentCheckListManualAdditionals;
	}
}