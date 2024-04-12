package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class RejectTemplateVariableDataM implements Serializable, Cloneable{
	public RejectTemplateVariableDataM(){
		super();
	}
	private String applicationGroupId;
	private String businessClassId;
	private String applicationGroupNo;
	private String applicationNo;
	private String finalDecisionDate;
	private String personalId;
	private String titleName;
	private String personalNameTh;
	private String personalNameEn;
	private String sellerCustomerNameTh;
	private String addressLine1;
	private String addressLine2;
	private String zipcode;
	private String personalType;
	private String nationality;
	private String emailPrimary;
	private String productName;
	private String productNameEn;
	private String productNameTh;
	private String productNameThEn;
	private String productName2lang;
	private String productType;
	private String idNo;
	private ArrayList<String> allApplicationRecordIds;
	private String applicationRecordId;
	private String contactCenterNoProduct;
	private String contactCenterNoCC;
	private String contactCenterNoKEC;
	private String contactCenterNoKPL;
	private String rejectReasonAllProduct;
	private String documentList;
	private String productFullDescription;
	private String website;
	private String rejectReason;
	private String contactNoSpace;
	private String websiteNoSpace;
	private String BundleFullDescription;
	private String cisNo;
	private String thFirstName;
	private String thMidName;
	private String thLastName;

	private String afpDeliveryMethod;			// KPL: Store MakeAFP delivery method (EMAIL, PAPER)
	private String letterChannel;
	
	public String getLetterChannel() {
		return letterChannel;
	}
	public void setLetterChannel(String letterChannel) {
		this.letterChannel = letterChannel;
	}
	public String getAfpDeliveryMethod() {
		return afpDeliveryMethod;
	}
	public void setAfpDeliveryMethod(String afpDeliveryMethod) {
		this.afpDeliveryMethod = afpDeliveryMethod;
	}	
	public String getApplicationGroupId(){
		return applicationGroupId;
	}
	public void setApplicationGroupId(String applicationGroupId){
		this.applicationGroupId = applicationGroupId;
	}
	public String getApplicationGroupNo(){
		return applicationGroupNo;
	}
	public void setApplicationGroupNo(String applicationGroupNo){
		this.applicationGroupNo = applicationGroupNo;
	}
	public String getApplicationNo(){
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo){
		this.applicationNo = applicationNo;
	}
	public String getFinalDecisionDate(){
		return finalDecisionDate;
	}
	public void setFinalDecisionDate(String finalDecisionDate){
		this.finalDecisionDate = finalDecisionDate;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getTitleName(){
		return titleName;
	}
	public void setTitleName(String titleName){
		this.titleName = titleName;
	}
	public String getPersonalNameTh(){
		return personalNameTh;
	}
	public void setPersonalNameTh(String personalNameTh){
		this.personalNameTh = personalNameTh;
	}
	public String getPersonalNameEn(){
		return personalNameEn;
	}
	public void setPersonalNameEn(String personalNameEn){
		this.personalNameEn = personalNameEn;
	}
	public String getSellerCustomerNameTh() {
		return sellerCustomerNameTh;
	}
	public void setSellerCustomerNameTh(String sellerCustomerNameTh) {
		this.sellerCustomerNameTh = sellerCustomerNameTh;
	}
	public String getAddressLine1(){
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1){
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2(){
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2){
		this.addressLine2 = addressLine2;
	}
	public String getZipcode(){
		return zipcode;
	}
	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}
	public String getPersonalType(){
		return personalType;
	}
	public void setPersonalType(String personalType){
		this.personalType = personalType;
	}
	public String getNationality(){
		return nationality;
	}
	public void setNationality(String nationality){
		this.nationality = nationality;
	}
	public String getEmailPrimary(){
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary){
		this.emailPrimary = emailPrimary;
	}
	public String getProductName(){
		return productName;
	}
	public void setProductName(String productName){
		this.productName = productName;
	}
	public String getProductNameEn() {
		return productNameEn;
	}
	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}
	public String getProductNameTh() {
		return productNameTh;
	}
	public void setProductNameTh(String productNameTh) {
		this.productNameTh = productNameTh;
	}
	public String getProductNameThEn() {
		return productNameThEn;
	}
	public void setProductNameThEn(String productNameThEn) {
		this.productNameThEn = productNameThEn;
	}
	public String getProductName2lang() {
		return productName2lang;
	}
	public void setProductName2lang(String productName2lang) {
		this.productName2lang = productName2lang;
	}
	public String getProductType(){
		return productType;
	}
	public void setProductType(String productType){
		this.productType = productType;
	}
	public String getIdNo(){
		return idNo;
	}
	public void setIdNo(String idNo){
		this.idNo = idNo;
	}
	public ArrayList<String> getAllApplicationRecordIds(){
		return allApplicationRecordIds;
	}
	public void setAllApplicationRecordIds(ArrayList<String> allApplicationRecordIds){
		this.allApplicationRecordIds = allApplicationRecordIds;
	}
	public String getApplicationRecordId(){
		return applicationRecordId;
	}
	public void setApplicationRecordId(String applicationRecordId){
		this.applicationRecordId = applicationRecordId;
	}
	public String getContactCenterNoProduct(){
		return contactCenterNoProduct;
	}
	public void setContactCenterNoProduct(String contactCenterNoProduct){
		this.contactCenterNoProduct = contactCenterNoProduct;
	}
	public String getRejectReasonAllProduct(){
		return rejectReasonAllProduct;
	}
	public void setRejectReasonAllProduct(String rejectReasonAllProduct){
		this.rejectReasonAllProduct = rejectReasonAllProduct;
	}
	public String getContactCenterNoCC(){
		return contactCenterNoCC;
	}
	public void setContactCenterNoCC(String contactCenterNoCC){
		this.contactCenterNoCC = contactCenterNoCC;
	}
	public String getContactCenterNoKEC(){
		return contactCenterNoKEC;
	}
	public void setContactCenterNoKEC(String contactCenterNoKEC){
		this.contactCenterNoKEC = contactCenterNoKEC;
	}
	public String getContactCenterNoKPL(){
		return contactCenterNoKPL;
	}
	public void setContactCenterNoKPL(String contactCenterNoKPL){
		this.contactCenterNoKPL = contactCenterNoKPL;
	}
	public String getBusinessClassId(){
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId){
		this.businessClassId = businessClassId;
	}
	
	public String getBusinessClassProductType(){
		return (null != businessClassId)?businessClassId.split("\\_")[0]:null;
	}
	public String getDocumentList(){
		return documentList;
	}
	public void setDocumentList(String documentList){
		this.documentList = documentList;
	}
	public String getProductFullDescription() {
		return productFullDescription;
	}
	public void setProductFullDescription(String productFullDescription) {
		this.productFullDescription = productFullDescription;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getContactNoSpace() {
		return contactNoSpace;
	}
	public void setContactNoSpace(String contactNoSpace) {
		this.contactNoSpace = contactNoSpace;
	}
	public String getWebsiteNoSpace() {
		return websiteNoSpace;
	}
	public void setWebsiteNoSpace(String websiteNoSpace) {
		this.websiteNoSpace = websiteNoSpace;
	}
	public String getBundleFullDescription() {
		return BundleFullDescription;
	}
	public void setBundleFullDescription(String bundleFullDescription) {
		BundleFullDescription = bundleFullDescription;
	}
	public String getCisNo() {
		return cisNo;
	}
	public void setCisNo(String cisNo) {
		this.cisNo = cisNo;
	}
	public String getThFirstName() {
		return thFirstName;
	}
	public void setThFirstName(String thFirstName) {
		this.thFirstName = thFirstName;
	}
	public String getThMidName() {
		return thMidName;
	}
	public void setThMidName(String thMidName) {
		this.thMidName = thMidName;
	}
	public String getThLastName() {
		return thLastName;
	}
	public void setThLastName(String thLastName) {
		this.thLastName = thLastName;
	}
}