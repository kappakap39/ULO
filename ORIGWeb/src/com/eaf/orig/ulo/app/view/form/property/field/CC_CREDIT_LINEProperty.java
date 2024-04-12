package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;

public class CC_CREDIT_LINEProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(CC_CREDIT_LINEProperty.class);
	String PRODUCT_CRADIT_CARD =  SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		String validate = ValidateFormInf.VALIDATE_NO;;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ApplicationDataM application = applicationGroup.getApplicationProduct(PRODUCT_CRADIT_CARD);
		if(!Util.empty(application)){
			validate = ValidateFormInf.VALIDATE_SUBMIT;
		}
		return validate;
	}
	
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		BundleHLDataM bundleHL = (BundleHLDataM)objectForm;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ApplicationDataM application = applicationGroup.getApplicationById(bundleHL.getApplicationRecordId());
		
		FormErrorUtil formError = new FormErrorUtil();
		if(PRODUCT_CRADIT_CARD.equals(application.getProduct()) && Util.empty(bundleHL.getCcApprovedAmt())){
			formError.error("CC_CREDIT_LINE",PREFIX_ERROR+LabelUtil.getText(request,"CC_CREDIT_LINE"));
		}
		return formError.getFormError();
	}
}
