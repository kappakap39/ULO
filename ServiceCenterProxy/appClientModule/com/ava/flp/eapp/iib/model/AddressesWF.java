package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddressesWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class AddressesWF   {
  @JsonProperty("addressType")
  private String addressType = null;

  @JsonProperty("houseNumber")
  private String houseNumber = null;

  @JsonProperty("village")
  private String village = null;

  @JsonProperty("floorNumber")
  private String floorNumber = null;

  @JsonProperty("room")
  private String room = null;

  @JsonProperty("buildingName")
  private String buildingName = null;

  @JsonProperty("moo")
  private String moo = null;

  @JsonProperty("soi")
  private String soi = null;

  @JsonProperty("street")
  private String street = null;

  @JsonProperty("subDistrict")
  private String subDistrict = null;

  @JsonProperty("district")
  private String district = null;

  @JsonProperty("province")
  private String province = null;

  @JsonProperty("postalCode")
  private String postalCode = null;

  @JsonProperty("country")
  private String country = null;

  public AddressesWF addressType(String addressType) {
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

  public AddressesWF houseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
    return this;
  }

   /**
   * Get houseNumber
   * @return houseNumber
  **/
  @ApiModelProperty(value = "")


  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public AddressesWF village(String village) {
    this.village = village;
    return this;
  }

   /**
   * Get village
   * @return village
  **/
  @ApiModelProperty(value = "")


  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public AddressesWF floorNumber(String floorNumber) {
    this.floorNumber = floorNumber;
    return this;
  }

   /**
   * Get floorNumber
   * @return floorNumber
  **/
  @ApiModelProperty(value = "")


  public String getFloorNumber() {
    return floorNumber;
  }

  public void setFloorNumber(String floorNumber) {
    this.floorNumber = floorNumber;
  }

  public AddressesWF room(String room) {
    this.room = room;
    return this;
  }

   /**
   * Get room
   * @return room
  **/
  @ApiModelProperty(value = "")


  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public AddressesWF buildingName(String buildingName) {
    this.buildingName = buildingName;
    return this;
  }

   /**
   * Get buildingName
   * @return buildingName
  **/
  @ApiModelProperty(value = "")


  public String getBuildingName() {
    return buildingName;
  }

  public void setBuildingName(String buildingName) {
    this.buildingName = buildingName;
  }

  public AddressesWF moo(String moo) {
    this.moo = moo;
    return this;
  }

   /**
   * Get moo
   * @return moo
  **/
  @ApiModelProperty(value = "")


  public String getMoo() {
    return moo;
  }

  public void setMoo(String moo) {
    this.moo = moo;
  }

  public AddressesWF soi(String soi) {
    this.soi = soi;
    return this;
  }

   /**
   * Get soi
   * @return soi
  **/
  @ApiModelProperty(value = "")


  public String getSoi() {
    return soi;
  }

  public void setSoi(String soi) {
    this.soi = soi;
  }

  public AddressesWF street(String street) {
    this.street = street;
    return this;
  }

   /**
   * Get street
   * @return street
  **/
  @ApiModelProperty(value = "")


  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public AddressesWF subDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
    return this;
  }

   /**
   * Get subDistrict
   * @return subDistrict
  **/
  @ApiModelProperty(value = "")


  public String getSubDistrict() {
    return subDistrict;
  }

  public void setSubDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
  }

  public AddressesWF district(String district) {
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

  public AddressesWF province(String province) {
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

  public AddressesWF postalCode(String postalCode) {
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

  public AddressesWF country(String country) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressesWF addressesWF = (AddressesWF) o;
    return Objects.equals(this.addressType, addressesWF.addressType) &&
        Objects.equals(this.houseNumber, addressesWF.houseNumber) &&
        Objects.equals(this.village, addressesWF.village) &&
        Objects.equals(this.floorNumber, addressesWF.floorNumber) &&
        Objects.equals(this.room, addressesWF.room) &&
        Objects.equals(this.buildingName, addressesWF.buildingName) &&
        Objects.equals(this.moo, addressesWF.moo) &&
        Objects.equals(this.soi, addressesWF.soi) &&
        Objects.equals(this.street, addressesWF.street) &&
        Objects.equals(this.subDistrict, addressesWF.subDistrict) &&
        Objects.equals(this.district, addressesWF.district) &&
        Objects.equals(this.province, addressesWF.province) &&
        Objects.equals(this.postalCode, addressesWF.postalCode) &&
        Objects.equals(this.country, addressesWF.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressType, houseNumber, village, floorNumber, room, buildingName, moo, soi, street, subDistrict, district, province, postalCode, country);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressesWF {\n");
    
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    village: ").append(toIndentedString(village)).append("\n");
    sb.append("    floorNumber: ").append(toIndentedString(floorNumber)).append("\n");
    sb.append("    room: ").append(toIndentedString(room)).append("\n");
    sb.append("    buildingName: ").append(toIndentedString(buildingName)).append("\n");
    sb.append("    moo: ").append(toIndentedString(moo)).append("\n");
    sb.append("    soi: ").append(toIndentedString(soi)).append("\n");
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    subDistrict: ").append(toIndentedString(subDistrict)).append("\n");
    sb.append("    district: ").append(toIndentedString(district)).append("\n");
    sb.append("    province: ").append(toIndentedString(province)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
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

