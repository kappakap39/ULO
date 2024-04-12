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
 * LoanTiers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class LoanTiers   {
  @JsonProperty("interestRate")
  private BigDecimal interestRate = null;

  @JsonProperty("rateType")
  private String rateType = null;

  @JsonProperty("monthlyInstallment")
  private BigDecimal monthlyInstallment = null;

  public LoanTiers interestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
    return this;
  }

   /**
   * Get interestRate
   * @return interestRate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  public LoanTiers rateType(String rateType) {
    this.rateType = rateType;
    return this;
  }

   /**
   * Get rateType
   * @return rateType
  **/
  @ApiModelProperty(value = "")


  public String getRateType() {
    return rateType;
  }

  public void setRateType(String rateType) {
    this.rateType = rateType;
  }

  public LoanTiers monthlyInstallment(BigDecimal monthlyInstallment) {
    this.monthlyInstallment = monthlyInstallment;
    return this;
  }

   /**
   * Get monthlyInstallment
   * @return monthlyInstallment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMonthlyInstallment() {
    return monthlyInstallment;
  }

  public void setMonthlyInstallment(BigDecimal monthlyInstallment) {
    this.monthlyInstallment = monthlyInstallment;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoanTiers loanTiers = (LoanTiers) o;
    return Objects.equals(this.interestRate, loanTiers.interestRate) &&
        Objects.equals(this.rateType, loanTiers.rateType) &&
        Objects.equals(this.monthlyInstallment, loanTiers.monthlyInstallment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interestRate, rateType, monthlyInstallment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoanTiers {\n");
    
    sb.append("    interestRate: ").append(toIndentedString(interestRate)).append("\n");
    sb.append("    rateType: ").append(toIndentedString(rateType)).append("\n");
    sb.append("    monthlyInstallment: ").append(toIndentedString(monthlyInstallment)).append("\n");
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

