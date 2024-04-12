package com.eaf.orig.ulo.pl.app.utility;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool.Constant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.cache.model.SensitiveFieldCacheDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXrulesNplLpmDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class SensitiveFieldTool{	
	
	Logger logger = Logger.getLogger(this.getClass());	
	
	public void MapObjectDisplaySensitive(PLApplicationDataM appM ,PLApplicationDataM appSM 
									, Vector<SensitiveFieldCacheDataM> sensitiveVect, String type ,UserDetailM userM
										,JSONArray jsonArray ,List<String> sButton){
		logger.debug("[MapObjectDisplaySensitive]..");
		JSONObject obj = null;
		for(SensitiveFieldCacheDataM dataM :sensitiveVect){
			switch (dataM.getServiceID()){
				case PLXrulesConstant.ModuleService.SPECIAL_CUSTOMER:	
					obj = new JSONObject();		
					obj = this.MapSpecialCustomer(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.DEMOGRAPHIC:
					obj = new JSONObject();
					obj = this.MapDemoGraphic(appM, appSM,type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION:
					obj = new JSONObject();
					obj = this.MapDuplicateApplication(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.EXISTING_KEC:
					obj = new JSONObject();
					obj = this.MapExistingKEC(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE:
					obj = new JSONObject();
					obj = this.MapBlockCodeCC(appM, appSM, type, userM);
					jsonArray.put(obj);					
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.KEC_BLOCKCODE:
					obj = new JSONObject();
					obj = this.MapBlockCodeKEC(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.CLASSIFY_LEVEL_LPM:
					obj = new JSONObject();
					obj = this.MapClassifyLevel(appM, appSM,type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.SP:
					obj = new JSONObject();
					obj = this.MapSP(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.SANCTION_LIST:
					obj = new JSONObject();
					obj = this.MapSancationList(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BANKRUPTCY:
					obj = new JSONObject();
					obj = this.MapBankRuptcy(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.AMC_TAMC:
					obj = new JSONObject();
					obj = this.MapAmcTamc(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.WATCHLIST_FRAUD:
					obj = new JSONObject();
					obj = this.MapWathcListFraud(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE:
					obj = new JSONObject();
					obj = this.MapBehavoiorRiskGrade(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.PAY_ROLL:
					obj = new JSONObject();
					obj = this.MapPayRoll(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.KBANK_EMPLOYEE:
					obj = new JSONObject();
					obj = this.MapKbankEmployee(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.LISTED_COMPANY:
					obj = new JSONObject();
					obj = this.MapListedCompany(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.FRAUD_COMPANY:
					obj = new JSONObject();
					obj = this.MapFraudCompany(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY:
					obj = new JSONObject();
					obj = this.MapBankruptcyCompany(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY:	
					obj = new JSONObject();
					obj = this.MapAmcTamcCompany(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.NPL_LPM:
					obj = new JSONObject();
					obj = this.MapNplLpm(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY_FIRST:
					obj = new JSONObject();
					obj = this.MapAmcTamcCompanyFirst(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY_FIRST:
					obj = new JSONObject();
					obj = this.MapBankruptcyCompanyFirst(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.STATUS_RESTRUCTURE_FLAG:
					obj = new JSONObject();
					obj = this.MapLastRestructureFlag(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.LAST_RESURUCTURE_DATE_LPM:
					obj = new JSONObject();
					obj = this.MapLastRestructureDate(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.INCOME_DEBT:
					obj = new JSONObject();
					obj = this.MapIncomeDebt(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.MONTH_PROFILE:
					obj = new JSONObject();
					obj = this.MapMonthProfile(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;				
				case PLXrulesConstant.ModuleService.CIS_NO:
					obj = new JSONObject();
					obj = this.MapCisNo(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.CUSTOMER_NO:
					obj = new JSONObject();
					obj = this.MapCustomerNo(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.EXISTING_KEC_POD:	
					obj = new JSONObject();		
					obj = this.MapExistingPOD(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BUNDLING_CC_AGE:	
					obj = new JSONObject();		
					obj = this.MapBundlingCCAge(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BUNDLING_CC_EDUCATION:	
					obj = new JSONObject();		
					obj = this.MapBundlingCCEducation(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BUNDLING_CC_RESIDENT:	
					obj = new JSONObject();		
					obj = this.MapBundlingCCResident(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.SOLO_VIEW:	
					obj = new JSONObject();		
					obj = this.MapSoloView(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.LEC:	
					obj = new JSONObject();		
					obj = this.MapLec(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				case PLXrulesConstant.ModuleService.BOT5X:	
					obj = new JSONObject();		
					obj = this.MapBOT5X(appM, appSM, type, userM);
					jsonArray.put(obj);
					if(!ValidateSensitiveButton(sButton, dataM.getActionExecute())){
						sButton.add(dataM.getActionExecute());
						this.ButtonExecuteLogic(dataM.getActionExecute(), appM, appSM, type);
					}
					break;
				default:
					break;
			}
		}
	}
	
	public JSONObject MapDemoGraphic(PLApplicationDataM appM ,PLApplicationDataM appSM ,String type ,UserDetailM userM){
		
			PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
			JSONObject obj = new JSONObject();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}	
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.DEMOGRAPHIC);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
			if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");				
				xrulesVerM.setDemographicCode("");
				xrulesVerM.setDemographicResult("");
				xrulesVerM.setAgeVerCode("");
				xrulesVerM.setAgeVerResult("");
				xrulesVerM.setNationalityVerCode("");
				xrulesVerM.setNationalityVerResult("");
				xrulesVerM.setDemographicUpdateDate(new Timestamp(new java.util.Date().getTime()));
				xrulesVerM.setDemographicUpdateBy(userM.getUserName());
				/*Remove Policy Rules*/
				xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_NATIONALITY);
				xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_AGE);				  
				/*End Remove Policy Rules*/				 
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));				
				return obj;
			}
	
			PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
			if(null == sXrulesVerM){
				sXrulesVerM = new PLXRulesVerificationResultDataM();
				sPersonalM.setXrulesVerification(sXrulesVerM);
			}	
			
			xrulesVerM.setDemographicCode(sXrulesVerM.getDemographicCode());
			xrulesVerM.setDemographicResult(sXrulesVerM.getDemographicResult());
			xrulesVerM.setAgeVerCode(sXrulesVerM.getAgeVerCode());
			xrulesVerM.setAgeVerResult(sXrulesVerM.getAgeVerResult());
			xrulesVerM.setNationalityVerCode(sXrulesVerM.getNationalityVerCode());
			xrulesVerM.setNationalityVerResult(sXrulesVerM.getNationalityVerResult());
			xrulesVerM.setDemographicUpdateDate(sXrulesVerM.getDemographicUpdateDate());
			xrulesVerM.setDemographicUpdateBy(sXrulesVerM.getDemographicUpdateBy());		
			
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getDemographicCode()));
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getDemographicResult()));
			
//			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_NATIONALITY);				 
			if(null != policyRulesSM){
//				xrulesVerM.getvXRulesPolicyRulesDataM().add(policyRulesSM);
				xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_NATIONALITY,policyRulesSM);
			}	
			PLXRulesPolicyRulesDataM policyRulesAgeSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_AGE);				 
			if(null != policyRulesAgeSM){
//				xrulesVerM.getvXRulesPolicyRulesDataM().add(policyRulesAgeSM);
				xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_AGE,policyRulesAgeSM);
			}	
//			/*End Set Policy Rules */
			 
//			obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getDemographicCode(), sXrulesVerM.getDemographicResult())); 
			 
		return obj;
	}
	
	
	public JSONObject MapSpecialCustomer(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){	
			PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
			JSONObject obj = new JSONObject();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}	
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.SPECIAL_CUSTOMER);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
			if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){				
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");		
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
				xrulesVerM.setSpecialCusCode("");
				xrulesVerM.setSpecialCusResult("");
				xrulesVerM.setSpecialCusUpdateDate(new Timestamp(new java.util.Date().getTime()));
				xrulesVerM.setSpecialCusUpdateBy(userM.getUserName());
				return obj;
			}

			PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
			if(null == sXrulesVerM){
				sXrulesVerM = new PLXRulesVerificationResultDataM();
				sPersonalM.setXrulesVerification(sXrulesVerM);
			}	
			
			xrulesVerM.setSpecialCusCode(sXrulesVerM.getSpecialCusCode());		
			xrulesVerM.setSpecialCusResult(sXrulesVerM.getSpecialCusResult());
			xrulesVerM.setSpecialCusUpdateDate(sXrulesVerM.getSpecialCusUpdateDate());
			xrulesVerM.setSpecialCusUpdateBy(sXrulesVerM.getSpecialCusUpdateBy());			
			
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSpecialCusCode()));
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSpecialCusResult()));
			
//			obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getSpecialCusCode(), sXrulesVerM.getSpecialCusResult())); 
			
		return obj;
	}
		

	public JSONObject MapDuplicateApplication(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setDedupCode("");
			xrulesVerM.setDedupResult("");
			xrulesVerM.setDedupUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setDedupUpdateBy(userM.getUserName());
			xrulesVerM.setVXRulesDedupDataM(null);
			
			//Vikrom 20121218
			xrulesVerM.setDupInProcessFlag(null);
			xrulesVerM.setDupAscendancyFlag(null);
			xrulesVerM.setDupRejectCancleFlag(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_EXISTING_APPLICATION);
			/*End Remove Policy Rules*/
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		
		xrulesVerM.setDedupCode(sXrulesVerM.getDedupCode());
		xrulesVerM.setDedupResult(sXrulesVerM.getDedupResult());
		xrulesVerM.setDedupUpdateDate(sXrulesVerM.getDedupUpdateDate());
		xrulesVerM.setDedupUpdateBy(sXrulesVerM.getDedupUpdateBy());
		xrulesVerM.setVXRulesDedupDataM(sXrulesVerM.getVXRulesDedupDataM());
		//Vikrom 20121218
		xrulesVerM.setDupInProcessFlag(sXrulesVerM.getDupInProcessFlag());
		xrulesVerM.setDupAscendancyFlag(sXrulesVerM.getDupAscendancyFlag());
		xrulesVerM.setDupRejectCancleFlag(sXrulesVerM.getDupRejectCancleFlag());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getDedupCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getDedupResult()));
		
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_EXISTING_APPLICATION);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_EXISTING_APPLICATION,policyRulesSM);
		}		  
		/*End Set Policy Rules */
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getDedupCode(), sXrulesVerM.getDedupResult())); 
		 
		return obj;
	}

	public JSONObject MapExistingKEC(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.EXISTING_KEC);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setExistCustCode("");
			xrulesVerM.setExistCustResult("");
			xrulesVerM.setExistingCustUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setExistCustUpdateBy(userM.getUserName());		
			
			eaiM.setxRulesExistCustM(null);
			xrulesVerM.setxRulesExistCustM(null);
			
			eaiM.setCodeServiceCP0148I01(null);
			xrulesVerM.setCodeServiceCP0148I01(null);
			eaiM.setCodeKecBlockCode(null);
			xrulesVerM.setCodeKecBlockCode(null);
						
			eaiM.setKecCurrentBalance(null);
			eaiM.setKecLastPaymentDate(null);
			
			xrulesVerM.setKecCurrentBalance(null);
			xrulesVerM.setKecLastPaymentDate(null);
			xrulesVerM.setExistCustType(null);
			if(OrigConstant.BusClass.FCP_KEC_HL.equals(appM.getBusinessClassId())
					||OrigConstant.BusClass.FCP_KEC_SME.equals(appM.getBusinessClassId())
						|| OrigConstant.BusClass.FCP_KEC_AL.equals(appM.getBusinessClassId())){
				appM.setICDCFlag(null);
			}
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC);
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC_POD);
			/*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		
		xrulesVerM.setExistCustCode(sXrulesVerM.getExistCustCode());		
		xrulesVerM.setExistCustResult(sXrulesVerM.getExistCustResult());
		xrulesVerM.setExistingCustUpdateDate(sXrulesVerM.getExistingCustUpdateDate());
		xrulesVerM.setExistCustUpdateBy(sXrulesVerM.getExistCustUpdateBy());
			eaiM.setxRulesExistCustM(sXrulesVerM.getxRulesExistCustM());
		xrulesVerM.setxRulesExistCustM(sXrulesVerM.getxRulesExistCustM());
		
		eaiM.setCodeServiceCP0148I01(sXrulesVerM.getCodeServiceCP0148I01());
		xrulesVerM.setCodeServiceCP0148I01(sXrulesVerM.getCodeServiceCP0148I01());
		
		eaiM.setCodeKecBlockCode(sXrulesVerM.getCodeKecBlockCode());
		xrulesVerM.setCodeKecBlockCode(sXrulesVerM.getCodeKecBlockCode());
		
		eaiM.setKecCurrentBalance(sXrulesVerM.getKecCurrentBalance());
		eaiM.setKecLastPaymentDate(sXrulesVerM.getKecLastPaymentDate());
		
		xrulesVerM.setKecCurrentBalance(sXrulesVerM.getKecCurrentBalance());
		xrulesVerM.setKecLastPaymentDate(sXrulesVerM.getKecLastPaymentDate());
		
		if(OrigConstant.BusClass.FCP_KEC_HL.equals(appM.getBusinessClassId())
				||OrigConstant.BusClass.FCP_KEC_SME.equals(appM.getBusinessClassId())
					|| OrigConstant.BusClass.FCP_KEC_AL.equals(appM.getBusinessClassId())){
			appM.setICDCFlag(appSM.getICDCFlag());
		}
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getExistCustCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getExistCustResult()));
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC,policyRulesSM);
		}		
		PLXRulesPolicyRulesDataM policyRulesPODSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC_POD);				 
		if(null != policyRulesPODSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesPODSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC_POD,policyRulesPODSM);
		}	
		/*End Set Policy Rules */
		 		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getExistCustCode(), sXrulesVerM.getExistCustResult()));  
		 
		return obj;	
	}
	
	public JSONObject MapBlockCodeCC(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
			
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setBlockCodeccCode("");
			xrulesVerM.setBlockCodeccResult("");
			xrulesVerM.setBlockCodeccUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBlockCodeccUpdateBy(userM.getUserName());		
			
			xrulesVerM.setBlockCodeCc(null);
			eaiM.setBlockCodeCc(null);
			
			xrulesVerM.setEditBlockCode(null);
			eaiM.setEditBlockCode(null);			
			xrulesVerM.setCodeServiceBlockcode(null);
			eaiM.setCodeServiceBlockcode(null);
			
//			if(!EAIConstant.BlockCode.O.equals(xrulesVerM.getEditBlockCode())){
			
			xrulesVerM.setcCCurrentBalance(null);
			xrulesVerM.setcCLastPaymentDate(null);
			eaiM.setcCCurrentBalance(null);
			eaiM.setcCLastPaymentDate(null);
			xrulesVerM.setCodeCCBlockCode(null);
			eaiM.setCodeCCBlockCode(null);
			
//			}
									
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_CARD_BLOCK_CODE);
			/*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setBlockCodeccCode(sXrulesVerM.getBlockCodeccCode());		
		xrulesVerM.setBlockCodeccResult(sXrulesVerM.getBlockCodeccResult());
		xrulesVerM.setBlockCodeccUpdateDate(sXrulesVerM.getBlockCodeccUpdateDate());
		xrulesVerM.setBlockCodeccUpdateBy(sXrulesVerM.getBlockCodeccUpdateBy());

		xrulesVerM.setBlockCodeCc(sXrulesVerM.getBlockCodeCc());
		eaiM.setBlockCodeCc(sXrulesVerM.getBlockCodeCc());		
		
		xrulesVerM.setcCCurrentBalance(sXrulesVerM.getcCCurrentBalance());
		xrulesVerM.setcCLastPaymentDate(sXrulesVerM.getcCLastPaymentDate());
		eaiM.setcCCurrentBalance(sXrulesVerM.getcCCurrentBalance());
		eaiM.setcCLastPaymentDate(sXrulesVerM.getcCLastPaymentDate());
		
		xrulesVerM.setCodeCCBlockCode(sXrulesVerM.getCodeCCBlockCode());
		eaiM.setCodeCCBlockCode(sXrulesVerM.getCodeCCBlockCode());		
		xrulesVerM.setEditBlockCode(sXrulesVerM.getEditBlockCode());
		eaiM.setEditBlockCode(sXrulesVerM.getEditBlockCode());
		
		xrulesVerM.setCodeServiceBlockcode(sXrulesVerM.getCodeServiceBlockcode());
		eaiM.setCodeServiceBlockcode(sXrulesVerM.getCodeServiceBlockcode());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBlockCodeccCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBlockCodeccResult()));
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_CARD_BLOCK_CODE);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_CARD_BLOCK_CODE,policyRulesSM);
		}		  
		/*End Set Policy Rules */
 		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getBlockCodeccCode(), sXrulesVerM.getBlockCodeccResult()));  
			 
		return obj;	
	}
	
	public JSONObject MapBlockCodeKEC(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.KEC_BLOCKCODE);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setBlockCodeKecCode("");
			xrulesVerM.setBlockCodeKecResult("");
			xrulesVerM.setBlockCodeKecUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBlockCodeKecUpdateBy(userM.getUserName());	
			eaiM.setxRulesExistCustM(null);
			xrulesVerM.setxRulesExistCustM(null);
			
			eaiM.setKecCurrentBalance(null);
			eaiM.setKecLastPaymentDate(null);
			
			eaiM.setCodeServiceCP0148I01(null);
			xrulesVerM.setCodeServiceCP0148I01(null);
			eaiM.setCodeKecBlockCode(null);
			xrulesVerM.setCodeKecBlockCode(null);
						
			eaiM.setKecCurrentBalance(null);
			eaiM.setKecLastPaymentDate(null);
			
			xrulesVerM.setKecCurrentBalance(null);
			xrulesVerM.setKecLastPaymentDate(null);
			xrulesVerM.setExistCustType(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_KEC_BLOCK_CODE);
			/*End Remove Policy Rules*/
			 
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			 
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setBlockCodeKecCode(sXrulesVerM.getBlockCodeKecCode());		
		xrulesVerM.setBlockCodeKecResult(sXrulesVerM.getBlockCodeKecResult());
		xrulesVerM.setBlockCodeKecUpdateDate(sXrulesVerM.getBlockCodeKecUpdateDate());
		xrulesVerM.setBlockCodeKecUpdateBy(sXrulesVerM.getBlockCodeKecUpdateBy());
		xrulesVerM.setxRulesExistCustM(sXrulesVerM.getxRulesExistCustM());
		eaiM.setxRulesExistCustM(sXrulesVerM.getxRulesExistCustM());
		
		eaiM.setCodeServiceCP0148I01(sXrulesVerM.getCodeServiceCP0148I01());
		xrulesVerM.setCodeServiceCP0148I01(sXrulesVerM.getCodeServiceCP0148I01());
		
		eaiM.setCodeKecBlockCode(sXrulesVerM.getCodeKecBlockCode());
		xrulesVerM.setCodeKecBlockCode(sXrulesVerM.getCodeKecBlockCode());
		
		eaiM.setKecCurrentBalance(sXrulesVerM.getKecCurrentBalance());
		eaiM.setKecLastPaymentDate(sXrulesVerM.getKecLastPaymentDate());
		
		xrulesVerM.setKecCurrentBalance(sXrulesVerM.getKecCurrentBalance());
		xrulesVerM.setKecLastPaymentDate(sXrulesVerM.getKecLastPaymentDate());
		
		xrulesVerM.setExistCustType(sXrulesVerM.getExistCustType());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBlockCodeKecCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBlockCodeKecResult()));

		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_KEC_BLOCK_CODE);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_KEC_BLOCK_CODE,policyRulesSM);
		}		  
		/*End Set Policy Rules */
		
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getBlockCodeKecCode(), sXrulesVerM.getBlockCodeKecResult()));  
		
		return obj;	
	}
	
	public JSONObject MapClassifyLevel(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CLASSIFY_LEVEL_LPM);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setClassifyLevel(null);
			eaiM.setClassifyLevel(null);
			
			xrulesVerM.setEditClassifyLevel(null);
			eaiM.setEditClassifyLevel(null);			
			eaiM.setCodeServiceclassifyLevel(null);
			xrulesVerM.setCodeServiceclassifyLevel(null);
			
			xrulesVerM.setLpmClassifyLvCode("");
			xrulesVerM.setLpmClassifyLvResult("");
			xrulesVerM.setLpmClassifyLvUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setLpmClassifyLvUpdateBy(userM.getUserName());	
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CLASSIFY_LEVEL_LPM);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setLpmClassifyLvCode(sXrulesVerM.getLpmClassifyLvCode());		
		xrulesVerM.setLpmClassifyLvResult(sXrulesVerM.getLpmClassifyLvResult());
		xrulesVerM.setLpmClassifyLvUpdateDate(sXrulesVerM.getLpmClassifyLvUpdateDate());
		xrulesVerM.setLpmClassifyLvUpdateBy(sXrulesVerM.getLpmClassifyLvUpdateBy());
		
		xrulesVerM.setClassifyLevel(sXrulesVerM.getClassifyLevel());
		eaiM.setClassifyLevel(sXrulesVerM.getClassifyLevel());
		
		xrulesVerM.setEditClassifyLevel(sXrulesVerM.getEditClassifyLevel());
		eaiM.setEditClassifyLevel(sXrulesVerM.getEditClassifyLevel());			
		eaiM.setCodeServiceclassifyLevel(sXrulesVerM.getCodeServiceclassifyLevel());
		xrulesVerM.setCodeServiceclassifyLevel(sXrulesVerM.getCodeServiceclassifyLevel());				
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getLpmClassifyLvCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getLpmClassifyLvResult()));
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CLASSIFY_LEVEL_LPM);				 
		if(null != policyRulesSM){
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CLASSIFY_LEVEL_LPM,policyRulesSM);
		}		  
		/*End Set Policy Rules */		
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getLpmClassifyLvCode(), sXrulesVerM.getLpmClassifyLvResult())); 
		
		return obj;
	}
	
	public JSONObject MapSP(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.SP);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setSpResult("");
			xrulesVerM.setSpResultCode("");
			xrulesVerM.setSpUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setSpUpdateBy(userM.getUserName());		
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SP);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setSpResultCode(sXrulesVerM.getSpResultCode());		
		xrulesVerM.setSpResult(sXrulesVerM.getSpResult());
		xrulesVerM.setSpUpdateDate(sXrulesVerM.getSpUpdateDate());
		xrulesVerM.setSpUpdateBy(sXrulesVerM.getSpUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSpResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSpResult()));
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SP);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SP,policyRulesSM);
		}		  
		/*End Set Policy Rules */
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getSpResultCode(), sXrulesVerM.getSpResult())); 
		
		return obj;
	}
	
	public JSONObject MapSancationList(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.SANCTION_LIST);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setSanctionListResult("");
			xrulesVerM.setSanctionListResultCode("");
			xrulesVerM.setSanctionListUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setSanctionListUpdateBy(userM.getUserName());		
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SANCTION_LIST);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setSanctionListResultCode(sXrulesVerM.getSanctionListResultCode());		
		xrulesVerM.setSanctionListResult(sXrulesVerM.getSanctionListResult());
		xrulesVerM.setSanctionListUpdateDate(sXrulesVerM.getSanctionListUpdateDate());
		xrulesVerM.setSanctionListUpdateBy(sXrulesVerM.getSanctionListUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSanctionListResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSanctionListResult()));

		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SANCTION_LIST);				 
		if(null != policyRulesSM){
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SANCTION_LIST,policyRulesSM);
		}		  
		/*End Set Policy Rules */		
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getSanctionListResultCode(), sXrulesVerM.getSanctionListResult()));  
		 
		return obj;
	}
	
	public JSONObject MapBankRuptcy(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){		
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setBankRuptcy(null);
			eaiM.setBankRuptcy(null);
			
			xrulesVerM.setEditBankruptcy(null);
			eaiM.setEditBankruptcy(null);			
			xrulesVerM.setCodeServiceBankRuptcy(null);
			eaiM.setCodeServiceBankRuptcy(null);
			
			xrulesVerM.setBankruptcyResult("");
			xrulesVerM.setBankruptcyCode("");
			xrulesVerM.setBankruptcyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBankruptcyUpdateBy(userM.getUserName());		
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setBankruptcyCode(sXrulesVerM.getBankruptcyCode());		
		xrulesVerM.setBankruptcyResult(sXrulesVerM.getBankruptcyResult());
		xrulesVerM.setBankruptcyUpdateDate(sXrulesVerM.getBankruptcyUpdateDate());
		xrulesVerM.setBankruptcyUpdateBy(sXrulesVerM.getBankruptcyUpdateBy());
		
		xrulesVerM.setBankRuptcy(sXrulesVerM.getBankRuptcy());
		eaiM.setBankRuptcy(sXrulesVerM.getBankRuptcy());
		
		xrulesVerM.setEditBankruptcy(sXrulesVerM.getEditBankruptcy());
		eaiM.setEditBankruptcy(sXrulesVerM.getEditBankruptcy());			
		xrulesVerM.setCodeServiceBankRuptcy(sXrulesVerM.getCodeServiceBankRuptcy());
		eaiM.setCodeServiceBankRuptcy(sXrulesVerM.getCodeServiceBankRuptcy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBankruptcyCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBankruptcyResult()));
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY,policyRulesSM);
		}		  
		/*End Set Policy Rules */
		
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getBankruptcyCode(), sXrulesVerM.getBankruptcyResult()));  
		
		return obj;
	}
	
	public JSONObject MapAmcTamc(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setAmcTamc(null);
			eaiM.setAmcTamc(null);
			
			xrulesVerM.setEditAmcTamc(null);
			eaiM.setEditAmcTamc(null);			
			xrulesVerM.setCodeServiceAmcTamc(null);
			eaiM.setCodeServiceAmcTamc(null);
			
			xrulesVerM.setAmcTamcResult("");
			xrulesVerM.setAmcTamcCode("");
			xrulesVerM.setAmcTamcUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setAmcTamcUpdateBy(userM.getUserName());	
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC);
			 /*End Remove Policy Rules*/		
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setAmcTamcCode(sXrulesVerM.getAmcTamcCode());		
		xrulesVerM.setAmcTamcResult(sXrulesVerM.getAmcTamcResult());
		xrulesVerM.setAmcTamcUpdateDate(sXrulesVerM.getAmcTamcUpdateDate());
		xrulesVerM.setAmcTamcUpdateBy(sXrulesVerM.getAmcTamcUpdateBy());
		
		xrulesVerM.setAmcTamc(sXrulesVerM.getAmcTamc());
		eaiM.setAmcTamc(sXrulesVerM.getAmcTamc());
		
		xrulesVerM.setEditAmcTamc(sXrulesVerM.getEditAmcTamc());
		eaiM.setEditAmcTamc(sXrulesVerM.getEditAmcTamc());			
		xrulesVerM.setCodeServiceAmcTamc(sXrulesVerM.getCodeServiceAmcTamc());
		eaiM.setCodeServiceAmcTamc(sXrulesVerM.getCodeServiceAmcTamc());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getAmcTamcCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getAmcTamcResult()));
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC,policyRulesSM);
		}		  
		/*End Set Policy Rules */	
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getAmcTamcCode(), sXrulesVerM.getAmcTamcResult())); 
		
		return obj;
	}

	public JSONObject MapWathcListFraud(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.WATCHLIST_FRAUD);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setWatchListResult("");
			xrulesVerM.setWatchListCode("");
			xrulesVerM.setWatchListUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setWatchListUpdateBy(userM.getUserName());	
			xrulesVerM.setxRulesFraudDataMs(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WATCH_LIST_FRAUD);
			 /*End Remove Policy Rules*/	
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setWatchListCode(sXrulesVerM.getWatchListCode());		
		xrulesVerM.setWatchListResult(sXrulesVerM.getWatchListResult());
		xrulesVerM.setWatchListUpdateDate(sXrulesVerM.getWatchListUpdateDate());
		xrulesVerM.setWatchListUpdateBy(sXrulesVerM.getWatchListUpdateBy());
		xrulesVerM.setxRulesFraudDataMs(sXrulesVerM.getxRulesFraudDataMs());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getWatchListCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getWatchListResult()));
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WATCH_LIST_FRAUD);				 
		if(null != policyRulesSM){
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WATCH_LIST_FRAUD,policyRulesSM);
		}		  
		 /*End Set Policy Rules */	
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getWatchListCode(), sXrulesVerM.getWatchListResult())); 
		
		return obj;	
	}
	
	public JSONObject MapBehavoiorRiskGrade(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setCodeServiceBehaviorScore(null);
			xrulesVerM.setBehaviorRiskGradeResult("");
			xrulesVerM.setBehaviorRiskGradeCode("");
			xrulesVerM.setBehaviorRiskGradeUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBehaviorRiskGradeUpdateBy(userM.getUserName());	
			xrulesVerM.setxRulesBScoreVect(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BEHAVIOR_RISK_GRADE);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setBehaviorRiskGradeCode(sXrulesVerM.getBehaviorRiskGradeCode());		
		xrulesVerM.setBehaviorRiskGradeResult(sXrulesVerM.getBehaviorRiskGradeResult());
		xrulesVerM.setBehaviorRiskGradeUpdateDate(sXrulesVerM.getBehaviorRiskGradeUpdateDate());
		xrulesVerM.setBehaviorRiskGradeUpdateBy(sXrulesVerM.getBehaviorRiskGradeUpdateBy());
		xrulesVerM.setxRulesBScoreVect(sXrulesVerM.getxRulesBScoreVect());
		
		xrulesVerM.setCodeServiceBehaviorScore(sXrulesVerM.getCodeServiceBehaviorScore());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBehaviorRiskGradeCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBehaviorRiskGradeResult()));
		
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BEHAVIOR_RISK_GRADE);				 
		 if(null != policyRulesSM){
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			 xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BEHAVIOR_RISK_GRADE,policyRulesSM);
		 }		  
		/*End Set Policy Rules */
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getBehaviorRiskGradeCode(), sXrulesVerM.getBehaviorRiskGradeResult()));  
		 
		return obj;	
	}
	
	public JSONObject MapPayRoll(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
				
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.PAY_ROLL);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setPayrollResult("");
			xrulesVerM.setPayrollCode("");
			xrulesVerM.setPayrollUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setPayrollUpdateBy(userM.getUserName());	
			xrulesVerM.setxRulesPayRolls(null);	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setPayrollCode(sXrulesVerM.getPayrollCode());		
		xrulesVerM.setPayrollResult(sXrulesVerM.getPayrollResult());
		xrulesVerM.setPayrollUpdateDate(sXrulesVerM.getPayrollUpdateDate());
		xrulesVerM.setPayrollUpdateBy(sXrulesVerM.getPayrollUpdateBy());
		xrulesVerM.setxRulesPayRolls(sXrulesVerM.getxRulesPayRolls());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getPayrollCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getPayrollResult()));
		
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getPayrollCode(), sXrulesVerM.getPayrollResult()));  
		
		return obj;	
		
	}
	public JSONObject MapMonthProfile(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.MONTH_PROFILE);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);		
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setCodeServiceCP0148I01(null);
			eaiM.setCodeServiceCP0148I01(null);
			
			xrulesVerM.setMonthProfileCode("");
			xrulesVerM.setMonthProfileResult("");
			xrulesVerM.setMonthProfileUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setMonthProfileUpdateBy(userM.getUserName());
			eaiM.setxRulesExistCustM(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_12_MONTH_PROFILE);
			/*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		
		xrulesVerM.setCodeServiceCP0148I01(sXrulesVerM.getCodeServiceCP0148I01());
		eaiM.setCodeServiceCP0148I01(sXrulesVerM.getCodeServiceCP0148I01());
		
		xrulesVerM.setMonthProfileCode(sXrulesVerM.getMonthProfileCode());		
		xrulesVerM.setMonthProfileResult(sXrulesVerM.getMonthProfileResult());
		xrulesVerM.setMonthProfileUpdateDate(sXrulesVerM.getMonthProfileUpdateDate());
		xrulesVerM.setMonthProfileUpdateBy(sXrulesVerM.getMonthProfileUpdateBy());
		eaiM.setxRulesExistCustM(sEaiM.getxRulesExistCustM());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getMonthProfileCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getMonthProfileResult()));

		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_12_MONTH_PROFILE);				 
		if(null != policyRulesSM){
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_12_MONTH_PROFILE,policyRulesSM);
		}		  
		/*End Set Policy Rules */
				 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getMonthProfileCode(), sXrulesVerM.getMonthProfileResult())); 		 
				 
		return obj;
		
	}
	public JSONObject MapKbankEmployee(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){		
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.KBANK_EMPLOYEE);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setkBankEmployeeResult("");
			xrulesVerM.setkBankEmployeeCode("");
			xrulesVerM.setkBankEmployeeUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setkBankEmployeeUpdateBy(userM.getUserName());	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setkBankEmployeeCode(sXrulesVerM.getkBankEmployeeCode());		
		xrulesVerM.setkBankEmployeeResult(sXrulesVerM.getkBankEmployeeResult());
		xrulesVerM.setkBankEmployeeUpdateDate(sXrulesVerM.getkBankEmployeeUpdateDate());
		xrulesVerM.setkBankEmployeeUpdateBy(sXrulesVerM.getkBankEmployeeUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getkBankEmployeeCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getkBankEmployeeResult()));
		
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getkBankEmployeeCode(), sXrulesVerM.getkBankEmployeeResult())); 	
		
		return obj;
	}
	
	public JSONObject MapListedCompany(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.LISTED_COMPANY);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setListedCompanyResult("");
			xrulesVerM.setListedCompanyCode("");
			xrulesVerM.setListedCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setListedCompanyUpdateBy(userM.getUserName());	
			xrulesVerM.setxRulesListedCompanyDataMs(null);
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setListedCompanyCode(sXrulesVerM.getListedCompanyCode());		
		xrulesVerM.setListedCompanyResult(sXrulesVerM.getListedCompanyResult());
		xrulesVerM.setListedCompanyUpdateDate(sXrulesVerM.getListedCompanyUpdateDate());
		xrulesVerM.setListedCompanyUpdateBy(sXrulesVerM.getListedCompanyUpdateBy());
		xrulesVerM.setxRulesListedCompanyDataMs(sXrulesVerM.getxRulesListedCompanyDataMs());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getListedCompanyCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getListedCompanyResult()));
		
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getListedCompanyCode(), sXrulesVerM.getListedCompanyResult())); 	
		
		return obj;		
	}
	
	public JSONObject MapFraudCompany(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.FRAUD_COMPANY);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setFraudCompanyResult(null);
			xrulesVerM.setFraudCompanyCode(null);
			xrulesVerM.setFraudCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setFraudCompanyUpdateBy(userM.getUserName());	
			xrulesVerM.setFraudCompanySearchType(null);
			xrulesVerM.setXrulesFraudCompVect(null);
			xrulesVerM.setFraudCompanyDecision(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FRAUD_COMPANY);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setFraudCompanyCode(sXrulesVerM.getFraudCompanyCode());		
		xrulesVerM.setFraudCompanyResult(sXrulesVerM.getFraudCompanyResult());
		xrulesVerM.setFraudCompanyUpdateDate(sXrulesVerM.getFraudCompanyUpdateDate());
		xrulesVerM.setFraudCompanyUpdateBy(sXrulesVerM.getFraudCompanyUpdateBy());
		xrulesVerM.setXrulesFraudCompVect(sXrulesVerM.getXrulesFraudCompVect());
		xrulesVerM.setFraudCompanyDecision(sXrulesVerM.getFraudCompanyDecision());
		xrulesVerM.setFraudCompanySearchType(sXrulesVerM.getFraudCompanySearchType());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getFraudCompanyCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getFraudCompanyResult()));
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FRAUD_COMPANY);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FRAUD_COMPANY,policyRulesSM);
		}		  
		/*End Set Policy Rules */		
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getFraudCompanyCode(), sXrulesVerM.getFraudCompanyResult()));  
		 
		return obj;		
	}
	
	public JSONObject MapBankruptcyCompany(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setBankRuptcyCompany(null);
			eaiM.setBankRuptcyCompany(null);
			xrulesVerM.setCodeServiceTFB0152I01(null);
			eaiM.setCodeServiceTFB0152I01(null);
			
			xrulesVerM.setBankRuptcyCompanyResult("");
			xrulesVerM.setBankRuptcyCompanyCode("");
			xrulesVerM.setBankRuptcyCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBankRuptcyCompanyUpdateBy(userM.getUserName());	
			xrulesVerM.setBankruptcyCompanyFirstResult("");
			xrulesVerM.setBankruptcyCompanyFirstCode("");
			xrulesVerM.setBankruptcyCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBankruptcyCompanyFirstUpdateBy(userM.getUserName());	
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		
		xrulesVerM.setBankRuptcyCompany(sXrulesVerM.getBankRuptcyCompany());
		eaiM.setBankRuptcyCompany(sXrulesVerM.getBankRuptcyCompany());
		xrulesVerM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());
		eaiM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());		
		
		xrulesVerM.setBankRuptcyCompanyCode(sXrulesVerM.getBankRuptcyCompanyCode());		
		xrulesVerM.setBankRuptcyCompanyResult(sXrulesVerM.getBankRuptcyCompanyResult());
		xrulesVerM.setBankRuptcyCompanyUpdateDate(sXrulesVerM.getBankRuptcyCompanyUpdateDate());
		xrulesVerM.setBankRuptcyCompanyUpdateBy(sXrulesVerM.getBankRuptcyCompanyUpdateBy());
		
		xrulesVerM.setBankruptcyCompanyFirstCode(sXrulesVerM.getBankruptcyCompanyFirstCode());		
		xrulesVerM.setBankruptcyCompanyFirstResult(sXrulesVerM.getBankruptcyCompanyFirstResult());
		xrulesVerM.setBankruptcyCompanyFirstUpdateDate(sXrulesVerM.getBankruptcyCompanyFirstUpdateDate());
		xrulesVerM.setBankruptcyCompanyFirstUpdateBy(sXrulesVerM.getBankruptcyCompanyFirstUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBankRuptcyCompanyCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBankRuptcyCompanyResult()));
		
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY,policyRulesSM);
		}		  
		/*End Set Policy Rules */	
		 
