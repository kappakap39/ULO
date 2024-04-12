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
 * OldIncomes
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class OldIncomes   {
  @JsonProperty("accountStatus")
  private String accountStatus = null;

  @JsonProperty("applyType")
  private String applyType = null;

  @JsonProperty("blockCode")
  private String blockCode = null;

  @JsonProperty("cardNo")
  private String cardNo = null;

  @JsonProperty("cardType")
  private String cardType = null;

  @JsonProperty("incomeSource")
  private String incomeSource = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("productId")
  private String productId = null;

  @JsonProperty("sourceData")
  private String sourceData = null;

  @JsonProperty("typeOfFin")
  private String typeOfFin = null;

  @JsonProperty("verifiedIncome")
  private String verifiedIncome = null;

  @JsonProperty("dateSort")
  private BigDecimal dateSort = null;

  public OldIncomes accountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

   /**
   * Get accountStatus
   * @return accountStatus
  **/
  @ApiModelProperty(value = "")


  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  public OldIncomes applyType(String applyType) {
    this.applyType = applyType;
    return this;
  }

   /**
   * Get applyType
   * @return applyType
  **/
  @ApiModelProperty(value = "")


  public String getApplyType() {
    return applyType;
  }

  public void setApplyType(String applyType) {
    this.applyType = applyType;
  }

  public OldIncomes blockCode(String blockCode) {
    this.blockCode = blockCode;
    return this;
  }

   /**
   * Get blockCode
   * @return blockCode
  **/
  @ApiModelProperty(value = "")


  public String getBlockCode() {
    return blockCode;
  }

  public void setBlockCode(String blockCode) {
    this.blockCode = blockCode;
  }

  public OldIncomes cardNo(String cardNo) {
    this.cardNo = cardNo;
    return this;
  }

   /**
   * Get cardNo
   * @return cardNo
  **/
  @ApiModelProperty(value = "")


  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public OldIncomes cardType(String cardType) {
    this.cardType = cardType;
    return this;
  }

   /**
   * Get cardType
   * @return cardType
  **/
  @ApiModelProperty(value = "")


  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public OldIncomes incomeSource(String incomeSource) {
    this.incomeSource = incomeSource;
    return this;
  }

   /**
   * Get incomeSource
   * @return incomeSource
  **/
  @ApiModelProperty(value = "")


  public String getIncomeSource() {
    return incomeSource;
  }

  public void setIncomeSource(String incomeSource) {
    this.incomeSource = incomeSource;
  }

  public OldIncomes openDate(DateTime openDate) {
    this.openDate = openDate;
    return this;
  }

   /**
   * Get openDate
   * @return openDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getOpenDate() {
    return openDate;
  }

  public void setOpenDate(DateTime openDate) {
    this.openDate = openDate;
  }

  public OldIncomes productId(String productId) {
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

  public OldIncomes sourceData(String sourceData) {
    this.sourceData = sourceData;
    return this;
  }

   /**
   * Get sourceData
   * @return sourceData
  **/
  @ApiModelProperty(value = "")


  public String getSourceData() {
    return sourceData;
  }

  public void setSourceData(String sourceData) {
    this.sourceData = sourceData;
  }

  public OldIncomes typeOfFin(String typeOfFin) {
    this.typeOfFin = typeOfFin;
    return this;
  }

   /**
   * Get typeOfFin
   * @return typeOfFin
  **/
  @ApiModelProperty(value = "")


  public String getTypeOfFin() {
    return typeOfFin;
  }

  public void setTypeOfFin(String typeOfFin) {
    this.typeOfFin = typeOfFin;
  }

  public OldIncomes verifiedIncome(String verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
    return this;
  }

   /**
   * Get verifiedIncome
   * @return verifiedIncome
  **/
  @ApiModelProperty(value = "")


  public String getVerifiedIncome() {
    return verifiedIncome;
  }

  public void setVerifiedIncome(String verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
  }

  public OldIncomes dateSort(BigDecimal dateSort) {
    this.dateSort = dateSort;
    return this;
  }

   /**
   * Get dateSort
   * @return dateSort
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDateSort() {
    return dateSort;
  }

  public void setDateSort(BigDecimal dateSort) {
    this.dateSort = dateSort;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OldIncomes oldIncomes = (OldIncomes) o;
    return Objects.equals(this.accountStatus, oldIncomes.accountStatus) &&
        Objects.equals(this.applyType, oldIncomes.applyType) &&
        Objects.equals(this.blockCode, oldIncomes.blockCode) &&
        Objects.equals(this.cardNo, oldIncomes.cardNo) &&
        Objects.equals(this.cardType, oldIncomes.cardType) &&
        Objects.equals(this.incomeSource, oldIncomes.incomeSource) &&
        Objects.equals(this.openDate, oldIncomes.openDate) &&
        Objects.equals(this.productId, oldIncomes.productId) &&
        Objects.equals(this.sourceData, oldIncomes.sourceData) &&
        Objects.equals(this.typeOfFin, oldIncomes.typeOfFin) &&
        Objects.equals(this.verifiedIncome, oldIncomes.verifiedIncome) &&
        Objects.equals(this.dateSort, oldIncomes.dateSort);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountStatus, applyType, blockCode, cardNo, cardType, incomeSource, openDate, productId, sourceData, typeOfFin, verifiedIncome, dateSort);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OldIncomes {\n");
    
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
    sb.append("    applyType: ").append(toIndentedString(applyType)).append("\n");
    sb.append("    blockCode: ").append(toIndentedString(blockCode)).append("\n");
    sb.append("    cardNo: ").append(toIndentedString(cardNo)).append("\n");
    sb.append("    cardType: ").append(toIndentedString(cardType)).append("\n");
    sb.append("    incomeSource: ").append(toIndentedString(incomeSource)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    sourceData: ").append(toIndentedString(sourceData)).append("\n");
    sb.append("    typeOfFin: ").append(toIndentedString(typeOfFin)).append("\n");
    sb.append("    verifiedIncome: ").append(toIndentedString(verifiedIncome)).append("\n");
    sb.append("    dateSort: ").append(toIndentedString(dateSort)).append("\n");
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

