package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPaymentMethodDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.cache.model.SensitiveGroupCacheDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.dao.util.XRulesTool;
import com.eaf.xrules.ulo.pl.model.DemographicDataM;
import com.eaf.xrules.ulo.pl.model.FinalResultDataM;
import com.eaf.xrules.ulo.pl.model.IncomeDebtDataM;
import com.eaf.xrules.ulo.pl.model.ReasonResultM;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesBScoreDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDedupDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFraudCompanyDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFraudDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesListedCompanyDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPayRollDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPayRollDetailDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXrulesNplLpmDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.ExecuteResponseDataM;
//import com.eaf.ulo.pl.eai.util.EAILogic;

public class ORIGXRulesTool{
	static Logger logger = Logger.getLogger(ORIGXRulesTool.class);
	public ORIGXRulesTool(){
		super();
	}
	public class Constant{
		public static final String EXE_CODE = "EXE_CODE";
		public static final String EXE_DESC = "EXE_DESC";
		public static final String RESULT_CODE = "RESULT_CODE";
		public static final String RESULT_DESC = "RESULT_DESC";
		public static final String SERVICE_ID = "SERVICE_ID";
		public static final String FINAL_EXE = "FINAL_EXE";
		public static final String EXECUTE = "EXECUTE";
		public static final String TYPE = "TYPE";
		public static final String BUTTON = "BUTTON";
		public static final String BUTTON_EDIT = "BUTTON_EDIT";
		public static final String DEBT_SCORE = "DEBT_SCORE";
		public static final String CLEAR_RULE = "CLEAR_RULE";
		public static final String CLEAR_RULE_NON_MESSAGE = "CLEAR_RULE_NON_MESSAGE";
		public static final String ROLLBACK_RULE = "ROLLBACK_RULE";
		public static final String SERVICE = "SERVICE";
		public static final String MANUAL = "MANUAL";
		public static final String SENSITIVE_TYPE = "SENSITIVE_TYPE";
		public static final String FIELD01 = "FIELD01";
		public static final String VALUE01 = "VALUE01";
		public static final String FIELD02 = "FIELD02";
		public static final String VALUE02 = "VALUE02";
		public static final String STYLE = "STYLE";
		public static final String SUMMARY_RULE = "SUMMARY_RULE";
	}
	public JSONArray DisplayServiceExecute(XrulesResponseDataM reponse,PLApplicationDataM appM ,HttpServletRequest request){
		logger.debug("[DisplayServiceExecute]");
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}
		if(null == reponse) reponse = new XrulesResponseDataM();
		JSONArray jsonArray = new JSONArray();	
		Vector<ExecuteResponseDataM> executeVect = reponse.getExecuteVect();
		if(null == executeVect) executeVect = new Vector<ExecuteResponseDataM>();
			for(ExecuteResponseDataM executeM : executeVect){
				jsonArray.put(this.MapObjectService(executeM, appM,request));				
			}
//		logger.debug("[DisplayServiceExecute] Obj "+jsonArray.toString());
		return jsonArray;
	}
	
	public void MapServiceExecute(XrulesResponseDataM reponse,PLApplicationDataM appM ,HttpServletRequest request){
		DisplayServiceExecute(reponse, appM, request);
	}
	
	public JSONObject MapObjectService(ExecuteResponseDataM executeM,PLApplicationDataM appM ,HttpServletRequest request){
			switch (executeM.getServiceID()){
				case PLXrulesConstant.ModuleService.SPECIAL_CUSTOMER:	
					return this.MapObjectSpecialCustomer(executeM, appM,request);
				case PLXrulesConstant.ModuleService.DEMOGRAPHIC:
					return this.MapObjectDemoGraphic(executeM, appM,request);
				case PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION:
					return this.MapObjectDeplicateApplication(executeM, appM,request);
				case PLXrulesConstant.ModuleService.EXISTING_KEC:
					return this.MapObjectExistingKEC(executeM, appM,request);
				case PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE:
					return this.MapObjectCCBlockCode(executeM, appM,request);
				case PLXrulesConstant.ModuleService.KEC_BLOCKCODE:
					return this.MapObjectKECBlockCode(executeM, appM,request);
				case PLXrulesConstant.ModuleService.CLASSIFY_LEVEL_LPM:
					return this.MapObjectClassifyLevelLPM(executeM, appM,request);
				case PLXrulesConstant.ModuleService.SP:
					return this.MapObjectSP(executeM, appM,request);
				case PLXrulesConstant.ModuleService.SANCTION_LIST:
					return this.MapObjectSanctionList(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BANKRUPTCY:
					return this.MapObjectBankruptcy(executeM, appM,request);
				case PLXrulesConstant.ModuleService.AMC_TAMC:
					return this.MapObjectAmcTamc(executeM, appM,request);
				case PLXrulesConstant.ModuleService.WATCHLIST_FRAUD:
					return this.MapObjectWatchListFraud(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE:
					return this.MapObjectBehaviorRiskGrade(executeM, appM,request);
				case PLXrulesConstant.ModuleService.PAY_ROLL:
					return this.MapObjectPayRoll(executeM, appM,request);
				case PLXrulesConstant.ModuleService.KBANK_EMPLOYEE:
					return this.MapObjectKbankEmployee(executeM, appM,request);
				case PLXrulesConstant.ModuleService.LISTED_COMPANY:
					return this.MapObjectListedCompany(executeM, appM,request);
				case PLXrulesConstant.ModuleService.FRAUD_COMPANY:
					return this.MapObjectFraudCompanyCompany(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY:
					return this.MapObjectBankruptcyCompany(executeM, appM,request);
				case PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY:	
					return this.MapObjectAmcTamcCompany(executeM, appM,request);
				case PLXrulesConstant.ModuleService.NPL_LPM:
					return this.MapObjectNPL(executeM, appM,request);
				case PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY_FIRST:
					return this.MapObjectAmcTamcCompanyFirst(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY_FIRST:
					return this.MapObjectBankruptcyCompanyFirst(executeM, appM,request);
				case PLXrulesConstant.ModuleService.STATUS_RESTRUCTURE_FLAG:
					return this.MapObjectStatusRestructureFlag(executeM,appM,request);
				case PLXrulesConstant.ModuleService.LAST_RESURUCTURE_DATE_LPM:
					return this.MapObjectLastResuructureDate(executeM,request);
				case PLXrulesConstant.ModuleService.INCOME_DEBT:
					return this.MapObjectIncomeDebt(executeM, appM,request);
				case PLXrulesConstant.ModuleService.MONTH_PROFILE:
					return this.MapObjectMonthProfile(executeM, appM,request);
				case PLXrulesConstant.ModuleService.CIS_NO:
					return this.MapObjectCisNo(executeM, appM,request);
				case PLXrulesConstant.ModuleService.CUSTOMER_NO:
					return this.MapObjectCustomerNo(executeM, appM,request);
				case PLXrulesConstant.ModuleService.EXISTING_KEC_POD:
					return this.MapObjectExistingPOD(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BUNDLING_CC_AGE:
					return this.MapObjectBundlingCCAge(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BUNDLING_CC_EDUCATION:
					return this.MapObjectBundlingCCEducation(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BUNDLING_CC_RESIDENT:
					return this.MapObjectBundlingCCResident(executeM, appM,request);
				case PLXrulesConstant.ModuleService.SOLO_VIEW:
					return this.MapObjectSoloView(executeM, appM,request);
				case PLXrulesConstant.ModuleService.LEC:
					return this.MapObjectLEC(executeM, appM,request);
				case PLXrulesConstant.ModuleService.BOT5X:
					return this.MapObjectBOT5X(executeM, appM,request);
				default:
					break;
			}
		return new JSONObject();
	}
	public JSONObject MapObjectLastResuructureDate(ExecuteResponseDataM executeM,HttpServletRequest request){
		if(null == executeM) executeM = new ExecuteResponseDataM();
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE,"");
//			jObj.put(Constant.RESULT_DESC, DataFormatUtility.DateEnToStringDateTh(executeM.getLastRestructureDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
//			jObj.put(Constant.STYLE,"");
			return jObj;
	}
	public JSONObject MapObjectCisNo(ExecuteResponseDataM executeM, PLApplicationDataM appM,HttpServletRequest request){	
		if(null == executeM) executeM = new ExecuteResponseDataM();
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();		
		EAIDataM eaiM = xrulesVerM.getEaiM();		
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}			
		personM.setCisNo(executeM.getCisNo());				
		eaiM.setCodeServiceCisNo(executeM.getCodeServiceCisNo());
		xrulesVerM.setCodeServiceCisNo(executeM.getCodeServiceCisNo());
		
		if(OrigUtil.isEmptyString(executeM.getCisNo())){
			executeM.setCisNo("-");
		}		
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE,"");
//			jObj.put(Constant.RESULT_DESC,executeM.getCisNo());
//			jObj.put(Constant.STYLE,"");
		return jObj;
	}
	public JSONObject MapObjectCustomerNo(ExecuteResponseDataM executeM, PLApplicationDataM appM,HttpServletRequest request){	
		if(null == executeM) executeM = new ExecuteResponseDataM();
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			personM.setCardLinkCustNo(executeM.getCustomerNo());
			
		if(OrigUtil.isEmptyString(executeM.getCustomerNo())){
			executeM.setCustomerNo("-");
		}		
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE,"");
//			jObj.put(Constant.RESULT_DESC,executeM.getCustomerNo());
//			jObj.put(Constant.STYLE,"");
			return jObj;
	}
	public JSONObject MapObjectStatusRestructureFlag(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){
		if(null == executeM) executeM = new ExecuteResponseDataM();
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//			personM.setCardLinkCustNo(executeM.getCustomerNo());
			
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE,"");
//			jObj.put(Constant.RESULT_DESC,HTMLRenderUtil.replaceNull(executeM.getStatusRestructureFlag()));
//			jObj.put(Constant.STYLE,"");
		return jObj;
	}
	public JSONObject MapObjectAmcTamcCompanyFirst(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			
//			EAILogic eaiLogic = new EAILogic();			
			String executeMsg = executeM.getExecuteDesc();			
//			if(eaiLogic.LogicErrorStatusEAIService(executeMsg)){
//				executeMsg = executeMsg+"_TFB0152I01";
//			}			
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeMsg);
			
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		eaiM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		eaiM.setAmcTamcCompany(executeM.getAmcTamcCompany());
	
		xrulesVerM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		xrulesVerM.setAmcTamcCompany(executeM.getAmcTamcCompany());
	
		xrulesVerM.setAmctamcCompanyFirstCode(executeM.getResultCode());
		xrulesVerM.setAmctamcCompanyFirstResult(executeM.getResultDesc());
		xrulesVerM.setAmctamcCompanyFirstUpdateBy(executeM.getExecuteBy());
		xrulesVerM.setAmctamcCompanyFirstUpdateDate(executeM.getExecuteTime());	
		xrulesVerM.setAmcTamcCompanyCode(executeM.getResultCode());
		xrulesVerM.setAmcTamcCompanyResult(executeM.getResultDesc());
		xrulesVerM.setAmcTamcCompanyUpdateBy(executeM.getExecuteBy());
		xrulesVerM.setAmcTamcCompanyUpdateDate(executeM.getExecuteTime());	
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectBankruptcyCompanyFirst(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
//			EAILogic eaiLogic = new EAILogic();			
			String executeMsg = executeM.getExecuteDesc();			
//			if(eaiLogic.LogicErrorStatusEAIService(executeMsg)){
//				executeMsg = executeMsg+"_TFB0152I01";
//			}			
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeMsg);
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		eaiM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		eaiM.setBankRuptcyCompany(executeM.getBankRuptcyCompany());
		
		xrulesVerM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		xrulesVerM.setBankRuptcyCompany(executeM.getBankRuptcyCompany());
		
			xrulesVerM.setBankruptcyCompanyFirstCode(executeM.getResultCode());
			xrulesVerM.setBankruptcyCompanyFirstResult(executeM.getResultDesc());
			xrulesVerM.setBankruptcyCompanyFirstUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBankruptcyCompanyFirstUpdateDate(executeM.getExecuteTime());	
			xrulesVerM.setBankRuptcyCompanyCode(executeM.getResultCode());
			xrulesVerM.setBankRuptcyCompanyResult(executeM.getResultDesc());
			xrulesVerM.setBankRuptcyCompanyUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBankRuptcyCompanyUpdateDate(executeM.getExecuteTime());
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectAmcTamcCompany(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
//			EAILogic eaiLogic = new EAILogic();			
			String executeMsg = executeM.getExecuteDesc();			
//			if(eaiLogic.LogicErrorStatusEAIService(executeMsg)){
//				executeMsg = executeMsg+"_TFB0152I01";
//			}			
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeMsg);
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		eaiM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		eaiM.setAmcTamcCompany(executeM.getAmcTamcCompany());
		xrulesVerM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		xrulesVerM.setAmcTamcCompany(executeM.getAmcTamcCompany());
		
			xrulesVerM.setAmcTamcCompanyCode(executeM.getResultCode());
			xrulesVerM.setAmcTamcCompanyResult(executeM.getResultDesc());
			xrulesVerM.setAmcTamcCompanyUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setAmcTamcCompanyUpdateDate(executeM.getExecuteTime());		
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getAmcTamcCompanyCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getAmcTamcCompanyResult());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getAmcTamcCompanyUpdateBy());
			/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectBankruptcyCompany(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
//			EAILogic eaiLogic = new EAILogic();			
			String executeMsg = executeM.getExecuteDesc();			
//			if(eaiLogic.LogicErrorStatusEAIService(executeMsg)){
//				executeMsg = executeMsg+"_TFB0152I01";
//			}			
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeMsg);
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		eaiM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		eaiM.setBankRuptcyCompany(executeM.getBankRuptcyCompany());
		
		xrulesVerM.setCodeServiceTFB0152I01(executeM.getCodeServiceTFB0152I01());
		xrulesVerM.setBankRuptcyCompany(executeM.getBankRuptcyCompany());		
		
			xrulesVerM.setBankRuptcyCompanyCode(executeM.getResultCode());
			xrulesVerM.setBankRuptcyCompanyResult(executeM.getResultDesc());
			xrulesVerM.setBankRuptcyCompanyUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBankRuptcyCompanyUpdateDate(executeM.getExecuteTime());	
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getBankRuptcyCompanyCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getBankRuptcyCompanyResult());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getBankRuptcyCompanyUpdateBy());
			/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectFraudCompanyCompany(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		Vector<PLXRulesFraudCompanyDataM> xrulesFraudCompVect = (Vector<PLXRulesFraudCompanyDataM>) executeM.getObj();
		
			xrulesVerM.setFraudCompanyCode(executeM.getResultCode());
			xrulesVerM.setFraudCompanyResult(executeM.getResultDesc());
			xrulesVerM.setFraudCompanyUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setFraudCompanyUpdateDate(executeM.getExecuteTime());	
			xrulesVerM.setFraudCompanySearchType(executeM.getFraudCompanySearchType());
		
		xrulesVerM.setXrulesFraudCompVect(xrulesFraudCompVect);
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FRAUD_COMPANY);
//		 if(plXrulesPolicyRules==null){
//			 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//			 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_FRAUD_COMPANY);
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//		 }
		xrulesPolicyM.setResultCode(xrulesVerM.getFraudCompanyCode());
		xrulesPolicyM.setResultDesc(xrulesVerM.getFraudCompanyResult());			
		xrulesPolicyM.setUpdateBy(xrulesVerM.getFraudCompanyUpdateBy());
		/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.FIELD01, "fraud-comp-searchtype");			
