package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IDNO_EXPIRE_DATEProperty extends FieldPropertyHelper{
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String CIDTYPE_IDCARD=SystemConstant.getConstant("CIDTYPE_IDCARD");
	private static transient Logger logger = Logger.getLogger(IDNO_EXPIRE_DATEProperty.class);
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("IDNO_EXPIRE_DATE_Proerties.validateFlag");
		PersonalApplicationInfoDataM personalapplicationinfoDataM= (PersonalApplicationInfoDataM)FormControl.getObjectForm(request);
		PersonalInfoDataM personalInfo = personalapplicationinfoDataM.getPersonalInfo();
		logger.debug("#####"+personalInfo.getCidType());
		if(CIDTYPE_IDCARD.equals(personalInfo.getCidType())){
			return ValidateFormInf.VALIDATE_YES;
		}
		return ValidateFormInf.VALIDATE_NO;
	}
}
