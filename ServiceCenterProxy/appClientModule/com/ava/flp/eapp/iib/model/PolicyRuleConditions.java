package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PolicyRuleConditions
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PolicyRuleConditions   {
  @JsonProperty("conditionCode")
  private String conditionCode = null;

  public PolicyRuleConditions conditionCode(String conditionCode) {
    this.conditionCode = conditionCode;
    return this;
  }

   /**
   * Get conditionCode
   * @return conditionCode
  **/
  @ApiModelProperty(value = "")


  public String getConditionCode() {
    return conditionCode;
  }

  public void setConditionCode(String conditionCode) {
    this.conditionCode = conditionCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolicyRuleConditions policyRuleConditions = (PolicyRuleConditions) o;
    return Objects.equals(this.conditionCode, policyRuleConditions.conditionCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conditionCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PolicyRuleConditions {\n");
    
    sb.append("    conditionCode: ").append(toIndentedString(conditionCode)).append("\n");
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

