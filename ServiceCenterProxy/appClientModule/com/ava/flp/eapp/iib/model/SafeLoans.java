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
 * SafeLoans
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class SafeLoans   {
  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("asOfDate")
  private String asOfDate = null;

  @JsonProperty("closeDate")
  private DateTime closeDate = null;

  @JsonProperty("creditLimit")
  private BigDecimal creditLimit = null;

  @JsonProperty("loanPurpose")
  private String loanPurpose = null;

  @JsonProperty("loanType")
  private String loanType = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("outStanding")
  private BigDecimal outStanding = null;

  @JsonProperty("productCategory")
  private String productCategory = null;

  @JsonProperty("subAccountNo")
  private String subAccountNo = null;

  public SafeLoans accountNo(String accountNo) {
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

  public SafeLoans asOfDate(String asOfDate) {
    this.asOfDate = asOfDate;
    return this;
  }

   /**
   * Get asOfDate
   * @return asOfDate
  **/
  @ApiModelProperty(value = "")


  public String getAsOfDate() {
    return asOfDate;
  }

  public void setAsOfDate(String asOfDate) {
    this.asOfDate = asOfDate;
  }

  public SafeLoans closeDate(DateTime closeDate) {
    this.closeDate = closeDate;
    return this;
  }

   /**
   * Get closeDate
   * @return closeDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getCloseDate() {
    return closeDate;
  }

  public void setCloseDate(DateTime closeDate) {
    this.closeDate = closeDate;
  }

  public SafeLoans creditLimit(BigDecimal creditLimit) {
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

  public SafeLoans loanPurpose(String loanPurpose) {
    this.loanPurpose = loanPurpose;
    return this;
  }

   /**
   * Get loanPurpose
   * @return loanPurpose
  **/
  @ApiModelProperty(value = "")


  public String getLoanPurpose() {
    return loanPurpose;
  }

  public void setLoanPurpose(String loanPurpose) {
    this.loanPurpose = loanPurpose;
  }

  public SafeLoans loanType(String loanType) {
    this.loanType = loanType;
    return this;
  }

   /**
   * Get loanType
   * @return loanType
  **/
  @ApiModelProperty(value = "")


  public String getLoanType() {
    return loanType;
  }

  public void setLoanType(String loanType) {
    this.loanType = loanType;
  }

  public SafeLoans openDate(DateTime openDate) {
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

  public SafeLoans outStanding(BigDecimal outStanding) {
    this.outStanding = outStanding;
    return this;
  }

   /**
   * Get outStanding
   * @return outStanding
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getOutStanding() {
    return outStanding;
  }

  public void setOutStanding(BigDecimal outStanding) {
    this.outStanding = outStanding;
  }

  public SafeLoans productCategory(String productCategory) {
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

  public SafeLoans subAccountNo(String subAccountNo) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SafeLoans safeLoans = (SafeLoans) o;
    return Objects.equals(this.accountNo, safeLoans.accountNo) &&
        Objects.equals(this.asOfDate, safeLoans.asOfDate) &&
        Objects.equals(this.closeDate, safeLoans.closeDate) &&
        Objects.equals(this.creditLimit, safeLoans.creditLimit) &&
        Objects.equals(this.loanPurpose, safeLoans.loanPurpose) &&
        Objects.equals(this.loanType, safeLoans.loanType) &&
        Objects.equals(this.openDate, safeLoans.openDate) &&
        Objects.equals(this.outStanding, safeLoans.outStanding) &&
        Objects.equals(this.productCategory, safeLoans.productCategory) &&
        Objects.equals(this.subAccountNo, safeLoans.subAccountNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, asOfDate, closeDate, creditLimit, loanPurpose, loanType, openDate, outStanding, productCategory, subAccountNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SafeLoans {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    asOfDate: ").append(toIndentedString(asOfDate)).append("\n");
    sb.append("    closeDate: ").append(toIndentedString(closeDate)).append("\n");
    sb.append("    creditLimit: ").append(toIndentedString(creditLimit)).append("\n");
    sb.append("    loanPurpose: ").append(toIndentedString(loanPurpose)).append("\n");
    sb.append("    loanType: ").append(toIndentedString(loanType)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    outStanding: ").append(toIndentedString(outStanding)).append("\n");
    sb.append("    productCategory: ").append(toIndentedString(productCategory)).append("\n");
    sb.append("    subAccountNo: ").append(toIndentedString(subAccountNo)).append("\n");
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

