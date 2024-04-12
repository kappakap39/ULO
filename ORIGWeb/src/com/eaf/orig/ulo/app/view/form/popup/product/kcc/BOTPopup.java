package com.eaf.orig.ulo.app.view.form.popup.product.kcc;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class BOTPopup extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(BOTPopup.class);
	private String subformId = "BOT_POPUP";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	private String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		
		String  membershipNo = request.getParameter("MEMBERSHIP_NO_"+PRODUCT_CRADIT_CARD);
		ApplicationDataM application = (ApplicationDataM)appForm;
		CardDataM card = application.getCard();		
		card.setMembershipNo(membershipNo);
		if(Util.empty(membershipNo)){
			card.setCompleteFlag(COMPLETE_FLAG_N);
		}else{
			card.setCompleteFlag(COMPLETE_FLAG_Y);
		}
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("displayValueForm Method >> BOTPopup >>"+objectForm);
		String ApplicationRecordId = getSubformData("ApplicationRecordId");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationDataM applicationdataM = applicationGroup.getApplicationById(ApplicationRecordId);
		PersonalInfoDataM perssonalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		CardDataM card = applicationdataM.getCard();
		if(Util.empty(card)){
			card = new CardDataM();
		}
		HashMap<String, Object> CardInfoList = CardInfoUtil.getCardInfo(card.getCardType());
		String cardCode = SQLQueryEngine.display(CardInfoList, "CARD_CODE");
		String product = applicationdataM.getProduct();
		String cardCodeRefId = CompareSensitiveFieldUtil.getCardCodeRefId(perssonalApplicant, product, cardCode);
		logger.debug("cardCodeRefId >>"+cardCodeRefId);
		logger.debug("cardMembershipNo data >>"+card.getMembershipNo());
		card.setMembershipNo(formValue.getValue("MEMBERSHIP_NO_CC", "MEMBERSHIP_NO_CC_"+cardCodeRefId, card.getMembershipNo()));
		logger.debug("after set cardMembershipNo data >>"+card.getMembershipNo());
		if(Util.empty(card.getMembershipNo())){
			card.setCompleteFlag(COMPLETE_FLAG_N);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
//		CardDataM card = ((CardDataM)appForm);
		ApplicationDataM application = (ApplicationDataM)appForm;
		CardDataM card = application.getCard();
		FormErrorUtil formError = new FormErrorUtil(application);

//		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		formError.mandatory(subformId,"MEMBERSHIP_NO",card.getMembershipNo(),request);
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"MEMBERSHIP_NO"};
		try {
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			ApplicationDataM application = ((ApplicationDataM)objectForm);	
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			logger.debug("personalInfo.getPersonalType() >>"+personalInfo.getPersonalType());
			if(PERSONAL_TYPE.equals(personalInfo.getPersonalType())){
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId(application.getApplicationRecordId());
//				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				 fieldElements.add(fieldElement);
			 	}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	
}
