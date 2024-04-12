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
 * SalaryCerts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class SalaryCerts   {
  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("income")
  private BigDecimal income = null;

  @JsonProperty("salaryCertId")
  private String salaryCertId = null;

  @JsonProperty("otherIncome")
  private BigDecimal otherIncome = null;

  @JsonProperty("totalIncome")
  private BigDecimal totalIncome = null;

  public SalaryCerts companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

   /**
   * Get companyName
   * @return companyName
  **/
  @ApiModelProperty(value = "")


  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public SalaryCerts income(BigDecimal income) {
    this.income = income;
    return this;
  }

   /**
   * Get income
   * @return income
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncome() {
    return income;
  }

  public void setIncome(BigDecimal income) {
    this.income = income;
  }

  public SalaryCerts salaryCertId(String salaryCertId) {
    this.salaryCertId = salaryCertId;
    return this;
  }

   /**
   * Get salaryCertId
   * @return salaryCertId
  **/
  @ApiModelProperty(value = "")


  public String getSalaryCertId() {
    return salaryCertId;
  }

  public void setSalaryCertId(String salaryCertId) {
    this.salaryCertId = salaryCertId;
  }

  public SalaryCerts otherIncome(BigDecimal otherIncome) {
    this.otherIncome = otherIncome;
    return this;
  }

   /**
   * Get otherIncome
   * @return otherIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getOtherIncome() {
    return otherIncome;
  }

  public void setOtherIncome(BigDecimal otherIncome) {
    this.otherIncome = otherIncome;
  }

  public SalaryCerts totalIncome(BigDecimal totalIncome) {
    this.totalIncome = totalIncome;
    return this;
  }

   /**
   * Get totalIncome
   * @return totalIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotalIncome() {
    return totalIncome;
  }

  public void setTotalIncome(BigDecimal totalIncome) {
    this.totalIncome = totalIncome;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SalaryCerts salaryCerts = (SalaryCerts) o;
    return Objects.equals(this.companyName, salaryCerts.companyName) &&
        Objects.equals(this.income, salaryCerts.income) &&
        Objects.equals(this.salaryCertId, salaryCerts.salaryCertId) &&
        Objects.equals(this.otherIncome, salaryCerts.otherIncome) &&
        Objects.equals(this.totalIncome, salaryCerts.totalIncome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(companyName, income, salaryCertId, otherIncome, totalIncome);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SalaryCerts {\n");
    
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    income: ").append(toIndentedString(income)).append("\n");
    sb.append("    salaryCertId: ").append(toIndentedString(salaryCertId)).append("\n");
    sb.append("    otherIncome: ").append(toIndentedString(otherIncome)).append("\n");
    sb.append("    totalIncome: ").append(toIndentedString(totalIncome)).append("\n");
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