//			jObj.put(Constant.VALUE01,executeM.getFraudCompanySearchType());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectListedCompany(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		Vector<PLXRulesListedCompanyDataM> listedCompanyVect =  (Vector<PLXRulesListedCompanyDataM>) executeM.getObj();		
		
			xrulesVerM.setListedCompanyCode(executeM.getResultCode());
			xrulesVerM.setListedCompanyResult(executeM.getResultDesc());
			xrulesVerM.setListedCompanyUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setListedCompanyUpdateDate(executeM.getExecuteTime());	
			
		xrulesVerM.setxRulesListedCompanyDataMs(listedCompanyVect);
		
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectKbankEmployee(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setkBankEmployeeCode(executeM.getResultCode());
			xrulesVerM.setkBankEmployeeResult(executeM.getResultDesc());
			xrulesVerM.setkBankEmployeeUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setkBankEmployeeUpdateDate(executeM.getExecuteTime());			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectPayRoll(ExecuteResponseDataM executeM,PLApplicationDataM applicationM,HttpServletRequest request){		
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
					
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		PLXRulesPayRollDetailDataM detailM = null;
		PLXRulesPayRollDataM payRollM =  (PLXRulesPayRollDataM) executeM.getObj();	
		
		BigDecimal salary = BigDecimal.ZERO;
		
		if(null != payRollM){
			Vector<PLXRulesPayRollDetailDataM> payRollVect = payRollM.getxRulesPayRollDetailVect();
			detailM = new PLXRulesPayRollDetailDataM();
			if(!XRulesTool.isEmptyVector(payRollVect)){
				detailM = (PLXRulesPayRollDetailDataM) payRollVect.lastElement();
			}
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personM.setXrulesVerification(xrulesVerM);
				
			}
			PLXRulesDebtBdDataM xRuleDebt = xrulesVerM.getXRulesDebtBdDataM();
			if(null == xRuleDebt){
				 xRuleDebt = new PLXRulesDebtBdDataM();
				 xrulesVerM.setXRulesDebtBdDataM(xRuleDebt);
			}
			salary = xRuleDebt.getSalary();
			if(null == salary || salary.compareTo(BigDecimal.ZERO) == 0){
				if(null == salary) salary = BigDecimal.ZERO;
				if(OrigConstant.ApplicantIncomeType.SAVEING_INCOME.equals(personM.getApplicationIncomeType())){
					if(null != personM.getSavingIncome()){
						salary = salary.add(personM.getSavingIncome().multiply(new BigDecimal(0.05)));
					}
				}
				if(null != detailM.getGrossIncome()){
					salary = salary.add(detailM.getGrossIncome());
				}
			}
			if(null == salary){
				salary = BigDecimal.ZERO;
			}
			xRuleDebt.setSalary(salary);		
			BigDecimal loanIncome  =  xRuleDebt.getLoanIncome();
			if(null == loanIncome){
				loanIncome = BigDecimal.ZERO;
			}
			BigDecimal otherIncome =  xRuleDebt.getOtherIncome();
			if(null == otherIncome){
				otherIncome = BigDecimal.ZERO;
			}
			BigDecimal totalIncome = BigDecimal.ZERO;		
			totalIncome = totalIncome.add(salary).add(loanIncome).add(otherIncome);
			xRuleDebt.setTotalIncome(totalIncome);
		}		

		xrulesVerM.setPayrollCode(executeM.getResultCode());
		xrulesVerM.setPayrollResult(executeM.getResultDesc());
		xrulesVerM.setPayrollUpdateBy(executeM.getExecuteBy());
		xrulesVerM.setPayrollUpdateDate(executeM.getExecuteTime());	
		
		xrulesVerM.setxRulesPayRolls(payRollM);
		
		JSONObject json = new JSONObject();
//			json.put(Constant.SERVICE_ID, executeM.getServiceID());
//			json.put(Constant.TYPE, Constant.EXECUTE);
//			json.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			json.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			json.put(Constant.RESULT_CODE, executeM.getResultCode());
//			json.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			json.put(Constant.FIELD01,  "salary");
			try{			
				json.put(Constant.VALUE01, DataFormatUtility.displayCommaNumber(salary));
			}catch (Exception e) {
				logger.fatal("Exception ",e);
//				json.put(Constant.VALUE01,"0.00");
			}			
//			json.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		
		if(null != detailM
			&& !OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())
				&& !OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){		
			if("PASS(FOUND)".equals(executeM.getResultDesc())){
				if(OrigConstant.PAYROLL.equals(applicationM.getProductFeature())
						|| OrigConstant.PRE_SCREEN.equals(applicationM.getProductFeature())){
					if(!OrigUtil.isEmptyString(detailM.getProjectCode1())
							&& !detailM.getProjectCode1().equals(applicationM.getProductCode())){
						
						ORIGCacheUtil origc = new ORIGCacheUtil();
						ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();	
						String product_feature = origc.getSystemIDFromListbox(applicationM.getProductFeature(), "35", "2");
												
						PLProjectCodeDataM projectCodeM  = origBean.Loadtable(detailM.getProjectCode1(),product_feature
																		,applicationM.getBusinessClassId(),applicationM.getExceptionProject());
						JsonObjectUtil util = new JsonObjectUtil();
						if(null != projectCodeM && !OrigUtil.isEmptyString(projectCodeM.getProjectcode())){
							applicationM.setProjectCode(projectCodeM.getProjectcode());							
							util.CreateJsonObject("startdate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getStartprojectdate())));
							util.CreateJsonObject("div_startdate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getStartprojectdate())));
							util.CreateJsonObject("enddate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getEndprojectdate())));
							util.CreateJsonObject("div_enddate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getEndprojectdate())));
							util.CreateJsonObject("details", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProjectdesc()));
							util.CreateJsonObject("div_details", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProjectdesc()));
							util.CreateJsonObject("promotion", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getPromotion()));
							util.CreateJsonObject("div_promotion", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getPromotion()));
							util.CreateJsonObject("approvedate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getApprovedate())));
							util.CreateJsonObject("div_approvedate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getApprovedate())));
							util.CreateJsonObject("customergroup", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getCustomertype()));
							util.CreateJsonObject("div_customergroup", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getCustomertype()));
							util.CreateJsonObject("projectCode", applicationM.getProjectCode());
							util.CreateJsonObject("project_code", applicationM.getProjectCode());					
							util.CreateJsonObject("professionCode", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProfessionCode()));
							util.CreateJsonObject("Priority", HTMLRenderUtil.displayHTML(projectCodeM.getPriority()));
							util.CreateJsonObject("application_property", origc.getORIGCacheDisplayNameDataM(110, projectCodeM.getApplicationProperty()));							
							util.CreateJsonObject("div_projectcode", HTMLRenderUtil.DisplayPopUpProjectCode(applicationM.getProjectCode(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"13","project_code","textbox-projectcode","13","...","button-popup"));
							util.CreateJsonObject("projectcode_displaymode", HTMLRenderUtil.DISPLAY_MODE_VIEW);
							util.CreateJsonObject("projectcode_buttonmode", HTMLRenderUtil.DISPLAY_MODE_EDIT);	
							
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
							
							if(OrigUtil.isEmptyString(xrulesVerM.getVerCusResultCode())){
								util.CreateJsonObject("result_1032", "");
								util.CreateJsonObject("code_1032", "");
							}
							if(OrigUtil.isEmptyString(xrulesVerM.getVerHRResultCode())){
								util.CreateJsonObject("result_1033", "");
								util.CreateJsonObject("code_1033", "");
							}
							
						}
						
						PLPaymentMethodDataM  paymentM = applicationM.getPaymentMethod();						
						if(null == paymentM){
							paymentM = new PLPaymentMethodDataM();
							applicationM.setPaymentMethod(paymentM);
						}
						if(OrigUtil.isEmptyString(paymentM.getDueCycle())){
							paymentM.setDueCycle(detailM.getCycleCut());
							util.CreateJsonObject("payment_method_dueCycle", HTMLRenderUtil.replaceNull(detailM.getCycleCut()));
						}						
//						json.put("OBJECT", util.returnJson());
					}
				}
			}
		}
	
						
		return json;
	}
	public JSONObject MapObjectBehaviorRiskGrade(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		Vector<PLXRulesBScoreDataM> xrulesBScoreVect = (Vector<PLXRulesBScoreDataM>) executeM.getObj();
		
			xrulesVerM.setBehaviorRiskGradeCode(executeM.getResultCode());
			xrulesVerM.setBehaviorRiskGradeResult(executeM.getResultDesc());
			xrulesVerM.setBehaviorRiskGradeUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBehaviorRiskGradeUpdateDate(executeM.getExecuteTime());	
			xrulesVerM.setBehavRiskGradeDecision(executeM.getBehaviorResult());
			xrulesVerM.setCodeServiceBehaviorScore(executeM.getCodeServiceBehaviorScore());
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BEHAVIOR_RISK_GRADE);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_BEHAVIOR_RISK_GRADE);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			 xrulesPolicyM.setResultCode(xrulesVerM.getBehaviorRiskGradeCode());
			 xrulesPolicyM.setResultDesc(xrulesVerM.getBehaviorRiskGradeResult());
			 xrulesPolicyM.setUpdateBy(xrulesVerM.getBehaviorRiskGradeUpdateBy());
			/*End Set Policy Rules */
		xrulesVerM.setxRulesBScoreVect(xrulesBScoreVect);	
		
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectWatchListFraud(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setWatchListCode(executeM.getResultCode());
			xrulesVerM.setWatchListResult(executeM.getResultDesc());
			xrulesVerM.setWatchListUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setWatchListUpdateDate(executeM.getExecuteTime());
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WATCH_LIST_FRAUD);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_WATCH_LIST_FRAUD);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getWatchListCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getWatchListResult());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getWatchListUpdateBy());
			/*End Set Policy Rules */
		PLXRulesFraudDataM fraudM = (PLXRulesFraudDataM) executeM.getObj();	
			xrulesVerM.setxRulesFraudDataMs(fraudM);
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectAmcTamc(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setAmcTamcCode(executeM.getResultCode());
			xrulesVerM.setAmcTamcResult(executeM.getResultDesc());
			xrulesVerM.setAmcTamcUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setAmcTamcUpdateDate(executeM.getExecuteTime());	
			xrulesVerM.setAmcTamc(executeM.getAmcTamc());
			xrulesVerM.setCodeServiceAmcTamc(executeM.getCodeServiceAmcTamc());
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_AMC_TAMC);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			 xrulesPolicyM.setResultCode(xrulesVerM.getAmcTamcCode());
			 xrulesPolicyM.setResultDesc(xrulesVerM.getAmcTamcResult());	
			 xrulesPolicyM.setUpdateBy(xrulesVerM.getAmcTamcUpdateBy());
			/*End Set Policy Rules */
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectNPL(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();		
		
		logger.debug("executeM.getExecuteCode >> "+executeM.getExecuteCode());
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		PLXrulesNplLpmDataM nplLpmM = (PLXrulesNplLpmDataM) executeM.getObj();
			if(null == nplLpmM) nplLpmM = new PLXrulesNplLpmDataM();
		EAIDataM eaiM = xrulesVerM.getEaiM();		
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
			
		eaiM.setCodeServiceNplLpm(executeM.getCodeServiceNplLpm());
		eaiM.setNplLpm(executeM.getNplLpm());
		
		xrulesVerM.setCodeServiceNplLpm(executeM.getCodeServiceNplLpm());
		xrulesVerM.setNplLpm(executeM.getNplLpm());
		
			xrulesVerM.setNplLpmCode(executeM.getResultCode());
			xrulesVerM.setNplLpmResult(executeM.getResultDesc());
			xrulesVerM.setNplLpmUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setNplLpmUpdateDate(executeM.getExecuteTime());	
			
		eaiM.setxRulesNplLpmM(nplLpmM);
		xrulesVerM.setxRulesNplLpmM(nplLpmM);
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_NPL_LPM);
//		 if(plXrulesPolicyRules==null){
//			 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//			 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_NPL_LPM);
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//		 }
		xrulesPolicyM.setResultCode(xrulesVerM.getNplLpmCode());
		xrulesPolicyM.setResultDesc(xrulesVerM.getNplLpmResult());		
		xrulesPolicyM.setUpdateBy(xrulesVerM.getNplLpmUpdateBy());
		/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectBankruptcy(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		eaiM.setCodeServiceBankRuptcy(executeM.getCodeServiceBankRuptcy());
		eaiM.setBankRuptcy(executeM.getBankRuptcy());
		
		xrulesVerM.setCodeServiceBankRuptcy(executeM.getCodeServiceBankRuptcy());
		xrulesVerM.setBankRuptcy(executeM.getBankRuptcy());
		
			xrulesVerM.setBankruptcyCode(executeM.getResultCode());
			xrulesVerM.setBankruptcyResult(executeM.getResultDesc());
			xrulesVerM.setBankruptcyUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBankruptcyUpdateDate(executeM.getExecuteTime());		
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_BANKRUPTCY);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getBankruptcyCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getBankruptcyResult());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getBankruptcyUpdateBy());
			/*End Set Policy Rules */	
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectSanctionList(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setSanctionListResultCode(executeM.getResultCode());
			xrulesVerM.setSanctionListResult(executeM.getResultDesc());
			xrulesVerM.setSanctionListUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setSanctionListUpdateDate(executeM.getExecuteTime());	
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SANCTION_LIST);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_SANCTION_LIST);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			 xrulesPolicyM.setResultCode(xrulesVerM.getSanctionListResultCode());
			 xrulesPolicyM.setResultDesc(xrulesVerM.getSanctionListResult());	
			 xrulesPolicyM.setUpdateBy(xrulesVerM.getSanctionListUpdateBy());
			/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectSP(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setSpResultCode(executeM.getResultCode());
			xrulesVerM.setSpResult(executeM.getResultDesc());
			xrulesVerM.setSpUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setSpUpdateDate(executeM.getExecuteTime());	
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM =xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SP);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_SP);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getSpResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getSpResult());		
			xrulesPolicyM.setUpdateBy(xrulesVerM.getSpUpdateBy());
			/*End Set Policy Rules */ 
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectClassifyLevelLPM(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		eaiM.setCodeServiceclassifyLevel(executeM.getCodeServiceclassifyLevel());
		eaiM.setClassifyLevel(executeM.getClassifyLevel());
		
		xrulesVerM.setCodeServiceclassifyLevel(executeM.getCodeServiceclassifyLevel());
		xrulesVerM.setClassifyLevel(executeM.getClassifyLevel());		
	
			xrulesVerM.setLpmClassifyLvCode(executeM.getResultCode());
			xrulesVerM.setLpmClassifyLvResult(executeM.getResultDesc());
			xrulesVerM.setLpmClassifyLvUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setLpmClassifyLvUpdateDate(executeM.getExecuteTime());	
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CLASSIFY_LEVEL_LPM);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_CLASSIFY_LEVEL_LPM);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getLpmClassifyLvCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getLpmClassifyLvResult());		
			xrulesPolicyM.setUpdateBy(xrulesVerM.getLpmClassifyLvUpdateBy());
			/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectKECBlockCode(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();			
		
		PLXRulesExistCustDataM existCustM  = (PLXRulesExistCustDataM) executeM.getObj();		
			eaiM.setxRulesExistCustM(existCustM);
			xrulesVerM.setxRulesExistCustM(existCustM);
					
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
//			EAILogic eaiLogic = new EAILogic();			
			String executeMsg = executeM.getExecuteDesc();			
//			if(eaiLogic.LogicErrorStatusEAIService(executeMsg)){
//				executeMsg = executeMsg+"_CP0148I01";
//			}			
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeMsg);
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			eaiM.setCodeServiceCP0148I01(executeM.getCodeServiceCP0148I01());
			eaiM.setCodeKecBlockCode(executeM.getCodeKecBlockCode());			
		
			xrulesVerM.setCodeServiceCP0148I01(executeM.getCodeServiceCP0148I01());
			xrulesVerM.setCodeKecBlockCode(executeM.getCodeKecBlockCode());	
						
			xrulesVerM.setBlockCodeKecCode(executeM.getResultCode());
			xrulesVerM.setBlockCodeKecResult(executeM.getResultDesc());
			
			xrulesVerM.setBlockCodeKecUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBlockCodeKecUpdateDate(executeM.getExecuteTime());	
			
			xrulesVerM.setExistCustType(executeM.getExistCustType());
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_KEC_BLOCK_CODE);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_KEC_BLOCK_CODE);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getBlockCodeKecCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getBlockCodeKecResult());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getBlockCodeccUpdateBy());
			/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectCCBlockCode(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
						
		eaiM.setCodeServiceBlockcode(executeM.getCodeServiceBlockcode());
		eaiM.setCodeCCBlockCode(executeM.getCodeCCBlockCode());
		eaiM.setBlockCodeCc(executeM.getBlockCodeCc());
		
		xrulesVerM.setCodeServiceBlockcode(executeM.getCodeServiceBlockcode());
		xrulesVerM.setCodeCCBlockCode(executeM.getCodeCCBlockCode());
		xrulesVerM.setBlockCodeCc(executeM.getBlockCodeCc());
		xrulesVerM.setBlockCodeDatecc(executeM.getBlockCodeDatecc());
	
		xrulesVerM.setBlockCodeccCode(executeM.getResultCode());
		xrulesVerM.setBlockCodeccResult(executeM.getResultDesc());
		xrulesVerM.setBlockCodeccUpdateBy(executeM.getExecuteBy());
		xrulesVerM.setBlockCodeccUpdateDate(executeM.getExecuteTime());	
		
		
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_CARD_BLOCK_CODE);
//		 if(plXrulesPolicyRules==null){
//			 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//			 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_CREDIT_CARD_BLOCK_CODE);
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//		 }
		xrulesPolicyM.setResultCode(xrulesVerM.getBlockCodeccCode());
		xrulesPolicyM.setResultDesc(xrulesVerM.getBlockCodeccResult());	
		xrulesPolicyM.setUpdateBy(xrulesVerM.getBlockCodeccUpdateBy());
		/*End Set Policy Rules */
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectExistingKEC(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();		
		PLXRulesExistCustDataM existCustM  = (PLXRulesExistCustDataM) executeM.getObj();		
		
			if(null == existCustM) existCustM  = new PLXRulesExistCustDataM();
			
			eaiM.setxRulesExistCustM(existCustM);
			xrulesVerM.setxRulesExistCustM(existCustM);
					
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
//			EAILogic eaiLogic = new EAILogic();			
			String executeMsg = executeM.getExecuteDesc();			
//			if(eaiLogic.LogicErrorStatusEAIService(executeMsg)){
//				executeMsg = executeMsg+"_CP0148I01";
//			}			
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeMsg);
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		eaiM.setCodeServiceCP0148I01(executeM.getCodeServiceCP0148I01());
		xrulesVerM.setCodeServiceCP0148I01(executeM.getCodeServiceCP0148I01());		

			xrulesVerM.setExistCustCode(executeM.getResultCode());
			xrulesVerM.setExistCustResult(executeM.getResultDesc());
			xrulesVerM.setExistCustUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setExistingCustUpdateDate(executeM.getExecuteTime());
			
			if(OrigConstant.BusClass.FCP_KEC_HL.equals(appM.getBusinessClassId())
					||OrigConstant.BusClass.FCP_KEC_SME.equals(appM.getBusinessClassId())
						|| OrigConstant.BusClass.FCP_KEC_AL.equals(appM.getBusinessClassId())){
				appM.setICDCFlag(executeM.getIcdcFlag());
			}
		
		PLCardDataM cardM = personM.getCardInformation();
		
		if(null == cardM){
			cardM = new PLCardDataM();
			personM.setCardInformation(cardM);
		}

