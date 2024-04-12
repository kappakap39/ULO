package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;

public class EN_FIRST_LAST_NAMEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(EN_FIRST_LAST_NAMEProperty.class);
	String BUTTON_ACTION_SAVE = SystemConstant.getConstant("BUTTON_ACTION_SAVE");
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String CIDTYPE_PASSPORT = ServiceCache.getConstant("CIDTYPE_PASSPORT");
	String DE1_1 = ServiceCache.getConstant("ROLE_DE1_1");
	String DE1_2 = ServiceCache.getConstant("ROLE_DE1_2");
	String DE2 = ServiceCache.getConstant("ROLE_DE2");
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("EN_FIRST_LAST_NAMEProperty.validateForm");
		String buttonAction = request.getParameter("buttonAction");
		String roleId = FormControl.getFormRoleId(request);
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(BUTTON_ACTION_SUBMIT.equals(buttonAction)){
			if(Util.empty(personalInfo.getEnTitleCode()) || Util.empty(personalInfo.getEnFirstName()) || Util.empty(personalInfo.getEnLastName())){
				formError.error("EN_FIRST_LAST_NAME",PREFIX_ERROR+LabelUtil.getText(request,"EN_FIRST_LAST_NAME"));
			}else{
				if(DE1_1.equals(roleId) || DE1_2.equals(roleId)){
					if(PersonalInfoUtil.validateRegExp(personalInfo)){
						formError.error("EN_FIRST_LAST_NAME",MessageErrorUtil.getText(request,"ERROR_ENG_FORMAT"));
					}
				}
			}
		}else{
			if(DE1_1.equals(roleId) || DE1_2.equals(roleId)){
				if(PersonalInfoUtil.validateRegExp(personalInfo)){
					formError.error("EN_FIRST_LAST_NAME",MessageErrorUtil.getText(request,"ERROR_ENG_FORMAT"));
				}
			}
		}
		return formError.getFormError();
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		String personalType = (String)objectValue;
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		boolean compareName = CompareObject.compare(personalInfo.getEnFirstName(), lastpersonalInfo.getEnFirstName(),null);

		if(!compareName){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "EN_NAME"), request));
			audit.setOldValue(FormatUtil.displayText(lastpersonalInfo.getEnFirstName()));
			audit.setNewValue(FormatUtil.displayText(personalInfo.getEnFirstName()));
			audits.add(audit);
		}
		boolean compareLastName = CompareObject.compare(personalInfo.getEnLastName(), lastpersonalInfo.getEnLastName(),null);
		if(!compareLastName){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "EN_LAST_NAME"), request));
			audit.setOldValue(FormatUtil.displayText(lastpersonalInfo.getEnLastName()));
			audit.setNewValue(FormatUtil.displayText(personalInfo.getEnLastName()));
			audits.add(audit);
		}
		return audits;
	}
}
