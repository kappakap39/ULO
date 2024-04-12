package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CheckKPLDuplicateResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CheckKPLDuplicateResponse   {
  @JsonProperty("duplicateFlag")
  private String duplicateFlag = null;

  public CheckKPLDuplicateResponse duplicateFlag(String duplicateFlag) {
    this.duplicateFlag = duplicateFlag;
    return this;
  }

   /**
   * Get duplicateFlag
   * @return duplicateFlag
  **/
  @ApiModelProperty(value = "")


  public String getDuplicateFlag() {
    return duplicateFlag;
  }

  public void setDuplicateFlag(String duplicateFlag) {
    this.duplicateFlag = duplicateFlag;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CheckKPLDuplicateResponse checkKPLDuplicateResponse = (CheckKPLDuplicateResponse) o;
    return Objects.equals(this.duplicateFlag, checkKPLDuplicateResponse.duplicateFlag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(duplicateFlag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CheckKPLDuplicateResponse {\n");
    
    sb.append("    duplicateFlag: ").append(toIndentedString(duplicateFlag)).append("\n");
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

