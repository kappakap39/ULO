package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KBankEmployee
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KBankEmployee   {
  @JsonProperty("birthDate")
  private DateTime birthDate = null;

  @JsonProperty("corpTitleCode")
  private String corpTitleCode = null;

  @JsonProperty("empId")
  private String empId = null;

  @JsonProperty("enFirstName")
  private String enFirstName = null;

  @JsonProperty("enLastName")
  private String enLastName = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("jobCode")
  private String jobCode = null;

  @JsonProperty("jobLayerCode")
  private String jobLayerCode = null;

  @JsonProperty("thFirstName")
  private String thFirstName = null;

  @JsonProperty("thLastName")
  private String thLastName = null;

  public KBankEmployee birthDate(DateTime birthDate) {
    this.birthDate = birthDate;
    return this;
  }

   /**
   * Get birthDate
   * @return birthDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(DateTime birthDate) {
    this.birthDate = birthDate;
  }

  public KBankEmployee corpTitleCode(String corpTitleCode) {
    this.corpTitleCode = corpTitleCode;
    return this;
  }

   /**
   * Get corpTitleCode
   * @return corpTitleCode
  **/
  @ApiModelProperty(value = "")


  public String getCorpTitleCode() {
    return corpTitleCode;
  }

  public void setCorpTitleCode(String corpTitleCode) {
    this.corpTitleCode = corpTitleCode;
  }

  public KBankEmployee empId(String empId) {
    this.empId = empId;
    return this;
  }

   /**
   * Get empId
   * @return empId
  **/
  @ApiModelProperty(value = "")


  public String getEmpId() {
    return empId;
  }

  public void setEmpId(String empId) {
    this.empId = empId;
  }

  public KBankEmployee enFirstName(String enFirstName) {
    this.enFirstName = enFirstName;
    return this;
  }

   /**
   * Get enFirstName
   * @return enFirstName
  **/
  @ApiModelProperty(value = "")


  public String getEnFirstName() {
    return enFirstName;
  }

  public void setEnFirstName(String enFirstName) {
    this.enFirstName = enFirstName;
  }

  public KBankEmployee enLastName(String enLastName) {
    this.enLastName = enLastName;
    return this;
  }

   /**
   * Get enLastName
   * @return enLastName
  **/
  @ApiModelProperty(value = "")


  public String getEnLastName() {
    return enLastName;
  }

  public void setEnLastName(String enLastName) {
    this.enLastName = enLastName;
  }

  public KBankEmployee idNo(String idNo) {
    this.idNo = idNo;
    return this;
  }

   /**
   * Get idNo
   * @return idNo
  **/
  @ApiModelProperty(value = "")


  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public KBankEmployee jobCode(String jobCode) {
    this.jobCode = jobCode;
    return this;
  }

   /**
   * Get jobCode
   * @return jobCode
  **/
  @ApiModelProperty(value = "")


  public String getJobCode() {
    return jobCode;
  }

  public void setJobCode(String jobCode) {
    this.jobCode = jobCode;
  }

  public KBankEmployee jobLayerCode(String jobLayerCode) {
    this.jobLayerCode = jobLayerCode;
    return this;
  }

   /**
   * Get jobLayerCode
   * @return jobLayerCode
  **/
  @ApiModelProperty(value = "")


  public String getJobLayerCode() {
    return jobLayerCode;
  }

  public void setJobLayerCode(String jobLayerCode) {
    this.jobLayerCode = jobLayerCode;
  }

  public KBankEmployee thFirstName(String thFirstName) {
    this.thFirstName = thFirstName;
    return this;
  }

   /**
   * Get thFirstName
   * @return thFirstName
  **/
  @ApiModelProperty(value = "")


  public String getThFirstName() {
    return thFirstName;
  }

  public void setThFirstName(String thFirstName) {
    this.thFirstName = thFirstName;
  }

  public KBankEmployee thLastName(String thLastName) {
    this.thLastName = thLastName;
    return this;
  }

   /**
   * Get thLastName
   * @return thLastName
  **/
  @ApiModelProperty(value = "")


  public String getThLastName() {
    return thLastName;
  }

  public void setThLastName(String thLastName) {
    this.thLastName = thLastName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KBankEmployee kbankEmployee = (KBankEmployee) o;
    return Objects.equals(this.birthDate, kbankEmployee.birthDate) &&
        Objects.equals(this.corpTitleCode, kbankEmployee.corpTitleCode) &&
        Objects.equals(this.empId, kbankEmployee.empId) &&
        Objects.equals(this.enFirstName, kbankEmployee.enFirstName) &&
        Objects.equals(this.enLastName, kbankEmployee.enLastName) &&
        Objects.equals(this.idNo, kbankEmployee.idNo) &&
        Objects.equals(this.jobCode, kbankEmployee.jobCode) &&
        Objects.equals(this.jobLayerCode, kbankEmployee.jobLayerCode) &&
        Objects.equals(this.thFirstName, kbankEmployee.thFirstName) &&
        Objects.equals(this.thLastName, kbankEmployee.thLastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(birthDate, corpTitleCode, empId, enFirstName, enLastName, idNo, jobCode, jobLayerCode, thFirstName, thLastName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KBankEmployee {\n");
    
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    corpTitleCode: ").append(toIndentedString(corpTitleCode)).append("\n");
    sb.append("    empId: ").append(toIndentedString(empId)).append("\n");
    sb.append("    enFirstName: ").append(toIndentedString(enFirstName)).append("\n");
    sb.append("    enLastName: ").append(toIndentedString(enLastName)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    jobCode: ").append(toIndentedString(jobCode)).append("\n");
    sb.append("    jobLayerCode: ").append(toIndentedString(jobLayerCode)).append("\n");
    sb.append("    thFirstName: ").append(toIndentedString(thFirstName)).append("\n");
    sb.append("    thLastName: ").append(toIndentedString(thLastName)).append("\n");
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

