package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DocumentSlaTypes
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class DocumentSlaTypes   {
  @JsonProperty("documentSlaType")
  private String documentSlaType = null;

  public DocumentSlaTypes documentSlaType(String documentSlaType) {
    this.documentSlaType = documentSlaType;
    return this;
  }

   /**
   * Get documentSlaType
   * @return documentSlaType
  **/
  @ApiModelProperty(value = "")


  public String getDocumentSlaType() {
    return documentSlaType;
  }

  public void setDocumentSlaType(String documentSlaType) {
    this.documentSlaType = documentSlaType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentSlaTypes documentSlaTypes = (DocumentSlaTypes) o;
    return Objects.equals(this.documentSlaType, documentSlaTypes.documentSlaType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentSlaType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentSlaTypes {\n");
    
    sb.append("    documentSlaType: ").append(toIndentedString(documentSlaType)).append("\n");
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

