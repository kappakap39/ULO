package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PayslipFormDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(PayslipFormDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		IncomeInfoDataM incomeM = (IncomeInfoDataM) moduleHandler.getObjectForm();
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		logger.debug("ActionType >> "+actionType);
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
			return HtmlUtil.VIEW;
		}
		PayslipDataM payslipM = null;
		if(incomeList != null && incomeList.size() > 0) {
			payslipM = (PayslipDataM)incomeList.get(0);
		}
		String displayMode = HtmlUtil.EDIT;
		if(!Util.empty(payslipM)) {
			logger.debug("payslipM.getNoMonth >> "+payslipM.getNoMonth());
			if(payslipM.getNoMonth() == 0){
				displayMode =  HtmlUtil.VIEW;
			}else {
				displayMode =  HtmlUtil.EDIT;
			}
		}
		return displayMode;
	}
}
