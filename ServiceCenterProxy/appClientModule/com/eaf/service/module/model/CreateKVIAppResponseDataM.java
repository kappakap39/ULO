package com.eaf.service.module.model;

import java.io.Serializable;

public class CreateKVIAppResponseDataM implements Serializable,Cloneable{
	private String fId;
	private String tokenId;
	private String fgAppNo;
	
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getFgAppNo() {
		return fgAppNo;
	}
	public void setFgAppNo(String fgAppNo) {
		this.fgAppNo = fgAppNo;
	}
	
}
