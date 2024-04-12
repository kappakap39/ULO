package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KycInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class KycInfo   {
  @JsonProperty("relationFlag")
  private String relationFlag = null;

  @JsonProperty("relTitleDesc")
  private String relTitleDesc = null;

  @JsonProperty("relName")
  private String relName = null;

  @JsonProperty("relSurname")
  private String relSurname = null;

  @JsonProperty("relPosition")
  private String relPosition = null;

  @JsonProperty("relDetail")
  private String relDetail = null;

  @JsonProperty("workStartDate")
  private String workStartDate = null;

  @JsonProperty("workEndDate")
  private String workEndDate = null;

  @JsonProperty("relTitleName")
  private String relTitleName = null;

  @JsonProperty("customerRiskGrade")
  private String customerRiskGrade = null;

  @JsonProperty("sanctionList")
  private String sanctionList = null;

  public KycInfo relationFlag(String relationFlag) {
    this.relationFlag = relationFlag;
    return this;
  }

   /**
   * Get relationFlag
   * @return relationFlag
  **/
  @ApiModelProperty(value = "")


  public String getRelationFlag() {
    return relationFlag;
  }

  public void setRelationFlag(String relationFlag) {
    this.relationFlag = relationFlag;
  }

  public KycInfo relTitleDesc(String relTitleDesc) {
    this.relTitleDesc = relTitleDesc;
    return this;
  }

   /**
   * Get relTitleDesc
   * @return relTitleDesc
  **/
  @ApiModelProperty(value = "")


  public String getRelTitleDesc() {
    return relTitleDesc;
  }

  public void setRelTitleDesc(String relTitleDesc) {
    this.relTitleDesc = relTitleDesc;
  }

  public KycInfo relName(String relName) {
    this.relName = relName;
    return this;
  }

   /**
   * Get relName
   * @return relName
  **/
  @ApiModelProperty(value = "")


  public String getRelName() {
    return relName;
  }

  public void setRelName(String relName) {
    this.relName = relName;
  }

  public KycInfo relSurname(String relSurname) {
    this.relSurname = relSurname;
    return this;
  }

   /**
   * Get relSurname
   * @return relSurname
  **/
  @ApiModelProperty(value = "")


  public String getRelSurname() {
    return relSurname;
  }

  public void setRelSurname(String relSurname) {
    this.relSurname = relSurname;
  }

  public KycInfo relPosition(String relPosition) {
    this.relPosition = relPosition;
    return this;
  }

   /**
   * Get relPosition
   * @return relPosition
  **/
  @ApiModelProperty(value = "")


  public String getRelPosition() {
    return relPosition;
  }

  public void setRelPosition(String relPosition) {
    this.relPosition = relPosition;
  }

  public KycInfo relDetail(String relDetail) {
    this.relDetail = relDetail;
    return this;
  }

   /**
   * Get relDetail
   * @return relDetail
  **/
  @ApiModelProperty(value = "")


  public String getRelDetail() {
    return relDetail;
  }

  public void setRelDetail(String relDetail) {
    this.relDetail = relDetail;
  }

  public KycInfo workStartDate(String workStartDate) {
    this.workStartDate = workStartDate;
    return this;
  }

   /**
   * Get workStartDate
   * @return workStartDate
  **/
  @ApiModelProperty(value = "")


  public String getWorkStartDate() {
    return workStartDate;
  }

  public void setWorkStartDate(String workStartDate) {
    this.workStartDate = workStartDate;
  }

  public KycInfo workEndDate(String workEndDate) {
    this.workEndDate = workEndDate;
    return this;
  }

   /**
   * Get workEndDate
   * @return workEndDate
  **/
  @ApiModelProperty(value = "")


  public String getWorkEndDate() {
    return workEndDate;
  }

  public void setWorkEndDate(String workEndDate) {
    this.workEndDate = workEndDate;
  }

  public KycInfo relTitleName(String relTitleName) {
    this.relTitleName = relTitleName;
    return this;
  }

   /**
   * Get relTitleName
   * @return relTitleName
  **/
  @ApiModelProperty(value = "")


  public String getRelTitleName() {
    return relTitleName;
  }

  public void setRelTitleName(String relTitleName) {
    this.relTitleName = relTitleName;
  }

  public KycInfo customerRiskGrade(String customerRiskGrade) {
    this.customerRiskGrade = customerRiskGrade;
    return this;
  }

   /**
   * Get customerRiskGrade
   * @return customerRiskGrade
  **/
  @ApiModelProperty(value = "")


  public String getCustomerRiskGrade() {
    return customerRiskGrade;
  }

  public void setCustomerRiskGrade(String customerRiskGrade) {
    this.customerRiskGrade = customerRiskGrade;
  }

  public KycInfo sanctionList(String sanctionList) {
    this.sanctionList = sanctionList;
    return this;
  }

   /**
   * Get sanctionList
   * @return sanctionList
  **/
  @ApiModelProperty(value = "")


  public String getSanctionList() {
    return sanctionList;
  }

  public void setSanctionList(String sanctionList) {
    this.sanctionList = sanctionList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KycInfo kycInfo = (KycInfo) o;
    return Objects.equals(this.relationFlag, kycInfo.relationFlag) &&
        Objects.equals(this.relTitleDesc, kycInfo.relTitleDesc) &&
        Objects.equals(this.relName, kycInfo.relName) &&
        Objects.equals(this.relSurname, kycInfo.relSurname) &&
        Objects.equals(this.relPosition, kycInfo.relPosition) &&
        Objects.equals(this.relDetail, kycInfo.relDetail) &&
        Objects.equals(this.workStartDate, kycInfo.workStartDate) &&
        Objects.equals(this.workEndDate, kycInfo.workEndDate) &&
        Objects.equals(this.relTitleName, kycInfo.relTitleName) &&
        Objects.equals(this.customerRiskGrade, kycInfo.customerRiskGrade) &&
        Objects.equals(this.sanctionList, kycInfo.sanctionList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(relationFlag, relTitleDesc, relName, relSurname, relPosition, relDetail, workStartDate, workEndDate, relTitleName, customerRiskGrade, sanctionList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KycInfo {\n");
    
    sb.append("    relationFlag: ").append(toIndentedString(relationFlag)).append("\n");
    sb.append("    relTitleDesc: ").append(toIndentedString(relTitleDesc)).append("\n");
    sb.append("    relName: ").append(toIndentedString(relName)).append("\n");
    sb.append("    relSurname: ").append(toIndentedString(relSurname)).append("\n");
    sb.append("    relPosition: ").append(toIndentedString(relPosition)).append("\n");
    sb.append("    relDetail: ").append(toIndentedString(relDetail)).append("\n");
    sb.append("    workStartDate: ").append(toIndentedString(workStartDate)).append("\n");
    sb.append("    workEndDate: ").append(toIndentedString(workEndDate)).append("\n");
    sb.append("    relTitleName: ").append(toIndentedString(relTitleName)).append("\n");
    sb.append("    customerRiskGrade: ").append(toIndentedString(customerRiskGrade)).append("\n");
    sb.append("    sanctionList: ").append(toIndentedString(sanctionList)).append("\n");
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

