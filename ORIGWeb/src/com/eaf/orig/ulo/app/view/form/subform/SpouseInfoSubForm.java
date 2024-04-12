package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SpouseInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(IncomeSubForm3.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		String M_TITLE_CODE = request.getParameter("M_TITLE_CODE");
		String M_TITLE_DESC = request.getParameter("M_TITLE_DESC");
		String M_TH_FIRST_NAME = request.getParameter("M_TH_FIRST_NAME");
		String M_TH_LAST_NAME = request.getParameter("M_TH_LAST_NAME");
		String M_TH_OLD_LAST_NAME = request.getParameter("M_TH_OLD_LAST_NAME");
		String M_COMPANY = request.getParameter("M_COMPANY");
		String M_HOME_TEL = request.getParameter("M_HOME_TEL");
		String M_INCOME = request.getParameter("M_INCOME");
		String M_POSITION = request.getParameter("M_POSITION");
		
		personalInfo.setmTitleCode(M_TITLE_CODE);
		personalInfo.setmTitleDesc(M_TITLE_DESC);
		personalInfo.setmThFirstName(M_TH_FIRST_NAME);
		personalInfo.setmThLastName(M_TH_LAST_NAME);
		personalInfo.setmThOldLastName(M_TH_OLD_LAST_NAME);
		personalInfo.setmCompany(M_COMPANY);
		personalInfo.setmHomeTel(FormatUtil.removeDash(M_HOME_TEL));
		personalInfo.setmIncome(FormatUtil.toBigDecimal(M_INCOME));
		personalInfo.setmPosition(M_POSITION);
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String subformId = "SPOUSE_INFO_SUBFORM";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		
		formError.mandatory(subformId,"M_TH_NAME",personalInfo,request);
		formError.mandatory(subformId,"M_TH_OLD_LAST_NAME",personalInfo.getmThOldLastName(),request);
		formError.mandatory(subformId,"M_COMPANY",personalInfo.getmCompany(),request);
		formError.mandatory(subformId,"MTL_SALES_PHONE_NO",personalInfo.getmHomeTel(),request);
		formError.mandatory(subformId,"M_INCOME",personalInfo.getmIncome(),request);
		formError.mandatory(subformId,"M_POSITION",personalInfo.getmPosition(),request);
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm)
	{
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		//This subform will only render if application template is not KPL
		if(KPLUtil.isKPL(applicationGroup))
		{
			return MConstant.FLAG_N;
		}		
		return MConstant.FLAG_Y;
	}

}
