package com.eaf.orig.ulo.pl.ajax;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JSONXrules;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class ExecuteReset implements AjaxDisplayGenerateInf{	
	static Logger logger = Logger.getLogger(ExecuteReset.class);	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{
		logger.debug("ExecuteReset ...");
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		this.doResetExecute1(applicationM,userM);	
		
		PLApplicationDataM sensitiveappM = (PLApplicationDataM)SerializeUtil.clone(applicationM);
		request.getSession(true).setAttribute(PLOrigFormHandler.PLORIGSensitive,sensitiveappM);
		
		return doDisplay(applicationM);
	}
	public String doDisplay(PLApplicationDataM applicationM){
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		JSONXrules json = new JSONXrules();
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.SPECIAL_CUSTOMER),xrulesVerM.getSpecialCusCode(),xrulesVerM.getSpecialCusResult());	
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.DEMOGRAPHIC),xrulesVerM.getDemographicCode(),xrulesVerM.getDemographicResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION),xrulesVerM.getDedupCode(),xrulesVerM.getDedupResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.EXISTING_KEC),xrulesVerM.getExistCustCode(),xrulesVerM.getExistCustResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE),xrulesVerM.getBlockCodeccCode(),xrulesVerM.getBlockCodeccResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.KEC_BLOCKCODE),xrulesVerM.getBlockCodeKecCode(),xrulesVerM.getBlockCodeKecResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.CLASSIFY_LEVEL_LPM),xrulesVerM.getLpmClassifyLvCode(),xrulesVerM.getLpmClassifyLvResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.SP),xrulesVerM.getSpResultCode(),xrulesVerM.getSpResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.SANCTION_LIST),xrulesVerM.getSanctionListResultCode(),xrulesVerM.getSanctionListResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.BANKRUPTCY),xrulesVerM.getBankruptcyCode(),xrulesVerM.getBankruptcyResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.AMC_TAMC),xrulesVerM.getAmcTamcCode(),xrulesVerM.getAmcTamcResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.WATCHLIST_FRAUD),xrulesVerM.getWatchListCode(),xrulesVerM.getWatchListResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE),xrulesVerM.getBehaviorRiskGradeCode(),xrulesVerM.getBehaviorRiskGradeResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.PAY_ROLL),xrulesVerM.getPayrollCode(),xrulesVerM.getPayrollResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.KBANK_EMPLOYEE),xrulesVerM.getkBankEmployeeCode(),xrulesVerM.getkBankEmployeeResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.LISTED_COMPANY),xrulesVerM.getListedCompanyCode(),xrulesVerM.getListedCompanyResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.FRAUD_COMPANY),xrulesVerM.getFraudCompanyCode(),xrulesVerM.getFraudCompanyResult(),"fraud-comp-searchtype");
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY),xrulesVerM.getBankRuptcyCompanyCode(),xrulesVerM.getBankRuptcyCompanyResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.NPL_LPM),xrulesVerM.getNplLpmCode(),xrulesVerM.getNplLpmResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY_FIRST),xrulesVerM.getAmctamcCompanyFirstCode(),xrulesVerM.getAmctamcCompanyFirstResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY_FIRST),xrulesVerM.getBankruptcyCompanyFirstCode(),xrulesVerM.getBankruptcyCompanyFirstResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY),xrulesVerM.getAmcTamcCompanyCode(),xrulesVerM.getAmcTamcCompanyResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY),xrulesVerM.getBankRuptcyCompanyCode(),xrulesVerM.getBankRuptcyCompanyResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.MONTH_PROFILE),xrulesVerM.getMonthProfileCode(),xrulesVerM.getMonthProfileResult());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.LAST_RESURUCTURE_DATE_LPM),"");
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.STATUS_RESTRUCTURE_FLAG),"");
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.SOLO_VIEW),xrulesVerM.getSoloViewResultCode(),xrulesVerM.getSoloViewResultDesc());
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.LEC),xrulesVerM.getLecResultCode(),xrulesVerM.getLecResultDesc());
		String cisNo = personalM.getCisNo();
		if(OrigUtil.isEmptyString(cisNo)){
			cisNo = "-";
		}
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.CIS_NO),cisNo);
		String customerNo = personalM.getCardLinkCustNo();
		if(OrigUtil.isEmptyString(customerNo)){
			customerNo = "-";
		}
		json.doDisplayEmpty(String.valueOf(PLXrulesConstant.ModuleService.CUSTOMER_NO),customerNo);
		
		json.doButtonEdit("edit_7001");
		json.doButtonExecute("button_7001");
		
		return json.response();
	}
	public void doResetExecute1(PLApplicationDataM applicationM,UserDetailM userM){
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
		
		xrulesVerM.setExecute1Result(null);
		xrulesVerM.setFlagTFB0137I01(null);
		eaiM.setFlagTFB0137I01(null);
		
		this.doSPECIAL_CUSTOMER(xrulesVerM,userM);
		this.doDEMOGRAPHIC(xrulesVerM,userM);
		this.doDEPLICATE_APPLICATION(xrulesVerM,userM);
		this.doEXISTING_KEC(xrulesVerM,userM);
		this.doCREDIT_CARD_BLOCKCODE(xrulesVerM,userM);
		this.doKEC_BLOCKCODE(xrulesVerM,userM);
		this.doCLASSIFY_LEVEL_LPM(xrulesVerM,userM);
		this.doSP(xrulesVerM,userM);
		this.doSANCTION_LIST(xrulesVerM,userM);
		this.doBANKRUPTCY(xrulesVerM,userM);
		this.doAMC_TAMC(xrulesVerM,userM);
		this.doWATCHLIST_FRAUD(xrulesVerM,userM);
		this.doBEHAVIOR_RISK_GRADE(xrulesVerM,userM);
		this.doPAY_ROLL(xrulesVerM,userM);
		this.doKBANK_EMPLOYEE(xrulesVerM,userM);
		this.doLISTED_COMPANY(xrulesVerM,userM);
		this.doFRAUD_COMPANY(xrulesVerM,userM);
		this.doBANKRUPTCY_COMPANY(xrulesVerM,userM);
		this.doAMC_TAMC_COMPANY(xrulesVerM,userM);
		this.doNPL_LPM(xrulesVerM, userM);
		this.doAMC_TAMC_COMPANY_FIRST(xrulesVerM, userM);
		this.doBANKRUPTCY_COMPANY_FIRST(xrulesVerM, userM);
		this.doMONTH_PROFILE(xrulesVerM, userM);
		this.doCIS_NO(xrulesVerM, userM, personalM);
		this.doCUSTOMER_NO(xrulesVerM, userM, personalM);
		this.doLEC(xrulesVerM, userM);
		this.doSoloView(xrulesVerM, userM);
	}
	public void doCUSTOMER_NO(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM,PLPersonalInfoDataM personalM){
		personalM.setCardLinkCustNo(null);
	}
	public void doCIS_NO(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM,PLPersonalInfoDataM personalM){
		personalM.setCisNo(null);
		xrulesVerM.setEditCisNo(null);
		xrulesVerM.setCodeServiceCisNo(null);
	}
	public void doMONTH_PROFILE(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		xrulesVerM.setCodeServiceCP0148I01(null);
		eaiM.setCodeServiceCP0148I01(null);		
		xrulesVerM.setMonthProfileCode(null);
		xrulesVerM.setMonthProfileResult(null);
		xrulesVerM.setMonthProfileUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setMonthProfileUpdateBy(userM.getUserName());
		eaiM.setxRulesExistCustM(null);
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_12_MONTH_PROFILE);
	}
	public void doBANKRUPTCY_COMPANY_FIRST(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		xrulesVerM.setBankRuptcyCompany(null);
		eaiM.setBankRuptcyCompany(null);
		xrulesVerM.setCodeServiceTFB0152I01(null);
		eaiM.setCodeServiceTFB0152I01(null);					
		xrulesVerM.setBankRuptcyCompanyResult(null);
		xrulesVerM.setBankRuptcyCompanyCode(null);
		xrulesVerM.setBankRuptcyCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBankRuptcyCompanyUpdateBy(userM.getUserName());	
		xrulesVerM.setBankruptcyCompanyFirstResult(null);
		xrulesVerM.setBankruptcyCompanyFirstCode(null);
		xrulesVerM.setBankruptcyCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBankruptcyCompanyFirstUpdateBy(userM.getUserName());	
	}
	public void doAMC_TAMC_COMPANY_FIRST(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		xrulesVerM.setAmcTamcCompany(null);
		xrulesVerM.setCodeServiceTFB0152I01(null);		
		eaiM.setAmcTamcCompany(null);
		eaiM.setCodeServiceTFB0152I01(null);		
		xrulesVerM.setAmcTamcCompanyResult(null);
		xrulesVerM.setAmcTamcCompanyCode(null);
		xrulesVerM.setAmcTamcCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setAmcTamcCompanyUpdateBy(userM.getUserName());				
		xrulesVerM.setAmctamcCompanyFirstResult(null);
		xrulesVerM.setAmctamcCompanyFirstCode(null);
		xrulesVerM.setAmctamcCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setAmctamcCompanyFirstUpdateBy(userM.getUserName());		
	}
	public void doNPL_LPM(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
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
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_NPL_LPM);
	}
	public void doAMC_TAMC_COMPANY(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
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
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY);
	}
	public void doBANKRUPTCY_COMPANY(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
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
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY);
	}
	public void doFRAUD_COMPANY(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setFraudCompanyResult(null);
		xrulesVerM.setFraudCompanyCode(null);
		xrulesVerM.setFraudCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setFraudCompanyUpdateBy(userM.getUserName());	
		xrulesVerM.setFraudCompanySearchType(null);
		xrulesVerM.setXrulesFraudCompVect(null);
		xrulesVerM.setFraudCompanyDecision(null);
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FRAUD_COMPANY);
	}
	public void doLISTED_COMPANY(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setListedCompanyResult(null);
		xrulesVerM.setListedCompanyCode(null);
		xrulesVerM.setListedCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setListedCompanyUpdateBy(userM.getUserName());	
		xrulesVerM.setxRulesListedCompanyDataMs(null);
	}
	public void doKBANK_EMPLOYEE(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setkBankEmployeeResult(null);
		xrulesVerM.setkBankEmployeeCode(null);
		xrulesVerM.setkBankEmployeeUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setkBankEmployeeUpdateBy(userM.getUserName());	
	}
	public void doPAY_ROLL(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setPayrollResult(null);
		xrulesVerM.setPayrollCode(null);
		xrulesVerM.setPayrollUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setPayrollUpdateBy(userM.getUserName());	
		xrulesVerM.setxRulesPayRolls(null);	
	}
	public void doBEHAVIOR_RISK_GRADE(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setCodeServiceBehaviorScore(null);
		xrulesVerM.setBehaviorRiskGradeResult(null);
		xrulesVerM.setBehaviorRiskGradeCode(null);
		xrulesVerM.setBehaviorRiskGradeUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBehaviorRiskGradeUpdateBy(userM.getUserName());	
		xrulesVerM.setxRulesBScoreVect(null);
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BEHAVIOR_RISK_GRADE);
	}
	public void doWATCHLIST_FRAUD(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setWatchListResult(null);
		xrulesVerM.setWatchListCode(null);
		xrulesVerM.setWatchListUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setWatchListUpdateBy(userM.getUserName());	
		xrulesVerM.setxRulesFraudDataMs(null);
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WATCH_LIST_FRAUD);
	}
	public void doAMC_TAMC(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		xrulesVerM.setAmcTamc(null);
		eaiM.setAmcTamc(null);		
		xrulesVerM.setEditAmcTamc(null);
		eaiM.setEditAmcTamc(null);			
		xrulesVerM.setCodeServiceAmcTamc(null);
		eaiM.setCodeServiceAmcTamc(null);		
		xrulesVerM.setAmcTamcResult(null);
		xrulesVerM.setAmcTamcCode(null);
		xrulesVerM.setAmcTamcUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setAmcTamcUpdateBy(userM.getUserName());	
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC);
	}
	public void doBANKRUPTCY(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}
		xrulesVerM.setBankRuptcy(null);
		eaiM.setBankRuptcy(null);		
		xrulesVerM.setEditBankruptcy(null);
		eaiM.setEditBankruptcy(null);			
		xrulesVerM.setCodeServiceBankRuptcy(null);
		eaiM.setCodeServiceBankRuptcy(null);		
		xrulesVerM.setBankruptcyResult(null);
		xrulesVerM.setBankruptcyCode(null);
		xrulesVerM.setBankruptcyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBankruptcyUpdateBy(userM.getUserName());		
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY);
	}
	public void doSANCTION_LIST(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setSanctionListResult(null);
		xrulesVerM.setSanctionListResultCode(null);
		xrulesVerM.setSanctionListUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setSanctionListUpdateBy(userM.getUserName());	
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SANCTION_LIST);
	}
	public void doSP(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setSpResult(null);
		xrulesVerM.setSpResultCode(null);
		xrulesVerM.setSpUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setSpUpdateBy(userM.getUserName());
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SP);
	}
	public void doCLASSIFY_LEVEL_LPM(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){		
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}		
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
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CLASSIFY_LEVEL_LPM);
	}
	public void doKEC_BLOCKCODE(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){		
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}		
		xrulesVerM.setBlockCodeKecCode(null);
		xrulesVerM.setBlockCodeKecResult(null);
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
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_KEC_BLOCK_CODE);
	}
	public void doCREDIT_CARD_BLOCKCODE(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setBlockCodeccCode(null);
		xrulesVerM.setBlockCodeccResult(null);
		xrulesVerM.setBlockCodeccUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBlockCodeccUpdateBy(userM.getUserName());	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}		
		xrulesVerM.setBlockCodeCc(null);
		eaiM.setBlockCodeCc(null);		
		xrulesVerM.setEditBlockCode(null);
		eaiM.setEditBlockCode(null);			
		xrulesVerM.setCodeServiceBlockcode(null);
		eaiM.setCodeServiceBlockcode(null);				
		xrulesVerM.setcCCurrentBalance(null);
		xrulesVerM.setcCLastPaymentDate(null);
		eaiM.setcCCurrentBalance(null);
		eaiM.setcCLastPaymentDate(null);
		xrulesVerM.setCodeCCBlockCode(null);
		eaiM.setCodeCCBlockCode(null);		
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CREDIT_CARD_BLOCK_CODE);
		
	}
	public void doEXISTING_KEC(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setExistCustCode(null);
		xrulesVerM.setExistCustResult(null);
		xrulesVerM.setExistingCustUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setExistCustUpdateBy(userM.getUserName());	
		EAIDataM eaiM = xrulesVerM.getEaiM();
		if(null == eaiM){
			eaiM = new EAIDataM();
			xrulesVerM.setEaiM(eaiM);
		}		
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
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC);
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_ALREADY_HAVE_KEC_POD);		
	}
	public void doDEPLICATE_APPLICATION(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setDedupCode(null);
		xrulesVerM.setDedupResult(null);
		xrulesVerM.setDedupUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setDedupUpdateBy(userM.getUserName());
		xrulesVerM.setVXRulesDedupDataM(null);
		xrulesVerM.setDupInProcessFlag(null);
		xrulesVerM.setDupAscendancyFlag(null);
		xrulesVerM.setDupRejectCancleFlag(null);
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_EXISTING_APPLICATION);
	}
	public void doDEMOGRAPHIC(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setDemographicCode(null);
		xrulesVerM.setDemographicResult(null);
		xrulesVerM.setAgeVerCode(null);
		xrulesVerM.setAgeVerResult(null);
		xrulesVerM.setNationalityVerCode(null);
		xrulesVerM.setNationalityVerResult(null);
		xrulesVerM.setDemographicUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_NATIONALITY);
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_AGE);	
		xrulesVerM.setDemographicUpdateBy(userM.getUserName());		
	}
	public void doSPECIAL_CUSTOMER(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setSpecialCusCode(null);
		xrulesVerM.setSpecialCusResult(null);
		xrulesVerM.setSpecialCusUpdateBy(userM.getUserName());
		xrulesVerM.setSpecialCusUpdateDate(new Timestamp(new Date().getTime()));		
	}
	public void doSoloView(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setSoloViewResultCode(null);
		xrulesVerM.setSoloViewResultDesc(null);
		xrulesVerM.setSoloViewUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setSoloViewUpdateBy(userM.getUserName());
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SOLO_VIEW);
	}
	public void doLEC(PLXRulesVerificationResultDataM xrulesVerM,UserDetailM userM){
		xrulesVerM.setLecResultCode(null);
		xrulesVerM.setLecResultDesc(null);
		xrulesVerM.setLecUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setLecUpdateBy(userM.getUserName());
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_LEC);
	}
}
