package com.eaf.orig.ulo.app.view.form.subform.product.manual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;

public class CardInfoUtil {
	private static transient Logger logger = Logger.getLogger(CardInfoUtil.class);
	public static HashMap<String,Object> getCardInfo(String CARD_TYPE,String CARD_LEVEL){
		String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
		ArrayList<HashMap> cardInfos = CacheControl.getCacheList(CACHE_CARD_INFO);
		if(!Util.empty(cardInfos)){
			for (HashMap cardInfo : cardInfos) {
				String _CARD_CODE = SQLQueryEngine.display(cardInfo,"CARD_CODE");
				String _CARD_LEVEL = SQLQueryEngine.display(cardInfo,"CARD_LEVEL");
				if(null != _CARD_CODE && _CARD_CODE.equals(CARD_TYPE)
						&& null != _CARD_LEVEL && _CARD_LEVEL.equals(CARD_LEVEL)){
					return cardInfo;
				}
			}
		}
		return new HashMap<String,Object>();
	}
	public static HashMap<String,Object> getCardInfoCardType(String CARD_TYPE){
		String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
		ArrayList<HashMap> cardInfos = CacheControl.getCacheList(CACHE_CARD_INFO);
		if(!Util.empty(cardInfos)){
			for (HashMap cardInfo : cardInfos) {
				String _CARD_CODE = SQLQueryEngine.display(cardInfo,"CARD_CODE");
				if(null != _CARD_CODE && _CARD_CODE.equals(CARD_TYPE)){
					return cardInfo;
				}
			}
		}
		return new HashMap<String,Object>();
	}
	public static HashMap<String,Object> getCardInfo(String CARD_TYPE_ID){
		String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
		ArrayList<HashMap> cardInfos = CacheControl.getCacheList(CACHE_CARD_INFO);
		if(!Util.empty(cardInfos)){
			for (HashMap cardInfo : cardInfos) {
				String _CARD_TYPE_ID = SQLQueryEngine.display(cardInfo,"CARD_TYPE_ID");
				if(null != _CARD_TYPE_ID && _CARD_TYPE_ID.equals(CARD_TYPE_ID)){
					return cardInfo;
				}
			}
		}
		return new HashMap<String,Object>();
	}
	public static String getCardDetail(String CARD_TYPE_ID,String fieldId){
		String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
		ArrayList<HashMap> cardInfos = CacheControl.getCacheList(CACHE_CARD_INFO);
		if(!Util.empty(cardInfos)){
			for (HashMap cardInfo : cardInfos) {
				String _CARD_TYPE_ID = SQLQueryEngine.display(cardInfo,"CARD_TYPE_ID");
				if(null != _CARD_TYPE_ID && _CARD_TYPE_ID.equals(CARD_TYPE_ID)){
					return (String) cardInfo.get(fieldId);
				}
			}
		}
		return "";
	}
	public static String getFullCardDetail(String CARD_TYPE_ID,String fieldId){
		String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO_FULL");
		ArrayList<HashMap> cardInfos = CacheControl.getCacheList(CACHE_CARD_INFO);
		if(!Util.empty(cardInfos)){
			for (HashMap cardInfo : cardInfos) {
				String _CARD_TYPE_ID = SQLQueryEngine.display(cardInfo,"CARD_TYPE_ID");
				if(null != _CARD_TYPE_ID && _CARD_TYPE_ID.equals(CARD_TYPE_ID)){
					return (String) cardInfo.get(fieldId);
				}
			}
		}
		return "";
	}
	public static boolean valdiateCreditLimit(BigDecimal creditLimit){
		return creditLimit.remainder(new BigDecimal("1000")).compareTo(new BigDecimal(0))>0;
	}
}
