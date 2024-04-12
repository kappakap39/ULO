package com.eaf.inf.batch.ulo.notification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmailBranchSummaryDataM implements Serializable,Cloneable{
	public EmailBranchSummaryDataM(){
		super();
	}

	private String applicationNo;
	private String branchName;
	private String customerFullName;
	private String productName;
	private String sellerId;
	private String recommenderId;
	private String finalDecision;
	private String rejectReason;
	private List<String> notCompleteDocs = new ArrayList<String>();
	private String customerPhoneNo;
	private String  poEmail;
	private String  poPhoneNo;

	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getCustomerFullName() {
		return customerFullName;
	}
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getRecommenderId() {
		return recommenderId;
	}
	public void setRecommenderId(String recommenderId) {
		this.recommenderId = recommenderId;
	}
	public String getFinalDecision() {
		return finalDecision;
	}
	public void setFinalDecision(String finalDecision) {
		this.finalDecision = finalDecision;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getPoEmail() {
		return poEmail;
	}
	public void setPoEmail(String poEmail) {
		this.poEmail = poEmail;
	}
	public String getPoPhoneNo() {
		return poPhoneNo;
	}
	public void setPoPhoneNo(String poPhoneNo) {
		this.poPhoneNo = poPhoneNo;
	}
	public List<String> getNotCompleteDocs() {
		return notCompleteDocs;
	}
	public String getNotCompleteDocText(){
		String comma = "";
		StringBuilder text = new StringBuilder("");
		if(null!=notCompleteDocs){
			for (String documentName : notCompleteDocs) {
				text.append(comma+documentName);
				comma = ",";
			}
		}
		return text.toString();
	}
	public void setNotCompleteDocs(List<String> notCompleteDocs) {
		this.notCompleteDocs = notCompleteDocs;
	}
	public String getCustomerPhoneNo() {
		return customerPhoneNo;
	}
	public void setCustomerPhoneNo(String customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailBranchSummaryDataM [");
		if (applicationNo != null) {
			builder.append("applicationNo=");
			builder.append(applicationNo);
			builder.append(", ");
		}
		if (branchName != null) {
			builder.append("branchName=");
			builder.append(branchName);
			builder.append(", ");
		}
		if (customerFullName != null) {
			builder.append("customerFullName=");
			builder.append(customerFullName);
			builder.append(", ");
		}
		if (productName != null) {
			builder.append("productName=");
			builder.append(productName);
			builder.append(", ");
		}
		if (sellerId != null) {
			builder.append("sellerId=");
			builder.append(sellerId);
			builder.append(", ");
		}
		if (recommenderId != null) {
			builder.append("recommenderId=");
			builder.append(recommenderId);
			builder.append(", ");
		}
		if (finalDecision != null) {
			builder.append("finalDecision=");
			builder.append(finalDecision);
			builder.append(", ");
		}
		if (rejectReason != null) {
			builder.append("rejectReason=");
			builder.append(rejectReason);
			builder.append(", ");
		}
		if (notCompleteDocs != null) {
			builder.append("notCompleteDocs=");
			builder.append(notCompleteDocs);
			builder.append(", ");
		}
		if (customerPhoneNo != null) {
			builder.append("customerPhoneNo=");
			builder.append(customerPhoneNo);
			builder.append(", ");
		}
		if (poEmail != null) {
			builder.append("poEmail=");
			builder.append(poEmail);
			builder.append(", ");
		}
		if (poPhoneNo != null) {
			builder.append("poPhoneNo=");
			builder.append(poPhoneNo);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
