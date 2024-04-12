package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;

public class ValidateAddCardNo extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateAddCardNo.class);

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		// TODO Auto-generated method stub
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(
			HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();		
		String CARD_NO =  request.getParameter("CARD_NO");
		String cardLimit = SystemConstant.getConstant("PRODUCT_CRADIT_CARD_LIMIT");
		logger.debug("CARD_NO >>> "+CARD_NO);
		if(Util.empty(CARD_NO)){
			formError.mandatoryElement("CARD_NO","CARD_NO",CARD_NO,request);
		}

		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<CardDataM> cards= applicationGroup.getCards();
		logger.debug("cards >>"+cards.size());
		if(!Util.empty(cards) && cards.size()>=FormatUtil.getInt(cardLimit)){
			formError.error(MessageUtil.getText(request, "MSG_MAX_CARD"));
		}
		String applicationGroupId = request.getParameter("applicationGroupId");
		return formError.getFormError();
	}
	
	

}
