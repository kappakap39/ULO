package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CIS0314I01ResponseDataM implements Serializable,Cloneable{
	private String customerType;
	private String customerId;
	private String thaiTitleName;
	private String thaiName;
	private String thaiSurname;
	private String englishTitleName;
	private String englishName;
	private String englishSurname;
	private String soucreAssetCode;
	private String soucreAssetOtherDescription;
	private String policalPostionDescription;
	private BigDecimal assetValueAmount;
	private String riskReasonCode;
	private String assetValueCode;
	private String assetValueDescription;
	private String riskLevelCode;
	
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getThaiTitleName() {
		return thaiTitleName;
	}
	public void setThaiTitleName(String thaiTitleName) {
		this.thaiTitleName = thaiTitleName;
	}
	public String getThaiName() {
		return thaiName;
	}
	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}
	public String getThaiSurname() {
		return thaiSurname;
	}
	public void setThaiSurname(String thaiSurname) {
		this.thaiSurname = thaiSurname;
	}
	public String getEnglishTitleName() {
		return englishTitleName;
	}
	public void setEnglishTitleName(String englishTitleName) {
		this.englishTitleName = englishTitleName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getEnglishSurname() {
		return englishSurname;
	}
	public void setEnglishSurname(String englishSurname) {
		this.englishSurname = englishSurname;
	}
	public String getSoucreAssetCode() {
		return soucreAssetCode;
	}
	public void setSoucreAssetCode(String soucreAssetCode) {
		this.soucreAssetCode = soucreAssetCode;
	}
	public String getSoucreAssetOtherDescription() {
		return soucreAssetOtherDescription;
	}
	public void setSoucreAssetOtherDescription(String soucreAssetOtherDescription) {
		this.soucreAssetOtherDescription = soucreAssetOtherDescription;
	}
	public String getPolicalPostionDescription() {
		return policalPostionDescription;
	}
	public void setPolicalPostionDescription(String policalPostionDescription) {
		this.policalPostionDescription = policalPostionDescription;
	}
	public BigDecimal getAssetValueAmount() {
		return assetValueAmount;
	}
	public void setAssetValueAmount(BigDecimal assetValueAmount) {
		this.assetValueAmount = assetValueAmount;
	}
	public String getRiskReasonCode() {
		return riskReasonCode;
	}
	public void setRiskReasonCode(String riskReasonCode) {
		this.riskReasonCode = riskReasonCode;
	}
	public String getAssetValueCode() {
		return assetValueCode;
	}
	public void setAssetValueCode(String assetValueCode) {
		this.assetValueCode = assetValueCode;
	}
	public String getAssetValueDescription() {
		return assetValueDescription;
	}
	public void setAssetValueDescription(String assetValueDescription) {
		this.assetValueDescription = assetValueDescription;
	}
	public String getRiskLevelCode() {
		return riskLevelCode;
	}
	public void setRiskLevelCode(String riskLevelCode) {
		this.riskLevelCode = riskLevelCode;
	}

}
