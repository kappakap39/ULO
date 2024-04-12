package com.eaf.orig.ulo.model.cardMaintenance;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class CardMaintenanceDetailDataM implements Serializable, Cloneable {
	public CardMaintenanceDetailDataM() {
		
	}
	private String applyType;
	private String applicationType;
	private String cardNoMark;
	private String cardNo;
	private String cardDesc;
	private BigDecimal loanAmt;
	private String mainCardNo1;
	private String mainCardNo2;
	private String mainCardNo3;
	private String supCardNo1;
	private String supCardNo2;
	private String supCardNo3;
	private String errorCardDesc1;
	private String errorSupCardDesc1;
	private String errorCardDesc2;
	private String errorSupCardDesc2;
	private String errorCardDesc3;
	private String errorSupCardDesc3;
	private String cardType;
	private String cardLevel;
	private String applicationRecordId;
	private String cardStatus;
	private String cardId;

	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getCardNoMark() {
		return cardNoMark;
	}
	public void setCardNoMark(String cardNoMark) {
		this.cardNoMark = cardNoMark;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardDesc() {
		return cardDesc;
	}
	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}
	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	public String getMainCardNo1() {
		return mainCardNo1;
	}
	public void setMainCardNo1(String mainCardNo1) {
		this.mainCardNo1 = mainCardNo1;
	}
	public String getMainCardNo2() {
		return mainCardNo2;
	}
	public void setMainCardNo2(String mainCardNo2) {
		this.mainCardNo2 = mainCardNo2;
	}
	public String getMainCardNo3() {
		return mainCardNo3;
	}
	public void setMainCardNo3(String mainCardNo3) {
		this.mainCardNo3 = mainCardNo3;
	}
	public String getSupCardNo1() {
		return supCardNo1;
	}
	public void setSupCardNo1(String supCardNo1) {
		this.supCardNo1 = supCardNo1;
	}
	public String getSupCardNo2() {
		return supCardNo2;
	}
	public void setSupCardNo2(String supCardNo2) {
		this.supCardNo2 = supCardNo2;
	}
	public String getSupCardNo3() {
		return supCardNo3;
	}
	public void setSupCardNo3(String supCardNo3) {
		this.supCardNo3 = supCardNo3;
	}
	public String getErrorCardDesc1() {
		return errorCardDesc1;
	}
	public void setErrorCardDesc1(String errorCardDesc1) {
		this.errorCardDesc1 = errorCardDesc1;
	}
	public String getErrorSupCardDesc1() {
		return errorSupCardDesc1;
	}
	public void setErrorSupCardDesc1(String errorSupCardDesc1) {
		this.errorSupCardDesc1 = errorSupCardDesc1;
	}
	public String getErrorCardDesc2() {
		return errorCardDesc2;
	}
	public void setErrorCardDesc2(String errorCardDesc2) {
		this.errorCardDesc2 = errorCardDesc2;
	}
	public String getErrorSupCardDesc2() {
		return errorSupCardDesc2;
	}
	public void setErrorSupCardDesc2(String errorSupCardDesc2) {
		this.errorSupCardDesc2 = errorSupCardDesc2;
	}
	public String getErrorCardDesc3() {
		return errorCardDesc3;
	}
	public void setErrorCardDesc3(String errorCardDesc3) {
		this.errorCardDesc3 = errorCardDesc3;
	}
	public String getErrorSupCardDesc3() {
		return errorSupCardDesc3;
	}
	public void setErrorSupCardDesc3(String errorSupCardDesc3) {
		this.errorSupCardDesc3 = errorSupCardDesc3;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardLevel() {
		return cardLevel;
	}
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
	}
	public String getApplicationRecordId() {
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId) {
		this.applicationRecordId = applicationRecordId;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}
