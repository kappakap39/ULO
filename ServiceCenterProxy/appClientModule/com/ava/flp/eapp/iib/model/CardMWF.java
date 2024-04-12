package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CardMWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-24T16:24:29.486+07:00")

public class CardMWF   {
  @JsonProperty("cardCode")
  private String cardCode = null;

  @JsonProperty("cardLevel")
  private String cardLevel = null;

  @JsonProperty("plasticType")
  private String plasticType = null;

  @JsonProperty("docNo")
  private String docNo = null;

  @JsonProperty("cardApplyType")
  private String cardApplyType = null;

  @JsonProperty("cardNo")
  private String cardNo = null;

  @JsonProperty("cardNoEncrypted")
  private String cardNoEncrypted = null;

  @JsonProperty("priorityPassNo")
  private String priorityPassNo = null;

  @JsonProperty("cardType")
  private String cardType = null;

  @JsonProperty("cardlinkCustNo")
  private String cardlinkCustNo = null;

  @JsonProperty("cardlinkOrgNo")
  private String cardlinkOrgNo = null;

  @JsonProperty("mainCardNo")
  private String mainCardNo = null;

  @JsonProperty("maincardNoEncrypted")
  private String maincardNoEncrypted = null;

  @JsonProperty("mainCustNo")
  private String mainCustNo = null;

  @JsonProperty("mainOrgNo")
  private String mainOrgNo = null;

  public CardMWF cardCode(String cardCode) {
    this.cardCode = cardCode;
    return this;
  }

   /**
   * Get cardCode
   * @return cardCode
  **/
  @ApiModelProperty(value = "")


  public String getCardCode() {
    return cardCode;
  }

  public void setCardCode(String cardCode) {
    this.cardCode = cardCode;
  }

  public CardMWF cardLevel(String cardLevel) {
    this.cardLevel = cardLevel;
    return this;
  }

   /**
   * Get cardLevel
   * @return cardLevel
  **/
  @ApiModelProperty(value = "")


  public String getCardLevel() {
    return cardLevel;
  }

  public void setCardLevel(String cardLevel) {
    this.cardLevel = cardLevel;
  }

  public CardMWF plasticType(String plasticType) {
    this.plasticType = plasticType;
    return this;
  }

   /**
   * Get plasticType
   * @return plasticType
  **/
  @ApiModelProperty(value = "")


  public String getPlasticType() {
    return plasticType;
  }

  public void setPlasticType(String plasticType) {
    this.plasticType = plasticType;
  }

