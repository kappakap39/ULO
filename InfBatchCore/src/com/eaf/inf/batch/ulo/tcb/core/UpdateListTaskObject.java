package com.eaf.inf.batch.ulo.tcb.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

@SuppressWarnings("serial")
public class UpdateListTaskObject extends TaskObjectDataM {
	private String applicationGroupNo;
	private String	cisNo;
	private Timestamp approveDate;
	private String claimId;
	private String loanId;
	private String product;
	private String subProduct;
	private String marketCode;
	private BigDecimal limit;
	private BigDecimal term;
	private Timestamp openDate;
	private String accountNo;
	private String mainCisNo;
	private String status;
	
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public Timestamp getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Timestamp approveDate) {
		this.approveDate = approveDate;
	}
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSubProduct() {
		return subProduct;
	}
	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}
	public String getMarketCode() {
		return marketCode;
	}
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	public Timestamp getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getMainCisNo() {
		return mainCisNo;
	}
	public void setMainCisNo(String mainCisNo) {
		this.mainCisNo = mainCisNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toString() {
		return "{cisNo="+cisNo+",approveDate="+approveDate+",loanId="+loanId+",claimId="+claimId+",product="+product+",subproduct="+subProduct+",marketcode="+marketCode+",accountNo="+accountNo+",openDate="+openDate+",status="+status+",maincis="+mainCisNo+"}";
	}
	public BigDecimal getLimit() {
		return limit;
	}
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
	public BigDecimal getTerm() {
		return term;
	}
	public void setTerm(BigDecimal term) {
		this.term = term;
	}
	public String getApplicationGroupNo() {
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo) {
		this.applicationGroupNo = applicationGroupNo;
	}
}
