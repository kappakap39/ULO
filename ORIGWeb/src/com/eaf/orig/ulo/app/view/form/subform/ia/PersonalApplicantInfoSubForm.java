package com.eaf.orig.ulo.app.view.form.subform.ia;

import java.sql.Date;
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
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;

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
import com.eaf.orig.shared.model.Age;
  



public class PersonalApplicantInfoSubForm extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(PersonalApplicantInfoSubForm.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String subformId = "IA_PERSONAL_APPLICANT_INFO_SUBFORM";
	private String CIDTYPE_PASSPORT = ServiceCache.getConstant("CIDTYPE_PASSPORT");
	private String VERIFICATION_CHECK_AGE_TWENTY = SystemConstant.getConstant("VERIFICATION_CHECK_AGE_TWENTY");
	private String FORM_NAME_ORIG = SystemConstant.getConstant("FORM_NAME_ORIG");

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		PersonalApplicationInfoDataM personalApplicantInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicantInfo.getPersonalInfo();

//		String PERSONAL_TYPE = request.getParameter("PERSONAL_TYPE");
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
		String PASSPORT_EXPIRE_DATE = request.getParameter("PASSPORT_EXPIRE_DATE");
		String IDNO_EXPIRE_DATE = request.getParameter("IDNO_EXPIRE_DATE");
		String CONSENT_MODEL = request.getParameter("CONSENT_MODEL");
		String ID_NO_CONSENT = request.getParameter("ID_NO_CONSENT");
		String PAYROLL_DATE = request.getParameter("PAYROLL_DATE");
		
		logger.debug("PERSONAL_TYPE >>"+PERSONAL_TYPE);
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
		logger.debug("PASSPORT_EXPIRE_DATE >>"+PASSPORT_EXPIRE_DATE);
		logger.debug("IDNO_EXPIRE_DATE >>"+IDNO_EXPIRE_DATE);
		logger.debug("CONSENT_MODEL >>"+CONSENT_MODEL);
		logger.debug("ID_NO_CONSENT >> "+ID_NO_CONSENT );
		logger.debug("PAYROLL_DATE >> "+PAYROLL_DATE );
//		personalInfo.setPersonalType(PERSONAL_TYPE);
		personalInfo.setCisNo(CIS_NUMBER);
		personalInfo.setCidType(CID_TYPE);
		personalInfo.setIdno(ID_NO);
		personalInfo.setThTitleCode(TH_TITLE_CODE);
		personalInfo.setThTitleDesc(TH_TITLE_DESC);
		personalInfo.setThFirstName(TH_FIRST_NAME);
		personalInfo.setThMidName(TH_MID_NAME);
		personalInfo.setThLastName(TH_LAST_NAME);
		personalInfo.setBirthDate(FormatUtil.toDate(EN_BIRTH_DATE,FormatUtil.EN));
		personalInfo.setVisaType(VISA_TYPE);
		personalInfo.setVisaExpDate(FormatUtil.toDate(VISA_EXPIRE_DATE,FormatUtil.EN));
		if(!Util.empty(PASSPORT_EXPIRE_DATE))
		personalInfo.setCidExpDate(FormatUtil.toDate(PASSPORT_EXPIRE_DATE,FormatUtil.EN));
		personalInfo.setConsentModelFlag(CONSENT_MODEL);
		personalInfo.setIdNoConsent(ID_NO_CONSENT);
		personalInfo.setPayrollDate(PAYROLL_DATE);

	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalApplicationInfoDataM personalApplicantInfo = (PersonalApplicationInfoDataM)appForm;
		PersonalInfoDataM personalInfo = personalApplicantInfo.getPersonalInfo();
		 
		FormErrorUtil formError = new FormErrorUtil(personalInfo);
		logger.debug("subformId >> "+subformId);
		String TH_BIRTH_DATE = request.getParameter("TH_BIRTH_DATE");
		Date thBirthDate = !Util.empty(TH_BIRTH_DATE) ? FormatUtil.toDate(TH_BIRTH_DATE,FormatUtil.TH) : personalInfo.getBirthDate() ;
		if(PersonalInfoUtil.isBundingTemplate(applicationGroup) && Util.empty(personalInfo.getCisNo())){
			formError.error("CIS_NUMBER", MessageErrorUtil.getText(request,"ERROR_BUNDING_CIS_NO"));
		}
		logger.debug("Personal Type : "+personalInfo.getPersonalType());
		formError.mandatory(subformId,"PERSONAL_TYPE",personalInfo.getPersonalType(),request);	
		formError.mandatory(subformId,"CID_TYPE",personalInfo.getCidType(),request);	
		formError.mandatory(subformId,"ID_NO",personalInfo.getIdno(),request);
		formError.mandatory(subformId,"TH_FIRST_LAST_NAME",personalInfo,request);
		formError.mandatoryDate(subformId,"EN_BIRTH_DATE",thBirthDate,request);
		formError.mandatoryDate(subformId,"EN_BIRTH_DATE",personalInfo.getBirthDate(),request,DateValidateUtil.EN);
		//formError.mandatoryDate(subformId,"EN_BIRTH_DATE",personalInfo.getBirthDate(),request,DateValidateUtil.EN,DateValidateUtil.BIRTH_DATE_VALIDATE);
		formError.mandatory(subformId,"VISA_TYPE",personalInfo.getVisaType(),request);
		formError.mandatoryDate(subformId,"VISA_EXPIRE_DATE",personalInfo.getVisaExpDate(),request,DateValidateUtil.EN);
		formError.mandatoryDate(subformId,"PASSPORT_EXPIRE_DATE",personalInfo,request,DateValidateUtil.EN);
		formError.mandatory(subformId,"CONSENT_MODEL",personalInfo,request);
		//formError.mandatory(subformId,"ID_NO_CONSENT",personalInfo.getIdNoConsent(),request);
		
		// id_no_pic validate 	
		logger.debug("applicationType >> "+ applicationGroup.getApplicationType());
		// check type PERSONAL_TYPE A = main S = Sub
		String tmpPersonalType  =personalInfo.getPersonalType();
		String mainCheck = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		// get VALIDATE FORM TYPE
		HashMap<String,String> SubformData = super.getSubformData();
		String VALIDATE_FORM_TYPE = SubformData.get("VALIDATE_FORM_TYPE");
		logger.debug("VALIDATE_FORM_TYPE >>"+VALIDATE_FORM_TYPE);
		// end get formHandle for check Display Type
		// validate ID_NO_CONSENT
//		if(mainCheck.equals(tmpPersonalType)){ 
//			//applicationType have not * is ADD and NULL
//			if("ADD".equals(applicationGroup.getApplicationType())||Util.empty(applicationGroup.getApplicationType())){
//				//not validate
//			}else{ //applicationType Other not ADD and NULL
//				if(Util.empty(personalInfo.getIdno())){ // id_no is empty
//					if(Util.empty(personalInfo.getIdNoConsent())){ // id_no_consent is empty
//						formError.error("ID_NO_CONSENT", MessageErrorUtil.getText(request,"ERROR_ID_NO_PICTURE_IS_BLANK"));
//					}else{ //id_no_consent is not empty
//						if(!personalInfo.getIdno().equals(personalInfo.getIdNoConsent())){
//							formError.error("ID_NO_CONSENT", MessageErrorUtil.getText(request,"ERROR_ID_NO_AND_ID_NO_CONSENT_NOT_MATCH"));
//						}
//					}
//				}else{// id_no isn't empty
//				
//					if(!personalInfo.getIdno().equals(personalInfo.getIdNoConsent())){
//						formError.error("ID_NO_CONSENT", MessageErrorUtil.getText(request,"ERROR_ID_NO_AND_ID_NO_CONSENT_NOT_MATCH"));
//					}
//					
//				}
//			}
//			logger.debug("VERIFICATION_CHECK_AGE_TWENTY >>> "+ VERIFICATION_CHECK_AGE_TWENTY);
//			// validate If customer age is lower 20 years 
//			/*if(!Util.empty(thBirthDate)){
//				java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
//				Age personDate = Util.age(thBirthDate, sqlDate);
//				logger.debug("ageYear :"+personDate.getYears());
//				 if(personDate.getYears() < Integer.parseInt(VERIFICATION_CHECK_AGE_TWENTY)){
//					 formError.error("TH_BIRTH_DATE", MessageErrorUtil.getText(request,"ERROR_BIRDTH_DATE_LEST_TWENTY_AGE"));
//				 }
//			}*/
//			// end validate If customer age is lower 20 years 
//		}
		/*String idNoConsent=personalInfo.getIdNoConsent();
		if(!Util.empty(idNoConsent) && !idNoConsent.equals(personalInfo.getIdno())){
			formError.error("ID_NO_CONSENT", MessageErrorUtil.getText(request,"ERROR_ID_NO_AND_ID_NO_CONSENT_NOT_MATCH"));
		}*/
		
		if(!Util.empty(personalInfo.getPayrollDate()) && (Integer.parseInt(personalInfo.getPayrollDate()) > 31 || Integer.parseInt(personalInfo.getPayrollDate()) < 1) ){
			formError.error("PAYROLL_DATE", MessageErrorUtil.getText(request,"ERROR_FORMAT_PAYROLL_DATE"));
		}
		if(PersonalInfoUtil.isBundingTemplate(applicationGroup) && Util.empty(personalInfo.getCisNo())){
			formError.error("CIS_NUMBER", MessageErrorUtil.getText(request,"ERROR_BUNDING_CIS_NO"));
		}
//		formError.mandatory(subformId,"IDNO_EXPIRE_DATE",personalInfo.getCidExpDate(),request);
			String MINIMUM_OLD_CHIELD = SystemConfig.getGeneralParam("MINIMUM_OLD_CHIELD");
			if(PersonalInfoUtil.validateAge(FormatUtil.getInt(MINIMUM_OLD_CHIELD), personalInfo)){
			formError.error("TH_TITLE_DESC", MessageErrorUtil.getText(request,"ERROR_TITLECODE_MORE_AGE"));
			}
			// 
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
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"CID_TYPE","ID_NO","TH_FIRST_NAME","TH_LAST_NAME","TH_BIRTH_DATE","VISA_TYPE","VISA_EXPIRE_DATE","PASSPORT_EXPIRE_DATE","IDNO_EXPIRE_DATE","CONSENT_MODEL","PAYROLL_DATE"};
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
		auditFormUtil.auditForm(subformId, "ID_NO", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "TH_FIRST_LAST_NAME", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "EN_BIRTH_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "VISA_TYPE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "VISA_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "PASSPORT_EXPIRE_DATE", personalInfo,lastpersonalInfo, request);
		return auditFormUtil.getAuditForm();
		
	}
}