//		 obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getBankRuptcyCompanyCode(), sXrulesVerM.getBankRuptcyCompanyResult()));   
		 
		return obj;			
	}
	
	public JSONObject MapAmcTamcCompany(PLApplicationDataM appM ,PLApplicationDataM appSM , String type,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){	
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setAmcTamcCompany(null);
			xrulesVerM.setCodeServiceTFB0152I01(null);
			
			eaiM.setAmcTamcCompany(null);
			eaiM.setCodeServiceTFB0152I01(null);
			
			xrulesVerM.setAmcTamcCompanyResult("");
			xrulesVerM.setAmcTamcCompanyCode("");
			xrulesVerM.setAmcTamcCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setAmcTamcCompanyUpdateBy(userM.getUserName());	
			xrulesVerM.setAmctamcCompanyFirstResult("");
			xrulesVerM.setAmctamcCompanyFirstCode("");
			xrulesVerM.setAmctamcCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setAmctamcCompanyFirstUpdateBy(userM.getUserName());
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		
		xrulesVerM.setAmcTamcCompany(sXrulesVerM.getAmcTamcCompany());
		xrulesVerM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());
		
		eaiM.setAmcTamcCompany(sXrulesVerM.getAmcTamcCompany());
		eaiM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());
		
		xrulesVerM.setAmcTamcCompanyCode(sXrulesVerM.getAmcTamcCompanyCode());		
		xrulesVerM.setAmcTamcCompanyResult(sXrulesVerM.getAmcTamcCompanyResult());
		xrulesVerM.setAmcTamcCompanyUpdateDate(sXrulesVerM.getAmcTamcCompanyUpdateDate());
		xrulesVerM.setAmcTamcCompanyUpdateBy(sXrulesVerM.getAmcTamcCompanyUpdateBy());
		
		xrulesVerM.setAmctamcCompanyFirstCode(sXrulesVerM.getAmctamcCompanyFirstCode());		
		xrulesVerM.setAmctamcCompanyFirstResult(sXrulesVerM.getAmctamcCompanyFirstResult());
		xrulesVerM.setAmctamcCompanyFirstUpdateDate(sXrulesVerM.getAmctamcCompanyFirstUpdateDate());
		xrulesVerM.setAmctamcCompanyFirstUpdateBy(sXrulesVerM.getAmctamcCompanyFirstUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getAmcTamcCompanyCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getAmcTamcCompanyResult()));
		
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY);				 
		if(null != policyRulesSM){
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY,policyRulesSM);
		}		  
		/*End Set Policy Rules */
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getAmcTamcCompanyCode(), sXrulesVerM.getAmcTamcCompanyResult()));   
		 
		return obj;			

	}
	
	public JSONObject MapNplLpm(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.NPL_LPM);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setNplLpm(null);
			eaiM.setNplLpm(null);
			
			xrulesVerM.setEditNpl(null);
			eaiM.setEditNpl(null);
			xrulesVerM.setCodeServiceNplLpm(null);
			eaiM.setCodeServiceNplLpm(null);
			
			xrulesVerM.setNplLpmResult("");
			xrulesVerM.setNplLpmCode("");
			xrulesVerM.setNplLpmUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setNplLpmUpdateBy(userM.getUserName());	
			eaiM.setxRulesNplLpmM(null);
			xrulesVerM.setxRulesNplLpmM(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_NPL_LPM);
			/*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		
		xrulesVerM.setNplLpm(sXrulesVerM.getNplLpm());
		eaiM.setNplLpm(sXrulesVerM.getNplLpm());		
		
		xrulesVerM.setEditNpl(sXrulesVerM.getEditNpl());
		eaiM.setEditNpl(sXrulesVerM.getEditNpl());
		xrulesVerM.setCodeServiceNplLpm(sXrulesVerM.getCodeServiceNplLpm());
		eaiM.setCodeServiceNplLpm(sXrulesVerM.getCodeServiceNplLpm());		
		
		xrulesVerM.setNplLpmCode(sXrulesVerM.getNplLpmCode());		
		xrulesVerM.setNplLpmResult(sXrulesVerM.getNplLpmResult());
		xrulesVerM.setNplLpmUpdateDate(sXrulesVerM.getNplLpmUpdateDate());
		xrulesVerM.setNplLpmUpdateBy(sXrulesVerM.getNplLpmUpdateBy());
		eaiM.setxRulesNplLpmM(xrulesVerM.getxRulesNplLpmM());
		xrulesVerM.setxRulesNplLpmM(xrulesVerM.getxRulesNplLpmM());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getNplLpmCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getNplLpmResult()));

		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_NPL_LPM);				 
		if(null != policyRulesSM){
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_NPL_LPM,policyRulesSM);
		}		  
		/*End Set Policy Rules */
				 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getNplLpmCode(), sXrulesVerM.getNplLpmResult()));  		 
				 
		return obj;		
	
	}
	
	public JSONObject MapBankruptcyCompanyFirst(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY_FIRST);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setBankRuptcyCompany(null);
			eaiM.setBankRuptcyCompany(null);
			xrulesVerM.setCodeServiceTFB0152I01(null);
			eaiM.setCodeServiceTFB0152I01(null);
						
			xrulesVerM.setBankRuptcyCompanyResult("");
			xrulesVerM.setBankRuptcyCompanyCode("");
			xrulesVerM.setBankRuptcyCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBankRuptcyCompanyUpdateBy(userM.getUserName());	
			xrulesVerM.setBankruptcyCompanyFirstResult("");
			xrulesVerM.setBankruptcyCompanyFirstCode("");
			xrulesVerM.setBankruptcyCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBankruptcyCompanyFirstUpdateBy(userM.getUserName());	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		
		xrulesVerM.setBankRuptcyCompany(sXrulesVerM.getBankRuptcyCompany());
		eaiM.setBankRuptcyCompany(sXrulesVerM.getBankRuptcyCompany());
		xrulesVerM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());
		eaiM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());
		
		xrulesVerM.setBankRuptcyCompanyCode(sXrulesVerM.getBankRuptcyCompanyCode());		
		xrulesVerM.setBankRuptcyCompanyResult(sXrulesVerM.getBankRuptcyCompanyResult());
		xrulesVerM.setBankRuptcyCompanyUpdateDate(sXrulesVerM.getBankRuptcyCompanyUpdateDate());
		xrulesVerM.setBankRuptcyCompanyUpdateBy(sXrulesVerM.getBankRuptcyCompanyUpdateBy());
		
		xrulesVerM.setBankruptcyCompanyFirstCode(sXrulesVerM.getBankruptcyCompanyFirstCode());		
		xrulesVerM.setBankruptcyCompanyFirstResult(sXrulesVerM.getBankruptcyCompanyFirstResult());
		xrulesVerM.setBankruptcyCompanyFirstUpdateDate(sXrulesVerM.getBankruptcyCompanyFirstUpdateDate());
		xrulesVerM.setBankruptcyCompanyFirstUpdateBy(sXrulesVerM.getBankruptcyCompanyFirstUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBankRuptcyCompanyCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBankRuptcyCompanyResult()));
		
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getBankRuptcyCompanyCode(), sXrulesVerM.getBankRuptcyCompanyResult()));  	
		
		return obj;			
	}
	
	public JSONObject MapAmcTamcCompanyFirst(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY_FIRST);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			
			xrulesVerM.setAmcTamcCompany(null);
			xrulesVerM.setCodeServiceTFB0152I01(null);
			
			eaiM.setAmcTamcCompany(null);
			eaiM.setCodeServiceTFB0152I01(null);
			
			xrulesVerM.setAmcTamcCompanyResult("");
			xrulesVerM.setAmcTamcCompanyCode("");
			xrulesVerM.setAmcTamcCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setAmcTamcCompanyUpdateBy(userM.getUserName());				
			xrulesVerM.setAmctamcCompanyFirstResult("");
			xrulesVerM.setAmctamcCompanyFirstCode("");
			xrulesVerM.setAmctamcCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setAmctamcCompanyFirstUpdateBy(userM.getUserName());		
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
				
		xrulesVerM.setAmcTamcCompany(sXrulesVerM.getAmcTamcCompany());
		xrulesVerM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());
		
		eaiM.setAmcTamcCompany(sXrulesVerM.getAmcTamcCompany());
		eaiM.setCodeServiceTFB0152I01(sXrulesVerM.getCodeServiceTFB0152I01());
		
		xrulesVerM.setAmcTamcCompanyCode(sXrulesVerM.getAmcTamcCompanyCode());		
		xrulesVerM.setAmcTamcCompanyResult(sXrulesVerM.getAmcTamcCompanyResult());
		xrulesVerM.setAmcTamcCompanyUpdateDate(sXrulesVerM.getAmcTamcCompanyUpdateDate());
		xrulesVerM.setAmcTamcCompanyUpdateBy(sXrulesVerM.getAmcTamcCompanyUpdateBy());
		
		xrulesVerM.setAmctamcCompanyFirstCode(sXrulesVerM.getAmctamcCompanyFirstCode());		
		xrulesVerM.setAmctamcCompanyFirstResult(sXrulesVerM.getAmctamcCompanyFirstResult());
		xrulesVerM.setAmctamcCompanyFirstUpdateDate(sXrulesVerM.getAmctamcCompanyFirstUpdateDate());
		xrulesVerM.setAmctamcCompanyFirstUpdateBy(sXrulesVerM.getAmctamcCompanyFirstUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getAmcTamcCompanyCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getAmcTamcCompanyResult()));
		
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getAmcTamcCompanyCode(), sXrulesVerM.getAmcTamcCompanyResult()));  
		
		return obj;			

	}	
	
	public JSONObject MapLastRestructureFlag(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.STATUS_RESTRUCTURE_FLAG);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_RESTRUCTURE_LOAN);
			/*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		
		PLXrulesNplLpmDataM sNplLpmM = sEaiM.getxRulesNplLpmM();
		if(null == sNplLpmM){
			sNplLpmM = new PLXrulesNplLpmDataM();
		}
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.replaceNull(sNplLpmM.getStatusRestructureFlag()));
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_RESTRUCTURE_LOAN);				 
		if(null != policyRulesSM){
//			xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_RESTRUCTURE_LOAN,policyRulesSM);
		}		  
		/*End Set Policy Rules */
