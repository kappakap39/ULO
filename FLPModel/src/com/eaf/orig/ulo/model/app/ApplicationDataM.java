package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("serial")
public class ApplicationDataM implements Serializable,Cloneable,Comparator<Object>{
	public class PRODUCT{
		public static final String PRODUCT_CRADIT_CARD="CC";
		public static final String PRODUCT_K_EXPRESS_CASH="KEC";
		public static final String PRODUCT_K_PERSONAL_LOAN="KPL";
	}
	public class SEQ_PRODUCT{
		public static final int CC = 1;
		public static final int KEC = 2;
		public static final int NEW = 3;
	}
	public class STATUS{
		public static final String APPLICATION_STATUS_APPROVE="Pre-Approve";
		public static final String APPLICATION_STATUS_REJECT="Pre-Reject";
		public static final String APPLICATION_STATUS_POST_APPROVE="Post Approved";
		public static final String APPLICATION_STATUS_APPROVED="Approved";
	}
	public ApplicationDataM(){
		super();
	}
	public ApplicationDataM(String uniqueId){
		super();
		this.applicationRecordId = uniqueId;
	}
	private String applicationGroupId;	//ORIG_APPLICATION.APPLICATION_GROUP_ID(VARCHAR2)
	private String applicationRecordId;	//ORIG_APPLICATION.APPLICATION_RECORD_ID(VARCHAR2)
	private String projectCode;	//ORIG_APPLICATION.PROJECT_CODE(VARCHAR2)
	private String spSignOffFlag;	//ORIG_APPLICATION.SP_SIGN_OFF_FLAG(VARCHAR2)
	private String applicationNo;	//ORIG_APPLICATION.APPLICATION_NO(VARCHAR2)
	private String tcbAccountNo;	//ORIG_APPLICATION.TCB_ACCOUNT_NO(VARCHAR2)
	private String spSignoffAuthBy;	//ORIG_APPLICATION.SP_SIGNOFF_AUTH_BY(VARCHAR2)
	private String recommendDecision;	//ORIG_APPLICATION.RECOMMEND_DECISION(VARCHAR2)
	private Date finalAppDecisionDate;	//ORIG_APPLICATION.FINAL_APP_DECISION_DATE(DATE)
	private String bookingBy;	//ORIG_APPLICATION.BOOKING_BY(VARCHAR2)
	private String finalAppDecision;	//ORIG_APPLICATION.FINAL_APP_DECISION(VARCHAR2)
	private BigDecimal maxCreditLine;	//ORIG_APPLICATION.MAX_CREDIT_LINE(NUMBER)
	private String finalAppDecisionBy;	//ORIG_APPLICATION.FINAL_APP_DECISION_BY(VARCHAR2)
	private String maincardRecordId;	//ORIG_APPLICATION.MAINCARD_RECORD_ID(VARCHAR2)
	private BigDecimal recommentCreditLine;	//ORIG_APPLICATION.RECOMMENT_CREDIT_LINE(NUMBER)
	private String businessClassId;	//ORIG_APPLICATION.BUSINESS_CLASS_ID(VARCHAR2)
	private String applicationType;	//ORIG_APPLICATION.APPLICATION_TYPE(VARCHAR2)
	private int lifeCycle;	//ORIG_APPLICATION.LIFE_CYCLE(NUMBER)
	private String refApplicationRecordId;	//ORIG_APPLICATION.REF_APPLICATION_RECORD_ID(VARCHAR2)
	private Date bookingDate;	//ORIG_APPLICATION.BOOKING_DATE(DATE)
	private Date spSignoffDate;	//ORIG_APPLICATION.SP_SIGNOFF_DATE(DATE)
	private String createBy;	//ORIG_APPLICATION.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_APPLICATION.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_APPLICATION.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_APPLICATION.UPDATE_DATE(DATE)
	private String diffRequestResult; //ORIG_APPLICATION.DIFF_REQUEST_RESULT(VARCHAR2)
	private String policyProgramId; //ORIG_APPLICATION.PROLICY_PROGRAM_ID(VARCHAR2)
	private String existingPriorityPass; //ORIG_APPLICATION.EXISTING_PRIORITY_PASS(VARCHAR2)	
	private String policySegmentId; //ORIG_APPLICATION.POLICY_SEGMENT_ID(VARCHAR2)
	private String isVetoEligibleFlag; //ORIG_APPLICATION.IS_VETO_ELIGIBLE(VARCHAR2)
	private String referCriteria;	//ORIG_APPLICATION.REFER_CRITERIA	
	private String cardlinkRefNo;	//ORIG_APPLICATION.CARDLINK_REF_NO(VARCHAR2)
	private int cardlinkSeq;
	private String cardlinkFlag;
	private String vlinkFlag;
	private String  reExecuteKeyProgramFlag; //ORIG_APPLICATION.RE_EXECUTE_KEY_PROGRAM_FLAG(VARCHAR2)
	private BundleHLDataM bundleHL;
	private BundleKLDataM bundleKL;
	private BundleSMEDataM bundleSME;
	private ArrayList<ReasonDataM> reasons;
	private ArrayList<ReasonLogDataM> reasonLogs;
	private ArrayList<LoanDataM> loans;
	private ArrayList<InfBatchLogDataM> infBatchLogs;
	private VerificationResultDataM verificationResult;
	private Date applicationDate;
//	private boolean deleteFlag = false;
	private int seq;
	private String previousRecommendDecision;	//ORIG_APPLICATION.PREVIOUS_RECOMMEND_DECISION(VARCHAR2)
	
