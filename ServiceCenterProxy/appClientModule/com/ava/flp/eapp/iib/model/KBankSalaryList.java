package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KBankSalaryList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KBankSalaryList   {
  @JsonProperty("averageSalary")
  private String averageSalary = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("surname")
  private String surname = null;

  public KBankSalaryList averageSalary(String averageSalary) {
    this.averageSalary = averageSalary;
    return this;
  }

   /**
   * Get averageSalary
   * @return averageSalary
  **/
  @ApiModelProperty(value = "")


  public String getAverageSalary() {
    return averageSalary;
  }

  public void setAverageSalary(String averageSalary) {
    this.averageSalary = averageSalary;
  }

  public KBankSalaryList idNo(String idNo) {
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

  public KBankSalaryList description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public KBankSalaryList name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public KBankSalaryList surname(String surname) {
    this.surname = surname;
    return this;
  }

   /**
   * Get surname
   * @return surname
  **/
  @ApiModelProperty(value = "")


  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KBankSalaryList kbankSalaryList = (KBankSalaryList) o;
    return Objects.equals(this.averageSalary, kbankSalaryList.averageSalary) &&
        Objects.equals(this.idNo, kbankSalaryList.idNo) &&
        Objects.equals(this.description, kbankSalaryList.description) &&
        Objects.equals(this.name, kbankSalaryList.name) &&
        Objects.equals(this.surname, kbankSalaryList.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(averageSalary, idNo, description, name, surname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KBankSalaryList {\n");
    
    sb.append("    averageSalary: ").append(toIndentedString(averageSalary)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    surname: ").append(toIndentedString(surname)).append("\n");
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