//		obj.put(Constant.STYLE,"");   
		 
		return obj;		
	
	}
	
	public JSONObject MapLastRestructureDate(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.LAST_RESURUCTURE_DATE_LPM);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//			obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		PLXrulesNplLpmDataM sNplLpmM = sEaiM.getxRulesNplLpmM();
		if(null == sNplLpmM){
			sNplLpmM = new PLXrulesNplLpmDataM();
		}
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,DataFormatUtility.DateEnToStringDateTh(sNplLpmM.getLastRestructureDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
		
//		obj.put(Constant.STYLE,"");  
		
		return obj;		
	
	}
	
	public JSONObject MapCisNo(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CIS_NO);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"-");
			personalM.setCisNo("");
			xrulesVerM.setEditCisNo(null);
			xrulesVerM.setCodeServiceCisNo(null);
//			obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.DisplayReplaceLineWithNull(sPersonalM.getCisNo()));
		
		personalM.setCisNo(sPersonalM.getCisNo());
		xrulesVerM.setEditCisNo(sXrulesVerM.getEditCisNo());
		xrulesVerM.setCodeServiceCisNo(sXrulesVerM.getCodeServiceCisNo());
		
//		obj.put(Constant.STYLE,"");	
		
		return obj;		
	
	}
	
	public JSONObject MapCustomerNo(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CUSTOMER_NO);
		
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"-");
			personalM.setCardLinkCustNo("");
