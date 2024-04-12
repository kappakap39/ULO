package com.eaf.orig.ulo.model.ncb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;

public class NcbInfoDataM implements Serializable,Cloneable{
	public NcbInfoDataM(){
		super();
	}
	private String personalId;		//NCB_INFO.PERSONAL_ID(VARCHAR2)
	private String trackingCode;	//NCB_INFO.TRACKING_CODE(VARCHAR2)
	private String ficoScore;		//NCB_INFO.FICO_SCORE(VARCHAR2)
	private String ficoReason2Code;	//NCB_INFO.FICO_REASON2_CODE(VARCHAR2)
	private String ficoErrorCode;	//NCB_INFO.FICO_ERROR_CODE(VARCHAR2)
	private String ficoReason1Desc;	//NCB_INFO.FICO_REASON1_DESC(VARCHAR2)
	private String consentRefNo;	//NCB_INFO.CONSENT_REF_NO(VARCHAR2)
	private String ficoReason4Code;	//NCB_INFO.FICO_REASON4_CODE(VARCHAR2)
	private String ficoReason3Desc;	//NCB_INFO.FICO_REASON3_DESC(VARCHAR2)
	private String ficoReason4Desc;	//NCB_INFO.FICO_REASON4_DESC(VARCHAR2)
	private String ficoReason2Desc;	//NCB_INFO.FICO_REASON2_DESC(VARCHAR2)
	private String ficoReason1Code;	//NCB_INFO.FICO_REASON1_CODE(VARCHAR2)
	private String ficoErrorMsg;	//NCB_INFO.FICO_ERROR_MSG(VARCHAR2)
	private String ficoReason3Code;	//NCB_INFO.FICO_REASON3_CODE(VARCHAR2)
	private long noOfTimesEnquiry;	//NCB_INFO.NO_TIMES_ENQUIRY(NUMBER)
	private long noOfTimesEnquiry6M;//NCB_INFO.NO_TIMES_ENQUIRY_6M(NUMBER)
	private long noOfCCCard;		//NCB_INFO.NO_CC_CARD(NUMBER)
	private long totCCOutStanding;	//NCB_INFO.TOT_CC_OUT_STATDING(NUMBER)
	private String createBy;		//NCB_INFO.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//NCB_INFO.CREATE_DATE(DATE)
	private String updateBy;		//NCB_INFO.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//NCB_INFO.UPDATE_DATE(DATE)
	private BigDecimal personalLoanUnderBotIssuer;	// PERSONAL_LOAN_UNDER_BOT_ISSUER(NUMBER)
	private ArrayList<String> segMentValue;
	private Date dateOfSearch;
	
	private ArrayList<NcbAccountDataM> ncbAccounts;
	private ArrayList<NcbIdDataM> ncbIds;
	private ArrayList<NcbNameDataM> ncbNames;
	private ArrayList<NcbAddressDataM> ncbAddress;
	private Timestamp oldestNcbNew;
	
