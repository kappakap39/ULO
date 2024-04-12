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
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class BUNDLE_SME_BUS_CODEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUNDLE_SME_BUS_CODEProperty.class);
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
		String LOAN_TYPE_COBORROWER = SystemConstant.getConstant("LOAN_TYPE_COBORROWER");
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		BundleSMEDataM bundleSMEM = (BundleSMEDataM)moduleHandler.getObjectForm();
		if(!Util.empty(bundleSMEM)) {
			String BORROWING_TYPE = bundleSMEM.getBorrowingType();
			if(LOAN_TYPE_COBORROWER.equals(BORROWING_TYPE)){
				return ValidateFormInf.VALIDATE_YES;
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("BUNDLE_SME_BUS_CODEProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		BundleSMEDataM bundleM = (BundleSMEDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		String borrowing_type = SystemConstant.getConstant("LOAN_TYPE_COBORROWER");
		if(!Util.empty(bundleM) && borrowing_type.equals(bundleM.getBorrowingType())){
			if(Util.empty(bundleM.getBusinessCode())) {
				formError.error("BUSINESS_CODE",PREFIX_ERROR+LabelUtil.getText(request,"BUSINESS_CODE"));
			}
		}
		return formError.getFormError();
	}
}