	//KPL Additional
	private String savingPlusFlag; //ORIG_APPLICATION.BLOCK_FLAG (""=No block, "A"=Pending deduct a/c)
	private String diffRequestFlag; //ORIG_APPLICATION.REOPEN_FLAG
	private String letterChannel; //ORIG_APPLICATION.LETTER_CHANNEL
	private String eRecommendDecision;
	
	private String lowIncomeFlag;
	private String eApplicationRecordId;	//ORIG_APPLICATION.E_APPLICATION_RECORD_ID(VARCHAR2)
	private String applyTypeFlag; //ORIG_APPLICATION.APPLY_TYPE_FLAG(VARCHAR2)
	
	public String getLetterChannel() {
		return letterChannel;
	}
	public void setLetterChannel(String letterChannel) {
		this.letterChannel = letterChannel;
	}
	public String getDiffRequestFlag() {
		return diffRequestFlag;
	}
	public void setDiffRequestFlag(String diffRequestFlag) {
		this.diffRequestFlag = diffRequestFlag;
	}
	public String getSavingPlusFlag() {
		return savingPlusFlag;
	}

	public void setSavingPlusFlag(String savingPlusFlag) {
		this.savingPlusFlag = savingPlusFlag;
	}
	
	public void clearValue(){
		this.reasons = new ArrayList<ReasonDataM>();
		this.reasonLogs = new ArrayList<ReasonLogDataM>();
		this.infBatchLogs = new ArrayList<InfBatchLogDataM>();
		this.verificationResult = new VerificationResultDataM();
		this.spSignOffFlag = null;
		this.tcbAccountNo = null;
		this.spSignoffAuthBy = null;
		this.recommendDecision = null;
		this.finalAppDecisionDate = null;
		this.cardlinkRefNo = null;
		this.bookingBy = null;
		this.finalAppDecision = null;
		this.maxCreditLine = null;
		this.finalAppDecisionBy = null;
		this.recommentCreditLine = null;
		this.bookingDate = null;
		this.spSignoffDate = null;
		this.diffRequestResult = null;
		this.policyProgramId = null;
		this.existingPriorityPass = null;
		this.policySegmentId = null;
		this.isVetoEligibleFlag = null;
		this.applicationNo=null;
		this.referCriteria=null;
		this.previousRecommendDecision=null;
		this.savingPlusFlag = null;
		this.lowIncomeFlag = null;
	}
	
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getRefApplicationRecordId() {
		return refApplicationRecordId;
	}
	public void setRefApplicationRecordId(String refApplicationRecordId) {
		this.refApplicationRecordId = refApplicationRecordId;
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getSpSignOffFlag() {
		return spSignOffFlag;
	}
	public void setSpSignOffFlag(String spSignOffFlag) {
		this.spSignOffFlag = spSignOffFlag;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}		
	public String getReferCriteria() {
		return referCriteria;
	}
	public void setReferCriteria(String referCriteria) {
		this.referCriteria = referCriteria;
	}
	public String getTcbAccountNo() {
		return tcbAccountNo;
	}
	public void setTcbAccountNo(String tcbAccountNo) {
		this.tcbAccountNo = tcbAccountNo;
	}
	public String getSpSignoffAuthBy() {
		return spSignoffAuthBy;
	}
	public void setSpSignoffAuthBy(String spSignoffAuthBy) {
		this.spSignoffAuthBy = spSignoffAuthBy;
	}
	public String getRecommendDecision() {
		return recommendDecision;
	}
	public void setRecommendDecision(String recommendDecision) {
		this.recommendDecision = recommendDecision;
	}
	public Date getFinalAppDecisionDate() {
		return finalAppDecisionDate;
	}
	public void setFinalAppDecisionDate(Date finalAppDecisionDate) {
		this.finalAppDecisionDate = finalAppDecisionDate;
	}
	public String getCardlinkRefNo() {
		return cardlinkRefNo;
	}
	public void setCardlinkRefNo(String cardlinkRefNo) {
		this.cardlinkRefNo = cardlinkRefNo;
	}
	public String getBookingBy() {
		return bookingBy;
	}
	public void setBookingBy(String bookingBy) {
		this.bookingBy = bookingBy;
	}
	public String getFinalAppDecision() {
		return finalAppDecision;
	}
	public void setFinalAppDecision(String finalAppDecision) {
		this.finalAppDecision = finalAppDecision;
	}
	public BigDecimal getMaxCreditLine() {
		return maxCreditLine;
	}
	public void setMaxCreditLine(BigDecimal maxCreditLine) {
		this.maxCreditLine = maxCreditLine;
	}
	public String getFinalAppDecisionBy() {
		return finalAppDecisionBy;
	}
	public void setFinalAppDecisionBy(String finalAppDecisionBy) {
		this.finalAppDecisionBy = finalAppDecisionBy;
	}
	public String getMaincardRecordId() {
		return maincardRecordId;
	}
	public void setMaincardRecordId(String maincardRecordId) {
		this.maincardRecordId = maincardRecordId;
	}
	public BigDecimal getRecommentCreditLine() {
		return recommentCreditLine;
	}
	public void setRecommentCreditLine(BigDecimal recommentCreditLine) {
		this.recommentCreditLine = recommentCreditLine;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Date getSpSignoffDate() {
		return spSignoffDate;
	}
	public void setSpSignoffDate(Date spSignoffDate) {
		this.spSignoffDate = spSignoffDate;
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
	public BundleHLDataM getBundleHL() {
		return bundleHL;
	}
	public void setBundleHL(BundleHLDataM bundleHL) {
		this.bundleHL = bundleHL;
	}
	public BundleKLDataM getBundleKL() {
		return bundleKL;
	}
	public void setBundleKL(BundleKLDataM bundleKL) {
		this.bundleKL = bundleKL;
	}
	public BundleSMEDataM getBundleSME() {
		return bundleSME;
	}
	public void setBundleSME(BundleSMEDataM bundleSME) {
		this.bundleSME = bundleSME;
	}	
	public ArrayList<ReasonDataM> getReasons() {
		return reasons;
	}
	public void setReasons(ArrayList<ReasonDataM> reasons) {
		this.reasons = reasons;
	}
	public void addReason(ReasonDataM reason){
		if(this.reasons == null){
			this.reasons = new ArrayList<ReasonDataM>();
		}
		if(reason != null){
			this.reasons.add(reason);
		}
	}
	public void addReasonLog(ReasonLogDataM reason){
		if(this.reasonLogs == null){
			this.reasonLogs = new ArrayList<ReasonLogDataM>();
		}
		if(reason != null){
			this.reasonLogs.add(reason);
		}
	}
	public void addReason(ArrayList<ReasonDataM> reasons){
		if(this.reasons == null){
			this.reasons = new ArrayList<ReasonDataM>();
		}
		if(reasons != null){
			for(ReasonDataM reason : reasons){
				this.reasons.add(reason);
			}
		}
	}
	public ArrayList<ReasonLogDataM> getReasonLogs() {
		return reasonLogs;
	}
	public void setReasonLogs(ArrayList<ReasonLogDataM> reasonLogs) {
		this.reasonLogs = reasonLogs;
	}
	public ArrayList<LoanDataM> getLoans() {
		return loans;
	}
	public void setLoans(ArrayList<LoanDataM> loans) {
		this.loans = loans;
	}	
	public ArrayList<InfBatchLogDataM> getInfBatchLogs() {
		return infBatchLogs;
	}
	public void setInfBatchLogs(ArrayList<InfBatchLogDataM> infBatchLogs) {
		this.infBatchLogs = infBatchLogs;
	}
	public VerificationResultDataM getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(VerificationResultDataM verificationResult) {
		this.verificationResult = verificationResult;
	}
	
	public LoanDataM getloanById(String loanId) {
		if (loans != null) {
			for (LoanDataM loanM : loans) {
				if (loanId.equals(loanM.getLoanId())) {
					return loanM;
				}
			}
		}

		return null;
	}
	public void addLoan(LoanDataM loan){
		if(null == loans){
			loans = new ArrayList<LoanDataM>();
		}
		loans.add(loan);
	}
	public String getProduct(){
		return (null != businessClassId)?businessClassId.split("\\_")[0]:null;
	}
	public LoanDataM getLoan(int index){		 
		if(null != loans){
			return loans.get(index);
		}
		return null;
	}
	public CardDataM getCard(){
		if(null != loans && loans.size() > 0){
			return loans.get(0).getCard();
		}
		return null;
	}
	public CashTransferDataM getCashTransfer(String cashTransferType){
		if(null != loans && loans.size() > 0){
			return loans.get(0).getCashTransfer(cashTransferType);
		}
		return null;
	}
	public CashTransferDataM getCashTransfer(String []cashTransFerType){
		if(null != loans && loans.size() > 0){
			return loans.get(0).getCashTransfer(cashTransFerType);
		}
		return null;
	}
	
	public String getDiffRequestResult() {
		return diffRequestResult;
	}
	public void setDiffRequestResult(String diffRequestResult) {
		this.diffRequestResult = diffRequestResult;
	}
	public String getPolicyProgramId() {
		return policyProgramId;
	}
	public void setPolicyProgramId(String policyProgramId) {
		this.policyProgramId = policyProgramId;
	}
	public String getExistingPriorityPass() {
		return existingPriorityPass;
	}
	public void setExistingPriorityPass(String existingPriorityPass) {
		this.existingPriorityPass = existingPriorityPass;
	}
	public LoanDataM getLoan(){
		if(null != loans && loans.size() > 0){
			return loans.get(0);
		}
		return null;
	}
	public String getPolicySegmentId() {
		return policySegmentId;
	}
	public void setPolicySegmentId(String policySegmentId) {
		this.policySegmentId = policySegmentId;
	}
	public String getIsVetoEligibleFlag() {
		return isVetoEligibleFlag;
	}
	public void setIsVetoEligibleFlag(String isVetoEligibleFlag) {
		this.isVetoEligibleFlag = isVetoEligibleFlag;
	}
	public void addAdditionalServiceId(String serviceId) {
		if(null != loans && loans.size() > 0){
			for(LoanDataM loanM : loans) {
				loanM.addAdditionalServiceId(serviceId);
			}
		}
	}
	public void removeAdditionalServiceId(String serviceId) {
		if(null != loans && loans.size() > 0){
			for(LoanDataM loanM : loans) {
				loanM.removeAdditionalServiceId(serviceId);
			}
		}
	}	
//	// Delete flag.
//	public boolean isDeleteFlag() {
//		return deleteFlag;
//	}
//	public void setDeleteFlag() {
//		this.deleteFlag = true;
//	}
	public int getCardlinkSeq() {
		return cardlinkSeq;
	}
	public void setCardlinkSeq(int cardlinkSeq) {
		this.cardlinkSeq = cardlinkSeq;
	}
	public String getCardlinkFlag() {
		return cardlinkFlag;
	}
	public void setCardlinkFlag(String cardlinkFlag) {
		this.cardlinkFlag = cardlinkFlag;
	}
	public String getVlinkFlag() {
		return vlinkFlag;
	}
	public void setVlinkFlag(String vlinkFlag) {
		this.vlinkFlag = vlinkFlag;
	}
	public String getReExecuteKeyProgramFlag() {
		return reExecuteKeyProgramFlag;
	}
	public void setReExecuteKeyProgramFlag(String reExecuteKeyProgramFlag) {
		this.reExecuteKeyProgramFlag = reExecuteKeyProgramFlag;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getPreviousRecommendDecision() {
		return previousRecommendDecision;
	}
	public void setPreviousRecommendDecision(String previousRecommendDecision) {
		this.previousRecommendDecision = previousRecommendDecision;
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
	@Override
	public int compare(Object object1, Object object2) {
		if(object1 instanceof ApplicationDataM && object2 instanceof ApplicationDataM){
			ApplicationDataM applicationCompare1 = (ApplicationDataM)object1;
			ApplicationDataM applicationCompare2 = (ApplicationDataM)object2;
			Integer seqOb1 = getSeqProduct(applicationCompare1.getProduct());
			Integer seqOb2 = getSeqProduct(applicationCompare1.getProduct());
			Integer seqObProduct = seqOb1.compareTo(seqOb2);
			if(seqObProduct != 0) return seqObProduct;
			Integer seqobCardType = 0;
			if(applicationCompare1.getCard() != null 
					&& applicationCompare2.getCard() != null 
					&& applicationCompare2.getCard().getCardType() != null
				    && applicationCompare1.getCard().getCardType() != null){
				seqobCardType = applicationCompare1.getCard().getCardType().compareTo(applicationCompare2.getCard().getCardType());
			}
			
			return seqobCardType;
		}
		return 0;
	}
	
	public int getSeqProduct(String product){
		if (ApplicationDataM.PRODUCT.PRODUCT_CRADIT_CARD.equals(product)){
			return ApplicationDataM.SEQ_PRODUCT.CC;
		}else if(ApplicationDataM.PRODUCT.PRODUCT_K_EXPRESS_CASH.equals(product)){
			return ApplicationDataM.SEQ_PRODUCT.KEC;
		}
		return ApplicationDataM.SEQ_PRODUCT.NEW;
	}
	public void clearFinalAppDecisionValue(){
		this.finalAppDecision = null;
		this.finalAppDecisionBy = null;
		this.finalAppDecisionDate = null;
		this.previousRecommendDecision = null;
	}
	public String geteRecommendDecision() {
		return eRecommendDecision;
	}
	public void seteRecommendDecision(String eRecommendDecision) {
		this.eRecommendDecision = eRecommendDecision;
	}
	public String getLowIncomeFlag() {
		return lowIncomeFlag;
	}
	public void setLowIncomeFlag(String lowIncomeFlag) {
		this.lowIncomeFlag = lowIncomeFlag;
	}
	public String geteApplicationRecordId() {
		return eApplicationRecordId;
	}
	public void seteApplicationRecordId(String eApplicationRecordId) {
		this.eApplicationRecordId = eApplicationRecordId;
	}
	public String getApplyTypeFlag() {
		return applyTypeFlag;
	}
	public void setApplyTypeFlag(String applyTypeFlag) {
		this.applyTypeFlag = applyTypeFlag;
	}
	public void clearReason(){
		this.reasons = new ArrayList<ReasonDataM>();
	}
	
}
