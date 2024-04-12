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
 * FixedGuarantees
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class FixedGuarantees   {
  @JsonProperty("accountName")
  private String accountName = null;

  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("branchNo")
  private String branchNo = null;

  @JsonProperty("fixedGuaranteeId")
  private String fixedGuaranteeId = null;

  @JsonProperty("retentionAmt")
  private BigDecimal retentionAmt = null;

  @JsonProperty("retentionDate")
  private DateTime retentionDate = null;

  @JsonProperty("retentionType")
  private String retentionType = null;

  @JsonProperty("retentionTypeDesc")
  private String retentionTypeDesc = null;

  @JsonProperty("sub")
  private String sub = null;

  public FixedGuarantees accountName(String accountName) {
    this.accountName = accountName;
    return this;
  }

   /**
   * Get accountName
   * @return accountName
  **/
  @ApiModelProperty(value = "")


  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public FixedGuarantees accountNo(String accountNo) {
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

  public FixedGuarantees branchNo(String branchNo) {
    this.branchNo = branchNo;
    return this;
  }

   /**
   * Get branchNo
   * @return branchNo
  **/
  @ApiModelProperty(value = "")


  public String getBranchNo() {
    return branchNo;
  }

  public void setBranchNo(String branchNo) {
    this.branchNo = branchNo;
  }

  public FixedGuarantees fixedGuaranteeId(String fixedGuaranteeId) {
    this.fixedGuaranteeId = fixedGuaranteeId;
    return this;
  }

   /**
   * Get fixedGuaranteeId
   * @return fixedGuaranteeId
  **/
  @ApiModelProperty(value = "")


  public String getFixedGuaranteeId() {
    return fixedGuaranteeId;
  }

  public void setFixedGuaranteeId(String fixedGuaranteeId) {
    this.fixedGuaranteeId = fixedGuaranteeId;
  }

  public FixedGuarantees retentionAmt(BigDecimal retentionAmt) {
    this.retentionAmt = retentionAmt;
    return this;
  }

   /**
   * Get retentionAmt
   * @return retentionAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRetentionAmt() {
    return retentionAmt;
  }

  public void setRetentionAmt(BigDecimal retentionAmt) {
    this.retentionAmt = retentionAmt;
  }

  public FixedGuarantees retentionDate(DateTime retentionDate) {
    this.retentionDate = retentionDate;
    return this;
  }

   /**
   * Get retentionDate
   * @return retentionDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getRetentionDate() {
    return retentionDate;
  }

  public void setRetentionDate(DateTime retentionDate) {
    this.retentionDate = retentionDate;
  }

  public FixedGuarantees retentionType(String retentionType) {
    this.retentionType = retentionType;
    return this;
  }

   /**
   * Get retentionType
   * @return retentionType
  **/
  @ApiModelProperty(value = "")


  public String getRetentionType() {
    return retentionType;
  }

  public void setRetentionType(String retentionType) {
    this.retentionType = retentionType;
  }

  public FixedGuarantees retentionTypeDesc(String retentionTypeDesc) {
    this.retentionTypeDesc = retentionTypeDesc;
    return this;
  }

   /**
   * Get retentionTypeDesc
   * @return retentionTypeDesc
  **/
  @ApiModelProperty(value = "")


  public String getRetentionTypeDesc() {
    return retentionTypeDesc;
  }

  public void setRetentionTypeDesc(String retentionTypeDesc) {
    this.retentionTypeDesc = retentionTypeDesc;
  }

  public FixedGuarantees sub(String sub) {
    this.sub = sub;
    return this;
  }

   /**
   * Get sub
   * @return sub
  **/
  @ApiModelProperty(value = "")


  public String getSub() {
    return sub;
  }

  public void setSub(String sub) {
    this.sub = sub;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FixedGuarantees fixedGuarantees = (FixedGuarantees) o;
    return Objects.equals(this.accountName, fixedGuarantees.accountName) &&
        Objects.equals(this.accountNo, fixedGuarantees.accountNo) &&
        Objects.equals(this.branchNo, fixedGuarantees.branchNo) &&
        Objects.equals(this.fixedGuaranteeId, fixedGuarantees.fixedGuaranteeId) &&
        Objects.equals(this.retentionAmt, fixedGuarantees.retentionAmt) &&
        Objects.equals(this.retentionDate, fixedGuarantees.retentionDate) &&
        Objects.equals(this.retentionType, fixedGuarantees.retentionType) &&
        Objects.equals(this.retentionTypeDesc, fixedGuarantees.retentionTypeDesc) &&
        Objects.equals(this.sub, fixedGuarantees.sub);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountName, accountNo, branchNo, fixedGuaranteeId, retentionAmt, retentionDate, retentionType, retentionTypeDesc, sub);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FixedGuarantees {\n");
    
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    branchNo: ").append(toIndentedString(branchNo)).append("\n");
    sb.append("    fixedGuaranteeId: ").append(toIndentedString(fixedGuaranteeId)).append("\n");
    sb.append("    retentionAmt: ").append(toIndentedString(retentionAmt)).append("\n");
    sb.append("    retentionDate: ").append(toIndentedString(retentionDate)).append("\n");
    sb.append("    retentionType: ").append(toIndentedString(retentionType)).append("\n");
    sb.append("    retentionTypeDesc: ").append(toIndentedString(retentionTypeDesc)).append("\n");
    sb.append("    sub: ").append(toIndentedString(sub)).append("\n");
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

