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
 * Addresses
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T16:05:55.951+07:00")

public class Addresses   {
  @JsonProperty("address")
  private String address = null;

  @JsonProperty("addressType")
  private String addressType = null;

  @JsonProperty("adrsts")
  private String adrsts = null;

  @JsonProperty("amphur")
  private String amphur = null;

  @JsonProperty("building")
  private String building = null;

  @JsonProperty("buildingType")
  private String buildingType = null;

  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("companyTitle")
  private String companyTitle = null;

  @JsonProperty("country")
  private String country = null;

  @JsonProperty("department")
  private String department = null;

  @JsonProperty("ext1")
  private String ext1 = null;

  @JsonProperty("fax")
  private String fax = null;

  @JsonProperty("floor")
  private String floor = null;

  @JsonProperty("mobile")
  private String mobile = null;

  @JsonProperty("moo")
  private String moo = null;

  @JsonProperty("phone1")
  private String phone1 = null;

  @JsonProperty("province")
  private String province = null;

  @JsonProperty("rents")
  private BigDecimal rents = null;

  @JsonProperty("resideMonth")
  private BigDecimal resideMonth = null;

  @JsonProperty("resideYear")
  private BigDecimal resideYear = null;

  @JsonProperty("road")
  private String road = null;

  @JsonProperty("room")
  private String room = null;

  @JsonProperty("seq")
  private BigDecimal seq = null;

  @JsonProperty("soi")
  private String soi = null;

  @JsonProperty("state")
  private String state = null;

  @JsonProperty("tambol")
  private String tambol = null;

  @JsonProperty("vatCode")
  private String vatCode = null;

  @JsonProperty("vatRegistration")
  private String vatRegistration = null;

  @JsonProperty("vilapt")
  private String vilapt = null;

  @JsonProperty("zipcode")
  private String zipcode = null;

  @JsonProperty("residenceProvince")
  private String residenceProvince = null;

  public Addresses address(String address) {
    this.address = address;
    return this;
  }

