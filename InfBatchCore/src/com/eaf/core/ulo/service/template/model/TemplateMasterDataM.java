package com.eaf.core.ulo.service.template.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.eaf.core.ulo.common.display.FormatUtil;

@SuppressWarnings("serial")
public class TemplateMasterDataM implements Serializable,Cloneable{
	public TemplateMasterDataM(){
		super();
	}
	private String templateId;
	private String templateCode;
	private String templateName;
	private String templateType;
	private String templateBody;
	private String templateHeader;
	private String applyType;
	private String detail1;
	private String detail2;
	private String detail3;
	private String language=FormatUtil.TH;
	private String postScript1;
	private String postScript2;
	private String rejectLetterProduct;
	private ArrayList<String> products;
	private String remark1;
	private String rejectLetterTemplateType;
	private String templateBodyKmobile;
	private String contactPoint;
	private String productType;
	private boolean isGeneratePaperOnly;
	private boolean isSendSellerNoCust;
	private boolean isEmailAllAfp;
	private String templateBodyKmobileTh;
	private String templateBodyKmobileEn;
	private String alertMessageTh;
	private String alertMessageEn;
	
	public String getTemplateId(){
		return templateId;
	}
	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}
	public String getTemplateName(){
		return templateName;
	}
	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}
	public String getTemplateType(){
		return templateType;
	}
	public void setTemplateType(String templateType){
		this.templateType = templateType;
	}
	public String getTemplateBody(){
		return templateBody;
	}
	public void setTemplateBody(String templateBody){
		this.templateBody = templateBody;
	}
	public String getTemplateHeader(){
		return templateHeader;
	}
	public void setTemplateHeader(String templateHeader){
		this.templateHeader = templateHeader;
	}
	public String getTemplateCode(){
		return templateCode;
	}
	public void setTemplateCode(String templateCode){
		this.templateCode = templateCode;
	}
	public String getApplyType(){
		return applyType;
	}
	public void setApplyType(String applyType){
		this.applyType = applyType;
	}
	public String getDetail1(){
		return detail1;
	}
	public void setDetail1(String detail1){
		this.detail1 = detail1;
	}
	public String getDetail2(){
		return detail2;
	}
	public void setDetail2(String detail2){
		this.detail2 = detail2;
	}
	public String getDetail3(){
		return detail3;
	}
	public void setDetail3(String detail3){
		this.detail3 = detail3;
	}
	public String getLanguage(){
		return language;
	}
	public void setLanguage(String language){
		this.language = language;
	}
	public String getPostScript1(){
		return postScript1;
	}
	public void setPostScript1(String postScript1){
		this.postScript1 = postScript1;
	}
	public String getPostScript2(){
		return postScript2;
	}
	public void setPostScript2(String postScript2){
		this.postScript2 = postScript2;
	}
	public String getRejectLetterProduct(){
		return rejectLetterProduct;
	}
	public void setRejectLetterProduct(String rejectLetterProduct){
		this.rejectLetterProduct = rejectLetterProduct;
	}
	public String getRemark1(){
		return remark1;
	}
	public void setRemark1(String remark1){
		this.remark1 = remark1;
	}
	public String getRejectLetterTemplateType(){
		return rejectLetterTemplateType;
	}
	public void setRejectLetterTemplateType(String rejectLetterTemplateType){
		this.rejectLetterTemplateType = rejectLetterTemplateType;
	}
	public ArrayList<String> getProducts(){
		return products;
	}
	public void setProducts(ArrayList<String> products){
		this.products = products;
	}
	public String getTemplateBodyKmobile(){
		return templateBodyKmobile;
	}
	public void setTemplateBodyKmobile(String templateBodyKmobile){
		this.templateBodyKmobile = templateBodyKmobile;
	}
	public String getContactPoint(){
		return contactPoint;
	}
	public void setContactPoint(String contactPoint){
		this.contactPoint = contactPoint;
	}
	public String getProductType(){
		return productType;
	}
	public void setProductType(String productType){
		this.productType = productType;
	}
	public boolean isGeneratePaperOnly(){
		return isGeneratePaperOnly;
	}
	public void setGeneratePaperOnly(boolean isGeneratePaperOnly){
		this.isGeneratePaperOnly = isGeneratePaperOnly;
	}
	public boolean isSendSellerNoCust() {
		return isSendSellerNoCust;
	}
	public void setSendSellerNoCust(boolean isSendSellerNoCust) {
		this.isSendSellerNoCust = isSendSellerNoCust;
	}
	public boolean isEmailAllAfp() {
		return isEmailAllAfp;
	}
	public void setEmailAllAfp(boolean isEmailAllAfp) {
		this.isEmailAllAfp = isEmailAllAfp;
	}
	public String getTemplateBodyKmobileTh() {
		return templateBodyKmobileTh;
	}
	public void setTemplateBodyKmobileTh(String templateBodyKmobileTh) {
		this.templateBodyKmobileTh = templateBodyKmobileTh;
	}
	public String getTemplateBodyKmobileEn() {
		return templateBodyKmobileEn;
	}
	public void setTemplateBodyKmobileEn(String templateBodyKmobileEn) {
		this.templateBodyKmobileEn = templateBodyKmobileEn;
	}
	public String getAlertMessageTh() {
		return alertMessageTh;
	}
	public void setAlertMessageTh(String alertMessageTh) {
		this.alertMessageTh = alertMessageTh;
	}
	public String getAlertMessageEn() {
		return alertMessageEn;
	}
	public void setAlertMessageEn(String alertMessageEn) {
		this.alertMessageEn = alertMessageEn;
	}	
		
}
