package com.eaf.orig.ulo.app.view.form.subform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class AllOccupationSubForm3  extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AllOccupationSubForm3.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private String PROFESSION_TYPE_JUDGE = SystemConstant.getConstant("PROFESSION_TYPE_JUDGE");
	private String FIELD_ID_IMAGE_GOVERNMENT = SystemConstant.getConstant("FIELD_ID_IMAGE_GOVERNMENT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		String K_GROUP = request.getParameter("K_GROUP");
		String OCCUPATION = request.getParameter("OCCUPATION");
		String PROFESSION = request.getParameter("PROFESSION");
		String POSITION = request.getParameter("POSITION");
		String POSITION_CODE = request.getParameter("POSITION_CODE");
		String POSITION_LEVEL = request.getParameter("POSITION_LEVEL");
		String CONTACT_TIME = request.getParameter("CONTACT_TIME");
		String TOT_WORK_YEAR = request.getParameter("TOT_WORK_YEAR");
		String TOT_WORK_MONTH = request.getParameter("TOT_WORK_MONTH");
		String BUSINESS_TYPE = request.getParameter("BUSINESS_TYPE");
		String BUSINESS_NATURE = request.getParameter("BUSINESS_NATURE");
		String PREV_COMPANY_TITLE = request.getParameter("PREV_COMPANY_TITLE");
		String PREV_COMPANY = request.getParameter("PREV_COMPANY");
		String PREV_COMPANY_PHONE_NO = request.getParameter("PREV_COMPANY_PHONE_NO");
		String PREV_WORK_YEAR = request.getParameter("PREV_WORK_YEAR");
		String PREV_WORK_MONTH = request.getParameter("PREV_WORK_MONTH");
		String SPECIAL_MERCHANT_TYPE = request.getParameter("SPECIAL_MERCHANT_TYPE");

		logger.debug("K_GROUP >>"+K_GROUP);
		logger.debug("OCCUPATION >>"+OCCUPATION);
		logger.debug("PROFESSION >>"+PROFESSION);
		logger.debug("POSITION >>"+POSITION);
		logger.debug("POSITION_CODE >>"+POSITION_CODE);
		logger.debug("POSITION_LEVEL >>"+POSITION_LEVEL);
		logger.debug("TOT_WORK_YEAR >>"+TOT_WORK_YEAR);
		logger.debug("TOT_WORK_MONTH >>"+TOT_WORK_MONTH);
		logger.debug("CONTACT_TIME >>"+CONTACT_TIME);
		logger.debug("BUSINESS_TYPE >>"+BUSINESS_TYPE);
		logger.debug("BUSINESS_NATURE >>"+BUSINESS_NATURE);
		logger.debug("PREV_COMPANY_TITLE >>"+PREV_COMPANY_TITLE);
		logger.debug("PREV_COMPANY >>"+PREV_COMPANY);
		logger.debug("PREV_COMPANY_PHONE_NO >>"+PREV_COMPANY_PHONE_NO);
		logger.debug("PREV_WORK_YEAR >>"+PREV_WORK_YEAR);
		logger.debug("PREV_WORK_MONTH >>"+PREV_WORK_MONTH);
		logger.debug("SPECIAL_MERCHANT_TYPE >>"+SPECIAL_MERCHANT_TYPE);
				
		boolean imageGov = false;
		ArrayList<ApplicationImageSplitDataM> docListArr = applicationGroup.getImageSplits();
		for(ApplicationImageSplitDataM docList : docListArr){
			String IMType = docList.getApplicantTypeIM();
			logger.debug("docList.getDocType() >> "+docList.getDocType());
			if(IM_PERSONAL_TYPE_APPLICANT.equals(IMType) && FIELD_ID_IMAGE_GOVERNMENT.equals(docList.getDocType())){
				imageGov = true;
			}
		}
		
		personalInfo.setPositionCode(POSITION_CODE);
		personalInfo.setPositionDesc(POSITION);
		if(GOVERNMENT_OFFICER.equals(personalInfo.getOccupation()) && imageGov){
			personalInfo.setPositionDesc(null);
		}else if(GOVERNMENT_OFFICER.equals(personalInfo.getOccupation()) && PROFESSION_TYPE_JUDGE.equals(personalInfo.getProfession())){
			personalInfo.setPositionDesc(null);
		}else{
			personalInfo.setPositionCode(null);
		}
		
		personalInfo.setkGroupFlag(K_GROUP);
//		personalInfo.setOccupation(OCCUPATION);
//		personalInfo.setProfession(PROFESSION);
//		personalInfo.setPositionDesc(POSITION);
//		personalInfo.setPositionCode(POSITION_CODE);
		personalInfo.setPositionLevel(POSITION_LEVEL);
		personalInfo.setTotWorkYear(FormatUtil.toBigDecimal(TOT_WORK_YEAR, true));
		personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_MONTH, true));
		
		if(!KPLUtil.isKPL(applicationGroup))
		{
			personalInfo.setContactTime(CONTACT_TIME);
			personalInfo.setPrevCompanyPhoneNo(FormatUtil.removeDash(PREV_COMPANY_PHONE_NO));
		}
		
