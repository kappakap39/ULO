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
 * FinancialInstruments
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class FinancialInstruments   {
  @JsonProperty("currentBalance")
  private BigDecimal currentBalance = null;

  @JsonProperty("expireDate")
  private DateTime expireDate = null;

  @JsonProperty("finInstrument")
  private String finInstrument = null;

  @JsonProperty("holderName")
  private String holderName = null;

  @JsonProperty("holdingRatio")
  private BigDecimal holdingRatio = null;

  @JsonProperty("instrumentType")
  private String instrumentType = null;

  @JsonProperty("instrumentTypeDesc")
  private String instrumentTypeDesc = null;

  @JsonProperty("issuerName")
  private String issuerName = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  public FinancialInstruments currentBalance(BigDecimal currentBalance) {
    this.currentBalance = currentBalance;
    return this;
  }

   /**
   * Get currentBalance
   * @return currentBalance
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(BigDecimal currentBalance) {
    this.currentBalance = currentBalance;
  }

  public FinancialInstruments expireDate(DateTime expireDate) {
    this.expireDate = expireDate;
    return this;
  }

   /**
   * Get expireDate
   * @return expireDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(DateTime expireDate) {
    this.expireDate = expireDate;
  }

  public FinancialInstruments finInstrument(String finInstrument) {
    this.finInstrument = finInstrument;
    return this;
  }

   /**
   * Get finInstrument
   * @return finInstrument
  **/
  @ApiModelProperty(value = "")


  public String getFinInstrument() {
    return finInstrument;
  }

  public void setFinInstrument(String finInstrument) {
    this.finInstrument = finInstrument;
  }

  public FinancialInstruments holderName(String holderName) {
    this.holderName = holderName;
    return this;
  }

   /**
   * Get holderName
   * @return holderName
  **/
  @ApiModelProperty(value = "")


  public String getHolderName() {
    return holderName;
  }

  public void setHolderName(String holderName) {
    this.holderName = holderName;
  }

  public FinancialInstruments holdingRatio(BigDecimal holdingRatio) {
    this.holdingRatio = holdingRatio;
    return this;
  }

   /**
   * Get holdingRatio
   * @return holdingRatio
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getHoldingRatio() {
    return holdingRatio;
  }

  public void setHoldingRatio(BigDecimal holdingRatio) {
    this.holdingRatio = holdingRatio;
  }

  public FinancialInstruments instrumentType(String instrumentType) {
    this.instrumentType = instrumentType;
    return this;
  }

   /**
   * Get instrumentType
   * @return instrumentType
  **/
  @ApiModelProperty(value = "")


  public String getInstrumentType() {
    return instrumentType;
  }

  public void setInstrumentType(String instrumentType) {
    this.instrumentType = instrumentType;
  }

  public FinancialInstruments instrumentTypeDesc(String instrumentTypeDesc) {
    this.instrumentTypeDesc = instrumentTypeDesc;
    return this;
  }

   /**
   * Get instrumentTypeDesc
   * @return instrumentTypeDesc
  **/
  @ApiModelProperty(value = "")


  public String getInstrumentTypeDesc() {
    return instrumentTypeDesc;
  }

  public void setInstrumentTypeDesc(String instrumentTypeDesc) {
    this.instrumentTypeDesc = instrumentTypeDesc;
  }

  public FinancialInstruments issuerName(String issuerName) {
    this.issuerName = issuerName;
    return this;
  }

   /**
   * Get issuerName
   * @return issuerName
  **/
  @ApiModelProperty(value = "")


  public String getIssuerName() {
    return issuerName;
  }

  public void setIssuerName(String issuerName) {
    this.issuerName = issuerName;
  }

  public FinancialInstruments openDate(DateTime openDate) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FinancialInstruments financialInstruments = (FinancialInstruments) o;
    return Objects.equals(this.currentBalance, financialInstruments.currentBalance) &&
        Objects.equals(this.expireDate, financialInstruments.expireDate) &&
        Objects.equals(this.finInstrument, financialInstruments.finInstrument) &&
        Objects.equals(this.holderName, financialInstruments.holderName) &&
        Objects.equals(this.holdingRatio, financialInstruments.holdingRatio) &&
        Objects.equals(this.instrumentType, financialInstruments.instrumentType) &&
        Objects.equals(this.instrumentTypeDesc, financialInstruments.instrumentTypeDesc) &&
        Objects.equals(this.issuerName, financialInstruments.issuerName) &&
        Objects.equals(this.openDate, financialInstruments.openDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentBalance, expireDate, finInstrument, holderName, holdingRatio, instrumentType, instrumentTypeDesc, issuerName, openDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FinancialInstruments {\n");
    
    sb.append("    currentBalance: ").append(toIndentedString(currentBalance)).append("\n");
    sb.append("    expireDate: ").append(toIndentedString(expireDate)).append("\n");
    sb.append("    finInstrument: ").append(toIndentedString(finInstrument)).append("\n");
    sb.append("    holderName: ").append(toIndentedString(holderName)).append("\n");
    sb.append("    holdingRatio: ").append(toIndentedString(holdingRatio)).append("\n");
    sb.append("    instrumentType: ").append(toIndentedString(instrumentType)).append("\n");
    sb.append("    instrumentTypeDesc: ").append(toIndentedString(instrumentTypeDesc)).append("\n");
    sb.append("    issuerName: ").append(toIndentedString(issuerName)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
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

