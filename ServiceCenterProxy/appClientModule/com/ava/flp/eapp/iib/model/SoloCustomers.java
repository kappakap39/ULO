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
 * SoloCustomers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class SoloCustomers   {
  @JsonProperty("birthDate")
  private DateTime birthDate = null;

  @JsonProperty("citizenId")
  private String citizenId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("surname")
  private String surname = null;

  public SoloCustomers birthDate(DateTime birthDate) {
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

  public SoloCustomers citizenId(String citizenId) {
    this.citizenId = citizenId;
    return this;
  }

   /**
   * Get citizenId
   * @return citizenId
  **/
  @ApiModelProperty(value = "")


  public String getCitizenId() {
    return citizenId;
  }

  public void setCitizenId(String citizenId) {
    this.citizenId = citizenId;
  }

  public SoloCustomers name(String name) {
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

  public SoloCustomers surname(String surname) {
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
    SoloCustomers soloCustomers = (SoloCustomers) o;
    return Objects.equals(this.birthDate, soloCustomers.birthDate) &&
        Objects.equals(this.citizenId, soloCustomers.citizenId) &&
        Objects.equals(this.name, soloCustomers.name) &&
        Objects.equals(this.surname, soloCustomers.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(birthDate, citizenId, name, surname);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoloCustomers {\n");
    
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    citizenId: ").append(toIndentedString(citizenId)).append("\n");
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

