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
 * StableIncome
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class StableIncome   {
  @JsonProperty("potentialIncomeAmt")
  private BigDecimal potentialIncomeAmt = null;

  @JsonProperty("bonusAmt")
  private BigDecimal bonusAmt = null;

  @JsonProperty("stableIncomeFlag")
  private String stableIncomeFlag = null;

  public StableIncome potentialIncomeAmt(BigDecimal potentialIncomeAmt) {
    this.potentialIncomeAmt = potentialIncomeAmt;
    return this;
  }

   /**
   * Get potentialIncomeAmt
   * @return potentialIncomeAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPotentialIncomeAmt() {
    return potentialIncomeAmt;
  }

  public void setPotentialIncomeAmt(BigDecimal potentialIncomeAmt) {
    this.potentialIncomeAmt = potentialIncomeAmt;
  }

  public StableIncome bonusAmt(BigDecimal bonusAmt) {
    this.bonusAmt = bonusAmt;
    return this;
  }

   /**
   * Get bonusAmt
   * @return bonusAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBonusAmt() {
    return bonusAmt;
  }

  public void setBonusAmt(BigDecimal bonusAmt) {
    this.bonusAmt = bonusAmt;
  }

  public StableIncome stableIncomeFlag(String stableIncomeFlag) {
    this.stableIncomeFlag = stableIncomeFlag;
    return this;
  }

   /**
   * Get stableIncomeFlag
   * @return stableIncomeFlag
  **/
  @ApiModelProperty(value = "")


  public String getStableIncomeFlag() {
    return stableIncomeFlag;
  }

  public void setStableIncomeFlag(String stableIncomeFlag) {
    this.stableIncomeFlag = stableIncomeFlag;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StableIncome stableIncome = (StableIncome) o;
    return Objects.equals(this.potentialIncomeAmt, stableIncome.potentialIncomeAmt) &&
        Objects.equals(this.bonusAmt, stableIncome.bonusAmt) &&
        Objects.equals(this.stableIncomeFlag, stableIncome.stableIncomeFlag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(potentialIncomeAmt, bonusAmt, stableIncomeFlag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StableIncome {\n");
    
    sb.append("    potentialIncomeAmt: ").append(toIndentedString(potentialIncomeAmt)).append("\n");
    sb.append("    bonusAmt: ").append(toIndentedString(bonusAmt)).append("\n");
    sb.append("    stableIncomeFlag: ").append(toIndentedString(stableIncomeFlag)).append("\n");
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

