package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PersonalInfosAdditionalField
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PersonalInfosAdditionalField   {
  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("officialCountry")
  private String officialCountry = null;

  @JsonProperty("thNameCapitialize")
  private String thNameCapitialize = null;

  @JsonProperty("thSurnameCapitialize")
  private String thSurnameCapitialize = null;

  @JsonProperty("enNameCapitialize")
  private String enNameCapitialize = null;

  @JsonProperty("enSurnameCapitialize")
  private String enSurnameCapitialize = null;

  @JsonProperty("companyNameCapitialize")
  private String companyNameCapitialize = null;

  @JsonProperty("thNameSurnameNormalize")
  private String thNameSurnameNormalize = null;

  @JsonProperty("enNameSurnameNormalize")
  private String enNameSurnameNormalize = null;

  @JsonProperty("thCompanyNameNormalize")
  private String thCompanyNameNormalize = null;

  @JsonProperty("companyNameNormalize")
  private String companyNameNormalize = null;

  public PersonalInfosAdditionalField companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

   /**
   * Get companyName
   * @return companyName
  **/
  @ApiModelProperty(value = "")


  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public PersonalInfosAdditionalField officialCountry(String officialCountry) {
    this.officialCountry = officialCountry;
    return this;
  }

   /**
   * Get officialCountry
   * @return officialCountry
  **/
  @ApiModelProperty(value = "")


  public String getOfficialCountry() {
    return officialCountry;
  }

  public void setOfficialCountry(String officialCountry) {
    this.officialCountry = officialCountry;
  }

  public PersonalInfosAdditionalField thNameCapitialize(String thNameCapitialize) {
    this.thNameCapitialize = thNameCapitialize;
    return this;
  }

   /**
   * Get thNameCapitialize
   * @return thNameCapitialize
  **/
  @ApiModelProperty(value = "")


  public String getThNameCapitialize() {
    return thNameCapitialize;
  }

  public void setThNameCapitialize(String thNameCapitialize) {
    this.thNameCapitialize = thNameCapitialize;
  }

  public PersonalInfosAdditionalField thSurnameCapitialize(String thSurnameCapitialize) {
    this.thSurnameCapitialize = thSurnameCapitialize;
    return this;
  }

   /**
   * Get thSurnameCapitialize
   * @return thSurnameCapitialize
  **/
  @ApiModelProperty(value = "")


  public String getThSurnameCapitialize() {
    return thSurnameCapitialize;
  }

  public void setThSurnameCapitialize(String thSurnameCapitialize) {
    this.thSurnameCapitialize = thSurnameCapitialize;
  }

  public PersonalInfosAdditionalField enNameCapitialize(String enNameCapitialize) {
    this.enNameCapitialize = enNameCapitialize;
    return this;
  }

   /**
   * Get enNameCapitialize
   * @return enNameCapitialize
  **/
  @ApiModelProperty(value = "")


  public String getEnNameCapitialize() {
    return enNameCapitialize;
  }

  public void setEnNameCapitialize(String enNameCapitialize) {
    this.enNameCapitialize = enNameCapitialize;
  }

  public PersonalInfosAdditionalField enSurnameCapitialize(String enSurnameCapitialize) {
    this.enSurnameCapitialize = enSurnameCapitialize;
    return this;
  }

   /**
   * Get enSurnameCapitialize
   * @return enSurnameCapitialize
  **/
  @ApiModelProperty(value = "")


  public String getEnSurnameCapitialize() {
    return enSurnameCapitialize;
  }

  public void setEnSurnameCapitialize(String enSurnameCapitialize) {
    this.enSurnameCapitialize = enSurnameCapitialize;
  }

  public PersonalInfosAdditionalField companyNameCapitialize(String companyNameCapitialize) {
    this.companyNameCapitialize = companyNameCapitialize;
    return this;
  }

   /**
   * Get companyNameCapitialize
   * @return companyNameCapitialize
  **/
  @ApiModelProperty(value = "")


  public String getCompanyNameCapitialize() {
    return companyNameCapitialize;
  }

  public void setCompanyNameCapitialize(String companyNameCapitialize) {
    this.companyNameCapitialize = companyNameCapitialize;
  }

  public PersonalInfosAdditionalField thNameSurnameNormalize(String thNameSurnameNormalize) {
    this.thNameSurnameNormalize = thNameSurnameNormalize;
    return this;
  }

   /**
   * Get thNameSurnameNormalize
   * @return thNameSurnameNormalize
  **/
  @ApiModelProperty(value = "")


  public String getThNameSurnameNormalize() {
    return thNameSurnameNormalize;
  }

  public void setThNameSurnameNormalize(String thNameSurnameNormalize) {
    this.thNameSurnameNormalize = thNameSurnameNormalize;
  }

  public PersonalInfosAdditionalField enNameSurnameNormalize(String enNameSurnameNormalize) {
    this.enNameSurnameNormalize = enNameSurnameNormalize;
    return this;
  }

   /**
   * Get enNameSurnameNormalize
   * @return enNameSurnameNormalize
  **/
  @ApiModelProperty(value = "")


  public String getEnNameSurnameNormalize() {
    return enNameSurnameNormalize;
  }

  public void setEnNameSurnameNormalize(String enNameSurnameNormalize) {
    this.enNameSurnameNormalize = enNameSurnameNormalize;
  }

  public PersonalInfosAdditionalField thCompanyNameNormalize(String thCompanyNameNormalize) {
    this.thCompanyNameNormalize = thCompanyNameNormalize;
    return this;
  }

   /**
   * Get thCompanyNameNormalize
   * @return thCompanyNameNormalize
  **/
  @ApiModelProperty(value = "")


  public String getThCompanyNameNormalize() {
    return thCompanyNameNormalize;
  }

  public void setThCompanyNameNormalize(String thCompanyNameNormalize) {
    this.thCompanyNameNormalize = thCompanyNameNormalize;
  }

  public PersonalInfosAdditionalField companyNameNormalize(String companyNameNormalize) {
    this.companyNameNormalize = companyNameNormalize;
    return this;
  }

   /**
   * Get companyNameNormalize
   * @return companyNameNormalize
  **/
  @ApiModelProperty(value = "")


  public String getCompanyNameNormalize() {
    return companyNameNormalize;
  }

  public void setCompanyNameNormalize(String companyNameNormalize) {
    this.companyNameNormalize = companyNameNormalize;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalInfosAdditionalField personalInfosAdditionalField = (PersonalInfosAdditionalField) o;
    return Objects.equals(this.companyName, personalInfosAdditionalField.companyName) &&
        Objects.equals(this.officialCountry, personalInfosAdditionalField.officialCountry) &&
        Objects.equals(this.thNameCapitialize, personalInfosAdditionalField.thNameCapitialize) &&
        Objects.equals(this.thSurnameCapitialize, personalInfosAdditionalField.thSurnameCapitialize) &&
        Objects.equals(this.enNameCapitialize, personalInfosAdditionalField.enNameCapitialize) &&
        Objects.equals(this.enSurnameCapitialize, personalInfosAdditionalField.enSurnameCapitialize) &&
        Objects.equals(this.companyNameCapitialize, personalInfosAdditionalField.companyNameCapitialize) &&
        Objects.equals(this.thNameSurnameNormalize, personalInfosAdditionalField.thNameSurnameNormalize) &&
        Objects.equals(this.enNameSurnameNormalize, personalInfosAdditionalField.enNameSurnameNormalize) &&
        Objects.equals(this.thCompanyNameNormalize, personalInfosAdditionalField.thCompanyNameNormalize) &&
        Objects.equals(this.companyNameNormalize, personalInfosAdditionalField.companyNameNormalize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(companyName, officialCountry, thNameCapitialize, thSurnameCapitialize, enNameCapitialize, enSurnameCapitialize, companyNameCapitialize, thNameSurnameNormalize, enNameSurnameNormalize, thCompanyNameNormalize, companyNameNormalize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalInfosAdditionalField {\n");
    
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    officialCountry: ").append(toIndentedString(officialCountry)).append("\n");
    sb.append("    thNameCapitialize: ").append(toIndentedString(thNameCapitialize)).append("\n");
    sb.append("    thSurnameCapitialize: ").append(toIndentedString(thSurnameCapitialize)).append("\n");
    sb.append("    enNameCapitialize: ").append(toIndentedString(enNameCapitialize)).append("\n");
    sb.append("    enSurnameCapitialize: ").append(toIndentedString(enSurnameCapitialize)).append("\n");
    sb.append("    companyNameCapitialize: ").append(toIndentedString(companyNameCapitialize)).append("\n");
    sb.append("    thNameSurnameNormalize: ").append(toIndentedString(thNameSurnameNormalize)).append("\n");
    sb.append("    enNameSurnameNormalize: ").append(toIndentedString(enNameSurnameNormalize)).append("\n");
    sb.append("    thCompanyNameNormalize: ").append(toIndentedString(thCompanyNameNormalize)).append("\n");
    sb.append("    companyNameNormalize: ").append(toIndentedString(companyNameNormalize)).append("\n");
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

