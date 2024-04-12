package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;

public class SUP_PAYMENT_ACCOUNT_NOProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(SUP_PAYMENT_ACCOUNT_NOProperty.class);	
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String PAYMENT_METHOD_DIRECT_DEBIT_TYPE = SystemConstant.getConstant("PAYMENT_METHOD_DIRECT_DEBIT_TYPE");
	String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
	String ELEMENT_ID;
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		Object masterObjectForm = getMasterObjectForm();
		if(null == masterObjectForm){
			masterObjectForm = FormControl.getMasterObjectForm(request);
		}
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
		if(Util.empty(personalApplicationInfo)){
			personalApplicationInfo = new PersonalApplicationInfoDataM();
		}
		String personalId = personalApplicationInfo.getPersonalId();
		
//		PaymentMethodDataM paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
				
		PaymentMethodDataM paymentMethod = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(personalApplicationInfo.getApplicationType())){
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId);
		}else{
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		}
		
		logger.debug("paymentMethod "+paymentMethod);		
		if(!Util.empty(paymentMethod) && PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
			return ValidateFormInf.VALIDATE_YES;
		} 
		return ValidateFormInf.VALIDATE_NO;
	}	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		logger.debug("SUP_PAYMENT_ACCOUNT_NOProperty.auditForm");
		String personalType = (String)objectValue;
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		try{
			PaymentMethodDataM paymentMethod = (PaymentMethodDataM)objectForm;
			PaymentMethodDataM lastpaymentMethod = (PaymentMethodDataM)lastObjectForm;			
			if(Util.empty(paymentMethod)){
				paymentMethod = new PaymentMethodDataM();
			}
			if(Util.empty(lastpaymentMethod)){
				lastpaymentMethod = new PaymentMethodDataM();
			}			
			if(PAYMENT_METHOD_DIRECT_DEBIT_TYPE.equals(paymentMethod.getPaymentMethod())){
				if("PAYMENT_RATIO".equals(ELEMENT_ID)){
					boolean compareAccountNo = CompareObject.compare(paymentMethod.getPaymentRatio(), lastpaymentMethod.getPaymentRatio(),null);
					if(!compareAccountNo){
						AuditDataM audit = new AuditDataM();
						audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "PAYMENT_RATIO"), request));
						audit.setOldValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_PAYMENT_RATIO, String.valueOf(lastpaymentMethod.getPaymentRatio()))));
						audit.setNewValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_PAYMENT_RATIO, String.valueOf(paymentMethod.getPaymentRatio()))));
						audits.add(audit);
					}		
				}
				if("ACCOUNT_NO".equals(ELEMENT_ID)){
					boolean compareAccountNo = CompareObject.compare(paymentMethod.getAccountNo(), lastpaymentMethod.getAccountNo(),null);
					if(!compareAccountNo){
						AuditDataM audit = new AuditDataM();
						audit.setFieldName(AuditFormUtil.getAuditFieldName(personalType, LabelUtil.getText(request, "ACCOUNT_NO"), request));
						audit.setOldValue(FormatUtil.displayText(lastpaymentMethod.getAccountNo()));
						audit.setNewValue(FormatUtil.displayText(paymentMethod.getAccountNo()));
						audits.add(audit);
					}		
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR >> ",e);
		}
		return audits;
	}
	@Override
	public void auditInfo(String subformId, String elementId) {
		this.ELEMENT_ID = elementId;
	}
}
