package com.eaf.orig.ulo.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class PaymentMethodDataM implements Serializable,Cloneable {
	public PaymentMethodDataM(){
		super();
	}
	
	public void init(String uniqueId) {
		this.paymentMethodId=uniqueId;
	}
	private String personalId; //ORIG_PAYMENT_METHOD.PERSONAL_ID(VARCHAR2)
	private String paymentMethodId;	//ORIG_PAYMENT_METHOD.PAYMENT_METHOD_ID(VARCHAR2)
	private BigDecimal dueCycle;	//ORIG_PAYMENT_METHOD.DUE_CYCLE(NUMBER)
	private String bankCode;	//ORIG_PAYMENT_METHOD.BANK_CODE(VARCHAR2)
	private String accountType;	//ORIG_PAYMENT_METHOD.ACCOUNT_TYPE(VARCHAR2)
	private String productType;	//ORIG_PAYMENT_METHOD.PRODUCT_TYPE(VARCHAR2)
	private BigDecimal paymentRatio;	//ORIG_PAYMENT_METHOD.PAYMENT_RATIO(NUMBER)
	private String paymentMethod;	//ORIG_PAYMENT_METHOD.PAYMENT_METHOD(VARCHAR2)
	private String accountNo;	//ORIG_PAYMENT_METHOD.ACCOUNT_NO(VARCHAR2)
	private String accountName;	//ORIG_PAYMENT_METHOD.ACCOUNT_NAME(VARCHAR2)
	private String createBy;	//ORIG_PAYMENT_METHOD.CREATE_BY(VARCHAR2)
	private Timestamp createDate;	//ORIG_PAYMENT_METHOD.CREATE_DATE(DATE)
	private String updateBy;	//ORIG_PAYMENT_METHOD.UPDATE_BY(VARCHAR2)
	private Timestamp updateDate;	//ORIG_PAYMENT_METHOD.UPDATE_DATE(DATE)
	private String completeData; //ORIG_PAYMENT_METHOD.COMPLETE_DATA(VARCHAR2)
	private String fullAccountNo;
	
	public String getPaymentMethodId() {
		return paymentMethodId;
	}
	public void setPaymentMethodId(String paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}
	public BigDecimal getDueCycle() {
		return dueCycle;
	}
	public void setDueCycle(BigDecimal dueCycle) {
		this.dueCycle = dueCycle;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getPaymentRatio() {
		return paymentRatio;
	}
	public void setPaymentRatio(BigDecimal paymentRatio) {
		this.paymentRatio = paymentRatio;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getCompleteData() {
		return completeData;
	}
	public void setCompleteData(String completeData) {
		this.completeData = completeData;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getFullAccountNo() {
		return fullAccountNo;
	}

	public void setFullAccountNo(String fullAccountNo) {
		this.fullAccountNo = fullAccountNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentMethodDataM [personalId=");
		builder.append(personalId);
		builder.append(", paymentMethodId=");
		builder.append(paymentMethodId);
		builder.append(", dueCycle=");
		builder.append(dueCycle);
		builder.append(", bankCode=");
		builder.append(bankCode);
		builder.append(", accountType=");
		builder.append(accountType);
		builder.append(", productType=");
		builder.append(productType);
		builder.append(", paymentRatio=");
		builder.append(paymentRatio);
		builder.append(", paymentMethod=");
		builder.append(paymentMethod);
		builder.append(", accountNo=");
		builder.append(accountNo);
		builder.append(", fullAccountNo=");
		builder.append(fullAccountNo);
		builder.append(", accountName=");
		builder.append(accountName);
		builder.append(", createBy=");
		builder.append(createBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", updateBy=");
		builder.append(updateBy);
		builder.append(", updateDate=");
		builder.append(updateDate);
		builder.append(", completeData=");
		builder.append(completeData);
		builder.append("]");
		return builder.toString();
	}	
}
