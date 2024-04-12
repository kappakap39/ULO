package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;

public class KECPaymentAccountDisplayMode extends DE2FormDisplayMode{
	private static transient Logger logger = Logger.getLogger(KECPaymentAccountDisplayMode.class);
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getObjectForm(request);
		PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalType(PERSONAL_TYPE, PRODUCT_K_EXPRESS_CASH);		
		String displayMode = HtmlUtil.VIEW;
		logger.debug("roleId >> "+roleId);
		if(ROLE_DE2.equals(roleId)){
			if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
				if(MConstant.FLAG_Y.equals(applicationGroup.foundLowIncome())){
					displayMode =  HtmlUtil.VIEW;
				}else if(!isHasDataPreviousRole(applicationGroup) && !Util.empty(paymentMethod) 
						&& PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
					displayMode =  HtmlUtil.EDIT;
				}else{
					displayMode =  HtmlUtil.VIEW;
				}
			}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
				if(!Util.empty(objectElement) && !Util.empty(paymentMethod) 
						&& PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
					displayMode =  HtmlUtil.EDIT;
				}
			}
		}else{
			if(!Util.empty(paymentMethod) && PAYMENT_METHOD_DEPOSIT_ACCOUNT.equals(paymentMethod.getPaymentMethod())){
				displayMode =  HtmlUtil.EDIT;
			}
		}
		logger.debug(displayMode);
		return displayMode;
	}
}
