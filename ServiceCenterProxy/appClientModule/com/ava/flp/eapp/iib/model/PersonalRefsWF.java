package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PersonalRefsWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PersonalRefsWF   {
  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("applicantType")
  private String applicantType = null;

  public PersonalRefsWF personalId(String personalId) {
    this.personalId = personalId;
    return this;
  }

   /**
   * Get personalId
   * @return personalId
  **/
  @ApiModelProperty(value = "")


  public String getPersonalId() {
    return personalId;
  }

  public void setPersonalId(String personalId) {
    this.personalId = personalId;
  }

  public PersonalRefsWF applicantType(String applicantType) {
    this.applicantType = applicantType;
    return this;
  }

   /**
   * Get applicantType
   * @return applicantType
  **/
  @ApiModelProperty(value = "")


  public String getApplicantType() {
    return applicantType;
  }

  public void setApplicantType(String applicantType) {
    this.applicantType = applicantType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalRefsWF personalRefsWF = (PersonalRefsWF) o;
    return Objects.equals(this.personalId, personalRefsWF.personalId) &&
        Objects.equals(this.applicantType, personalRefsWF.applicantType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personalId, applicantType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalRefsWF {\n");
    
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    applicantType: ").append(toIndentedString(applicantType)).append("\n");
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

