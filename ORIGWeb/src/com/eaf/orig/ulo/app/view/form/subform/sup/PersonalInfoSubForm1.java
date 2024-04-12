package com.eaf.orig.ulo.app.view.form.subform.sup;

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
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.service.common.api.ServiceCache;

@SuppressWarnings("serial")
public class PersonalInfoSubForm1 extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PersonalInfoSubForm1.class);
	private String subformId = "SUP_PERSONAL_INFO_SUBFROM_1";
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String MINIMUM_OLD_CHIELD = SystemConfig.getGeneralParam("MINIMUM_OLD_CHIELD");
	private String CIDTYPE_PASSPORT = ServiceCache.getConstant("CIDTYPE_PASSPORT");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();	
		
		String CIS_NUMBER = request.getParameter("CIS_NUMBER");
		String CID_TYPE = request.getParameter("CID_TYPE");
		String ID_NO = request.getParameter("ID_NO");
		String TH_TITLE_CODE = request.getParameter("TH_TITLE_CODE");
		String TH_TITLE_DESC = request.getParameter("TH_TITLE_DESC");
		String TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME");
		String TH_MID_NAME = request.getParameter("TH_MID_NAME");
		String TH_LAST_NAME = request.getParameter("TH_LAST_NAME");
		String EN_TITLE_CODE = request.getParameter("EN_TITLE_CODE");
		String EN_TITLE_DESC = request.getParameter("EN_TITLE_DESC");
		String EN_FIRST_NAME = request.getParameter("EN_FIRST_NAME");
		String EN_MID_NAME = request.getParameter("EN_MID_NAME");
		String EN_LAST_NAME = request.getParameter("EN_LAST_NAME");
		String TH_BIRTH_DATE = request.getParameter("TH_BIRTH_DATE");
		String EN_BIRTH_DATE = request.getParameter("EN_BIRTH_DATE");
		String GENDER = request.getParameter("GENDER");
		String NATIONALITY = request.getParameter("NATIONALITY");
		String MARRIED = request.getParameter("MARRIED");
		String VISA_TYPE = request.getParameter("VISA_TYPE");
		String VISA_EXPIRE_DATE = request.getParameter("VISA_EXPIRE_DATE");
		String PASSPORT_EXPIRE_DATE = request.getParameter("PASSPORT_EXPIRE_DATE");
		String IDNO_EXPIRE_DATE = request.getParameter("IDNO_EXPIRE_DATE");

		
//		String OCCUPATION = request.getParameter("OCCUPATION");

		logger.debug("CIS_NUMBER >> "+CIS_NUMBER);
		logger.debug("CID_TYPE >> "+CID_TYPE);	
		logger.debug("ID_NO >>"+ID_NO);
		logger.debug("TH_TITLE_CODE >>"+TH_TITLE_CODE);
		logger.debug("TH_TITLE_DESC >>"+TH_TITLE_DESC);
		logger.debug("TH_FIRST_NAME >>"+TH_FIRST_NAME);
		logger.debug("TH_MID_NAME >>"+TH_MID_NAME);
		logger.debug("TH_LAST_NAME >>"+TH_LAST_NAME);
		logger.debug("EN_TITLE_CODE >>"+EN_TITLE_CODE);
		logger.debug("EN_TITLE_DESC >>"+EN_TITLE_DESC);
		logger.debug("EN_FIRST_NAME >>"+EN_FIRST_NAME);
		logger.debug("EN_MID_NAME >>"+EN_MID_NAME);
		logger.debug("EN_LAST_NAME >>"+EN_LAST_NAME);
		logger.debug("TH_BIRTH_DATE >>"+TH_BIRTH_DATE);
		logger.debug("EN_BIRTH_DATE >>"+EN_BIRTH_DATE);
		logger.debug("GENDER >>"+GENDER);
		logger.debug("NATIONALITY >>"+NATIONALITY);
		logger.debug("MARRIED >>"+MARRIED);
		logger.debug("VISA_TYPE >>"+VISA_TYPE);
		logger.debug("VISA_EXPIRE_DATE >>"+VISA_EXPIRE_DATE);
		logger.debug("PASSPORT_EXPIRE_DATE >>"+PASSPORT_EXPIRE_DATE);
		logger.debug("IDNO_EXPIRE_DATE >>"+IDNO_EXPIRE_DATE);
