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
 * IncomeCalculations
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-07-11T12:24:40.224+07:00")

public class IncomeCalculations   {
  @JsonProperty("incomeSourceType")
  private String incomeSourceType = null;

  @JsonProperty("verifiedIncome")
  private BigDecimal verifiedIncome = null;

  @JsonProperty("typeOfFin")
  private String typeOfFin = null;

  @JsonProperty("totalVerifiedIncome")
  private BigDecimal totalVerifiedIncome = null;

  @JsonProperty("existingCreditLine")
  private BigDecimal existingCreditLine = null;

  @JsonProperty("verifiedIncomeType")
  private String verifiedIncomeType = null;

  @JsonProperty("verifiedIncomeCalculation")
  private String verifiedIncomeCalculation = null;

  public IncomeCalculations incomeSourceType(String incomeSourceType) {
    this.incomeSourceType = incomeSourceType;
    return this;
  }

   /**
   * Get incomeSourceType
   * @return incomeSourceType
  **/
  @ApiModelProperty(value = "")


  public String getIncomeSourceType() {
    return incomeSourceType;
  }

  public void setIncomeSourceType(String incomeSourceType) {
    this.incomeSourceType = incomeSourceType;
  }

  public IncomeCalculations verifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
    return this;
  }

   /**
   * Get verifiedIncome
   * @return verifiedIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getVerifiedIncome() {
    return verifiedIncome;
  }

  public void setVerifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
  }

  public IncomeCalculations typeOfFin(String typeOfFin) {
    this.typeOfFin = typeOfFin;
    return this;
  }

   /**
   * Get typeOfFin
   * @return typeOfFin
  **/
  @ApiModelProperty(value = "")


  public String getTypeOfFin() {
    return typeOfFin;
  }

  public void setTypeOfFin(String typeOfFin) {
    this.typeOfFin = typeOfFin;
  }

  public IncomeCalculations totalVerifiedIncome(BigDecimal totalVerifiedIncome) {
    this.totalVerifiedIncome = totalVerifiedIncome;
    return this;
  }

   /**
   * Get totalVerifiedIncome
   * @return totalVerifiedIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotalVerifiedIncome() {
    return totalVerifiedIncome;
  }

  public void setTotalVerifiedIncome(BigDecimal totalVerifiedIncome) {
    this.totalVerifiedIncome = totalVerifiedIncome;
  }

  public IncomeCalculations existingCreditLine(BigDecimal existingCreditLine) {
    this.existingCreditLine = existingCreditLine;
    return this;
  }

   /**
   * Get existingCreditLine
   * @return existingCreditLine
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getExistingCreditLine() {
    return existingCreditLine;
  }

  public void setExistingCreditLine(BigDecimal existingCreditLine) {
    this.existingCreditLine = existingCreditLine;
  }

  public IncomeCalculations verifiedIncomeType(String verifiedIncomeType) {
    this.verifiedIncomeType = verifiedIncomeType;
    return this;
  }

   /**
   * Get verifiedIncomeType
   * @return verifiedIncomeType
  **/
  @ApiModelProperty(value = "")


  public String getVerifiedIncomeType() {
    return verifiedIncomeType;
  }

  public void setVerifiedIncomeType(String verifiedIncomeType) {
    this.verifiedIncomeType = verifiedIncomeType;
  }

  public IncomeCalculations verifiedIncomeCalculation(String verifiedIncomeCalculation) {
    this.verifiedIncomeCalculation = verifiedIncomeCalculation;
    return this;
  }

   /**
   * Get verifiedIncomeCalculation
   * @return verifiedIncomeCalculation
  **/
  @ApiModelProperty(value = "")


  public String getVerifiedIncomeCalculation() {
    return verifiedIncomeCalculation;
  }

  public void setVerifiedIncomeCalculation(String verifiedIncomeCalculation) {
    this.verifiedIncomeCalculation = verifiedIncomeCalculation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncomeCalculations incomeCalculations = (IncomeCalculations) o;
    return Objects.equals(this.incomeSourceType, incomeCalculations.incomeSourceType) &&
        Objects.equals(this.verifiedIncome, incomeCalculations.verifiedIncome) &&
        Objects.equals(this.typeOfFin, incomeCalculations.typeOfFin) &&
        Objects.equals(this.totalVerifiedIncome, incomeCalculations.totalVerifiedIncome) &&
        Objects.equals(this.existingCreditLine, incomeCalculations.existingCreditLine) &&
        Objects.equals(this.verifiedIncomeType, incomeCalculations.verifiedIncomeType) &&
        Objects.equals(this.verifiedIncomeCalculation, incomeCalculations.verifiedIncomeCalculation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(incomeSourceType, verifiedIncome, typeOfFin, totalVerifiedIncome, existingCreditLine, verifiedIncomeType, verifiedIncomeCalculation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncomeCalculations {\n");
    
    sb.append("    incomeSourceType: ").append(toIndentedString(incomeSourceType)).append("\n");
    sb.append("    verifiedIncome: ").append(toIndentedString(verifiedIncome)).append("\n");
    sb.append("    typeOfFin: ").append(toIndentedString(typeOfFin)).append("\n");
    sb.append("    totalVerifiedIncome: ").append(toIndentedString(totalVerifiedIncome)).append("\n");
    sb.append("    existingCreditLine: ").append(toIndentedString(existingCreditLine)).append("\n");
    sb.append("    verifiedIncomeType: ").append(toIndentedString(verifiedIncomeType)).append("\n");
    sb.append("    verifiedIncomeCalculation: ").append(toIndentedString(verifiedIncomeCalculation)).append("\n");
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

