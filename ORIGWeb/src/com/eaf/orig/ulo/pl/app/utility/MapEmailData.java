package com.eaf.orig.ulo.pl.app.utility;

import java.util.Locale;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.cache.properties.MainSaleTypeProperties;
import com.eaf.orig.cache.properties.ProductProperties;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.EmailTemplateMasterM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.PLOrigEmailSMSDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigSalesInfoDAO;
import com.eaf.orig.ulo.pl.model.app.EmailSMSDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;

public class MapEmailData {
	private static String special = "\\";
	
	static Logger logger = Logger.getLogger(MapEmailData.class);
	
	public PLOrigContactDataM map(EmailSMSDataM emailSmsM,String templateID) throws Exception{
		PLOrigContactDataM contractM = new PLOrigContactDataM();
		PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
		EmailTemplateMasterM template = emailDAO.getEMailTemplateMaster(templateID, emailSmsM.getBusClassID());
		if(null != template){
			if(OrigConstant.FLAG_Y.equals(template.getEnable())){
				contractM.setEmail(create(template, emailSmsM));
				contractM.setFlag(OrigConstant.FLAG_Y);
			}
		}
		return contractM;
	}
	
	public EmailM create(EmailTemplateMasterM template,EmailSMSDataM emailSmsM) throws Exception{
		EmailM email = null;
		GeneralParamProperties po = this.getPOEmail();
		String templateID = template.getTemplateName();
		String sendTo = getSendTo(emailSmsM, templateID);
		
		String subject = getSubject(templateID, template.getSubject(), emailSmsM);
		String content = getContent(templateID, template.getContent(), emailSmsM);
		
		email = new EmailM();
		email.setTo(sendTo);
		email.setContent(content);
		email.setSubject(subject);
		email.setFromName(po.getParamvalue2());
		email.setFrom(po.getParamvalue());
		return email;
	}
	
	private boolean found(String template, String key){
		if(template != null && key != null){
			return template.contains(key);
		}
		return false;
	}
	private String StringNullToSpecific(String input, String specific) {
		if (input != null) {
			return input;
		}
		return specific;
	}	
	public String StringNullOrEmptyToSpecific(String input, String specific) {
		if (input == null || "".equals(input.trim())) {
			return specific;
		}
		return input;
	}
	
