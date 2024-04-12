package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * IncomeSources
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class IncomeSources   {
  @JsonProperty("incomeSource")
  private String incomeSource = null;

  public IncomeSources incomeSource(String incomeSource) {
    this.incomeSource = incomeSource;
    return this;
  }

   /**
   * Get incomeSource
   * @return incomeSource
  **/
  @ApiModelProperty(value = "")


  public String getIncomeSource() {
    return incomeSource;
  }

  public void setIncomeSource(String incomeSource) {
    this.incomeSource = incomeSource;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncomeSources incomeSources = (IncomeSources) o;
    return Objects.equals(this.incomeSource, incomeSources.incomeSource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(incomeSource);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncomeSources {\n");
    
    sb.append("    incomeSource: ").append(toIndentedString(incomeSource)).append("\n");
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

