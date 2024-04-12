package com.eaf.orig.ulo.app.view.form.subform.normal;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
import com.eaf.orig.shared.model.Age;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;


@SuppressWarnings("serial")
public class PersonalInfoSubForm1 extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PersonalInfoSubForm1.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String subformId = "NORMAL_PERSONAL_INFO_SUBFORM_1";
	private String MINIMUM_OLD_CHIELD = SystemConfig.getGeneralParam("MINIMUM_OLD_CHIELD");
	String CIDTYPE_PASSPORT=SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String VERIFICATION_CHECK_AGE_TWENTY = SystemConstant.getConstant("VERIFICATION_CHECK_AGE_TWENTY");
	String CIDTYPE_NON_THAI_NATINALITY = SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY");
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
		String MARRIED = request.getParameter("MARRIED");
		String DEGREE = request.getParameter("DEGREE");
		String VISA_TYPE = request.getParameter("VISA_TYPE");
		String VISA_EXPIRE_DATE = request.getParameter("VISA_EXPIRE_DATE");
		String PASSPORT_EXPIRE_DATE = request.getParameter("PASSPORT_EXPIRE_DATE");
		String IDNO_EXPIRE_DATE = request.getParameter("IDNO_EXPIRE_DATE");
		String CONSENT_MODEL = request.getParameter("CONSENT_MODEL");
		String ID_NO_CONSENT = request.getParameter("ID_NO_CONSENT");
		String PAYROLL_DATE = request.getParameter("PAYROLL_DATE");
		
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
		logger.debug("DEGREE >>"+DEGREE);
		logger.debug("VISA_TYPE >>"+VISA_TYPE);
		logger.debug("VISA_EXPIRE_DATE >>"+VISA_EXPIRE_DATE);
		logger.debug("PASSPORT_EXPIRE_DATE >>"+PASSPORT_EXPIRE_DATE);
		logger.debug("IDNO_EXPIRE_DATE >>"+IDNO_EXPIRE_DATE);
		logger.debug("CONSENT_MODEL >>"+CONSENT_MODEL);
		logger.debug("ID_NO_CONSENT >> "+ID_NO_CONSENT );
		logger.debug("PAYROLL_DATE >> "+PAYROLL_DATE );
		
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
		personalInfo.setDegree(DEGREE);
		personalInfo.setVisaType(VISA_TYPE);
		personalInfo.setVisaExpDate(FormatUtil.toDate(VISA_EXPIRE_DATE,FormatUtil.EN));
		personalInfo.setConsentModelFlag(CONSENT_MODEL);
		if(!Util.empty(PASSPORT_EXPIRE_DATE))
		personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE,FormatUtil.EN));
		if(!Util.empty(IDNO_EXPIRE_DATE))
			personalInfo.setCidExpDate(FormatUtil.toDate(IDNO_EXPIRE_DATE,FormatUtil.EN));
		personalInfo.setIdNoConsent(ID_NO_CONSENT);
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
		personalInfo.setPayrollDate( PAYROLL_DATE );
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String TH_BIRTH_DATE = request.getParameter("TH_BIRTH_DATE");
		Date thBirthDate = !Util.empty(TH_BIRTH_DATE) ? FormatUtil.toDate(TH_BIRTH_DATE,FormatUtil.TH) : personalInfo.getBirthDate() ;
		FormErrorUtil formError = new FormErrorUtil(personalInfo);
		logger.debug("subformId >> "+subformId);
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
		formError.mandatory(subformId,"VISA_TYPE",personalInfo.getVisaType(),request);
		formError.mandatory(subformId,"VISA_TYPE",personalInfo.getVisaType(),request);
		formError.mandatory(subformId,"CONSENT_MODEL",personalInfo,request);
		formError.mandatoryDate(subformId,"VISA_EXPIRE_DATE",personalInfo.getVisaExpDate(),request,DateValidateUtil.EN);
		formError.mandatoryDate(subformId,"PASSPORT_EXPIRE_DATE",personalInfo.getCidExpDate(),request,DateValidateUtil.EN);

		//Validate TH_FIRST_LAST_NAME PASSPORT
		if(CIDTYPE_PASSPORT.equals(personalInfo.getCidType())){
			if(PersonalInfoUtil.validateRegExpTH(personalInfo)){
				formError.error("TH_FIRST_LAST_NAME",MessageErrorUtil.getText(request,"ERROR_THAI_FORMAT"));
			}
		}
		if(!Util.empty(personalInfo.getPayrollDate()) && (Integer.parseInt(personalInfo.getPayrollDate()) > 31 || Integer.parseInt(personalInfo.getPayrollDate()) < 1) ){
			formError.error("PAYROLL_DATE", MessageErrorUtil.getText(request,"ERROR_FORMAT_PAYROLL_DATE"));
		}
		if(!KPLUtil.isKPL_TOPUP(applicationGroup))
		{
			formError.mandatory(subformId,"MARRIED",personalInfo.getMarried(),request);
			formError.mandatory(subformId,"DEGREE",personalInfo.getDegree(),request);
		}
		
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
		PersonalInfoDataM 	lastpersonalInfo=	lastapplicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.setObjectValue(personalInfo.getPersonalType());
		auditFormUtil.auditForm(subformId,"CID_TYPE",null,null,request);
		auditFormUtil.auditForm(subformId,"TH_FIRST_LAST_NAME", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"EN_FIRST_LAST_NAME", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"ID_NO", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"EN_BIRTH_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"GENDER", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"NATIONALITY", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"MARRIED", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId,"DEGREE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "PASSPORT_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "VISA_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "VISA_TYPE", personalInfo,lastpersonalInfo, request);
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
//		personalInfo.setBirthDate(formValue.getValue("EN_BIRTH_DATE", "EN_BIRTH_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getBirthDate()));
		personalInfo.setGender(formValue.getValue("GENDER", "GENDER_"+PREFIX_ELEMENT_ID, personalInfo.getGender()));
		personalInfo.setNationality(formValue.getValue("NATIONALITY", "NATIONALITY_"+PREFIX_ELEMENT_ID, personalInfo.getNationality()));
		personalInfo.setMarried(formValue.getValue("MARRIED", "MARRIED_"+PREFIX_ELEMENT_ID, personalInfo.getMarried()));
		personalInfo.setDegree(formValue.getValue("DEGREE", "DEGREE_"+PREFIX_ELEMENT_ID, personalInfo.getDegree()));
		personalInfo.setVisaType(formValue.getValue("VISA_TYPE", "VISA_TYPE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaType()));
		personalInfo.setVisaExpDate(formValue.getValue("VISA_EXPIRE_DATE", "VISA_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getVisaExpDate()));
		personalInfo.setCidExpDate(formValue.getValue("IDNO_EXPIRE_DATE", "IDNO_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getCidExpDate()));
		personalInfo.setCidExpDate(formValue.getValue("PASSPORT_EXPIRE_DATE", "PASSPORT_EXPIRE_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getCidExpDate()));
		personalInfo.setConsentModelFlag(formValue.getValue("CONSENT_MODEL", "CONSENT_MODEL_"+PREFIX_ELEMENT_ID, personalInfo.getConsentModelFlag()));
		personalInfo.setPayrollDate(formValue.getValue("PAYROLL_DATE", "PAYROLL_DATE_"+PREFIX_ELEMENT_ID, personalInfo.getPayrollDate()));
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"CID_TYPE","ID_NO","TH_FIRST_NAME","TH_LAST_NAME","TH_BIRTH_DATE","DEGREE"
							,"EN_FIRST_NAME","EN_LAST_NAME","GENDER","NATIONALITY","MARRIED","DEGREE","VISA_TYPE","VISA_EXPIRE_DATE","CONSENT_MODEL","PAYROLL_DATE"};
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
	public boolean inLength(Integer value,Integer start ,Integer end){
		if(value>=start && value <= end){
			return true;
		}else{
			return false;
		}
	}
}
