package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VrHrResultProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(VrHrResultProperty.class);
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("VrHrResultProperty.validateFlag");
		String FLAG = ValidateFormInf.VALIDATE_NO;
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		
		if(!Util.empty(verificationResult)){
			HRVerificationDataM hrVerification = verificationResult.getLastHrVerification();
			if(!Util.empty(hrVerification)){
				String STATUS = hrVerification.getPhoneVerStatus();
				logger.debug("STATUS : "+STATUS);
				if(MConstant.FLAG_Y.equals(STATUS)){
					FLAG = ValidateFormInf.VALIDATE_YES;
				}else{
					FLAG = ValidateFormInf.VALIDATE_NO;
				}
			}
		}
		return FLAG;
	}
}
