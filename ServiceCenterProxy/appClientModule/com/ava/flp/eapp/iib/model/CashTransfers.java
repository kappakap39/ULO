package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CashTransfers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CashTransfers   {
  @JsonProperty("accountName")
  private String accountName = null;

  @JsonProperty("cashTransferType")
  private String cashTransferType = null;

  @JsonProperty("expressTransfer")
  private String expressTransfer = null;

  @JsonProperty("firstTransferAmount")
  private BigDecimal firstTransferAmount = null;

  @JsonProperty("percentTransfer")
  private BigDecimal percentTransfer = null;

  @JsonProperty("productType")
  private String productType = null;

  @JsonProperty("transferAccount")
  private String transferAccount = null;

  @JsonProperty("callForCashFlag")
  private String callForCashFlag = null;

  public CashTransfers accountName(String accountName) {
    this.accountName = accountName;
    return this;
  }

   /**
   * Get accountName
   * @return accountName
  **/
  @ApiModelProperty(value = "")


  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public CashTransfers cashTransferType(String cashTransferType) {
    this.cashTransferType = cashTransferType;
    return this;
  }

   /**
   * Get cashTransferType
   * @return cashTransferType
  **/
  @ApiModelProperty(value = "")


  public String getCashTransferType() {
    return cashTransferType;
  }

  public void setCashTransferType(String cashTransferType) {
    this.cashTransferType = cashTransferType;
  }

  public CashTransfers expressTransfer(String expressTransfer) {
    this.expressTransfer = expressTransfer;
    return this;
  }

   /**
   * Get expressTransfer
   * @return expressTransfer
  **/
  @ApiModelProperty(value = "")


  public String getExpressTransfer() {
    return expressTransfer;
  }

  public void setExpressTransfer(String expressTransfer) {
    this.expressTransfer = expressTransfer;
  }

  public CashTransfers firstTransferAmount(BigDecimal firstTransferAmount) {
    this.firstTransferAmount = firstTransferAmount;
    return this;
  }

   /**
   * Get firstTransferAmount
   * @return firstTransferAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getFirstTransferAmount() {
    return firstTransferAmount;
  }

  public void setFirstTransferAmount(BigDecimal firstTransferAmount) {
    this.firstTransferAmount = firstTransferAmount;
  }

  public CashTransfers percentTransfer(BigDecimal percentTransfer) {
    this.percentTransfer = percentTransfer;
    return this;
  }

   /**
   * Get percentTransfer
   * @return percentTransfer
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPercentTransfer() {
    return percentTransfer;
  }

  public void setPercentTransfer(BigDecimal percentTransfer) {
    this.percentTransfer = percentTransfer;
  }

  public CashTransfers productType(String productType) {
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

  public CashTransfers transferAccount(String transferAccount) {
    this.transferAccount = transferAccount;
    return this;
  }

   /**
   * Get transferAccount
   * @return transferAccount
  **/
  @ApiModelProperty(value = "")


  public String getTransferAccount() {
    return transferAccount;
  }

  public void setTransferAccount(String transferAccount) {
    this.transferAccount = transferAccount;
  }

  public CashTransfers callForCashFlag(String callForCashFlag) {
    this.callForCashFlag = callForCashFlag;
    return this;
  }

   /**
   * Get callForCashFlag
   * @return callForCashFlag
  **/
  @ApiModelProperty(value = "")


  public String getCallForCashFlag() {
    return callForCashFlag;
  }

  public void setCallForCashFlag(String callForCashFlag) {
    this.callForCashFlag = callForCashFlag;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CashTransfers cashTransfers = (CashTransfers) o;
    return Objects.equals(this.accountName, cashTransfers.accountName) &&
        Objects.equals(this.cashTransferType, cashTransfers.cashTransferType) &&
        Objects.equals(this.expressTransfer, cashTransfers.expressTransfer) &&
        Objects.equals(this.firstTransferAmount, cashTransfers.firstTransferAmount) &&
        Objects.equals(this.percentTransfer, cashTransfers.percentTransfer) &&
        Objects.equals(this.productType, cashTransfers.productType) &&
        Objects.equals(this.transferAccount, cashTransfers.transferAccount) &&
        Objects.equals(this.callForCashFlag, cashTransfers.callForCashFlag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountName, cashTransferType, expressTransfer, firstTransferAmount, percentTransfer, productType, transferAccount, callForCashFlag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CashTransfers {\n");
    
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    cashTransferType: ").append(toIndentedString(cashTransferType)).append("\n");
    sb.append("    expressTransfer: ").append(toIndentedString(expressTransfer)).append("\n");
    sb.append("    firstTransferAmount: ").append(toIndentedString(firstTransferAmount)).append("\n");
    sb.append("    percentTransfer: ").append(toIndentedString(percentTransfer)).append("\n");
    sb.append("    productType: ").append(toIndentedString(productType)).append("\n");
    sb.append("    transferAccount: ").append(toIndentedString(transferAccount)).append("\n");
    sb.append("    callForCashFlag: ").append(toIndentedString(callForCashFlag)).append("\n");
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

