package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * IncomeScreens
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class IncomeScreens   {
  @JsonProperty("incomeScreenType")
  private String incomeScreenType = null;

  public IncomeScreens incomeScreenType(String incomeScreenType) {
    this.incomeScreenType = incomeScreenType;
    return this;
  }

   /**
   * Get incomeScreenType
   * @return incomeScreenType
  **/
  @ApiModelProperty(value = "")


  public String getIncomeScreenType() {
    return incomeScreenType;
  }

  public void setIncomeScreenType(String incomeScreenType) {
    this.incomeScreenType = incomeScreenType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncomeScreens incomeScreens = (IncomeScreens) o;
    return Objects.equals(this.incomeScreenType, incomeScreens.incomeScreenType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(incomeScreenType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncomeScreens {\n");
    
    sb.append("    incomeScreenType: ").append(toIndentedString(incomeScreenType)).append("\n");
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

