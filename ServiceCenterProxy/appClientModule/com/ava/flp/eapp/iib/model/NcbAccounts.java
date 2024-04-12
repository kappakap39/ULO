package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.NcbRuleResults;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NcbAccounts
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class NcbAccounts   {
  @JsonProperty("segmentValue")
  private String segmentValue = null;

  @JsonProperty("memberCode")
  private String memberCode = null;

  @JsonProperty("memberShortName")
  private String memberShortName = null;

  @JsonProperty("accountNumber")
  private String accountNumber = null;

  @JsonProperty("accountType")
  private String accountType = null;

  @JsonProperty("ownershipIndicator")
  private String ownershipIndicator = null;

  @JsonProperty("currencyCode")
  private String currencyCode = null;

  @JsonProperty("dateAccountOpened")
  private DateTime dateAccountOpened = null;

  @JsonProperty("dateOfLastPayment")
  private DateTime dateOfLastPayment = null;

  @JsonProperty("dateAccountClose")
  private DateTime dateAccountClose = null;

  @JsonProperty("asOfDate")
  private DateTime asOfDate = null;

  @JsonProperty("creditLimit")
  private String creditLimit = null;

  @JsonProperty("amountOwed")
  private String amountOwed = null;

  @JsonProperty("amountPastDue")
  private String amountPastDue = null;

  @JsonProperty("defaultDate")
  private DateTime defaultDate = null;

  @JsonProperty("instalmentFrequency")
  private String instalmentFrequency = null;

  @JsonProperty("installmentAmount")
  private String installmentAmount = null;

  @JsonProperty("installmentNumberOfPayments")
  private String installmentNumberOfPayments = null;

  @JsonProperty("accountStatus")
  private String accountStatus = null;

  @JsonProperty("loanClass")
  private String loanClass = null;

  @JsonProperty("paymentHistory1")
  private String paymentHistory1 = null;

  @JsonProperty("paymentHistory2")
  private String paymentHistory2 = null;

  @JsonProperty("paymentHistoryStartDate")
  private String paymentHistoryStartDate = null;

  @JsonProperty("paymentHistoryEndDate")
  private String paymentHistoryEndDate = null;

  @JsonProperty("loanObjective")
  private String loanObjective = null;

  @JsonProperty("collateral1")
  private String collateral1 = null;

  @JsonProperty("collateral2")
  private String collateral2 = null;

  @JsonProperty("collateral3")
  private String collateral3 = null;

  @JsonProperty("dateOfLastDebtRestructure")
  private DateTime dateOfLastDebtRestructure = null;

  @JsonProperty("percentPayment")
  private String percentPayment = null;

  @JsonProperty("typeOfCreditCard")
  private String typeOfCreditCard = null;

  @JsonProperty("numberOfCoborrowers")
  private String numberOfCoborrowers = null;

  @JsonProperty("unitMake")
  private String unitMake = null;

  @JsonProperty("unitModel")
  private String unitModel = null;

  @JsonProperty("creditTypeFlag")
  private String creditTypeFlag = null;

  @JsonProperty("creditCardType")
  private String creditCardType = null;

  @JsonProperty("ncbRuleResults")
  private List<NcbRuleResults> ncbRuleResults = null;

  public NcbAccounts segmentValue(String segmentValue) {
    this.segmentValue = segmentValue;
    return this;
  }

   /**
   * Get segmentValue
   * @return segmentValue
  **/
  @ApiModelProperty(value = "")


  public String getSegmentValue() {
    return segmentValue;
  }

  public void setSegmentValue(String segmentValue) {
    this.segmentValue = segmentValue;
  }

  public NcbAccounts memberCode(String memberCode) {
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

  public NcbAccounts memberShortName(String memberShortName) {
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

  public NcbAccounts accountNumber(String accountNumber) {
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

  public NcbAccounts accountType(String accountType) {
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

  public NcbAccounts ownershipIndicator(String ownershipIndicator) {
    this.ownershipIndicator = ownershipIndicator;
    return this;
  }

   /**
   * Get ownershipIndicator
   * @return ownershipIndicator
  **/
  @ApiModelProperty(value = "")


  public String getOwnershipIndicator() {
    return ownershipIndicator;
  }

  public void setOwnershipIndicator(String ownershipIndicator) {
    this.ownershipIndicator = ownershipIndicator;
  }

  public NcbAccounts currencyCode(String currencyCode) {
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

  public NcbAccounts dateAccountOpened(DateTime dateAccountOpened) {
    this.dateAccountOpened = dateAccountOpened;
    return this;
  }

   /**
   * Get dateAccountOpened
   * @return dateAccountOpened
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateAccountOpened() {
    return dateAccountOpened;
  }

  public void setDateAccountOpened(DateTime dateAccountOpened) {
    this.dateAccountOpened = dateAccountOpened;
  }

  public NcbAccounts dateOfLastPayment(DateTime dateOfLastPayment) {
    this.dateOfLastPayment = dateOfLastPayment;
    return this;
  }

   /**
   * Get dateOfLastPayment
   * @return dateOfLastPayment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateOfLastPayment() {
    return dateOfLastPayment;
  }

  public void setDateOfLastPayment(DateTime dateOfLastPayment) {
    this.dateOfLastPayment = dateOfLastPayment;
  }

  public NcbAccounts dateAccountClose(DateTime dateAccountClose) {
    this.dateAccountClose = dateAccountClose;
    return this;
  }

   /**
   * Get dateAccountClose
   * @return dateAccountClose
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateAccountClose() {
    return dateAccountClose;
  }

  public void setDateAccountClose(DateTime dateAccountClose) {
    this.dateAccountClose = dateAccountClose;
  }

  public NcbAccounts asOfDate(DateTime asOfDate) {
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

  public NcbAccounts creditLimit(String creditLimit) {
    this.creditLimit = creditLimit;
    return this;
  }

   /**
   * Get creditLimit
   * @return creditLimit
  **/
  @ApiModelProperty(value = "")


  public String getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(String creditLimit) {
    this.creditLimit = creditLimit;
  }

  public NcbAccounts amountOwed(String amountOwed) {
    this.amountOwed = amountOwed;
    return this;
  }

   /**
   * Get amountOwed
   * @return amountOwed
  **/
  @ApiModelProperty(value = "")


  public String getAmountOwed() {
    return amountOwed;
  }

  public void setAmountOwed(String amountOwed) {
    this.amountOwed = amountOwed;
  }

  public NcbAccounts amountPastDue(String amountPastDue) {
    this.amountPastDue = amountPastDue;
    return this;
  }

   /**
   * Get amountPastDue
   * @return amountPastDue
  **/
  @ApiModelProperty(value = "")


  public String getAmountPastDue() {
    return amountPastDue;
  }

  public void setAmountPastDue(String amountPastDue) {
    this.amountPastDue = amountPastDue;
  }

  public NcbAccounts defaultDate(DateTime defaultDate) {
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

  public NcbAccounts instalmentFrequency(String instalmentFrequency) {
    this.instalmentFrequency = instalmentFrequency;
    return this;
  }

   /**
   * Get instalmentFrequency
   * @return instalmentFrequency
  **/
  @ApiModelProperty(value = "")


  public String getInstalmentFrequency() {
    return instalmentFrequency;
  }

  public void setInstalmentFrequency(String instalmentFrequency) {
    this.instalmentFrequency = instalmentFrequency;
  }

  public NcbAccounts installmentAmount(String installmentAmount) {
    this.installmentAmount = installmentAmount;
    return this;
  }

   /**
   * Get installmentAmount
   * @return installmentAmount
  **/
  @ApiModelProperty(value = "")


  public String getInstallmentAmount() {
    return installmentAmount;
  }

  public void setInstallmentAmount(String installmentAmount) {
    this.installmentAmount = installmentAmount;
  }

  public NcbAccounts installmentNumberOfPayments(String installmentNumberOfPayments) {
    this.installmentNumberOfPayments = installmentNumberOfPayments;
    return this;
  }

   /**
   * Get installmentNumberOfPayments
   * @return installmentNumberOfPayments
  **/
  @ApiModelProperty(value = "")


  public String getInstallmentNumberOfPayments() {
    return installmentNumberOfPayments;
  }

  public void setInstallmentNumberOfPayments(String installmentNumberOfPayments) {
    this.installmentNumberOfPayments = installmentNumberOfPayments;
  }

  public NcbAccounts accountStatus(String accountStatus) {
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

  public NcbAccounts loanClass(String loanClass) {
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

  public NcbAccounts paymentHistory1(String paymentHistory1) {
    this.paymentHistory1 = paymentHistory1;
    return this;
  }

   /**
   * Get paymentHistory1
   * @return paymentHistory1
  **/
  @ApiModelProperty(value = "")


  public String getPaymentHistory1() {
    return paymentHistory1;
  }

  public void setPaymentHistory1(String paymentHistory1) {
    this.paymentHistory1 = paymentHistory1;
  }

  public NcbAccounts paymentHistory2(String paymentHistory2) {
    this.paymentHistory2 = paymentHistory2;
    return this;
  }

   /**
   * Get paymentHistory2
   * @return paymentHistory2
  **/
  @ApiModelProperty(value = "")


  public String getPaymentHistory2() {
    return paymentHistory2;
  }

  public void setPaymentHistory2(String paymentHistory2) {
    this.paymentHistory2 = paymentHistory2;
  }

  public NcbAccounts paymentHistoryStartDate(String paymentHistoryStartDate) {
    this.paymentHistoryStartDate = paymentHistoryStartDate;
    return this;
  }

   /**
   * Get paymentHistoryStartDate
   * @return paymentHistoryStartDate
  **/
  @ApiModelProperty(value = "")


  public String getPaymentHistoryStartDate() {
    return paymentHistoryStartDate;
  }

  public void setPaymentHistoryStartDate(String paymentHistoryStartDate) {
    this.paymentHistoryStartDate = paymentHistoryStartDate;
  }

  public NcbAccounts paymentHistoryEndDate(String paymentHistoryEndDate) {
    this.paymentHistoryEndDate = paymentHistoryEndDate;
    return this;
  }

   /**
   * Get paymentHistoryEndDate
   * @return paymentHistoryEndDate
  **/
  @ApiModelProperty(value = "")


  public String getPaymentHistoryEndDate() {
    return paymentHistoryEndDate;
  }

  public void setPaymentHistoryEndDate(String paymentHistoryEndDate) {
    this.paymentHistoryEndDate = paymentHistoryEndDate;
  }

  public NcbAccounts loanObjective(String loanObjective) {
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

  public NcbAccounts collateral1(String collateral1) {
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

  public NcbAccounts collateral2(String collateral2) {
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

  public NcbAccounts collateral3(String collateral3) {
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

  public NcbAccounts dateOfLastDebtRestructure(DateTime dateOfLastDebtRestructure) {
    this.dateOfLastDebtRestructure = dateOfLastDebtRestructure;
    return this;
  }

   /**
   * Get dateOfLastDebtRestructure
   * @return dateOfLastDebtRestructure
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDateOfLastDebtRestructure() {
    return dateOfLastDebtRestructure;
  }

  public void setDateOfLastDebtRestructure(DateTime dateOfLastDebtRestructure) {
    this.dateOfLastDebtRestructure = dateOfLastDebtRestructure;
  }

  public NcbAccounts percentPayment(String percentPayment) {
    this.percentPayment = percentPayment;
    return this;
  }

   /**
   * Get percentPayment
   * @return percentPayment
  **/
  @ApiModelProperty(value = "")


  public String getPercentPayment() {
    return percentPayment;
  }

  public void setPercentPayment(String percentPayment) {
    this.percentPayment = percentPayment;
  }

  public NcbAccounts typeOfCreditCard(String typeOfCreditCard) {
    this.typeOfCreditCard = typeOfCreditCard;
    return this;
  }

   /**
   * Get typeOfCreditCard
   * @return typeOfCreditCard
  **/
  @ApiModelProperty(value = "")


  public String getTypeOfCreditCard() {
    return typeOfCreditCard;
  }

  public void setTypeOfCreditCard(String typeOfCreditCard) {
    this.typeOfCreditCard = typeOfCreditCard;
  }

  public NcbAccounts numberOfCoborrowers(String numberOfCoborrowers) {
    this.numberOfCoborrowers = numberOfCoborrowers;
    return this;
  }

   /**
   * Get numberOfCoborrowers
   * @return numberOfCoborrowers
  **/
  @ApiModelProperty(value = "")


  public String getNumberOfCoborrowers() {
    return numberOfCoborrowers;
  }

  public void setNumberOfCoborrowers(String numberOfCoborrowers) {
    this.numberOfCoborrowers = numberOfCoborrowers;
  }

  public NcbAccounts unitMake(String unitMake) {
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

  public NcbAccounts unitModel(String unitModel) {
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

  public NcbAccounts creditTypeFlag(String creditTypeFlag) {
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

  public NcbAccounts creditCardType(String creditCardType) {
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

  public NcbAccounts ncbRuleResults(List<NcbRuleResults> ncbRuleResults) {
    this.ncbRuleResults = ncbRuleResults;
    return this;
  }

  public NcbAccounts addNcbRuleResultsItem(NcbRuleResults ncbRuleResultsItem) {
    if (this.ncbRuleResults == null) {
      this.ncbRuleResults = new ArrayList<NcbRuleResults>();
    }
    this.ncbRuleResults.add(ncbRuleResultsItem);
    return this;
  }

   /**
   * Get ncbRuleResults
   * @return ncbRuleResults
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<NcbRuleResults> getNcbRuleResults() {
    return ncbRuleResults;
  }

  public void setNcbRuleResults(List<NcbRuleResults> ncbRuleResults) {
    this.ncbRuleResults = ncbRuleResults;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NcbAccounts ncbAccounts = (NcbAccounts) o;
    return Objects.equals(this.segmentValue, ncbAccounts.segmentValue) &&
        Objects.equals(this.memberCode, ncbAccounts.memberCode) &&
        Objects.equals(this.memberShortName, ncbAccounts.memberShortName) &&
        Objects.equals(this.accountNumber, ncbAccounts.accountNumber) &&
        Objects.equals(this.accountType, ncbAccounts.accountType) &&
        Objects.equals(this.ownershipIndicator, ncbAccounts.ownershipIndicator) &&
        Objects.equals(this.currencyCode, ncbAccounts.currencyCode) &&
        Objects.equals(this.dateAccountOpened, ncbAccounts.dateAccountOpened) &&
        Objects.equals(this.dateOfLastPayment, ncbAccounts.dateOfLastPayment) &&
        Objects.equals(this.dateAccountClose, ncbAccounts.dateAccountClose) &&
        Objects.equals(this.asOfDate, ncbAccounts.asOfDate) &&
        Objects.equals(this.creditLimit, ncbAccounts.creditLimit) &&
        Objects.equals(this.amountOwed, ncbAccounts.amountOwed) &&
        Objects.equals(this.amountPastDue, ncbAccounts.amountPastDue) &&
        Objects.equals(this.defaultDate, ncbAccounts.defaultDate) &&
        Objects.equals(this.instalmentFrequency, ncbAccounts.instalmentFrequency) &&
        Objects.equals(this.installmentAmount, ncbAccounts.installmentAmount) &&
        Objects.equals(this.installmentNumberOfPayments, ncbAccounts.installmentNumberOfPayments) &&
        Objects.equals(this.accountStatus, ncbAccounts.accountStatus) &&
        Objects.equals(this.loanClass, ncbAccounts.loanClass) &&
        Objects.equals(this.paymentHistory1, ncbAccounts.paymentHistory1) &&
        Objects.equals(this.paymentHistory2, ncbAccounts.paymentHistory2) &&
        Objects.equals(this.paymentHistoryStartDate, ncbAccounts.paymentHistoryStartDate) &&
        Objects.equals(this.paymentHistoryEndDate, ncbAccounts.paymentHistoryEndDate) &&
        Objects.equals(this.loanObjective, ncbAccounts.loanObjective) &&
        Objects.equals(this.collateral1, ncbAccounts.collateral1) &&
        Objects.equals(this.collateral2, ncbAccounts.collateral2) &&
        Objects.equals(this.collateral3, ncbAccounts.collateral3) &&
        Objects.equals(this.dateOfLastDebtRestructure, ncbAccounts.dateOfLastDebtRestructure) &&
        Objects.equals(this.percentPayment, ncbAccounts.percentPayment) &&
        Objects.equals(this.typeOfCreditCard, ncbAccounts.typeOfCreditCard) &&
        Objects.equals(this.numberOfCoborrowers, ncbAccounts.numberOfCoborrowers) &&
        Objects.equals(this.unitMake, ncbAccounts.unitMake) &&
        Objects.equals(this.unitModel, ncbAccounts.unitModel) &&
        Objects.equals(this.creditTypeFlag, ncbAccounts.creditTypeFlag) &&
        Objects.equals(this.creditCardType, ncbAccounts.creditCardType) &&
        Objects.equals(this.ncbRuleResults, ncbAccounts.ncbRuleResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentValue, memberCode, memberShortName, accountNumber, accountType, ownershipIndicator, currencyCode, dateAccountOpened, dateOfLastPayment, dateAccountClose, asOfDate, creditLimit, amountOwed, amountPastDue, defaultDate, instalmentFrequency, installmentAmount, installmentNumberOfPayments, accountStatus, loanClass, paymentHistory1, paymentHistory2, paymentHistoryStartDate, paymentHistoryEndDate, loanObjective, collateral1, collateral2, collateral3, dateOfLastDebtRestructure, percentPayment, typeOfCreditCard, numberOfCoborrowers, unitMake, unitModel, creditTypeFlag, creditCardType, ncbRuleResults);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NcbAccounts {\n");
    
    sb.append("    segmentValue: ").append(toIndentedString(segmentValue)).append("\n");
    sb.append("    memberCode: ").append(toIndentedString(memberCode)).append("\n");
    sb.append("    memberShortName: ").append(toIndentedString(memberShortName)).append("\n");
    sb.append("    accountNumber: ").append(toIndentedString(accountNumber)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    ownershipIndicator: ").append(toIndentedString(ownershipIndicator)).append("\n");
    sb.append("    currencyCode: ").append(toIndentedString(currencyCode)).append("\n");
    sb.append("    dateAccountOpened: ").append(toIndentedString(dateAccountOpened)).append("\n");
    sb.append("    dateOfLastPayment: ").append(toIndentedString(dateOfLastPayment)).append("\n");
    sb.append("    dateAccountClose: ").append(toIndentedString(dateAccountClose)).append("\n");
    sb.append("    asOfDate: ").append(toIndentedString(asOfDate)).append("\n");
    sb.append("    creditLimit: ").append(toIndentedString(creditLimit)).append("\n");
    sb.append("    amountOwed: ").append(toIndentedString(amountOwed)).append("\n");
    sb.append("    amountPastDue: ").append(toIndentedString(amountPastDue)).append("\n");
    sb.append("    defaultDate: ").append(toIndentedString(defaultDate)).append("\n");
    sb.append("    instalmentFrequency: ").append(toIndentedString(instalmentFrequency)).append("\n");
    sb.append("    installmentAmount: ").append(toIndentedString(installmentAmount)).append("\n");
    sb.append("    installmentNumberOfPayments: ").append(toIndentedString(installmentNumberOfPayments)).append("\n");
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
    sb.append("    loanClass: ").append(toIndentedString(loanClass)).append("\n");
    sb.append("    paymentHistory1: ").append(toIndentedString(paymentHistory1)).append("\n");
    sb.append("    paymentHistory2: ").append(toIndentedString(paymentHistory2)).append("\n");
    sb.append("    paymentHistoryStartDate: ").append(toIndentedString(paymentHistoryStartDate)).append("\n");
    sb.append("    paymentHistoryEndDate: ").append(toIndentedString(paymentHistoryEndDate)).append("\n");
    sb.append("    loanObjective: ").append(toIndentedString(loanObjective)).append("\n");
    sb.append("    collateral1: ").append(toIndentedString(collateral1)).append("\n");
    sb.append("    collateral2: ").append(toIndentedString(collateral2)).append("\n");
    sb.append("    collateral3: ").append(toIndentedString(collateral3)).append("\n");
    sb.append("    dateOfLastDebtRestructure: ").append(toIndentedString(dateOfLastDebtRestructure)).append("\n");
    sb.append("    percentPayment: ").append(toIndentedString(percentPayment)).append("\n");
    sb.append("    typeOfCreditCard: ").append(toIndentedString(typeOfCreditCard)).append("\n");
    sb.append("    numberOfCoborrowers: ").append(toIndentedString(numberOfCoborrowers)).append("\n");
    sb.append("    unitMake: ").append(toIndentedString(unitMake)).append("\n");
    sb.append("    unitModel: ").append(toIndentedString(unitModel)).append("\n");
    sb.append("    creditTypeFlag: ").append(toIndentedString(creditTypeFlag)).append("\n");
    sb.append("    creditCardType: ").append(toIndentedString(creditCardType)).append("\n");
    sb.append("    ncbRuleResults: ").append(toIndentedString(ncbRuleResults)).append("\n");
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

