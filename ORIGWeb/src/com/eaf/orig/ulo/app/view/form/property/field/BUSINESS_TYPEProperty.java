package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BUSINESS_TYPEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUSINESS_TYPEProperty.class);
	String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("BUSINESS_TYPEProperty.validateFlag...");		
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}		
		
		logger.debug("MANDATORY_BUSINESS_TYPE BY OCCUPATION >> "+personalInfo.getOccupation());
		String[] MANDATORY_BUSINESS_TYPE = SystemConstant.getConstant("MANDATORY_BUSINESS_TYPE").split(",");
		for(int i=0;i<MANDATORY_BUSINESS_TYPE.length;i++){ 
			if(MANDATORY_BUSINESS_TYPE[i].equals(personalInfo.getOccupation())){
				return ValidateFormInf.VALIDATE_SUBMIT;
			}
		}
		return ValidateFormInf.VALIDATE_NO;
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
		
		boolean compareFlag = CompareObject.compare(personalInfo.getBusinessType(), lastpersonalInfo.getBusinessType(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(LabelUtil.getText(request, "BUSINESS_TYPE"));
			audit.setOldValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_BUSINESS_TYPE, String.valueOf(lastpersonalInfo.getBusinessType()))));
			audit.setNewValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_BUSINESS_TYPE, String.valueOf(personalInfo.getBusinessType()))));
			
			audits.add(audit);
		}
		return audits;
	}
}
