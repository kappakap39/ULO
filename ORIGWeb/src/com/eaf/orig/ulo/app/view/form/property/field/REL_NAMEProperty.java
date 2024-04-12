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
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.KYCDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class REL_NAMEProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(REL_NAMEProperty.class);
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("REL_NAMEProperty.validateFlag...");	
		Object masterObjectForm = getMasterObjectForm();
		if(null == masterObjectForm){
			masterObjectForm = FormControl.getMasterObjectForm(request);
		}
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}		
		logger.debug("personalInfo.getKyc() >> "+personalInfo.getKyc());
		KYCDataM kyc = personalInfo.getKyc();
		if(Util.empty(kyc)){
			kyc = new KYCDataM();
		}
		if(MConstant.FLAG.YES.equals(kyc.getRelationFlag())){
			return ValidateFormInf.VALIDATE_SUBMIT;
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("REL_NAMEProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		KYCDataM kycd = (KYCDataM)objectForm;		 
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(kycd.getRelTitleName()) || Util.empty(kycd.getRelName())){
			formError.error("REL_NAME",PREFIX_ERROR+LabelUtil.getText(request,"REL_NAME"));
		}
		return formError.getFormError();
	}
}
