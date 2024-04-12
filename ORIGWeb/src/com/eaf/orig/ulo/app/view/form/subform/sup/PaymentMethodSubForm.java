package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
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
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;

@SuppressWarnings("serial")
public class PaymentMethodSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PaymentMethodSubForm.class);
	String subformId = "SUP_PAYMENT_METHOD_SUBFORM";
	String NORMAL_APPLICATION_FORM = SystemConstant.getConstant("NORMAL_APPLICATION_FORM");
	String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String SAVING_ACCOUNT_TYPE = SystemConstant.getConstant("SAVING_ACCOUNT_TYPE");
	String CURRENT_ACCOUNT_TYPE = SystemConstant.getConstant("CURRENT_ACCOUNT_TYPE");
	String FIX_ACCOUNT_TYPE = SystemConstant.getConstant("FIX_ACCOUNT_TYPE");
	String SFIX_ACCOUNT_TYPE = SystemConstant.getConstant("SFIX_ACCOUNT_TYPE");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		Object masterObjectForm = FormControl.getMasterObjectForm(request);			
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
		String personalId = personalApplicationInfo.getPersonalId();		
		PaymentMethodDataM paymentMethod = null;
		String CIS_SRC_STM_CD = null;
		String applicationType = personalApplicationInfo.getApplicationType();
		logger.debug("applicationType >> "+applicationType);
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId);
			if(Util.empty(paymentMethod)) {
				paymentMethod = new PaymentMethodDataM();
				String paymentMethodId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
				paymentMethod.init(paymentMethodId);
				paymentMethod.setPersonalId(personalId);
				paymentMethod.setProductType(PRODUCT_CRADIT_CARD);
				personalApplicationInfo.addPaymentMethod(paymentMethod);
			}	
		}else{
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
			if(Util.empty(paymentMethod)) {
				paymentMethod = new PaymentMethodDataM();
				String paymentMethodId= GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK);
				paymentMethod.init(paymentMethodId);
				paymentMethod.setPersonalId(personalId);
				paymentMethod.setProductType(PRODUCT_CRADIT_CARD);
				personalApplicationInfo.addPaymentMethod(paymentMethod);
			}	
		}
		String PAYMENT_METHOD = request.getParameter("PAYMENT_METHOD");
		String PAYMENT_RATIO = request.getParameter("PAYMENT_RATIO");
		String ACCOUNT_NO = FormatUtil.removeDash(request.getParameter("ACCOUNT_NO"));
		String COMPLETE_DATA = request.getParameter("COMPLETE_DATA");
		String ACCOUNT_NAME = request.getParameter("ACCOUNT_NAME");	
		
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
		if(Util.empty(COMPLETE_DATA)) {
			COMPLETE_DATA = INFO_IS_CORRECT;
		}
		logger.debug("PAYMENT_METHOD >> " + PAYMENT_METHOD);
		logger.debug("PAYMENT_RATIO >> " + PAYMENT_RATIO);
		logger.debug("ACCOUNT_NO >> " + ACCOUNT_NO);
		logger.debug("COMPLETE_DATA >> " + COMPLETE_DATA);
		logger.debug("ACCOUNT_NAME >> " + ACCOUNT_NAME);		
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
		if(APPLICATION_TYPE_ADD_SUP.equals(personalApplicationInfo.getApplicationType())){
			ArrayList<ApplicationDataM> applications = personalApplicationInfo.filterApplicationRelationLifeCycle(personalId);
			if(!Util.empty(applications)){
				for (ApplicationDataM application : applications) {
					ArrayList<LoanDataM> loans = application.getLoans();
					if(!Util.empty(loans)){
						for(LoanDataM loan : loans){
							loan.setPaymentMethodId(paymentMethod.getPaymentMethodId());
						}
					}
				}
			}
		}else{
			ArrayList<ApplicationDataM> applications = personalApplicationInfo.filterApplicationRelationLifeCycle(personalId,PRODUCT_CRADIT_CARD);
			if(!Util.empty(applications)){
				for (ApplicationDataM application : applications) {
					ArrayList<LoanDataM> loans = application.getLoans();
					if(!Util.empty(loans)){
						for(LoanDataM loan : loans){
							loan.setPaymentMethodId(paymentMethod.getPaymentMethodId());
						}
					}
				}
			}
		}				
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("subformId >> "+subformId);		
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		String personalId = personalApplicationInfo.getPersonalId();
		FormErrorUtil formError = new FormErrorUtil(personalApplicationInfo);		
