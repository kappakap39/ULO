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
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class TH_FIRST_LAST_NAMEProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(TH_FIRST_LAST_NAMEProperty.class);
	
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("TH_FIRST_LAST_NAMEProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(Util.empty(personalInfo.getThTitleCode()) || Util.empty(personalInfo.getThFirstName()) || Util.empty(personalInfo.getThLastName())){
			formError.error("TH_FIRST_LAST_NAME",PREFIX_ERROR+LabelUtil.getText(request,"TH_FIRST_LAST_NAME"));
		}
		return formError.getFormError();
	}

	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		String personalType = personalInfo.getPersonalType();
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}

		if(!CompareObject.compare(personalInfo.getThFirstName(),lastpersonalInfo.getThFirstName(),null)){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "TH_NAME"), request));
			audit.setOldValue(FormatUtil.displayText(lastpersonalInfo.getThFirstName()));
			audit.setNewValue(FormatUtil.displayText(personalInfo.getThFirstName()));
			audits.add(audit);
		}
		if(!CompareObject.compare(personalInfo.getThLastName(),lastpersonalInfo.getThLastName(),null)){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "TH_LAST_NAME"), request));
			audit.setOldValue(FormatUtil.displayText(lastpersonalInfo.getThLastName()));
			audit.setNewValue(FormatUtil.displayText(personalInfo.getThLastName()));
			audits.add(audit);
		}
		return audits;
	}
}
