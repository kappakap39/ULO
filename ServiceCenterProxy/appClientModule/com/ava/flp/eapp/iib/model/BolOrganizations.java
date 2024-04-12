package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BolOrganizations
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class BolOrganizations   {
  @JsonProperty("orgId")
  private String orgId = null;

  @JsonProperty("telNo")
  private String telNo = null;

  public BolOrganizations orgId(String orgId) {
    this.orgId = orgId;
    return this;
  }

   /**
   * Get orgId
   * @return orgId
  **/
  @ApiModelProperty(value = "")


  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public BolOrganizations telNo(String telNo) {
    this.telNo = telNo;
    return this;
  }

   /**
   * Get telNo
   * @return telNo
  **/
  @ApiModelProperty(value = "")


  public String getTelNo() {
    return telNo;
  }

  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BolOrganizations bolOrganizations = (BolOrganizations) o;
    return Objects.equals(this.orgId, bolOrganizations.orgId) &&
        Objects.equals(this.telNo, bolOrganizations.telNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orgId, telNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BolOrganizations {\n");
    
    sb.append("    orgId: ").append(toIndentedString(orgId)).append("\n");
    sb.append("    telNo: ").append(toIndentedString(telNo)).append("\n");
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

