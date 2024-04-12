package com.eaf.orig.ulo.app.view.form.subform.sup;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SupPaymentMethodSubForm extends PaymentMethodSubForm {
	private static transient Logger logger = Logger.getLogger(SupPaymentMethodSubForm.class);
	String FormId = SystemConstant.getConstant("SUP_PRODUCT_FORM_CC");
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm) {
		String formId = FormControl.getOrigFormId(request);
		logger.debug("formId >> "+formId);
		logger.debug("SUP_CARD_SEPARATELY_FORM >> "+SUP_CARD_SEPARATELY_FORM);
		if(Util.empty(formId) || SUP_CARD_SEPARATELY_FORM.equals(formId)){
			return MConstant.FLAG_Y;
		}
		return MConstant.FLAG_N;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		logger.debug("displayValueForm.subformId >> "+subformId);		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String personalId = getSubformData("PERSONAL_ID");
		
		logger.debug("formId >> "+FormId);
		logger.debug("formLevel >> "+formLevel);
		logger.debug("roleId >> "+roleId);
		logger.debug("subformId >> "+subformId);
		logger.debug("personalId >> "+personalId);		
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(FormId,formLevel,roleId,subformId,applicationGroup,request);
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
		filedNames.add("SUP_PAYMENT_METHOD");
		try {		
			ApplicationGroupDataM applicationGroup =  FormControl.getOrigObjectForm(request);
			PersonalInfoDataM personalInfo =  new PersonalInfoDataM();
			
			if(objectForm instanceof PersonalInfoDataM){
				personalInfo = (PersonalInfoDataM)objectForm;
			}else{
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			}
			PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId());			 
			if(Util.empty(paymentMethod)){
				paymentMethod = new PaymentMethodDataM();
			}
			
			String paymentMethodType = paymentMethod.getPaymentMethod();		
			logger.debug("paymentMethodType>>>"+paymentMethodType);
//			if(PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethodType)){
				filedNames.add("PAYMENT_RATIO");
				filedNames.add("ACCOUNT_NO");
//			}
			
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
				 fieldElement.setElementGroupId(personalInfo.getPersonalId());
				 fieldElement.setElementGroupLevel(CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY);
				 fieldElements.add(fieldElement);
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
