package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class RetrieveOldNcbManadatory implements AjaxDisplayGenerateInf{
	static Logger logger = Logger.getLogger(RetrieveOldNcbManadatory.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		String idNo = request.getParameter("identification_no");
		String errorMsg = null;
		if(OrigUtil.isEmptyString(idNo)){
			errorMsg =  ErrorUtil.getShortErrorMessage(request,"MSG_XRULES_REQUIRE_IDNO");
			jObj.CreateJsonObject("identification_no",errorMsg);
		}
		
		if (OrigUtil.isEmptyString(xrulesVerM.getExecute1Result())
				|| !PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getExecute1Result())) {
			errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_RESULT_EXECUTE1");
			jObj.CreateJsonObject("",errorMsg);
		}		
		return jObj.returnJson();
	}

}
