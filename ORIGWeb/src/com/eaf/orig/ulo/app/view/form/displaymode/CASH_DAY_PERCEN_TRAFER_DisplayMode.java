package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class CASH_DAY_PERCEN_TRAFER_DisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(CASH_DAY_PERCEN_TRAFER_DisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String displayMode = HtmlUtil.VIEW;
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
		String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
		String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};
		ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		
		CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
		logger.debug("cashTransfer "+cashTransfer);
		if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getCashTransferType()) ){
			logger.debug("cashTransfer.getCashTransferType() "+cashTransfer.getCashTransferType());
			displayMode = HtmlUtil.EDIT;
		}
		return displayMode;
	}
}
