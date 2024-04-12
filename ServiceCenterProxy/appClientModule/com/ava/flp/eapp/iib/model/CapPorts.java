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
 * CapPorts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CapPorts   {
  @JsonProperty("remainingCap")
  private BigDecimal remainingCap = null;

  @JsonProperty("capAmount")
  private BigDecimal capAmount = null;

  @JsonProperty("grantedAmount")
  private BigDecimal grantedAmount = null;

  @JsonProperty("warningPoint")
  private BigDecimal warningPoint = null;

  @JsonProperty("declinePoint")
  private BigDecimal declinePoint = null;

  @JsonProperty("capportId")
  private String capportId = null;

  @JsonProperty("capApplication")
  private BigDecimal capApplication = null;

  @JsonProperty("grantedApplication")
  private String grantedApplication = null;

  @JsonProperty("declinePercentageAmount")
  private BigDecimal declinePercentageAmount = null;

  @JsonProperty("declinePercentageApplication")
  private BigDecimal declinePercentageApplication = null;

  public CapPorts remainingCap(BigDecimal remainingCap) {
    this.remainingCap = remainingCap;
    return this;
  }

   /**
   * Get remainingCap
   * @return remainingCap
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRemainingCap() {
    return remainingCap;
  }

  public void setRemainingCap(BigDecimal remainingCap) {
    this.remainingCap = remainingCap;
  }

  public CapPorts capAmount(BigDecimal capAmount) {
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

  public CapPorts grantedAmount(BigDecimal grantedAmount) {
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

  public CapPorts warningPoint(BigDecimal warningPoint) {
    this.warningPoint = warningPoint;
    return this;
  }

   /**
   * Get warningPoint
   * @return warningPoint
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getWarningPoint() {
    return warningPoint;
  }

  public void setWarningPoint(BigDecimal warningPoint) {
    this.warningPoint = warningPoint;
  }

  public CapPorts declinePoint(BigDecimal declinePoint) {
    this.declinePoint = declinePoint;
    return this;
  }

   /**
   * Get declinePoint
   * @return declinePoint
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDeclinePoint() {
    return declinePoint;
  }

  public void setDeclinePoint(BigDecimal declinePoint) {
    this.declinePoint = declinePoint;
  }

  public CapPorts capportId(String capportId) {
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

  public CapPorts capApplication(BigDecimal capApplication) {
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

  public CapPorts grantedApplication(String grantedApplication) {
    this.grantedApplication = grantedApplication;
    return this;
  }

   /**
   * Get grantedApplication
   * @return grantedApplication
  **/
  @ApiModelProperty(value = "")


  public String getGrantedApplication() {
    return grantedApplication;
  }

  public void setGrantedApplication(String grantedApplication) {
    this.grantedApplication = grantedApplication;
  }

  public CapPorts declinePercentageAmount(BigDecimal declinePercentageAmount) {
    this.declinePercentageAmount = declinePercentageAmount;
    return this;
  }

   /**
   * Get declinePercentageAmount
   * @return declinePercentageAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDeclinePercentageAmount() {
    return declinePercentageAmount;
  }

  public void setDeclinePercentageAmount(BigDecimal declinePercentageAmount) {
    this.declinePercentageAmount = declinePercentageAmount;
  }

  public CapPorts declinePercentageApplication(BigDecimal declinePercentageApplication) {
    this.declinePercentageApplication = declinePercentageApplication;
    return this;
  }

   /**
   * Get declinePercentageApplication
   * @return declinePercentageApplication
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDeclinePercentageApplication() {
    return declinePercentageApplication;
  }

  public void setDeclinePercentageApplication(BigDecimal declinePercentageApplication) {
    this.declinePercentageApplication = declinePercentageApplication;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CapPorts capPorts = (CapPorts) o;
    return Objects.equals(this.remainingCap, capPorts.remainingCap) &&
        Objects.equals(this.capAmount, capPorts.capAmount) &&
        Objects.equals(this.grantedAmount, capPorts.grantedAmount) &&
        Objects.equals(this.warningPoint, capPorts.warningPoint) &&
        Objects.equals(this.declinePoint, capPorts.declinePoint) &&
        Objects.equals(this.capportId, capPorts.capportId) &&
        Objects.equals(this.capApplication, capPorts.capApplication) &&
        Objects.equals(this.grantedApplication, capPorts.grantedApplication) &&
        Objects.equals(this.declinePercentageAmount, capPorts.declinePercentageAmount) &&
        Objects.equals(this.declinePercentageApplication, capPorts.declinePercentageApplication);
  }

  @Override
  public int hashCode() {
    return Objects.hash(remainingCap, capAmount, grantedAmount, warningPoint, declinePoint, capportId, capApplication, grantedApplication, declinePercentageAmount, declinePercentageApplication);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CapPorts {\n");
    
    sb.append("    remainingCap: ").append(toIndentedString(remainingCap)).append("\n");
    sb.append("    capAmount: ").append(toIndentedString(capAmount)).append("\n");
    sb.append("    grantedAmount: ").append(toIndentedString(grantedAmount)).append("\n");
    sb.append("    warningPoint: ").append(toIndentedString(warningPoint)).append("\n");
    sb.append("    declinePoint: ").append(toIndentedString(declinePoint)).append("\n");
    sb.append("    capportId: ").append(toIndentedString(capportId)).append("\n");
    sb.append("    capApplication: ").append(toIndentedString(capApplication)).append("\n");
    sb.append("    grantedApplication: ").append(toIndentedString(grantedApplication)).append("\n");
    sb.append("    declinePercentageAmount: ").append(toIndentedString(declinePercentageAmount)).append("\n");
    sb.append("    declinePercentageApplication: ").append(toIndentedString(declinePercentageApplication)).append("\n");
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

