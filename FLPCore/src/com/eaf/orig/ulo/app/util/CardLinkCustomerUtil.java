package com.eaf.orig.ulo.app.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CardLinkCustomerUtil {
	private static transient Logger logger = Logger.getLogger(CardLinkCustomerUtil.class);	
	static String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	static String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");	
	static String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");	
	static String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");	
	static String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");	
	static ArrayList<String> FINAL_APP_DECISION_CONDITION = SystemConstant.getArrayListConstant("SET_APPLICATION_FINAL_APP_DECISION_CONDITION");	
	static String SPECIAL_ADDITIONAL_SERVICE_POSTAL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_POSTAL");
	static String SPECIAL_ADDITIONAL_SERVICE_EMAIL = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_EMAIL");
	static String GEN_PARAM_CC_INFINITE = SystemConstant.getConstant("GEN_PARAM_CC_INFINITE");
	static String GEN_PARAM_CC_WISDOM = SystemConstant.getConstant("GEN_PARAM_CC_WISDOM");
	static String DEFAULT_REC_DECISION = SystemConstant.getConstant("DEFAULT_REC_DECISION");
	static String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	static String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	
	
	public static CardlinkCustomerDataM getCardlinkCustomer(ApplicationGroupDataM applicationGroup,String applicationRecordId){
		String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
		String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
		String CC_THAIBEV = SystemConfig.getGeneralParam("CC_THAIBEV");
		String CC_CUSTOMER_PREFIX = SystemConstant.getConstant("CC_CUSTOMER_PREFIX");
		String THAIBEV_CUSTOMER_PREFIX = SystemConstant.getConstant("THAIBEV_CUSTOMER_PREFIX");
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationRecordId);	
		ApplicationDataM application = applicationGroup.getApplicationById(applicationRecordId);		
		
		if(null != personalInfo && null != application){
			CardDataM cardM = application.getCard();
			if(null != cardM){
				String orgId =  CacheControl.getName(CACHE_BUSINESS_CLASS,"BUS_CLASS_ID",application.getBusinessClassId(),"ORG_ID");
				String orgNo = CacheControl.getName(CACHE_ORGANIZATION,"ORG_ID",orgId,"ORG_NO");
				logger.debug("orgId : "+orgId);
				logger.debug("orgNo : "+orgNo);
				String prefix = null;
				String notPrefix = null;
				if(PRODUCT_CRADIT_CARD.equals(application.getProduct())){
					if(CC_THAIBEV.equals(cardM.getCardType())){
						prefix = THAIBEV_CUSTOMER_PREFIX;
					}else{
						prefix = CC_CUSTOMER_PREFIX;
						notPrefix = THAIBEV_CUSTOMER_PREFIX;
					}
				}
				logger.debug("prefix : "+prefix);
				logger.debug("notPrefix : "+notPrefix);
				return personalInfo.getCardLinkCustomer(orgNo, prefix, notPrefix);
			}
		}
		return null;
	}
	public static boolean isNewCardLinkCustomer(ApplicationGroupDataM applicationGroup,String applicationRecordId){
		CardlinkCustomerDataM cardLinkCustomer = getCardlinkCustomer(applicationGroup,applicationRecordId);
		if(Util.empty(cardLinkCustomer) || MConstant.FLAG_Y.equals(cardLinkCustomer.getNewCardlinkCustFlag())){
			return true;
		}
		//INC1177172 case main exist sup new not found new customer
		String[] finalAppDecisions = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_APPROVE");
		ArrayList<ApplicationDataM> supplemantaryApplications = applicationGroup.filterLinkApplicationDecision(applicationRecordId, finalAppDecisions, APPLICATION_CARD_TYPE_SUPPLEMENTARY);
		if(!Util.empty(supplemantaryApplications)){
			for(ApplicationDataM supplemantaryApplication : supplemantaryApplications){		
				String supplemantaryApplicationRecordId = supplemantaryApplication.getApplicationRecordId();
				CardlinkCustomerDataM supCardLinkCustomer = getCardlinkCustomer(applicationGroup,supplemantaryApplicationRecordId);
				if(Util.empty(supCardLinkCustomer) || MConstant.FLAG_Y.equals(supCardLinkCustomer.getNewCardlinkCustFlag())){
					return true;
				}
			}
		}
		
		return false;
	}
}