//			obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
			personalM.setCardLinkCustNo(sPersonalM.getCardLinkCustNo());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.DisplayReplaceLineWithNull(sPersonalM.getCardLinkCustNo()));
//		
//		obj.put(Constant.STYLE,"");	
		
		return obj;		
	
	}
	
	public JSONObject MapIncomeDebt(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLXRulesDebtBdDataM debtBDM = xrulesVerM.getXRulesDebtBdDataM();
		if(null == debtBDM){
			debtBDM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(debtBDM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.INCOME_DEBT);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setDEBT_BDResult("");
			xrulesVerM.setDebtBdCode("");
			xrulesVerM.setSummaryIncomeVerCode("");
			xrulesVerM.setSummaryIncomeVerResult("");
			debtBDM.setDebtBurdentScore(null);
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);
			 /*End Remove Policy Rules*/
			 /*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);
			 
			 xrulesVerM.setBot5xResultCode("");
			 xrulesVerM.setBot5xResultDesc("");
			 xrulesVerM.setBot5xUpdateDate(new Timestamp(new java.util.Date().getTime()));
			 xrulesVerM.setBot5xUpdateBy(userM.getUserName());	
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_ROLE_ID_BOT5X);	
			 
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,"");
			return obj;
		}
		
//		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
//		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
//		if(null == sXrulesVerM){
//			sXrulesVerM = new PLXRulesVerificationResultDataM();
//			sPersonalM.setXrulesVerification(sXrulesVerM);
//		}
//		PLXRulesDebtBdDataM sDebtBdM = sXrulesVerM.getXRulesDebtBdDataM();
//		if(null == sDebtBdM){
//			sDebtBdM = new PLXRulesDebtBdDataM();			
//		}
//		xrulesVerM.setDEBT_BDResult(sXrulesVerM.getDEBT_BDResult());
//		xrulesVerM.setDebtBdCode(sXrulesVerM.getDebtBdCode());
//		xrulesVerM.setSummaryIncomeVerCode(sXrulesVerM.getSummaryIncomeVerCode());
//		xrulesVerM.setSummaryIncomeVerResult(sXrulesVerM.getSummaryIncomeVerResult());
//		debtBDM.setDebtBurdentScore(sDebtBdM.getDebtBurdentScore());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,(null == debtBDM.getDebtBurdentScore())?"":debtBDM.getDebtBurdentScore());
		
//		/* Set Policy rules*/
//		PLPersonalInfoDataM personalSM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerSM = personalSM.getXrulesVerification();	
//		PLXRulesPolicyRulesDataM policyRulesSM = xrulesVerSM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);				 
//		 if(null != policyRulesSM){
////			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesSM);
//			 xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME,policyRulesSM);
//		 }		  
//		 PLXRulesPolicyRulesDataM policyRulesDebtSM = xrulesVerSM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);				 
//		 if(null != policyRulesDebtSM){
////			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRulesDebtSM);
//			 xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN,policyRulesDebtSM);
//		 }	
//		 /*End Set Policy Rules */	
		 
//		obj.put(Constant.STYLE, "");  
		
		return obj;
	}
	
	public boolean ValidateSensitiveButton(List<String> sButton ,String buttonId){
		if(null == sButton) sButton = new ArrayList<String>();
		for (String button : sButton) {
			if(null != button && button.equals(buttonId)){
				return true;
			}
		}
		return false;
	}
	
	public void ButtonExecuteLogic(String buttonID , PLApplicationDataM appM ,PLApplicationDataM appSM , String type){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLXRulesDebtBdDataM debtBDM = xrulesVerM.getXRulesDebtBdDataM();
		if(null == debtBDM){
			debtBDM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(debtBDM);
		}
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
			if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
				xrulesVerM.setExecute1Result(null);
			}
			if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(buttonID)){
				xrulesVerM.setExecute2Result(null);
			}	
			return;
		}		
		
		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}		
		
		if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
			xrulesVerM.setExecute1Result(sXrulesVerM.getExecute1Result());
		}
		if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(buttonID)){
			xrulesVerM.setExecute2Result(sXrulesVerM.getExecute2Result());
		}	
		
	}
	
	public JSONObject MapExistingPOD(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){	
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.EXISTING_KEC_POD);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){				
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");				
			xrulesVerM.setExistingKECPODResultCode("");
			xrulesVerM.setExistingKECPODResultDesc("");
			xrulesVerM.setExistingKECPODUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setExistingKECPODUpdateBy(userM.getUserName());
//			obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		
		xrulesVerM.setExistingKECPODResultCode(sXrulesVerM.getExistingKECPODResultCode());		
		xrulesVerM.setExistingKECPODResultDesc(sXrulesVerM.getExistingKECPODResultDesc());
		xrulesVerM.setExistingKECPODUpdateDate(sXrulesVerM.getExistingKECPODUpdateDate());
		xrulesVerM.setExistingKECPODUpdateBy(sXrulesVerM.getExistingKECPODUpdateBy());			
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getExistingKECPODResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getExistingKECPODResultDesc()));
		
