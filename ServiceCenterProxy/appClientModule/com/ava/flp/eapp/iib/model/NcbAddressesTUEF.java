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
 * NcbAddressesTUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbAddressesTUEF   {
  @JsonProperty("segmentTag")
  private String segmentTag = null;

  @JsonProperty("addressLine1")
  private String addressLine1 = null;

  @JsonProperty("addressLine2")
  private String addressLine2 = null;

  @JsonProperty("addressLine3")
  private String addressLine3 = null;

  @JsonProperty("subdistrict")
  private String subdistrict = null;

  @JsonProperty("district")
  private String district = null;

  @JsonProperty("province")
  private String province = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("postalCode")
  private String postalCode = null;

  @JsonProperty("telephone")
  private String telephone = null;

  @JsonProperty("telephoneType")
  private String telephoneType = null;

  @JsonProperty("addressType")
  private String addressType = null;

  @JsonProperty("residentialStatus")
  private String residentialStatus = null;

  @JsonProperty("reportedDate")
  private DateTime reportedDate = null;

  public NcbAddressesTUEF segmentTag(String segmentTag) {
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

  public NcbAddressesTUEF addressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
    return this;
  }

   /**
   * Get addressLine1
   * @return addressLine1
  **/
  @ApiModelProperty(value = "")


  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public NcbAddressesTUEF addressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
    return this;
  }

   /**
   * Get addressLine2
   * @return addressLine2
  **/
  @ApiModelProperty(value = "")


  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public NcbAddressesTUEF addressLine3(String addressLine3) {
    this.addressLine3 = addressLine3;
    return this;
  }

   /**
   * Get addressLine3
   * @return addressLine3
  **/
  @ApiModelProperty(value = "")


  public String getAddressLine3() {
    return addressLine3;
  }

  public void setAddressLine3(String addressLine3) {
    this.addressLine3 = addressLine3;
  }

  public NcbAddressesTUEF subdistrict(String subdistrict) {
    this.subdistrict = subdistrict;
    return this;
  }

   /**
   * Get subdistrict
   * @return subdistrict
  **/
  @ApiModelProperty(value = "")


  public String getSubdistrict() {
    return subdistrict;
  }

  public void setSubdistrict(String subdistrict) {
    this.subdistrict = subdistrict;
  }

  public NcbAddressesTUEF district(String district) {
    this.district = district;
    return this;
  }

   /**
   * Get district
   * @return district
  **/
  @ApiModelProperty(value = "")


  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public NcbAddressesTUEF province(String province) {
    this.province = province;
    return this;
  }

   /**
   * Get province
   * @return province
  **/
  @ApiModelProperty(value = "")


  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public NcbAddressesTUEF country(String country) {
    this.country = country;
    return this;
  }

   /**
   * Get country
   * @return country
  **/
  @ApiModelProperty(value = "")


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public NcbAddressesTUEF postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

   /**
   * Get postalCode
   * @return postalCode
  **/
  @ApiModelProperty(value = "")


  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public NcbAddressesTUEF telephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

   /**
   * Get telephone
   * @return telephone
  **/
  @ApiModelProperty(value = "")


  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public NcbAddressesTUEF telephoneType(String telephoneType) {
    this.telephoneType = telephoneType;
    return this;
  }

   /**
   * Get telephoneType
   * @return telephoneType
  **/
  @ApiModelProperty(value = "")


  public String getTelephoneType() {
    return telephoneType;
  }

  public void setTelephoneType(String telephoneType) {
    this.telephoneType = telephoneType;
  }

  public NcbAddressesTUEF addressType(String addressType) {
    this.addressType = addressType;
    return this;
  }

   /**
   * Get addressType
   * @return addressType
  **/
  @ApiModelProperty(value = "")


  public String getAddressType() {
    return addressType;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  public NcbAddressesTUEF residentialStatus(String residentialStatus) {
    this.residentialStatus = residentialStatus;
    return this;
  }

   /**
   * Get residentialStatus
   * @return residentialStatus
  **/
  @ApiModelProperty(value = "")


  public String getResidentialStatus() {
    return residentialStatus;
  }

  public void setResidentialStatus(String residentialStatus) {
    this.residentialStatus = residentialStatus;
  }

  public NcbAddressesTUEF reportedDate(DateTime reportedDate) {
    this.reportedDate = reportedDate;
    return this;
  }

   /**
   * Get reportedDate
   * @return reportedDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getReportedDate() {
    return reportedDate;
  }

  public void setReportedDate(DateTime reportedDate) {
    this.reportedDate = reportedDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbAddressesTUEF ncbAddressesTUEF = (NcbAddressesTUEF) o;
    return Objects.equals(this.segmentTag, ncbAddressesTUEF.segmentTag) &&
        Objects.equals(this.addressLine1, ncbAddressesTUEF.addressLine1) &&
        Objects.equals(this.addressLine2, ncbAddressesTUEF.addressLine2) &&
        Objects.equals(this.addressLine3, ncbAddressesTUEF.addressLine3) &&
        Objects.equals(this.subdistrict, ncbAddressesTUEF.subdistrict) &&
        Objects.equals(this.district, ncbAddressesTUEF.district) &&
        Objects.equals(this.province, ncbAddressesTUEF.province) &&
        Objects.equals(this.country, ncbAddressesTUEF.country) &&
        Objects.equals(this.postalCode, ncbAddressesTUEF.postalCode) &&
        Objects.equals(this.telephone, ncbAddressesTUEF.telephone) &&
        Objects.equals(this.telephoneType, ncbAddressesTUEF.telephoneType) &&
        Objects.equals(this.addressType, ncbAddressesTUEF.addressType) &&
        Objects.equals(this.residentialStatus, ncbAddressesTUEF.residentialStatus) &&
        Objects.equals(this.reportedDate, ncbAddressesTUEF.reportedDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentTag, addressLine1, addressLine2, addressLine3, subdistrict, district, province, country, postalCode, telephone, telephoneType, addressType, residentialStatus, reportedDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbAddressesTUEF {\n");
    
    sb.append("    segmentTag: ").append(toIndentedString(segmentTag)).append("\n");
    sb.append("    addressLine1: ").append(toIndentedString(addressLine1)).append("\n");
    sb.append("    addressLine2: ").append(toIndentedString(addressLine2)).append("\n");
    sb.append("    addressLine3: ").append(toIndentedString(addressLine3)).append("\n");
    sb.append("    subdistrict: ").append(toIndentedString(subdistrict)).append("\n");
    sb.append("    district: ").append(toIndentedString(district)).append("\n");
    sb.append("    province: ").append(toIndentedString(province)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    telephone: ").append(toIndentedString(telephone)).append("\n");
    sb.append("    telephoneType: ").append(toIndentedString(telephoneType)).append("\n");
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
    sb.append("    residentialStatus: ").append(toIndentedString(residentialStatus)).append("\n");
    sb.append("    reportedDate: ").append(toIndentedString(reportedDate)).append("\n");
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

