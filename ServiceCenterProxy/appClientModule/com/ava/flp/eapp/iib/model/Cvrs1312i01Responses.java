package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.CVRSInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Cvrs1312i01Responses
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Cvrs1312i01Responses   {
  @JsonProperty("CVRSInfo")
  private CVRSInfo cvRSInfo = null;

  public Cvrs1312i01Responses cvRSInfo(CVRSInfo cvRSInfo) {
    this.cvRSInfo = cvRSInfo;
    return this;
  }

   /**
   * Get cvRSInfo
   * @return cvRSInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CVRSInfo getCvRSInfo() {
    return cvRSInfo;
  }

  public void setCvRSInfo(CVRSInfo cvRSInfo) {
    this.cvRSInfo = cvRSInfo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cvrs1312i01Responses cvrs1312i01Responses = (Cvrs1312i01Responses) o;
    return Objects.equals(this.cvRSInfo, cvrs1312i01Responses.cvRSInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cvRSInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cvrs1312i01Responses {\n");
    
    sb.append("    cvRSInfo: ").append(toIndentedString(cvRSInfo)).append("\n");
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

