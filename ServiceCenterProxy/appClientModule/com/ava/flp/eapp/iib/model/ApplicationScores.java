package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApplicationScores
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class ApplicationScores   {
  @JsonProperty("coarseRiskGrade")
  private String coarseRiskGrade = null;

  @JsonProperty("fineRiskGrade")
  private String fineRiskGrade = null;

  @JsonProperty("productId")
  private String productId = null;

  public ApplicationScores coarseRiskGrade(String coarseRiskGrade) {
    this.coarseRiskGrade = coarseRiskGrade;
    return this;
  }

   /**
   * Get coarseRiskGrade
   * @return coarseRiskGrade
  **/
  @ApiModelProperty(value = "")


  public String getCoarseRiskGrade() {
    return coarseRiskGrade;
  }

  public void setCoarseRiskGrade(String coarseRiskGrade) {
    this.coarseRiskGrade = coarseRiskGrade;
  }

  public ApplicationScores fineRiskGrade(String fineRiskGrade) {
    this.fineRiskGrade = fineRiskGrade;
    return this;
  }

   /**
   * Get fineRiskGrade
   * @return fineRiskGrade
  **/
  @ApiModelProperty(value = "")


  public String getFineRiskGrade() {
    return fineRiskGrade;
  }

  public void setFineRiskGrade(String fineRiskGrade) {
    this.fineRiskGrade = fineRiskGrade;
  }

  public ApplicationScores productId(String productId) {
    this.productId = productId;
    return this;
  }

   /**
   * Get productId
   * @return productId
  **/
  @ApiModelProperty(value = "")


  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationScores applicationScores = (ApplicationScores) o;
    return Objects.equals(this.coarseRiskGrade, applicationScores.coarseRiskGrade) &&
        Objects.equals(this.fineRiskGrade, applicationScores.fineRiskGrade) &&
        Objects.equals(this.productId, applicationScores.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coarseRiskGrade, fineRiskGrade, productId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationScores {\n");
    
    sb.append("    coarseRiskGrade: ").append(toIndentedString(coarseRiskGrade)).append("\n");
    sb.append("    fineRiskGrade: ").append(toIndentedString(fineRiskGrade)).append("\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
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

