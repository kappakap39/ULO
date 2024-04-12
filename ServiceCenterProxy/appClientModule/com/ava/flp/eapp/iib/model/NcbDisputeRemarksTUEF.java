package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NcbDisputeRemarksTUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbDisputeRemarksTUEF   {
  @JsonProperty("segmentTag")
  private String segmentTag = null;

  @JsonProperty("referenceSegmentType")
  private String referenceSegmentType = null;

  @JsonProperty("referenceSegment")
  private String referenceSegment = null;

  @JsonProperty("disputeCode")
  private String disputeCode = null;

  @JsonProperty("disputeDate")
  private DateTime disputeDate = null;

  @JsonProperty("disputeTime")
  private String disputeTime = null;

  @JsonProperty("disputeRemark1")
  private String disputeRemark1 = null;

  @JsonProperty("disputeRemark2")
  private String disputeRemark2 = null;

  @JsonProperty("disputeRemark3")
  private String disputeRemark3 = null;

  @JsonProperty("disputeRemark4")
  private String disputeRemark4 = null;

  @JsonProperty("dispute")
  private String dispute = null;

  @JsonProperty("remarks5")
  private String remarks5 = null;

  @JsonProperty("disputeRemark6")
  private String disputeRemark6 = null;

  public NcbDisputeRemarksTUEF segmentTag(String segmentTag) {
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

  public NcbDisputeRemarksTUEF referenceSegmentType(String referenceSegmentType) {
    this.referenceSegmentType = referenceSegmentType;
    return this;
  }

   /**
   * Get referenceSegmentType
   * @return referenceSegmentType
  **/
  @ApiModelProperty(value = "")


  public String getReferenceSegmentType() {
    return referenceSegmentType;
  }

  public void setReferenceSegmentType(String referenceSegmentType) {
    this.referenceSegmentType = referenceSegmentType;
  }

  public NcbDisputeRemarksTUEF referenceSegment(String referenceSegment) {
    this.referenceSegment = referenceSegment;
    return this;
  }

   /**
   * Get referenceSegment
   * @return referenceSegment
  **/
  @ApiModelProperty(value = "")


  public String getReferenceSegment() {
    return referenceSegment;
  }

  public void setReferenceSegment(String referenceSegment) {
    this.referenceSegment = referenceSegment;
  }

  public NcbDisputeRemarksTUEF disputeCode(String disputeCode) {
    this.disputeCode = disputeCode;
    return this;
  }

   /**
   * Get disputeCode
   * @return disputeCode
  **/
  @ApiModelProperty(value = "")


  public String getDisputeCode() {
    return disputeCode;
  }

  public void setDisputeCode(String disputeCode) {
    this.disputeCode = disputeCode;
  }

  public NcbDisputeRemarksTUEF disputeDate(DateTime disputeDate) {
    this.disputeDate = disputeDate;
    return this;
  }

   /**
   * Get disputeDate
   * @return disputeDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDisputeDate() {
    return disputeDate;
  }

  public void setDisputeDate(DateTime disputeDate) {
    this.disputeDate = disputeDate;
  }

  public NcbDisputeRemarksTUEF disputeTime(String disputeTime) {
    this.disputeTime = disputeTime;
    return this;
  }

   /**
   * Get disputeTime
   * @return disputeTime
  **/
  @ApiModelProperty(value = "")


  public String getDisputeTime() {
    return disputeTime;
  }

  public void setDisputeTime(String disputeTime) {
    this.disputeTime = disputeTime;
  }

  public NcbDisputeRemarksTUEF disputeRemark1(String disputeRemark1) {
    this.disputeRemark1 = disputeRemark1;
    return this;
  }

   /**
   * Get disputeRemark1
   * @return disputeRemark1
  **/
  @ApiModelProperty(value = "")


  public String getDisputeRemark1() {
    return disputeRemark1;
  }

  public void setDisputeRemark1(String disputeRemark1) {
    this.disputeRemark1 = disputeRemark1;
  }

  public NcbDisputeRemarksTUEF disputeRemark2(String disputeRemark2) {
    this.disputeRemark2 = disputeRemark2;
    return this;
  }

   /**
   * Get disputeRemark2
   * @return disputeRemark2
  **/
  @ApiModelProperty(value = "")


  public String getDisputeRemark2() {
    return disputeRemark2;
  }

  public void setDisputeRemark2(String disputeRemark2) {
    this.disputeRemark2 = disputeRemark2;
  }

  public NcbDisputeRemarksTUEF disputeRemark3(String disputeRemark3) {
    this.disputeRemark3 = disputeRemark3;
    return this;
  }

   /**
   * Get disputeRemark3
   * @return disputeRemark3
  **/
  @ApiModelProperty(value = "")


  public String getDisputeRemark3() {
    return disputeRemark3;
  }

  public void setDisputeRemark3(String disputeRemark3) {
    this.disputeRemark3 = disputeRemark3;
  }

  public NcbDisputeRemarksTUEF disputeRemark4(String disputeRemark4) {
    this.disputeRemark4 = disputeRemark4;
    return this;
  }

   /**
   * Get disputeRemark4
   * @return disputeRemark4
  **/
  @ApiModelProperty(value = "")


  public String getDisputeRemark4() {
    return disputeRemark4;
  }

  public void setDisputeRemark4(String disputeRemark4) {
    this.disputeRemark4 = disputeRemark4;
  }

  public NcbDisputeRemarksTUEF dispute(String dispute) {
    this.dispute = dispute;
    return this;
  }

   /**
   * Get dispute
   * @return dispute
  **/
  @ApiModelProperty(value = "")


  public String getDispute() {
    return dispute;
  }

  public void setDispute(String dispute) {
    this.dispute = dispute;
  }

  public NcbDisputeRemarksTUEF remarks5(String remarks5) {
    this.remarks5 = remarks5;
    return this;
  }

   /**
   * Get remarks5
   * @return remarks5
  **/
  @ApiModelProperty(value = "")


  public String getRemarks5() {
    return remarks5;
  }

  public void setRemarks5(String remarks5) {
    this.remarks5 = remarks5;
  }

  public NcbDisputeRemarksTUEF disputeRemark6(String disputeRemark6) {
    this.disputeRemark6 = disputeRemark6;
    return this;
  }

   /**
   * Get disputeRemark6
   * @return disputeRemark6
  **/
  @ApiModelProperty(value = "")


  public String getDisputeRemark6() {
    return disputeRemark6;
  }

  public void setDisputeRemark6(String disputeRemark6) {
    this.disputeRemark6 = disputeRemark6;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbDisputeRemarksTUEF ncbDisputeRemarksTUEF = (NcbDisputeRemarksTUEF) o;
    return Objects.equals(this.segmentTag, ncbDisputeRemarksTUEF.segmentTag) &&
        Objects.equals(this.referenceSegmentType, ncbDisputeRemarksTUEF.referenceSegmentType) &&
        Objects.equals(this.referenceSegment, ncbDisputeRemarksTUEF.referenceSegment) &&
        Objects.equals(this.disputeCode, ncbDisputeRemarksTUEF.disputeCode) &&
        Objects.equals(this.disputeDate, ncbDisputeRemarksTUEF.disputeDate) &&
        Objects.equals(this.disputeTime, ncbDisputeRemarksTUEF.disputeTime) &&
        Objects.equals(this.disputeRemark1, ncbDisputeRemarksTUEF.disputeRemark1) &&
        Objects.equals(this.disputeRemark2, ncbDisputeRemarksTUEF.disputeRemark2) &&
        Objects.equals(this.disputeRemark3, ncbDisputeRemarksTUEF.disputeRemark3) &&
        Objects.equals(this.disputeRemark4, ncbDisputeRemarksTUEF.disputeRemark4) &&
        Objects.equals(this.dispute, ncbDisputeRemarksTUEF.dispute) &&
        Objects.equals(this.remarks5, ncbDisputeRemarksTUEF.remarks5) &&
        Objects.equals(this.disputeRemark6, ncbDisputeRemarksTUEF.disputeRemark6);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentTag, referenceSegmentType, referenceSegment, disputeCode, disputeDate, disputeTime, disputeRemark1, disputeRemark2, disputeRemark3, disputeRemark4, dispute, remarks5, disputeRemark6);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbDisputeRemarksTUEF {\n");
    
    sb.append("    segmentTag: ").append(toIndentedString(segmentTag)).append("\n");
    sb.append("    referenceSegmentType: ").append(toIndentedString(referenceSegmentType)).append("\n");
    sb.append("    referenceSegment: ").append(toIndentedString(referenceSegment)).append("\n");
    sb.append("    disputeCode: ").append(toIndentedString(disputeCode)).append("\n");
    sb.append("    disputeDate: ").append(toIndentedString(disputeDate)).append("\n");
    sb.append("    disputeTime: ").append(toIndentedString(disputeTime)).append("\n");
    sb.append("    disputeRemark1: ").append(toIndentedString(disputeRemark1)).append("\n");
    sb.append("    disputeRemark2: ").append(toIndentedString(disputeRemark2)).append("\n");
    sb.append("    disputeRemark3: ").append(toIndentedString(disputeRemark3)).append("\n");
    sb.append("    disputeRemark4: ").append(toIndentedString(disputeRemark4)).append("\n");
    sb.append("    dispute: ").append(toIndentedString(dispute)).append("\n");
    sb.append("    remarks5: ").append(toIndentedString(remarks5)).append("\n");
    sb.append("    disputeRemark6: ").append(toIndentedString(disputeRemark6)).append("\n");
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

