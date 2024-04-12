package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class POSITION_CODEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(POSITION_CODEProperty.class);
	private static String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private static String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private static String CACHE_POSITION_CODE = SystemConstant.getConstant("CACHE_POSITION_CODE");
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		String retValidate="";
		logger.debug(">>mandatoryConfig>>>"+mandatoryConfig);
		if(mandatoryConfig.equals(ValidateFormInf.VALIDATE_SUBMIT)){
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			if(null == personalInfo){
				personalInfo = new PersonalInfoDataM();
			}
			if (GOVERNMENT_OFFICER.equals(personalInfo.getOccupation())) {
				retValidate = ValidateFormInf.VALIDATE_SUBMIT;
			} else {
				retValidate = ValidateFormInf.VALIDATE_NO;
			}
		}
		return retValidate;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("POSITION_CODEProperty.validateForm");
		FormErrorUtil formError = new FormErrorUtil();
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		logger.debug("applicationGroup : " + applicationGroup);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}
		if (GOVERNMENT_OFFICER.equals(personalInfo.getOccupation())) {
			// IF Government !!
			if (Util.empty(personalInfo.getPositionCode())) {
				formError.error("POSITION_CODE", PREFIX_ERROR+LabelUtil.getText(request,"POSITION_CODE"));
			}
		}		
		return formError.getFormError();
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		boolean compareFlag = CompareObject.compare(personalInfo.getPositionCode(), lastpersonalInfo.getPositionCode(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "POSITION_CODE"), request));
			audit.setOldValue(FormatUtil.displayText(getPositionCodeDesc(lastpersonalInfo.getPositionCode())));
			audit.setNewValue(FormatUtil.displayText(getPositionCodeDesc(personalInfo.getPositionCode())));
			audits.add(audit);
		}
		return audits;
	}
	private String getPositionCodeDesc(String code){
		return CacheControl.getName(CACHE_POSITION_CODE, "CODE", code, "VALUE");
	}
}
