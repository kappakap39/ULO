package com.eaf.orig.ulo.app.view.form.subform.product.kec;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

public class KExpressCashProductPopup1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(KExpressCashProductPopup1.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		
		ApplicationDataM applicationItem =(ApplicationDataM)appForm;
		CardDataM card = applicationItem.getCard();
		String PRODUCT_K_EXPRESS_CASH =SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String GROUP_NO =request.getParameter("GROUP_NO_"+PRODUCT_K_EXPRESS_CASH);
		String NO_APP_IN_GANG = request.getParameter("NO_APP_IN_GANG_"+PRODUCT_K_EXPRESS_CASH);
		logger.debug("GROUP_NO >>> "+GROUP_NO);
		logger.debug("NO_APP_IN_GANG >>> "+NO_APP_IN_GANG);
	
		card.setGangNo(NO_APP_IN_GANG);
		card.setNoAppInGang(FormatUtil.toBigDecimal(NO_APP_IN_GANG));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
