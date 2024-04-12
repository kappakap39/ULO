package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CURRENT_ADRSTSProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(CURRENT_ADRSTSProperty.class);
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
		logger.debug("mandatoryConfig >> "+mandatoryConfig);
		if(ValidateFormInf.VALIDATE_SUBMIT.equals(mandatoryConfig)){
			String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
			Object masterObjectForm = FormControl.getMasterObjectForm(request);		
			PersonalInfoDataM personalInfo = null;
			if(masterObjectForm instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
				PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
				personalInfo = personalApplicationInfo.getPersonalInfo();
			}
			if(!Util.empty(personalInfo)) {
				if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())) {
					return ValidateFormInf.VALIDATE_YES;
				}
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}
		if(!Util.empty(personalInfo)) {
			HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
			AddressDataM currentAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);
			if(null == currentAddress){
				currentAddress = new AddressDataM();
			}
			if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) &&
					Util.empty(currentAddress.getAdrsts())) {
				formError.error("ADRSTS",PREFIX_ERROR+LabelUtil.getText(request,"ADRSTS"));
			}
		}
		return formError.getFormError();
	}
}
