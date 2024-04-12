package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class PersonalInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PersonalInfoSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_TYPE_INCREASE= SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	private String MINIMUM_OLD_CHIELD = SystemConfig.getGeneralParam("MINIMUM_OLD_CHIELD");
	private String subformId = "INCREASE_PERSONAL_INFO_SUBFORM";
	String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE");
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String CIDTYPE_PASSPORT=SystemConstant.getConstant("CIDTYPE_PASSPORT");
	private String VERIFICATION_CHECK_AGE_TWENTY = SystemConstant.getConstant("VERIFICATION_CHECK_AGE_TWENTY");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
				
		String CIS_NUMBER = request.getParameter("CIS_NUMBER");
		String CID_TYPE = request.getParameter("CID_TYPE");
		String ID_NO = request.getParameter("ID_NO");
		String TH_TITLE_CODE = request.getParameter("TH_TITLE_CODE");
		String TH_TITLE_DESC = request.getParameter("TH_TITLE_DESC");
		String TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME");
		String TH_MID_NAME = request.getParameter("TH_MID_NAME");
		String TH_LAST_NAME = request.getParameter("TH_LAST_NAME");
		String TH_BIRTH_DATE = request.getParameter("TH_BIRTH_DATE");
		String EN_BIRTH_DATE = request.getParameter("EN_BIRTH_DATE");
		String VISA_TYPE = request.getParameter("VISA_TYPE");
		String VISA_EXPIRE_DATE = request.getParameter("VISA_EXPIRE_DATE");
		String CONSENT_MODEL = request.getParameter("CONSENT_MODEL");
		String ID_NO_CONSENT = request.getParameter("ID_NO_CONSENT");
		String	CID_EXP_DATE	= request.getParameter("PASSPORT_EXPIRE_DATE");
		
		logger.debug("TH_TITLE_CODE >> "+personalInfo.getThTitleCode());
		logger.debug("TH_TITLE_DESCRIPTION >> "+personalInfo.getThTitleDesc());
		logger.debug("CIS_NUMBERALARY >>"+CIS_NUMBER);
		logger.debug("CID_TYPE >>"+CID_TYPE);
		logger.debug("ID_NO >>"+ID_NO);
		logger.debug("TH_TITLE_CODE >>"+TH_TITLE_CODE);
		logger.debug("TH_TITLE_DESC >>"+TH_TITLE_DESC);
		logger.debug("TH_FIRST_NAME >>"+TH_FIRST_NAME);
		logger.debug("TH_MID_NAME >>"+TH_MID_NAME);
		logger.debug("TH_LAST_NAME >>"+TH_LAST_NAME);
		logger.debug("TH_BIRTH_DATE >>"+TH_BIRTH_DATE);
		logger.debug("EN_BIRTH_DATE >>"+EN_BIRTH_DATE);
		logger.debug("VISA_TYPE >>"+VISA_TYPE);
		logger.debug("VISA_EXPIRE_DATE >>"+VISA_EXPIRE_DATE);
		logger.debug("CONSENT_MODEL >>"+CONSENT_MODEL);
		logger.debug("ID_NO_CONSENT >> "+ID_NO_CONSENT );
		logger.debug("CID_EXP_DATE >>"+CID_EXP_DATE);
		
		String EN_TITLE_CODE = ListBoxControl.getName(FIELD_ID_TH_TITLE_CODE,"CHOICE_NO",TH_TITLE_CODE,"SYSTEM_ID1");
		String EN_TITLE_DESC = ListBoxControl.getName(FIELD_ID_EN_TITLE_CODE,"CHOICE_NO",EN_TITLE_CODE,"DISPLAY_NAME");
		if(!Util.empty(EN_TITLE_DESC)){
			personalInfo.setEnTitleCode(EN_TITLE_CODE);
			personalInfo.setEnTitleDesc(EN_TITLE_DESC);
		}
		personalInfo.setCisNo(CIS_NUMBER);
		personalInfo.setCidType(CID_TYPE);
		personalInfo.setIdno(ID_NO);
		personalInfo.setThTitleCode(TH_TITLE_CODE);
		personalInfo.setThTitleDesc(TH_TITLE_DESC);
		personalInfo.setThFirstName(TH_FIRST_NAME);
		personalInfo.setThMidName(TH_MID_NAME);
		personalInfo.setThLastName(TH_LAST_NAME);
		personalInfo.setVisaType(VISA_TYPE);
		personalInfo.setConsentModelFlag(CONSENT_MODEL);
		personalInfo.setVisaExpDate(FormatUtil.toDate(VISA_EXPIRE_DATE, FormatUtil.EN));
		personalInfo.setBirthDate(FormatUtil.toDate(EN_BIRTH_DATE,FormatUtil.EN));
		personalInfo.setIdNoConsent(ID_NO_CONSENT);
		personalInfo.setCidExpDate(FormatUtil.toDate(CID_EXP_DATE,FormatUtil.EN));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		FormErrorUtil formError = new FormErrorUtil(personalInfo);
		logger.debug("subformId >> "+subformId);
		if(PersonalInfoUtil.isBundingTemplate(applicationGroup) && Util.empty(personalInfo.getCisNo())){
			formError.error("CIS_NUMBER", MessageErrorUtil.getText(request,"ERROR_BUNDING_CIS_NO"));
		}
		
		if(PersonalInfoUtil.validateAge(FormatUtil.getInt(MINIMUM_OLD_CHIELD), personalInfo)){
			formError.error("TH_TITLE_DESC", MessageErrorUtil.getText(request,"ERROR_TITLECODE_MORE_AGE"));
		}	
		//Validate TH_FIRST_LAST_NAME PASSPORT
		if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType())){
			if(PersonalInfoUtil.validateRegExpTH(personalInfo)){
				formError.error("TH_FIRST_LAST_NAME",MessageErrorUtil.getText(request,"ERROR_THAI_FORMAT"));
			}
		}
		
		formError.mandatory(subformId,"CID_TYPE",personalInfo.getCidType(),request);	
		formError.mandatory(subformId,"ID_NO",personalInfo.getIdno(),request);
		formError.mandatory(subformId,"TH_FIRST_LAST_NAME",personalInfo,request);
		formError.mandatoryDate(subformId,"EN_BIRTH_DATE",personalInfo.getBirthDate(),request,DateValidateUtil.EN);
		formError.mandatory(subformId,"VISA_TYPE",personalInfo.getVisaType(),request);
		formError.mandatoryDate(subformId,"VISA_EXPIRE_DATE",personalInfo.getVisaExpDate(),request,DateValidateUtil.EN);
		formError.mandatory(subformId,"CONSENT_MODEL",personalInfo,request);
		formError.mandatory(subformId,"PASSPORT_EXPIRE_DATE",personalInfo.getCidExpDate(),request);

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
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
		String subformId = getSubFormID();
		
		auditFormUtil.setObjectValue(personalInfo.getPersonalType());
		auditFormUtil.auditForm(subformId, "ID_NO", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "TH_FIRST_LAST_NAME", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "EN_FIRST_LAST_NAME", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "EN_BIRTH_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "VISA_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "PASSPORT_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
//		if(APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType())) {
//			return;
//		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if (null == personalInfo) {
			personalInfo = new PersonalInfoDataM();
		}
