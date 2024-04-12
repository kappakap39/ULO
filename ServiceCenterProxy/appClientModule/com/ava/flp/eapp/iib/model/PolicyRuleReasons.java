package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PolicyRuleReasons
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PolicyRuleReasons   {
  @JsonProperty("reasonCode")
  private String reasonCode = null;

  @JsonProperty("reasonRank")
  private String reasonRank = null;

  public PolicyRuleReasons reasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
    return this;
  }

   /**
   * Get reasonCode
   * @return reasonCode
  **/
  @ApiModelProperty(value = "")


  public String getReasonCode() {
    return reasonCode;
  }

  public void setReasonCode(String reasonCode) {
    this.reasonCode = reasonCode;
  }

  public PolicyRuleReasons reasonRank(String reasonRank) {
    this.reasonRank = reasonRank;
    return this;
  }

   /**
   * Get reasonRank
   * @return reasonRank
  **/
  @ApiModelProperty(value = "")


  public String getReasonRank() {
    return reasonRank;
  }

  public void setReasonRank(String reasonRank) {
    this.reasonRank = reasonRank;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolicyRuleReasons policyRuleReasons = (PolicyRuleReasons) o;
    return Objects.equals(this.reasonCode, policyRuleReasons.reasonCode) &&
        Objects.equals(this.reasonRank, policyRuleReasons.reasonRank);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reasonCode, reasonRank);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PolicyRuleReasons {\n");
    
    sb.append("    reasonCode: ").append(toIndentedString(reasonCode)).append("\n");
    sb.append("    reasonRank: ").append(toIndentedString(reasonRank)).append("\n");
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

