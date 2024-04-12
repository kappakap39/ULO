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
 * NcbAccountsTUEF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbAccountsTUEF   {
  @JsonProperty("segmentTag")
  private String segmentTag = null;

  @JsonProperty("collateral2")
  private String collateral2 = null;

  @JsonProperty("dateaccOpened")
  private DateTime dateaccOpened = null;

  @JsonProperty("creditlimit")
  private BigDecimal creditlimit = null;

  @JsonProperty("accountType")
  private String accountType = null;

  @JsonProperty("currencyCode")
  private String currencyCode = null;

  @JsonProperty("match")
  private String match = null;

  @JsonProperty("dateOfLastPmt")
  private DateTime dateOfLastPmt = null;

  @JsonProperty("dateaccClosed")
  private String dateaccClosed = null;

  @JsonProperty("asOfDate")
  private DateTime asOfDate = null;

  @JsonProperty("negativeFlag")
  private String negativeFlag = null;

  @JsonProperty("amtOwed")
  private BigDecimal amtOwed = null;

  @JsonProperty("amtPastDue")
  private BigDecimal amtPastDue = null;

  @JsonProperty("defaultDate")
  private DateTime defaultDate = null;

  @JsonProperty("installment")
  private String installment = null;

  @JsonProperty("installFreq")
  private BigDecimal installFreq = null;

  @JsonProperty("installamt")
  private BigDecimal installamt = null;

  @JsonProperty("installNoofpay")
  private BigDecimal installNoofpay = null;

  @JsonProperty("accountStatus")
  private String accountStatus = null;

  @JsonProperty("loanClass")
  private String loanClass = null;

  @JsonProperty("pmtHist1")
  private String pmtHist1 = null;

  @JsonProperty("pmtHist2")
  private String pmtHist2 = null;

  @JsonProperty("pmtHistStartdate")
  private DateTime pmtHistStartdate = null;

  @JsonProperty("pmtHistEnddate")
  private DateTime pmtHistEnddate = null;

  @JsonProperty("loanObjective")
  private String loanObjective = null;

  @JsonProperty("collateral1")
  private String collateral1 = null;

  @JsonProperty("accountNumber")
  private String accountNumber = null;

  @JsonProperty("collateral3")
  private String collateral3 = null;

  @JsonProperty("lastDebtRestDate")
  private DateTime lastDebtRestDate = null;

  @JsonProperty("percentPayment")
  private BigDecimal percentPayment = null;

  @JsonProperty("creditCardType")
  private String creditCardType = null;

  @JsonProperty("numberOfCoborr")
  private BigDecimal numberOfCoborr = null;

  @JsonProperty("unitMake")
  private String unitMake = null;

  @JsonProperty("unitModel")
  private String unitModel = null;

  @JsonProperty("creditTypeFlag")
  private String creditTypeFlag = null;

  @JsonProperty("ownerShipIndicator")
  private String ownerShipIndicator = null;

  @JsonProperty("memberCode")
  private String memberCode = null;

  @JsonProperty("memberShortName")
  private String memberShortName = null;

  public NcbAccountsTUEF segmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
    return this;
  }

   /**
   * Get segmentTag
   * @return segmentTag
  **/
  @ApiModelProperty(value = "")


  public String getSegmentTag() {
    return segmentTag;
  }

  public void setSegmentTag(String segmentTag) {
    this.segmentTag = segmentTag;
  }

  public NcbAccountsTUEF collateral2(String collateral2) {
    this.collateral2 = collateral2;
    return this;
  }

   /**
   * Get collateral2
   * @return collateral2
  **/
  @ApiModelProperty(value = "")


  public String getCollateral2() {
    return collateral2;
  }

  public void setCollateral2(String collateral2) {
    this.collateral2 = collateral2;
  }

  public NcbAccountsTUEF dateaccOpened(DateTime dateaccOpened) {
    this.dateaccOpened = dateaccOpened;
    return this;
  }

   /**
   * Get dateaccOpened
   * @return dateaccOpened
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateaccOpened() {
    return dateaccOpened;
  }

  public void setDateaccOpened(DateTime dateaccOpened) {
    this.dateaccOpened = dateaccOpened;
  }

  public NcbAccountsTUEF creditlimit(BigDecimal creditlimit) {
    this.creditlimit = creditlimit;
    return this;
  }

   /**
   * Get creditlimit
   * @return creditlimit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getCreditlimit() {
    return creditlimit;
  }

  public void setCreditlimit(BigDecimal creditlimit) {
    this.creditlimit = creditlimit;
  }

  public NcbAccountsTUEF accountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

   /**
   * Get accountType
   * @return accountType
  **/
  @ApiModelProperty(value = "")


  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public NcbAccountsTUEF currencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    return this;
  }

   /**
   * Get currencyCode
   * @return currencyCode
  **/
  @ApiModelProperty(value = "")


  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public NcbAccountsTUEF match(String match) {
    this.match = match;
    return this;
  }

   /**
   * Get match
   * @return match
  **/
  @ApiModelProperty(value = "")


  public String getMatch() {
    return match;
  }

  public void setMatch(String match) {
    this.match = match;
  }

  public NcbAccountsTUEF dateOfLastPmt(DateTime dateOfLastPmt) {
    this.dateOfLastPmt = dateOfLastPmt;
    return this;
  }

   /**
   * Get dateOfLastPmt
   * @return dateOfLastPmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateOfLastPmt() {
    return dateOfLastPmt;
  }

  public void setDateOfLastPmt(DateTime dateOfLastPmt) {
    this.dateOfLastPmt = dateOfLastPmt;
  }

  public NcbAccountsTUEF dateaccClosed(String dateaccClosed) {
    this.dateaccClosed = dateaccClosed;
    return this;
  }

   /**
   * Get dateaccClosed
   * @return dateaccClosed
  **/
  @ApiModelProperty(value = "")


  public String getDateaccClosed() {
    return dateaccClosed;
  }

  public void setDateaccClosed(String dateaccClosed) {
    this.dateaccClosed = dateaccClosed;
  }

  public NcbAccountsTUEF asOfDate(DateTime asOfDate) {
    this.asOfDate = asOfDate;
    return this;
  }

   /**
   * Get asOfDate
   * @return asOfDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getAsOfDate() {
    return asOfDate;
  }

  public void setAsOfDate(DateTime asOfDate) {
    this.asOfDate = asOfDate;
  }

  public NcbAccountsTUEF negativeFlag(String negativeFlag) {
    this.negativeFlag = negativeFlag;
    return this;
  }

   /**
   * Get negativeFlag
   * @return negativeFlag
  **/
  @ApiModelProperty(value = "")


  public String getNegativeFlag() {
    return negativeFlag;
  }

  public void setNegativeFlag(String negativeFlag) {
    this.negativeFlag = negativeFlag;
  }

  public NcbAccountsTUEF amtOwed(BigDecimal amtOwed) {
    this.amtOwed = amtOwed;
    return this;
  }

   /**
   * Get amtOwed
   * @return amtOwed
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAmtOwed() {
    return amtOwed;
  }

  public void setAmtOwed(BigDecimal amtOwed) {
    this.amtOwed = amtOwed;
  }

  public NcbAccountsTUEF amtPastDue(BigDecimal amtPastDue) {
    this.amtPastDue = amtPastDue;
    return this;
  }

   /**
   * Get amtPastDue
   * @return amtPastDue
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAmtPastDue() {
    return amtPastDue;
  }

  public void setAmtPastDue(BigDecimal amtPastDue) {
    this.amtPastDue = amtPastDue;
  }

  public NcbAccountsTUEF defaultDate(DateTime defaultDate) {
    this.defaultDate = defaultDate;
    return this;
  }

   /**
   * Get defaultDate
   * @return defaultDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDefaultDate() {
    return defaultDate;
  }

  public void setDefaultDate(DateTime defaultDate) {
    this.defaultDate = defaultDate;
  }

  public NcbAccountsTUEF installment(String installment) {
    this.installment = installment;
    return this;
  }

   /**
   * Get installment
   * @return installment
  **/
  @ApiModelProperty(value = "")


  public String getInstallment() {
    return installment;
  }

  public void setInstallment(String installment) {
    this.installment = installment;
  }

  public NcbAccountsTUEF installFreq(BigDecimal installFreq) {
    this.installFreq = installFreq;
    return this;
  }

   /**
   * Get installFreq
   * @return installFreq
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getInstallFreq() {
    return installFreq;
  }

  public void setInstallFreq(BigDecimal installFreq) {
    this.installFreq = installFreq;
  }

  public NcbAccountsTUEF installamt(BigDecimal installamt) {
    this.installamt = installamt;
    return this;
  }

   /**
   * Get installamt
   * @return installamt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getInstallamt() {
    return installamt;
  }

  public void setInstallamt(BigDecimal installamt) {
    this.installamt = installamt;
  }

  public NcbAccountsTUEF installNoofpay(BigDecimal installNoofpay) {
    this.installNoofpay = installNoofpay;
    return this;
  }

   /**
   * Get installNoofpay
   * @return installNoofpay
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getInstallNoofpay() {
    return installNoofpay;
  }

  public void setInstallNoofpay(BigDecimal installNoofpay) {
    this.installNoofpay = installNoofpay;
  }

  public NcbAccountsTUEF accountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

   /**
   * Get accountStatus
   * @return accountStatus
  **/
  @ApiModelProperty(value = "")


  public String getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
  }

  public NcbAccountsTUEF loanClass(String loanClass) {
    this.loanClass = loanClass;
    return this;
  }

   /**
   * Get loanClass
   * @return loanClass
  **/
  @ApiModelProperty(value = "")


  public String getLoanClass() {
    return loanClass;
  }

  public void setLoanClass(String loanClass) {
    this.loanClass = loanClass;
  }

  public NcbAccountsTUEF pmtHist1(String pmtHist1) {
    this.pmtHist1 = pmtHist1;
    return this;
  }

   /**
   * Get pmtHist1
   * @return pmtHist1
  **/
  @ApiModelProperty(value = "")


  public String getPmtHist1() {
    return pmtHist1;
  }

  public void setPmtHist1(String pmtHist1) {
    this.pmtHist1 = pmtHist1;
  }

  public NcbAccountsTUEF pmtHist2(String pmtHist2) {
    this.pmtHist2 = pmtHist2;
    return this;
  }

   /**
   * Get pmtHist2
   * @return pmtHist2
  **/
  @ApiModelProperty(value = "")


  public String getPmtHist2() {
    return pmtHist2;
  }

  public void setPmtHist2(String pmtHist2) {
    this.pmtHist2 = pmtHist2;
  }

  public NcbAccountsTUEF pmtHistStartdate(DateTime pmtHistStartdate) {
    this.pmtHistStartdate = pmtHistStartdate;
    return this;
  }

   /**
   * Get pmtHistStartdate
   * @return pmtHistStartdate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getPmtHistStartdate() {
    return pmtHistStartdate;
  }

  public void setPmtHistStartdate(DateTime pmtHistStartdate) {
    this.pmtHistStartdate = pmtHistStartdate;
  }

  public NcbAccountsTUEF pmtHistEnddate(DateTime pmtHistEnddate) {
    this.pmtHistEnddate = pmtHistEnddate;
    return this;
  }

   /**
   * Get pmtHistEnddate
   * @return pmtHistEnddate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getPmtHistEnddate() {
    return pmtHistEnddate;
  }

  public void setPmtHistEnddate(DateTime pmtHistEnddate) {
    this.pmtHistEnddate = pmtHistEnddate;
  }

  public NcbAccountsTUEF loanObjective(String loanObjective) {
    this.loanObjective = loanObjective;
    return this;
  }

   /**
   * Get loanObjective
   * @return loanObjective
  **/
  @ApiModelProperty(value = "")


  public String getLoanObjective() {
    return loanObjective;
  }

  public void setLoanObjective(String loanObjective) {
    this.loanObjective = loanObjective;
  }

  public NcbAccountsTUEF collateral1(String collateral1) {
    this.collateral1 = collateral1;
    return this;
  }

   /**
   * Get collateral1
   * @return collateral1
  **/
  @ApiModelProperty(value = "")


  public String getCollateral1() {
    return collateral1;
  }

  public void setCollateral1(String collateral1) {
    this.collateral1 = collateral1;
  }

  public NcbAccountsTUEF accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

   /**
   * Get accountNumber
   * @return accountNumber
  **/
  @ApiModelProperty(value = "")


  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public NcbAccountsTUEF collateral3(String collateral3) {
    this.collateral3 = collateral3;
    return this;
  }

   /**
   * Get collateral3
   * @return collateral3
  **/
  @ApiModelProperty(value = "")


  public String getCollateral3() {
    return collateral3;
  }

  public void setCollateral3(String collateral3) {
    this.collateral3 = collateral3;
  }

  public NcbAccountsTUEF lastDebtRestDate(DateTime lastDebtRestDate) {
    this.lastDebtRestDate = lastDebtRestDate;
    return this;
  }

   /**
   * Get lastDebtRestDate
   * @return lastDebtRestDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getLastDebtRestDate() {
    return lastDebtRestDate;
  }

  public void setLastDebtRestDate(DateTime lastDebtRestDate) {
    this.lastDebtRestDate = lastDebtRestDate;
  }

  public NcbAccountsTUEF percentPayment(BigDecimal percentPayment) {
    this.percentPayment = percentPayment;
    return this;
  }

   /**
   * Get percentPayment
   * @return percentPayment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPercentPayment() {
    return percentPayment;
  }

  public void setPercentPayment(BigDecimal percentPayment) {
    this.percentPayment = percentPayment;
  }

  public NcbAccountsTUEF creditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
    return this;
  }

   /**
   * Get creditCardType
   * @return creditCardType
  **/
  @ApiModelProperty(value = "")


  public String getCreditCardType() {
    return creditCardType;
  }

  public void setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
  }

  public NcbAccountsTUEF numberOfCoborr(BigDecimal numberOfCoborr) {
    this.numberOfCoborr = numberOfCoborr;
    return this;
  }

   /**
   * Get numberOfCoborr
   * @return numberOfCoborr
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumberOfCoborr() {
    return numberOfCoborr;
  }

  public void setNumberOfCoborr(BigDecimal numberOfCoborr) {
    this.numberOfCoborr = numberOfCoborr;
  }

  public NcbAccountsTUEF unitMake(String unitMake) {
    this.unitMake = unitMake;
    return this;
  }

   /**
   * Get unitMake
   * @return unitMake
  **/
  @ApiModelProperty(value = "")


  public String getUnitMake() {
    return unitMake;
  }

  public void setUnitMake(String unitMake) {
    this.unitMake = unitMake;
  }

  public NcbAccountsTUEF unitModel(String unitModel) {
    this.unitModel = unitModel;
    return this;
  }

   /**
   * Get unitModel
   * @return unitModel
  **/
  @ApiModelProperty(value = "")


  public String getUnitModel() {
    return unitModel;
  }

  public void setUnitModel(String unitModel) {
    this.unitModel = unitModel;
  }

  public NcbAccountsTUEF creditTypeFlag(String creditTypeFlag) {
    this.creditTypeFlag = creditTypeFlag;
    return this;
  }

   /**
   * Get creditTypeFlag
   * @return creditTypeFlag
  **/
  @ApiModelProperty(value = "")


  public String getCreditTypeFlag() {
    return creditTypeFlag;
  }

  public void setCreditTypeFlag(String creditTypeFlag) {
    this.creditTypeFlag = creditTypeFlag;
  }

  public NcbAccountsTUEF ownerShipIndicator(String ownerShipIndicator) {
    this.ownerShipIndicator = ownerShipIndicator;
    return this;
  }

   /**
   * Get ownerShipIndicator
   * @return ownerShipIndicator
  **/
  @ApiModelProperty(value = "")


  public String getOwnerShipIndicator() {
    return ownerShipIndicator;
  }

  public void setOwnerShipIndicator(String ownerShipIndicator) {
    this.ownerShipIndicator = ownerShipIndicator;
  }

  public NcbAccountsTUEF memberCode(String memberCode) {
    this.memberCode = memberCode;
    return this;
  }

   /**
   * Get memberCode
   * @return memberCode
  **/
  @ApiModelProperty(value = "")


  public String getMemberCode() {
    return memberCode;
  }

  public void setMemberCode(String memberCode) {
    this.memberCode = memberCode;
  }

  public NcbAccountsTUEF memberShortName(String memberShortName) {
    this.memberShortName = memberShortName;
    return this;
  }

   /**
   * Get memberShortName
   * @return memberShortName
  **/
  @ApiModelProperty(value = "")


  public String getMemberShortName() {
    return memberShortName;
  }

  public void setMemberShortName(String memberShortName) {
    this.memberShortName = memberShortName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbAccountsTUEF ncbAccountsTUEF = (NcbAccountsTUEF) o;
    return Objects.equals(this.segmentTag, ncbAccountsTUEF.segmentTag) &&
        Objects.equals(this.collateral2, ncbAccountsTUEF.collateral2) &&
        Objects.equals(this.dateaccOpened, ncbAccountsTUEF.dateaccOpened) &&
        Objects.equals(this.creditlimit, ncbAccountsTUEF.creditlimit) &&
        Objects.equals(this.accountType, ncbAccountsTUEF.accountType) &&
        Objects.equals(this.currencyCode, ncbAccountsTUEF.currencyCode) &&
        Objects.equals(this.match, ncbAccountsTUEF.match) &&
        Objects.equals(this.dateOfLastPmt, ncbAccountsTUEF.dateOfLastPmt) &&
        Objects.equals(this.dateaccClosed, ncbAccountsTUEF.dateaccClosed) &&
        Objects.equals(this.asOfDate, ncbAccountsTUEF.asOfDate) &&
        Objects.equals(this.negativeFlag, ncbAccountsTUEF.negativeFlag) &&
        Objects.equals(this.amtOwed, ncbAccountsTUEF.amtOwed) &&
        Objects.equals(this.amtPastDue, ncbAccountsTUEF.amtPastDue) &&
        Objects.equals(this.defaultDate, ncbAccountsTUEF.defaultDate) &&
        Objects.equals(this.installment, ncbAccountsTUEF.installment) &&
        Objects.equals(this.installFreq, ncbAccountsTUEF.installFreq) &&
        Objects.equals(this.installamt, ncbAccountsTUEF.installamt) &&
        Objects.equals(this.installNoofpay, ncbAccountsTUEF.installNoofpay) &&
        Objects.equals(this.accountStatus, ncbAccountsTUEF.accountStatus) &&
        Objects.equals(this.loanClass, ncbAccountsTUEF.loanClass) &&
        Objects.equals(this.pmtHist1, ncbAccountsTUEF.pmtHist1) &&
        Objects.equals(this.pmtHist2, ncbAccountsTUEF.pmtHist2) &&
        Objects.equals(this.pmtHistStartdate, ncbAccountsTUEF.pmtHistStartdate) &&
        Objects.equals(this.pmtHistEnddate, ncbAccountsTUEF.pmtHistEnddate) &&
        Objects.equals(this.loanObjective, ncbAccountsTUEF.loanObjective) &&
        Objects.equals(this.collateral1, ncbAccountsTUEF.collateral1) &&
        Objects.equals(this.accountNumber, ncbAccountsTUEF.accountNumber) &&
        Objects.equals(this.collateral3, ncbAccountsTUEF.collateral3) &&
        Objects.equals(this.lastDebtRestDate, ncbAccountsTUEF.lastDebtRestDate) &&
        Objects.equals(this.percentPayment, ncbAccountsTUEF.percentPayment) &&
        Objects.equals(this.creditCardType, ncbAccountsTUEF.creditCardType) &&
        Objects.equals(this.numberOfCoborr, ncbAccountsTUEF.numberOfCoborr) &&
        Objects.equals(this.unitMake, ncbAccountsTUEF.unitMake) &&
        Objects.equals(this.unitModel, ncbAccountsTUEF.unitModel) &&
        Objects.equals(this.creditTypeFlag, ncbAccountsTUEF.creditTypeFlag) &&
        Objects.equals(this.ownerShipIndicator, ncbAccountsTUEF.ownerShipIndicator) &&
        Objects.equals(this.memberCode, ncbAccountsTUEF.memberCode) &&
        Objects.equals(this.memberShortName, ncbAccountsTUEF.memberShortName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentTag, collateral2, dateaccOpened, creditlimit, accountType, currencyCode, match, dateOfLastPmt, dateaccClosed, asOfDate, negativeFlag, amtOwed, amtPastDue, defaultDate, installment, installFreq, installamt, installNoofpay, accountStatus, loanClass, pmtHist1, pmtHist2, pmtHistStartdate, pmtHistEnddate, loanObjective, collateral1, accountNumber, collateral3, lastDebtRestDate, percentPayment, creditCardType, numberOfCoborr, unitMake, unitModel, creditTypeFlag, ownerShipIndicator, memberCode, memberShortName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbAccountsTUEF {\n");
    
    sb.append("    segmentTag: ").append(toIndentedString(segmentTag)).append("\n");
    sb.append("    collateral2: ").append(toIndentedString(collateral2)).append("\n");
    sb.append("    dateaccOpened: ").append(toIndentedString(dateaccOpened)).append("\n");
    sb.append("    creditlimit: ").append(toIndentedString(creditlimit)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    currencyCode: ").append(toIndentedString(currencyCode)).append("\n");
    sb.append("    match: ").append(toIndentedString(match)).append("\n");
    sb.append("    dateOfLastPmt: ").append(toIndentedString(dateOfLastPmt)).append("\n");
    sb.append("    dateaccClosed: ").append(toIndentedString(dateaccClosed)).append("\n");
    sb.append("    asOfDate: ").append(toIndentedString(asOfDate)).append("\n");
    sb.append("    negativeFlag: ").append(toIndentedString(negativeFlag)).append("\n");
    sb.append("    amtOwed: ").append(toIndentedString(amtOwed)).append("\n");
    sb.append("    amtPastDue: ").append(toIndentedString(amtPastDue)).append("\n");
    sb.append("    defaultDate: ").append(toIndentedString(defaultDate)).append("\n");
    sb.append("    installment: ").append(toIndentedString(installment)).append("\n");
    sb.append("    installFreq: ").append(toIndentedString(installFreq)).append("\n");
    sb.append("    installamt: ").append(toIndentedString(installamt)).append("\n");
    sb.append("    installNoofpay: ").append(toIndentedString(installNoofpay)).append("\n");
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
    sb.append("    loanClass: ").append(toIndentedString(loanClass)).append("\n");
    sb.append("    pmtHist1: ").append(toIndentedString(pmtHist1)).append("\n");
    sb.append("    pmtHist2: ").append(toIndentedString(pmtHist2)).append("\n");
    sb.append("    pmtHistStartdate: ").append(toIndentedString(pmtHistStartdate)).append("\n");
    sb.append("    pmtHistEnddate: ").append(toIndentedString(pmtHistEnddate)).append("\n");
    sb.append("    loanObjective: ").append(toIndentedString(loanObjective)).append("\n");
    sb.append("    collateral1: ").append(toIndentedString(collateral1)).append("\n");
    sb.append("    accountNumber: ").append(toIndentedString(accountNumber)).append("\n");
    sb.append("    collateral3: ").append(toIndentedString(collateral3)).append("\n");
    sb.append("    lastDebtRestDate: ").append(toIndentedString(lastDebtRestDate)).append("\n");
    sb.append("    percentPayment: ").append(toIndentedString(percentPayment)).append("\n");
    sb.append("    creditCardType: ").append(toIndentedString(creditCardType)).append("\n");
    sb.append("    numberOfCoborr: ").append(toIndentedString(numberOfCoborr)).append("\n");
    sb.append("    unitMake: ").append(toIndentedString(unitMake)).append("\n");
    sb.append("    unitModel: ").append(toIndentedString(unitModel)).append("\n");
    sb.append("    creditTypeFlag: ").append(toIndentedString(creditTypeFlag)).append("\n");
    sb.append("    ownerShipIndicator: ").append(toIndentedString(ownerShipIndicator)).append("\n");
    sb.append("    memberCode: ").append(toIndentedString(memberCode)).append("\n");
    sb.append("    memberShortName: ").append(toIndentedString(memberShortName)).append("\n");
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

