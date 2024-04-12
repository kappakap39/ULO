package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public class FixedGuaranteePopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(FixedGuaranteePopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> guaranteeList = incomeM.getAllIncomes();

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
		String subformId = "FIXED_GUARANTEE_POPUP";
		formError.mandatory(subformId,"FIXED_GUARANTEE_AMT","AMOUNT", incomeM,request);		
		String roleId = FormControl.getFormRoleId(request);
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource(); 
		
		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
			if(ApplicationUtil.eApp(source)) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			}else{
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
					formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
					
					if(Util.empty(formError.getFormError())) {
						incomeM.setCompareFlag(MConstant.FLAG.YES);
					} else {
						incomeM.setCompareFlag(MConstant.FLAG.TEMP);
					}
				}
			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
