package com.eaf.inf.batch.ulo.letter.history.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

public class LetterHistoryDataM implements Serializable, Cloneable
{

	private String letterNo;
	private String appGroupNo;
	private String idNo;
	private String firstName;
	private String midName;
	private String lastName;
	private Date de2SubmitDate;
	private String letterType;
	private String letterTemplate;
	private String letterContent;
	
	private String sendFlag;
	private String customerSendFlag;
	private String customerSendMethod;
	private String customerEmailAddress;
	private String customerEmailTemplate;
	private String customerLanguage;
	private String customerAttachFile;
	private String sellerSendFlag;
	private String sellerSendMethod;
	private String sellerEmailAddress;
	private String sellerEmailTemplate;
	private String sellerLanguage;
	private String sellerAttachFile;
	private String customerResendSendMethod;
	private String customerResendEmailAddress;
	private String customerResendEmailTemplate;
	private Date customerResendDate;
	private String customerResendBy;
	private int customerResendCount;
	
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public String getAppGroupNo() {
		return appGroupNo;
	}
	public void setAppGroupNo(String appGroupNo) {
		this.appGroupNo = appGroupNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMidName() {
		return midName;
	}
	public void setMidName(String midName) {
		this.midName = midName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDe2SubmitDate() {
		return de2SubmitDate;
	}
	public void setDe2SubmitDate(Date de2SubmitDate) {
		this.de2SubmitDate = de2SubmitDate;
	}
	public String getLetterContent() {
		return letterContent;
	}
	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}
	public String getLetterType() {
		return letterType;
	}
	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}
	public String getLetterTemplate() {
		return letterTemplate;
	}
	public void setLetterTemplate(String letterTemplate) {
		this.letterTemplate = letterTemplate;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getCustomerSendFlag() {
		return customerSendFlag;
	}
	public void setCustomerSendFlag(String customerSendFlag) {
		this.customerSendFlag = customerSendFlag;
	}
	public String getCustomerSendMethod() {
		return customerSendMethod;
	}
	public void setCustomerSendMethod(String customerSendMethod) {
		this.customerSendMethod = customerSendMethod;
	}
	public String getCustomerEmailAddress() {
		return customerEmailAddress;
	}
	public void setCustomerEmailAddress(String customerEmailAddress) {
		this.customerEmailAddress = customerEmailAddress;
	}
	public String getCustomerEmailTemplate() {
		return customerEmailTemplate;
	}
	public void setCustomerEmailTemplate(String customerEmailTemplate) {
		this.customerEmailTemplate = customerEmailTemplate;
	}
	public String getCustomerLanguage() {
		return customerLanguage;
	}
	public void setCustomerLanguage(String customerLanguage) {
		this.customerLanguage = customerLanguage;
	}
	public String getCustomerAttachFile() {
		return customerAttachFile;
	}
	public void setCustomerAttachFile(String customerAttachFile) {
		this.customerAttachFile = customerAttachFile;
	}
	public String getSellerSendFlag() {
		return sellerSendFlag;
	}
	public void setSellerSendFlag(String sellerSendFlag) {
		this.sellerSendFlag = sellerSendFlag;
	}
	public String getSellerSendMethod() {
		return sellerSendMethod;
	}
	public void setSellerSendMethod(String sellerSendMethod) {
		this.sellerSendMethod = sellerSendMethod;
	}
	public String getSellerEmailAddress() {
		return sellerEmailAddress;
	}
	public void setSellerEmailAddress(String sellerEmailAddress) {
		this.sellerEmailAddress = sellerEmailAddress;
	}
	public String getSellerEmailTemplate() {
		return sellerEmailTemplate;
	}
	public void setSellerEmailTemplate(String sellerEmailTemplate) {
		this.sellerEmailTemplate = sellerEmailTemplate;
	}
	public String getSellerLanguage() {
		return sellerLanguage;
	}
	public void setSellerLanguage(String sellerLanguage) {
		this.sellerLanguage = sellerLanguage;
	}
	public String getSellerAttachFile() {
		return sellerAttachFile;
	}
	public void setSellerAttachFile(String sellerAttachFile) {
		this.sellerAttachFile = sellerAttachFile;
	}
	public String getCustomerResendSendMethod() {
		return customerResendSendMethod;
	}
	public void setCustomerResendSendMethod(String customerResendSendMethod) {
		this.customerResendSendMethod = customerResendSendMethod;
	}
	public String getCustomerResendEmailAddress() {
		return customerResendEmailAddress;
	}
	public void setCustomerResendEmailAddress(String customerResendEmailAddress) {
		this.customerResendEmailAddress = customerResendEmailAddress;
	}
	public String getCustomerResendEmailTemplate() {
		return customerResendEmailTemplate;
	}
	public void setCustomerResendEmailTemplate(String customerResendEmailTemplate) {
		this.customerResendEmailTemplate = customerResendEmailTemplate;
	}
	public Date getCustomerResendDate() {
		return customerResendDate;
	}
	public void setCustomerResendDate(Date customerResendDate) {
		this.customerResendDate = customerResendDate;
	}
	public String getCustomerResendBy() {
		return customerResendBy;
	}
	public void setCustomerResendBy(String customerResendBy) {
		this.customerResendBy = customerResendBy;
	}
	public int getCustomerResendCount() {
		return customerResendCount;
	}
	public void setCustomerResendCount(int customerResendCount) {
		this.customerResendCount = customerResendCount;
	}
}
