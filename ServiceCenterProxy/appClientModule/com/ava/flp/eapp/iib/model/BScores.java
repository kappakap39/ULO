package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BScores
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class BScores   {
  @JsonProperty("lpmId")
  private String lpmId = null;

  @JsonProperty("postDate")
  private DateTime postDate = null;

  @JsonProperty("productType")
  private String productType = null;

  @JsonProperty("riskGrade")
  private String riskGrade = null;

  public BScores lpmId(String lpmId) {
    this.lpmId = lpmId;
    return this;
  }

   /**
   * Get lpmId
   * @return lpmId
  **/
  @ApiModelProperty(value = "")


  public String getLpmId() {
    return lpmId;
  }

  public void setLpmId(String lpmId) {
    this.lpmId = lpmId;
  }

  public BScores postDate(DateTime postDate) {
    this.postDate = postDate;
    return this;
  }

   /**
   * Get postDate
   * @return postDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getPostDate() {
    return postDate;
  }

  public void setPostDate(DateTime postDate) {
    this.postDate = postDate;
  }

  public BScores productType(String productType) {
    this.productType = productType;
    return this;
  }

   /**
   * Get productType
   * @return productType
  **/
  @ApiModelProperty(value = "")


  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public BScores riskGrade(String riskGrade) {
    this.riskGrade = riskGrade;
    return this;
  }

   /**
   * Get riskGrade
   * @return riskGrade
  **/
  @ApiModelProperty(value = "")


  public String getRiskGrade() {
    return riskGrade;
  }

  public void setRiskGrade(String riskGrade) {
    this.riskGrade = riskGrade;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BScores bscores = (BScores) o;
    return Objects.equals(this.lpmId, bscores.lpmId) &&
        Objects.equals(this.postDate, bscores.postDate) &&
        Objects.equals(this.productType, bscores.productType) &&
        Objects.equals(this.riskGrade, bscores.riskGrade);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lpmId, postDate, productType, riskGrade);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BScores {\n");
    
    sb.append("    lpmId: ").append(toIndentedString(lpmId)).append("\n");
    sb.append("    postDate: ").append(toIndentedString(postDate)).append("\n");
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    riskGrade: ").append(toIndentedString(riskGrade)).append("\n");
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

