package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.CardFaceProperties;
import com.eaf.orig.cache.properties.ListboxProperties;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class DisplayCardFace implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(DisplayCardFace.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		PLOrigFormHandler plOrigForm = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		PLApplicationDataM appM = plOrigForm.getAppForm();
		if(appM == null) appM = new PLApplicationDataM();
		JsonObjectUtil jsonObj = new JsonObjectUtil();
		
		String cardType = request.getParameter("cardType");
		String cardFace = request.getParameter("cardFace");
		String displayMode = request.getParameter("displayMode");
		String applicantType = request.getParameter("applicantType");
		logger.debug("cardType = "+cardType);
		logger.debug("cardFace = "+cardFace);
		logger.debug("applicantType:" + applicantType);
		
		Vector cardFaceVt = null;
		if(applicantType == null || "".equals(applicantType)){
			cardFaceVt = ORIGCacheUtil.getInstance().loadCardFace(cardType, displayMode);
		}else{
			cardFaceVt = ORIGCacheUtil.getInstance().loadCardFace(cardType,applicantType, displayMode);
		}
		String defaultValue = "";
		if(!OrigConstant.FLAG_Y.equals(appM.getICDCFlag())){
			if(cardFaceVt != null && cardFaceVt.size() == 1){
				CardFaceProperties cardFaceProp = (CardFaceProperties)cardFaceVt.get(0);
				defaultValue = cardFaceProp.getCode();
			}else if(applicantType != null && !"".equals(applicantType)){
				ListboxProperties listBoxProp = ORIGCacheUtil.getInstance().GetListBoxListboxChoiceNo(applicantType, String.valueOf(OrigConstant.fieldId.INCOME_TYPE));
				if(listBoxProp != null){
					defaultValue = listBoxProp.getSystemID5(); //field id 49 system_id4 = default card face
				}
			}
		}else{
			defaultValue = cardFace;
			displayMode  = HTMLRenderUtil.DISPLAY_MODE_VIEW;
		}
		logger.debug("defaultValue:" + defaultValue);
		String tagCardFace = HTMLRenderUtil.displaySelectTagScriptAction_ORIG(cardFaceVt,defaultValue,"card_info_card_face",displayMode,"");
		logger.debug("tagCardFace :" + tagCardFace);
		jsonObj.CreateJsonObject("td_card_info_card_face", tagCardFace);
		
		return jsonObj.returnJson();
	}

}
