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
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class POSITION_LEVELProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(POSITION_LEVELProperty.class);
	private static String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private static String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private static String FIELD_ID_POSITION_LEVEL = SystemConstant.getConstant("FIELD_ID_POSITION_LEVEL");
	
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
			if (!GOVERNMENT_OFFICER.equals(personalInfo.getOccupation())) {
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
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(null == personalInfo){
			personalInfo = new PersonalInfoDataM();
		}		
		if (!GOVERNMENT_OFFICER.equals(personalInfo.getOccupation())) {
			// IF Government !!
			if (Util.empty(personalInfo.getPositionLevel())) {
				formError.error("POSITION_LEVEL", PREFIX_ERROR+LabelUtil.getText(request,"POSITION_LEVEL"));
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
		boolean compareFlag = CompareObject.compare(personalInfo.getPositionLevel(), lastpersonalInfo.getPositionLevel(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "POSITION_LEVEL"), request));
			audit.setOldValue(FormatUtil.displayText(getPositionLevelDesc(lastpersonalInfo.getPositionLevel())));
			audit.setNewValue(FormatUtil.displayText(getPositionLevelDesc(personalInfo.getPositionLevel())));
			audits.add(audit);
		}
		return audits;
	}
	private String getPositionLevelDesc(String code){
		return ListBoxControl.getName(FIELD_ID_POSITION_LEVEL, code);
	}
}
