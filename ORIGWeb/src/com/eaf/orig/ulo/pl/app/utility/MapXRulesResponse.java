package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.DemographicDataM;
import com.eaf.xrules.ulo.pl.model.FinalResultDataM;
import com.eaf.xrules.ulo.pl.model.PLXrulesResultDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResultDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesBScoreDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFraudCompanyDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFraudDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesListedCompanyDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPayRollDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXrulesNplLpmDataM;

@Deprecated
public class MapXRulesResponse{	
	public MapXRulesResponse(){
		super();
	}	
	static Logger logger = Logger.getLogger(MapXRulesResponse.class);
	public class Constant{
		public static final String EXE_CODE = "EXE_CODE";
		public static final String EXE_DESC = "EXE_DESC";
		public static final String RESULT_CODE = "RESULT_CODE";
		public static final String RESULT_DESC = "RESULT_DESC";
		public static final String SERVICE_ID = "SERVICE_ID";
		public static final String FINAL_EXE = "FINAL_EXE";
		public static final String TYPE = "TYPE";
	}
	
	public class JsonAttribute{
		public static final String EXE_CODE = "execode";
		public static final String EXE_DESC = "exedesc";
		public static final String RESULT_CODE = "resultcode";
		public static final String RESULT_DESC = "resultdesc";
		public static final String SERVICE_ID = "serviceId";
		public static final String FINAL_EXE = "finalExecute";
		public static final String TYPE = "type";
	}
	
	public static final String CLEAR_RULE = "CLEAR_RULE";
	public static final String ROLLBACK_RULE = "ROLLBACK_RULE";
		
