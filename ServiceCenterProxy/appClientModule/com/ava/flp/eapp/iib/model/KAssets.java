package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.MonthEndBalance;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KAssets
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KAssets   {
  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("assetType")
  private String assetType = null;

  @JsonProperty("countOwner")
  private BigDecimal countOwner = null;

  @JsonProperty("fundId")
  private String fundId = null;

  @JsonProperty("fundType")
  private String fundType = null;

  @JsonProperty("maturityDate")
  private DateTime maturityDate = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("operationFlag")
  private String operationFlag = null;

  @JsonProperty("outstanding")
  private BigDecimal outstanding = null;

  @JsonProperty("referenceId")
  private String referenceId = null;

  @JsonProperty("subAccountNo")
  private String subAccountNo = null;

  @JsonProperty("monthEndBalance")
  private List<MonthEndBalance> monthEndBalance = null;

  public KAssets accountNo(String accountNo) {
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

  public KAssets assetType(String assetType) {
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

  public KAssets countOwner(BigDecimal countOwner) {
    this.countOwner = countOwner;
    return this;
  }

   /**
   * Get countOwner
   * @return countOwner
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCountOwner() {
    return countOwner;
  }

  public void setCountOwner(BigDecimal countOwner) {
    this.countOwner = countOwner;
  }

  public KAssets fundId(String fundId) {
    this.fundId = fundId;
    return this;
  }

   /**
   * Get fundId
   * @return fundId
  **/
  @ApiModelProperty(value = "")


  public String getFundId() {
    return fundId;
  }

  public void setFundId(String fundId) {
    this.fundId = fundId;
  }

  public KAssets fundType(String fundType) {
    this.fundType = fundType;
    return this;
  }

   /**
   * Get fundType
   * @return fundType
  **/
  @ApiModelProperty(value = "")


  public String getFundType() {
    return fundType;
  }

  public void setFundType(String fundType) {
    this.fundType = fundType;
  }

  public KAssets maturityDate(DateTime maturityDate) {
    this.maturityDate = maturityDate;
    return this;
  }

   /**
   * Get maturityDate
   * @return maturityDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getMaturityDate() {
    return maturityDate;
  }

  public void setMaturityDate(DateTime maturityDate) {
    this.maturityDate = maturityDate;
  }

  public KAssets openDate(DateTime openDate) {
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

  public KAssets operationFlag(String operationFlag) {
    this.operationFlag = operationFlag;
    return this;
  }

   /**
   * Get operationFlag
   * @return operationFlag
  **/
  @ApiModelProperty(value = "")


  public String getOperationFlag() {
    return operationFlag;
  }

  public void setOperationFlag(String operationFlag) {
    this.operationFlag = operationFlag;
  }

  public KAssets outstanding(BigDecimal outstanding) {
    this.outstanding = outstanding;
    return this;
  }

   /**
   * Get outstanding
   * @return outstanding
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getOutstanding() {
    return outstanding;
  }

  public void setOutstanding(BigDecimal outstanding) {
    this.outstanding = outstanding;
  }

  public KAssets referenceId(String referenceId) {
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

  public KAssets subAccountNo(String subAccountNo) {
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

  public KAssets monthEndBalance(List<MonthEndBalance> monthEndBalance) {
    this.monthEndBalance = monthEndBalance;
    return this;
  }

  public KAssets addMonthEndBalanceItem(MonthEndBalance monthEndBalanceItem) {
    if (this.monthEndBalance == null) {
      this.monthEndBalance = new ArrayList<MonthEndBalance>();
    }
    this.monthEndBalance.add(monthEndBalanceItem);
    return this;
  }

   /**
   * Get monthEndBalance
   * @return monthEndBalance
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<MonthEndBalance> getMonthEndBalance() {
    return monthEndBalance;
  }

  public void setMonthEndBalance(List<MonthEndBalance> monthEndBalance) {
    this.monthEndBalance = monthEndBalance;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KAssets kassets = (KAssets) o;
    return Objects.equals(this.accountNo, kassets.accountNo) &&
        Objects.equals(this.assetType, kassets.assetType) &&
        Objects.equals(this.countOwner, kassets.countOwner) &&
        Objects.equals(this.fundId, kassets.fundId) &&
        Objects.equals(this.fundType, kassets.fundType) &&
        Objects.equals(this.maturityDate, kassets.maturityDate) &&
        Objects.equals(this.openDate, kassets.openDate) &&
        Objects.equals(this.operationFlag, kassets.operationFlag) &&
        Objects.equals(this.outstanding, kassets.outstanding) &&
        Objects.equals(this.referenceId, kassets.referenceId) &&
        Objects.equals(this.subAccountNo, kassets.subAccountNo) &&
        Objects.equals(this.monthEndBalance, kassets.monthEndBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, assetType, countOwner, fundId, fundType, maturityDate, openDate, operationFlag, outstanding, referenceId, subAccountNo, monthEndBalance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KAssets {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    assetType: ").append(toIndentedString(assetType)).append("\n");
    sb.append("    countOwner: ").append(toIndentedString(countOwner)).append("\n");
    sb.append("    fundId: ").append(toIndentedString(fundId)).append("\n");
    sb.append("    fundType: ").append(toIndentedString(fundType)).append("\n");
    sb.append("    maturityDate: ").append(toIndentedString(maturityDate)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    operationFlag: ").append(toIndentedString(operationFlag)).append("\n");
    sb.append("    outstanding: ").append(toIndentedString(outstanding)).append("\n");
    sb.append("    referenceId: ").append(toIndentedString(referenceId)).append("\n");
    sb.append("    subAccountNo: ").append(toIndentedString(subAccountNo)).append("\n");
    sb.append("    monthEndBalance: ").append(toIndentedString(monthEndBalance)).append("\n");
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

