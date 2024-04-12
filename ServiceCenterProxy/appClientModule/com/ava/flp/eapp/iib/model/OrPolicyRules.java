package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.OrPolicyRulesDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OrPolicyRules
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class OrPolicyRules   {
  @JsonProperty("minApprovalAuth")
  private String minApprovalAuth = null;

  @JsonProperty("overideCode")
  private String overideCode = null;

  @JsonProperty("overideResult")
  private String overideResult = null;

  @JsonProperty("orPolicyRulesDetails")
  private List<OrPolicyRulesDetails> orPolicyRulesDetails = null;

  public OrPolicyRules minApprovalAuth(String minApprovalAuth) {
    this.minApprovalAuth = minApprovalAuth;
    return this;
  }

   /**
   * Get minApprovalAuth
   * @return minApprovalAuth
  **/
  @ApiModelProperty(value = "")


  public String getMinApprovalAuth() {
    return minApprovalAuth;
  }

  public void setMinApprovalAuth(String minApprovalAuth) {
    this.minApprovalAuth = minApprovalAuth;
  }

  public OrPolicyRules overideCode(String overideCode) {
    this.overideCode = overideCode;
    return this;
  }

   /**
   * Get overideCode
   * @return overideCode
  **/
  @ApiModelProperty(value = "")


  public String getOverideCode() {
    return overideCode;
  }

  public void setOverideCode(String overideCode) {
    this.overideCode = overideCode;
  }

  public OrPolicyRules overideResult(String overideResult) {
    this.overideResult = overideResult;
    return this;
  }

   /**
   * Get overideResult
   * @return overideResult
  **/
  @ApiModelProperty(value = "")


  public String getOverideResult() {
    return overideResult;
  }

  public void setOverideResult(String overideResult) {
    this.overideResult = overideResult;
  }

  public OrPolicyRules orPolicyRulesDetails(List<OrPolicyRulesDetails> orPolicyRulesDetails) {
    this.orPolicyRulesDetails = orPolicyRulesDetails;
    return this;
  }

  public OrPolicyRules addOrPolicyRulesDetailsItem(OrPolicyRulesDetails orPolicyRulesDetailsItem) {
    if (this.orPolicyRulesDetails == null) {
      this.orPolicyRulesDetails = new ArrayList<OrPolicyRulesDetails>();
    }
    this.orPolicyRulesDetails.add(orPolicyRulesDetailsItem);
    return this;
  }

   /**
   * Get orPolicyRulesDetails
   * @return orPolicyRulesDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<OrPolicyRulesDetails> getOrPolicyRulesDetails() {
    return orPolicyRulesDetails;
  }

  public void setOrPolicyRulesDetails(List<OrPolicyRulesDetails> orPolicyRulesDetails) {
    this.orPolicyRulesDetails = orPolicyRulesDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrPolicyRules orPolicyRules = (OrPolicyRules) o;
    return Objects.equals(this.minApprovalAuth, orPolicyRules.minApprovalAuth) &&
        Objects.equals(this.overideCode, orPolicyRules.overideCode) &&
        Objects.equals(this.overideResult, orPolicyRules.overideResult) &&
        Objects.equals(this.orPolicyRulesDetails, orPolicyRules.orPolicyRulesDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(minApprovalAuth, overideCode, overideResult, orPolicyRulesDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrPolicyRules {\n");
    
    sb.append("    minApprovalAuth: ").append(toIndentedString(minApprovalAuth)).append("\n");
    sb.append("    overideCode: ").append(toIndentedString(overideCode)).append("\n");
    sb.append("    overideResult: ").append(toIndentedString(overideResult)).append("\n");
    sb.append("    orPolicyRulesDetails: ").append(toIndentedString(orPolicyRulesDetails)).append("\n");
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