//		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setCisNo(formValue.getValue("CIS_NUMBER","CIS_NUMBER_"+PREFIX_ELEMENT_ID,personalInfo.getCisNo()));
		//personalInfo.setIdno(formValue.getValue("ID_NO","ID_NO_"+PREFIX_ELEMENT_ID,personalInfo.getIdno()));
		personalInfo.setThTitleCode(formValue.getValue("TH_FIRST_LAST_NAME","TH_TITLE_CODE_"+PREFIX_ELEMENT_ID,personalInfo.getThTitleCode()));
		personalInfo.setThTitleDesc(formValue.getValue("TH_FIRST_LAST_NAME","TH_TITLE_DESC_"+PREFIX_ELEMENT_ID,personalInfo.getThTitleDesc()));
		personalInfo.setThFirstName(formValue.getValue("TH_FIRST_LAST_NAME", "TH_FIRST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThFirstName()));
		personalInfo.setThMidName(formValue.getValue("TH_FIRST_LAST_NAME", "TH_MID_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThMidName()));
		personalInfo.setThLastName(formValue.getValue("TH_FIRST_LAST_NAME", "TH_LAST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThLastName()));
		personalInfo.setEnTitleCode(formValue.getValue("EN_FIRST_LAST_NAME", "EN_TITLE_CODE_"+PREFIX_ELEMENT_ID, personalInfo.getEnTitleCode()));
		personalInfo.setEnTitleDesc(formValue.getValue("EN_FIRST_LAST_NAME","EN_TITLE_DESC_"+PREFIX_ELEMENT_ID,personalInfo.getEnTitleDesc()));
		personalInfo.setEnFirstName(formValue.getValue("EN_FIRST_LAST_NAME", "EN_FIRST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnFirstName()));
		personalInfo.setEnMidName(formValue.getValue("EN_FIRST_LAST_NAME", "EN_MID_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnMidName()));
		personalInfo.setEnLastName(formValue.getValue("EN_FIRST_LAST_NAME", "EN_LAST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnLastName()));
		personalInfo.setBirthDate(formValue.getValue("EN_BIRTH_DATE", "TH_BIRTH_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getBirthDate()));
		personalInfo.setVisaType(formValue.getValue("VISA_TYPE", "VISA_TYPE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaType()));
		personalInfo.setVisaExpDate(formValue.getValue("VISA_EXPIRE_DATE", "VISA_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaExpDate()));
		personalInfo.setConsentModelFlag(formValue.getValue("CONSENT_MODEL", "CONSENT_MODEL_"+PREFIX_ELEMENT_ID, personalInfo.getConsentModelFlag()));
		personalInfo.setCidExpDate(formValue.getValue("PASSPORT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getCidExpDate()));
//		personalInfo.setBirthDate(formValue.getValue("EN_BIRTH_DATE", "EN_BIRTH_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getBirthDate()));
//		personalInfo.setMarried(formValue.getValue("MARRIED", "MARRIED_"+TAG_SMART_DATA_PERSONAL, personalInfo.getMarried()));
//		personalInfo.setDegree(formValue.getValue("DEGREE", "DEGREE_"+TAG_SMART_DATA_PERSONAL, personalInfo.getDegree()));
		formValue.clearValue("APPLICATION_GROUP",applicationGroup);
	}

	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"ID_NO","TH_FIRST_NAME","TH_LAST_NAME","VISA_TYPE","VISA_EXPIRE_DATE","CONSENT_MODEL","PASSPORT_EXPIRE_DATE"};
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
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
}
