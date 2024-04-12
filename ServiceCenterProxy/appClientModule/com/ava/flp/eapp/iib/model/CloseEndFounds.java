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
 * CloseEndFounds
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CloseEndFounds   {
  @JsonProperty("accountBalance")
  private BigDecimal accountBalance = null;

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

  public CloseEndFounds accountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
    return this;
  }

   /**
   * Get accountBalance
   * @return accountBalance
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
  }

  public CloseEndFounds accountName(String accountName) {
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

  public CloseEndFounds accountNo(String accountNo) {
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

  public CloseEndFounds bankCode(String bankCode) {
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

  public CloseEndFounds fundName(String fundName) {
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

  public CloseEndFounds holdingRatio(BigDecimal holdingRatio) {
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

  public CloseEndFounds openDate(DateTime openDate) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CloseEndFounds closeEndFounds = (CloseEndFounds) o;
    return Objects.equals(this.accountBalance, closeEndFounds.accountBalance) &&
        Objects.equals(this.accountName, closeEndFounds.accountName) &&
        Objects.equals(this.accountNo, closeEndFounds.accountNo) &&
        Objects.equals(this.bankCode, closeEndFounds.bankCode) &&
        Objects.equals(this.fundName, closeEndFounds.fundName) &&
        Objects.equals(this.holdingRatio, closeEndFounds.holdingRatio) &&
        Objects.equals(this.openDate, closeEndFounds.openDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountBalance, accountName, accountNo, bankCode, fundName, holdingRatio, openDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CloseEndFounds {\n");
    
    sb.append("    accountBalance: ").append(toIndentedString(accountBalance)).append("\n");
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    bankCode: ").append(toIndentedString(bankCode)).append("\n");
    sb.append("    fundName: ").append(toIndentedString(fundName)).append("\n");
    sb.append("    holdingRatio: ").append(toIndentedString(holdingRatio)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
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

