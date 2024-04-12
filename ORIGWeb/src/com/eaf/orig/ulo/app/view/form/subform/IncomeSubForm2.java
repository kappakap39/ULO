package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IncomeSubForm2 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(IncomeSubForm2.class);

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
				
		String SALARY = request.getParameter("SALARY");
		String CIRCULATION_INCOME = request.getParameter("CIRCULATION_INCOME");
		String NET_PROFIT_INCOME = request.getParameter("NET_PROFIT_INCOME");
		String FREELANCE_INCOME = request.getParameter("FREELANCE_INCOME");
		String INCOME_NET = request.getParameter("INCOME_NET");
		String MONTHLY_INCOME = request.getParameter("MONTHLY_INCOME");
		String SRC_OTH_INC_BONUS = request.getParameter("SRC_OTH_INC_BONUS");
		String SRC_OTH_INC_COMMISSION = request.getParameter("SRC_OTH_INC_COMMISSION");
		String SRC_OTH_INC_OTHER = request.getParameter("SRC_OTH_INC_OTHER");
		String OTHER_INCOME_DESC = request.getParameter("OTHER_INCOME_DESC");
		String ASSET = request.getParameter("ASSET");

		logger.debug("SALARY >>"+SALARY);
		logger.debug("CIRCULATION_INCOME >>"+CIRCULATION_INCOME);
		logger.debug("NET_PROFIT_INCOME >>"+NET_PROFIT_INCOME);
		logger.debug("FREELANCE_INCOME >>"+FREELANCE_INCOME);
		logger.debug("INCOME_NET >>"+INCOME_NET);
		logger.debug("MONTHLY_INCOME >>"+MONTHLY_INCOME);
		logger.debug("OTHER_INCOME_DESC >>"+OTHER_INCOME_DESC);
		logger.debug("ASSET >>"+ASSET);
		
		
		personalInfo.setSalary(FormatUtil.toBigDecimal(SALARY,true));
		personalInfo.setCirculationIncome(FormatUtil.toBigDecimal(CIRCULATION_INCOME,true));
		personalInfo.setNetProfitIncome(FormatUtil.toBigDecimal(NET_PROFIT_INCOME,true));
		personalInfo.setFreelanceIncome(FormatUtil.toBigDecimal(FREELANCE_INCOME,true));
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
		String subformId = "INCOME_SUBFORM_2";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		 
		formError.mandatory(subformId,"SALARY",personalInfo.getSalary(),request);
		formError.mandatory(subformId,"CIRCULATION_INCOME",personalInfo.getCirculationIncome(),request);
		formError.mandatory(subformId,"NET_PROFIT_INCOME",personalInfo.getNetProfitIncome(),request);
		formError.mandatory(subformId,"FREELANCE_INCOME",personalInfo.getFreelanceIncome(),request);	
		formError.mandatory(subformId,"MONTHLY_INCOME",personalInfo.getMonthlyIncome(),request);
		formError.mandatory(subformId,"ASSET",personalInfo.getAssetsAmount(),request);
		formError.mandatory(subformId,"RECEIVE_INCOME_METHOD",personalInfo.getReceiveIncomeMethod(),request);
		formError.mandatory(subformId,"MONTHLY_EXPENSE",personalInfo.getMonthlyExpense(),request);
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
