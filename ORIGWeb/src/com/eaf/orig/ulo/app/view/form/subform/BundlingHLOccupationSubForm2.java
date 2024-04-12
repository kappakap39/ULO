package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BundlingHLOccupationSubForm2  extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundlingHLOccupationSubForm2.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		String K_GROUP = request.getParameter("K_GROUP");
		String OCCUPATION = request.getParameter("OCCUPATION_CODE");
		String POSITION_DESC = request.getParameter("POSITION_DESC");
		String POSITION_LEVEL = request.getParameter("POSITION_LEVEL");
		String TOT_WORK_YEAR = request.getParameter("TOT_WORK_YEAR");
		String TOT_WORK_MONTH = request.getParameter("TOT_WORK_MONTH");

		logger.debug("K_GROUP >>"+K_GROUP);
		logger.debug("OCCUPATION >>"+OCCUPATION);
		logger.debug("POSITION_DESC >>"+POSITION_DESC);
		logger.debug("POSITION_LEVEL >>"+POSITION_LEVEL);
		logger.debug("TOT_WORK_YEAR >>"+TOT_WORK_YEAR);
		logger.debug("TOT_WORK_MONTH >>"+TOT_WORK_MONTH);
		
		personalInfo.setkGroupFlag(K_GROUP);
		personalInfo.setOccupation(OCCUPATION);
		personalInfo.setPositionDesc(POSITION_DESC);
		personalInfo.setPositionLevel(POSITION_LEVEL);
		personalInfo.setTotWorkYear(FormatUtil.toBigDecimal(TOT_WORK_YEAR));
		personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_MONTH));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String subformId = "BUNDLING_HL_OCCUPATION_SUBFORM_2";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		
		formError.mandatory(subformId,"OCCUPATION_SUBFORM",personalInfo.getPositionDesc(),request);
		formError.mandatory(subformId,"POSITION",personalInfo.getOccupation(),request);
		formError.mandatory(subformId,"POSITION_LEVEL",personalInfo.getPositionLevel(),request);
		formError.mandatory(subformId,"TOT_WORK",personalInfo,request);
		formError.mandatory(subformId,"BUSINESS_TYPE",personalInfo.getBusinessType(),request);
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
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
