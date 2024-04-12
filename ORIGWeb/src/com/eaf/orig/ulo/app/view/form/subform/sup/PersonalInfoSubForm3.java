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
public class PersonalInfoSubForm3 extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PersonalInfoSubForm3.class);
	String subformId = "SUP_PERSONAL_INFO_SUBFROM_3";
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
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
		String VISA_TYPE = request.getParameter("VISA_TYPE");
		String VISA_EXPIRE_DATE = request.getParameter("VISA_EXPIRE_DATE");
		String WORK_PERMIT_NO = request.getParameter("WORK_PERMIT_NO");
		String WORK_PERMIT_EXPIRE_DATE = request.getParameter("WORK_PERMIT_EXPIRE_DATE");
		String PASSPORT_EXPIRE_DATE = request.getParameter("PASSPORT_EXPIRE_DATE");
		String MARRIED = request.getParameter("MARRIED");
//		String OCCUPATION = request.getParameter("OCCUPATION");
		String RELATION_WITH_APPLICANT = request.getParameter("RELATION_WITH_APPLICANT");
		String RELATION_WITH_APPLICANT_OTHER = request.getParameter("RELATION_WITH_APPLICANT_OTHER");
		String DISCLOSURE_FLAG = request.getParameter("DISCLOSURE_FLAG");
		String ISSUE_DOCUMENT_DATE = request.getParameter("ISSUE_DOCUMENT_DATE");


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
//		logger.debug("OCCUPATION >>"+OCCUPATION);
		logger.debug("RELATION_WITH_APPLICANT >>"+RELATION_WITH_APPLICANT);
		logger.debug("RELATION_WITH_APPLICANT_OTHER >>"+RELATION_WITH_APPLICANT_OTHER);
		logger.debug("DISCLOSURE_FLAG >>"+DISCLOSURE_FLAG);
		logger.debug("ISSUE_DOCUMENT_DATE >>"+ISSUE_DOCUMENT_DATE);
		
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
		personalInfo.setVisaExpDate(FormatUtil.toDate(VISA_EXPIRE_DATE,FormatUtil.EN));
		personalInfo.setWorkPermitNo(WORK_PERMIT_NO);
		personalInfo.setWorkPermitExpDate(FormatUtil.toDate(WORK_PERMIT_EXPIRE_DATE,FormatUtil.TH));
//		personalInfo.setPassportDate
		personalInfo.setMarried(MARRIED);
//		personalInfo.setOccupation(OCCUPATION);
		personalInfo.setRelationshipType(RELATION_WITH_APPLICANT);
		personalInfo.setRelationshipTypeDesc(RELATION_WITH_APPLICANT_OTHER);
		personalInfo.setDisclosureFlag(DISCLOSURE_FLAG);
		if(!Util.empty(PASSPORT_EXPIRE_DATE))
			personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE,FormatUtil.EN));
		    personalInfo.setCidIssueDate(FormatUtil.toDate(ISSUE_DOCUMENT_DATE,FormatUtil.EN));	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicationInfo.getPersonalInfo();
		FormErrorUtil formError = new FormErrorUtil();
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		String roleId = ORIGForm.getRoleId();
		logger.debug("subformId >> "+subformId);
		formError.mandatory(subformId,"CIS_NUMBER",personalInfo.getCisNo(),request);
		formError.mandatory(subformId,"CID_TYPE",personalInfo.getCidType(),request);
		formError.mandatory(subformId,"ID_NO",personalInfo.getIdno(),request);
		formError.mandatory(subformId,"TH_FIRST_LAST_NAME",personalInfo,request);
		formError.mandatory(subformId,"EN_FIRST_LAST_NAME",personalInfo,request);
//		formError.mandatory(subformId,"EMBOSS_NAME",personalInfo.getemboss,request);
		formError.mandatoryDate(subformId,"TH_BIRTH_DATE",personalInfo.getBirthDate(),request);
		formError.mandatoryDate(subformId,"EN_BIRTH_DATE",personalInfo.getBirthDate(),request,DateValidateUtil.EN);
		formError.mandatory(subformId,"GENDER",personalInfo.getGender(),request);
		formError.mandatory(subformId,"NATIONALITY",personalInfo.getNationality(),request);
		formError.mandatory(subformId,"VISA_TYPE",personalInfo.getVisaType(),request);
		formError.mandatoryDate(subformId,"VISA_EXPIRE_DATE",personalInfo.getVisaExpDate(),request,DateValidateUtil.EN);
		formError.mandatory(subformId,"WORK_PERMIT_NO",personalInfo.getWorkPermitNo(),request);
		formError.mandatoryDate(subformId,"WORK_PERMIT_EXPIRE_DATE",personalInfo.getWorkPermitExpDate(),request,DateValidateUtil.EN);
//		formError.mandatory(subformId,"PASSPORT_EXPIRE_DATE",personalInfo.getpassport,request);
		formError.mandatory(subformId,"MARRIED",personalInfo.getMarried(),request);
		formError.mandatory(subformId,"OCCUPATION",personalInfo.getOccupation(),request);
		formError.mandatory(subformId,"RELATION_WITH_APPLICANT",personalInfo.getRelationshipType(),request);
		formError.mandatoryDate(subformId, "ISSUE_DOCUMENT_DATE", personalInfo.getCidIssueDate(), request, DateValidateUtil.EN);	
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
	
		formError.mandatory(subformId, "TITLE_CODE", personalInfo, request);
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
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
		auditFormUtil.auditForm(subformId, "MARRIED", personalSupplementary, lastPersonalSupplementary, request);
		auditFormUtil.auditForm(subformId, "OCCUPATION", personalSupplementary, lastPersonalSupplementary, request);
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
				 ,"EN_FIRST_NAME","EN_LAST_NAME","GENDER","NATIONALITY","MARRIED","OCCUPATION"};
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
