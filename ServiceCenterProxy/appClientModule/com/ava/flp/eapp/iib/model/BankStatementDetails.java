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
 * BankStatementDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class BankStatementDetails   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("bankStatementDetailId")
  private String bankStatementDetailId = null;

  @JsonProperty("bankStatementId")
  private String bankStatementId = null;

  @JsonProperty("month")
  private String month = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("year")
  private String year = null;

  public BankStatementDetails amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BankStatementDetails bankStatementDetailId(String bankStatementDetailId) {
    this.bankStatementDetailId = bankStatementDetailId;
    return this;
  }

   /**
   * Get bankStatementDetailId
   * @return bankStatementDetailId
  **/
  @ApiModelProperty(value = "")


  public String getBankStatementDetailId() {
    return bankStatementDetailId;
  }

  public void setBankStatementDetailId(String bankStatementDetailId) {
    this.bankStatementDetailId = bankStatementDetailId;
  }

  public BankStatementDetails bankStatementId(String bankStatementId) {
    this.bankStatementId = bankStatementId;
    return this;
  }

   /**
   * Get bankStatementId
   * @return bankStatementId
  **/
  @ApiModelProperty(value = "")


  public String getBankStatementId() {
    return bankStatementId;
  }

  public void setBankStatementId(String bankStatementId) {
    this.bankStatementId = bankStatementId;
  }

  public BankStatementDetails month(String month) {
    this.month = month;
    return this;
  }

   /**
   * Get month
   * @return month
  **/
  @ApiModelProperty(value = "")


  public String getMonth() {
    return month;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public BankStatementDetails seq(BigDecimal seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSeq() {
    return seq;
  }

  public void setSeq(BigDecimal seq) {
    this.seq = seq;
  }

  public BankStatementDetails year(String year) {
    this.year = year;
    return this;
  }

   /**
   * Get year
   * @return year
  **/
  @ApiModelProperty(value = "")


  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankStatementDetails bankStatementDetails = (BankStatementDetails) o;
    return Objects.equals(this.amount, bankStatementDetails.amount) &&
        Objects.equals(this.bankStatementDetailId, bankStatementDetails.bankStatementDetailId) &&
        Objects.equals(this.bankStatementId, bankStatementDetails.bankStatementId) &&
        Objects.equals(this.month, bankStatementDetails.month) &&
        Objects.equals(this.seq, bankStatementDetails.seq) &&
        Objects.equals(this.year, bankStatementDetails.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, bankStatementDetailId, bankStatementId, month, seq, year);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankStatementDetails {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    bankStatementDetailId: ").append(toIndentedString(bankStatementDetailId)).append("\n");
    sb.append("    bankStatementId: ").append(toIndentedString(bankStatementId)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
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

