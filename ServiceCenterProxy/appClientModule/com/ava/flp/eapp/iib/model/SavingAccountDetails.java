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
 * SavingAccountDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class SavingAccountDetails   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("month")
  private String month = null;

  @JsonProperty("savingAccDetailId")
  private String savingAccDetailId = null;

  @JsonProperty("savingAccId")
  private String savingAccId = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("year")
  private String year = null;

  public SavingAccountDetails amount(BigDecimal amount) {
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

  public SavingAccountDetails month(String month) {
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

  public SavingAccountDetails savingAccDetailId(String savingAccDetailId) {
    this.savingAccDetailId = savingAccDetailId;
    return this;
  }

   /**
   * Get savingAccDetailId
   * @return savingAccDetailId
  **/
  @ApiModelProperty(value = "")


  public String getSavingAccDetailId() {
    return savingAccDetailId;
  }

  public void setSavingAccDetailId(String savingAccDetailId) {
    this.savingAccDetailId = savingAccDetailId;
  }

  public SavingAccountDetails savingAccId(String savingAccId) {
    this.savingAccId = savingAccId;
    return this;
  }

   /**
   * Get savingAccId
   * @return savingAccId
  **/
  @ApiModelProperty(value = "")


  public String getSavingAccId() {
    return savingAccId;
  }

  public void setSavingAccId(String savingAccId) {
    this.savingAccId = savingAccId;
  }

  public SavingAccountDetails seq(BigDecimal seq) {
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

  public SavingAccountDetails year(String year) {
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
    SavingAccountDetails savingAccountDetails = (SavingAccountDetails) o;
    return Objects.equals(this.amount, savingAccountDetails.amount) &&
        Objects.equals(this.month, savingAccountDetails.month) &&
        Objects.equals(this.savingAccDetailId, savingAccountDetails.savingAccDetailId) &&
        Objects.equals(this.savingAccId, savingAccountDetails.savingAccId) &&
        Objects.equals(this.seq, savingAccountDetails.seq) &&
        Objects.equals(this.year, savingAccountDetails.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, month, savingAccDetailId, savingAccId, seq, year);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SavingAccountDetails {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    savingAccDetailId: ").append(toIndentedString(savingAccDetailId)).append("\n");
    sb.append("    savingAccId: ").append(toIndentedString(savingAccId)).append("\n");
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

