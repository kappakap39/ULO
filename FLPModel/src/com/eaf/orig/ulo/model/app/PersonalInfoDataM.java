package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;

@SuppressWarnings("serial")
public class PersonalInfoDataM implements Serializable,Cloneable,Comparator<Object> {
	public PersonalInfoDataM(){
		super();
	}
	public class PersonalType{
		public static final String APPLICANT = "A";
		public static final String GUARANTOR = "G";
		public static final String SUPPLEMENTARY = "S";
		public static final String INFO = "I";
	}
	private String sortType;
	public class SORT_TYPE{
		public static final String ASC = "ASC";
		public static final String DESC = "DESC";
	}
	public class PREFIX_CREDIT_CARD{
		public static final String PREFIX_THAIBEV = "19";
	}
	private String applicationGroupId;	//ORIG_PERSONAL_INFO.APPLICATION_GROUP_ID(VARCHAR2)
	private String personalId;	//ORIG_PERSONAL_INFO.PERSONAL_ID(VARCHAR2)
	private String gender;	//ORIG_PERSONAL_INFO.GENDER(VARCHAR2)
	private String kGroupFlag;	//ORIG_PERSONAL_INFO.K_GROUP_FLAG(VARCHAR2)
	private String matchAddrCardlinkFlag;	//ORIG_PERSONAL_INFO.MATCH_ADDR_CARDLINK_FLAG(VARCHAR2)
	private String mCompany;	//ORIG_PERSONAL_INFO.M_COMPANY(VARCHAR2)
	private String businessTypeOther;	//ORIG_PERSONAL_INFO.BUSINESS_TYPE_OTHER(VARCHAR2)
	private String positionCode;	//ORIG_PERSONAL_INFO.POSITION_CODE(VARCHAR2)
	private BigDecimal monthlyExpense;	//ORIG_PERSONAL_INFO.MONTHLY_EXPENSE(NUMBER)
	private String race;	//ORIG_PERSONAL_INFO.RACE(VARCHAR2)
	private String disclosureFlag;	//ORIG_PERSONAL_INFO.DISCLOSURE_FLAG(VARCHAR2)
	private String prevCompany;	//ORIG_PERSONAL_INFO.PREV_COMPANY(VARCHAR2)
	private String idno;	//ORIG_PERSONAL_INFO.IDNO(VARCHAR2)
	private String relationshipTypeDesc;	//ORIG_PERSONAL_INFO.RELATIONSHIP_TYPE_DESC(VARCHAR2)
	private String occupation;	//ORIG_PERSONAL_INFO.OCCUPATION(VARCHAR2)
	private String occpationOther; //ORIG_PERSONAL_INFO.OCCUPATION_OTHER(VARCHAR2)
	private String branchReceiveCard;	//ORIG_PERSONAL_INFO.BRANCH_RECEIVE_CARD(VARCHAR2)
	private String sorceOfIncomeOther;	//ORIG_PERSONAL_INFO.SORCE_OF_INCOME_OTHER(VARCHAR2)
	private String married;	//ORIG_PERSONAL_INFO.MARRIED(VARCHAR2)
	private String srcOthIncBonus;	//ORIG_PERSONAL_INFO.SRC_OTH_INC_BONUS(VARCHAR2)
	private String prevCompanyPhoneNo;	//ORIG_PERSONAL_INFO.PREV_COMPANY_PHONE_NO(VARCHAR2)
	private BigDecimal freelanceIncome;	//ORIG_PERSONAL_INFO.FREELANCE_INCOME(NUMBER)
	private String nationality;	//ORIG_PERSONAL_INFO.NATIONALITY(VARCHAR2)
	private String enFirstName;	//ORIG_PERSONAL_INFO.EN_FIRST_NAME(VARCHAR2)
	private String professionOther;	//ORIG_PERSONAL_INFO.PROFESSION_OTHER(VARCHAR2)
	private String customerType;	//ORIG_PERSONAL_INFO.CUSTOMER_TYPE(VARCHAR2)
	private BigDecimal assetsAmount;	//ORIG_PERSONAL_INFO.ASSETS_AMOUNT(NUMBER)
	private String businessType;	//ORIG_PERSONAL_INFO.BUSINESS_TYPE(VARCHAR2)
	private Date visaExpDate;	//ORIG_PERSONAL_INFO.VISA_EXP_DATE(DATE)
	private String establishmentAddrFlag;	//ORIG_PERSONAL_INFO.ESTABLISHMENT_ADDR_FLAG(VARCHAR2)
	private String mPosition;	//ORIG_PERSONAL_INFO.M_POSITION(VARCHAR2)
	private BigDecimal netProfitIncome;	//ORIG_PERSONAL_INFO.NET_PROFIT_INCOME(NUMBER)
	private String enTitleCode;	//ORIG_PERSONAL_INFO.EN_TITLE_CODE(VARCHAR2)
	private String mOfficeTel;	//ORIG_PERSONAL_INFO.M_OFFICE_TEL(VARCHAR2)
	private String receiveIncomeBankBranch;	//ORIG_PERSONAL_INFO.RECEIVE_INCOME_BANK_BRANCH(VARCHAR2)
	private String workPermitNo;	//ORIG_PERSONAL_INFO.WORK_PERMIT_NO(VARCHAR2)
	private BigDecimal totWorkMonth;	//ORIG_PERSONAL_INFO.TOT_WORK_MONTH(NUMBER)
	private BigDecimal prevWorkMonth;	//ORIG_PERSONAL_INFO.PREV_WORK_MONTH(NUMBER)
	private BigDecimal prevWorkYear;	//ORIG_PERSONAL_INFO.PREV_WORK_YEAR(NUMBER)
	private String profession;	//ORIG_PERSONAL_INFO.PROFESSION(VARCHAR2)
	private BigDecimal monthlyIncome;	//ORIG_PERSONAL_INFO.MONTHLY_INCOME(NUMBER)
	private String incPolicySegmentCode;	//ORIG_PERSONAL_INFO.INC_POLICY_SEGMENT_CODE(VARCHAR2)
	private String thFirstName;	//ORIG_PERSONAL_INFO.TH_FIRST_NAME(VARCHAR2)
	private String thLastName;	//ORIG_PERSONAL_INFO.TH_LAST_NAME(VARCHAR2)
//	private String contactTimeFrom;	//ORIG_PERSONAL_INFO.CONTACT_TIME_FROM(VARCHAR2)
	private Date cidExpDate;	//ORIG_PERSONAL_INFO.CID_EXP_DATE(DATE)
	private Date cidIssueDate;  //ORIG_PERSONAL_INFO.CID_ISSUE_DATE(DATE)
	private String mThFirstName;	//ORIG_PERSONAL_INFO.M_TH_FIRST_NAME(VARCHAR2)
	private Date workPermitExpDate;	//ORIG_PERSONAL_INFO.WORK_PERMIT_EXP_DATE(DATE)
	private String employmentType;	//ORIG_PERSONAL_INFO.EMPLOYMENT_TYPE(VARCHAR2)
	private String emailSecondary;	//ORIG_PERSONAL_INFO.EMAIL_SECONDARY(VARCHAR2)
	private String mTitleCode;	//ORIG_PERSONAL_INFO.M_TITLE_CODE(VARCHAR2)
	private String srcOthIncCommission;	//ORIG_PERSONAL_INFO.SRC_OTH_INC_COMMISSION(VARCHAR2)
	private BigDecimal otherIncome;	//ORIG_PERSONAL_INFO.OTHER_INCOME(NUMBER)
	private BigDecimal savingIncome;	//ORIG_PERSONAL_INFO.SAVING_INCOME(NUMBER)
	private BigDecimal debtBurden;	//ORIG_PERSONAL_INFO.DEBT_BURDEN(NUMBER)
	private BigDecimal grossIncome;	//ORIG_PERSONAL_INFO.GROSS_INCOME(NUMBER)
	private String srcOthIncOther;	//ORIG_PERSONAL_INFO.SRC_OTH_INC_OTHER(VARCHAR2)
	private String emailPrimary;	//ORIG_PERSONAL_INFO.EMAIL_PRIMARY(VARCHAR2)
	private String enMidName;	//ORIG_PERSONAL_INFO.EN_MID_NAME(VARCHAR2)
	private String thTitleCode;	//ORIG_PERSONAL_INFO.TH_TITLE_CODE(VARCHAR2)
	private String mHomeTel;	//ORIG_PERSONAL_INFO.M_HOME_TEL(VARCHAR2)
	private String visaType;	//ORIG_PERSONAL_INFO.VISA_TYPE(VARCHAR2)
	private String degree;	//ORIG_PERSONAL_INFO.DEGREE(VARCHAR2)
	private String placeReceiveCard;	//ORIG_PERSONAL_INFO.PLACE_RECEIVE_CARD(VARCHAR2)
	private String relationshipType;	//ORIG_PERSONAL_INFO.RELATIONSHIP_TYPE(VARCHAR2)
//	private String contactTimeTo;	//ORIG_PERSONAL_INFO.CONTACT_TIME_TO(VARCHAR2)
	private String prevCompanyTitle;	//ORIG_PERSONAL_INFO.PREV_COMPANY_TITLE(VARCHAR2)
	private String enLastName;	//ORIG_PERSONAL_INFO.EN_LAST_NAME(VARCHAR2)
	private String staffFlag;	//ORIG_PERSONAL_INFO.STAFF_FLAG(VARCHAR2)
	private String cisNo;	//ORIG_PERSONAL_INFO.CIS_NO(VARCHAR2)
	private String matchAddrNcbFlag;	//ORIG_PERSONAL_INFO.MATCH_ADDR_NCB_FLAG(VARCHAR2)
	private String positionDesc;	//ORIG_PERSONAL_INFO.POSITION_DESC(VARCHAR2)
	private String mailingAddress;	//ORIG_PERSONAL_INFO.MAILING_ADDRESS(VARCHAR2)
	private String personalType;	//ORIG_PERSONAL_INFO.PERSONAL_TYPE(VARCHAR2)
	private String mThOldLastName;	//ORIG_PERSONAL_INFO.M_TH_OLD_LAST_NAME(VARCHAR2)
	private String vatRegistFlag;	//ORIG_PERSONAL_INFO.VAT_REGIST_FLAG(VARCHAR2)
	private Date birthDate;	//ORIG_PERSONAL_INFO.BIRTH_DATE(DATE)
	private BigDecimal salary;	//ORIG_PERSONAL_INFO.SALARY(NUMBER)
	private String positionLevel;	//ORIG_PERSONAL_INFO.POSITION_LEVEL(VARCHAR2)
	private String mThLastName;	//ORIG_PERSONAL_INFO.M_TH_LAST_NAME(VARCHAR2)
	private BigDecimal circulationIncome;	//ORIG_PERSONAL_INFO.CIRCULATION_INCOME(NUMBER)
	private BigDecimal hrIncome;	//ORIG_PERSONAL_INFO.HR_INCOME(NUMBER)
	private BigDecimal mIncome;	//ORIG_PERSONAL_INFO.M_INCOME(NUMBER)
	private String receiveIncomeMethod;	//ORIG_PERSONAL_INFO.RECEIVE_INCOME_METHOD(VARCHAR2)
	private String cidType;	//ORIG_PERSONAL_INFO.CID_TYPE(VARCHAR2)
	private BigDecimal totWorkYear;	//ORIG_PERSONAL_INFO.TOT_WORK_YEAR(NUMBER)
	private String mobileNo;	//ORIG_PERSONAL_INFO.MOBILE_NO(VARCHAR2)
	private String businessNature;	//ORIG_PERSONAL_INFO.BUSINESS_NATURE(VARCHAR2)
	private BigDecimal hrBonus;	//ORIG_PERSONAL_INFO.HR_BONUS(NUMBER)
	private String mPhoneNo;	//ORIG_PERSONAL_INFO.M_PHONE_NO(VARCHAR2)
	private String thMidName;	//ORIG_PERSONAL_INFO.TH_MID_NAME(VARCHAR2)
	private BigDecimal totVerifiedIncome;	//ORIG_PERSONAL_INFO.TOT_VERIFIED_INCOME(NUMBER)
	private String receiveIncomeBank;	//ORIG_PERSONAL_INFO.RECEIVE_INCOME_BANK(VARCHAR2)
	private String createBy;	//ORIG_PERSONAL_INFO.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_PERSONAL_INFO.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_PERSONAL_INFO.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_PERSONAL_INFO.UPDATE_DATE(DATE)
	private int seq;
	private BigDecimal noOfChild; //ORIG_PERSONAL_INFO.NO_OF_CHILD(NUMBER)
	private String contactTime; //ORIG_PERSONAL_INFO.CONTACT_TIME(VARCHAR2)
	private String typeOfFin; //ORIG_PERSONAL_INFO.TYPE_OF_FIN(VARCHAR2)
	private String referCriteria; //ORIG_PERSONAL_INFO.REFER_CRITERIA(VARCHAR2)
	private String cisPrimSegment; //ORIG_PERSONAL_INFO.CIS_PRIM_SEGMENT(VARCHAR2)
	private String cisPrimSubSegment; //ORIG_PERSONAL_INFO.CIS_PRIM_SUB_SEGMENT(VARCHAR2)
	private String cisSecSegment; //ORIG_PERSONAL_INFO.CIS_SEC_SEGMENT(VARCHAR2)
	private String cisSecSubSegment; //ORIG_PERSONAL_INFO.CIS_SEC_SUB_SEGMENT(VARCHAR2)
	private String busRegistId; //ORIG_PERSONAL_INFO.BUS_REGIST_ID(VARCHAR2)
	private Date busRegistDate; //ORIG_PERSONAL_INFO.BUS_REGIST_DATE(DATE)
	private String busRegistReqNo; //ORIG_PERSONAL_INFO.BUS_REGIST_REQ_NO(VARCHAR2)
	private String noMainCard; //ORIG_PERSONAL_INFO.NO_MAIN_CARD(NUMBER)
	private String noSupCard; //ORIG_PERSONAL_INFO.NO_SUP_CARD(NUMBER)
	private String permanentStaff; //ORIG_PERSONAL_INFO.PERMANENT_STAFF(VARCHAR2)
	private String sorceOfIncome; //ORIG_PERSONAL_INFO.SORCE_OF_INCOME(VARCHAR2)
	private String phoneNoBol; //ORIG_PERSONAL_INFO.PHONE_NO_BOL(VARCHAR2)
	private String freelanceType; //ORIG_PERSONAL_INFO.FREELANCE_TYPE(VARCHAR2)
	private String companyCISId; //ORIG_PERSONAL_INFO.COMPANY_CIS_ID(VARCHAR2)
	private String companyOrgIdBol; //ORIG_PERSONAL_INFO.COMPANY_ORG_ID_BOL(VARCHAR2)
	private String displayEditBTN;// 
	private String thTitleDesc; //ORIG_PERSONAL_INFO.TH_TITLE_DESC(VARCHAR2)
	private String enTitleDesc; //ORIG_PERSONAL_INFO.EN_TITLE_DESC(VARCHAR2)
	private String mTitleDesc; //ORIG_PERSONAL_INFO.M_TITLE_DESC(VARCHAR2)
	private String bureauRequiredFlag; //ORIG_PERSONAL_INFO.BUREAU_REQUIRED_FLAG(VARCHAR2)
	private String pegaPhoneType; //ORIG_PERSONAL_INFO.PEGA_PHONE_TYPE(VARCHAR2)
	private String pegaPhoneNo; //ORIG_PERSONAL_INFO.PEGA_PHONE_NO(VARCHAR2)
	private String pegaPhoneExt; //ORIG_PERSONAL_INFO.PEGA_PHONE_EXT(VARCHAR2)
	private String completeData; //ORIG_PERSONAL_INFO.COMPLETE_DATA(VARCHAR2)
	private String procedureTypeOfIncome; //ORIG_PERSONAL_INFO.PROCEDURE_TYPE_INCOME(VARCHAR2)
	private String branchReceiveCardName; //ORIG_PERSONAL_INFO.BRANCH_RECEIVE_CARD_NAME(VACHAR2)
	private String personalError; //ORIG_PERSONAL_INFO.PERSONAL_ERROR(VACHAR2)
	private String mainCardNo; //ORIG_PERSONAL_INFO.MAIN_CARD_NO(VACHAR2)
	private String mainCardHolderName; //ORIG_PERSONAL_INFO.MAIN_CARD_HOLDER_NAME(VACHAR2)
	private String consentModelFlag; //ORIG_PERSONAL_INFO.CONSENT_MODEL_FLAG(VACHAR2)
	private BigDecimal numberOfIssuer; //ORIG_PERSONAL_INFO.NUMBER_OF_ISSUER(NUMBER)
	private BigDecimal selfDeclareLimit; //ORIG_PERSONAL_INFO.SELF_DECLARE_LIMIT(NUMBER)
	private String cisPrimDesc; //ORIG_PERSONAL_INFO.CIS_PRIM_DESC(VACHAR2)
	private String cisPrimSubDesc; //ORIG_PERSONAL_INFO.CIS_PRIM_SUB_DESC(VACHAR2)
	private String cisSecDesc; //ORIG_PERSONAL_INFO.CIS_SEC_DESC(VACHAR2)
	private String cisSecSubDesc; //ORIG_PERSONAL_INFO.CIS_SEC_SUB_DESC(VACHAR2)
	private String lineId; //ORIG_PERSONAL_INFO.LINE_ID(VACHAR2)
	private String incomeResource; //ORIG_PERSONAL_INFO.INCOME_RESOURCE(VACHAR2)
	private String incomeResourceOther; //ORIG_PERSONAL_INFO.INCOME_RESOURCE_OTHER(VACHAR2)
	private String countryOfIncome; //ORIG_PERSONAL_INFO.COUNTRY_OF_INCOME(VACHAR2)
	
