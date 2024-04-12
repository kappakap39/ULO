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
 * DebtInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class DebtInfo   {
  @JsonProperty("debtCommercialAmount")
  private BigDecimal debtCommercialAmount = null;

  @JsonProperty("welfareAmount")
  private BigDecimal welfareAmount = null;

  @JsonProperty("otherDebtAmount")
  private BigDecimal otherDebtAmount = null;

  @JsonProperty("totalOtherDebtAmount")
  private BigDecimal totalOtherDebtAmount = null;

  public DebtInfo debtCommercialAmount(BigDecimal debtCommercialAmount) {
    this.debtCommercialAmount = debtCommercialAmount;
    return this;
  }

   /**
   * Get debtCommercialAmount
   * @return debtCommercialAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDebtCommercialAmount() {
    return debtCommercialAmount;
  }

  public void setDebtCommercialAmount(BigDecimal debtCommercialAmount) {
    this.debtCommercialAmount = debtCommercialAmount;
  }

  public DebtInfo welfareAmount(BigDecimal welfareAmount) {
    this.welfareAmount = welfareAmount;
    return this;
  }

   /**
   * Get welfareAmount
   * @return welfareAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getWelfareAmount() {
    return welfareAmount;
  }

  public void setWelfareAmount(BigDecimal welfareAmount) {
    this.welfareAmount = welfareAmount;
  }

  public DebtInfo otherDebtAmount(BigDecimal otherDebtAmount) {
    this.otherDebtAmount = otherDebtAmount;
    return this;
  }

   /**
   * Get otherDebtAmount
   * @return otherDebtAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getOtherDebtAmount() {
    return otherDebtAmount;
  }

  public void setOtherDebtAmount(BigDecimal otherDebtAmount) {
    this.otherDebtAmount = otherDebtAmount;
  }

  public DebtInfo totalOtherDebtAmount(BigDecimal totalOtherDebtAmount) {
    this.totalOtherDebtAmount = totalOtherDebtAmount;
    return this;
  }

   /**
   * Get totalOtherDebtAmount
   * @return totalOtherDebtAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTotalOtherDebtAmount() {
    return totalOtherDebtAmount;
  }

  public void setTotalOtherDebtAmount(BigDecimal totalOtherDebtAmount) {
    this.totalOtherDebtAmount = totalOtherDebtAmount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DebtInfo debtInfo = (DebtInfo) o;
    return Objects.equals(this.debtCommercialAmount, debtInfo.debtCommercialAmount) &&
        Objects.equals(this.welfareAmount, debtInfo.welfareAmount) &&
        Objects.equals(this.otherDebtAmount, debtInfo.otherDebtAmount) &&
        Objects.equals(this.totalOtherDebtAmount, debtInfo.totalOtherDebtAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(debtCommercialAmount, welfareAmount, otherDebtAmount, totalOtherDebtAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DebtInfo {\n");
    
    sb.append("    debtCommercialAmount: ").append(toIndentedString(debtCommercialAmount)).append("\n");
    sb.append("    welfareAmount: ").append(toIndentedString(welfareAmount)).append("\n");
    sb.append("    otherDebtAmount: ").append(toIndentedString(otherDebtAmount)).append("\n");
    sb.append("    totalOtherDebtAmount: ").append(toIndentedString(totalOtherDebtAmount)).append("\n");
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

