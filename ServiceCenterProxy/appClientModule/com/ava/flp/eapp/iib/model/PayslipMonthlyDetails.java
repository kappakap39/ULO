package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PayslipMonthlyDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PayslipMonthlyDetails   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("month")
  private String month = null;

  @JsonProperty("payslipMonthlyDetailId")
  private BigDecimal payslipMonthlyDetailId = null;

  @JsonProperty("payslipMonthlyId")
  private BigDecimal payslipMonthlyId = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("year")
  private BigDecimal year = null;

  public PayslipMonthlyDetails amount(BigDecimal amount) {
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

  public PayslipMonthlyDetails month(String month) {
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

  public PayslipMonthlyDetails payslipMonthlyDetailId(BigDecimal payslipMonthlyDetailId) {
    this.payslipMonthlyDetailId = payslipMonthlyDetailId;
    return this;
  }

   /**
   * Get payslipMonthlyDetailId
   * @return payslipMonthlyDetailId
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPayslipMonthlyDetailId() {
    return payslipMonthlyDetailId;
  }

  public void setPayslipMonthlyDetailId(BigDecimal payslipMonthlyDetailId) {
    this.payslipMonthlyDetailId = payslipMonthlyDetailId;
  }

  public PayslipMonthlyDetails payslipMonthlyId(BigDecimal payslipMonthlyId) {
    this.payslipMonthlyId = payslipMonthlyId;
    return this;
  }

   /**
   * Get payslipMonthlyId
   * @return payslipMonthlyId
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPayslipMonthlyId() {
    return payslipMonthlyId;
  }

  public void setPayslipMonthlyId(BigDecimal payslipMonthlyId) {
    this.payslipMonthlyId = payslipMonthlyId;
  }

  public PayslipMonthlyDetails seq(BigDecimal seq) {
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

  public PayslipMonthlyDetails year(BigDecimal year) {
    this.year = year;
    return this;
  }

   /**
   * Get year
   * @return year
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getYear() {
    return year;
  }

  public void setYear(BigDecimal year) {
    this.year = year;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PayslipMonthlyDetails payslipMonthlyDetails = (PayslipMonthlyDetails) o;
    return Objects.equals(this.amount, payslipMonthlyDetails.amount) &&
        Objects.equals(this.month, payslipMonthlyDetails.month) &&
        Objects.equals(this.payslipMonthlyDetailId, payslipMonthlyDetails.payslipMonthlyDetailId) &&
        Objects.equals(this.payslipMonthlyId, payslipMonthlyDetails.payslipMonthlyId) &&
        Objects.equals(this.seq, payslipMonthlyDetails.seq) &&
        Objects.equals(this.year, payslipMonthlyDetails.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, month, payslipMonthlyDetailId, payslipMonthlyId, seq, year);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PayslipMonthlyDetails {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    payslipMonthlyDetailId: ").append(toIndentedString(payslipMonthlyDetailId)).append("\n");
    sb.append("    payslipMonthlyId: ").append(toIndentedString(payslipMonthlyId)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
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

