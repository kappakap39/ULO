package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KBank1550i01Response
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KBank1550i01Response   {
  @JsonProperty("bankruptcyCompanyStatus")
  private String bankruptcyCompanyStatus = null;

  @JsonProperty("amcCompanyStatus")
  private String amcCompanyStatus = null;

  @JsonProperty("tamcCompanyStatus")
  private String tamcCompanyStatus = null;

  public KBank1550i01Response bankruptcyCompanyStatus(String bankruptcyCompanyStatus) {
    this.bankruptcyCompanyStatus = bankruptcyCompanyStatus;
    return this;
  }

   /**
   * Get bankruptcyCompanyStatus
   * @return bankruptcyCompanyStatus
  **/
  @ApiModelProperty(value = "")


  public String getBankruptcyCompanyStatus() {
    return bankruptcyCompanyStatus;
  }

  public void setBankruptcyCompanyStatus(String bankruptcyCompanyStatus) {
    this.bankruptcyCompanyStatus = bankruptcyCompanyStatus;
  }

  public KBank1550i01Response amcCompanyStatus(String amcCompanyStatus) {
    this.amcCompanyStatus = amcCompanyStatus;
    return this;
  }

   /**
   * Get amcCompanyStatus
   * @return amcCompanyStatus
  **/
  @ApiModelProperty(value = "")


  public String getAmcCompanyStatus() {
    return amcCompanyStatus;
  }

  public void setAmcCompanyStatus(String amcCompanyStatus) {
    this.amcCompanyStatus = amcCompanyStatus;
  }

  public KBank1550i01Response tamcCompanyStatus(String tamcCompanyStatus) {
    this.tamcCompanyStatus = tamcCompanyStatus;
    return this;
  }

   /**
   * Get tamcCompanyStatus
   * @return tamcCompanyStatus
  **/
  @ApiModelProperty(value = "")


  public String getTamcCompanyStatus() {
    return tamcCompanyStatus;
  }

  public void setTamcCompanyStatus(String tamcCompanyStatus) {
    this.tamcCompanyStatus = tamcCompanyStatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KBank1550i01Response kbank1550i01Response = (KBank1550i01Response) o;
    return Objects.equals(this.bankruptcyCompanyStatus, kbank1550i01Response.bankruptcyCompanyStatus) &&
        Objects.equals(this.amcCompanyStatus, kbank1550i01Response.amcCompanyStatus) &&
        Objects.equals(this.tamcCompanyStatus, kbank1550i01Response.tamcCompanyStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bankruptcyCompanyStatus, amcCompanyStatus, tamcCompanyStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KBank1550i01Response {\n");
    
    sb.append("    bankruptcyCompanyStatus: ").append(toIndentedString(bankruptcyCompanyStatus)).append("\n");
    sb.append("    amcCompanyStatus: ").append(toIndentedString(amcCompanyStatus)).append("\n");
    sb.append("    tamcCompanyStatus: ").append(toIndentedString(tamcCompanyStatus)).append("\n");
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