//		obj.put(Constant.STYLE,"");  
		
		return obj;
	}
	
	public JSONObject MapBundlingCCAge(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){	
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BUNDLING_CC_AGE);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){				
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");				
			xrulesVerM.setBundlingCCAgeResultCode("");
			xrulesVerM.setBundlingCCAgeResultDesc("");
			xrulesVerM.setBundlingCCAgeUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBundlingCCAgeUpdateBy(userM.getUserName());
			//Remove data from policy rule
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_APPLICANT_AGE);
//			obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		
		xrulesVerM.setBundlingCCAgeResultCode(sXrulesVerM.getBundlingCCAgeResultCode());		
		xrulesVerM.setBundlingCCAgeResultDesc(sXrulesVerM.getBundlingCCAgeResultDesc());
		xrulesVerM.setBundlingCCAgeUpdateDate(sXrulesVerM.getBundlingCCAgeUpdateDate());
		xrulesVerM.setBundlingCCAgeUpdateBy(sXrulesVerM.getBundlingCCAgeUpdateBy());		

		PLXRulesPolicyRulesDataM sPolicyM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_APPLICANT_AGE);
		if(null !=sPolicyM){
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_APPLICANT_AGE, sPolicyM);
		}
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBundlingCCAgeResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBundlingCCAgeResultDesc()));
		
