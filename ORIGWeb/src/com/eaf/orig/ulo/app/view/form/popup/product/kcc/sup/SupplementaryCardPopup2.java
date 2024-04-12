package com.eaf.orig.ulo.app.view.form.popup.product.kcc.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.popup.OfficeAddressPopup1;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SupplementaryCardPopup2 extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(OfficeAddressPopup1.class);
	String SUBFORM_ID="SUPPLEMENTARY_CARD_POPUP_2";
	
	String COMPLETE_FLAG_Y = SystemConstant.getConstant("COMPLETE_FLAG_Y");
	String COMPLETE_FLAG_N = SystemConstant.getConstant("COMPLETE_FLAG_N");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String percentLimitMaincard = request.getParameter("PERCENT_LIMIT_MAINCARD");
		String PERCENT_LIMIT = request.getParameter("PERCENT_LIMIT");
		ApplicationDataM applicationItem = (ApplicationDataM)appForm;
		CardDataM cardM = applicationItem.getCard();
		cardM.setPercentLimitMaincard(percentLimitMaincard);
		if(Util.empty(percentLimitMaincard)){
			cardM.setCompleteFlag(COMPLETE_FLAG_N);
		}else{
			cardM.setCompleteFlag(COMPLETE_FLAG_Y);
		}
		LoanDataM loan = applicationItem.getLoan();
		loan.setRequestLoanAmt(FormatUtil.toBigDecimal(PERCENT_LIMIT,true));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("validateForm.subformId >> "+SUBFORM_ID);
		ApplicationDataM applicationItem = (ApplicationDataM)appForm;
		if(Util.empty(applicationItem)){
			applicationItem = new ApplicationDataM();
		}
		FormErrorUtil formError = new FormErrorUtil(applicationItem);
		CardDataM cardM = applicationItem.getCard();
		if(Util.empty(cardM)){
			cardM  = new CardDataM();
		}
		formError.mandatory(SUBFORM_ID,"PERCENT_LIMIT_MAINCARD",cardM.getPercentLimitMaincard(),request);
		formError.mandatory(SUBFORM_ID,"PERCENT_LIMIT",applicationItem,request);

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={};
		try {
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			ApplicationDataM application = ((ApplicationDataM)objectForm);	
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			logger.debug("personalInfo.getPersonalType() >>"+personalInfo.getPersonalType());
			logger.debug("applicationGroup.getApplicationGroupId() >>"+applicationGroup.getApplicationGroupId());
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
