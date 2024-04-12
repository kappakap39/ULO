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
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;

public class KVIPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(KVIPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		if(incomeList == null) {
			incomeList = new ArrayList<IncomeCategoryDataM>();
			incomeM.setAllIncomes(incomeList);
		}
		KVIIncomeDataM kviM = null;
		if(incomeList.size() > 0){
			kviM = (KVIIncomeDataM)incomeList.get(0);
		} else {
			kviM = new KVIIncomeDataM();
			kviM.setSeq(incomeList.size() + 1);
			incomeList.add(kviM);
		}
		
		String VERIFIED_INCOME = request.getParameter("VERIFIED_INCOME");
		String PERCENT_CHEQUE = request.getParameter("PERCENT_CHEQUE");
		
		logger.debug("PERCENT_CHEQUE >>"+PERCENT_CHEQUE);	
		logger.debug("VERIFIED_INCOME >>"+VERIFIED_INCOME);	
		
		kviM.setPercentChequeReturn(FormatUtil.toBigDecimal(PERCENT_CHEQUE));	
		kviM.setVerifiedIncome(FormatUtil.toBigDecimal(VERIFIED_INCOME));
		
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
		String roleId = FormControl.getFormRoleId(request);
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		//Check source e-app for ignore compare field
		/*if(ApplicationUtil.eApp(source)) {
			incomeM.setCompareFlag(MConstant.FLAG.YES);
		}else{*/
			if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
				formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
				
				if(Util.empty(formError.getFormError())) {
					incomeM.setCompareFlag(MConstant.FLAG.YES);
				} else {
					incomeM.setCompareFlag(MConstant.FLAG.TEMP);
				}
			}
		/*}*/
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
