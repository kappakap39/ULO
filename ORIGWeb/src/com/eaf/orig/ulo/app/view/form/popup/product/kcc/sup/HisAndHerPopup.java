package com.eaf.orig.ulo.app.view.form.popup.product.kcc.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;


public class HisAndHerPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AmwayPopup.class);
	String SUB_FORM_ID = "SUP_HIS_HER_POPUP";
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String hisHer  = request.getParameter("HIS_HER_NO_"+PRODUCT_CRADIT_CARD);
	String 	percent_limit_maincard =request.getParameter("PERCENT_LIMIT_MAINCARD_"+PRODUCT_CRADIT_CARD);
	String 	percent_limit =request.getParameter("PERCENT_LIMIT");
	String cardlevel = request.getParameter("CARD_LEVEL_"+PRODUCT_CRADIT_CARD);
	logger.debug("hisHer >>> "+hisHer);
	logger.debug("percent_limit_maincard >>"+percent_limit_maincard);
	logger.debug("percent_limit >>"+percent_limit);
	logger.debug("cardlevel >>"+cardlevel);
	ApplicationDataM applicationItem =  (ApplicationDataM)appForm;
	CardDataM card = applicationItem.getCard();
		if(!Util.empty(card)){
			card.setMembershipNo(hisHer);
			card.setPercentLimitMaincard(percent_limit_maincard);
			card.setCardLevel(cardlevel);
			if(Util.empty(hisHer)){
				card.setCompleteFlag(COMPLETE_FLAG_N);
			}else{
				card.setCompleteFlag(COMPLETE_FLAG_Y);
			}
		}
	LoanDataM loan = applicationItem.getLoan();
	loan.setRequestLoanAmt(FormatUtil.toBigDecimal(percent_limit,true));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("validateForm.subformId >> "+SUB_FORM_ID);
		ApplicationDataM applicationItem = (ApplicationDataM)appForm;
		if(Util.empty(applicationItem)){
			applicationItem = new ApplicationDataM();
		}
		CardDataM cardM = applicationItem.getCard();
		if(Util.empty(cardM)){
			cardM  = new CardDataM();
		}
		FormErrorUtil formError = new FormErrorUtil(applicationItem);
		formError.mandatory(SUB_FORM_ID,"HIS_HER_NO",cardM.getMembershipNo(),request);
		formError.mandatory(SUB_FORM_ID,"PERCENT_LIMIT_MAINCARD",cardM.getPercentLimitMaincard(),request);
		formError.mandatory(SUB_FORM_ID,"PERCENT_LIMIT",applicationItem,request);

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
		String[] filedNames ={"SUB_HIS_HER_NO_CC"};
		try {
			ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);
			ApplicationDataM application = ((ApplicationDataM)objectForm);	
//			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
//			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
			logger.debug("personalInfo.getPersonalType() sub >>"+personalInfo.getPersonalType());
			if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType()))
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId(application.getApplicationRecordId());
//				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				 fieldElements.add(fieldElement);
			 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	
}
