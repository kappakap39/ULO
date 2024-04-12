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

public class BUNDLE_SME_G_DSCR_REQProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUNDLE_SME_G_DSCR_REQProperty.class);
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
		logger.debug("BUNDLE_SME_G_DSCR_REQProperty.validateFlag");
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		BundleSMEDataM bundleSMEM = (BundleSMEDataM)moduleHandler.getObjectForm();
		if(!Util.empty(bundleSMEM)) {
			String BORROWING_TYPE = bundleSMEM.getBorrowingType();
			logger.debug("BORROWING_TYPE :"+BORROWING_TYPE);
			String LOAN_TYPE_SINGLE = SystemConstant.getConstant("LOAN_TYPE_SINGLE");
			String LOAN_TYPE_COBORROWER_JOINT = SystemConstant.getConstant("LOAN_TYPE_COBORROWER_JOINT");
			if(!Util.empty(BORROWING_TYPE)) { 
				if(LOAN_TYPE_COBORROWER_JOINT.equals(BORROWING_TYPE)){
					return ValidateFormInf.VALIDATE_YES;
				}
				else if(LOAN_TYPE_SINGLE.equals(BORROWING_TYPE)){
					if((Util.empty(bundleSMEM.getVerifiedIncome()) && Util.empty(bundleSMEM.getgDscrReq()))
							|| (!Util.empty(bundleSMEM.getVerifiedIncome()) && !Util.empty(bundleSMEM.getgDscrReq()))){
						return ValidateFormInf.VALIDATE_YES;
					}
				}
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("BUNDLE_SME_G_DSCR_REQProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		String ERROR_ALL_IN_VERIFIED_INCOME_G_FIELD = MessageErrorUtil.getText(request,"ERROR_ALL_IN_VERIFIED_INCOME_G_FIELD");
		String ERROR_NOT_FILL_VERIFIED_INCOME_G_FIELD = MessageErrorUtil.getText(request,"ERROR_NOT_FILL_VERIFIED_INCOME_G_FIELD");
		String ERROR_G_FIELD = MessageErrorUtil.getText(request,"ERROR_G_FIELD");
		BundleSMEDataM bundleM = (BundleSMEDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		String LOAN_TYPE_SINGLE = SystemConstant.getConstant("LOAN_TYPE_SINGLE");
		String LOAN_TYPE_COBORROWER_JOINT = SystemConstant.getConstant("LOAN_TYPE_COBORROWER_JOINT");
		if(!Util.empty(bundleM)) {    
			if(LOAN_TYPE_COBORROWER_JOINT.equals(bundleM.getBorrowingType())){
				if(Util.empty(bundleM.getgDscrReq())) {
					formError.error("G_DSCR_REQ",PREFIX_ERROR+LabelUtil.getText(request,"G_DSCR_REQ"));
				}
			}
			else if(LOAN_TYPE_SINGLE.equals(bundleM.getBorrowingType())){
				if(Util.empty(bundleM.getgDscrReq()) && Util.empty(bundleM.getgTotExistPayment())
						&& Util.empty(bundleM.getgTotNewPayReq()) && Util.empty(bundleM.getVerifiedIncome())){
					formError.error("G_DSCR_REQ",ERROR_NOT_FILL_VERIFIED_INCOME_G_FIELD);
				}
				else if((!Util.empty(bundleM.getgDscrReq()) || !Util.empty(bundleM.getgTotExistPayment())
						|| !Util.empty(bundleM.getgTotNewPayReq())) 
						&& !Util.empty(bundleM.getVerifiedIncome())){
					formError.error("G_DSCR_REQ",ERROR_ALL_IN_VERIFIED_INCOME_G_FIELD);
				}
				else if(!Util.empty(bundleM.getgTotExistPayment()) || !Util.empty(bundleM.getgTotNewPayReq())){
					if(Util.empty(bundleM.getgDscrReq())) {
						formError.error("G_DSCR_REQ",ERROR_G_FIELD);
					}
				}
			}
		}
		return formError.getFormError();
	}
}
