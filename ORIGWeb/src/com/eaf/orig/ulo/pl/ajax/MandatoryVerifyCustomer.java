package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPhoneVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class MandatoryVerifyCustomer implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(MandatoryVerifyCustomer.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{		
		String vercusResult = request.getParameter("vercus-final-status");
		logger.debug("vercus-final-status >> "+vercusResult);
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}
		Vector<PLXRulesPhoneVerificationDataM> phoneVerVect = xrulesVerM.getVXRulesPhoneVerificationDataM();		
		JsonObjectUtil jObjUtil = new JsonObjectUtil();	
		if(PLXrulesConstant.ResultCode.CODE_PASS.equals(vercusResult)){
			if(!OrigUtil.isEmptyVector(phoneVerVect)){
				PLXRulesPhoneVerificationDataM verifyM = phoneVerVect.lastElement();
				if(OrigConstant.FLAG_N.equals(verifyM.getPhoneVerStatus())){
					jObjUtil.CreateJsonMessage("error",ErrorUtil.getShortErrorMessage(request,"MSG_ERROR_VERCUS_RESULT"));
					return jObjUtil.returnJson();
				}				
			}
		}

		if(!OrigUtil.isEmptyString(vercusResult)){
			if(OrigUtil.isEmptyVector(phoneVerVect)){
				jObjUtil.CreateJsonMessage("error",ErrorUtil.getShortErrorMessage(request,"MSG_ERROR_VERCUS_RESULT"));
				return jObjUtil.returnJson();				
			}
		}
		return jObjUtil.returnJson();
	}

}
