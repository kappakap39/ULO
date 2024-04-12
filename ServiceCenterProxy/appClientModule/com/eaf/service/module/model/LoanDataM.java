package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class LoanDataM implements Serializable,Cloneable{
	private String accountNo;
	private BigDecimal finalCreditLimit;
	private BigDecimal installmentAmt;
	private String bookedFlag;
	private String cycleCut;
	private String paymentMethod;
	private String autoPaymentNo;	
	private ArrayList<PersonalRefDataM> personalRefs;
	private CardDataM card;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getFinalCreditLimit() {
		return finalCreditLimit;
	}
	public void setFinalCreditLimit(BigDecimal finalCreditLimit) {
		this.finalCreditLimit = finalCreditLimit;
	}
	public BigDecimal getInstallmentAmt() {
		return installmentAmt;
	}
	public void setInstallmentAmt(BigDecimal installmentAmt) {
		this.installmentAmt = installmentAmt;
	}
	public String getBookedFlag() {
		return bookedFlag;
	}
	public void setBookedFlag(String bookedFlag) {
		this.bookedFlag = bookedFlag;
	}
	public String getCycleCut() {
		return cycleCut;
	}
	public void setCycleCut(String cycleCut) {
		this.cycleCut = cycleCut;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getAutoPaymentNo() {
		return autoPaymentNo;
	}
	public void setAutoPaymentNo(String autoPaymentNo) {
		this.autoPaymentNo = autoPaymentNo;
	}
	public ArrayList<PersonalRefDataM> getPersonalRefs() {
		return personalRefs;
	}
	public void setPersonalRefs(ArrayList<PersonalRefDataM> personalRefs) {
		this.personalRefs = personalRefs;
	}
	public CardDataM getCard() {
		return card;
	}
	public void setCard(CardDataM card) {
		this.card = card;
	}
}