//		logger.debug("OCCUPATION >>"+OCCUPATION);
		
		personalInfo.setCisNo(CIS_NUMBER);
		personalInfo.setCidType(CID_TYPE);
		personalInfo.setIdno(ID_NO);
		personalInfo.setThTitleCode(TH_TITLE_CODE);
		personalInfo.setThTitleDesc(TH_TITLE_DESC);
		personalInfo.setThFirstName(TH_FIRST_NAME);
		personalInfo.setThMidName(TH_MID_NAME);
		personalInfo.setThLastName(TH_LAST_NAME);
		personalInfo.setEnTitleCode(EN_TITLE_CODE);
		personalInfo.setEnTitleDesc(EN_TITLE_DESC);
		personalInfo.setEnFirstName(EN_FIRST_NAME);
		personalInfo.setEnMidName(EN_MID_NAME);
		personalInfo.setEnLastName(EN_LAST_NAME);
		personalInfo.setBirthDate(FormatUtil.toDate(EN_BIRTH_DATE,FormatUtil.EN));
		personalInfo.setGender(GENDER);
		personalInfo.setNationality(NATIONALITY);
		personalInfo.setMarried(MARRIED);
		personalInfo.setVisaType(VISA_TYPE);
		personalInfo.setVisaExpDate(FormatUtil.toDate(VISA_EXPIRE_DATE,FormatUtil.EN));
		if(!Util.empty(PASSPORT_EXPIRE_DATE))
		personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE,FormatUtil.EN));
		personalInfo.setLinkFirstName(personalInfo.getThFirstName());
		personalInfo.setLinkLastName(personalInfo.getThLastName());
//		if(!Util.empty(IDNO_EXPIRE_DATE))
//			personalInfo.setCidExpDate(FormatUtil.toDate(IDNO_EXPIRE_DATE,FormatUtil.EN));
//		personalInfo.setOccupation(OCCUPATION);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();		
		
		String subformId = "SUP_PERSONAL_INFO_SUBFROM_1";
		logger.debug("subformId >> "+subformId);		
		
		FormErrorUtil formError = new FormErrorUtil();
		
		if(PersonalInfoUtil.isBundingTemplate(applicationGroup) && Util.empty(personalInfo.getCisNo())){
			formError.error("CIS_NUMBER", MessageErrorUtil.getText(request,"ERROR_BUNDING_CIS_NO"));
		}
		if(PersonalInfoUtil.validateAge(FormatUtil.getInt(MINIMUM_OLD_CHIELD), personalInfo)){
			formError.error("TH_TITLE_DESC", MessageErrorUtil.getText(request,"ERROR_TITLECODE_MORE_AGE"));
		}	
		formError.mandatory(subformId,"CID_TYPE",personalInfo.getCidType(),request);	
		formError.mandatory(subformId,"ID_NO",personalInfo.getIdno(),request);
		formError.mandatory(subformId,"TH_FIRST_LAST_NAME",personalInfo,request);
		formError.mandatory(subformId,"EN_FIRST_LAST_NAME",personalInfo,request);
		formError.mandatoryDate(subformId,"EN_BIRTH_DATE",personalInfo.getBirthDate(),request,DateValidateUtil.EN);
		formError.mandatory(subformId,"GENDER",personalInfo.getGender(),request);
		formError.mandatory(subformId,"NATIONALITY",personalInfo.getNationality(),request);
		formError.mandatory(subformId,"MARRIED",personalInfo.getMarried(),request);
		formError.mandatory(subformId,"OCCUPATION",personalInfo.getOccupation(),request);
		formError.mandatory(subformId,"VISA_TYPE",personalInfo.getVisaType(),request);
		formError.mandatoryDate(subformId,"VISA_EXPIRE_DATE",personalInfo.getVisaExpDate(),request,DateValidateUtil.EN);
		formError.mandatoryDate(subformId,"PASSPORT_EXPIRE_DATE",personalInfo.getCidExpDate(),request,DateValidateUtil.EN);
//		formError.mandatory(subformId,"IDNO_EXPIRE_DATE",personalInfo.getCidExpDate(),request);
		
		//Validate EN_FIRST_LAST_NAME  
		if(PersonalInfoUtil.validateRegExp(personalInfo)){
			formError.error("EN_FIRST_LAST_NAME",MessageErrorUtil.getText(request,"ERROR_ENG_FORMAT"));
		}
		//Validate TH_FIRST_LAST_NAME PASSPORT
		if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType())){
			if(PersonalInfoUtil.validateRegExpTH(personalInfo)){
				formError.error("TH_FIRST_LAST_NAME",MessageErrorUtil.getText(request,"ERROR_THAI_FORMAT"));
			}
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		
		logger.debug(">>>>subformId>>>"+subformId);
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)objectForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();		
//		String PERSONAL_TYPE = personalInfo.getPersonalType();
 
