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
 * LoanPricings
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class LoanPricings   {
  @JsonProperty("feeWaiveCode")
  private String feeWaiveCode = null;

  @JsonProperty("feeStartMonth")
  private BigDecimal feeStartMonth = null;

  @JsonProperty("feeEndMonth")
  private BigDecimal feeEndMonth = null;

  @JsonProperty("terminationFeeFlag")
  private String terminationFeeFlag = null;

  @JsonProperty("terminationFeeCode")
  private String terminationFeeCode = null;

  public LoanPricings feeWaiveCode(String feeWaiveCode) {
    this.feeWaiveCode = feeWaiveCode;
    return this;
  }

   /**
   * Get feeWaiveCode
   * @return feeWaiveCode
  **/
  @ApiModelProperty(value = "")


  public String getFeeWaiveCode() {
    return feeWaiveCode;
  }

  public void setFeeWaiveCode(String feeWaiveCode) {
    this.feeWaiveCode = feeWaiveCode;
  }

  public LoanPricings feeStartMonth(BigDecimal feeStartMonth) {
    this.feeStartMonth = feeStartMonth;
    return this;
  }

   /**
   * Get feeStartMonth
   * @return feeStartMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getFeeStartMonth() {
    return feeStartMonth;
  }

  public void setFeeStartMonth(BigDecimal feeStartMonth) {
    this.feeStartMonth = feeStartMonth;
  }

  public LoanPricings feeEndMonth(BigDecimal feeEndMonth) {
    this.feeEndMonth = feeEndMonth;
    return this;
  }

   /**
   * Get feeEndMonth
   * @return feeEndMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getFeeEndMonth() {
    return feeEndMonth;
  }

  public void setFeeEndMonth(BigDecimal feeEndMonth) {
    this.feeEndMonth = feeEndMonth;
  }

  public LoanPricings terminationFeeFlag(String terminationFeeFlag) {
    this.terminationFeeFlag = terminationFeeFlag;
    return this;
  }

   /**
   * Get terminationFeeFlag
   * @return terminationFeeFlag
  **/
  @ApiModelProperty(value = "")


  public String getTerminationFeeFlag() {
    return terminationFeeFlag;
  }

  public void setTerminationFeeFlag(String terminationFeeFlag) {
    this.terminationFeeFlag = terminationFeeFlag;
  }

  public LoanPricings terminationFeeCode(String terminationFeeCode) {
    this.terminationFeeCode = terminationFeeCode;
    return this;
  }

   /**
   * Get terminationFeeCode
   * @return terminationFeeCode
  **/
  @ApiModelProperty(value = "")


  public String getTerminationFeeCode() {
    return terminationFeeCode;
  }

  public void setTerminationFeeCode(String terminationFeeCode) {
    this.terminationFeeCode = terminationFeeCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoanPricings loanPricings = (LoanPricings) o;
    return Objects.equals(this.feeWaiveCode, loanPricings.feeWaiveCode) &&
        Objects.equals(this.feeStartMonth, loanPricings.feeStartMonth) &&
        Objects.equals(this.feeEndMonth, loanPricings.feeEndMonth) &&
        Objects.equals(this.terminationFeeFlag, loanPricings.terminationFeeFlag) &&
        Objects.equals(this.terminationFeeCode, loanPricings.terminationFeeCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(feeWaiveCode, feeStartMonth, feeEndMonth, terminationFeeFlag, terminationFeeCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoanPricings {\n");
    
    sb.append("    feeWaiveCode: ").append(toIndentedString(feeWaiveCode)).append("\n");
    sb.append("    feeStartMonth: ").append(toIndentedString(feeStartMonth)).append("\n");
    sb.append("    feeEndMonth: ").append(toIndentedString(feeEndMonth)).append("\n");
    sb.append("    terminationFeeFlag: ").append(toIndentedString(terminationFeeFlag)).append("\n");
    sb.append("    terminationFeeCode: ").append(toIndentedString(terminationFeeCode)).append("\n");
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

