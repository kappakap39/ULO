package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CardlinkCustMWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CardlinkCustMWF   {
  @JsonProperty("cardlinkCustNo")
  private String cardlinkCustNo = null;

  @JsonProperty("orgId")
  private String orgId = null;

  public CardlinkCustMWF cardlinkCustNo(String cardlinkCustNo) {
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

  public CardlinkCustMWF orgId(String orgId) {
    this.orgId = orgId;
    return this;
  }

   /**
   * Get orgId
   * @return orgId
  **/
  @ApiModelProperty(value = "")


  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardlinkCustMWF cardlinkCustMWF = (CardlinkCustMWF) o;
    return Objects.equals(this.cardlinkCustNo, cardlinkCustMWF.cardlinkCustNo) &&
        Objects.equals(this.orgId, cardlinkCustMWF.orgId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardlinkCustNo, orgId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CardlinkCustMWF {\n");
    
    sb.append("    cardlinkCustNo: ").append(toIndentedString(cardlinkCustNo)).append("\n");
    sb.append("    orgId: ").append(toIndentedString(orgId)).append("\n");
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

