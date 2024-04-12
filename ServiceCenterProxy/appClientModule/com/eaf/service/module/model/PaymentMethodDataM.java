package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentMethodDataM implements Serializable, Cloneable{
	private String paymentMethod;
	private String dueCycle;
	private String autopayAccountNo;
	private BigDecimal paymentRatio;
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getDueCycle() {
		return dueCycle;
	}
	public void setDueCycle(String dueCycle) {
		this.dueCycle = dueCycle;
	}
	public String getAutopayAccountNo() {
		return autopayAccountNo;
	}
	public void setAutopayAccountNo(String autopayAccountNo) {
		this.autopayAccountNo = autopayAccountNo;
	}
	public BigDecimal getPaymentRatio() {
		return paymentRatio;
	}
	public void setPaymentRatio(BigDecimal paymentRatio) {
		this.paymentRatio = paymentRatio;
	}
}
