package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.Addresses;
import com.ava.flp.eapp.iib.model.ApplicationScores;
import com.ava.flp.eapp.iib.model.BScores;
import com.ava.flp.eapp.iib.model.BolOrganizations;
import com.ava.flp.eapp.iib.model.CardLinkCards;
import com.ava.flp.eapp.iib.model.CardLinkCustomers;
import com.ava.flp.eapp.iib.model.CcaCampaignInfos;
import com.ava.flp.eapp.iib.model.CheckKPLDuplicateResponse;
import com.ava.flp.eapp.iib.model.Cis0368i01Response;
import com.ava.flp.eapp.iib.model.CoBrand;
import com.ava.flp.eapp.iib.model.CurrentAccounts;
import com.ava.flp.eapp.iib.model.Cvrs1312i01Responses;
import com.ava.flp.eapp.iib.model.DebtInfo;
import com.ava.flp.eapp.iib.model.DocumentSlaTypes;
import com.ava.flp.eapp.iib.model.Documents;
import com.ava.flp.eapp.iib.model.FixAccounts;
import com.ava.flp.eapp.iib.model.IncomeInfos;
import com.ava.flp.eapp.iib.model.KAssets;
import com.ava.flp.eapp.iib.model.KBank1211i01Response;
import com.ava.flp.eapp.iib.model.KBank1550i01Response;
import com.ava.flp.eapp.iib.model.KBankEmployee;
import com.ava.flp.eapp.iib.model.KBankPayroll;
import com.ava.flp.eapp.iib.model.KBankSalaryList;
import com.ava.flp.eapp.iib.model.KcbsResponse;
import com.ava.flp.eapp.iib.model.LeadInfos;
import com.ava.flp.eapp.iib.model.LpmBlacklists;
import com.ava.flp.eapp.iib.model.NcbInfo;
import com.ava.flp.eapp.iib.model.OldIncomes;
import com.ava.flp.eapp.iib.model.PersonalInfosAdditionalField;
import com.ava.flp.eapp.iib.model.SafeLoans;
import com.ava.flp.eapp.iib.model.SavingAccountsPersonalInfos;
import com.ava.flp.eapp.iib.model.SoloCustomers;
import com.ava.flp.eapp.iib.model.StableIncome;
import com.ava.flp.eapp.iib.model.TcbLoans;
import com.ava.flp.eapp.iib.model.TrustedCompanys;
import com.ava.flp.eapp.iib.model.VerificationResultPersonalInfos;
import com.ava.flp.eapp.iib.model.Wealths;
import com.ava.flp.eapp.iib.model.WfInquiryResponseM;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PersonalInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T18:23:47.347+07:00")

public class PersonalInfos   {
  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("personalType")
  private String personalType = null;

  @JsonProperty("customerType")
  private String customerType = null;

  @JsonProperty("cisNo")
  private String cisNo = null;

  @JsonProperty("cidType")
  private String cidType = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("thTitleCode")
  private String thTitleCode = null;

  @JsonProperty("thFirstName")
  private String thFirstName = null;

  @JsonProperty("thLastName")
  private String thLastName = null;

  @JsonProperty("thMidName")
  private String thMidName = null;

  @JsonProperty("enTitleCode")
  private String enTitleCode = null;

  @JsonProperty("enFirstName")
  private String enFirstName = null;

  @JsonProperty("enLastName")
  private String enLastName = null;

  @JsonProperty("enMidName")
  private String enMidName = null;

  @JsonProperty("birthDate")
  private DateTime birthDate = null;

  @JsonProperty("ncbTranNo")
  private String ncbTranNo = null;

  @JsonProperty("consentRefNo")
  private String consentRefNo = null;

  @JsonProperty("branchReceiveCard")
  private String branchReceiveCard = null;

  @JsonProperty("bundleWithMainFlag")
  private String bundleWithMainFlag = null;

  @JsonProperty("businessType")
  private String businessType = null;

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

  @JsonProperty("degree")
  private String degree = null;

  @JsonProperty("gender")
  private String gender = null;

  @JsonProperty("married")
  private String married = null;

  @JsonProperty("moblieNo")
  private String moblieNo = null;

  @JsonProperty("nationality")
  private String nationality = null;

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

  @JsonProperty("receiveIncomeBank")
  private String receiveIncomeBank = null;

  @JsonProperty("relationshipType")
  private String relationshipType = null;

  @JsonProperty("requireBureau")
  private String requireBureau = null;

  @JsonProperty("totalWorkMonth")
  private BigDecimal totalWorkMonth = null;

  @JsonProperty("totalWorkYear")
  private BigDecimal totalWorkYear = null;

  @JsonProperty("totVerifiedIncome")
  private BigDecimal totVerifiedIncome = null;

  @JsonProperty("income")
  private BigDecimal income = null;

  @JsonProperty("otherIncome")
  private BigDecimal otherIncome = null;

  @JsonProperty("typeofFin")
  private String typeofFin = null;

  @JsonProperty("vatRegisterFlag")
  private String vatRegisterFlag = null;

  @JsonProperty("visaExpiryDate")
  private DateTime visaExpiryDate = null;

  @JsonProperty("visaType")
  private String visaType = null;

  @JsonProperty("workPermitExpiryDate")
  private DateTime workPermitExpiryDate = null;

  @JsonProperty("workPermitNo")
  private String workPermitNo = null;

  @JsonProperty("mailingAddress")
  private String mailingAddress = null;

  @JsonProperty("wfVerfiedFlag")
  private String wfVerfiedFlag = null;

  @JsonProperty("positionType")
  private String positionType = null;

  @JsonProperty("positionLevel")
  private String positionLevel = null;

  @JsonProperty("salaryLevel")
  private String salaryLevel = null;

  @JsonProperty("passportExpiryDate")
  private DateTime passportExpiryDate = null;

  @JsonProperty("previousCompanyName")
  private String previousCompanyName = null;

  @JsonProperty("specialMerchant")
  private String specialMerchant = null;

  @JsonProperty("companyType")
  private String companyType = null;

  @JsonProperty("kGroupFlag")
  private String kGroupFlag = null;

  @JsonProperty("assetsAmount")
  private BigDecimal assetsAmount = null;

  @JsonProperty("totPreIncome")
  private BigDecimal totPreIncome = null;

  @JsonProperty("infoFlag")
  private String infoFlag = null;

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

  @JsonProperty("foundBOLFlag")
  private String foundBOLFlag = null;

  @JsonProperty("foundBScoreFlag")
  private String foundBScoreFlag = null;

  @JsonProperty("foundCardlinkCardFlag")
  private String foundCardlinkCardFlag = null;

  @JsonProperty("foundCardlinkCustomerFlag")
  private String foundCardlinkCustomerFlag = null;

  @JsonProperty("foundCCAFlag")
  private String foundCCAFlag = null;

  @JsonProperty("foundCoBrandFlag")
  private String foundCoBrandFlag = null;

  @JsonProperty("foundCurrentAccountFlag")
  private String foundCurrentAccountFlag = null;

  @JsonProperty("foundFixAccountFlag")
  private String foundFixAccountFlag = null;

  @JsonProperty("foundKAssetFlag")
  private String foundKAssetFlag = null;

  @JsonProperty("foundKBankEmployeeFlag")
  private String foundKBankEmployeeFlag = null;

  @JsonProperty("foundKBankPayrollFlag")
  private String foundKBankPayrollFlag = null;

  @JsonProperty("foundKBankSalaryFlag")
  private String foundKBankSalaryFlag = null;

  @JsonProperty("foundLPMBlacklistFlag")
  private String foundLPMBlacklistFlag = null;

  @JsonProperty("foundOldIncomeFlag")
  private String foundOldIncomeFlag = null;

  @JsonProperty("foundPayrollFlag")
  private String foundPayrollFlag = null;

  @JsonProperty("foundSAFELoanFlag")
  private String foundSAFELoanFlag = null;

  @JsonProperty("foundSavingAccountFlag")
  private String foundSavingAccountFlag = null;

  @JsonProperty("foundAllSavingAccountFlag")
  private String foundAllSavingAccountFlag = null;

  @JsonProperty("foundSoloFlag")
  private String foundSoloFlag = null;

  @JsonProperty("foundThaiBevFlag")
  private String foundThaiBevFlag = null;

  @JsonProperty("foundTCBLoanFlag")
  private String foundTCBLoanFlag = null;

  @JsonProperty("foundAllTCBLoanFlag")
  private String foundAllTCBLoanFlag = null;

  @JsonProperty("foundTrustedCompanyFlag")
  private String foundTrustedCompanyFlag = null;

  @JsonProperty("foundWealthFlag")
  private String foundWealthFlag = null;

  @JsonProperty("foundStableIncomeFlag")
  private String foundStableIncomeFlag = null;

  @JsonProperty("addresses")
  private List<Addresses> addresses = null;

  @JsonProperty("applicationScores")
  private List<ApplicationScores> applicationScores = null;

  @JsonProperty("bolOrganizations")
  private List<BolOrganizations> bolOrganizations = null;

  @JsonProperty("cardLinkCustomers")
  private List<CardLinkCustomers> cardLinkCustomers = null;

  @JsonProperty("cardLinkCards")
  private List<CardLinkCards> cardLinkCards = null;

  @JsonProperty("kBankPayroll")
  private KBankPayroll kBankPayroll = null;

  @JsonProperty("debtInfo")
  private List<DebtInfo> debtInfo = null;

  @JsonProperty("documents")
  private List<Documents> documents = null;

  @JsonProperty("documentSlaTypes")
  private List<DocumentSlaTypes> documentSlaTypes = null;

  @JsonProperty("incomeInfos")
  private List<IncomeInfos> incomeInfos = null;

  @JsonProperty("ncbInfo")
  private NcbInfo ncbInfo = null;

  @JsonProperty("verificationResult")
  private VerificationResultPersonalInfos verificationResult = null;

  @JsonProperty("bScores")
  private List<BScores> bScores = null;

