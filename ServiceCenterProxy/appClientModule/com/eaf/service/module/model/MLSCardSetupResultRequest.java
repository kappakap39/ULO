package com.eaf.service.module.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;

public class MLSCardSetupResultRequest 
{
	header header;
	body body;
	
	public MLSCardSetupResultRequest()
	{
		header header = new header();
		body body = new body();
		this.setHeader(header);
		this.setBody(body);
	}

	public header getHeader() {
		return header;
	}

	public void setHeader(header header) {
		this.header = header;
	}

	public body getBody() {
		return body;
	}

	public void setBody(body body) {
		this.body = body;
	}

	public class header
	{
		//Head
		@JsonProperty("appId")
		String appId;
		
		@JsonProperty("messageUid")
		String messageUid;
		
		@JsonProperty("messageDt")
		String messageDt;
		
		@JsonProperty("appUser")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String appUser;
		
		@JsonProperty("appPwd")
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String appPwd;
		
		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getMessageUid() {
			return messageUid;
		}

		public void setMessageUid(String messageUid) {
			this.messageUid = messageUid;
		}

		public String getMessageDt() {
			return messageDt;
		}

		public void setMessageDt(String messageDt) {
			this.messageDt = messageDt;
		}

		public String getAppUser() {
			return appUser;
		}

		public void setAppUser(String appUser) {
			this.appUser = appUser;
		}

		public String getAppPwd() {
			return appPwd;
		}

		public void setAppPwd(String appPwd) {
			this.appPwd = appPwd;
		}
	}
	
	public class body
	{
		//Body
		@JsonProperty("contractNo")
		String contractNo;
		
		@JsonProperty("result")
		String result;
		
		@JsonProperty("cardNo")
		String cardNo;
		
		@JsonProperty("cardNoMask")
		String cardNoMask;
		
		@JsonProperty("remark")
		String remark;
		
		@JsonProperty("cardLinkCustNo")
		String cardLinkCustNo;
		
		public String getContractNo() {
			return contractNo;
		}
	
		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}
	
		public String getResult() {
			return result;
		}
	
		public void setResult(String result) {
			this.result = result;
		}
	
		public String getCardNo() {
			return cardNo;
		}
	
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
	
		public String getCardNoMask() {
			return cardNoMask;
		}
	
		public void setCardNoMask(String cardNoMask) {
			this.cardNoMask = cardNoMask;
		}
	
		public String getRemark() {
			return remark;
		}
	
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
		public String getCardLinkCustNo() {
			return cardLinkCustNo;
		}

		public void setCardLinkCustNo(String cardLinkCustNo) {
			this.cardLinkCustNo = cardLinkCustNo;
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"header\":{\"appId\":\"");
		builder.append(this.getHeader().getAppId()+"\"");
		builder.append(", \"messageUid\":\"");
		builder.append(this.getHeader().getMessageUid()+"\"");
		builder.append(", \"messageDt\":\"");
		builder.append(this.getHeader().getMessageDt()+"\"");
		builder.append(", \"appUser\":\"");
		builder.append(this.getHeader().getAppUser()+"\"");
		builder.append(", \"appPwd\":\"");
		builder.append(this.getHeader().getAppPwd()+"\"");
		builder.append("},");
		builder.append("\"body\":{\"contractNo\":\"");
		builder.append(this.getBody().getContractNo()+"\"");
		builder.append(", \"result\":\"");
		builder.append(this.getBody().getResult()+"\"");
		builder.append(", \"cardNo\":\"");
		builder.append(this.getBody().getCardNo()+"\"");
		builder.append(", \"cardNoMask\":\"");
		builder.append(this.getBody().getCardNoMask()+"\"");
		builder.append(", \"remark\":\"");
		builder.append(this.getBody().getRemark()+"\"");
		builder.append(", \"cardLinkCustNo\":\"");
		builder.append(this.getBody().getCardLinkCustNo()+"\"");
		builder.append("}}");
		return builder.toString();
	}
}
