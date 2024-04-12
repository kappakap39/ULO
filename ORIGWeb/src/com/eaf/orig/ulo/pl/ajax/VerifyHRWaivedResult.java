package com.eaf.orig.ulo.pl.ajax;

import java.sql.Timestamp;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class VerifyHRWaivedResult implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(VerifyHRWaivedResult.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		if(null == formHandler) formHandler = new PLOrigFormHandler();
		PLApplicationDataM appM = formHandler.getAppForm();
		if(null == appM) appM = new PLApplicationDataM();
		
		String formID = formHandler.getFormID();
		String searchType = (String) request.getSession().getAttribute("searchType");		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		ORIGFormUtil formUtil = new ORIGFormUtil();		
		String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), appM.getJobState(), formID, searchType);				
		logger.debug("DisplayMode >> "+displayMode);
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();		
		if(null == xrulesVerM) {
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		
		ORIGCacheUtil origCache = new ORIGCacheUtil();			
		Vector v = (Vector)(origCache.getNaosCacheDataMs(appM.getBusinessClassId(),71)).clone();	
		CacheDataM cacheM = null;
		if(null == v){
			v = new Vector();
		}
		
		JsonObjectUtil jObj = new JsonObjectUtil();	
		
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
		
			XrulesResponseDataM reponse = this.ExecuteILOGVerifyHR(appM, userM);
			
			if(null != reponse && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(reponse.getExecuteCode())){
				if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(reponse.getResultCode())){
					
					cacheM = new CacheDataM();
					cacheM.setCode(PLXrulesConstant.ResultCode.CODE_WAIVED);
					cacheM.setThDesc(PLXrulesConstant.ResultDesc.CODE_WAIVED_DESC);
						v.add(cacheM);		
					
					if(OrigUtil.isEmptyString(xrulesVerM.getVerHRResultCode())){	
						
						xrulesVerM.setVerHRResultCode(reponse.getResultCode());
						xrulesVerM.setVerHRResult(reponse.getResultDesc());
						xrulesVerM.setVerHRUpdateBy(userM.getUserName());
						xrulesVerM.setVerHRUpdateDate(new Timestamp(new java.util.Date().getTime()));
						
						String verHrResultField = "result_"+PLXrulesConstant.ModuleService.VERIFY_HR;
						String verHrCodeField = "code_"+PLXrulesConstant.ModuleService.VERIFY_HR;
						jObj.CreateJsonObject(verHrCodeField,PLXrulesConstant.ResultCode.CODE_WAIVED);
						jObj.CreateJsonObject(verHrResultField,PLXrulesConstant.ResultDesc.CODE_WAIVED_DESC);
						jObj.CreateJsonObject("STYLE",ORIGLogic.LogicColorStyleResult(PLXrulesConstant.ResultCode.CODE_WAIVED, ""));
						
					}				
				}else{
					if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerHRResultCode())){						
						xrulesVerM.setVerHRResultCode(null);
						xrulesVerM.setVerHRResult(null);
						xrulesVerM.setVerHRUpdateBy(userM.getUserName());
						xrulesVerM.setVerHRUpdateDate(new Timestamp(new java.util.Date().getTime()));
						
						String verHrResultField = "result_"+PLXrulesConstant.ModuleService.VERIFY_HR;
						String verHrCodeField = "code_"+PLXrulesConstant.ModuleService.VERIFY_HR;
						jObj.CreateJsonObject(verHrCodeField,"");
						jObj.CreateJsonObject(verHrResultField,"");
						jObj.CreateJsonObject("STYLE",ORIGLogic.LogicColorStyleResult("", ""));
					}
				}
			}
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicy = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
//			if(xrulesPolicy==null){
//				 xrulesPolicy=new PLXRulesPolicyRulesDataM();
//				 xrulesPolicy.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(xrulesPolicy);
//			}
			xrulesPolicy.setResultCode(xrulesVerM.getVerHRResultCode());
			xrulesPolicy.setResultDesc(xrulesVerM.getVerHRResult());	
			xrulesPolicy.setUpdateBy(userM.getUserName());					 
			/*End Set Policy Rules */
			

		}else{
			if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerHRResultCode())){
				cacheM = new CacheDataM();
				cacheM.setCode(PLXrulesConstant.ResultCode.CODE_WAIVED);
				cacheM.setThDesc(PLXrulesConstant.ResultDesc.CODE_WAIVED_DESC);
					v.add(cacheM);
			}
		}
		
		jObj.CreateJsonObject("td-verhr-final-staus", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(v,xrulesVerM.getVerHRResultCode()
											,"verhr-final-staus",displayMode,""));	
		
		return jObj.returnJson();
	}
	
	public XrulesResponseDataM ExecuteILOGVerifyHR(PLApplicationDataM appM ,UserDetailM userM){	
		XrulesResponseDataM reponse = null;
		try{
			if(null == appM) appM = new PLApplicationDataM();			
			PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();			
			if(null == xrulesVerM) {
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
//			if(OrigUtil.isEmptyString(xrulesVerM.getVerHRResultCode())){
				ORIGXRulesTool tool = new ORIGXRulesTool();				
				XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(appM, PLXrulesConstant.ModuleService.VERIFY_HR, userM);
				ExecuteServiceManager execute = ORIGEJBService.getExecuteServiceManager();
				reponse = execute.ILOGServiceModule(requestM);
//				if(PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(reponse.getExecuteCode())
//						&& PLXrulesConstant.ResultCode.CODE_WAIVED.equals(reponse.getResultCode())){
//					xrulesVerM.setVerHRResultCode(reponse.getResultCode());
//					xrulesVerM.setVerHRResult(reponse.getResultDesc());
//					xrulesVerM.setVerHRUpdateBy(userM.getUserName());
//					xrulesVerM.setVerHRUpdateDate(new Timestamp(new java.util.Date().getTime()));
//				}
//			}
		}catch (Exception e) {
			logger.debug("Error "+e.getMessage());
		}
		return reponse;
	}
	
}
