package com.eaf.orig.ulo.app.view.form.subform.normal;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.model.Age;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;

public class PersonalInfoSubForm3 extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PersonalInfoSubForm3.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String CIDTYPE_PASSPORT = ServiceCache.getConstant("CIDTYPE_PASSPORT");
	private String VERIFICATION_CHECK_AGE_TWENTY = SystemConstant.getConstant("VERIFICATION_CHECK_AGE_TWENTY");
	String CIDTYPE_NON_THAI_NATINALITY = SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY");
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
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
		String CHILDREN_NUM = request.getParameter("CHILDREN_NUM");
		String DISCLOSURE_FLAG = request.getParameter("DISCLOSURE_FLAG");
		String ISSUE_DOCUMENT_DATE = request.getParameter("ISSUE_DOCUMENT_DATE");
		String CONSENT_MODEL = request.getParameter("CONSENT_MODEL");
		String	ID_NO_CONSENT	= request.getParameter("ID_NO_CONSENT");
		String	PAYROLL_DATE = request.getParameter("PAYROLL_DATE");
		
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
		logger.debug("CHILDREN_NUM >>"+CHILDREN_NUM);
		logger.debug("DISCLOSURE_FLAG >>"+DISCLOSURE_FLAG);
		logger.debug("ISSUE_DOCUMENT_DATE >>"+ISSUE_DOCUMENT_DATE);
		logger.debug("CONSENT_MODEL >>"+CONSENT_MODEL);
		logger.debug("ID_NO_CONSENT >>"+ID_NO_CONSENT);
		logger.debug("PAYROLL_DATE >>"+PAYROLL_DATE);
		
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
//		personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE,FormatUtil.EN));
		personalInfo.setMarried(MARRIED);
		personalInfo.setDegree(DEGREE);
		personalInfo.setNoOfChild(FormatUtil.toBigDecimal(CHILDREN_NUM,true));
		personalInfo.setDisclosureFlag(!Util.empty(DISCLOSURE_FLAG)?DISCLOSURE_FLAG:"0");
		personalInfo.setConsentModelFlag(CONSENT_MODEL);
		personalInfo.setIdNoConsent(ID_NO_CONSENT);
		if(!Util.empty(PASSPORT_EXPIRE_DATE))
			personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE,FormatUtil.EN));
		    personalInfo.setCidIssueDate(FormatUtil.toDate(ISSUE_DOCUMENT_DATE,FormatUtil.EN));
	
		if(CIDTYPE_NON_THAI_NATINALITY.equals(CID_TYPE)){
				if(!Util.empty(ID_NO)&&ID_NO.length()>=13){
					if("00".equals(ID_NO.substring(0, 2))){
						
					}else if("0".equals(ID_NO.substring(0, 1))){
						
						personalInfo.setNationality("XX");;
						
					}else if("6".equals(ID_NO.substring(0, 1))&&inLength(Integer.valueOf(ID_NO.substring(5, 7)),0,49)){
						
					}else if("6".equals(ID_NO.substring(0, 1))&&inLength(Integer.valueOf(ID_NO.substring(5, 7)),50,72)){
						personalInfo.setNationality("XX");
					}else{
						personalInfo.setNationality("TH");
					}
				}
			}
		
		if (!Util.empty(PAYROLL_DATE)) personalInfo.setPayrollDate(PAYROLL_DATE); 
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		String roleId = ORIGForm.getRoleId();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String TH_BIRTH_DATE = request.getParameter("TH_BIRTH_DATE");
		Date thBirthDate = !Util.empty(TH_BIRTH_DATE) ? FormatUtil.toDate(TH_BIRTH_DATE,FormatUtil.TH) : personalInfo.getBirthDate() ;
		FormErrorUtil formError = new FormErrorUtil(personalInfo);
		String subformId = "NORMAL_PERSONAL_INFO_SUBFORM_3";
		String source = applicationGroup.getSource();
		logger.debug("subformId >> "+subformId);
		logger.debug("source >> "+source);
		logger.debug("roleId >> "+roleId);
		if(!(ROLE_DE2.equals(roleId) && ApplicationUtil.eApp(source))){
			formError.mandatory(subformId,"CID_TYPE",personalInfo.getCidType(),request);	
			formError.mandatory(subformId,"ID_NO",personalInfo.getIdno(),request);
			formError.mandatory(subformId,"TH_FIRST_LAST_NAME",personalInfo,request);
			formError.mandatory(subformId,"EN_FIRST_LAST_NAME",personalInfo,request);
	//		formError.mandatory(subformId,"EMBOSS_NAME",personalInfo.getEmbos,request);
			formError.mandatoryDate(subformId, "TH_BIRTH_DATE", personalInfo.getBirthDate(), request);
			formError.mandatoryDate(subformId, "EN_BIRTH_DATE", personalInfo.getBirthDate(), request, DateValidateUtil.EN);
			formError.mandatoryDate(subformId, "EN_BIRTH_DATE", personalInfo.getBirthDate(), request, DateValidateUtil.EN);
			formError.mandatory(subformId, "GENDER", personalInfo.getGender(), request);
			formError.mandatory(subformId, "NATIONALITY", personalInfo.getNationality(), request);
			formError.mandatory(subformId, "VISA_TYPE", personalInfo.getVisaType(), request);
			formError.mandatoryDate(subformId, "VISA_EXPIRE_DATE", personalInfo.getVisaExpDate(), request, DateValidateUtil.EN);
			formError.mandatory(subformId, "WORK_PERMIT_NO", personalInfo.getWorkPermitNo(), request);
			formError.mandatoryDate(subformId, "WORK_PERMIT_EXPIRE_DATE", personalInfo.getWorkPermitExpDate(), request);
			formError.mandatoryDate(subformId, "PASSPORT_EXPIRE_DATE", personalInfo.getCidExpDate(), request, DateValidateUtil.EN);
			formError.mandatory(subformId, "MARRIED", personalInfo.getMarried(), request);
			formError.mandatory(subformId, "DEGREE", personalInfo.getDegree(), request);
			formError.mandatory(subformId, "CHILDREN_NUM", personalInfo.getNoOfChild(), request);
		    formError.mandatoryDate(subformId, "ISSUE_DOCUMENT_DATE", personalInfo.getCidIssueDate(), request, DateValidateUtil.EN);	
		    formError.mandatory(subformId, "TITLE_CODE", personalInfo, request);
		    formError.mandatory(subformId,"CONSENT_MODEL",personalInfo,request);
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
			// validate If customer age is lower 20 years 
			/*if(!Util.empty(thBirthDate)){
				java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
				Age personDate = Util.age(thBirthDate, sqlDate);
				logger.debug("ageYear :"+personDate.getYears());
				 if(personDate.getYears() < Integer.parseInt(VERIFICATION_CHECK_AGE_TWENTY)){
					 formError.error("TH_BIRTH_DATE", MessageErrorUtil.getText(request,"ERROR_BIRDTH_DATE_LEST_TWENTY_AGE"));
				 }
			}*/
			// end validate If customer age is lower 20 years
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastapplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
		
		PersonalInfoDataM 	personalInfo=	applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		
		PersonalInfoDataM 	lastpersonalInfo=	lastapplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.setObjectValue(personalInfo.getPersonalType());
		auditFormUtil.auditForm(subformId,"CID_TYPE",null,null,request);
		auditFormUtil.auditForm(subformId, "TH_FIRST_LAST_NAME", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "EN_FIRST_LAST_NAME", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "ID_NO", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "EN_BIRTH_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"GENDER", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"NATIONALITY", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"MARRIED", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"DEGREE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "PASSPORT_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "VISA_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "VISA_TYPE", personalInfo,lastpersonalInfo, request);
		
		return auditFormUtil.getAuditForm();
	}	
	private boolean validateRegExp(PersonalInfoDataM personalInfo){
		String regex1 = "[a-zA-Z0-9]";
		String regex2 = "^\\-+";
		String regex3 = "\\-+$";
		String regex4 = "^\\-$";
		Pattern pattern1 = Pattern.compile(regex1);
		Pattern pattern2 = Pattern.compile(regex2);
		Pattern pattern3 = Pattern.compile(regex3);
		Pattern pattern4 = Pattern.compile(regex4);
		if(!Util.empty(personalInfo.getEnLastName())){
			logger.debug("EnLastName is: "+personalInfo.getEnLastName());
			logger.debug("CidType is: "+personalInfo.getCidType());
			if((pattern1.matcher(personalInfo.getEnLastName())).find()){
					if(pattern2.matcher(personalInfo.getEnLastName()).find() || pattern3.matcher(personalInfo.getEnLastName()).find() || pattern4.matcher(personalInfo.getEnLastName()).find()){
						return true;
					}
			}else{
				if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType())){
					if(!pattern4.matcher(personalInfo.getEnLastName()).find()){
						return true;
					}
				}else{
					return true;
				}
			}
		}
		return false;
	}
	public boolean inLength(Integer value,Integer start ,Integer end){
		if(value>=start && value <= end){
			return true;
		}else{
			return false;
		}
	}
}
