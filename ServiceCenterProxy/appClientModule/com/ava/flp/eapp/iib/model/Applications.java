package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.CcaCampaign;
import com.ava.flp.eapp.iib.model.Loans;
import com.ava.flp.eapp.iib.model.ReferCriterias;
import com.ava.flp.eapp.iib.model.VerificationResultApplications;
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
 * Applications
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T18:23:47.347+07:00")

public class Applications   {
  @JsonProperty("applicationRecordId")
  private String applicationRecordId = null;

  @JsonProperty("orgId")
  private String orgId = null;

  @JsonProperty("subProduct")
  private String subProduct = null;

  @JsonProperty("applyType")
  private String applyType = null;

  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("policyProgramId")
  private String policyProgramId = null;

  @JsonProperty("recommendDecision")
  private String recommendDecision = null;

  @JsonProperty("previousProductStatus")
  private String previousProductStatus = null;

  @JsonProperty("spSignoffDate")
  private DateTime spSignoffDate = null;

  @JsonProperty("recommendProjectCode")
  private String recommendProjectCode = null;

  @JsonProperty("reExecuteKeyProgramFlag")
  private String reExecuteKeyProgramFlag = null;

  @JsonProperty("lowIncomeFlag")
  private String lowIncomeFlag = null;

  @JsonProperty("approveDate")
  private DateTime approveDate = null;

  @JsonProperty("errorMessage")
  private String errorMessage = null;

  @JsonProperty("blockFlag")
  private String blockFlag = null;

  @JsonProperty("billingCycle")
  private BigDecimal billingCycle = null;

  @JsonProperty("isVetoEligibleFlag")
  private String isVetoEligibleFlag = null;

  @JsonProperty("mainApplicationRecordId")
  private String mainApplicationRecordId = null;

  @JsonProperty("applyTypeFlag")
  private String applyTypeFlag = null;

  @JsonProperty("referCriterias")
  private List<ReferCriterias> referCriterias = null;

  @JsonProperty("verificationResult")
  private VerificationResultApplications verificationResult = null;

  @JsonProperty("loans")
  private List<Loans> loans = null;

  @JsonProperty("ccaCampaign")
  private CcaCampaign ccaCampaign = null;

  public Applications applicationRecordId(String applicationRecordId) {
    this.applicationRecordId = applicationRecordId;
    return this;
  }

   /**
   * Get applicationRecordId
   * @return applicationRecordId
  **/
  @ApiModelProperty(value = "")


  public String getApplicationRecordId() {
    return applicationRecordId;
  }

  public void setApplicationRecordId(String applicationRecordId) {
    this.applicationRecordId = applicationRecordId;
  }

  public Applications orgId(String orgId) {
    this.orgId = orgId;
    return this;
  }

   /**
   * Get orgId
   * @return orgId
  **/
  @ApiModelProperty(value = "")


  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public Applications subProduct(String subProduct) {
    this.subProduct = subProduct;
    return this;
  }

   /**
   * Get subProduct
   * @return subProduct
  **/
  @ApiModelProperty(value = "")


  public String getSubProduct() {
    return subProduct;
  }

  public void setSubProduct(String subProduct) {
    this.subProduct = subProduct;
  }

  public Applications applyType(String applyType) {
    this.applyType = applyType;
    return this;
  }

   /**
   * Get applyType
   * @return applyType
  **/
  @ApiModelProperty(value = "")


  public String getApplyType() {
    return applyType;
  }

  public void setApplyType(String applyType) {
    this.applyType = applyType;
  }

