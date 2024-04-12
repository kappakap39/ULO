package com.eaf.orig.ulo.app.view.form.subform.product.kec;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;

@SuppressWarnings("serial")
public class VerCustPaymentMethodSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(VerCustPaymentMethodSubForm.class);
	String subformId = "VER_CUSTOMER_KEC_PAYMENT_METHOD_SUBFORM";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	String SAVING_ACCOUNT_TYPE = SystemConstant.getConstant("SAVING_ACCOUNT_TYPE");
	String CURRENT_ACCOUNT_TYPE = SystemConstant.getConstant("CURRENT_ACCOUNT_TYPE");
	String FIX_ACCOUNT_TYPE = SystemConstant.getConstant("FIX_ACCOUNT_TYPE");
	String SFIX_ACCOUNT_TYPE = SystemConstant.getConstant("SFIX_ACCOUNT_TYPE");
	String KEC_PAYROLL_CARD_TYPE = SystemConstant.getConstant("KEC_PAYROLL_CARD_TYPE");
	String PRODUCT_CODE_KEC = SystemConstant.getConstant("PRODUCT_CODE_KEC");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm){
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);		
		String PAYMENT_METHOD = request.getParameter("PAYMENT_METHOD_"+PRODUCT_K_EXPRESS_CASH);
		String PAYMENT_RATIO =	request.getParameter("PAYMENT_RATIO_"+PRODUCT_K_EXPRESS_CASH);
		String FULL_ACCOUNT_NO =  request.getParameter("ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH);
		String APPROVAL_LIMIT = request.getParameter("APPROVAL_LIMIT");
		String ACCOUNT_NO = "";
		String ACCOUNT_NAME = "";
		if(!Util.empty(FULL_ACCOUNT_NO)){
			ACCOUNT_NO = FormatUtil.removeDash(FULL_ACCOUNT_NO.substring(0,14)).trim();
			ACCOUNT_NAME = FULL_ACCOUNT_NO.substring(14,FULL_ACCOUNT_NO.length()).trim();
		}
		
		logger.debug("FULL_ACCOUNT_NO KEC >> "+FULL_ACCOUNT_NO);
		logger.debug("PAYMENT_METHOD KEC >> "+PAYMENT_METHOD);
		logger.debug("PAYMENT_RATIO KEC >> "+PAYMENT_RATIO);
		logger.debug("ACCOUNT_NO KEC >> "+ACCOUNT_NO);
		logger.debug("ACCOUNT_NAME KEC >> "+ACCOUNT_NAME);
		logger.debug("APPROVAL_LIMIT KEC >> "+APPROVAL_LIMIT);
		ApplicationDataM applicationM = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);		
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH); 
		
		logger.debug("setProperties paymentMethod>> "+paymentMethod);		
		if(Util.empty(paymentMethod)){
			logger.debug("paymentMethod null");
			paymentMethod = new PaymentMethodDataM();
			String paymentMethodId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
			paymentMethod.init(paymentMethodId);
			paymentMethod.setPersonalId(personalId);
			paymentMethod.setProductType(PRODUCT_K_EXPRESS_CASH);
			applicationGroup.addPaymentMethod(paymentMethod);

		}
		paymentMethod.setPaymentMethod(PAYMENT_METHOD);
		logger.debug("PAYMENT METHOD : " + paymentMethod.getPaymentMethod());
		if (!Util.empty(applicationM)) {
			LoanDataM loan =applicationM.getLoan();
			if(!Util.empty(loan)){
				loan.setLoanAmt(FormatUtil.toBigDecimal(APPROVAL_LIMIT, true));		
			}
		}
		logger.debug("getPaymentMethod : " + paymentMethod.getPaymentMethod());
		if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())) {
			logger.debug("PAYMENT_METHOD_DEPOSIT_ACCOUNT paymentMethod>> "+paymentMethod);	
			paymentMethod.setPaymentRatio(FormatUtil.toBigDecimal(PAYMENT_RATIO, true));
			paymentMethod.setAccountNo(ACCOUNT_NO);
			paymentMethod.setAccountName(ACCOUNT_NAME);
			paymentMethod.setAccountType(SAVING_ACCOUNT_TYPE);
		}else{
			paymentMethod.setPaymentRatio(null);
			paymentMethod.setAccountNo(null);
			paymentMethod.setAccountName(null);
			paymentMethod.setCompleteData(INFO_IS_CORRECT);
		}
		logger.debug("last paymentMethod>> "+paymentMethod);	
		ArrayList<ApplicationDataM> application = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(application)){
			for(ApplicationDataM applicationItem : application){
				ArrayList<LoanDataM> loans = applicationItem.getLoans();
				if(null != loans){
					for(LoanDataM loan : loans){
						loan.setPaymentMethodId(paymentMethod.getPaymentMethodId());
						loan.setLoanAmt(loan.getLoanAmt());
					}	
				}
			}
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("validateForm.subformId >> "+subformId);		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String PAYMENT_METHOD = request.getParameter("PAYMENT_METHOD_"+PRODUCT_K_EXPRESS_CASH);
		String FULL_ACCOUNT_NO =  request.getParameter("ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH);
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);		
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH); 
		logger.debug("paymentMethod >> "+paymentMethod);
		
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
		}
		ArrayList<PhoneVerificationDataM> phoneVerifications = verificationResult.getPhoneVerifications();
		if(!Util.empty(phoneVerifications)){
			PhoneVerificationDataM phoneVerification = phoneVerifications.get(phoneVerifications.size()-1);
			String PhoneVerStatus = phoneVerification.getPhoneVerStatus();
			logger.debug("PhoneVerStatus >> "+PhoneVerStatus);
			if(!MConstant.FLAG_N.equals(PhoneVerStatus)){
				formError.mandatory(subFormID,"PAYMENT_METHOD",PAYMENT_METHOD,request);
				if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
					formError.mandatory(subFormID,"ACCOUNT_NO",FULL_ACCOUNT_NO,request);
				}
			}
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		logger.debug("PaymentMethodSubForm.auditForm >> "+subformId);
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH);
		
		PersonalInfoDataM lastPersonalInfo = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String lastPersonalId = lastPersonalInfo.getPersonalId();		
		PaymentMethodDataM lastPaymentMethod = lastApplicationGroup.getPaymentMethodLifeCycleByPersonalId(lastPersonalId,PRODUCT_K_EXPRESS_CASH);
		
		auditFormUtil.setObjectValue(PERSONAL_TYPE);
		auditFormUtil.auditForm(subformId, "PAYMENT_METHOD", paymentMethod, lastPaymentMethod, request);
		
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		logger.debug("displayValueForm.subformId >> "+subformId);		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		logger.debug("formId >> "+formId);
		logger.debug("formLevel >> "+formLevel);
		logger.debug("roleId >> "+roleId);
		logger.debug("subformId >> "+subformId);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId >> "+personalId);
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_K_EXPRESS_CASH); 
		
		if(null == paymentMethod){
			logger.debug("paymentMethod null");
			paymentMethod = new PaymentMethodDataM();
			String paymentMethodId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
			paymentMethod.init(paymentMethodId);
			paymentMethod.setPersonalId(personalId);
			paymentMethod.setProductType(PRODUCT_K_EXPRESS_CASH);
			applicationGroup.addPaymentMethod(paymentMethod);
			
			ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationRelationLifeCycle(personalId, PRODUCT_K_EXPRESS_CASH);
			String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getProductRefId(personalInfo,PRODUCT_K_EXPRESS_CASH);
			for(ApplicationDataM application : applications){
				LoanDataM loan = application.getLoan();
				if(null != loan){
					loan.setPaymentMethodId(paymentMethodId);
					logger.debug("loan.getLoanAmt()>>"+loan.getLoanAmt());
					loan.setLoanAmt(formValue.getValue("APPROVAL_LIMIT","APPROVAL_LIMIT_"+PREFIX_ELEMENT_ID,loan.getLoanAmt()));
				}
			}
		}
		
		if(null != paymentMethod){		
//			String PREFIX_ELEMENT_ID = personalInfo.getIdno()+"_"+PRODUCT_K_EXPRESS_CASH;
			String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getProductRefId(personalInfo,PRODUCT_K_EXPRESS_CASH);
			paymentMethod.setPaymentMethod(formValue.getValue("PAYMENT_METHOD","PAYMENT_METHOD_"+PREFIX_ELEMENT_ID,paymentMethod.getPaymentMethod()));
			paymentMethod.setPaymentRatio(formValue.getValue("PAYMENT_RATIO","PAYMENT_RATIO_"+PREFIX_ELEMENT_ID,paymentMethod.getPaymentRatio()));
			paymentMethod.setAccountNo(formValue.getValue("ACCOUNT_NO","ACCOUNT_NO_"+PREFIX_ELEMENT_ID,paymentMethod.getAccountNo()));
			paymentMethod.setAccountName(formValue.getValue("ACCOUNT_NAME","ACCOUNT_NAME_"+PREFIX_ELEMENT_ID,paymentMethod.getAccountName()));
			paymentMethod.setAccountType(SAVING_ACCOUNT_TYPE);
			logger.debug("displayValueForm paymentMethod>>>"+paymentMethod);
		}
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("PAYMENT_METHOD");
		filedNames.add("PAYMENT_RATIO");
		filedNames.add("ACCOUNT_NO");
		filedNames.add("APPROVAL_LIMIT");
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(),PRODUCT_K_EXPRESS_CASH); 
			String paymentMethodType = paymentMethod.getPaymentMethod();		
			logger.debug("paymentMethodType>>>"+paymentMethodType);
		/*	if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethodType)){
				filedNames.add("PAYMENT_RATIO");
				filedNames.add("ACCOUNT_NO");
			}*/
							
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
				 fieldElement.setElementGroupId(personalInfo.getPersonalId());
				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				 fieldElements.add(fieldElement);
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) 
	{
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		if(!Util.empty(applicationGroup)){
			ApplicationDataM application = applicationGroup.filterApplicationItemLifeCycle();
			if(!Util.empty(application)){
				String product = applicationGroup.getProduct(application);
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
				if(null != personalInfo){
					BigDecimal vi = personalInfo.getTotVerifiedIncome()==null?new BigDecimal(0):personalInfo.getTotVerifiedIncome();
					logger.debug("renderSubformFlag product: "+product);
					
					if(PRODUCT_CODE_KEC.equals(product) && MConstant.FLAG_Y.equals(applicationGroup.foundLowIncome()) 
							&& DecisionApplicationUtil.isApprove(application.getFinalAppDecision())
							&& vi.compareTo(FormatUtil.toBigDecimal("15000", true)) < 0){ 
						return MConstant.FLAG_Y;
					}
				}
			}
		}
		return MConstant.FLAG_N;
	}

}
