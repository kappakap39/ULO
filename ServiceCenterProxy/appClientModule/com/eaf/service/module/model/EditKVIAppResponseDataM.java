package com.eaf.service.module.model;

import java.io.Serializable;

public class EditKVIAppResponseDataM implements Serializable,Cloneable{
	private String fId;
	private String tokenId;
	
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
	
}
