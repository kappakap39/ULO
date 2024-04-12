package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Documents
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class Documents   {
  @JsonProperty("documentType")
  private String documentType = null;

  @JsonProperty("lhRefId")
  private String lhRefId = null;

  public Documents documentType(String documentType) {
    this.documentType = documentType;
    return this;
  }

   /**
   * Get documentType
   * @return documentType
  **/
  @ApiModelProperty(value = "")


  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }

  public Documents lhRefId(String lhRefId) {
    this.lhRefId = lhRefId;
    return this;
  }

   /**
   * Get lhRefId
   * @return lhRefId
  **/
  @ApiModelProperty(value = "")


  public String getLhRefId() {
    return lhRefId;
  }

  public void setLhRefId(String lhRefId) {
    this.lhRefId = lhRefId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Documents documents = (Documents) o;
    return Objects.equals(this.documentType, documents.documentType) &&
        Objects.equals(this.lhRefId, documents.lhRefId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentType, lhRefId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Documents {\n");
    
    sb.append("    documentType: ").append(toIndentedString(documentType)).append("\n");
    sb.append("    lhRefId: ").append(toIndentedString(lhRefId)).append("\n");
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

