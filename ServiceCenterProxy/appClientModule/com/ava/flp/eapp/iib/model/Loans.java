package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.AdditionalServices;
import com.ava.flp.eapp.iib.model.Capport;
import com.ava.flp.eapp.iib.model.Card;
import com.ava.flp.eapp.iib.model.CashTransfers;
import com.ava.flp.eapp.iib.model.LoanFees;
import com.ava.flp.eapp.iib.model.LoanPricings;
import com.ava.flp.eapp.iib.model.LoanTiers;
import com.ava.flp.eapp.iib.model.PaymentMethods;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Loans
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Loans   {
  @JsonProperty("loanId")
  private String loanId = null;

  @JsonProperty("normalCreditLimit")
  private BigDecimal normalCreditLimit = null;

  @JsonProperty("maxCreditLimit")
  private BigDecimal maxCreditLimit = null;

  @JsonProperty("maxCreditLimitBot")
  private BigDecimal maxCreditLimitBot = null;

  @JsonProperty("minCreditLimit")
  private BigDecimal minCreditLimit = null;

  @JsonProperty("recommendLoanAmt")
  private BigDecimal recommendLoanAmt = null;

  @JsonProperty("recommendTerm")
  private BigDecimal recommendTerm = null;

  @JsonProperty("requestLoanAmt")
  private BigDecimal requestLoanAmt = null;

  @JsonProperty("requestTerm")
  private BigDecimal requestTerm = null;

  @JsonProperty("term")
  private BigDecimal term = null;

  @JsonProperty("campaignCode")
  private String campaignCode = null;

  @JsonProperty("dueDate")
  private DateTime dueDate = null;

  @JsonProperty("coaProductCode")
  private String coaProductCode = null;

  @JsonProperty("debtAmount")
  private BigDecimal debtAmount = null;

  @JsonProperty("debtRecommend")
  private BigDecimal debtRecommend = null;

  @JsonProperty("botCondition")
  private String botCondition = null;

  @JsonProperty("botFactor")
  private BigDecimal botFactor = null;

  @JsonProperty("productFactor")
  private BigDecimal productFactor = null;

  @JsonProperty("installmentAmount")
  private BigDecimal installmentAmount = null;

  @JsonProperty("maxDebtBurdenInstallmentAmount")
  private BigDecimal maxDebtBurdenInstallmentAmount = null;

  @JsonProperty("installmentDate")
  private DateTime installmentDate = null;

  @JsonProperty("cbProduct")
  private String cbProduct = null;

  @JsonProperty("cbSubProduct")
  private String cbSubProduct = null;

  @JsonProperty("cbMarketCode")
  private String cbMarketCode = null;

  @JsonProperty("stampDuty")
  private BigDecimal stampDuty = null;

  @JsonProperty("penalty")
  private BigDecimal penalty = null;

  @JsonProperty("maxDebtBurden")
  private BigDecimal maxDebtBurden = null;

  @JsonProperty("eligibleCoaProductCode")
  private String eligibleCoaProductCode = null;

  @JsonProperty("debtBurden")
  private BigDecimal debtBurden = null;

  @JsonProperty("debtBurdenCreditLimit")
  private BigDecimal debtBurdenCreditLimit = null;

  @JsonProperty("additionalServices")
  private List<AdditionalServices> additionalServices = null;

  @JsonProperty("card")
  private Card card = null;

  @JsonProperty("cashTransfers")
  private List<CashTransfers> cashTransfers = null;

  @JsonProperty("loanFees")
  private List<LoanFees> loanFees = null;

  @JsonProperty("loanPricings")
  private List<LoanPricings> loanPricings = null;

  @JsonProperty("paymentMethods")
  private List<PaymentMethods> paymentMethods = null;

  @JsonProperty("loanTiers")
  private List<LoanTiers> loanTiers = null;

  @JsonProperty("capport")
  private Capport capport = null;

  public Loans loanId(String loanId) {
    this.loanId = loanId;
    return this;
  }

   /**
   * Get loanId
   * @return loanId
  **/
  @ApiModelProperty(value = "")


  public String getLoanId() {
    return loanId;
  }

  public void setLoanId(String loanId) {
    this.loanId = loanId;
  }

  public Loans normalCreditLimit(BigDecimal normalCreditLimit) {
    this.normalCreditLimit = normalCreditLimit;
    return this;
  }

   /**
   * Get normalCreditLimit
   * @return normalCreditLimit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNormalCreditLimit() {
    return normalCreditLimit;
  }

  public void setNormalCreditLimit(BigDecimal normalCreditLimit) {
    this.normalCreditLimit = normalCreditLimit;
  }

  public Loans maxCreditLimit(BigDecimal maxCreditLimit) {
    this.maxCreditLimit = maxCreditLimit;
    return this;
  }

   /**
   * Get maxCreditLimit
   * @return maxCreditLimit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMaxCreditLimit() {
    return maxCreditLimit;
  }

  public void setMaxCreditLimit(BigDecimal maxCreditLimit) {
    this.maxCreditLimit = maxCreditLimit;
  }

  public Loans maxCreditLimitBot(BigDecimal maxCreditLimitBot) {
    this.maxCreditLimitBot = maxCreditLimitBot;
    return this;
  }

   /**
   * Get maxCreditLimitBot
   * @return maxCreditLimitBot
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMaxCreditLimitBot() {
    return maxCreditLimitBot;
  }

  public void setMaxCreditLimitBot(BigDecimal maxCreditLimitBot) {
    this.maxCreditLimitBot = maxCreditLimitBot;
  }

  public Loans minCreditLimit(BigDecimal minCreditLimit) {
    this.minCreditLimit = minCreditLimit;
    return this;
  }

   /**
   * Get minCreditLimit
   * @return minCreditLimit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMinCreditLimit() {
    return minCreditLimit;
  }

  public void setMinCreditLimit(BigDecimal minCreditLimit) {
    this.minCreditLimit = minCreditLimit;
  }

  public Loans recommendLoanAmt(BigDecimal recommendLoanAmt) {
    this.recommendLoanAmt = recommendLoanAmt;
    return this;
  }

   /**
   * Get recommendLoanAmt
   * @return recommendLoanAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRecommendLoanAmt() {
    return recommendLoanAmt;
  }

  public void setRecommendLoanAmt(BigDecimal recommendLoanAmt) {
    this.recommendLoanAmt = recommendLoanAmt;
  }

  public Loans recommendTerm(BigDecimal recommendTerm) {
    this.recommendTerm = recommendTerm;
    return this;
  }

   /**
   * Get recommendTerm
   * @return recommendTerm
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRecommendTerm() {
    return recommendTerm;
  }

  public void setRecommendTerm(BigDecimal recommendTerm) {
    this.recommendTerm = recommendTerm;
  }

  public Loans requestLoanAmt(BigDecimal requestLoanAmt) {
    this.requestLoanAmt = requestLoanAmt;
    return this;
  }

   /**
   * Get requestLoanAmt
   * @return requestLoanAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRequestLoanAmt() {
    return requestLoanAmt;
  }

  public void setRequestLoanAmt(BigDecimal requestLoanAmt) {
    this.requestLoanAmt = requestLoanAmt;
  }

  public Loans requestTerm(BigDecimal requestTerm) {
    this.requestTerm = requestTerm;
    return this;
  }

   /**
   * Get requestTerm
   * @return requestTerm
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRequestTerm() {
    return requestTerm;
  }

  public void setRequestTerm(BigDecimal requestTerm) {
    this.requestTerm = requestTerm;
  }

  public Loans term(BigDecimal term) {
    this.term = term;
    return this;
  }

   /**
   * Get term
   * @return term
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getTerm() {
    return term;
  }

  public void setTerm(BigDecimal term) {
    this.term = term;
  }

  public Loans campaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
    return this;
  }

   /**
   * Get campaignCode
   * @return campaignCode
  **/
  @ApiModelProperty(value = "")


  public String getCampaignCode() {
    return campaignCode;
  }

  public void setCampaignCode(String campaignCode) {
    this.campaignCode = campaignCode;
  }

  public Loans dueDate(DateTime dueDate) {
    this.dueDate = dueDate;
    return this;
  }

   /**
   * Get dueDate
   * @return dueDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(DateTime dueDate) {
    this.dueDate = dueDate;
  }

  public Loans coaProductCode(String coaProductCode) {
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

  public Loans debtAmount(BigDecimal debtAmount) {
    this.debtAmount = debtAmount;
    return this;
  }

   /**
   * Get debtAmount
   * @return debtAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDebtAmount() {
    return debtAmount;
  }

  public void setDebtAmount(BigDecimal debtAmount) {
    this.debtAmount = debtAmount;
  }

  public Loans debtRecommend(BigDecimal debtRecommend) {
    this.debtRecommend = debtRecommend;
    return this;
  }

   /**
   * Get debtRecommend
   * @return debtRecommend
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDebtRecommend() {
    return debtRecommend;
  }

  public void setDebtRecommend(BigDecimal debtRecommend) {
    this.debtRecommend = debtRecommend;
  }

  public Loans botCondition(String botCondition) {
    this.botCondition = botCondition;
    return this;
  }

   /**
   * Get botCondition
   * @return botCondition
  **/
  @ApiModelProperty(value = "")


  public String getBotCondition() {
    return botCondition;
  }

  public void setBotCondition(String botCondition) {
    this.botCondition = botCondition;
  }

  public Loans botFactor(BigDecimal botFactor) {
    this.botFactor = botFactor;
    return this;
  }

   /**
   * Get botFactor
   * @return botFactor
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBotFactor() {
    return botFactor;
  }

  public void setBotFactor(BigDecimal botFactor) {
    this.botFactor = botFactor;
  }

  public Loans productFactor(BigDecimal productFactor) {
    this.productFactor = productFactor;
    return this;
  }

   /**
   * Get productFactor
   * @return productFactor
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getProductFactor() {
    return productFactor;
  }

  public void setProductFactor(BigDecimal productFactor) {
    this.productFactor = productFactor;
  }

  public Loans installmentAmount(BigDecimal installmentAmount) {
    this.installmentAmount = installmentAmount;
    return this;
  }

   /**
   * Get installmentAmount
   * @return installmentAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getInstallmentAmount() {
    return installmentAmount;
  }

  public void setInstallmentAmount(BigDecimal installmentAmount) {
    this.installmentAmount = installmentAmount;
  }

  public Loans maxDebtBurdenInstallmentAmount(BigDecimal maxDebtBurdenInstallmentAmount) {
    this.maxDebtBurdenInstallmentAmount = maxDebtBurdenInstallmentAmount;
    return this;
  }

   /**
   * Get maxDebtBurdenInstallmentAmount
   * @return maxDebtBurdenInstallmentAmount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMaxDebtBurdenInstallmentAmount() {
    return maxDebtBurdenInstallmentAmount;
  }

  public void setMaxDebtBurdenInstallmentAmount(BigDecimal maxDebtBurdenInstallmentAmount) {
    this.maxDebtBurdenInstallmentAmount = maxDebtBurdenInstallmentAmount;
  }

  public Loans installmentDate(DateTime installmentDate) {
    this.installmentDate = installmentDate;
    return this;
  }

   /**
   * Get installmentDate
   * @return installmentDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getInstallmentDate() {
    return installmentDate;
  }

  public void setInstallmentDate(DateTime installmentDate) {
    this.installmentDate = installmentDate;
  }

  public Loans cbProduct(String cbProduct) {
    this.cbProduct = cbProduct;
    return this;
  }

   /**
   * Get cbProduct
   * @return cbProduct
  **/
  @ApiModelProperty(value = "")


  public String getCbProduct() {
    return cbProduct;
  }

  public void setCbProduct(String cbProduct) {
    this.cbProduct = cbProduct;
  }

  public Loans cbSubProduct(String cbSubProduct) {
    this.cbSubProduct = cbSubProduct;
    return this;
  }

   /**
   * Get cbSubProduct
   * @return cbSubProduct
  **/
  @ApiModelProperty(value = "")


  public String getCbSubProduct() {
    return cbSubProduct;
  }

  public void setCbSubProduct(String cbSubProduct) {
    this.cbSubProduct = cbSubProduct;
  }

  public Loans cbMarketCode(String cbMarketCode) {
    this.cbMarketCode = cbMarketCode;
    return this;
  }

   /**
   * Get cbMarketCode
   * @return cbMarketCode
  **/
  @ApiModelProperty(value = "")


  public String getCbMarketCode() {
    return cbMarketCode;
  }

  public void setCbMarketCode(String cbMarketCode) {
    this.cbMarketCode = cbMarketCode;
  }

  public Loans stampDuty(BigDecimal stampDuty) {
    this.stampDuty = stampDuty;
    return this;
  }

   /**
   * Get stampDuty
   * @return stampDuty
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getStampDuty() {
    return stampDuty;
  }

  public void setStampDuty(BigDecimal stampDuty) {
    this.stampDuty = stampDuty;
  }

  public Loans penalty(BigDecimal penalty) {
    this.penalty = penalty;
    return this;
  }

   /**
   * Get penalty
   * @return penalty
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPenalty() {
    return penalty;
  }

  public void setPenalty(BigDecimal penalty) {
    this.penalty = penalty;
  }

  public Loans maxDebtBurden(BigDecimal maxDebtBurden) {
    this.maxDebtBurden = maxDebtBurden;
    return this;
  }

   /**
   * Get maxDebtBurden
   * @return maxDebtBurden
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getMaxDebtBurden() {
    return maxDebtBurden;
  }

  public void setMaxDebtBurden(BigDecimal maxDebtBurden) {
    this.maxDebtBurden = maxDebtBurden;
  }

  public Loans eligibleCoaProductCode(String eligibleCoaProductCode) {
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

  public Loans debtBurden(BigDecimal debtBurden) {
    this.debtBurden = debtBurden;
    return this;
  }

   /**
   * Get debtBurden
   * @return debtBurden
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDebtBurden() {
    return debtBurden;
  }

  public void setDebtBurden(BigDecimal debtBurden) {
    this.debtBurden = debtBurden;
  }

  public Loans debtBurdenCreditLimit(BigDecimal debtBurdenCreditLimit) {
    this.debtBurdenCreditLimit = debtBurdenCreditLimit;
    return this;
  }

   /**
   * Get debtBurdenCreditLimit
   * @return debtBurdenCreditLimit
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getDebtBurdenCreditLimit() {
    return debtBurdenCreditLimit;
  }

  public void setDebtBurdenCreditLimit(BigDecimal debtBurdenCreditLimit) {
    this.debtBurdenCreditLimit = debtBurdenCreditLimit;
  }

  public Loans additionalServices(List<AdditionalServices> additionalServices) {
    this.additionalServices = additionalServices;
    return this;
  }

  public Loans addAdditionalServicesItem(AdditionalServices additionalServicesItem) {
    if (this.additionalServices == null) {
      this.additionalServices = new ArrayList<AdditionalServices>();
    }
    this.additionalServices.add(additionalServicesItem);
    return this;
  }

   /**
   * Get additionalServices
   * @return additionalServices
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<AdditionalServices> getAdditionalServices() {
    return additionalServices;
  }

  public void setAdditionalServices(List<AdditionalServices> additionalServices) {
    this.additionalServices = additionalServices;
  }

  public Loans card(Card card) {
    this.card = card;
    return this;
  }

   /**
   * Get card
   * @return card
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Card getCard() {
    return card;
  }

  public void setCard(Card card) {
    this.card = card;
  }

  public Loans cashTransfers(List<CashTransfers> cashTransfers) {
    this.cashTransfers = cashTransfers;
    return this;
  }

  public Loans addCashTransfersItem(CashTransfers cashTransfersItem) {
    if (this.cashTransfers == null) {
      this.cashTransfers = new ArrayList<CashTransfers>();
    }
    this.cashTransfers.add(cashTransfersItem);
    return this;
  }

   /**
   * Get cashTransfers
   * @return cashTransfers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CashTransfers> getCashTransfers() {
    return cashTransfers;
  }

  public void setCashTransfers(List<CashTransfers> cashTransfers) {
    this.cashTransfers = cashTransfers;
  }

  public Loans loanFees(List<LoanFees> loanFees) {
    this.loanFees = loanFees;
    return this;
  }

  public Loans addLoanFeesItem(LoanFees loanFeesItem) {
    if (this.loanFees == null) {
      this.loanFees = new ArrayList<LoanFees>();
    }
    this.loanFees.add(loanFeesItem);
    return this;
  }

   /**
   * Get loanFees
   * @return loanFees
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<LoanFees> getLoanFees() {
    return loanFees;
  }

  public void setLoanFees(List<LoanFees> loanFees) {
    this.loanFees = loanFees;
  }

  public Loans loanPricings(List<LoanPricings> loanPricings) {
    this.loanPricings = loanPricings;
    return this;
  }

  public Loans addLoanPricingsItem(LoanPricings loanPricingsItem) {
    if (this.loanPricings == null) {
      this.loanPricings = new ArrayList<LoanPricings>();
    }
    this.loanPricings.add(loanPricingsItem);
    return this;
  }

   /**
   * Get loanPricings
   * @return loanPricings
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<LoanPricings> getLoanPricings() {
    return loanPricings;
  }

  public void setLoanPricings(List<LoanPricings> loanPricings) {
    this.loanPricings = loanPricings;
  }

  public Loans paymentMethods(List<PaymentMethods> paymentMethods) {
    this.paymentMethods = paymentMethods;
    return this;
  }

  public Loans addPaymentMethodsItem(PaymentMethods paymentMethodsItem) {
    if (this.paymentMethods == null) {
      this.paymentMethods = new ArrayList<PaymentMethods>();
    }
    this.paymentMethods.add(paymentMethodsItem);
    return this;
  }

   /**
   * Get paymentMethods
   * @return paymentMethods
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PaymentMethods> getPaymentMethods() {
    return paymentMethods;
  }

  public void setPaymentMethods(List<PaymentMethods> paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public Loans loanTiers(List<LoanTiers> loanTiers) {
    this.loanTiers = loanTiers;
    return this;
  }

  public Loans addLoanTiersItem(LoanTiers loanTiersItem) {
    if (this.loanTiers == null) {
      this.loanTiers = new ArrayList<LoanTiers>();
    }
    this.loanTiers.add(loanTiersItem);
    return this;
  }

   /**
   * Get loanTiers
   * @return loanTiers
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<LoanTiers> getLoanTiers() {
    return loanTiers;
  }

  public void setLoanTiers(List<LoanTiers> loanTiers) {
    this.loanTiers = loanTiers;
  }

  public Loans capport(Capport capport) {
    this.capport = capport;
    return this;
  }

   /**
   * Get capport
   * @return capport
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Capport getCapport() {
    return capport;
  }

  public void setCapport(Capport capport) {
    this.capport = capport;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Loans loans = (Loans) o;
    return Objects.equals(this.loanId, loans.loanId) &&
        Objects.equals(this.normalCreditLimit, loans.normalCreditLimit) &&
        Objects.equals(this.maxCreditLimit, loans.maxCreditLimit) &&
        Objects.equals(this.maxCreditLimitBot, loans.maxCreditLimitBot) &&
        Objects.equals(this.minCreditLimit, loans.minCreditLimit) &&
        Objects.equals(this.recommendLoanAmt, loans.recommendLoanAmt) &&
        Objects.equals(this.recommendTerm, loans.recommendTerm) &&
        Objects.equals(this.requestLoanAmt, loans.requestLoanAmt) &&
        Objects.equals(this.requestTerm, loans.requestTerm) &&
        Objects.equals(this.term, loans.term) &&
        Objects.equals(this.campaignCode, loans.campaignCode) &&
        Objects.equals(this.dueDate, loans.dueDate) &&
        Objects.equals(this.coaProductCode, loans.coaProductCode) &&
        Objects.equals(this.debtAmount, loans.debtAmount) &&
        Objects.equals(this.debtRecommend, loans.debtRecommend) &&
        Objects.equals(this.botCondition, loans.botCondition) &&
        Objects.equals(this.botFactor, loans.botFactor) &&
        Objects.equals(this.productFactor, loans.productFactor) &&
        Objects.equals(this.installmentAmount, loans.installmentAmount) &&
        Objects.equals(this.maxDebtBurdenInstallmentAmount, loans.maxDebtBurdenInstallmentAmount) &&
        Objects.equals(this.installmentDate, loans.installmentDate) &&
        Objects.equals(this.cbProduct, loans.cbProduct) &&
        Objects.equals(this.cbSubProduct, loans.cbSubProduct) &&
        Objects.equals(this.cbMarketCode, loans.cbMarketCode) &&
        Objects.equals(this.stampDuty, loans.stampDuty) &&
        Objects.equals(this.penalty, loans.penalty) &&
        Objects.equals(this.maxDebtBurden, loans.maxDebtBurden) &&
        Objects.equals(this.eligibleCoaProductCode, loans.eligibleCoaProductCode) &&
        Objects.equals(this.debtBurden, loans.debtBurden) &&
        Objects.equals(this.debtBurdenCreditLimit, loans.debtBurdenCreditLimit) &&
        Objects.equals(this.additionalServices, loans.additionalServices) &&
        Objects.equals(this.card, loans.card) &&
        Objects.equals(this.cashTransfers, loans.cashTransfers) &&
        Objects.equals(this.loanFees, loans.loanFees) &&
        Objects.equals(this.loanPricings, loans.loanPricings) &&
        Objects.equals(this.paymentMethods, loans.paymentMethods) &&
        Objects.equals(this.loanTiers, loans.loanTiers) &&
        Objects.equals(this.capport, loans.capport);
  }

  @Override
  public int hashCode() {
    return Objects.hash(loanId, normalCreditLimit, maxCreditLimit, maxCreditLimitBot, minCreditLimit, recommendLoanAmt, recommendTerm, requestLoanAmt, requestTerm, term, campaignCode, dueDate, coaProductCode, debtAmount, debtRecommend, botCondition, botFactor, productFactor, installmentAmount, maxDebtBurdenInstallmentAmount, installmentDate, cbProduct, cbSubProduct, cbMarketCode, stampDuty, penalty, maxDebtBurden, eligibleCoaProductCode, debtBurden, debtBurdenCreditLimit, additionalServices, card, cashTransfers, loanFees, loanPricings, paymentMethods, loanTiers, capport);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Loans {\n");
    
    sb.append("    loanId: ").append(toIndentedString(loanId)).append("\n");
    sb.append("    normalCreditLimit: ").append(toIndentedString(normalCreditLimit)).append("\n");
    sb.append("    maxCreditLimit: ").append(toIndentedString(maxCreditLimit)).append("\n");
    sb.append("    maxCreditLimitBot: ").append(toIndentedString(maxCreditLimitBot)).append("\n");
    sb.append("    minCreditLimit: ").append(toIndentedString(minCreditLimit)).append("\n");
    sb.append("    recommendLoanAmt: ").append(toIndentedString(recommendLoanAmt)).append("\n");
    sb.append("    recommendTerm: ").append(toIndentedString(recommendTerm)).append("\n");
    sb.append("    requestLoanAmt: ").append(toIndentedString(requestLoanAmt)).append("\n");
    sb.append("    requestTerm: ").append(toIndentedString(requestTerm)).append("\n");
    sb.append("    term: ").append(toIndentedString(term)).append("\n");
    sb.append("    campaignCode: ").append(toIndentedString(campaignCode)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    coaProductCode: ").append(toIndentedString(coaProductCode)).append("\n");
    sb.append("    debtAmount: ").append(toIndentedString(debtAmount)).append("\n");
    sb.append("    debtRecommend: ").append(toIndentedString(debtRecommend)).append("\n");
    sb.append("    botCondition: ").append(toIndentedString(botCondition)).append("\n");
    sb.append("    botFactor: ").append(toIndentedString(botFactor)).append("\n");
    sb.append("    productFactor: ").append(toIndentedString(productFactor)).append("\n");
    sb.append("    installmentAmount: ").append(toIndentedString(installmentAmount)).append("\n");
    sb.append("    maxDebtBurdenInstallmentAmount: ").append(toIndentedString(maxDebtBurdenInstallmentAmount)).append("\n");
    sb.append("    installmentDate: ").append(toIndentedString(installmentDate)).append("\n");
    sb.append("    cbProduct: ").append(toIndentedString(cbProduct)).append("\n");
    sb.append("    cbSubProduct: ").append(toIndentedString(cbSubProduct)).append("\n");
    sb.append("    cbMarketCode: ").append(toIndentedString(cbMarketCode)).append("\n");
    sb.append("    stampDuty: ").append(toIndentedString(stampDuty)).append("\n");
    sb.append("    penalty: ").append(toIndentedString(penalty)).append("\n");
    sb.append("    maxDebtBurden: ").append(toIndentedString(maxDebtBurden)).append("\n");
    sb.append("    eligibleCoaProductCode: ").append(toIndentedString(eligibleCoaProductCode)).append("\n");
    sb.append("    debtBurden: ").append(toIndentedString(debtBurden)).append("\n");
    sb.append("    debtBurdenCreditLimit: ").append(toIndentedString(debtBurdenCreditLimit)).append("\n");
    sb.append("    additionalServices: ").append(toIndentedString(additionalServices)).append("\n");
    sb.append("    card: ").append(toIndentedString(card)).append("\n");
    sb.append("    cashTransfers: ").append(toIndentedString(cashTransfers)).append("\n");
    sb.append("    loanFees: ").append(toIndentedString(loanFees)).append("\n");
    sb.append("    loanPricings: ").append(toIndentedString(loanPricings)).append("\n");
    sb.append("    paymentMethods: ").append(toIndentedString(paymentMethods)).append("\n");
    sb.append("    loanTiers: ").append(toIndentedString(loanTiers)).append("\n");
    sb.append("    capport: ").append(toIndentedString(capport)).append("\n");
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

