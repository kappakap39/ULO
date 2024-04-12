package com.eaf.orig.ulo.app.view.form.subform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IncomeSubForm3 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(IncomeSubForm3.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	private String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		
		String SALARY = request.getParameter("SALARY");
		String MONTHLY_INCOME = request.getParameter("MONTHLY_INCOME");
		String SRC_OTH_INC_BONUS = request.getParameter("SRC_OTH_INC_BONUS");
		String SRC_OTH_INC_COMMISSION = request.getParameter("SRC_OTH_INC_COMMISSION");
		String SRC_OTH_INC_OTHER = request.getParameter("SRC_OTH_INC_OTHER");
		String OTHER_INCOME_DESC = request.getParameter("OTHER_INCOME_DESC");
		String ASSET = request.getParameter("ASSET");

		personalInfo.setSalary(FormatUtil.toBigDecimal(SALARY,true));
		personalInfo.setMonthlyIncome(FormatUtil.toBigDecimal(MONTHLY_INCOME,true));
		personalInfo.setSrcOthIncBonus(SRC_OTH_INC_BONUS);
		personalInfo.setSrcOthIncCommission(SRC_OTH_INC_COMMISSION);
		personalInfo.setSrcOthIncOther(SRC_OTH_INC_OTHER);
		personalInfo.setSorceOfIncomeOther(OTHER_INCOME_DESC);
		personalInfo.setAssetsAmount(FormatUtil.toBigDecimal(ASSET,false));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String subformId = "INCOME_SUBFORM_3";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);	
		
		String SALARY = request.getParameter("SALARY");
		String MONTHLY_INCOME = request.getParameter("MONTHLY_INCOME");
		String OTHER_INCOME_DESC = request.getParameter("OTHER_INCOME_DESC");
		String ASSET = request.getParameter("ASSET");
		
		String role =FormControl.getFormRoleId(request);
		if(!KPLUtil.isKPL(applicationGroup))
		{
			if((Util.empty(SALARY) || BigDecimal.ZERO == FormatUtil.toBigDecimal(SALARY,true))
				&& (Util.empty(MONTHLY_INCOME) || BigDecimal.ZERO == FormatUtil.toBigDecimal(MONTHLY_INCOME,true)))
			{
				formError.mandatory(subformId,"SALARY",SALARY,request);
				formError.mandatory(subformId,"MONTHLY_INCOME",MONTHLY_INCOME,request);
			}
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) {
		String[] EXCEPTION_APPLICATION_TEMPLATE_CONDITION = SystemConstant.getArrayConstant("INCOME_EXCEPTION_APPLICATION_TEMPLATE");	
		ArrayList<String> templateConditions = new ArrayList<String>(Arrays.asList(EXCEPTION_APPLICATION_TEMPLATE_CONDITION));
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug(">>>templateId>>>"+templateId);
		if(templateConditions.contains(templateId)){
			return MConstant.FLAG_N;
		}
		return  MConstant.FLAG_Y;
	}
}
