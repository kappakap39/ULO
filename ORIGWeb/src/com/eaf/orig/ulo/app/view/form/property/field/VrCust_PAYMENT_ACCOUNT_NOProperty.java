package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.validation.AccountValidationHelper;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VrCust_PAYMENT_ACCOUNT_NOProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(VrCust_PAYMENT_ACCOUNT_NOProperty.class);
	private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");	
	private String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	private String FIELD_ID_ACCT_VALIDATION_ERROR = SystemConstant.getConstant("FIELD_ID_ACCT_VALIDATION_ERROR");	
	private String VALIDATION_STATUS_COMPLETED_NOT_PASS = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED_NOT_PASS");
	private String VER_CUS_COMPLETE_VERIFY_COMPLETED =SystemConstant.getConstant("VER_CUS_COMPLETE_VERIFY_COMPLETED");
	private String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");	
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("VrCust_PAYMENT_ACCOUNT_NOProperty.validateForm");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		String personalType = personalInfo.getPersonalType();
		logger.debug("personalType >> "+personalType);
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalType(personalType,PRODUCT_K_PERSONAL_LOAN);				
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		FormErrorUtil formError = new FormErrorUtil();
		
		//Add Additional logic for KPL 
		boolean savingPlusEmpty = Util.empty(application.getSavingPlusFlag());
		boolean followSPCompleted = !savingPlusEmpty && KPLUtil.haveKbankSavingDoc(applicationGroup);
		
		if(!Util.empty(paymentMethod) && (savingPlusEmpty || followSPCompleted)) {
			String VerCusResultCode = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
			if(VALIDATION_STATUS_COMPLETED.equals(VerCusResultCode)){
				formError.addAll(validatePayment(request,paymentMethod));
			} else if(VALIDATION_STATUS_COMPLETED_NOT_PASS.equals(VerCusResultCode) &&
					VER_CUS_COMPLETE_VERIFY_COMPLETED.equals(verificationResult.getVerCusComplete()) && MConstant.FLAG.YES.equals(verificationResult.getFicoFlag())) {
				logger.debug("FICO return validateForm");
				if(!Util.empty(application) && !Util.empty(application.getRecommendDecision()) && !FINAL_APP_DECISION_REJECT.equals(application.getRecommendDecision())) {
					formError.addAll(validatePayment(request,paymentMethod));
				}
			}
		}
		return formError.getFormError();
	}
	
	private HashMap<String, Object> validatePayment(HttpServletRequest request, PaymentMethodDataM paymentMethod) {
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(paymentMethod.getAccountNo())) {
			formError.error("ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN,PREFIX_ERROR+LabelUtil.getText(request,"ACCOUNT_NO"));
		}
		if(!AccountValidationHelper.isAccountValid(paymentMethod)) {
			formError.clearElement("ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN);
			formError.clearElement("ACCOUNT_NAME_"+PRODUCT_K_PERSONAL_LOAN);
			formError.error("ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN,ListBoxControl.getName(FIELD_ID_ACCT_VALIDATION_ERROR, 
					paymentMethod.getCompleteData()));
		}
		return formError.getFormError();
	}
}
