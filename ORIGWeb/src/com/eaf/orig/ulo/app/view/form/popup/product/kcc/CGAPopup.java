package com.eaf.orig.ulo.app.view.form.popup.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CGAPopup extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(CGAPopup.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String subformId = "CGA_POPUP";
	

	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String cardType  = request.getParameter("CARD_TYPE_"+PRODUCT_CRADIT_CARD);
		String referralCardNo = request.getParameter("REFERRAL_CARD_NO_"+PRODUCT_CRADIT_CARD);
		String channelNo = request.getParameter("CHANNEL_NO_"+PRODUCT_CRADIT_CARD);
		logger.debug("PRODUCT_CRADIT_CARD >> "+PRODUCT_CRADIT_CARD);
		logger.debug("cardType >> "+cardType);
		logger.debug("referralCardNo >> "+referralCardNo);
		logger.debug("channelNo >> "+channelNo);
		ApplicationDataM application = (ApplicationDataM)appForm;
		CardDataM card = application.getCard();
		card.setCgaCode(referralCardNo);
		card.setCgaApplyChannel(channelNo);
		
		if(Util.empty(channelNo) || Util.empty(referralCardNo)){
			card.setCompleteFlag(COMPLETE_FLAG_N);
		}else{
			card.setCompleteFlag(COMPLETE_FLAG_Y);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationDataM application = (ApplicationDataM)appForm;
		CardDataM card = application.getCard();
		FormErrorUtil formError = new FormErrorUtil(application);
//		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		formError.mandatory(subformId,"CHANNEL_NO",card.getCgaCode(),request);
		formError.mandatory(subformId,"CHANNEL_NO",card.getCgaApplyChannel(),request);
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
