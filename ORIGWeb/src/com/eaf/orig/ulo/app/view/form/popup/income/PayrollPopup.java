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
import com.eaf.orig.ulo.model.app.PayrollDataM;

public class PayrollPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PayrollPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> payrollList = incomeM.getAllIncomes();
		if(payrollList == null) {
			payrollList = new ArrayList<IncomeCategoryDataM>();
		}
		PayrollDataM payrollM = null;
		if(payrollList.size() > 0){
			payrollM = (PayrollDataM)payrollList.get(0);
		} else {
			payrollM = new PayrollDataM();
			payrollM.setSeq(payrollList.size() + 1);
			payrollList.add(payrollM);
		}
		String NO_OF_EMPLOYEES = request.getParameter("NO_OF_EMPLOYEES");
		String INCOME_MIN = request.getParameter("INCOME_MIN");
			
		logger.debug("NO_OF_EMPLOYEES >>"+NO_OF_EMPLOYEES);	
		logger.debug("INCOME_MIN >>"+INCOME_MIN);
			
		payrollM.setNoOfEmployee(FormatUtil.getInt(NO_OF_EMPLOYEES));
		payrollM.setIncome(FormatUtil.toBigDecimal(INCOME_MIN, true));

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
		String subformId = "PAYROLL_POPUP";
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> payrollList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(payrollList)) {
			for(IncomeCategoryDataM incomeObj: payrollList) {
				PayrollDataM payrollM = (PayrollDataM) incomeObj;
				formError.mandatory(subformId,"NO_OF_EMPLOYEES","NO_OF_EMPLOYEES", payrollM.getNoOfEmployee(),request);	
				formError.mandatory(subformId,"INCOME_MIN","INCOME_MIN", payrollM.getIncome(),request);
			}
		}

		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
			/*if(ApplicationUtil.eApp(source)) {
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
			/*}*/
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
