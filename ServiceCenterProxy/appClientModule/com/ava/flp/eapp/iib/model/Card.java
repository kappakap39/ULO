package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Card
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Card   {
  @JsonProperty("cardEntryType")
  private String cardEntryType = null;

  @JsonProperty("cardType")
  private String cardType = null;

  @JsonProperty("cardLevel")
  private String cardLevel = null;

  @JsonProperty("cardNo")
  private String cardNo = null;

  @JsonProperty("cgaApplyChannel")
  private String cgaApplyChannel = null;

  @JsonProperty("cgaCode")
  private String cgaCode = null;

  @JsonProperty("chassisNo")
  private String chassisNo = null;

  @JsonProperty("mainCardNo")
  private String mainCardNo = null;

  @JsonProperty("membershipNo")
  private String membershipNo = null;

  @JsonProperty("plasticCode")
  private String plasticCode = null;

  @JsonProperty("cardlinkCardCode")
  private String cardlinkCardCode = null;

  @JsonProperty("eligibleCardType")
  private String eligibleCardType = null;

  @JsonProperty("eligibleCardLevel")
  private String eligibleCardLevel = null;

  @JsonProperty("eligibleCoaProductCode")
  private String eligibleCoaProductCode = null;

  @JsonProperty("percentLimitMainCard")
  private BigDecimal percentLimitMainCard = null;

  @JsonProperty("cardTransformationFlag")
  private String cardTransformationFlag = null;

  @JsonProperty("cardNoMark")
  private String cardNoMark = null;

  public Card cardEntryType(String cardEntryType) {
    this.cardEntryType = cardEntryType;
    return this;
  }

   /**
   * Get cardEntryType
   * @return cardEntryType
  **/
  @ApiModelProperty(value = "")


  public String getCardEntryType() {
    return cardEntryType;
  }

  public void setCardEntryType(String cardEntryType) {
    this.cardEntryType = cardEntryType;
  }

  public Card cardType(String cardType) {
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

  public Card cardLevel(String cardLevel) {
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

  public Card cardNo(String cardNo) {
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

  public Card cgaApplyChannel(String cgaApplyChannel) {
    this.cgaApplyChannel = cgaApplyChannel;
    return this;
  }

   /**
   * Get cgaApplyChannel
   * @return cgaApplyChannel
  **/
  @ApiModelProperty(value = "")


  public String getCgaApplyChannel() {
    return cgaApplyChannel;
  }

  public void setCgaApplyChannel(String cgaApplyChannel) {
    this.cgaApplyChannel = cgaApplyChannel;
  }

  public Card cgaCode(String cgaCode) {
    this.cgaCode = cgaCode;
    return this;
  }

   /**
   * Get cgaCode
   * @return cgaCode
  **/
  @ApiModelProperty(value = "")


  public String getCgaCode() {
    return cgaCode;
  }

  public void setCgaCode(String cgaCode) {
    this.cgaCode = cgaCode;
  }

  public Card chassisNo(String chassisNo) {
    this.chassisNo = chassisNo;
    return this;
  }

   /**
   * Get chassisNo
   * @return chassisNo
  **/
  @ApiModelProperty(value = "")


  public String getChassisNo() {
    return chassisNo;
  }

  public void setChassisNo(String chassisNo) {
    this.chassisNo = chassisNo;
  }

  public Card mainCardNo(String mainCardNo) {
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

  public Card membershipNo(String membershipNo) {
    this.membershipNo = membershipNo;
    return this;
  }

   /**
   * Get membershipNo
   * @return membershipNo
  **/
  @ApiModelProperty(value = "")


  public String getMembershipNo() {
    return membershipNo;
  }

  public void setMembershipNo(String membershipNo) {
    this.membershipNo = membershipNo;
  }

  public Card plasticCode(String plasticCode) {
    this.plasticCode = plasticCode;
    return this;
  }

   /**
   * Get plasticCode
   * @return plasticCode
  **/
  @ApiModelProperty(value = "")


  public String getPlasticCode() {
    return plasticCode;
  }

  public void setPlasticCode(String plasticCode) {
    this.plasticCode = plasticCode;
  }

  public Card cardlinkCardCode(String cardlinkCardCode) {
    this.cardlinkCardCode = cardlinkCardCode;
    return this;
  }

   /**
   * Get cardlinkCardCode
   * @return cardlinkCardCode
  **/
  @ApiModelProperty(value = "")


  public String getCardlinkCardCode() {
    return cardlinkCardCode;
  }

  public void setCardlinkCardCode(String cardlinkCardCode) {
    this.cardlinkCardCode = cardlinkCardCode;
  }

  public Card eligibleCardType(String eligibleCardType) {
    this.eligibleCardType = eligibleCardType;
    return this;
  }

   /**
   * Get eligibleCardType
   * @return eligibleCardType
  **/
  @ApiModelProperty(value = "")


  public String getEligibleCardType() {
    return eligibleCardType;
  }

  public void setEligibleCardType(String eligibleCardType) {
    this.eligibleCardType = eligibleCardType;
  }

  public Card eligibleCardLevel(String eligibleCardLevel) {
    this.eligibleCardLevel = eligibleCardLevel;
    return this;
  }

   /**
   * Get eligibleCardLevel
   * @return eligibleCardLevel
  **/
  @ApiModelProperty(value = "")


  public String getEligibleCardLevel() {
    return eligibleCardLevel;
  }

  public void setEligibleCardLevel(String eligibleCardLevel) {
    this.eligibleCardLevel = eligibleCardLevel;
  }

  public Card eligibleCoaProductCode(String eligibleCoaProductCode) {
    this.eligibleCoaProductCode = eligibleCoaProductCode;
    return this;
  }

   /**
   * Get eligibleCoaProductCode
   * @return eligibleCoaProductCode
  **/
  @ApiModelProperty(value = "")


  public String getEligibleCoaProductCode() {
    return eligibleCoaProductCode;
  }

  public void setEligibleCoaProductCode(String eligibleCoaProductCode) {
    this.eligibleCoaProductCode = eligibleCoaProductCode;
  }

  public Card percentLimitMainCard(BigDecimal percentLimitMainCard) {
    this.percentLimitMainCard = percentLimitMainCard;
    return this;
  }

   /**
   * Get percentLimitMainCard
   * @return percentLimitMainCard
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPercentLimitMainCard() {
    return percentLimitMainCard;
  }

  public void setPercentLimitMainCard(BigDecimal percentLimitMainCard) {
    this.percentLimitMainCard = percentLimitMainCard;
  }

  public Card cardTransformationFlag(String cardTransformationFlag) {
    this.cardTransformationFlag = cardTransformationFlag;
    return this;
  }

   /**
   * Get cardTransformationFlag
   * @return cardTransformationFlag
  **/
  @ApiModelProperty(value = "")


  public String getCardTransformationFlag() {
    return cardTransformationFlag;
  }

  public void setCardTransformationFlag(String cardTransformationFlag) {
    this.cardTransformationFlag = cardTransformationFlag;
  }

  public Card cardNoMark(String cardNoMark) {
    this.cardNoMark = cardNoMark;
    return this;
  }

   /**
   * Get cardNoMark
   * @return cardNoMark
  **/
  @ApiModelProperty(value = "")


  public String getCardNoMark() {
    return cardNoMark;
  }

  public void setCardNoMark(String cardNoMark) {
    this.cardNoMark = cardNoMark;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return Objects.equals(this.cardEntryType, card.cardEntryType) &&
        Objects.equals(this.cardType, card.cardType) &&
        Objects.equals(this.cardLevel, card.cardLevel) &&
        Objects.equals(this.cardNo, card.cardNo) &&
        Objects.equals(this.cgaApplyChannel, card.cgaApplyChannel) &&
        Objects.equals(this.cgaCode, card.cgaCode) &&
        Objects.equals(this.chassisNo, card.chassisNo) &&
        Objects.equals(this.mainCardNo, card.mainCardNo) &&
        Objects.equals(this.membershipNo, card.membershipNo) &&
        Objects.equals(this.plasticCode, card.plasticCode) &&
        Objects.equals(this.cardlinkCardCode, card.cardlinkCardCode) &&
        Objects.equals(this.eligibleCardType, card.eligibleCardType) &&
        Objects.equals(this.eligibleCardLevel, card.eligibleCardLevel) &&
        Objects.equals(this.eligibleCoaProductCode, card.eligibleCoaProductCode) &&
        Objects.equals(this.percentLimitMainCard, card.percentLimitMainCard) &&
        Objects.equals(this.cardTransformationFlag, card.cardTransformationFlag) &&
        Objects.equals(this.cardNoMark, card.cardNoMark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardEntryType, cardType, cardLevel, cardNo, cgaApplyChannel, cgaCode, chassisNo, mainCardNo, membershipNo, plasticCode, cardlinkCardCode, eligibleCardType, eligibleCardLevel, eligibleCoaProductCode, percentLimitMainCard, cardTransformationFlag, cardNoMark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Card {\n");
    
    sb.append("    cardEntryType: ").append(toIndentedString(cardEntryType)).append("\n");
    sb.append("    cardType: ").append(toIndentedString(cardType)).append("\n");
    sb.append("    cardLevel: ").append(toIndentedString(cardLevel)).append("\n");
    sb.append("    cardNo: ").append(toIndentedString(cardNo)).append("\n");
    sb.append("    cgaApplyChannel: ").append(toIndentedString(cgaApplyChannel)).append("\n");
    sb.append("    cgaCode: ").append(toIndentedString(cgaCode)).append("\n");
    sb.append("    chassisNo: ").append(toIndentedString(chassisNo)).append("\n");
    sb.append("    mainCardNo: ").append(toIndentedString(mainCardNo)).append("\n");
    sb.append("    membershipNo: ").append(toIndentedString(membershipNo)).append("\n");
    sb.append("    plasticCode: ").append(toIndentedString(plasticCode)).append("\n");
    sb.append("    cardlinkCardCode: ").append(toIndentedString(cardlinkCardCode)).append("\n");
    sb.append("    eligibleCardType: ").append(toIndentedString(eligibleCardType)).append("\n");
    sb.append("    eligibleCardLevel: ").append(toIndentedString(eligibleCardLevel)).append("\n");
    sb.append("    eligibleCoaProductCode: ").append(toIndentedString(eligibleCoaProductCode)).append("\n");
    sb.append("    percentLimitMainCard: ").append(toIndentedString(percentLimitMainCard)).append("\n");
    sb.append("    cardTransformationFlag: ").append(toIndentedString(cardTransformationFlag)).append("\n");
    sb.append("    cardNoMark: ").append(toIndentedString(cardNoMark)).append("\n");
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

