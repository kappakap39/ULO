package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.ava.flp.eapp.submitapplication.model.ApplicationGroup;
import com.ava.flp.eapp.submitapplication.model.KBankHeader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SubmitApplication
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class SubmitApplication   {
  @JsonProperty("KBankHeader")
  private KBankHeader kbankHeader = null;

  @JsonIgnore
  private ApplicationGroup applicationGroup = null;

  public SubmitApplication kbankHeader(KBankHeader kbankHeader) {
    this.kbankHeader = kbankHeader;
    return this;
  }

   /**
   * Get kbankHeader
   * @return kbankHeader
  **/
  @ApiModelProperty(value = "")

  @Valid

  public KBankHeader getKbankHeader() {
    return kbankHeader;
  }

  public void setKbankHeader(KBankHeader kbankHeader) {
    this.kbankHeader = kbankHeader;
  }

  public SubmitApplication applicationGroup(ApplicationGroup applicationGroup) {
    this.applicationGroup = applicationGroup;
    return this;
  }

   /**
   * Get applicationGroup
   * @return applicationGroup
  **/
  @ApiModelProperty(value = "")

  @Valid
  @JsonIgnore
  public ApplicationGroup getApplicationGroup() {
    return applicationGroup;
  }

  @JsonProperty("ApplicationGroup")
  public void setApplicationGroup(ApplicationGroup applicationGroup) {
    this.applicationGroup = applicationGroup;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubmitApplication submitApplication = (SubmitApplication) o;
    return Objects.equals(this.kbankHeader, submitApplication.kbankHeader) &&
        Objects.equals(this.applicationGroup, submitApplication.applicationGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kbankHeader, applicationGroup);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubmitApplication {\n");
    
    sb.append("    kbankHeader: ").append(toIndentedString(kbankHeader)).append("\n");
    sb.append("    applicationGroup: ").append(toIndentedString(applicationGroup)).append("\n");
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

