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

public class INSTR_ISSUED_NAMEIncomeDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(INSTR_ISSUED_NAMEIncomeDisplayMode.class);
	String INC_TYPE_FIN_INSTR_KBANK = SystemConstant.getConstant("INC_TYPE_FIN_INSTR_KBANK");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		IncomeInfoDataM incomeM = (IncomeInfoDataM) moduleHandler.getObjectForm();
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		ArrayList<IncomeCategoryDataM> finInstrumentList = incomeM.getAllIncomes();
		String displayMode = HtmlUtil.EDIT;
		if(!Util.empty(finInstrumentList)) {
			if(INC_TYPE_FIN_INSTR_KBANK.equals(incomeM.getIncomeType())){
				displayMode =  HtmlUtil.VIEW;
			}else {
				displayMode =  HtmlUtil.EDIT;
			}
		}
		logger.debug("INSTR_ISSUED_NAMEIncomeDisplayMode >>"+displayMode);
		return displayMode;
	}
}