	private String getContent(String templateID, String content, EmailSMSDataM emailSmsM){
		String result = content;		
		if(OrigUtil.isEmptyString(result)){
			return null;
		}		
		if(found(result, OrigConstant.EmailSMS.KEY_APPLICATION_NO)){
			String appNo = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_APPLICATION_NO);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_APPLICATION_NO, StringNullToSpecific(appNo,""));
		}			
		if(found(result, OrigConstant.EmailSMS.KEY_BRANCH_NAME)){
			String branchName = getBranchName(emailSmsM);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_BRANCH_NAME, StringNullToSpecific(branchName,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_CUSTOMER_NAME)){
			String customerName =  emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_CUSTOMER_NAME);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_CUSTOMER_NAME, StringNullToSpecific(customerName,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_PRODUCE_NAME)){
			String product = getProductName(emailSmsM);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_PRODUCE_NAME, StringNullToSpecific(product,""));
		}			
		if(found(result, OrigConstant.EmailSMS.KEY_PHONE_NO)){
			String telephoneNo = getPOTelephone();
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_PHONE_NO, StringNullToSpecific(telephoneNo,""));
		}		
		if(found(result, OrigConstant.EmailSMS.KEY_REF_NO)){
			String refNo =  emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_REF_NO);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_REF_NO, StringNullToSpecific(refNo,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_SALE_TYPE)){
			String saleType =  emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_SALE_TYPE);
			String desc = getSaleType(saleType);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_TYPE, StringNullToSpecific(desc,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_SALE_ID)){
			String saleID = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_SALE_ID);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_ID, StringNullToSpecific(saleID,"-"));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_SALE_NAME)){
			String saleName = getSaleName(emailSmsM);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_NAME, StringNullToSpecific(saleName,"-"));
		}
		return result;
	}
	
	private String getSubject(String templateID, String subject, EmailSMSDataM emailSmsM){
		String result = subject;		
		if(OrigUtil.isEmptyString(result)){
			return null;
		}		
		if(found(result, OrigConstant.EmailSMS.KEY_APPLICATION_NO)){
			String appNo = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_APPLICATION_NO);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_APPLICATION_NO, StringNullToSpecific(appNo,""));
		}			
		if(found(result, OrigConstant.EmailSMS.KEY_BRANCH_NAME)){
			String branchName = getBranchName(emailSmsM);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_BRANCH_NAME, StringNullToSpecific(branchName,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_CUSTOMER_NAME)){
			String customerName =  emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_CUSTOMER_NAME);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_CUSTOMER_NAME, StringNullToSpecific(customerName,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_PRODUCE_NAME)){
			String product = getProductName(emailSmsM);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_PRODUCE_NAME, StringNullToSpecific(product,""));
		}			
		if(found(result, OrigConstant.EmailSMS.KEY_PHONE_NO)){
			String telephoneNo = getPOTelephone();
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_PHONE_NO, StringNullToSpecific(telephoneNo,""));
		}		
		if(found(result, OrigConstant.EmailSMS.KEY_REF_NO)){
			String refNo =  emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_REF_NO);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_REF_NO, StringNullToSpecific(refNo,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_SALE_TYPE)){
			String saleType =  emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_SALE_TYPE);
			String desc = getSaleType(saleType);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_TYPE, StringNullToSpecific(desc,""));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_SALE_ID)){
			String saleID = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_SALE_ID);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_ID, StringNullToSpecific(saleID,"-"));
		}
		if(found(result, OrigConstant.EmailSMS.KEY_SALE_NAME)){
			String saleName = getSaleName(emailSmsM);
			result = result.replaceAll(special + OrigConstant.EmailSMS.KEY_SALE_NAME, StringNullToSpecific(saleName,"-"));
		}
		return result;
	}
	
	private String getSaleName(EmailSMSDataM emailSmsM){
		String saleID = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_SALE_ID);
		try{
			return ORIGDAOFactory.getUtilityDAO().getSellerName(saleID);
		}catch(Exception e){
			return "";
		}
	}
	
	private String getSaleType(String saleTypeCode){
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		Vector<MainSaleTypeProperties> vSaleType = cacheUtil.loadCacheByName("MainSaleType");
		if(vSaleType != null && vSaleType.size() > 0){
			for(MainSaleTypeProperties saleTypeProp : vSaleType){
				if(saleTypeProp.getCode() != null && saleTypeProp.getCode().equals(saleTypeCode)){
					return saleTypeProp.getThDesc();
				}
			}
		}
		return "";
	}
	
	private String getPOTelephone(){
		PLOrigEmailSMSUtil sendUtil = new PLOrigEmailSMSUtil();
		GeneralParamProperties prop = sendUtil.getGeneralParamDetails(OrigConstant.ParamCode.PARAM_PO_PHONE);
		if(null != prop){
			return prop.getParamvalue();
		}
		return null;
	}
	
	private String getProductName(EmailSMSDataM emailSmsM){
		String product = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_PRODUCE_NAME);
		ProductProperties prop = getProductDetails(product);
		if(null != prop){
			return prop.getEnDesc();
		}
		return null;
	}
	
	public ProductProperties getProductDetails(String product){
		try{
			PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
			return emailDAO.getProductData(product);
		}catch (Exception e){
			return null;
		}
	}
	
	private String getBranchName(EmailSMSDataM emailSmsM){
		String branchCode = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_BRANCH_NAME);
		return getBranchDesc(branchCode, null);
	}
	
	private String getBranchDesc(String branchCode, Locale localeID){
		if(localeID == null){
			localeID = new Locale("th_TH");
		}
		try{
			PLOrigSalesInfoDAO salesDAO = PLORIGDAOFactory.getPLOrigSalesInfoDAO();
			return salesDAO.getBranchDecription(branchCode, localeID);
		}catch (Exception e){
			return null;
		}
	}
	
	private String getSendTo(EmailSMSDataM emailSmsM, String templateID) throws Exception{
		PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
		String appRecID = emailSmsM.getAppRecID();
		String channel = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_CHENNEL);
		String busClassID = emailSmsM.getBusClassID();
		String saleID = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_SALE_ID);
		String branchCode = emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_BRANCH_NAME);
		String refName =  emailSmsM.getData().get(OrigConstant.EmailSMS.KEY_REF_NAME);
		return emailDAO.getKBANKSendToEmails(appRecID, channel, saleID, branchCode, refName, busClassID, templateID);
	}
	
	public GeneralParamProperties getPOEmail(){
		PLOrigEmailSMSUtil sendUtil = new PLOrigEmailSMSUtil();
		GeneralParamProperties param = sendUtil.getGeneralParamDetails(OrigConstant.ParamCode.PARAM_PO_EMAIL);
		if(null != param){
			return param;
		}else{
			return new GeneralParamProperties();
		}
	}
}
