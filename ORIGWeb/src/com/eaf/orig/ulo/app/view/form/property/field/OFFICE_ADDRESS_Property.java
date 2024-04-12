package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class OFFICE_ADDRESS_Property extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(OFFICE_ADDRESS_Property.class);
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}

	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("OFFICE_ADDRESS_Property.validateFlag...");
		logger.debug("mandatoryConfig >> "+mandatoryConfig);
		if(mandatoryConfig.equals(ValidateFormInf.VALIDATE_SUBMIT)){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
		PersonalInfoDataM personalInfo = null;
		personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
		Object masterObject = FormControl.getMasterObjectForm(request);
		if(masterObject instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfoDataM = (PersonalApplicationInfoDataM)masterObject;
			if(Util.empty(personalApplicationInfoDataM)){
				personalApplicationInfoDataM = new PersonalApplicationInfoDataM();
			}
			personalInfo = personalApplicationInfoDataM.getPersonalInfo();
		}
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}	
//		if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
			return ValidateFormInf.VALIDATE_YES;
//			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
}
