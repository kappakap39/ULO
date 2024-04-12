package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PreviousIncomesWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PreviousIncomesWF   {
  @JsonProperty("income")
  private String income = null;

  @JsonProperty("incomeDate")
  private String incomeDate = null;

  public PreviousIncomesWF income(String income) {
    this.income = income;
    return this;
  }

   /**
   * Get income
   * @return income
  **/
  @ApiModelProperty(value = "")


  public String getIncome() {
    return income;
  }

  public void setIncome(String income) {
    this.income = income;
  }

  public PreviousIncomesWF incomeDate(String incomeDate) {
    this.incomeDate = incomeDate;
    return this;
  }

   /**
   * Get incomeDate
   * @return incomeDate
  **/
  @ApiModelProperty(value = "")


  public String getIncomeDate() {
    return incomeDate;
  }

  public void setIncomeDate(String incomeDate) {
    this.incomeDate = incomeDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PreviousIncomesWF previousIncomesWF = (PreviousIncomesWF) o;
    return Objects.equals(this.income, previousIncomesWF.income) &&
        Objects.equals(this.incomeDate, previousIncomesWF.incomeDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(income, incomeDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PreviousIncomesWF {\n");
    
    sb.append("    income: ").append(toIndentedString(income)).append("\n");
    sb.append("    incomeDate: ").append(toIndentedString(incomeDate)).append("\n");
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