//		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		personalInfo.setCisNo(formValue.getValue("CID_TYPE","CID_TYPE_"+PREFIX_ELEMENT_ID,personalInfo.getCisNo()));
		personalInfo.setThTitleCode(formValue.getValue("TH_FIRST_LAST_NAME","TH_TITLE_CODE_"+PREFIX_ELEMENT_ID,personalInfo.getThTitleCode()));
		personalInfo.setThTitleDesc(formValue.getValue("TH_FIRST_LAST_NAME","TH_TITLE_DESC_"+PREFIX_ELEMENT_ID,personalInfo.getThTitleDesc()));
		personalInfo.setThFirstName(formValue.getValue("TH_FIRST_NAME", "TH_FIRST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThFirstName()));
		personalInfo.setThLastName(formValue.getValue("TH_LAST_NAME", "TH_LAST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThLastName()));
		personalInfo.setEnFirstName(formValue.getValue("EN_FIRST_NAME", "EN_FIRST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnFirstName()));
		personalInfo.setEnLastName(formValue.getValue("EN_LAST_NAME", "EN_LAST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnLastName()));
		personalInfo.setBirthDate(formValue.getValue("EN_BIRTH_DATE", "TH_BIRTH_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getBirthDate()));
//		personalInfo.setBirthDate(formValue.getValue("EN_BIRTH_DATE", "EN_BIRTH_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getBirthDate()));
		personalInfo.setMarried(formValue.getValue("MARRIED", "MARRIED_"+PREFIX_ELEMENT_ID, personalInfo.getMarried()));
		personalInfo.setOccupation(formValue.getValue("OCCUPATION", "OCCUPATION_"+PREFIX_ELEMENT_ID, personalInfo.getOccupation()));
		personalInfo.setVisaType(formValue.getValue("VISA_TYPE", "VISA_TYPE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaType()));
		personalInfo.setVisaExpDate(formValue.getValue("VISA_EXPIRE_DATE", "VISA_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaExpDate()));
		personalInfo.setCidExpDate(formValue.getValue("PASSPORT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getCidExpDate()));
		personalInfo.setCidExpDate(formValue.getValue("IDNO_EXPIRE_DATE", "IDNO_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getCidExpDate()));
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("sup.auditForm");		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
		
		PersonalInfoDataM personalSupplementary = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		PersonalInfoDataM lastPersonalSupplementary = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.setObjectValue(personalSupplementary.getPersonalType());
		auditFormUtil.auditForm(subformId, "ID_NO", personalSupplementary, lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "EN_FIRST_LAST_NAME", personalSupplementary, lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "TH_FIRST_LAST_NAME", personalSupplementary, lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "EN_BIRTH_DATE", personalSupplementary, lastPersonalSupplementary, request);	
		auditFormUtil.auditForm(subformId, "OCCUPATION", personalSupplementary, lastPersonalSupplementary, request);	
		auditFormUtil.auditForm(subformId, "MARRIED", personalSupplementary, lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "NATIONALITY", personalSupplementary, lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "GENDER", personalSupplementary, lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "PASSPORT_EXPIRE_DATE", personalSupplementary,lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "VISA_EXPIRE_DATE", personalSupplementary,lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "VISA_TYPE", personalSupplementary,lastPersonalSupplementary, request);
		return auditFormUtil.getAuditForm();
	}
	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"CID_TYPE","ID_NO","TH_FIRST_NAME","TH_LAST_NAME","TH_BIRTH_DATE"
							 ,"EN_FIRST_NAME","EN_LAST_NAME","GENDER","NATIONALITY","MARRIED","OCCUPATION","VISA_TYPE","VISA_EXPIRE_DATE","PASSPORT_EXPIRE_DATE"};
		try {
			PersonalInfoDataM personalInfo  = new PersonalInfoDataM();
			if(objectForm instanceof PersonalInfoDataM){
				personalInfo = (PersonalInfoDataM)objectForm;
			}else if(objectForm instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
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
}
