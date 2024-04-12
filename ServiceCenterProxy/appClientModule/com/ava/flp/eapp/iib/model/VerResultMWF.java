package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.OrCodesWF;
import com.ava.flp.eapp.iib.model.WebsitesWF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VerResultMWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class VerResultMWF   {
  @JsonProperty("verifyCustResult")
  private String verifyCustResult = null;

  @JsonProperty("orCodes")
  private List<OrCodesWF> orCodes = null;

  @JsonProperty("websites")
  private List<WebsitesWF> websites = null;

  public VerResultMWF verifyCustResult(String verifyCustResult) {
    this.verifyCustResult = verifyCustResult;
    return this;
  }

   /**
   * Get verifyCustResult
   * @return verifyCustResult
  **/
  @ApiModelProperty(value = "")


  public String getVerifyCustResult() {
    return verifyCustResult;
  }

  public void setVerifyCustResult(String verifyCustResult) {
    this.verifyCustResult = verifyCustResult;
  }

  public VerResultMWF orCodes(List<OrCodesWF> orCodes) {
    this.orCodes = orCodes;
    return this;
  }

  public VerResultMWF addOrCodesItem(OrCodesWF orCodesItem) {
    if (this.orCodes == null) {
      this.orCodes = new ArrayList<OrCodesWF>();
    }
    this.orCodes.add(orCodesItem);
    return this;
  }

   /**
   * Get orCodes
   * @return orCodes
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<OrCodesWF> getOrCodes() {
    return orCodes;
  }

  public void setOrCodes(List<OrCodesWF> orCodes) {
    this.orCodes = orCodes;
  }

  public VerResultMWF websites(List<WebsitesWF> websites) {
    this.websites = websites;
    return this;
  }

  public VerResultMWF addWebsitesItem(WebsitesWF websitesItem) {
    if (this.websites == null) {
      this.websites = new ArrayList<WebsitesWF>();
    }
    this.websites.add(websitesItem);
    return this;
  }

   /**
   * Get websites
   * @return websites
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<WebsitesWF> getWebsites() {
    return websites;
  }

  public void setWebsites(List<WebsitesWF> websites) {
    this.websites = websites;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VerResultMWF verResultMWF = (VerResultMWF) o;
    return Objects.equals(this.verifyCustResult, verResultMWF.verifyCustResult) &&
        Objects.equals(this.orCodes, verResultMWF.orCodes) &&
        Objects.equals(this.websites, verResultMWF.websites);
  }

  @Override
  public int hashCode() {
    return Objects.hash(verifyCustResult, orCodes, websites);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VerResultMWF {\n");
    
    sb.append("    verifyCustResult: ").append(toIndentedString(verifyCustResult)).append("\n");
    sb.append("    orCodes: ").append(toIndentedString(orCodes)).append("\n");
    sb.append("    websites: ").append(toIndentedString(websites)).append("\n");
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

