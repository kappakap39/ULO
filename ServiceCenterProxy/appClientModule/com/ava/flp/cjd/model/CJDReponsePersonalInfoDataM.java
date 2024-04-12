package com.ava.flp.cjd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CJDReponsePersonalInfoDataM implements Serializable, Cloneable{
	public CJDReponsePersonalInfoDataM(){
		super();
	}
	private String cisNo;
	private String idNo;
	private String thTitleCode;
	private String thFirstName;
	private String thMidName;
	private String thLastName;
	private String enTitleCode;
	private String enFirstName;
	private String enLastName;
	private String enMidName;
	private ArrayList<CJDReponseDocumentSuggestionDataM> documentSuggestions;
	private ArrayList<CJDReponseIncomeInfoDataM> incomeInfos;
	private CJDReponseVerificationResultDataM verificationResult;
	private String verifiedIncomeSource;
	private BigDecimal totalVerifiedIncome;
	
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getThTitleCode() {
		return thTitleCode;
	}
	public void setThTitleCode(String thTitleCode) {
		this.thTitleCode = thTitleCode;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThMidName() {
		return thMidName;
	}
	public void setThMidName(String thMidName) {
		this.thMidName = thMidName;
	}
	public String getEnTitleCode() {
		return enTitleCode;
	}
	public void setEnTitleCode(String enTitleCode) {
		this.enTitleCode = enTitleCode;
	}
	public String getEnFirstName() {
		return enFirstName;
	}
	public void setEnFirstName(String enFirstName) {
		this.enFirstName = enFirstName;
	}
	public String getEnLastName() {
		return enLastName;
	}
	public void setEnLastName(String enLastName) {
		this.enLastName = enLastName;
	}
	public String getEnMidName() {
		return enMidName;
	}
	public void setEnMidName(String enMidName) {
		this.enMidName = enMidName;
	}
	public ArrayList<CJDReponseDocumentSuggestionDataM> getDocumentSuggestions() {
		return documentSuggestions;
	}
	public void setDocumentSuggestions(
			ArrayList<CJDReponseDocumentSuggestionDataM> documentSuggestions) {
		this.documentSuggestions = documentSuggestions;
	}
	public ArrayList<CJDReponseIncomeInfoDataM> getIncomeInfos() {
		return incomeInfos;
	}
	public void setIncomeInfos(ArrayList<CJDReponseIncomeInfoDataM> incomeInfos) {
		this.incomeInfos = incomeInfos;
	}
	public CJDReponseVerificationResultDataM getVerificationResult() {
		return verificationResult;
	}
	public void setVerificationResult(
			CJDReponseVerificationResultDataM verificationResult) {
		this.verificationResult = verificationResult;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
	public String getVerifiedIncomeSource() {
		return verifiedIncomeSource;
	}
	public void setVerifiedIncomeSource(String verifiedIncomeSource) {
		this.verifiedIncomeSource = verifiedIncomeSource;
	}
	public BigDecimal getTotalVerifiedIncome() {
		return totalVerifiedIncome;
	}
	public void setTotalVerifiedIncome(BigDecimal totalVerifiedIncome) {
		this.totalVerifiedIncome = totalVerifiedIncome;
	}
	
}
