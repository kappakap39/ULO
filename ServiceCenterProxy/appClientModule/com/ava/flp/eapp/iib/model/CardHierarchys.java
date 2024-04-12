package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CardHierarchys
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CardHierarchys   {
  @JsonProperty("cardCode")
  private String cardCode = null;

  @JsonProperty("cardCodeDesc")
  private String cardCodeDesc = null;

  @JsonProperty("cardLevelDesc")
  private String cardLevelDesc = null;

  @JsonProperty("cardLevelEligibleCard")
  private String cardLevelEligibleCard = null;

  @JsonProperty("cardType")
  private String cardType = null;

  @JsonProperty("cardTypeBillingCycle")
  private String cardTypeBillingCycle = null;

  @JsonProperty("kBankProductCode")
  private String kBankProductCode = null;

  @JsonProperty("plasticCode")
  private String plasticCode = null;

  @JsonProperty("plasticCodeDesc")
  private String plasticCodeDesc = null;

  @JsonProperty("productId")
  private String productId = null;

  public CardHierarchys cardCode(String cardCode) {
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

  public CardHierarchys cardCodeDesc(String cardCodeDesc) {
    this.cardCodeDesc = cardCodeDesc;
    return this;
  }

   /**
   * Get cardCodeDesc
   * @return cardCodeDesc
  **/
  @ApiModelProperty(value = "")


  public String getCardCodeDesc() {
    return cardCodeDesc;
  }

  public void setCardCodeDesc(String cardCodeDesc) {
    this.cardCodeDesc = cardCodeDesc;
  }

  public CardHierarchys cardLevelDesc(String cardLevelDesc) {
    this.cardLevelDesc = cardLevelDesc;
    return this;
  }

   /**
   * Get cardLevelDesc
   * @return cardLevelDesc
  **/
  @ApiModelProperty(value = "")


  public String getCardLevelDesc() {
    return cardLevelDesc;
  }

  public void setCardLevelDesc(String cardLevelDesc) {
    this.cardLevelDesc = cardLevelDesc;
  }

  public CardHierarchys cardLevelEligibleCard(String cardLevelEligibleCard) {
    this.cardLevelEligibleCard = cardLevelEligibleCard;
    return this;
  }

   /**
   * Get cardLevelEligibleCard
   * @return cardLevelEligibleCard
  **/
  @ApiModelProperty(value = "")


  public String getCardLevelEligibleCard() {
    return cardLevelEligibleCard;
  }

  public void setCardLevelEligibleCard(String cardLevelEligibleCard) {
    this.cardLevelEligibleCard = cardLevelEligibleCard;
  }

  public CardHierarchys cardType(String cardType) {
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

  public CardHierarchys cardTypeBillingCycle(String cardTypeBillingCycle) {
    this.cardTypeBillingCycle = cardTypeBillingCycle;
    return this;
  }

   /**
   * Get cardTypeBillingCycle
   * @return cardTypeBillingCycle
  **/
  @ApiModelProperty(value = "")


  public String getCardTypeBillingCycle() {
    return cardTypeBillingCycle;
  }

  public void setCardTypeBillingCycle(String cardTypeBillingCycle) {
    this.cardTypeBillingCycle = cardTypeBillingCycle;
  }

  public CardHierarchys kBankProductCode(String kBankProductCode) {
    this.kBankProductCode = kBankProductCode;
    return this;
  }

   /**
   * Get kBankProductCode
   * @return kBankProductCode
  **/
  @ApiModelProperty(value = "")


  public String getKBankProductCode() {
    return kBankProductCode;
  }

  public void setKBankProductCode(String kBankProductCode) {
    this.kBankProductCode = kBankProductCode;
  }

  public CardHierarchys plasticCode(String plasticCode) {
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

  public CardHierarchys plasticCodeDesc(String plasticCodeDesc) {
    this.plasticCodeDesc = plasticCodeDesc;
    return this;
  }

   /**
   * Get plasticCodeDesc
   * @return plasticCodeDesc
  **/
  @ApiModelProperty(value = "")


  public String getPlasticCodeDesc() {
    return plasticCodeDesc;
  }

  public void setPlasticCodeDesc(String plasticCodeDesc) {
    this.plasticCodeDesc = plasticCodeDesc;
  }

  public CardHierarchys productId(String productId) {
    this.productId = productId;
    return this;
  }

   /**
   * Get productId
   * @return productId
  **/
  @ApiModelProperty(value = "")


  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardHierarchys cardHierarchys = (CardHierarchys) o;
    return Objects.equals(this.cardCode, cardHierarchys.cardCode) &&
        Objects.equals(this.cardCodeDesc, cardHierarchys.cardCodeDesc) &&
        Objects.equals(this.cardLevelDesc, cardHierarchys.cardLevelDesc) &&
        Objects.equals(this.cardLevelEligibleCard, cardHierarchys.cardLevelEligibleCard) &&
        Objects.equals(this.cardType, cardHierarchys.cardType) &&
        Objects.equals(this.cardTypeBillingCycle, cardHierarchys.cardTypeBillingCycle) &&
        Objects.equals(this.kBankProductCode, cardHierarchys.kBankProductCode) &&
        Objects.equals(this.plasticCode, cardHierarchys.plasticCode) &&
        Objects.equals(this.plasticCodeDesc, cardHierarchys.plasticCodeDesc) &&
        Objects.equals(this.productId, cardHierarchys.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardCode, cardCodeDesc, cardLevelDesc, cardLevelEligibleCard, cardType, cardTypeBillingCycle, kBankProductCode, plasticCode, plasticCodeDesc, productId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CardHierarchys {\n");
    
    sb.append("    cardCode: ").append(toIndentedString(cardCode)).append("\n");
    sb.append("    cardCodeDesc: ").append(toIndentedString(cardCodeDesc)).append("\n");
    sb.append("    cardLevelDesc: ").append(toIndentedString(cardLevelDesc)).append("\n");
    sb.append("    cardLevelEligibleCard: ").append(toIndentedString(cardLevelEligibleCard)).append("\n");
    sb.append("    cardType: ").append(toIndentedString(cardType)).append("\n");
    sb.append("    cardTypeBillingCycle: ").append(toIndentedString(cardTypeBillingCycle)).append("\n");
    sb.append("    kBankProductCode: ").append(toIndentedString(kBankProductCode)).append("\n");
    sb.append("    plasticCode: ").append(toIndentedString(plasticCode)).append("\n");
    sb.append("    plasticCodeDesc: ").append(toIndentedString(plasticCodeDesc)).append("\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
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

