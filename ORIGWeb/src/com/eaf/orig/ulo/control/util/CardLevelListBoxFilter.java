package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class CardLevelListBoxFilter  implements ListBoxFilterInf,Comparator<HashMap<String, Object>>{
	private static transient Logger logger = Logger.getLogger(CardLevelListBoxFilter.class);
	String CACHE_CARD_LEVEL = SystemConstant.getConstant("CACHE_CARD_LEVEL");
	String FIELD_ID_CARD_LEVEL = SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String PRODUCT_CODE_KEC = SystemConstant.getConstant("PRODUCT_CODE_KEC");
	String CARD_TYPE_CODE_KBANK_VISA_SPW = SystemConstant.getConstant("CARD_TYPE_CODE_KBANK_VISA_SPW");
	String CARD_LEVEL_PLATINUM = SystemConstant.getConstant("CARD_LEVEL_PLATINUM");
	String APPLICATION_TEMPLATE_CC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_BUNDLE_SME");
	String CARD_TYPE_CODE_KBANK_JCB = SystemConstant.getConstant("CARD_TYPE_CODE_KBANK_JCB");
	private Object objectElement;
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
		String cardCode = getCardCode(request.getParameter("CARD_CODE"));
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		String TEMPLATE_ID = applicationGroup.getApplicationTemplate();
		logger.debug("TEMPLATE_ID >> "+TEMPLATE_ID);
		logger.debug("cardCode : "+cardCode);
		if(Util.empty(cardCode)) {
			cardCode = getCardCode(typeId);
		}
		ArrayList<HashMap<String, Object>> cardinfo = CacheControl.search(CACHE_CARD_LEVEL, "CARD_CODE", cardCode);
//		BUSINESS_CLASS_ID=KEC_EPG_GE
			
			if(!Util.empty(cardinfo)){
				for(Iterator<HashMap<String, Object>> iterator = cardinfo.iterator();iterator.hasNext();){
					HashMap<String, Object> cardItem = iterator.next();
					String BUSINESS_CLASS_ID = (String)cardItem.get("BUSINESS_CLASS_ID");
					if(!Util.empty(BUSINESS_CLASS_ID)){
						String product[] = BUSINESS_CLASS_ID.split("\\_");
						if(!Util.empty(product) && PRODUCT_CODE_KEC.equals(product[0])){
						iterator.remove();
						break;
						}
					}
					
					if(CARD_TYPE_CODE_KBANK_VISA_SPW.equals(cardCode)
							&& CARD_LEVEL_PLATINUM.equals((String)cardItem.get("CARD_LEVEL"))){
						iterator.remove();
						continue;
					}
					
					//CR0217 Add only JCB platinum to bundle SME template
					if(APPLICATION_TEMPLATE_CC_BUNDLE_SME.equals(TEMPLATE_ID)
							&& CARD_TYPE_CODE_KBANK_JCB.equals(cardCode)
							&& !CARD_LEVEL_PLATINUM.equals((String)cardItem.get("CARD_LEVEL"))){
						iterator.remove();
						continue;
					}
					
						cardItem.put("VALUE", CacheControl.getName(FIELD_ID_CARD_LEVEL, (String)cardItem.get("CARD_LEVEL"),"DISPLAY_NAME"));
					
				}
				
				//#Fix KEC NOT HAVE CARD LEVEL
//				for(HashMap<String, Object> cardItem :cardinfo){
//					if(KEC_BUSSCLASS_CLASS_ID.equals((String)cardItem.get("BUSINESS_CLASS_ID"))){
//						cardItem.remove("VALUE");
//					}else{
//						cardItem.put("VALUE", CacheControl.getName(FIELD_ID_CARD_LEVEL, (String)cardItem.get("CARD_LEVEL"),"DISPLAY_NAME"));
//					}
//					
//				}
			}	
			Collections.sort(cardinfo, new CardLevelListBoxFilter());
		return cardinfo;
	}
	private String getCardCode(String requestCardCode){
		try{
			if(null != requestCardCode){
				String[] objectRequestCardCode = requestCardCode.split("\\_");
				if(null != objectRequestCardCode && objectRequestCardCode.length == 1){
					return objectRequestCardCode[0];
				}else if(null != objectRequestCardCode && objectRequestCardCode.length == 2){
					return objectRequestCardCode[1];
				}
			}
		}catch(Exception e){		
			logger.fatal("ERROR",e);
		}
		return "";
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId, HttpServletRequest request) {
		
	}

	@Override
	public int compare(HashMap<String, Object> cardObject1,HashMap<String, Object> cardObject2) {
		try {
			if(!Util.empty(cardObject1) && !Util.empty(cardObject2)){
				String VALUE1 =  (String)cardObject1.get("VALUE");
				String VALUE2 =  (String)cardObject2.get("VALUE");	
				return VALUE1.compareTo(VALUE2);	
			}
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return 0;
	}

	@Override
	public void init(Object objectForm) {
		this.objectElement = objectForm;
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
 
}
