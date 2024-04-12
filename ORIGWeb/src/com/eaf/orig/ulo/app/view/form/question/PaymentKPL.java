package com.eaf.orig.ulo.app.view.form.question;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.validation.AccountValidationHelper;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PaymentKPL extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(PaymentCC.class);
	private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personalId = (String)objectElement;
		logger.debug("personalId >>> "+personalId);
		return "/orig/ulo/subform/question/PaymentKPL.jsp?PERSONAL_ID="+personalId;
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(paymentMethod) && AccountValidationHelper.isJointAccount(personalInfo, paymentMethod)) {
			DIHProxy dihService = new DIHProxy();
			String accountNo = FormatUtil.removeDash(request.getParameter("ACCOUNT_NO_"+PRODUCT_K_PERSONAL_LOAN));
			String COMPLETE_DATA = request.getParameter("COMPLETE_DATA_"+PRODUCT_K_PERSONAL_LOAN);
			DIHQueryResult<String> queryResult = dihService.getAccountName(accountNo);
			String ACCOUNT_NAME = queryResult.getResult();
			if(Util.empty(COMPLETE_DATA)) {
				COMPLETE_DATA = INFO_IS_CORRECT;
			}
			paymentMethod.setAccountNo(FormatUtil.removeDash(accountNo) );
			paymentMethod.setAccountName(ACCOUNT_NAME);
			paymentMethod.setCompleteData(COMPLETE_DATA);
		}
		return null;
	}
	

	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
		String  personalId = (String)objectElement;
		logger.debug("personalId >> "+personalId);
		EntityFormHandler EntityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM  personalInfo = applicationGroup.getPersonalById(personalId);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}	
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(),PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(paymentMethod)){					
			//Verify Customer Payment Section
			if(!Util.empty(paymentMethod) && (AccountValidationHelper.isJointAccount(personalInfo, paymentMethod) || !AccountValidationHelper.isAccountValid(paymentMethod))) {
				return MConstant.FLAG.YES;
			}
			
		}
		
		return MConstant.FLAG.NO;
	}
	
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {
		String subformId = "VERIFY_CUSTOMER_FORM";
		FormErrorUtil formError = new FormErrorUtil();
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(paymentMethod)) {
			formError.mandatory(subformId, "ACCOUNT_NO", personalInfo, request);
		}
		return formError.getFormError();
	}
	
}
