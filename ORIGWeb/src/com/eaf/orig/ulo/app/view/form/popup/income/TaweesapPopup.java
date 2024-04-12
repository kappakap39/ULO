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
import com.eaf.orig.ulo.model.app.TaweesapDataM;

public class TaweesapPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(TaweesapPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		if(incomeList == null) {
			incomeList = new ArrayList<IncomeCategoryDataM>();
			incomeM.setAllIncomes(incomeList);
		}
		TaweesapDataM taweesapM = null;
		if(incomeList.size() > 0){
			taweesapM = (TaweesapDataM)incomeList.get(0);
		} else {
			taweesapM = new TaweesapDataM();
			taweesapM.setSeq(incomeList.size() + 1);
			incomeList.add(taweesapM);
		}
		
		String aum = request.getParameter("AUM");
		logger.debug("aum >>"+aum);
		taweesapM.setAum(FormatUtil.toBigDecimal(aum, true));
		
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
		String subformId = "TAWEESAP_POPUP";
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> taweesapList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(taweesapList)) {
			for(IncomeCategoryDataM incomeObj: taweesapList) {
				TaweesapDataM taweesapM = (TaweesapDataM) incomeObj;
				formError.mandatory(subformId,"AUM","AUM", taweesapM.getAum(),request);
			}
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
			/*}*/
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