	public Timestamp getOldestNcbNew() {
		return oldestNcbNew;
	}
	public void setOldestNcbNew(Timestamp oldestNcbNew) {
		this.oldestNcbNew = oldestNcbNew;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getTrackingCode() {
		return trackingCode;
	}
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}
	public String getFicoScore() {
		return ficoScore;
	}
	public void setFicoScore(String ficoScore) {
		this.ficoScore = ficoScore;
	}
	public String getFicoReason2Code() {
		return ficoReason2Code;
	}
	public void setFicoReason2Code(String ficoReason2Code) {
		this.ficoReason2Code = ficoReason2Code;
	}
	public String getFicoErrorCode() {
		return ficoErrorCode;
	}
	public void setFicoErrorCode(String ficoErrorCode) {
		this.ficoErrorCode = ficoErrorCode;
	}
	public String getFicoReason1Desc() {
		return ficoReason1Desc;
	}
	public void setFicoReason1Desc(String ficoReason1Desc) {
		this.ficoReason1Desc = ficoReason1Desc;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public String getFicoReason4Code() {
		return ficoReason4Code;
	}
	public void setFicoReason4Code(String ficoReason4Code) {
		this.ficoReason4Code = ficoReason4Code;
	}
	public String getFicoReason3Desc() {
		return ficoReason3Desc;
	}
	public void setFicoReason3Desc(String ficoReason3Desc) {
		this.ficoReason3Desc = ficoReason3Desc;
	}
	public String getFicoReason4Desc() {
		return ficoReason4Desc;
	}
	public void setFicoReason4Desc(String ficoReason4Desc) {
		this.ficoReason4Desc = ficoReason4Desc;
	}
	public String getFicoReason2Desc() {
		return ficoReason2Desc;
	}
	public void setFicoReason2Desc(String ficoReason2Desc) {
		this.ficoReason2Desc = ficoReason2Desc;
	}
	public String getFicoReason1Code() {
		return ficoReason1Code;
	}
	public void setFicoReason1Code(String ficoReason1Code) {
		this.ficoReason1Code = ficoReason1Code;
	}
	public String getFicoErrorMsg() {
		return ficoErrorMsg;
	}
	public void setFicoErrorMsg(String ficoErrorMsg) {
		this.ficoErrorMsg = ficoErrorMsg;
	}
	public String getFicoReason3Code() {
		return ficoReason3Code;
	}
	public void setFicoReason3Code(String ficoReason3Code) {
		this.ficoReason3Code = ficoReason3Code;
	}
	public long getNoOfTimesEnquiry() {
		return noOfTimesEnquiry;
	}
	public void setNoOfTimesEnquiry(long noOfTimesEnquiry) {
		this.noOfTimesEnquiry = noOfTimesEnquiry;
	}
	public long getNoOfTimesEnquiry6M() {
		return noOfTimesEnquiry6M;
	}
	public void setNoOfTimesEnquiry6M(long noOfTimesEnquiry6M) {
		this.noOfTimesEnquiry6M = noOfTimesEnquiry6M;
	}
	public long getNoOfCCCard() {
		return noOfCCCard;
	}
	public void setNoOfCCCard(long noOfCCCard) {
		this.noOfCCCard = noOfCCCard;
	}
	public long getTotCCOutStanding() {
		return totCCOutStanding;
	}
	public void setTotCCOutStanding(long totCCOutStanding) {
		this.totCCOutStanding = totCCOutStanding;
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
	public ArrayList<NcbAccountDataM> getNcbAccounts() {
		return ncbAccounts;
	}
	public void setNcbAccounts(ArrayList<NcbAccountDataM> ncbAccounts) {
		this.ncbAccounts = ncbAccounts;
	}
	public ArrayList<NcbIdDataM> getNcbIds() {
		return ncbIds;
	}
	public void setNcbIds(ArrayList<NcbIdDataM> ncbIds) {
		this.ncbIds = ncbIds;
	}
	public ArrayList<NcbNameDataM> getNcbNames() {
		return ncbNames;
	}
	public void setNcbNames(ArrayList<NcbNameDataM> ncbNames) {
		this.ncbNames = ncbNames;
	}
	public ArrayList<NcbAddressDataM> getNcbAddress() {
		return ncbAddress;
	}
	public void setNcbAddress(ArrayList<NcbAddressDataM> ncbAddress) {
		this.ncbAddress = ncbAddress;
	}	
	
	public BigDecimal getPersonalLoanUnderBotIssuer() {
		return personalLoanUnderBotIssuer;
	}
	public void setPersonalLoanUnderBotIssuer(BigDecimal personalLoanUnderBotIssuer) {
		this.personalLoanUnderBotIssuer = personalLoanUnderBotIssuer;
	}
	
	public Date getDateOfSearch() {
		return dateOfSearch;
	}
	public void setDateOfSearch(Date dateOfSearch) {
		this.dateOfSearch = dateOfSearch;
	}
	public boolean  isContainAccountReportRuleCondition(String ruleId,String ruleResult) {
		if(null!=ncbAccounts){
			for(NcbAccountDataM ncbAccount : ncbAccounts){
				ArrayList<NcbAccountReportDataM> accountReports =ncbAccount.getAccountReports();
				if(null!=accountReports){
					for(NcbAccountReportDataM ncbAccountReportDataM : accountReports){
						if(null!=ncbAccountReportDataM.getRules()){
							for(NcbAccountReportRuleDataM ncbAccountReportRuleDataM : ncbAccountReportDataM.getRules()){
								String filterRuleId = ncbAccountReportRuleDataM.getRuleId();
								String filterRuleResult = ncbAccountReportRuleDataM.getRuleResult();
								if(ruleId.equals(filterRuleId) && ruleResult.equals(filterRuleResult)){
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
	public ArrayList<NcbAccountDataM>  filterNCBAccountByRule(String ruleId,String ruleResult) {
		ArrayList<NcbAccountDataM> filterNCBAccounts = new ArrayList<NcbAccountDataM>();
		if(null!=ncbAccounts){
			for(NcbAccountDataM ncbAccount : ncbAccounts){
				ArrayList<NcbAccountReportDataM> accountReports =ncbAccount.getAccountReports();
				if(null!=accountReports){
					for(NcbAccountReportDataM ncbAccountReportDataM : accountReports){
						if(null!=ncbAccountReportDataM.getRules()){
							for(NcbAccountReportRuleDataM ncbAccountReportRuleDataM : ncbAccountReportDataM.getRules()){
								String filterRuleId = ncbAccountReportRuleDataM.getRuleId();
								String filterRuleResult = ncbAccountReportRuleDataM.getRuleResult();
								if(ruleId.equals(filterRuleId) && ruleResult.equals(filterRuleResult)){
									filterNCBAccounts.add(ncbAccount);
								}
							}
						}
					}
				}
			}
		}
		return filterNCBAccounts;
	}	
	public ArrayList<NcbAccountDataM> fillterPassNCBAccountList (ArrayList<String> notInRuleIds,String notInRuleResult,String notInAccountStatus,int maxAccount,String filterAccountType){
		Collections.sort(ncbAccounts, new NcbAccountDataM());
		ArrayList<NcbAccountDataM> filterNCBAccounts = new ArrayList<NcbAccountDataM>();
		segMentValue = new ArrayList<String>();
		this.filterPassCreditCardNCBAccount(notInRuleIds,notInRuleResult,notInAccountStatus,maxAccount,filterAccountType,filterNCBAccounts);
		this.filterPassNotCreditCardNCBAccount(notInRuleIds,notInRuleResult,notInAccountStatus,maxAccount,filterAccountType,filterNCBAccounts);
		return filterNCBAccounts;
	}
	public ArrayList<NcbAccountDataM> fillterNotPassNCBAccountList (ArrayList<String> ruleIds,String ruleResult,String notInAccountStatus,int maxAccount,String filterAccountType){
		ArrayList<NcbAccountDataM> filterNotPassNCBAccounts = new ArrayList<NcbAccountDataM>();
		segMentValue = new ArrayList<String>();
		Collections.sort(ncbAccounts, new NcbAccountDataM());
		this.filterAllNotPassNotNCBAccount(ruleIds, ruleResult, filterNotPassNCBAccounts);
		this.filterPassCreditCardNCBAccount(ruleIds,ruleResult,"",maxAccount,filterAccountType,filterNotPassNCBAccounts);
		this.filterPassNotCreditCardNCBAccount(ruleIds,ruleResult,"",maxAccount,filterAccountType,filterNotPassNCBAccounts);
		return filterNotPassNCBAccounts;
	}
	
	public void  filterPassCreditCardNCBAccount(ArrayList<String> notInRuleIds,String notInRuleResult,String notInAccountStatus,int maxAccount,String filterAccountType,ArrayList<NcbAccountDataM> filterNCBAccounts) {
		if(null!=ncbAccounts){
			int count =0;
			for(NcbAccountDataM ncbAccount : ncbAccounts){
				ArrayList<NcbAccountReportDataM> accountReports =ncbAccount.getAccountReports();
				String accountStatus =ncbAccount.getAccountStatus();
				String accountType = ncbAccount.getAccountType();
				if(!notInAccountStatus.equals(accountStatus)  &&  count<maxAccount && filterAccountType.equals(accountType)){
					if(null!=accountReports){
						for(NcbAccountReportDataM ncbAccountReportDataM : accountReports){
							if(null!=ncbAccountReportDataM.getRules()){
								for(NcbAccountReportRuleDataM ncbAccountReportRuleDataM : ncbAccountReportDataM.getRules()){
									String filterRuleId = ncbAccountReportRuleDataM.getRuleId();
									String filterRuleResult = ncbAccountReportRuleDataM.getRuleResult();
									if(!notInRuleIds.contains(filterRuleId) && !notInRuleResult.equals(filterRuleResult) && !segMentValue.contains(ncbAccount.getSegmentValue())){
										filterNCBAccounts.add(ncbAccount);
										segMentValue.add(ncbAccount.getSegmentValue());
										count++;
									}
								}
							}
						}
					}else{
						filterNCBAccounts.add(ncbAccount);
						count++;
					}
			   }
			}
		}
	}	
	public void filterPassNotCreditCardNCBAccount(ArrayList<String> notInRuleIds,String notInRuleResult,String notInAccountStatus,int maxAccount,String notInAccountType,ArrayList<NcbAccountDataM> filterNCBAccounts) {
		if(null!=ncbAccounts){
			int count =0;
			for(NcbAccountDataM ncbAccount : ncbAccounts){
				ArrayList<NcbAccountReportDataM> accountReports =ncbAccount.getAccountReports();
				String accountStatus =ncbAccount.getAccountStatus();
				String accountType = ncbAccount.getAccountType();
				if(!notInAccountStatus.equals(accountStatus)  &&  count<maxAccount && !notInAccountType.equals(accountType)){
					if(null!=accountReports){
						for(NcbAccountReportDataM ncbAccountReportDataM : accountReports){
							if(null!=ncbAccountReportDataM.getRules()){
								for(NcbAccountReportRuleDataM ncbAccountReportRuleDataM : ncbAccountReportDataM.getRules()){
									String filterRuleId = ncbAccountReportRuleDataM.getRuleId();
									String filterRuleResult = ncbAccountReportRuleDataM.getRuleResult();
									if( !notInRuleIds.contains(filterRuleId) && !notInRuleResult.equals(filterRuleResult) && !segMentValue.contains(ncbAccount.getSegmentValue())){
										filterNCBAccounts.add(ncbAccount);
										segMentValue.add(ncbAccount.getSegmentValue());
										count++;
									}
								}
							}
						}
					}else{
						filterNCBAccounts.add(ncbAccount);
						count++;
					}
				}
			}
		}
	}	
	
	public void filterAllNotPassNotNCBAccount(ArrayList<String> ruleIds,String ruleResult,ArrayList<NcbAccountDataM> filterNCBAccounts) {
		if(null!=ncbAccounts){
			for(NcbAccountDataM ncbAccount : ncbAccounts){
				ArrayList<NcbAccountReportDataM> accountReports =ncbAccount.getAccountReports();
					if(null!=accountReports){
						for(NcbAccountReportDataM ncbAccountReportDataM : accountReports){
							if(null!=ncbAccountReportDataM.getRules()){
								for(NcbAccountReportRuleDataM ncbAccountReportRuleDataM : ncbAccountReportDataM.getRules()){
									String filterRuleId = ncbAccountReportRuleDataM.getRuleId();
									String filterRuleResult = ncbAccountReportRuleDataM.getRuleResult();
									if(ruleIds.contains(filterRuleId) && ruleResult.equals(filterRuleResult) && !segMentValue.contains(ncbAccount.getSegmentValue())){
										filterNCBAccounts.add(ncbAccount);
										segMentValue.add(ncbAccount.getSegmentValue());
									}
								}
							}
						}
					} 
			}
		}
	}	
}