	@Deprecated
	public JSONArray MappingObject( PLApplicationDataM plApplicationM ,PLXrulesResultDataM plXrulesResultM){
		
		logger.debug("[MappingObject]..");
		
		if(plXrulesResultM == null) return new JSONArray();
		
		Vector<XrulesResultDataM> plXrulesExecute = plXrulesResultM.getPlXrulesExecute();
		
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonObj = null;
		
		/**Mapping Execute Service Id. #SeptemWi*/
		
		if(!OrigUtil.isEmptyVector(plXrulesExecute)){
			for(XrulesResultDataM executeM :plXrulesExecute){	
				switch (executeM.getServiceId()) {
					case PLXrulesConstant.ServiceID.SPECIAL_CUSTOMER:
						jsonObj = new JSONObject();
						jsonObj = MapSpecialCustomer(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.DUPLICATE_APPLICATION:
						jsonObj = new JSONObject();
						jsonObj = MapDuplicateApplication(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.EXISTING_KEC:
						jsonObj = new JSONObject();
						jsonObj = MapExistingKEC(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.NPL:
						jsonObj = new JSONObject();
						jsonObj = MapNplLpm(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.CLASSIFY_LEVEL:
						jsonObj = new JSONObject();
						jsonObj = MapClassifyLevel(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.BANKRUPTCY:
						jsonObj = new JSONObject();
						jsonObj = MapBankRuptcy(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.AMCTAMC:
						jsonObj = new JSONObject();
						jsonObj = MapAmcTamc(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.BLOCKCODE:
						jsonObj = new JSONObject();
						jsonObj = MapBlockCode(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.DEMOGRAPHIC:
						jsonObj = new JSONObject();
						jsonObj = MapDemoGraphic(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.WATCHLIST_FRAUD:
						jsonObj = new JSONObject();
						jsonObj = MapWathcListFraud(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.BEHAVIOR_RISK_GRADE:
						jsonObj = new JSONObject();
						jsonObj = MapBehavoiorRiskGrade(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.PAYROLL:						
						jsonObj = new JSONObject();
						jsonObj = MapPayRoll(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.KBANK_EMPLOYEE:
						jsonObj = new JSONObject();
						jsonObj = MapKbankEmployee(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.BANKRUPTCY_COMPANY:
						jsonObj = new JSONObject();
						jsonObj = MapBankruptcyCompany(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.AMCTAMC_COMPANY:
						jsonObj = new JSONObject();
						jsonObj = MapAmcTamcCompany(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.LISTED_COMPANY:
						jsonObj = new JSONObject();
						jsonObj = MapListedCompany(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					case PLXrulesConstant.ServiceID.FRAUD_COMPANY:
						jsonObj = new JSONObject();
						jsonObj = MapFraudCompany(plApplicationM, executeM);
//						jsonObj.put(JsonAttribute.TYPE, "EXECUTE");
						jsonArray.put(jsonObj);
						break;
					default:
						break;
				}  
			}
		}		
		
		/**Mapping Final ExecuteCode #SeptemWi*/
		
		jsonArray.put(MappFinalResultExecute(plApplicationM, plXrulesResultM));	
		
		/***/
		
		return jsonArray;
	}
	
	public JSONObject MappFinalResultExecute(PLApplicationDataM plApplicationM ,PLXrulesResultDataM plXrulesResultM){
		JSONObject jsonObject = new JSONObject();			
		if(plXrulesResultM == null) plXrulesResultM = new PLXrulesResultDataM();		
		FinalResultDataM finalExecuteM = plXrulesResultM.getFinalExecuteM();		
		if(finalExecuteM == null) finalExecuteM = new FinalResultDataM();		
//		jsonObject.put(JsonAttribute.TYPE,JsonAttribute.FINAL_EXE);
//		jsonObject.put(JsonAttribute.EXE_CODE,finalExecuteM.getExecuteCode());
//		jsonObject.put(JsonAttribute.RESULT_CODE,finalExecuteM.getResultCode());
		/**Put Resaon To Application #SeptemWi*/
//		Vector<ReasonResultM> reasonResultVect = finalExecuteM.getReasonResultVect();
//		if(!OrigUtil.isEmptyVector(reasonResultVect)){
//			for(ReasonResultM reasonResultM : reasonResultVect) {
//				
//			}
//		}
		
		return jsonObject;
	}
	
	public JSONObject MapSpecialCustomer(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){	
			PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
			JSONObject jsonObject = new JSONObject();
			if(plXrulesVerM == null){
				plXrulesVerM = new PLXRulesVerificationResultDataM();
				plPersonalM.setXrulesVerification(plXrulesVerM);
			}
			if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//				jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//				jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//				jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//				jsonObject.put(JsonAttribute.RESULT_CODE,"");
//				jsonObject.put(JsonAttribute.RESULT_DESC,"");				
				plXrulesVerM.setSpecialCusCode("");
				plXrulesVerM.setSpecialCusResult("");
				plXrulesVerM.setSpecialCusUpdateDate(executeM.getExecuteTime());
				plXrulesVerM.setSpecialCusUpdateBy(executeM.getExecuteBy());
				return jsonObject;
			}
			/**Case Success #SeptemWi*/
			plXrulesVerM.setSpecialCusCode(executeM.getResultCode());		
			plXrulesVerM.setSpecialCusResult(executeM.getResultDesc());
			plXrulesVerM.setSpecialCusUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setSpecialCusUpdateBy(executeM.getExecuteBy());			
			
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//			jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
			
		return jsonObject;
	}
	public JSONObject MapDuplicateApplication(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
			PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();
			
			JSONObject jsonObject = new JSONObject();
			if(plXrulesVerM == null){
				plXrulesVerM = new PLXRulesVerificationResultDataM();
				plPersonalM.setXrulesVerification(plXrulesVerM);
			}
			
			if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//				jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//				jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//				jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//				jsonObject.put(JsonAttribute.RESULT_CODE,"");
//				jsonObject.put(JsonAttribute.RESULT_DESC,"");				
				plXrulesVerM.setDedupCode("");		
				plXrulesVerM.setDedupResult("");
				plXrulesVerM.setDedupUpdateDate(executeM.getExecuteTime());
				plXrulesVerM.setDedupUpdateBy(executeM.getExecuteBy());
				plXrulesVerM.setVXRulesDedupDataM(new Vector());
				return jsonObject;
			}		
			
			plXrulesVerM.setDedupCode(executeM.getResultCode());		
			plXrulesVerM.setDedupResult(executeM.getResultDesc());
			plXrulesVerM.setDedupUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setDedupUpdateBy(executeM.getExecuteBy());
			
			Vector plXrulesDupVect = (Vector) executeM.getObj();						
			plXrulesVerM.setVXRulesDedupDataM(plXrulesDupVect);			
			
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,executeM.getResultCode());
//			jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());	
			
		return jsonObject;
	}
	public JSONObject MapExistingKEC(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		if(executeM == null) executeM = new XrulesResultDataM();
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");	
			plXrulesVerM.setExistCustCode("");
			plXrulesVerM.setExistCustResult("");				
			plXrulesVerM.setExistingCustUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setExistCustUpdateBy(executeM.getExecuteBy());
//			plXrulesVerM.setPlXrulesExistCustM(new PLXRulesExistCustDataM());
			return jsonObject;
		}	
		
		plXrulesVerM.setExistCustCode(executeM.getResultCode());
		plXrulesVerM.setExistCustResult(executeM.getResultDesc());				
		plXrulesVerM.setExistingCustUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setExistCustUpdateBy(executeM.getExecuteBy());
		
		PLXRulesExistCustDataM plXrulesExistCustM = (PLXRulesExistCustDataM) executeM.getObj();
		if(plXrulesExistCustM == null) plXrulesExistCustM =new PLXRulesExistCustDataM();
//		plXrulesVerM.setPlXrulesExistCustM(plXrulesExistCustM);
	
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE,executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
			
		return jsonObject;
	}
	public JSONObject MapNplLpm(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setNplLpmCode("");
			plXrulesVerM.setNplLpmResult("");
			plXrulesVerM.setNplLpmUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setNplLpmUpdateBy(executeM.getExecuteBy());
//			plXrulesVerM.setPlXrulesNplLpmM(new PLXrulesNplLpmDataM());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setNplLpmCode(executeM.getResultCode());		
		plXrulesVerM.setNplLpmResult(executeM.getResultDesc());
		plXrulesVerM.setNplLpmUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setNplLpmUpdateBy(executeM.getExecuteBy());
		PLXrulesNplLpmDataM nplLpmM = (PLXrulesNplLpmDataM) executeM.getObj();		
		if(nplLpmM == null) nplLpmM = new PLXrulesNplLpmDataM();		
//		plXrulesVerM.setPlXrulesNplLpmM(nplLpmM);
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		return jsonObject;
	}
	public JSONObject MapClassifyLevel(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setLpmClassifyLvCode("");
			plXrulesVerM.setLpmClassifyLvResult("");
			plXrulesVerM.setLpmClassifyLvUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setLpmClassifyLvUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setLpmClassifyLvCode(executeM.getResultCode());		
		plXrulesVerM.setLpmClassifyLvResult(executeM.getResultDesc());
		plXrulesVerM.setLpmClassifyLvUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setLpmClassifyLvUpdateBy(executeM.getExecuteBy());			
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		return jsonObject;
	}
	public JSONObject MapBankRuptcy(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setBankruptcyCode("");
			plXrulesVerM.setBankruptcyResult("");
			plXrulesVerM.setBankruptcyUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setBankruptcyUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setBankruptcyCode(executeM.getResultCode());		
		plXrulesVerM.setBankruptcyResult(executeM.getResultDesc());
		plXrulesVerM.setBankruptcyUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setBankruptcyUpdateBy(executeM.getExecuteBy());			
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		return jsonObject;
	}
	public JSONObject MapAmcTamc(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setAmcTamcCode("");
			plXrulesVerM.setAmcTamcResult("");
			plXrulesVerM.setAmcTamcUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setAmcTamcUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setAmcTamcCode(executeM.getResultCode());		
		plXrulesVerM.setAmcTamcResult(executeM.getResultDesc());
		plXrulesVerM.setAmcTamcUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setAmcTamcUpdateBy(executeM.getExecuteBy());			
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		return jsonObject;
	}
	public JSONObject MapBlockCode(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
//			plXrulesVerM.setBlockCodeCode("");
//			plXrulesVerM.setBlockCodeResult("");
//			plXrulesVerM.setBlockCodeUpdateDate(executeM.getExecuteTime());
//			plXrulesVerM.setBlockCodeUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
//		plXrulesVerM.setBlockCodeCode(executeM.getResultCode());
//		plXrulesVerM.setBlockCodeResult(executeM.getResultDesc());
//		plXrulesVerM.setBlockCodeUpdateDate(executeM.getExecuteTime());
//		plXrulesVerM.setBlockCodeUpdateBy(executeM.getExecuteBy());			
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		return jsonObject;
	}
	public JSONObject MapDemoGraphic(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setDemographicCode("");
			plXrulesVerM.setDemographicResult("");
			plXrulesVerM.setAgeVerCode("");
			plXrulesVerM.setAgeVerResult("");
			plXrulesVerM.setNationalityVerCode("");
			plXrulesVerM.setNationalityVerResult("");
			plXrulesVerM.setDemographicUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setDemographicUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/		
		DemographicDataM demoGraphicM = (DemographicDataM) executeM.getObj();
		
		if(demoGraphicM == null) demoGraphicM = new DemographicDataM();
		
		plXrulesVerM.setDemographicCode(demoGraphicM.getDemoGraphicCode());
		plXrulesVerM.setDemographicResult(demoGraphicM.getDemoGraphicDesc());
		plXrulesVerM.setAgeVerCode(demoGraphicM.getAgeCode());
		plXrulesVerM.setAgeVerResult(demoGraphicM.getAgeDesc());
		plXrulesVerM.setNationalityVerCode(demoGraphicM.getNationalityCode());
		plXrulesVerM.setNationalityVerResult(demoGraphicM.getNationalityDesc());
		plXrulesVerM.setDemographicUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setDemographicUpdateBy(executeM.getExecuteBy());
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, demoGraphicM.getDemoGraphicCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,demoGraphicM.getDemoGraphicDesc());
		
		return jsonObject;
	}
	public JSONObject MapWathcListFraud(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setWatchListCode("");
			plXrulesVerM.setWatchListResult("");
			plXrulesVerM.setWatchListUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setWatchListUpdateBy(executeM.getExecuteBy());
			plXrulesVerM.setxRulesFraudDataMs(new PLXRulesFraudDataM());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setWatchListCode(executeM.getResultCode());		
		plXrulesVerM.setWatchListResult(executeM.getResultDesc());
		plXrulesVerM.setWatchListUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setWatchListUpdateBy(executeM.getExecuteBy());			
		
		PLXRulesFraudDataM plXrulesFraudM = (PLXRulesFraudDataM) executeM.getObj();
		if(plXrulesFraudM == null) plXrulesFraudM = new PLXRulesFraudDataM();
		plXrulesVerM.setxRulesFraudDataMs(plXrulesFraudM);	
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		return jsonObject;
	}
	public JSONObject MapBehavoiorRiskGrade(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setBehaviorRiskGradeCode("");
			plXrulesVerM.setBehaviorRiskGradeResult("");
			plXrulesVerM.setBehaviorRiskGradeUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setBehaviorRiskGradeUpdateBy(executeM.getExecuteBy());
			plXrulesVerM.setxRulesBScoreVect(new Vector<PLXRulesBScoreDataM>());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setBehaviorRiskGradeCode(executeM.getResultCode());		
		plXrulesVerM.setBehaviorRiskGradeResult(executeM.getResultDesc());
		plXrulesVerM.setBehaviorRiskGradeUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setBehaviorRiskGradeUpdateBy(executeM.getExecuteBy());			
		
		Vector<PLXRulesBScoreDataM> plXrulesBscoreVect = (Vector<PLXRulesBScoreDataM>) executeM.getObj();
		if(plXrulesBscoreVect == null) plXrulesBscoreVect = new Vector<PLXRulesBScoreDataM>();
		plXrulesVerM.setxRulesBScoreVect(plXrulesBscoreVect);
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		
		return jsonObject;
	}
	public JSONObject MapPayRoll(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
				
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE,"");
//			jsonObject.put(JsonAttribute.RESULT_DESC,"");				
			plXrulesVerM.setPayrollCode("");
			plXrulesVerM.setPayrollResult("");
			plXrulesVerM.setPayrollUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setPayrollUpdateBy(executeM.getExecuteBy());
			plXrulesVerM.setxRulesPayRolls(null);
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setPayrollCode(executeM.getResultCode());		
		plXrulesVerM.setPayrollResult(executeM.getResultDesc());
		plXrulesVerM.setPayrollUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setPayrollUpdateBy(executeM.getExecuteBy());			
		
		PLXRulesPayRollDataM plXrulesPayRollM = (PLXRulesPayRollDataM) executeM.getObj();
		
//		if(plXrulesPayRollM == null) plXrulesPayRollM = new PLXRulesPayRollDataM();
		
		plXrulesVerM.setxRulesPayRolls(plXrulesPayRollM);
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		return jsonObject;
	}
	public JSONObject MapKbankEmployee(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//			jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());				
			plXrulesVerM.setkBankEmployeeCode("");
			plXrulesVerM.setkBankEmployeeResult("");
			plXrulesVerM.setkBankEmployeeUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setkBankEmployeeUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setkBankEmployeeCode(executeM.getResultCode());		
		plXrulesVerM.setkBankEmployeeResult(executeM.getResultDesc());
		plXrulesVerM.setkBankEmployeeUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setkBankEmployeeUpdateBy(executeM.getExecuteBy());			
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		
	return jsonObject;
	}
	public JSONObject MapBankruptcyCompany(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//			jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());				
			plXrulesVerM.setBankRuptcyCompanyCode("");
			plXrulesVerM.setBankRuptcyCompanyResult("");
			plXrulesVerM.setBankRuptcyCompanyUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setBankRuptcyCompanyUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setBankRuptcyCompanyCode(executeM.getResultCode());		
		plXrulesVerM.setBankRuptcyCompanyResult(executeM.getResultDesc());
		plXrulesVerM.setBankRuptcyCompanyUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setBankRuptcyCompanyUpdateBy(executeM.getExecuteBy());			
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		
		return jsonObject;
	}
	public JSONObject MapAmcTamcCompany(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//			jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());				
			plXrulesVerM.setAmcTamcCompanyCode("");
			plXrulesVerM.setAmcTamcCompanyResult("");
			plXrulesVerM.setAmcTamcCompanyUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setAmcTamcCompanyUpdateBy(executeM.getExecuteBy());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setAmcTamcCompanyCode(executeM.getResultCode());		
		plXrulesVerM.setAmcTamcCompanyResult(executeM.getResultDesc());
		plXrulesVerM.setAmcTamcCompanyUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setAmcTamcCompanyUpdateBy(executeM.getExecuteBy());			
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		
		return jsonObject;
	}
	
	public JSONObject MapListedCompany(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//			jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());				
			plXrulesVerM.setListedCompanyCode("");
			plXrulesVerM.setListedCompanyResult("");
			plXrulesVerM.setListedCompanyUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setListedCompanyUpdateBy(executeM.getExecuteBy());
			plXrulesVerM.setxRulesListedCompanyDataMs(new Vector<PLXRulesListedCompanyDataM>());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setListedCompanyCode(executeM.getResultCode());		
		plXrulesVerM.setListedCompanyResult(executeM.getResultDesc());
		plXrulesVerM.setListedCompanyUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setListedCompanyUpdateBy(executeM.getExecuteBy());			
		
		Vector<PLXRulesListedCompanyDataM> plXRulesListedVect = (Vector<PLXRulesListedCompanyDataM>) executeM.getObj();
		
		if(plXRulesListedVect == null) plXRulesListedVect = new Vector<PLXRulesListedCompanyDataM>();
		
		plXrulesVerM.setxRulesListedCompanyDataMs(plXRulesListedVect);
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		
		return jsonObject;
	}
	public JSONObject MapFraudCompany(PLApplicationDataM plApplicationM ,XrulesResultDataM executeM){
		
		
		PLPersonalInfoDataM plPersonalM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM plXrulesVerM = 	plPersonalM.getXrulesVerification();	
		JSONObject jsonObject = new JSONObject();
		if(plXrulesVerM == null){
			plXrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(plXrulesVerM);
		}
		if(!PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(executeM.getExecuteCode())){
//			jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//			jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//			jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//			jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//			jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());				
			plXrulesVerM.setFraudCompanyCode("");
			plXrulesVerM.setFraudCompanyResult("");
			plXrulesVerM.setFraudCompanyUpdateDate(executeM.getExecuteTime());
			plXrulesVerM.setFraudCompanyUpdateBy(executeM.getExecuteBy());
//			plXrulesVerM.setPlXrulesFraudCompanyM(new PLXRulesFraudCompanyDataM());
			return jsonObject;
		}
		/**Case Success #SeptemWi*/
		plXrulesVerM.setFraudCompanyCode(executeM.getResultCode());		
		plXrulesVerM.setFraudCompanyResult(executeM.getResultDesc());
		plXrulesVerM.setFraudCompanyUpdateDate(executeM.getExecuteTime());
		plXrulesVerM.setFraudCompanyUpdateBy(executeM.getExecuteBy());		
		
		PLXRulesFraudCompanyDataM plXrulesFraudCompanyM = (PLXRulesFraudCompanyDataM) executeM.getObj();
		
		if(plXrulesFraudCompanyM == null) plXrulesFraudCompanyM = new PLXRulesFraudCompanyDataM();
		
//		plXrulesVerM.setPlXrulesFraudCompanyM(plXrulesFraudCompanyM);
		
//		jsonObject.put(JsonAttribute.SERVICE_ID, executeM.getServiceId());
//		jsonObject.put(JsonAttribute.EXE_CODE, executeM.getExecuteCode());
//		jsonObject.put(JsonAttribute.EXE_DESC, executeM.getExecuteDesc());
//		jsonObject.put(JsonAttribute.RESULT_CODE, executeM.getResultCode());
//		jsonObject.put(JsonAttribute.RESULT_DESC,executeM.getResultDesc());
		
		return jsonObject;
	}
		
}
