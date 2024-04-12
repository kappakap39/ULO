package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Capport
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Capport   {
  @JsonProperty("applicationCapportId")
  private String applicationCapportId = null;

  @JsonProperty("amountCapportId")
  private String amountCapportId = null;

  @JsonProperty("capportId")
  private String capportId = null;

  @JsonProperty("capAmount")
  private BigDecimal capAmount = null;

  @JsonProperty("grantedAmount")
  private BigDecimal grantedAmount = null;

  @JsonProperty("remainingAmount")
  private BigDecimal remainingAmount = null;

  @JsonProperty("capApplication")
  private BigDecimal capApplication = null;

  @JsonProperty("grantedApplication")
  private BigDecimal grantedApplication = null;

  @JsonProperty("remainingApplication")
  private BigDecimal remainingApplication = null;

  public Capport applicationCapportId(String applicationCapportId) {
    this.applicationCapportId = applicationCapportId;
    return this;
  }

   /**
   * Get applicationCapportId
   * @return applicationCapportId
  **/
  @ApiModelProperty(value = "")


  public String getApplicationCapportId() {
    return applicationCapportId;
  }

  public void setApplicationCapportId(String applicationCapportId) {
    this.applicationCapportId = applicationCapportId;
  }

  public Capport amountCapportId(String amountCapportId) {
    this.amountCapportId = amountCapportId;
    return this;
  }

   /**
   * Get amountCapportId
   * @return amountCapportId
  **/
  @ApiModelProperty(value = "")


  public String getAmountCapportId() {
    return amountCapportId;
  }

  public void setAmountCapportId(String amountCapportId) {
    this.amountCapportId = amountCapportId;
  }

  public Capport capportId(String capportId) {
    this.capportId = capportId;
    return this;
  }

   /**
   * Get capportId
   * @return capportId
  **/
  @ApiModelProperty(value = "")


  public String getCapportId() {
    return capportId;
  }

  public void setCapportId(String capportId) {
    this.capportId = capportId;
  }

  public Capport capAmount(BigDecimal capAmount) {
    this.capAmount = capAmount;
    return this;
  }

   /**
   * Get capAmount
   * @return capAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCapAmount() {
    return capAmount;
  }

  public void setCapAmount(BigDecimal capAmount) {
    this.capAmount = capAmount;
  }

  public Capport grantedAmount(BigDecimal grantedAmount) {
    this.grantedAmount = grantedAmount;
    return this;
  }

   /**
   * Get grantedAmount
   * @return grantedAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getGrantedAmount() {
    return grantedAmount;
  }

  public void setGrantedAmount(BigDecimal grantedAmount) {
    this.grantedAmount = grantedAmount;
  }

  public Capport remainingAmount(BigDecimal remainingAmount) {
    this.remainingAmount = remainingAmount;
    return this;
  }

   /**
   * Get remainingAmount
   * @return remainingAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRemainingAmount() {
    return remainingAmount;
  }

  public void setRemainingAmount(BigDecimal remainingAmount) {
    this.remainingAmount = remainingAmount;
  }

  public Capport capApplication(BigDecimal capApplication) {
    this.capApplication = capApplication;
    return this;
  }

   /**
   * Get capApplication
   * @return capApplication
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCapApplication() {
    return capApplication;
  }

  public void setCapApplication(BigDecimal capApplication) {
    this.capApplication = capApplication;
  }

  public Capport grantedApplication(BigDecimal grantedApplication) {
    this.grantedApplication = grantedApplication;
    return this;
  }

   /**
   * Get grantedApplication
   * @return grantedApplication
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getGrantedApplication() {
    return grantedApplication;
  }

  public void setGrantedApplication(BigDecimal grantedApplication) {
    this.grantedApplication = grantedApplication;
  }

  public Capport remainingApplication(BigDecimal remainingApplication) {
    this.remainingApplication = remainingApplication;
    return this;
  }

   /**
   * Get remainingApplication
   * @return remainingApplication
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRemainingApplication() {
    return remainingApplication;
  }

  public void setRemainingApplication(BigDecimal remainingApplication) {
    this.remainingApplication = remainingApplication;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Capport capport = (Capport) o;
    return Objects.equals(this.applicationCapportId, capport.applicationCapportId) &&
        Objects.equals(this.amountCapportId, capport.amountCapportId) &&
        Objects.equals(this.capportId, capport.capportId) &&
        Objects.equals(this.capAmount, capport.capAmount) &&
        Objects.equals(this.grantedAmount, capport.grantedAmount) &&
        Objects.equals(this.remainingAmount, capport.remainingAmount) &&
        Objects.equals(this.capApplication, capport.capApplication) &&
        Objects.equals(this.grantedApplication, capport.grantedApplication) &&
        Objects.equals(this.remainingApplication, capport.remainingApplication);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationCapportId, amountCapportId, capportId, capAmount, grantedAmount, remainingAmount, capApplication, grantedApplication, remainingApplication);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Capport {\n");
    
    sb.append("    applicationCapportId: ").append(toIndentedString(applicationCapportId)).append("\n");
    sb.append("    amountCapportId: ").append(toIndentedString(amountCapportId)).append("\n");
    sb.append("    capportId: ").append(toIndentedString(capportId)).append("\n");
    sb.append("    capAmount: ").append(toIndentedString(capAmount)).append("\n");
    sb.append("    grantedAmount: ").append(toIndentedString(grantedAmount)).append("\n");
    sb.append("    remainingAmount: ").append(toIndentedString(remainingAmount)).append("\n");
    sb.append("    capApplication: ").append(toIndentedString(capApplication)).append("\n");
    sb.append("    grantedApplication: ").append(toIndentedString(grantedApplication)).append("\n");
    sb.append("    remainingApplication: ").append(toIndentedString(remainingApplication)).append("\n");
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

