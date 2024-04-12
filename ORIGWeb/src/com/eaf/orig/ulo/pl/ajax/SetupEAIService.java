package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool.Constant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.ulo.pl.eai.util.EAIConstant;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class SetupEAIService implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{
		String eaiflag = request.getParameter("eaiflag");
		logger.debug("EAI Flag >>> "+eaiflag);
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(request);		
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();			
			personM.setXrulesVerification(xrulesVerM);
		}
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		JSONArray jArray = new JSONArray();
		
		ORIGXRulesTool tool = new ORIGXRulesTool();
		if(OrigConstant.FLAG_Y.equals(eaiflag)){
			xrulesVerM.setFlagTFB0137I01(null);
			this.ClearAmcTamc(eaiM, xrulesVerM, jArray);	
			this.ClearBankruptcy(eaiM, xrulesVerM, jArray);
			this.ClearBlockCode(eaiM, xrulesVerM, jArray);
			this.ClearCisNo(eaiM, xrulesVerM, jArray, personM);	
			this.ClearClassifyLevel(eaiM, xrulesVerM, jArray);
			this.ClearNPL(eaiM, xrulesVerM, jArray);	
			tool.ButtonStyleEngine(jArray, PLXrulesConstant.ButtonID.BUTTON_7001, appM, request);			
		}else{
//			xrulesVerM.setFlagTFB0137I01(EAIConstant.USED_EDIT_TFB0137I01);
		}
		return jArray.toString();
	}
	public void ClearAmcTamc(EAIDataM eaiM,PLXRulesVerificationResultDataM xrulesVerM ,JSONArray jArray){
		xrulesVerM.setAmcTamcCode(null);
		xrulesVerM.setAmcTamcResult(null);
		xrulesVerM.setEditAmcTamc(null);
		eaiM.setEditAmcTamc(null);
		xrulesVerM.setCodeServiceAmcTamc(null);
		eaiM.setCodeServiceAmcTamc(null);			
		JSONObject jObject = new JSONObject();
//			jObject.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.AMC_TAMC);
//			jObject.put(Constant.TYPE, Constant.EXECUTE);
//			jObject.put(Constant.RESULT_CODE,"");
//			jObject.put(Constant.RESULT_DESC,"");
//			jObject.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));
		jArray.put(jObject);
	}
	public void ClearBankruptcy(EAIDataM eaiM,PLXRulesVerificationResultDataM xrulesVerM ,JSONArray jArray){
		xrulesVerM.setBankruptcyCode(null);
		xrulesVerM.setBankruptcyResult(null);
		xrulesVerM.setEditBankruptcy(null);
		eaiM.setEditBankruptcy(null);
		xrulesVerM.setCodeServiceBankRuptcy(null);
		eaiM.setCodeServiceBankRuptcy(null);
		JSONObject jObject = new JSONObject();
//			jObject.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.BANKRUPTCY);
//			jObject.put(Constant.TYPE, Constant.EXECUTE);
//			jObject.put(Constant.RESULT_CODE,"");
//			jObject.put(Constant.RESULT_DESC,"");
//			jObject.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));
		jArray.put(jObject);
	}
	public void ClearBlockCode(EAIDataM eaiM,PLXRulesVerificationResultDataM xrulesVerM ,JSONArray jArray){
		xrulesVerM.setBlockCodeccCode(null);
		xrulesVerM.setBlockCodeccResult(null);
		xrulesVerM.setcCCurrentBalance(null);
		xrulesVerM.setcCLastPaymentDate(null);
		xrulesVerM.setEditBlockCode(null);
		eaiM.setEditBlockCode(null);
		xrulesVerM.setCodeServiceBlockcode(null);
		eaiM.setCodeServiceBlockcode(null);
		JSONObject jObject = new JSONObject();
//			jObject.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE);
//			jObject.put(Constant.TYPE, Constant.EXECUTE);
//			jObject.put(Constant.RESULT_CODE,"");
//			jObject.put(Constant.RESULT_DESC,"");
//			jObject.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));
		jArray.put(jObject);
	}
	public void ClearCisNo(EAIDataM eaiM,PLXRulesVerificationResultDataM xrulesVerM ,JSONArray jArray ,PLPersonalInfoDataM personM){
		xrulesVerM.setEditCisNo(null);
		eaiM.setEditCisNo(null);
		xrulesVerM.setCodeServiceCisNo(null);
		eaiM.setCodeServiceCisNo(null);			
		personM.setCisNo(null);
		JSONObject jObject = null;
		jObject = new JSONObject();
//			jObject.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.CIS_NO);
//			jObject.put(Constant.TYPE, Constant.EXECUTE);
//			jObject.put(Constant.RESULT_CODE,"");
//			jObject.put(Constant.RESULT_DESC,"-");
//			jObject.put(Constant.STYLE,"");
		jArray.put(jObject);			
		
		xrulesVerM.setBehaviorRiskGradeCode(null);
		xrulesVerM.setBehaviorRiskGradeResult(null);
		xrulesVerM.setBehavRiskGradeDecision(null);
		xrulesVerM.setxRulesBScoreVect(null);
			jObject = new JSONObject();
//			jObject.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE);
//			jObject.put(Constant.TYPE, Constant.EXECUTE);
//			jObject.put(Constant.RESULT_CODE,"");
//			jObject.put(Constant.RESULT_DESC,"");
//			jObject.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));
		jArray.put(jObject);
	}
	public void ClearClassifyLevel(EAIDataM eaiM,PLXRulesVerificationResultDataM xrulesVerM ,JSONArray jArray){
		xrulesVerM.setLpmClassifyLvCode(null);
		xrulesVerM.setLpmClassifyLvResult(null);
		xrulesVerM.setEditClassifyLevel(null);
		eaiM.setEditClassifyLevel(null);
		xrulesVerM.setCodeServiceclassifyLevel(null);
		eaiM.setCodeServiceclassifyLevel(null);
		JSONObject jObject = new JSONObject();
//			jObject.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.CLASSIFY_LEVEL_LPM);
//			jObject.put(Constant.TYPE, Constant.EXECUTE);
//			jObject.put(Constant.RESULT_CODE,"");
//			jObject.put(Constant.RESULT_DESC,"");
//			jObject.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));
		jArray.put(jObject);
	}
	public void ClearNPL(EAIDataM eaiM,PLXRulesVerificationResultDataM xrulesVerM ,JSONArray jArray){
		xrulesVerM.setNplLpmCode(null);
		xrulesVerM.setNplLpmResult(null);
		xrulesVerM.setEditNpl(null);
		eaiM.setEditNpl(null);
		xrulesVerM.setCodeServiceNplLpm(null);
		eaiM.setCodeServiceNplLpm(null);
		JSONObject jObject = new JSONObject();
//			jObject.put(Constant.SERVICE_ID, PLXrulesConstant.ModuleService.NPL_LPM);
//			jObject.put(Constant.TYPE, Constant.EXECUTE);
//			jObject.put(Constant.RESULT_CODE,"");
//			jObject.put(Constant.RESULT_DESC,"");
//			jObject.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));
		jArray.put(jObject);
	}
}
