package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.MonthlyTaxi50Details;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MonthlyTawi50s
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class MonthlyTawi50s   {
  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("monthlyTawiId")
  private String monthlyTawiId = null;

  @JsonProperty("tawiIncomeType")
  private String tawiIncomeType = null;

  @JsonProperty("monthlyTaxi50Details")
  private List<MonthlyTaxi50Details> monthlyTaxi50Details = null;

  public MonthlyTawi50s companyName(String companyName) {
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

  public MonthlyTawi50s monthlyTawiId(String monthlyTawiId) {
    this.monthlyTawiId = monthlyTawiId;
    return this;
  }

   /**
   * Get monthlyTawiId
   * @return monthlyTawiId
  **/
  @ApiModelProperty(value = "")


  public String getMonthlyTawiId() {
    return monthlyTawiId;
  }

  public void setMonthlyTawiId(String monthlyTawiId) {
    this.monthlyTawiId = monthlyTawiId;
  }

  public MonthlyTawi50s tawiIncomeType(String tawiIncomeType) {
    this.tawiIncomeType = tawiIncomeType;
    return this;
  }

   /**
   * Get tawiIncomeType
   * @return tawiIncomeType
  **/
  @ApiModelProperty(value = "")


  public String getTawiIncomeType() {
    return tawiIncomeType;
  }

  public void setTawiIncomeType(String tawiIncomeType) {
    this.tawiIncomeType = tawiIncomeType;
  }

  public MonthlyTawi50s monthlyTaxi50Details(List<MonthlyTaxi50Details> monthlyTaxi50Details) {
    this.monthlyTaxi50Details = monthlyTaxi50Details;
    return this;
  }

  public MonthlyTawi50s addMonthlyTaxi50DetailsItem(MonthlyTaxi50Details monthlyTaxi50DetailsItem) {
    if (this.monthlyTaxi50Details == null) {
      this.monthlyTaxi50Details = new ArrayList<MonthlyTaxi50Details>();
    }
    this.monthlyTaxi50Details.add(monthlyTaxi50DetailsItem);
    return this;
  }

   /**
   * Get monthlyTaxi50Details
   * @return monthlyTaxi50Details
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<MonthlyTaxi50Details> getMonthlyTaxi50Details() {
    return monthlyTaxi50Details;
  }

  public void setMonthlyTaxi50Details(List<MonthlyTaxi50Details> monthlyTaxi50Details) {
    this.monthlyTaxi50Details = monthlyTaxi50Details;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MonthlyTawi50s monthlyTawi50s = (MonthlyTawi50s) o;
    return Objects.equals(this.companyName, monthlyTawi50s.companyName) &&
        Objects.equals(this.monthlyTawiId, monthlyTawi50s.monthlyTawiId) &&
        Objects.equals(this.tawiIncomeType, monthlyTawi50s.tawiIncomeType) &&
        Objects.equals(this.monthlyTaxi50Details, monthlyTawi50s.monthlyTaxi50Details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(companyName, monthlyTawiId, tawiIncomeType, monthlyTaxi50Details);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonthlyTawi50s {\n");
    
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    monthlyTawiId: ").append(toIndentedString(monthlyTawiId)).append("\n");
    sb.append("    tawiIncomeType: ").append(toIndentedString(tawiIncomeType)).append("\n");
    sb.append("    monthlyTaxi50Details: ").append(toIndentedString(monthlyTaxi50Details)).append("\n");
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

