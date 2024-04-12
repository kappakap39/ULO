package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NcbNamesTUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbNamesTUEF   {
  @JsonProperty("segmentTag")
  private String segmentTag = null;

  @JsonProperty("familyName1")
  private String familyName1 = null;

  @JsonProperty("familyName2")
  private String familyName2 = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("middle")
  private String middle = null;

  @JsonProperty("maritalStatus")
  private String maritalStatus = null;

  @JsonProperty("dateOfBirth")
  private DateTime dateOfBirth = null;

  @JsonProperty("gender")
  private String gender = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("nationality")
  private String nationality = null;

  @JsonProperty("numberOfChildren")
  private BigDecimal numberOfChildren = null;

  @JsonProperty("spouseName")
  private String spouseName = null;

  @JsonProperty("occupation")
  private String occupation = null;

  @JsonProperty("consentToEnquire")
  private String consentToEnquire = null;

  public NcbNamesTUEF segmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
    return this;
  }

   /**
   * Get segmentTag
   * @return segmentTag
  **/
  @ApiModelProperty(value = "")


  public String getSegmentTag() {
    return segmentTag;
  }

  public void setSegmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
  }

  public NcbNamesTUEF familyName1(String familyName1) {
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

  public NcbNamesTUEF familyName2(String familyName2) {
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

  public NcbNamesTUEF firstName(String firstName) {
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

  public NcbNamesTUEF middle(String middle) {
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

  public NcbNamesTUEF maritalStatus(String maritalStatus) {
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

  public NcbNamesTUEF dateOfBirth(DateTime dateOfBirth) {
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

  public NcbNamesTUEF gender(String gender) {
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

  public NcbNamesTUEF title(String title) {
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

  public NcbNamesTUEF nationality(String nationality) {
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

  public NcbNamesTUEF numberOfChildren(BigDecimal numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
    return this;
  }

   /**
   * Get numberOfChildren
   * @return numberOfChildren
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumberOfChildren() {
    return numberOfChildren;
  }

  public void setNumberOfChildren(BigDecimal numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
  }

  public NcbNamesTUEF spouseName(String spouseName) {
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

  public NcbNamesTUEF occupation(String occupation) {
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

  public NcbNamesTUEF consentToEnquire(String consentToEnquire) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbNamesTUEF ncbNamesTUEF = (NcbNamesTUEF) o;
    return Objects.equals(this.segmentTag, ncbNamesTUEF.segmentTag) &&
        Objects.equals(this.familyName1, ncbNamesTUEF.familyName1) &&
        Objects.equals(this.familyName2, ncbNamesTUEF.familyName2) &&
        Objects.equals(this.firstName, ncbNamesTUEF.firstName) &&
        Objects.equals(this.middle, ncbNamesTUEF.middle) &&
        Objects.equals(this.maritalStatus, ncbNamesTUEF.maritalStatus) &&
        Objects.equals(this.dateOfBirth, ncbNamesTUEF.dateOfBirth) &&
        Objects.equals(this.gender, ncbNamesTUEF.gender) &&
        Objects.equals(this.title, ncbNamesTUEF.title) &&
        Objects.equals(this.nationality, ncbNamesTUEF.nationality) &&
        Objects.equals(this.numberOfChildren, ncbNamesTUEF.numberOfChildren) &&
        Objects.equals(this.spouseName, ncbNamesTUEF.spouseName) &&
        Objects.equals(this.occupation, ncbNamesTUEF.occupation) &&
        Objects.equals(this.consentToEnquire, ncbNamesTUEF.consentToEnquire);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentTag, familyName1, familyName2, firstName, middle, maritalStatus, dateOfBirth, gender, title, nationality, numberOfChildren, spouseName, occupation, consentToEnquire);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbNamesTUEF {\n");
    
    sb.append("    segmentTag: ").append(toIndentedString(segmentTag)).append("\n");
    sb.append("    familyName1: ").append(toIndentedString(familyName1)).append("\n");
    sb.append("    familyName2: ").append(toIndentedString(familyName2)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    middle: ").append(toIndentedString(middle)).append("\n");
    sb.append("    maritalStatus: ").append(toIndentedString(maritalStatus)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    nationality: ").append(toIndentedString(nationality)).append("\n");
    sb.append("    numberOfChildren: ").append(toIndentedString(numberOfChildren)).append("\n");
    sb.append("    spouseName: ").append(toIndentedString(spouseName)).append("\n");
    sb.append("    occupation: ").append(toIndentedString(occupation)).append("\n");
    sb.append("    consentToEnquire: ").append(toIndentedString(consentToEnquire)).append("\n");
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

