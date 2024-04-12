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
 * ReferencePersons
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class ReferencePersons   {
  @JsonProperty("moblie")
  private String moblie = null;

  @JsonProperty("officePhone")
  private String officePhone = null;

  @JsonProperty("phone1")
  private String phone1 = null;

  @JsonProperty("relation")
  private String relation = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("thFirstName")
  private String thFirstName = null;

  @JsonProperty("thLastName")
  private String thLastName = null;

  @JsonProperty("thTitleCode")
  private String thTitleCode = null;

  public ReferencePersons moblie(String moblie) {
    this.moblie = moblie;
    return this;
  }

   /**
   * Get moblie
   * @return moblie
  **/
  @ApiModelProperty(value = "")


  public String getMoblie() {
    return moblie;
  }

  public void setMoblie(String moblie) {
    this.moblie = moblie;
  }

  public ReferencePersons officePhone(String officePhone) {
    this.officePhone = officePhone;
    return this;
  }

   /**
   * Get officePhone
   * @return officePhone
  **/
  @ApiModelProperty(value = "")


  public String getOfficePhone() {
    return officePhone;
  }

  public void setOfficePhone(String officePhone) {
    this.officePhone = officePhone;
  }

  public ReferencePersons phone1(String phone1) {
    this.phone1 = phone1;
    return this;
  }

   /**
   * Get phone1
   * @return phone1
  **/
  @ApiModelProperty(value = "")


  public String getPhone1() {
    return phone1;
  }

  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }

  public ReferencePersons relation(String relation) {
    this.relation = relation;
    return this;
  }

   /**
   * Get relation
   * @return relation
  **/
  @ApiModelProperty(value = "")


  public String getRelation() {
    return relation;
  }

  public void setRelation(String relation) {
    this.relation = relation;
  }

  public ReferencePersons seq(BigDecimal seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSeq() {
    return seq;
  }

  public void setSeq(BigDecimal seq) {
    this.seq = seq;
  }

  public ReferencePersons thFirstName(String thFirstName) {
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

  public ReferencePersons thLastName(String thLastName) {
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

  public ReferencePersons thTitleCode(String thTitleCode) {
    this.thTitleCode = thTitleCode;
    return this;
  }

   /**
   * Get thTitleCode
   * @return thTitleCode
  **/
  @ApiModelProperty(value = "")


  public String getThTitleCode() {
    return thTitleCode;
  }

  public void setThTitleCode(String thTitleCode) {
    this.thTitleCode = thTitleCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReferencePersons referencePersons = (ReferencePersons) o;
    return Objects.equals(this.moblie, referencePersons.moblie) &&
        Objects.equals(this.officePhone, referencePersons.officePhone) &&
        Objects.equals(this.phone1, referencePersons.phone1) &&
        Objects.equals(this.relation, referencePersons.relation) &&
        Objects.equals(this.seq, referencePersons.seq) &&
        Objects.equals(this.thFirstName, referencePersons.thFirstName) &&
        Objects.equals(this.thLastName, referencePersons.thLastName) &&
        Objects.equals(this.thTitleCode, referencePersons.thTitleCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(moblie, officePhone, phone1, relation, seq, thFirstName, thLastName, thTitleCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferencePersons {\n");
    
    sb.append("    moblie: ").append(toIndentedString(moblie)).append("\n");
    sb.append("    officePhone: ").append(toIndentedString(officePhone)).append("\n");
    sb.append("    phone1: ").append(toIndentedString(phone1)).append("\n");
    sb.append("    relation: ").append(toIndentedString(relation)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    thFirstName: ").append(toIndentedString(thFirstName)).append("\n");
    sb.append("    thLastName: ").append(toIndentedString(thLastName)).append("\n");
    sb.append("    thTitleCode: ").append(toIndentedString(thTitleCode)).append("\n");
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

