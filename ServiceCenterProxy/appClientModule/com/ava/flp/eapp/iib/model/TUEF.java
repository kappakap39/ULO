package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.FicoDataTUEF;
import com.ava.flp.eapp.iib.model.NcbAccountsTUEF;
import com.ava.flp.eapp.iib.model.NcbAddressesTUEF;
import com.ava.flp.eapp.iib.model.NcbDisputeRemarksTUEF;
import com.ava.flp.eapp.iib.model.NcbEnquirysTUEF;
import com.ava.flp.eapp.iib.model.NcbHistorysTUEF;
import com.ava.flp.eapp.iib.model.NcbIdsTUEF;
import com.ava.flp.eapp.iib.model.NcbNamesTUEF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * TUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class TUEF   {
  @JsonProperty("ncbNames")
  private List<NcbNamesTUEF> ncbNames = null;

  @JsonProperty("ncbIds")
  private List<NcbIdsTUEF> ncbIds = null;

  @JsonProperty("ncbAddresses")
  private List<NcbAddressesTUEF> ncbAddresses = null;

  @JsonProperty("ncbAccounts")
  private List<NcbAccountsTUEF> ncbAccounts = null;

  @JsonProperty("ncbHistorys")
  private List<NcbHistorysTUEF> ncbHistorys = null;

  @JsonProperty("ncbEnquirys")
  private List<NcbEnquirysTUEF> ncbEnquirys = null;

  @JsonProperty("ncbDisputeRemarks")
  private List<NcbDisputeRemarksTUEF> ncbDisputeRemarks = null;

  @JsonProperty("ficoData")
  private FicoDataTUEF ficoData = null;

  public TUEF ncbNames(List<NcbNamesTUEF> ncbNames) {
    this.ncbNames = ncbNames;
    return this;
  }

  public TUEF addNcbNamesItem(NcbNamesTUEF ncbNamesItem) {
    if (this.ncbNames == null) {
      this.ncbNames = new ArrayList<NcbNamesTUEF>();
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

  public List<NcbNamesTUEF> getNcbNames() {
    return ncbNames;
  }

  public void setNcbNames(List<NcbNamesTUEF> ncbNames) {
    this.ncbNames = ncbNames;
  }

  public TUEF ncbIds(List<NcbIdsTUEF> ncbIds) {
    this.ncbIds = ncbIds;
    return this;
  }

  public TUEF addNcbIdsItem(NcbIdsTUEF ncbIdsItem) {
    if (this.ncbIds == null) {
      this.ncbIds = new ArrayList<NcbIdsTUEF>();
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

  public List<NcbIdsTUEF> getNcbIds() {
    return ncbIds;
  }

  public void setNcbIds(List<NcbIdsTUEF> ncbIds) {
    this.ncbIds = ncbIds;
  }

  public TUEF ncbAddresses(List<NcbAddressesTUEF> ncbAddresses) {
    this.ncbAddresses = ncbAddresses;
    return this;
  }

  public TUEF addNcbAddressesItem(NcbAddressesTUEF ncbAddressesItem) {
    if (this.ncbAddresses == null) {
      this.ncbAddresses = new ArrayList<NcbAddressesTUEF>();
    }
    this.ncbAddresses.add(ncbAddressesItem);
    return this;
  }

   /**
   * Get ncbAddresses
   * @return ncbAddresses
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbAddressesTUEF> getNcbAddresses() {
    return ncbAddresses;
  }

  public void setNcbAddresses(List<NcbAddressesTUEF> ncbAddresses) {
    this.ncbAddresses = ncbAddresses;
  }

  public TUEF ncbAccounts(List<NcbAccountsTUEF> ncbAccounts) {
    this.ncbAccounts = ncbAccounts;
    return this;
  }

  public TUEF addNcbAccountsItem(NcbAccountsTUEF ncbAccountsItem) {
    if (this.ncbAccounts == null) {
      this.ncbAccounts = new ArrayList<NcbAccountsTUEF>();
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

  public List<NcbAccountsTUEF> getNcbAccounts() {
    return ncbAccounts;
  }

  public void setNcbAccounts(List<NcbAccountsTUEF> ncbAccounts) {
    this.ncbAccounts = ncbAccounts;
  }

  public TUEF ncbHistorys(List<NcbHistorysTUEF> ncbHistorys) {
    this.ncbHistorys = ncbHistorys;
    return this;
  }

  public TUEF addNcbHistorysItem(NcbHistorysTUEF ncbHistorysItem) {
    if (this.ncbHistorys == null) {
      this.ncbHistorys = new ArrayList<NcbHistorysTUEF>();
    }
    this.ncbHistorys.add(ncbHistorysItem);
    return this;
  }

   /**
   * Get ncbHistorys
   * @return ncbHistorys
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbHistorysTUEF> getNcbHistorys() {
    return ncbHistorys;
  }

  public void setNcbHistorys(List<NcbHistorysTUEF> ncbHistorys) {
    this.ncbHistorys = ncbHistorys;
  }

  public TUEF ncbEnquirys(List<NcbEnquirysTUEF> ncbEnquirys) {
    this.ncbEnquirys = ncbEnquirys;
    return this;
  }

  public TUEF addNcbEnquirysItem(NcbEnquirysTUEF ncbEnquirysItem) {
    if (this.ncbEnquirys == null) {
      this.ncbEnquirys = new ArrayList<NcbEnquirysTUEF>();
    }
    this.ncbEnquirys.add(ncbEnquirysItem);
    return this;
  }

   /**
   * Get ncbEnquirys
   * @return ncbEnquirys
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbEnquirysTUEF> getNcbEnquirys() {
    return ncbEnquirys;
  }

  public void setNcbEnquirys(List<NcbEnquirysTUEF> ncbEnquirys) {
    this.ncbEnquirys = ncbEnquirys;
  }

  public TUEF ncbDisputeRemarks(List<NcbDisputeRemarksTUEF> ncbDisputeRemarks) {
    this.ncbDisputeRemarks = ncbDisputeRemarks;
    return this;
  }

  public TUEF addNcbDisputeRemarksItem(NcbDisputeRemarksTUEF ncbDisputeRemarksItem) {
    if (this.ncbDisputeRemarks == null) {
      this.ncbDisputeRemarks = new ArrayList<NcbDisputeRemarksTUEF>();
    }
    this.ncbDisputeRemarks.add(ncbDisputeRemarksItem);
    return this;
  }

   /**
   * Get ncbDisputeRemarks
   * @return ncbDisputeRemarks
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbDisputeRemarksTUEF> getNcbDisputeRemarks() {
    return ncbDisputeRemarks;
  }

  public void setNcbDisputeRemarks(List<NcbDisputeRemarksTUEF> ncbDisputeRemarks) {
    this.ncbDisputeRemarks = ncbDisputeRemarks;
  }

  public TUEF ficoData(FicoDataTUEF ficoData) {
    this.ficoData = ficoData;
    return this;
  }

   /**
   * Get ficoData
   * @return ficoData
  **/
  @ApiModelProperty(value = "")

  @Valid

  public FicoDataTUEF getFicoData() {
    return ficoData;
  }

  public void setFicoData(FicoDataTUEF ficoData) {
    this.ficoData = ficoData;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TUEF TUEF = (TUEF) o;
    return Objects.equals(this.ncbNames, TUEF.ncbNames) &&
        Objects.equals(this.ncbIds, TUEF.ncbIds) &&
        Objects.equals(this.ncbAddresses, TUEF.ncbAddresses) &&
        Objects.equals(this.ncbAccounts, TUEF.ncbAccounts) &&
        Objects.equals(this.ncbHistorys, TUEF.ncbHistorys) &&
        Objects.equals(this.ncbEnquirys, TUEF.ncbEnquirys) &&
        Objects.equals(this.ncbDisputeRemarks, TUEF.ncbDisputeRemarks) &&
        Objects.equals(this.ficoData, TUEF.ficoData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ncbNames, ncbIds, ncbAddresses, ncbAccounts, ncbHistorys, ncbEnquirys, ncbDisputeRemarks, ficoData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TUEF {\n");
    
    sb.append("    ncbNames: ").append(toIndentedString(ncbNames)).append("\n");
    sb.append("    ncbIds: ").append(toIndentedString(ncbIds)).append("\n");
    sb.append("    ncbAddresses: ").append(toIndentedString(ncbAddresses)).append("\n");
    sb.append("    ncbAccounts: ").append(toIndentedString(ncbAccounts)).append("\n");
    sb.append("    ncbHistorys: ").append(toIndentedString(ncbHistorys)).append("\n");
    sb.append("    ncbEnquirys: ").append(toIndentedString(ncbEnquirys)).append("\n");
    sb.append("    ncbDisputeRemarks: ").append(toIndentedString(ncbDisputeRemarks)).append("\n");
    sb.append("    ficoData: ").append(toIndentedString(ficoData)).append("\n");
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

