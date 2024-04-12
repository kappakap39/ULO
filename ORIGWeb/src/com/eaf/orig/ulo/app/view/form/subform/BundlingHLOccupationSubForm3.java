package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BundlingHLOccupationSubForm3  extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundlingHLOccupationSubForm3.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);

		String K_GROUP = request.getParameter("K_GROUP");
		String OCCUPATION = request.getParameter("OCCUPATION_CODE");
		String LEVEL = request.getParameter("LEVEL");
		String PROFESSION = request.getParameter("PROFESSION");
		String PROFESSION_OTHER = request.getParameter("PROFESSION_OTHER");
		String POSITION = request.getParameter("POSITION");
		String POSITION_LEVEL = request.getParameter("POSITION_LEVEL");
		String TOT_WORK_YEAR = request.getParameter("TOT_WORK_YEAR");
		String TOT_WORK_MONTH = request.getParameter("TOT_WORK_MONTH");

		logger.debug("K_GROUP >>"+K_GROUP);
		logger.debug("OCCUPATION >>"+OCCUPATION);
		logger.debug("LEVEL >>"+LEVEL);
		logger.debug("PROFESSION >>"+PROFESSION);
		logger.debug("PROFESSION_OTHER >>"+PROFESSION_OTHER);
		logger.debug("POSITION_DESC >>"+POSITION);
		logger.debug("POSITION_LEVEL >>"+POSITION_LEVEL);
		logger.debug("TOT_WORK_YEAR >>"+TOT_WORK_YEAR);
		logger.debug("TOT_WORK_MONTH >>"+TOT_WORK_MONTH);
		
		personalInfo.setkGroupFlag(K_GROUP);
//		personalInfo.setOccupation(OCCUPATION);
		personalInfo.setPositionLevel(LEVEL);
//		personalInfo.setProfession(PROFESSION);
//		personalInfo.setProfessionOther(PROFESSION_OTHER);
		personalInfo.setPositionDesc(POSITION);
		personalInfo.setPositionLevel(POSITION_LEVEL);
		personalInfo.setTotWorkYear(FormatUtil.toBigDecimal(TOT_WORK_YEAR));
		personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_MONTH));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String subformId = "BUNDLING_HL_OCCUPATION_SUBFORM_3";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		
		formError.mandatory(subformId,"OCCUPATION_SUBFORM",personalInfo.getOccupation(),request);
//		formError.mandatory(subformId,"LEVEL",null,request);
		formError.mandatory(subformId,"PROFESSION",personalInfo.getProfession(),request);
		formError.mandatory(subformId,"POSITION",personalInfo.getPositionDesc(),request);
		formError.mandatory(subformId,"POSITION_LEVEL",personalInfo.getPositionLevel(),request);	
		formError.mandatory(subformId,"TOT_WORK",personalInfo,request);
		
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
		
		
		return auditFormUtil.getAuditForm();
	}	
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,
			Object objectForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug("templateId >> "+templateId);		
		RenderSubform render = new RenderSubform();

		String OCCUPATION_TYPE_HL = SystemConstant.getConstant("OCCUPATION_TYPE_HL");	
		logger.debug("templateId >> "+templateId);		
		
		String checkProductType = render.determineProductType(templateId);
		if(OCCUPATION_TYPE_HL.equals(checkProductType)){
			return MConstant.FLAG_Y;
		}		
		return MConstant.FLAG_N;
	}

}
