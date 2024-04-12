package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.PayslipMonthlyDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PayslipMonthlys
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PayslipMonthlys   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("incomeDesc")
  private String incomeDesc = null;

  @JsonProperty("incomeOthDesc")
  private String incomeOthDesc = null;

  @JsonProperty("incomeType")
  private String incomeType = null;

  @JsonProperty("month")
  private String month = null;

  @JsonProperty("payslipId")
  private String payslipId = null;

  @JsonProperty("payslipMonthlyId")
  private String payslipMonthlyId = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("year")
  private String year = null;

  @JsonProperty("payslipMonthlyDetails")
  private List<PayslipMonthlyDetails> payslipMonthlyDetails = null;

  public PayslipMonthlys amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public PayslipMonthlys incomeDesc(String incomeDesc) {
    this.incomeDesc = incomeDesc;
    return this;
  }

   /**
   * Get incomeDesc
   * @return incomeDesc
  **/
  @ApiModelProperty(value = "")


  public String getIncomeDesc() {
    return incomeDesc;
  }

  public void setIncomeDesc(String incomeDesc) {
    this.incomeDesc = incomeDesc;
  }

  public PayslipMonthlys incomeOthDesc(String incomeOthDesc) {
    this.incomeOthDesc = incomeOthDesc;
    return this;
  }

   /**
   * Get incomeOthDesc
   * @return incomeOthDesc
  **/
  @ApiModelProperty(value = "")


  public String getIncomeOthDesc() {
    return incomeOthDesc;
  }

  public void setIncomeOthDesc(String incomeOthDesc) {
    this.incomeOthDesc = incomeOthDesc;
  }

  public PayslipMonthlys incomeType(String incomeType) {
    this.incomeType = incomeType;
    return this;
  }

   /**
   * Get incomeType
   * @return incomeType
  **/
  @ApiModelProperty(value = "")


  public String getIncomeType() {
    return incomeType;
  }

  public void setIncomeType(String incomeType) {
    this.incomeType = incomeType;
  }

  public PayslipMonthlys month(String month) {
    this.month = month;
    return this;
  }

   /**
   * Get month
   * @return month
  **/
  @ApiModelProperty(value = "")


  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public PayslipMonthlys payslipId(String payslipId) {
    this.payslipId = payslipId;
    return this;
  }

   /**
   * Get payslipId
   * @return payslipId
  **/
  @ApiModelProperty(value = "")


  public String getPayslipId() {
    return payslipId;
  }

  public void setPayslipId(String payslipId) {
    this.payslipId = payslipId;
  }

  public PayslipMonthlys payslipMonthlyId(String payslipMonthlyId) {
    this.payslipMonthlyId = payslipMonthlyId;
    return this;
  }

   /**
   * Get payslipMonthlyId
   * @return payslipMonthlyId
  **/
  @ApiModelProperty(value = "")


  public String getPayslipMonthlyId() {
    return payslipMonthlyId;
  }

  public void setPayslipMonthlyId(String payslipMonthlyId) {
    this.payslipMonthlyId = payslipMonthlyId;
  }

  public PayslipMonthlys seq(BigDecimal seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSeq() {
    return seq;
  }

  public void setSeq(BigDecimal seq) {
    this.seq = seq;
  }

  public PayslipMonthlys year(String year) {
    this.year = year;
    return this;
  }

   /**
   * Get year
   * @return year
  **/
  @ApiModelProperty(value = "")


  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public PayslipMonthlys payslipMonthlyDetails(List<PayslipMonthlyDetails> payslipMonthlyDetails) {
    this.payslipMonthlyDetails = payslipMonthlyDetails;
    return this;
  }

  public PayslipMonthlys addPayslipMonthlyDetailsItem(PayslipMonthlyDetails payslipMonthlyDetailsItem) {
    if (this.payslipMonthlyDetails == null) {
      this.payslipMonthlyDetails = new ArrayList<PayslipMonthlyDetails>();
    }
    this.payslipMonthlyDetails.add(payslipMonthlyDetailsItem);
    return this;
  }

   /**
   * Get payslipMonthlyDetails
   * @return payslipMonthlyDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PayslipMonthlyDetails> getPayslipMonthlyDetails() {
    return payslipMonthlyDetails;
  }

  public void setPayslipMonthlyDetails(List<PayslipMonthlyDetails> payslipMonthlyDetails) {
    this.payslipMonthlyDetails = payslipMonthlyDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PayslipMonthlys payslipMonthlys = (PayslipMonthlys) o;
    return Objects.equals(this.amount, payslipMonthlys.amount) &&
        Objects.equals(this.incomeDesc, payslipMonthlys.incomeDesc) &&
        Objects.equals(this.incomeOthDesc, payslipMonthlys.incomeOthDesc) &&
        Objects.equals(this.incomeType, payslipMonthlys.incomeType) &&
        Objects.equals(this.month, payslipMonthlys.month) &&
        Objects.equals(this.payslipId, payslipMonthlys.payslipId) &&
        Objects.equals(this.payslipMonthlyId, payslipMonthlys.payslipMonthlyId) &&
        Objects.equals(this.seq, payslipMonthlys.seq) &&
        Objects.equals(this.year, payslipMonthlys.year) &&
        Objects.equals(this.payslipMonthlyDetails, payslipMonthlys.payslipMonthlyDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, incomeDesc, incomeOthDesc, incomeType, month, payslipId, payslipMonthlyId, seq, year, payslipMonthlyDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PayslipMonthlys {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    incomeDesc: ").append(toIndentedString(incomeDesc)).append("\n");
    sb.append("    incomeOthDesc: ").append(toIndentedString(incomeOthDesc)).append("\n");
    sb.append("    incomeType: ").append(toIndentedString(incomeType)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    payslipId: ").append(toIndentedString(payslipId)).append("\n");
    sb.append("    payslipMonthlyId: ").append(toIndentedString(payslipMonthlyId)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    payslipMonthlyDetails: ").append(toIndentedString(payslipMonthlyDetails)).append("\n");
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

