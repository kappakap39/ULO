package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.OpenEndFundDetails;
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
 * OpendEndFounds
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class OpendEndFounds   {
  @JsonProperty("accountName")
  private String accountName = null;

  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("bankCode")
  private String bankCode = null;

  @JsonProperty("fundName")
  private String fundName = null;

  @JsonProperty("holdingRatio")
  private BigDecimal holdingRatio = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("opnEndFundId")
  private String opnEndFundId = null;

  @JsonProperty("openEndFundDetails")
  private List<OpenEndFundDetails> openEndFundDetails = null;

  public OpendEndFounds accountName(String accountName) {
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

  public OpendEndFounds accountNo(String accountNo) {
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

  public OpendEndFounds bankCode(String bankCode) {
    this.bankCode = bankCode;
    return this;
  }

   /**
   * Get bankCode
   * @return bankCode
  **/
  @ApiModelProperty(value = "")


  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public OpendEndFounds fundName(String fundName) {
    this.fundName = fundName;
    return this;
  }

   /**
   * Get fundName
   * @return fundName
  **/
  @ApiModelProperty(value = "")


  public String getFundName() {
    return fundName;
  }

  public void setFundName(String fundName) {
    this.fundName = fundName;
  }

  public OpendEndFounds holdingRatio(BigDecimal holdingRatio) {
    this.holdingRatio = holdingRatio;
    return this;
  }

   /**
   * Get holdingRatio
   * @return holdingRatio
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getHoldingRatio() {
    return holdingRatio;
  }

  public void setHoldingRatio(BigDecimal holdingRatio) {
    this.holdingRatio = holdingRatio;
  }

  public OpendEndFounds openDate(DateTime openDate) {
    this.openDate = openDate;
    return this;
  }

   /**
   * Get openDate
   * @return openDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getOpenDate() {
    return openDate;
  }

  public void setOpenDate(DateTime openDate) {
    this.openDate = openDate;
  }

  public OpendEndFounds opnEndFundId(String opnEndFundId) {
    this.opnEndFundId = opnEndFundId;
    return this;
  }

   /**
   * Get opnEndFundId
   * @return opnEndFundId
  **/
  @ApiModelProperty(value = "")


  public String getOpnEndFundId() {
    return opnEndFundId;
  }

  public void setOpnEndFundId(String opnEndFundId) {
    this.opnEndFundId = opnEndFundId;
  }

  public OpendEndFounds openEndFundDetails(List<OpenEndFundDetails> openEndFundDetails) {
    this.openEndFundDetails = openEndFundDetails;
    return this;
  }

  public OpendEndFounds addOpenEndFundDetailsItem(OpenEndFundDetails openEndFundDetailsItem) {
    if (this.openEndFundDetails == null) {
      this.openEndFundDetails = new ArrayList<OpenEndFundDetails>();
    }
    this.openEndFundDetails.add(openEndFundDetailsItem);
    return this;
  }

   /**
   * Get openEndFundDetails
   * @return openEndFundDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<OpenEndFundDetails> getOpenEndFundDetails() {
    return openEndFundDetails;
  }

  public void setOpenEndFundDetails(List<OpenEndFundDetails> openEndFundDetails) {
    this.openEndFundDetails = openEndFundDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpendEndFounds opendEndFounds = (OpendEndFounds) o;
    return Objects.equals(this.accountName, opendEndFounds.accountName) &&
        Objects.equals(this.accountNo, opendEndFounds.accountNo) &&
        Objects.equals(this.bankCode, opendEndFounds.bankCode) &&
        Objects.equals(this.fundName, opendEndFounds.fundName) &&
        Objects.equals(this.holdingRatio, opendEndFounds.holdingRatio) &&
        Objects.equals(this.openDate, opendEndFounds.openDate) &&
        Objects.equals(this.opnEndFundId, opendEndFounds.opnEndFundId) &&
        Objects.equals(this.openEndFundDetails, opendEndFounds.openEndFundDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountName, accountNo, bankCode, fundName, holdingRatio, openDate, opnEndFundId, openEndFundDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpendEndFounds {\n");
    
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    bankCode: ").append(toIndentedString(bankCode)).append("\n");
    sb.append("    fundName: ").append(toIndentedString(fundName)).append("\n");
    sb.append("    holdingRatio: ").append(toIndentedString(holdingRatio)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    opnEndFundId: ").append(toIndentedString(opnEndFundId)).append("\n");
    sb.append("    openEndFundDetails: ").append(toIndentedString(openEndFundDetails)).append("\n");
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

