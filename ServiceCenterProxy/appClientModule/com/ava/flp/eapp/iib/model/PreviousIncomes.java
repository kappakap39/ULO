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
 * PreviousIncomes
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PreviousIncomes   {
  @JsonProperty("income")
  private BigDecimal income = null;

  @JsonProperty("incomeDate")
  private DateTime incomeDate = null;

  @JsonProperty("previousIncomeId")
  private String previousIncomeId = null;

  @JsonProperty("product")
  private String product = null;

  public PreviousIncomes income(BigDecimal income) {
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

  public PreviousIncomes incomeDate(DateTime incomeDate) {
    this.incomeDate = incomeDate;
    return this;
  }

   /**
   * Get incomeDate
   * @return incomeDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getIncomeDate() {
    return incomeDate;
  }

  public void setIncomeDate(DateTime incomeDate) {
    this.incomeDate = incomeDate;
  }

  public PreviousIncomes previousIncomeId(String previousIncomeId) {
    this.previousIncomeId = previousIncomeId;
    return this;
  }

   /**
   * Get previousIncomeId
   * @return previousIncomeId
  **/
  @ApiModelProperty(value = "")


  public String getPreviousIncomeId() {
    return previousIncomeId;
  }

  public void setPreviousIncomeId(String previousIncomeId) {
    this.previousIncomeId = previousIncomeId;
  }

  public PreviousIncomes product(String product) {
    this.product = product;
    return this;
  }

   /**
   * Get product
   * @return product
  **/
  @ApiModelProperty(value = "")


  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PreviousIncomes previousIncomes = (PreviousIncomes) o;
    return Objects.equals(this.income, previousIncomes.income) &&
        Objects.equals(this.incomeDate, previousIncomes.incomeDate) &&
        Objects.equals(this.previousIncomeId, previousIncomes.previousIncomeId) &&
        Objects.equals(this.product, previousIncomes.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(income, incomeDate, previousIncomeId, product);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PreviousIncomes {\n");
    
    sb.append("    income: ").append(toIndentedString(income)).append("\n");
    sb.append("    incomeDate: ").append(toIndentedString(incomeDate)).append("\n");
    sb.append("    previousIncomeId: ").append(toIndentedString(previousIncomeId)).append("\n");
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
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

