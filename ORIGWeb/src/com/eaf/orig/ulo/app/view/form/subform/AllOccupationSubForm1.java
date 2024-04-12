package com.eaf.orig.ulo.app.view.form.subform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class AllOccupationSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(AllOccupationSubForm1.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String GOVERNMENT_OFFICER = SystemConstant.getConstant("GOVERNMENT_OFFICER");
	private String PROFESSION_TYPE_JUDGE = SystemConstant.getConstant("PROFESSION_TYPE_JUDGE");
	private String FIELD_ID_IMAGE_GOVERNMENT = SystemConstant.getConstant("FIELD_ID_IMAGE_GOVERNMENT");
	private String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	private String subformId = "ALL_OCCUPATION_SUBFORM_1";
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");	
	String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		String OCCUPATION = request.getParameter("OCCUPATION");
		String PROFESSION = request.getParameter("PROFESSION");
		String POSITION = request.getParameter("POSITION");
		String POSITION_CODE = request.getParameter("POSITION_CODE");
		String POSITION_LEVEL = request.getParameter("POSITION_LEVEL");
		String CONTACT_TIME = request.getParameter("CONTACT_TIME");
		String TOT_WORK_YEAR = request.getParameter("TOT_WORK_YEAR");
		String TOT_WORK_MONTH = request.getParameter("TOT_WORK_MONTH");
		String BUSINESS_TYPE = request.getParameter("BUSINESS_TYPE");
		String PREV_WORK_YEAR = request.getParameter("PREV_WORK_YEAR");
		String PREV_WORK_MONTH = request.getParameter("PREV_WORK_MONTH");
		String SPECIAL_MERCHANT_TYPE = request.getParameter("SPECIAL_MERCHANT_TYPE");
		
		
		logger.debug("OCCUPATION >>"+OCCUPATION);
		logger.debug("PROFESSION >>"+PROFESSION);
		logger.debug("POSITION >>"+POSITION);
		logger.debug("POSITION_CODE >>"+POSITION_CODE);
		logger.debug("POSITION_LEVEL >>"+POSITION_LEVEL);
		logger.debug("TOT_WORK_YEAR >>"+TOT_WORK_YEAR);
		logger.debug("TOT_WORK_MONTH >>"+TOT_WORK_MONTH);
		logger.debug("CONTACT_TIME >>"+CONTACT_TIME);
		logger.debug("BUSINESS_TYPE >>"+BUSINESS_TYPE);
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
		
//		personalInfo.setOccupation(OCCUPATION);
//		personalInfo.setProfession(PROFESSION);
//		personalInfo.setPositionDesc(POSITION);
//		personalInfo.setPositionCode(POSITION_CODE);
		personalInfo.setPositionLevel(POSITION_LEVEL);
		personalInfo.setTotWorkYear(FormatUtil.toBigDecimal(TOT_WORK_YEAR, true));
		personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_MONTH, true));
		personalInfo.setContactTime(CONTACT_TIME);
//		personalInfo.setBusinessType(BUSINESS_TYPE);
		personalInfo.setPrevWorkYear(FormatUtil.toBigDecimal(PREV_WORK_YEAR, true));
		personalInfo.setPrevWorkMonth(FormatUtil.toBigDecimal(PREV_WORK_MONTH, true));
		
		//KPL Additional
		personalInfo.setSpecialMerchantType(SPECIAL_MERCHANT_TYPE); 
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		logger.debug("subformId >> "+subformId);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId,"POSITION_LEVEL",personalInfo.getPositionLevel(),request);
		formError.mandatory(subformId,"BUSINESS_TYPE",personalInfo.getBusinessType(),request);
		formError.mandatory(subformId,"PREV_WORK",personalInfo,request);
		
		if(!KPLUtil.isKPL(applicationGroup))
		{formError.mandatory(subformId,"CONTACT_TIME",personalInfo.getContactTime(),request);}
		
		if(!KPLUtil.isKPL_TOPUP(applicationGroup))
		{
			formError.mandatory(subformId,"OCCUPATION",personalInfo.getOccupation(),request);
			formError.mandatory(subformId,"PROFESSION",personalInfo.getProfession(),request);			
			formError.mandatory(subformId,"TOT_WORK",personalInfo,request);
			formError.mandatory(subformId,"POSITION",personalInfo.getPositionDesc(),request);
			formError.mandatory(subformId,"POSITION_CODE",personalInfo.getPositionCode(),request);
		}
		
		BigDecimal prevWorkYear = personalInfo.getPrevWorkYear();
		BigDecimal prevWorkMonth = personalInfo.getPrevWorkMonth();
		logger.debug("prevWorkYear : " + prevWorkYear);
		logger.debug("prevWorkMonth : " + prevWorkMonth);
		
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
		auditFormUtil.auditForm(subformId, "OCCUPATION", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "TOT_WORK", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "BUSINESS_TYPE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "PREV_WORK", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "POSITION_CODE", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "POSITION_LEVEL", personalInfo,lastpersonalInfo, request);
		auditFormUtil.auditForm(subformId, "SPECIAL_MERCHANT_TYPE", personalInfo,lastpersonalInfo, request);
		return auditFormUtil.getAuditForm();
	}	
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) {
		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug("AllOccupationSubForm1>>templateId >> "+templateId);		
		RenderSubform render = new RenderSubform();

		String OCCUPATION_TYPE_ALL = SystemConstant.getConstant("OCCUPATION_TYPE_ALL");	
		
		String checkProductType = render.determineProductType(templateId);
		logger.debug("OCCUPATION_TYPE_ALL>>>"+OCCUPATION_TYPE_ALL);	
		logger.debug("checkProductType >> "+checkProductType);	
		if(OCCUPATION_TYPE_ALL.equals(checkProductType)){
			return MConstant.FLAG_Y;
		}		
		return MConstant.FLAG_N;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);

