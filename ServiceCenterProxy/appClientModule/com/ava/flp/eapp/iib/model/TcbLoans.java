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
 * TcbLoans
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class TcbLoans   {
  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("accountStatus")
  private String accountStatus = null;

  @JsonProperty("coaProductCode")
  private String coaProductCode = null;

  @JsonProperty("creditLimit")
  private BigDecimal creditLimit = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("outstanding")
  private BigDecimal outstanding = null;

  @JsonProperty("productCategory")
  private String productCategory = null;

  @JsonProperty("thAccountName")
  private String thAccountName = null;

  @JsonProperty("accountValidFlag")
  private String accountValidFlag = null;

  @JsonProperty("loanTerm")
  private BigDecimal loanTerm = null;

  @JsonProperty("loanInterest")
  private BigDecimal loanInterest = null;

  @JsonProperty("loanInstallment")
  private BigDecimal loanInstallment = null;

  @JsonProperty("loanAge")
  private BigDecimal loanAge = null;

  public TcbLoans accountNo(String accountNo) {
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

  public TcbLoans accountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

   /**
   * Get accountStatus
   * @return accountStatus
  **/
  @ApiModelProperty(value = "")


  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  public TcbLoans coaProductCode(String coaProductCode) {
    this.coaProductCode = coaProductCode;
    return this;
  }

   /**
   * Get coaProductCode
   * @return coaProductCode
  **/
  @ApiModelProperty(value = "")


  public String getCoaProductCode() {
    return coaProductCode;
  }

  public void setCoaProductCode(String coaProductCode) {
    this.coaProductCode = coaProductCode;
  }

  public TcbLoans creditLimit(BigDecimal creditLimit) {
    this.creditLimit = creditLimit;
    return this;
  }

   /**
   * Get creditLimit
   * @return creditLimit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(BigDecimal creditLimit) {
    this.creditLimit = creditLimit;
  }

  public TcbLoans openDate(DateTime openDate) {
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

  public TcbLoans outstanding(BigDecimal outstanding) {
    this.outstanding = outstanding;
    return this;
  }

   /**
   * Get outstanding
   * @return outstanding
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getOutstanding() {
    return outstanding;
  }

  public void setOutstanding(BigDecimal outstanding) {
    this.outstanding = outstanding;
  }

  public TcbLoans productCategory(String productCategory) {
    this.productCategory = productCategory;
    return this;
  }

   /**
   * Get productCategory
   * @return productCategory
  **/
  @ApiModelProperty(value = "")


  public String getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(String productCategory) {
    this.productCategory = productCategory;
  }

  public TcbLoans thAccountName(String thAccountName) {
    this.thAccountName = thAccountName;
    return this;
  }

   /**
   * Get thAccountName
   * @return thAccountName
  **/
  @ApiModelProperty(value = "")


  public String getThAccountName() {
    return thAccountName;
  }

  public void setThAccountName(String thAccountName) {
    this.thAccountName = thAccountName;
  }

  public TcbLoans accountValidFlag(String accountValidFlag) {
    this.accountValidFlag = accountValidFlag;
    return this;
  }

   /**
   * Get accountValidFlag
   * @return accountValidFlag
  **/
  @ApiModelProperty(value = "")


  public String getAccountValidFlag() {
    return accountValidFlag;
  }

  public void setAccountValidFlag(String accountValidFlag) {
    this.accountValidFlag = accountValidFlag;
  }

  public TcbLoans loanTerm(BigDecimal loanTerm) {
    this.loanTerm = loanTerm;
    return this;
  }

   /**
   * Get loanTerm
   * @return loanTerm
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLoanTerm() {
    return loanTerm;
  }

  public void setLoanTerm(BigDecimal loanTerm) {
    this.loanTerm = loanTerm;
  }

  public TcbLoans loanInterest(BigDecimal loanInterest) {
    this.loanInterest = loanInterest;
    return this;
  }

   /**
   * Get loanInterest
   * @return loanInterest
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLoanInterest() {
    return loanInterest;
  }

  public void setLoanInterest(BigDecimal loanInterest) {
    this.loanInterest = loanInterest;
  }

  public TcbLoans loanInstallment(BigDecimal loanInstallment) {
    this.loanInstallment = loanInstallment;
    return this;
  }

   /**
   * Get loanInstallment
   * @return loanInstallment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLoanInstallment() {
    return loanInstallment;
  }

  public void setLoanInstallment(BigDecimal loanInstallment) {
    this.loanInstallment = loanInstallment;
  }

  public TcbLoans loanAge(BigDecimal loanAge) {
    this.loanAge = loanAge;
    return this;
  }

   /**
   * Get loanAge
   * @return loanAge
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLoanAge() {
    return loanAge;
  }

  public void setLoanAge(BigDecimal loanAge) {
    this.loanAge = loanAge;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TcbLoans tcbLoans = (TcbLoans) o;
    return Objects.equals(this.accountNo, tcbLoans.accountNo) &&
        Objects.equals(this.accountStatus, tcbLoans.accountStatus) &&
        Objects.equals(this.coaProductCode, tcbLoans.coaProductCode) &&
        Objects.equals(this.creditLimit, tcbLoans.creditLimit) &&
        Objects.equals(this.openDate, tcbLoans.openDate) &&
        Objects.equals(this.outstanding, tcbLoans.outstanding) &&
        Objects.equals(this.productCategory, tcbLoans.productCategory) &&
        Objects.equals(this.thAccountName, tcbLoans.thAccountName) &&
        Objects.equals(this.accountValidFlag, tcbLoans.accountValidFlag) &&
        Objects.equals(this.loanTerm, tcbLoans.loanTerm) &&
        Objects.equals(this.loanInterest, tcbLoans.loanInterest) &&
        Objects.equals(this.loanInstallment, tcbLoans.loanInstallment) &&
        Objects.equals(this.loanAge, tcbLoans.loanAge);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, accountStatus, coaProductCode, creditLimit, openDate, outstanding, productCategory, thAccountName, accountValidFlag, loanTerm, loanInterest, loanInstallment, loanAge);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TcbLoans {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
    sb.append("    coaProductCode: ").append(toIndentedString(coaProductCode)).append("\n");
    sb.append("    creditLimit: ").append(toIndentedString(creditLimit)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    outstanding: ").append(toIndentedString(outstanding)).append("\n");
    sb.append("    productCategory: ").append(toIndentedString(productCategory)).append("\n");
    sb.append("    thAccountName: ").append(toIndentedString(thAccountName)).append("\n");
    sb.append("    accountValidFlag: ").append(toIndentedString(accountValidFlag)).append("\n");
    sb.append("    loanTerm: ").append(toIndentedString(loanTerm)).append("\n");
    sb.append("    loanInterest: ").append(toIndentedString(loanInterest)).append("\n");
    sb.append("    loanInstallment: ").append(toIndentedString(loanInstallment)).append("\n");
    sb.append("    loanAge: ").append(toIndentedString(loanAge)).append("\n");
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

