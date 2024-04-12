package com.eaf.orig.ulo.app.view.form.popup.product.kcc.sup;

import java.math.BigDecimal;
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
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;


public class AmwayPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AmwayPopup.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	private String subformId = "SUP_AMWAY_POPUP";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String amwayNo  = request.getParameter("AMWAY_NO_"+PRODUCT_CRADIT_CARD);
	String 	percent_limit_maincard =request.getParameter("PERCENT_LIMIT_MAINCARD_"+PRODUCT_CRADIT_CARD);
	String  percent_limit =request.getParameter("PERCENT_LIMIT");
	String 	card_level = request.getParameter("CARD_LEVEL_"+PRODUCT_CRADIT_CARD);
	logger.debug("amwayNo >>> "+amwayNo);
	logger.debug("percent_limit_maincard >>"+percent_limit_maincard);
	logger.debug("percent_limit >>> "+percent_limit);
	logger.debug("card_level >>> "+card_level);

	ApplicationDataM applicationItem =  (ApplicationDataM)appForm;
	CardDataM card = applicationItem.getCard();
	LoanDataM loan = applicationItem.getLoan();
	loan.setRequestLoanAmt(FormatUtil.toBigDecimal(percent_limit,true));
		if(!Util.empty(card)){
			card.setCardLevel(card_level);
			card.setMembershipNo(amwayNo);
			card.setPercentLimitMaincard(percent_limit_maincard);
			if(Util.empty(amwayNo)){
				card.setCompleteFlag(COMPLETE_FLAG_N);
			}else{
				card.setCompleteFlag(COMPLETE_FLAG_Y);
			}
		}
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationDataM applicationItem =(ApplicationDataM)appForm;
		CardDataM card = applicationItem.getCard();
		LoanDataM loan = applicationItem.getLoan();
		FormErrorUtil formError = new FormErrorUtil(applicationItem);
//		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		formError.mandatory(subformId,"AMWAY_NO",card.getMembershipNo(),request);
		formError.mandatory(subformId,"PERCENT_LIMIT_MAINCARD",card.getPercentLimitMaincard(),request);
		formError.mandatory(subformId,"PERCENT_LIMIT",applicationItem,request);
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
		String[] filedNames ={"SUP_AMWAY_NO"};
		try {
			ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);
			ApplicationDataM application = ((ApplicationDataM)objectForm);	
//			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
//			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());
			logger.debug("personalInfo type sub >>"+personalInfo.getPersonalType());
			 for(String elementId:filedNames){
				 logger.debug("FormId : AmwayPopup Comparing Field name "+elementId);
				 logger.debug("FormId : AmwayPopup "+application.getApplicationRecordId());
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
