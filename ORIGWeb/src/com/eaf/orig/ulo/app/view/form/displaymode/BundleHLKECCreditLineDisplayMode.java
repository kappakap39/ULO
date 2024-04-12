package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class BundleHLKECCreditLineDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(BundleHLKECCreditLineDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		ApplicationGroupDataM appGroup = ORIGFormHandler.getObjectForm(request);
		ArrayList<ApplicationDataM> appList = appGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		
		String displayMode = HtmlUtil.VIEW;
		if(!Util.empty(appList)) {
			displayMode =  HtmlUtil.EDIT;
			logger.debug("BundleHLKEC_CREDIT_LINEDisplayMode >> "+displayMode);
		} 
		return displayMode;
	}
}
