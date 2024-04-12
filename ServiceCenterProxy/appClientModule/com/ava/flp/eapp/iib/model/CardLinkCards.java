package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CardLinkCards
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CardLinkCards   {
  @JsonProperty("mainOrgNo")
  private String mainOrgNo = null;

  @JsonProperty("mainCustNo")
  private String mainCustNo = null;

  @JsonProperty("cardNoInEncrypt")
  private String cardNoInEncrypt = null;

  @JsonProperty("blockCode")
  private String blockCode = null;

  @JsonProperty("blockDate")
  private DateTime blockDate = null;

  @JsonProperty("projectCode")
  private String projectCode = null;

  @JsonProperty("supOrgNo")
  private String supOrgNo = null;

  @JsonProperty("supCustNo")
  private String supCustNo = null;

  @JsonProperty("coaProductCode")
  private String coaProductCode = null;

  @JsonProperty("cardCode")
  private String cardCode = null;

  @JsonProperty("cardType")
  private String cardType = null;

  @JsonProperty("cardTypeBillingCycle")
  private String cardTypeBillingCycle = null;

  @JsonProperty("cardLevelEligibleCard")
  private String cardLevelEligibleCard = null;

  @JsonProperty("plasticCode")
  private String plasticCode = null;

  @JsonProperty("paymentCondition")
  private String paymentCondition = null;

  @JsonProperty("paymentFlag")
  private String paymentFlag = null;

  @JsonProperty("creditLimitAmount")
  private BigDecimal creditLimitAmount = null;

  @JsonProperty("month24Profile")
  private String month24Profile = null;

  @JsonProperty("expiryDate")
  private String expiryDate = null;

  @JsonProperty("cardNoInHash")
  private String cardNoInHash = null;

  @JsonProperty("embossname3")
  private String embossname3 = null;

  @JsonProperty("outstandingBal")
  private BigDecimal outstandingBal = null;

  @JsonProperty("lastPaymentDate")
  private DateTime lastPaymentDate = null;

  @JsonProperty("openDate")
  private DateTime openDate = null;

  @JsonProperty("lastIncreaseDate")
  private DateTime lastIncreaseDate = null;

  @JsonProperty("billCycle")
  private BigDecimal billCycle = null;

  @JsonProperty("realOrgNo")
  private String realOrgNo = null;

  @JsonProperty("realCustNo")
  private String realCustNo = null;

  @JsonProperty("autoPayFlag")
  private String autoPayFlag = null;

  @JsonProperty("autoPayAcctNo")
  private String autoPayAcctNo = null;

  @JsonProperty("cardFlag")
  private String cardFlag = null;

  public CardLinkCards mainOrgNo(String mainOrgNo) {
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

  public CardLinkCards mainCustNo(String mainCustNo) {
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

  public CardLinkCards cardNoInEncrypt(String cardNoInEncrypt) {
    this.cardNoInEncrypt = cardNoInEncrypt;
    return this;
  }

   /**
   * Get cardNoInEncrypt
   * @return cardNoInEncrypt
  **/
  @ApiModelProperty(value = "")


  public String getCardNoInEncrypt() {
    return cardNoInEncrypt;
  }

  public void setCardNoInEncrypt(String cardNoInEncrypt) {
    this.cardNoInEncrypt = cardNoInEncrypt;
  }

  public CardLinkCards blockCode(String blockCode) {
    this.blockCode = blockCode;
    return this;
  }

   /**
   * Get blockCode
   * @return blockCode
  **/
  @ApiModelProperty(value = "")


  public String getBlockCode() {
    return blockCode;
  }

  public void setBlockCode(String blockCode) {
    this.blockCode = blockCode;
  }

  public CardLinkCards blockDate(DateTime blockDate) {
    this.blockDate = blockDate;
    return this;
  }

   /**
   * Get blockDate
   * @return blockDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getBlockDate() {
    return blockDate;
  }

  public void setBlockDate(DateTime blockDate) {
    this.blockDate = blockDate;
  }

  public CardLinkCards projectCode(String projectCode) {
    this.projectCode = projectCode;
    return this;
  }

   /**
   * Get projectCode
   * @return projectCode
  **/
  @ApiModelProperty(value = "")


  public String getProjectCode() {
    return projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public CardLinkCards supOrgNo(String supOrgNo) {
    this.supOrgNo = supOrgNo;
    return this;
  }

   /**
   * Get supOrgNo
   * @return supOrgNo
  **/
  @ApiModelProperty(value = "")


  public String getSupOrgNo() {
    return supOrgNo;
  }

  public void setSupOrgNo(String supOrgNo) {
    this.supOrgNo = supOrgNo;
  }

  public CardLinkCards supCustNo(String supCustNo) {
    this.supCustNo = supCustNo;
    return this;
  }

   /**
   * Get supCustNo
   * @return supCustNo
  **/
  @ApiModelProperty(value = "")


  public String getSupCustNo() {
    return supCustNo;
  }

  public void setSupCustNo(String supCustNo) {
    this.supCustNo = supCustNo;
  }

  public CardLinkCards coaProductCode(String coaProductCode) {
    this.coaProductCode = coaProductCode;
    return this;
  }

   /**
   * Get coaProductCode
   * @return coaProductCode
  **/
  @ApiModelProperty(value = "")


  public String getCoaProductCode() {
    return coaProductCode;
  }

  public void setCoaProductCode(String coaProductCode) {
    this.coaProductCode = coaProductCode;
  }

  public CardLinkCards cardCode(String cardCode) {
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

  public CardLinkCards cardType(String cardType) {
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

  public CardLinkCards cardTypeBillingCycle(String cardTypeBillingCycle) {
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

  public CardLinkCards cardLevelEligibleCard(String cardLevelEligibleCard) {
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

  public CardLinkCards plasticCode(String plasticCode) {
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

  public CardLinkCards paymentCondition(String paymentCondition) {
    this.paymentCondition = paymentCondition;
    return this;
  }

   /**
   * Get paymentCondition
   * @return paymentCondition
  **/
  @ApiModelProperty(value = "")


  public String getPaymentCondition() {
    return paymentCondition;
  }

  public void setPaymentCondition(String paymentCondition) {
    this.paymentCondition = paymentCondition;
  }

  public CardLinkCards paymentFlag(String paymentFlag) {
    this.paymentFlag = paymentFlag;
    return this;
  }

   /**
   * Get paymentFlag
   * @return paymentFlag
  **/
  @ApiModelProperty(value = "")


  public String getPaymentFlag() {
    return paymentFlag;
  }

  public void setPaymentFlag(String paymentFlag) {
    this.paymentFlag = paymentFlag;
  }

  public CardLinkCards creditLimitAmount(BigDecimal creditLimitAmount) {
    this.creditLimitAmount = creditLimitAmount;
    return this;
  }

   /**
   * Get creditLimitAmount
   * @return creditLimitAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCreditLimitAmount() {
    return creditLimitAmount;
  }

  public void setCreditLimitAmount(BigDecimal creditLimitAmount) {
    this.creditLimitAmount = creditLimitAmount;
  }

  public CardLinkCards month24Profile(String month24Profile) {
    this.month24Profile = month24Profile;
    return this;
  }

   /**
   * Get month24Profile
   * @return month24Profile
  **/
  @ApiModelProperty(value = "")


  public String getMonth24Profile() {
    return month24Profile;
  }

  public void setMonth24Profile(String month24Profile) {
    this.month24Profile = month24Profile;
  }

  public CardLinkCards expiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
    return this;
  }

   /**
   * Get expiryDate
   * @return expiryDate
  **/
  @ApiModelProperty(value = "")


  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public CardLinkCards cardNoInHash(String cardNoInHash) {
    this.cardNoInHash = cardNoInHash;
    return this;
  }

   /**
   * Get cardNoInHash
   * @return cardNoInHash
  **/
  @ApiModelProperty(value = "")


  public String getCardNoInHash() {
    return cardNoInHash;
  }

  public void setCardNoInHash(String cardNoInHash) {
    this.cardNoInHash = cardNoInHash;
  }

  public CardLinkCards embossname3(String embossname3) {
    this.embossname3 = embossname3;
    return this;
  }

   /**
   * Get embossname3
   * @return embossname3
  **/
  @ApiModelProperty(value = "")


  public String getEmbossname3() {
    return embossname3;
  }

  public void setEmbossname3(String embossname3) {
    this.embossname3 = embossname3;
  }

  public CardLinkCards outstandingBal(BigDecimal outstandingBal) {
    this.outstandingBal = outstandingBal;
    return this;
  }

   /**
   * Get outstandingBal
   * @return outstandingBal
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getOutstandingBal() {
    return outstandingBal;
  }

  public void setOutstandingBal(BigDecimal outstandingBal) {
    this.outstandingBal = outstandingBal;
  }

  public CardLinkCards lastPaymentDate(DateTime lastPaymentDate) {
    this.lastPaymentDate = lastPaymentDate;
    return this;
  }

   /**
   * Get lastPaymentDate
   * @return lastPaymentDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getLastPaymentDate() {
    return lastPaymentDate;
  }

  public void setLastPaymentDate(DateTime lastPaymentDate) {
    this.lastPaymentDate = lastPaymentDate;
  }

  public CardLinkCards openDate(DateTime openDate) {
    this.openDate = openDate;
    return this;
  }

   /**
   * Get openDate
   * @return openDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getOpenDate() {
    return openDate;
  }

  public void setOpenDate(DateTime openDate) {
    this.openDate = openDate;
  }

  public CardLinkCards lastIncreaseDate(DateTime lastIncreaseDate) {
    this.lastIncreaseDate = lastIncreaseDate;
    return this;
  }

   /**
   * Get lastIncreaseDate
   * @return lastIncreaseDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getLastIncreaseDate() {
    return lastIncreaseDate;
  }

  public void setLastIncreaseDate(DateTime lastIncreaseDate) {
    this.lastIncreaseDate = lastIncreaseDate;
  }

  public CardLinkCards billCycle(BigDecimal billCycle) {
    this.billCycle = billCycle;
    return this;
  }

   /**
   * Get billCycle
   * @return billCycle
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBillCycle() {
    return billCycle;
  }

  public void setBillCycle(BigDecimal billCycle) {
    this.billCycle = billCycle;
  }

  public CardLinkCards realOrgNo(String realOrgNo) {
    this.realOrgNo = realOrgNo;
    return this;
  }

   /**
   * Get realOrgNo
   * @return realOrgNo
  **/
  @ApiModelProperty(value = "")


  public String getRealOrgNo() {
    return realOrgNo;
  }

  public void setRealOrgNo(String realOrgNo) {
    this.realOrgNo = realOrgNo;
  }

  public CardLinkCards realCustNo(String realCustNo) {
    this.realCustNo = realCustNo;
    return this;
  }

   /**
   * Get realCustNo
   * @return realCustNo
  **/
  @ApiModelProperty(value = "")


  public String getRealCustNo() {
    return realCustNo;
  }

  public void setRealCustNo(String realCustNo) {
    this.realCustNo = realCustNo;
  }

  public CardLinkCards autoPayFlag(String autoPayFlag) {
    this.autoPayFlag = autoPayFlag;
    return this;
  }

   /**
   * Get autoPayFlag
   * @return autoPayFlag
  **/
  @ApiModelProperty(value = "")


  public String getAutoPayFlag() {
    return autoPayFlag;
  }

  public void setAutoPayFlag(String autoPayFlag) {
    this.autoPayFlag = autoPayFlag;
  }

  public CardLinkCards autoPayAcctNo(String autoPayAcctNo) {
    this.autoPayAcctNo = autoPayAcctNo;
    return this;
  }

   /**
   * Get autoPayAcctNo
   * @return autoPayAcctNo
  **/
  @ApiModelProperty(value = "")


  public String getAutoPayAcctNo() {
    return autoPayAcctNo;
  }

  public void setAutoPayAcctNo(String autoPayAcctNo) {
    this.autoPayAcctNo = autoPayAcctNo;
  }

  public CardLinkCards cardFlag(String cardFlag) {
    this.cardFlag = cardFlag;
    return this;
  }

   /**
   * Get cardFlag
   * @return cardFlag
  **/
  @ApiModelProperty(value = "")


  public String getCardFlag() {
    return cardFlag;
  }

  public void setCardFlag(String cardFlag) {
    this.cardFlag = cardFlag;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CardLinkCards cardLinkCards = (CardLinkCards) o;
    return Objects.equals(this.mainOrgNo, cardLinkCards.mainOrgNo) &&
        Objects.equals(this.mainCustNo, cardLinkCards.mainCustNo) &&
        Objects.equals(this.cardNoInEncrypt, cardLinkCards.cardNoInEncrypt) &&
        Objects.equals(this.blockCode, cardLinkCards.blockCode) &&
        Objects.equals(this.blockDate, cardLinkCards.blockDate) &&
        Objects.equals(this.projectCode, cardLinkCards.projectCode) &&
        Objects.equals(this.supOrgNo, cardLinkCards.supOrgNo) &&
        Objects.equals(this.supCustNo, cardLinkCards.supCustNo) &&
        Objects.equals(this.coaProductCode, cardLinkCards.coaProductCode) &&
        Objects.equals(this.cardCode, cardLinkCards.cardCode) &&
        Objects.equals(this.cardType, cardLinkCards.cardType) &&
        Objects.equals(this.cardTypeBillingCycle, cardLinkCards.cardTypeBillingCycle) &&
        Objects.equals(this.cardLevelEligibleCard, cardLinkCards.cardLevelEligibleCard) &&
        Objects.equals(this.plasticCode, cardLinkCards.plasticCode) &&
        Objects.equals(this.paymentCondition, cardLinkCards.paymentCondition) &&
        Objects.equals(this.paymentFlag, cardLinkCards.paymentFlag) &&
        Objects.equals(this.creditLimitAmount, cardLinkCards.creditLimitAmount) &&
        Objects.equals(this.month24Profile, cardLinkCards.month24Profile) &&
        Objects.equals(this.expiryDate, cardLinkCards.expiryDate) &&
        Objects.equals(this.cardNoInHash, cardLinkCards.cardNoInHash) &&
        Objects.equals(this.embossname3, cardLinkCards.embossname3) &&
        Objects.equals(this.outstandingBal, cardLinkCards.outstandingBal) &&
        Objects.equals(this.lastPaymentDate, cardLinkCards.lastPaymentDate) &&
        Objects.equals(this.openDate, cardLinkCards.openDate) &&
        Objects.equals(this.lastIncreaseDate, cardLinkCards.lastIncreaseDate) &&
        Objects.equals(this.billCycle, cardLinkCards.billCycle) &&
        Objects.equals(this.realOrgNo, cardLinkCards.realOrgNo) &&
        Objects.equals(this.realCustNo, cardLinkCards.realCustNo) &&
        Objects.equals(this.autoPayFlag, cardLinkCards.autoPayFlag) &&
        Objects.equals(this.autoPayAcctNo, cardLinkCards.autoPayAcctNo) &&
        Objects.equals(this.cardFlag, cardLinkCards.cardFlag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mainOrgNo, mainCustNo, cardNoInEncrypt, blockCode, blockDate, projectCode, supOrgNo, supCustNo, coaProductCode, cardCode, cardType, cardTypeBillingCycle, cardLevelEligibleCard, plasticCode, paymentCondition, paymentFlag, creditLimitAmount, month24Profile, expiryDate, cardNoInHash, embossname3, outstandingBal, lastPaymentDate, openDate, lastIncreaseDate, billCycle, realOrgNo, realCustNo, autoPayFlag, autoPayAcctNo, cardFlag);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CardLinkCards {\n");
    
    sb.append("    mainOrgNo: ").append(toIndentedString(mainOrgNo)).append("\n");
    sb.append("    mainCustNo: ").append(toIndentedString(mainCustNo)).append("\n");
    sb.append("    cardNoInEncrypt: ").append(toIndentedString(cardNoInEncrypt)).append("\n");
    sb.append("    blockCode: ").append(toIndentedString(blockCode)).append("\n");
    sb.append("    blockDate: ").append(toIndentedString(blockDate)).append("\n");
    sb.append("    projectCode: ").append(toIndentedString(projectCode)).append("\n");
    sb.append("    supOrgNo: ").append(toIndentedString(supOrgNo)).append("\n");
    sb.append("    supCustNo: ").append(toIndentedString(supCustNo)).append("\n");
    sb.append("    coaProductCode: ").append(toIndentedString(coaProductCode)).append("\n");
    sb.append("    cardCode: ").append(toIndentedString(cardCode)).append("\n");
    sb.append("    cardType: ").append(toIndentedString(cardType)).append("\n");
    sb.append("    cardTypeBillingCycle: ").append(toIndentedString(cardTypeBillingCycle)).append("\n");
    sb.append("    cardLevelEligibleCard: ").append(toIndentedString(cardLevelEligibleCard)).append("\n");
    sb.append("    plasticCode: ").append(toIndentedString(plasticCode)).append("\n");
    sb.append("    paymentCondition: ").append(toIndentedString(paymentCondition)).append("\n");
    sb.append("    paymentFlag: ").append(toIndentedString(paymentFlag)).append("\n");
    sb.append("    creditLimitAmount: ").append(toIndentedString(creditLimitAmount)).append("\n");
    sb.append("    month24Profile: ").append(toIndentedString(month24Profile)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("    cardNoInHash: ").append(toIndentedString(cardNoInHash)).append("\n");
    sb.append("    embossname3: ").append(toIndentedString(embossname3)).append("\n");
    sb.append("    outstandingBal: ").append(toIndentedString(outstandingBal)).append("\n");
    sb.append("    lastPaymentDate: ").append(toIndentedString(lastPaymentDate)).append("\n");
    sb.append("    openDate: ").append(toIndentedString(openDate)).append("\n");
    sb.append("    lastIncreaseDate: ").append(toIndentedString(lastIncreaseDate)).append("\n");
    sb.append("    billCycle: ").append(toIndentedString(billCycle)).append("\n");
    sb.append("    realOrgNo: ").append(toIndentedString(realOrgNo)).append("\n");
    sb.append("    realCustNo: ").append(toIndentedString(realCustNo)).append("\n");
    sb.append("    autoPayFlag: ").append(toIndentedString(autoPayFlag)).append("\n");
    sb.append("    autoPayAcctNo: ").append(toIndentedString(autoPayAcctNo)).append("\n");
    sb.append("    cardFlag: ").append(toIndentedString(cardFlag)).append("\n");
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

