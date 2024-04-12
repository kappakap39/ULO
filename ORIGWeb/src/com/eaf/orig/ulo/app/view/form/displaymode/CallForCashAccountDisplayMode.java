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

public class CallForCashAccountDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(CallForCashAccountDisplayMode.class);
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		if(Util.empty(applicationItem)){
			applicationItem = new ApplicationDataM();
		}
		
		CashTransferDataM callForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
		logger.debug("callForCash "+callForCash);
		String displayMode = HtmlUtil.VIEW;
		if(!Util.empty(callForCash) && CALL_FOR_CASH.equals(callForCash.getCashTransferType())){
			logger.debug("callForCash "+callForCash.getCashTransferType());
			displayMode =  HtmlUtil.EDIT;
		}
		return displayMode;
	}
}
