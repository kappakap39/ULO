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
 * NcbInfoMWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbInfoMWF   {
  @JsonProperty("trackingCode")
  private String trackingCode = null;

  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("ficoScore")
  private String ficoScore = null;

  @JsonProperty("ficoErrorCode")
  private String ficoErrorCode = null;

  @JsonProperty("ficoErrorMsg")
  private String ficoErrorMsg = null;

  @JsonProperty("ficoReason1Code")
  private String ficoReason1Code = null;

  @JsonProperty("ficoReason1Desc")
  private String ficoReason1Desc = null;

  @JsonProperty("ficoReason2Code")
  private String ficoReason2Code = null;

  @JsonProperty("ficoReason2Desc")
  private String ficoReason2Desc = null;

  @JsonProperty("ficoReason3Code")
  private String ficoReason3Code = null;

  @JsonProperty("ficoReason3Desc")
  private String ficoReason3Desc = null;

  @JsonProperty("ficoReason4Code")
  private String ficoReason4Code = null;

  @JsonProperty("ficoReason4Desc")
  private String ficoReason4Desc = null;

  @JsonProperty("consentRefNo")
  private String consentRefNo = null;

  @JsonProperty("noTimesEnquiry")
  private BigDecimal noTimesEnquiry = null;

  @JsonProperty("noTimesEnquiry6M")
  private BigDecimal noTimesEnquiry6M = null;

  @JsonProperty("noCCCard")
  private BigDecimal noCCCard = null;

  @JsonProperty("noCCOutStanding")
  private BigDecimal noCCOutStanding = null;

  @JsonProperty("personalLoanUnderBOTIssuer")
  private BigDecimal personalLoanUnderBOTIssuer = null;

  @JsonProperty("oldestNcbNew")
  private DateTime oldestNcbNew = null;

  public NcbInfoMWF trackingCode(String trackingCode) {
    this.trackingCode = trackingCode;
    return this;
  }

   /**
   * Get trackingCode
   * @return trackingCode
  **/
  @ApiModelProperty(value = "")


  public String getTrackingCode() {
    return trackingCode;
  }

  public void setTrackingCode(String trackingCode) {
    this.trackingCode = trackingCode;
  }

  public NcbInfoMWF personalId(String personalId) {
    this.personalId = personalId;
    return this;
  }

   /**
   * Get personalId
   * @return personalId
  **/
  @ApiModelProperty(value = "")


  public String getPersonalId() {
    return personalId;
  }

  public void setPersonalId(String personalId) {
    this.personalId = personalId;
  }

  public NcbInfoMWF ficoScore(String ficoScore) {
    this.ficoScore = ficoScore;
    return this;
  }

   /**
   * Get ficoScore
   * @return ficoScore
  **/
  @ApiModelProperty(value = "")


  public String getFicoScore() {
    return ficoScore;
  }

  public void setFicoScore(String ficoScore) {
    this.ficoScore = ficoScore;
  }

  public NcbInfoMWF ficoErrorCode(String ficoErrorCode) {
    this.ficoErrorCode = ficoErrorCode;
    return this;
  }

   /**
   * Get ficoErrorCode
   * @return ficoErrorCode
  **/
  @ApiModelProperty(value = "")


  public String getFicoErrorCode() {
    return ficoErrorCode;
  }

  public void setFicoErrorCode(String ficoErrorCode) {
    this.ficoErrorCode = ficoErrorCode;
  }

  public NcbInfoMWF ficoErrorMsg(String ficoErrorMsg) {
    this.ficoErrorMsg = ficoErrorMsg;
    return this;
  }

   /**
   * Get ficoErrorMsg
   * @return ficoErrorMsg
  **/
  @ApiModelProperty(value = "")


  public String getFicoErrorMsg() {
    return ficoErrorMsg;
  }

  public void setFicoErrorMsg(String ficoErrorMsg) {
    this.ficoErrorMsg = ficoErrorMsg;
  }

  public NcbInfoMWF ficoReason1Code(String ficoReason1Code) {
    this.ficoReason1Code = ficoReason1Code;
    return this;
  }

   /**
   * Get ficoReason1Code
   * @return ficoReason1Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason1Code() {
    return ficoReason1Code;
  }

  public void setFicoReason1Code(String ficoReason1Code) {
    this.ficoReason1Code = ficoReason1Code;
  }

  public NcbInfoMWF ficoReason1Desc(String ficoReason1Desc) {
    this.ficoReason1Desc = ficoReason1Desc;
    return this;
  }

   /**
   * Get ficoReason1Desc
   * @return ficoReason1Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason1Desc() {
    return ficoReason1Desc;
  }

  public void setFicoReason1Desc(String ficoReason1Desc) {
    this.ficoReason1Desc = ficoReason1Desc;
  }

  public NcbInfoMWF ficoReason2Code(String ficoReason2Code) {
    this.ficoReason2Code = ficoReason2Code;
    return this;
  }

   /**
   * Get ficoReason2Code
   * @return ficoReason2Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason2Code() {
    return ficoReason2Code;
  }

  public void setFicoReason2Code(String ficoReason2Code) {
    this.ficoReason2Code = ficoReason2Code;
  }

  public NcbInfoMWF ficoReason2Desc(String ficoReason2Desc) {
    this.ficoReason2Desc = ficoReason2Desc;
    return this;
  }

   /**
   * Get ficoReason2Desc
   * @return ficoReason2Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason2Desc() {
    return ficoReason2Desc;
  }

  public void setFicoReason2Desc(String ficoReason2Desc) {
    this.ficoReason2Desc = ficoReason2Desc;
  }

  public NcbInfoMWF ficoReason3Code(String ficoReason3Code) {
    this.ficoReason3Code = ficoReason3Code;
    return this;
  }

   /**
   * Get ficoReason3Code
   * @return ficoReason3Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason3Code() {
    return ficoReason3Code;
  }

  public void setFicoReason3Code(String ficoReason3Code) {
    this.ficoReason3Code = ficoReason3Code;
  }

  public NcbInfoMWF ficoReason3Desc(String ficoReason3Desc) {
    this.ficoReason3Desc = ficoReason3Desc;
    return this;
  }

   /**
   * Get ficoReason3Desc
   * @return ficoReason3Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason3Desc() {
    return ficoReason3Desc;
  }

  public void setFicoReason3Desc(String ficoReason3Desc) {
    this.ficoReason3Desc = ficoReason3Desc;
  }

  public NcbInfoMWF ficoReason4Code(String ficoReason4Code) {
    this.ficoReason4Code = ficoReason4Code;
    return this;
  }

   /**
   * Get ficoReason4Code
   * @return ficoReason4Code
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason4Code() {
    return ficoReason4Code;
  }

  public void setFicoReason4Code(String ficoReason4Code) {
    this.ficoReason4Code = ficoReason4Code;
  }

  public NcbInfoMWF ficoReason4Desc(String ficoReason4Desc) {
    this.ficoReason4Desc = ficoReason4Desc;
    return this;
  }

   /**
   * Get ficoReason4Desc
   * @return ficoReason4Desc
  **/
  @ApiModelProperty(value = "")


  public String getFicoReason4Desc() {
    return ficoReason4Desc;
  }

  public void setFicoReason4Desc(String ficoReason4Desc) {
    this.ficoReason4Desc = ficoReason4Desc;
  }

  public NcbInfoMWF consentRefNo(String consentRefNo) {
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

  public NcbInfoMWF noTimesEnquiry(BigDecimal noTimesEnquiry) {
    this.noTimesEnquiry = noTimesEnquiry;
    return this;
  }

   /**
   * Get noTimesEnquiry
   * @return noTimesEnquiry
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNoTimesEnquiry() {
    return noTimesEnquiry;
  }

  public void setNoTimesEnquiry(BigDecimal noTimesEnquiry) {
    this.noTimesEnquiry = noTimesEnquiry;
  }

  public NcbInfoMWF noTimesEnquiry6M(BigDecimal noTimesEnquiry6M) {
    this.noTimesEnquiry6M = noTimesEnquiry6M;
    return this;
  }

   /**
   * Get noTimesEnquiry6M
   * @return noTimesEnquiry6M
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNoTimesEnquiry6M() {
    return noTimesEnquiry6M;
  }

  public void setNoTimesEnquiry6M(BigDecimal noTimesEnquiry6M) {
    this.noTimesEnquiry6M = noTimesEnquiry6M;
  }

  public NcbInfoMWF noCCCard(BigDecimal noCCCard) {
    this.noCCCard = noCCCard;
    return this;
  }

   /**
   * Get noCCCard
   * @return noCCCard
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNoCCCard() {
    return noCCCard;
  }

  public void setNoCCCard(BigDecimal noCCCard) {
    this.noCCCard = noCCCard;
  }

  public NcbInfoMWF noCCOutStanding(BigDecimal noCCOutStanding) {
    this.noCCOutStanding = noCCOutStanding;
    return this;
  }

   /**
   * Get noCCOutStanding
   * @return noCCOutStanding
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNoCCOutStanding() {
    return noCCOutStanding;
  }

  public void setNoCCOutStanding(BigDecimal noCCOutStanding) {
    this.noCCOutStanding = noCCOutStanding;
  }

  public NcbInfoMWF personalLoanUnderBOTIssuer(BigDecimal personalLoanUnderBOTIssuer) {
    this.personalLoanUnderBOTIssuer = personalLoanUnderBOTIssuer;
    return this;
  }

   /**
   * Get personalLoanUnderBOTIssuer
   * @return personalLoanUnderBOTIssuer
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPersonalLoanUnderBOTIssuer() {
    return personalLoanUnderBOTIssuer;
  }

  public void setPersonalLoanUnderBOTIssuer(BigDecimal personalLoanUnderBOTIssuer) {
    this.personalLoanUnderBOTIssuer = personalLoanUnderBOTIssuer;
  }

  public NcbInfoMWF oldestNcbNew(DateTime oldestNcbNew) {
    this.oldestNcbNew = oldestNcbNew;
    return this;
  }

   /**
   * Get oldestNcbNew
   * @return oldestNcbNew
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getOldestNcbNew() {
    return oldestNcbNew;
  }

  public void setOldestNcbNew(DateTime oldestNcbNew) {
    this.oldestNcbNew = oldestNcbNew;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbInfoMWF ncbInfoMWF = (NcbInfoMWF) o;
    return Objects.equals(this.trackingCode, ncbInfoMWF.trackingCode) &&
        Objects.equals(this.personalId, ncbInfoMWF.personalId) &&
        Objects.equals(this.ficoScore, ncbInfoMWF.ficoScore) &&
        Objects.equals(this.ficoErrorCode, ncbInfoMWF.ficoErrorCode) &&
        Objects.equals(this.ficoErrorMsg, ncbInfoMWF.ficoErrorMsg) &&
        Objects.equals(this.ficoReason1Code, ncbInfoMWF.ficoReason1Code) &&
        Objects.equals(this.ficoReason1Desc, ncbInfoMWF.ficoReason1Desc) &&
        Objects.equals(this.ficoReason2Code, ncbInfoMWF.ficoReason2Code) &&
        Objects.equals(this.ficoReason2Desc, ncbInfoMWF.ficoReason2Desc) &&
        Objects.equals(this.ficoReason3Code, ncbInfoMWF.ficoReason3Code) &&
        Objects.equals(this.ficoReason3Desc, ncbInfoMWF.ficoReason3Desc) &&
        Objects.equals(this.ficoReason4Code, ncbInfoMWF.ficoReason4Code) &&
        Objects.equals(this.ficoReason4Desc, ncbInfoMWF.ficoReason4Desc) &&
        Objects.equals(this.consentRefNo, ncbInfoMWF.consentRefNo) &&
        Objects.equals(this.noTimesEnquiry, ncbInfoMWF.noTimesEnquiry) &&
        Objects.equals(this.noTimesEnquiry6M, ncbInfoMWF.noTimesEnquiry6M) &&
        Objects.equals(this.noCCCard, ncbInfoMWF.noCCCard) &&
        Objects.equals(this.noCCOutStanding, ncbInfoMWF.noCCOutStanding) &&
        Objects.equals(this.personalLoanUnderBOTIssuer, ncbInfoMWF.personalLoanUnderBOTIssuer) &&
        Objects.equals(this.oldestNcbNew, ncbInfoMWF.oldestNcbNew);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trackingCode, personalId, ficoScore, ficoErrorCode, ficoErrorMsg, ficoReason1Code, ficoReason1Desc, ficoReason2Code, ficoReason2Desc, ficoReason3Code, ficoReason3Desc, ficoReason4Code, ficoReason4Desc, consentRefNo, noTimesEnquiry, noTimesEnquiry6M, noCCCard, noCCOutStanding, personalLoanUnderBOTIssuer, oldestNcbNew);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbInfoMWF {\n");
    
    sb.append("    trackingCode: ").append(toIndentedString(trackingCode)).append("\n");
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    ficoScore: ").append(toIndentedString(ficoScore)).append("\n");
    sb.append("    ficoErrorCode: ").append(toIndentedString(ficoErrorCode)).append("\n");
    sb.append("    ficoErrorMsg: ").append(toIndentedString(ficoErrorMsg)).append("\n");
    sb.append("    ficoReason1Code: ").append(toIndentedString(ficoReason1Code)).append("\n");
    sb.append("    ficoReason1Desc: ").append(toIndentedString(ficoReason1Desc)).append("\n");
    sb.append("    ficoReason2Code: ").append(toIndentedString(ficoReason2Code)).append("\n");
    sb.append("    ficoReason2Desc: ").append(toIndentedString(ficoReason2Desc)).append("\n");
    sb.append("    ficoReason3Code: ").append(toIndentedString(ficoReason3Code)).append("\n");
    sb.append("    ficoReason3Desc: ").append(toIndentedString(ficoReason3Desc)).append("\n");
    sb.append("    ficoReason4Code: ").append(toIndentedString(ficoReason4Code)).append("\n");
    sb.append("    ficoReason4Desc: ").append(toIndentedString(ficoReason4Desc)).append("\n");
    sb.append("    consentRefNo: ").append(toIndentedString(consentRefNo)).append("\n");
    sb.append("    noTimesEnquiry: ").append(toIndentedString(noTimesEnquiry)).append("\n");
    sb.append("    noTimesEnquiry6M: ").append(toIndentedString(noTimesEnquiry6M)).append("\n");
    sb.append("    noCCCard: ").append(toIndentedString(noCCCard)).append("\n");
    sb.append("    noCCOutStanding: ").append(toIndentedString(noCCOutStanding)).append("\n");
    sb.append("    personalLoanUnderBOTIssuer: ").append(toIndentedString(personalLoanUnderBOTIssuer)).append("\n");
    sb.append("    oldestNcbNew: ").append(toIndentedString(oldestNcbNew)).append("\n");
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

