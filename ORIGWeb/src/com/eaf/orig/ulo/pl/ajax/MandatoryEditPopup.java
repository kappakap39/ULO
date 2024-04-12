package com.eaf.orig.ulo.pl.ajax;

//import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.ulo.pl.eai.util.EAIConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class MandatoryEditPopup implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(MandatoryEditPopup.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{		
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		if(null == formHandler) formHandler = new PLOrigFormHandler();
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if(null == applicationM) applicationM = new PLApplicationDataM();	
		String displayModeCCBlockCode = request.getParameter("display-mode-cc-blockcode");
		String displayMOdeKecBlockCode = request.getParameter("display-mode-kec-blockcode");
		
		JsonObjectUtil jObjUtil = new JsonObjectUtil();	
		String errorMessage = "";
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		
		PLXRulesExistCustDataM existCusM = xrulesVerM.getxRulesExistCustM();
		
		if(null == existCusM){
			existCusM = new PLXRulesExistCustDataM();
		}
		
//		BigDecimal zero = new BigDecimal("0.00");
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayModeCCBlockCode)){
//			BigDecimal ccCurrentBalance = DataFormatUtility.StringToBigDecimal(request.getParameter("edit-cc-current-balance"));
//			if (ccCurrentBalance.compareTo(zero) <= 0) {
//				errorMessage = ErrorUtil.getShortErrorMessage(request, "REQUIRE_EDIT_CC_CURRENT_BALANCE");
//				jObjUtil.CreateJsonMessage("error", errorMessage);
//			}		
			String ccLastPaymentDate = request.getParameter("edit-cc-lastpayment-date");
			if(OrigUtil.isEmptyString(ccLastPaymentDate)){
				errorMessage = ErrorUtil.getShortErrorMessage(request, "REQUIRE_EDIT_CC_LAST_PAYMENT_DATE");
				jObjUtil.CreateJsonMessage("error", errorMessage);
			}	
		}
		
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMOdeKecBlockCode)){
//			BigDecimal kecCurrentBalance = DataFormatUtility.StringToBigDecimal(request.getParameter("edit-kec-current-balance"));
//			if (kecCurrentBalance.compareTo(zero) <= 0) {
//				errorMessage = ErrorUtil.getShortErrorMessage(request, "REQUIRE_EDIT_KEC_CURRENT_BALANCE");
//				jObjUtil.CreateJsonMessage("error", errorMessage);
//			}
//			String kecLastPaymentDate = request.getParameter("edit-kec-lastpayment-date");
//			if(OrigUtil.isEmptyString(kecLastPaymentDate) && EAIConstant.BlockCode.O.equals(existCusM.getBlockCode())){
//				errorMessage = ErrorUtil.getShortErrorMessage(request, "REQUIRE_EDIT_KEC_LAST_PAYMENT_DATE");
//				jObjUtil.CreateJsonMessage("error", errorMessage);
//			}	
		}
		
		return jObjUtil.returnJson();
	}
	
}
