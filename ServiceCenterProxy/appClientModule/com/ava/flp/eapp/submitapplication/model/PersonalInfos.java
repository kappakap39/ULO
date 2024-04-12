package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.ava.flp.eapp.submitapplication.model.Addresses;
import com.ava.flp.eapp.submitapplication.model.Documents;
import com.ava.flp.eapp.submitapplication.model.KycInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PersonalInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-18T11:16:08.237+07:00")

public class PersonalInfos   {
  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("personalType")
  private String personalType = null;

  @JsonProperty("cisNo")
  private String cisNo = null;

  @JsonProperty("cidType")
  private String cidType = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("thTitleCode")
  private String thTitleCode = null;

  @JsonProperty("thTitleDesc")
  private String thTitleDesc = null;

  @JsonProperty("thFirstName")
  private String thFirstName = null;

  @JsonProperty("thLastName")
  private String thLastName = null;

  @JsonProperty("thMidName")
  private String thMidName = null;

  @JsonProperty("enTitleCode")
  private String enTitleCode = null;

  @JsonProperty("enTitleDesc")
  private String enTitleDesc = null;

  @JsonProperty("enFirstName")
  private String enFirstName = null;

  @JsonProperty("enLastName")
  private String enLastName = null;

  @JsonProperty("enMidName")
  private String enMidName = null;

  @JsonProperty("birthDate")
  private String birthDate = null;

  @JsonProperty("cidExpDate")
  private String cidExpDate = null;

  @JsonProperty("cidIssueDate")
  private String cidIssueDate = null;

  @JsonProperty("cisPrimSegment")
  private String cisPrimSegment = null;

  @JsonProperty("cisPrimSubSegment")
  private String cisPrimSubSegment = null;

  @JsonProperty("cisSecSegment")
  private String cisSecSegment = null;

  @JsonProperty("cisSecSubSegment")
  private String cisSecSubSegment = null;

  @JsonProperty("contactTime")
  private String contactTime = null;

  @JsonProperty("customerType")
  private String customerType = null;

  @JsonProperty("degree")
  private String degree = null;

  @JsonProperty("disclosureFlag")
  private String disclosureFlag = null;

  @JsonProperty("bundleWithMainFlag")
  private String bundleWithMainFlag = null;

  @JsonProperty("businessType")
  private String businessType = null;

  @JsonProperty("employmentType")
  private String employmentType = null;

  @JsonProperty("freelanceIncome")
  private BigDecimal freelanceIncome = null;

  @JsonProperty("freelanceType")
  private String freelanceType = null;

  @JsonProperty("gender")
  private String gender = null;

  @JsonProperty("married")
  private String married = null;

  @JsonProperty("mobileNo")
  private String mobileNo = null;

  @JsonProperty("monthlyExpense")
  private BigDecimal monthlyExpense = null;

  @JsonProperty("monthlyIncome")
  private BigDecimal monthlyIncome = null;

  @JsonProperty("nationality")
  private String nationality = null;

  @JsonProperty("netProfitIncome")
  private BigDecimal netProfitIncome = null;

  @JsonProperty("numberOfChild")
  private BigDecimal numberOfChild = null;

  @JsonProperty("occupation")
  private String occupation = null;

  @JsonProperty("previousCompanyTitle")
  private String previousCompanyTitle = null;

  @JsonProperty("previousWorkMonth")
  private BigDecimal previousWorkMonth = null;

  @JsonProperty("previousWorkYear")
  private BigDecimal previousWorkYear = null;

  @JsonProperty("profession")
  private String profession = null;

  @JsonProperty("professionOther")
  private String professionOther = null;

  @JsonProperty("receiveIncomeBank")
  private String receiveIncomeBank = null;

  @JsonProperty("relationshipType")
  private String relationshipType = null;

  @JsonProperty("requireBureau")
  private String requireBureau = null;

  @JsonProperty("ncbTranNo")
  private String ncbTranNo = null;

  @JsonProperty("consentRefNo")
  private String consentRefNo = null;

  @JsonProperty("salary")
  private BigDecimal salary = null;

  @JsonProperty("sourceOfIncomeOther")
  private String sourceOfIncomeOther = null;

  @JsonProperty("srcOthIncOther")
  private String srcOthIncOther = null;

  @JsonProperty("srcOthIncBonus")
  private String srcOthIncBonus = null;

  @JsonProperty("srcOthIncCommission")
  private String srcOthIncCommission = null;

  @JsonProperty("totalWorkMonth")
  private BigDecimal totalWorkMonth = null;

  @JsonProperty("totalWorkYear")
  private BigDecimal totalWorkYear = null;

  @JsonProperty("income")
  private BigDecimal income = null;

  @JsonProperty("otherIncome")
  private BigDecimal otherIncome = null;

  @JsonProperty("vatRegisterFlag")
  private String vatRegisterFlag = null;

  @JsonProperty("visaExpiryDate")
  private String visaExpiryDate = null;

  @JsonProperty("visaType")
  private String visaType = null;

  @JsonProperty("workPermitExpiryDate")
  private String workPermitExpiryDate = null;

  @JsonProperty("workPermitNo")
  private String workPermitNo = null;

  @JsonProperty("mailingAddress")
  private String mailingAddress = null;

  @JsonProperty("positionType")
  private String positionType = null;

  @JsonProperty("positionLevel")
  private String positionLevel = null;

  @JsonProperty("salaryLevel")
  private String salaryLevel = null;

  @JsonProperty("passportExpiryDate")
  private String passportExpiryDate = null;

  @JsonProperty("previousCompanyName")
  private String previousCompanyName = null;

  @JsonProperty("specialMerchant")
  private String specialMerchant = null;

  @JsonProperty("businessTypeOther")
  private String businessTypeOther = null;

  @JsonProperty("positionDesc")
  private String positionDesc = null;

  @JsonProperty("positionCode")
  private String positionCode = null;

  @JsonProperty("businessNature")
  private String businessNature = null;

  @JsonProperty("placeReceiveCard")
  private String placeReceiveCard = null;

  @JsonProperty("companyCisId")
  private String companyCisId = null;

  @JsonProperty("emailPrimary")
  private String emailPrimary = null;

  @JsonProperty("selfDeclareLimit")
  private BigDecimal selfDeclareLimit = null;

  @JsonProperty("numberOfIssuer")
  private BigDecimal numberOfIssuer = null;

  @JsonProperty("mThOldLastName")
  private String mThOldLastName = null;

  @JsonProperty("mThLastName")
  private String mThLastName = null;

  @JsonProperty("mTitleDesc")
  private String mTitleDesc = null;

  @JsonProperty("mHomeTel")
  private String mHomeTel = null;

  @JsonProperty("mIncome")
  private BigDecimal mIncome = null;

  @JsonProperty("mPosition")
  private String mPosition = null;

  @JsonProperty("cisPrimDesc")
  private String cisPrimDesc = null;

  @JsonProperty("cisPrimSubDesc")
  private String cisPrimSubDesc = null;

  @JsonProperty("cisSecDesc")
  private String cisSecDesc = null;

  @JsonProperty("cisSecSubDesc")
  private String cisSecSubDesc = null;

  @JsonProperty("lineId")
  private String lineId = null;

  @JsonProperty("incomeResource")
  private String incomeResource = null;

  @JsonProperty("incomeResourceOther")
  private String incomeResourceOther = null;

  @JsonProperty("countryOfIncome")
  private String countryOfIncome = null;

  @JsonProperty("occupationOther")
  private String occupationOther = null;

  @JsonProperty("addresses")
  private List<Addresses> addresses = null;

  @JsonProperty("kycInfos")
  private List<KycInfo> kycInfos = null;

  @JsonProperty("documents")
  private List<Documents> documents = null;

  public PersonalInfos personalId(String personalId) {
    this.personalId = personalId;
    return this;
  }

   /**
   * Get personalId
   * @return personalId
  **/
  @ApiModelProperty(value = "")


  public String getPersonalId() {
    return personalId;
  }

  public void setPersonalId(String personalId) {
    this.personalId = personalId;
  }

  public PersonalInfos personalType(String personalType) {
    this.personalType = personalType;
    return this;
  }

   /**
   * Get personalType
   * @return personalType
  **/
  @ApiModelProperty(value = "")


