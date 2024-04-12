package com.eaf.orig.ulo.model.comparesignature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompareSignatureDataM implements Serializable,Cloneable {
	public CompareSignatureDataM(){
		super();
	}
	
	private String currentSetID;
	private List<String> currentDocTypeCodes = new ArrayList<String>();
	private String oldSetID;
	private String oldDocTypeCode;
	private String tokenID;
	private String compareSignatureFlag;
	private String url;
	private String defaultAppformPage;
	
	public String getCurrentSetID() {
		return currentSetID;
	}
	public void setCurrentSetID(String currentSetID) {
		this.currentSetID = currentSetID;
	}	 
	public List<String> getCurrentDocTypeCodes() {
		return currentDocTypeCodes;
	}
	public void setCurrentDocTypeCodes(List<String> currentDocTypeCodes) {
		this.currentDocTypeCodes = currentDocTypeCodes;
	}
	public String getOldSetID() {
		return oldSetID;
	}
	public void setOldSetID(String oldSetID) {
		this.oldSetID = oldSetID;
	}

	public String getOldDocTypeCode() {
		return oldDocTypeCode;
	}
	public void setOldDocTypeCode(String oldDocTypeCode) {
		this.oldDocTypeCode = oldDocTypeCode;
	}
	public String getTokenID() {
		return tokenID;
	}
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	public String getCompareSignatureFlag() {
		return compareSignatureFlag;
	}
	public void setCompareSignatureFlag(String compareSignatureFlag) {
		this.compareSignatureFlag = compareSignatureFlag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
	public String getDefaultAppformPage() {
		return defaultAppformPage;
	}
	public void setDefaultAppformPage(String defaultAppformPage) {
		this.defaultAppformPage = defaultAppformPage;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[currentSetID=");
		builder.append(currentSetID);
		builder.append(", currentDocTypeCodes=");
		builder.append(currentDocTypeCodes);
		builder.append(", oldSetID=");
		builder.append(oldSetID);
		builder.append(", oldDocTypeCode=");
		builder.append(oldDocTypeCode);
		builder.append(", tokenID=");
		builder.append(tokenID);
		builder.append(", compareSignatureFlag=");
		builder.append(compareSignatureFlag);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}
}
