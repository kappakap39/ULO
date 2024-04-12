package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * IncomeSourcesWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class IncomeSourcesWF   {
  @JsonProperty("incomeSouce")
  private String incomeSouce = null;

  public IncomeSourcesWF incomeSouce(String incomeSouce) {
    this.incomeSouce = incomeSouce;
    return this;
  }

   /**
   * Get incomeSouce
   * @return incomeSouce
  **/
  @ApiModelProperty(value = "")


  public String getIncomeSouce() {
    return incomeSouce;
  }

  public void setIncomeSouce(String incomeSouce) {
    this.incomeSouce = incomeSouce;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncomeSourcesWF incomeSourcesWF = (IncomeSourcesWF) o;
    return Objects.equals(this.incomeSouce, incomeSourcesWF.incomeSouce);
  }

  @Override
  public int hashCode() {
    return Objects.hash(incomeSouce);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncomeSourcesWF {\n");
    
    sb.append("    incomeSouce: ").append(toIndentedString(incomeSouce)).append("\n");
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

