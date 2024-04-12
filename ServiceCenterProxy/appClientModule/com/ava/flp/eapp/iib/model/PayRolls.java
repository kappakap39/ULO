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
 * PayRolls
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PayRolls   {
  @JsonProperty("income")
  private BigDecimal income = null;

  @JsonProperty("noOfEmployee")
  private BigDecimal noOfEmployee = null;

  @JsonProperty("payrollId")
  private String payrollId = null;

  public PayRolls income(BigDecimal income) {
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

  public PayRolls noOfEmployee(BigDecimal noOfEmployee) {
    this.noOfEmployee = noOfEmployee;
    return this;
  }

   /**
   * Get noOfEmployee
   * @return noOfEmployee
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNoOfEmployee() {
    return noOfEmployee;
  }

  public void setNoOfEmployee(BigDecimal noOfEmployee) {
    this.noOfEmployee = noOfEmployee;
  }

  public PayRolls payrollId(String payrollId) {
    this.payrollId = payrollId;
    return this;
  }

   /**
   * Get payrollId
   * @return payrollId
  **/
  @ApiModelProperty(value = "")


  public String getPayrollId() {
    return payrollId;
  }

  public void setPayrollId(String payrollId) {
    this.payrollId = payrollId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PayRolls payRolls = (PayRolls) o;
    return Objects.equals(this.income, payRolls.income) &&
        Objects.equals(this.noOfEmployee, payRolls.noOfEmployee) &&
        Objects.equals(this.payrollId, payRolls.payrollId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(income, noOfEmployee, payrollId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PayRolls {\n");
    
    sb.append("    income: ").append(toIndentedString(income)).append("\n");
    sb.append("    noOfEmployee: ").append(toIndentedString(noOfEmployee)).append("\n");
    sb.append("    payrollId: ").append(toIndentedString(payrollId)).append("\n");
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

