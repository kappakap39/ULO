package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.MonthEndBalance;
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
 * SavingAccountsPersonalInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-05T17:39:32.390+07:00")

public class SavingAccountsPersonalInfos   {
  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("accountName")
  private String accountName = null;

  @JsonProperty("countOwner")
  private String countOwner = null;

  @JsonProperty("currentBalance")
  private BigDecimal currentBalance = null;

  @JsonProperty("effectiveDate")
  private String effectiveDate = null;

  @JsonProperty("holdBalance")
  private BigDecimal holdBalance = null;

  @JsonProperty("limitOpenDate")
  private DateTime limitOpenDate = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("productGroup")
  private String productGroup = null;

  @JsonProperty("subAccountNo")
  private String subAccountNo = null;

  @JsonProperty("monthEndBalance")
  private List<MonthEndBalance> monthEndBalance = null;

  public SavingAccountsPersonalInfos accountNo(String accountNo) {
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

  public SavingAccountsPersonalInfos accountName(String accountName) {
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

  public SavingAccountsPersonalInfos countOwner(String countOwner) {
    this.countOwner = countOwner;
    return this;
  }

   /**
   * Get countOwner
   * @return countOwner
  **/
  @ApiModelProperty(value = "")


  public String getCountOwner() {
    return countOwner;
  }

  public void setCountOwner(String countOwner) {
    this.countOwner = countOwner;
  }

  public SavingAccountsPersonalInfos currentBalance(BigDecimal currentBalance) {
    this.currentBalance = currentBalance;
    return this;
  }

   /**
   * Get currentBalance
   * @return currentBalance
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(BigDecimal currentBalance) {
    this.currentBalance = currentBalance;
  }

  public SavingAccountsPersonalInfos effectiveDate(String effectiveDate) {
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

  public SavingAccountsPersonalInfos holdBalance(BigDecimal holdBalance) {
    this.holdBalance = holdBalance;
    return this;
  }

   /**
   * Get holdBalance
   * @return holdBalance
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getHoldBalance() {
    return holdBalance;
  }

  public void setHoldBalance(BigDecimal holdBalance) {
    this.holdBalance = holdBalance;
  }

  public SavingAccountsPersonalInfos limitOpenDate(DateTime limitOpenDate) {
    this.limitOpenDate = limitOpenDate;
    return this;
  }

   /**
   * Get limitOpenDate
   * @return limitOpenDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getLimitOpenDate() {
    return limitOpenDate;
  }

  public void setLimitOpenDate(DateTime limitOpenDate) {
    this.limitOpenDate = limitOpenDate;
  }

  public SavingAccountsPersonalInfos openDate(DateTime openDate) {
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

  public SavingAccountsPersonalInfos productGroup(String productGroup) {
    this.productGroup = productGroup;
    return this;
  }

   /**
   * Get productGroup
   * @return productGroup
  **/
  @ApiModelProperty(value = "")


  public String getProductGroup() {
    return productGroup;
  }

  public void setProductGroup(String productGroup) {
    this.productGroup = productGroup;
  }

  public SavingAccountsPersonalInfos subAccountNo(String subAccountNo) {
    this.subAccountNo = subAccountNo;
    return this;
  }

   /**
   * Get subAccountNo
   * @return subAccountNo
  **/
  @ApiModelProperty(value = "")


  public String getSubAccountNo() {
    return subAccountNo;
  }

  public void setSubAccountNo(String subAccountNo) {
    this.subAccountNo = subAccountNo;
  }

  public SavingAccountsPersonalInfos monthEndBalance(List<MonthEndBalance> monthEndBalance) {
    this.monthEndBalance = monthEndBalance;
    return this;
  }

  public SavingAccountsPersonalInfos addMonthEndBalanceItem(MonthEndBalance monthEndBalanceItem) {
    if (this.monthEndBalance == null) {
      this.monthEndBalance = new ArrayList<MonthEndBalance>();
    }
    this.monthEndBalance.add(monthEndBalanceItem);
    return this;
  }

   /**
   * Get monthEndBalance
   * @return monthEndBalance
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<MonthEndBalance> getMonthEndBalance() {
    return monthEndBalance;
  }

  public void setMonthEndBalance(List<MonthEndBalance> monthEndBalance) {
    this.monthEndBalance = monthEndBalance;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SavingAccountsPersonalInfos savingAccountsPersonalInfos = (SavingAccountsPersonalInfos) o;
    return Objects.equals(this.accountNo, savingAccountsPersonalInfos.accountNo) &&
        Objects.equals(this.accountName, savingAccountsPersonalInfos.accountName) &&
        Objects.equals(this.countOwner, savingAccountsPersonalInfos.countOwner) &&
        Objects.equals(this.currentBalance, savingAccountsPersonalInfos.currentBalance) &&
        Objects.equals(this.effectiveDate, savingAccountsPersonalInfos.effectiveDate) &&
        Objects.equals(this.holdBalance, savingAccountsPersonalInfos.holdBalance) &&
        Objects.equals(this.limitOpenDate, savingAccountsPersonalInfos.limitOpenDate) &&
        Objects.equals(this.openDate, savingAccountsPersonalInfos.openDate) &&
        Objects.equals(this.productGroup, savingAccountsPersonalInfos.productGroup) &&
        Objects.equals(this.subAccountNo, savingAccountsPersonalInfos.subAccountNo) &&
        Objects.equals(this.monthEndBalance, savingAccountsPersonalInfos.monthEndBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, accountName, countOwner, currentBalance, effectiveDate, holdBalance, limitOpenDate, openDate, productGroup, subAccountNo, monthEndBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SavingAccountsPersonalInfos {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    countOwner: ").append(toIndentedString(countOwner)).append("\n");
    sb.append("    currentBalance: ").append(toIndentedString(currentBalance)).append("\n");
    sb.append("    effectiveDate: ").append(toIndentedString(effectiveDate)).append("\n");
    sb.append("    holdBalance: ").append(toIndentedString(holdBalance)).append("\n");
    sb.append("    limitOpenDate: ").append(toIndentedString(limitOpenDate)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    productGroup: ").append(toIndentedString(productGroup)).append("\n");
    sb.append("    subAccountNo: ").append(toIndentedString(subAccountNo)).append("\n");
    sb.append("    monthEndBalance: ").append(toIndentedString(monthEndBalance)).append("\n");
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

