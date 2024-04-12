package com.eaf.orig.ulo.app.view.form.subform.ia;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
  
public class NCBApplicantInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(NCBApplicantInfoSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	ArrayList<String> NCB_SUBFORM_EXCEPTION_APPLICATION_TEMPLATE = new ArrayList<String>(Arrays.asList(SystemConstant.getArrayConstant("NCB_SUBFORM_EXCEPTION_APPLICATION_TEMPLATE")));
	String subformId = "IA_NCB_APPLICANT_INFO_SUBFORM";

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		PersonalApplicationInfoDataM personalApplicantInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicantInfo.getPersonalInfo();
		
		String CONSENT_DATE = request.getParameter("DATE_CONSENT");
		String TH_BIRTH_DATE_CONSENT = request.getParameter("TH_BIRTH_DATE_CONSENT");
		String EN_BIRTH_DATE_CONSENT = request.getParameter("EN_BIRTH_DATE_CONSENT");
		String PLACE_CONSENT = request.getParameter("PLACE_CONSENT");
		String WITNESS_CONSENT = request.getParameter("WITNESS_CONSENT");
		String ID_NO_CONSENT = request.getParameter("ID_NO_CONSENT");

		logger.debug("TH_BIRTH_DATE_CONSENT >>"+TH_BIRTH_DATE_CONSENT);
		logger.debug("EN_BIRTH_DATE_CONSENT >>"+EN_BIRTH_DATE_CONSENT);
		logger.debug("PLACE_CONSENT >>"+PLACE_CONSENT);
		logger.debug("WITNESS_CONSENT >>"+WITNESS_CONSENT);
		logger.debug("ID_NO_CONSENT >> "+ID_NO_CONSENT );
		logger.debug("CONSENT_DATE >> "+CONSENT_DATE );

		personalInfo.setPlaceConsent(PLACE_CONSENT);
		personalInfo.setWitnessConsent(WITNESS_CONSENT);
		personalInfo.setBirthDateConsent(FormatUtil.toDate(EN_BIRTH_DATE_CONSENT,FormatUtil.EN));
		personalInfo.setIdNoConsent(ID_NO_CONSENT);
		personalInfo.setConsentDate(FormatUtil.toDate(CONSENT_DATE,FormatUtil.TH));
		logger.debug("CONSENT_DATE >>"+personalInfo.getConsentDate());
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalApplicationInfoDataM personalApplicantInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicantInfo.getPersonalInfo();
		 
		FormErrorUtil formError = new FormErrorUtil(personalInfo);
		logger.debug("subformId >> "+subformId);
		String TH_BIRTH_DATE = request.getParameter("TH_BIRTH_DATE_CONSENT");
		String CONSENT_DATE = request.getParameter("DATE_CONSENT");
		Date thBirthDate = !Util.empty(TH_BIRTH_DATE) ? FormatUtil.toDate(TH_BIRTH_DATE,FormatUtil.TH) : personalInfo.getBirthDateConsent() ;
		Date thConsentDate = !Util.empty(CONSENT_DATE) ? FormatUtil.toDate(CONSENT_DATE,FormatUtil.TH) : personalInfo.getConsentDate() ;
		
//		formError.mandatory(subformId,"ID_NO_CONSENT",personalInfo.getIdNoConsent(),request);
//		formError.mandatoryDate(subformId,"DATE_CONSENT",personalInfo.getConsentDate(),request,DateValidateUtil.EN);
//		formError.mandatoryDate(subformId,"TH_BIRTH_DATE_CONSENT",thBirthDate,request);
//		formError.mandatoryDate(subformId,"EN_BIRTH_DATE_CONSENT",personalInfo.getBirthDateConsent(),request,DateValidateUtil.EN);
//		formError.mandatory(subformId,"PLACE_CONSENT",personalInfo.getPlaceConsent(),request);
//		formError.mandatory(subformId,"WITNESS_CONSENT",personalInfo.getWitnessConsent(),request);
		
		
		// id_no_pic validate 	
		logger.debug("applicationType >> "+ applicationGroup.getApplicationType());

		// get VALIDATE FORM TYPE
		HashMap<String,String> SubformData = super.getSubformData();
		String VALIDATE_FORM_TYPE = SubformData.get("VALIDATE_FORM_TYPE");
		logger.debug("VALIDATE_FORM_TYPE >>"+VALIDATE_FORM_TYPE);

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
		 
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"EN_BIRTH_DATE_CONSENT"};
		try {
			PersonalInfoDataM personalInfo = new PersonalInfoDataM();
			if(objectForm instanceof PersonalInfoDataM){
				personalInfo =(PersonalInfoDataM)objectForm;
			}else if(objectForm instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);	
				personalInfo =PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			}
			 for(String elementId:filedNames){
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
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("PersonalApplicantInfoSubForm.auditForm");
		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		
		auditFormUtil.setObjectValue(personalInfo.getPersonalType());
		auditFormUtil.auditForm(subformId, "EN_BIRTH_DATE_CONSENT", personalInfo,lastpersonalInfo, request);
		return auditFormUtil.getAuditForm();
		
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object appForm) {
		logger.debug("!!TEST!! :: RENDER SUBFORM");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String applicationTemplate = applicationGroup.getApplicationTemplate();
		PersonalApplicationInfoDataM personalApplicantInfo = (PersonalApplicationInfoDataM) appForm;
		PersonalInfoDataM personalInfo = personalApplicantInfo.getPersonalInfo();
		logger.debug("!!TEST!! PERSON TYPE :: " + personalInfo.getPersonalType());
		logger.debug("!!TEST!! PERSON PERSONAL_TYPE :: " + PERSONAL_TYPE);
		if(!PERSONAL_TYPE.equals(personalInfo.getPersonalType()) || NCB_SUBFORM_EXCEPTION_APPLICATION_TEMPLATE.contains(applicationTemplate)) {
			return MConstant.FLAG_N;
		}		
		return MConstant.FLAG_Y;
	}
	
}
