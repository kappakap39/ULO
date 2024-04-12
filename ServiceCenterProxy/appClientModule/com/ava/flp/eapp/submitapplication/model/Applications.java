package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.ava.flp.eapp.submitapplication.model.Loans;
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
 * Applications
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-18T16:18:26.118+07:00")

public class Applications   {
  @JsonProperty("applicationRecordId")
  private String applicationRecordId = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("orgId")
  private String orgId = null;

  @JsonProperty("subProduct")
  private String subProduct = null;

  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("applyType")
  private String applyType = null;

  @JsonProperty("recommendDecision")
  private String recommendDecision = null;

  @JsonProperty("mainApplicationRecordId")
  private String mainApplicationRecordId = null;

  @JsonProperty("applyTypeFlag")
  private String applyTypeFlag = null;

  @JsonProperty("policyProgramId")
  private String policyProgramId = null;

  @JsonProperty("loans")
  private List<Loans> loans = null;

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

  public Applications seq(BigDecimal seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSeq() {
    return seq;
  }

  public void setSeq(BigDecimal seq) {
    this.seq = seq;
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
        Objects.equals(this.seq, applications.seq) &&
        Objects.equals(this.orgId, applications.orgId) &&
        Objects.equals(this.subProduct, applications.subProduct) &&
        Objects.equals(this.personalId, applications.personalId) &&
        Objects.equals(this.applyType, applications.applyType) &&
        Objects.equals(this.recommendDecision, applications.recommendDecision) &&
        Objects.equals(this.mainApplicationRecordId, applications.mainApplicationRecordId) &&
        Objects.equals(this.applyTypeFlag, applications.applyTypeFlag) &&
        Objects.equals(this.policyProgramId, applications.policyProgramId) &&
        Objects.equals(this.loans, applications.loans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationRecordId, seq, orgId, subProduct, personalId, applyType, recommendDecision, mainApplicationRecordId, applyTypeFlag, policyProgramId, loans);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Applications {\n");
    
    sb.append("    applicationRecordId: ").append(toIndentedString(applicationRecordId)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    orgId: ").append(toIndentedString(orgId)).append("\n");
    sb.append("    subProduct: ").append(toIndentedString(subProduct)).append("\n");
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    applyType: ").append(toIndentedString(applyType)).append("\n");
    sb.append("    recommendDecision: ").append(toIndentedString(recommendDecision)).append("\n");
    sb.append("    mainApplicationRecordId: ").append(toIndentedString(mainApplicationRecordId)).append("\n");
    sb.append("    applyTypeFlag: ").append(toIndentedString(applyTypeFlag)).append("\n");
    sb.append("    policyProgramId: ").append(toIndentedString(policyProgramId)).append("\n");
    sb.append("    loans: ").append(toIndentedString(loans)).append("\n");
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
  @JsonProperty("projectCode")
  private String projectCode = null;

  public String getProjectCode() {
	return projectCode;
}

public void setProjectCode(String projectCode) {
	this.projectCode = projectCode;
}
}

