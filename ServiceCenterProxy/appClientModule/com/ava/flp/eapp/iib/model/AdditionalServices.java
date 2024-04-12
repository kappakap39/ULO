package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AdditionalServices
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class AdditionalServices   {
  @JsonProperty("currentAccName")
  private String currentAccName = null;

  @JsonProperty("currentAccNo")
  private String currentAccNo = null;

  @JsonProperty("savingAccName")
  private String savingAccName = null;

  @JsonProperty("savingAccNo")
  private String savingAccNo = null;

  @JsonProperty("serviceType")
  private String serviceType = null;

  public AdditionalServices currentAccName(String currentAccName) {
    this.currentAccName = currentAccName;
    return this;
  }

   /**
   * Get currentAccName
   * @return currentAccName
  **/
  @ApiModelProperty(value = "")


  public String getCurrentAccName() {
    return currentAccName;
  }

  public void setCurrentAccName(String currentAccName) {
    this.currentAccName = currentAccName;
  }

  public AdditionalServices currentAccNo(String currentAccNo) {
    this.currentAccNo = currentAccNo;
    return this;
  }

   /**
   * Get currentAccNo
   * @return currentAccNo
  **/
  @ApiModelProperty(value = "")


  public String getCurrentAccNo() {
    return currentAccNo;
  }

  public void setCurrentAccNo(String currentAccNo) {
    this.currentAccNo = currentAccNo;
  }

  public AdditionalServices savingAccName(String savingAccName) {
    this.savingAccName = savingAccName;
    return this;
  }

   /**
   * Get savingAccName
   * @return savingAccName
  **/
  @ApiModelProperty(value = "")


  public String getSavingAccName() {
    return savingAccName;
  }

  public void setSavingAccName(String savingAccName) {
    this.savingAccName = savingAccName;
  }

  public AdditionalServices savingAccNo(String savingAccNo) {
    this.savingAccNo = savingAccNo;
    return this;
  }

   /**
   * Get savingAccNo
   * @return savingAccNo
  **/
  @ApiModelProperty(value = "")


  public String getSavingAccNo() {
    return savingAccNo;
  }

  public void setSavingAccNo(String savingAccNo) {
    this.savingAccNo = savingAccNo;
  }

  public AdditionalServices serviceType(String serviceType) {
    this.serviceType = serviceType;
    return this;
  }

   /**
   * Get serviceType
   * @return serviceType
  **/
  @ApiModelProperty(value = "")


  public String getServiceType() {
    return serviceType;
  }

  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdditionalServices additionalServices = (AdditionalServices) o;
    return Objects.equals(this.currentAccName, additionalServices.currentAccName) &&
        Objects.equals(this.currentAccNo, additionalServices.currentAccNo) &&
        Objects.equals(this.savingAccName, additionalServices.savingAccName) &&
        Objects.equals(this.savingAccNo, additionalServices.savingAccNo) &&
        Objects.equals(this.serviceType, additionalServices.serviceType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentAccName, currentAccNo, savingAccName, savingAccNo, serviceType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AdditionalServices {\n");
    
    sb.append("    currentAccName: ").append(toIndentedString(currentAccName)).append("\n");
    sb.append("    currentAccNo: ").append(toIndentedString(currentAccNo)).append("\n");
    sb.append("    savingAccName: ").append(toIndentedString(savingAccName)).append("\n");
    sb.append("    savingAccNo: ").append(toIndentedString(savingAccNo)).append("\n");
    sb.append("    serviceType: ").append(toIndentedString(serviceType)).append("\n");
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

