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
 * LoanFees
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class LoanFees   {
  @JsonProperty("endMonth")
  private BigDecimal endMonth = null;

  @JsonProperty("fee")
  private BigDecimal fee = null;

  @JsonProperty("feeCalcType")
  private String feeCalcType = null;

  @JsonProperty("feeLevelType")
  private String feeLevelType = null;

  @JsonProperty("feeSrc")
  private String feeSrc = null;

  @JsonProperty("feeType")
  private String feeType = null;

  @JsonProperty("finalFeeAmount")
  private BigDecimal finalFeeAmount = null;

  @JsonProperty("maxFee")
  private BigDecimal maxFee = null;

  @JsonProperty("minFee")
  private BigDecimal minFee = null;

  @JsonProperty("startMonth")
  private BigDecimal startMonth = null;

  public LoanFees endMonth(BigDecimal endMonth) {
    this.endMonth = endMonth;
    return this;
  }

   /**
   * Get endMonth
   * @return endMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getEndMonth() {
    return endMonth;
  }

  public void setEndMonth(BigDecimal endMonth) {
    this.endMonth = endMonth;
  }

  public LoanFees fee(BigDecimal fee) {
    this.fee = fee;
    return this;
  }

   /**
   * Get fee
   * @return fee
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(BigDecimal fee) {
    this.fee = fee;
  }

  public LoanFees feeCalcType(String feeCalcType) {
    this.feeCalcType = feeCalcType;
    return this;
  }

   /**
   * Get feeCalcType
   * @return feeCalcType
  **/
  @ApiModelProperty(value = "")


  public String getFeeCalcType() {
    return feeCalcType;
  }

  public void setFeeCalcType(String feeCalcType) {
    this.feeCalcType = feeCalcType;
  }

  public LoanFees feeLevelType(String feeLevelType) {
    this.feeLevelType = feeLevelType;
    return this;
  }

   /**
   * Get feeLevelType
   * @return feeLevelType
  **/
  @ApiModelProperty(value = "")


  public String getFeeLevelType() {
    return feeLevelType;
  }

  public void setFeeLevelType(String feeLevelType) {
    this.feeLevelType = feeLevelType;
  }

  public LoanFees feeSrc(String feeSrc) {
    this.feeSrc = feeSrc;
    return this;
  }

   /**
   * Get feeSrc
   * @return feeSrc
  **/
  @ApiModelProperty(value = "")


  public String getFeeSrc() {
    return feeSrc;
  }

  public void setFeeSrc(String feeSrc) {
    this.feeSrc = feeSrc;
  }

  public LoanFees feeType(String feeType) {
    this.feeType = feeType;
    return this;
  }

   /**
   * Get feeType
   * @return feeType
  **/
  @ApiModelProperty(value = "")


  public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }

  public LoanFees finalFeeAmount(BigDecimal finalFeeAmount) {
    this.finalFeeAmount = finalFeeAmount;
    return this;
  }

   /**
   * Get finalFeeAmount
   * @return finalFeeAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getFinalFeeAmount() {
    return finalFeeAmount;
  }

  public void setFinalFeeAmount(BigDecimal finalFeeAmount) {
    this.finalFeeAmount = finalFeeAmount;
  }

  public LoanFees maxFee(BigDecimal maxFee) {
    this.maxFee = maxFee;
    return this;
  }

   /**
   * Get maxFee
   * @return maxFee
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMaxFee() {
    return maxFee;
  }

  public void setMaxFee(BigDecimal maxFee) {
    this.maxFee = maxFee;
  }

  public LoanFees minFee(BigDecimal minFee) {
    this.minFee = minFee;
    return this;
  }

   /**
   * Get minFee
   * @return minFee
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMinFee() {
    return minFee;
  }

  public void setMinFee(BigDecimal minFee) {
    this.minFee = minFee;
  }

  public LoanFees startMonth(BigDecimal startMonth) {
    this.startMonth = startMonth;
    return this;
  }

   /**
   * Get startMonth
   * @return startMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getStartMonth() {
    return startMonth;
  }

  public void setStartMonth(BigDecimal startMonth) {
    this.startMonth = startMonth;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoanFees loanFees = (LoanFees) o;
    return Objects.equals(this.endMonth, loanFees.endMonth) &&
        Objects.equals(this.fee, loanFees.fee) &&
        Objects.equals(this.feeCalcType, loanFees.feeCalcType) &&
        Objects.equals(this.feeLevelType, loanFees.feeLevelType) &&
        Objects.equals(this.feeSrc, loanFees.feeSrc) &&
        Objects.equals(this.feeType, loanFees.feeType) &&
        Objects.equals(this.finalFeeAmount, loanFees.finalFeeAmount) &&
        Objects.equals(this.maxFee, loanFees.maxFee) &&
        Objects.equals(this.minFee, loanFees.minFee) &&
        Objects.equals(this.startMonth, loanFees.startMonth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(endMonth, fee, feeCalcType, feeLevelType, feeSrc, feeType, finalFeeAmount, maxFee, minFee, startMonth);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoanFees {\n");
    
    sb.append("    endMonth: ").append(toIndentedString(endMonth)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
    sb.append("    feeCalcType: ").append(toIndentedString(feeCalcType)).append("\n");
    sb.append("    feeLevelType: ").append(toIndentedString(feeLevelType)).append("\n");
    sb.append("    feeSrc: ").append(toIndentedString(feeSrc)).append("\n");
    sb.append("    feeType: ").append(toIndentedString(feeType)).append("\n");
    sb.append("    finalFeeAmount: ").append(toIndentedString(finalFeeAmount)).append("\n");
    sb.append("    maxFee: ").append(toIndentedString(maxFee)).append("\n");
    sb.append("    minFee: ").append(toIndentedString(minFee)).append("\n");
    sb.append("    startMonth: ").append(toIndentedString(startMonth)).append("\n");
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

