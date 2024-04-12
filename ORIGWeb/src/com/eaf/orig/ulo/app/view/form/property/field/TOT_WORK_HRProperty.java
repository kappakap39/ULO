package com.eaf.orig.ulo.app.view.form.property.field;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class TOT_WORK_HRProperty extends FieldPropertyHelper  {
	private static transient Logger logger = Logger.getLogger(TOT_WORK_HRProperty.class);
	String TOTAL_WORK_HR_QUESTION = SystemConstant.getConstant("TOTAL_WORK_HR_QUESTION");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String PHONE_VER_STATUS_Y = SystemConstant.getConstant("PHONE_VER_STATUS_Y");
	String PERMANENT_STAFF_QUESTION = SystemConstant.getConstant("PERMANENT_STAFF_QUESTION");
	String PERMANENT_STAFF_Y = SystemConstant.getConstant("PERMANENT_STAFF_Y");
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("TOT_WORK_HRProperty.validateFlag");
		String FLAG = ValidateFormInf.VALIDATE_NO;
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)FormControl.getOrigObjectForm(request);
		String APPLICATION_TYPE = applicationGroup.getApplicationType();

		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		
		HRVerificationDataM hrVerification = verificationResult.getLastHrVerification();
		if(!Util.empty(hrVerification)){
			String STATUS = hrVerification.getPhoneVerStatus();
			logger.debug("hrVerification.getPhoneVerStatus() : "+STATUS);
			if(PHONE_VER_STATUS_Y.equals(STATUS)){
				IdentifyQuestionDataM PERMANENT_STAFF = verificationResult.getIndentifyQuesitionNo(PERMANENT_STAFF_QUESTION);
				if(!Util.empty(PERMANENT_STAFF)){
					logger.debug("PERMANENT_STAFF.getCustomerAnswer() : "+PERMANENT_STAFF.getCustomerAnswer());
					if(PERMANENT_STAFF_Y.equals(PERMANENT_STAFF.getCustomerAnswer())){
						FLAG = ValidateFormInf.VALIDATE_YES;
					}
				}
			}
		}
		
		logger.debug("APPLICATION_TYPE : "+APPLICATION_TYPE);
		if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE) || APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){
			FLAG = ValidateFormInf.VALIDATE_NO;
		}
		return FLAG;
	}
	
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();
		BigDecimal TOT_WORK_YEAR =  FormatUtil.toBigDecimal(request.getParameter("TOT_WORK_YEAR_"+TOTAL_WORK_HR_QUESTION));
		BigDecimal TOT_WORK_MONTH =  FormatUtil.toBigDecimal(request.getParameter("TOT_WORK_MONTH_"+TOTAL_WORK_HR_QUESTION));
		
		logger.debug("TOT_WORK_YEAR : "+TOT_WORK_YEAR);
		logger.debug("TOT_WORK_MONTH : "+TOT_WORK_MONTH);
 
		if ((TOT_WORK_YEAR.compareTo(BigDecimal.ZERO) ==0) && (TOT_WORK_MONTH.compareTo(BigDecimal.ZERO)==0)){
			logger.debug("ERROR TOTAL WORK ");
			formError.error("TOT_WORK", MessageErrorUtil.getText(request, "ERROR_INVALID_TOT_WORK"));
		}
		return formError.getFormError();
	}
}
