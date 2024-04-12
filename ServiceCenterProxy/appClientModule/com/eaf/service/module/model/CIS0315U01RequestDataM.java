package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CIS0315U01RequestDataM implements Serializable,Cloneable{
	private String customerId;
	private String soucreAssetCode;
	private String soucreAssetOtherDescription;
	private String policalPositionDescription;
	private BigDecimal assetValueAmount;
	private String assetValueCode;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getPolicalPositionDescription() {
		return policalPositionDescription;
	}
	public void setPolicalPositionDescription(String policalPositionDescription) {
		this.policalPositionDescription = policalPositionDescription;
	}
	public BigDecimal getAssetValueAmount() {
		return assetValueAmount;
	}
	public void setAssetValueAmount(BigDecimal assetValueAmount) {
		this.assetValueAmount = assetValueAmount;
	}
	public String getAssetValueCode() {
		return assetValueCode;
	}
	public void setAssetValueCode(String assetValueCode) {
		this.assetValueCode = assetValueCode;
	}
	
}
