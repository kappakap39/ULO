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
 * NcbEnquirysTUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbEnquirysTUEF   {
  @JsonProperty("segmentTag")
  private String segmentTag = null;

  @JsonProperty("dateOfEnquiry")
  private DateTime dateOfEnquiry = null;

  @JsonProperty("timeOfEnquiry")
  private String timeOfEnquiry = null;

  @JsonProperty("memberCode")
  private String memberCode = null;

  @JsonProperty("memberShortName")
  private String memberShortName = null;

  @JsonProperty("enquiryPurpose")
  private String enquiryPurpose = null;

  @JsonProperty("enquiryAmount")
  private BigDecimal enquiryAmount = null;

  @JsonProperty("currencyCode")
  private String currencyCode = null;

  public NcbEnquirysTUEF segmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
    return this;
  }

   /**
   * Get segmentTag
   * @return segmentTag
  **/
  @ApiModelProperty(value = "")


  public String getSegmentTag() {
    return segmentTag;
  }

  public void setSegmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
  }

  public NcbEnquirysTUEF dateOfEnquiry(DateTime dateOfEnquiry) {
    this.dateOfEnquiry = dateOfEnquiry;
    return this;
  }

   /**
   * Get dateOfEnquiry
   * @return dateOfEnquiry
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateOfEnquiry() {
    return dateOfEnquiry;
  }

  public void setDateOfEnquiry(DateTime dateOfEnquiry) {
    this.dateOfEnquiry = dateOfEnquiry;
  }

  public NcbEnquirysTUEF timeOfEnquiry(String timeOfEnquiry) {
    this.timeOfEnquiry = timeOfEnquiry;
    return this;
  }

   /**
   * Get timeOfEnquiry
   * @return timeOfEnquiry
  **/
  @ApiModelProperty(value = "")


  public String getTimeOfEnquiry() {
    return timeOfEnquiry;
  }

  public void setTimeOfEnquiry(String timeOfEnquiry) {
    this.timeOfEnquiry = timeOfEnquiry;
  }

  public NcbEnquirysTUEF memberCode(String memberCode) {
    this.memberCode = memberCode;
    return this;
  }

   /**
   * Get memberCode
   * @return memberCode
  **/
  @ApiModelProperty(value = "")


  public String getMemberCode() {
    return memberCode;
  }

  public void setMemberCode(String memberCode) {
    this.memberCode = memberCode;
  }

  public NcbEnquirysTUEF memberShortName(String memberShortName) {
    this.memberShortName = memberShortName;
    return this;
  }

   /**
   * Get memberShortName
   * @return memberShortName
  **/
  @ApiModelProperty(value = "")


  public String getMemberShortName() {
    return memberShortName;
  }

  public void setMemberShortName(String memberShortName) {
    this.memberShortName = memberShortName;
  }

  public NcbEnquirysTUEF enquiryPurpose(String enquiryPurpose) {
    this.enquiryPurpose = enquiryPurpose;
    return this;
  }

   /**
   * Get enquiryPurpose
   * @return enquiryPurpose
  **/
  @ApiModelProperty(value = "")


  public String getEnquiryPurpose() {
    return enquiryPurpose;
  }

  public void setEnquiryPurpose(String enquiryPurpose) {
    this.enquiryPurpose = enquiryPurpose;
  }

  public NcbEnquirysTUEF enquiryAmount(BigDecimal enquiryAmount) {
    this.enquiryAmount = enquiryAmount;
    return this;
  }

   /**
   * Get enquiryAmount
   * @return enquiryAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getEnquiryAmount() {
    return enquiryAmount;
  }

  public void setEnquiryAmount(BigDecimal enquiryAmount) {
    this.enquiryAmount = enquiryAmount;
  }

  public NcbEnquirysTUEF currencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    return this;
  }

   /**
   * Get currencyCode
   * @return currencyCode
  **/
  @ApiModelProperty(value = "")


  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbEnquirysTUEF ncbEnquirysTUEF = (NcbEnquirysTUEF) o;
    return Objects.equals(this.segmentTag, ncbEnquirysTUEF.segmentTag) &&
        Objects.equals(this.dateOfEnquiry, ncbEnquirysTUEF.dateOfEnquiry) &&
        Objects.equals(this.timeOfEnquiry, ncbEnquirysTUEF.timeOfEnquiry) &&
        Objects.equals(this.memberCode, ncbEnquirysTUEF.memberCode) &&
        Objects.equals(this.memberShortName, ncbEnquirysTUEF.memberShortName) &&
        Objects.equals(this.enquiryPurpose, ncbEnquirysTUEF.enquiryPurpose) &&
        Objects.equals(this.enquiryAmount, ncbEnquirysTUEF.enquiryAmount) &&
        Objects.equals(this.currencyCode, ncbEnquirysTUEF.currencyCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentTag, dateOfEnquiry, timeOfEnquiry, memberCode, memberShortName, enquiryPurpose, enquiryAmount, currencyCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbEnquirysTUEF {\n");
    
    sb.append("    segmentTag: ").append(toIndentedString(segmentTag)).append("\n");
    sb.append("    dateOfEnquiry: ").append(toIndentedString(dateOfEnquiry)).append("\n");
    sb.append("    timeOfEnquiry: ").append(toIndentedString(timeOfEnquiry)).append("\n");
    sb.append("    memberCode: ").append(toIndentedString(memberCode)).append("\n");
    sb.append("    memberShortName: ").append(toIndentedString(memberShortName)).append("\n");
    sb.append("    enquiryPurpose: ").append(toIndentedString(enquiryPurpose)).append("\n");
    sb.append("    enquiryAmount: ").append(toIndentedString(enquiryAmount)).append("\n");
    sb.append("    currencyCode: ").append(toIndentedString(currencyCode)).append("\n");
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

