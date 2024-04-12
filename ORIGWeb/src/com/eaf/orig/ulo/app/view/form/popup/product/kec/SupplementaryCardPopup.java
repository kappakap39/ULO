package com.eaf.orig.ulo.app.view.form.popup.product.kec;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.popup.product.kcc.AmwayPopup;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class SupplementaryCardPopup extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(SupplementaryCardPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
	
	String	cardId= request.getParameter("KEC_CARD_ID");
	String	percentLimitMaincard= request.getParameter("KEC_PERCENT_LIMIT_MAINCARD");
	String	percentLimit= request.getParameter("KEC_PERCENT_LIMIT");
	logger.debug("percentLimitMaincard >>> "+percentLimitMaincard);
				CardDataM cardM = ((CardDataM)appForm);
				if(Util.empty(cardM)){
					cardM = new CardDataM();
				}
				cardM.setPercentLimitMaincard(percentLimitMaincard);
			
				
		//*****		percentLimit	
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		CardDataM cardM = ((CardDataM)appForm);
		FormErrorUtil formError = new FormErrorUtil();

//		formError.mandatory(subformId,"SEND_DOC",personalInfo.getMailingAddress(),request);
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
