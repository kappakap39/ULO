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

public class BUNDLE_SME_G_EXIST_PAYMENTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUNDLE_SME_G_EXIST_PAYMENTProperty.class);
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
		logger.debug("BUNDLE_SME_G_EXIST_PAYMENTProperty.validateFlag");
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
					if((Util.empty(bundleSMEM.getVerifiedIncome()) && Util.empty(bundleSMEM.getgTotExistPayment()))
							|| (!Util.empty(bundleSMEM.getVerifiedIncome()) && !Util.empty(bundleSMEM.getgTotExistPayment()))){
						return ValidateFormInf.VALIDATE_YES;
					}
				}
				
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("BUNDLE_SME_G_EXIST_PAYMENTProperty.validateForm");
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
				if(Util.empty(bundleM.getgTotExistPayment())) {
					formError.error("G_TOTAL_EXIST_PAYMENT",PREFIX_ERROR+LabelUtil.getText(request,"G_TOTAL_EXIST_PAYMENT"));
				}
			}
			else if(LOAN_TYPE_SINGLE.equals(bundleM.getBorrowingType())){
				logger.debug("bundleM.getgDscrReq() :: " + bundleM.getgDscrReq());
				logger.debug("bundleM.getgTotExistPayment() :: " + bundleM.getgTotExistPayment());
				logger.debug("bundleM.getgTotNewPayReq() :: " + bundleM.getgTotNewPayReq());
				logger.debug("bundleM.getVerifiedIncome() :: " + bundleM.getVerifiedIncome());
				if(Util.empty(bundleM.getgDscrReq()) && Util.empty(bundleM.getgTotExistPayment())
						&& Util.empty(bundleM.getgTotNewPayReq()) && Util.empty(bundleM.getVerifiedIncome())){
					formError.error("G_TOTAL_EXIST_PAYMENT",ERROR_NOT_FILL_VERIFIED_INCOME_G_FIELD);
				}
				else if((!Util.empty(bundleM.getgDscrReq()) || !Util.empty(bundleM.getgTotExistPayment())
						|| !Util.empty(bundleM.getgTotNewPayReq())) 
						&& !Util.empty(bundleM.getVerifiedIncome())){
					logger.debug("111");
					formError.error("G_TOTAL_EXIST_PAYMENT",ERROR_ALL_IN_VERIFIED_INCOME_G_FIELD);
				}
				else if(!Util.empty(bundleM.getgDscrReq()) || !Util.empty(bundleM.getgTotNewPayReq())){
					if(Util.empty(bundleM.getgTotExistPayment())) {
						formError.error("G_TOTAL_EXIST_PAYMENT",ERROR_G_FIELD);
					}
				}
			}
		}
		return formError.getFormError();
	}
}
