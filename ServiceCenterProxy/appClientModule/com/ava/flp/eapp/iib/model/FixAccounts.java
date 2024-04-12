package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.MonthEndBalance;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * FixAccounts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class FixAccounts   {
  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("countOwner")
  private String countOwner = null;

  @JsonProperty("currentBalance")
  private String currentBalance = null;

  @JsonProperty("effectiveDate")
  private String effectiveDate = null;

  @JsonProperty("holdBalance")
  private String holdBalance = null;

  @JsonProperty("limitOptionDate")
  private String limitOptionDate = null;

  @JsonProperty("openDate")
  private String openDate = null;

  @JsonProperty("productGroup")
  private String productGroup = null;

  @JsonProperty("subAccountNo")
  private String subAccountNo = null;

  @JsonProperty("monthEndBalance")
  private List<MonthEndBalance> monthEndBalance = null;

  public FixAccounts accountNo(String accountNo) {
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

  public FixAccounts countOwner(String countOwner) {
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

  public FixAccounts currentBalance(String currentBalance) {
    this.currentBalance = currentBalance;
    return this;
  }

   /**
   * Get currentBalance
   * @return currentBalance
  **/
  @ApiModelProperty(value = "")


  public String getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(String currentBalance) {
    this.currentBalance = currentBalance;
  }

  public FixAccounts effectiveDate(String effectiveDate) {
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

  public FixAccounts holdBalance(String holdBalance) {
    this.holdBalance = holdBalance;
    return this;
  }

   /**
   * Get holdBalance
   * @return holdBalance
  **/
  @ApiModelProperty(value = "")


  public String getHoldBalance() {
    return holdBalance;
  }

  public void setHoldBalance(String holdBalance) {
    this.holdBalance = holdBalance;
  }

  public FixAccounts limitOptionDate(String limitOptionDate) {
    this.limitOptionDate = limitOptionDate;
    return this;
  }

   /**
   * Get limitOptionDate
   * @return limitOptionDate
  **/
  @ApiModelProperty(value = "")


  public String getLimitOptionDate() {
    return limitOptionDate;
  }

  public void setLimitOptionDate(String limitOptionDate) {
    this.limitOptionDate = limitOptionDate;
  }

  public FixAccounts openDate(String openDate) {
    this.openDate = openDate;
    return this;
  }

   /**
   * Get openDate
   * @return openDate
  **/
  @ApiModelProperty(value = "")


  public String getOpenDate() {
    return openDate;
  }

  public void setOpenDate(String openDate) {
    this.openDate = openDate;
  }

  public FixAccounts productGroup(String productGroup) {
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

  public FixAccounts subAccountNo(String subAccountNo) {
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

  public FixAccounts monthEndBalance(List<MonthEndBalance> monthEndBalance) {
    this.monthEndBalance = monthEndBalance;
    return this;
  }

  public FixAccounts addMonthEndBalanceItem(MonthEndBalance monthEndBalanceItem) {
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
    FixAccounts fixAccounts = (FixAccounts) o;
    return Objects.equals(this.accountNo, fixAccounts.accountNo) &&
        Objects.equals(this.countOwner, fixAccounts.countOwner) &&
        Objects.equals(this.currentBalance, fixAccounts.currentBalance) &&
        Objects.equals(this.effectiveDate, fixAccounts.effectiveDate) &&
        Objects.equals(this.holdBalance, fixAccounts.holdBalance) &&
        Objects.equals(this.limitOptionDate, fixAccounts.limitOptionDate) &&
        Objects.equals(this.openDate, fixAccounts.openDate) &&
        Objects.equals(this.productGroup, fixAccounts.productGroup) &&
        Objects.equals(this.subAccountNo, fixAccounts.subAccountNo) &&
        Objects.equals(this.monthEndBalance, fixAccounts.monthEndBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, countOwner, currentBalance, effectiveDate, holdBalance, limitOptionDate, openDate, productGroup, subAccountNo, monthEndBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FixAccounts {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    countOwner: ").append(toIndentedString(countOwner)).append("\n");
    sb.append("    currentBalance: ").append(toIndentedString(currentBalance)).append("\n");
    sb.append("    effectiveDate: ").append(toIndentedString(effectiveDate)).append("\n");
    sb.append("    holdBalance: ").append(toIndentedString(holdBalance)).append("\n");
    sb.append("    limitOptionDate: ").append(toIndentedString(limitOptionDate)).append("\n");
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