  @JsonProperty("ccaCampaignInfos")
  private List<CcaCampaignInfos> ccaCampaignInfos = null;

  @JsonProperty("checkKPLDuplicateResponse")
  private CheckKPLDuplicateResponse checkKPLDuplicateResponse = null;

  @JsonProperty("cis0368i01Response")
  private Cis0368i01Response cis0368i01Response = null;

  @JsonProperty("coBrand")
  private CoBrand coBrand = null;

  @JsonProperty("currentAccounts")
  private List<CurrentAccounts> currentAccounts = null;

  @JsonProperty("cvrs1312i01Responses")
  private List<Cvrs1312i01Responses> cvrs1312i01Responses = null;

  @JsonProperty("fixAccounts")
  private List<FixAccounts> fixAccounts = null;

  @JsonProperty("leadInfos")
  private List<LeadInfos> leadInfos = null;

  @JsonProperty("lpmBlacklists")
  private List<LpmBlacklists> lpmBlacklists = null;

  @JsonProperty("kAssets")
  private List<KAssets> kAssets = null;

  @JsonProperty("kcbsResponse")
  private KcbsResponse kcbsResponse = null;

  @JsonProperty("kBank1211i01Response")
  private KBank1211i01Response kBank1211i01Response = null;

  @JsonProperty("kBank1550i01Response")
  private KBank1550i01Response kBank1550i01Response = null;

  @JsonProperty("kBankEmployee")
  private KBankEmployee kBankEmployee = null;

  @JsonProperty("kBankSalaryList")
  private KBankSalaryList kBankSalaryList = null;

  @JsonProperty("oldIncomes")
  private List<OldIncomes> oldIncomes = null;

  @JsonProperty("safeLoans")
  private List<SafeLoans> safeLoans = null;

  @JsonProperty("savingAccounts")
  private List<SavingAccountsPersonalInfos> savingAccounts = null;

  @JsonProperty("soloCustomers")
  private List<SoloCustomers> soloCustomers = null;

  @JsonProperty("stableIncome")
  private StableIncome stableIncome = null;

  @JsonProperty("tcbLoans")
  private List<TcbLoans> tcbLoans = null;

  @JsonProperty("trustedCompanys")
  private TrustedCompanys trustedCompanys = null;

  @JsonProperty("wealths")
  private List<Wealths> wealths = null;

  @JsonProperty("wfInquiryResponseM")
  private WfInquiryResponseM wfInquiryResponseM = null;

  @JsonProperty("additionalField")
  private PersonalInfosAdditionalField additionalField = null;

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

  public PersonalInfos birthDate(DateTime birthDate) {
    this.birthDate = birthDate;
    return this;
  }

   /**
   * Get birthDate
   * @return birthDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(DateTime birthDate) {
    this.birthDate = birthDate;
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

  public PersonalInfos branchReceiveCard(String branchReceiveCard) {
    this.branchReceiveCard = branchReceiveCard;
    return this;
  }

   /**
   * Get branchReceiveCard
   * @return branchReceiveCard
  **/
  @ApiModelProperty(value = "")


  public String getBranchReceiveCard() {
    return branchReceiveCard;
  }

