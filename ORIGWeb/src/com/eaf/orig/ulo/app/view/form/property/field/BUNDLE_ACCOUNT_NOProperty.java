package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.ajax.ValidateBundleAccountNo.ResponseObject;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.DIHAccountResult;
import com.google.gson.Gson;

public class BUNDLE_ACCOUNT_NOProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUNDLE_SME_TYPE_LIMITProperty.class);
	String ACCOUNT_TYPE_LOAN = SystemConstant.getConstant("ACCOUNT_TYPE_LOAN");
	String ACCOUNT_TYPE_CURRENT = SystemConstant.getConstant("ACCOUNT_TYPE_CURRENT");
	String ACCOUNT_STATUS_ACTIVE = SystemConstant.getConstant("ACCOUNT_STATUS_ACTIVE");
	String ACCOUNT_STATUS_SUSPENDED = SystemConstant.getConstant("ACCOUNT_STATUS_SUSPENDED");
	String ACCOUNT_STATUS_APPROVED = SystemConstant.getConstant("ACCOUNT_STATUS_APPROVED");
	String INC_TYPE_BUNDLE_HL = SystemConstant.getConstant("INC_TYPE_BUNDLE_HL");
	String ACCOUNT_TYPE_SAFE = SystemConstant.getConstant("ACCOUNT_TYPE_SAFE");

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
		logger.debug("BUNDLE_ACCOUNT_NOProperty.validateFlag");
		BundleSMEDataM bundleSME = (BundleSMEDataM) objectForm;
		if (!Util.empty(bundleSME)) {
			String ACCOUNT_NO = bundleSME.getAccountNo();
			if (!Util.empty(ACCOUNT_NO)) {
				return ValidateFormInf.VALIDATE_YES;
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}

	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {
		logger.debug("BUNDLE_ACCOUNT_NOProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request, "PREFIX_ERROR");
		String elementFieldId = "BUNDLE_ACCOUNT_NO";
		BundleSMEDataM bundleSME = (BundleSMEDataM) objectForm;
		String ACCOUNT_NO = bundleSME.getAccountNo();
		ACCOUNT_NO = FormatUtil.removeDash(ACCOUNT_NO);

		String TYPE_LIMIT = bundleSME.getTypeLimit();
		FormErrorUtil formError = new FormErrorUtil();

		if (ValidateFormInf.VALIDATE_SAVE.equals(mandatoryConfig) || ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig)) {
			if (Util.empty(ACCOUNT_NO)) {
				formError.error(elementFieldId, PREFIX_ERROR + LabelUtil.getText(request, elementFieldId));
				return formError.getFormError();
			}
		}

		DIHProxy proxy = new DIHProxy();
		DIHQueryResult<DIHAccountResult> queryResult = proxy.getAccountData(FormatUtil.displayText(ACCOUNT_NO), FormatUtil.displayText(TYPE_LIMIT));
		DIHAccountResult dihResult = queryResult.getResult();
		queryResult.setErrorData(dihResult.getErrorData());
		String statusCode = dihResult.getStatusCode();
		if (Util.empty(statusCode))
			statusCode = queryResult.getStatusCode();
		logger.debug("isFoundAccount : " + dihResult.isFoundAccount());
		logger.debug("AccountStatus : " + dihResult.getAccountStatus());
		logger.debug("sourceAccount : " + dihResult.getSourceAccount());
		logger.debug("statusCode : " + statusCode);
		logger.debug("getErrorData : " + dihResult.getErrorData());
		if (ResponseData.SUCCESS.equals(statusCode) && null != dihResult) {
			if (dihResult.isFoundAccount()) {
				if (ACCOUNT_TYPE_LOAN.equals(TYPE_LIMIT)) {
					if (!(ACCOUNT_STATUS_APPROVED.equals(dihResult.getAccountStatus()) || ACCOUNT_STATUS_ACTIVE.equals(dihResult.getAccountStatus()) || ACCOUNT_STATUS_SUSPENDED
							.equals(dihResult.getAccountStatus()))) {
						if (!ACCOUNT_TYPE_SAFE.equals(dihResult.getSourceAccount())) {
							formError.error(elementFieldId, MessageErrorUtil.getText("ERROR_" + elementFieldId));
						}
					}
				} else if (ACCOUNT_TYPE_CURRENT.equals(TYPE_LIMIT) && !ACCOUNT_STATUS_ACTIVE.equals(dihResult.getAccountStatus())) {
					formError.error(elementFieldId, MessageErrorUtil.getText("ERROR_" + elementFieldId));
				}
			} else {
				formError.error(elementFieldId, MessageErrorUtil.getText("ERROR_" + elementFieldId));
			}
		}

		return formError.getFormError();
	}

}
