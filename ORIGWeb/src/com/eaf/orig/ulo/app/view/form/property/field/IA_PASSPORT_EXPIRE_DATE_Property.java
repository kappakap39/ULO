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
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IA_PASSPORT_EXPIRE_DATE_Property extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(IA_PASSPORT_EXPIRE_DATE_Property.class);
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE");
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
		logger.debug("IA_PASSPORT_EXPIRE_DATE_Property.validateFlag");
			return ValidateFormInf.VALIDATE_YES;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,Object objectForm) {
		logger.debug("IA_PASSPORT_EXPIRE_DATE_Property.validateFlag");
		FormErrorUtil formError = new FormErrorUtil();
		String btnAction = request.getParameter("buttonAction");
		logger.debug("btnAction : "+btnAction);
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		if(BUTTON_ACTION_SUBMIT.equals(btnAction) 
				&& !Util.empty(personalInfo) && Util.empty(personalInfo.getCidExpDate())){
			logger.debug("BUTTON_ACTION_SUBMIT : ");
			formError.error("PASSPORT_EXPIRE_DATE",MessageErrorUtil.getText("ERROR_PASSPORT_EXPIRE_DATE_"+personalInfo.getPersonalType()));
		}else if(!Util.empty(personalInfo) && !Util.empty(personalInfo.getCidExpDate())){
			logger.debug("else : ");
			if(DateValidateUtil.validateDate(personalInfo.getCidExpDate(), DateValidateUtil.FORMAT_DATE_VALIDATE,DateValidateUtil.EN)
					||DateValidateUtil.validateDate(personalInfo.getCidExpDate(), DateValidateUtil.LIMIT_YEAR_DATE_VALIDATE,DateValidateUtil.EN)){
				formError.error("PASSPORT_EXPIRE_DATE",LabelUtil.getText(request,"PASSPORT_EXPIRE_DATE")+MessageErrorUtil.getText(request, "ERROR_DATE_FORMAT"));
			}
		}
		return formError.getFormError();
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		logger.debug("IA_PASSPORT_EXPIRE_DATE_Property.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		boolean compareFlag = CompareObject.compare(personalInfo.getCidExpDate(), lastpersonalInfo.getCidExpDate(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "PASSPORT_EXPIRE_DATE")+ getCidTypeDesc(personalInfo.getCidType()), request));
			audit.setOldValue(FormatUtil.display(lastpersonalInfo.getCidExpDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy));
			audit.setNewValue(FormatUtil.display(personalInfo.getCidExpDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy));
			audits.add(audit);
		}
		return audits;
	}
	private String getCidTypeDesc(String cidType) {
		return " (" + ListBoxControl.getName(FIELD_ID_CID_TYPE, cidType) + ")";
	}
}