   /**
   * Get address
   * @return address
  **/
  @ApiModelProperty(value = "")


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Addresses addressType(String addressType) {
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

  public Addresses adrsts(String adrsts) {
    this.adrsts = adrsts;
    return this;
  }

   /**
   * Get adrsts
   * @return adrsts
  **/
  @ApiModelProperty(value = "")


  public String getAdrsts() {
    return adrsts;
  }

  public void setAdrsts(String adrsts) {
    this.adrsts = adrsts;
  }

  public Addresses amphur(String amphur) {
    this.amphur = amphur;
    return this;
  }

   /**
   * Get amphur
   * @return amphur
  **/
  @ApiModelProperty(value = "")


  public String getAmphur() {
    return amphur;
  }

  public void setAmphur(String amphur) {
    this.amphur = amphur;
  }

  public Addresses building(String building) {
    this.building = building;
    return this;
  }

   /**
   * Get building
   * @return building
  **/
  @ApiModelProperty(value = "")


  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public Addresses buildingType(String buildingType) {
    this.buildingType = buildingType;
    return this;
  }

   /**
   * Get buildingType
   * @return buildingType
  **/
  @ApiModelProperty(value = "")


  public String getBuildingType() {
    return buildingType;
  }

  public void setBuildingType(String buildingType) {
    this.buildingType = buildingType;
  }

  public Addresses companyName(String companyName) {
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

  public Addresses companyTitle(String companyTitle) {
    this.companyTitle = companyTitle;
    return this;
  }

   /**
   * Get companyTitle
   * @return companyTitle
  **/
  @ApiModelProperty(value = "")


  public String getCompanyTitle() {
    return companyTitle;
  }

  public void setCompanyTitle(String companyTitle) {
    this.companyTitle = companyTitle;
  }

  public Addresses country(String country) {
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

  public Addresses department(String department) {
    this.department = department;
    return this;
  }

   /**
   * Get department
   * @return department
  **/
  @ApiModelProperty(value = "")


  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Addresses ext1(String ext1) {
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

  public Addresses fax(String fax) {
    this.fax = fax;
    return this;
  }

   /**
   * Get fax
   * @return fax
  **/
  @ApiModelProperty(value = "")


  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public Addresses floor(String floor) {
    this.floor = floor;
    return this;
  }

   /**
   * Get floor
   * @return floor
  **/
  @ApiModelProperty(value = "")


  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public Addresses mobile(String mobile) {
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

  public Addresses moo(String moo) {
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

  public Addresses phone1(String phone1) {
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

  public Addresses province(String province) {
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

  public Addresses rents(BigDecimal rents) {
    this.rents = rents;
    return this;
  }

   /**
   * Get rents
   * @return rents
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRents() {
    return rents;
  }

  public void setRents(BigDecimal rents) {
    this.rents = rents;
  }

  public Addresses resideMonth(BigDecimal resideMonth) {
    this.resideMonth = resideMonth;
    return this;
  }

   /**
   * Get resideMonth
   * @return resideMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getResideMonth() {
    return resideMonth;
  }

  public void setResideMonth(BigDecimal resideMonth) {
    this.resideMonth = resideMonth;
  }

  public Addresses resideYear(BigDecimal resideYear) {
    this.resideYear = resideYear;
    return this;
  }

   /**
   * Get resideYear
   * @return resideYear
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getResideYear() {
    return resideYear;
  }

  public void setResideYear(BigDecimal resideYear) {
    this.resideYear = resideYear;
  }

  public Addresses road(String road) {
    this.road = road;
    return this;
  }

   /**
   * Get road
   * @return road
  **/
  @ApiModelProperty(value = "")


  public String getRoad() {
    return road;
  }

  public void setRoad(String road) {
    this.road = road;
  }

  public Addresses room(String room) {
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

  public Addresses seq(BigDecimal seq) {
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

  public Addresses soi(String soi) {
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

  public Addresses state(String state) {
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @ApiModelProperty(value = "")


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Addresses tambol(String tambol) {
    this.tambol = tambol;
    return this;
  }

   /**
   * Get tambol
   * @return tambol
  **/
  @ApiModelProperty(value = "")


  public String getTambol() {
    return tambol;
  }

  public void setTambol(String tambol) {
    this.tambol = tambol;
  }

  public Addresses vatCode(String vatCode) {
    this.vatCode = vatCode;
    return this;
  }

   /**
   * Get vatCode
   * @return vatCode
  **/
  @ApiModelProperty(value = "")


  public String getVatCode() {
    return vatCode;
  }

  public void setVatCode(String vatCode) {
    this.vatCode = vatCode;
  }

  public Addresses vatRegistration(String vatRegistration) {
    this.vatRegistration = vatRegistration;
    return this;
  }

   /**
   * Get vatRegistration
   * @return vatRegistration
  **/
  @ApiModelProperty(value = "")


  public String getVatRegistration() {
    return vatRegistration;
  }

  public void setVatRegistration(String vatRegistration) {
    this.vatRegistration = vatRegistration;
  }

  public Addresses vilapt(String vilapt) {
    this.vilapt = vilapt;
    return this;
  }

   /**
   * Get vilapt
   * @return vilapt
  **/
  @ApiModelProperty(value = "")


  public String getVilapt() {
    return vilapt;
  }

  public void setVilapt(String vilapt) {
    this.vilapt = vilapt;
  }

  public Addresses zipcode(String zipcode) {
    this.zipcode = zipcode;
    return this;
  }

   /**
   * Get zipcode
   * @return zipcode
  **/
  @ApiModelProperty(value = "")


  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public Addresses residenceProvince(String residenceProvince) {
    this.residenceProvince = residenceProvince;
    return this;
  }

   /**
   * Get residenceProvince
   * @return residenceProvince
  **/
  @ApiModelProperty(value = "")


  public String getResidenceProvince() {
    return residenceProvince;
  }

  public void setResidenceProvince(String residenceProvince) {
    this.residenceProvince = residenceProvince;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Addresses addresses = (Addresses) o;
    return Objects.equals(this.address, addresses.address) &&
        Objects.equals(this.addressType, addresses.addressType) &&
        Objects.equals(this.adrsts, addresses.adrsts) &&
        Objects.equals(this.amphur, addresses.amphur) &&
        Objects.equals(this.building, addresses.building) &&
        Objects.equals(this.buildingType, addresses.buildingType) &&
        Objects.equals(this.companyName, addresses.companyName) &&
        Objects.equals(this.companyTitle, addresses.companyTitle) &&
        Objects.equals(this.country, addresses.country) &&
        Objects.equals(this.department, addresses.department) &&
        Objects.equals(this.ext1, addresses.ext1) &&
        Objects.equals(this.fax, addresses.fax) &&
        Objects.equals(this.floor, addresses.floor) &&
        Objects.equals(this.mobile, addresses.mobile) &&
        Objects.equals(this.moo, addresses.moo) &&
        Objects.equals(this.phone1, addresses.phone1) &&
        Objects.equals(this.province, addresses.province) &&
        Objects.equals(this.rents, addresses.rents) &&
        Objects.equals(this.resideMonth, addresses.resideMonth) &&
        Objects.equals(this.resideYear, addresses.resideYear) &&
        Objects.equals(this.road, addresses.road) &&
        Objects.equals(this.room, addresses.room) &&
        Objects.equals(this.seq, addresses.seq) &&
        Objects.equals(this.soi, addresses.soi) &&
        Objects.equals(this.state, addresses.state) &&
        Objects.equals(this.tambol, addresses.tambol) &&
        Objects.equals(this.vatCode, addresses.vatCode) &&
        Objects.equals(this.vatRegistration, addresses.vatRegistration) &&
        Objects.equals(this.vilapt, addresses.vilapt) &&
        Objects.equals(this.zipcode, addresses.zipcode) &&
        Objects.equals(this.residenceProvince, addresses.residenceProvince);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, addressType, adrsts, amphur, building, buildingType, companyName, companyTitle, country, department, ext1, fax, floor, mobile, moo, phone1, province, rents, resideMonth, resideYear, road, room, seq, soi, state, tambol, vatCode, vatRegistration, vilapt, zipcode, residenceProvince);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Addresses {\n");
    
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
    sb.append("    adrsts: ").append(toIndentedString(adrsts)).append("\n");
    sb.append("    amphur: ").append(toIndentedString(amphur)).append("\n");
    sb.append("    building: ").append(toIndentedString(building)).append("\n");
    sb.append("    buildingType: ").append(toIndentedString(buildingType)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    companyTitle: ").append(toIndentedString(companyTitle)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    department: ").append(toIndentedString(department)).append("\n");
    sb.append("    ext1: ").append(toIndentedString(ext1)).append("\n");
    sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
    sb.append("    floor: ").append(toIndentedString(floor)).append("\n");
    sb.append("    mobile: ").append(toIndentedString(mobile)).append("\n");
    sb.append("    moo: ").append(toIndentedString(moo)).append("\n");
    sb.append("    phone1: ").append(toIndentedString(phone1)).append("\n");
    sb.append("    province: ").append(toIndentedString(province)).append("\n");
    sb.append("    rents: ").append(toIndentedString(rents)).append("\n");
    sb.append("    resideMonth: ").append(toIndentedString(resideMonth)).append("\n");
    sb.append("    resideYear: ").append(toIndentedString(resideYear)).append("\n");
    sb.append("    road: ").append(toIndentedString(road)).append("\n");
    sb.append("    room: ").append(toIndentedString(room)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    soi: ").append(toIndentedString(soi)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    tambol: ").append(toIndentedString(tambol)).append("\n");
    sb.append("    vatCode: ").append(toIndentedString(vatCode)).append("\n");
    sb.append("    vatRegistration: ").append(toIndentedString(vatRegistration)).append("\n");
    sb.append("    vilapt: ").append(toIndentedString(vilapt)).append("\n");
    sb.append("    zipcode: ").append(toIndentedString(zipcode)).append("\n");
    sb.append("    residenceProvince: ").append(toIndentedString(residenceProvince)).append("\n");
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