//		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo);
		String PREFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.PERSONAL,personalInfo.getPersonalId());
		if(OCCUPATION_OTHER.equals(personalInfo.getOccupation())){
			personalInfo.setOccpationOther(formValue.getValue("OCCUPATION", "OCCUPATION_"+PREFIX_ELEMENT_ID, personalInfo.getOccpationOther()));
			personalInfo.setOccupation(OCCUPATION_OTHER);
		}else{
			personalInfo.setOccupation(formValue.getValue("OCCUPATION", "OCCUPATION_"+PREFIX_ELEMENT_ID, personalInfo.getOccupation()));
		}
		if(PROFESSION_OTHER.equals(personalInfo.getProfession())){
			personalInfo.setProfessionOther(formValue.getValue("PROFESSION", "PROFESSION_"+PREFIX_ELEMENT_ID, personalInfo.getProfessionOther()));
			personalInfo.setProfession(PROFESSION_OTHER);
		}else{
			personalInfo.setProfession(formValue.getValue("PROFESSION", "PROFESSION_"+PREFIX_ELEMENT_ID, personalInfo.getProfession()));
		}
		personalInfo.setPositionDesc(formValue.getValue("POSITION", "POSITION_"+PREFIX_ELEMENT_ID, personalInfo.getPositionDesc()));
		personalInfo.setPositionCode(formValue.getValue("POSITION_CODE", "POSITION_CODE_"+PREFIX_ELEMENT_ID, personalInfo.getPositionCode()));
		personalInfo.setPositionLevel(formValue.getValue("POSITION_LEVEL", "POSITION_LEVEL_"+PREFIX_ELEMENT_ID, personalInfo.getPositionLevel()));
		personalInfo.setTotWorkYear(formValue.getValue("TOT_WORK", "TOT_WORK_YEAR_"+PREFIX_ELEMENT_ID, personalInfo.getTotWorkYear()));
		personalInfo.setTotWorkMonth(formValue.getValue("TOT_WORK", "TOT_WORK_MONTH_"+PREFIX_ELEMENT_ID, personalInfo.getTotWorkMonth()));
		
		if(BUSINESS_TYPE_OTHER.equals(personalInfo.getBusinessType())){
			personalInfo.setBusinessTypeOther(formValue.getValue("BUSINESS_TYPE", "BUSINESS_TYPE_"+PREFIX_ELEMENT_ID, personalInfo.getBusinessTypeOther()));
			personalInfo.setBusinessType(BUSINESS_TYPE_OTHER);
		}else{
			personalInfo.setBusinessType(formValue.getValue("BUSINESS_TYPE", "BUSINESS_TYPE_"+PREFIX_ELEMENT_ID, personalInfo.getBusinessType()));
		}
		personalInfo.setPrevWorkYear(formValue.getValue("PREV_WORK", "PREV_WORK_YEAR_"+PREFIX_ELEMENT_ID, personalInfo.getPrevWorkYear()));
		personalInfo.setPrevWorkMonth(formValue.getValue("PREV_WORK", "PREV_WORK_MONTH_"+PREFIX_ELEMENT_ID, personalInfo.getPrevWorkMonth()));
		
		//KPL Additional
		if(!KPLUtil.isKPL(applicationGroup))
		{personalInfo.setContactTime(formValue.getValue("CONTACT_TIME","CONTACT_TIME_"+PREFIX_ELEMENT_ID,personalInfo.getContactTime()));}
		
		if(KPLUtil.isKPL(applicationGroup))
		{personalInfo.setSpecialMerchantType(formValue.getValue("SPECIAL_MERCHANT_TYPE","SPECIAL_MERCHANT_TYPE_"+PREFIX_ELEMENT_ID,personalInfo.getSpecialMerchantType()));}
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] fieldNameList={"OCCUPATION","PROFESSION","POSITION_CODE","POSITION_DESC",
				 "SPECIAL_MERCHANT_TYPE","TOT_WORK_MONTH","TOT_WORK_YEAR","PREV_WORK_MONTH","PREV_WORK_YEAR","BUSINESS_TYPE"};
		
		try {
			try {
				ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
				 for(String elementId:fieldNameList){
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
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
