package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
//import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.ulo.pl.eai.util.EAILogic;
//import com.eaf.xrules.ulo.pl.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class EditBlockcodeValidate implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(EditBlockcodeValidate.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {	
		
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		if(null == formHandler) formHandler = new PLOrigFormHandler();
		PLApplicationDataM appM = formHandler.getAppForm();
		if(null == appM) appM = new PLApplicationDataM();
		
		String blockCode = request.getParameter("edit-cc-blockcode");
		logger.debug("Block Code >> "+blockCode);
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		EAIDataM eaiM = xrulesVerM.getEaiM();		
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}		
		JsonObjectUtil jObj = new JsonObjectUtil();	
		
//		EAILogic eaiLogic = new EAILogic();
		
//		if(eaiLogic.LogicCCBlockCode(blockCode)){
//			xrulesVerM.setCodeCCBlockCode(PLXrulesConstant.WebServiceCode.REQUIRE_CC_BLOCKCODE);
//			eaiM.setCodeCCBlockCode(PLXrulesConstant.WebServiceCode.REQUIRE_CC_BLOCKCODE);
//			jObj.CreateJsonObject("td-edit-cc-current-balance", HTMLRenderUtil.DisplayInputCurrency(null,HTMLRenderUtil.DISPLAY_MODE_EDIT,"########0.00","edit-cc-current-balance","textbox-currency","","12", true));
//			jObj.CreateJsonObject("td-edit-cc-lastpayment-date", HTMLRenderUtil.displayInputTagDate("appFormName",DataFormatUtility.dateEnToStringDateEn(null
//										,DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S),HTMLRenderUtil.DISPLAY_MODE_EDIT,"15","edit-cc-lastpayment-date"
//													,"right","currentDate","" ));
//			jObj.CreateJsonObject("display-mode-cc-blockcode", HTMLRenderUtil.DISPLAY_MODE_EDIT);
//		}else{
//			xrulesVerM.setCodeCCBlockCode(null);
//			jObj.CreateJsonObject("td-edit-cc-current-balance", HTMLRenderUtil.DisplayInputCurrency(null,HTMLRenderUtil.DISPLAY_MODE_VIEW,"########0.00","edit-cc-current-balance","textbox-currency","","12", true));
//			jObj.CreateJsonObject("td-edit-cc-lastpayment-date", HTMLRenderUtil.displayInputTagDate("appFormName",DataFormatUtility.dateEnToStringDateEn(null
//										,DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S),HTMLRenderUtil.DISPLAY_MODE_VIEW,"15","edit-cc-lastpayment-date"
//													,"right","currentDate","" ));
//			jObj.CreateJsonObject("display-mode-cc-blockcode", HTMLRenderUtil.DISPLAY_MODE_VIEW);
//		}
		return jObj.returnJson();
	}
	
}
