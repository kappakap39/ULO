package com.eaf.service.module.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CBS1215I01ResponseDataM implements Serializable,Cloneable{
	public CBS1215I01ResponseDataM(){
		super();
	}
	private String accountId;
	private String domicileBranchId;
	private BigDecimal totalRetentionAmount;
	private ArrayList<CBSRetentionDataM> cbsRetentions;
	private String moreDataInd;
	private int numberRecord;
	private int totalRecord;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getDomicileBranchId() {
		return domicileBranchId;
	}
	public void setDomicileBranchId(String domicileBranchId) {
		this.domicileBranchId = domicileBranchId;
	}
	public BigDecimal getTotalRetentionAmount() {
		return totalRetentionAmount;
	}
	public void setTotalRententionAmount(BigDecimal totalRetentionAmount) {
		this.totalRetentionAmount = totalRetentionAmount;
	}
	public ArrayList<CBSRetentionDataM> getCbsRetentions() {
		return cbsRetentions;
	}
	public void setCbsRententions(ArrayList<CBSRetentionDataM> cbsRetentions) {
		this.cbsRetentions = cbsRetentions;
	}
	public String getMoreDataInd() {
		return moreDataInd;
	}
	public void setMoreDataInd(String moreDataInd) {
		this.moreDataInd = moreDataInd;
	}
	public int getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(int numberRecord) {
		this.numberRecord = numberRecord;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
}
