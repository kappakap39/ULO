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
 * FixedAccounts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class FixedAccounts   {
  @JsonProperty("accountBalance")
  private BigDecimal accountBalance = null;

  @JsonProperty("accountName")
  private String accountName = null;

  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("bankCode")
  private String bankCode = null;

  @JsonProperty("fixedAccId")
  private String fixedAccId = null;

  @JsonProperty("holdingRatio")
  private BigDecimal holdingRatio = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  public FixedAccounts accountBalance(BigDecimal accountBalance) {
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

  public FixedAccounts accountName(String accountName) {
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

  public FixedAccounts accountNo(String accountNo) {
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

  public FixedAccounts bankCode(String bankCode) {
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

  public FixedAccounts fixedAccId(String fixedAccId) {
    this.fixedAccId = fixedAccId;
    return this;
  }

   /**
   * Get fixedAccId
   * @return fixedAccId
  **/
  @ApiModelProperty(value = "")


  public String getFixedAccId() {
    return fixedAccId;
  }

  public void setFixedAccId(String fixedAccId) {
    this.fixedAccId = fixedAccId;
  }

  public FixedAccounts holdingRatio(BigDecimal holdingRatio) {
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

  public FixedAccounts openDate(DateTime openDate) {
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
    FixedAccounts fixedAccounts = (FixedAccounts) o;
    return Objects.equals(this.accountBalance, fixedAccounts.accountBalance) &&
        Objects.equals(this.accountName, fixedAccounts.accountName) &&
        Objects.equals(this.accountNo, fixedAccounts.accountNo) &&
        Objects.equals(this.bankCode, fixedAccounts.bankCode) &&
        Objects.equals(this.fixedAccId, fixedAccounts.fixedAccId) &&
        Objects.equals(this.holdingRatio, fixedAccounts.holdingRatio) &&
        Objects.equals(this.openDate, fixedAccounts.openDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountBalance, accountName, accountNo, bankCode, fixedAccId, holdingRatio, openDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FixedAccounts {\n");
    
    sb.append("    accountBalance: ").append(toIndentedString(accountBalance)).append("\n");
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    bankCode: ").append(toIndentedString(bankCode)).append("\n");
    sb.append("    fixedAccId: ").append(toIndentedString(fixedAccId)).append("\n");
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

