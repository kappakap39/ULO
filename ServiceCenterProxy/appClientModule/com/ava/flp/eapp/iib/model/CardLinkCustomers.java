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
 * CardLinkCustomers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CardLinkCustomers   {
  @JsonProperty("cardlinkCustNo")
  private String cardlinkCustNo = null;

  @JsonProperty("orgNo")
  private String orgNo = null;

  @JsonProperty("financialInfoCode")
  private String financialInfoCode = null;

  @JsonProperty("vatCode")
  private String vatCode = null;

  @JsonProperty("billCycle")
  private BigDecimal billCycle = null;

  @JsonProperty("creditLine")
  private BigDecimal creditLine = null;

  @JsonProperty("addressLine1")
  private String addressLine1 = null;

  @JsonProperty("addressLine2")
  private String addressLine2 = null;

  @JsonProperty("addressLine3")
  private String addressLine3 = null;

  @JsonProperty("zipCode")
  private String zipCode = null;

  @JsonProperty("referAddressLine1")
  private String referAddressLine1 = null;

  @JsonProperty("referAddressLine2")
  private String referAddressLine2 = null;

  @JsonProperty("referAddressLine3")
  private String referAddressLine3 = null;

  @JsonProperty("referZipcode")
  private String referZipcode = null;

  @JsonProperty("mobileNo")
  private String mobileNo = null;

  @JsonProperty("firstOpenDate")
  private DateTime firstOpenDate = null;

  @JsonProperty("occupation")
  private String occupation = null;

  @JsonProperty("empPosition")
  private String empPosition = null;

  @JsonProperty("incomeAmount")
  private BigDecimal incomeAmount = null;

  @JsonProperty("custType")
  private String custType = null;

  public CardLinkCustomers cardlinkCustNo(String cardlinkCustNo) {
    this.cardlinkCustNo = cardlinkCustNo;
    return this;
  }

   /**
   * Get cardlinkCustNo
   * @return cardlinkCustNo
  **/
  @ApiModelProperty(value = "")


  public String getCardlinkCustNo() {
    return cardlinkCustNo;
  }

  public void setCardlinkCustNo(String cardlinkCustNo) {
    this.cardlinkCustNo = cardlinkCustNo;
  }

  public CardLinkCustomers orgNo(String orgNo) {
    this.orgNo = orgNo;
    return this;
  }

   /**
   * Get orgNo
   * @return orgNo
  **/
  @ApiModelProperty(value = "")


  public String getOrgNo() {
    return orgNo;
  }

  public void setOrgNo(String orgNo) {
    this.orgNo = orgNo;
  }

  public CardLinkCustomers financialInfoCode(String financialInfoCode) {
    this.financialInfoCode = financialInfoCode;
    return this;
  }

   /**
   * Get financialInfoCode
   * @return financialInfoCode
  **/
  @ApiModelProperty(value = "")


  public String getFinancialInfoCode() {
    return financialInfoCode;
  }

  public void setFinancialInfoCode(String financialInfoCode) {
    this.financialInfoCode = financialInfoCode;
  }

  public CardLinkCustomers vatCode(String vatCode) {
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

  public CardLinkCustomers billCycle(BigDecimal billCycle) {
    this.billCycle = billCycle;
    return this;
  }

   /**
   * Get billCycle
   * @return billCycle
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBillCycle() {
    return billCycle;
  }

  public void setBillCycle(BigDecimal billCycle) {
    this.billCycle = billCycle;
  }

  public CardLinkCustomers creditLine(BigDecimal creditLine) {
    this.creditLine = creditLine;
    return this;
  }

   /**
   * Get creditLine
   * @return creditLine
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCreditLine() {
    return creditLine;
  }

  public void setCreditLine(BigDecimal creditLine) {
    this.creditLine = creditLine;
  }

  public CardLinkCustomers addressLine1(String addressLine1) {
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

  public CardLinkCustomers addressLine2(String addressLine2) {
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

  public CardLinkCustomers addressLine3(String addressLine3) {
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

  public CardLinkCustomers zipCode(String zipCode) {
    this.zipCode = zipCode;
    return this;
  }

   /**
   * Get zipCode
   * @return zipCode
  **/
  @ApiModelProperty(value = "")


  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public CardLinkCustomers referAddressLine1(String referAddressLine1) {
    this.referAddressLine1 = referAddressLine1;
    return this;
  }

   /**
   * Get referAddressLine1
   * @return referAddressLine1
  **/
  @ApiModelProperty(value = "")


  public String getReferAddressLine1() {
    return referAddressLine1;
  }

  public void setReferAddressLine1(String referAddressLine1) {
    this.referAddressLine1 = referAddressLine1;
  }

  public CardLinkCustomers referAddressLine2(String referAddressLine2) {
    this.referAddressLine2 = referAddressLine2;
    return this;
  }

   /**
   * Get referAddressLine2
   * @return referAddressLine2
  **/
  @ApiModelProperty(value = "")


  public String getReferAddressLine2() {
    return referAddressLine2;
  }

  public void setReferAddressLine2(String referAddressLine2) {
    this.referAddressLine2 = referAddressLine2;
  }

  public CardLinkCustomers referAddressLine3(String referAddressLine3) {
    this.referAddressLine3 = referAddressLine3;
    return this;
  }

   /**
   * Get referAddressLine3
   * @return referAddressLine3
  **/
  @ApiModelProperty(value = "")


  public String getReferAddressLine3() {
    return referAddressLine3;
  }

  public void setReferAddressLine3(String referAddressLine3) {
    this.referAddressLine3 = referAddressLine3;
  }

  public CardLinkCustomers referZipcode(String referZipcode) {
    this.referZipcode = referZipcode;
    return this;
  }

   /**
   * Get referZipcode
   * @return referZipcode
  **/
  @ApiModelProperty(value = "")


  public String getReferZipcode() {
    return referZipcode;
  }

  public void setReferZipcode(String referZipcode) {
    this.referZipcode = referZipcode;
  }

  public CardLinkCustomers mobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
    return this;
  }

   /**
   * Get mobileNo
   * @return mobileNo
  **/
  @ApiModelProperty(value = "")


  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public CardLinkCustomers firstOpenDate(DateTime firstOpenDate) {
    this.firstOpenDate = firstOpenDate;
    return this;
  }

   /**
   * Get firstOpenDate
   * @return firstOpenDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getFirstOpenDate() {
    return firstOpenDate;
  }

  public void setFirstOpenDate(DateTime firstOpenDate) {
    this.firstOpenDate = firstOpenDate;
  }

  public CardLinkCustomers occupation(String occupation) {
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

  public CardLinkCustomers empPosition(String empPosition) {
    this.empPosition = empPosition;
    return this;
  }

   /**
   * Get empPosition
   * @return empPosition
  **/
  @ApiModelProperty(value = "")


  public String getEmpPosition() {
    return empPosition;
  }

  public void setEmpPosition(String empPosition) {
    this.empPosition = empPosition;
  }

  public CardLinkCustomers incomeAmount(BigDecimal incomeAmount) {
    this.incomeAmount = incomeAmount;
    return this;
  }

   /**
   * Get incomeAmount
   * @return incomeAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncomeAmount() {
    return incomeAmount;
  }

  public void setIncomeAmount(BigDecimal incomeAmount) {
    this.incomeAmount = incomeAmount;
  }

  public CardLinkCustomers custType(String custType) {
    this.custType = custType;
    return this;
  }

   /**
   * Get custType
   * @return custType
  **/
  @ApiModelProperty(value = "")


  public String getCustType() {
    return custType;
  }

  public void setCustType(String custType) {
    this.custType = custType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardLinkCustomers cardLinkCustomers = (CardLinkCustomers) o;
    return Objects.equals(this.cardlinkCustNo, cardLinkCustomers.cardlinkCustNo) &&
        Objects.equals(this.orgNo, cardLinkCustomers.orgNo) &&
        Objects.equals(this.financialInfoCode, cardLinkCustomers.financialInfoCode) &&
        Objects.equals(this.vatCode, cardLinkCustomers.vatCode) &&
        Objects.equals(this.billCycle, cardLinkCustomers.billCycle) &&
        Objects.equals(this.creditLine, cardLinkCustomers.creditLine) &&
        Objects.equals(this.addressLine1, cardLinkCustomers.addressLine1) &&
        Objects.equals(this.addressLine2, cardLinkCustomers.addressLine2) &&
        Objects.equals(this.addressLine3, cardLinkCustomers.addressLine3) &&
        Objects.equals(this.zipCode, cardLinkCustomers.zipCode) &&
        Objects.equals(this.referAddressLine1, cardLinkCustomers.referAddressLine1) &&
        Objects.equals(this.referAddressLine2, cardLinkCustomers.referAddressLine2) &&
        Objects.equals(this.referAddressLine3, cardLinkCustomers.referAddressLine3) &&
        Objects.equals(this.referZipcode, cardLinkCustomers.referZipcode) &&
        Objects.equals(this.mobileNo, cardLinkCustomers.mobileNo) &&
        Objects.equals(this.firstOpenDate, cardLinkCustomers.firstOpenDate) &&
        Objects.equals(this.occupation, cardLinkCustomers.occupation) &&
        Objects.equals(this.empPosition, cardLinkCustomers.empPosition) &&
        Objects.equals(this.incomeAmount, cardLinkCustomers.incomeAmount) &&
        Objects.equals(this.custType, cardLinkCustomers.custType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardlinkCustNo, orgNo, financialInfoCode, vatCode, billCycle, creditLine, addressLine1, addressLine2, addressLine3, zipCode, referAddressLine1, referAddressLine2, referAddressLine3, referZipcode, mobileNo, firstOpenDate, occupation, empPosition, incomeAmount, custType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CardLinkCustomers {\n");
    
    sb.append("    cardlinkCustNo: ").append(toIndentedString(cardlinkCustNo)).append("\n");
    sb.append("    orgNo: ").append(toIndentedString(orgNo)).append("\n");
    sb.append("    financialInfoCode: ").append(toIndentedString(financialInfoCode)).append("\n");
    sb.append("    vatCode: ").append(toIndentedString(vatCode)).append("\n");
    sb.append("    billCycle: ").append(toIndentedString(billCycle)).append("\n");
    sb.append("    creditLine: ").append(toIndentedString(creditLine)).append("\n");
    sb.append("    addressLine1: ").append(toIndentedString(addressLine1)).append("\n");
    sb.append("    addressLine2: ").append(toIndentedString(addressLine2)).append("\n");
    sb.append("    addressLine3: ").append(toIndentedString(addressLine3)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    referAddressLine1: ").append(toIndentedString(referAddressLine1)).append("\n");
    sb.append("    referAddressLine2: ").append(toIndentedString(referAddressLine2)).append("\n");
    sb.append("    referAddressLine3: ").append(toIndentedString(referAddressLine3)).append("\n");
    sb.append("    referZipcode: ").append(toIndentedString(referZipcode)).append("\n");
    sb.append("    mobileNo: ").append(toIndentedString(mobileNo)).append("\n");
    sb.append("    firstOpenDate: ").append(toIndentedString(firstOpenDate)).append("\n");
    sb.append("    occupation: ").append(toIndentedString(occupation)).append("\n");
    sb.append("    empPosition: ").append(toIndentedString(empPosition)).append("\n");
    sb.append("    incomeAmount: ").append(toIndentedString(incomeAmount)).append("\n");
    sb.append("    custType: ").append(toIndentedString(custType)).append("\n");
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