	private ArrayList<IncomeInfoDataM> incomeInfos;
	private ArrayList<IncomeSourceDataM> incomeSources;
	private ArrayList<CardlinkCustomerDataM> cardLinkCustomers;
	private ArrayList<CardlinkCardDataM> cardLinkCards;
	private KYCDataM kyc;
	private ArrayList<NCBDocumentDataM> ncbDocuments;
	private ArrayList<NCBDocumentHistoryDataM> ncbDocumentHistorys;
	private ArrayList<AddressDataM> addresses = new ArrayList<AddressDataM>();
	private VerificationResultDataM verificationResult;
//	private ArrayList<DocumentCheckListDataM> documentCheckLists;
	private ArrayList<DebtInfoDataM> debtInfos;
	private ArrayList<PersonalRelationDataM> personalRelations;
	private ArrayList<NcbInfoDataM> ncbInfos;
	private String emailIdRef;//EMAIL_ID_REF
	private String mobileIdRef; //MOBILE_ID_REF
	private String linkFirstName;
	private String linkLastName;
	private ArrayList<String> verCusPhoneNo = new ArrayList<String>();
	private String cisCustType; //ORIG_PERSONAL_INFO.CIS_CUST_TYPE(VARCHAR2)
	private String idNoConsent; // ORIG_PERSONAL_INFO.ID_NO_CONSENT(VARCHAR2(20 CHAR))
	
