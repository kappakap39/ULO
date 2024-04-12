package com.eaf.service.module.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class MLSCardSetupResultResponse 
{
	header header;
	
	public MLSCardSetupResultResponse()
	{
		header header = new header();
		this.setHeader(header);
	}

	public header getHeader() {
		return header;
	}

	public void setHeader(header header) {
		this.header = header;
	}

	public class header
	{
		//Head
		
		@JsonProperty("messageUid")
		String messageUid;
		
		@JsonProperty("messageDt")
		String messageDt;
		
		@JsonProperty("respCode")
		String respCode;
		
		@JsonProperty("respDesc")
		String respDesc;
		
		@JsonProperty("reqMessageUid")
		String reqMessageUid;
		
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

		public String getRespCode() {
			return respCode;
		}

		public void setRespCode(String respCode) {
			this.respCode = respCode;
		}

		public String getRespDesc() {
			return respDesc;
		}

		public void setRespDesc(String respDesc) {
			this.respDesc = respDesc;
		}

		public String getReqMessageUid() {
			return reqMessageUid;
		}

		public void setReqMessageUid(String reqMessageUid) {
			this.reqMessageUid = reqMessageUid;
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"header\":{\"messageUid\":\"");
		builder.append(this.getHeader().getMessageUid()+"\"");
		builder.append(", \"messageDt\":\"");
		builder.append(this.getHeader().getMessageDt()+"\"");
		builder.append(", \"respCode\":\"");
		builder.append(this.getHeader().getRespCode()+"\"");
		builder.append(", \"respDesc\":\"");
		builder.append(this.getHeader().getRespDesc()+"\"");
		builder.append(", \"reqMessageUid\":\"");
		builder.append(this.getHeader().getReqMessageUid()+"\"");
		builder.append("}}");
		return builder.toString();
	}
}
