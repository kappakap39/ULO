package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.LPMCustomer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KBank1211i01Response
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KBank1211i01Response   {
  @JsonProperty("applicantBankruptcyStatus")
  private String applicantBankruptcyStatus = null;

  @JsonProperty("LPMCustomer")
  private LPMCustomer lpMCustomer = null;

  public KBank1211i01Response applicantBankruptcyStatus(String applicantBankruptcyStatus) {
    this.applicantBankruptcyStatus = applicantBankruptcyStatus;
    return this;
  }

   /**
   * Get applicantBankruptcyStatus
   * @return applicantBankruptcyStatus
  **/
  @ApiModelProperty(value = "")


  public String getApplicantBankruptcyStatus() {
    return applicantBankruptcyStatus;
  }

  public void setApplicantBankruptcyStatus(String applicantBankruptcyStatus) {
    this.applicantBankruptcyStatus = applicantBankruptcyStatus;
  }

  public KBank1211i01Response lpMCustomer(LPMCustomer lpMCustomer) {
    this.lpMCustomer = lpMCustomer;
    return this;
  }

   /**
   * Get lpMCustomer
   * @return lpMCustomer
  **/
  @ApiModelProperty(value = "")

  @Valid

  public LPMCustomer getLpMCustomer() {
    return lpMCustomer;
  }

  public void setLpMCustomer(LPMCustomer lpMCustomer) {
    this.lpMCustomer = lpMCustomer;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KBank1211i01Response kbank1211i01Response = (KBank1211i01Response) o;
    return Objects.equals(this.applicantBankruptcyStatus, kbank1211i01Response.applicantBankruptcyStatus) &&
        Objects.equals(this.lpMCustomer, kbank1211i01Response.lpMCustomer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicantBankruptcyStatus, lpMCustomer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KBank1211i01Response {\n");
    
    sb.append("    applicantBankruptcyStatus: ").append(toIndentedString(applicantBankruptcyStatus)).append("\n");
    sb.append("    lpMCustomer: ").append(toIndentedString(lpMCustomer)).append("\n");
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

