package com.kbank.eappu.model;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

/**
 * A data model of message.
 * @author Pinyo.L 
 **/
public class MessageData {
	@Expose
	private String campaignCode;
	@Expose
	private String score;
	@Expose
	private String customerIndicator;
	@Expose
	private String customer;
	@Expose
	private String hashtag;
	@Expose
	private String contentCategory;
	@Expose
	private Integer authType;
	@Expose
	private String command;
	@Expose
	private JsonObject parameter;
	@Expose
	private String actionExpireDate;
	@Expose
	private String trackingID;
//	@Expose
//	private String feedReferenceID;
	@Expose
	private Integer imodeFlag;
	@Expose
	private Integer idingFlag;
	@Expose
	private Integer landingFlag;
	@Expose
	private Integer iotherType;
	@Expose
	private String imodeImgTH;
	@Expose
	private String imodeImgEN;
	@Expose
	private String imodeBadgeTH;
	@Expose
	private String imodeBadgeEN;
	@Expose
	private String imodeMsgTopicTH;
	@Expose
	private String imodeMsgTopicEN;
	@Expose
	private String imodeMsgBodyTH;
	@Expose
	private String imodeMsgBodyEN;
	@Expose
	private String imodeMsgHighLightTH;
	@Expose
	private String imodeMsgHighLightEN;
	@Expose
	private String imodeMsgFooterTH;
	@Expose
	private String imodeMsgFooterEN;
	@Expose
	private String idingNotiImgTH;
	@Expose
	private String idingNotiImgEN;
	@Expose
	private String idingNotiMsgTopicTH;
	@Expose
	private String idingNotiMsgTopicEN;
	@Expose
	private String idingNotiMsgBodyTH;
	@Expose
	private String idingNotiMsgBodyEN;
	@Expose
	private String idingImgTH;
	@Expose
	private String idingImgEN;
	@Expose
	private String idingMsgTopicTH;
	@Expose
	private String idingMsgTopicEN;
	@Expose
	private String idingMsgBodyTH;
	@Expose
	private String idingMsgBodyEN;
	@Expose
	private String idingMsgHighLightTH;
	@Expose
	private String idingMsgHighLightEN;
	@Expose
	private String idingMsgFooterTH;
	@Expose
	private String idingMsgFooterEN;
	@Expose
	private List<String> landingPageImgTH;
	@Expose
	private List<String>landingPageImgEN;
	@Expose
	private String landingPageAuthorTH;
	@Expose
	private String landingPageAuthorEN;
	@Expose
	private String landingPageAuthorIconTH;
	@Expose
	private String landingPageAuthorIconEN;
	@Expose
	private String landingPageMsgTopicTH;
	@Expose
	private String landingPageMsgTopicEN;
	@Expose
	private JsonObject landingPageMsgBodyTH;
	@Expose
	private JsonObject landingPageMsgBodyEN;
	@Expose
	private String landingPageTemplateID;
	@Expose
	private String landingPageButtonLabelSet;
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCustomerIndicator() {
		return customerIndicator;
	}
	public void setCustomerIndicator(String customerIndicator) {
		this.customerIndicator = customerIndicator;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public String getContentCategory() {
		return contentCategory;
	}
	public void setContentCategory(String contentCategory) {
		this.contentCategory = contentCategory;
	}
	public Integer getAuthType() {
		return authType;
	}
	public void setAuthType(Integer authType) {
		this.authType = authType;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public JsonObject getParameter() {
		return parameter;
	}
	public void setParameter(JsonObject parameter) {
		this.parameter = parameter;
	}
	public String getActionExpireDate() {
		return actionExpireDate;
	}
	public void setActionExpireDate(String actionExpireDate) {
		this.actionExpireDate = actionExpireDate;
	}
	public String getTrackingID() {
		return trackingID;
	}
	public void setTrackingID(String trackingID) {
		this.trackingID = trackingID;
	}
//	public String getFeedReferenceID() {
//		return feedReferenceID;
//	}
//	public void setFeedReferenceID(String feedReferenceID) {
//		this.feedReferenceID = feedReferenceID;
//	}
	public Integer getImodeFlag() {
		return imodeFlag;
	}
	public void setImodeFlag(Integer imodeFlag) {
		this.imodeFlag = imodeFlag;
	}
	public Integer getIdingFlag() {
		return idingFlag;
	}
	public void setIdingFlag(Integer idingFlag) {
		this.idingFlag = idingFlag;
	}
	public Integer getLandingFlag() {
		return landingFlag;
	}
	public void setLandingFlag(Integer landingFlag) {
		this.landingFlag = landingFlag;
	}
	public Integer getIotherType() {
		return iotherType;
	}
	public void setIotherType(Integer iotherType) {
		this.iotherType = iotherType;
	}
	public String getImodeImgTH() {
		return imodeImgTH;
	}
	public void setImodeImgTH(String imodeImgTH) {
		this.imodeImgTH = imodeImgTH;
	}
	public String getImodeImgEN() {
		return imodeImgEN;
	}
	public void setImodeImgEN(String imodeImgEN) {
		this.imodeImgEN = imodeImgEN;
	}
	public String getImodeBadgeTH() {
		return imodeBadgeTH;
	}
	public void setImodeBadgeTH(String imodeBadgeTH) {
		this.imodeBadgeTH = imodeBadgeTH;
	}
	public String getImodeBadgeEN() {
		return imodeBadgeEN;
	}
	public void setImodeBadgeEN(String imodeBadgeEN) {
		this.imodeBadgeEN = imodeBadgeEN;
	}
	public String getImodeMsgTopicTH() {
		return imodeMsgTopicTH;
	}
	public void setImodeMsgTopicTH(String imodeMsgTopicTH) {
		this.imodeMsgTopicTH = imodeMsgTopicTH;
	}
	public String getImodeMsgTopicEN() {
		return imodeMsgTopicEN;
	}
	public void setImodeMsgTopicEN(String imodeMsgTopicEN) {
		this.imodeMsgTopicEN = imodeMsgTopicEN;
	}
	public String getImodeMsgBodyTH() {
		return imodeMsgBodyTH;
	}
	public void setImodeMsgBodyTH(String imodeMsgBodyTH) {
		this.imodeMsgBodyTH = imodeMsgBodyTH;
	}
	public String getImodeMsgBodyEN() {
		return imodeMsgBodyEN;
	}
	public void setImodeMsgBodyEN(String imodeMsgBodyEN) {
		this.imodeMsgBodyEN = imodeMsgBodyEN;
	}
	public String getImodeMsgHighLightTH() {
		return imodeMsgHighLightTH;
	}
	public void setImodeMsgHighLightTH(String imodeMsgHighLightTH) {
		this.imodeMsgHighLightTH = imodeMsgHighLightTH;
	}
	public String getImodeMsgHighLightEN() {
		return imodeMsgHighLightEN;
	}
	public void setImodeMsgHighLightEN(String imodeMsgHighLightEN) {
		this.imodeMsgHighLightEN = imodeMsgHighLightEN;
	}
	public String getImodeMsgFooterTH() {
		return imodeMsgFooterTH;
	}
	public void setImodeMsgFooterTH(String imodeMsgFooterTH) {
		this.imodeMsgFooterTH = imodeMsgFooterTH;
	}
	public String getImodeMsgFooterEN() {
		return imodeMsgFooterEN;
	}
	public void setImodeMsgFooterEN(String imodeMsgFooterEN) {
		this.imodeMsgFooterEN = imodeMsgFooterEN;
	}
	public String getIdingNotiImgTH() {
		return idingNotiImgTH;
	}
	public void setIdingNotiImgTH(String idingNotiImgTH) {
		this.idingNotiImgTH = idingNotiImgTH;
	}
	public String getIdingNotiImgEN() {
		return idingNotiImgEN;
	}
	public void setIdingNotiImgEN(String idingNotiImgEN) {
		this.idingNotiImgEN = idingNotiImgEN;
	}
	public String getIdingNotiMsgTopicTH() {
		return idingNotiMsgTopicTH;
	}
	public void setIdingNotiMsgTopicTH(String idingNotiMsgTopicTH) {
		this.idingNotiMsgTopicTH = idingNotiMsgTopicTH;
	}
	public String getIdingNotiMsgTopicEN() {
		return idingNotiMsgTopicEN;
	}
	public void setIdingNotiMsgTopicEN(String idingNotiMsgTopicEN) {
		this.idingNotiMsgTopicEN = idingNotiMsgTopicEN;
	}
	public String getIdingNotiMsgBodyTH() {
		return idingNotiMsgBodyTH;
	}
	public void setIdingNotiMsgBodyTH(String idingNotiMsgBodyTH) {
		this.idingNotiMsgBodyTH = idingNotiMsgBodyTH;
	}
	public String getIdingNotiMsgBodyEN() {
		return idingNotiMsgBodyEN;
	}
	public void setIdingNotiMsgBodyEN(String idingNotiMsgBodyEN) {
		this.idingNotiMsgBodyEN = idingNotiMsgBodyEN;
	}
	public String getIdingImgTH() {
		return idingImgTH;
	}
	public void setIdingImgTH(String idingImgTH) {
		this.idingImgTH = idingImgTH;
	}
	public String getIdingImgEN() {
		return idingImgEN;
	}
	public void setIdingImgEN(String idingImgEN) {
		this.idingImgEN = idingImgEN;
	}
	public String getIdingMsgTopicTH() {
		return idingMsgTopicTH;
	}
	public void setIdingMsgTopicTH(String idingMsgTopicTH) {
		this.idingMsgTopicTH = idingMsgTopicTH;
	}
	public String getIdingMsgTopicEN() {
		return idingMsgTopicEN;
	}
	public void setIdingMsgTopicEN(String idingMsgTopicEN) {
		this.idingMsgTopicEN = idingMsgTopicEN;
	}
	public String getIdingMsgBodyTH() {
		return idingMsgBodyTH;
	}
	public void setIdingMsgBodyTH(String idingMsgBodyTH) {
		this.idingMsgBodyTH = idingMsgBodyTH;
	}
	public String getIdingMsgBodyEN() {
		return idingMsgBodyEN;
	}
	public void setIdingMsgBodyEN(String idingMsgBodyEN) {
		this.idingMsgBodyEN = idingMsgBodyEN;
	}
	public String getIdingMsgHighLightTH() {
		return idingMsgHighLightTH;
	}
	public void setIdingMsgHighLightTH(String idingMsgHighLightTH) {
		this.idingMsgHighLightTH = idingMsgHighLightTH;
	}
	public String getIdingMsgHighLightEN() {
		return idingMsgHighLightEN;
	}
	public void setIdingMsgHighLightEN(String idingMsgHighLightEN) {
		this.idingMsgHighLightEN = idingMsgHighLightEN;
	}
	public String getIdingMsgFooterTH() {
		return idingMsgFooterTH;
	}
	public void setIdingMsgFooterTH(String idingMsgFooterTH) {
		this.idingMsgFooterTH = idingMsgFooterTH;
	}
	public String getIdingMsgFooterEN() {
		return idingMsgFooterEN;
	}
	public void setIdingMsgFooterEN(String idingMsgFooterEN) {
		this.idingMsgFooterEN = idingMsgFooterEN;
	}
	public List<String> getLandingPageImgTH() {
		return landingPageImgTH;
	}
	public void setLandingPageImgTH(List<String> landingPageImgTH) {
		this.landingPageImgTH = landingPageImgTH;
	}
	public List<String> getLandingPageImgEN() {
		return landingPageImgEN;
	}
	public void setLandingPageImgEN(List<String> landingPageImgEN) {
		this.landingPageImgEN = landingPageImgEN;
	}
	public String getLandingPageAuthorTH() {
		return landingPageAuthorTH;
	}
	public void setLandingPageAuthorTH(String landingPageAuthorTH) {
		this.landingPageAuthorTH = landingPageAuthorTH;
	}
	public String getLandingPageAuthorEN() {
		return landingPageAuthorEN;
	}
	public void setLandingPageAuthorEN(String landingPageAuthorEN) {
		this.landingPageAuthorEN = landingPageAuthorEN;
	}
	public String getLandingPageAuthorIconTH() {
		return landingPageAuthorIconTH;
	}
	public void setLandingPageAuthorIconTH(String landingPageAuthorIconTH) {
		this.landingPageAuthorIconTH = landingPageAuthorIconTH;
	}
	public String getLandingPageAuthorIconEN() {
		return landingPageAuthorIconEN;
	}
	public void setLandingPageAuthorIconEN(String landingPageAuthorIconEN) {
		this.landingPageAuthorIconEN = landingPageAuthorIconEN;
	}
	public String getLandingPageMsgTopicTH() {
		return landingPageMsgTopicTH;
	}
	public void setLandingPageMsgTopicTH(String landingPageMsgTopicTH) {
		this.landingPageMsgTopicTH = landingPageMsgTopicTH;
	}
	public String getLandingPageMsgTopicEN() {
		return landingPageMsgTopicEN;
	}
	public void setLandingPageMsgTopicEN(String landingPageMsgTopicEN) {
		this.landingPageMsgTopicEN = landingPageMsgTopicEN;
	}
	public JsonObject getLandingPageMsgBodyTH() {
		return landingPageMsgBodyTH;
	}
	public void setLandingPageMsgBodyTH(JsonObject landingPageMsgBodyTH) {
		this.landingPageMsgBodyTH = landingPageMsgBodyTH;
	}
	public JsonObject getLandingPageMsgBodyEN() {
		return landingPageMsgBodyEN;
	}
	public void setLandingPageMsgBodyEN(JsonObject landingPageMsgBodyEN) {
		this.landingPageMsgBodyEN = landingPageMsgBodyEN;
	}
	public String getLandingPageTemplateID() {
		return landingPageTemplateID;
	}
	public void setLandingPageTemplateID(String landingPageTemplateID) {
		this.landingPageTemplateID = landingPageTemplateID;
	}
	public String getLandingPageButtonLabelSet() {
		return landingPageButtonLabelSet;
	}
	public void setLandingPageButtonLabelSet(String landingPageButtonLabelSet) {
		this.landingPageButtonLabelSet = landingPageButtonLabelSet;
	}

	
}
