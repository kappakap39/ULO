package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CcaCampaignInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CcaCampaignInfos   {
  @JsonProperty("additionalDetail")
  private String additionalDetail = null;

  @JsonProperty("applicationType")
  private String applicationType = null;

  @JsonProperty("cisNo")
  private String cisNo = null;

  @JsonProperty("campaignCode")
  private String campaignCode = null;

  @JsonProperty("effectiveDate")
  private String effectiveDate = null;

  @JsonProperty("enFirstName")
  private String enFirstName = null;

  @JsonProperty("enLastName")
  private String enLastName = null;

  @JsonProperty("enTitleCode")
  private String enTitleCode = null;

  @JsonProperty("expiryDate")
  private String expiryDate = null;

  @JsonProperty("highestIncome")
  private String highestIncome = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("projectCode")
  private String projectCode = null;

  @JsonProperty("thFirstName")
  private String thFirstName = null;

  @JsonProperty("thLastName")
  private String thLastName = null;

  @JsonProperty("thTitleCode")
  private String thTitleCode = null;

  @JsonProperty("updateDate")
  private String updateDate = null;

  @JsonProperty("updateTime")
  private String updateTime = null;

  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("limit")
  private BigDecimal limit = null;

  @JsonProperty("interest")
  private BigDecimal interest = null;

  @JsonProperty("term")
  private BigDecimal term = null;

  public CcaCampaignInfos additionalDetail(String additionalDetail) {
    this.additionalDetail = additionalDetail;
    return this;
  }

   /**
   * Get additionalDetail
   * @return additionalDetail
  **/
  @ApiModelProperty(value = "")


  public String getAdditionalDetail() {
    return additionalDetail;
  }

  public void setAdditionalDetail(String additionalDetail) {
    this.additionalDetail = additionalDetail;
  }

  public CcaCampaignInfos applicationType(String applicationType) {
    this.applicationType = applicationType;
    return this;
  }

   /**
   * Get applicationType
   * @return applicationType
  **/
  @ApiModelProperty(value = "")


  public String getApplicationType() {
    return applicationType;
  }

  public void setApplicationType(String applicationType) {
    this.applicationType = applicationType;
  }

  public CcaCampaignInfos cisNo(String cisNo) {
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

  public CcaCampaignInfos campaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
    return this;
  }

   /**
   * Get campaignCode
   * @return campaignCode
  **/
  @ApiModelProperty(value = "")


  public String getCampaignCode() {
    return campaignCode;
  }

  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  public CcaCampaignInfos effectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
    return this;
  }

   /**
   * Get effectiveDate
   * @return effectiveDate
  **/
  @ApiModelProperty(value = "")


  public String getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public CcaCampaignInfos enFirstName(String enFirstName) {
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

  public CcaCampaignInfos enLastName(String enLastName) {
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

  public CcaCampaignInfos enTitleCode(String enTitleCode) {
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

  public CcaCampaignInfos expiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

   /**
   * Get expiryDate
   * @return expiryDate
  **/
  @ApiModelProperty(value = "")


  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public CcaCampaignInfos highestIncome(String highestIncome) {
    this.highestIncome = highestIncome;
    return this;
  }

   /**
   * Get highestIncome
   * @return highestIncome
  **/
  @ApiModelProperty(value = "")


  public String getHighestIncome() {
    return highestIncome;
  }

  public void setHighestIncome(String highestIncome) {
    this.highestIncome = highestIncome;
  }

  public CcaCampaignInfos idNo(String idNo) {
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

  public CcaCampaignInfos projectCode(String projectCode) {
    this.projectCode = projectCode;
    return this;
  }

   /**
   * Get projectCode
   * @return projectCode
  **/
  @ApiModelProperty(value = "")


  public String getProjectCode() {
    return projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public CcaCampaignInfos thFirstName(String thFirstName) {
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

  public CcaCampaignInfos thLastName(String thLastName) {
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

  public CcaCampaignInfos thTitleCode(String thTitleCode) {
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

  public CcaCampaignInfos updateDate(String updateDate) {
    this.updateDate = updateDate;
    return this;
  }

   /**
   * Get updateDate
   * @return updateDate
  **/
  @ApiModelProperty(value = "")


  public String getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(String updateDate) {
    this.updateDate = updateDate;
  }

  public CcaCampaignInfos updateTime(String updateTime) {
    this.updateTime = updateTime;
    return this;
  }

   /**
   * Get updateTime
   * @return updateTime
  **/
  @ApiModelProperty(value = "")


  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public CcaCampaignInfos accountNo(String accountNo) {
    this.accountNo = accountNo;
    return this;
  }

   /**
   * Get accountNo
   * @return accountNo
  **/
  @ApiModelProperty(value = "")


  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public CcaCampaignInfos limit(BigDecimal limit) {
    this.limit = limit;
    return this;
  }

   /**
   * Get limit
   * @return limit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLimit() {
    return limit;
  }

  public void setLimit(BigDecimal limit) {
    this.limit = limit;
  }

  public CcaCampaignInfos interest(BigDecimal interest) {
    this.interest = interest;
    return this;
  }

   /**
   * Get interest
   * @return interest
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getInterest() {
    return interest;
  }

  public void setInterest(BigDecimal interest) {
    this.interest = interest;
  }

  public CcaCampaignInfos term(BigDecimal term) {
    this.term = term;
    return this;
  }

   /**
   * Get term
   * @return term
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTerm() {
    return term;
  }

  public void setTerm(BigDecimal term) {
    this.term = term;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CcaCampaignInfos ccaCampaignInfos = (CcaCampaignInfos) o;
    return Objects.equals(this.additionalDetail, ccaCampaignInfos.additionalDetail) &&
        Objects.equals(this.applicationType, ccaCampaignInfos.applicationType) &&
        Objects.equals(this.cisNo, ccaCampaignInfos.cisNo) &&
        Objects.equals(this.campaignCode, ccaCampaignInfos.campaignCode) &&
        Objects.equals(this.effectiveDate, ccaCampaignInfos.effectiveDate) &&
        Objects.equals(this.enFirstName, ccaCampaignInfos.enFirstName) &&
        Objects.equals(this.enLastName, ccaCampaignInfos.enLastName) &&
        Objects.equals(this.enTitleCode, ccaCampaignInfos.enTitleCode) &&
        Objects.equals(this.expiryDate, ccaCampaignInfos.expiryDate) &&
        Objects.equals(this.highestIncome, ccaCampaignInfos.highestIncome) &&
        Objects.equals(this.idNo, ccaCampaignInfos.idNo) &&
        Objects.equals(this.projectCode, ccaCampaignInfos.projectCode) &&
        Objects.equals(this.thFirstName, ccaCampaignInfos.thFirstName) &&
        Objects.equals(this.thLastName, ccaCampaignInfos.thLastName) &&
        Objects.equals(this.thTitleCode, ccaCampaignInfos.thTitleCode) &&
        Objects.equals(this.updateDate, ccaCampaignInfos.updateDate) &&
        Objects.equals(this.updateTime, ccaCampaignInfos.updateTime) &&
        Objects.equals(this.accountNo, ccaCampaignInfos.accountNo) &&
        Objects.equals(this.limit, ccaCampaignInfos.limit) &&
        Objects.equals(this.interest, ccaCampaignInfos.interest) &&
        Objects.equals(this.term, ccaCampaignInfos.term);
  }

  @Override
  public int hashCode() {
    return Objects.hash(additionalDetail, applicationType, cisNo, campaignCode, effectiveDate, enFirstName, enLastName, enTitleCode, expiryDate, highestIncome, idNo, projectCode, thFirstName, thLastName, thTitleCode, updateDate, updateTime, accountNo, limit, interest, term);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CcaCampaignInfos {\n");
    
    sb.append("    additionalDetail: ").append(toIndentedString(additionalDetail)).append("\n");
    sb.append("    applicationType: ").append(toIndentedString(applicationType)).append("\n");
    sb.append("    cisNo: ").append(toIndentedString(cisNo)).append("\n");
    sb.append("    campaignCode: ").append(toIndentedString(campaignCode)).append("\n");
    sb.append("    effectiveDate: ").append(toIndentedString(effectiveDate)).append("\n");
    sb.append("    enFirstName: ").append(toIndentedString(enFirstName)).append("\n");
    sb.append("    enLastName: ").append(toIndentedString(enLastName)).append("\n");
    sb.append("    enTitleCode: ").append(toIndentedString(enTitleCode)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    highestIncome: ").append(toIndentedString(highestIncome)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    projectCode: ").append(toIndentedString(projectCode)).append("\n");
    sb.append("    thFirstName: ").append(toIndentedString(thFirstName)).append("\n");
    sb.append("    thLastName: ").append(toIndentedString(thLastName)).append("\n");
    sb.append("    thTitleCode: ").append(toIndentedString(thTitleCode)).append("\n");
    sb.append("    updateDate: ").append(toIndentedString(updateDate)).append("\n");
    sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    interest: ").append(toIndentedString(interest)).append("\n");
    sb.append("    term: ").append(toIndentedString(term)).append("\n");
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

