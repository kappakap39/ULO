package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WebVerifications
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class WebVerifications   {
  @JsonProperty("remark")
  private String remark = null;

  @JsonProperty("verResult")
  private String verResult = null;

  @JsonProperty("verResultId")
  private String verResultId = null;

  @JsonProperty("webCode")
  private String webCode = null;

  @JsonProperty("webVerifyId")
  private String webVerifyId = null;

  @JsonProperty("webResult")
  private String webResult = null;

  public WebVerifications remark(String remark) {
    this.remark = remark;
    return this;
  }

   /**
   * Get remark
   * @return remark
  **/
  @ApiModelProperty(value = "")


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public WebVerifications verResult(String verResult) {
    this.verResult = verResult;
    return this;
  }

   /**
   * Get verResult
   * @return verResult
  **/
  @ApiModelProperty(value = "")


  public String getVerResult() {
    return verResult;
  }

  public void setVerResult(String verResult) {
    this.verResult = verResult;
  }

  public WebVerifications verResultId(String verResultId) {
    this.verResultId = verResultId;
    return this;
  }

   /**
   * Get verResultId
   * @return verResultId
  **/
  @ApiModelProperty(value = "")


  public String getVerResultId() {
    return verResultId;
  }

  public void setVerResultId(String verResultId) {
    this.verResultId = verResultId;
  }

  public WebVerifications webCode(String webCode) {
    this.webCode = webCode;
    return this;
  }

   /**
   * Get webCode
   * @return webCode
  **/
  @ApiModelProperty(value = "")


  public String getWebCode() {
    return webCode;
  }

  public void setWebCode(String webCode) {
    this.webCode = webCode;
  }

  public WebVerifications webVerifyId(String webVerifyId) {
    this.webVerifyId = webVerifyId;
    return this;
  }

   /**
   * Get webVerifyId
   * @return webVerifyId
  **/
  @ApiModelProperty(value = "")


  public String getWebVerifyId() {
    return webVerifyId;
  }

  public void setWebVerifyId(String webVerifyId) {
    this.webVerifyId = webVerifyId;
  }

  public WebVerifications webResult(String webResult) {
    this.webResult = webResult;
    return this;
  }

   /**
   * Get webResult
   * @return webResult
  **/
  @ApiModelProperty(value = "")


  public String getWebResult() {
    return webResult;
  }

  public void setWebResult(String webResult) {
    this.webResult = webResult;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebVerifications webVerifications = (WebVerifications) o;
    return Objects.equals(this.remark, webVerifications.remark) &&
        Objects.equals(this.verResult, webVerifications.verResult) &&
        Objects.equals(this.verResultId, webVerifications.verResultId) &&
        Objects.equals(this.webCode, webVerifications.webCode) &&
        Objects.equals(this.webVerifyId, webVerifications.webVerifyId) &&
        Objects.equals(this.webResult, webVerifications.webResult);
  }

  @Override
  public int hashCode() {
    return Objects.hash(remark, verResult, verResultId, webCode, webVerifyId, webResult);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebVerifications {\n");
    
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
    sb.append("    verResult: ").append(toIndentedString(verResult)).append("\n");
    sb.append("    verResultId: ").append(toIndentedString(verResultId)).append("\n");
    sb.append("    webCode: ").append(toIndentedString(webCode)).append("\n");
    sb.append("    webVerifyId: ").append(toIndentedString(webVerifyId)).append("\n");
    sb.append("    webResult: ").append(toIndentedString(webResult)).append("\n");
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