//		obj.put(Constant.STYLE,""); 
		
		return obj;
	}
	
	public JSONObject MapBundlingCCEducation(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){	
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BUNDLING_CC_EDUCATION);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){				
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");				
			xrulesVerM.setBundlingCCEducationResultCode("");
			xrulesVerM.setBundlingCCEducationResultDesc("");
			xrulesVerM.setBundlingCCEducationUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBundlingCCEducationUpdateBy(userM.getUserName());
			//Remove data from policy rule
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_EDUCATION);
//			obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		
		xrulesVerM.setBundlingCCEducationResultCode(sXrulesVerM.getBundlingCCEducationResultCode());		
		xrulesVerM.setBundlingCCEducationResultDesc(sXrulesVerM.getBundlingCCEducationResultDesc());
		xrulesVerM.setBundlingCCEducationUpdateDate(sXrulesVerM.getBundlingCCEducationUpdateDate());
		xrulesVerM.setBundlingCCEducationUpdateBy(sXrulesVerM.getBundlingCCEducationUpdateBy());
		
		PLXRulesPolicyRulesDataM sPolicyM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_EDUCATION);
		if(null != sPolicyM){
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_EDUCATION, sPolicyM);
		}
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBundlingCCEducationResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBundlingCCEducationResultDesc()));
		
