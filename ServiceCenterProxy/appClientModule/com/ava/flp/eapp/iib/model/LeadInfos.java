package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LeadInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class LeadInfos   {
  @JsonProperty("product")
  private String product = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("expiryDate")
  private DateTime expiryDate = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("approveStatus")
  private BigDecimal approveStatus = null;

  @JsonProperty("finalApproveStatus")
  private BigDecimal finalApproveStatus = null;

  public LeadInfos product(String product) {
    this.product = product;
    return this;
  }

   /**
   * Get product
   * @return product
  **/
  @ApiModelProperty(value = "")


  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public LeadInfos id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LeadInfos expiryDate(DateTime expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

   /**
   * Get expiryDate
   * @return expiryDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(DateTime expiryDate) {
    this.expiryDate = expiryDate;
  }

  public LeadInfos status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @ApiModelProperty(value = "")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LeadInfos approveStatus(BigDecimal approveStatus) {
    this.approveStatus = approveStatus;
    return this;
  }

   /**
   * Get approveStatus
   * @return approveStatus
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getApproveStatus() {
    return approveStatus;
  }

  public void setApproveStatus(BigDecimal approveStatus) {
    this.approveStatus = approveStatus;
  }

  public LeadInfos finalApproveStatus(BigDecimal finalApproveStatus) {
    this.finalApproveStatus = finalApproveStatus;
    return this;
  }

   /**
   * Get finalApproveStatus
   * @return finalApproveStatus
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getFinalApproveStatus() {
    return finalApproveStatus;
  }

  public void setFinalApproveStatus(BigDecimal finalApproveStatus) {
    this.finalApproveStatus = finalApproveStatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LeadInfos leadInfos = (LeadInfos) o;
    return Objects.equals(this.product, leadInfos.product) &&
        Objects.equals(this.id, leadInfos.id) &&
        Objects.equals(this.expiryDate, leadInfos.expiryDate) &&
        Objects.equals(this.status, leadInfos.status) &&
        Objects.equals(this.approveStatus, leadInfos.approveStatus) &&
        Objects.equals(this.finalApproveStatus, leadInfos.finalApproveStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, id, expiryDate, status, approveStatus, finalApproveStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LeadInfos {\n");
    
    sb.append("    product: ").append(toIndentedString(product)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    approveStatus: ").append(toIndentedString(approveStatus)).append("\n");
    sb.append("    finalApproveStatus: ").append(toIndentedString(finalApproveStatus)).append("\n");
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

