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

public class BUNDLE_SME_APP_QUALITYProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUNDLE_SME_APP_QUALITYProperty.class);
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
		String LOAN_TYPE_SINGLE = SystemConstant.getConstant("LOAN_TYPE_SINGLE");
		
		String LOAN_TYPE_COBORROWER = SystemConstant.getConstant("LOAN_TYPE_COBORROWER");
		String LOAN_TYPE_COBORROWER_JOINT = SystemConstant.getConstant("LOAN_TYPE_COBORROWER_JOINT");
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		BundleSMEDataM bundleSMEM = (BundleSMEDataM)moduleHandler.getObjectForm();
		if(!Util.empty(bundleSMEM)) {
			String BORROWING_TYPE = bundleSMEM.getBorrowingType();
			if(!Util.empty(BORROWING_TYPE)){
				if(LOAN_TYPE_COBORROWER.equals(BORROWING_TYPE) || LOAN_TYPE_COBORROWER_JOINT.equals(BORROWING_TYPE)){
					return ValidateFormInf.VALIDATE_YES;
				}
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("BUNDLE_SME_APP_QUALITYProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		BundleSMEDataM bundleM = (BundleSMEDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		String LOAN_TYPE_SINGLE = SystemConstant.getConstant("LOAN_TYPE_SINGLE");
		String LOAN_TYPE_COBORROWER = SystemConstant.getConstant("LOAN_TYPE_COBORROWER");
		String LOAN_TYPE_COBORROWER_JOINT = SystemConstant.getConstant("LOAN_TYPE_COBORROWER_JOINT");
		if(!Util.empty(bundleM)) { 
			if(LOAN_TYPE_COBORROWER.equals(bundleM.getBorrowingType()) ||
					LOAN_TYPE_COBORROWER_JOINT.equals(bundleM.getBorrowingType())){
				if(Util.empty(bundleM.getApplicantQuality())) {
					formError.error("APPLICANT_QUALITY",PREFIX_ERROR+LabelUtil.getText(request,"APPLICANT_QUALITY"));
				}
			}
			
		}
		return formError.getFormError();
	}
}
