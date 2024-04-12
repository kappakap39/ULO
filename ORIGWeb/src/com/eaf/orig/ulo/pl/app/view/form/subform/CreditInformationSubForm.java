package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleCCDataM;

public class CreditInformationSubForm extends ORIGSubForm {
	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler plOrigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		
		PLApplicationDataM plAppM = plOrigform.getAppForm();		
		if(OrigUtil.isEmptyObject(plAppM))  plAppM = new PLApplicationDataM();
		
		PLBundleCCDataM plBundleCCDataM = plAppM.getBundleCCM();
		if(OrigUtil.isEmptyObject(plBundleCCDataM))  plBundleCCDataM = new PLBundleCCDataM();
		
		String credit_card_result = request.getParameter("credit_card_result");
		String credit_card_app_score = request.getParameter("credit_card_app_score");
		
		plBundleCCDataM.setCreditCardResult(credit_card_result);
		plBundleCCDataM.setCreditCardAppScore(credit_card_app_score);
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		String credit_card_app_score = request.getParameter("credit_card_app_score");
		boolean result = false;
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);

		logger.debug("VerificationSubForm SaveType >>> " + formHandler.getSaveType());

		PLApplicationDataM appM = formHandler.getAppForm();
		if (null == appM) appM = new PLApplicationDataM();
		String errorMsg = "";;
		if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType()) &&OrigConstant.BusClass.FCP_KEC_CC.equals(appM.getBusinessClassId())){
			if(OrigUtil.isEmptyString(credit_card_app_score)){											
					errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_CREDIT_CARD_APP_SCORE");
					formHandler.PushErrorMessage("credit_card_app_score", errorMsg);
					result = true;
				}
	        }
		return result;
	}

}
