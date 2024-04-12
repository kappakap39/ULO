package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CVRSInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CVRSInfo   {
  @JsonProperty("sanctionFlg")
  private String sanctionFlg = null;

  public CVRSInfo sanctionFlg(String sanctionFlg) {
    this.sanctionFlg = sanctionFlg;
    return this;
  }

   /**
   * Get sanctionFlg
   * @return sanctionFlg
  **/
  @ApiModelProperty(value = "")


  public String getSanctionFlg() {
    return sanctionFlg;
  }

  public void setSanctionFlg(String sanctionFlg) {
    this.sanctionFlg = sanctionFlg;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CVRSInfo cvRSInfo = (CVRSInfo) o;
    return Objects.equals(this.sanctionFlg, cvRSInfo.sanctionFlg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sanctionFlg);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CVRSInfo {\n");
    
    sb.append("    sanctionFlg: ").append(toIndentedString(sanctionFlg)).append("\n");
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

