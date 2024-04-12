package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class VerificationResultDataM implements Serializable,Cloneable {
	public VerificationResultDataM(){
		super();
	}
	public void init(String refId,String uniqueId){
		this.refId = refId;
		this.verResultId = uniqueId;
	}
//	private String applicationGroupId;	//XRULES_VERIFICATION_RESULT.APPLICATION_GROUP_ID(VARCHAR2)
//	private String personalId;	//XRULES_VERIFICATION_RESULT.PERSONAL_ID(VARCHAR2)
	private String verResultId;	//XRULES_VERIFICATION_RESULT.VER_RESULT_ID(VARCHAR2)
//	private String applicationRecordId;	//XRULES_VERIFICATION_RESULT.APPLICATION_RECORD_ID(VARCHAR2)
	private String refId; //XRULES_VERIFICATION_RESULT.REF_ID(VARCHAR2)
	private String docCompletedFlag;	//XRULES_VERIFICATION_RESULT.DOC_COMPLETED_FLAG(VARCHAR2)
	private String summaryIncomeResultCode;	//XRULES_VERIFICATION_RESULT.SUMMARY_INCOME_RESULT_CODE(VARCHAR2)
	private String verWebResult;	//XRULES_VERIFICATION_RESULT.VER_WEB_RESULT(VARCHAR2)
	private String requiredVerHrFlag;	//XRULES_VERIFICATION_RESULT.REQUIRED_VER_HR_FLAG(VARCHAR2)
	private String summaryIncomeResult;	//XRULES_VERIFICATION_RESULT.SUMMARY_INCOME_RESULT(VARCHAR2)
	private String verWebResultCode;	//XRULES_VERIFICATION_RESULT.VER_WEB_RESULT_CODE(VARCHAR2)
	private String verPrivilegeResultCode;	//XRULES_VERIFICATION_RESULT.VER_PRIVILEGE_RESULT_CODE(VARCHAR2)
	private String requiredVerCustFlag;	//XRULES_VERIFICATION_RESULT.REQUIRED_VER_CUST_FLAG(VARCHAR2)
	private String verCusResultCode;	//XRULES_VERIFICATION_RESULT.VER_CUS_RESULT_CODE(VARCHAR2)
	private String verHrResult;	//XRULES_VERIFICATION_RESULT.VER_HR_RESULT(VARCHAR2)
	private String verHrResultCode;	//XRULES_VERIFICATION_RESULT.VER_HR_RESULT_CODE(VARCHAR2)
	private String requiredVerWebFlag;	//XRULES_VERIFICATION_RESULT.REQUIRED_VER_WEB_FLAG(VARCHAR2)
	private String verPrivilegeResult;	//XRULES_VERIFICATION_RESULT.VER_PRIVILEGE_RESULT(VARCHAR2)
	private String requiredVerPrivilegeFlag;	//XRULES_VERIFICATION_RESULT.REQUIRED_VER_PRIVILEGE_FLAG(VARCHAR2)
	private String verCusResult;	//XRULES_VERIFICATION_RESULT.VER_CUS_RESULT(VARCHAR2)
	private String requiredVerIncomeFlag;	//XRULES_VERIFICATION_RESULT.REQUIRED_VER_INCOME_FLAG(VARCHAR2)
	private String verLevel;	//XRULES_VERIFICATION_RESULT.VER_LEVEL(VARCHAR2)
	private String createBy;	//XRULES_VERIFICATION_RESULT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//XRULES_VERIFICATION_RESULT.CREATE_DATE(DATE)
	private String updateBy;	//XRULES_VERIFICATION_RESULT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//XRULES_VERIFICATION_RESULT.UPDATE_DATE(DATE)
	private String busRegistVerResult; //XRULES_VERIFICATION_RESULT.BUS_REGIST_VER_RESULT(VARCHAR2)
	private String verCusComplete; //XRULES_VERIFICATION_RESULT.VER_CUS_COMPLETE(VARCHAR2)
	private String riskGradeCoarseCode; //XRULES_VERIFICATION_RESULT.RISK_GRADE_COARSE_CODE(VARCHAR2)
	private String riskGradeFineCode; //XRULES_VERIFICATION_RESULT.RISK_GRADE_FINE_CODE(VARCHAR2)
	private String compareSignatureResult; //XRULES_VERIFICATION_RESULT.COMPARE_SIGNATURE_RESULT(VARCHAR2)
	private String requirePriorityPass; //XRULES_VERIFICATION_RESULT.REQUIRED_PRIORITY_PASS(VARCHAR2)
	private String ficoFlag;// Used to check for FICO call in verify customer
	private String docFollowUpStatus;// DOC_FOLLOW_UP_STATUS
	
	private ArrayList<HRVerificationDataM> hrVerifications;
	private ArrayList<IdentifyQuestionDataM> indentifyQuestions;
	private ArrayList<PhoneVerificationDataM> phoneVerifications;
	private ArrayList<PolicyRulesDataM> policyRules;
	private ArrayList<WebVerificationDataM> webVerifications;
	private ArrayList<PrivilegeProjectCodeDataM> privilegeProjectCodes;
	private ArrayList<DocumentVerificationDataM> documentVerifications;
	private ArrayList<RequiredDocDataM> requiredDocs;
	private ArrayList<IdentifyQuestionSetDataM> indentifyQuesitionSets;
	private ArrayList<PriorityPassVerification> priorityPassVerifications;
	private ArrayList<IncomeCalculationDataM> incomeCalculations;
	private IncomeScreenDataM incomeScreens;
	private String aScoreCode; //XRULES_VERIFICATION_RESULT.A_SCORE_CODE (VARCHAR2)
	
//	public String getPersonalId() {
//		return personalId;
//	}
//	public void setPersonalId(String personalId) {
//		this.personalId = personalId;
//	}
		
	public String getVerPrivilegeResult() {
		return verPrivilegeResult;
	}
	public String getBusRegistVerResult() {
		return busRegistVerResult;
	}
	public void setBusRegistVerResult(String busRegistVerResult) {
		this.busRegistVerResult = busRegistVerResult;
	}
	public void setVerPrivilegeResult(String verPrivilegeResult) {
		this.verPrivilegeResult = verPrivilegeResult;
	}
	public String getRequiredVerPrivilegeFlag() {
		return requiredVerPrivilegeFlag;
	}
	public void setRequiredVerPrivilegeFlag(String requiredVerPrivilegeFlag) {
		this.requiredVerPrivilegeFlag = requiredVerPrivilegeFlag;
	}
	public String getVerWebResult() {
		return verWebResult;
	}
	public void setVerWebResult(String verWebResult) {
		this.verWebResult = verWebResult;
	}
	public String getSummaryIncomeResultCode() {
		return summaryIncomeResultCode;
	}
	public void setSummaryIncomeResultCode(String summaryIncomeResultCode) {
		this.summaryIncomeResultCode = summaryIncomeResultCode;
	}
	public String getDocCompletedFlag() {
		return docCompletedFlag;
	}
	public void setDocCompletedFlag(String docCompletedFlag) {
		this.docCompletedFlag = docCompletedFlag;
	}
	public String getRequiredVerHrFlag() {
		return requiredVerHrFlag;
	}
	public void setRequiredVerHrFlag(String requiredVerHrFlag) {
		this.requiredVerHrFlag = requiredVerHrFlag;
	}
	public String getSummaryIncomeResult() {
		return summaryIncomeResult;
	}
	public void setSummaryIncomeResult(String summaryIncomeResult) {
		this.summaryIncomeResult = summaryIncomeResult;
	}
	public String getVerCusResult() {
		return verCusResult;
	}
	public void setVerCusResult(String verCusResult) {
		this.verCusResult = verCusResult;
	}
	public String getVerWebResultCode() {
		return verWebResultCode;
	}
	public void setVerWebResultCode(String verWebResultCode) {
		this.verWebResultCode = verWebResultCode;
	}
	public String getRequiredVerWebFlag() {
		return requiredVerWebFlag;
	}
	public void setRequiredVerWebFlag(String requiredVerWebFlag) {
		this.requiredVerWebFlag = requiredVerWebFlag;
	}
	public String getVerHrResultCode() {
		return verHrResultCode;
	}
	public void setVerHrResultCode(String verHrResultCode) {
		this.verHrResultCode = verHrResultCode;
	}
	public String getRequiredVerIncomeFlag() {
		return requiredVerIncomeFlag;
	}
	public void setRequiredVerIncomeFlag(String requiredVerIncomeFlag) {
		this.requiredVerIncomeFlag = requiredVerIncomeFlag;
	}
	public String getVerCusResultCode() {
		return verCusResultCode;
	}
	public void setVerCusResultCode(String verCusResultCode) {
		this.verCusResultCode = verCusResultCode;
	}
	public String getVerHrResult() {
		return verHrResult;
	}
	public void setVerHrResult(String verHrResult) {
		this.verHrResult = verHrResult;
	}
	public String getVerPrivilegeResultCode() {
		return verPrivilegeResultCode;
	}
	public void setVerPrivilegeResultCode(String verPrivilegeResultCode) {
		this.verPrivilegeResultCode = verPrivilegeResultCode;
	}
	public String getRequiredVerCustFlag() {
		return requiredVerCustFlag;
	}
	public void setRequiredVerCustFlag(String requiredVerCustFlag) {
		this.requiredVerCustFlag = requiredVerCustFlag;
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
	public ArrayList<HRVerificationDataM> getHrVerifications() {
		return hrVerifications;
	}
	public void setHrVerifications(ArrayList<HRVerificationDataM> hrVerifications) {
		this.hrVerifications = hrVerifications;
	}
	public ArrayList<IdentifyQuestionDataM> getIndentifyQuestions() {
		return indentifyQuestions;
	}
	public void setIndentifyQuestions(ArrayList<IdentifyQuestionDataM> indentifyQuestions) {
		this.indentifyQuestions = indentifyQuestions;
	}
	public ArrayList<PhoneVerificationDataM> getPhoneVerifications() {
		return phoneVerifications;
	}
	public void setPhoneVerifications(ArrayList<PhoneVerificationDataM> phoneVerifications) {
		this.phoneVerifications = phoneVerifications;
	}
	public ArrayList<PolicyRulesDataM> getPolicyRules() {
		return policyRules;
	}
	public void setPolicyRules(ArrayList<PolicyRulesDataM> policyRules) {
		this.policyRules = policyRules;
	}
	public ArrayList<WebVerificationDataM> getWebVerifications() {
		return webVerifications;
	}
	public void setWebVerifications(ArrayList<WebVerificationDataM> webVerifications) {
		this.webVerifications = webVerifications;
	}	
	public ArrayList<PrivilegeProjectCodeDataM> getPrivilegeProjectCodes() {
		return privilegeProjectCodes;
	}
	public void setPrivilegeProjectCodes(
			ArrayList<PrivilegeProjectCodeDataM> privilegeProjectCodes) {
		this.privilegeProjectCodes = privilegeProjectCodes;
	}
	//	public String getApplicationGroupId() {
//		return applicationGroupId;
//	}
//	public void setApplicationGroupId(String applicationGroupId) {
//		this.applicationGroupId = applicationGroupId;
//	}
	public String getVerResultId() {
		return verResultId;
	}
	public void setVerResultId(String verResultId) {
		this.verResultId = verResultId;
	}
//	public String getApplicationRecordId() {
//		return applicationRecordId;
//	}
//	public void setApplicationRecordId(String applicationRecordId) {
//		this.applicationRecordId = applicationRecordId;
//	}
	public String getVerLevel() {
		return verLevel;
	}
	public void setVerLevel(String verLevel) {
		this.verLevel = verLevel;
	}
	public ArrayList<DocumentVerificationDataM> getDocumentVerifications() {
		return documentVerifications;
	}
	public void setDocumentVerifications(ArrayList<DocumentVerificationDataM> documentVerifications) {
		this.documentVerifications = documentVerifications;
	}
	public ArrayList<RequiredDocDataM> getRequiredDocs() {
		return requiredDocs;
	}
	public ArrayList<String> getRequiredDocCode(){
		ArrayList<String> docCodes = new ArrayList<String>();
		if(null != requiredDocs){
			for (RequiredDocDataM requiredDoc : requiredDocs) {
				ArrayList<RequiredDocDetailDataM> requiredDocDetails = requiredDoc.getRequiredDocDetails();
				if(null != requiredDocDetails){
					for (RequiredDocDetailDataM requiredDocDetail : requiredDocDetails) {
						String docCode = requiredDocDetail.getDocumentCode();
						if(null != docCode && !docCodes.contains(docCode)){
							docCodes.add(docCode);
						}
					}
				}
			}
		}
		return docCodes;
	}
	public RequiredDocDataM filterRequireDocument(String scenarioType,String docGroupCode){
		if(null != scenarioType && null!=docGroupCode && null!=requiredDocs){
			for (RequiredDocDataM requiredDoc : requiredDocs) {
				if(scenarioType.equals(requiredDoc.getScenarioType()) && docGroupCode.equals(requiredDoc.getDocumentGroupCode()) ){
					return requiredDoc;
				}
			}
		}
		return null;
	}

	public ArrayList<PhoneVerificationDataM> filterPhoneVerifications(int lifeCycle) {
		ArrayList<PhoneVerificationDataM> phoneVerificationList = new ArrayList<PhoneVerificationDataM>();
		if(null != phoneVerifications){
			for(PhoneVerificationDataM phoneVerificationM : phoneVerifications){
				if(lifeCycle == phoneVerificationM.getLifeCycle()){
					phoneVerificationList.add(phoneVerificationM);
				}
			}
		}
		
		return phoneVerificationList;
	}
	public void setRequiredDocs(ArrayList<RequiredDocDataM> requiredDocs) {
		this.requiredDocs = requiredDocs;
	}
	public void addRequiredDocs(RequiredDocDataM  requiredDoc) {
		if(null==requiredDocs){
			requiredDocs  = new ArrayList<RequiredDocDataM>();
		}
		requiredDocs.add(requiredDoc);
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}

	public HRVerificationDataM getHRVerificationByType(String contactType){
		if(null!= hrVerifications){
			for(HRVerificationDataM hrVerification :hrVerifications){
				if(null !=hrVerification.getContactType() && hrVerification.getContactType().equals(contactType)){
						return hrVerification;
				}
			}
		}
		return null;
	}
	public PhoneVerificationDataM getPhoneVerificationByType(String contactType){
		if(null!= phoneVerifications){
			for(PhoneVerificationDataM phoneVerification :phoneVerifications){
				if(null !=phoneVerification.getContactType() && phoneVerification.getContactType().equals(contactType)){
						return phoneVerification;
				}
			}
		}
		return null;
	}	
	public void addHRVerification(HRVerificationDataM hrVerification){
		if(null == hrVerifications){
			hrVerifications = new ArrayList<HRVerificationDataM>();
		}
		hrVerifications.add(hrVerification);
	}	
	public void addIdentifyQuestion(IdentifyQuestionDataM identifyQuestion){
		if(null==indentifyQuestions) {
			indentifyQuestions = new ArrayList<IdentifyQuestionDataM>();
		}
		indentifyQuestions.add(identifyQuestion);
	}
	public void addphoneVerifications(PhoneVerificationDataM PhoneVerification){
		if(null==phoneVerifications) {
			phoneVerifications = new ArrayList<PhoneVerificationDataM>();
		}
		phoneVerifications.add(PhoneVerification);
	}
	public PrivilegeProjectCodeDataM getPrivilegeProjectCode(int index){
		if(null != privilegeProjectCodes){
			return privilegeProjectCodes.get(index);
		}
		return null;
	}
	public ArrayList<IdentifyQuestionSetDataM> getIndentifyQuesitionSets() {
		return indentifyQuesitionSets;
	}
	public ArrayList<IdentifyQuestionSetDataM> filterIndentifyQuesitionSets(String questionSetType) {
		ArrayList<IdentifyQuestionSetDataM> filters = new ArrayList<IdentifyQuestionSetDataM>();
		if(null != indentifyQuesitionSets){
			for(IdentifyQuestionSetDataM identifyQuestionSet:indentifyQuesitionSets){
				if(null != identifyQuestionSet.getQuestionSetType() && identifyQuestionSet.getQuestionSetType().equals(questionSetType)){
					filters.add(identifyQuestionSet);
				}
			}
		}
		return filters;
	}
	public ArrayList<IdentifyQuestionDataM> filterIndentifyQuesitions(String questionSetType){
		ArrayList<IdentifyQuestionDataM> filters = new ArrayList<IdentifyQuestionDataM>();
		if(null != indentifyQuestions){
			for (IdentifyQuestionDataM identifyQuestion : indentifyQuestions) {
				if(null != identifyQuestion.getQuestionSetType() && identifyQuestion.getQuestionSetType().equals(questionSetType)){
					filters.add(identifyQuestion);
				}
			}
		}
		return filters;
	}
	public boolean isContainOnlyConditionQuestionSetCodes(String questionSetType,String qustionSetCode){
		ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = this.filterIndentifyQuesitionSetsBySetType(questionSetType);
		if(null!=identifyQuestionSets){
			for (IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets) {
				String filterQuesttionSetCode= identifyQuestionSet.getQuestionSetCode();
				if(null !=filterQuesttionSetCode && !filterQuesttionSetCode.equals(qustionSetCode)){
					return false;					
				}
			}
		}
		return true;
	}
	public boolean isContainConditionQuestionSetCodes(String questionSetType,String qustionSetCode){
		ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = this.filterIndentifyQuesitionSetsBySetType(questionSetType);
		if(null!=identifyQuestionSets){
			for (IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets) {
				String filterQuesttionSetCode= identifyQuestionSet.getQuestionSetCode();
				if(null !=filterQuesttionSetCode && filterQuesttionSetCode.equals(qustionSetCode)){
					return true;					
				}
			}
		}
		return false;
	}
	public void setIndentifyQuesitionSets(ArrayList<IdentifyQuestionSetDataM> indentifyQuesitionSets) {
		this.indentifyQuesitionSets = indentifyQuesitionSets;
	}
	public void addWebVerification(WebVerificationDataM webVerification){
		if(null == webVerifications){
			webVerifications = new ArrayList<>();
		}
		if(null != webVerification){
			webVerifications.add(webVerification);
		}
	}
	public IdentifyQuestionDataM getIndentifyQuesitionNo(String QuestionNo,String questionSetCode){
		if(null !=indentifyQuestions){
			for(IdentifyQuestionDataM indentifyQuestion:indentifyQuestions){
				if(null !=indentifyQuestion.getQuestionNo() 
						&& indentifyQuestion.getQuestionNo().equals(QuestionNo) 
						&& questionSetCode.equals(indentifyQuestion.getQuestionSetCode())){
					return indentifyQuestion;
				}
			}
		}
		
		return null;
	}
	public IdentifyQuestionDataM getIndentifyQuesitionNo(String QuestionNo){
		if(null !=indentifyQuestions){
			for(IdentifyQuestionDataM indentifyQuestion:indentifyQuestions){
				if(null !=indentifyQuestion.getQuestionNo() 
						&& indentifyQuestion.getQuestionNo().equals(QuestionNo)){
					return indentifyQuestion;
				}
			}
		}
		
		return null;
	}
	
	public IdentifyQuestionSetDataM filterIndentifyQuesitionSetBySetType(String SetType){
		if(null !=indentifyQuesitionSets){
			for(IdentifyQuestionSetDataM indentifyQuesitionSet:indentifyQuesitionSets){
				if(indentifyQuesitionSet.getQuestionSetType().equals(SetType)){
					return indentifyQuesitionSet;
				}
			}
		}
		return null;
	}
	
	public ArrayList<IdentifyQuestionSetDataM> filterIndentifyQuesitionSetsBySetType(String SetType){
		ArrayList<IdentifyQuestionSetDataM> filters = new ArrayList<IdentifyQuestionSetDataM>();
		if(null !=indentifyQuesitionSets){
			for(IdentifyQuestionSetDataM indentifyQuesitionSet:indentifyQuesitionSets){
				if(indentifyQuesitionSet.getQuestionSetType().equals(SetType)){
					filters.add(indentifyQuesitionSet);
				}
			}
		}
		return filters;
	}
	
	public void addIndentifyQuesitionSet(IdentifyQuestionSetDataM identifyQuestionSet){
		if(null == indentifyQuesitionSets){
			indentifyQuesitionSets = new ArrayList<IdentifyQuestionSetDataM>();
		}
		indentifyQuesitionSets.add(identifyQuestionSet);
	}
	public String getVerCusComplete() {
		return verCusComplete;
	}
	public void setVerCusComplete(String verCusComplete) {
		this.verCusComplete = verCusComplete;
	}
	public String getRiskGradeCoarseCode() {
		return riskGradeCoarseCode;
	}
	public void setRiskGradeCoarseCode(String riskGradeCoarseCode) {
		this.riskGradeCoarseCode = riskGradeCoarseCode;
	}
	public String getRiskGradeFineCode() {
		return riskGradeFineCode;
	}
	public void setRiskGradeFineCode(String riskGradeFineCode) {
		this.riskGradeFineCode = riskGradeFineCode;
	}
	public String getCompareSignatureResult() {
		return compareSignatureResult;
	}
	public void setCompareSignatureResult(String compareSignatureResult) {
		this.compareSignatureResult = compareSignatureResult;
	}
	
	public String getRequirePriorityPass() {
		return requirePriorityPass;
	}
	public void setRequirePriorityPass(String requirePriorityPass) {
		this.requirePriorityPass = requirePriorityPass;
	}
	public String getFicoFlag() {
		return ficoFlag;
	}
	public void setFicoFlag(String ficoFlag) {
		this.ficoFlag = ficoFlag;
	}
	
	public String getDocFollowUpStatus() {
		return docFollowUpStatus;
	}
	public void setDocFollowUpStatus(String docFollowUpStatus) {
		this.docFollowUpStatus = docFollowUpStatus;
	}
	public int getIndexQuesetion(String questionNo, String questionSet){
		ArrayList<IdentifyQuestionDataM> indentifyQuestionInSet = new ArrayList<IdentifyQuestionDataM> ();
		if(null !=indentifyQuestions){
			for(IdentifyQuestionDataM indentifyQuestion:indentifyQuestions){
					if(indentifyQuestion.getQuestionSetType().equals(questionSet)){
						indentifyQuestionInSet.add(indentifyQuestion);
					}	
			}
			
		}
		if(null != indentifyQuestionInSet){
			for(int i=0;i < indentifyQuestionInSet.size();i++ ){
				if(indentifyQuestionInSet.get(i).getQuestionNo().equals(questionNo)){
					return i;
				}
			}
			
		}
		
		
	return 0;
	}
	
	public WebVerificationDataM getWebVerificationWebCode(String webCode){
		if(null!=webVerifications && null!=webCode){
			for(WebVerificationDataM webVerification : webVerifications){
				if(webVerification.getWebCode().equals(webCode)){
					return webVerification;
				}
			}
		}
		return null;
	}
	
	public void getWebVerification(ArrayList<WebVerificationDataM> webVerifications, final ArrayList<String> webSSOs){
		if(null!=webVerifications && null!=webSSOs){
				Collections.sort(webVerifications,new Comparator<WebVerificationDataM>(){
					@Override
					public int compare(final WebVerificationDataM webVer1,WebVerificationDataM webVer2) {
						return getIndexWebSSOVerification(webSSOs, webVer1.getWebCode()) < getIndexWebSSOVerification(webSSOs, webVer2.getWebCode()) ? -1 : 1;
					}
				});
		}
	}
	private int getIndexWebSSOVerification(ArrayList<String> webSSO, String webCode){
		if(null != webSSO){
			for(int i = 0; i < webSSO.size(); i++){
				String webSSOCode = webSSO.get(i);
				if(webCode.equals(webSSOCode)){
					return i;
				}
			}
		}
		return 99;
	}
	public ArrayList<WebVerificationDataM> getOtherWebVerifications(ArrayList<String> webSSO){
		ArrayList<WebVerificationDataM>  listOtherWebVerification = new ArrayList<WebVerificationDataM>();
		if(null!=webVerifications && null!=webSSO){
			for(WebVerificationDataM webVerificationDataM: webVerifications){
				if(!webSSO.contains(webVerificationDataM.getWebCode())){
					listOtherWebVerification.add(webVerificationDataM);
				}
			}
		}
		return listOtherWebVerification;
	}
	
	public WebVerificationDataM getWebVerificationDataM(String webCode, ArrayList<WebVerificationDataM>  listWebVerifications){
		if(null!=listWebVerifications){
			for(WebVerificationDataM webVerificationDataM : listWebVerifications){
				if(webCode.equals(webVerificationDataM.getWebCode())){
					return webVerificationDataM;
				}
			}
		}
		return null;
	} 
	
	public HRVerificationDataM getLastHrVerification(){
		if(null!=hrVerifications){
			HRVerificationDataM hrVerification = hrVerifications.get(hrVerifications.size()-1);
			return hrVerification;
		}
		return null;
	}
	public ArrayList<IdentifyQuestionSetDataM> getVerifyCustomerQuestionSets(String[] QUESTION_SET_VERIFY_CUSTOMER){
		ArrayList<IdentifyQuestionSetDataM> indentifyQuestionSets =new ArrayList<IdentifyQuestionSetDataM>();
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(QUESTION_SET_VERIFY_CUSTOMER));
		if(null != indentifyQuesitionSets){
			for (IdentifyQuestionSetDataM indentifyQuestionSet : indentifyQuesitionSets) {
				if(null != indentifyQuestionSet.getQuestionSetCode() && conditions.contains(indentifyQuestionSet.getQuestionSetCode())){
					indentifyQuestionSets.add(indentifyQuestionSet);
				}
			}
		}
		return indentifyQuestionSets;
	}
	public String getVerifyHrQuestionSet(String[] QUESTION_SET_VERIFY_HR){
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(QUESTION_SET_VERIFY_HR));
		if(null != indentifyQuesitionSets){
			for (IdentifyQuestionSetDataM indentifyQuestionSet : indentifyQuesitionSets) {
				if(null != indentifyQuestionSet.getQuestionSetCode() && conditions.contains(indentifyQuestionSet.getQuestionSetCode())){
					return indentifyQuestionSet.getQuestionSetCode();
				}
			}
		}
		return null;
	}
	public boolean containRequiredDocMandatoryFlag(String docCode,String mandatoryFlag){
		if(null != requiredDocs){
			for (RequiredDocDataM reqDoc : requiredDocs) {
				if(null != reqDoc.getRequiredDocDetails()){
					 for(RequiredDocDetailDataM reqDocDetail : reqDoc.getRequiredDocDetails()){
						 if(null != docCode && docCode.equals(reqDocDetail.getDocumentCode()) 
								 && mandatoryFlag.equals(reqDocDetail.getMandatoryFlag())){
							 return true;							 
						 }						 
					 }
				}
			}
		}
		return false;
	}
	
	public ArrayList<PriorityPassVerification> getPriorityPassVerifications() {
		return priorityPassVerifications;
	}
	
	public void setPriorityPassVerifications(
			ArrayList<PriorityPassVerification> priorityPassVerifications) {
		this.priorityPassVerifications = priorityPassVerifications;
	}	
	public ArrayList<IncomeCalculationDataM> getIncomeCalculations() {
		return incomeCalculations;
	}
	public void setIncomeCalculations(
			ArrayList<IncomeCalculationDataM> incomeCalculations) {
		this.incomeCalculations = incomeCalculations;
	}
	public void addIncomeCalculation(IncomeCalculationDataM incomeCalculation) {
		if(null==incomeCalculations){
			incomeCalculations = new ArrayList<IncomeCalculationDataM>();
		}
		incomeCalculations.add(incomeCalculation);
	}
	public void addPriorityPassVerification(PriorityPassVerification priorityPassVerification) {
		if(null==priorityPassVerifications){
			priorityPassVerifications = new ArrayList<PriorityPassVerification>();
		}
		priorityPassVerifications.add(priorityPassVerification);
	}
	public IncomeScreenDataM getIncomeScreens() {
		return incomeScreens;
	}
	public void setIncomeScreens(IncomeScreenDataM incomeScreens) {
		this.incomeScreens = incomeScreens;
	}
	public String getAScoreCode() {
		return aScoreCode;
	}
	public void setAScoreCode(String aScoreCode) {
		this.aScoreCode = aScoreCode;
	}
	public void clearVerifyResult(){
		this.summaryIncomeResult = null;
		this.summaryIncomeResultCode = null;
		this.verPrivilegeResult = null;
		this.verPrivilegeResultCode = null;
		this.verWebResult = null;
		this.verWebResultCode = null;
		this.verCusResult = null;
		this.verCusResultCode = null;
		this.aScoreCode = null;
	}
}
