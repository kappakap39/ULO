package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.SavingAccountDetails;
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
 * SavingAccounts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-05T17:39:32.390+07:00")

public class SavingAccounts   {
  @JsonProperty("accountBalance")
  private BigDecimal accountBalance = null;

  @JsonProperty("accountName")
  private String accountName = null;

  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("bankCode")
  private String bankCode = null;

  @JsonProperty("holdingRatio")
  private BigDecimal holdingRatio = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("savingAccId")
  private String savingAccId = null;

  @JsonProperty("savingAccountDetails")
  private List<SavingAccountDetails> savingAccountDetails = null;

  public SavingAccounts accountBalance(BigDecimal accountBalance) {
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

  public SavingAccounts accountName(String accountName) {
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

  public SavingAccounts accountNo(String accountNo) {
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

  public SavingAccounts bankCode(String bankCode) {
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

  public SavingAccounts holdingRatio(BigDecimal holdingRatio) {
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

  public SavingAccounts openDate(DateTime openDate) {
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

  public SavingAccounts savingAccId(String savingAccId) {
    this.savingAccId = savingAccId;
    return this;
  }

   /**
   * Get savingAccId
   * @return savingAccId
  **/
  @ApiModelProperty(value = "")


  public String getSavingAccId() {
    return savingAccId;
  }

  public void setSavingAccId(String savingAccId) {
    this.savingAccId = savingAccId;
  }

  public SavingAccounts savingAccountDetails(List<SavingAccountDetails> savingAccountDetails) {
    this.savingAccountDetails = savingAccountDetails;
    return this;
  }

  public SavingAccounts addSavingAccountDetailsItem(SavingAccountDetails savingAccountDetailsItem) {
    if (this.savingAccountDetails == null) {
      this.savingAccountDetails = new ArrayList<SavingAccountDetails>();
    }
    this.savingAccountDetails.add(savingAccountDetailsItem);
    return this;
  }

   /**
   * Get savingAccountDetails
   * @return savingAccountDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SavingAccountDetails> getSavingAccountDetails() {
    return savingAccountDetails;
  }

  public void setSavingAccountDetails(List<SavingAccountDetails> savingAccountDetails) {
    this.savingAccountDetails = savingAccountDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SavingAccounts savingAccounts = (SavingAccounts) o;
    return Objects.equals(this.accountBalance, savingAccounts.accountBalance) &&
        Objects.equals(this.accountName, savingAccounts.accountName) &&
        Objects.equals(this.accountNo, savingAccounts.accountNo) &&
        Objects.equals(this.bankCode, savingAccounts.bankCode) &&
        Objects.equals(this.holdingRatio, savingAccounts.holdingRatio) &&
        Objects.equals(this.openDate, savingAccounts.openDate) &&
        Objects.equals(this.savingAccId, savingAccounts.savingAccId) &&
        Objects.equals(this.savingAccountDetails, savingAccounts.savingAccountDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountBalance, accountName, accountNo, bankCode, holdingRatio, openDate, savingAccId, savingAccountDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SavingAccounts {\n");
    
    sb.append("    accountBalance: ").append(toIndentedString(accountBalance)).append("\n");
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    bankCode: ").append(toIndentedString(bankCode)).append("\n");
    sb.append("    holdingRatio: ").append(toIndentedString(holdingRatio)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    savingAccId: ").append(toIndentedString(savingAccId)).append("\n");
    sb.append("    savingAccountDetails: ").append(toIndentedString(savingAccountDetails)).append("\n");
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

