package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ClearDataProjectCode implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(ClearDataProjectCode.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerCusResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerCusResultCode())){
			xrulesVerM.setVerCusResult(null);
			xrulesVerM.setVerCusResultCode(null);
			xrulesVerM.setVerCusUpdateBy(null);
			xrulesVerM.setVerCusUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
		}
		
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerHRResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerHRResultCode())){	
			xrulesVerM.setVerHRResult(null);
			xrulesVerM.setVerHRResultCode(null);
			xrulesVerM.setVerHRUpdateBy(null);
			xrulesVerM.setVerHRUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
		}
			
		JsonObjectUtil json = new JsonObjectUtil();
			json.CreateJsonObject("div_projectcode", HTMLRenderUtil.DisplayPopUpProjectCode(applicationM.getProjectCode(),HTMLRenderUtil.DISPLAY_MODE_EDIT,"13","project_code","textbox-projectcode","13","...","button-popup"));
			json.CreateJsonObject("project_code", "");
			json.CreateJsonObject("div_startdate", "");
			json.CreateJsonObject("div_enddate", "");
			json.CreateJsonObject("div_details", "");
			json.CreateJsonObject("div_promotion", "");
			json.CreateJsonObject("div_approvedate", "");
			json.CreateJsonObject("div_customergroup", "");
			json.CreateJsonObject("professionCode", "");
			json.CreateJsonObject("Priority", "");
			json.CreateJsonObject("application_property", "");
			json.CreateJsonObject("projectCode", "");	
			
			if(OrigUtil.isEmptyString(xrulesVerM.getVerCusResultCode())){
				json.CreateJsonObject("result_1032", "");
				json.CreateJsonObject("code_1032", "");
			}
			if(OrigUtil.isEmptyString(xrulesVerM.getVerHRResultCode())){
				json.CreateJsonObject("result_1033", "");
				json.CreateJsonObject("code_1033", "");
			}
			
			json.CreateJsonObject("projectcode_displaymode", HTMLRenderUtil.DISPLAY_MODE_EDIT);
			json.CreateJsonObject("projectcode_buttonmode", HTMLRenderUtil.DISPLAY_MODE_VIEW);
		return json.returnJson();
	}
	
}
