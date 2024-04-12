package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BundlingSMEOccupationSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BundlingSMEOccupationSubForm1.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
				
		String POSITION_CODE = request.getParameter("POSITION_CODE");
		String POSITION_DESC = request.getParameter("POSITION_DESC");
		String TOT_WORK_YEAR = request.getParameter("TOT_WORK_YEAR");
		String TOT_WORK_MONTH = request.getParameter("TOT_WORK_MONTH");

		logger.debug("POSITION_CODE >>"+POSITION_CODE);
		logger.debug("POSITION_DESC >>"+POSITION_DESC);
		logger.debug("TOT_WORK_YEAR >>"+TOT_WORK_YEAR);
		logger.debug("TOT_WORK_MONTH >>"+TOT_WORK_MONTH);
		
		personalInfo.setPositionCode(POSITION_CODE);
		personalInfo.setPositionDesc(POSITION_DESC);
		personalInfo.setTotWorkYear(FormatUtil.toBigDecimal(TOT_WORK_YEAR));
		personalInfo.setTotWorkMonth(FormatUtil.toBigDecimal(TOT_WORK_MONTH));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
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

		String OCCUPATION_TYPE_SME = SystemConstant.getConstant("OCCUPATION_TYPE_SME");	
		logger.debug("templateId >> "+templateId);		
		
		String checkProductType = render.determineProductType(templateId);
		if(OCCUPATION_TYPE_SME.equals(checkProductType)){
			return MConstant.FLAG_Y;
		}		
		return MConstant.FLAG_N;
	}

}
