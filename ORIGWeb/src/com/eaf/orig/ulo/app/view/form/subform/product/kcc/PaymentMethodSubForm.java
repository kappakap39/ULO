package com.eaf.orig.ulo.app.view.form.subform.product.kcc;


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
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;

@SuppressWarnings("serial")
public class PaymentMethodSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PaymentMethodSubForm.class);
	String subformId = "KCC_PAYMENT_METHOD_SUBFORM";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String SAVING_ACCOUNT_TYPE = SystemConstant.getConstant("SAVING_ACCOUNT_TYPE");
	String CURRENT_ACCOUNT_TYPE = SystemConstant.getConstant("CURRENT_ACCOUNT_TYPE");
	String FIX_ACCOUNT_TYPE = SystemConstant.getConstant("FIX_ACCOUNT_TYPE");
	String SFIX_ACCOUNT_TYPE = SystemConstant.getConstant("SFIX_ACCOUNT_TYPE");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm){
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);				
		String PAYMENT_METHOD = request.getParameter("PAYMENT_METHOD_"+PRODUCT_CRADIT_CARD);
		String PAYMENT_RATIO =	request.getParameter("PAYMENT_RATIO_"+PRODUCT_CRADIT_CARD);
		String ACCOUNT_NO = FormatUtil.removeDash(request.getParameter("ACCOUNT_NO_"+PRODUCT_CRADIT_CARD));
		String COMPLETE_DATA = request.getParameter("COMPLETE_DATA_"+PRODUCT_CRADIT_CARD);			
		logger.debug("PRODUCT_CRADIT_CARD >> "+PRODUCT_CRADIT_CARD);
		logger.debug("PAYMENT_METHOD >> "+PAYMENT_METHOD);
		logger.debug("PAYMENT_RATIO >> "+PAYMENT_RATIO);
		logger.debug("ACCOUNT_NO >> "+ACCOUNT_NO);
		logger.debug("COMPLETE_DATA >> "+COMPLETE_DATA);		
		if(Util.empty(COMPLETE_DATA)) {
			COMPLETE_DATA = INFO_IS_CORRECT;
		}
		String ACCOUNT_NAME = null;
		String CIS_SRC_STM_CD = null;
		DIHProxy dihProxy = new DIHProxy();
		if(!Util.empty(ACCOUNT_NO)){
			DIHQueryResult<String> queryResult =  dihProxy.getAccountName(ACCOUNT_NO);		
			ACCOUNT_NAME = queryResult.getResult();
			DIHQueryResult<String> dihCIS_SRC_STM_CD= dihProxy.getCisInfoByAccountNo(ACCOUNT_NO, "CIS_SRC_STM_CD");
			if(ResponseData.SUCCESS.equals(dihCIS_SRC_STM_CD.getStatusCode())){
				CIS_SRC_STM_CD = dihCIS_SRC_STM_CD.getResult();
				logger.debug("CIS_SRC_STM_CD >> "+CIS_SRC_STM_CD);
			}
		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();		
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		if(null == paymentMethod){
			paymentMethod = new PaymentMethodDataM();
			String paymentMethodId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
			paymentMethod.init(paymentMethodId);
			paymentMethod.setPersonalId(personalId);
			paymentMethod.setProductType(PRODUCT_CRADIT_CARD);
			applicationGroup.addPaymentMethod(paymentMethod);
		}
		paymentMethod.setPaymentMethod(PAYMENT_METHOD);
		if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())) {
			paymentMethod.setPaymentRatio(FormatUtil.toBigDecimal(PAYMENT_RATIO, true));
			paymentMethod.setAccountNo(ACCOUNT_NO);
			paymentMethod.setAccountName(ACCOUNT_NAME);
			paymentMethod.setCompleteData(COMPLETE_DATA);
			if(!Util.empty(CIS_SRC_STM_CD)){
				if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.CURRENT.getCode())){
					paymentMethod.setAccountType(CURRENT_ACCOUNT_TYPE);
				}else if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.SAVING.getCode())){
					paymentMethod.setAccountType(SAVING_ACCOUNT_TYPE);
				}else if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.FIXED.getCode())){
					paymentMethod.setAccountType(FIX_ACCOUNT_TYPE);
				}else if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.SFIXED.getCode())){
					paymentMethod.setAccountType(SFIX_ACCOUNT_TYPE);
				}  
			}
		}else{
			paymentMethod.setPaymentRatio(null);
			paymentMethod.setAccountNo(null);
			paymentMethod.setAccountName(null);
			paymentMethod.setCompleteData(INFO_IS_CORRECT);
		}		
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				ArrayList<LoanDataM> loans = application.getLoans();
				if(!Util.empty(loans)){
					for(LoanDataM loan : loans){
						loan.setPaymentMethodId(paymentMethod.getPaymentMethodId());
					}
				}
			}
		}		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();		
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		if(null != paymentMethod){
			formError.mandatory(subformId,"PAYMENT_METHOD","PAYMENT_METHOD_"+PRODUCT_CRADIT_CARD,paymentMethod.getPaymentMethod(),request);
			formError.mandatory(subformId,"PAYMENT_RATIO","PAYMENT_RATIO_"+PRODUCT_CRADIT_CARD,paymentMethod.getPaymentRatio(),request);
			formError.mandatory(subformId,"ACCOUNT_NO","ACCOUNT_NO_"+PRODUCT_CRADIT_CARD,paymentMethod.getAccountNo(),request);
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {
		logger.debug("PaymentMethodSubForm.auditForm >> "+subformId);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);

		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.setObjectValue(PERSONAL_TYPE);

		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();		
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
				
		PersonalInfoDataM lastPersonalInfo = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String lastPersonalId = lastPersonalInfo.getPersonalId();		
		PaymentMethodDataM lastPaymentMethod = lastApplicationGroup.getPaymentMethodLifeCycleByPersonalId(lastPersonalId,PRODUCT_CRADIT_CARD);
		
		auditFormUtil.auditForm(subformId, "ACCOUNT_NO", paymentMethod, lastPaymentMethod, request);
		auditFormUtil.auditForm(subformId, "PAYMENT_METHOD", paymentMethod, lastPaymentMethod, request);
		auditFormUtil.auditForm(subformId, "PAYMENT_RATIO", paymentMethod, lastPaymentMethod, request);
		
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
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		if(null != paymentMethod){
//			String PREFIX_ELEMENT_ID = personalInfo.getIdno()+"_"+PRODUCT_CRADIT_CARD;		
			String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getProductRefId(personalInfo,PRODUCT_CRADIT_CARD);
			paymentMethod.setPaymentMethod(formValue.getValue("PAYMENT_METHOD","PAYMENT_METHOD_"+PREFIX_ELEMENT_ID,paymentMethod.getPaymentMethod()));
			paymentMethod.setPaymentRatio(formValue.getValue("PAYMENT_RATIO","PAYMENT_RATIO_"+PREFIX_ELEMENT_ID,paymentMethod.getPaymentRatio()));
			paymentMethod.setAccountNo(formValue.getValue("ACCOUNT_NO","ACCOUNT_NO_"+PREFIX_ELEMENT_ID,paymentMethod.getAccountNo()));
			paymentMethod.setAccountName(formValue.getValue("ACCOUNT_NAME","ACCOUNT_NAME_"+PREFIX_ELEMENT_ID,paymentMethod.getAccountName()));
		}
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("PAYMENT_METHOD");
		filedNames.add("PAYMENT_RATIO");
		filedNames.add("ACCOUNT_NO");
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(),PRODUCT_CRADIT_CARD);
			String paymentType = paymentMethod.getPaymentMethod();
			logger.debug("paymentType>>>>>"+paymentType);
/*			if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentType)){
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
}
