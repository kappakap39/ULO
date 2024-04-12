package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CoBrand
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CoBrand   {
  @JsonProperty("dateOfBirth")
  private String dateOfBirth = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("programId")
  private String programId = null;

  @JsonProperty("surname")
  private String surname = null;

  public CoBrand dateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

   /**
   * Get dateOfBirth
   * @return dateOfBirth
  **/
  @ApiModelProperty(value = "")


  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public CoBrand description(String description) {
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

  public CoBrand idNo(String idNo) {
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

  public CoBrand name(String name) {
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

  public CoBrand programId(String programId) {
    this.programId = programId;
    return this;
  }

   /**
   * Get programId
   * @return programId
  **/
  @ApiModelProperty(value = "")


  public String getProgramId() {
    return programId;
  }

  public void setProgramId(String programId) {
    this.programId = programId;
  }

  public CoBrand surname(String surname) {
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
    CoBrand coBrand = (CoBrand) o;
    return Objects.equals(this.dateOfBirth, coBrand.dateOfBirth) &&
        Objects.equals(this.description, coBrand.description) &&
        Objects.equals(this.idNo, coBrand.idNo) &&
        Objects.equals(this.name, coBrand.name) &&
        Objects.equals(this.programId, coBrand.programId) &&
        Objects.equals(this.surname, coBrand.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dateOfBirth, description, idNo, name, programId, surname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CoBrand {\n");
    
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    programId: ").append(toIndentedString(programId)).append("\n");
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