//		PaymentMethodDataM paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		PaymentMethodDataM paymentMethod = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(personalApplicationInfo.getApplicationType())){
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId);
		}else{
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		}
		if(Util.empty(paymentMethod)) {
			paymentMethod = new PaymentMethodDataM();
		}
		formError.mandatory(subformId,"PAYMENT_METHOD",paymentMethod.getPaymentMethod(),request);
		formError.mandatory(subformId,"PAYMENT_RATIO",paymentMethod.getPaymentRatio(),request);
		formError.mandatory(subformId,"ACCOUNT_NO",paymentMethod.getAccountNo(),request);		
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
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		String personalId = personalInfo.getPersonalId();		
//		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		PaymentMethodDataM paymentMethod = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())){
			paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId);
		}else{
			paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		}
				
		PersonalInfoDataM lastPersonalInfo = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		String lastPersonalId = lastPersonalInfo.getPersonalId();		
//		PaymentMethodDataM lastPaymentMethod = lastApplicationGroup.getPaymentMethodLifeCycleByPersonalId(lastPersonalId,PRODUCT_CRADIT_CARD);
		PaymentMethodDataM lastPaymentMethod = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(lastApplicationGroup.getApplicationType())){
			lastPaymentMethod = lastApplicationGroup.getPaymentMethodLifeCycleByPersonalId(lastPersonalId);
		}else{
			lastPaymentMethod = lastApplicationGroup.getPaymentMethodLifeCycleByPersonalId(lastPersonalId,PRODUCT_CRADIT_CARD);
		}
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.setObjectValue(PERSONAL_TYPE_SUPPLEMENTARY);
		auditFormUtil.auditForm(subformId,"ACCOUNT_NO", paymentMethod, lastPaymentMethod, request);
		auditFormUtil.auditForm(subformId,"PAYMENT_METHOD", paymentMethod, lastPaymentMethod, request);
		auditFormUtil.auditForm(subformId,"PAYMENT_RATIO", paymentMethod, lastPaymentMethod, request);
		
		return auditFormUtil.getAuditForm();
	}	
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		logger.debug("displayValueForm.subformId >> "+subformId);		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String personalId = getSubformData("PERSONAL_ID");		
		logger.debug("formId >> "+formId);
		logger.debug("formLevel >> "+formLevel);
		logger.debug("roleId >> "+roleId);
		logger.debug("subformId >> "+subformId);
		logger.debug("personalId >> "+personalId);		
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
//		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		PaymentMethodDataM paymentMethod = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())){
			paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId);
		}else{
			paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		}
		if(null == paymentMethod){
			paymentMethod = new PaymentMethodDataM();
		}
//		String PREFIX_ELEMENT_ID = personalInfo.getIdno()+"_"+PRODUCT_CRADIT_CARD;		
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getProductRefId(personalInfo,PRODUCT_CRADIT_CARD);
		paymentMethod.setPaymentMethod(formValue.getValue("PAYMENT_METHOD","PAYMENT_METHOD_"+PREFIX_ELEMENT_ID,paymentMethod.getPaymentMethod()));
		paymentMethod.setPaymentRatio(formValue.getValue("PAYMENT_RATIO","PAYMENT_RATIO_"+PREFIX_ELEMENT_ID,paymentMethod.getPaymentRatio()));
		paymentMethod.setAccountNo(formValue.getValue("ACCOUNT_NO","ACCOUNT_NO_"+PREFIX_ELEMENT_ID,paymentMethod.getAccountNo()));
		paymentMethod.setAccountName(formValue.getValue("ACCOUNT_NAME","ACCOUNT_NAME_"+PREFIX_ELEMENT_ID,paymentMethod.getAccountName()));
	}
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("PAYMENT_METHOD");
		try {		
			ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);
			PersonalInfoDataM personalInfo =  new PersonalInfoDataM();
			
			if(objectForm instanceof PersonalInfoDataM){
				personalInfo = (PersonalInfoDataM)objectForm;
			}else{
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			}
			PaymentMethodDataM paymentMethod = null;
			if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())){
				paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId());
			}else{
				paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(),PRODUCT_CRADIT_CARD);
			}
			if(Util.empty(paymentMethod)){
				paymentMethod = new PaymentMethodDataM();
			}
			
			String paymentMethodType = paymentMethod.getPaymentMethod();		
			logger.debug("paymentMethodType>>>"+paymentMethodType);
			if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethodType)){
				filedNames.add("PAYMENT_RATIO");
				filedNames.add("ACCOUNT_NO");
			}
			
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
