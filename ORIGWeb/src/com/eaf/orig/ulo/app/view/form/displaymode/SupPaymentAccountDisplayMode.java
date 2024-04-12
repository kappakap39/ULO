package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;

public class SupPaymentAccountDisplayMode extends DE2FormDisplayMode{
	private static transient Logger logger = Logger.getLogger(SupPaymentAccountDisplayMode.class);
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
		Object masterObjectForm = FormControl.getMasterObjectForm(request);
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
		String personalId = personalApplicationInfo.getPersonalId();
		PaymentMethodDataM paymentMethod = null;
		if(APPLICATION_TYPE_ADD_SUP.equals(personalApplicationInfo.getApplicationType())){
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId);
		}else{
			paymentMethod = personalApplicationInfo.getPaymentMethodLifeCycleByPersonalId(personalId,PRODUCT_CRADIT_CARD);
		}		
		String displayMode = HtmlUtil.VIEW;
		logger.debug("roleId >> "+roleId);
		if(ROLE_DE2.equals(roleId)){
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
				if(!isHasDataPreviousRole(applicationGroup) && PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
					displayMode =  HtmlUtil.EDIT;
				}else{
					displayMode =  HtmlUtil.VIEW;
				}
			}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
				if(!Util.empty(objectElement)&& PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
					displayMode =  HtmlUtil.EDIT;
				}
			}
		}else{
			if(!Util.empty(paymentMethod) && PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
				displayMode =  HtmlUtil.EDIT;
			}
		}
		return displayMode;
	}
}