//		comment by CR18		
//		if(null != existCustM.getLastTemporaryCreditLimitAmount()){
//			cardM.setCurCreditLine(existCustM.getCreditLimit());
//		}
		cardM.setCurCreditLine(existCustM.getCreditLimit());
		
		/**#SeptemWi Set KEC Current Balance*/
		xrulesVerM.setKecCurrentBalance(existCustM.getCurrentBalance());
		
		eaiM.setCodeKecBlockCode(executeM.getCodeKecBlockCode());
		xrulesVerM.setCodeKecBlockCode(executeM.getCodeKecBlockCode());
		
		xrulesVerM.setExistCustType(executeM.getExistCustType());
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC);
//		 if(plXrulesPolicyRules==null){
//			 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//			 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC);
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//		 }
		xrulesPolicyM.setResultCode(xrulesVerM.getExistCustCode());
		xrulesPolicyM.setResultDesc(xrulesVerM.getExistCustResult());				 
		/*End Set Policy Rules */		
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.FIELD01, "card_info_current_credit");		
			try{					
				jObj.put(Constant.VALUE01, DataFormatUtility.displayCommaNumber(cardM.getCurCreditLine()));				
			}catch (Exception e) {
				logger.fatal("Exception ",e);
//				jObj.put(Constant.VALUE01,"0.00");
			}
