package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SelfDeclareSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(SelfDeclareSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String subformId = "SELF_DECLARE_SUBFORM";
	@Override
	public void setProperties(HttpServletRequest request,Object appForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String NUMBER_OF_ISSUER = request.getParameter("NUMBER_OF_ISSUER");
		String SELF_DECLARE_LIMIT = request.getParameter("SELF_DECLARE_LIMIT");
		logger.debug("NUMBER_OF_ISSUER : "+NUMBER_OF_ISSUER);
		logger.debug("SELF_DECLARE_LIMIT : "+SELF_DECLARE_LIMIT);
		personalInfo.setNumberOfIssuer(FormatUtil.toBigDecimal(NUMBER_OF_ISSUER,true));
		personalInfo.setSelfDeclareLimit(FormatUtil.toBigDecimal(SELF_DECLARE_LIMIT,true));
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,Object appForm){
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId,"NUMBER_OF_ISSUER",personalInfo.getNumberOfIssuer(),request);
		formError.mandatory(subformId,"SELF_DECLARE_LIMIT",personalInfo.getSelfDeclareLimit(),request);
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
}
