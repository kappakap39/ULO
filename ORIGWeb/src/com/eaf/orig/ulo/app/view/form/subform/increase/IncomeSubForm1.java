package com.eaf.orig.ulo.app.view.form.subform.increase;

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

public class IncomeSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(IncomeSubForm1.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
				
		String SALARY = request.getParameter("SALARY");
		String CIRCULATION_INCOME = request.getParameter("CIRCULATION_INCOME");
		String NET_PROFIT_INCOME = request.getParameter("NET_PROFIT_INCOME");
		String PROCEDURE_TYPE = request.getParameter("PROCEDURE_TYPE");
		String FREELANCE_INCOME = request.getParameter("FREELANCE_INCOME");
		String FREELANCE_TYPE = request.getParameter("FREELANCE_TYPE");	
		String SAVING_INCOME = request.getParameter("SAVING_INCOME");		
		String MONTHLY_INCOME = request.getParameter("MONTHLY_INCOME");		
		String SRC_OTH_INC_BONUS = request.getParameter("SRC_OTH_INC_BONUS");
		String SRC_OTH_INC_COMMISSION = request.getParameter("SRC_OTH_INC_COMMISSION");
		String SRC_OTH_INC_OTHER = request.getParameter("SRC_OTH_INC_OTHER");
		String OTHER_INCOME_DESC = request.getParameter("OTHER_INCOME_DESC");

		logger.debug("SALARY >>"+SALARY);
		logger.debug("CIRCULATION_INCOME >>"+CIRCULATION_INCOME);
		logger.debug("NET_PROFIT_INCOME >>"+NET_PROFIT_INCOME);
		logger.debug("PROCEDURE_TYPE >>"+PROCEDURE_TYPE);
		logger.debug("FREELANCE_INCOME >>"+FREELANCE_INCOME);
		logger.debug("SAVING_INCOME >>"+SAVING_INCOME);
		logger.debug("MONTHLY_INCOME >>"+MONTHLY_INCOME);
 
		
		personalInfo.setSalary(FormatUtil.toBigDecimal(SALARY));
		personalInfo.setCirculationIncome(FormatUtil.toBigDecimal(CIRCULATION_INCOME,true));
		personalInfo.setNetProfitIncome(FormatUtil.toBigDecimal(NET_PROFIT_INCOME,true));
		personalInfo.setFreelanceIncome(FormatUtil.toBigDecimal(FREELANCE_INCOME,true));
		personalInfo.setSavingIncome(FormatUtil.toBigDecimal(SAVING_INCOME,true));
		personalInfo.setMonthlyIncome(FormatUtil.toBigDecimal(MONTHLY_INCOME,true));
		personalInfo.setSrcOthIncBonus(SRC_OTH_INC_BONUS);
		personalInfo.setSrcOthIncCommission(SRC_OTH_INC_COMMISSION);
		personalInfo.setSrcOthIncOther(SRC_OTH_INC_OTHER);
		personalInfo.setSorceOfIncomeOther(OTHER_INCOME_DESC);
		personalInfo.setSorceOfIncome(PROCEDURE_TYPE);
		personalInfo.setFreelanceType(FREELANCE_TYPE);
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "INCREASE_INCOME_SUBFORM_1";
		logger.debug("subformId >> "+subformId);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		
		FormErrorUtil formError = new FormErrorUtil();	
		
		formError.mandatory(subformId,"SALARY",personalInfo.getSalary(),request);
		formError.mandatory(subformId,"CIRCULATION_INCOME",personalInfo.getCirculationIncome(),request);
		formError.mandatory(subformId,"NET_PROFIT_INCOME",personalInfo.getNetProfitIncome(),request);
		formError.mandatory(subformId,"SAVING_INCOME",personalInfo.getSavingIncome(),request);
		formError.mandatory(subformId,"FREELANCE_INCOME",personalInfo.getFreelanceIncome(),request);
		formError.mandatory(subformId,"MONTHLY_INCOME",personalInfo.getMonthlyIncome(),request);
		formError.mandatory(subformId,"RECEIVE_INCOME_METHOD",personalInfo.getReceiveIncomeMethod(),request);
		formError.mandatory(subformId,"RECEIVE_INCOME_BANK",personalInfo.getReceiveIncomeBank(),request);
		formError.mandatory(subformId,"PROCEDURE_TYPE",personalInfo.getSorceOfIncome(),request);
//		if(!Util.empty(personalInfo.getSrcOthIncOther())){
//			formError.mandatoryElement("OTHER_INCOME_DESC", "OTHER_INCOME_DESC", personalInfo.getSorceOfIncomeOther(), request);
//			formError.mandatory(subformId,"OTHER_INCOME_DESC",personalInfo.getSorceOfIncomeOther(),request);
//		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
