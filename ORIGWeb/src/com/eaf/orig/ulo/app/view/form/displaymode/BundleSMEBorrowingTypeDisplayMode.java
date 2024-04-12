package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class BundleSMEBorrowingTypeDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(BundleSMEBorrowingTypeDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		BundleSMEDataM bundleSMEM = (BundleSMEDataM) moduleHandler.getObjectForm();
		ApplicationGroupDataM appGroupM = (ApplicationGroupDataM) EntityForm.getObjectForm();
		ApplicationDataM appM = appGroupM.getApplicationById(bundleSMEM.getApplicationRecordId());
		String displayMode = HtmlUtil.EDIT;
		if(!Util.empty(bundleSMEM) && PRODUCT_CRADIT_CARD.equals(appM.getProduct())) {
			logger.debug("bundleSMEM.getBorrowingType() >> "+bundleSMEM.getBorrowingType());
			displayMode =  HtmlUtil.VIEW;
		} else {
			displayMode =  HtmlUtil.EDIT;
		}
		return displayMode;
	}
}