	private ArrayList<BScoreDataM> bScores; // ORIG_PERSONAL_INFO.B_SCORE(VARCHAR2(400 CHAR))
	private ArrayList<AccountDataM> accounts;
	//KPL Additional
	private String specialMerchantType;	//ORIG_PERSONAL_INFO.COMPANY_TYPE(VARCHAR2)
	private String payrollAccountNo;
	private BigDecimal applicationIncome;
	
	private String ePersonalId;
	private String ncbTranNo;
	private String consentRefNo;
	private Date consentDate;
	private Date birthDateConsent;	
	private String placeConsent;
	private String witnessConsent;
	
	// CR247
	private String payrollDate;
	
	public String getPayrollAccountNo() {
		return payrollAccountNo;
	}
	public void setPayrollAccountNo(String payrollAccountNo) {
		this.payrollAccountNo = payrollAccountNo;
	}
	public String getSpecialMerchantType() {
		return specialMerchantType;
	}
	public void setSpecialMerchantType(String specialMerchantType) {
		this.specialMerchantType = specialMerchantType;
	}
	public ArrayList<String> getVerCusPhoneNo() {
		return verCusPhoneNo;
	}
	public void setVerCusPhoneNo(ArrayList<String> verCusPhoneNo) {
		this.verCusPhoneNo = verCusPhoneNo;
	}
	public void addVerCusPhoneNo(String verCusPhoneNo){
		if(this.verCusPhoneNo == null){
			this.verCusPhoneNo = new ArrayList<String>();
		}
		this.verCusPhoneNo.add(verCusPhoneNo);
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
	public String getLinkFirstName() {
		return linkFirstName;
	}
	public void setLinkFirstName(String linkFirstName) {
		this.linkFirstName = linkFirstName;
	}
	public String getLinkLastName() {
		return linkLastName;
	}
	public void setLinkLastName(String linkLastName) {
		this.linkLastName = linkLastName;
	}
	public String getBranchReceiveCardName() {
		return branchReceiveCardName;
	}
	public void setBranchReceiveCardName(String branchReceiveCardName) {
		this.branchReceiveCardName = branchReceiveCardName;
	}
	public String getCompleteData() {
		return completeData;
	}
	public void setCompleteData(String completeData) {
		this.completeData = completeData;
	}
	public String getBusRegistId() {
		return busRegistId;
	}
	public void setBusRegistId(String busRegistId) {
		this.busRegistId = busRegistId;
	}
	public Date getBusRegistDate() {
		return busRegistDate;
	}
	public void setBusRegistDate(Date busRegistDate) {
		this.busRegistDate = busRegistDate;
	}
	public String getBusRegistReqNo() {
		return busRegistReqNo;
	}
	public void setBusRegistReqNo(String busRegistReqNo) {
		this.busRegistReqNo = busRegistReqNo;
	}
	public String getApplicationGroupId() {
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId) {
		this.applicationGroupId = applicationGroupId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getkGroupFlag() {
		return kGroupFlag;
	}
	public void setkGroupFlag(String kGroupFlag) {
		this.kGroupFlag = kGroupFlag;
	}
	public String getMatchAddrCardlinkFlag() {
		return matchAddrCardlinkFlag;
	}
	public void setMatchAddrCardlinkFlag(String matchAddrCardlinkFlag) {
		this.matchAddrCardlinkFlag = matchAddrCardlinkFlag;
	}
	public String getmCompany() {
		return mCompany;
	}
	public Date getCidIssueDate() {
		return cidIssueDate;
	}
	public void setCidIssueDate(Date cidIssueDate) {
		this.cidIssueDate = cidIssueDate;
	}
	public void setmCompany(String mCompany) {
		this.mCompany = mCompany;
	}
	public String getBusinessTypeOther() {
		return businessTypeOther;
	}
	public void setBusinessTypeOther(String businessTypeOther) {
		this.businessTypeOther = businessTypeOther;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public BigDecimal getMonthlyExpense() {
		return monthlyExpense;
	}
	public void setMonthlyExpense(BigDecimal monthlyExpense) {
		this.monthlyExpense = monthlyExpense;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getDisclosureFlag() {
		return disclosureFlag;
	}
	public void setDisclosureFlag(String disclosureFlag) {
		this.disclosureFlag = disclosureFlag;
	}
	public String getPrevCompany() {
		return prevCompany;
	}
	public void setPrevCompany(String prevCompany) {
		this.prevCompany = prevCompany;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}	
	public String getOccpationOther() {
		return occpationOther;
	}
	public void setOccpationOther(String occpationOther) {
		this.occpationOther = occpationOther;
	}
	public String getBranchReceiveCard() {
		return branchReceiveCard;
	}
	public void setBranchReceiveCard(String branchReceiveCard) {
		this.branchReceiveCard = branchReceiveCard;
	}
	public String getSorceOfIncomeOther() {
		return sorceOfIncomeOther;
	}
	public void setSorceOfIncomeOther(String sorceOfIncomeOther) {
		this.sorceOfIncomeOther = sorceOfIncomeOther;
	}
	public String getMarried() {
		return married;
	}
	public void setMarried(String married) {
		this.married = married;
	}
	public String getPrevCompanyPhoneNo() {
		return prevCompanyPhoneNo;
	}
	public void setPrevCompanyPhoneNo(String prevCompanyPhoneNo) {
		this.prevCompanyPhoneNo = prevCompanyPhoneNo;
	}
	public BigDecimal getFreelanceIncome() {
		return freelanceIncome;
	}
	public void setFreelanceIncome(BigDecimal freelanceIncome) {
		this.freelanceIncome = freelanceIncome;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getEnFirstName() {
		return enFirstName;
	}
	public void setEnFirstName(String enFirstName) {
		this.enFirstName = enFirstName;
	}
	public String getProfessionOther() {
		return professionOther;
	}
	public void setProfessionOther(String professionOther) {
		this.professionOther = professionOther;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public BigDecimal getAssetsAmount() {
		return assetsAmount;
	}
	public void setAssetsAmount(BigDecimal assetsAmount) {
		this.assetsAmount = assetsAmount;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Date getVisaExpDate() {
		return visaExpDate;
	}
	public void setVisaExpDate(Date visaExpDate) {
		this.visaExpDate = visaExpDate;
	}
	public String getEstablishmentAddrFlag() {
		return establishmentAddrFlag;
	}
	public void setEstablishmentAddrFlag(String establishmentAddrFlag) {
		this.establishmentAddrFlag = establishmentAddrFlag;
	}
	public String getmPosition() {
		return mPosition;
	}
	public void setmPosition(String mPosition) {
		this.mPosition = mPosition;
	}
	public BigDecimal getNetProfitIncome() {
		return netProfitIncome;
	}
	public void setNetProfitIncome(BigDecimal netProfitIncome) {
		this.netProfitIncome = netProfitIncome;
	}
	public String getEnTitleCode() {
		return enTitleCode;
	}
	public void setEnTitleCode(String enTitleCode) {
		this.enTitleCode = enTitleCode;
	}
	public String getmOfficeTel() {
		return mOfficeTel;
	}
	public void setmOfficeTel(String mOfficeTel) {
		this.mOfficeTel = mOfficeTel;
	}
	public String getWorkPermitNo() {
		return workPermitNo;
	}
	public void setWorkPermitNo(String workPermitNo) {
		this.workPermitNo = workPermitNo;
	}
	public BigDecimal getTotWorkMonth() {
		return totWorkMonth;
	}
	public void setTotWorkMonth(BigDecimal totWorkMonth) {
		this.totWorkMonth = totWorkMonth;
	}
	public BigDecimal getPrevWorkMonth() {
		return prevWorkMonth;
	}
	public void setPrevWorkMonth(BigDecimal prevWorkMonth) {
		this.prevWorkMonth = prevWorkMonth;
	}
	public BigDecimal getPrevWorkYear() {
		return prevWorkYear;
	}
	public void setPrevWorkYear(BigDecimal prevWorkYear) {
		this.prevWorkYear = prevWorkYear;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
//	public String getContactTimeFrom() {
//		return contactTimeFrom;
//	}
//	public void setContactTimeFrom(String contactTimeFrom) {
//		this.contactTimeFrom = contactTimeFrom;
//	}
	public Date getCidExpDate() {
		return cidExpDate;
	}
	public void setCidExpDate(Date cidExpDate) {
		this.cidExpDate = cidExpDate;
	}
	public String getmThFirstName() {
		return mThFirstName;
	}
	public void setmThFirstName(String mThFirstName) {
		this.mThFirstName = mThFirstName;
	}
	public Date getWorkPermitExpDate() {
		return workPermitExpDate;
	}
	public void setWorkPermitExpDate(Date workPermitExpDate) {
		this.workPermitExpDate = workPermitExpDate;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getEmailSecondary() {
		return emailSecondary;
	}
	public void setEmailSecondary(String emailSecondary) {
		this.emailSecondary = emailSecondary;
	}
	public String getmTitleCode() {
		return mTitleCode;
	}
	public void setmTitleCode(String mTitleCode) {
		this.mTitleCode = mTitleCode;
	}
	public BigDecimal getOtherIncome() {
		return otherIncome;
	}
	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}
	public BigDecimal getSavingIncome() {
		return savingIncome;
	}
	public void setSavingIncome(BigDecimal savingIncome) {
		this.savingIncome = savingIncome;
	}
	public String getEmailPrimary() {
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary) {
		this.emailPrimary = emailPrimary;
	}
	public String getEnMidName() {
		return enMidName;
	}
	public void setEnMidName(String enMidName) {
		this.enMidName = enMidName;
	}
	public String getThTitleCode() {
		return thTitleCode;
	}
	public void setThTitleCode(String thTitleCode) {
		this.thTitleCode = thTitleCode;
	}
	public String getmHomeTel() {
		return mHomeTel;
	}
	public void setmHomeTel(String mHomeTel) {
		this.mHomeTel = mHomeTel;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getPlaceReceiveCard() {
		return placeReceiveCard;
	}
	public void setPlaceReceiveCard(String placeReceiveCard) {
		this.placeReceiveCard = placeReceiveCard;
	}
//	public String getContactTimeTo() {
//		return contactTimeTo;
//	}
//	public void setContactTimeTo(String contactTimeTo) {
//		this.contactTimeTo = contactTimeTo;
//	}
	public String getPrevCompanyTitle() {
		return prevCompanyTitle;
	}
	public void setPrevCompanyTitle(String prevCompanyTitle) {
		this.prevCompanyTitle = prevCompanyTitle;
	}
	public String getEnLastName() {
		return enLastName;
	}
	public void setEnLastName(String enLastName) {
		this.enLastName = enLastName;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getMatchAddrNcbFlag() {
		return matchAddrNcbFlag;
	}
	public void setMatchAddrNcbFlag(String matchAddrNcbFlag) {
		this.matchAddrNcbFlag = matchAddrNcbFlag;
	}
	public String getPositionDesc() {
		return positionDesc;
	}
	public void setPositionDesc(String positionDesc) {
		this.positionDesc = positionDesc;
	}
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public String getPersonalType() {
		return personalType;
	}
	public void setPersonalType(String personalType) {
		this.personalType = personalType;
	}
	public String getmThOldLastName() {
		return mThOldLastName;
	}
	public void setmThOldLastName(String mThOldLastName) {
		this.mThOldLastName = mThOldLastName;
	}
	public String getVatRegistFlag() {
		return vatRegistFlag;
	}
	public void setVatRegistFlag(String vatRegistFlag) {
		this.vatRegistFlag = vatRegistFlag;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public String getPositionLevel() {
		return positionLevel;
	}
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}
	public String getmThLastName() {
		return mThLastName;
	}
	public void setmThLastName(String mThLastName) {
		this.mThLastName = mThLastName;
	}
	public BigDecimal getCirculationIncome() {
		return circulationIncome;
	}
	public void setCirculationIncome(BigDecimal circulationIncome) {
		this.circulationIncome = circulationIncome;
	}
	public BigDecimal getHrIncome() {
		return hrIncome;
	}
	public void setHrIncome(BigDecimal hrIncome) {
		this.hrIncome = hrIncome;
	}
	public BigDecimal getmIncome() {
		return mIncome;
	}
	public void setmIncome(BigDecimal mIncome) {
		this.mIncome = mIncome;
	}
	public String getReceiveIncomeMethod() {
		return receiveIncomeMethod;
	}
	public void setReceiveIncomeMethod(String receiveIncomeMethod) {
		this.receiveIncomeMethod = receiveIncomeMethod;
	}
	public String getCidType() {
		return cidType;
	}
	public void setCidType(String cidType) {
		this.cidType = cidType;
	}
	public BigDecimal getTotWorkYear() {
		return totWorkYear;
	}
	public void setTotWorkYear(BigDecimal totWorkYear) {
		this.totWorkYear = totWorkYear;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getBusinessNature() {
		return businessNature;
	}
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}
	public BigDecimal getHrBonus() {
		return hrBonus;
	}
	public void setHrBonus(BigDecimal hrBonus) {
		this.hrBonus = hrBonus;
	}
	public String getmPhoneNo() {
		return mPhoneNo;
	}
	public void setmPhoneNo(String mPhoneNo) {
		this.mPhoneNo = mPhoneNo;
	}
	public String getThMidName() {
		return thMidName;
	}
	public void setThMidName(String thMidName) {
		this.thMidName = thMidName;
	}
	public String getReceiveIncomeBank() {
		return receiveIncomeBank;
	}
	public void setReceiveIncomeBank(String receiveIncomeBank) {
		this.receiveIncomeBank = receiveIncomeBank;
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
	public BigDecimal getNoOfChild() {
		return noOfChild;
	}
	public void setNoOfChild(BigDecimal noOfChild) {
		this.noOfChild = noOfChild;
	}
	public ArrayList<IncomeInfoDataM> getIncomeInfos() {
		return incomeInfos;
	}
	public void setIncomeInfos(ArrayList<IncomeInfoDataM> incomeInfos) {
		this.incomeInfos = incomeInfos;
	}
	public ArrayList<IncomeSourceDataM> getIncomeSources() {
		return incomeSources;
	}
	
	public void setIncomeSources(ArrayList<IncomeSourceDataM> incomeSources) {
		this.incomeSources = incomeSources;
	}
	
	public Date getConsentDate() {
		return consentDate;
	}
	public void setConsentDate(Date consentDate) {
		this.consentDate = consentDate;
	}
	public Date getBirthDateConsent() {
		return birthDateConsent;
	}
	public void setBirthDateConsent(Date birthDateConsent) {
		this.birthDateConsent = birthDateConsent;
	}
	public String getPlaceConsent() {
		return placeConsent;
	}
	public void setPlaceConsent(String placeConsent) {
		this.placeConsent = placeConsent;
	}
	public String getWitnessConsent() {
		return witnessConsent;
	}
	public void setWitnessConsent(String witnessConsent) {
		this.witnessConsent = witnessConsent;
	}
	public void addIncomeSources(IncomeSourceDataM incomeSource) {
		if(null==incomeSources){
			incomeSources = new ArrayList<IncomeSourceDataM>();
		}
		incomeSources.add(incomeSource);
	}
	
	public ArrayList<CardlinkCustomerDataM> getCardLinkCustomers() {
		return cardLinkCustomers;
	}
	public void setCardLinkCustomers(
			ArrayList<CardlinkCustomerDataM> cardLinkCustomers) {
		this.cardLinkCustomers = cardLinkCustomers;
	}
	public ArrayList<CardlinkCardDataM> getCardLinkCards() {
		return cardLinkCards;
	}
	
	public CardlinkCardDataM getCardLinkCardById(String cardlinkCardId) {
		if(null!=cardlinkCardId && null!=cardLinkCards){
			for(CardlinkCardDataM cardlinkCard: cardLinkCards){
				if(cardlinkCardId.equals(cardlinkCard.getCardlinkCardId())){
					return cardlinkCard;
				}
			}
		}
		return null;
	}
	
	public void setCardLinkCards(ArrayList<CardlinkCardDataM> cardLinkCards) {
		this.cardLinkCards = cardLinkCards;
	}
	public void addCardLinkCards(CardlinkCardDataM cardLinkCard) {
	 if(null==cardLinkCards){
		 cardLinkCards = new ArrayList<CardlinkCardDataM>();
	 }
	 cardLinkCards.add(cardLinkCard);
	}
	
	public KYCDataM getKyc() {
		return kyc;
	}
	public void setKyc(KYCDataM kyc) {
		this.kyc = kyc;
	}	
	public ArrayList<NCBDocumentDataM> getNcbDocuments() {
		return ncbDocuments;
	}
	public void setNcbDocuments(ArrayList<NCBDocumentDataM> ncbDocuments) {
		this.ncbDocuments = ncbDocuments;
	}
	public ArrayList<NCBDocumentHistoryDataM> getNcbDocumentHistorys() {
		return ncbDocumentHistorys;
	}
	public void setNcbDocumentHistorys(ArrayList<NCBDocumentHistoryDataM> ncbDocumentHistorys) {
		this.ncbDocumentHistorys = ncbDocumentHistorys;
	}	
	public ArrayList<AddressDataM> getAddresses() {
		return addresses;
	}
	public int getAddressesIndex(String addressId){
		int addressesIndex = -1;
		if(null != addresses){
			for (AddressDataM aaddressInfo : addresses) {
				++addressesIndex;
				if(null != addressId && addressId.equals(aaddressInfo.getAddressId())){
					return addressesIndex;
				}
			}
		}
		return -1;
	}
	public void setAddresses(ArrayList<AddressDataM> addresses) {
		this.addresses = addresses;
	}
	public VerificationResultDataM getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(VerificationResultDataM verificationResult) {
		this.verificationResult = verificationResult;
	}	
	public String getTypeOfFin() {
		return typeOfFin;
	}
	public void setTypeOfFin(String typeOfFin) {
		this.typeOfFin = typeOfFin;
	}
	public String getReferCriteria() {
		return referCriteria;
	}
	public void setReferCriteria(String referCriteria) {
		this.referCriteria = referCriteria;
	}
	public String getThName(){
		StringBuilder Text = new StringBuilder("");
		if(null != thFirstName || null != thLastName){
			Text.append((null != thFirstName)?thFirstName:"").append((null != thFirstName && null != thLastName)?" ":"").append((null != thLastName)?thLastName:"");
		}
		return Text.toString();
	}
	public String getEnName(){
		StringBuilder Text = new StringBuilder("");
		if(null != enFirstName || null != enLastName){
			Text.append((null != enFirstName)?enFirstName:"").append((null != enFirstName && null != enLastName)?" ":"").append((null != enLastName)?enLastName:"");
		}
		return Text.toString();
	}
	public String getThLinkName(){
		StringBuilder Text = new StringBuilder("");
		if(null != linkFirstName || null != linkLastName){
			Text.append((null != linkFirstName)?linkFirstName:"").append((null != linkFirstName && null != linkLastName)?" ":"").append((null != linkFirstName)?linkLastName:"");
		}
		return Text.toString();
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}	
	public String getStaffFlag() {
		return staffFlag;
	}
	public void setStaffFlag(String staffFlag) {
		this.staffFlag = staffFlag;
	}	
	public String getRelationshipTypeDesc() {
		return relationshipTypeDesc;
	}
	public void setRelationshipTypeDesc(String relationshipTypeDesc) {
		this.relationshipTypeDesc = relationshipTypeDesc;
	}
	public String getSrcOthIncBonus() {
		return srcOthIncBonus;
	}
	public void setSrcOthIncBonus(String srcOthIncBonus) {
		this.srcOthIncBonus = srcOthIncBonus;
	}
	public String getReceiveIncomeBankBranch() {
		return receiveIncomeBankBranch;
	}
	public void setReceiveIncomeBankBranch(String receiveIncomeBankBranch) {
		this.receiveIncomeBankBranch = receiveIncomeBankBranch;
	}
	public String getSrcOthIncCommission() {
		return srcOthIncCommission;
	}
	public void setSrcOthIncCommission(String srcOthIncCommission) {
		this.srcOthIncCommission = srcOthIncCommission;
	}
	public BigDecimal getDebtBurden() {
		return debtBurden;
	}
	public void setDebtBurden(BigDecimal debtBurden) {
		this.debtBurden = debtBurden;
	}
	public BigDecimal getGrossIncome() {
		return grossIncome;
	}
	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = grossIncome;
	}
	public String getSrcOthIncOther() {
		return srcOthIncOther;
	}
	public void setSrcOthIncOther(String srcOthIncOther) {
		this.srcOthIncOther = srcOthIncOther;
	}
	public String getRelationshipType() {
		return relationshipType;
	}
	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}
//	public ArrayList<DocumentCheckListDataM> getDocumentCheckLists() {
//		return documentCheckLists;
//	}
//	public void setDocumentCheckLists(ArrayList<DocumentCheckListDataM> documentCheckLists) {
//		this.documentCheckLists = documentCheckLists;
//	}	
	public ArrayList<DebtInfoDataM> getDebtInfos() {
		return debtInfos;
	}
	public void setDebtInfos(ArrayList<DebtInfoDataM> debtInfos) {
		this.debtInfos = debtInfos;
	}
	
	public DebtInfoDataM getDebtInfoByType(String debtType) {
		if (null != debtInfos) {
			for (DebtInfoDataM debtInfoM : debtInfos) {
				if (null != debtInfoM.getDebtType() && debtInfoM.getDebtType().equals(debtType)) {
					return debtInfoM;
				}
			}
		}
		return null;
	}
	public String getIncPolicySegmentCode() {
		return incPolicySegmentCode;
	}
	public void setIncPolicySegmentCode(String incPolicySegmentCode) {
		this.incPolicySegmentCode = incPolicySegmentCode;
	}
	public BigDecimal getTotVerifiedIncome() {
		return totVerifiedIncome;
	}
	public void setTotVerifiedIncome(BigDecimal totVerifiedIncome) {
		this.totVerifiedIncome = totVerifiedIncome;
	}	
	public String getContactTime() {
		return contactTime;
	}
	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
	}

	public void addAddress(AddressDataM address){
		if(null == addresses){
			addresses = new ArrayList<>();
		}
		int size = addresses.size();
		address.setSeq(size+1);
		addresses.add(address);
	}
	
	public AddressDataM getAddress(String addressType){
		if(null != addresses){
			for (AddressDataM address:addresses) {
				if(null != addressType && addressType.equals(address.getAddressType())){
					return address;
				}
			}
		}
		return null;
	}	
	public ArrayList<PersonalRelationDataM> getPersonalRelations() {
		return personalRelations;
	}
	public PersonalRelationDataM getPersonalRelation() {	
		if(null != personalRelations && personalRelations.size()>0){
			return personalRelations.get(0);
		}
		return null;
	}
	public void setPersonalRelations(ArrayList<PersonalRelationDataM> personalRelations) {
		this.personalRelations = personalRelations;
	}	
	public ArrayList<AddressDataM> getAddress(String[] filterAddress){
		ArrayList<AddressDataM> addressesList =  new ArrayList<AddressDataM>();	
		ArrayList<String> compare = new ArrayList<String>(Arrays.asList(filterAddress));
		if(null != addresses){
			for(AddressDataM address:addresses){
				String addressType = address.getAddressType();
				if(null!=addressType&&compare.contains(addressType)){
					addressesList.add(address);
				}
			}
		}
		return addressesList;	
	}
	
	public AddressDataM getAddressById(String addressId) {
		if(addressId != null && addresses != null) {
			for(AddressDataM addressM: addresses){
				if(addressId.equals(addressM.getAddressId())) {
					return addressM;
				}
			}
		}
		return null;
	}
	public IncomeInfoDataM getIncomeById(String incomeId) {
		if(incomeId != null && incomeInfos != null) {
			for(IncomeInfoDataM incomeM: incomeInfos){
				if(incomeId.equals(incomeM.getIncomeId())) {
					return incomeM;
				}
			}
		}
		return null;
	}
	public IncomeInfoDataM getIncomeBySeq(int seq) {
		if(seq != 0 && incomeInfos != null) {
			for(IncomeInfoDataM incomeM: incomeInfos){
				if(seq == incomeM.getSeq()) {
					return incomeM;
				}
			}
		}
		return null;
	}
	public IncomeInfoDataM getIncomeByType(String incomeType) {
		if(incomeType != null && incomeInfos != null) {
			for(IncomeInfoDataM incomeM: incomeInfos){
				if(incomeType.equals(incomeM.getIncomeType())) {
					return incomeM;
				}
			}
		}
		return null;
	}
	public IncomeSourceDataM getIncomeSourceById(String incomeSourceId) {
		if(incomeSourceId != null && incomeSources != null) {
			for(IncomeSourceDataM incomeMSource: incomeSources){
				if(incomeSourceId.equals(incomeMSource.getIncomeSourceId())) {
					return incomeMSource;
				}
			}
		}
		return null;
	}
	public IncomeSourceDataM getIncomeSourceByType(String incomeSourceType) {
		if(null!=incomeSourceType && null!=incomeSources) {
			for(IncomeSourceDataM incomeMSource: incomeSources){
				if(incomeSourceType.equals(incomeMSource.getIncomeSource())) {
					return incomeMSource;
				}
			}
		}
		return null;
	}
	public String getCisPrimSegment() {
		return cisPrimSegment;
	}
	public void setCisPrimSegment(String cisPrimSegment) {
		this.cisPrimSegment = cisPrimSegment;
	}
	public String getCisPrimSubSegment() {
		return cisPrimSubSegment;
	}
	public void setCisPrimSubSegment(String cisPrimSubSegment) {
		this.cisPrimSubSegment = cisPrimSubSegment;
	}
	public String getCisSecSegment() {
		return cisSecSegment;
	}
	public void setCisSecSegment(String cisSecSegment) {
		this.cisSecSegment = cisSecSegment;
	}
	public String getCisSecSubSegment() {
		return cisSecSubSegment;
	}
	public void setCisSecSubSegment(String cisSecSubSegment) {
		this.cisSecSubSegment = cisSecSubSegment;
	}	
	public void addPersonalRelation(String refId,String personalType,String relationLevel){
		if(null == personalRelations){
			personalRelations = new ArrayList<PersonalRelationDataM>();
		}
		PersonalRelationDataM personalReation = getPersonalRelation(refId,personalType,relationLevel);
		if(null == personalReation){
			personalReation = new PersonalRelationDataM();
			personalReation.setRefId(refId);
			personalReation.setPersonalType(personalType);
			personalReation.setRelationLevel(relationLevel);
			personalReation.setPersonalId(personalId);
			personalRelations.add(personalReation);
		}
	}
	public PersonalRelationDataM getPersonalRelation(String refId,String personalType,String relationLevel){
		if(null != personalRelations){
			for (PersonalRelationDataM personalReation : personalRelations) {
				if(personalReation.getRefId().equals(refId) 
					&& personalReation.getPersonalType().equals(personalType) 
						&& personalReation.getRelationLevel().equals(relationLevel)){
					return personalReation;
				}					
			}
		}
		return null;
	}
	public PersonalRelationDataM getPersonalRelation(String referId,String relationLevel){
		if(null != personalRelations){
			for (PersonalRelationDataM personalReation : personalRelations) {
				if(personalReation.getRefId().equals(referId)&& personalReation.getRelationLevel().equals(relationLevel)){
					return personalReation;
				}					
			}
		}
		return null;
	}
	public PersonalRelationDataM getPersonalRelation(String referId){
		if(null != personalRelations){
			for (PersonalRelationDataM personalReation : personalRelations) {
				if(null != personalReation.getRefId() && personalReation.getRefId().equals(referId)){
					return personalReation;
				}					
			}
		}
		return null;
	}
	public ArrayList<NcbInfoDataM> getNcbInfos() {
		return ncbInfos;
	}
	public void setNcbInfos(ArrayList<NcbInfoDataM> ncbInfos) {
		this.ncbInfos = ncbInfos;
	}
	public void addNcbInfos(NcbInfoDataM  ncbInfo) {
		if(null==ncbInfos){
			ncbInfos = new ArrayList<NcbInfoDataM>();
		}
		ncbInfos.add(ncbInfo);
	}
	public String getNoMainCard() {
		return noMainCard;
	}
	public void setNoMainCard(String noMainCard) {
		this.noMainCard = noMainCard;
	}
	public String getNoSupCard() {
		return noSupCard;
	}
	public void setNoSupCard(String noSupCard) {
		this.noSupCard = noSupCard;
	}
	public String getPermanentStaff() {
		return permanentStaff;
	}
	public void setPermanentStaff(String permanentStaff) {
		this.permanentStaff = permanentStaff;
	}
	public String getSorceOfIncome() {
		return sorceOfIncome;
	}
	public void setSorceOfIncome(String sorceOfIncome) {
		this.sorceOfIncome = sorceOfIncome;
	}
	public void addIncomeInfo(IncomeInfoDataM IncomeInfo){
		if(null == incomeInfos){
			incomeInfos = new ArrayList<IncomeInfoDataM>();
		}
		incomeInfos.add(IncomeInfo);
	}
	public String getPhoneNoBol() {
		return phoneNoBol;
	}
	public void setPhoneNoBol(String phoneNoBol) {
		this.phoneNoBol = phoneNoBol;
	}
	public String getFreelanceType() {
		return freelanceType;
	}
	public void setFreelanceType(String freelanceType) {
		this.freelanceType = freelanceType;
	}
	
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
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
		try{
		if(null != obj1 && null !=  obj2 ){
			if(obj1 instanceof AddressDataM && obj2 instanceof AddressDataM){
				AddressDataM addressDataM1 = (AddressDataM)obj1;
				AddressDataM addressDataM2 = (AddressDataM)obj2;
				return addressDataM1.getAddressType().compareTo(addressDataM2.getAddressType());		
			}else if(obj1 instanceof PersonalInfoDataM && obj2 instanceof PersonalInfoDataM){
				PersonalInfoDataM personalDataM1 = (PersonalInfoDataM)obj1;
				PersonalInfoDataM personalDataM2 = (PersonalInfoDataM)obj2;
				return personalDataM1.getPersonalType().compareTo(personalDataM2.getPersonalType());
			}
		}
		}catch(Exception e){}
		return 0;
	}
	// notPrefix used to check only if not thai bev
	// As both CC and thai bev starts with 1
	public CardlinkCustomerDataM getCardLinkCustomer(String orgNo, String prefix, String notPrefix) {
		if(null != cardLinkCustomers && null!=orgNo){
			for (CardlinkCustomerDataM cardLinkCust : cardLinkCustomers) {
				if(orgNo.equals(cardLinkCust.getOrgId())){
					String customerNo = cardLinkCust.getCardlinkCustNo();
					if(prefix != null && customerNo != null) {
						if(notPrefix != null && customerNo.startsWith(prefix)
								&& !customerNo.startsWith(notPrefix)) {
							return cardLinkCust;
						} else if(notPrefix == null && customerNo.startsWith(prefix)) {
							return cardLinkCust;
						}
					} else {
						return cardLinkCust;
					}
				}					
			}
		}
		return null;
	}
	public CardlinkCustomerDataM getCardLinkCustomer(String orgNo, String customerNo) {
		if(null != cardLinkCustomers){
			for (CardlinkCustomerDataM cardLinkCust : cardLinkCustomers) {
				if(cardLinkCust.getOrgId() != null && cardLinkCust.getOrgId().equals(orgNo)){
					if(cardLinkCust.getCardlinkCustNo() != null && 
							cardLinkCust.getCardlinkCustNo().equals(customerNo)) {
						return cardLinkCust;
					}
				}					
			}
			// check for null if no matching customer number
			for (CardlinkCustomerDataM cardLinkCust : cardLinkCustomers) {
				if(cardLinkCust.getOrgId() != null && cardLinkCust.getOrgId().equals(orgNo)){
					if(cardLinkCust.getCardlinkCustNo() == null) {
						return cardLinkCust;
					}
				}					
			}
		}
		return null;
	}
	public void addCardLinkCustomer(CardlinkCustomerDataM cardLinkCust){
		if(null == cardLinkCustomers){
			cardLinkCustomers = new ArrayList<CardlinkCustomerDataM>();
		}
		cardLinkCustomers.add(cardLinkCust);
	}
	public String getCompanyCISId() {
		return companyCISId;
	}
	public void setCompanyCISId(String companyCISId) {
		this.companyCISId = companyCISId;
	}
	public String getCompanyOrgIdBol() {
		return companyOrgIdBol;
	}
	public void setCompanyOrgIdBol(String companyOrgIdBol) {
		this.companyOrgIdBol = companyOrgIdBol;
	}
	public String getDisplayEditBTN() {
		return displayEditBTN;
	}
	public void setDisplayEditBTN(String displayEditBTN) {
		this.displayEditBTN = displayEditBTN;
	}
	public String getThTitleDesc() {
		return thTitleDesc;
	}
	public void setThTitleDesc(String thTitleDesc) {
		this.thTitleDesc = thTitleDesc;
	}
	public String getEnTitleDesc() {
		return enTitleDesc;
	}
	public void setEnTitleDesc(String enTitleDesc) {
		this.enTitleDesc = enTitleDesc;
	}
	public String getmTitleDesc() {
		return mTitleDesc;
	}
	public void setmTitleDesc(String mTitleDesc) {
		this.mTitleDesc = mTitleDesc;
	}	
	public NcbInfoDataM getNCBInfo(String trackingCode) {
		if(null!=ncbInfos && null!=trackingCode){
			for(NcbInfoDataM ncbInfoDataM : ncbInfos){
				if(trackingCode.equals(ncbInfoDataM.getTrackingCode())){
					return ncbInfoDataM;
				}
			}
		}
		return null;
	}
	
	public boolean isContainIncomeTypeException(ArrayList<String> incomeExceptions) {
		if(null!=incomeExceptions && null!=incomeInfos){
			for(IncomeInfoDataM inComeInfo : incomeInfos){
				if(incomeExceptions.contains(inComeInfo.getIncomeType())){
					return true;
				}
			}
		}
		return false;
	}
	public String getBureauRequiredFlag() {
		return bureauRequiredFlag;
	}
	public void setBureauRequiredFlag(String bureauRequiredFlag) {
		this.bureauRequiredFlag = bureauRequiredFlag;
	}
	public String getPegaPhoneType() {
		return pegaPhoneType;
	}
	public void setPegaPhoneType(String pegaPhoneType) {
		this.pegaPhoneType = pegaPhoneType;
	}
	public String getPegaPhoneNo() {
		return pegaPhoneNo;
	}
	public void setPegaPhoneNo(String pegaPhoneNo) {
		this.pegaPhoneNo = pegaPhoneNo;
	}
	public String getPegaPhoneExt() {
		return pegaPhoneExt;
	}
	public void setPegaPhoneExt(String pegaPhoneExt) {
		this.pegaPhoneExt = pegaPhoneExt;
	}	
	public boolean isNewCardlinkCustFlag(){
		if(null != cardLinkCustomers){
			for(CardlinkCustomerDataM cardLinkCustomer:cardLinkCustomers){
				if(null != cardLinkCustomer && "N".equals(cardLinkCustomer.getNewCardlinkCustFlag())){
					return false;
				}
			}
		}
		return true;
	}
	public boolean isNewCardlinkCustFlag(String productId,boolean thaibevFlag){
		if(null != cardLinkCustomers){
			for(CardlinkCustomerDataM cardLinkCustomer:cardLinkCustomers){
				if(null != cardLinkCustomer 
						&& "N".equals(cardLinkCustomer.getNewCardlinkCustFlag()) 
						&& isSameProductNewCardlink(cardLinkCustomer,productId,thaibevFlag)){		
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isSameProductNewCardlink(CardlinkCustomerDataM cardLinkCustomer,String productId,boolean thaibevFlag){
		String _orgId = cardLinkCustomer.getOrgId();
		String _cardLinkcustId = cardLinkCustomer.getCardlinkCustId();
		if(null != cardLinkCustomer && productId != null && productId.equals(_orgId) && !thaibevFlag){
			return true;
		}else if(null != cardLinkCustomer && productId != null 
				&& thaibevFlag 
				&& null!=_cardLinkcustId
				&& _cardLinkcustId.length()>=2
				&& productId.equals(_orgId)
				&& PREFIX_CREDIT_CARD.PREFIX_THAIBEV.equals(_cardLinkcustId.substring(0, 1))){
			return true;
		}
		return false;
	}
	public String getEmailIdRef() {
		return emailIdRef;
	}
	public void setEmailIdRef(String emailIdRef) {
		this.emailIdRef = emailIdRef;
	}
	public String getMobileIdRef() {
		return mobileIdRef;
	}
	public void setMobileIdRef(String mobileIdRef) {
		this.mobileIdRef = mobileIdRef;
	}	
	public String getProcedureTypeOfIncome() {
		return procedureTypeOfIncome;
	}
	public void setProcedureTypeOfIncome(String procedureTypeOfIncome) {
		this.procedureTypeOfIncome = procedureTypeOfIncome;
	}
	public String getPersonalError() {
		return personalError;
	}
	public void setPersonalError(String personalError) {
		this.personalError = personalError;
	}
	public String getConsentModelFlag() {
		return consentModelFlag;
	}
	public void setConsentModelFlag(String consentModelFlag) {
		this.consentModelFlag = consentModelFlag;
	}	
	public BigDecimal getNumberOfIssuer() {
		return numberOfIssuer;
	}
	public void setNumberOfIssuer(BigDecimal numberOfIssuer) {
		this.numberOfIssuer = numberOfIssuer;
	}
	public BigDecimal getSelfDeclareLimit() {
		return selfDeclareLimit;
	}
	public void setSelfDeclareLimit(BigDecimal selfDeclareLimit) {
		this.selfDeclareLimit = selfDeclareLimit;
	}
	public String getCisCustType() {
		return cisCustType;
	}
	public void setCisCustType(String cisCustType) {
		this.cisCustType = cisCustType;
	}
	public String getIdNoConsent() {
		return idNoConsent;
	}
	public ArrayList<BScoreDataM> getBscores() {
		return bScores;
	}
	public void setBScores(ArrayList<BScoreDataM> bScores) {
		this.bScores = bScores;
	}
	public void setIdNoConsent(String idNoConsent) {
		this.idNoConsent = idNoConsent;
	}
	public void addBScores(BScoreDataM  bScore) {
		if(null==bScores){
			bScores = new ArrayList<BScoreDataM>();
		}
		bScores.add(bScore);
	}
	public ArrayList<AccountDataM> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<AccountDataM> accounts) {
		this.accounts = accounts;
	}
	public void addAccounts(AccountDataM  account) {
		if(null==accounts){
			accounts = new ArrayList<AccountDataM>();
		}
		accounts.add(account);
	}
	public BigDecimal getApplicationIncome() {
		return applicationIncome;
	}
	public void setApplicationIncome(BigDecimal applicationIncome) {
		this.applicationIncome = applicationIncome;
	}
	
	public String getePersonalId() {
		return ePersonalId;
	}
	public void setePersonalId(String ePersonalId) {
		this.ePersonalId = ePersonalId;
	}
	public String getNcbTranNo() {
		return ncbTranNo;
	}
	public void setNcbTranNo(String ncbTranNo) {
		this.ncbTranNo = ncbTranNo;
	}
	public String getConsentRefNo() {
		return consentRefNo;
	}
	public void setConsentRefNo(String consentRefNo) {
		this.consentRefNo = consentRefNo;
	}
	public String getPayrollDate() {
		return payrollDate;
	}
	public void setPayrollDate(String payrollDate) {
		this.payrollDate = payrollDate;
	}
	public String getCisPrimDesc() {
		return cisPrimDesc;
	}
	public void setCisPrimDesc(String cisPrimDesc) {
		this.cisPrimDesc = cisPrimDesc;
	}
	public String getCisPrimSubDesc() {
		return cisPrimSubDesc;
	}
	public void setCisPrimSubDesc(String cisPrimSubDesc) {
		this.cisPrimSubDesc = cisPrimSubDesc;
	}
	public String getCisSecDesc() {
		return cisSecDesc;
	}
	public void setCisSecDesc(String cisSecDesc) {
		this.cisSecDesc = cisSecDesc;
	}
	public String getCisSecSubDesc() {
		return cisSecSubDesc;
	}
	public void setCisSecSubDesc(String cisSecSubDesc) {
		this.cisSecSubDesc = cisSecSubDesc;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getIncomeResource() {
		return incomeResource;
	}
	public void setIncomeResource(String incomeResource) {
		this.incomeResource = incomeResource;
	}
	public String getIncomeResourceOther() {
		return incomeResourceOther;
	}
	public void setIncomeResourceOther(String incomeResourceOther) {
		this.incomeResourceOther = incomeResourceOther;
	}
	public String getCountryOfIncome() {
		return countryOfIncome;
	}
	public void setCountryOfIncome(String countryOfIncome) {
		this.countryOfIncome = countryOfIncome;
	}
	public ArrayList<BScoreDataM> getbScores() {
		return bScores;
	}
	public void setbScores(ArrayList<BScoreDataM> bScores) {
		this.bScores = bScores;
	}
	public void clearValue(){	
		this.gender=null;	
		this.mCompany=null;
		this.businessTypeOther=null;
		this.positionCode=null;
		this.monthlyExpense=null;
		this.race=null;
		this.prevCompany=null;
		this.idno=null;
		this.relationshipTypeDesc=null;
		this.occupation=null;
		this.occpationOther=null; 
		this.branchReceiveCard=null;
		this.sorceOfIncomeOther=null;
		this.married=null;
		this.srcOthIncBonus=null;
		this.prevCompanyPhoneNo=null;
		this.freelanceIncome=null;
		this.nationality=null;
		this.enFirstName=null;
		this.professionOther=null;
		this.assetsAmount=null;
		this.businessType=null;
		this.visaExpDate=null;
		this.establishmentAddrFlag=null;	
		this.mPosition=null;	
		this.netProfitIncome=null;	
		this.enTitleCode=null;	
		this.mOfficeTel=null;	
		this.receiveIncomeBankBranch=null;	
		this.workPermitNo=null;	
		this.totWorkMonth=null;	
		this.prevWorkMonth=null;	
		this.prevWorkYear=null;	
		this.profession=null;	
		this.monthlyIncome=null;	
		this.incPolicySegmentCode=null;	
		this.cidExpDate=null;	
		this.cidIssueDate=null;  
		this.mThFirstName=null;	
		this.thFirstName=null;
		this.enFirstName=null;
		this.workPermitExpDate=null;	
		this.employmentType=null;	
		this.emailSecondary=null;	
		this.mTitleCode=null;	
		this.srcOthIncCommission=null;	
		this.otherIncome=null;	
		this.savingIncome=null;	
		this.debtBurden=null;	
		this.grossIncome=null;	
		this.srcOthIncOther=null;	
		this.emailPrimary=null;	
		this.enMidName=null;	
		this.thTitleCode=null;	
		this.mHomeTel=null;	
		this.visaType=null;	
		this.degree=null;	
		this.placeReceiveCard=null;
		this.relationshipType=null;	
		this.prevCompanyTitle=null;
		this.enLastName=null;	
		this.staffFlag=null;	
		this.cisNo=null;	
		this.matchAddrNcbFlag=null;	
		this.positionDesc=null;	
		this.mailingAddress=null;	
		this.mThOldLastName=null;	
		this.vatRegistFlag=null;	
		this.birthDate=null;	
		this.salary=null;	
		this.positionLevel=null;
		this.thLastName=null;	
		this.mThLastName=null;	
		this.circulationIncome=null;	
		this.hrIncome=null;	
		this.mIncome=null;	
		this.receiveIncomeMethod=null;	
		this.cidType=null;	
		this.totWorkYear=null;
		this.mobileNo=null;	
		this.businessNature=null;	
		this.hrBonus=null;	
		this.mPhoneNo=null;	
		this.thMidName=null;	
		this.totVerifiedIncome=null;	
		this.receiveIncomeBank=null;	
		this.createBy=null;	
		this.createDate=null;	
		this.updateBy=null;	
		this.updateDate=null;	
		this.noOfChild=null; 
		this.contactTime=null;
		this.typeOfFin=null; 
		this.referCriteria=null;
		this.cisPrimSegment=null; 
		this.cisPrimSubSegment=null; 
		this.cisSecSegment=null; 
		this.cisSecSubSegment=null; 
		this.busRegistId=null;
		this.busRegistDate=null;
		this.busRegistReqNo=null; 
		this.noMainCard=null;
		this.noSupCard=null; 
		this.permanentStaff=null; 
		this.sorceOfIncome=null; 
		this.phoneNoBol=null;
		this.freelanceType=null; 
		this.companyCISId=null; 
		this.companyOrgIdBol=null; 
		this.displayEditBTN=null;
		this.thTitleDesc=null;
		this.enTitleDesc=null;
		this.mTitleDesc=null;
		this.bureauRequiredFlag=null;
		this.pegaPhoneType=null;
		this.pegaPhoneNo=null;
		this.pegaPhoneExt=null;
		this.addresses=null;
		this.numberOfIssuer=null;
		this.selfDeclareLimit=null;
		this.cisCustType=null;
		this.consentModelFlag=null;
		this.idNoConsent = null;
		//KPL Additional
		this.specialMerchantType = null;
		this.applicationIncome = null;
		this.payrollDate = null;
		this.consentDate = null;
		this.birthDateConsent = null;
		this.placeConsent = null;
		this.witnessConsent = null;
	}
}
