package com.eaf.orig.ulo.app.view.form.popup.income;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;

public class PayslipMonthlyPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PayslipMonthlyPopup.class);
	String PAYSLIP_INCOME_SSO = SystemConstant.getConstant("PAYSLIP_INCOME_SSO");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		Calendar calen = Calendar.getInstance();
		Date appDate = mainForm.getObjectForm().getApplicationDate();
		if(Util.empty(appDate)) {
			appDate = ApplicationDate.getDate();
		}
		
		
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		PayslipDataM paySlipM = null;
		if(incomeList.size() > 0){
			paySlipM = (PayslipDataM)incomeList.get(0);
		} else {
			paySlipM = new PayslipDataM();
			paySlipM.setSeq(incomeList.size() + 1);
			incomeList.add(paySlipM);
		}
		if(!Util.empty(paySlipM) && paySlipM.getNoMonth() == 0) {
			ArrayList<PayslipMonthlyDataM> monthly = paySlipM.getPayslipMonthlys();
			if(!Util.empty(monthly)) {
				for(PayslipMonthlyDataM monthlyData: monthly) {
					String incomeType = request.getParameter("TYPE_"+monthlyData.getSeq());
					String incomeDesc = request.getParameter("INCOME_DESC_"+monthlyData.getSeq());
					String incomeOthDesc = request.getParameter("INCOME_OTH_DESC_"+monthlyData.getSeq());
					String monIncome1 = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_1");
					String monIncome2 = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_2");
					String monIncome3 = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_3");
					String monIncome4 = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_4");
					String monIncome5 = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_5");
					String monIncome6 = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_6");
					String monIncome7 = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_7");
					
					logger.debug("incomeType >>"+incomeType);	
					logger.debug("incomeDesc >>"+incomeDesc);	
					logger.debug("incomeOthDesc >>"+incomeOthDesc);	
					logger.debug("mon1Income >>"+monIncome1);
					logger.debug("mon2Income >>"+monIncome2);
					logger.debug("mon3Income >>"+monIncome3);
					logger.debug("mon4Income >>"+monIncome4);
					logger.debug("mon5Income >>"+monIncome5);
					logger.debug("mon6Income >>"+monIncome6);
					logger.debug("mon7Income >>"+monIncome7);
					
					if(!PAYSLIP_INCOME_SSO.equals(monthlyData.getIncomeType())) {
	 					monthlyData.setIncomeType(incomeType);
	 					monthlyData.setIncomeDesc(incomeDesc);
	 					monthlyData.setIncomeOthDesc(incomeOthDesc);
					} else {
						monthlyData.setIncomeType(PAYSLIP_INCOME_SSO);
					}
					ArrayList<PayslipMonthlyDetailDataM> detailList = monthlyData.getPayslipMonthlyDetails();
					if(Util.empty(detailList)) {
						detailList = new ArrayList<PayslipMonthlyDetailDataM>();
						monthlyData.setPayslipMonthlyDetails(detailList);
						for(int i = 0;i<PayslipMonthlyDetailDataM.MONTH_COUNT; i++) {
							PayslipMonthlyDetailDataM monthDtl = new PayslipMonthlyDetailDataM();
							monthDtl.setSeq(i+1);
							detailList.add(monthDtl);
						}
					}
					for(int i = 0; i < detailList.size(); i++) {
						PayslipMonthlyDetailDataM monthDtl = detailList.get(i);
						if(monthDtl == null) {
							monthDtl = new PayslipMonthlyDetailDataM();
							detailList.set(i, monthDtl);
						}
								
						Date prevMonth = Util.getPreviousMonth(appDate, i);
						calen.setTime(prevMonth);
						String year = Util.getYear(prevMonth);	
						monthDtl.setAmount(FormatUtil.toBigDecimal(request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_"+(i+1)), true));
						monthDtl.setMonth(FormatUtil.toString(calen.get(Calendar.MONTH)+1));
						monthDtl.setYear(year);
					}
				}
			}
		
			String bonus = request.getParameter("BONUS");
			String bonusMonth = request.getParameter("MONTH2_BONUS");
			String bonusYear = request.getParameter("YEAR2_BONUS");
			String SALARY_DATE = request.getParameter("SALARY_DATE2");
			String spacialPension = request.getParameter("KBANK_PENSION2");
			
			logger.debug("bonusMonth >>"+bonusMonth);	
			logger.debug("bonusYear >>"+bonusYear);	
			logger.debug("bonus >>"+bonus);	
			logger.debug("SALARY_DATE >>"+SALARY_DATE);	
			logger.debug("spacialPension >>"+spacialPension);
			
			paySlipM.setBonus(FormatUtil.toBigDecimal(bonus, true));
			paySlipM.setBonusMonth(bonusMonth);
			paySlipM.setBonusYear(bonusYear);
			paySlipM.setSalaryDate(FormatUtil.toDate(SALARY_DATE, FormatUtil.TH));		
			paySlipM.setSpacialPension(FormatUtil.toBigDecimal(spacialPension, true));

			
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
		
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "PAYSLIP_MONTHLY_POPUP";
		FormErrorUtil formError = new FormErrorUtil();
		IncomeInfoDataM income = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> paySlips = income.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		PayslipDataM paySlip = null;
		if(paySlips.size() > 0){
			paySlip = (PayslipDataM)paySlips.get(0);
		}
		if(!Util.empty(paySlip) && paySlip.getNoMonth() == 0) {
			ArrayList<PayslipMonthlyDataM> monthly = paySlip.getPayslipMonthlys();
			int nonSSOMonthlyCount = 0;
			if(!Util.empty(monthly)) {
				for(PayslipMonthlyDataM monthlyData: monthly) {
					if(PAYSLIP_INCOME_SSO.equals(monthlyData.getIncomeType())) {
	 					continue;
	 				}
					nonSSOMonthlyCount++;
					formError.mandatory(subformId,"INCOME_TYPE","TYPE_"+monthlyData.getSeq(),"", monthlyData.getIncomeType(),request);	
					formError.mandatory(subformId,"INCOME_DESC","INCOME_DESC_"+monthlyData.getSeq(),"", monthlyData.getIncomeDesc(),request);
					formError.mandatory(subformId,"INCOME_OTH_DESC","INCOME_OTH_DESC_"+monthlyData.getSeq(), monthlyData,request);
					formError.mandatory(subformId,"PAYSLIP_MONTHLY_AMT","", monthlyData,request);
				}
			}
			// IN Monthly section
			formError.mandatory(subformId,"PAYSLIP_TYPE_FIX","", paySlip,request);
			formError.mandatory(subformId,"MONTH_YR_BONUS2",paySlip,request);
			
			if(nonSSOMonthlyCount == 0) {
				formError.error("PAYSLIP_MONTHLY",MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_MONTHLY"));
			}
			if(!Util.empty(paySlip.getSalaryDate())){
				formError.mandatoryDate(subformId,"SALARY_DATE2",paySlip.getSalaryDate(),request);
			}
			if(Util.empty(formError.getFormError())) {
				//Check source e-app for ignore compare field
			/*	if(ApplicationUtil.eApp(source)) {
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
//				}
			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
