package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.PolicyRules;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VerificationResultApplications
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class VerificationResultApplications   {
  @JsonProperty("policyrules")
  private List<PolicyRules> policyrules = null;

  public VerificationResultApplications policyrules(List<PolicyRules> policyrules) {
    this.policyrules = policyrules;
    return this;
  }

  public VerificationResultApplications addPolicyrulesItem(PolicyRules policyrulesItem) {
    if (this.policyrules == null) {
      this.policyrules = new ArrayList<PolicyRules>();
    }
    this.policyrules.add(policyrulesItem);
    return this;
  }

   /**
   * Get policyrules
   * @return policyrules
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PolicyRules> getPolicyrules() {
    return policyrules;
  }

  public void setPolicyrules(List<PolicyRules> policyrules) {
    this.policyrules = policyrules;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VerificationResultApplications verificationResultApplications = (VerificationResultApplications) o;
    return Objects.equals(this.policyrules, verificationResultApplications.policyrules);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policyrules);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VerificationResultApplications {\n");
    
    sb.append("    policyrules: ").append(toIndentedString(policyrules)).append("\n");
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

