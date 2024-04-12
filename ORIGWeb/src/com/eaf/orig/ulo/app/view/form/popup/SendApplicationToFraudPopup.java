package com.eaf.orig.ulo.app.view.form.popup;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.FraudRemarkDataM;

@SuppressWarnings("serial")
public class SendApplicationToFraudPopup extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(SendApplicationToFraudPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {		
		FraudRemarkDataM fraudRemarkM = (FraudRemarkDataM)appForm;
		String fraudRemark = request.getParameter("FRAUD_REMARK");
		logger.debug("fraudRemark : "+fraudRemark);
		fraudRemarkM.setRemark(fraudRemark);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String fraudRemark = request.getParameter("FRAUD_REMARK");
		logger.debug("fraudRemark : "+fraudRemark);
		if(Util.empty(fraudRemark)){
			formError.error("FRAUD_REMARK",MessageErrorUtil.getText(request, "ERROR_MUST_ENTER_REMARKS"));
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){		
		return false;
	}
}
