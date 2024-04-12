package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.CreditSumryObj;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LPMCustomer
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class LPMCustomer   {
  @JsonProperty("classCode")
  private String classCode = null;

  @JsonProperty("creditSumryObj")
  private CreditSumryObj creditSumryObj = null;

  public LPMCustomer classCode(String classCode) {
    this.classCode = classCode;
    return this;
  }

   /**
   * Get classCode
   * @return classCode
  **/
  @ApiModelProperty(value = "")


  public String getClassCode() {
    return classCode;
  }

  public void setClassCode(String classCode) {
    this.classCode = classCode;
  }

  public LPMCustomer creditSumryObj(CreditSumryObj creditSumryObj) {
    this.creditSumryObj = creditSumryObj;
    return this;
  }

   /**
   * Get creditSumryObj
   * @return creditSumryObj
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CreditSumryObj getCreditSumryObj() {
    return creditSumryObj;
  }

  public void setCreditSumryObj(CreditSumryObj creditSumryObj) {
    this.creditSumryObj = creditSumryObj;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LPMCustomer lpMCustomer = (LPMCustomer) o;
    return Objects.equals(this.classCode, lpMCustomer.classCode) &&
        Objects.equals(this.creditSumryObj, lpMCustomer.creditSumryObj);
  }

  @Override
  public int hashCode() {
    return Objects.hash(classCode, creditSumryObj);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LPMCustomer {\n");
    
    sb.append("    classCode: ").append(toIndentedString(classCode)).append("\n");
    sb.append("    creditSumryObj: ").append(toIndentedString(creditSumryObj)).append("\n");
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

