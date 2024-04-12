package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;

public class PayslipPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PayslipPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		if(incomeList == null) {
			incomeList = new ArrayList<IncomeCategoryDataM>();
			incomeM.setAllIncomes(incomeList);
		}
		PayslipDataM payslipM = null;
		if(incomeList.size() > 0){
			payslipM = (PayslipDataM)incomeList.get(0);
		} else {
			payslipM = new PayslipDataM();
			payslipM.setSeq(incomeList.size() + 1);
			incomeList.add(payslipM);
		}
		
		String noMonth = request.getParameter("ACCUMULATED_MONTH");
		String sumIncome = request.getParameter("ACCUMULATED_INCOME");
		String sumOthIncome = request.getParameter("OTH_ACCUMULATED_INCOME");
		String sumSso = request.getParameter("ACCUMULATED_SSO");
		String bonus = request.getParameter("ACCUMULATED_BONUS");
		String bonusMonth = request.getParameter("MONTH_BONUS");
		String bonusYear = request.getParameter("YEAR_BONUS");
		String SALARY_DATE = request.getParameter("SALARY_DATE");
		String spacialPension = request.getParameter("KBANK_PENSION");
		
		logger.debug("noMonth >>"+noMonth);	
		logger.debug("sumIncome >>"+sumIncome);	
		logger.debug("sumOthIncome >>"+sumOthIncome);	
		logger.debug("sumSso >>"+sumSso);	
		logger.debug("bonusMonth >>"+bonusMonth);	
		logger.debug("bonusYear >>"+bonusYear);	
		logger.debug("bonus >>"+bonus);	
		logger.debug("SALARY_DATE >>"+SALARY_DATE);	
		logger.debug("spacialPension >>"+spacialPension);	
		
		payslipM.setNoMonth(FormatUtil.getInt(noMonth));
		payslipM.setSumIncome(FormatUtil.toBigDecimal(sumIncome, true));
		payslipM.setSumOthIncome(FormatUtil.toBigDecimal(sumOthIncome, true));
		payslipM.setSumSso(FormatUtil.toBigDecimal(sumSso, true));
		payslipM.setBonus(FormatUtil.toBigDecimal(bonus, true));
		payslipM.setBonusMonth(bonusMonth);
		payslipM.setBonusYear(bonusYear);
		payslipM.setSalaryDate(FormatUtil.toDate(SALARY_DATE, FormatUtil.TH));		
		payslipM.setSpacialPension(FormatUtil.toBigDecimal(spacialPension, true));
		
		if(payslipM.getNoMonth() != 0) {
			payslipM.setPayslipMonthlys(null);
		}
		
		//to check if final value from screen match with previous or not
		if(MConstant.FLAG.TEMP.equals(incomeM.getCompareFlag())) {
			FormErrorUtil formError = new FormErrorUtil();
			formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
			if(Util.empty(formError.getFormError())) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			} else {
				incomeM.setCompareFlag(MConstant.FLAG.WRONG);
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "PAYSLIP_POPUP";
		FormErrorUtil formError = new FormErrorUtil();
		IncomeInfoDataM income = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> paySlips = income.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		PayslipDataM paySlip = null;
		if(paySlips.size() > 0){
			paySlip = (PayslipDataM)paySlips.get(0);
		}
		if(!Util.empty(paySlip)) {
			if(paySlip.getNoMonth() != 0) {
				formError.mandatory(subformId,"ACCUMULATED_INCOME",paySlip.getSumIncome(),request);	
				formError.mandatory(subformId,"MONTH_YR_BONUS",paySlip,request);
				if(!Util.empty(paySlip.getSalaryDate())){
					formError.mandatoryDate(subformId,"SALARY_DATE",paySlip.getSalaryDate(),request);
				}
			}
		}
		if(Util.empty(formError.getFormError()) && paySlip.getNoMonth() != 0) {
			//Check source e-app for ignore compare field
/*			if(ApplicationUtil.eApp(source)) {
				income.setCompareFlag(MConstant.FLAG.YES);
			}else{*/
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isIncomeComplete(income, roleId)) {
					formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, income));
					
					if(Util.empty(formError.getFormError())) {
						income.setCompareFlag(MConstant.FLAG.YES);
					} else {
						income.setCompareFlag(MConstant.FLAG.TEMP);
					}
				}	
//			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
