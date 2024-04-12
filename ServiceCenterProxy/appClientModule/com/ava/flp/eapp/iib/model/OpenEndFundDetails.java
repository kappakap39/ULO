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
 * OpenEndFundDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class OpenEndFundDetails   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("month")
  private BigDecimal month = null;

  @JsonProperty("opnEndFundDetailId")
  private String opnEndFundDetailId = null;

  @JsonProperty("opnEndFundId")
  private String opnEndFundId = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("year")
  private BigDecimal year = null;

  public OpenEndFundDetails amount(BigDecimal amount) {
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

  public OpenEndFundDetails month(BigDecimal month) {
    this.month = month;
    return this;
  }

   /**
   * Get month
   * @return month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMonth() {
    return month;
  }

  public void setMonth(BigDecimal month) {
    this.month = month;
  }

  public OpenEndFundDetails opnEndFundDetailId(String opnEndFundDetailId) {
    this.opnEndFundDetailId = opnEndFundDetailId;
    return this;
  }

   /**
   * Get opnEndFundDetailId
   * @return opnEndFundDetailId
  **/
  @ApiModelProperty(value = "")


  public String getOpnEndFundDetailId() {
    return opnEndFundDetailId;
  }

  public void setOpnEndFundDetailId(String opnEndFundDetailId) {
    this.opnEndFundDetailId = opnEndFundDetailId;
  }

  public OpenEndFundDetails opnEndFundId(String opnEndFundId) {
    this.opnEndFundId = opnEndFundId;
    return this;
  }

   /**
   * Get opnEndFundId
   * @return opnEndFundId
  **/
  @ApiModelProperty(value = "")


  public String getOpnEndFundId() {
    return opnEndFundId;
  }

  public void setOpnEndFundId(String opnEndFundId) {
    this.opnEndFundId = opnEndFundId;
  }

  public OpenEndFundDetails seq(BigDecimal seq) {
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

  public OpenEndFundDetails year(BigDecimal year) {
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
    OpenEndFundDetails openEndFundDetails = (OpenEndFundDetails) o;
    return Objects.equals(this.amount, openEndFundDetails.amount) &&
        Objects.equals(this.month, openEndFundDetails.month) &&
        Objects.equals(this.opnEndFundDetailId, openEndFundDetails.opnEndFundDetailId) &&
        Objects.equals(this.opnEndFundId, openEndFundDetails.opnEndFundId) &&
        Objects.equals(this.seq, openEndFundDetails.seq) &&
        Objects.equals(this.year, openEndFundDetails.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, month, opnEndFundDetailId, opnEndFundId, seq, year);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenEndFundDetails {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    opnEndFundDetailId: ").append(toIndentedString(opnEndFundDetailId)).append("\n");
    sb.append("    opnEndFundId: ").append(toIndentedString(opnEndFundId)).append("\n");
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

