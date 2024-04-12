package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class CIS1044U01RequestDataM implements Serializable,Cloneable{
	private String branchNo;
	private String hubNo;
	private String userId;
	private String prospectFlag; 
	private String customerId;
	private String thTitle;
	private String thFstName;
	private String thMidName;
	private String thLstName;
	private String engTitle;
	private String engFstName;
	private String engMidName;
	private String engLstName;
	private String nmChgResnCode;
	private String custTypeCode;
	private String svcBrchCode;
	private String docTypeCode;
	private String  docNum;
	private Date  idCrdExpDate;
	private Date  idCrdIssuDate;
	private String idCrdIssuPlaceDesc;
	private String  idCrdDesc;
	private Date  birthDate;
	private String taxNum;
	private String officialAddressCountry;
	private String contactAddressCountry;
	private String   gender;
	private String   maritalStatus;
	private String   religion;
	private String   occupation;
	private String   jobPosition;
	private String   businessTypeCode;
	private String   salary;
	private Date   startWorkDate;
	private String   degree;
	private String   nationality;
	private String   race;
	private String   bookNumberForChangeName;
	private String   sequenceNumberForChangeName;
	private Date    theDateChangeName;
	private String   consendFlag;
	private String   sourceOfConsent;
	private String   kycBranch;
	private String   completeDocumentFlag;
	private String   completeKYCDocumentFlag;
	private String   professionCode;
	private String 	othProfessDesc;
	private int   	numberOfEmployee;
	private BigDecimal   assetExcludeLand;
	private String   multipleContactChannel;
	private int   	amountOfChildren;
	private int   	ageOfOldestChild;
	private int   	ageOfYoungestChild;
	private String   familyIncomeRange;
	private String   customerSegment;
	private String   titleForcedSaveFlag;
	private String   addrOffCntctSameCde;
	private String   vipFlag;
	private String   deathFlag;
	private Date   deathDate;
	private String customerType;
	
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getHubNo() {
		return hubNo;
	}
	public void setHubNo(String hubNo) {
		this.hubNo = hubNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProspectFlag() {
		return prospectFlag;
	}
	public void setProspectFlag(String prospectFlag) {
		this.prospectFlag = prospectFlag;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getThTitle() {
		return thTitle;
	}
	public void setThTitle(String thTitle) {
		this.thTitle = thTitle;
	}
	public String getThFstName() {
		return thFstName;
	}
	public void setThFstName(String thFstName) {
		this.thFstName = thFstName;
	}
	public String getThMidName() {
		return thMidName;
	}
	public void setThMidName(String thMidName) {
		this.thMidName = thMidName;
	}
	public String getThLstName() {
		return thLstName;
	}
	public void setThLstName(String thLstName) {
		this.thLstName = thLstName;
	}
	public String getEngTitle() {
		return engTitle;
	}
	public void setEngTitle(String engTitle) {
		this.engTitle = engTitle;
	}
	public String getEngFstName() {
		return engFstName;
	}
	public void setEngFstName(String engFstName) {
		this.engFstName = engFstName;
	}
	public String getEngMidName() {
		return engMidName;
	}
	public void setEngMidName(String engMidName) {
		this.engMidName = engMidName;
	}
	public String getEngLstName() {
		return engLstName;
	}
	public void setEngLstName(String engLstName) {
		this.engLstName = engLstName;
	}
	public String getNmChgResnCode() {
		return nmChgResnCode;
	}
 
	public String getCustTypeCode() {
		return custTypeCode;
	}
	public void setCustTypeCode(String custTypeCode) {
		this.custTypeCode = custTypeCode;
	}
	public String getSvcBrchCode() {
		return svcBrchCode;
	}
	public void setSvcBrchCode(String svcBrchCode) {
		this.svcBrchCode = svcBrchCode;
	}
	public String getDocTypeCode() {
		return docTypeCode;
	}
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public Date getIdCrdExpDate() {
		return idCrdExpDate;
	}
	public void setIdCrdExpDate(Date idCrdExpDate) {
		this.idCrdExpDate = idCrdExpDate;
	}
	public Date getIdCrdIssuDate() {
		return idCrdIssuDate;
	}
	public void setIdCrdIssuDate(Date idCrdIssuDate) {
		this.idCrdIssuDate = idCrdIssuDate;
	}
	public String getIdCrdIssuPlaceDesc() {
		return idCrdIssuPlaceDesc;
	}
	public void setIdCrdIssuPlaceDesc(String idCrdIssuPlaceDesc) {
		this.idCrdIssuPlaceDesc = idCrdIssuPlaceDesc;
	}
	public String getIdCrdDesc() {
		return idCrdDesc;
	}
	public void setIdCrdDesc(String idCrdDesc) {
		this.idCrdDesc = idCrdDesc;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getTaxNum() {
		return taxNum;
	}
	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}
	public String getOfficialAddressCountry() {
		return officialAddressCountry;
	}
	public void setOfficialAddressCountry(String officialAddressCountry) {
		this.officialAddressCountry = officialAddressCountry;
	}
	public String getContactAddressCountry() {
		return contactAddressCountry;
	}
	public void setContactAddressCountry(String contactAddressCountry) {
		this.contactAddressCountry = contactAddressCountry;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public Date getStartWorkDate() {
		return startWorkDate;
	}
	public void setStartWorkDate(Date startWorkDate) {
		this.startWorkDate = startWorkDate;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getBookNumberForChangeName() {
		return bookNumberForChangeName;
	}
	public void setBookNumberForChangeName(String bookNumberForChangeName) {
		this.bookNumberForChangeName = bookNumberForChangeName;
	}
	public String getSequenceNumberForChangeName() {
		return sequenceNumberForChangeName;
	}
	public void setSequenceNumberForChangeName(String sequenceNumberForChangeName) {
		this.sequenceNumberForChangeName = sequenceNumberForChangeName;
	}
	public Date getTheDateChangeName() {
		return theDateChangeName;
	}
	public void setTheDateChangeName(Date theDateChangeName) {
		this.theDateChangeName = theDateChangeName;
	}
	public String getConsendFlag() {
		return consendFlag;
	}
	public void setConsendFlag(String consendFlag) {
		this.consendFlag = consendFlag;
	}
	public String getSourceOfConsent() {
		return sourceOfConsent;
	}
	public void setSourceOfConsent(String sourceOfConsent) {
		this.sourceOfConsent = sourceOfConsent;
	}
	public String getKycBranch() {
		return kycBranch;
	}
	public void setKycBranch(String kycBranch) {
		this.kycBranch = kycBranch;
	}
	public String getCompleteDocumentFlag() {
		return completeDocumentFlag;
	}
	public void setCompleteDocumentFlag(String completeDocumentFlag) {
		this.completeDocumentFlag = completeDocumentFlag;
	}
	public String getCompleteKYCDocumentFlag() {
		return completeKYCDocumentFlag;
	}
	public void setCompleteKYCDocumentFlag(String completeKYCDocumentFlag) {
		this.completeKYCDocumentFlag = completeKYCDocumentFlag;
	}
	public String getProfessionCode() {
		return professionCode;
	}
	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}
	public int getNumberOfEmployee() {
		return numberOfEmployee;
	}
	public void setNumberOfEmployee(int numberOfEmployee) {
		this.numberOfEmployee = numberOfEmployee;
	}
	public BigDecimal getAssetExcludeLand() {
		return assetExcludeLand;
	}
	public void setAssetExcludeLand(BigDecimal assetExcludeLand) {
		this.assetExcludeLand = assetExcludeLand;
	}
	public String getMultipleContactChannel() {
		return multipleContactChannel;
	}
	public void setMultipleContactChannel(String multipleContactChannel) {
		this.multipleContactChannel = multipleContactChannel;
	}
	public int getAmountOfChildren() {
		return amountOfChildren;
	}
	public void setAmountOfChildren(int amountOfChildren) {
		this.amountOfChildren = amountOfChildren;
	}
	public int getAgeOfOldestChild() {
		return ageOfOldestChild;
	}
	public void setAgeOfOldestChild(int ageOfOldestChild) {
		this.ageOfOldestChild = ageOfOldestChild;
	}
	public int getAgeOfYoungestChild() {
		return ageOfYoungestChild;
	}
	public void setAgeOfYoungestChild(int ageOfYoungestChild) {
		this.ageOfYoungestChild = ageOfYoungestChild;
	}	 
	public String getFamilyIncomeRange() {
		return familyIncomeRange;
	}
	public void setFamilyIncomeRange(String familyIncomeRange) {
		this.familyIncomeRange = familyIncomeRange;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getTitleForcedSaveFlag() {
		return titleForcedSaveFlag;
	}
	public void setTitleForcedSaveFlag(String titleForcedSaveFlag) {
		this.titleForcedSaveFlag = titleForcedSaveFlag;
	}
	public String getAddrOffCntctSameCde() {
		return addrOffCntctSameCde;
	}
	public void setAddrOffCntctSameCde(String addrOffCntctSameCde) {
		this.addrOffCntctSameCde = addrOffCntctSameCde;
	}
	public String getVipFlag() {
		return vipFlag;
	}
	public void setVipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
	}
	public String getDeathFlag() {
		return deathFlag;
	}
	public void setDeathFlag(String deathFlag) {
		this.deathFlag = deathFlag;
	}
	public Date getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getOthProfessDesc() {
		return othProfessDesc;
	}
	public void setOthProfessDesc(String othProfessDesc) {
		this.othProfessDesc = othProfessDesc;
	}	
}
