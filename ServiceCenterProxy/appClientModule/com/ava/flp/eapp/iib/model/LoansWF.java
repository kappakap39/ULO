package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.CardMWF;
import com.ava.flp.eapp.iib.model.PersonalRefsWF;
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
 * LoansWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-07T15:43:28.181+07:00")

public class LoansWF   {
  @JsonProperty("accountNo")
  private String accountNo = null;

  @JsonProperty("finalCreditLimit")
  private String finalCreditLimit = null;

  @JsonProperty("installmentAmt")
  private String installmentAmt = null;

  @JsonProperty("bookedFlag")
  private String bookedFlag = null;

  @JsonProperty("cycleCut")
  private String cycleCut = null;

  @JsonProperty("paymentMethod")
  private String paymentMethod = null;

  @JsonProperty("autopayAccountNo")
  private String autopayAccountNo = null;

  @JsonProperty("paymentRatio")
  private BigDecimal paymentRatio = null;

  @JsonProperty("personalRefs")
  private List<PersonalRefsWF> personalRefs = null;

  @JsonProperty("cardM")
  private CardMWF cardM = null;

  @JsonProperty("loanAmt")
  private BigDecimal loanAmt = null;

  @JsonProperty("term")
  private BigDecimal term = null;

  @JsonProperty("accountOpenDate")
  private DateTime accountOpenDate = null;

  public LoansWF accountNo(String accountNo) {
    this.accountNo = accountNo;
    return this;
  }

   /**
   * Get accountNo
   * @return accountNo
  **/
  @ApiModelProperty(value = "")


  public String getAccountNo() {
    return accountNo;
  }

  public void setAccountNo(String accountNo) {
    this.accountNo = accountNo;
  }

  public LoansWF finalCreditLimit(String finalCreditLimit) {
    this.finalCreditLimit = finalCreditLimit;
    return this;
  }

   /**
   * Get finalCreditLimit
   * @return finalCreditLimit
  **/
  @ApiModelProperty(value = "")


  public String getFinalCreditLimit() {
    return finalCreditLimit;
  }

  public void setFinalCreditLimit(String finalCreditLimit) {
    this.finalCreditLimit = finalCreditLimit;
  }

  public LoansWF installmentAmt(String installmentAmt) {
    this.installmentAmt = installmentAmt;
    return this;
  }

   /**
   * Get installmentAmt
   * @return installmentAmt
  **/
  @ApiModelProperty(value = "")


  public String getInstallmentAmt() {
    return installmentAmt;
  }

  public void setInstallmentAmt(String installmentAmt) {
    this.installmentAmt = installmentAmt;
  }

  public LoansWF bookedFlag(String bookedFlag) {
    this.bookedFlag = bookedFlag;
    return this;
  }

   /**
   * Get bookedFlag
   * @return bookedFlag
  **/
  @ApiModelProperty(value = "")


  public String getBookedFlag() {
    return bookedFlag;
  }

  public void setBookedFlag(String bookedFlag) {
    this.bookedFlag = bookedFlag;
  }

  public LoansWF cycleCut(String cycleCut) {
    this.cycleCut = cycleCut;
    return this;
  }

   /**
   * Get cycleCut
   * @return cycleCut
  **/
  @ApiModelProperty(value = "")


  public String getCycleCut() {
    return cycleCut;
  }

  public void setCycleCut(String cycleCut) {
    this.cycleCut = cycleCut;
  }