//		obj.put(Constant.STYLE,""); 	
		
		return obj;
	}
	
	public JSONObject MapBundlingCCResident(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){	
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BUNDLING_CC_RESIDENT);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){				
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");				
			xrulesVerM.setBundlingCCResidentResultCode("");
			xrulesVerM.setBundlingCCResidentResultDesc("");
			xrulesVerM.setBundlingCCResidentUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBundlingCCResidentUpdateBy(userM.getUserName());
			//Remove data from policy rule
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_RESIDENCE_TYPE);
//			obj.put(Constant.STYLE,"");	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		
		xrulesVerM.setBundlingCCResidentResultCode(sXrulesVerM.getBundlingCCResidentResultCode());		
		xrulesVerM.setBundlingCCResidentResultDesc(sXrulesVerM.getBundlingCCResidentResultDesc());
		xrulesVerM.setBundlingCCResidentUpdateDate(sXrulesVerM.getBundlingCCResidentUpdateDate());
		xrulesVerM.setBundlingCCResidentUpdateBy(sXrulesVerM.getBundlingCCResidentUpdateBy());	
		
		PLXRulesPolicyRulesDataM sPolicyM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_RESIDENCE_TYPE);
		if(null != sPolicyM){
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_RESIDENCE_TYPE, sPolicyM);
		}
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBundlingCCResidentResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getBundlingCCResidentResultDesc()));
		
