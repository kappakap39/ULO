package com.eaf.orig.ulo.app.view.form.property.field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;

public class BUNDLE_HL_CREDITLINEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUNDLE_HL_CREDITLINEProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("BUNDLE_HL_CREDITLINEProperty.validateForm");
		String PRODUCT_CRADIT_CARD =  SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String PRODUCT_K_EXPRESS_CASH =  SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		BundleHLDataM bundleHLM = (BundleHLDataM)objectForm;
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationDataM appM = ORIGForm.getObjectForm().getApplicationById(bundleHLM.getApplicationRecordId());
		ArrayList<ApplicationDataM> applications =  ORIGForm.getObjectForm().getApplicationLifeCycle(new BigDecimal(appM.getLifeCycle()));
		FormErrorUtil formError = new FormErrorUtil();
		for(ApplicationDataM application: applications){
			if(PRODUCT_K_EXPRESS_CASH.equals(application.getProduct()) && Util.empty(bundleHLM.getKecApprovedAmt())){
				formError.error("KEC_CREDIT_LINE",PREFIX_ERROR+LabelUtil.getText(request,"KEC_CREDIT_LINE"));
			}else if(PRODUCT_CRADIT_CARD.equals(application.getProduct()) && Util.empty(bundleHLM.getCcApprovedAmt())){
				formError.error("CC_CREDIT_LINE",PREFIX_ERROR+LabelUtil.getText(request,"CC_CREDIT_LINE"));
			}
		}
		
		return formError.getFormError();
	}
}
