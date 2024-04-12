package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectLetterProcessDataM implements Serializable, Cloneable{
	public RejectLetterProcessDataM(){
		super();
	}
	private String product;
	private String language = RejectLetterUtil.TH;
	private String templateCode;
	private String applyType;
	private String minRankReasonCode;
	private String applicationGroupId;
	private int lifeCycle;
	private boolean isPaper;
	private boolean isEmail;
	private boolean isSendSellerNoCust;
	private boolean isEmailAllAfp;
	private String sendTo;
	private ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes;
	 
	public String getProduct(){
		return product;
	}
	public void setProduct(String product){
		this.product = product;
	}
	public String getApplyType(){
		return applyType;
	}
	public void setApplyType(String applyType){
		this.applyType = applyType;
	}
	public ArrayList<TemplateReasonCodeDetailDataM> getTemplateReasonCodes(){
		return templateReasonCodes;
	}
	public void setTemplateReasonCodes(
			ArrayList<TemplateReasonCodeDetailDataM> templateReasonCodes){
		this.templateReasonCodes = templateReasonCodes;
	}
	public String getLanguage(){
		return language;
	}
	public void setLanguage(String language){
		this.language = language;
	}
	public String getTemplateCode(){
		return templateCode;
	}
	public void setTemplateCode(String templateCode){
		this.templateCode = templateCode;
	}
	public String getMinRankReasonCode(){
		return minRankReasonCode;
	}
	public void setMinRankReasonCode(String minRankReasonCode){
		this.minRankReasonCode = minRankReasonCode;
	}
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	} 
	
	public ArrayList<String> getPerosnalIds(){
		if(null!=templateReasonCodes){
			ArrayList<String> personalIds = new ArrayList<String>();
			for(TemplateReasonCodeDetailDataM templateCode :templateReasonCodes){
				if(!personalIds.contains(templateCode.getPersonalId())){
					personalIds.add(templateCode.getPersonalId());
				}				
			}
			return personalIds;
		}
		return null;
	}
	public ArrayList<String> getBusinessClassIds(){
		if(null!=templateReasonCodes){
			ArrayList<String> businessClassIds = new ArrayList<String>();
			for(TemplateReasonCodeDetailDataM templateCode :templateReasonCodes){
				if(!businessClassIds.contains(templateCode.getBusinessClassId())){
					businessClassIds.add(templateCode.getBusinessClassId());
				}				
			}
			return businessClassIds;
		}
		return null;
	}
	
	public ArrayList<String> getCardCodes(){
		if(null!=templateReasonCodes){
			ArrayList<String> cardTypes = new ArrayList<String>();
			for(TemplateReasonCodeDetailDataM templateCode :templateReasonCodes){
				if(!cardTypes.contains(templateCode.getCardCode())){
					cardTypes.add(templateCode.getCardCode());
				}				
			}
			return cardTypes;
		}
		return null;
	}

	public boolean isPaper(){
		return isPaper;
	}
	public void setPaper(boolean isPaper){
		this.isPaper = isPaper;
	}
	public boolean isEmail(){
		return isEmail;
	}
	public void setEmail(boolean isEmail){
		this.isEmail = isEmail;
	}
	public boolean isSendSellerNoCust() {
		return isSendSellerNoCust;
	}
	public void setSendSellerNoCust(boolean isSendSellerNoCust) {
		this.isSendSellerNoCust = isSendSellerNoCust;
	}
	public int getLifeCycle(){
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle){
		this.lifeCycle = lifeCycle;
	}
	public String getSendTo(){
		return sendTo;
	}
	public void setSendTo(String sendTo){
		this.sendTo = sendTo;
	}
	public boolean isEmailAllAfp() {
		return isEmailAllAfp;
	}
	public void setEmailAllAfp(boolean isEmailAllAfp) {
		this.isEmailAllAfp = isEmailAllAfp;
	}	
}
