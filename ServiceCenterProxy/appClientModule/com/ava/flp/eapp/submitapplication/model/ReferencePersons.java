package com.ava.flp.eapp.submitapplication.model;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class ReferencePersons   {
  @JsonProperty("mobile")
  private String mobile = null;

  @JsonProperty("officePhone")
  private String officePhone = null;

  @JsonProperty("officePhoneExt")
  private String officePhoneExt = null;

  @JsonProperty("phone1")
  private String phone1 = null;

  @JsonProperty("ext1")
  private String ext1 = null;

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

  public ReferencePersons mobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

   /**
   * Get mobile
   * @return mobile
  **/
  @ApiModelProperty(value = "")


  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
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

  public ReferencePersons officePhoneExt(String officePhoneExt) {
    this.officePhoneExt = officePhoneExt;
    return this;
  }

   /**
   * Get officePhoneExt
   * @return officePhoneExt
  **/
  @ApiModelProperty(value = "")


  public String getOfficePhoneExt() {
    return officePhoneExt;
  }

  public void setOfficePhoneExt(String officePhoneExt) {
    this.officePhoneExt = officePhoneExt;
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

  public ReferencePersons ext1(String ext1) {
    this.ext1 = ext1;
    return this;
  }

   /**
   * Get ext1
   * @return ext1
  **/
  @ApiModelProperty(value = "")


  public String getExt1() {
    return ext1;
  }

  public void setExt1(String ext1) {
    this.ext1 = ext1;
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
    return Objects.equals(this.mobile, referencePersons.mobile) &&
        Objects.equals(this.officePhone, referencePersons.officePhone) &&
        Objects.equals(this.officePhoneExt, referencePersons.officePhoneExt) &&
        Objects.equals(this.phone1, referencePersons.phone1) &&
        Objects.equals(this.ext1, referencePersons.ext1) &&
        Objects.equals(this.relation, referencePersons.relation) &&
        Objects.equals(this.seq, referencePersons.seq) &&
        Objects.equals(this.thFirstName, referencePersons.thFirstName) &&
        Objects.equals(this.thLastName, referencePersons.thLastName) &&
        Objects.equals(this.thTitleCode, referencePersons.thTitleCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mobile, officePhone, officePhoneExt, phone1, ext1, relation, seq, thFirstName, thLastName, thTitleCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReferencePersons {\n");
    
    sb.append("    mobile: ").append(toIndentedString(mobile)).append("\n");
    sb.append("    officePhone: ").append(toIndentedString(officePhone)).append("\n");
    sb.append("    officePhoneExt: ").append(toIndentedString(officePhoneExt)).append("\n");
    sb.append("    phone1: ").append(toIndentedString(phone1)).append("\n");
    sb.append("    ext1: ").append(toIndentedString(ext1)).append("\n");
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

