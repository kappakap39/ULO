package com.ava.flp.eapp.submitapplication.model;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class Card   {
  @JsonProperty("cardType")
  private String cardType = null;

  @JsonProperty("cardLevel")
  private String cardLevel = null;

  @JsonProperty("cardEntryType")
  private String cardEntryType = null;

  @JsonProperty("membershipNo")
  private String membershipNo = null;

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

  @JsonProperty("percentLimitMainCard")
  private BigDecimal percentLimitMainCard = null;

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return Objects.equals(this.cardType, card.cardType) &&
        Objects.equals(this.cardLevel, card.cardLevel) &&
        Objects.equals(this.cardEntryType, card.cardEntryType) &&
        Objects.equals(this.membershipNo, card.membershipNo) &&
        Objects.equals(this.cardNo, card.cardNo) &&
        Objects.equals(this.cgaApplyChannel, card.cgaApplyChannel) &&
        Objects.equals(this.cgaCode, card.cgaCode) &&
        Objects.equals(this.chassisNo, card.chassisNo) &&
        Objects.equals(this.mainCardNo, card.mainCardNo) &&
        Objects.equals(this.percentLimitMainCard, card.percentLimitMainCard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardType, cardLevel, cardEntryType, membershipNo, cardNo, cgaApplyChannel, cgaCode, chassisNo, mainCardNo, percentLimitMainCard);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Card {\n");
    
    sb.append("    cardType: ").append(toIndentedString(cardType)).append("\n");
    sb.append("    cardLevel: ").append(toIndentedString(cardLevel)).append("\n");
    sb.append("    cardEntryType: ").append(toIndentedString(cardEntryType)).append("\n");
    sb.append("    membershipNo: ").append(toIndentedString(membershipNo)).append("\n");
    sb.append("    cardNo: ").append(toIndentedString(cardNo)).append("\n");
    sb.append("    cgaApplyChannel: ").append(toIndentedString(cgaApplyChannel)).append("\n");
    sb.append("    cgaCode: ").append(toIndentedString(cgaCode)).append("\n");
    sb.append("    chassisNo: ").append(toIndentedString(chassisNo)).append("\n");
    sb.append("    mainCardNo: ").append(toIndentedString(mainCardNo)).append("\n");
    sb.append("    percentLimitMainCard: ").append(toIndentedString(percentLimitMainCard)).append("\n");
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
  
  //MLP Additional
  @JsonProperty("cardLinkcardCode")
  private String cardLinkcardCode = null;
  
  @JsonProperty("plasticCode")
  private String plasticCode = null;
  
  @JsonProperty("cardLinkCustomerId")
  private String cardLinkCustomerId = null;
  
  public String getCardLinkcardCode() {
	return cardLinkcardCode;
}

public void setCardLinkcardCode(String cardLinkcardCode) {
	this.cardLinkcardCode = cardLinkcardCode;
}

public String getPlasticCode() {
	return plasticCode;
}

public void setPlasticCode(String plasticCode) {
	this.plasticCode = plasticCode;
}

public String getCardLinkCustomerId() {
	return cardLinkCustomerId;
}

public void setCardLinkCustomerId(String cardLinkCustomerId) {
	this.cardLinkCustomerId = cardLinkCustomerId;
}
}

