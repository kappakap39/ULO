package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NcbIds
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbIds   {
  @JsonProperty("idIssueCountry")
  private String idIssueCountry = null;

  @JsonProperty("idNumber")
  private String idNumber = null;

  @JsonProperty("idType")
  private String idType = null;

  @JsonProperty("segmentValue")
  private String segmentValue = null;

  public NcbIds idIssueCountry(String idIssueCountry) {
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

  public NcbIds idNumber(String idNumber) {
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

  public NcbIds idType(String idType) {
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

  public NcbIds segmentValue(String segmentValue) {
    this.segmentValue = segmentValue;
    return this;
  }

   /**
   * Get segmentValue
   * @return segmentValue
  **/
  @ApiModelProperty(value = "")


  public String getSegmentValue() {
    return segmentValue;
  }

  public void setSegmentValue(String segmentValue) {
    this.segmentValue = segmentValue;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbIds ncbIds = (NcbIds) o;
    return Objects.equals(this.idIssueCountry, ncbIds.idIssueCountry) &&
        Objects.equals(this.idNumber, ncbIds.idNumber) &&
        Objects.equals(this.idType, ncbIds.idType) &&
        Objects.equals(this.segmentValue, ncbIds.segmentValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idIssueCountry, idNumber, idType, segmentValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbIds {\n");
    
    sb.append("    idIssueCountry: ").append(toIndentedString(idIssueCountry)).append("\n");
    sb.append("    idNumber: ").append(toIndentedString(idNumber)).append("\n");
    sb.append("    idType: ").append(toIndentedString(idType)).append("\n");
    sb.append("    segmentValue: ").append(toIndentedString(segmentValue)).append("\n");
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