  public String getPersonalType() {
    return personalType;
  }

  public void setPersonalType(String personalType) {
    this.personalType = personalType;
  }

  public PersonalInfos cisNo(String cisNo) {
    this.cisNo = cisNo;
    return this;
  }

   /**
   * Get cisNo
   * @return cisNo
  **/
  @ApiModelProperty(value = "")


  public String getCisNo() {
    return cisNo;
  }

  public void setCisNo(String cisNo) {
    this.cisNo = cisNo;
  }

  public PersonalInfos cidType(String cidType) {
    this.cidType = cidType;
    return this;
  }

   /**
   * Get cidType
   * @return cidType
  **/
  @ApiModelProperty(value = "")


  public String getCidType() {
    return cidType;
  }

  public void setCidType(String cidType) {
    this.cidType = cidType;
  }

  public PersonalInfos idNo(String idNo) {
    this.idNo = idNo;
    return this;
  }

   /**
   * Get idNo
   * @return idNo
  **/
  @ApiModelProperty(value = "")


  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public PersonalInfos thTitleCode(String thTitleCode) {
    this.thTitleCode = thTitleCode;
    return this;
  }

   /**
   * Get thTitleCode
   * @return thTitleCode
  **/
  @ApiModelProperty(value = "")


  public String getThTitleCode() {
    return thTitleCode;
  }

  public void setThTitleCode(String thTitleCode) {
    this.thTitleCode = thTitleCode;
  }

  public PersonalInfos thTitleDesc(String thTitleDesc) {
    this.thTitleDesc = thTitleDesc;
    return this;
  }

   /**
   * Get thTitleDesc
   * @return thTitleDesc
  **/
  @ApiModelProperty(value = "")


  public String getThTitleDesc() {
    return thTitleDesc;
  }

  public void setThTitleDesc(String thTitleDesc) {
    this.thTitleDesc = thTitleDesc;
  }

  public PersonalInfos thFirstName(String thFirstName) {
    this.thFirstName = thFirstName;
    return this;
  }

   /**
   * Get thFirstName
   * @return thFirstName
  **/
  @ApiModelProperty(value = "")


  public String getThFirstName() {
    return thFirstName;
  }

  public void setThFirstName(String thFirstName) {
    this.thFirstName = thFirstName;
  }

  public PersonalInfos thLastName(String thLastName) {
    this.thLastName = thLastName;
    return this;
  }

   /**
   * Get thLastName
   * @return thLastName
  **/
  @ApiModelProperty(value = "")


  public String getThLastName() {
    return thLastName;
  }

  public void setThLastName(String thLastName) {
    this.thLastName = thLastName;
  }

  public PersonalInfos thMidName(String thMidName) {
    this.thMidName = thMidName;
    return this;
  }

   /**
   * Get thMidName
   * @return thMidName
  **/
  @ApiModelProperty(value = "")


  public String getThMidName() {
    return thMidName;
  }

  public void setThMidName(String thMidName) {
    this.thMidName = thMidName;
  }

  public PersonalInfos enTitleCode(String enTitleCode) {
    this.enTitleCode = enTitleCode;
    return this;
  }

   /**
   * Get enTitleCode
   * @return enTitleCode
  **/
  @ApiModelProperty(value = "")


  public String getEnTitleCode() {
    return enTitleCode;
  }

  public void setEnTitleCode(String enTitleCode) {
    this.enTitleCode = enTitleCode;
  }

  public PersonalInfos enTitleDesc(String enTitleDesc) {
    this.enTitleDesc = enTitleDesc;
    return this;
  }

   /**
   * Get enTitleDesc
   * @return enTitleDesc
  **/
  @ApiModelProperty(value = "")


  public String getEnTitleDesc() {
    return enTitleDesc;
  }

  public void setEnTitleDesc(String enTitleDesc) {
    this.enTitleDesc = enTitleDesc;
  }

  public PersonalInfos enFirstName(String enFirstName) {
    this.enFirstName = enFirstName;
    return this;
  }

   /**
   * Get enFirstName
   * @return enFirstName
  **/
  @ApiModelProperty(value = "")


  public String getEnFirstName() {
    return enFirstName;
  }

  public void setEnFirstName(String enFirstName) {
    this.enFirstName = enFirstName;
  }

  public PersonalInfos enLastName(String enLastName) {
    this.enLastName = enLastName;
    return this;
  }

   /**
   * Get enLastName
   * @return enLastName
  **/
  @ApiModelProperty(value = "")


  public String getEnLastName() {
    return enLastName;
  }

  public void setEnLastName(String enLastName) {
    this.enLastName = enLastName;
  }

  public PersonalInfos enMidName(String enMidName) {
    this.enMidName = enMidName;
    return this;
  }

   /**
   * Get enMidName
   * @return enMidName
  **/
  @ApiModelProperty(value = "")


  public String getEnMidName() {
    return enMidName;
  }

  public void setEnMidName(String enMidName) {
    this.enMidName = enMidName;
  }

  public PersonalInfos birthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

   /**
   * Get birthDate
   * @return birthDate
  **/
  @ApiModelProperty(value = "")


  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public PersonalInfos cidExpDate(String cidExpDate) {
    this.cidExpDate = cidExpDate;
    return this;
  }

   /**
   * Get cidExpDate
   * @return cidExpDate
  **/
  @ApiModelProperty(value = "")


  public String getCidExpDate() {
    return cidExpDate;
  }

  public void setCidExpDate(String cidExpDate) {
    this.cidExpDate = cidExpDate;
  }

  public PersonalInfos cidIssueDate(String cidIssueDate) {
    this.cidIssueDate = cidIssueDate;
    return this;
  }

   /**
   * Get cidIssueDate
   * @return cidIssueDate
  **/
  @ApiModelProperty(value = "")


  public String getCidIssueDate() {
    return cidIssueDate;
  }

  public void setCidIssueDate(String cidIssueDate) {
    this.cidIssueDate = cidIssueDate;
  }

  public PersonalInfos cisPrimSegment(String cisPrimSegment) {
    this.cisPrimSegment = cisPrimSegment;
    return this;
  }

   /**
   * Get cisPrimSegment
   * @return cisPrimSegment
  **/
  @ApiModelProperty(value = "")


  public String getCisPrimSegment() {
    return cisPrimSegment;
  }

  public void setCisPrimSegment(String cisPrimSegment) {
    this.cisPrimSegment = cisPrimSegment;
  }

  public PersonalInfos cisPrimSubSegment(String cisPrimSubSegment) {
    this.cisPrimSubSegment = cisPrimSubSegment;
    return this;
  }

   /**
   * Get cisPrimSubSegment
   * @return cisPrimSubSegment
  **/
  @ApiModelProperty(value = "")


  public String getCisPrimSubSegment() {
    return cisPrimSubSegment;
  }

  public void setCisPrimSubSegment(String cisPrimSubSegment) {
    this.cisPrimSubSegment = cisPrimSubSegment;
  }

  public PersonalInfos cisSecSegment(String cisSecSegment) {
    this.cisSecSegment = cisSecSegment;
    return this;
  }

   /**
   * Get cisSecSegment
   * @return cisSecSegment
  **/
  @ApiModelProperty(value = "")


  public String getCisSecSegment() {
    return cisSecSegment;
  }

  public void setCisSecSegment(String cisSecSegment) {
    this.cisSecSegment = cisSecSegment;
  }

  public PersonalInfos cisSecSubSegment(String cisSecSubSegment) {
    this.cisSecSubSegment = cisSecSubSegment;
    return this;
  }

   /**
   * Get cisSecSubSegment
   * @return cisSecSubSegment
  **/
  @ApiModelProperty(value = "")


  public String getCisSecSubSegment() {
    return cisSecSubSegment;
  }

  public void setCisSecSubSegment(String cisSecSubSegment) {
    this.cisSecSubSegment = cisSecSubSegment;
  }

  public PersonalInfos contactTime(String contactTime) {
    this.contactTime = contactTime;
    return this;
  }

   /**
   * Get contactTime
   * @return contactTime
  **/
  @ApiModelProperty(value = "")


  public String getContactTime() {
    return contactTime;
  }

  public void setContactTime(String contactTime) {
    this.contactTime = contactTime;
  }

  public PersonalInfos customerType(String customerType) {
    this.customerType = customerType;
    return this;
  }

