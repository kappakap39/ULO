package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
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


public class CardTypeRekeyListBoxFilter implements ListBoxFilterInf {
	private static transient Logger logger = Logger.getLogger(CardTypeRekeyListBoxFilter.class);	
	String CACHE_CARD_PRODUCT_INFO = SystemConstant.getConstant("CACHE_CARD_PRODUCT_INFO");
	String CACHE_NAME_TEMPLATE_CARDTYPE = SystemConstant.getConstant("CACHE_NAME_TEMPLATE_CARDTYPE");
	private String displayMode = HtmlUtil.EDIT;
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {		
		String BUS_CLASS_ID = typeId;
		logger.debug("displayMode >> "+displayMode);
		logger.debug("BUS_CLASS_ID >> "+BUS_CLASS_ID);		
		ArrayList<HashMap<String, Object>> cards = new ArrayList<HashMap<String,Object>>();
		if(HtmlUtil.VIEW.equals(displayMode)){
			cards = (ArrayList<HashMap<String,Object>>)CacheControl.getCache(CACHE_CARD_PRODUCT_INFO);
		}else{
			cards = CacheControl.search(CACHE_CARD_PRODUCT_INFO, "BUS_CLASS_ID", BUS_CLASS_ID);		
		}
		if(!Util.empty(BUS_CLASS_ID) && ListBoxControl.Type.ACTIVE.equals(BUS_CLASS_ID)){
			cards = (ArrayList<HashMap<String,Object>>)CacheControl.getCache(CACHE_CARD_PRODUCT_INFO);
		}
		String formId = FormControl.getFormId(request);
		logger.debug("formId : "+formId);
		if("REKEY_SENSITIVE_FORM".equals(formId)){
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			cards = filterCardTemplate(CACHE_NAME_TEMPLATE_CARDTYPE,"TEMPLATE_ID",applicationGroup.getApplicationTemplate());
		}
		if(!Util.empty(cards) && !HtmlUtil.VIEW.equals(displayMode) && !"REKEY_SENSITIVE_FORM".equals(formId)){
			removeCardSelect(cards,cacheId, request);
		}
		return cards;
	}
	
	private ArrayList<HashMap<String,Object>> filterCardTemplate(String cacheIdTemplate,String searchTemplate,String CodeTemplate){
		ArrayList<HashMap<String,Object>> searchResults = new ArrayList<HashMap<String,Object>>();
		CachePropertiesInf<HashMap<String,Object>> cacheProperties = CacheController.getCacheProperties(cacheIdTemplate);
		if(null != cacheProperties){
			ArrayList<HashMap<String,Object>> cacheTypeResults = cacheProperties.getCacheTypeData(CacheConstant.PropertiesType.ALL);
			if(null != cacheTypeResults){
				for(HashMap<String,Object> cacheResult : cacheTypeResults){
					String codeValue  = CacheUtil.getValue(cacheResult,searchTemplate);
					if(null != codeValue && codeValue.equals(CodeTemplate)){
						searchResults.add(cacheResult);
					}
				}
			}	
		}
		return searchResults;
	}
	
	@Override
	public void setFilterProperties(String configId, String cacheId,String typeId, HttpServletRequest request) {
		
	}	
	public ArrayList<HashMap<String, Object>> removeCardSelect(ArrayList<HashMap<String, Object>> cardSelect,String origCardCode, HttpServletRequest request){
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ArrayList<CardDataM> cards = applicationGroup.getCards();
		if(!Util.empty(cards)){
			for(CardDataM card:cards){
				if(null != card && !Util.empty(card.getCardType())){
					HashMap<String,Object> CardInfo = CardInfoUtil.getCardInfo(card.getCardType());	
					String CardCode = SQLQueryEngine.display(CardInfo,"CARD_CODE");
					if(!Util.empty(cardSelect)){
						Iterator<HashMap<String, Object>> iter = cardSelect.iterator();
						while(iter.hasNext()){
							HashMap<String, Object> cardRecord = iter.next();
							if(cardRecord.get("CODE").equals(CardCode) 
									&& !cardRecord.get("CODE").equals(origCardCode)){
								iter.remove();
								break;
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
