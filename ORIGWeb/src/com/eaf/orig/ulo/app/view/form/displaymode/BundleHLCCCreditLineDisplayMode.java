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

public class BundleHLCCCreditLineDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(BundleHLCCCreditLineDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		ApplicationGroupDataM appGroup = ORIGFormHandler.getObjectForm(request);
		ArrayList<ApplicationDataM> appList = appGroup.filterApplicationLifeCycle(PRODUCT_CRADIT_CARD);
		
		String displayMode = HtmlUtil.VIEW;
		if(!Util.empty(appList)) {
			displayMode =  HtmlUtil.EDIT;
			logger.debug("BundleHLCC_CREDIT_LINEDisplayMode >> "+displayMode);
		} 
		return displayMode;
	}
}
