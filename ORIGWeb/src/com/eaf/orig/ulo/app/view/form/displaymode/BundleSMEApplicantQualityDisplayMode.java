package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class BundleSMEApplicantQualityDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(BundleSMEApplicantQualityDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String LOAN_TYPE_SINGLE = SystemConstant.getConstant("LOAN_TYPE_SINGLE");
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		BundleSMEDataM bundleSMEM = (BundleSMEDataM) moduleHandler.getObjectForm();
		String displayMode = HtmlUtil.VIEW;
//		&& !LOAN_TYPE_SINGLE.equals(bundleSMEM.getBorrowingType())
		if(!Util.empty(bundleSMEM) && !Util.empty(bundleSMEM.getBorrowingType())) {
			logger.debug("bundleSMEM.getBorrowingType() >> "+bundleSMEM.getBorrowingType());
			displayMode =  HtmlUtil.EDIT;
		} 
		return displayMode;
	}
}
