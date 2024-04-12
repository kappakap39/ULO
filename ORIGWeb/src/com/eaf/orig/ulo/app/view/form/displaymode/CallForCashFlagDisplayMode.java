package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class CallForCashFlagDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(CallForCashFlagDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		
		String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
		String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
		String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};
		
		ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		
		CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
		logger.debug("cashTransfer "+cashTransfer);
		
		String displayMode = HtmlUtil.EDIT;
		if(!Util.empty(cashTransfer)) {
			if(CASH_DAY_1.equals(cashTransfer.getCashTransferType()) || CASH_1_HOUR.equals(cashTransfer.getCashTransferType()) ){
				logger.debug("cashTransfer "+cashTransfer.getCashTransferType());
				displayMode =  HtmlUtil.VIEW;
			} else {
				displayMode = HtmlUtil.EDIT;
			}
		}
		return displayMode;
	}
}
