package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.OrPolicyRules;
import com.ava.flp.eapp.iib.model.PolicyRuleConditions;
import com.ava.flp.eapp.iib.model.PolicyRuleReasons;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PolicyRules
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PolicyRules   {
  @JsonProperty("policyCode")
  private String policyCode = null;

  @JsonProperty("rank")
  private String rank = null;

  @JsonProperty("result")
  private String result = null;

  @JsonProperty("policyRuleReasons")
  private List<PolicyRuleReasons> policyRuleReasons = null;

  @JsonProperty("policyRuleConditions")
  private List<PolicyRuleConditions> policyRuleConditions = null;

  @JsonProperty("orPolicyRules")
  private List<OrPolicyRules> orPolicyRules = null;

  public PolicyRules policyCode(String policyCode) {
    this.policyCode = policyCode;
    return this;
  }

   /**
   * Get policyCode
   * @return policyCode
  **/
  @ApiModelProperty(value = "")


  public String getPolicyCode() {
    return policyCode;
  }

  public void setPolicyCode(String policyCode) {
    this.policyCode = policyCode;
  }

  public PolicyRules rank(String rank) {
    this.rank = rank;
    return this;
  }

   /**
   * Get rank
   * @return rank
  **/
  @ApiModelProperty(value = "")


  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  public PolicyRules result(String result) {
    this.result = result;
    return this;
  }

   /**
   * Get result
   * @return result
  **/
  @ApiModelProperty(value = "")


  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public PolicyRules policyRuleReasons(List<PolicyRuleReasons> policyRuleReasons) {
    this.policyRuleReasons = policyRuleReasons;
    return this;
  }

  public PolicyRules addPolicyRuleReasonsItem(PolicyRuleReasons policyRuleReasonsItem) {
    if (this.policyRuleReasons == null) {
      this.policyRuleReasons = new ArrayList<PolicyRuleReasons>();
    }
    this.policyRuleReasons.add(policyRuleReasonsItem);
    return this;
  }

   /**
   * Get policyRuleReasons
   * @return policyRuleReasons
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PolicyRuleReasons> getPolicyRuleReasons() {
    return policyRuleReasons;
  }

  public void setPolicyRuleReasons(List<PolicyRuleReasons> policyRuleReasons) {
    this.policyRuleReasons = policyRuleReasons;
  }

  public PolicyRules policyRuleConditions(List<PolicyRuleConditions> policyRuleConditions) {
    this.policyRuleConditions = policyRuleConditions;
    return this;
  }

  public PolicyRules addPolicyRuleConditionsItem(PolicyRuleConditions policyRuleConditionsItem) {
    if (this.policyRuleConditions == null) {
      this.policyRuleConditions = new ArrayList<PolicyRuleConditions>();
    }
    this.policyRuleConditions.add(policyRuleConditionsItem);
    return this;
  }

   /**
   * Get policyRuleConditions
   * @return policyRuleConditions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PolicyRuleConditions> getPolicyRuleConditions() {
    return policyRuleConditions;
  }

  public void setPolicyRuleConditions(List<PolicyRuleConditions> policyRuleConditions) {
    this.policyRuleConditions = policyRuleConditions;
  }

  public PolicyRules orPolicyRules(List<OrPolicyRules> orPolicyRules) {
    this.orPolicyRules = orPolicyRules;
    return this;
  }

  public PolicyRules addOrPolicyRulesItem(OrPolicyRules orPolicyRulesItem) {
    if (this.orPolicyRules == null) {
      this.orPolicyRules = new ArrayList<OrPolicyRules>();
    }
    this.orPolicyRules.add(orPolicyRulesItem);
    return this;
  }

   /**
   * Get orPolicyRules
   * @return orPolicyRules
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<OrPolicyRules> getOrPolicyRules() {
    return orPolicyRules;
  }

  public void setOrPolicyRules(List<OrPolicyRules> orPolicyRules) {
    this.orPolicyRules = orPolicyRules;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolicyRules policyRules = (PolicyRules) o;
    return Objects.equals(this.policyCode, policyRules.policyCode) &&
        Objects.equals(this.rank, policyRules.rank) &&
        Objects.equals(this.result, policyRules.result) &&
        Objects.equals(this.policyRuleReasons, policyRules.policyRuleReasons) &&
        Objects.equals(this.policyRuleConditions, policyRules.policyRuleConditions) &&
        Objects.equals(this.orPolicyRules, policyRules.orPolicyRules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policyCode, rank, result, policyRuleReasons, policyRuleConditions, orPolicyRules);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PolicyRules {\n");
    
    sb.append("    policyCode: ").append(toIndentedString(policyCode)).append("\n");
    sb.append("    rank: ").append(toIndentedString(rank)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    policyRuleReasons: ").append(toIndentedString(policyRuleReasons)).append("\n");
    sb.append("    policyRuleConditions: ").append(toIndentedString(policyRuleConditions)).append("\n");
    sb.append("    orPolicyRules: ").append(toIndentedString(orPolicyRules)).append("\n");
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

