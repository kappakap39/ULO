package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.Doclists;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DocumentScenarios
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class DocumentScenarios   {
  @JsonProperty("scen")
  private BigDecimal scen = null;

  @JsonProperty("scenType")
  private String scenType = null;

  @JsonProperty("doclists")
  private List<Doclists> doclists = null;

  public DocumentScenarios scen(BigDecimal scen) {
    this.scen = scen;
    return this;
  }

   /**
   * Get scen
   * @return scen
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getScen() {
    return scen;
  }

  public void setScen(BigDecimal scen) {
    this.scen = scen;
  }

  public DocumentScenarios scenType(String scenType) {
    this.scenType = scenType;
    return this;
  }

   /**
   * Get scenType
   * @return scenType
  **/
  @ApiModelProperty(value = "")


  public String getScenType() {
    return scenType;
  }

  public void setScenType(String scenType) {
    this.scenType = scenType;
  }

  public DocumentScenarios doclists(List<Doclists> doclists) {
    this.doclists = doclists;
    return this;
  }

  public DocumentScenarios addDoclistsItem(Doclists doclistsItem) {
    if (this.doclists == null) {
      this.doclists = new ArrayList<Doclists>();
    }
    this.doclists.add(doclistsItem);
    return this;
  }

   /**
   * Get doclists
   * @return doclists
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Doclists> getDoclists() {
    return doclists;
  }

  public void setDoclists(List<Doclists> doclists) {
    this.doclists = doclists;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentScenarios documentScenarios = (DocumentScenarios) o;
    return Objects.equals(this.scen, documentScenarios.scen) &&
        Objects.equals(this.scenType, documentScenarios.scenType) &&
        Objects.equals(this.doclists, documentScenarios.doclists);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scen, scenType, doclists);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentScenarios {\n");
    
    sb.append("    scen: ").append(toIndentedString(scen)).append("\n");
    sb.append("    scenType: ").append(toIndentedString(scenType)).append("\n");
    sb.append("    doclists: ").append(toIndentedString(doclists)).append("\n");
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

