package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class SubCardTypeListBoxFilter extends CardTypeListBoxFilter{
	private static transient Logger logger = Logger.getLogger(SubCardTypeListBoxFilter.class);
	String PRODUCT_TYPE = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	private String displayMode = HtmlUtil.EDIT;
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {		
		logger.debug("PRODUCT_TYPE >> "+PRODUCT_TYPE);	
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		String TEMPLATE_ID = applicationGroup.getApplicationTemplate();
		String BUS_CLASS_ID = CacheControl.getName(CACHE_BUSINESS_CLASS,"ORG_ID",PRODUCT_TYPE,"BUS_CLASS_ID");
		logger.debug("BUS_CLASS_ID >> "+BUS_CLASS_ID);
		logger.debug("TEMPLATE_ID >> "+TEMPLATE_ID);
//		ArrayList<HashMap<String, Object>> cards = CacheControl.search(CACHE_CARD_PRODUCT_INFO, "BUS_CLASS_ID", BUS_CLASS_ID);	
		ArrayList<HashMap<String, Object>> cards = filterCardBussinessClassTemplate(CACHE_NAME_TEMPLATE_CARDTYPE,"BUS_CLASS_ID","TEMPLATE_ID",BUS_CLASS_ID,TEMPLATE_ID);
		String formId = FormControl.getFormId(request);
		if(!Util.empty(cards)&& !HtmlUtil.VIEW.equals(displayMode) && !"REKEY_SENSITIVE_FORM".equals(formId)){
			removeCardSelect(cards,request);
		}
		return cards;
	}
	public void setElementDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
}
