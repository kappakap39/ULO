package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.TUEF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ResultKCBS
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class ResultKCBS   {
  @JsonProperty("TUEF")
  private TUEF TUEF = null;

  public ResultKCBS TUEF(TUEF TUEF) {
    this.TUEF = TUEF;
    return this;
  }

   /**
   * Get TUEF
   * @return TUEF
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TUEF getTUEF() {
    return TUEF;
  }

  public void setTUEF(TUEF TUEF) {
    this.TUEF = TUEF;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultKCBS resultKCBS = (ResultKCBS) o;
    return Objects.equals(this.TUEF, resultKCBS.TUEF);
  }

  @Override
  public int hashCode() {
    return Objects.hash(TUEF);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultKCBS {\n");
    
    sb.append("    TUEF: ").append(toIndentedString(TUEF)).append("\n");
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