  public void setBranchReceiveCard(String branchReceiveCard) {
    this.branchReceiveCard = branchReceiveCard;
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

  public PersonalInfos moblieNo(String moblieNo) {
    this.moblieNo = moblieNo;
    return this;
  }

   /**
   * Get moblieNo
   * @return moblieNo
  **/
  @ApiModelProperty(value = "")


  public String getMoblieNo() {
    return moblieNo;
  }

  public void setMoblieNo(String moblieNo) {
    this.moblieNo = moblieNo;
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

  public PersonalInfos totVerifiedIncome(BigDecimal totVerifiedIncome) {
    this.totVerifiedIncome = totVerifiedIncome;
    return this;
  }

   /**
   * Get totVerifiedIncome
   * @return totVerifiedIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotVerifiedIncome() {
    return totVerifiedIncome;
  }

  public void setTotVerifiedIncome(BigDecimal totVerifiedIncome) {
    this.totVerifiedIncome = totVerifiedIncome;
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

  public PersonalInfos typeofFin(String typeofFin) {
    this.typeofFin = typeofFin;
    return this;
  }

   /**
   * Get typeofFin
   * @return typeofFin
  **/
  @ApiModelProperty(value = "")


  public String getTypeofFin() {
    return typeofFin;
  }

  public void setTypeofFin(String typeofFin) {
    this.typeofFin = typeofFin;
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

  public PersonalInfos visaExpiryDate(DateTime visaExpiryDate) {
    this.visaExpiryDate = visaExpiryDate;
    return this;
  }

   /**
   * Get visaExpiryDate
   * @return visaExpiryDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getVisaExpiryDate() {
    return visaExpiryDate;
  }

  public void setVisaExpiryDate(DateTime visaExpiryDate) {
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

  public PersonalInfos workPermitExpiryDate(DateTime workPermitExpiryDate) {
    this.workPermitExpiryDate = workPermitExpiryDate;
    return this;
  }

   /**
   * Get workPermitExpiryDate
   * @return workPermitExpiryDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getWorkPermitExpiryDate() {
    return workPermitExpiryDate;
  }

  public void setWorkPermitExpiryDate(DateTime workPermitExpiryDate) {
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

  public PersonalInfos wfVerfiedFlag(String wfVerfiedFlag) {
    this.wfVerfiedFlag = wfVerfiedFlag;
    return this;
  }

   /**
   * Get wfVerfiedFlag
   * @return wfVerfiedFlag
  **/
  @ApiModelProperty(value = "")


  public String getWfVerfiedFlag() {
    return wfVerfiedFlag;
  }

  public void setWfVerfiedFlag(String wfVerfiedFlag) {
    this.wfVerfiedFlag = wfVerfiedFlag;
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

  public PersonalInfos passportExpiryDate(DateTime passportExpiryDate) {
    this.passportExpiryDate = passportExpiryDate;
    return this;
  }

   /**
   * Get passportExpiryDate
   * @return passportExpiryDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getPassportExpiryDate() {
    return passportExpiryDate;
  }

  public void setPassportExpiryDate(DateTime passportExpiryDate) {
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

  public PersonalInfos companyType(String companyType) {
    this.companyType = companyType;
    return this;
  }

   /**
   * Get companyType
   * @return companyType
  **/
  @ApiModelProperty(value = "")


  public String getCompanyType() {
    return companyType;
  }

  public void setCompanyType(String companyType) {
    this.companyType = companyType;
  }

  public PersonalInfos kGroupFlag(String kGroupFlag) {
    this.kGroupFlag = kGroupFlag;
    return this;
  }

   /**
   * Get kGroupFlag
   * @return kGroupFlag
  **/
  @ApiModelProperty(value = "")


  public String getKGroupFlag() {
    return kGroupFlag;
  }

  public void setKGroupFlag(String kGroupFlag) {
    this.kGroupFlag = kGroupFlag;
  }

  public PersonalInfos assetsAmount(BigDecimal assetsAmount) {
    this.assetsAmount = assetsAmount;
    return this;
  }

   /**
   * Get assetsAmount
   * @return assetsAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAssetsAmount() {
    return assetsAmount;
  }

  public void setAssetsAmount(BigDecimal assetsAmount) {
    this.assetsAmount = assetsAmount;
  }

  public PersonalInfos totPreIncome(BigDecimal totPreIncome) {
    this.totPreIncome = totPreIncome;
    return this;
  }

   /**
   * Get totPreIncome
   * @return totPreIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotPreIncome() {
    return totPreIncome;
  }

  public void setTotPreIncome(BigDecimal totPreIncome) {
    this.totPreIncome = totPreIncome;
  }

  public PersonalInfos infoFlag(String infoFlag) {
    this.infoFlag = infoFlag;
    return this;
  }

   /**
   * Get infoFlag
   * @return infoFlag
  **/
  @ApiModelProperty(value = "")


  public String getInfoFlag() {
    return infoFlag;
  }

  public void setInfoFlag(String infoFlag) {
    this.infoFlag = infoFlag;
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

  public PersonalInfos foundBOLFlag(String foundBOLFlag) {
    this.foundBOLFlag = foundBOLFlag;
    return this;
  }

   /**
   * Get foundBOLFlag
   * @return foundBOLFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundBOLFlag() {
    return foundBOLFlag;
  }

  public void setFoundBOLFlag(String foundBOLFlag) {
    this.foundBOLFlag = foundBOLFlag;
  }

  public PersonalInfos foundBScoreFlag(String foundBScoreFlag) {
    this.foundBScoreFlag = foundBScoreFlag;
    return this;
  }

   /**
   * Get foundBScoreFlag
   * @return foundBScoreFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundBScoreFlag() {
    return foundBScoreFlag;
  }

  public void setFoundBScoreFlag(String foundBScoreFlag) {
    this.foundBScoreFlag = foundBScoreFlag;
  }

  public PersonalInfos foundCardlinkCardFlag(String foundCardlinkCardFlag) {
    this.foundCardlinkCardFlag = foundCardlinkCardFlag;
    return this;
  }

   /**
   * Get foundCardlinkCardFlag
   * @return foundCardlinkCardFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundCardlinkCardFlag() {
    return foundCardlinkCardFlag;
  }

  public void setFoundCardlinkCardFlag(String foundCardlinkCardFlag) {
    this.foundCardlinkCardFlag = foundCardlinkCardFlag;
  }

  public PersonalInfos foundCardlinkCustomerFlag(String foundCardlinkCustomerFlag) {
    this.foundCardlinkCustomerFlag = foundCardlinkCustomerFlag;
    return this;
  }

   /**
   * Get foundCardlinkCustomerFlag
   * @return foundCardlinkCustomerFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundCardlinkCustomerFlag() {
    return foundCardlinkCustomerFlag;
  }

  public void setFoundCardlinkCustomerFlag(String foundCardlinkCustomerFlag) {
    this.foundCardlinkCustomerFlag = foundCardlinkCustomerFlag;
  }

  public PersonalInfos foundCCAFlag(String foundCCAFlag) {
    this.foundCCAFlag = foundCCAFlag;
    return this;
  }

   /**
   * Get foundCCAFlag
   * @return foundCCAFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundCCAFlag() {
    return foundCCAFlag;
  }

  public void setFoundCCAFlag(String foundCCAFlag) {
    this.foundCCAFlag = foundCCAFlag;
  }

  public PersonalInfos foundCoBrandFlag(String foundCoBrandFlag) {
    this.foundCoBrandFlag = foundCoBrandFlag;
    return this;
  }

   /**
   * Get foundCoBrandFlag
   * @return foundCoBrandFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundCoBrandFlag() {
    return foundCoBrandFlag;
  }

  public void setFoundCoBrandFlag(String foundCoBrandFlag) {
    this.foundCoBrandFlag = foundCoBrandFlag;
  }

  public PersonalInfos foundCurrentAccountFlag(String foundCurrentAccountFlag) {
    this.foundCurrentAccountFlag = foundCurrentAccountFlag;
    return this;
  }

   /**
   * Get foundCurrentAccountFlag
   * @return foundCurrentAccountFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundCurrentAccountFlag() {
    return foundCurrentAccountFlag;
  }

  public void setFoundCurrentAccountFlag(String foundCurrentAccountFlag) {
    this.foundCurrentAccountFlag = foundCurrentAccountFlag;
  }

  public PersonalInfos foundFixAccountFlag(String foundFixAccountFlag) {
    this.foundFixAccountFlag = foundFixAccountFlag;
    return this;
  }

   /**
   * Get foundFixAccountFlag
   * @return foundFixAccountFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundFixAccountFlag() {
    return foundFixAccountFlag;
  }

  public void setFoundFixAccountFlag(String foundFixAccountFlag) {
    this.foundFixAccountFlag = foundFixAccountFlag;
  }

  public PersonalInfos foundKAssetFlag(String foundKAssetFlag) {
    this.foundKAssetFlag = foundKAssetFlag;
    return this;
  }

   /**
   * Get foundKAssetFlag
   * @return foundKAssetFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundKAssetFlag() {
    return foundKAssetFlag;
  }

  public void setFoundKAssetFlag(String foundKAssetFlag) {
    this.foundKAssetFlag = foundKAssetFlag;
  }

  public PersonalInfos foundKBankEmployeeFlag(String foundKBankEmployeeFlag) {
    this.foundKBankEmployeeFlag = foundKBankEmployeeFlag;
    return this;
  }

   /**
   * Get foundKBankEmployeeFlag
   * @return foundKBankEmployeeFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundKBankEmployeeFlag() {
    return foundKBankEmployeeFlag;
  }

  public void setFoundKBankEmployeeFlag(String foundKBankEmployeeFlag) {
    this.foundKBankEmployeeFlag = foundKBankEmployeeFlag;
  }

  public PersonalInfos foundKBankPayrollFlag(String foundKBankPayrollFlag) {
    this.foundKBankPayrollFlag = foundKBankPayrollFlag;
    return this;
  }

   /**
   * Get foundKBankPayrollFlag
   * @return foundKBankPayrollFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundKBankPayrollFlag() {
    return foundKBankPayrollFlag;
  }

  public void setFoundKBankPayrollFlag(String foundKBankPayrollFlag) {
    this.foundKBankPayrollFlag = foundKBankPayrollFlag;
  }

  public PersonalInfos foundKBankSalaryFlag(String foundKBankSalaryFlag) {
    this.foundKBankSalaryFlag = foundKBankSalaryFlag;
    return this;
  }

   /**
   * Get foundKBankSalaryFlag
   * @return foundKBankSalaryFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundKBankSalaryFlag() {
    return foundKBankSalaryFlag;
  }

  public void setFoundKBankSalaryFlag(String foundKBankSalaryFlag) {
    this.foundKBankSalaryFlag = foundKBankSalaryFlag;
  }

  public PersonalInfos foundLPMBlacklistFlag(String foundLPMBlacklistFlag) {
    this.foundLPMBlacklistFlag = foundLPMBlacklistFlag;
    return this;
  }

   /**
   * Get foundLPMBlacklistFlag
   * @return foundLPMBlacklistFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundLPMBlacklistFlag() {
    return foundLPMBlacklistFlag;
  }

  public void setFoundLPMBlacklistFlag(String foundLPMBlacklistFlag) {
    this.foundLPMBlacklistFlag = foundLPMBlacklistFlag;
  }

  public PersonalInfos foundOldIncomeFlag(String foundOldIncomeFlag) {
    this.foundOldIncomeFlag = foundOldIncomeFlag;
    return this;
  }

   /**
   * Get foundOldIncomeFlag
   * @return foundOldIncomeFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundOldIncomeFlag() {
    return foundOldIncomeFlag;
  }

  public void setFoundOldIncomeFlag(String foundOldIncomeFlag) {
    this.foundOldIncomeFlag = foundOldIncomeFlag;
  }

  public PersonalInfos foundPayrollFlag(String foundPayrollFlag) {
    this.foundPayrollFlag = foundPayrollFlag;
    return this;
  }

   /**
   * Get foundPayrollFlag
   * @return foundPayrollFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundPayrollFlag() {
    return foundPayrollFlag;
  }

  public void setFoundPayrollFlag(String foundPayrollFlag) {
    this.foundPayrollFlag = foundPayrollFlag;
  }

  public PersonalInfos foundSAFELoanFlag(String foundSAFELoanFlag) {
    this.foundSAFELoanFlag = foundSAFELoanFlag;
    return this;
  }

   /**
   * Get foundSAFELoanFlag
   * @return foundSAFELoanFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundSAFELoanFlag() {
    return foundSAFELoanFlag;
  }

  public void setFoundSAFELoanFlag(String foundSAFELoanFlag) {
    this.foundSAFELoanFlag = foundSAFELoanFlag;
  }

  public PersonalInfos foundSavingAccountFlag(String foundSavingAccountFlag) {
    this.foundSavingAccountFlag = foundSavingAccountFlag;
    return this;
  }

   /**
   * Get foundSavingAccountFlag
   * @return foundSavingAccountFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundSavingAccountFlag() {
    return foundSavingAccountFlag;
  }

  public void setFoundSavingAccountFlag(String foundSavingAccountFlag) {
    this.foundSavingAccountFlag = foundSavingAccountFlag;
  }

  public PersonalInfos foundAllSavingAccountFlag(String foundAllSavingAccountFlag) {
    this.foundAllSavingAccountFlag = foundAllSavingAccountFlag;
    return this;
  }

   /**
   * Get foundAllSavingAccountFlag
   * @return foundAllSavingAccountFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundAllSavingAccountFlag() {
    return foundAllSavingAccountFlag;
  }

  public void setFoundAllSavingAccountFlag(String foundAllSavingAccountFlag) {
    this.foundAllSavingAccountFlag = foundAllSavingAccountFlag;
  }

  public PersonalInfos foundSoloFlag(String foundSoloFlag) {
    this.foundSoloFlag = foundSoloFlag;
    return this;
  }

   /**
   * Get foundSoloFlag
   * @return foundSoloFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundSoloFlag() {
    return foundSoloFlag;
  }

  public void setFoundSoloFlag(String foundSoloFlag) {
    this.foundSoloFlag = foundSoloFlag;
  }

  public PersonalInfos foundThaiBevFlag(String foundThaiBevFlag) {
    this.foundThaiBevFlag = foundThaiBevFlag;
    return this;
  }

   /**
   * Get foundThaiBevFlag
   * @return foundThaiBevFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundThaiBevFlag() {
    return foundThaiBevFlag;
  }

  public void setFoundThaiBevFlag(String foundThaiBevFlag) {
    this.foundThaiBevFlag = foundThaiBevFlag;
  }

  public PersonalInfos foundTCBLoanFlag(String foundTCBLoanFlag) {
    this.foundTCBLoanFlag = foundTCBLoanFlag;
    return this;
  }

   /**
   * Get foundTCBLoanFlag
   * @return foundTCBLoanFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundTCBLoanFlag() {
    return foundTCBLoanFlag;
  }

  public void setFoundTCBLoanFlag(String foundTCBLoanFlag) {
    this.foundTCBLoanFlag = foundTCBLoanFlag;
  }

  public PersonalInfos foundAllTCBLoanFlag(String foundAllTCBLoanFlag) {
    this.foundAllTCBLoanFlag = foundAllTCBLoanFlag;
    return this;
  }

   /**
   * Get foundAllTCBLoanFlag
   * @return foundAllTCBLoanFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundAllTCBLoanFlag() {
    return foundAllTCBLoanFlag;
  }

  public void setFoundAllTCBLoanFlag(String foundAllTCBLoanFlag) {
    this.foundAllTCBLoanFlag = foundAllTCBLoanFlag;
  }

  public PersonalInfos foundTrustedCompanyFlag(String foundTrustedCompanyFlag) {
    this.foundTrustedCompanyFlag = foundTrustedCompanyFlag;
    return this;
  }

   /**
   * Get foundTrustedCompanyFlag
   * @return foundTrustedCompanyFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundTrustedCompanyFlag() {
    return foundTrustedCompanyFlag;
  }

  public void setFoundTrustedCompanyFlag(String foundTrustedCompanyFlag) {
    this.foundTrustedCompanyFlag = foundTrustedCompanyFlag;
  }

  public PersonalInfos foundWealthFlag(String foundWealthFlag) {
    this.foundWealthFlag = foundWealthFlag;
    return this;
  }

   /**
   * Get foundWealthFlag
   * @return foundWealthFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundWealthFlag() {
    return foundWealthFlag;
  }

  public void setFoundWealthFlag(String foundWealthFlag) {
    this.foundWealthFlag = foundWealthFlag;
  }

  public PersonalInfos foundStableIncomeFlag(String foundStableIncomeFlag) {
    this.foundStableIncomeFlag = foundStableIncomeFlag;
    return this;
  }

   /**
   * Get foundStableIncomeFlag
   * @return foundStableIncomeFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundStableIncomeFlag() {
    return foundStableIncomeFlag;
  }

  public void setFoundStableIncomeFlag(String foundStableIncomeFlag) {
    this.foundStableIncomeFlag = foundStableIncomeFlag;
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

  public PersonalInfos applicationScores(List<ApplicationScores> applicationScores) {
    this.applicationScores = applicationScores;
    return this;
  }

  public PersonalInfos addApplicationScoresItem(ApplicationScores applicationScoresItem) {
    if (this.applicationScores == null) {
      this.applicationScores = new ArrayList<ApplicationScores>();
    }
    this.applicationScores.add(applicationScoresItem);
    return this;
  }

   /**
   * Get applicationScores
   * @return applicationScores
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ApplicationScores> getApplicationScores() {
    return applicationScores;
  }

  public void setApplicationScores(List<ApplicationScores> applicationScores) {
    this.applicationScores = applicationScores;
  }

  public PersonalInfos bolOrganizations(List<BolOrganizations> bolOrganizations) {
    this.bolOrganizations = bolOrganizations;
    return this;
  }

  public PersonalInfos addBolOrganizationsItem(BolOrganizations bolOrganizationsItem) {
    if (this.bolOrganizations == null) {
      this.bolOrganizations = new ArrayList<BolOrganizations>();
    }
    this.bolOrganizations.add(bolOrganizationsItem);
    return this;
  }

   /**
   * Get bolOrganizations
   * @return bolOrganizations
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<BolOrganizations> getBolOrganizations() {
    return bolOrganizations;
  }

  public void setBolOrganizations(List<BolOrganizations> bolOrganizations) {
    this.bolOrganizations = bolOrganizations;
  }

  public PersonalInfos cardLinkCustomers(List<CardLinkCustomers> cardLinkCustomers) {
    this.cardLinkCustomers = cardLinkCustomers;
    return this;
  }

  public PersonalInfos addCardLinkCustomersItem(CardLinkCustomers cardLinkCustomersItem) {
    if (this.cardLinkCustomers == null) {
      this.cardLinkCustomers = new ArrayList<CardLinkCustomers>();
    }
    this.cardLinkCustomers.add(cardLinkCustomersItem);
    return this;
  }

   /**
   * Get cardLinkCustomers
   * @return cardLinkCustomers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CardLinkCustomers> getCardLinkCustomers() {
    return cardLinkCustomers;
  }

  public void setCardLinkCustomers(List<CardLinkCustomers> cardLinkCustomers) {
    this.cardLinkCustomers = cardLinkCustomers;
  }

  public PersonalInfos cardLinkCards(List<CardLinkCards> cardLinkCards) {
    this.cardLinkCards = cardLinkCards;
    return this;
  }

  public PersonalInfos addCardLinkCardsItem(CardLinkCards cardLinkCardsItem) {
    if (this.cardLinkCards == null) {
      this.cardLinkCards = new ArrayList<CardLinkCards>();
    }
    this.cardLinkCards.add(cardLinkCardsItem);
    return this;
  }

   /**
   * Get cardLinkCards
   * @return cardLinkCards
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CardLinkCards> getCardLinkCards() {
    return cardLinkCards;
  }

  public void setCardLinkCards(List<CardLinkCards> cardLinkCards) {
    this.cardLinkCards = cardLinkCards;
  }

  public PersonalInfos kBankPayroll(KBankPayroll kBankPayroll) {
    this.kBankPayroll = kBankPayroll;
    return this;
  }

   /**
   * Get kBankPayroll
   * @return kBankPayroll
  **/
  @ApiModelProperty(value = "")

  @Valid

  public KBankPayroll getKBankPayroll() {
    return kBankPayroll;
  }

  public void setKBankPayroll(KBankPayroll kBankPayroll) {
    this.kBankPayroll = kBankPayroll;
  }

  public PersonalInfos debtInfo(List<DebtInfo> debtInfo) {
    this.debtInfo = debtInfo;
    return this;
  }

  public PersonalInfos addDebtInfoItem(DebtInfo debtInfoItem) {
    if (this.debtInfo == null) {
      this.debtInfo = new ArrayList<DebtInfo>();
    }
    this.debtInfo.add(debtInfoItem);
    return this;
  }

   /**
   * Get debtInfo
   * @return debtInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<DebtInfo> getDebtInfo() {
    return debtInfo;
  }

  public void setDebtInfo(List<DebtInfo> debtInfo) {
    this.debtInfo = debtInfo;
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

  public PersonalInfos documentSlaTypes(List<DocumentSlaTypes> documentSlaTypes) {
    this.documentSlaTypes = documentSlaTypes;
    return this;
  }

  public PersonalInfos addDocumentSlaTypesItem(DocumentSlaTypes documentSlaTypesItem) {
    if (this.documentSlaTypes == null) {
      this.documentSlaTypes = new ArrayList<DocumentSlaTypes>();
    }
    this.documentSlaTypes.add(documentSlaTypesItem);
    return this;
  }

   /**
   * Get documentSlaTypes
   * @return documentSlaTypes
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<DocumentSlaTypes> getDocumentSlaTypes() {
    return documentSlaTypes;
  }

  public void setDocumentSlaTypes(List<DocumentSlaTypes> documentSlaTypes) {
    this.documentSlaTypes = documentSlaTypes;
  }

  public PersonalInfos incomeInfos(List<IncomeInfos> incomeInfos) {
    this.incomeInfos = incomeInfos;
    return this;
  }

  public PersonalInfos addIncomeInfosItem(IncomeInfos incomeInfosItem) {
    if (this.incomeInfos == null) {
      this.incomeInfos = new ArrayList<IncomeInfos>();
    }
    this.incomeInfos.add(incomeInfosItem);
    return this;
  }

   /**
   * Get incomeInfos
   * @return incomeInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<IncomeInfos> getIncomeInfos() {
    return incomeInfos;
  }

  public void setIncomeInfos(List<IncomeInfos> incomeInfos) {
    this.incomeInfos = incomeInfos;
  }

  public PersonalInfos ncbInfo(NcbInfo ncbInfo) {
    this.ncbInfo = ncbInfo;
    return this;
  }

   /**
   * Get ncbInfo
   * @return ncbInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public NcbInfo getNcbInfo() {
    return ncbInfo;
  }

  public void setNcbInfo(NcbInfo ncbInfo) {
    this.ncbInfo = ncbInfo;
  }

  public PersonalInfos verificationResult(VerificationResultPersonalInfos verificationResult) {
    this.verificationResult = verificationResult;
    return this;
  }

   /**
   * Get verificationResult
   * @return verificationResult
  **/
  @ApiModelProperty(value = "")

  @Valid

  public VerificationResultPersonalInfos getVerificationResult() {
    return verificationResult;
  }

  public void setVerificationResult(VerificationResultPersonalInfos verificationResult) {
    this.verificationResult = verificationResult;
  }

  public PersonalInfos bScores(List<BScores> bScores) {
    this.bScores = bScores;
    return this;
  }

  public PersonalInfos addBScoresItem(BScores bScoresItem) {
    if (this.bScores == null) {
      this.bScores = new ArrayList<BScores>();
    }
    this.bScores.add(bScoresItem);
    return this;
  }

   /**
   * Get bScores
   * @return bScores
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<BScores> getBScores() {
    return bScores;
  }

  public void setBScores(List<BScores> bScores) {
    this.bScores = bScores;
  }

  public PersonalInfos ccaCampaignInfos(List<CcaCampaignInfos> ccaCampaignInfos) {
    this.ccaCampaignInfos = ccaCampaignInfos;
    return this;
  }

  public PersonalInfos addCcaCampaignInfosItem(CcaCampaignInfos ccaCampaignInfosItem) {
    if (this.ccaCampaignInfos == null) {
      this.ccaCampaignInfos = new ArrayList<CcaCampaignInfos>();
    }
    this.ccaCampaignInfos.add(ccaCampaignInfosItem);
    return this;
  }

   /**
   * Get ccaCampaignInfos
   * @return ccaCampaignInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CcaCampaignInfos> getCcaCampaignInfos() {
    return ccaCampaignInfos;
  }

  public void setCcaCampaignInfos(List<CcaCampaignInfos> ccaCampaignInfos) {
    this.ccaCampaignInfos = ccaCampaignInfos;
  }

  public PersonalInfos checkKPLDuplicateResponse(CheckKPLDuplicateResponse checkKPLDuplicateResponse) {
    this.checkKPLDuplicateResponse = checkKPLDuplicateResponse;
    return this;
  }

   /**
   * Get checkKPLDuplicateResponse
   * @return checkKPLDuplicateResponse
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CheckKPLDuplicateResponse getCheckKPLDuplicateResponse() {
    return checkKPLDuplicateResponse;
  }

  public void setCheckKPLDuplicateResponse(CheckKPLDuplicateResponse checkKPLDuplicateResponse) {
    this.checkKPLDuplicateResponse = checkKPLDuplicateResponse;
  }

  public PersonalInfos cis0368i01Response(Cis0368i01Response cis0368i01Response) {
    this.cis0368i01Response = cis0368i01Response;
    return this;
  }

   /**
   * Get cis0368i01Response
   * @return cis0368i01Response
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Cis0368i01Response getCis0368i01Response() {
    return cis0368i01Response;
  }

  public void setCis0368i01Response(Cis0368i01Response cis0368i01Response) {
    this.cis0368i01Response = cis0368i01Response;
  }

  public PersonalInfos coBrand(CoBrand coBrand) {
    this.coBrand = coBrand;
    return this;
  }

   /**
   * Get coBrand
   * @return coBrand
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CoBrand getCoBrand() {
    return coBrand;
  }

  public void setCoBrand(CoBrand coBrand) {
    this.coBrand = coBrand;
  }

  public PersonalInfos currentAccounts(List<CurrentAccounts> currentAccounts) {
    this.currentAccounts = currentAccounts;
    return this;
  }

  public PersonalInfos addCurrentAccountsItem(CurrentAccounts currentAccountsItem) {
    if (this.currentAccounts == null) {
      this.currentAccounts = new ArrayList<CurrentAccounts>();
    }
    this.currentAccounts.add(currentAccountsItem);
    return this;
  }

   /**
   * Get currentAccounts
   * @return currentAccounts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CurrentAccounts> getCurrentAccounts() {
    return currentAccounts;
  }

  public void setCurrentAccounts(List<CurrentAccounts> currentAccounts) {
    this.currentAccounts = currentAccounts;
  }

  public PersonalInfos cvrs1312i01Responses(List<Cvrs1312i01Responses> cvrs1312i01Responses) {
    this.cvrs1312i01Responses = cvrs1312i01Responses;
    return this;
  }

  public PersonalInfos addCvrs1312i01ResponsesItem(Cvrs1312i01Responses cvrs1312i01ResponsesItem) {
    if (this.cvrs1312i01Responses == null) {
      this.cvrs1312i01Responses = new ArrayList<Cvrs1312i01Responses>();
    }
    this.cvrs1312i01Responses.add(cvrs1312i01ResponsesItem);
    return this;
  }

   /**
   * Get cvrs1312i01Responses
   * @return cvrs1312i01Responses
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Cvrs1312i01Responses> getCvrs1312i01Responses() {
    return cvrs1312i01Responses;
  }

  public void setCvrs1312i01Responses(List<Cvrs1312i01Responses> cvrs1312i01Responses) {
    this.cvrs1312i01Responses = cvrs1312i01Responses;
  }

  public PersonalInfos fixAccounts(List<FixAccounts> fixAccounts) {
    this.fixAccounts = fixAccounts;
    return this;
  }

  public PersonalInfos addFixAccountsItem(FixAccounts fixAccountsItem) {
    if (this.fixAccounts == null) {
      this.fixAccounts = new ArrayList<FixAccounts>();
    }
    this.fixAccounts.add(fixAccountsItem);
    return this;
  }

   /**
   * Get fixAccounts
   * @return fixAccounts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<FixAccounts> getFixAccounts() {
    return fixAccounts;
  }

  public void setFixAccounts(List<FixAccounts> fixAccounts) {
    this.fixAccounts = fixAccounts;
  }

  public PersonalInfos leadInfos(List<LeadInfos> leadInfos) {
    this.leadInfos = leadInfos;
    return this;
  }

  public PersonalInfos addLeadInfosItem(LeadInfos leadInfosItem) {
    if (this.leadInfos == null) {
      this.leadInfos = new ArrayList<LeadInfos>();
    }
    this.leadInfos.add(leadInfosItem);
    return this;
  }

   /**
   * Get leadInfos
   * @return leadInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<LeadInfos> getLeadInfos() {
    return leadInfos;
  }

  public void setLeadInfos(List<LeadInfos> leadInfos) {
    this.leadInfos = leadInfos;
  }

  public PersonalInfos lpmBlacklists(List<LpmBlacklists> lpmBlacklists) {
    this.lpmBlacklists = lpmBlacklists;
    return this;
  }

  public PersonalInfos addLpmBlacklistsItem(LpmBlacklists lpmBlacklistsItem) {
    if (this.lpmBlacklists == null) {
      this.lpmBlacklists = new ArrayList<LpmBlacklists>();
    }
    this.lpmBlacklists.add(lpmBlacklistsItem);
    return this;
  }

   /**
   * Get lpmBlacklists
   * @return lpmBlacklists
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<LpmBlacklists> getLpmBlacklists() {
    return lpmBlacklists;
  }

  public void setLpmBlacklists(List<LpmBlacklists> lpmBlacklists) {
    this.lpmBlacklists = lpmBlacklists;
  }

  public PersonalInfos kAssets(List<KAssets> kAssets) {
    this.kAssets = kAssets;
    return this;
  }

  public PersonalInfos addKAssetsItem(KAssets kAssetsItem) {
    if (this.kAssets == null) {
      this.kAssets = new ArrayList<KAssets>();
    }
    this.kAssets.add(kAssetsItem);
    return this;
  }

   /**
   * Get kAssets
   * @return kAssets
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<KAssets> getKAssets() {
    return kAssets;
  }

  public void setKAssets(List<KAssets> kAssets) {
    this.kAssets = kAssets;
  }

  public PersonalInfos kcbsResponse(KcbsResponse kcbsResponse) {
    this.kcbsResponse = kcbsResponse;
    return this;
  }

   /**
   * Get kcbsResponse
   * @return kcbsResponse
  **/
  @ApiModelProperty(value = "")

  @Valid

  public KcbsResponse getKcbsResponse() {
    return kcbsResponse;
  }

  public void setKcbsResponse(KcbsResponse kcbsResponse) {
    this.kcbsResponse = kcbsResponse;
  }

  public PersonalInfos kBank1211i01Response(KBank1211i01Response kBank1211i01Response) {
    this.kBank1211i01Response = kBank1211i01Response;
    return this;
  }

   /**
   * Get kBank1211i01Response
   * @return kBank1211i01Response
  **/
  @ApiModelProperty(value = "")

  @Valid

  public KBank1211i01Response getKBank1211i01Response() {
    return kBank1211i01Response;
  }

  public void setKBank1211i01Response(KBank1211i01Response kBank1211i01Response) {
    this.kBank1211i01Response = kBank1211i01Response;
  }

  public PersonalInfos kBank1550i01Response(KBank1550i01Response kBank1550i01Response) {
    this.kBank1550i01Response = kBank1550i01Response;
    return this;
  }

   /**
   * Get kBank1550i01Response
   * @return kBank1550i01Response
  **/
  @ApiModelProperty(value = "")

  @Valid

  public KBank1550i01Response getKBank1550i01Response() {
    return kBank1550i01Response;
  }

  public void setKBank1550i01Response(KBank1550i01Response kBank1550i01Response) {
    this.kBank1550i01Response = kBank1550i01Response;
  }

  public PersonalInfos kBankEmployee(KBankEmployee kBankEmployee) {
    this.kBankEmployee = kBankEmployee;
    return this;
  }

   /**
   * Get kBankEmployee
   * @return kBankEmployee
  **/
  @ApiModelProperty(value = "")

  @Valid

  public KBankEmployee getKBankEmployee() {
    return kBankEmployee;
  }

  public void setKBankEmployee(KBankEmployee kBankEmployee) {
    this.kBankEmployee = kBankEmployee;
  }

  public PersonalInfos kBankSalaryList(KBankSalaryList kBankSalaryList) {
    this.kBankSalaryList = kBankSalaryList;
    return this;
  }

   /**
   * Get kBankSalaryList
   * @return kBankSalaryList
  **/
  @ApiModelProperty(value = "")

  @Valid

  public KBankSalaryList getKBankSalaryList() {
    return kBankSalaryList;
  }

  public void setKBankSalaryList(KBankSalaryList kBankSalaryList) {
    this.kBankSalaryList = kBankSalaryList;
  }

  public PersonalInfos oldIncomes(List<OldIncomes> oldIncomes) {
    this.oldIncomes = oldIncomes;
    return this;
  }

  public PersonalInfos addOldIncomesItem(OldIncomes oldIncomesItem) {
    if (this.oldIncomes == null) {
      this.oldIncomes = new ArrayList<OldIncomes>();
    }
    this.oldIncomes.add(oldIncomesItem);
    return this;
  }

   /**
   * Get oldIncomes
   * @return oldIncomes
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<OldIncomes> getOldIncomes() {
    return oldIncomes;
  }

  public void setOldIncomes(List<OldIncomes> oldIncomes) {
    this.oldIncomes = oldIncomes;
  }

  public PersonalInfos safeLoans(List<SafeLoans> safeLoans) {
    this.safeLoans = safeLoans;
    return this;
  }

  public PersonalInfos addSafeLoansItem(SafeLoans safeLoansItem) {
    if (this.safeLoans == null) {
      this.safeLoans = new ArrayList<SafeLoans>();
    }
    this.safeLoans.add(safeLoansItem);
    return this;
  }

   /**
   * Get safeLoans
   * @return safeLoans
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SafeLoans> getSafeLoans() {
    return safeLoans;
  }

  public void setSafeLoans(List<SafeLoans> safeLoans) {
    this.safeLoans = safeLoans;
  }

  public PersonalInfos savingAccounts(List<SavingAccountsPersonalInfos> savingAccounts) {
    this.savingAccounts = savingAccounts;
    return this;
  }

  public PersonalInfos addSavingAccountsItem(SavingAccountsPersonalInfos savingAccountsItem) {
    if (this.savingAccounts == null) {
      this.savingAccounts = new ArrayList<SavingAccountsPersonalInfos>();
    }
    this.savingAccounts.add(savingAccountsItem);
    return this;
  }

   /**
   * Get savingAccounts
   * @return savingAccounts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SavingAccountsPersonalInfos> getSavingAccounts() {
    return savingAccounts;
  }

  public void setSavingAccounts(List<SavingAccountsPersonalInfos> savingAccounts) {
    this.savingAccounts = savingAccounts;
  }

  public PersonalInfos soloCustomers(List<SoloCustomers> soloCustomers) {
    this.soloCustomers = soloCustomers;
    return this;
  }

  public PersonalInfos addSoloCustomersItem(SoloCustomers soloCustomersItem) {
    if (this.soloCustomers == null) {
      this.soloCustomers = new ArrayList<SoloCustomers>();
    }
    this.soloCustomers.add(soloCustomersItem);
    return this;
  }

   /**
   * Get soloCustomers
   * @return soloCustomers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SoloCustomers> getSoloCustomers() {
    return soloCustomers;
  }

  public void setSoloCustomers(List<SoloCustomers> soloCustomers) {
    this.soloCustomers = soloCustomers;
  }

  public PersonalInfos stableIncome(StableIncome stableIncome) {
    this.stableIncome = stableIncome;
    return this;
  }

   /**
   * Get stableIncome
   * @return stableIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public StableIncome getStableIncome() {
    return stableIncome;
  }

  public void setStableIncome(StableIncome stableIncome) {
    this.stableIncome = stableIncome;
  }

  public PersonalInfos tcbLoans(List<TcbLoans> tcbLoans) {
    this.tcbLoans = tcbLoans;
    return this;
  }

  public PersonalInfos addTcbLoansItem(TcbLoans tcbLoansItem) {
    if (this.tcbLoans == null) {
      this.tcbLoans = new ArrayList<TcbLoans>();
    }
    this.tcbLoans.add(tcbLoansItem);
    return this;
  }

   /**
   * Get tcbLoans
   * @return tcbLoans
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<TcbLoans> getTcbLoans() {
    return tcbLoans;
  }

  public void setTcbLoans(List<TcbLoans> tcbLoans) {
    this.tcbLoans = tcbLoans;
  }

  public PersonalInfos trustedCompanys(TrustedCompanys trustedCompanys) {
    this.trustedCompanys = trustedCompanys;
    return this;
  }

   /**
   * Get trustedCompanys
   * @return trustedCompanys
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TrustedCompanys getTrustedCompanys() {
    return trustedCompanys;
  }

  public void setTrustedCompanys(TrustedCompanys trustedCompanys) {
    this.trustedCompanys = trustedCompanys;
  }

  public PersonalInfos wealths(List<Wealths> wealths) {
    this.wealths = wealths;
    return this;
  }

  public PersonalInfos addWealthsItem(Wealths wealthsItem) {
    if (this.wealths == null) {
      this.wealths = new ArrayList<Wealths>();
    }
    this.wealths.add(wealthsItem);
    return this;
  }

   /**
   * Get wealths
   * @return wealths
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Wealths> getWealths() {
    return wealths;
  }

  public void setWealths(List<Wealths> wealths) {
    this.wealths = wealths;
  }

  public PersonalInfos wfInquiryResponseM(WfInquiryResponseM wfInquiryResponseM) {
    this.wfInquiryResponseM = wfInquiryResponseM;
    return this;
  }

   /**
   * Get wfInquiryResponseM
   * @return wfInquiryResponseM
  **/
  @ApiModelProperty(value = "")

  @Valid

  public WfInquiryResponseM getWfInquiryResponseM() {
    return wfInquiryResponseM;
  }

  public void setWfInquiryResponseM(WfInquiryResponseM wfInquiryResponseM) {
    this.wfInquiryResponseM = wfInquiryResponseM;
  }

  public PersonalInfos additionalField(PersonalInfosAdditionalField additionalField) {
    this.additionalField = additionalField;
    return this;
  }

   /**
   * Get additionalField
   * @return additionalField
  **/
  @ApiModelProperty(value = "")

  @Valid

  public PersonalInfosAdditionalField getAdditionalField() {
    return additionalField;
  }

  public void setAdditionalField(PersonalInfosAdditionalField additionalField) {
    this.additionalField = additionalField;
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
        Objects.equals(this.customerType, personalInfos.customerType) &&
        Objects.equals(this.cisNo, personalInfos.cisNo) &&
        Objects.equals(this.cidType, personalInfos.cidType) &&
        Objects.equals(this.idNo, personalInfos.idNo) &&
        Objects.equals(this.thTitleCode, personalInfos.thTitleCode) &&
        Objects.equals(this.thFirstName, personalInfos.thFirstName) &&
        Objects.equals(this.thLastName, personalInfos.thLastName) &&
        Objects.equals(this.thMidName, personalInfos.thMidName) &&
        Objects.equals(this.enTitleCode, personalInfos.enTitleCode) &&
        Objects.equals(this.enFirstName, personalInfos.enFirstName) &&
        Objects.equals(this.enLastName, personalInfos.enLastName) &&
        Objects.equals(this.enMidName, personalInfos.enMidName) &&
        Objects.equals(this.birthDate, personalInfos.birthDate) &&
        Objects.equals(this.ncbTranNo, personalInfos.ncbTranNo) &&
        Objects.equals(this.consentRefNo, personalInfos.consentRefNo) &&
        Objects.equals(this.branchReceiveCard, personalInfos.branchReceiveCard) &&
        Objects.equals(this.bundleWithMainFlag, personalInfos.bundleWithMainFlag) &&
        Objects.equals(this.businessType, personalInfos.businessType) &&
        Objects.equals(this.cisPrimSegment, personalInfos.cisPrimSegment) &&
        Objects.equals(this.cisPrimSubSegment, personalInfos.cisPrimSubSegment) &&
        Objects.equals(this.cisSecSegment, personalInfos.cisSecSegment) &&
        Objects.equals(this.cisSecSubSegment, personalInfos.cisSecSubSegment) &&
        Objects.equals(this.contactTime, personalInfos.contactTime) &&
        Objects.equals(this.degree, personalInfos.degree) &&
        Objects.equals(this.gender, personalInfos.gender) &&
        Objects.equals(this.married, personalInfos.married) &&
        Objects.equals(this.moblieNo, personalInfos.moblieNo) &&
        Objects.equals(this.nationality, personalInfos.nationality) &&
        Objects.equals(this.numberOfChild, personalInfos.numberOfChild) &&
        Objects.equals(this.occupation, personalInfos.occupation) &&
        Objects.equals(this.previousCompanyTitle, personalInfos.previousCompanyTitle) &&
        Objects.equals(this.previousWorkMonth, personalInfos.previousWorkMonth) &&
        Objects.equals(this.previousWorkYear, personalInfos.previousWorkYear) &&
        Objects.equals(this.profession, personalInfos.profession) &&
        Objects.equals(this.receiveIncomeBank, personalInfos.receiveIncomeBank) &&
        Objects.equals(this.relationshipType, personalInfos.relationshipType) &&
        Objects.equals(this.requireBureau, personalInfos.requireBureau) &&
        Objects.equals(this.totalWorkMonth, personalInfos.totalWorkMonth) &&
        Objects.equals(this.totalWorkYear, personalInfos.totalWorkYear) &&
        Objects.equals(this.totVerifiedIncome, personalInfos.totVerifiedIncome) &&
        Objects.equals(this.income, personalInfos.income) &&
        Objects.equals(this.otherIncome, personalInfos.otherIncome) &&
        Objects.equals(this.typeofFin, personalInfos.typeofFin) &&
        Objects.equals(this.vatRegisterFlag, personalInfos.vatRegisterFlag) &&
        Objects.equals(this.visaExpiryDate, personalInfos.visaExpiryDate) &&
        Objects.equals(this.visaType, personalInfos.visaType) &&
        Objects.equals(this.workPermitExpiryDate, personalInfos.workPermitExpiryDate) &&
        Objects.equals(this.workPermitNo, personalInfos.workPermitNo) &&
        Objects.equals(this.mailingAddress, personalInfos.mailingAddress) &&
        Objects.equals(this.wfVerfiedFlag, personalInfos.wfVerfiedFlag) &&
        Objects.equals(this.positionType, personalInfos.positionType) &&
        Objects.equals(this.positionLevel, personalInfos.positionLevel) &&
        Objects.equals(this.salaryLevel, personalInfos.salaryLevel) &&
        Objects.equals(this.passportExpiryDate, personalInfos.passportExpiryDate) &&
        Objects.equals(this.previousCompanyName, personalInfos.previousCompanyName) &&
        Objects.equals(this.specialMerchant, personalInfos.specialMerchant) &&
        Objects.equals(this.companyType, personalInfos.companyType) &&
        Objects.equals(this.kGroupFlag, personalInfos.kGroupFlag) &&
        Objects.equals(this.assetsAmount, personalInfos.assetsAmount) &&
        Objects.equals(this.totPreIncome, personalInfos.totPreIncome) &&
        Objects.equals(this.infoFlag, personalInfos.infoFlag) &&
        Objects.equals(this.cisPrimDesc, personalInfos.cisPrimDesc) &&
        Objects.equals(this.cisPrimSubDesc, personalInfos.cisPrimSubDesc) &&
        Objects.equals(this.cisSecDesc, personalInfos.cisSecDesc) &&
        Objects.equals(this.cisSecSubDesc, personalInfos.cisSecSubDesc) &&
        Objects.equals(this.lineId, personalInfos.lineId) &&
        Objects.equals(this.incomeResource, personalInfos.incomeResource) &&
        Objects.equals(this.incomeResourceOther, personalInfos.incomeResourceOther) &&
        Objects.equals(this.countryOfIncome, personalInfos.countryOfIncome) &&
        Objects.equals(this.foundBOLFlag, personalInfos.foundBOLFlag) &&
        Objects.equals(this.foundBScoreFlag, personalInfos.foundBScoreFlag) &&
        Objects.equals(this.foundCardlinkCardFlag, personalInfos.foundCardlinkCardFlag) &&
        Objects.equals(this.foundCardlinkCustomerFlag, personalInfos.foundCardlinkCustomerFlag) &&
        Objects.equals(this.foundCCAFlag, personalInfos.foundCCAFlag) &&
        Objects.equals(this.foundCoBrandFlag, personalInfos.foundCoBrandFlag) &&
        Objects.equals(this.foundCurrentAccountFlag, personalInfos.foundCurrentAccountFlag) &&
        Objects.equals(this.foundFixAccountFlag, personalInfos.foundFixAccountFlag) &&
        Objects.equals(this.foundKAssetFlag, personalInfos.foundKAssetFlag) &&
        Objects.equals(this.foundKBankEmployeeFlag, personalInfos.foundKBankEmployeeFlag) &&
        Objects.equals(this.foundKBankPayrollFlag, personalInfos.foundKBankPayrollFlag) &&
        Objects.equals(this.foundKBankSalaryFlag, personalInfos.foundKBankSalaryFlag) &&
        Objects.equals(this.foundLPMBlacklistFlag, personalInfos.foundLPMBlacklistFlag) &&
        Objects.equals(this.foundOldIncomeFlag, personalInfos.foundOldIncomeFlag) &&
        Objects.equals(this.foundPayrollFlag, personalInfos.foundPayrollFlag) &&
        Objects.equals(this.foundSAFELoanFlag, personalInfos.foundSAFELoanFlag) &&
        Objects.equals(this.foundSavingAccountFlag, personalInfos.foundSavingAccountFlag) &&
        Objects.equals(this.foundAllSavingAccountFlag, personalInfos.foundAllSavingAccountFlag) &&
        Objects.equals(this.foundSoloFlag, personalInfos.foundSoloFlag) &&
        Objects.equals(this.foundThaiBevFlag, personalInfos.foundThaiBevFlag) &&
        Objects.equals(this.foundTCBLoanFlag, personalInfos.foundTCBLoanFlag) &&
        Objects.equals(this.foundAllTCBLoanFlag, personalInfos.foundAllTCBLoanFlag) &&
        Objects.equals(this.foundTrustedCompanyFlag, personalInfos.foundTrustedCompanyFlag) &&
        Objects.equals(this.foundWealthFlag, personalInfos.foundWealthFlag) &&
        Objects.equals(this.foundStableIncomeFlag, personalInfos.foundStableIncomeFlag) &&
        Objects.equals(this.addresses, personalInfos.addresses) &&
        Objects.equals(this.applicationScores, personalInfos.applicationScores) &&
        Objects.equals(this.bolOrganizations, personalInfos.bolOrganizations) &&
        Objects.equals(this.cardLinkCustomers, personalInfos.cardLinkCustomers) &&
        Objects.equals(this.cardLinkCards, personalInfos.cardLinkCards) &&
        Objects.equals(this.kBankPayroll, personalInfos.kBankPayroll) &&
        Objects.equals(this.debtInfo, personalInfos.debtInfo) &&
        Objects.equals(this.documents, personalInfos.documents) &&
        Objects.equals(this.documentSlaTypes, personalInfos.documentSlaTypes) &&
        Objects.equals(this.incomeInfos, personalInfos.incomeInfos) &&
        Objects.equals(this.ncbInfo, personalInfos.ncbInfo) &&
        Objects.equals(this.verificationResult, personalInfos.verificationResult) &&
        Objects.equals(this.bScores, personalInfos.bScores) &&
        Objects.equals(this.ccaCampaignInfos, personalInfos.ccaCampaignInfos) &&
        Objects.equals(this.checkKPLDuplicateResponse, personalInfos.checkKPLDuplicateResponse) &&
        Objects.equals(this.cis0368i01Response, personalInfos.cis0368i01Response) &&
        Objects.equals(this.coBrand, personalInfos.coBrand) &&
        Objects.equals(this.currentAccounts, personalInfos.currentAccounts) &&
        Objects.equals(this.cvrs1312i01Responses, personalInfos.cvrs1312i01Responses) &&
        Objects.equals(this.fixAccounts, personalInfos.fixAccounts) &&
        Objects.equals(this.leadInfos, personalInfos.leadInfos) &&
        Objects.equals(this.lpmBlacklists, personalInfos.lpmBlacklists) &&
        Objects.equals(this.kAssets, personalInfos.kAssets) &&
        Objects.equals(this.kcbsResponse, personalInfos.kcbsResponse) &&
        Objects.equals(this.kBank1211i01Response, personalInfos.kBank1211i01Response) &&
        Objects.equals(this.kBank1550i01Response, personalInfos.kBank1550i01Response) &&
        Objects.equals(this.kBankEmployee, personalInfos.kBankEmployee) &&
        Objects.equals(this.kBankSalaryList, personalInfos.kBankSalaryList) &&
        Objects.equals(this.oldIncomes, personalInfos.oldIncomes) &&
        Objects.equals(this.safeLoans, personalInfos.safeLoans) &&
        Objects.equals(this.savingAccounts, personalInfos.savingAccounts) &&
        Objects.equals(this.soloCustomers, personalInfos.soloCustomers) &&
        Objects.equals(this.stableIncome, personalInfos.stableIncome) &&
        Objects.equals(this.tcbLoans, personalInfos.tcbLoans) &&
        Objects.equals(this.trustedCompanys, personalInfos.trustedCompanys) &&
        Objects.equals(this.wealths, personalInfos.wealths) &&
        Objects.equals(this.wfInquiryResponseM, personalInfos.wfInquiryResponseM) &&
        Objects.equals(this.additionalField, personalInfos.additionalField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personalId, personalType, customerType, cisNo, cidType, idNo, thTitleCode, thFirstName, thLastName, thMidName, enTitleCode, enFirstName, enLastName, enMidName, birthDate, ncbTranNo, consentRefNo, branchReceiveCard, bundleWithMainFlag, businessType, cisPrimSegment, cisPrimSubSegment, cisSecSegment, cisSecSubSegment, contactTime, degree, gender, married, moblieNo, nationality, numberOfChild, occupation, previousCompanyTitle, previousWorkMonth, previousWorkYear, profession, receiveIncomeBank, relationshipType, requireBureau, totalWorkMonth, totalWorkYear, totVerifiedIncome, income, otherIncome, typeofFin, vatRegisterFlag, visaExpiryDate, visaType, workPermitExpiryDate, workPermitNo, mailingAddress, wfVerfiedFlag, positionType, positionLevel, salaryLevel, passportExpiryDate, previousCompanyName, specialMerchant, companyType, kGroupFlag, assetsAmount, totPreIncome, infoFlag, cisPrimDesc, cisPrimSubDesc, cisSecDesc, cisSecSubDesc, lineId, incomeResource, incomeResourceOther, countryOfIncome, foundBOLFlag, foundBScoreFlag, foundCardlinkCardFlag, foundCardlinkCustomerFlag, foundCCAFlag, foundCoBrandFlag, foundCurrentAccountFlag, foundFixAccountFlag, foundKAssetFlag, foundKBankEmployeeFlag, foundKBankPayrollFlag, foundKBankSalaryFlag, foundLPMBlacklistFlag, foundOldIncomeFlag, foundPayrollFlag, foundSAFELoanFlag, foundSavingAccountFlag, foundAllSavingAccountFlag, foundSoloFlag, foundThaiBevFlag, foundTCBLoanFlag, foundAllTCBLoanFlag, foundTrustedCompanyFlag, foundWealthFlag, foundStableIncomeFlag, addresses, applicationScores, bolOrganizations, cardLinkCustomers, cardLinkCards, kBankPayroll, debtInfo, documents, documentSlaTypes, incomeInfos, ncbInfo, verificationResult, bScores, ccaCampaignInfos, checkKPLDuplicateResponse, cis0368i01Response, coBrand, currentAccounts, cvrs1312i01Responses, fixAccounts, leadInfos, lpmBlacklists, kAssets, kcbsResponse, kBank1211i01Response, kBank1550i01Response, kBankEmployee, kBankSalaryList, oldIncomes, safeLoans, savingAccounts, soloCustomers, stableIncome, tcbLoans, trustedCompanys, wealths, wfInquiryResponseM, additionalField);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalInfos {\n");
    
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    personalType: ").append(toIndentedString(personalType)).append("\n");
    sb.append("    customerType: ").append(toIndentedString(customerType)).append("\n");
    sb.append("    cisNo: ").append(toIndentedString(cisNo)).append("\n");
    sb.append("    cidType: ").append(toIndentedString(cidType)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    thTitleCode: ").append(toIndentedString(thTitleCode)).append("\n");
    sb.append("    thFirstName: ").append(toIndentedString(thFirstName)).append("\n");
    sb.append("    thLastName: ").append(toIndentedString(thLastName)).append("\n");
    sb.append("    thMidName: ").append(toIndentedString(thMidName)).append("\n");
    sb.append("    enTitleCode: ").append(toIndentedString(enTitleCode)).append("\n");
    sb.append("    enFirstName: ").append(toIndentedString(enFirstName)).append("\n");
    sb.append("    enLastName: ").append(toIndentedString(enLastName)).append("\n");
    sb.append("    enMidName: ").append(toIndentedString(enMidName)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    ncbTranNo: ").append(toIndentedString(ncbTranNo)).append("\n");
    sb.append("    consentRefNo: ").append(toIndentedString(consentRefNo)).append("\n");
    sb.append("    branchReceiveCard: ").append(toIndentedString(branchReceiveCard)).append("\n");
    sb.append("    bundleWithMainFlag: ").append(toIndentedString(bundleWithMainFlag)).append("\n");
    sb.append("    businessType: ").append(toIndentedString(businessType)).append("\n");
    sb.append("    cisPrimSegment: ").append(toIndentedString(cisPrimSegment)).append("\n");
    sb.append("    cisPrimSubSegment: ").append(toIndentedString(cisPrimSubSegment)).append("\n");
    sb.append("    cisSecSegment: ").append(toIndentedString(cisSecSegment)).append("\n");
    sb.append("    cisSecSubSegment: ").append(toIndentedString(cisSecSubSegment)).append("\n");
    sb.append("    contactTime: ").append(toIndentedString(contactTime)).append("\n");
    sb.append("    degree: ").append(toIndentedString(degree)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    married: ").append(toIndentedString(married)).append("\n");
    sb.append("    moblieNo: ").append(toIndentedString(moblieNo)).append("\n");
    sb.append("    nationality: ").append(toIndentedString(nationality)).append("\n");
    sb.append("    numberOfChild: ").append(toIndentedString(numberOfChild)).append("\n");
    sb.append("    occupation: ").append(toIndentedString(occupation)).append("\n");
    sb.append("    previousCompanyTitle: ").append(toIndentedString(previousCompanyTitle)).append("\n");
    sb.append("    previousWorkMonth: ").append(toIndentedString(previousWorkMonth)).append("\n");
    sb.append("    previousWorkYear: ").append(toIndentedString(previousWorkYear)).append("\n");
    sb.append("    profession: ").append(toIndentedString(profession)).append("\n");
    sb.append("    receiveIncomeBank: ").append(toIndentedString(receiveIncomeBank)).append("\n");
    sb.append("    relationshipType: ").append(toIndentedString(relationshipType)).append("\n");
    sb.append("    requireBureau: ").append(toIndentedString(requireBureau)).append("\n");
    sb.append("    totalWorkMonth: ").append(toIndentedString(totalWorkMonth)).append("\n");
    sb.append("    totalWorkYear: ").append(toIndentedString(totalWorkYear)).append("\n");
    sb.append("    totVerifiedIncome: ").append(toIndentedString(totVerifiedIncome)).append("\n");
    sb.append("    income: ").append(toIndentedString(income)).append("\n");
    sb.append("    otherIncome: ").append(toIndentedString(otherIncome)).append("\n");
    sb.append("    typeofFin: ").append(toIndentedString(typeofFin)).append("\n");
    sb.append("    vatRegisterFlag: ").append(toIndentedString(vatRegisterFlag)).append("\n");
    sb.append("    visaExpiryDate: ").append(toIndentedString(visaExpiryDate)).append("\n");
    sb.append("    visaType: ").append(toIndentedString(visaType)).append("\n");
    sb.append("    workPermitExpiryDate: ").append(toIndentedString(workPermitExpiryDate)).append("\n");
    sb.append("    workPermitNo: ").append(toIndentedString(workPermitNo)).append("\n");
    sb.append("    mailingAddress: ").append(toIndentedString(mailingAddress)).append("\n");
    sb.append("    wfVerfiedFlag: ").append(toIndentedString(wfVerfiedFlag)).append("\n");
    sb.append("    positionType: ").append(toIndentedString(positionType)).append("\n");
    sb.append("    positionLevel: ").append(toIndentedString(positionLevel)).append("\n");
    sb.append("    salaryLevel: ").append(toIndentedString(salaryLevel)).append("\n");
    sb.append("    passportExpiryDate: ").append(toIndentedString(passportExpiryDate)).append("\n");
    sb.append("    previousCompanyName: ").append(toIndentedString(previousCompanyName)).append("\n");
    sb.append("    specialMerchant: ").append(toIndentedString(specialMerchant)).append("\n");
    sb.append("    companyType: ").append(toIndentedString(companyType)).append("\n");
    sb.append("    kGroupFlag: ").append(toIndentedString(kGroupFlag)).append("\n");
    sb.append("    assetsAmount: ").append(toIndentedString(assetsAmount)).append("\n");
    sb.append("    totPreIncome: ").append(toIndentedString(totPreIncome)).append("\n");
    sb.append("    infoFlag: ").append(toIndentedString(infoFlag)).append("\n");
    sb.append("    cisPrimDesc: ").append(toIndentedString(cisPrimDesc)).append("\n");
    sb.append("    cisPrimSubDesc: ").append(toIndentedString(cisPrimSubDesc)).append("\n");
    sb.append("    cisSecDesc: ").append(toIndentedString(cisSecDesc)).append("\n");
    sb.append("    cisSecSubDesc: ").append(toIndentedString(cisSecSubDesc)).append("\n");
    sb.append("    lineId: ").append(toIndentedString(lineId)).append("\n");
    sb.append("    incomeResource: ").append(toIndentedString(incomeResource)).append("\n");
    sb.append("    incomeResourceOther: ").append(toIndentedString(incomeResourceOther)).append("\n");
    sb.append("    countryOfIncome: ").append(toIndentedString(countryOfIncome)).append("\n");
    sb.append("    foundBOLFlag: ").append(toIndentedString(foundBOLFlag)).append("\n");
    sb.append("    foundBScoreFlag: ").append(toIndentedString(foundBScoreFlag)).append("\n");
    sb.append("    foundCardlinkCardFlag: ").append(toIndentedString(foundCardlinkCardFlag)).append("\n");
    sb.append("    foundCardlinkCustomerFlag: ").append(toIndentedString(foundCardlinkCustomerFlag)).append("\n");
    sb.append("    foundCCAFlag: ").append(toIndentedString(foundCCAFlag)).append("\n");
    sb.append("    foundCoBrandFlag: ").append(toIndentedString(foundCoBrandFlag)).append("\n");
    sb.append("    foundCurrentAccountFlag: ").append(toIndentedString(foundCurrentAccountFlag)).append("\n");
    sb.append("    foundFixAccountFlag: ").append(toIndentedString(foundFixAccountFlag)).append("\n");
    sb.append("    foundKAssetFlag: ").append(toIndentedString(foundKAssetFlag)).append("\n");
    sb.append("    foundKBankEmployeeFlag: ").append(toIndentedString(foundKBankEmployeeFlag)).append("\n");
    sb.append("    foundKBankPayrollFlag: ").append(toIndentedString(foundKBankPayrollFlag)).append("\n");
    sb.append("    foundKBankSalaryFlag: ").append(toIndentedString(foundKBankSalaryFlag)).append("\n");
    sb.append("    foundLPMBlacklistFlag: ").append(toIndentedString(foundLPMBlacklistFlag)).append("\n");
    sb.append("    foundOldIncomeFlag: ").append(toIndentedString(foundOldIncomeFlag)).append("\n");
    sb.append("    foundPayrollFlag: ").append(toIndentedString(foundPayrollFlag)).append("\n");
    sb.append("    foundSAFELoanFlag: ").append(toIndentedString(foundSAFELoanFlag)).append("\n");
    sb.append("    foundSavingAccountFlag: ").append(toIndentedString(foundSavingAccountFlag)).append("\n");
    sb.append("    foundAllSavingAccountFlag: ").append(toIndentedString(foundAllSavingAccountFlag)).append("\n");
    sb.append("    foundSoloFlag: ").append(toIndentedString(foundSoloFlag)).append("\n");
    sb.append("    foundThaiBevFlag: ").append(toIndentedString(foundThaiBevFlag)).append("\n");
    sb.append("    foundTCBLoanFlag: ").append(toIndentedString(foundTCBLoanFlag)).append("\n");
    sb.append("    foundAllTCBLoanFlag: ").append(toIndentedString(foundAllTCBLoanFlag)).append("\n");
    sb.append("    foundTrustedCompanyFlag: ").append(toIndentedString(foundTrustedCompanyFlag)).append("\n");
    sb.append("    foundWealthFlag: ").append(toIndentedString(foundWealthFlag)).append("\n");
    sb.append("    foundStableIncomeFlag: ").append(toIndentedString(foundStableIncomeFlag)).append("\n");
    sb.append("    addresses: ").append(toIndentedString(addresses)).append("\n");
    sb.append("    applicationScores: ").append(toIndentedString(applicationScores)).append("\n");
    sb.append("    bolOrganizations: ").append(toIndentedString(bolOrganizations)).append("\n");
    sb.append("    cardLinkCustomers: ").append(toIndentedString(cardLinkCustomers)).append("\n");
    sb.append("    cardLinkCards: ").append(toIndentedString(cardLinkCards)).append("\n");
    sb.append("    kBankPayroll: ").append(toIndentedString(kBankPayroll)).append("\n");
    sb.append("    debtInfo: ").append(toIndentedString(debtInfo)).append("\n");
    sb.append("    documents: ").append(toIndentedString(documents)).append("\n");
    sb.append("    documentSlaTypes: ").append(toIndentedString(documentSlaTypes)).append("\n");
    sb.append("    incomeInfos: ").append(toIndentedString(incomeInfos)).append("\n");
    sb.append("    ncbInfo: ").append(toIndentedString(ncbInfo)).append("\n");
    sb.append("    verificationResult: ").append(toIndentedString(verificationResult)).append("\n");
    sb.append("    bScores: ").append(toIndentedString(bScores)).append("\n");
    sb.append("    ccaCampaignInfos: ").append(toIndentedString(ccaCampaignInfos)).append("\n");
    sb.append("    checkKPLDuplicateResponse: ").append(toIndentedString(checkKPLDuplicateResponse)).append("\n");
    sb.append("    cis0368i01Response: ").append(toIndentedString(cis0368i01Response)).append("\n");
    sb.append("    coBrand: ").append(toIndentedString(coBrand)).append("\n");
    sb.append("    currentAccounts: ").append(toIndentedString(currentAccounts)).append("\n");
    sb.append("    cvrs1312i01Responses: ").append(toIndentedString(cvrs1312i01Responses)).append("\n");
    sb.append("    fixAccounts: ").append(toIndentedString(fixAccounts)).append("\n");
    sb.append("    leadInfos: ").append(toIndentedString(leadInfos)).append("\n");
    sb.append("    lpmBlacklists: ").append(toIndentedString(lpmBlacklists)).append("\n");
    sb.append("    kAssets: ").append(toIndentedString(kAssets)).append("\n");
    sb.append("    kcbsResponse: ").append(toIndentedString(kcbsResponse)).append("\n");
    sb.append("    kBank1211i01Response: ").append(toIndentedString(kBank1211i01Response)).append("\n");
    sb.append("    kBank1550i01Response: ").append(toIndentedString(kBank1550i01Response)).append("\n");
    sb.append("    kBankEmployee: ").append(toIndentedString(kBankEmployee)).append("\n");
    sb.append("    kBankSalaryList: ").append(toIndentedString(kBankSalaryList)).append("\n");
    sb.append("    oldIncomes: ").append(toIndentedString(oldIncomes)).append("\n");
    sb.append("    safeLoans: ").append(toIndentedString(safeLoans)).append("\n");
    sb.append("    savingAccounts: ").append(toIndentedString(savingAccounts)).append("\n");
    sb.append("    soloCustomers: ").append(toIndentedString(soloCustomers)).append("\n");
    sb.append("    stableIncome: ").append(toIndentedString(stableIncome)).append("\n");
    sb.append("    tcbLoans: ").append(toIndentedString(tcbLoans)).append("\n");
    sb.append("    trustedCompanys: ").append(toIndentedString(trustedCompanys)).append("\n");
    sb.append("    wealths: ").append(toIndentedString(wealths)).append("\n");
    sb.append("    wfInquiryResponseM: ").append(toIndentedString(wfInquiryResponseM)).append("\n");
    sb.append("    additionalField: ").append(toIndentedString(additionalField)).append("\n");
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
}

