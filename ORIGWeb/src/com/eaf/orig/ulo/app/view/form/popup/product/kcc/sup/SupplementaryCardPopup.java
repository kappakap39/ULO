package com.eaf.orig.ulo.app.view.form.popup.product.kcc.sup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.Formatter.Format;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class SupplementaryCardPopup extends ORIGSubForm {

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String	percentLimitMaincard= request.getParameter("PERCENT_LIMIT_MAINCARD_"+PRODUCT_CRADIT_CARD);
	String	percentLimit= request.getParameter("PERCENT_LIMIT");
	
	ApplicationDataM applicationItem = (ApplicationDataM)appForm;
	CardDataM cardM = applicationItem.getCard();
	LoanDataM loanM = applicationItem.getLoan();
	cardM.setPercentLimitMaincard(percentLimitMaincard);
	loanM.setRequestLoanAmt(FormatUtil.toBigDecimal(percentLimit, true));
		//*****		percentLimit
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
