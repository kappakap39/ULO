package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.FicoScore;
import com.ava.flp.eapp.iib.model.NcbAccounts;
import com.ava.flp.eapp.iib.model.NcbAddress;
import com.ava.flp.eapp.iib.model.NcbIds;
import com.ava.flp.eapp.iib.model.NcbNames;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NcbInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbInfo   {
  @JsonProperty("consentRefNo")
  private String consentRefNo = null;

  @JsonProperty("dateOfSearch")
  private DateTime dateOfSearch = null;

  @JsonProperty("numTimesOfEnquiry")
  private BigDecimal numTimesOfEnquiry = null;

  @JsonProperty("numTimesOfEnquiry6Month")
  private BigDecimal numTimesOfEnquiry6Month = null;

  @JsonProperty("numberOfPersonalLoanUnderBOTIssuer")
  private BigDecimal numberOfPersonalLoanUnderBOTIssuer = null;

  @JsonProperty("ficoScore")
  private FicoScore ficoScore = null;

  @JsonProperty("ncbAccounts")
  private List<NcbAccounts> ncbAccounts = null;

  @JsonProperty("ncbAddress")
  private List<NcbAddress> ncbAddress = null;

  @JsonProperty("ncbIds")
  private List<NcbIds> ncbIds = null;

  @JsonProperty("ncbNames")
  private List<NcbNames> ncbNames = null;

  public NcbInfo consentRefNo(String consentRefNo) {
    this.consentRefNo = consentRefNo;
    return this;
  }

   /**
   * Get consentRefNo
   * @return consentRefNo
  **/
  @ApiModelProperty(value = "")


  public String getConsentRefNo() {
    return consentRefNo;
  }

  public void setConsentRefNo(String consentRefNo) {
    this.consentRefNo = consentRefNo;
  }

  public NcbInfo dateOfSearch(DateTime dateOfSearch) {
    this.dateOfSearch = dateOfSearch;
    return this;
  }

   /**
   * Get dateOfSearch
   * @return dateOfSearch
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateOfSearch() {
    return dateOfSearch;
  }

  public void setDateOfSearch(DateTime dateOfSearch) {
    this.dateOfSearch = dateOfSearch;
  }

  public NcbInfo numTimesOfEnquiry(BigDecimal numTimesOfEnquiry) {
    this.numTimesOfEnquiry = numTimesOfEnquiry;
    return this;
  }

   /**
   * Get numTimesOfEnquiry
   * @return numTimesOfEnquiry
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumTimesOfEnquiry() {
    return numTimesOfEnquiry;
  }

  public void setNumTimesOfEnquiry(BigDecimal numTimesOfEnquiry) {
    this.numTimesOfEnquiry = numTimesOfEnquiry;
  }

  public NcbInfo numTimesOfEnquiry6Month(BigDecimal numTimesOfEnquiry6Month) {
    this.numTimesOfEnquiry6Month = numTimesOfEnquiry6Month;
    return this;
  }

   /**
   * Get numTimesOfEnquiry6Month
   * @return numTimesOfEnquiry6Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumTimesOfEnquiry6Month() {
    return numTimesOfEnquiry6Month;
  }

  public void setNumTimesOfEnquiry6Month(BigDecimal numTimesOfEnquiry6Month) {
    this.numTimesOfEnquiry6Month = numTimesOfEnquiry6Month;
  }

  public NcbInfo numberOfPersonalLoanUnderBOTIssuer(BigDecimal numberOfPersonalLoanUnderBOTIssuer) {
    this.numberOfPersonalLoanUnderBOTIssuer = numberOfPersonalLoanUnderBOTIssuer;
    return this;
  }

   /**
   * Get numberOfPersonalLoanUnderBOTIssuer
   * @return numberOfPersonalLoanUnderBOTIssuer
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumberOfPersonalLoanUnderBOTIssuer() {
    return numberOfPersonalLoanUnderBOTIssuer;
  }

  public void setNumberOfPersonalLoanUnderBOTIssuer(BigDecimal numberOfPersonalLoanUnderBOTIssuer) {
    this.numberOfPersonalLoanUnderBOTIssuer = numberOfPersonalLoanUnderBOTIssuer;
  }

  public NcbInfo ficoScore(FicoScore ficoScore) {
    this.ficoScore = ficoScore;
    return this;
  }

   /**
   * Get ficoScore
   * @return ficoScore
  **/
  @ApiModelProperty(value = "")

  @Valid

  public FicoScore getFicoScore() {
    return ficoScore;
  }

  public void setFicoScore(FicoScore ficoScore) {
    this.ficoScore = ficoScore;
  }

  public NcbInfo ncbAccounts(List<NcbAccounts> ncbAccounts) {
    this.ncbAccounts = ncbAccounts;
    return this;
  }

  public NcbInfo addNcbAccountsItem(NcbAccounts ncbAccountsItem) {
    if (this.ncbAccounts == null) {
      this.ncbAccounts = new ArrayList<NcbAccounts>();
    }
    this.ncbAccounts.add(ncbAccountsItem);
    return this;
  }

   /**
   * Get ncbAccounts
   * @return ncbAccounts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbAccounts> getNcbAccounts() {
    return ncbAccounts;
  }

  public void setNcbAccounts(List<NcbAccounts> ncbAccounts) {
    this.ncbAccounts = ncbAccounts;
  }

  public NcbInfo ncbAddress(List<NcbAddress> ncbAddress) {
    this.ncbAddress = ncbAddress;
    return this;
  }

  public NcbInfo addNcbAddressItem(NcbAddress ncbAddressItem) {
    if (this.ncbAddress == null) {
      this.ncbAddress = new ArrayList<NcbAddress>();
    }
    this.ncbAddress.add(ncbAddressItem);
    return this;
  }

   /**
   * Get ncbAddress
   * @return ncbAddress
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbAddress> getNcbAddress() {
    return ncbAddress;
  }

  public void setNcbAddress(List<NcbAddress> ncbAddress) {
    this.ncbAddress = ncbAddress;
  }

  public NcbInfo ncbIds(List<NcbIds> ncbIds) {
    this.ncbIds = ncbIds;
    return this;
  }

  public NcbInfo addNcbIdsItem(NcbIds ncbIdsItem) {
    if (this.ncbIds == null) {
      this.ncbIds = new ArrayList<NcbIds>();
    }
    this.ncbIds.add(ncbIdsItem);
    return this;
  }

   /**
   * Get ncbIds
   * @return ncbIds
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbIds> getNcbIds() {
    return ncbIds;
  }

  public void setNcbIds(List<NcbIds> ncbIds) {
    this.ncbIds = ncbIds;
  }

  public NcbInfo ncbNames(List<NcbNames> ncbNames) {
    this.ncbNames = ncbNames;
    return this;
  }

  public NcbInfo addNcbNamesItem(NcbNames ncbNamesItem) {
    if (this.ncbNames == null) {
      this.ncbNames = new ArrayList<NcbNames>();
    }
    this.ncbNames.add(ncbNamesItem);
    return this;
  }

   /**
   * Get ncbNames
   * @return ncbNames
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbNames> getNcbNames() {
    return ncbNames;
  }

  public void setNcbNames(List<NcbNames> ncbNames) {
    this.ncbNames = ncbNames;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbInfo ncbInfo = (NcbInfo) o;
    return Objects.equals(this.consentRefNo, ncbInfo.consentRefNo) &&
        Objects.equals(this.dateOfSearch, ncbInfo.dateOfSearch) &&
        Objects.equals(this.numTimesOfEnquiry, ncbInfo.numTimesOfEnquiry) &&
        Objects.equals(this.numTimesOfEnquiry6Month, ncbInfo.numTimesOfEnquiry6Month) &&
        Objects.equals(this.numberOfPersonalLoanUnderBOTIssuer, ncbInfo.numberOfPersonalLoanUnderBOTIssuer) &&
        Objects.equals(this.ficoScore, ncbInfo.ficoScore) &&
        Objects.equals(this.ncbAccounts, ncbInfo.ncbAccounts) &&
        Objects.equals(this.ncbAddress, ncbInfo.ncbAddress) &&
        Objects.equals(this.ncbIds, ncbInfo.ncbIds) &&
        Objects.equals(this.ncbNames, ncbInfo.ncbNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consentRefNo, dateOfSearch, numTimesOfEnquiry, numTimesOfEnquiry6Month, numberOfPersonalLoanUnderBOTIssuer, ficoScore, ncbAccounts, ncbAddress, ncbIds, ncbNames);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbInfo {\n");
    
    sb.append("    consentRefNo: ").append(toIndentedString(consentRefNo)).append("\n");
    sb.append("    dateOfSearch: ").append(toIndentedString(dateOfSearch)).append("\n");
    sb.append("    numTimesOfEnquiry: ").append(toIndentedString(numTimesOfEnquiry)).append("\n");
    sb.append("    numTimesOfEnquiry6Month: ").append(toIndentedString(numTimesOfEnquiry6Month)).append("\n");
    sb.append("    numberOfPersonalLoanUnderBOTIssuer: ").append(toIndentedString(numberOfPersonalLoanUnderBOTIssuer)).append("\n");
    sb.append("    ficoScore: ").append(toIndentedString(ficoScore)).append("\n");
    sb.append("    ncbAccounts: ").append(toIndentedString(ncbAccounts)).append("\n");
    sb.append("    ncbAddress: ").append(toIndentedString(ncbAddress)).append("\n");
    sb.append("    ncbIds: ").append(toIndentedString(ncbIds)).append("\n");
    sb.append("    ncbNames: ").append(toIndentedString(ncbNames)).append("\n");
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

