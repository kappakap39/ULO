package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WisdomDataM;

public class REQ_PRIORITY_PASSProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(REQ_PRIORITY_PASSProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("REQ_PRIORITY_PASSProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		WisdomDataM wisdomCard = (WisdomDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
		PersonalInfoDataM personalInfo =  PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
		}
		String requiredFlag = verificationResult.getRequirePriorityPass();
		logger.debug("RequirePriorityPass>>>"+requiredFlag);
		
		if(MConstant.FLAG.YES.equals(requiredFlag)) {
			if(Util.empty(wisdomCard.getReqPriorityPassSup())){
				formError.error("REQ_PRIORITY_PASS_SUP",PREFIX_ERROR+LabelUtil.getText(request,"REQ_PRIORITY_PASS_SUP"));
			}
		}
		return formError.getFormError();
	}
}
