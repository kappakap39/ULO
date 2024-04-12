package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.ApplicationGroupMWF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WfInquiryResponseM
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class WfInquiryResponseM   {
  @JsonProperty("applicationGroupM")
  private ApplicationGroupMWF applicationGroupM = null;

  public WfInquiryResponseM applicationGroupM(ApplicationGroupMWF applicationGroupM) {
    this.applicationGroupM = applicationGroupM;
    return this;
  }

   /**
   * Get applicationGroupM
   * @return applicationGroupM
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ApplicationGroupMWF getApplicationGroupM() {
    return applicationGroupM;
  }

  public void setApplicationGroupM(ApplicationGroupMWF applicationGroupM) {
    this.applicationGroupM = applicationGroupM;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WfInquiryResponseM wfInquiryResponseM = (WfInquiryResponseM) o;
    return Objects.equals(this.applicationGroupM, wfInquiryResponseM.applicationGroupM);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationGroupM);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WfInquiryResponseM {\n");
    
    sb.append("    applicationGroupM: ").append(toIndentedString(applicationGroupM)).append("\n");
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

