package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BundleSME
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class BundleSME   {
  @JsonProperty("accountOpenDate")
  private DateTime accountOpenDate = null;

  @JsonProperty("applicantQuality")
  private String applicantQuality = null;

  @JsonProperty("applicationRecordId")
  private String applicationRecordId = null;

  @JsonProperty("approvedDate")
  private DateTime approvedDate = null;

  @JsonProperty("approvalLimit")
  private String approvalLimit = null;

  @JsonProperty("approveCreditLine")
  private String approveCreditLine = null;

  @JsonProperty("borrowingType")
  private String borrowingType = null;

  @JsonProperty("businessCode")
  private String businessCode = null;

  @JsonProperty("busOwnerFlag")
  private String busOwnerFlag = null;

  @JsonProperty("corporateRatio")
  private BigDecimal corporateRatio = null;

  @JsonProperty("gDscrReq")
  private String gDscrReq = null;

  @JsonProperty("gTotExistPayment")
  private String gTotExistPayment = null;

  @JsonProperty("gTotNewPayReq")
  private String gTotNewPayReq = null;

  @JsonProperty("individualRatio")
  private BigDecimal individualRatio = null;

  public BundleSME accountOpenDate(DateTime accountOpenDate) {
    this.accountOpenDate = accountOpenDate;
    return this;
  }

   /**
   * Get accountOpenDate
   * @return accountOpenDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getAccountOpenDate() {
    return accountOpenDate;
  }

  public void setAccountOpenDate(DateTime accountOpenDate) {
    this.accountOpenDate = accountOpenDate;
  }

  public BundleSME applicantQuality(String applicantQuality) {
    this.applicantQuality = applicantQuality;
    return this;
  }

   /**
   * Get applicantQuality
   * @return applicantQuality
  **/
  @ApiModelProperty(value = "")


  public String getApplicantQuality() {
    return applicantQuality;
  }

  public void setApplicantQuality(String applicantQuality) {
    this.applicantQuality = applicantQuality;
  }

  public BundleSME applicationRecordId(String applicationRecordId) {
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

  public BundleSME approvedDate(DateTime approvedDate) {
    this.approvedDate = approvedDate;
    return this;
  }

   /**
   * Get approvedDate
   * @return approvedDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getApprovedDate() {
    return approvedDate;
  }

  public void setApprovedDate(DateTime approvedDate) {
    this.approvedDate = approvedDate;
  }

  public BundleSME approvalLimit(String approvalLimit) {
    this.approvalLimit = approvalLimit;
    return this;
  }

   /**
   * Get approvalLimit
   * @return approvalLimit
  **/
  @ApiModelProperty(value = "")


  public String getApprovalLimit() {
    return approvalLimit;
  }

  public void setApprovalLimit(String approvalLimit) {
    this.approvalLimit = approvalLimit;
  }

  public BundleSME approveCreditLine(String approveCreditLine) {
    this.approveCreditLine = approveCreditLine;
    return this;
  }

   /**
   * Get approveCreditLine
   * @return approveCreditLine
  **/
  @ApiModelProperty(value = "")


  public String getApproveCreditLine() {
    return approveCreditLine;
  }

  public void setApproveCreditLine(String approveCreditLine) {
    this.approveCreditLine = approveCreditLine;
  }

  public BundleSME borrowingType(String borrowingType) {
    this.borrowingType = borrowingType;
    return this;
  }

   /**
   * Get borrowingType
   * @return borrowingType
  **/
  @ApiModelProperty(value = "")


  public String getBorrowingType() {
    return borrowingType;
  }

  public void setBorrowingType(String borrowingType) {
    this.borrowingType = borrowingType;
  }

  public BundleSME businessCode(String businessCode) {
    this.businessCode = businessCode;
    return this;
  }

   /**
   * Get businessCode
   * @return businessCode
  **/
  @ApiModelProperty(value = "")


  public String getBusinessCode() {
    return businessCode;
  }

  public void setBusinessCode(String businessCode) {
    this.businessCode = businessCode;
  }

  public BundleSME busOwnerFlag(String busOwnerFlag) {
    this.busOwnerFlag = busOwnerFlag;
    return this;
  }

   /**
   * Get busOwnerFlag
   * @return busOwnerFlag
  **/
  @ApiModelProperty(value = "")


  public String getBusOwnerFlag() {
    return busOwnerFlag;
  }

  public void setBusOwnerFlag(String busOwnerFlag) {
    this.busOwnerFlag = busOwnerFlag;
  }

  public BundleSME corporateRatio(BigDecimal corporateRatio) {
    this.corporateRatio = corporateRatio;
    return this;
  }

   /**
   * Get corporateRatio
   * @return corporateRatio
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCorporateRatio() {
    return corporateRatio;
  }

  public void setCorporateRatio(BigDecimal corporateRatio) {
    this.corporateRatio = corporateRatio;
  }

  public BundleSME gDscrReq(String gDscrReq) {
    this.gDscrReq = gDscrReq;
    return this;
  }

   /**
   * Get gDscrReq
   * @return gDscrReq
  **/
  @ApiModelProperty(value = "")


  public String getGDscrReq() {
    return gDscrReq;
  }

  public void setGDscrReq(String gDscrReq) {
    this.gDscrReq = gDscrReq;
  }

  public BundleSME gTotExistPayment(String gTotExistPayment) {
    this.gTotExistPayment = gTotExistPayment;
    return this;
  }

   /**
   * Get gTotExistPayment
   * @return gTotExistPayment
  **/
  @ApiModelProperty(value = "")


  public String getGTotExistPayment() {
    return gTotExistPayment;
  }

  public void setGTotExistPayment(String gTotExistPayment) {
    this.gTotExistPayment = gTotExistPayment;
  }

  public BundleSME gTotNewPayReq(String gTotNewPayReq) {
    this.gTotNewPayReq = gTotNewPayReq;
    return this;
  }

   /**
   * Get gTotNewPayReq
   * @return gTotNewPayReq
  **/
  @ApiModelProperty(value = "")


  public String getGTotNewPayReq() {
    return gTotNewPayReq;
  }

  public void setGTotNewPayReq(String gTotNewPayReq) {
    this.gTotNewPayReq = gTotNewPayReq;
  }

  public BundleSME individualRatio(BigDecimal individualRatio) {
    this.individualRatio = individualRatio;
    return this;
  }

   /**
   * Get individualRatio
   * @return individualRatio
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIndividualRatio() {
    return individualRatio;
  }

  public void setIndividualRatio(BigDecimal individualRatio) {
    this.individualRatio = individualRatio;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleSME bundleSME = (BundleSME) o;
    return Objects.equals(this.accountOpenDate, bundleSME.accountOpenDate) &&
        Objects.equals(this.applicantQuality, bundleSME.applicantQuality) &&
        Objects.equals(this.applicationRecordId, bundleSME.applicationRecordId) &&
        Objects.equals(this.approvedDate, bundleSME.approvedDate) &&
        Objects.equals(this.approvalLimit, bundleSME.approvalLimit) &&
        Objects.equals(this.approveCreditLine, bundleSME.approveCreditLine) &&
        Objects.equals(this.borrowingType, bundleSME.borrowingType) &&
        Objects.equals(this.businessCode, bundleSME.businessCode) &&
        Objects.equals(this.busOwnerFlag, bundleSME.busOwnerFlag) &&
        Objects.equals(this.corporateRatio, bundleSME.corporateRatio) &&
        Objects.equals(this.gDscrReq, bundleSME.gDscrReq) &&
        Objects.equals(this.gTotExistPayment, bundleSME.gTotExistPayment) &&
        Objects.equals(this.gTotNewPayReq, bundleSME.gTotNewPayReq) &&
        Objects.equals(this.individualRatio, bundleSME.individualRatio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountOpenDate, applicantQuality, applicationRecordId, approvedDate, approvalLimit, approveCreditLine, borrowingType, businessCode, busOwnerFlag, corporateRatio, gDscrReq, gTotExistPayment, gTotNewPayReq, individualRatio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleSME {\n");
    
    sb.append("    accountOpenDate: ").append(toIndentedString(accountOpenDate)).append("\n");
    sb.append("    applicantQuality: ").append(toIndentedString(applicantQuality)).append("\n");
    sb.append("    applicationRecordId: ").append(toIndentedString(applicationRecordId)).append("\n");
    sb.append("    approvedDate: ").append(toIndentedString(approvedDate)).append("\n");
    sb.append("    approvalLimit: ").append(toIndentedString(approvalLimit)).append("\n");
    sb.append("    approveCreditLine: ").append(toIndentedString(approveCreditLine)).append("\n");
    sb.append("    borrowingType: ").append(toIndentedString(borrowingType)).append("\n");
    sb.append("    businessCode: ").append(toIndentedString(businessCode)).append("\n");
    sb.append("    busOwnerFlag: ").append(toIndentedString(busOwnerFlag)).append("\n");
    sb.append("    corporateRatio: ").append(toIndentedString(corporateRatio)).append("\n");
    sb.append("    gDscrReq: ").append(toIndentedString(gDscrReq)).append("\n");
    sb.append("    gTotExistPayment: ").append(toIndentedString(gTotExistPayment)).append("\n");
    sb.append("    gTotNewPayReq: ").append(toIndentedString(gTotNewPayReq)).append("\n");
    sb.append("    individualRatio: ").append(toIndentedString(individualRatio)).append("\n");
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

