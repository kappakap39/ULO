package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;

public class DataFinalDecisonSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(DataFinalDecisonSubForm.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub

	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		logger.debug("validate Check information DF_REJECT Role ");
		boolean result = false;
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if (OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) &&
				(OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType())) { //Praisan 20121218 validate only type submit (1)
			 String df_reject_decision =request.getParameter("df_reject_decision");
			 String errorMsg = "";
			 logger.debug("df_reject_decision= "+df_reject_decision);
			 if(df_reject_decision==null||df_reject_decision.equalsIgnoreCase("unchecked")){
				 errorMsg = ErrorUtil.getShortErrorMessage(request,"DF_REJECT_VALIDATE");
				 formHandler.PushErrorMessage("df_reject_decision", errorMsg);
				 result = true;
			 }
		}
		return result;
	}

}