//			jObj.put(Constant.FIELD02, "msg_notequal_cardstatus");	
//			jObj.put(Constant.VALUE02, executeM.getNotEqualStausCard());	
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectDeplicateApplication(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		Vector<PLXRulesDedupDataM> dupVect = (Vector<PLXRulesDedupDataM>) executeM.getObj();	
		 	xrulesVerM.setVXRulesDedupDataM(dupVect);
			xrulesVerM.setDedupCode(executeM.getResultCode());
			xrulesVerM.setDedupResult(executeM.getResultDesc());
			xrulesVerM.setDedupUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setDedupUpdateDate(executeM.getExecuteTime());
			xrulesVerM.setDupInProcessFlag(executeM.getDupInProcessFlag());
			xrulesVerM.setDupAscendancyFlag(executeM.getDupAscendancyFlag());
			xrulesVerM.setDupRejectCancleFlag(executeM.getDupRejectCancleFlag());
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_EXISTING_APPLICATION);
			
			xrulesPolicyM.setResultCode(xrulesVerM.getDedupCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getDedupResult());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getDedupUpdateBy());
			/*End Set Policy Rules */
			 
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectDemoGraphic(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		DemographicDataM demoGraphicM = (DemographicDataM)executeM.getObj();		
		if(null == demoGraphicM) demoGraphicM = new DemographicDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
			demoGraphicM.setDemoGraphicDesc(msg);
		}
	
		xrulesVerM.setDemographicCode(demoGraphicM.getDemoGraphicCode());
		xrulesVerM.setDemographicResult(demoGraphicM.getDemoGraphicDesc());
		xrulesVerM.setDemographicUpdateBy(executeM.getExecuteBy());
		xrulesVerM.setDemographicUpdateDate(executeM.getExecuteTime());		
		
		xrulesVerM.setAgeVerCode(demoGraphicM.getAgeCode());
		xrulesVerM.setAgeVerResult(demoGraphicM.getAgeDesc());
		
		xrulesVerM.setNationalityVerCode(demoGraphicM.getNationalityCode());
		xrulesVerM.setNationalityVerResult(demoGraphicM.getNationalityDesc());
		
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_NATIONALITY);
//		 if(plXrulesPolicyRules==null){
//			 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//			 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_APPLICANT_NATIONALITY);
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//		 }
		 xrulesPolicyM.setResultCode(xrulesVerM.getNationalityVerCode());
		 xrulesPolicyM.setResultDesc(xrulesVerM.getNationalityVerResult());				 
		/*End Set Policy Rules */
		 /* Set Policy rules*/
		PLXRulesPolicyRulesDataM xrulesPolicyM2 = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_AGE);
