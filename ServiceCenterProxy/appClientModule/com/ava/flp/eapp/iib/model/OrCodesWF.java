package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrCodesWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class OrCodesWF   {
  @JsonProperty("orCode")
  private String orCode = null;

  public OrCodesWF orCode(String orCode) {
    this.orCode = orCode;
    return this;
  }

   /**
   * Get orCode
   * @return orCode
  **/
  @ApiModelProperty(value = "")


  public String getOrCode() {
    return orCode;
  }

  public void setOrCode(String orCode) {
    this.orCode = orCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrCodesWF orCodesWF = (OrCodesWF) o;
    return Objects.equals(this.orCode, orCodesWF.orCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrCodesWF {\n");
    
    sb.append("    orCode: ").append(toIndentedString(orCode)).append("\n");
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

