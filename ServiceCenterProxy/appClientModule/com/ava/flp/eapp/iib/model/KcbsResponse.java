package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.ResultKCBS;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KcbsResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KcbsResponse   {
  @JsonProperty("consentRefNo")
  private String consentRefNo = null;

  @JsonProperty("dateOfSearch")
  private String dateOfSearch = null;

  @JsonProperty("result")
  private ResultKCBS result = null;

  public KcbsResponse consentRefNo(String consentRefNo) {
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

  public KcbsResponse dateOfSearch(String dateOfSearch) {
    this.dateOfSearch = dateOfSearch;
    return this;
  }

   /**
   * Get dateOfSearch
   * @return dateOfSearch
  **/
  @ApiModelProperty(value = "")


  public String getDateOfSearch() {
    return dateOfSearch;
  }

  public void setDateOfSearch(String dateOfSearch) {
    this.dateOfSearch = dateOfSearch;
  }

  public KcbsResponse result(ResultKCBS result) {
    this.result = result;
    return this;
  }

   /**
   * Get result
   * @return result
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ResultKCBS getResult() {
    return result;
  }

  public void setResult(ResultKCBS result) {
    this.result = result;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KcbsResponse kcbsResponse = (KcbsResponse) o;
    return Objects.equals(this.consentRefNo, kcbsResponse.consentRefNo) &&
        Objects.equals(this.dateOfSearch, kcbsResponse.dateOfSearch) &&
        Objects.equals(this.result, kcbsResponse.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consentRefNo, dateOfSearch, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KcbsResponse {\n");
    
    sb.append("    consentRefNo: ").append(toIndentedString(consentRefNo)).append("\n");
    sb.append("    dateOfSearch: ").append(toIndentedString(dateOfSearch)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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

