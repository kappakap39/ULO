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

public class PaymentKEC extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(PaymentKEC.class);
	private String PRODUCT_K_EXPRESS_CASH =SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	private String ERR_CODE_ACCT_SINGLE_ACCT_KEC =SystemConstant.getConstant("ERR_CODE_ACCT_SINGLE_ACCT_KEC");
	private String PAYMENT_METHOD_DEPOSIT_ACCOUNT =SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
//	private String FIELD_ID_ACCT_VALIDATION_ERROR = SystemConstant.getConstant("FIELD_ID_ACCT_VALIDATION_ERROR");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String personalId = (String)objectElement;
		logger.debug("personalId >> "+personalId);
		EntityFormHandler EntityForm =(EntityFormHandler) request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();	
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(paymentMethod)){
			logger.debug("paymentMethod >> "+paymentMethod.getPaymentMethodId());
			if(!Util.empty(paymentMethod.getCompleteData()) && ERR_CODE_ACCT_SINGLE_ACCT_KEC.equals(paymentMethod.getCompleteData())){
				paymentMethod.setPaymentMethod(PAYMENT_METHOD_DEPOSIT_ACCOUNT);
			}	
		}
		return "/orig/ulo/subform/question/PaymentKEC.jsp?PERSONAL_ID="+personalId;
	}	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId, PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(paymentMethod)  && AccountValidationHelper.isJointAccount(personalInfo, paymentMethod)) {
			DIHProxy dihService = new DIHProxy();
			String PAYMENT_METHOD = request.getParameter("PAYMENT_METHOD_"+PRODUCT_K_EXPRESS_CASH);
			String PAYMENT_RATIO =	request.getParameter("PAYMENT_RATIO_"+PRODUCT_K_EXPRESS_CASH);
			String ACCOUNT_NO = FormatUtil.removeDash(request.getParameter("ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH));
			String COMPLETE_DATA = request.getParameter("COMPLETE_DATA_"+PRODUCT_K_EXPRESS_CASH);
			DIHQueryResult<String> queryResult = dihService.getAccountName(ACCOUNT_NO);
			String ACCOUNT_NAME = queryResult.getResult();
			if(Util.empty(COMPLETE_DATA)) {
				COMPLETE_DATA = INFO_IS_CORRECT;
			}
			paymentMethod.setPaymentMethod(PAYMENT_METHOD);
			if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())) {
				paymentMethod.setPaymentRatio(FormatUtil.toBigDecimal(PAYMENT_RATIO, true));
				paymentMethod.setAccountNo(ACCOUNT_NO);
				paymentMethod.setAccountName(ACCOUNT_NAME);
				paymentMethod.setCompleteData(COMPLETE_DATA);
			} else {
				paymentMethod.setPaymentRatio(null);
				paymentMethod.setAccountNo(null);
				paymentMethod.setAccountName(null);
				paymentMethod.setCompleteData(null);
			}
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
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(),PRODUCT_K_EXPRESS_CASH);
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
		FormErrorUtil formError = new FormErrorUtil();
//		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
//		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
//		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
//		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodByProduct(personalInfo.getPersonalType(), PRODUCT_K_EXPRESS_CASH);
//		if(!Util.empty(paymentMethod)) {
//			if(!PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())) {
//				if(Util.empty(paymentMethod.getAccountNo())) {
//					formError.error("ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH,PREFIX_ERROR+LabelUtil.getText(request,"ACCOUNT_NO"));
//				}
//				if(!AccountValidationHelper.isAccountValid(paymentMethod)) {
//					formError.clearElement("ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH);
//					formError.clearElement("ACCOUNT_NAME_"+PRODUCT_K_EXPRESS_CASH);
//					formError.error("ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH,ListBoxControl.getName(FIELD_ID_ACCT_VALIDATION_ERROR, 
//							paymentMethod.getCompleteData()));
//				}
//			}
//		}
		return formError.getFormError();
	}

}