//		 if(plXrulesPolicyRules2==null){
//			 plXrulesPolicyRules2=new PLXRulesPolicyRulesDataM();
//			 plXrulesPolicyRules2.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_APPLICANT_AGE);
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules2);
//		 }
		xrulesPolicyM2.setResultCode(xrulesVerM.getAgeVerCode());
		xrulesPolicyM2.setResultDesc(xrulesVerM.getAgeVerResult());				 
		/*End Set Policy Rules */ 
		
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, demoGraphicM.getDemoGraphicCode());
//			jObj.put(Constant.RESULT_DESC, demoGraphicM.getDemoGraphicDesc());
//			jObj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(demoGraphicM.getDemoGraphicCode(), demoGraphicM.getDemoGraphicDesc()));
		return jObj;
	}
	public JSONObject MapObjectSpecialCustomer(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setSpecialCusCode(executeM.getResultCode());
			xrulesVerM.setSpecialCusResult(executeM.getResultDesc());
			xrulesVerM.setSpecialCusUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setSpecialCusUpdateDate(executeM.getExecuteTime());										
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	
	public JSONObject MapObjectIncomeDebt(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
		IncomeDebtDataM incomeDebtM  = (IncomeDebtDataM) executeM.getObj();
		if(null == incomeDebtM) incomeDebtM = new IncomeDebtDataM();
		
		PLXRulesDebtBdDataM xrulesDebtM = xrulesVerM.getXRulesDebtBdDataM();
		
		if(null == xrulesDebtM){
			xrulesDebtM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(xrulesDebtM);
		}
		xrulesDebtM.setDebtBurdentScore(incomeDebtM.getDebtBurdentScore());
		
		xrulesVerM.setDEBT_BDResult(incomeDebtM.getDebtBurdenResult());
		xrulesVerM.setDebtBdCode(incomeDebtM.getDebtBurdenCode());
		xrulesVerM.setDebtBdUpdateBy(executeM.getExecuteBy());
		xrulesVerM.setDebtBdUpdateDate(executeM.getExecuteTime());	
		xrulesVerM.setSummaryIncomeVerCode(incomeDebtM.getTotalIncomeCode());
		xrulesVerM.setSummaryIncomeVerResult(incomeDebtM.getTotalIncomeResult());
		/* Set Policy rules*/
		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);
//		 if(plXrulesPolicyRules==null){
//			 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//			 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);
//			 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//		 }
		xrulesPolicyM.setResultCode(xrulesVerM.getSummaryIncomeVerCode());
		xrulesPolicyM.setResultDesc(xrulesVerM.getSummaryIncomeVerResult());
		xrulesPolicyM.setUpdateBy(xrulesVerM.getSummaryOverideRuleUpdateBy());
		/*End Set Policy Rules */
		
		 /* Set Policy rules*/
			PLXRulesPolicyRulesDataM plXrulesPolicyRules2=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);
			 if(plXrulesPolicyRules2==null){
				 plXrulesPolicyRules2=new PLXRulesPolicyRulesDataM();
				 plXrulesPolicyRules2.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);
				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules2);
			 }
			 plXrulesPolicyRules2.setResultCode(xrulesVerM.getDebtBdCode());
			 plXrulesPolicyRules2.setResultDesc(xrulesVerM.getDEBT_BDResult());			
			 plXrulesPolicyRules2.setUpdateBy(xrulesVerM.getDebtBdUpdateBy());
			/*End Set Policy Rules */
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, DataFormatUtility.DisplayNumber(incomeDebtM.getDebtBurdentScore(),"##0.00",true));
//			jObj.put(Constant.STYLE,"");
		return jObj;
	}
	
	public JSONObject MapObjectMonthProfile(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){
		if(null == appM) appM = new PLApplicationDataM();
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
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		PLXRulesExistCustDataM existCustM  = (PLXRulesExistCustDataM) executeM.getObj();		
			eaiM.setxRulesExistCustM(existCustM);
			xrulesVerM.setxRulesExistCustM(existCustM);
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
//			EAILogic eaiLogic = new EAILogic();			
			String executeMsg = executeM.getExecuteDesc();			
//			if(eaiLogic.LogicErrorStatusEAIService(executeMsg)){
//				executeMsg = executeMsg+"_CP0148I01";
//			}			
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeMsg);
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
				
			xrulesVerM.setMonthProfileCode(executeM.getResultCode());
			xrulesVerM.setMonthProfileResult(executeM.getResultDesc());
			xrulesVerM.setMonthProfileUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setMonthProfileUpdateDate(executeM.getExecuteTime());	
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_12_MONTH_PROFILE);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_12_MONTH_PROFILE);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(executeM.getResultCode());
			xrulesPolicyM.setResultDesc(executeM.getResultDesc());			  
			/*End Set Policy Rules */
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.FIELD02, "msg_notequal_cardstatus");	
//			jObj.put(Constant.VALUE02, executeM.getNotEqualStausCard());	
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	
	public void ButtonEditStyleEngine(JSONArray jArray ,String buttonID,PLApplicationDataM applicationM ,HttpServletRequest request){
		if(OrigUtil.isEmptyString(buttonID))
			return;
		if(OrigUtil.isEmptyString(buttonID))
			return;
		if(null == jArray) jArray = new JSONArray();
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM 	= personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		JSONObject jObject = null;
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();
		String buttonStyle = xrulesTool.ButtonEditStyleEngie(applicationM);
		jObject = new JSONObject();
//		jObject.put(Constant.TYPE, Constant.BUTTON_EDIT);
//		jObject.put("id", buttonID);
//		jObject.put("value", buttonStyle);
		jArray.put(jObject);
	}
	public void ButtonStyleEngine(String buttonID,PLApplicationDataM appM ,HttpServletRequest request){
		ButtonStyleEngine(null, buttonID, appM, request);
	}
	public void ButtonStyleEngine(JSONArray jArray ,String buttonID,PLApplicationDataM applicationM ,HttpServletRequest request){
		if(OrigUtil.isEmptyString(buttonID))
			return;
		if(null == jArray) jArray = new JSONArray();
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM 	= personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		String buttonStyle = "button";
		try{
			JSONObject jObject = null;
			
			logger.debug("getExecute1Result()..."+xrulesVerM.getExecute1Result());	
			
			if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
				if(OrigUtil.isEmptyString(xrulesVerM.getExecute1Result())
						|| !PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getExecute1Result())){
					buttonStyle =  "button-red";
				}
			}
			
			if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(buttonID)){
				if(OrigUtil.isEmptyString(xrulesVerM.getExecute2Result())
						|| !PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getExecute2Result())){
					buttonStyle =  "button-red";
				}
			}
			
			if("button".equals(buttonStyle)){
				XrulesRequestDataM requestM = this.MapXrulesRequestDataM(applicationM, buttonID, null);
				ExecuteServiceManager execute = ORIGEJBService.getExecuteServiceManager();
				boolean result = execute.ValidateServiceExecute(requestM);
				logger.debug("[ButtonStyleEngine] "+result);
				if(result){
					buttonStyle = "button-red";
				}			
			}
			
			jObject = new JSONObject();
			jObject.put(Constant.TYPE, Constant.BUTTON);
			jObject.put("id", buttonID);
			jObject.put("value", buttonStyle);
			jArray.put(jObject);
			
			if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
				this.ButtonEditStyleEngine(jArray, PLXrulesConstant.ButtonID.EDIT_7001, applicationM, request);
			}
			
		}catch (Exception e) {
			logger.fatal("Error "+e.getMessage());
		}
	}
	
	public String ButtonEditStyleEngie(PLApplicationDataM applicationM){
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM 	= personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
//		
//		EAILogic eaiLogic = new EAILogic();
//			if(eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceCisNo())
//				||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceAmcTamc())
//					||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceBankRuptcy())
//						||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceBlockcode())
//							||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceBankRuptcy())
//								||eaiLogic.LogicServiceTFB0137I01(xrulesVerM.getCodeServiceNplLpm())
//									|| PLXrulesConstant.WebServiceCode.REQUIRE_CC_BLOCKCODE.equals(xrulesVerM.getCodeCCBlockCode())
//										|| PLXrulesConstant.WebServiceCode.REQUIRE_KEC_BLOCKCODE.equals(xrulesVerM.getCodeKecBlockCode())){
//				return "button-red";
//			}
		return "button";
	}

	public String DisplayModeCCBlockCode(PLApplicationDataM appM){	
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM 	= personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		if(PLXrulesConstant.WebServiceCode.REQUIRE_CC_BLOCKCODE.equals(xrulesVerM.getCodeCCBlockCode())){
			return HTMLRenderUtil.DISPLAY_MODE_EDIT;
		}
		return HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
	public String DisplayModeKECBlockCode(PLApplicationDataM appM){
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM 	= personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}		
		if(PLXrulesConstant.WebServiceCode.REQUIRE_KEC_BLOCKCODE.equals(xrulesVerM.getCodeKecBlockCode())){
			return HTMLRenderUtil.DISPLAY_MODE_EDIT;
		}
		return HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
	
	public String ButtonStyleEngine(String buttonID,PLApplicationDataM applicationM){
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM 	= personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		logger.debug("[ButtonStyleEngine] "+buttonID);
		String buttonStyle = "button";
		
		try{
			logger.debug(" ButtonStyleEngine().. Execute1 "+xrulesVerM.getExecute1Result());
			logger.debug(" ButtonStyleEngine().. Execute2 "+xrulesVerM.getExecute2Result());
			
			if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
				if(OrigUtil.isEmptyString(xrulesVerM.getExecute1Result())
						|| !PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getExecute1Result())){
					buttonStyle = "button-red";
				}
			}
			
			if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(buttonID)){
				if(OrigUtil.isEmptyString(xrulesVerM.getExecute2Result())
						|| !PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getExecute2Result())){
					buttonStyle = "button-red";
				}
			}
			
			if("button".equals(buttonStyle)){
				XrulesRequestDataM requestM = this.MapXrulesRequestDataM(applicationM, buttonID, null);
				ExecuteServiceManager execute = ORIGEJBService.getExecuteServiceManager();
				boolean result = execute.ValidateServiceExecute(requestM);
				logger.debug("[ButtonStyleEngine] "+result);
				if(result){
					buttonStyle = "button-red";
				}			
			}			
		}catch (Exception e) {
			logger.fatal("Error "+e.getMessage());
		}
		return buttonStyle;
	}
	public XrulesRequestDataM MapXrulesRequestDataM(PLApplicationDataM applicationM ,String buttonID,UserDetailM userM){
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
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
		this.MapListBoxData(applicationM);
		XrulesRequestDataM requestM = new XrulesRequestDataM();		
			requestM.setBusClass(applicationM.getBusinessClassId());
			requestM.setCardType(PLXrulesConstant.CardType.CARD_TYPE_B);
			requestM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			requestM.setJobState(applicationM.getJobState());
			requestM.setModuleType(PLXrulesConstant.ModuleType.VERIFICATION);
			requestM.setActionExecute(buttonID);
			requestM.setPlAppM(applicationM);
			requestM.setUserM(userM);
			requestM.setEaiM(eaiM);
			requestM.setRefNo(applicationM.getRefNo());
			requestM.setAppRecordID(applicationM.getAppRecordID());
			requestM.setTransactionID(applicationM.getTransactionID());
			requestM.setMatrixServiceID(applicationM.getMatrixServiceID());			
			this.MapEAIDataM(applicationM, requestM);
			logger.debug("MapXrulesRequestDataM >> "+requestM.toString());
		return requestM;
	}
	
	public XrulesRequestDataM MapDisplayXrulesRequestDataM(PLApplicationDataM applicationM ,String buttonID,UserDetailM userM 
																	,String searchType, String jobState){
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
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
		this.MapListBoxData(applicationM);
		XrulesRequestDataM requestM = new XrulesRequestDataM();		
			requestM.setBusClass(applicationM.getBusinessClassId());
			requestM.setCardType(PLXrulesConstant.CardType.CARD_TYPE_B);
			requestM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			requestM.setJobState(applicationM.getJobState());
			requestM.setModuleType(PLXrulesConstant.ModuleType.VERIFICATION);
			requestM.setActionExecute(buttonID);
			requestM.setPlAppM(applicationM);
			requestM.setUserM(userM);
			requestM.setEaiM(eaiM);
			requestM.setRefNo(applicationM.getRefNo());			
			requestM.setAppRecordID(applicationM.getAppRecordID());
			requestM.setTransactionID(applicationM.getTransactionID());
			if(OrigConstant.ROLE_PO.equals(userM.getCurrentRole())){
				requestM.setJobState(WorkflowConstant.JobState.DC_IQ);
			}else if(OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
				requestM.setJobState(jobState);
			}			
			requestM.setMatrixServiceID(applicationM.getMatrixServiceID());	
			this.MapEAIDataM(applicationM, requestM);
			logger.debug("MapXrulesRequestDataM >> "+requestM.toString());
		return requestM;
	}
	
	public void MapEAIDataM(PLApplicationDataM appM,XrulesRequestDataM requestM ){
			if(null == appM) appM = new PLApplicationDataM();
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
			
			eaiM.setAmcTamc(xrulesVerM.getAmcTamc());
			eaiM.setAmcTamcCompany(xrulesVerM.getAmcTamcCompany());
			eaiM.setBankRuptcy(xrulesVerM.getBankRuptcy());
			eaiM.setBankRuptcyCompany(xrulesVerM.getBankRuptcyCompany());
			eaiM.setBlockCodeCc(xrulesVerM.getBlockCodeCc());
			eaiM.setBlockCodeDatecc(xrulesVerM.getBlockCodeDatecc());
			eaiM.setCisNo(personalM.getCisNo());
			eaiM.setClassifyLevel(xrulesVerM.getClassifyLevel());
			eaiM.setNplLpm(xrulesVerM.getNplLpm());
						
			eaiM.setCodeServiceBehaviorScore(xrulesVerM.getCodeServiceBehaviorScore());
			eaiM.setCodeCCBlockCode(xrulesVerM.getCodeCCBlockCode());
			eaiM.setCodeKecBlockCode(xrulesVerM.getCodeKecBlockCode());
			eaiM.setCodeServiceCP0148I01(xrulesVerM.getCodeServiceCP0148I01());
			eaiM.setCodeServiceTFB0152I01(xrulesVerM.getCodeServiceTFB0152I01());
			
			eaiM.setCodeServiceCisNo(xrulesVerM.getCodeServiceCisNo());
			eaiM.setCodeServiceAmcTamc(xrulesVerM.getCodeServiceAmcTamc());
			eaiM.setCodeServiceBankRuptcy(xrulesVerM.getCodeServiceBankRuptcy());
			eaiM.setCodeServiceBlockcode(xrulesVerM.getCodeServiceBlockcode());
			eaiM.setCodeServiceclassifyLevel(xrulesVerM.getCodeServiceclassifyLevel());
			eaiM.setCodeServiceNplLpm(xrulesVerM.getCodeServiceNplLpm());
			
			eaiM.setFlagTFB0137I01(xrulesVerM.getFlagTFB0137I01());			
			
			eaiM.setxRulesExistCustM(xrulesVerM.getxRulesExistCustM());
			eaiM.setxRulesNplLpmM(xrulesVerM.getxRulesNplLpmM());
						
			eaiM.setEditAmcTamc(xrulesVerM.getEditAmcTamc());
			eaiM.setEditBankruptcy(xrulesVerM.getEditBankruptcy());
			eaiM.setEditCisNo(xrulesVerM.getEditCisNo());
			eaiM.setEditNpl(xrulesVerM.getEditNpl());
			eaiM.setEditClassifyLevel(xrulesVerM.getEditClassifyLevel());
			eaiM.setEditBlockCode(xrulesVerM.getEditBlockCode());
			
			eaiM.setcCCurrentBalance(xrulesVerM.getcCCurrentBalance());
			eaiM.setcCLastPaymentDate(xrulesVerM.getcCLastPaymentDate());
			
			eaiM.setKecCurrentBalance(xrulesVerM.getKecCurrentBalance());
			eaiM.setKecLastPaymentDate(xrulesVerM.getKecLastPaymentDate());	
			
			eaiM.setDoCP0148I01(false);
			eaiM.setDoTFB0137I01(false);
			eaiM.setDoTFB0152I01(false);			
	}
	
	public XrulesRequestDataM MapSensitiveXrulesRequestDataM(PLApplicationDataM applicationM ,UserDetailM userM){
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
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
		this.MapListBoxData(applicationM);
		XrulesRequestDataM requestM = new XrulesRequestDataM();		
			requestM.setBusClass(applicationM.getBusinessClassId());
			requestM.setCardType(PLXrulesConstant.CardType.CARD_TYPE_B);
			requestM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			requestM.setJobState(applicationM.getJobState());
			requestM.setModuleType(PLXrulesConstant.ModuleType.VERIFICATION);
			requestM.setPlAppM(applicationM);
			requestM.setUserM(userM);
			this.MapEAIDataM(applicationM, requestM);			
			requestM.setRefNo(applicationM.getRefNo());
			requestM.setAppRecordID(applicationM.getAppRecordID());
			requestM.setTransactionID(applicationM.getTransactionID());
			requestM.setMatrixServiceID(applicationM.getMatrixServiceID());	
		logger.debug("MapXrulesRequestDataM >> "+requestM.toString());
		return requestM;
	}
	
	public XrulesRequestDataM MapSensitiveXrulesRequestDataM(PLApplicationDataM applicationM ,SensitiveGroupCacheDataM groupM,UserDetailM userM){
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
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
		this.MapListBoxData(applicationM);
		XrulesRequestDataM requestM = new XrulesRequestDataM();		
			requestM.setBusClass(applicationM.getBusinessClassId());
			requestM.setCardType(PLXrulesConstant.CardType.CARD_TYPE_B);
			requestM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			requestM.setJobState(applicationM.getJobState());
			requestM.setModuleType(PLXrulesConstant.ModuleType.VERIFICATION);
			requestM.setFieldName(groupM.getFieldName());
			requestM.setPlAppM(applicationM);
			requestM.setUserM(userM);
			this.MapEAIDataM(applicationM, requestM);			
			requestM.setRefNo(applicationM.getRefNo());
			requestM.setAppRecordID(applicationM.getAppRecordID());
			requestM.setTransactionID(applicationM.getTransactionID());
			requestM.setFieldName(groupM.getFieldName());
			requestM.setMatrixServiceID(applicationM.getMatrixServiceID());	
		logger.debug("MapXrulesRequestDataM >> "+requestM.toString());
		return requestM;
	}
	
	public XrulesRequestDataM MapXrulesRequestDataM(PLApplicationDataM applicationM ,int serviceID,UserDetailM userM){
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			xrulesVerM.setPersonalID(personalM.getPersonalID());
			personalM.setXrulesVerification(xrulesVerM);
		}
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		this.MapListBoxData(applicationM);
		XrulesRequestDataM requestM = new XrulesRequestDataM();		
			requestM.setBusClass(applicationM.getBusinessClassId());
			requestM.setCardType(PLXrulesConstant.CardType.CARD_TYPE_B);
			requestM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			requestM.setJobState(applicationM.getJobState());
			requestM.setModuleType(PLXrulesConstant.ModuleType.VERIFICATION);
			requestM.setServiceID(serviceID);
			requestM.setPlAppM(applicationM);
			requestM.setUserM(userM);
			this.MapEAIDataM(applicationM, requestM);			
			requestM.setRefNo(applicationM.getRefNo());
			requestM.setAppRecordID(applicationM.getAppRecordID());
			requestM.setTransactionID(applicationM.getTransactionID());
			requestM.setMatrixServiceID(applicationM.getMatrixServiceID());	
		logger.debug("MapXrulesRequestDataM >> "+requestM.toString());
		return requestM;
	}
	public void MapListBoxData(PLApplicationDataM appM){
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			xrulesVerM.setPersonalID(personalM.getPersonalID());
			personalM.setXrulesVerification(xrulesVerM);
		}
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		ORIGCacheDataM mapIdType = cacheUtil.GetListboxCacheDataM(OrigConstant.fieldId.idType, appM.getBusinessClassId(), personalM.getCidType());
		personalM.setEaiCidType(mapIdType.getMapping5());
	}
	
	public void MapDecision(XrulesResponseDataM reponse,PLApplicationDataM applicationM,UserDetailM userM ,String buttonID){
		logger.debug("MapFinalResultM buttonID >> "+buttonID);
		try{
			if(null == reponse){
				reponse = new XrulesResponseDataM();
			}
			if(null == applicationM){
				applicationM = new PLApplicationDataM();			
			}
			
			FinalResultDataM finalM = reponse.getFinalResultM();
			if(null == finalM){
				finalM = new FinalResultDataM();
				reponse.setFinalResultM(finalM);
			}
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			
			logger.debug(" final result >> "+finalM.getResultCode());
			
			if(PLXrulesConstant.ResultCode.CODE_REJECT.equals(finalM.getResultCode())
					|| PLXrulesConstant.ResultCode.CODE_CANCEL.equals(finalM.getResultCode())
						|| PLXrulesConstant.ResultCode.CODE_BLOCK.equals(finalM.getResultCode())){
				
				if(PLXrulesConstant.ResultCode.CODE_REJECT.equals(finalM.getResultCode())
						|| PLXrulesConstant.ResultCode.CODE_BLOCK.equals(finalM.getResultCode())){				
					this.RemoveReason(applicationM, buttonID);
				}else{
					applicationM.setReasonVect(new Vector<PLReasonDataM>());
				}
				
				applicationM.setExecuteReasonVect(null);
				
				Vector<ReasonResultM> reasonVect = finalM.getReasonResultVect();		
				PLReasonDataM reasonM = null;
				if(!OrigUtil.isEmptyVector(reasonVect)){	
					for(ReasonResultM rResultM:reasonVect){
						reasonM = new PLReasonDataM();
						if(!this.EqualReason(applicationM, rResultM.getReasonType(), rResultM.getReasonCode())){			
							reasonM.setReasonCode(rResultM.getReasonCode());
							reasonM.setReasonType(rResultM.getReasonType());
							logger.debug("rResultM.getReasonCode() >> "+rResultM.getReasonCode());
							logger.debug("rResultM.getReasonType() >> "+rResultM.getReasonType());
							reasonM.setRole(userM.getCurrentRole());
							reasonM.setApplicationRecordId(applicationM.getAppRecordID());
							applicationM.add(reasonM);
						}
					}
				}
				
				applicationM.setExecuteReasonVect(applicationM.getReasonVect());
								
				applicationM.setExecuteDecision(finalM.getResultCode());
				
				if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
					xrulesVerM.setExecute1Result(finalM.getResultCode());
					logger.debug("xrulesVerM.getExecute1Result >> "+xrulesVerM.getExecute1Result());
				}else if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(buttonID)){
					xrulesVerM.setExecute2Result(finalM.getResultCode());
					logger.debug("xrulesVerM.getExecute2Result >> "+xrulesVerM.getExecute2Result());
				}				
			}
			
			logger.debug("getExecute1Result()..."+xrulesVerM.getExecute1Result());	
			
		}catch(Exception e){
			logger.debug("MapFinalResultM Error ",e);
		}
	}
	
	public String getExecuteCode(XrulesResponseDataM reponse){
		if(null == reponse ){
			reponse = new XrulesResponseDataM();		
		}
		FinalResultDataM finalM = reponse.getFinalResultM();
		if(null == finalM){
			finalM = new FinalResultDataM();
			reponse.setFinalResultM(finalM);
		}
		return finalM.getExecuteCode();
	}
	
	public String getExcuteResult(XrulesResponseDataM reponse){
		if(null == reponse ){
			reponse = new XrulesResponseDataM();		
		}
		FinalResultDataM finalM = reponse.getFinalResultM();
		if(null == finalM){
			finalM = new FinalResultDataM();
			reponse.setFinalResultM(finalM);
		}
		return finalM.getResultCode();
	}
	
	public void MapFinalResultM(XrulesResponseDataM reponse,PLApplicationDataM applicationM ,JSONArray obj ,UserDetailM userM ,String buttonID){
		logger.debug("MapFinalResultM buttonID >> "+buttonID);
		boolean foundRejectCreditLine = false;
		FinalResultDataM finalM = null;
		if(null == obj) obj = new JSONArray();
		try{
			if(null == reponse ) reponse = new XrulesResponseDataM();
			if(null == applicationM) applicationM = new PLApplicationDataM();			
				finalM = reponse.getFinalResultM();
			if(null == finalM){
				finalM = new FinalResultDataM();
				reponse.setFinalResultM(finalM);
			}
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			
			if(PLXrulesConstant.ResultCode.CODE_REJECT.equals(finalM.getResultCode())
					|| PLXrulesConstant.ResultCode.CODE_BLOCK.equals(finalM.getResultCode())){				
				this.RemoveReason(applicationM, buttonID);
			}else{
				applicationM.setReasonVect(new Vector<PLReasonDataM>());
			}
			
			applicationM.setExecuteReasonVect(null);
			applicationM.setExecuteDecision(null);
			
			Vector<ReasonResultM> reasonVect = finalM.getReasonResultVect();		
			PLReasonDataM reasonM = null;
			if(!OrigUtil.isEmptyVector(reasonVect)){				
				for(ReasonResultM rResultM:reasonVect){
					reasonM = new PLReasonDataM();
					if(!this.EqualReason(applicationM, rResultM.getReasonType(), rResultM.getReasonCode())){			
						reasonM.setReasonCode(rResultM.getReasonCode());
						reasonM.setReasonType(rResultM.getReasonType());
						logger.debug("rResultM.getReasonCode() >> "+rResultM.getReasonCode());
						logger.debug("rResultM.getReasonType() >> "+rResultM.getReasonType());
						reasonM.setRole(userM.getCurrentRole());
						reasonM.setApplicationRecordId(applicationM.getAppRecordID());
						applicationM.add(reasonM);
						
						//Check reason verify credit line fail for manual add or remove policy rules verify credit line
						if(OrigConstant.RejectReason.VERIFY_CREDIT_LINE_FAIL.equals(reasonM.getReasonCode())
								&& OrigConstant.fieldId.REJECT_REASON.equals(reasonM.getReasonType())){
							foundRejectCreditLine = true;
						}
					}
				}
			}
			
			if(PLXrulesConstant.ResultCode.CODE_REJECT.equals(finalM.getResultCode())
					|| PLXrulesConstant.ResultCode.CODE_CANCEL.equals(finalM.getResultCode())
						|| PLXrulesConstant.ResultCode.CODE_BLOCK.equals(finalM.getResultCode())){
				applicationM.setExecuteDecision(finalM.getResultCode());
				applicationM.setExecuteReasonVect(applicationM.getReasonVect());
			}
			
			if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
				xrulesVerM.setExecute1Result(finalM.getResultCode());
				logger.debug("xrulesVerM.getExecute1Result >> "+xrulesVerM.getExecute1Result()); 
			}
			
			if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(buttonID)){
				xrulesVerM.setExecute2Result(finalM.getResultCode());
				logger.debug("xrulesVerM.getExecute2Result >> "+xrulesVerM.getExecute2Result());
			}
			
			//Praisan Khunkaew CR27 20130709
			this.addOrRemovePolicyCreditLine(xrulesVerM, userM, foundRejectCreditLine);	
			
		}catch (Exception e) {
			logger.debug("MapFinalResultM Error ",e);
		}
		
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.TYPE, Constant.FINAL_EXE);
//			jObj.put(Constant.EXE_CODE, finalM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, finalM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, finalM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, finalM.getResultDesc());	
			
		obj.put(jObj);
		
	}
	
	public void RemoveReason(PLApplicationDataM applicationM ,String buttonExecute){
		Vector<PLReasonDataM> tmpReason = new Vector<PLReasonDataM>();
		Vector<PLReasonDataM> vReason = applicationM.getReasonVect();
		if(null != vReason){
			for(PLReasonDataM reasonM : vReason){
				if(null != reasonM && null != reasonM.getReasonType()){
					if(OrigConstant.fieldId.REJECT_REASON.equals(reasonM.getReasonType())){
						if(!EqualRejectReason(buttonExecute,reasonM.getReasonCode())){
							tmpReason.add(reasonM);
						}
					}
				}
			}
		}
		applicationM.setReasonVect(tmpReason);
	}
	
	public boolean EqualRejectReason(String buttonExecute ,String ReasonCode){
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		String sys1 = cacheUtil.getSystemIDFromListbox(ReasonCode, OrigConstant.fieldId.REJECT_REASON, "1");
		if(null != sys1 && sys1.equals(buttonExecute)){
			return true;
		}
		return false;
	}
	
	
	public boolean EqualReason(PLApplicationDataM applicationM,String reasonType,String reasonCode){
		Vector<PLReasonDataM> reasonVect  = applicationM.getReasonVect();
		if(null != reasonVect){
			for(PLReasonDataM reasonM : reasonVect){
				if(null != reasonM 	 && null != reasonM.getReasonType() && null != reasonM.getReasonCode() 
						&& reasonM.getReasonType().equals(reasonType) && reasonM.getReasonCode().equals(reasonCode)){
					return true;
				}
			}
		}
		return false;
	}
		
	public String GetErrorText(HttpServletRequest request,String errorCode){    
		Locale locale = Locale.getDefault();		
		Locale currentLocale = 	(Locale) (request.getSession().getAttribute("LOCALE"));		
		if (currentLocale != null) locale = currentLocale;		
		ResourceBundle resource = ResourceBundle.getBundle(locale + "/properties/getError", locale);
		errorCode = errorCode+"_SHORT_DESC";
		if (errorCode != null && !errorCode.equals("")) {
			try {
				return resource.getString(errorCode);
			}catch(Exception e){
				return "";
			}
		}
		return "";
	}
	
	public JSONObject MapObjectExistingPOD(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setExistingKECPODResultCode(executeM.getResultCode());
			xrulesVerM.setExistingKECPODResultDesc(executeM.getResultDesc());
			xrulesVerM.setExistingKECPODUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setExistingKECPODUpdateDate(executeM.getExecuteTime());
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC_POD);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC_POD);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getExistingKECPODResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getExistingKECPODResultDesc());
			xrulesPolicyM.setUpdateBy(xrulesVerM.getExistingKECPODUpdateBy());
			/*End Set Policy Rules */
			
		return new JSONObject();
	}
	
	public JSONObject MapObjectBundlingCCAge(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setBundlingCCAgeResultCode(executeM.getResultCode());
			xrulesVerM.setBundlingCCAgeResultDesc(executeM.getResultDesc());
			xrulesVerM.setBundlingCCAgeUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBundlingCCAgeUpdateDate(executeM.getExecuteTime());
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_APPLICANT_AGE);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_CC_APPLICANT_AGE);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getBundlingCCAgeResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getBundlingCCAgeResultDesc());
			xrulesPolicyM.setUpdateBy(xrulesVerM.getBundlingCCAgeUpdateBy());
			/*End Set Policy Rules */
			
		return new JSONObject();
	}
	
	public JSONObject MapObjectBundlingCCEducation(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setBundlingCCEducationResultCode(executeM.getResultCode());
			xrulesVerM.setBundlingCCEducationResultDesc(executeM.getResultDesc());
			xrulesVerM.setBundlingCCEducationUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBundlingCCEducationUpdateDate(executeM.getExecuteTime());
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_EDUCATION);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_CC_EDUCATION);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getBundlingCCEducationResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getBundlingCCEducationResultDesc());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getBundlingCCEducationUpdateBy());
			/*End Set Policy Rules */
		return new JSONObject();
	}
	
	public JSONObject MapObjectBundlingCCResident(ExecuteResponseDataM executeM,PLApplicationDataM appM,HttpServletRequest request){		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setBundlingCCResidentResultCode(executeM.getResultCode());
			xrulesVerM.setBundlingCCResidentResultDesc(executeM.getResultDesc());
			xrulesVerM.setBundlingCCResidentUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBundlingCCResidentUpdateDate(executeM.getExecuteTime());
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_RESIDENCE_TYPE);
//			 if(plXrulesPolicyRules==null){
//				 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//				 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_CC_RESIDENCE_TYPE);
//				 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//			 }
			xrulesPolicyM.setResultCode(xrulesVerM.getBundlingCCResidentResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getBundlingCCResidentResultDesc());	
			xrulesPolicyM.setUpdateBy(xrulesVerM.getBundlingCCResidentUpdateBy());
			/*End Set Policy Rules */
		return new JSONObject();
	}
	
	public void ModuleExecuteRules(PLApplicationDataM applicationM ,JSONArray obj){
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
		}
		obj.put(CreateModuleExecuteRules(xrulesVerM.getSummaryOverideRuleCode(),xrulesVerM.getSummaryOverideRuleResult(), "tr-overiderules", "ca_overriderule" ,false));
		
		obj.put(CreateModuleExecuteRules(xrulesVerM.getDebtBdCode(),xrulesVerM.getDEBT_BDResult(), "tr-debtburden", "ca_debt_burden" ,true));
		obj.put(CreateModuleExecuteRules(xrulesVerM.getbScoreCode(),xrulesVerM.getbScoreResult(), "tr-bscore", "ca_b_score" ,true));
		obj.put(CreateModuleExecuteRules(xrulesVerM.getFicoCode(),xrulesVerM.getFicoResult(), "tr-ficoscore", "ca_fico_score" ,true));
		obj.put(CreateModuleExecuteRules(xrulesVerM.getAppScoreResultCode(),xrulesVerM.getAppScoreResult(), "tr-appscore", "ca_app_score" ,true));
	}
	
	public JSONObject CreateModuleExecuteRules(String resultCode ,String resultDesc ,String attrStyle ,String attrValue ,boolean replaceNull){
		JSONObject obj = new JSONObject();
//			obj.put(Constant.TYPE,Constant.SUMMARY_RULE);
//			if(replaceNull){
//				obj.put("VALUE", HTMLRenderUtil.DisplayReplaceLineWithNull(resultDesc));
//			}else{
//				obj.put("VALUE", HTMLRenderUtil.displayHTML(resultDesc));
//			}
//			obj.put("STYLE_COLOR",ORIGLogic.LogicColorStyleResultDiv(resultCode,resultDesc));
//			obj.put("ATTR_STYLE",attrStyle);
//			obj.put("ATTR_VALUE",attrValue);
		return obj;
	}
	public void MapMessage(JSONArray json,XrulesResponseDataM reponse){
		HashMap<String,String> message = reponse.getMessage();
		if(null != message){
			for(String value : message.values()){
				JSONObject obj = new JSONObject();
//					obj.put(Constant.TYPE,"MESSAGE");
//					obj.put("message",value);
				json.put(obj);
			}
		}
	}
	public JSONObject MapObjectSoloView(ExecuteResponseDataM executeM,PLApplicationDataM applicationM,HttpServletRequest request){		
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setSoloViewResultCode(executeM.getResultCode());
			xrulesVerM.setSoloViewResultDesc(executeM.getResultDesc());
			xrulesVerM.setSoloViewUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setSoloViewUpdateDate(executeM.getExecuteTime());	
			
			PLXRulesPolicyRulesDataM xrulesPolicyM =xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SOLO_VIEW);

			xrulesPolicyM.setResultCode(xrulesVerM.getSoloViewResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getSoloViewResultDesc());		
			xrulesPolicyM.setUpdateBy(xrulesVerM.getSoloViewResultDesc());
			
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public JSONObject MapObjectBOT5X(ExecuteResponseDataM executeM,PLApplicationDataM applicationM,HttpServletRequest request){		
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setBot5xResultCode(executeM.getResultCode());
			xrulesVerM.setBot5xResultDesc(executeM.getResultDesc());
			xrulesVerM.setBot5xUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setBot5xUpdateDate(executeM.getExecuteTime());	
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM =xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_ROLE_ID_BOT5X);
			xrulesPolicyM.setResultCode(xrulesVerM.getBot5xResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getBot5xResultDesc());		
			xrulesPolicyM.setUpdateBy(xrulesVerM.getBot5xUpdateBy());
			/*End Set Policy Rules */ 
		return new JSONObject();
	}
	public JSONObject MapObjectLEC(ExecuteResponseDataM executeM,PLApplicationDataM applicationM,HttpServletRequest request){		
		if(null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}	
		
		if(null == executeM) executeM = new ExecuteResponseDataM();
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
			executeM.setResultCode("");
			String msg = this.GetErrorText(request, "MSG_XRULES_"+executeM.getExecuteDesc());
			if(OrigUtil.isEmptyString(msg)){
				msg = this.GetErrorText(request, "MSG_XRULES_9");
			}
			executeM.setExecuteDesc(msg);
			executeM.setResultDesc(msg);
		}
		
			xrulesVerM.setLecResultCode(executeM.getResultCode());
			xrulesVerM.setLecResultDesc(executeM.getResultDesc());
			xrulesVerM.setLecUpdateBy(executeM.getExecuteBy());
			xrulesVerM.setLecUpdateDate(executeM.getExecuteTime());	
			
			/* Set Policy rules*/
			PLXRulesPolicyRulesDataM xrulesPolicyM =xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_LEC);
			xrulesPolicyM.setResultCode(xrulesVerM.getLecResultCode());
			xrulesPolicyM.setResultDesc(xrulesVerM.getLecResultDesc());		
			xrulesPolicyM.setUpdateBy(xrulesVerM.getLecUpdateBy());
			/*End Set Policy Rules */ 
		JSONObject jObj = new JSONObject();
