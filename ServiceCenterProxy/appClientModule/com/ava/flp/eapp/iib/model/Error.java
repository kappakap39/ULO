package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Error
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Error   {
  @JsonProperty("errorAppId")
  private String errorAppId = null;

  @JsonProperty("errorAppAbbrv")
  private String errorAppAbbrv = null;

  @JsonProperty("errorCode")
  private String errorCode = null;

  @JsonProperty("errorDesc")
  private String errorDesc = null;

  @JsonProperty("errorSeverity")
  private String errorSeverity = null;

  public Error errorAppId(String errorAppId) {
    this.errorAppId = errorAppId;
    return this;
  }

   /**
   * Get errorAppId
   * @return errorAppId
  **/
  @ApiModelProperty(value = "")


  public String getErrorAppId() {
    return errorAppId;
  }

  public void setErrorAppId(String errorAppId) {
    this.errorAppId = errorAppId;
  }

  public Error errorAppAbbrv(String errorAppAbbrv) {
    this.errorAppAbbrv = errorAppAbbrv;
    return this;
  }

   /**
   * Get errorAppAbbrv
   * @return errorAppAbbrv
  **/
  @ApiModelProperty(value = "")


  public String getErrorAppAbbrv() {
    return errorAppAbbrv;
  }

  public void setErrorAppAbbrv(String errorAppAbbrv) {
    this.errorAppAbbrv = errorAppAbbrv;
  }

  public Error errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

   /**
   * Get errorCode
   * @return errorCode
  **/
  @ApiModelProperty(value = "")


  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public Error errorDesc(String errorDesc) {
    this.errorDesc = errorDesc;
    return this;
  }

   /**
   * Get errorDesc
   * @return errorDesc
  **/
  @ApiModelProperty(value = "")


  public String getErrorDesc() {
    return errorDesc;
  }

  public void setErrorDesc(String errorDesc) {
    this.errorDesc = errorDesc;
  }

  public Error errorSeverity(String errorSeverity) {
    this.errorSeverity = errorSeverity;
    return this;
  }

   /**
   * Get errorSeverity
   * @return errorSeverity
  **/
  @ApiModelProperty(value = "")


  public String getErrorSeverity() {
    return errorSeverity;
  }

  public void setErrorSeverity(String errorSeverity) {
    this.errorSeverity = errorSeverity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.errorAppId, error.errorAppId) &&
        Objects.equals(this.errorAppAbbrv, error.errorAppAbbrv) &&
        Objects.equals(this.errorCode, error.errorCode) &&
        Objects.equals(this.errorDesc, error.errorDesc) &&
        Objects.equals(this.errorSeverity, error.errorSeverity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorAppId, errorAppAbbrv, errorCode, errorDesc, errorSeverity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    
    sb.append("    errorAppId: ").append(toIndentedString(errorAppId)).append("\n");
    sb.append("    errorAppAbbrv: ").append(toIndentedString(errorAppAbbrv)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorDesc: ").append(toIndentedString(errorDesc)).append("\n");
    sb.append("    errorSeverity: ").append(toIndentedString(errorSeverity)).append("\n");
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

