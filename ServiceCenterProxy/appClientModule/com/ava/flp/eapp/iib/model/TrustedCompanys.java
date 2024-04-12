package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TrustedCompanys
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class TrustedCompanys   {
  @JsonProperty("address")
  private String address = null;

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("tel")
  private String tel = null;

  @JsonProperty("typeOfCompany")
  private String typeOfCompany = null;

  @JsonProperty("website")
  private String website = null;

  public TrustedCompanys address(String address) {
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

  public TrustedCompanys category(String category) {
    this.category = category;
    return this;
  }

   /**
   * Get category
   * @return category
  **/
  @ApiModelProperty(value = "")


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public TrustedCompanys companyName(String companyName) {
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

  public TrustedCompanys idNo(String idNo) {
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

  public TrustedCompanys tel(String tel) {
    this.tel = tel;
    return this;
  }

   /**
   * Get tel
   * @return tel
  **/
  @ApiModelProperty(value = "")


  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public TrustedCompanys typeOfCompany(String typeOfCompany) {
    this.typeOfCompany = typeOfCompany;
    return this;
  }

   /**
   * Get typeOfCompany
   * @return typeOfCompany
  **/
  @ApiModelProperty(value = "")


  public String getTypeOfCompany() {
    return typeOfCompany;
  }

  public void setTypeOfCompany(String typeOfCompany) {
    this.typeOfCompany = typeOfCompany;
  }

  public TrustedCompanys website(String website) {
    this.website = website;
    return this;
  }

   /**
   * Get website
   * @return website
  **/
  @ApiModelProperty(value = "")


  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TrustedCompanys trustedCompanys = (TrustedCompanys) o;
    return Objects.equals(this.address, trustedCompanys.address) &&
        Objects.equals(this.category, trustedCompanys.category) &&
        Objects.equals(this.companyName, trustedCompanys.companyName) &&
        Objects.equals(this.idNo, trustedCompanys.idNo) &&
        Objects.equals(this.tel, trustedCompanys.tel) &&
        Objects.equals(this.typeOfCompany, trustedCompanys.typeOfCompany) &&
        Objects.equals(this.website, trustedCompanys.website);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, category, companyName, idNo, tel, typeOfCompany, website);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TrustedCompanys {\n");
    
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    tel: ").append(toIndentedString(tel)).append("\n");
    sb.append("    typeOfCompany: ").append(toIndentedString(typeOfCompany)).append("\n");
    sb.append("    website: ").append(toIndentedString(website)).append("\n");
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

