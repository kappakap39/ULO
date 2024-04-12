package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * FicoScore
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class FicoScore   {
  @JsonProperty("ficoErrorCode")
  private String ficoErrorCode = null;

  @JsonProperty("ficoErrorMsg")
  private String ficoErrorMsg = null;

  @JsonProperty("ficoReason1Code")
  private String ficoReason1Code = null;

  @JsonProperty("ficoReason1Desc")
  private String ficoReason1Desc = null;

  @JsonProperty("ficoReason2Code")
  private String ficoReason2Code = null;

  @JsonProperty("ficoReason2Desc")
  private String ficoReason2Desc = null;

  @JsonProperty("ficoReason3Code")
  private String ficoReason3Code = null;

  @JsonProperty("ficoReason3Desc")
  private String ficoReason3Desc = null;

  @JsonProperty("ficoReason4Code")
  private String ficoReason4Code = null;

  @JsonProperty("ficoReason4Desc")
  private String ficoReason4Desc = null;

  @JsonProperty("ficoScore")
  private String ficoScore = null;

  public FicoScore ficoErrorCode(String ficoErrorCode) {
    this.ficoErrorCode = ficoErrorCode;
    return this;
  }

   /**
   * Get ficoErrorCode
   * @return ficoErrorCode
  **/
  @ApiModelProperty(value = "")


  public String getFicoErrorCode() {
    return ficoErrorCode;
  }

  public void setFicoErrorCode(String ficoErrorCode) {
    this.ficoErrorCode = ficoErrorCode;
  }

  public FicoScore ficoErrorMsg(String ficoErrorMsg) {
    this.ficoErrorMsg = ficoErrorMsg;
    return this;
  }

   /**
   * Get ficoErrorMsg
   * @return ficoErrorMsg
  **/
  @ApiModelProperty(value = "")


  public String getFicoErrorMsg() {
    return ficoErrorMsg;
  }

  public void setFicoErrorMsg(String ficoErrorMsg) {
    this.ficoErrorMsg = ficoErrorMsg;
  }

  public FicoScore ficoReason1Code(String ficoReason1Code) {
    this.ficoReason1Code = ficoReason1Code;
    return this;
  }

   /**
   * Get ficoReason1Code
   * @return ficoReason1Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason1Code() {
    return ficoReason1Code;
  }

  public void setFicoReason1Code(String ficoReason1Code) {
    this.ficoReason1Code = ficoReason1Code;
  }

  public FicoScore ficoReason1Desc(String ficoReason1Desc) {
    this.ficoReason1Desc = ficoReason1Desc;
    return this;
  }

   /**
   * Get ficoReason1Desc
   * @return ficoReason1Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason1Desc() {
    return ficoReason1Desc;
  }

  public void setFicoReason1Desc(String ficoReason1Desc) {
    this.ficoReason1Desc = ficoReason1Desc;
  }

  public FicoScore ficoReason2Code(String ficoReason2Code) {
    this.ficoReason2Code = ficoReason2Code;
    return this;
  }

   /**
   * Get ficoReason2Code
   * @return ficoReason2Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason2Code() {
    return ficoReason2Code;
  }

  public void setFicoReason2Code(String ficoReason2Code) {
    this.ficoReason2Code = ficoReason2Code;
  }

  public FicoScore ficoReason2Desc(String ficoReason2Desc) {
    this.ficoReason2Desc = ficoReason2Desc;
    return this;
  }

   /**
   * Get ficoReason2Desc
   * @return ficoReason2Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason2Desc() {
    return ficoReason2Desc;
  }

  public void setFicoReason2Desc(String ficoReason2Desc) {
    this.ficoReason2Desc = ficoReason2Desc;
  }

  public FicoScore ficoReason3Code(String ficoReason3Code) {
    this.ficoReason3Code = ficoReason3Code;
    return this;
  }

   /**
   * Get ficoReason3Code
   * @return ficoReason3Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason3Code() {
    return ficoReason3Code;
  }

  public void setFicoReason3Code(String ficoReason3Code) {
    this.ficoReason3Code = ficoReason3Code;
  }

  public FicoScore ficoReason3Desc(String ficoReason3Desc) {
    this.ficoReason3Desc = ficoReason3Desc;
    return this;
  }

   /**
   * Get ficoReason3Desc
   * @return ficoReason3Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason3Desc() {
    return ficoReason3Desc;
  }

  public void setFicoReason3Desc(String ficoReason3Desc) {
    this.ficoReason3Desc = ficoReason3Desc;
  }

  public FicoScore ficoReason4Code(String ficoReason4Code) {
    this.ficoReason4Code = ficoReason4Code;
    return this;
  }

   /**
   * Get ficoReason4Code
   * @return ficoReason4Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason4Code() {
    return ficoReason4Code;
  }

  public void setFicoReason4Code(String ficoReason4Code) {
    this.ficoReason4Code = ficoReason4Code;
  }

  public FicoScore ficoReason4Desc(String ficoReason4Desc) {
    this.ficoReason4Desc = ficoReason4Desc;
    return this;
  }

   /**
   * Get ficoReason4Desc
   * @return ficoReason4Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason4Desc() {
    return ficoReason4Desc;
  }

  public void setFicoReason4Desc(String ficoReason4Desc) {
    this.ficoReason4Desc = ficoReason4Desc;
  }

  public FicoScore ficoScore(String ficoScore) {
    this.ficoScore = ficoScore;
    return this;
  }

   /**
   * Get ficoScore
   * @return ficoScore
  **/
  @ApiModelProperty(value = "")


  public String getFicoScore() {
    return ficoScore;
  }

  public void setFicoScore(String ficoScore) {
    this.ficoScore = ficoScore;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FicoScore ficoScore = (FicoScore) o;
    return Objects.equals(this.ficoErrorCode, ficoScore.ficoErrorCode) &&
        Objects.equals(this.ficoErrorMsg, ficoScore.ficoErrorMsg) &&
        Objects.equals(this.ficoReason1Code, ficoScore.ficoReason1Code) &&
        Objects.equals(this.ficoReason1Desc, ficoScore.ficoReason1Desc) &&
        Objects.equals(this.ficoReason2Code, ficoScore.ficoReason2Code) &&
        Objects.equals(this.ficoReason2Desc, ficoScore.ficoReason2Desc) &&
        Objects.equals(this.ficoReason3Code, ficoScore.ficoReason3Code) &&
        Objects.equals(this.ficoReason3Desc, ficoScore.ficoReason3Desc) &&
        Objects.equals(this.ficoReason4Code, ficoScore.ficoReason4Code) &&
        Objects.equals(this.ficoReason4Desc, ficoScore.ficoReason4Desc) &&
        Objects.equals(this.ficoScore, ficoScore.ficoScore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ficoErrorCode, ficoErrorMsg, ficoReason1Code, ficoReason1Desc, ficoReason2Code, ficoReason2Desc, ficoReason3Code, ficoReason3Desc, ficoReason4Code, ficoReason4Desc, ficoScore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FicoScore {\n");
    
    sb.append("    ficoErrorCode: ").append(toIndentedString(ficoErrorCode)).append("\n");
    sb.append("    ficoErrorMsg: ").append(toIndentedString(ficoErrorMsg)).append("\n");
    sb.append("    ficoReason1Code: ").append(toIndentedString(ficoReason1Code)).append("\n");
    sb.append("    ficoReason1Desc: ").append(toIndentedString(ficoReason1Desc)).append("\n");
    sb.append("    ficoReason2Code: ").append(toIndentedString(ficoReason2Code)).append("\n");
    sb.append("    ficoReason2Desc: ").append(toIndentedString(ficoReason2Desc)).append("\n");
    sb.append("    ficoReason3Code: ").append(toIndentedString(ficoReason3Code)).append("\n");
    sb.append("    ficoReason3Desc: ").append(toIndentedString(ficoReason3Desc)).append("\n");
    sb.append("    ficoReason4Code: ").append(toIndentedString(ficoReason4Code)).append("\n");
    sb.append("    ficoReason4Desc: ").append(toIndentedString(ficoReason4Desc)).append("\n");
    sb.append("    ficoScore: ").append(toIndentedString(ficoScore)).append("\n");
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

