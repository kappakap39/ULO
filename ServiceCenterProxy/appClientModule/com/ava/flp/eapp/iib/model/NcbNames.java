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
 * NcbNames
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbNames   {
  @JsonProperty("consentToEnquire")
  private String consentToEnquire = null;

  @JsonProperty("dateOfBirth")
  private DateTime dateOfBirth = null;

  @JsonProperty("familyName1")
  private String familyName1 = null;

  @JsonProperty("familyName2")
  private String familyName2 = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("gender")
  private String gender = null;

  @JsonProperty("maritalStatus")
  private String maritalStatus = null;

  @JsonProperty("middle")
  private String middle = null;

  @JsonProperty("nationality")
  private String nationality = null;

  @JsonProperty("numberOfChildren")
  private String numberOfChildren = null;

  @JsonProperty("occupation")
  private String occupation = null;

  @JsonProperty("segmentValue")
  private String segmentValue = null;

  @JsonProperty("spouseName")
  private String spouseName = null;

  @JsonProperty("title")
  private String title = null;

  public NcbNames consentToEnquire(String consentToEnquire) {
    this.consentToEnquire = consentToEnquire;
    return this;
  }

   /**
   * Get consentToEnquire
   * @return consentToEnquire
  **/
  @ApiModelProperty(value = "")


  public String getConsentToEnquire() {
    return consentToEnquire;
  }

  public void setConsentToEnquire(String consentToEnquire) {
    this.consentToEnquire = consentToEnquire;
  }

  public NcbNames dateOfBirth(DateTime dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

   /**
   * Get dateOfBirth
   * @return dateOfBirth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(DateTime dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public NcbNames familyName1(String familyName1) {
    this.familyName1 = familyName1;
    return this;
  }

   /**
   * Get familyName1
   * @return familyName1
  **/
  @ApiModelProperty(value = "")


  public String getFamilyName1() {
    return familyName1;
  }

  public void setFamilyName1(String familyName1) {
    this.familyName1 = familyName1;
  }

  public NcbNames familyName2(String familyName2) {
    this.familyName2 = familyName2;
    return this;
  }

   /**
   * Get familyName2
   * @return familyName2
  **/
  @ApiModelProperty(value = "")


  public String getFamilyName2() {
    return familyName2;
  }

  public void setFamilyName2(String familyName2) {
    this.familyName2 = familyName2;
  }

  public NcbNames firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @ApiModelProperty(value = "")


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public NcbNames gender(String gender) {
    this.gender = gender;
    return this;
  }

   /**
   * Get gender
   * @return gender
  **/
  @ApiModelProperty(value = "")


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public NcbNames maritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
    return this;
  }

   /**
   * Get maritalStatus
   * @return maritalStatus
  **/
  @ApiModelProperty(value = "")


  public String getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public NcbNames middle(String middle) {
    this.middle = middle;
    return this;
  }

   /**
   * Get middle
   * @return middle
  **/
  @ApiModelProperty(value = "")


  public String getMiddle() {
    return middle;
  }

  public void setMiddle(String middle) {
    this.middle = middle;
  }

  public NcbNames nationality(String nationality) {
    this.nationality = nationality;
    return this;
  }

   /**
   * Get nationality
   * @return nationality
  **/
  @ApiModelProperty(value = "")


  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public NcbNames numberOfChildren(String numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
    return this;
  }

   /**
   * Get numberOfChildren
   * @return numberOfChildren
  **/
  @ApiModelProperty(value = "")


  public String getNumberOfChildren() {
    return numberOfChildren;
  }

  public void setNumberOfChildren(String numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
  }

  public NcbNames occupation(String occupation) {
    this.occupation = occupation;
    return this;
  }

   /**
   * Get occupation
   * @return occupation
  **/
  @ApiModelProperty(value = "")


  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public NcbNames segmentValue(String segmentValue) {
    this.segmentValue = segmentValue;
    return this;
  }

   /**
   * Get segmentValue
   * @return segmentValue
  **/
  @ApiModelProperty(value = "")


  public String getSegmentValue() {
    return segmentValue;
  }

  public void setSegmentValue(String segmentValue) {
    this.segmentValue = segmentValue;
  }

  public NcbNames spouseName(String spouseName) {
    this.spouseName = spouseName;
    return this;
  }

   /**
   * Get spouseName
   * @return spouseName
  **/
  @ApiModelProperty(value = "")


  public String getSpouseName() {
    return spouseName;
  }

  public void setSpouseName(String spouseName) {
    this.spouseName = spouseName;
  }

  public NcbNames title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @ApiModelProperty(value = "")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbNames ncbNames = (NcbNames) o;
    return Objects.equals(this.consentToEnquire, ncbNames.consentToEnquire) &&
        Objects.equals(this.dateOfBirth, ncbNames.dateOfBirth) &&
        Objects.equals(this.familyName1, ncbNames.familyName1) &&
        Objects.equals(this.familyName2, ncbNames.familyName2) &&
        Objects.equals(this.firstName, ncbNames.firstName) &&
        Objects.equals(this.gender, ncbNames.gender) &&
        Objects.equals(this.maritalStatus, ncbNames.maritalStatus) &&
        Objects.equals(this.middle, ncbNames.middle) &&
        Objects.equals(this.nationality, ncbNames.nationality) &&
        Objects.equals(this.numberOfChildren, ncbNames.numberOfChildren) &&
        Objects.equals(this.occupation, ncbNames.occupation) &&
        Objects.equals(this.segmentValue, ncbNames.segmentValue) &&
        Objects.equals(this.spouseName, ncbNames.spouseName) &&
        Objects.equals(this.title, ncbNames.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consentToEnquire, dateOfBirth, familyName1, familyName2, firstName, gender, maritalStatus, middle, nationality, numberOfChildren, occupation, segmentValue, spouseName, title);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbNames {\n");
    
    sb.append("    consentToEnquire: ").append(toIndentedString(consentToEnquire)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    familyName1: ").append(toIndentedString(familyName1)).append("\n");
    sb.append("    familyName2: ").append(toIndentedString(familyName2)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    maritalStatus: ").append(toIndentedString(maritalStatus)).append("\n");
    sb.append("    middle: ").append(toIndentedString(middle)).append("\n");
    sb.append("    nationality: ").append(toIndentedString(nationality)).append("\n");
    sb.append("    numberOfChildren: ").append(toIndentedString(numberOfChildren)).append("\n");
    sb.append("    occupation: ").append(toIndentedString(occupation)).append("\n");
    sb.append("    segmentValue: ").append(toIndentedString(segmentValue)).append("\n");
    sb.append("    spouseName: ").append(toIndentedString(spouseName)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

