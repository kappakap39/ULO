package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PaymentMethods
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class PaymentMethods   {
  @JsonProperty("accountName")
  private String accountName = null;

  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("accountType")
  private String accountType = null;

  @JsonProperty("bankCode")
  private String bankCode = null;

  @JsonProperty("dueCycle")
  private BigDecimal dueCycle = null;

  @JsonProperty("paymentMethod")
  private String paymentMethod = null;

  @JsonProperty("paymentRatio")
  private BigDecimal paymentRatio = null;

  public PaymentMethods accountName(String accountName) {
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

  public PaymentMethods accountNo(String accountNo) {
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

  public PaymentMethods accountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

   /**
   * Get accountType
   * @return accountType
  **/
  @ApiModelProperty(value = "")


  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public PaymentMethods bankCode(String bankCode) {
    this.bankCode = bankCode;
    return this;
  }

   /**
   * Get bankCode
   * @return bankCode
  **/
  @ApiModelProperty(value = "")


  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public PaymentMethods dueCycle(BigDecimal dueCycle) {
    this.dueCycle = dueCycle;
    return this;
  }

   /**
   * Get dueCycle
   * @return dueCycle
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDueCycle() {
    return dueCycle;
  }

  public void setDueCycle(BigDecimal dueCycle) {
    this.dueCycle = dueCycle;
  }

  public PaymentMethods paymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
    return this;
  }

   /**
   * Get paymentMethod
   * @return paymentMethod
  **/
  @ApiModelProperty(value = "")


  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public PaymentMethods paymentRatio(BigDecimal paymentRatio) {
    this.paymentRatio = paymentRatio;
    return this;
  }

   /**
   * Get paymentRatio
   * @return paymentRatio
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPaymentRatio() {
    return paymentRatio;
  }

  public void setPaymentRatio(BigDecimal paymentRatio) {
    this.paymentRatio = paymentRatio;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentMethods paymentMethods = (PaymentMethods) o;
    return Objects.equals(this.accountName, paymentMethods.accountName) &&
        Objects.equals(this.accountNo, paymentMethods.accountNo) &&
        Objects.equals(this.accountType, paymentMethods.accountType) &&
        Objects.equals(this.bankCode, paymentMethods.bankCode) &&
        Objects.equals(this.dueCycle, paymentMethods.dueCycle) &&
        Objects.equals(this.paymentMethod, paymentMethods.paymentMethod) &&
        Objects.equals(this.paymentRatio, paymentMethods.paymentRatio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountName, accountNo, accountType, bankCode, dueCycle, paymentMethod, paymentRatio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentMethods {\n");
    
    sb.append("    accountName: ").append(toIndentedString(accountName)).append("\n");
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    bankCode: ").append(toIndentedString(bankCode)).append("\n");
    sb.append("    dueCycle: ").append(toIndentedString(dueCycle)).append("\n");
    sb.append("    paymentMethod: ").append(toIndentedString(paymentMethod)).append("\n");
    sb.append("    paymentRatio: ").append(toIndentedString(paymentRatio)).append("\n");
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

