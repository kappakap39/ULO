package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAO;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAOImpl;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PayslipKbankPensionDisplayMode  extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(PayslipKbankPensionDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String displayMode = HtmlUtil.VIEW;
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		IncomeInfoDataM incomeM = (IncomeInfoDataM) moduleHandler.getObjectForm();
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		PayslipDataM payslipM = null;
		if(incomeList != null && incomeList.size() > 0) {
			payslipM = (PayslipDataM)incomeList.get(0);
		}
		ORIGFormHandler ORIGForm =(ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		PersonalInfoDataM personalInfo =ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		String idNo = personalInfo.getIdno();
		logger.debug(">>idNo>>"+idNo);
		logger.debug(">>elementId>>"+elementId);
		DihDAO dihDAO = new DihDAOImpl();
		try {
			if(dihDAO.isKbankEmployee(idNo) && !Util.empty(payslipM.getNoMonth())){
				displayMode =  HtmlUtil.EDIT;
			}
		} catch (ApplicationException e) {
			logger.fatal("ERROR",e);
		}

		return displayMode;
	}
}
