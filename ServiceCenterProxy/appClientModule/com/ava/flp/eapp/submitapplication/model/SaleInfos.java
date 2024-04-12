package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SaleInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class SaleInfos   {
  @JsonProperty("salesId")
  private String salesId = null;

  @JsonProperty("salesType")
  private String salesType = null;

  public SaleInfos salesId(String salesId) {
    this.salesId = salesId;
    return this;
  }

   /**
   * Get salesId
   * @return salesId
  **/
  @ApiModelProperty(value = "")


  public String getSalesId() {
    return salesId;
  }

  public void setSalesId(String salesId) {
    this.salesId = salesId;
  }

  public SaleInfos salesType(String salesType) {
    this.salesType = salesType;
    return this;
  }

   /**
   * Get salesType
   * @return salesType
  **/
  @ApiModelProperty(value = "")


  public String getSalesType() {
    return salesType;
  }

  public void setSalesType(String salesType) {
    this.salesType = salesType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaleInfos saleInfos = (SaleInfos) o;
    return Objects.equals(this.salesId, saleInfos.salesId) &&
        Objects.equals(this.salesType, saleInfos.salesType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(salesId, salesType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaleInfos {\n");
    
    sb.append("    salesId: ").append(toIndentedString(salesId)).append("\n");
    sb.append("    salesType: ").append(toIndentedString(salesType)).append("\n");
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

