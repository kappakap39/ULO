package com.eaf.orig.ulo.app.view.form.verification;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
//import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class VerifyCustomer extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(VerifyCustomer.class);
	private String FIELD_ID_DATA_VALIDATION_STATUS = SystemConstant.getConstant("FIELD_ID_DATA_VALIDATION_STATUS");
	private String VALIDATION_STATUS_REQUIRED = SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
	private String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	private String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY=SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String ACCT_VALIDATION_PASS =SystemConstant.getConstant("ACCT_VALIDATION_PASS");
	private String PRODUCT_K_PERSONAL_LOAN =SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private String RECEIVE_INCOME_METHOD_CASH_CHEQUE =SystemConstant.getConstant("RECEIVE_INCOME_METHOD_CASH_CHEQUE");
	private String PRODUCT_K_EXPRESS_CASH =SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	private String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	private String  SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
	private String subformId ="VERIFICATION_VALIDATION_SUBFROM";	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)getObjectRequest();
		String verifyCustomerResult = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
		
		//Defect#3263
		if(KPLUtil.isRequireDiffRequestResult(applicationGroup))
		{
			verifyCustomerResult = VerifyUtil.VALIDATION_STATUS_REQUIRED;
		}
		
		logger.debug("verifyCustomerResult : "+verifyCustomerResult);
		String styleClass = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, verifyCustomerResult, "SYSTEM_ID1");
		String verifyCustomerResultDesc = CacheControl.getName(FIELD_ID_DATA_VALIDATION_STATUS, verifyCustomerResult, "DISPLAY_NAME");
		logger.debug("verifyCustomerResultDesc : "+verifyCustomerResultDesc);
		FormEffects formEffect = new FormEffects(subformId, request);	
		StringBuilder  htmlTag = new StringBuilder();
		htmlTag.append("<div class='col-md-12'>");
		htmlTag.append("	<div class='form-group'>");
		htmlTag.append("		<div class='col-sm-5 col-md-4'>"+HtmlUtil.button("VERIFY_CUSTOMER_BTN", "", "VERIFY_CUSTOMER_BTN", "VERIFY_CUSTOMER_BTN", "btn btn-default btn-block", HtmlUtil.elementTagId("VERIFY_CUSTOMER_BTN"), formEffect)+"</div>");	
		htmlTag.append(			HtmlUtil.textBox("VERIFY_CUSTOMER_STATUS","",verifyCustomerResultDesc,styleClass,"",HtmlUtil.VIEW
				,HtmlUtil.elementTagId("VERIFY_CUSTOMER_STATUS"), "col-sm-4 col-md-4",request));
		htmlTag.append("	</div>");
		htmlTag.append("</div>");
		return htmlTag.toString();
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		FormErrorUtil formError = new FormErrorUtil();
		VerificationResultDataM verificationResult = (VerificationResultDataM)objectElement;
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)getObjectRequest();
		String verifyCustomerResult = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
		logger.debug("verifyCustomerResult : "+verifyCustomerResult);
		String ERROR_TYPE = VerifyUtil.validationVerification(applicationGroup);
		String REQUIRED_ALL = VerifyUtil.ERROR_TYPE_REQUIRED_ALL_VERIFICATION;
		String REQUIRED_SOME = VerifyUtil.ERROR_TYPE_REQUIRED_SOME_VERIFICATION;
		logger.debug("ERROR_TYPE : "+ERROR_TYPE);
		logger.debug("REQUIRED_ALL : "+REQUIRED_ALL);
		logger.debug("REQUIRED_SOME : "+REQUIRED_SOME);
		if((VALIDATION_STATUS_REQUIRED.equals(verifyCustomerResult) && (REQUIRED_ALL.equals(ERROR_TYPE) || REQUIRED_SOME.equals(ERROR_TYPE)))
			//Defect#3263
			|| KPLUtil.isRequireDiffRequestResult(applicationGroup)	){
			formError.mandatory(subformId, "VERIFY_CUSTOMER_BTN","", request);
		}
		return formError.getFormError();
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
		String applicantPersonalId = applicationGroup.getPersonalId(PERSONAL_TYPE_APPLICANT);	
		String supPersonalId = applicationGroup.getPersonalId(PERSONAL_TYPE_SUPPLEMENTARY);
		logger.debug("applicantPersonalId >> "+applicantPersonalId);
		logger.debug("supPersonalId >> "+supPersonalId);	
		String verifyCustomerResult = VerifyUtil.VerifyResult.getVerifyCustomerResult(applicationGroup);
		logger.debug("verifyCustomerResult : "+verifyCustomerResult);
		if(VALIDATION_STATUS_COMPLETED.equals(verifyCustomerResult)){
			 ArrayList<PaymentMethodDataM> paymentMethodApplicants = applicationGroup.getListPaymentMethodLifeCycleByPersonalId(applicantPersonalId);
			 ArrayList<PaymentMethodDataM> paymentMethodSupplementarys	= applicationGroup.getListPaymentMethodLifeCycleByPersonalId(supPersonalId);
			 if(!Util.empty(paymentMethodApplicants)){
				 for(PaymentMethodDataM paymentMethodApplicant:paymentMethodApplicants){
					 if(!PRODUCT_K_PERSONAL_LOAN.equals(paymentMethodApplicant.getProductType())){
						 if(!Util.empty(paymentMethodApplicant) && !Util.empty(paymentMethodApplicant.getCompleteData()) 
								 && !paymentMethodApplicant.getCompleteData().equals(ACCT_VALIDATION_PASS)){
							 // remove Data
							 paymentMethodApplicant.setPaymentMethod(RECEIVE_INCOME_METHOD_CASH_CHEQUE);
							 paymentMethodApplicant.setAccountNo("");
							 paymentMethodApplicant.setAccountName("");
							 paymentMethodApplicant.setPaymentRatio(null);
							 
						 }
					 }
				 }
			 }			 
			 if(!Util.empty(paymentMethodSupplementarys)){
				 for(PaymentMethodDataM paymentMethodSupplementary:paymentMethodSupplementarys){
					 if(!PRODUCT_K_PERSONAL_LOAN.equals(paymentMethodSupplementary.getProductType())){
						 if(!Util.empty(paymentMethodSupplementary) && !Util.empty(paymentMethodSupplementary.getCompleteData()) 
								 && !paymentMethodSupplementary.getCompleteData().equals(ACCT_VALIDATION_PASS)){
							 logger.debug("verifyCustomerResult remove Data: ");
							 // remove Data
							 paymentMethodSupplementary.setPaymentMethod(RECEIVE_INCOME_METHOD_CASH_CHEQUE);
							 paymentMethodSupplementary.setAccountNo("");
							 paymentMethodSupplementary.setAccountName("");
							 paymentMethodSupplementary.setPaymentRatio(null);							 
						 }
					 }
				 }
			 }		
			 ArrayList<SpecialAdditionalServiceDataM> additionalServiceApplicants = applicationGroup
					 .getListSpecialAdditionalServiceLifeCycleByPersonalId(applicantPersonalId);
			 if(!Util.empty(additionalServiceApplicants)){
				 for(SpecialAdditionalServiceDataM additionalServiceApplicant:additionalServiceApplicants){
					 if(SPECIAL_ADDITIONAL_SERVICE_ATM.equals(additionalServiceApplicant.getServiceType())){		
						 boolean isNotCompleteData=false;
						 boolean isNotCompleteSavingData=false;
						 if(!Util.empty(additionalServiceApplicant) && !Util.empty(additionalServiceApplicant.getCompleteData())
								 && !additionalServiceApplicant.getCompleteData().equals(ACCT_VALIDATION_PASS)){
							 additionalServiceApplicant.setCurrentAccNo("");
							 additionalServiceApplicant.setCurrentAccName("");
							 isNotCompleteData = true;
						 }
						 if(!Util.empty(additionalServiceApplicant) && !Util.empty(additionalServiceApplicant.getCompleteDataSaving())
								 && !additionalServiceApplicant.getCompleteDataSaving().equals(ACCT_VALIDATION_PASS)){
							 additionalServiceApplicant.setSavingAccNo("");
							 additionalServiceApplicant.setSavingAccName("");
							 isNotCompleteSavingData = true;
						 }	
						 
						 if(isNotCompleteData && isNotCompleteSavingData){
							 additionalServiceApplicant = new SpecialAdditionalServiceDataM();
						 }
					 }				 
				 }
			 }
			 ApplicationDataM kecItem =	applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_EXPRESS_CASH);
			 if(!Util.empty(kecItem)){
				String []cashTransFerType = ApplicationUtil.getCashTranferTypes();				 
				CashTransferDataM cashTransfer =  kecItem.getCashTransfer(cashTransFerType);
				if(!Util.empty(cashTransfer)){
					if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getCompleteData()) && !cashTransfer.getCompleteData().equals(ACCT_VALIDATION_PASS)){
//						cashTransfer.setPercentTransfer(null);
//						cashTransfer.setTransferAccount("");
						kecItem.getLoan().removeCashTransfer(cashTransFerType);
					}
				}
				CashTransferDataM cashTransferCallForCash =  kecItem.getCashTransfer(CALL_FOR_CASH);
				if(!Util.empty(cashTransferCallForCash) && !Util.empty(cashTransferCallForCash.getCompleteData())
						&& !cashTransferCallForCash.getCompleteData().equals(ACCT_VALIDATION_PASS)){
					ArrayList<LoanDataM> loans = kecItem.getLoans();
					for(LoanDataM loan : loans){
						loan.removeCashTransfer(CALL_FOR_CASH);
					}	
				}
			}
		}
		return null;
	}
}