  public Applications personalId(String personalId) {
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

  public Applications policyProgramId(String policyProgramId) {
    this.policyProgramId = policyProgramId;
    return this;
  }

   /**
   * Get policyProgramId
   * @return policyProgramId
  **/
  @ApiModelProperty(value = "")


  public String getPolicyProgramId() {
    return policyProgramId;
  }

  public void setPolicyProgramId(String policyProgramId) {
    this.policyProgramId = policyProgramId;
  }

  public Applications recommendDecision(String recommendDecision) {
    this.recommendDecision = recommendDecision;
    return this;
  }

   /**
   * Get recommendDecision
   * @return recommendDecision
  **/
  @ApiModelProperty(value = "")


  public String getRecommendDecision() {
    return recommendDecision;
  }

  public void setRecommendDecision(String recommendDecision) {
    this.recommendDecision = recommendDecision;
  }

  public Applications previousProductStatus(String previousProductStatus) {
    this.previousProductStatus = previousProductStatus;
    return this;
  }

   /**
   * Get previousProductStatus
   * @return previousProductStatus
  **/
  @ApiModelProperty(value = "")


  public String getPreviousProductStatus() {
    return previousProductStatus;
  }

  public void setPreviousProductStatus(String previousProductStatus) {
    this.previousProductStatus = previousProductStatus;
  }

  public Applications spSignoffDate(DateTime spSignoffDate) {
    this.spSignoffDate = spSignoffDate;
    return this;
  }

   /**
   * Get spSignoffDate
   * @return spSignoffDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getSpSignoffDate() {
    return spSignoffDate;
  }

  public void setSpSignoffDate(DateTime spSignoffDate) {
    this.spSignoffDate = spSignoffDate;
  }

  public Applications recommendProjectCode(String recommendProjectCode) {
    this.recommendProjectCode = recommendProjectCode;
    return this;
  }

   /**
   * Get recommendProjectCode
   * @return recommendProjectCode
  **/
  @ApiModelProperty(value = "")


  public String getRecommendProjectCode() {
    return recommendProjectCode;
  }

  public void setRecommendProjectCode(String recommendProjectCode) {
    this.recommendProjectCode = recommendProjectCode;
  }

  public Applications reExecuteKeyProgramFlag(String reExecuteKeyProgramFlag) {
    this.reExecuteKeyProgramFlag = reExecuteKeyProgramFlag;
    return this;
  }

   /**
   * Get reExecuteKeyProgramFlag
   * @return reExecuteKeyProgramFlag
  **/
  @ApiModelProperty(value = "")


  public String getReExecuteKeyProgramFlag() {
    return reExecuteKeyProgramFlag;
  }

  public void setReExecuteKeyProgramFlag(String reExecuteKeyProgramFlag) {
    this.reExecuteKeyProgramFlag = reExecuteKeyProgramFlag;
  }

  public Applications lowIncomeFlag(String lowIncomeFlag) {
    this.lowIncomeFlag = lowIncomeFlag;
    return this;
  }

   /**
   * Get lowIncomeFlag
   * @return lowIncomeFlag
  **/
  @ApiModelProperty(value = "")


  public String getLowIncomeFlag() {
    return lowIncomeFlag;
  }

  public void setLowIncomeFlag(String lowIncomeFlag) {
    this.lowIncomeFlag = lowIncomeFlag;
  }

  public Applications approveDate(DateTime approveDate) {
    this.approveDate = approveDate;
    return this;
  }

   /**
   * Get approveDate
   * @return approveDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getApproveDate() {
    return approveDate;
  }

  public void setApproveDate(DateTime approveDate) {
    this.approveDate = approveDate;
  }

  public Applications errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

   /**
   * Get errorMessage
   * @return errorMessage
  **/
  @ApiModelProperty(value = "")


  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Applications blockFlag(String blockFlag) {
    this.blockFlag = blockFlag;
    return this;
  }

   /**
   * Get blockFlag
   * @return blockFlag
  **/
  @ApiModelProperty(value = "")


  public String getBlockFlag() {
    return blockFlag;
  }

  public void setBlockFlag(String blockFlag) {
    this.blockFlag = blockFlag;
  }

  public Applications billingCycle(BigDecimal billingCycle) {
    this.billingCycle = billingCycle;
    return this;
  }

   /**
   * Get billingCycle
   * @return billingCycle
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBillingCycle() {
    return billingCycle;
  }

  public void setBillingCycle(BigDecimal billingCycle) {
    this.billingCycle = billingCycle;
  }

  public Applications isVetoEligibleFlag(String isVetoEligibleFlag) {
    this.isVetoEligibleFlag = isVetoEligibleFlag;
    return this;
  }

   /**
   * Get isVetoEligibleFlag
   * @return isVetoEligibleFlag
  **/
  @ApiModelProperty(value = "")


  public String getIsVetoEligibleFlag() {
    return isVetoEligibleFlag;
  }

  public void setIsVetoEligibleFlag(String isVetoEligibleFlag) {
    this.isVetoEligibleFlag = isVetoEligibleFlag;
  }

  public Applications mainApplicationRecordId(String mainApplicationRecordId) {
    this.mainApplicationRecordId = mainApplicationRecordId;
    return this;
  }

   /**
   * Get mainApplicationRecordId
   * @return mainApplicationRecordId
  **/
  @ApiModelProperty(value = "")


  public String getMainApplicationRecordId() {
    return mainApplicationRecordId;
  }

  public void setMainApplicationRecordId(String mainApplicationRecordId) {
    this.mainApplicationRecordId = mainApplicationRecordId;
  }

  public Applications applyTypeFlag(String applyTypeFlag) {
    this.applyTypeFlag = applyTypeFlag;
    return this;
  }

   /**
   * Get applyTypeFlag
   * @return applyTypeFlag
  **/
  @ApiModelProperty(value = "")


  public String getApplyTypeFlag() {
    return applyTypeFlag;
  }

  public void setApplyTypeFlag(String applyTypeFlag) {
    this.applyTypeFlag = applyTypeFlag;
  }

  public Applications referCriterias(List<ReferCriterias> referCriterias) {
    this.referCriterias = referCriterias;
    return this;
  }

  public Applications addReferCriteriasItem(ReferCriterias referCriteriasItem) {
    if (this.referCriterias == null) {
      this.referCriterias = new ArrayList<ReferCriterias>();
    }
    this.referCriterias.add(referCriteriasItem);
    return this;
  }

   /**
   * Get referCriterias
   * @return referCriterias
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ReferCriterias> getReferCriterias() {
    return referCriterias;
  }

  public void setReferCriterias(List<ReferCriterias> referCriterias) {
    this.referCriterias = referCriterias;
  }

  public Applications verificationResult(VerificationResultApplications verificationResult) {
    this.verificationResult = verificationResult;
    return this;
  }

   /**
   * Get verificationResult
   * @return verificationResult
  **/
  @ApiModelProperty(value = "")

  @Valid

  public VerificationResultApplications getVerificationResult() {
    return verificationResult;
  }

  public void setVerificationResult(VerificationResultApplications verificationResult) {
    this.verificationResult = verificationResult;
  }

  public Applications loans(List<Loans> loans) {
    this.loans = loans;
    return this;
  }

  public Applications addLoansItem(Loans loansItem) {
    if (this.loans == null) {
      this.loans = new ArrayList<Loans>();
    }
    this.loans.add(loansItem);
    return this;
  }

   /**
   * Get loans
   * @return loans
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Loans> getLoans() {
    return loans;
  }

  public void setLoans(List<Loans> loans) {
    this.loans = loans;
  }

  public Applications ccaCampaign(CcaCampaign ccaCampaign) {
    this.ccaCampaign = ccaCampaign;
    return this;
  }

   /**
   * Get ccaCampaign
   * @return ccaCampaign
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CcaCampaign getCcaCampaign() {
    return ccaCampaign;
  }

  public void setCcaCampaign(CcaCampaign ccaCampaign) {
    this.ccaCampaign = ccaCampaign;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Applications applications = (Applications) o;
    return Objects.equals(this.applicationRecordId, applications.applicationRecordId) &&
        Objects.equals(this.orgId, applications.orgId) &&
        Objects.equals(this.subProduct, applications.subProduct) &&
        Objects.equals(this.applyType, applications.applyType) &&
        Objects.equals(this.personalId, applications.personalId) &&
        Objects.equals(this.policyProgramId, applications.policyProgramId) &&
        Objects.equals(this.recommendDecision, applications.recommendDecision) &&
        Objects.equals(this.previousProductStatus, applications.previousProductStatus) &&
        Objects.equals(this.spSignoffDate, applications.spSignoffDate) &&
        Objects.equals(this.recommendProjectCode, applications.recommendProjectCode) &&
        Objects.equals(this.reExecuteKeyProgramFlag, applications.reExecuteKeyProgramFlag) &&
        Objects.equals(this.lowIncomeFlag, applications.lowIncomeFlag) &&
        Objects.equals(this.approveDate, applications.approveDate) &&
        Objects.equals(this.errorMessage, applications.errorMessage) &&
        Objects.equals(this.blockFlag, applications.blockFlag) &&
        Objects.equals(this.billingCycle, applications.billingCycle) &&
        Objects.equals(this.isVetoEligibleFlag, applications.isVetoEligibleFlag) &&
        Objects.equals(this.mainApplicationRecordId, applications.mainApplicationRecordId) &&
        Objects.equals(this.applyTypeFlag, applications.applyTypeFlag) &&
        Objects.equals(this.referCriterias, applications.referCriterias) &&
        Objects.equals(this.verificationResult, applications.verificationResult) &&
        Objects.equals(this.loans, applications.loans) &&
        Objects.equals(this.ccaCampaign, applications.ccaCampaign);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationRecordId, orgId, subProduct, applyType, personalId, policyProgramId, recommendDecision, previousProductStatus, spSignoffDate, recommendProjectCode, reExecuteKeyProgramFlag, lowIncomeFlag, approveDate, errorMessage, blockFlag, billingCycle, isVetoEligibleFlag, mainApplicationRecordId, applyTypeFlag, referCriterias, verificationResult, loans, ccaCampaign);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Applications {\n");
    
    sb.append("    applicationRecordId: ").append(toIndentedString(applicationRecordId)).append("\n");
    sb.append("    orgId: ").append(toIndentedString(orgId)).append("\n");
    sb.append("    subProduct: ").append(toIndentedString(subProduct)).append("\n");
    sb.append("    applyType: ").append(toIndentedString(applyType)).append("\n");
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    policyProgramId: ").append(toIndentedString(policyProgramId)).append("\n");
    sb.append("    recommendDecision: ").append(toIndentedString(recommendDecision)).append("\n");
    sb.append("    previousProductStatus: ").append(toIndentedString(previousProductStatus)).append("\n");
    sb.append("    spSignoffDate: ").append(toIndentedString(spSignoffDate)).append("\n");
    sb.append("    recommendProjectCode: ").append(toIndentedString(recommendProjectCode)).append("\n");
    sb.append("    reExecuteKeyProgramFlag: ").append(toIndentedString(reExecuteKeyProgramFlag)).append("\n");
    sb.append("    lowIncomeFlag: ").append(toIndentedString(lowIncomeFlag)).append("\n");
    sb.append("    approveDate: ").append(toIndentedString(approveDate)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    blockFlag: ").append(toIndentedString(blockFlag)).append("\n");
    sb.append("    billingCycle: ").append(toIndentedString(billingCycle)).append("\n");
    sb.append("    isVetoEligibleFlag: ").append(toIndentedString(isVetoEligibleFlag)).append("\n");
    sb.append("    mainApplicationRecordId: ").append(toIndentedString(mainApplicationRecordId)).append("\n");
    sb.append("    applyTypeFlag: ").append(toIndentedString(applyTypeFlag)).append("\n");
    sb.append("    referCriterias: ").append(toIndentedString(referCriterias)).append("\n");
    sb.append("    verificationResult: ").append(toIndentedString(verificationResult)).append("\n");
    sb.append("    loans: ").append(toIndentedString(loans)).append("\n");
    sb.append("    ccaCampaign: ").append(toIndentedString(ccaCampaign)).append("\n");
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

