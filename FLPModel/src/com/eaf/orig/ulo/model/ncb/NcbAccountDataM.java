package com.eaf.orig.ulo.model.ncb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author septemwi
 *
 */
public class NcbAccountDataM implements Serializable,Cloneable,Comparator<NcbAccountDataM>{
	public NcbAccountDataM(){
		super();
	}
	private String segmentValue;	//NCB_ACCOUNT.SEGMENT_VALUE(VARCHAR2)
	private int seq;	//NCB_ACCOUNT.SEQ(NUMBER)
	private String trackingCode;	//NCB_ACCOUNT.TRACKING_CODE(VARCHAR2)
	private String collateral2;	//NCB_ACCOUNT.COLLATERAL2(VARCHAR2)
	private Date dateAccOpened;	//NCB_ACCOUNT.DATE_ACC_OPENED(DATE)
	private BigDecimal creditlimOrloanamt;	//NCB_ACCOUNT.CREDITLIM_ORLOANAMT(NUMBER)
	private Date pmtHistStartdate;	//NCB_ACCOUNT.PMT_HIST_STARTDATE(DATE)
	private String collateral3;	//NCB_ACCOUNT.COLLATERAL3(VARCHAR2)
	private String match;	//NCB_ACCOUNT.MATCH(VARCHAR2)
	private String negativeFlag;	//NCB_ACCOUNT.NEGATIVE_FLAG(VARCHAR2)
	private Date asOfDate;	//NCB_ACCOUNT.AS_OF_DATE(DATE)
	private String accountStatus;	//NCB_ACCOUNT.ACCOUNT_STATUS(VARCHAR2)
	private String loanObjective;	//NCB_ACCOUNT.LOAN_OBJECTIVE(VARCHAR2)
	private Date pmtHistEnddate;	//NCB_ACCOUNT.PMT_HIST_ENDDATE(DATE)
	private BigDecimal installAmt;	//NCB_ACCOUNT.INSTALL_AMT(NUMBER)
	private String collateral1;	//NCB_ACCOUNT.COLLATERAL1(VARCHAR2)
	private String accountNumber;	//NCB_ACCOUNT.ACCOUNT_NUMBER(VARCHAR2)
	private Date defaultDate;	//NCB_ACCOUNT.DEFAULT_DATE(DATE)
	private BigDecimal amtPastDue;	//NCB_ACCOUNT.AMT_PAST_DUE(NUMBER)
	private BigDecimal amtOwed;	//NCB_ACCOUNT.AMT_OWED(NUMBER)
	private String currencyCode;	//NCB_ACCOUNT.CURRENCY_CODE(VARCHAR2)
	private int groupSeq;	//NCB_ACCOUNT.GROUP_SEQ(NUMBER)
	private Date dateAccClosed;	//NCB_ACCOUNT.DATE_ACC_CLOSED(DATE)
	private String pmtHist1;	//NCB_ACCOUNT.PMT_HIST1(VARCHAR2)
	private String ownershipIndicator;	//NCB_ACCOUNT.OWNERSHIP_INDICATOR(VARCHAR2)
	private BigDecimal installNoofpay;	//NCB_ACCOUNT.INSTALL_NOOFPAY(NUMBER)
	private String memberCode;	//NCB_ACCOUNT.MEMBER_CODE(VARCHAR2)
	private String memberShortName;	//NCB_ACCOUNT.MEMBER_SHORT_NAME(VARCHAR2)
	private String pmtHist2;	//NCB_ACCOUNT.PMT_HIST2(VARCHAR2)
	private String creditTypeFlag;	//NCB_ACCOUNT.CREDIT_TYPE_FLAG(VARCHAR2)
	private String positiveFlag;	//NCB_ACCOUNT.POSITIVE_FLAG(VARCHAR2)
	private BigDecimal numberofCoborr;	//NCB_ACCOUNT.NUMBEROF_COBORR(NUMBER)
	private BigDecimal percentPayment;	//NCB_ACCOUNT.PERCENT_PAYMENT(NUMBER)
	private String creditCardType;	//NCB_ACCOUNT.CREDIT_CARD_TYPE(VARCHAR2)
	private String loanClass;	//NCB_ACCOUNT.LOAN_CLASS(VARCHAR2)
	private String unitModel;	//NCB_ACCOUNT.UNIT_MODEL(VARCHAR2)
	private BigDecimal installFreq;	//NCB_ACCOUNT.INSTALL_FREQ(NUMBER)
	private Date dateOfLastPmt;	//NCB_ACCOUNT.DATE_OF_LAST_PMT(DATE)
	private String unitMake;	//NCB_ACCOUNT.UNIT_MAKE(VARCHAR2)
	private Date lastDebtRestDate;	//NCB_ACCOUNT.LAST_DEBT_REST_DATE(DATE)
	private String accountType;	//NCB_ACCOUNT.ACCOUNT_TYPE(VARCHAR2)
	private String createBy;	//NCB_ACCOUNT.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//NCB_ACCOUNT.CREATE_DATE(DATE)
	private String updateBy;	//NCB_ACCOUNT.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//NCB_ACCOUNT.UPDATE_DATE(DATE)
	
