package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SUP_CUSTOMER_TYPEProperty extends FieldPropertyHelper {
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String CIDTYPE_PASSPORT=SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String VISA_TYPE_F = SystemConstant.getConstant("VISA_TYPE_F");
	private static transient Logger logger = Logger.getLogger(SUP_CUSTOMER_TYPEProperty.class);
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("SUP_CUSTOMER_TYPEProperty.validateFlag");
		PersonalInfoDataM personalInfo = new PersonalInfoDataM();
		if(FormControl.getObjectForm(request) instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
			if(!Util.empty(applicationGroup)){
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			}
			
		}else if(FormControl.getObjectForm(request) instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalapplicationinfoDataM= (PersonalApplicationInfoDataM)FormControl.getObjectForm(request);
			if(!Util.empty(personalapplicationinfoDataM)){
				personalInfo = personalapplicationinfoDataM.getPersonalInfo();
			}		
		}
		if(!Util.empty(personalInfo)  &&   CIDTYPE_PASSPORT.equals(personalInfo.getCidType()) && !VISA_TYPE_F.equals(personalInfo.getVisaType())){
			return ValidateFormInf.VALIDATE_YES;
			}	
		return ValidateFormInf.VALIDATE_NO;
	}
}