  public LoansWF paymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
    return this;
  }

   /**
   * Get paymentMethod
   * @return paymentMethod
  **/
  @ApiModelProperty(value = "")


  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public LoansWF autopayAccountNo(String autopayAccountNo) {
    this.autopayAccountNo = autopayAccountNo;
    return this;
  }

   /**
   * Get autopayAccountNo
   * @return autopayAccountNo
  **/
  @ApiModelProperty(value = "")


  public String getAutopayAccountNo() {
    return autopayAccountNo;
  }

  public void setAutopayAccountNo(String autopayAccountNo) {
    this.autopayAccountNo = autopayAccountNo;
  }

  public LoansWF paymentRatio(BigDecimal paymentRatio) {
    this.paymentRatio = paymentRatio;
    return this;
  }

   /**
   * Get paymentRatio
   * @return paymentRatio
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPaymentRatio() {
    return paymentRatio;
  }

  public void setPaymentRatio(BigDecimal paymentRatio) {
    this.paymentRatio = paymentRatio;
  }

  public LoansWF personalRefs(List<PersonalRefsWF> personalRefs) {
    this.personalRefs = personalRefs;
    return this;
  }

  public LoansWF addPersonalRefsItem(PersonalRefsWF personalRefsItem) {
    if (this.personalRefs == null) {
      this.personalRefs = new ArrayList<PersonalRefsWF>();
    }
    this.personalRefs.add(personalRefsItem);
    return this;
  }

   /**
   * Get personalRefs
   * @return personalRefs
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PersonalRefsWF> getPersonalRefs() {
    return personalRefs;
  }

  public void setPersonalRefs(List<PersonalRefsWF> personalRefs) {
    this.personalRefs = personalRefs;
  }

  public LoansWF cardM(CardMWF cardM) {
    this.cardM = cardM;
    return this;
  }

   /**
   * Get cardM
   * @return cardM
  **/
  @ApiModelProperty(value = "")

  @Valid

  public CardMWF getCardM() {
    return cardM;
  }

  public void setCardM(CardMWF cardM) {
    this.cardM = cardM;
  }

  public LoansWF loanAmt(BigDecimal loanAmt) {
    this.loanAmt = loanAmt;
    return this;
  }

   /**
   * Get loanAmt
   * @return loanAmt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLoanAmt() {
    return loanAmt;
  }

  public void setLoanAmt(BigDecimal loanAmt) {
    this.loanAmt = loanAmt;
  }

  public LoansWF term(BigDecimal term) {
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

  public LoansWF accountOpenDate(DateTime accountOpenDate) {
    this.accountOpenDate = accountOpenDate;
    return this;
  }

   /**
   * Get accountOpenDate
   * @return accountOpenDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getAccountOpenDate() {
    return accountOpenDate;
  }

  public void setAccountOpenDate(DateTime accountOpenDate) {
    this.accountOpenDate = accountOpenDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoansWF loansWF = (LoansWF) o;
    return Objects.equals(this.accountNo, loansWF.accountNo) &&
        Objects.equals(this.finalCreditLimit, loansWF.finalCreditLimit) &&
        Objects.equals(this.installmentAmt, loansWF.installmentAmt) &&
        Objects.equals(this.bookedFlag, loansWF.bookedFlag) &&
        Objects.equals(this.cycleCut, loansWF.cycleCut) &&
        Objects.equals(this.paymentMethod, loansWF.paymentMethod) &&
        Objects.equals(this.autopayAccountNo, loansWF.autopayAccountNo) &&
        Objects.equals(this.paymentRatio, loansWF.paymentRatio) &&
        Objects.equals(this.personalRefs, loansWF.personalRefs) &&
        Objects.equals(this.cardM, loansWF.cardM) &&
        Objects.equals(this.loanAmt, loansWF.loanAmt) &&
        Objects.equals(this.term, loansWF.term) &&
        Objects.equals(this.accountOpenDate, loansWF.accountOpenDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNo, finalCreditLimit, installmentAmt, bookedFlag, cycleCut, paymentMethod, autopayAccountNo, paymentRatio, personalRefs, cardM, loanAmt, term, accountOpenDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoansWF {\n");
    
    sb.append("    accountNo: ").append(toIndentedString(accountNo)).append("\n");
    sb.append("    finalCreditLimit: ").append(toIndentedString(finalCreditLimit)).append("\n");
    sb.append("    installmentAmt: ").append(toIndentedString(installmentAmt)).append("\n");
    sb.append("    bookedFlag: ").append(toIndentedString(bookedFlag)).append("\n");
    sb.append("    cycleCut: ").append(toIndentedString(cycleCut)).append("\n");
    sb.append("    paymentMethod: ").append(toIndentedString(paymentMethod)).append("\n");
    sb.append("    autopayAccountNo: ").append(toIndentedString(autopayAccountNo)).append("\n");
    sb.append("    paymentRatio: ").append(toIndentedString(paymentRatio)).append("\n");
    sb.append("    personalRefs: ").append(toIndentedString(personalRefs)).append("\n");
    sb.append("    cardM: ").append(toIndentedString(cardM)).append("\n");
    sb.append("    loanAmt: ").append(toIndentedString(loanAmt)).append("\n");
    sb.append("    term: ").append(toIndentedString(term)).append("\n");
    sb.append("    accountOpenDate: ").append(toIndentedString(accountOpenDate)).append("\n");
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

