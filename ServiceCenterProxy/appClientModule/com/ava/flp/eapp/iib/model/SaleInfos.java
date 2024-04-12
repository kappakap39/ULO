package com.ava.flp.eapp.iib.model;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-12T14:54:16.117+07:00")

public class SaleInfos   {
  @JsonProperty("region")
  private String region = null;

  @JsonProperty("salesId")
  private String salesId = null;

  @JsonProperty("salesName")
  private String salesName = null;

  @JsonProperty("salesRCCode")
  private String salesRCCode = null;

  @JsonProperty("salesType")
  private String salesType = null;

  @JsonProperty("zone")
  private String zone = null;

  @JsonProperty("saleChannel")
  private String saleChannel = null;

  public SaleInfos region(String region) {
    this.region = region;
    return this;
  }

   /**
   * Get region
   * @return region
  **/
  @ApiModelProperty(value = "")


  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

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

  public SaleInfos salesName(String salesName) {
    this.salesName = salesName;
    return this;
  }

   /**
   * Get salesName
   * @return salesName
  **/
  @ApiModelProperty(value = "")


  public String getSalesName() {
    return salesName;
  }

  public void setSalesName(String salesName) {
    this.salesName = salesName;
  }

  public SaleInfos salesRCCode(String salesRCCode) {
    this.salesRCCode = salesRCCode;
    return this;
  }

   /**
   * Get salesRCCode
   * @return salesRCCode
  **/
  @ApiModelProperty(value = "")


  public String getSalesRCCode() {
    return salesRCCode;
  }

  public void setSalesRCCode(String salesRCCode) {
    this.salesRCCode = salesRCCode;
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

  public SaleInfos zone(String zone) {
    this.zone = zone;
    return this;
  }

   /**
   * Get zone
   * @return zone
  **/
  @ApiModelProperty(value = "")


  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public SaleInfos saleChannel(String saleChannel) {
    this.saleChannel = saleChannel;
    return this;
  }

   /**
   * Get saleChannel
   * @return saleChannel
  **/
  @ApiModelProperty(value = "")


  public String getSaleChannel() {
    return saleChannel;
  }

  public void setSaleChannel(String saleChannel) {
    this.saleChannel = saleChannel;
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
    return Objects.equals(this.region, saleInfos.region) &&
        Objects.equals(this.salesId, saleInfos.salesId) &&
        Objects.equals(this.salesName, saleInfos.salesName) &&
        Objects.equals(this.salesRCCode, saleInfos.salesRCCode) &&
        Objects.equals(this.salesType, saleInfos.salesType) &&
        Objects.equals(this.zone, saleInfos.zone) &&
        Objects.equals(this.saleChannel, saleInfos.saleChannel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(region, salesId, salesName, salesRCCode, salesType, zone, saleChannel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaleInfos {\n");
    
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    salesId: ").append(toIndentedString(salesId)).append("\n");
    sb.append("    salesName: ").append(toIndentedString(salesName)).append("\n");
    sb.append("    salesRCCode: ").append(toIndentedString(salesRCCode)).append("\n");
    sb.append("    salesType: ").append(toIndentedString(salesType)).append("\n");
    sb.append("    zone: ").append(toIndentedString(zone)).append("\n");
    sb.append("    saleChannel: ").append(toIndentedString(saleChannel)).append("\n");
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