   /**
   * Get customerType
   * @return customerType
  **/
  @ApiModelProperty(value = "")


  public String getCustomerType() {
    return customerType;
  }

  public void setCustomerType(String customerType) {
    this.customerType = customerType;
  }

  public PersonalInfos degree(String degree) {
    this.degree = degree;
    return this;
  }

   /**
   * Get degree
   * @return degree
  **/
  @ApiModelProperty(value = "")


  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public PersonalInfos disclosureFlag(String disclosureFlag) {
    this.disclosureFlag = disclosureFlag;
    return this;
  }

   /**
   * Get disclosureFlag
   * @return disclosureFlag
  **/
  @ApiModelProperty(value = "")


  public String getDisclosureFlag() {
    return disclosureFlag;
  }

  public void setDisclosureFlag(String disclosureFlag) {
    this.disclosureFlag = disclosureFlag;
  }

  public PersonalInfos bundleWithMainFlag(String bundleWithMainFlag) {
    this.bundleWithMainFlag = bundleWithMainFlag;
    return this;
  }

   /**
   * Get bundleWithMainFlag
   * @return bundleWithMainFlag
  **/
  @ApiModelProperty(value = "")


  public String getBundleWithMainFlag() {
    return bundleWithMainFlag;
  }

  public void setBundleWithMainFlag(String bundleWithMainFlag) {
    this.bundleWithMainFlag = bundleWithMainFlag;
  }

  public PersonalInfos businessType(String businessType) {
    this.businessType = businessType;
    return this;
  }

   /**
   * Get businessType
   * @return businessType
  **/
  @ApiModelProperty(value = "")


  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public PersonalInfos employmentType(String employmentType) {
    this.employmentType = employmentType;
    return this;
  }

   /**
   * Get employmentType
   * @return employmentType
  **/
  @ApiModelProperty(value = "")


  public String getEmploymentType() {
    return employmentType;
  }

  public void setEmploymentType(String employmentType) {
    this.employmentType = employmentType;
  }

  public PersonalInfos freelanceIncome(BigDecimal freelanceIncome) {
    this.freelanceIncome = freelanceIncome;
    return this;
  }

   /**
   * Get freelanceIncome
   * @return freelanceIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getFreelanceIncome() {
    return freelanceIncome;
  }

  public void setFreelanceIncome(BigDecimal freelanceIncome) {
    this.freelanceIncome = freelanceIncome;
  }

  public PersonalInfos freelanceType(String freelanceType) {
    this.freelanceType = freelanceType;
    return this;
  }

   /**
   * Get freelanceType
   * @return freelanceType
  **/
  @ApiModelProperty(value = "")


  public String getFreelanceType() {
    return freelanceType;
  }

  public void setFreelanceType(String freelanceType) {
    this.freelanceType = freelanceType;
  }

  public PersonalInfos gender(String gender) {
    this.gender = gender;
    return this;
  }

   /**
   * Get gender
   * @return gender
  **/
  @ApiModelProperty(value = "")


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public PersonalInfos married(String married) {
    this.married = married;
    return this;
  }

   /**
   * Get married
   * @return married
  **/
  @ApiModelProperty(value = "")


  public String getMarried() {
    return married;
  }

  public void setMarried(String married) {
    this.married = married;
  }

