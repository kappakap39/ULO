package com.eaf.orig.ulo.app.view.form.popup.income;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;

public class SalaryCertPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(SalaryCertPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> salaryCertList = incomeM.getAllIncomes();
		if(!Util.empty(salaryCertList)) {
			for(IncomeCategoryDataM incomeObj: salaryCertList) {
				SalaryCertDataM salaryCertM = (SalaryCertDataM) incomeObj;
				String companyName = request.getParameter("COMPANY_NAME_"+salaryCertM.getSeq());
				String income = request.getParameter("REVENUE_"+salaryCertM.getSeq());
				String otherIncome = request.getParameter("OTHER_INCOME_"+salaryCertM.getSeq());
				String totalIncome = request.getParameter("TOTAL_INCOME_"+salaryCertM.getSeq());
				
				logger.debug("companyName >>"+companyName);	
				logger.debug("income >>"+income);
				
				salaryCertM.setCompanyName(companyName);
				salaryCertM.setOtherIncome(FormatUtil.toBigDecimal(otherIncome, true));
				salaryCertM.setTotalIncome(FormatUtil.toBigDecimal(totalIncome, true));
				salaryCertM.setIncome(FormatUtil.toBigDecimal(income, true));
			}
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
		FormErrorUtil formError = new FormErrorUtil();
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		String subformId = "SALARY_CERT_POPUP";
		ArrayList<IncomeCategoryDataM> salaryCertList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(salaryCertList)) {
			for(IncomeCategoryDataM incomeObj: salaryCertList) {
				SalaryCertDataM salaryCertM = (SalaryCertDataM) incomeObj;
				formError.mandatory(subformId,"COMPANY_NAME","COMPANY_NAME_"+salaryCertM.getSeq(),"", salaryCertM.getCompanyName(),request);	
				boolean checkTotal = false;
				boolean checkIncome =false;
				
				BigDecimal income = salaryCertM.getIncome();
				BigDecimal otherIncome = salaryCertM.getOtherIncome();
				BigDecimal totalIncome = salaryCertM.getTotalIncome();
				
				if(!Util.empty(income) && income.compareTo(new BigDecimal(0)) > 0 || !Util.empty(otherIncome) && otherIncome.compareTo(new BigDecimal(0)) > 0){
					checkIncome = true;
				}
				if(!Util.empty(totalIncome) && totalIncome.compareTo(new BigDecimal(0)) > 0){
					checkTotal = true;
				}
				if(checkIncome && checkTotal){
					formError.error("REVENUE_"+salaryCertM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_SAVE_SCENARIO_AMOUNT"));
					formError.error("OTHER_INCOME_"+salaryCertM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_SAVE_SCENARIO_AMOUNT"));
					formError.error("TOTAL_INCOME_"+salaryCertM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_SAVE_SCENARIO_AMOUNT"));
				}else{
					if(!checkIncome && !checkTotal){
						formError.error("REVENUE_"+salaryCertM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_SAVE_AMOUNT"));
						formError.error("OTHER_INCOME_"+salaryCertM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_SAVE_AMOUNT"));
						formError.error("TOTAL_INCOME_"+salaryCertM.getSeq(),MessageErrorUtil.getText(request,"ERROR_ID_PAYSLIP_SAVE_AMOUNT"));
					}
				}
			}
		} else {
			formError.error("SALARY_CERT",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}

		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
/*			if(ApplicationUtil.eApp(source)) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			}else{*/
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
					formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
					
					if(Util.empty(formError.getFormError())) {
						incomeM.setCompareFlag(MConstant.FLAG.YES);
					} else {
						incomeM.setCompareFlag(MConstant.FLAG.TEMP);
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
