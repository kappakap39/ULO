package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NcbIdsTUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbIdsTUEF   {
  @JsonProperty("segmentTag")
  private String segmentTag = null;

  @JsonProperty("idType")
  private String idType = null;

  @JsonProperty("idNumber")
  private String idNumber = null;

  @JsonProperty("idIssueCountry")
  private String idIssueCountry = null;

  public NcbIdsTUEF segmentTag(String segmentTag) {
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

  public NcbIdsTUEF idType(String idType) {
    this.idType = idType;
    return this;
  }

   /**
   * Get idType
   * @return idType
  **/
  @ApiModelProperty(value = "")


  public String getIdType() {
    return idType;
  }

  public void setIdType(String idType) {
    this.idType = idType;
  }

  public NcbIdsTUEF idNumber(String idNumber) {
    this.idNumber = idNumber;
    return this;
  }

   /**
   * Get idNumber
   * @return idNumber
  **/
  @ApiModelProperty(value = "")


  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public NcbIdsTUEF idIssueCountry(String idIssueCountry) {
    this.idIssueCountry = idIssueCountry;
    return this;
  }

   /**
   * Get idIssueCountry
   * @return idIssueCountry
  **/
  @ApiModelProperty(value = "")


  public String getIdIssueCountry() {
    return idIssueCountry;
  }

  public void setIdIssueCountry(String idIssueCountry) {
    this.idIssueCountry = idIssueCountry;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbIdsTUEF ncbIdsTUEF = (NcbIdsTUEF) o;
    return Objects.equals(this.segmentTag, ncbIdsTUEF.segmentTag) &&
        Objects.equals(this.idType, ncbIdsTUEF.idType) &&
        Objects.equals(this.idNumber, ncbIdsTUEF.idNumber) &&
        Objects.equals(this.idIssueCountry, ncbIdsTUEF.idIssueCountry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentTag, idType, idNumber, idIssueCountry);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbIdsTUEF {\n");
    
    sb.append("    segmentTag: ").append(toIndentedString(segmentTag)).append("\n");
    sb.append("    idType: ").append(toIndentedString(idType)).append("\n");
    sb.append("    idNumber: ").append(toIndentedString(idNumber)).append("\n");
    sb.append("    idIssueCountry: ").append(toIndentedString(idIssueCountry)).append("\n");
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

