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
 * YearlyTawi50s
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class YearlyTawi50s   {
  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("income401")
  private BigDecimal income401 = null;

  @JsonProperty("income402")
  private BigDecimal income402 = null;

  @JsonProperty("month")
  private String month = null;

  @JsonProperty("sumSso")
  private BigDecimal sumSso = null;

  @JsonProperty("year")
  private String year = null;

  @JsonProperty("yearlyTawiId")
  private String yearlyTawiId = null;

  public YearlyTawi50s companyName(String companyName) {
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

  public YearlyTawi50s income401(BigDecimal income401) {
    this.income401 = income401;
    return this;
  }

   /**
   * Get income401
   * @return income401
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncome401() {
    return income401;
  }

  public void setIncome401(BigDecimal income401) {
    this.income401 = income401;
  }

  public YearlyTawi50s income402(BigDecimal income402) {
    this.income402 = income402;
    return this;
  }

   /**
   * Get income402
   * @return income402
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncome402() {
    return income402;
  }

  public void setIncome402(BigDecimal income402) {
    this.income402 = income402;
  }

  public YearlyTawi50s month(String month) {
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

  public YearlyTawi50s sumSso(BigDecimal sumSso) {
    this.sumSso = sumSso;
    return this;
  }

   /**
   * Get sumSso
   * @return sumSso
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSumSso() {
    return sumSso;
  }

  public void setSumSso(BigDecimal sumSso) {
    this.sumSso = sumSso;
  }

  public YearlyTawi50s year(String year) {
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

  public YearlyTawi50s yearlyTawiId(String yearlyTawiId) {
    this.yearlyTawiId = yearlyTawiId;
    return this;
  }

   /**
   * Get yearlyTawiId
   * @return yearlyTawiId
  **/
  @ApiModelProperty(value = "")


  public String getYearlyTawiId() {
    return yearlyTawiId;
  }

  public void setYearlyTawiId(String yearlyTawiId) {
    this.yearlyTawiId = yearlyTawiId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    YearlyTawi50s yearlyTawi50s = (YearlyTawi50s) o;
    return Objects.equals(this.companyName, yearlyTawi50s.companyName) &&
        Objects.equals(this.income401, yearlyTawi50s.income401) &&
        Objects.equals(this.income402, yearlyTawi50s.income402) &&
        Objects.equals(this.month, yearlyTawi50s.month) &&
        Objects.equals(this.sumSso, yearlyTawi50s.sumSso) &&
        Objects.equals(this.year, yearlyTawi50s.year) &&
        Objects.equals(this.yearlyTawiId, yearlyTawi50s.yearlyTawiId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(companyName, income401, income402, month, sumSso, year, yearlyTawiId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class YearlyTawi50s {\n");
    
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    income401: ").append(toIndentedString(income401)).append("\n");
    sb.append("    income402: ").append(toIndentedString(income402)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    sumSso: ").append(toIndentedString(sumSso)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    yearlyTawiId: ").append(toIndentedString(yearlyTawiId)).append("\n");
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

