package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SEND_DOC_SUPProperty extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(ADDRESS_NOProperty.class);
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String FIELD_ID_SEND_DOC = SystemConstant.getConstant("FIELD_ID_SEND_DOC");
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String mandatoryFlag = ValidateFormInf.VALIDATE_NO;
		if(mandatoryConfig.equals(ValidateFormInf.VALIDATE_SUBMIT)){
			if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
				mandatoryFlag = ValidateFormInf.VALIDATE_SUBMIT;
			}
		}	
		return mandatoryFlag;
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
		
		boolean compareFlag = CompareObject.compare(personalInfo.getMailingAddress(), lastpersonalInfo.getMailingAddress(),null);
		if(!compareFlag){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalInfo.getPersonalType(), LabelUtil.getText(request, "SEND_DOC_SUB"), request));
			audit.setOldValue(ListBoxControl.getName(FIELD_ID_SEND_DOC, lastpersonalInfo.getMailingAddress()));
			audit.setNewValue(ListBoxControl.getName(FIELD_ID_SEND_DOC, personalInfo.getMailingAddress()));
			audits.add(audit);
		}
		return audits;
	}
}