//		obj.put(Constant.STYLE,""); 	
		
		return obj;
	}
	
	public JSONObject MapSoloView(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.SOLO_VIEW);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setSoloViewResultDesc("");
			xrulesVerM.setSoloViewResultCode("");
			xrulesVerM.setSoloViewUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setSoloViewUpdateBy(userM.getUserName());		
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SOLO_VIEW);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setSoloViewResultCode(sXrulesVerM.getSoloViewResultCode());		
		xrulesVerM.setSoloViewResultDesc(sXrulesVerM.getSoloViewResultDesc());
		xrulesVerM.setSoloViewUpdateDate(sXrulesVerM.getSoloViewUpdateDate());
		xrulesVerM.setSoloViewUpdateBy(sXrulesVerM.getSoloViewUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSoloViewResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getSoloViewResultDesc()));
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SOLO_VIEW);				 
		if(null != policyRulesSM){
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SOLO_VIEW,policyRulesSM);
		}		  
		/*End Set Policy Rules */
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getSoloViewResultCode(), sXrulesVerM.getSoloViewResultDesc())); 
		
		return obj;
	}
	
	public JSONObject MapLec(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		JSONObject obj = new JSONObject();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.LEC);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,type);
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
			xrulesVerM.setLecResultDesc("");
			xrulesVerM.setLecResultCode("");
			xrulesVerM.setLecUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setLecUpdateBy(userM.getUserName());		
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_LEC);
			 /*End Remove Policy Rules*/
//			 obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("",""));	
			return obj;
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setLecResultCode(sXrulesVerM.getLecResultCode());		
		xrulesVerM.setLecResultDesc(sXrulesVerM.getLecResultDesc());
		xrulesVerM.setLecUpdateDate(sXrulesVerM.getLecUpdateDate());
		xrulesVerM.setLecUpdateBy(sXrulesVerM.getLecUpdateBy());
		
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getLecResultCode()));
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,HTMLRenderUtil.displayEmptyString(sXrulesVerM.getLecResultDesc()));
		/* Set Policy rules*/	
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_LEC);				 
		if(null != policyRulesSM){
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_LEC,policyRulesSM);
		}		  
		/*End Set Policy Rules */
		 
//		obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(sXrulesVerM.getLecResultCode(), sXrulesVerM.getLecResultDesc())); 
		
		return obj;
	}
	
	public JSONObject MapBOT5X(PLApplicationDataM appM ,PLApplicationDataM appSM , String type ,UserDetailM userM){
		
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
		if(ORIGXRulesTool.Constant.CLEAR_RULE.equals(type)){
			xrulesVerM.setBot5xResultCode("");
			xrulesVerM.setBot5xResultDesc("");
			xrulesVerM.setBot5xUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBot5xUpdateBy(userM.getUserName());	
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_ROLE_ID_BOT5X);
			return new JSONObject();
		}

		PLPersonalInfoDataM sPersonalM = appSM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();			
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}	
		EAIDataM sEaiM = sXrulesVerM.getEaiM();
		if(null == sEaiM){
			sEaiM = new EAIDataM();
			sXrulesVerM.setEaiM(sEaiM);
		}
		xrulesVerM.setBot5xResultCode(sXrulesVerM.getBot5xResultCode());		
		xrulesVerM.setBot5xResultDesc(sXrulesVerM.getBot5xResultDesc());
		xrulesVerM.setBot5xUpdateDate(sXrulesVerM.getBot5xUpdateDate());
		xrulesVerM.setBot5xUpdateBy(sXrulesVerM.getBot5xUpdateBy());
		
		PLXRulesPolicyRulesDataM policyRulesSM = sXrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_ROLE_ID_BOT5X);				 
		if(null != policyRulesSM){
			xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_ROLE_ID_BOT5X,policyRulesSM);
		}		  
		return new JSONObject();
	}
	
}
