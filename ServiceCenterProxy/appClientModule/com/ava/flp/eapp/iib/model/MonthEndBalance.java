package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MonthEndBalance
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class MonthEndBalance   {
  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("assetType")
  private String assetType = null;

  @JsonProperty("holdBalance")
  private String holdBalance = null;

  @JsonProperty("outstanding")
  private String outstanding = null;

  @JsonProperty("postDate")
  private String postDate = null;

  @JsonProperty("productGroup")
  private String productGroup = null;

  @JsonProperty("referenceId")
  private String referenceId = null;

  @JsonProperty("subAccountNo")
  private String subAccountNo = null;

  public MonthEndBalance accountNo(String accountNo) {
    this.accountNo = accountNo;
    return this;
  }

   /**
   * Get accountNo
   * @return accountNo
  **/
  @ApiModelProperty(value = "")


  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public MonthEndBalance assetType(String assetType) {
    this.assetType = assetType;
    return this;
  }

   /**
   * Get assetType
   * @return assetType
  **/
  @ApiModelProperty(value = "")


  public String getAssetType() {
    return assetType;
  }

  public void setAssetType(String assetType) {
    this.assetType = assetType;
  }

  public MonthEndBalance holdBalance(String holdBalance) {
    this.holdBalance = holdBalance;
    return this;
  }

   /**
   * Get holdBalance
   * @return holdBalance
  **/
  @ApiModelProperty(value = "")


  public String getHoldBalance() {
    return holdBalance;
  }

  public void setHoldBalance(String holdBalance) {
    this.holdBalance = holdBalance;
  }

  public MonthEndBalance outstanding(String outstanding) {
    this.outstanding = outstanding;
    return this;
  }

   /**
   * Get outstanding
   * @return outstanding
  **/
  @ApiModelProperty(value = "")


  public String getOutstanding() {
    return outstanding;
  }

  public void setOutstanding(String outstanding) {
    this.outstanding = outstanding;
  }

  public MonthEndBalance postDate(String postDate) {
    this.postDate = postDate;
    return this;
  }

   /**
   * Get postDate
   * @return postDate
  **/
  @ApiModelProperty(value = "")


  public String getPostDate() {
    return postDate;
  }

  public void setPostDate(String postDate) {
    this.postDate = postDate;
  }

  public MonthEndBalance productGroup(String productGroup) {
    this.productGroup = productGroup;
    return this;
  }

   /**
   * Get productGroup
   * @return productGroup
  **/
  @ApiModelProperty(value = "")


  public String getProductGroup() {
    return productGroup;
  }

  public void setProductGroup(String productGroup) {
    this.productGroup = productGroup;
  }

  public MonthEndBalance referenceId(String referenceId) {
    this.referenceId = referenceId;
    return this;
  }

   /**
   * Get referenceId
   * @return referenceId
  **/
  @ApiModelProperty(value = "")


  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public MonthEndBalance subAccountNo(String subAccountNo) {
    this.subAccountNo = subAccountNo;
    return this;
  }

   /**
   * Get subAccountNo
   * @return subAccountNo
  **/
  @ApiModelProperty(value = "")


  public String getSubAccountNo() {
    return subAccountNo;
  }

  public void setSubAccountNo(String subAccountNo) {
    this.subAccountNo = subAccountNo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MonthEndBalance monthEndBalance = (MonthEndBalance) o;
    return Objects.equals(this.accountNo, monthEndBalance.accountNo) &&
        Objects.equals(this.assetType, monthEndBalance.assetType) &&
        Objects.equals(this.holdBalance, monthEndBalance.holdBalance) &&
        Objects.equals(this.outstanding, monthEndBalance.outstanding) &&
        Objects.equals(this.postDate, monthEndBalance.postDate) &&
        Objects.equals(this.productGroup, monthEndBalance.productGroup) &&
        Objects.equals(this.referenceId, monthEndBalance.referenceId) &&
        Objects.equals(this.subAccountNo, monthEndBalance.subAccountNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, assetType, holdBalance, outstanding, postDate, productGroup, referenceId, subAccountNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MonthEndBalance {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    assetType: ").append(toIndentedString(assetType)).append("\n");
    sb.append("    holdBalance: ").append(toIndentedString(holdBalance)).append("\n");
    sb.append("    outstanding: ").append(toIndentedString(outstanding)).append("\n");
    sb.append("    postDate: ").append(toIndentedString(postDate)).append("\n");
    sb.append("    productGroup: ").append(toIndentedString(productGroup)).append("\n");
    sb.append("    referenceId: ").append(toIndentedString(referenceId)).append("\n");
    sb.append("    subAccountNo: ").append(toIndentedString(subAccountNo)).append("\n");
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