	private ArrayList<NcbAccountReportDataM> accountReports;
	
	public String getSegmentValue() {
		return segmentValue;
	}
	public void setSegmentValue(String segmentValue) {
		this.segmentValue = segmentValue;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTrackingCode() {
		return trackingCode;
	}
	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}
	public String getCollateral2() {
		return collateral2;
	}
	public void setCollateral2(String collateral2) {
		this.collateral2 = collateral2;
	}
	public Date getDateAccOpened() {
		return dateAccOpened;
	}
	public void setDateAccOpened(Date dateAccOpened) {
		this.dateAccOpened = dateAccOpened;
	}
	public BigDecimal getCreditlimOrloanamt() {
		return creditlimOrloanamt;
	}
	public void setCreditlimOrloanamt(BigDecimal creditlimOrloanamt) {
		this.creditlimOrloanamt = creditlimOrloanamt;
	}
	public Date getPmtHistStartdate() {
		return pmtHistStartdate;
	}
	public void setPmtHistStartdate(Date pmtHistStartdate) {
		this.pmtHistStartdate = pmtHistStartdate;
	}
	public String getCollateral3() {
		return collateral3;
	}
	public void setCollateral3(String collateral3) {
		this.collateral3 = collateral3;
	}
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public String getNegativeFlag() {
		return negativeFlag;
	}
	public void setNegativeFlag(String negativeFlag) {
		this.negativeFlag = negativeFlag;
	}
	public Date getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(Date asOfDate) {
		this.asOfDate = asOfDate;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getLoanObjective() {
		return loanObjective;
	}
	public void setLoanObjective(String loanObjective) {
		this.loanObjective = loanObjective;
	}
	public Date getPmtHistEnddate() {
		return pmtHistEnddate;
	}
	public void setPmtHistEnddate(Date pmtHistEnddate) {
		this.pmtHistEnddate = pmtHistEnddate;
	}
	public BigDecimal getInstallAmt() {
		return installAmt;
	}
	public void setInstallAmt(BigDecimal installAmt) {
		this.installAmt = installAmt;
	}
	public String getCollateral1() {
		return collateral1;
	}
	public void setCollateral1(String collateral1) {
		this.collateral1 = collateral1;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Date getDefaultDate() {
		return defaultDate;
	}
	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}
	public BigDecimal getAmtPastDue() {
		return amtPastDue;
	}
	public void setAmtPastDue(BigDecimal amtPastDue) {
		this.amtPastDue = amtPastDue;
	}
	public BigDecimal getAmtOwed() {
		return amtOwed;
	}
	public void setAmtOwed(BigDecimal amtOwed) {
		this.amtOwed = amtOwed;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public int getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(int groupSeq) {
		this.groupSeq = groupSeq;
	}
	public Date getDateAccClosed() {
		return dateAccClosed;
	}
	public void setDateAccClosed(Date dateAccClosed) {
		this.dateAccClosed = dateAccClosed;
	}
	public String getPmtHist1() {
		return pmtHist1;
	}
	public void setPmtHist1(String pmtHist1) {
		this.pmtHist1 = pmtHist1;
	}
	public String getOwnershipIndicator() {
		return ownershipIndicator;
	}
	public void setOwnershipIndicator(String ownershipIndicator) {
		this.ownershipIndicator = ownershipIndicator;
	}
	public BigDecimal getInstallNoofpay() {
		return installNoofpay;
	}
	public void setInstallNoofpay(BigDecimal installNoofpay) {
		this.installNoofpay = installNoofpay;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberShortName() {
		return memberShortName;
	}
	public void setMemberShortName(String memberShortName) {
		this.memberShortName = memberShortName;
	}
	public String getPmtHist2() {
		return pmtHist2;
	}
	public void setPmtHist2(String pmtHist2) {
		this.pmtHist2 = pmtHist2;
	}
	public String getCreditTypeFlag() {
		return creditTypeFlag;
	}
	public void setCreditTypeFlag(String creditTypeFlag) {
		this.creditTypeFlag = creditTypeFlag;
	}
	public String getPositiveFlag() {
		return positiveFlag;
	}
	public void setPositiveFlag(String positiveFlag) {
		this.positiveFlag = positiveFlag;
	}
	public BigDecimal getNumberofCoborr() {
		return numberofCoborr;
	}
	public void setNumberofCoborr(BigDecimal numberofCoborr) {
		this.numberofCoborr = numberofCoborr;
	}
	public BigDecimal getPercentPayment() {
		return percentPayment;
	}
	public void setPercentPayment(BigDecimal percentPayment) {
		this.percentPayment = percentPayment;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getLoanClass() {
		return loanClass;
	}
	public void setLoanClass(String loanClass) {
		this.loanClass = loanClass;
	}
	public String getUnitModel() {
		return unitModel;
	}
	public void setUnitModel(String unitModel) {
		this.unitModel = unitModel;
	}
	public BigDecimal getInstallFreq() {
		return installFreq;
	}
	public void setInstallFreq(BigDecimal installFreq) {
		this.installFreq = installFreq;
	}
	public Date getDateOfLastPmt() {
		return dateOfLastPmt;
	}
	public void setDateOfLastPmt(Date dateOfLastPmt) {
		this.dateOfLastPmt = dateOfLastPmt;
	}
	public String getUnitMake() {
		return unitMake;
	}
	public void setUnitMake(String unitMake) {
		this.unitMake = unitMake;
	}
	public Date getLastDebtRestDate() {
		return lastDebtRestDate;
	}
	public void setLastDebtRestDate(Date lastDebtRestDate) {
		this.lastDebtRestDate = lastDebtRestDate;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
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
	public ArrayList<NcbAccountReportDataM> getAccountReports() {
		return accountReports;
	}
	public void setAccountReports(ArrayList<NcbAccountReportDataM> accountReports) {
		this.accountReports = accountReports;
	}	
	public boolean isAccountReportGreaterThan1Year(String flagYes) {
		if(null!=accountReports){
			for(NcbAccountReportDataM ncbAccountReportDataM : accountReports){
				if(flagYes.equals(ncbAccountReportDataM.getRestructureGT1Yr())){
					return true;
				}
			}
		}
		return false;
	}
	public boolean isAccountReportNPL(String ruleResult, String ncbRuleResult) {
		if(null!=accountReports && (null!=ruleResult && !"".equals(ruleResult))){
			for(NcbAccountReportDataM ncbAccountReportDataM : accountReports){
				if(null!=ncbAccountReportDataM.getRules()){
					for(NcbAccountReportRuleDataM ncbAccountReportRule : ncbAccountReportDataM.getRules()){
						if(ruleResult.equals(ncbAccountReportRule.getRuleId()) && ncbRuleResult.equals(ncbAccountReportRule.getRuleResult())){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	@Override
	public int compare(NcbAccountDataM o1, NcbAccountDataM o2) {
		int compareNum=0;
		 compareNum = compareCreditlimOrloanamt(o1,o2);			   
       if (compareNum != 0) return compareNum;
       return compareNum;
	}
	
	private  int compareCreditlimOrloanamt(NcbAccountDataM obj1 ,NcbAccountDataM obj2){ 
		try{
			if(null != obj1 && null !=  obj2 ){
				NcbAccountDataM NcbAccount1 = (NcbAccountDataM)obj1;
				NcbAccountDataM NcbAccount2 = (NcbAccountDataM)obj2;
				BigDecimal  creditlimOrloanamt1 = NcbAccount1.getCreditlimOrloanamt();
				if(null==creditlimOrloanamt1){creditlimOrloanamt1=BigDecimal.ZERO;}
				BigDecimal creditlimOrloanamt2 = NcbAccount2.getCreditlimOrloanamt();
				if(null==creditlimOrloanamt2){creditlimOrloanamt2=BigDecimal.ZERO; }
				int result = creditlimOrloanamt2.compareTo(creditlimOrloanamt1);
			    return result;
			}
		}catch(Exception e){
			return 0;
		}
		return 0;
	}
}
