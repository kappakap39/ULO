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
 * BundleHL
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class BundleHL   {
  @JsonProperty("accountOpenDate")
  private DateTime accountOpenDate = null;

  @JsonProperty("applicationRecordId")
  private String applicationRecordId = null;

  @JsonProperty("approveCreditLine")
  private String approveCreditLine = null;

  @JsonProperty("approvedDate")
  private DateTime approvedDate = null;

  @JsonProperty("ccApprovedAmt")
  private BigDecimal ccApprovedAmt = null;

  @JsonProperty("kecApprovedAmt")
  private BigDecimal kecApprovedAmt = null;

  @JsonProperty("verifiedIncome")
  private BigDecimal verifiedIncome = null;

  @JsonProperty("mortGageDate")
  private DateTime mortGageDate = null;

  @JsonProperty("accountNo")
  private String accountNo = null;

  public BundleHL accountOpenDate(DateTime accountOpenDate) {
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

  public BundleHL applicationRecordId(String applicationRecordId) {
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

  public BundleHL approveCreditLine(String approveCreditLine) {
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

  public BundleHL approvedDate(DateTime approvedDate) {
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

  public BundleHL ccApprovedAmt(BigDecimal ccApprovedAmt) {
    this.ccApprovedAmt = ccApprovedAmt;
    return this;
  }

   /**
   * Get ccApprovedAmt
   * @return ccApprovedAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCcApprovedAmt() {
    return ccApprovedAmt;
  }

  public void setCcApprovedAmt(BigDecimal ccApprovedAmt) {
    this.ccApprovedAmt = ccApprovedAmt;
  }

  public BundleHL kecApprovedAmt(BigDecimal kecApprovedAmt) {
    this.kecApprovedAmt = kecApprovedAmt;
    return this;
  }

   /**
   * Get kecApprovedAmt
   * @return kecApprovedAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getKecApprovedAmt() {
    return kecApprovedAmt;
  }

  public void setKecApprovedAmt(BigDecimal kecApprovedAmt) {
    this.kecApprovedAmt = kecApprovedAmt;
  }

  public BundleHL verifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
    return this;
  }

   /**
   * Get verifiedIncome
   * @return verifiedIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getVerifiedIncome() {
    return verifiedIncome;
  }

  public void setVerifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
  }

  public BundleHL mortGageDate(DateTime mortGageDate) {
    this.mortGageDate = mortGageDate;
    return this;
  }

   /**
   * Get mortGageDate
   * @return mortGageDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getMortGageDate() {
    return mortGageDate;
  }

  public void setMortGageDate(DateTime mortGageDate) {
    this.mortGageDate = mortGageDate;
  }

  public BundleHL accountNo(String accountNo) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleHL bundleHL = (BundleHL) o;
    return Objects.equals(this.accountOpenDate, bundleHL.accountOpenDate) &&
        Objects.equals(this.applicationRecordId, bundleHL.applicationRecordId) &&
        Objects.equals(this.approveCreditLine, bundleHL.approveCreditLine) &&
        Objects.equals(this.approvedDate, bundleHL.approvedDate) &&
        Objects.equals(this.ccApprovedAmt, bundleHL.ccApprovedAmt) &&
        Objects.equals(this.kecApprovedAmt, bundleHL.kecApprovedAmt) &&
        Objects.equals(this.verifiedIncome, bundleHL.verifiedIncome) &&
        Objects.equals(this.mortGageDate, bundleHL.mortGageDate) &&
        Objects.equals(this.accountNo, bundleHL.accountNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountOpenDate, applicationRecordId, approveCreditLine, approvedDate, ccApprovedAmt, kecApprovedAmt, verifiedIncome, mortGageDate, accountNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleHL {\n");
    
    sb.append("    accountOpenDate: ").append(toIndentedString(accountOpenDate)).append("\n");
    sb.append("    applicationRecordId: ").append(toIndentedString(applicationRecordId)).append("\n");
    sb.append("    approveCreditLine: ").append(toIndentedString(approveCreditLine)).append("\n");
    sb.append("    approvedDate: ").append(toIndentedString(approvedDate)).append("\n");
    sb.append("    ccApprovedAmt: ").append(toIndentedString(ccApprovedAmt)).append("\n");
    sb.append("    kecApprovedAmt: ").append(toIndentedString(kecApprovedAmt)).append("\n");
    sb.append("    verifiedIncome: ").append(toIndentedString(verifiedIncome)).append("\n");
    sb.append("    mortGageDate: ").append(toIndentedString(mortGageDate)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
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

