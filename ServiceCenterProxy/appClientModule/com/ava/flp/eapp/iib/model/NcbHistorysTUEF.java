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
 * NcbHistorysTUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbHistorysTUEF   {
  @JsonProperty("segmentTag")
  private String segmentTag = null;

  @JsonProperty("asOfDate")
  private DateTime asOfDate = null;

  @JsonProperty("overDueMonths")
  private String overDueMonths = null;

  @JsonProperty("creditLimit")
  private BigDecimal creditLimit = null;

  @JsonProperty("amountOwed")
  private BigDecimal amountOwed = null;

  public NcbHistorysTUEF segmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
    return this;
  }

   /**
   * Get segmentTag
   * @return segmentTag
  **/
  @ApiModelProperty(value = "")


  public String getSegmentTag() {
    return segmentTag;
  }

  public void setSegmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
  }

  public NcbHistorysTUEF asOfDate(DateTime asOfDate) {
    this.asOfDate = asOfDate;
    return this;
  }

   /**
   * Get asOfDate
   * @return asOfDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getAsOfDate() {
    return asOfDate;
  }

  public void setAsOfDate(DateTime asOfDate) {
    this.asOfDate = asOfDate;
  }

  public NcbHistorysTUEF overDueMonths(String overDueMonths) {
    this.overDueMonths = overDueMonths;
    return this;
  }

   /**
   * Get overDueMonths
   * @return overDueMonths
  **/
  @ApiModelProperty(value = "")


  public String getOverDueMonths() {
    return overDueMonths;
  }

  public void setOverDueMonths(String overDueMonths) {
    this.overDueMonths = overDueMonths;
  }

  public NcbHistorysTUEF creditLimit(BigDecimal creditLimit) {
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

  public NcbHistorysTUEF amountOwed(BigDecimal amountOwed) {
    this.amountOwed = amountOwed;
    return this;
  }

   /**
   * Get amountOwed
   * @return amountOwed
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAmountOwed() {
    return amountOwed;
  }

  public void setAmountOwed(BigDecimal amountOwed) {
    this.amountOwed = amountOwed;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbHistorysTUEF ncbHistorysTUEF = (NcbHistorysTUEF) o;
    return Objects.equals(this.segmentTag, ncbHistorysTUEF.segmentTag) &&
        Objects.equals(this.asOfDate, ncbHistorysTUEF.asOfDate) &&
        Objects.equals(this.overDueMonths, ncbHistorysTUEF.overDueMonths) &&
        Objects.equals(this.creditLimit, ncbHistorysTUEF.creditLimit) &&
        Objects.equals(this.amountOwed, ncbHistorysTUEF.amountOwed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentTag, asOfDate, overDueMonths, creditLimit, amountOwed);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbHistorysTUEF {\n");
    
    sb.append("    segmentTag: ").append(toIndentedString(segmentTag)).append("\n");
    sb.append("    asOfDate: ").append(toIndentedString(asOfDate)).append("\n");
    sb.append("    overDueMonths: ").append(toIndentedString(overDueMonths)).append("\n");
    sb.append("    creditLimit: ").append(toIndentedString(creditLimit)).append("\n");
    sb.append("    amountOwed: ").append(toIndentedString(amountOwed)).append("\n");
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

