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
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class PersonalInfoSubForm2 extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PersonalInfoSubForm2.class);
	String subformId = "SUP_PERSONAL_INFO_SUBFROM_2";
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String CIDTYPE_PASSPORT=SystemConstant.getConstant("CIDTYPE_PASSPORT");
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
		String VISA_TYPE = request.getParameter("VISA_TYPE");
		String VISA_EXPIRE_DATE = request.getParameter("VISA_EXPIRE_DATE");
		String WORK_PERMIT_NO = request.getParameter("WORK_PERMIT_NO");
		String WORK_PERMIT_EXPIRE_DATE = request.getParameter("WORK_PERMIT_EXPIRE_DATE");
		String PASSPORT_EXPIRE_DATE = request.getParameter("PASSPORT_EXPIRE_DATE");
		String MARRIED = request.getParameter("MARRIED");
		String DEGREE = request.getParameter("DEGREE");
		String RELATION_WITH_APPLICANT = request.getParameter("RELATION_WITH_APPLICANT");
		String RELATION_WITH_APPLICANT_OTHER = request.getParameter("RELATION_WITH_APPLICANT_OTHER");
		String DISCLOSURE_FLAG = request.getParameter("DISCLOSURE_FLAG");
		String IDNO_EXPIRE_DATE = request.getParameter("IDNO_EXPIRE_DATE");

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
		logger.debug("VISA_TYPE >>"+VISA_TYPE);
		logger.debug("VISA_EXPIRE_DATE >>"+VISA_EXPIRE_DATE);
		logger.debug("WORK_PERMIT_NO >>"+WORK_PERMIT_NO);
		logger.debug("WORK_PERMIT_EXPIRE_DATE >>"+WORK_PERMIT_EXPIRE_DATE);
		logger.debug("PASSPORT_EXPIRE_DATE >>"+PASSPORT_EXPIRE_DATE);
		logger.debug("MARRIED >>"+MARRIED);
		logger.debug("DEGREE >>"+DEGREE);
		logger.debug("RELATION_WITH_APPLICANT >>"+RELATION_WITH_APPLICANT);
		logger.debug("RELATION_WITH_APPLICANT_OTHER >>"+RELATION_WITH_APPLICANT_OTHER);
		logger.debug("DISCLOSURE_FLAG >>"+DISCLOSURE_FLAG);
		logger.debug("IDNO_EXPIRE_DATE >>"+IDNO_EXPIRE_DATE);
		
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
		personalInfo.setVisaType(VISA_TYPE);
		if(!Util.empty(PASSPORT_EXPIRE_DATE))
			personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE,FormatUtil.EN));
		if(!Util.empty(IDNO_EXPIRE_DATE))
		personalInfo.setCidExpDate(FormatUtil.toDate(IDNO_EXPIRE_DATE,FormatUtil.EN));
		personalInfo.setWorkPermitNo(WORK_PERMIT_NO);
		personalInfo.setWorkPermitExpDate(FormatUtil.toDate(WORK_PERMIT_EXPIRE_DATE,FormatUtil.TH));
		personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE, FormatUtil.EN));
		personalInfo.setMarried(MARRIED);
		personalInfo.setDegree(DEGREE);
		personalInfo.setRelationshipType(RELATION_WITH_APPLICANT);
		personalInfo.setRelationshipTypeDesc(RELATION_WITH_APPLICANT_OTHER);
		personalInfo.setDisclosureFlag(DISCLOSURE_FLAG);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("subformId >> "+subformId);
		
		if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType())){
			formError.mandatory(subformId,"VISA_TYPE",personalInfo.getVisaType(),request);
			formError.mandatoryDate(subformId,"VISA_EXPIRE_DATE",personalInfo.getVisaExpDate(),request,DateValidateUtil.EN);
			formError.mandatoryDate(subformId,"PASSPORT_EXPIRE_DATE",personalInfo.getCidExpDate(),request,DateValidateUtil.EN);
		}
		
		formError.mandatory(subformId,"CIS_NUMBER",personalInfo.getCisNo(),request);
		formError.mandatory(subformId,"CID_TYPE",personalInfo.getCidType(),request);
		formError.mandatory(subformId,"ID_NO",personalInfo.getIdno(),request);
		formError.mandatory(subformId,"TH_FIRST_LAST_NAME",personalInfo,request);
		formError.mandatory(subformId,"EN_FIRST_LAST_NAME",personalInfo,request);
		formError.mandatoryDate(subformId,"TH_BIRTH_DATE",personalInfo.getBirthDate(),request);
		formError.mandatoryDate(subformId,"EN_BIRTH_DATE",personalInfo.getBirthDate(),request,DateValidateUtil.EN);
		formError.mandatory(subformId,"GENDER",personalInfo.getGender(),request);
		formError.mandatory(subformId,"NATIONALITY",personalInfo.getNationality(),request);
		formError.mandatory(subformId,"WORK_PERMIT_NO",personalInfo.getWorkPermitNo(),request);
		formError.mandatoryDate(subformId,"WORK_PERMIT_EXPIRE_DATE",personalInfo.getWorkPermitExpDate(),request);
		formError.mandatory(subformId,"MARRIED",personalInfo.getMarried(),request);
		formError.mandatory(subformId,"OCCUPATION",personalInfo.getOccupation(),request);
		formError.mandatory(subformId,"RELATION_WITH_APPLICANT",personalInfo.getRelationshipType(),request);
