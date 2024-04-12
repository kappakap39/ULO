package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NcbRuleResults
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbRuleResults   {
  @JsonProperty("ruleId")
  private String ruleId = null;

  @JsonProperty("ruleResult")
  private String ruleResult = null;

  @JsonProperty("ruleCondition")
  private String ruleCondition = null;

  public NcbRuleResults ruleId(String ruleId) {
    this.ruleId = ruleId;
    return this;
  }

   /**
   * Get ruleId
   * @return ruleId
  **/
  @ApiModelProperty(value = "")


  public String getRuleId() {
    return ruleId;
  }

  public void setRuleId(String ruleId) {
    this.ruleId = ruleId;
  }

  public NcbRuleResults ruleResult(String ruleResult) {
    this.ruleResult = ruleResult;
    return this;
  }

   /**
   * Get ruleResult
   * @return ruleResult
  **/
  @ApiModelProperty(value = "")


  public String getRuleResult() {
    return ruleResult;
  }

  public void setRuleResult(String ruleResult) {
    this.ruleResult = ruleResult;
  }

  public NcbRuleResults ruleCondition(String ruleCondition) {
    this.ruleCondition = ruleCondition;
    return this;
  }

   /**
   * Get ruleCondition
   * @return ruleCondition
  **/
  @ApiModelProperty(value = "")


  public String getRuleCondition() {
    return ruleCondition;
  }

  public void setRuleCondition(String ruleCondition) {
    this.ruleCondition = ruleCondition;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbRuleResults ncbRuleResults = (NcbRuleResults) o;
    return Objects.equals(this.ruleId, ncbRuleResults.ruleId) &&
        Objects.equals(this.ruleResult, ncbRuleResults.ruleResult) &&
        Objects.equals(this.ruleCondition, ncbRuleResults.ruleCondition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruleId, ruleResult, ruleCondition);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbRuleResults {\n");
    
    sb.append("    ruleId: ").append(toIndentedString(ruleId)).append("\n");
    sb.append("    ruleResult: ").append(toIndentedString(ruleResult)).append("\n");
    sb.append("    ruleCondition: ").append(toIndentedString(ruleCondition)).append("\n");
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

