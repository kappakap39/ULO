package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WebsitesWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class WebsitesWF   {
  @JsonProperty("websiteCode")
  private String websiteCode = null;

  @JsonProperty("websiteResult")
  private String websiteResult = null;

  public WebsitesWF websiteCode(String websiteCode) {
    this.websiteCode = websiteCode;
    return this;
  }

   /**
   * Get websiteCode
   * @return websiteCode
  **/
  @ApiModelProperty(value = "")


  public String getWebsiteCode() {
    return websiteCode;
  }

  public void setWebsiteCode(String websiteCode) {
    this.websiteCode = websiteCode;
  }

  public WebsitesWF websiteResult(String websiteResult) {
    this.websiteResult = websiteResult;
    return this;
  }

   /**
   * Get websiteResult
   * @return websiteResult
  **/
  @ApiModelProperty(value = "")


  public String getWebsiteResult() {
    return websiteResult;
  }

  public void setWebsiteResult(String websiteResult) {
    this.websiteResult = websiteResult;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebsitesWF websitesWF = (WebsitesWF) o;
    return Objects.equals(this.websiteCode, websitesWF.websiteCode) &&
        Objects.equals(this.websiteResult, websitesWF.websiteResult);
  }

  @Override
  public int hashCode() {
    return Objects.hash(websiteCode, websiteResult);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebsitesWF {\n");
    
    sb.append("    websiteCode: ").append(toIndentedString(websiteCode)).append("\n");
    sb.append("    websiteResult: ").append(toIndentedString(websiteResult)).append("\n");
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

