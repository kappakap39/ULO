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
 * MonthlyTaxi50Details
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class MonthlyTaxi50Details   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("month")
  private BigDecimal month = null;

  @JsonProperty("monthlyTawiDetailId")
  private String monthlyTawiDetailId = null;

  @JsonProperty("monthlyTawiId")
  private String monthlyTawiId = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("year")
  private BigDecimal year = null;

  public MonthlyTaxi50Details amount(BigDecimal amount) {
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

  public MonthlyTaxi50Details month(BigDecimal month) {
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

  public MonthlyTaxi50Details monthlyTawiDetailId(String monthlyTawiDetailId) {
    this.monthlyTawiDetailId = monthlyTawiDetailId;
    return this;
  }

   /**
   * Get monthlyTawiDetailId
   * @return monthlyTawiDetailId
  **/
  @ApiModelProperty(value = "")


  public String getMonthlyTawiDetailId() {
    return monthlyTawiDetailId;
  }

  public void setMonthlyTawiDetailId(String monthlyTawiDetailId) {
    this.monthlyTawiDetailId = monthlyTawiDetailId;
  }

  public MonthlyTaxi50Details monthlyTawiId(String monthlyTawiId) {
    this.monthlyTawiId = monthlyTawiId;
    return this;
  }

   /**
   * Get monthlyTawiId
   * @return monthlyTawiId
  **/
  @ApiModelProperty(value = "")


  public String getMonthlyTawiId() {
    return monthlyTawiId;
  }

  public void setMonthlyTawiId(String monthlyTawiId) {
    this.monthlyTawiId = monthlyTawiId;
  }

  public MonthlyTaxi50Details seq(BigDecimal seq) {
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

  public MonthlyTaxi50Details year(BigDecimal year) {
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
    MonthlyTaxi50Details monthlyTaxi50Details = (MonthlyTaxi50Details) o;
    return Objects.equals(this.amount, monthlyTaxi50Details.amount) &&
        Objects.equals(this.month, monthlyTaxi50Details.month) &&
        Objects.equals(this.monthlyTawiDetailId, monthlyTaxi50Details.monthlyTawiDetailId) &&
        Objects.equals(this.monthlyTawiId, monthlyTaxi50Details.monthlyTawiId) &&
        Objects.equals(this.seq, monthlyTaxi50Details.seq) &&
        Objects.equals(this.year, monthlyTaxi50Details.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, month, monthlyTawiDetailId, monthlyTawiId, seq, year);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonthlyTaxi50Details {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    monthlyTawiDetailId: ").append(toIndentedString(monthlyTawiDetailId)).append("\n");
    sb.append("    monthlyTawiId: ").append(toIndentedString(monthlyTawiId)).append("\n");
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