  public PersonalInfos mobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
    return this;
  }

   /**
   * Get mobileNo
   * @return mobileNo
  **/
  @ApiModelProperty(value = "")


  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public PersonalInfos monthlyExpense(BigDecimal monthlyExpense) {
    this.monthlyExpense = monthlyExpense;
    return this;
  }

   /**
   * Get monthlyExpense
   * @return monthlyExpense
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMonthlyExpense() {
    return monthlyExpense;
  }

  public void setMonthlyExpense(BigDecimal monthlyExpense) {
    this.monthlyExpense = monthlyExpense;
  }

  public PersonalInfos monthlyIncome(BigDecimal monthlyIncome) {
    this.monthlyIncome = monthlyIncome;
    return this;
  }

   /**
   * Get monthlyIncome
   * @return monthlyIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMonthlyIncome() {
    return monthlyIncome;
  }

  public void setMonthlyIncome(BigDecimal monthlyIncome) {
    this.monthlyIncome = monthlyIncome;
  }

  public PersonalInfos nationality(String nationality) {
    this.nationality = nationality;
    return this;
  }

   /**
   * Get nationality
   * @return nationality
  **/
  @ApiModelProperty(value = "")


  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public PersonalInfos netProfitIncome(BigDecimal netProfitIncome) {
    this.netProfitIncome = netProfitIncome;
    return this;
  }

   /**
   * Get netProfitIncome
   * @return netProfitIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNetProfitIncome() {
    return netProfitIncome;
  }

  public void setNetProfitIncome(BigDecimal netProfitIncome) {
    this.netProfitIncome = netProfitIncome;
  }

  public PersonalInfos numberOfChild(BigDecimal numberOfChild) {
    this.numberOfChild = numberOfChild;
    return this;
  }

   /**
   * Get numberOfChild
   * @return numberOfChild
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumberOfChild() {
    return numberOfChild;
  }

  public void setNumberOfChild(BigDecimal numberOfChild) {
    this.numberOfChild = numberOfChild;
  }

  public PersonalInfos occupation(String occupation) {
    this.occupation = occupation;
    return this;
  }

   /**
   * Get occupation
   * @return occupation
  **/
  @ApiModelProperty(value = "")


  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public PersonalInfos previousCompanyTitle(String previousCompanyTitle) {
    this.previousCompanyTitle = previousCompanyTitle;
    return this;
  }

   /**
   * Get previousCompanyTitle
   * @return previousCompanyTitle
  **/
  @ApiModelProperty(value = "")


  public String getPreviousCompanyTitle() {
    return previousCompanyTitle;
  }

  public void setPreviousCompanyTitle(String previousCompanyTitle) {
    this.previousCompanyTitle = previousCompanyTitle;
  }

  public PersonalInfos previousWorkMonth(BigDecimal previousWorkMonth) {
    this.previousWorkMonth = previousWorkMonth;
    return this;
  }

   /**
   * Get previousWorkMonth
   * @return previousWorkMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPreviousWorkMonth() {
    return previousWorkMonth;
  }

  public void setPreviousWorkMonth(BigDecimal previousWorkMonth) {
    this.previousWorkMonth = previousWorkMonth;
  }

  public PersonalInfos previousWorkYear(BigDecimal previousWorkYear) {
    this.previousWorkYear = previousWorkYear;
    return this;
  }

   /**
   * Get previousWorkYear
   * @return previousWorkYear
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPreviousWorkYear() {
    return previousWorkYear;
  }

  public void setPreviousWorkYear(BigDecimal previousWorkYear) {
    this.previousWorkYear = previousWorkYear;
  }

  public PersonalInfos profession(String profession) {
    this.profession = profession;
    return this;
  }

   /**
   * Get profession
   * @return profession
  **/
  @ApiModelProperty(value = "")


  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public PersonalInfos professionOther(String professionOther) {
    this.professionOther = professionOther;
    return this;
  }

   /**
   * Get professionOther
   * @return professionOther
  **/
  @ApiModelProperty(value = "")


  public String getProfessionOther() {
    return professionOther;
  }

  public void setProfessionOther(String professionOther) {
    this.professionOther = professionOther;
  }

  public PersonalInfos receiveIncomeBank(String receiveIncomeBank) {
    this.receiveIncomeBank = receiveIncomeBank;
    return this;
  }

   /**
   * Get receiveIncomeBank
   * @return receiveIncomeBank
  **/
  @ApiModelProperty(value = "")


  public String getReceiveIncomeBank() {
    return receiveIncomeBank;
  }

  public void setReceiveIncomeBank(String receiveIncomeBank) {
    this.receiveIncomeBank = receiveIncomeBank;
  }

  public PersonalInfos relationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
    return this;
  }

   /**
   * Get relationshipType
   * @return relationshipType
  **/
  @ApiModelProperty(value = "")


  public String getRelationshipType() {
    return relationshipType;
  }

  public void setRelationshipType(String relationshipType) {
    this.relationshipType = relationshipType;
  }

  public PersonalInfos requireBureau(String requireBureau) {
    this.requireBureau = requireBureau;
    return this;
  }

   /**
   * Get requireBureau
   * @return requireBureau
  **/
  @ApiModelProperty(value = "")


  public String getRequireBureau() {
    return requireBureau;
  }

  public void setRequireBureau(String requireBureau) {
    this.requireBureau = requireBureau;
  }

  public PersonalInfos ncbTranNo(String ncbTranNo) {
    this.ncbTranNo = ncbTranNo;
    return this;
  }

   /**
   * Get ncbTranNo
   * @return ncbTranNo
  **/
  @ApiModelProperty(value = "")


  public String getNcbTranNo() {
    return ncbTranNo;
  }

  public void setNcbTranNo(String ncbTranNo) {
    this.ncbTranNo = ncbTranNo;
  }

  public PersonalInfos consentRefNo(String consentRefNo) {
    this.consentRefNo = consentRefNo;
    return this;
  }

   /**
   * Get consentRefNo
   * @return consentRefNo
  **/
  @ApiModelProperty(value = "")


  public String getConsentRefNo() {
    return consentRefNo;
  }

  public void setConsentRefNo(String consentRefNo) {
    this.consentRefNo = consentRefNo;
  }

  public PersonalInfos salary(BigDecimal salary) {
    this.salary = salary;
    return this;
  }

   /**
   * Get salary
   * @return salary
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSalary() {
    return salary;
  }

  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }

  public PersonalInfos sourceOfIncomeOther(String sourceOfIncomeOther) {
    this.sourceOfIncomeOther = sourceOfIncomeOther;
    return this;
  }

   /**
   * Get sourceOfIncomeOther
   * @return sourceOfIncomeOther
  **/
  @ApiModelProperty(value = "")


  public String getSourceOfIncomeOther() {
    return sourceOfIncomeOther;
  }

  public void setSourceOfIncomeOther(String sourceOfIncomeOther) {
    this.sourceOfIncomeOther = sourceOfIncomeOther;
  }

  public PersonalInfos srcOthIncOther(String srcOthIncOther) {
    this.srcOthIncOther = srcOthIncOther;
    return this;
  }

   /**
   * Get srcOthIncOther
   * @return srcOthIncOther
  **/
  @ApiModelProperty(value = "")


  public String getSrcOthIncOther() {
    return srcOthIncOther;
  }

  public void setSrcOthIncOther(String srcOthIncOther) {
    this.srcOthIncOther = srcOthIncOther;
  }

  public PersonalInfos srcOthIncBonus(String srcOthIncBonus) {
    this.srcOthIncBonus = srcOthIncBonus;
    return this;
  }

   /**
   * Get srcOthIncBonus
   * @return srcOthIncBonus
  **/
  @ApiModelProperty(value = "")


  public String getSrcOthIncBonus() {
    return srcOthIncBonus;
  }

  public void setSrcOthIncBonus(String srcOthIncBonus) {
    this.srcOthIncBonus = srcOthIncBonus;
  }

  public PersonalInfos srcOthIncCommission(String srcOthIncCommission) {
    this.srcOthIncCommission = srcOthIncCommission;
    return this;
  }

   /**
   * Get srcOthIncCommission
   * @return srcOthIncCommission
  **/
  @ApiModelProperty(value = "")


  public String getSrcOthIncCommission() {
    return srcOthIncCommission;
  }

  public void setSrcOthIncCommission(String srcOthIncCommission) {
    this.srcOthIncCommission = srcOthIncCommission;
  }

  public PersonalInfos totalWorkMonth(BigDecimal totalWorkMonth) {
    this.totalWorkMonth = totalWorkMonth;
    return this;
  }

   /**
   * Get totalWorkMonth
   * @return totalWorkMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotalWorkMonth() {
    return totalWorkMonth;
  }

  public void setTotalWorkMonth(BigDecimal totalWorkMonth) {
    this.totalWorkMonth = totalWorkMonth;
  }

  public PersonalInfos totalWorkYear(BigDecimal totalWorkYear) {
    this.totalWorkYear = totalWorkYear;
    return this;
  }

   /**
   * Get totalWorkYear
   * @return totalWorkYear
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotalWorkYear() {
    return totalWorkYear;
  }

  public void setTotalWorkYear(BigDecimal totalWorkYear) {
    this.totalWorkYear = totalWorkYear;
  }

  public PersonalInfos income(BigDecimal income) {
    this.income = income;
    return this;
  }

   /**
   * Get income
   * @return income
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncome() {
    return income;
  }

  public void setIncome(BigDecimal income) {
    this.income = income;
  }

  public PersonalInfos otherIncome(BigDecimal otherIncome) {
    this.otherIncome = otherIncome;
    return this;
  }

   /**
   * Get otherIncome
   * @return otherIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getOtherIncome() {
    return otherIncome;
  }

  public void setOtherIncome(BigDecimal otherIncome) {
    this.otherIncome = otherIncome;
  }

  public PersonalInfos vatRegisterFlag(String vatRegisterFlag) {
    this.vatRegisterFlag = vatRegisterFlag;
    return this;
  }

   /**
   * Get vatRegisterFlag
   * @return vatRegisterFlag
  **/
  @ApiModelProperty(value = "")


  public String getVatRegisterFlag() {
    return vatRegisterFlag;
  }

  public void setVatRegisterFlag(String vatRegisterFlag) {
    this.vatRegisterFlag = vatRegisterFlag;
  }

  public PersonalInfos visaExpiryDate(String visaExpiryDate) {
    this.visaExpiryDate = visaExpiryDate;
    return this;
  }

   /**
   * Get visaExpiryDate
   * @return visaExpiryDate
  **/
  @ApiModelProperty(value = "")


  public String getVisaExpiryDate() {
    return visaExpiryDate;
  }

  public void setVisaExpiryDate(String visaExpiryDate) {
    this.visaExpiryDate = visaExpiryDate;
  }

  public PersonalInfos visaType(String visaType) {
    this.visaType = visaType;
    return this;
  }

   /**
   * Get visaType
   * @return visaType
  **/
  @ApiModelProperty(value = "")


  public String getVisaType() {
    return visaType;
  }

  public void setVisaType(String visaType) {
    this.visaType = visaType;
  }

  public PersonalInfos workPermitExpiryDate(String workPermitExpiryDate) {
    this.workPermitExpiryDate = workPermitExpiryDate;
    return this;
  }

   /**
   * Get workPermitExpiryDate
   * @return workPermitExpiryDate
  **/
  @ApiModelProperty(value = "")


  public String getWorkPermitExpiryDate() {
    return workPermitExpiryDate;
  }

  public void setWorkPermitExpiryDate(String workPermitExpiryDate) {
    this.workPermitExpiryDate = workPermitExpiryDate;
  }

  public PersonalInfos workPermitNo(String workPermitNo) {
    this.workPermitNo = workPermitNo;
    return this;
  }

   /**
   * Get workPermitNo
   * @return workPermitNo
  **/
  @ApiModelProperty(value = "")


  public String getWorkPermitNo() {
    return workPermitNo;
  }

  public void setWorkPermitNo(String workPermitNo) {
    this.workPermitNo = workPermitNo;
  }

  public PersonalInfos mailingAddress(String mailingAddress) {
    this.mailingAddress = mailingAddress;
    return this;
  }

   /**
   * Get mailingAddress
   * @return mailingAddress
  **/
  @ApiModelProperty(value = "")


  public String getMailingAddress() {
    return mailingAddress;
  }

  public void setMailingAddress(String mailingAddress) {
    this.mailingAddress = mailingAddress;
  }

  public PersonalInfos positionType(String positionType) {
    this.positionType = positionType;
    return this;
  }

   /**
   * Get positionType
   * @return positionType
  **/
  @ApiModelProperty(value = "")


  public String getPositionType() {
    return positionType;
  }

  public void setPositionType(String positionType) {
    this.positionType = positionType;
  }

  public PersonalInfos positionLevel(String positionLevel) {
    this.positionLevel = positionLevel;
    return this;
  }

   /**
   * Get positionLevel
   * @return positionLevel
  **/
  @ApiModelProperty(value = "")


  public String getPositionLevel() {
    return positionLevel;
  }

  public void setPositionLevel(String positionLevel) {
    this.positionLevel = positionLevel;
  }

  public PersonalInfos salaryLevel(String salaryLevel) {
    this.salaryLevel = salaryLevel;
    return this;
  }

   /**
   * Get salaryLevel
   * @return salaryLevel
  **/
  @ApiModelProperty(value = "")


  public String getSalaryLevel() {
    return salaryLevel;
  }

  public void setSalaryLevel(String salaryLevel) {
    this.salaryLevel = salaryLevel;
  }

  public PersonalInfos passportExpiryDate(String passportExpiryDate) {
    this.passportExpiryDate = passportExpiryDate;
    return this;
  }

   /**
   * Get passportExpiryDate
   * @return passportExpiryDate
  **/
  @ApiModelProperty(value = "")


  public String getPassportExpiryDate() {
    return passportExpiryDate;
  }

  public void setPassportExpiryDate(String passportExpiryDate) {
    this.passportExpiryDate = passportExpiryDate;
  }

  public PersonalInfos previousCompanyName(String previousCompanyName) {
    this.previousCompanyName = previousCompanyName;
    return this;
  }

   /**
   * Get previousCompanyName
   * @return previousCompanyName
  **/
  @ApiModelProperty(value = "")


  public String getPreviousCompanyName() {
    return previousCompanyName;
  }

  public void setPreviousCompanyName(String previousCompanyName) {
    this.previousCompanyName = previousCompanyName;
  }

  public PersonalInfos specialMerchant(String specialMerchant) {
    this.specialMerchant = specialMerchant;
    return this;
  }

   /**
   * Get specialMerchant
   * @return specialMerchant
  **/
  @ApiModelProperty(value = "")


  public String getSpecialMerchant() {
    return specialMerchant;
  }

  public void setSpecialMerchant(String specialMerchant) {
    this.specialMerchant = specialMerchant;
  }

  public PersonalInfos businessTypeOther(String businessTypeOther) {
    this.businessTypeOther = businessTypeOther;
    return this;
  }

   /**
   * Get businessTypeOther
   * @return businessTypeOther
  **/
  @ApiModelProperty(value = "")


  public String getBusinessTypeOther() {
    return businessTypeOther;
  }

  public void setBusinessTypeOther(String businessTypeOther) {
    this.businessTypeOther = businessTypeOther;
  }

  public PersonalInfos positionDesc(String positionDesc) {
    this.positionDesc = positionDesc;
    return this;
  }

   /**
   * Get positionDesc
   * @return positionDesc
  **/
  @ApiModelProperty(value = "")


  public String getPositionDesc() {
    return positionDesc;
  }

  public void setPositionDesc(String positionDesc) {
    this.positionDesc = positionDesc;
  }

  public PersonalInfos positionCode(String positionCode) {
    this.positionCode = positionCode;
    return this;
  }

   /**
   * Get positionCode
   * @return positionCode
  **/
  @ApiModelProperty(value = "")


  public String getPositionCode() {
    return positionCode;
  }

  public void setPositionCode(String positionCode) {
    this.positionCode = positionCode;
  }

  public PersonalInfos businessNature(String businessNature) {
    this.businessNature = businessNature;
    return this;
  }

   /**
   * Get businessNature
   * @return businessNature
  **/
  @ApiModelProperty(value = "")


  public String getBusinessNature() {
    return businessNature;
  }

  public void setBusinessNature(String businessNature) {
    this.businessNature = businessNature;
  }

  public PersonalInfos placeReceiveCard(String placeReceiveCard) {
    this.placeReceiveCard = placeReceiveCard;
    return this;
  }

   /**
   * Get placeReceiveCard
   * @return placeReceiveCard
  **/
  @ApiModelProperty(value = "")


  public String getPlaceReceiveCard() {
    return placeReceiveCard;
  }

  public void setPlaceReceiveCard(String placeReceiveCard) {
    this.placeReceiveCard = placeReceiveCard;
  }

  public PersonalInfos companyCisId(String companyCisId) {
    this.companyCisId = companyCisId;
    return this;
  }

   /**
   * Get companyCisId
   * @return companyCisId
  **/
  @ApiModelProperty(value = "")


  public String getCompanyCisId() {
    return companyCisId;
  }

  public void setCompanyCisId(String companyCisId) {
    this.companyCisId = companyCisId;
  }

  public PersonalInfos emailPrimary(String emailPrimary) {
    this.emailPrimary = emailPrimary;
    return this;
  }

   /**
   * Get emailPrimary
   * @return emailPrimary
  **/
  @ApiModelProperty(value = "")


  public String getEmailPrimary() {
    return emailPrimary;
  }

  public void setEmailPrimary(String emailPrimary) {
    this.emailPrimary = emailPrimary;
  }

  public PersonalInfos selfDeclareLimit(BigDecimal selfDeclareLimit) {
    this.selfDeclareLimit = selfDeclareLimit;
    return this;
  }

   /**
   * Get selfDeclareLimit
   * @return selfDeclareLimit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSelfDeclareLimit() {
    return selfDeclareLimit;
  }

  public void setSelfDeclareLimit(BigDecimal selfDeclareLimit) {
    this.selfDeclareLimit = selfDeclareLimit;
  }

  public PersonalInfos numberOfIssuer(BigDecimal numberOfIssuer) {
    this.numberOfIssuer = numberOfIssuer;
    return this;
  }

   /**
   * Get numberOfIssuer
   * @return numberOfIssuer
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumberOfIssuer() {
    return numberOfIssuer;
  }

  public void setNumberOfIssuer(BigDecimal numberOfIssuer) {
    this.numberOfIssuer = numberOfIssuer;
  }

  public PersonalInfos mThOldLastName(String mThOldLastName) {
    this.mThOldLastName = mThOldLastName;
    return this;
  }

   /**
   * Get mThOldLastName
   * @return mThOldLastName
  **/
  @ApiModelProperty(value = "")


  public String getMThOldLastName() {
    return mThOldLastName;
  }

  public void setMThOldLastName(String mThOldLastName) {
    this.mThOldLastName = mThOldLastName;
  }

  public PersonalInfos mThLastName(String mThLastName) {
    this.mThLastName = mThLastName;
    return this;
  }

   /**
   * Get mThLastName
   * @return mThLastName
  **/
  @ApiModelProperty(value = "")


  public String getMThLastName() {
    return mThLastName;
  }

  public void setMThLastName(String mThLastName) {
    this.mThLastName = mThLastName;
  }

  public PersonalInfos mTitleDesc(String mTitleDesc) {
    this.mTitleDesc = mTitleDesc;
    return this;
  }

   /**
   * Get mTitleDesc
   * @return mTitleDesc
  **/
  @ApiModelProperty(value = "")


  public String getMTitleDesc() {
    return mTitleDesc;
  }

  public void setMTitleDesc(String mTitleDesc) {
    this.mTitleDesc = mTitleDesc;
  }

  public PersonalInfos mHomeTel(String mHomeTel) {
    this.mHomeTel = mHomeTel;
    return this;
  }

   /**
   * Get mHomeTel
   * @return mHomeTel
  **/
  @ApiModelProperty(value = "")


  public String getMHomeTel() {
    return mHomeTel;
  }

  public void setMHomeTel(String mHomeTel) {
    this.mHomeTel = mHomeTel;
  }

  public PersonalInfos mIncome(BigDecimal mIncome) {
    this.mIncome = mIncome;
    return this;
  }

   /**
   * Get mIncome
   * @return mIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMIncome() {
    return mIncome;
  }

  public void setMIncome(BigDecimal mIncome) {
    this.mIncome = mIncome;
  }

  public PersonalInfos mPosition(String mPosition) {
    this.mPosition = mPosition;
    return this;
  }

   /**
   * Get mPosition
   * @return mPosition
  **/
  @ApiModelProperty(value = "")


  public String getMPosition() {
    return mPosition;
  }

  public void setMPosition(String mPosition) {
    this.mPosition = mPosition;
  }

  public PersonalInfos cisPrimDesc(String cisPrimDesc) {
    this.cisPrimDesc = cisPrimDesc;
    return this;
  }

   /**
   * Get cisPrimDesc
   * @return cisPrimDesc
  **/
  @ApiModelProperty(value = "")


  public String getCisPrimDesc() {
    return cisPrimDesc;
  }

  public void setCisPrimDesc(String cisPrimDesc) {
    this.cisPrimDesc = cisPrimDesc;
  }

  public PersonalInfos cisPrimSubDesc(String cisPrimSubDesc) {
    this.cisPrimSubDesc = cisPrimSubDesc;
    return this;
  }

   /**
   * Get cisPrimSubDesc
   * @return cisPrimSubDesc
  **/
  @ApiModelProperty(value = "")


  public String getCisPrimSubDesc() {
    return cisPrimSubDesc;
  }

  public void setCisPrimSubDesc(String cisPrimSubDesc) {
    this.cisPrimSubDesc = cisPrimSubDesc;
  }

  public PersonalInfos cisSecDesc(String cisSecDesc) {
    this.cisSecDesc = cisSecDesc;
    return this;
  }

   /**
   * Get cisSecDesc
   * @return cisSecDesc
  **/
  @ApiModelProperty(value = "")


  public String getCisSecDesc() {
    return cisSecDesc;
  }

  public void setCisSecDesc(String cisSecDesc) {
    this.cisSecDesc = cisSecDesc;
  }

  public PersonalInfos cisSecSubDesc(String cisSecSubDesc) {
    this.cisSecSubDesc = cisSecSubDesc;
    return this;
  }

   /**
   * Get cisSecSubDesc
   * @return cisSecSubDesc
  **/
  @ApiModelProperty(value = "")


  public String getCisSecSubDesc() {
    return cisSecSubDesc;
  }

  public void setCisSecSubDesc(String cisSecSubDesc) {
    this.cisSecSubDesc = cisSecSubDesc;
  }

  public PersonalInfos lineId(String lineId) {
    this.lineId = lineId;
    return this;
  }

   /**
   * Get lineId
   * @return lineId
  **/
  @ApiModelProperty(value = "")


  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
  }

  public PersonalInfos incomeResource(String incomeResource) {
    this.incomeResource = incomeResource;
    return this;
  }

   /**
   * Get incomeResource
   * @return incomeResource
  **/
  @ApiModelProperty(value = "")


  public String getIncomeResource() {
    return incomeResource;
  }

  public void setIncomeResource(String incomeResource) {
    this.incomeResource = incomeResource;
  }

  public PersonalInfos incomeResourceOther(String incomeResourceOther) {
    this.incomeResourceOther = incomeResourceOther;
    return this;
  }

   /**
   * Get incomeResourceOther
   * @return incomeResourceOther
  **/
  @ApiModelProperty(value = "")


  public String getIncomeResourceOther() {
    return incomeResourceOther;
  }

  public void setIncomeResourceOther(String incomeResourceOther) {
    this.incomeResourceOther = incomeResourceOther;
  }

  public PersonalInfos countryOfIncome(String countryOfIncome) {
    this.countryOfIncome = countryOfIncome;
    return this;
  }

   /**
   * Get countryOfIncome
   * @return countryOfIncome
  **/
  @ApiModelProperty(value = "")


  public String getCountryOfIncome() {
    return countryOfIncome;
  }

  public void setCountryOfIncome(String countryOfIncome) {
    this.countryOfIncome = countryOfIncome;
  }

  public PersonalInfos occupationOther(String occupationOther) {
    this.occupationOther = occupationOther;
    return this;
  }

   /**
   * Get occupationOther
   * @return occupationOther
  **/
  @ApiModelProperty(value = "")


  public String getOccupationOther() {
    return occupationOther;
  }

  public void setOccupationOther(String occupationOther) {
    this.occupationOther = occupationOther;
  }

  public PersonalInfos addresses(List<Addresses> addresses) {
    this.addresses = addresses;
    return this;
  }

  public PersonalInfos addAddressesItem(Addresses addressesItem) {
    if (this.addresses == null) {
      this.addresses = new ArrayList<Addresses>();
    }
    this.addresses.add(addressesItem);
    return this;
  }

   /**
   * Get addresses
   * @return addresses
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Addresses> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Addresses> addresses) {
    this.addresses = addresses;
  }

  public PersonalInfos kycInfos(List<KycInfo> kycInfos) {
    this.kycInfos = kycInfos;
    return this;
  }

  public PersonalInfos addKycInfosItem(KycInfo kycInfosItem) {
    if (this.kycInfos == null) {
      this.kycInfos = new ArrayList<KycInfo>();
    }
    this.kycInfos.add(kycInfosItem);
    return this;
  }

   /**
   * Get kycInfos
   * @return kycInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<KycInfo> getKycInfos() {
    return kycInfos;
  }

  public void setKycInfos(List<KycInfo> kycInfos) {
    this.kycInfos = kycInfos;
  }

  public PersonalInfos documents(List<Documents> documents) {
    this.documents = documents;
    return this;
  }

  public PersonalInfos addDocumentsItem(Documents documentsItem) {
    if (this.documents == null) {
      this.documents = new ArrayList<Documents>();
    }
    this.documents.add(documentsItem);
    return this;
  }

   /**
   * Get documents
   * @return documents
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Documents> getDocuments() {
    return documents;
  }

  public void setDocuments(List<Documents> documents) {
    this.documents = documents;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalInfos personalInfos = (PersonalInfos) o;
    return Objects.equals(this.personalId, personalInfos.personalId) &&
        Objects.equals(this.personalType, personalInfos.personalType) &&
        Objects.equals(this.cisNo, personalInfos.cisNo) &&
        Objects.equals(this.cidType, personalInfos.cidType) &&
        Objects.equals(this.idNo, personalInfos.idNo) &&
        Objects.equals(this.thTitleCode, personalInfos.thTitleCode) &&
        Objects.equals(this.thTitleDesc, personalInfos.thTitleDesc) &&
        Objects.equals(this.thFirstName, personalInfos.thFirstName) &&
        Objects.equals(this.thLastName, personalInfos.thLastName) &&
        Objects.equals(this.thMidName, personalInfos.thMidName) &&
        Objects.equals(this.enTitleCode, personalInfos.enTitleCode) &&
        Objects.equals(this.enTitleDesc, personalInfos.enTitleDesc) &&
        Objects.equals(this.enFirstName, personalInfos.enFirstName) &&
        Objects.equals(this.enLastName, personalInfos.enLastName) &&
        Objects.equals(this.enMidName, personalInfos.enMidName) &&
        Objects.equals(this.birthDate, personalInfos.birthDate) &&
        Objects.equals(this.cidExpDate, personalInfos.cidExpDate) &&
        Objects.equals(this.cidIssueDate, personalInfos.cidIssueDate) &&
        Objects.equals(this.cisPrimSegment, personalInfos.cisPrimSegment) &&
        Objects.equals(this.cisPrimSubSegment, personalInfos.cisPrimSubSegment) &&
        Objects.equals(this.cisSecSegment, personalInfos.cisSecSegment) &&
        Objects.equals(this.cisSecSubSegment, personalInfos.cisSecSubSegment) &&
        Objects.equals(this.contactTime, personalInfos.contactTime) &&
        Objects.equals(this.customerType, personalInfos.customerType) &&
        Objects.equals(this.degree, personalInfos.degree) &&
        Objects.equals(this.disclosureFlag, personalInfos.disclosureFlag) &&
        Objects.equals(this.bundleWithMainFlag, personalInfos.bundleWithMainFlag) &&
        Objects.equals(this.businessType, personalInfos.businessType) &&
        Objects.equals(this.employmentType, personalInfos.employmentType) &&
        Objects.equals(this.freelanceIncome, personalInfos.freelanceIncome) &&
        Objects.equals(this.freelanceType, personalInfos.freelanceType) &&
        Objects.equals(this.gender, personalInfos.gender) &&
        Objects.equals(this.married, personalInfos.married) &&
        Objects.equals(this.mobileNo, personalInfos.mobileNo) &&
        Objects.equals(this.monthlyExpense, personalInfos.monthlyExpense) &&
        Objects.equals(this.monthlyIncome, personalInfos.monthlyIncome) &&
        Objects.equals(this.nationality, personalInfos.nationality) &&
        Objects.equals(this.netProfitIncome, personalInfos.netProfitIncome) &&
        Objects.equals(this.numberOfChild, personalInfos.numberOfChild) &&
        Objects.equals(this.occupation, personalInfos.occupation) &&
        Objects.equals(this.previousCompanyTitle, personalInfos.previousCompanyTitle) &&
        Objects.equals(this.previousWorkMonth, personalInfos.previousWorkMonth) &&
        Objects.equals(this.previousWorkYear, personalInfos.previousWorkYear) &&
        Objects.equals(this.profession, personalInfos.profession) &&
        Objects.equals(this.professionOther, personalInfos.professionOther) &&
        Objects.equals(this.receiveIncomeBank, personalInfos.receiveIncomeBank) &&
        Objects.equals(this.relationshipType, personalInfos.relationshipType) &&
        Objects.equals(this.requireBureau, personalInfos.requireBureau) &&
        Objects.equals(this.ncbTranNo, personalInfos.ncbTranNo) &&
        Objects.equals(this.consentRefNo, personalInfos.consentRefNo) &&
        Objects.equals(this.salary, personalInfos.salary) &&
        Objects.equals(this.sourceOfIncomeOther, personalInfos.sourceOfIncomeOther) &&
        Objects.equals(this.srcOthIncOther, personalInfos.srcOthIncOther) &&
        Objects.equals(this.srcOthIncBonus, personalInfos.srcOthIncBonus) &&
        Objects.equals(this.srcOthIncCommission, personalInfos.srcOthIncCommission) &&
        Objects.equals(this.totalWorkMonth, personalInfos.totalWorkMonth) &&
        Objects.equals(this.totalWorkYear, personalInfos.totalWorkYear) &&
        Objects.equals(this.income, personalInfos.income) &&
        Objects.equals(this.otherIncome, personalInfos.otherIncome) &&
        Objects.equals(this.vatRegisterFlag, personalInfos.vatRegisterFlag) &&
        Objects.equals(this.visaExpiryDate, personalInfos.visaExpiryDate) &&
        Objects.equals(this.visaType, personalInfos.visaType) &&
        Objects.equals(this.workPermitExpiryDate, personalInfos.workPermitExpiryDate) &&
        Objects.equals(this.workPermitNo, personalInfos.workPermitNo) &&
        Objects.equals(this.mailingAddress, personalInfos.mailingAddress) &&
        Objects.equals(this.positionType, personalInfos.positionType) &&
        Objects.equals(this.positionLevel, personalInfos.positionLevel) &&
        Objects.equals(this.salaryLevel, personalInfos.salaryLevel) &&
        Objects.equals(this.passportExpiryDate, personalInfos.passportExpiryDate) &&
        Objects.equals(this.previousCompanyName, personalInfos.previousCompanyName) &&
        Objects.equals(this.specialMerchant, personalInfos.specialMerchant) &&
        Objects.equals(this.businessTypeOther, personalInfos.businessTypeOther) &&
        Objects.equals(this.positionDesc, personalInfos.positionDesc) &&
        Objects.equals(this.positionCode, personalInfos.positionCode) &&
        Objects.equals(this.businessNature, personalInfos.businessNature) &&
        Objects.equals(this.placeReceiveCard, personalInfos.placeReceiveCard) &&
        Objects.equals(this.companyCisId, personalInfos.companyCisId) &&
        Objects.equals(this.emailPrimary, personalInfos.emailPrimary) &&
        Objects.equals(this.selfDeclareLimit, personalInfos.selfDeclareLimit) &&
        Objects.equals(this.numberOfIssuer, personalInfos.numberOfIssuer) &&
        Objects.equals(this.mThOldLastName, personalInfos.mThOldLastName) &&
        Objects.equals(this.mThLastName, personalInfos.mThLastName) &&
        Objects.equals(this.mTitleDesc, personalInfos.mTitleDesc) &&
        Objects.equals(this.mHomeTel, personalInfos.mHomeTel) &&
        Objects.equals(this.mIncome, personalInfos.mIncome) &&
        Objects.equals(this.mPosition, personalInfos.mPosition) &&
        Objects.equals(this.cisPrimDesc, personalInfos.cisPrimDesc) &&
        Objects.equals(this.cisPrimSubDesc, personalInfos.cisPrimSubDesc) &&
        Objects.equals(this.cisSecDesc, personalInfos.cisSecDesc) &&
        Objects.equals(this.cisSecSubDesc, personalInfos.cisSecSubDesc) &&
        Objects.equals(this.lineId, personalInfos.lineId) &&
        Objects.equals(this.incomeResource, personalInfos.incomeResource) &&
        Objects.equals(this.incomeResourceOther, personalInfos.incomeResourceOther) &&
        Objects.equals(this.countryOfIncome, personalInfos.countryOfIncome) &&
        Objects.equals(this.occupationOther, personalInfos.occupationOther) &&
        Objects.equals(this.addresses, personalInfos.addresses) &&
        Objects.equals(this.kycInfos, personalInfos.kycInfos) &&
        Objects.equals(this.documents, personalInfos.documents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personalId, personalType, cisNo, cidType, idNo, thTitleCode, thTitleDesc, thFirstName, thLastName, thMidName, enTitleCode, enTitleDesc, enFirstName, enLastName, enMidName, birthDate, cidExpDate, cidIssueDate, cisPrimSegment, cisPrimSubSegment, cisSecSegment, cisSecSubSegment, contactTime, customerType, degree, disclosureFlag, bundleWithMainFlag, businessType, employmentType, freelanceIncome, freelanceType, gender, married, mobileNo, monthlyExpense, monthlyIncome, nationality, netProfitIncome, numberOfChild, occupation, previousCompanyTitle, previousWorkMonth, previousWorkYear, profession, professionOther, receiveIncomeBank, relationshipType, requireBureau, ncbTranNo, consentRefNo, salary, sourceOfIncomeOther, srcOthIncOther, srcOthIncBonus, srcOthIncCommission, totalWorkMonth, totalWorkYear, income, otherIncome, vatRegisterFlag, visaExpiryDate, visaType, workPermitExpiryDate, workPermitNo, mailingAddress, positionType, positionLevel, salaryLevel, passportExpiryDate, previousCompanyName, specialMerchant, businessTypeOther, positionDesc, positionCode, businessNature, placeReceiveCard, companyCisId, emailPrimary, selfDeclareLimit, numberOfIssuer, mThOldLastName, mThLastName, mTitleDesc, mHomeTel, mIncome, mPosition, cisPrimDesc, cisPrimSubDesc, cisSecDesc, cisSecSubDesc, lineId, incomeResource, incomeResourceOther, countryOfIncome, occupationOther, addresses, kycInfos, documents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalInfos {\n");
    
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    personalType: ").append(toIndentedString(personalType)).append("\n");
    sb.append("    cisNo: ").append(toIndentedString(cisNo)).append("\n");
    sb.append("    cidType: ").append(toIndentedString(cidType)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    thTitleCode: ").append(toIndentedString(thTitleCode)).append("\n");
    sb.append("    thTitleDesc: ").append(toIndentedString(thTitleDesc)).append("\n");
    sb.append("    thFirstName: ").append(toIndentedString(thFirstName)).append("\n");
    sb.append("    thLastName: ").append(toIndentedString(thLastName)).append("\n");
    sb.append("    thMidName: ").append(toIndentedString(thMidName)).append("\n");
    sb.append("    enTitleCode: ").append(toIndentedString(enTitleCode)).append("\n");
    sb.append("    enTitleDesc: ").append(toIndentedString(enTitleDesc)).append("\n");
    sb.append("    enFirstName: ").append(toIndentedString(enFirstName)).append("\n");
    sb.append("    enLastName: ").append(toIndentedString(enLastName)).append("\n");
    sb.append("    enMidName: ").append(toIndentedString(enMidName)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    cidExpDate: ").append(toIndentedString(cidExpDate)).append("\n");
    sb.append("    cidIssueDate: ").append(toIndentedString(cidIssueDate)).append("\n");
    sb.append("    cisPrimSegment: ").append(toIndentedString(cisPrimSegment)).append("\n");
    sb.append("    cisPrimSubSegment: ").append(toIndentedString(cisPrimSubSegment)).append("\n");
    sb.append("    cisSecSegment: ").append(toIndentedString(cisSecSegment)).append("\n");
    sb.append("    cisSecSubSegment: ").append(toIndentedString(cisSecSubSegment)).append("\n");
    sb.append("    contactTime: ").append(toIndentedString(contactTime)).append("\n");
    sb.append("    customerType: ").append(toIndentedString(customerType)).append("\n");
    sb.append("    degree: ").append(toIndentedString(degree)).append("\n");
    sb.append("    disclosureFlag: ").append(toIndentedString(disclosureFlag)).append("\n");
    sb.append("    bundleWithMainFlag: ").append(toIndentedString(bundleWithMainFlag)).append("\n");
    sb.append("    businessType: ").append(toIndentedString(businessType)).append("\n");
    sb.append("    employmentType: ").append(toIndentedString(employmentType)).append("\n");
    sb.append("    freelanceIncome: ").append(toIndentedString(freelanceIncome)).append("\n");
    sb.append("    freelanceType: ").append(toIndentedString(freelanceType)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    married: ").append(toIndentedString(married)).append("\n");
    sb.append("    mobileNo: ").append(toIndentedString(mobileNo)).append("\n");
    sb.append("    monthlyExpense: ").append(toIndentedString(monthlyExpense)).append("\n");
    sb.append("    monthlyIncome: ").append(toIndentedString(monthlyIncome)).append("\n");
    sb.append("    nationality: ").append(toIndentedString(nationality)).append("\n");
    sb.append("    netProfitIncome: ").append(toIndentedString(netProfitIncome)).append("\n");
    sb.append("    numberOfChild: ").append(toIndentedString(numberOfChild)).append("\n");
    sb.append("    occupation: ").append(toIndentedString(occupation)).append("\n");
    sb.append("    previousCompanyTitle: ").append(toIndentedString(previousCompanyTitle)).append("\n");
    sb.append("    previousWorkMonth: ").append(toIndentedString(previousWorkMonth)).append("\n");
    sb.append("    previousWorkYear: ").append(toIndentedString(previousWorkYear)).append("\n");
    sb.append("    profession: ").append(toIndentedString(profession)).append("\n");
    sb.append("    professionOther: ").append(toIndentedString(professionOther)).append("\n");
    sb.append("    receiveIncomeBank: ").append(toIndentedString(receiveIncomeBank)).append("\n");
    sb.append("    relationshipType: ").append(toIndentedString(relationshipType)).append("\n");
    sb.append("    requireBureau: ").append(toIndentedString(requireBureau)).append("\n");
    sb.append("    ncbTranNo: ").append(toIndentedString(ncbTranNo)).append("\n");
    sb.append("    consentRefNo: ").append(toIndentedString(consentRefNo)).append("\n");
    sb.append("    salary: ").append(toIndentedString(salary)).append("\n");
    sb.append("    sourceOfIncomeOther: ").append(toIndentedString(sourceOfIncomeOther)).append("\n");
    sb.append("    srcOthIncOther: ").append(toIndentedString(srcOthIncOther)).append("\n");
    sb.append("    srcOthIncBonus: ").append(toIndentedString(srcOthIncBonus)).append("\n");
    sb.append("    srcOthIncCommission: ").append(toIndentedString(srcOthIncCommission)).append("\n");
    sb.append("    totalWorkMonth: ").append(toIndentedString(totalWorkMonth)).append("\n");
    sb.append("    totalWorkYear: ").append(toIndentedString(totalWorkYear)).append("\n");
    sb.append("    income: ").append(toIndentedString(income)).append("\n");
    sb.append("    otherIncome: ").append(toIndentedString(otherIncome)).append("\n");
    sb.append("    vatRegisterFlag: ").append(toIndentedString(vatRegisterFlag)).append("\n");
    sb.append("    visaExpiryDate: ").append(toIndentedString(visaExpiryDate)).append("\n");
    sb.append("    visaType: ").append(toIndentedString(visaType)).append("\n");
    sb.append("    workPermitExpiryDate: ").append(toIndentedString(workPermitExpiryDate)).append("\n");
    sb.append("    workPermitNo: ").append(toIndentedString(workPermitNo)).append("\n");
    sb.append("    mailingAddress: ").append(toIndentedString(mailingAddress)).append("\n");
    sb.append("    positionType: ").append(toIndentedString(positionType)).append("\n");
    sb.append("    positionLevel: ").append(toIndentedString(positionLevel)).append("\n");
    sb.append("    salaryLevel: ").append(toIndentedString(salaryLevel)).append("\n");
    sb.append("    passportExpiryDate: ").append(toIndentedString(passportExpiryDate)).append("\n");
    sb.append("    previousCompanyName: ").append(toIndentedString(previousCompanyName)).append("\n");
    sb.append("    specialMerchant: ").append(toIndentedString(specialMerchant)).append("\n");
    sb.append("    businessTypeOther: ").append(toIndentedString(businessTypeOther)).append("\n");
    sb.append("    positionDesc: ").append(toIndentedString(positionDesc)).append("\n");
    sb.append("    positionCode: ").append(toIndentedString(positionCode)).append("\n");
    sb.append("    businessNature: ").append(toIndentedString(businessNature)).append("\n");
    sb.append("    placeReceiveCard: ").append(toIndentedString(placeReceiveCard)).append("\n");
    sb.append("    companyCisId: ").append(toIndentedString(companyCisId)).append("\n");
    sb.append("    emailPrimary: ").append(toIndentedString(emailPrimary)).append("\n");
    sb.append("    selfDeclareLimit: ").append(toIndentedString(selfDeclareLimit)).append("\n");
    sb.append("    numberOfIssuer: ").append(toIndentedString(numberOfIssuer)).append("\n");
    sb.append("    mThOldLastName: ").append(toIndentedString(mThOldLastName)).append("\n");
    sb.append("    mThLastName: ").append(toIndentedString(mThLastName)).append("\n");
    sb.append("    mTitleDesc: ").append(toIndentedString(mTitleDesc)).append("\n");
    sb.append("    mHomeTel: ").append(toIndentedString(mHomeTel)).append("\n");
    sb.append("    mIncome: ").append(toIndentedString(mIncome)).append("\n");
    sb.append("    mPosition: ").append(toIndentedString(mPosition)).append("\n");
    sb.append("    cisPrimDesc: ").append(toIndentedString(cisPrimDesc)).append("\n");
    sb.append("    cisPrimSubDesc: ").append(toIndentedString(cisPrimSubDesc)).append("\n");
    sb.append("    cisSecDesc: ").append(toIndentedString(cisSecDesc)).append("\n");
    sb.append("    cisSecSubDesc: ").append(toIndentedString(cisSecSubDesc)).append("\n");
    sb.append("    lineId: ").append(toIndentedString(lineId)).append("\n");
    sb.append("    incomeResource: ").append(toIndentedString(incomeResource)).append("\n");
    sb.append("    incomeResourceOther: ").append(toIndentedString(incomeResourceOther)).append("\n");
    sb.append("    countryOfIncome: ").append(toIndentedString(countryOfIncome)).append("\n");
    sb.append("    occupationOther: ").append(toIndentedString(occupationOther)).append("\n");
    sb.append("    addresses: ").append(toIndentedString(addresses)).append("\n");
    sb.append("    kycInfos: ").append(toIndentedString(kycInfos)).append("\n");
    sb.append("    documents: ").append(toIndentedString(documents)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  //MLP Additional
  
  @JsonProperty("staffFlag")
  private String staffFlag = null;
  
  @JsonProperty("verifyIncome")
  private BigDecimal verifyIncome = null;
  
  @JsonProperty("typeOfFin")
  private String typeOfFin = null;
  
  @JsonProperty("cardLinkCustomers")
  private List<CardLinkCustomers> cardLinkCustomers = null;

public String getStaffFlag() {
	return staffFlag;
}

public void setStaffFlag(String staffFlag) {
	this.staffFlag = staffFlag;
}

public BigDecimal getVerifyIncome() {
	return verifyIncome;
}

public void setVerifyIncome(BigDecimal verifyIncome) {
	this.verifyIncome = verifyIncome;
}

public String getTypeOfFin() {
	return typeOfFin;
}

public void setTypeOfFin(String typeOfFin) {
	this.typeOfFin = typeOfFin;
}

public List<CardLinkCustomers> getCardLinkCustomers() {
	return cardLinkCustomers;
}

public void setCardLinkCustomers(List<CardLinkCustomers> cardLinkCustomers) {
	this.cardLinkCustomers = cardLinkCustomers;
}
}

