package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class PhoneNoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PhoneNoSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String subformId = "INCREASE_PHONE_NO_SUBFORM";
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
//		int PERSONAL_SEQ = personalInfo.getSeq();	
//		String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
		String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
		
		String PHONE_NUMBER = request.getParameter("PHONE_NUMBER_"+personalElementId);	
		String CONTACT_TIME = request.getParameter("CONTACT_TIME_"+personalElementId);	
		
		logger.debug("PHONE_NUMBER >>"+PHONE_NUMBER);		
		logger.debug("CONTACT_TIME >>"+CONTACT_TIME);

		personalInfo.setMobileNo(FormatUtil.removeDash(PHONE_NUMBER));
		personalInfo.setContactTime(CONTACT_TIME);	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();				
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		logger.debug("subformId >> "+subformId);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);			
		
		formError.mandatory(subformId,"PHONE_NUMBER",personalInfo.getMobileNo(),request);
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if (null == personalInfo) {
			personalInfo = new PersonalInfoDataM();
		}		
//		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setMobileNo(formValue.getValue("MOBILE","PHONE_NUMBER_"+PREFIX_ELEMENT_ID,personalInfo.getMobileNo()));
		personalInfo.setContactTime(formValue.getValue("CONTACT_TIME","CONTACT_TIME_"+PREFIX_ELEMENT_ID,personalInfo.getContactTime()));
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastapplicationGroup = ((ApplicationGroupDataM)lastObjectForm);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM lastpersonalInfo = lastapplicationGroup.getPersonalInfo(PERSONAL_TYPE);

		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.setObjectValue(personalInfo.getPersonalType());
		auditFormUtil.auditForm(subformId, "MOBILE", personalInfo, lastpersonalInfo, request);
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
	ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
	String[] fieldNameList={"PHONE_NUMBER"};
	try {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		 for(String elementId:fieldNameList){
			 FieldElement fieldElement = new FieldElement();
			 fieldElement.setElementId(elementId);
			 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
			 fieldElement.setElementGroupId(personalInfo.getPersonalId());
			 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
			 fieldElements.add(fieldElement);
		 }
	} catch (Exception e) {
		logger.fatal("ERROR",e);
	}
	return fieldElements;
	}
}
