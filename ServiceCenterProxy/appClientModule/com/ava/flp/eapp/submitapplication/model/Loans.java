package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.ava.flp.eapp.submitapplication.model.AdditionalServices;
import com.ava.flp.eapp.submitapplication.model.Card;
import com.ava.flp.eapp.submitapplication.model.CashTransfers;
import com.ava.flp.eapp.submitapplication.model.PaymentMethods;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Loans
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-21T17:45:58.926+07:00")

public class Loans   {
  @JsonProperty("requestLoanAmt")
  private BigDecimal requestLoanAmt = null;

  @JsonProperty("requestTerm")
  private BigDecimal requestTerm = null;

  @JsonProperty("coaProductCode")
  private String coaProductCode = null;

  @JsonProperty("additionalServices")
  private List<AdditionalServices> additionalServices = null;

  @JsonProperty("card")
  private Card card = null;

  @JsonProperty("cashTransfers")
  private List<CashTransfers> cashTransfers = null;

  @JsonProperty("paymentMethods")
  private List<PaymentMethods> paymentMethods = null;

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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Loans loans = (Loans) o;
    return Objects.equals(this.requestLoanAmt, loans.requestLoanAmt) &&
        Objects.equals(this.requestTerm, loans.requestTerm) &&
        Objects.equals(this.coaProductCode, loans.coaProductCode) &&
        Objects.equals(this.additionalServices, loans.additionalServices) &&
        Objects.equals(this.card, loans.card) &&
        Objects.equals(this.cashTransfers, loans.cashTransfers) &&
        Objects.equals(this.paymentMethods, loans.paymentMethods)&&
        Objects.equals(this.loanTiers, loans.loanTiers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestLoanAmt, requestTerm, coaProductCode, additionalServices, card, cashTransfers, paymentMethods,loanTiers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Loans {\n");
    
    sb.append("    requestLoanAmt: ").append(toIndentedString(requestLoanAmt)).append("\n");
    sb.append("    requestTerm: ").append(toIndentedString(requestTerm)).append("\n");
    sb.append("    coaProductCode: ").append(toIndentedString(coaProductCode)).append("\n");
    sb.append("    additionalServices: ").append(toIndentedString(additionalServices)).append("\n");
    sb.append("    card: ").append(toIndentedString(card)).append("\n");
    sb.append("    cashTransfers: ").append(toIndentedString(cashTransfers)).append("\n");
    sb.append("    paymentMethods: ").append(toIndentedString(paymentMethods)).append("\n");
    sb.append("    loanTiers: ").append(toIndentedString(loanTiers)).append("\n");
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
  @JsonProperty("recommendLoanAmt")
  private BigDecimal recommendLoanAmt = null;
  
  @JsonProperty("feeWaiveCode")
  private String feeWaiveCode = null;
  
  public BigDecimal getRecommendLoanAmt() {
	return recommendLoanAmt;
}

public void setRecommendLoanAmt(BigDecimal recommendLoanAmt) {
	this.recommendLoanAmt = recommendLoanAmt;
}

public String getFeeWaiveCode() {
	return feeWaiveCode;
}

public void setFeeWaiveCode(String feeWaiveCode) {
	this.feeWaiveCode = feeWaiveCode;
}

	//CJD Additional
	@JsonProperty("loanTiers")
	private List<LoanTier> loanTiers = null;

	public List<LoanTier> getLoanTiers() {
		return loanTiers;
	}

	public void setLoanTiers(List<LoanTier> loanTiers) {
		this.loanTiers = loanTiers;
	}
}

