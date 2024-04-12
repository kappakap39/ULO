package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.inf.CachePropertiesInf;
import com.eaf.ulo.cache.util.CacheUtil;


public class CardTypeListBoxFilter implements ListBoxFilterInf {
	private static transient Logger logger = Logger.getLogger(CardTypeListBoxFilter.class);	
	String CACHE_CARD_PRODUCT_INFO = SystemConstant.getConstant("CACHE_CARD_PRODUCT_INFO");
	String CACHE_NAME_TEMPLATE_CARDTYPE = SystemConstant.getConstant("CACHE_NAME_TEMPLATE_CARDTYPE");
	String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	private String displayMode = HtmlUtil.EDIT;
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {		
		String PRODUCT_TYPE = request.getParameter("PRODUCT_TYPE");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		String TEMPLATE_ID = applicationGroup.getApplicationTemplate();
		logger.debug("TEMPLATE_ID >> "+TEMPLATE_ID);
		logger.debug("PRODUCT_TYPE >> "+PRODUCT_TYPE);
//		##Comment for Change Filter Seach Card Match with Template
		String BUS_CLASS_ID = CacheControl.getName(CACHE_BUSINESS_CLASS,"ORG_ID",PRODUCT_TYPE,"BUS_CLASS_ID");
		logger.debug("BUS_CLASS_ID >> "+BUS_CLASS_ID);		
//		ArrayList<HashMap<String, Object>> cards = CacheControl.search(CACHE_CARD_PRODUCT_INFO, "BUS_CLASS_ID", BUS_CLASS_ID);
//		ArrayList<HashMap<String, Object>> card = CacheControl.search(CACHE_NAME_TEMPLATE_CARDTYPE, "TEMPLATE_ID", TEMPLATE_ID);
		ArrayList<HashMap<String, Object>> cards = filterCardBussinessClassTemplate(CACHE_NAME_TEMPLATE_CARDTYPE,"BUS_CLASS_ID","TEMPLATE_ID",BUS_CLASS_ID,TEMPLATE_ID);
		String formId = FormControl.getFormId(request);
		logger.debug("formId : "+formId);
		if(!Util.empty(cards) && !HtmlUtil.VIEW.equals(displayMode) && !"REKEY_SENSITIVE_FORM".equals(formId)){
			removeCardSelect(cards,request);
		}
		return cards;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId,String typeId, HttpServletRequest request) {
		
	}
	
	public ArrayList<HashMap<String,Object>> filterCardBussinessClassTemplate(String cacheIdTemplate,String searchBus,String searchTemplate,String CodeBuss,String CodeTemplate){
		ArrayList<HashMap<String,Object>> searchResults = new ArrayList<HashMap<String,Object>>();
		CachePropertiesInf<HashMap<String,Object>> cacheProperties = CacheController.getCacheProperties(cacheIdTemplate);
		if(null != cacheProperties){
			ArrayList<HashMap<String,Object>> cacheTypeResults = cacheProperties.getCacheTypeData(CacheConstant.PropertiesType.ALL);
			if(null != cacheTypeResults){
				for(HashMap<String,Object> cacheResult : cacheTypeResults){
					String codeValue = CacheUtil.getValue(cacheResult,searchBus);
					String codeValue2  = CacheUtil.getValue(cacheResult,searchTemplate);
					if(null != codeValue && codeValue.equals(CodeBuss) && null != codeValue2 && codeValue2.equals(CodeTemplate)){
						searchResults.add(cacheResult);
					}
				}
			}	
		}
		return searchResults;
	}
	
	public ArrayList<HashMap<String, Object>> removeCardSelect(ArrayList<HashMap<String, Object>> cardSelect,HttpServletRequest request){
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<CardDataM> cards =applicationGroup.getCards();
		if(!Util.empty(cards)){
			for(CardDataM card:cards){
				if(null !=card && !Util.empty(card.getCardType())){
					HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(card.getCardType());	
					String CardCode = SQLQueryEngine.display(CardInfo,"CARD_CODE");
					if(!Util.empty(cardSelect)){
						for(int i=0;i<cardSelect.size();i++){
							if(cardSelect.get(i).get("CODE").equals(CardCode)){
								cardSelect.remove(i);
							}						
						}
					}
				}
			}
		}		
		logger.debug("removeCardSelect >>> "+cardSelect.size());		
		return cardSelect;
	}

	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
}
