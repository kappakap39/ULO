package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SupProductInfoForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(SupProductInfoForm.class);
	String ADD_CARD_NO_APPLICATION_ROLE = SystemConstant.getConstant("ADD_CARD_NO_APPLICATION_ROLE");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);
		String cardType = request.getParameter("PRODUCTS_CARD_TYPE");
		String cardId = request.getParameter("CARD_ID");
		String cardLevel = request.getParameter("CARD_LEVEL");
		if (Util.empty(applicationGroup)) {
			applicationGroup = new ApplicationGroupDataM();

		}
		ApplicationDataM appM = applicationGroup.getApplicationProduct(MConstant.Product.CREDIT_CARD);
		if (Util.empty(appM)) {
			appM = new ApplicationDataM();
		}
		ArrayList<LoanDataM> loanListM = appM.getLoans();
		CardDataM cardM = new CardDataM();
		if (Util.empty(loanListM)) {
			loanListM = new ArrayList<LoanDataM>();
			LoanDataM loanM = new LoanDataM();
			cardM.setCardType(cardType);
			cardM.setCardId(cardId);
			cardM.setCardLevel(cardLevel);
			loanM.setCard(cardM);
			loanListM.add(loanM);
			appM.setLoans(loanListM);
			applicationGroup.addApplications(appM);

		} else {

			for (LoanDataM loanM : loanListM) {
				cardM = loanM.getCard();
			}
			if (Util.empty(cardM)) {
				cardM = new CardDataM();
			}
			cardM.setCardType(cardType);
			cardM.setCardId(cardId);
			cardM.setCardLevel(cardLevel);
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);
		String subformId = "SUP_PRODUCT_INFO_SUBFORM";
		logger.debug("subformId >> " + subformId);
		
		FormErrorUtil formError = new FormErrorUtil();

		ApplicationDataM appM = applicationGroup.getApplicationProduct(MConstant.Product.CREDIT_CARD);
		ArrayList<LoanDataM> loanListM = appM.getLoans();
		CardDataM cardM = new CardDataM();
		for (LoanDataM loanM : loanListM) {
			cardM = loanM.getCard();
		}

		formError.mandatory(subformId, "PRODUCTS_CARD_TYPE", appM.getBusinessClassId(), request);
		// formError.mandatory(subformId,"PRODUCTS_CARD_TYPE",appM.getBusinessClassId(),request);
		// formError.mandatory(subformId,"PRODUCTS_CARD_TYPE",appM.getBusinessClassId(),request);

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm){
		String FLAG = MConstant.FLAG_Y;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String roleId = FormControl.getFormRoleId(request);
		if(ADD_CARD_NO_APPLICATION_ROLE.equals(roleId)){
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			if(!Util.empty(personalInfo)){
				if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getVerificationResult()) && !Util.empty(personalInfo.getVerificationResult().getPolicyRules())){
					FLAG = MConstant.FLAG_Y;
				}else{
					FLAG = MConstant.FLAG_N;
				}
			}
		}
		logger.debug("isVeto : "+applicationGroup.isVeto());
		if(applicationGroup.isVeto()){
			FLAG = MConstant.FLAG_N;
		}
		logger.debug("FLAG : "+FLAG);
		return FLAG;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		String subformId = "SUP_PRODUCT_INFO_SUBFORM";
		logger.debug("SupProductInfoForm.subformRoleId >> "+subformRoleId);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		formValue.clearValue("LIST_SUP_PRODUCT",applicationGroup);
	}
}
