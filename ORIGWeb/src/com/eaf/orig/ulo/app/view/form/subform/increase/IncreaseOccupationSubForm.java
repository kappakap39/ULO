package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

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
public class IncreaseOccupationSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(IncreaseOccupationSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String subformId = "INCREASE_OCCUPATION_SUBFORM";
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
//		String OCCUPATION = request.getParameter("OCCUPATION");
//		String PROFESSION = request.getParameter("PROFESSION");
		String POSITION_CODE = request.getParameter("POSITION_CODE");
		String POSITION_LEVEL = request.getParameter("POSITION_LEVEL");
		
//		personalInfo.setOccupation(OCCUPATION);
//		personalInfo.setProfession(PROFESSION);
		personalInfo.setPositionCode(POSITION_CODE);
		personalInfo.setPositionLevel(POSITION_LEVEL);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		formError.mandatory(subformId,"OCCUPATION",personalInfo.getOccupation(),request);
		formError.mandatory(subformId,"PROFESSION",personalInfo.getProfession(),request);
		formError.mandatory(subformId,"POSITION_CODE",personalInfo.getPositionCode(),request);
		formError.mandatory(subformId,"POSITION_LEVEL",personalInfo.getPositionLevel(),request);

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastapplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		PersonalInfoDataM lastpersonalInfo = lastapplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.setObjectValue(personalInfo.getPersonalType());
		String subformId = getSubFormID();
		auditFormUtil.auditForm(subformId, "PROFESSION", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "OCCUPATION", personalInfo, lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "POSITION_CODE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "POSITION_LEVEL", personalInfo,lastpersonalInfo, request);
		
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if (null == personalInfo) {
			personalInfo = new PersonalInfoDataM();
		}
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setOccupation(formValue.getValue("OCCUPATION","OCCUPATION_"+PREFIX_ELEMENT_ID,personalInfo.getOccupation()));
		personalInfo.setProfession(formValue.getValue("PROFESSION","PROFESSION_"+PREFIX_ELEMENT_ID,personalInfo.getProfession()));
		personalInfo.setPositionCode(formValue.getValue("POSITION_CODE", "POSITION_CODE_"+PREFIX_ELEMENT_ID, personalInfo.getPositionCode()));
		personalInfo.setPositionLevel(formValue.getValue("POSITION_LEVEL","POSITION_LEVEL_"+PREFIX_ELEMENT_ID,personalInfo.getPositionLevel()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] fieldNameList={"OCCUPATION","PROFESSION","POSITION_CODE"};
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
			 for(String elementId:fieldNameList){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
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
