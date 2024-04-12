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
 * CurrentAccounts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CurrentAccounts   {
  @JsonProperty("accountNo")
  private String accountNo = null;

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

  public CurrentAccounts accountNo(String accountNo) {
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

  public CurrentAccounts countOwner(String countOwner) {
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

  public CurrentAccounts currentBalance(BigDecimal currentBalance) {
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

  public CurrentAccounts effectiveDate(String effectiveDate) {
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

  public CurrentAccounts holdBalance(BigDecimal holdBalance) {
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

  public CurrentAccounts limitOpenDate(DateTime limitOpenDate) {
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

  public CurrentAccounts openDate(DateTime openDate) {
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

  public CurrentAccounts productGroup(String productGroup) {
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

  public CurrentAccounts subAccountNo(String subAccountNo) {
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

  public CurrentAccounts monthEndBalance(List<MonthEndBalance> monthEndBalance) {
    this.monthEndBalance = monthEndBalance;
    return this;
  }

  public CurrentAccounts addMonthEndBalanceItem(MonthEndBalance monthEndBalanceItem) {
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
    CurrentAccounts currentAccounts = (CurrentAccounts) o;
    return Objects.equals(this.accountNo, currentAccounts.accountNo) &&
        Objects.equals(this.countOwner, currentAccounts.countOwner) &&
        Objects.equals(this.currentBalance, currentAccounts.currentBalance) &&
        Objects.equals(this.effectiveDate, currentAccounts.effectiveDate) &&
        Objects.equals(this.holdBalance, currentAccounts.holdBalance) &&
        Objects.equals(this.limitOpenDate, currentAccounts.limitOpenDate) &&
        Objects.equals(this.openDate, currentAccounts.openDate) &&
        Objects.equals(this.productGroup, currentAccounts.productGroup) &&
        Objects.equals(this.subAccountNo, currentAccounts.subAccountNo) &&
        Objects.equals(this.monthEndBalance, currentAccounts.monthEndBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, countOwner, currentBalance, effectiveDate, holdBalance, limitOpenDate, openDate, productGroup, subAccountNo, monthEndBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CurrentAccounts {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
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