//			jObj.put(Constant.SERVICE_ID, executeM.getServiceID());
//			jObj.put(Constant.TYPE, Constant.EXECUTE);
//			jObj.put(Constant.EXE_CODE, executeM.getExecuteCode());
//			jObj.put(Constant.EXE_DESC, executeM.getExecuteDesc());
//			jObj.put(Constant.RESULT_CODE, executeM.getResultCode());
//			jObj.put(Constant.RESULT_DESC, executeM.getResultDesc());
//			jObj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult(executeM.getResultCode(), executeM.getResultDesc()));
		return jObj;
	}
	public void addOrRemovePolicyCreditLine(PLXRulesVerificationResultDataM verResult, UserDetailM userM, boolean foundRejectCreditLine){	
		if(foundRejectCreditLine){
			PLXRulesPolicyRulesDataM policyRulesM = new PLXRulesPolicyRulesDataM();
			policyRulesM.setPersonalID(verResult.getPersonalID());
			policyRulesM.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE);
			policyRulesM.setResultCode(PLXrulesConstant.ResultCode.CODE_FAIL);
			policyRulesM.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));				
			policyRulesM.setUpdateBy(userM.getUserName());
			policyRulesM.setResultDesc(PLXrulesConstant.ResultDesc.CODE_FAIL_DESC);
			verResult.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE, policyRulesM);
			logger.debug("@@@@@ Add Fail to policy:" + OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE);
		}else{
			verResult.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE);
			logger.debug("@@@@@ Remove policy:" + OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE);
		}
	}
}
