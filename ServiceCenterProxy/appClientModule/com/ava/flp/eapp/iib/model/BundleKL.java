package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BundleKL
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class BundleKL   {
  @JsonProperty("applicationRecordId")
  private String applicationRecordId = null;

  @JsonProperty("approveCreditLine")
  private String approveCreditLine = null;

  @JsonProperty("estimatedIncome")
  private BigDecimal estimatedIncome = null;

  @JsonProperty("verifiedDate")
  private DateTime verifiedDate = null;

  @JsonProperty("verifiedIncome")
  private BigDecimal verifiedIncome = null;

  public BundleKL applicationRecordId(String applicationRecordId) {
    this.applicationRecordId = applicationRecordId;
    return this;
  }

   /**
   * Get applicationRecordId
   * @return applicationRecordId
  **/
  @ApiModelProperty(value = "")


  public String getApplicationRecordId() {
    return applicationRecordId;
  }

  public void setApplicationRecordId(String applicationRecordId) {
    this.applicationRecordId = applicationRecordId;
  }

  public BundleKL approveCreditLine(String approveCreditLine) {
    this.approveCreditLine = approveCreditLine;
    return this;
  }

   /**
   * Get approveCreditLine
   * @return approveCreditLine
  **/
  @ApiModelProperty(value = "")


  public String getApproveCreditLine() {
    return approveCreditLine;
  }

  public void setApproveCreditLine(String approveCreditLine) {
    this.approveCreditLine = approveCreditLine;
  }

  public BundleKL estimatedIncome(BigDecimal estimatedIncome) {
    this.estimatedIncome = estimatedIncome;
    return this;
  }

   /**
   * Get estimatedIncome
   * @return estimatedIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getEstimatedIncome() {
    return estimatedIncome;
  }

  public void setEstimatedIncome(BigDecimal estimatedIncome) {
    this.estimatedIncome = estimatedIncome;
  }

  public BundleKL verifiedDate(DateTime verifiedDate) {
    this.verifiedDate = verifiedDate;
    return this;
  }

   /**
   * Get verifiedDate
   * @return verifiedDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getVerifiedDate() {
    return verifiedDate;
  }

  public void setVerifiedDate(DateTime verifiedDate) {
    this.verifiedDate = verifiedDate;
  }

  public BundleKL verifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
    return this;
  }

   /**
   * Get verifiedIncome
   * @return verifiedIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getVerifiedIncome() {
    return verifiedIncome;
  }

  public void setVerifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleKL bundleKL = (BundleKL) o;
    return Objects.equals(this.applicationRecordId, bundleKL.applicationRecordId) &&
        Objects.equals(this.approveCreditLine, bundleKL.approveCreditLine) &&
        Objects.equals(this.estimatedIncome, bundleKL.estimatedIncome) &&
        Objects.equals(this.verifiedDate, bundleKL.verifiedDate) &&
        Objects.equals(this.verifiedIncome, bundleKL.verifiedIncome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationRecordId, approveCreditLine, estimatedIncome, verifiedDate, verifiedIncome);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleKL {\n");
    
    sb.append("    applicationRecordId: ").append(toIndentedString(applicationRecordId)).append("\n");
    sb.append("    approveCreditLine: ").append(toIndentedString(approveCreditLine)).append("\n");
    sb.append("    estimatedIncome: ").append(toIndentedString(estimatedIncome)).append("\n");
    sb.append("    verifiedDate: ").append(toIndentedString(verifiedDate)).append("\n");
    sb.append("    verifiedIncome: ").append(toIndentedString(verifiedIncome)).append("\n");
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