//		formError.mandatory(subformId,"IDNO_EXPIRE_DATE",personalInfo.getCidExpDate(),request);
		if(SystemConstant.getConstant("RELATION_WITH_APPLICANT_OTHER").equals(personalInfo.getRelationshipType())){
			formError.mandatory(subformId, "RELATION_WITH_APPLICANT", "RELATION_WITH_APPLICANT_OTHER", personalInfo.getRelationshipTypeDesc(), request);
		}
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
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String personalId = getSubformData("PERSONAL_ID");
		logger.debug("personalId >> "+personalId);
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);	
		if(null != personalInfo){
 
//			String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
			String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
//			personalInfo.setCidType(formValue.getValue("CID_TYPE","CID_TYPE_"+PREFIX_ELEMENT_ID,personalInfo.getCidType()));
			personalInfo.setIdno(formValue.getValue("ID_NO","ID_NO_"+PREFIX_ELEMENT_ID,personalInfo.getIdno()));
			//personalInfo.setThTitleCode(formValue.getValue("TH_FIRST_LAST_NAME","TH_TITLE_CODE_"+PREFIX_ELEMENT_ID,personalInfo.getThTitleCode()));
			//personalInfo.setThTitleDesc(formValue.getValue("TH_FIRST_LAST_NAME","TH_TITLE_DESC_"+PREFIX_ELEMENT_ID,personalInfo.getThTitleDesc()));
			personalInfo.setThFirstName(formValue.getValue("TH_FIRST_LAST_NAME", "TH_FIRST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThFirstName()));
			personalInfo.setThMidName(formValue.getValue("TH_FIRST_LAST_NAME", "TH_MID_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThMidName()));
			personalInfo.setThLastName(formValue.getValue("TH_FIRST_LAST_NAME", "TH_LAST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getThLastName()));
			//personalInfo.setEnTitleCode(formValue.getValue("EN_FIRST_LAST_NAME", "EN_TITLE_CODE_"+PREFIX_ELEMENT_ID, personalInfo.getEnTitleCode()));
			//personalInfo.setThTitleDesc(formValue.getValue("EN_FIRST_LAST_NAME","EN_TITLE_DESC_"+PREFIX_ELEMENT_ID,personalInfo.getEnTitleDesc()));
			personalInfo.setEnFirstName(formValue.getValue("EN_FIRST_LAST_NAME", "EN_FIRST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnFirstName()));	
			personalInfo.setEnMidName(formValue.getValue("EN_FIRST_LAST_NAME", "TH_MID_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnMidName()));
			personalInfo.setEnLastName(formValue.getValue("EN_FIRST_LAST_NAME", "EN_LAST_NAME_"+PREFIX_ELEMENT_ID, personalInfo.getEnLastName()));
//			personalInfo.setBirthDate(formValue.getValue("EN_BIRTH_DATE", "EN_BIRTH_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getBirthDate()));
			personalInfo.setBirthDate(formValue.getValue("EN_BIRTH_DATE", "TH_BIRTH_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getBirthDate()));
			personalInfo.setGender(formValue.getValue("GENDER", "GENDER_"+PREFIX_ELEMENT_ID, personalInfo.getGender()));
			personalInfo.setNationality(formValue.getValue("NATIONALITY", "NATIONALITY_"+PREFIX_ELEMENT_ID, personalInfo.getNationality()));
			personalInfo.setMarried(formValue.getValue("MARRIED", "MARRIED_"+PREFIX_ELEMENT_ID, personalInfo.getMarried()));
			personalInfo.setDegree(formValue.getValue("DEGREE", "DEGREE_"+PREFIX_ELEMENT_ID, personalInfo.getDegree()));
			personalInfo.setVisaType(formValue.getValue("VISA_TYPE", "VISA_TYPE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaType()));
			personalInfo.setVisaExpDate(formValue.getValue("VISA_EXPIRE_DATE", "VISA_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaExpDate()));
			personalInfo.setWorkPermitNo(formValue.getValue("WORK_PERMIT_NO", "WORK_PERMIT_NO_"+PREFIX_ELEMENT_ID, personalInfo.getWorkPermitNo()));
			personalInfo.setWorkPermitExpDate(formValue.getValue("WORK_PERMIT_EXPIRE_DATE", "WORK_PERMIT_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getWorkPermitExpDate()));
			personalInfo.setCidExpDate(formValue.getValue("PASSPORT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getCidExpDate()));
			personalInfo.setCidExpDate(formValue.getValue("IDNO_EXPIRE_DATE", "IDNO_EXPIRE_DATE"+PREFIX_ELEMENT_ID, personalInfo.getCidExpDate()));
			personalInfo.setOccupation(formValue.getValue("OCCUPATION", "OCCUPATION_"+PREFIX_ELEMENT_ID, personalInfo.getOccupation()));
			if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
				personalInfo.setOccpationOther(formValue.getValue("OCCUPATION", "OCCUPATION_"+PREFIX_ELEMENT_ID, personalInfo.getOccpationOther()));
			}
		}
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("SUB PERSONAL INFO 2 AUDIT FORM ");
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		try{
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
			ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
			PersonalInfoDataM personalSupplementary = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			PersonalInfoDataM lastPersonalSupplementary = lastApplicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
			
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
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return auditFormUtil.getAuditForm();
	}
	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"CID_TYPE","ID_NO","TH_FIRST_NAME","TH_LAST_NAME","TH_BIRTH_DATE",
				 "EN_FIRST_NAME","EN_LAST_NAME","GENDER","NATIONALITY","MARRIED","OCCUPATION",
				 "CIS_NUMBER", "TH_MID_NAME", "TH_TITLE_CODE", "TH_TITLE_DESC", "EN_TITLE_CODE",
				 "EN_TITLE_DESC", "EN_MID_NAME", "VISA_TYPE", "VISA_EXPIRE_DATE", "WORK_PERMIT_NO",
				 "WORK_PERMIT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE", "RELATION_WITH_APPLICANT",
				 "RELATION_WITH_APPLICANT_OTHER", "DISCLOSURE_FLAG","IDNO_EXPIRE_DATE"};
		try {
			
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
			if(!Util.empty(objectForm)){
				 for(String elementId:filedNames){
					 FieldElement fieldElement = new FieldElement();
					 fieldElement.setElementId(elementId);	
					 fieldElement.setElementLevel(CompareDataM.UniqueLevel.PERSONAL);
					 fieldElement.setElementGroupId(personalInfo.getPersonalId());
					 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
					 fieldElements.add(fieldElement);
				 }
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