  public CardMWF docNo(String docNo) {
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

  public CardMWF cardApplyType(String cardApplyType) {
    this.cardApplyType = cardApplyType;
    return this;
  }

   /**
   * Get cardApplyType
   * @return cardApplyType
  **/
  @ApiModelProperty(value = "")


  public String getCardApplyType() {
    return cardApplyType;
  }

  public void setCardApplyType(String cardApplyType) {
    this.cardApplyType = cardApplyType;
  }

  public CardMWF cardNo(String cardNo) {
    this.cardNo = cardNo;
    return this;
  }

   /**
   * Get cardNo
   * @return cardNo
  **/
  @ApiModelProperty(value = "")


  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public CardMWF cardNoEncrypted(String cardNoEncrypted) {
    this.cardNoEncrypted = cardNoEncrypted;
    return this;
  }

   /**
   * Get cardNoEncrypted
   * @return cardNoEncrypted
  **/
  @ApiModelProperty(value = "")


  public String getCardNoEncrypted() {
    return cardNoEncrypted;
  }

  public void setCardNoEncrypted(String cardNoEncrypted) {
    this.cardNoEncrypted = cardNoEncrypted;
  }

  public CardMWF priorityPassNo(String priorityPassNo) {
    this.priorityPassNo = priorityPassNo;
    return this;
  }

   /**
   * Get priorityPassNo
   * @return priorityPassNo
  **/
  @ApiModelProperty(value = "")


  public String getPriorityPassNo() {
    return priorityPassNo;
  }

  public void setPriorityPassNo(String priorityPassNo) {
    this.priorityPassNo = priorityPassNo;
  }

  public CardMWF cardType(String cardType) {
    this.cardType = cardType;
    return this;
  }

   /**
   * Get cardType
   * @return cardType
  **/
  @ApiModelProperty(value = "")


  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public CardMWF cardlinkCustNo(String cardlinkCustNo) {
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

  public CardMWF cardlinkOrgNo(String cardlinkOrgNo) {
    this.cardlinkOrgNo = cardlinkOrgNo;
    return this;
  }

   /**
   * Get cardlinkOrgNo
   * @return cardlinkOrgNo
  **/
  @ApiModelProperty(value = "")


  public String getCardlinkOrgNo() {
    return cardlinkOrgNo;
  }

  public void setCardlinkOrgNo(String cardlinkOrgNo) {
    this.cardlinkOrgNo = cardlinkOrgNo;
  }

  public CardMWF mainCardNo(String mainCardNo) {
    this.mainCardNo = mainCardNo;
    return this;
  }

   /**
   * Get mainCardNo
   * @return mainCardNo
  **/
  @ApiModelProperty(value = "")


  public String getMainCardNo() {
    return mainCardNo;
  }

  public void setMainCardNo(String mainCardNo) {
    this.mainCardNo = mainCardNo;
  }

  public CardMWF maincardNoEncrypted(String maincardNoEncrypted) {
    this.maincardNoEncrypted = maincardNoEncrypted;
    return this;
  }

   /**
   * Get maincardNoEncrypted
   * @return maincardNoEncrypted
  **/
  @ApiModelProperty(value = "")


  public String getMaincardNoEncrypted() {
    return maincardNoEncrypted;
  }

  public void setMaincardNoEncrypted(String maincardNoEncrypted) {
    this.maincardNoEncrypted = maincardNoEncrypted;
  }

  public CardMWF mainCustNo(String mainCustNo) {
    this.mainCustNo = mainCustNo;
    return this;
  }

   /**
   * Get mainCustNo
   * @return mainCustNo
  **/
  @ApiModelProperty(value = "")


  public String getMainCustNo() {
    return mainCustNo;
  }

  public void setMainCustNo(String mainCustNo) {
    this.mainCustNo = mainCustNo;
  }

  public CardMWF mainOrgNo(String mainOrgNo) {
    this.mainOrgNo = mainOrgNo;
    return this;
  }

   /**
   * Get mainOrgNo
   * @return mainOrgNo
  **/
  @ApiModelProperty(value = "")


  public String getMainOrgNo() {
    return mainOrgNo;
  }

  public void setMainOrgNo(String mainOrgNo) {
    this.mainOrgNo = mainOrgNo;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardMWF cardMWF = (CardMWF) o;
    return Objects.equals(this.cardCode, cardMWF.cardCode) &&
        Objects.equals(this.cardLevel, cardMWF.cardLevel) &&
        Objects.equals(this.plasticType, cardMWF.plasticType) &&
        Objects.equals(this.docNo, cardMWF.docNo) &&
        Objects.equals(this.cardApplyType, cardMWF.cardApplyType) &&
        Objects.equals(this.cardNo, cardMWF.cardNo) &&
        Objects.equals(this.cardNoEncrypted, cardMWF.cardNoEncrypted) &&
        Objects.equals(this.priorityPassNo, cardMWF.priorityPassNo) &&
        Objects.equals(this.cardType, cardMWF.cardType) &&
        Objects.equals(this.cardlinkCustNo, cardMWF.cardlinkCustNo) &&
        Objects.equals(this.cardlinkOrgNo, cardMWF.cardlinkOrgNo) &&
        Objects.equals(this.mainCardNo, cardMWF.mainCardNo) &&
        Objects.equals(this.maincardNoEncrypted, cardMWF.maincardNoEncrypted) &&
        Objects.equals(this.mainCustNo, cardMWF.mainCustNo) &&
        Objects.equals(this.mainOrgNo, cardMWF.mainOrgNo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardCode, cardLevel, plasticType, docNo, cardApplyType, cardNo, cardNoEncrypted, priorityPassNo, cardType, cardlinkCustNo, cardlinkOrgNo, mainCardNo, maincardNoEncrypted, mainCustNo, mainOrgNo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CardMWF {\n");
    
    sb.append("    cardCode: ").append(toIndentedString(cardCode)).append("\n");
    sb.append("    cardLevel: ").append(toIndentedString(cardLevel)).append("\n");
    sb.append("    plasticType: ").append(toIndentedString(plasticType)).append("\n");
    sb.append("    docNo: ").append(toIndentedString(docNo)).append("\n");
    sb.append("    cardApplyType: ").append(toIndentedString(cardApplyType)).append("\n");
    sb.append("    cardNo: ").append(toIndentedString(cardNo)).append("\n");
    sb.append("    cardNoEncrypted: ").append(toIndentedString(cardNoEncrypted)).append("\n");
    sb.append("    priorityPassNo: ").append(toIndentedString(priorityPassNo)).append("\n");
    sb.append("    cardType: ").append(toIndentedString(cardType)).append("\n");
    sb.append("    cardlinkCustNo: ").append(toIndentedString(cardlinkCustNo)).append("\n");
    sb.append("    cardlinkOrgNo: ").append(toIndentedString(cardlinkOrgNo)).append("\n");
    sb.append("    mainCardNo: ").append(toIndentedString(mainCardNo)).append("\n");
    sb.append("    maincardNoEncrypted: ").append(toIndentedString(maincardNoEncrypted)).append("\n");
    sb.append("    mainCustNo: ").append(toIndentedString(mainCustNo)).append("\n");
    sb.append("    mainOrgNo: ").append(toIndentedString(mainOrgNo)).append("\n");
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

