package com.eaf.orig.ulo.app.view.form.popup.product.kpl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class SupplementaryCardPopup extends ORIGSubForm {

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
	
	String	cardId= request.getParameter("KPL_CARD_ID");
	String	percentLimitMaincard= request.getParameter("KPL_PERCENT_LIMIT_MAINCARD");
	String	percentLimit= request.getParameter("KPL_PERCENT_LIMIT");
				CardDataM cardM = ((CardDataM)appForm);
				cardM.setPercentLimitMaincard(percentLimitMaincard);
				cardM.setCardId(cardId);
				
		//*****		percentLimit	
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
