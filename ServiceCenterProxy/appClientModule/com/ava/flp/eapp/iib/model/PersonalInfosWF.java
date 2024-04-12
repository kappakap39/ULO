package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.AddressesWF;
import com.ava.flp.eapp.iib.model.CardlinkCustMWF;
import com.ava.flp.eapp.iib.model.IncomeInfosWF;
import com.ava.flp.eapp.iib.model.IncomeSourcesWF;
import com.ava.flp.eapp.iib.model.NcbInfoMWF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PersonalInfosWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class PersonalInfosWF   {
  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("docNo")
  private String docNo = null;

  @JsonProperty("docType")
  private String docType = null;

  @JsonProperty("thFirstName")
  private String thFirstName = null;

  @JsonProperty("thMidName")
  private String thMidName = null;

  @JsonProperty("thLastName")
  private String thLastName = null;

  @JsonProperty("cisNo")
  private String cisNo = null;

  @JsonProperty("dob")
  private String dob = null;

  @JsonProperty("applicantType")
  private String applicantType = null;

  @JsonProperty("finalVerifiedIncome")
  private String finalVerifiedIncome = null;

  @JsonProperty("mailingAddress")
  private String mailingAddress = null;

  @JsonProperty("typeOfFin")
  private String typeOfFin = null;

  @JsonProperty("mobileNo")
  private String mobileNo = null;

  @JsonProperty("vatCode")
  private String vatCode = null;

  @JsonProperty("addresses")
  private List<AddressesWF> addresses = null;

  @JsonProperty("cardlinkCustM")
  private CardlinkCustMWF cardlinkCustM = null;

  @JsonProperty("incomeInfos")
  private List<IncomeInfosWF> incomeInfos = null;

  @JsonProperty("incomeSources")
  private List<IncomeSourcesWF> incomeSources = null;

  @JsonProperty("ncbInfoM")
  private NcbInfoMWF ncbInfoM = null;

  public PersonalInfosWF personalId(String personalId) {
    this.personalId = personalId;
    return this;
  }

   /**
   * Get personalId
   * @return personalId
  **/
  @ApiModelProperty(value = "")


  public String getPersonalId() {
    return personalId;
  }

  public void setPersonalId(String personalId) {
    this.personalId = personalId;
  }

  public PersonalInfosWF docNo(String docNo) {
    this.docNo = docNo;
    return this;
  }

   /**
   * Get docNo
   * @return docNo
  **/
  @ApiModelProperty(value = "")


  public String getDocNo() {
    return docNo;
  }

  public void setDocNo(String docNo) {
    this.docNo = docNo;
  }

  public PersonalInfosWF docType(String docType) {
    this.docType = docType;
    return this;
  }

   /**
   * Get docType
   * @return docType
  **/
  @ApiModelProperty(value = "")


  public String getDocType() {
    return docType;
  }

  public void setDocType(String docType) {
    this.docType = docType;
  }

  public PersonalInfosWF thFirstName(String thFirstName) {
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

  public PersonalInfosWF thMidName(String thMidName) {
    this.thMidName = thMidName;
    return this;
  }

   /**
   * Get thMidName
   * @return thMidName
  **/
  @ApiModelProperty(value = "")


  public String getThMidName() {
    return thMidName;
  }

  public void setThMidName(String thMidName) {
    this.thMidName = thMidName;
  }

  public PersonalInfosWF thLastName(String thLastName) {
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

  public PersonalInfosWF cisNo(String cisNo) {
    this.cisNo = cisNo;
    return this;
  }

   /**
   * Get cisNo
   * @return cisNo
  **/
  @ApiModelProperty(value = "")


  public String getCisNo() {
    return cisNo;
  }

  public void setCisNo(String cisNo) {
    this.cisNo = cisNo;
  }

  public PersonalInfosWF dob(String dob) {
    this.dob = dob;
    return this;
  }

   /**
   * Get dob
   * @return dob
  **/
  @ApiModelProperty(value = "")


  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public PersonalInfosWF applicantType(String applicantType) {
    this.applicantType = applicantType;
    return this;
  }

   /**
   * Get applicantType
   * @return applicantType
  **/
  @ApiModelProperty(value = "")


  public String getApplicantType() {
    return applicantType;
  }

  public void setApplicantType(String applicantType) {
    this.applicantType = applicantType;
  }

  public PersonalInfosWF finalVerifiedIncome(String finalVerifiedIncome) {
    this.finalVerifiedIncome = finalVerifiedIncome;
    return this;
  }

   /**
   * Get finalVerifiedIncome
   * @return finalVerifiedIncome
  **/
  @ApiModelProperty(value = "")


  public String getFinalVerifiedIncome() {
    return finalVerifiedIncome;
  }

  public void setFinalVerifiedIncome(String finalVerifiedIncome) {
    this.finalVerifiedIncome = finalVerifiedIncome;
  }

  public PersonalInfosWF mailingAddress(String mailingAddress) {
    this.mailingAddress = mailingAddress;
    return this;
  }

   /**
   * Get mailingAddress
   * @return mailingAddress
  **/
  @ApiModelProperty(value = "")


  public String getMailingAddress() {
    return mailingAddress;
  }

  public void setMailingAddress(String mailingAddress) {
    this.mailingAddress = mailingAddress;
  }

  public PersonalInfosWF typeOfFin(String typeOfFin) {
    this.typeOfFin = typeOfFin;
    return this;
  }

   /**
   * Get typeOfFin
   * @return typeOfFin
  **/
  @ApiModelProperty(value = "")


  public String getTypeOfFin() {
    return typeOfFin;
  }

  public void setTypeOfFin(String typeOfFin) {
    this.typeOfFin = typeOfFin;
  }

  public PersonalInfosWF mobileNo(String mobileNo) {
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

  public PersonalInfosWF vatCode(String vatCode) {
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

  public PersonalInfosWF addresses(List<AddressesWF> addresses) {
    this.addresses = addresses;
    return this;
  }

  public PersonalInfosWF addAddressesItem(AddressesWF addressesItem) {
    if (this.addresses == null) {
      this.addresses = new ArrayList<AddressesWF>();
    }
    this.addresses.add(addressesItem);
    return this;
  }

   /**
   * Get addresses
   * @return addresses
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<AddressesWF> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<AddressesWF> addresses) {
    this.addresses = addresses;
  }

  public PersonalInfosWF cardlinkCustM(CardlinkCustMWF cardlinkCustM) {
    this.cardlinkCustM = cardlinkCustM;
    return this;
  }

   /**
   * Get cardlinkCustM
   * @return cardlinkCustM
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CardlinkCustMWF getCardlinkCustM() {
    return cardlinkCustM;
  }

  public void setCardlinkCustM(CardlinkCustMWF cardlinkCustM) {
    this.cardlinkCustM = cardlinkCustM;
  }

  public PersonalInfosWF incomeInfos(List<IncomeInfosWF> incomeInfos) {
    this.incomeInfos = incomeInfos;
    return this;
  }

  public PersonalInfosWF addIncomeInfosItem(IncomeInfosWF incomeInfosItem) {
    if (this.incomeInfos == null) {
      this.incomeInfos = new ArrayList<IncomeInfosWF>();
    }
    this.incomeInfos.add(incomeInfosItem);
    return this;
  }

   /**
   * Get incomeInfos
   * @return incomeInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<IncomeInfosWF> getIncomeInfos() {
    return incomeInfos;
  }

  public void setIncomeInfos(List<IncomeInfosWF> incomeInfos) {
    this.incomeInfos = incomeInfos;
  }

  public PersonalInfosWF incomeSources(List<IncomeSourcesWF> incomeSources) {
    this.incomeSources = incomeSources;
    return this;
  }

  public PersonalInfosWF addIncomeSourcesItem(IncomeSourcesWF incomeSourcesItem) {
    if (this.incomeSources == null) {
      this.incomeSources = new ArrayList<IncomeSourcesWF>();
    }
    this.incomeSources.add(incomeSourcesItem);
    return this;
  }

   /**
   * Get incomeSources
   * @return incomeSources
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<IncomeSourcesWF> getIncomeSources() {
    return incomeSources;
  }

  public void setIncomeSources(List<IncomeSourcesWF> incomeSources) {
    this.incomeSources = incomeSources;
  }

  public PersonalInfosWF ncbInfoM(NcbInfoMWF ncbInfoM) {
    this.ncbInfoM = ncbInfoM;
    return this;
  }

   /**
   * Get ncbInfoM
   * @return ncbInfoM
  **/
  @ApiModelProperty(value = "")

  @Valid

  public NcbInfoMWF getNcbInfoM() {
    return ncbInfoM;
  }

  public void setNcbInfoM(NcbInfoMWF ncbInfoM) {
    this.ncbInfoM = ncbInfoM;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalInfosWF personalInfosWF = (PersonalInfosWF) o;
    return Objects.equals(this.personalId, personalInfosWF.personalId) &&
        Objects.equals(this.docNo, personalInfosWF.docNo) &&
        Objects.equals(this.docType, personalInfosWF.docType) &&
        Objects.equals(this.thFirstName, personalInfosWF.thFirstName) &&
        Objects.equals(this.thMidName, personalInfosWF.thMidName) &&
        Objects.equals(this.thLastName, personalInfosWF.thLastName) &&
        Objects.equals(this.cisNo, personalInfosWF.cisNo) &&
        Objects.equals(this.dob, personalInfosWF.dob) &&
        Objects.equals(this.applicantType, personalInfosWF.applicantType) &&
        Objects.equals(this.finalVerifiedIncome, personalInfosWF.finalVerifiedIncome) &&
        Objects.equals(this.mailingAddress, personalInfosWF.mailingAddress) &&
        Objects.equals(this.typeOfFin, personalInfosWF.typeOfFin) &&
        Objects.equals(this.mobileNo, personalInfosWF.mobileNo) &&
        Objects.equals(this.vatCode, personalInfosWF.vatCode) &&
        Objects.equals(this.addresses, personalInfosWF.addresses) &&
        Objects.equals(this.cardlinkCustM, personalInfosWF.cardlinkCustM) &&
        Objects.equals(this.incomeInfos, personalInfosWF.incomeInfos) &&
        Objects.equals(this.incomeSources, personalInfosWF.incomeSources) &&
        Objects.equals(this.ncbInfoM, personalInfosWF.ncbInfoM);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personalId, docNo, docType, thFirstName, thMidName, thLastName, cisNo, dob, applicantType, finalVerifiedIncome, mailingAddress, typeOfFin, mobileNo, vatCode, addresses, cardlinkCustM, incomeInfos, incomeSources, ncbInfoM);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalInfosWF {\n");
    
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    docNo: ").append(toIndentedString(docNo)).append("\n");
    sb.append("    docType: ").append(toIndentedString(docType)).append("\n");
    sb.append("    thFirstName: ").append(toIndentedString(thFirstName)).append("\n");
    sb.append("    thMidName: ").append(toIndentedString(thMidName)).append("\n");
    sb.append("    thLastName: ").append(toIndentedString(thLastName)).append("\n");
    sb.append("    cisNo: ").append(toIndentedString(cisNo)).append("\n");
    sb.append("    dob: ").append(toIndentedString(dob)).append("\n");
    sb.append("    applicantType: ").append(toIndentedString(applicantType)).append("\n");
    sb.append("    finalVerifiedIncome: ").append(toIndentedString(finalVerifiedIncome)).append("\n");
    sb.append("    mailingAddress: ").append(toIndentedString(mailingAddress)).append("\n");
    sb.append("    typeOfFin: ").append(toIndentedString(typeOfFin)).append("\n");
    sb.append("    mobileNo: ").append(toIndentedString(mobileNo)).append("\n");
    sb.append("    vatCode: ").append(toIndentedString(vatCode)).append("\n");
    sb.append("    addresses: ").append(toIndentedString(addresses)).append("\n");
    sb.append("    cardlinkCustM: ").append(toIndentedString(cardlinkCustM)).append("\n");
    sb.append("    incomeInfos: ").append(toIndentedString(incomeInfos)).append("\n");
    sb.append("    incomeSources: ").append(toIndentedString(incomeSources)).append("\n");
    sb.append("    ncbInfoM: ").append(toIndentedString(ncbInfoM)).append("\n");
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