//		personalInfo.setBusinessType(BUSINESS_TYPE);
		personalInfo.setBusinessNature(BUSINESS_NATURE);
		personalInfo.setPrevCompanyTitle(PREV_COMPANY_TITLE);
		personalInfo.setPrevCompany(PREV_COMPANY);
		
		personalInfo.setPrevWorkYear(FormatUtil.toBigDecimal(PREV_WORK_YEAR, true));
		personalInfo.setPrevWorkMonth(FormatUtil.toBigDecimal(PREV_WORK_MONTH, true));
		
		//KPL Additional
		personalInfo.setSpecialMerchantType(SPECIAL_MERCHANT_TYPE); 
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String subformId = "ALL_OCCUPATION_SUBFORM_3";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		
		formError.mandatory(subformId,"K_GROUP",personalInfo.getkGroupFlag(),request);
		formError.mandatory(subformId,"OCCUPATION",personalInfo.getOccupation(),request);
		formError.mandatory(subformId,"PROFESSION",personalInfo.getProfession(),request);
		formError.mandatory(subformId,"POSITION",personalInfo.getPositionDesc(),request);
		formError.mandatory(subformId,"POSITION_CODE",personalInfo.getPositionCode(),request);
		formError.mandatory(subformId,"POSITION_LEVEL",personalInfo.getPositionLevel(),request);
		formError.mandatory(subformId,"TOT_WORK",personalInfo,request);
		
		if(!KPLUtil.isKPL(applicationGroup))
		{
			formError.mandatory(subformId,"CONTACT_TIME",personalInfo.getContactTime(),request);
			formError.mandatory(subformId,"PREV_COMPANY_PHONE_NO",personalInfo,request);	
		}
		
		formError.mandatory(subformId,"BUSINESS_TYPE",personalInfo.getBusinessType(),request);			
		formError.mandatory(subformId,"PREV_COMPANY",personalInfo,request);	
		formError.mandatory(subformId,"PREV_WORK",personalInfo,request);	
		
		BigDecimal prevWorkYear = personalInfo.getPrevWorkYear();
		BigDecimal prevWorkMonth = personalInfo.getPrevWorkMonth();
		logger.debug("prevWorkYear : " + prevWorkYear);
		logger.debug("prevWorkMonth : " + prevWorkMonth);
//		if (prevWorkYear.intValue() == 0 && prevWorkMonth.intValue() == 0) {
//			// Pass
//			logger.debug(" Year & Month is free.");
//		} else 
		if (Util.empty(prevWorkYear) || (!Util.empty(prevWorkYear) && prevWorkYear.intValue() == 0)) {
			if (!Util.empty(prevWorkMonth) && prevWorkMonth.intValue() <= 0) {
				formError.error("PREV_WORK", MessageErrorUtil.getText(request, "ERROR_INVALID_PREV_WORK"));
			}
		} else if (Util.empty(prevWorkMonth) || (!Util.empty(prevWorkMonth) && prevWorkMonth.intValue() == 0)) {
			if (!Util.empty(prevWorkYear) && prevWorkYear.intValue() <= 0) {
				formError.error("PREV_WORK", MessageErrorUtil.getText(request, "ERROR_INVALID_PREV_WORK"));
			}
		}
		if (!Util.empty(prevWorkMonth) && prevWorkMonth.intValue() > 11) {
			formError.error("PREV_WORK", MessageErrorUtil.getText(request, "ERROR_INVALID_TOT_WORK_MONTH"));
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
		auditFormUtil.auditForm(subformId, "PROFESSION", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "POSITION", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "POSITION_CODE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "POSITION_LEVEL", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "TOT_WORK", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "BUSINESS_TYPE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "PREV_WORK", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "OCCUPATION_SUBFORM", personalInfo,lastpersonalInfo, request);
		//auditFormUtil.auditForm(subformId, "SPECIAL_MERCHANT_TYPE", personalInfo,lastpersonalInfo, request);
		
		return auditFormUtil.getAuditForm();
	}	
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) {
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug("templateId >> "+templateId);		
		RenderSubform render = new RenderSubform();

		String OCCUPATION_TYPE_ALL = SystemConstant.getConstant("OCCUPATION_TYPE_ALL");	
		
		String checkProductType = render.determineProductType(templateId);
		if(OCCUPATION_TYPE_ALL.equals(checkProductType)){
			return MConstant.FLAG_Y;
		}		
		return MConstant.FLAG_N;
	}

}
