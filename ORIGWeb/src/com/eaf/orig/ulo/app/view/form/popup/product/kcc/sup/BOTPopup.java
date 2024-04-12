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

public class BOTPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BOTPopup.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	private String subformId = "SUP_BOT_POPUP";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
//		CardDataM cardM = ((CardDataM)appForm);
			String supName = request.getParameter("SUB_NAME");
			String cardLv = request.getParameter("CARD_LEVEL_"+PRODUCT_CRADIT_CARD);
			String percenLimitMainCard =request.getParameter("PERCENT_LIMIT_MAINCARD_"+PRODUCT_CRADIT_CARD);
			String percentLimit = request.getParameter("PERCENT_LIMIT");
			String membershipNo = request.getParameter("MEMBERSHIP_NO_"+PRODUCT_CRADIT_CARD);
			logger.debug("membershipNo >>"+membershipNo);
			logger.debug("percentLimit >>"+percentLimit);
			logger.debug("percenLimitMainCard >>"+percenLimitMainCard);
			logger.debug("cardLv >>"+cardLv);
			ApplicationDataM applicationDataM =  (ApplicationDataM)appForm;
			CardDataM cardM = applicationDataM.getCard();
			LoanDataM loanM = applicationDataM.getLoan();
			if(!Util.empty(cardM)){
				cardM.setPercentLimitMaincard(percenLimitMainCard);
				cardM.setMembershipNo(membershipNo);
			
				if(Util.empty(membershipNo)){
					cardM.setCompleteFlag(COMPLETE_FLAG_N);
				}else{
					cardM.setCompleteFlag(COMPLETE_FLAG_Y);
				}
			}
			loanM.setRequestLoanAmt(FormatUtil.toBigDecimal(percentLimit,true));
			
				//*****		percentLimit
//			cardM.setMembershipNo(membershipNo);

	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationDataM application = (ApplicationDataM)appForm;
		CardDataM card = application.getCard();
		FormErrorUtil formError = new FormErrorUtil(application);

//		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		formError.mandatory(subformId,"MEMBERSHIP_NO",card.getMembershipNo(),request);
		formError.mandatory(subformId,"PERCENT_LIMIT_MAINCARD",card.getPercentLimitMaincard(),request);
		formError.mandatory(subformId,"PERCENT_LIMIT",application,request);
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
			ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);
			ApplicationDataM application = ((ApplicationDataM)objectForm);	
//			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
//			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
			logger.debug("personalInfo.getPersonalType() sub >>"+personalInfo.getPersonalType());
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId(application.getApplicationRecordId());
				 logger.debug("application.getApplicationRecordId() >>"+application.getApplicationRecordId());
//				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				 fieldElements.add(fieldElement);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}

}
