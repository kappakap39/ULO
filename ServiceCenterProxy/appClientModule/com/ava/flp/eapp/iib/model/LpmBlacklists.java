package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * LpmBlacklists
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class LpmBlacklists   {
  @JsonProperty("blackListCode")
  private String blackListCode = null;

  @JsonProperty("blackListDate")
  private String blackListDate = null;

  @JsonProperty("blackListType")
  private String blackListType = null;

  @JsonProperty("lpmId")
  private String lpmId = null;

  @JsonProperty("inactiveDate")
  private String inactiveDate = null;

  public LpmBlacklists blackListCode(String blackListCode) {
    this.blackListCode = blackListCode;
    return this;
  }

   /**
   * Get blackListCode
   * @return blackListCode
  **/
  @ApiModelProperty(value = "")


  public String getBlackListCode() {
    return blackListCode;
  }

  public void setBlackListCode(String blackListCode) {
    this.blackListCode = blackListCode;
  }

  public LpmBlacklists blackListDate(String blackListDate) {
    this.blackListDate = blackListDate;
    return this;
  }

   /**
   * Get blackListDate
   * @return blackListDate
  **/
  @ApiModelProperty(value = "")


  public String getBlackListDate() {
    return blackListDate;
  }

  public void setBlackListDate(String blackListDate) {
    this.blackListDate = blackListDate;
  }

  public LpmBlacklists blackListType(String blackListType) {
    this.blackListType = blackListType;
    return this;
  }

   /**
   * Get blackListType
   * @return blackListType
  **/
  @ApiModelProperty(value = "")


  public String getBlackListType() {
    return blackListType;
  }

  public void setBlackListType(String blackListType) {
    this.blackListType = blackListType;
  }

  public LpmBlacklists lpmId(String lpmId) {
    this.lpmId = lpmId;
    return this;
  }

   /**
   * Get lpmId
   * @return lpmId
  **/
  @ApiModelProperty(value = "")


  public String getLpmId() {
    return lpmId;
  }

  public void setLpmId(String lpmId) {
    this.lpmId = lpmId;
  }

  public LpmBlacklists inactiveDate(String inactiveDate) {
    this.inactiveDate = inactiveDate;
    return this;
  }

   /**
   * Get inactiveDate
   * @return inactiveDate
  **/
  @ApiModelProperty(value = "")


  public String getInactiveDate() {
    return inactiveDate;
  }

  public void setInactiveDate(String inactiveDate) {
    this.inactiveDate = inactiveDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LpmBlacklists lpmBlacklists = (LpmBlacklists) o;
    return Objects.equals(this.blackListCode, lpmBlacklists.blackListCode) &&
        Objects.equals(this.blackListDate, lpmBlacklists.blackListDate) &&
        Objects.equals(this.blackListType, lpmBlacklists.blackListType) &&
        Objects.equals(this.lpmId, lpmBlacklists.lpmId) &&
        Objects.equals(this.inactiveDate, lpmBlacklists.inactiveDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blackListCode, blackListDate, blackListType, lpmId, inactiveDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LpmBlacklists {\n");
    
    sb.append("    blackListCode: ").append(toIndentedString(blackListCode)).append("\n");
    sb.append("    blackListDate: ").append(toIndentedString(blackListDate)).append("\n");
    sb.append("    blackListType: ").append(toIndentedString(blackListType)).append("\n");
    sb.append("    lpmId: ").append(toIndentedString(lpmId)).append("\n");
    sb.append("    inactiveDate: ").append(toIndentedString(inactiveDate)).append("\n");
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

