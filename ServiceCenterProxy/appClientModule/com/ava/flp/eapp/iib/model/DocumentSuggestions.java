package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.DocumentScenarios;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DocumentSuggestions
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class DocumentSuggestions   {
  @JsonProperty("documentGroup")
  private String documentGroup = null;

  @JsonProperty("docCompletedFlag")
  private String docCompletedFlag = null;

  @JsonProperty("documentScenarios")
  private List<DocumentScenarios> documentScenarios = null;

  public DocumentSuggestions documentGroup(String documentGroup) {
    this.documentGroup = documentGroup;
    return this;
  }

   /**
   * Get documentGroup
   * @return documentGroup
  **/
  @ApiModelProperty(value = "")


  public String getDocumentGroup() {
    return documentGroup;
  }

  public void setDocumentGroup(String documentGroup) {
    this.documentGroup = documentGroup;
  }

  public DocumentSuggestions docCompletedFlag(String docCompletedFlag) {
    this.docCompletedFlag = docCompletedFlag;
    return this;
  }

   /**
   * Get docCompletedFlag
   * @return docCompletedFlag
  **/
  @ApiModelProperty(value = "")


  public String getDocCompletedFlag() {
    return docCompletedFlag;
  }

  public void setDocCompletedFlag(String docCompletedFlag) {
    this.docCompletedFlag = docCompletedFlag;
  }

  public DocumentSuggestions documentScenarios(List<DocumentScenarios> documentScenarios) {
    this.documentScenarios = documentScenarios;
    return this;
  }

  public DocumentSuggestions addDocumentScenariosItem(DocumentScenarios documentScenariosItem) {
    if (this.documentScenarios == null) {
      this.documentScenarios = new ArrayList<DocumentScenarios>();
    }
    this.documentScenarios.add(documentScenariosItem);
    return this;
  }

   /**
   * Get documentScenarios
   * @return documentScenarios
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<DocumentScenarios> getDocumentScenarios() {
    return documentScenarios;
  }

  public void setDocumentScenarios(List<DocumentScenarios> documentScenarios) {
    this.documentScenarios = documentScenarios;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentSuggestions documentSuggestions = (DocumentSuggestions) o;
    return Objects.equals(this.documentGroup, documentSuggestions.documentGroup) &&
        Objects.equals(this.docCompletedFlag, documentSuggestions.docCompletedFlag) &&
        Objects.equals(this.documentScenarios, documentSuggestions.documentScenarios);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentGroup, docCompletedFlag, documentScenarios);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentSuggestions {\n");
    
    sb.append("    documentGroup: ").append(toIndentedString(documentGroup)).append("\n");
    sb.append("    docCompletedFlag: ").append(toIndentedString(docCompletedFlag)).append("\n");
    sb.append("    documentScenarios: ").append(toIndentedString(documentScenarios)).append("\n");
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

