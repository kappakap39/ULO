package com.eaf.orig.ulo.app.view.form.manual.defualtvalue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.manual.displayvalue.Cis2MakerDisplayValue;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SUP_NATIONALITY_DE1_2DefualtValue  extends Cis2MakerDisplayValue{
	private static final transient Logger logger = Logger.getLogger(SUP_NATIONALITY_DE1_2DefualtValue.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String NATIONALITY_TH = SystemConstant.getConstant("NATIONALITY_TH");
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		elementValue= super.getValue(elementFieldId, elementValue, request);
		ApplicationGroupDataM  applicationGroupDataM =  (ApplicationGroupDataM) objectForm;
		if(Util.empty(applicationGroupDataM)){
			applicationGroupDataM = new ApplicationGroupDataM();
		}
		PersonalInfoDataM personalInfo = applicationGroupDataM.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(elementValue) && !Util.empty(personalInfo.getCidType()) && !CIDTYPE_PASSPORT.equals(personalInfo.getCidType())){
			elementValue=NATIONALITY_TH;
		}
		logger.debug("SUP_NATIONALITY_DE1_2DefualtValue>>"+elementValue);
		return elementValue;
	}
}
