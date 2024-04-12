package com.eaf.orig.ulo.pl.app.utility;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.ulo.pl.model.IncomeDebtDataM;
import com.eaf.xrules.ulo.pl.model.PLApplicationScoreDataM;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesKECOverideResultDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesRecommendCreditlineDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class ILOGExecuteRule {
	static Logger logger = Logger.getLogger(ILOGExecuteRule.class);
	public void ILOGVerifyCustomer(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service VerifyCustomer ServiceID "+PLXrulesConstant.ModuleService.VERIFY_CUSTOMER);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.VERIFY_CUSTOMER, userM);
			XrulesResponseDataM response = null;			
			if(OrigUtil.isEmptyString(xrulesVerM.getVerCusResultCode()) 
					|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerCusResultCode())
						    || PLXrulesConstant.ResultCode.CODE_REQUIRED.equals(xrulesVerM.getVerCusResultCode())){
				logger.debug("Not Found Result VerCusResultCode Call ILOG !! ");
				response = xRulesService.ILOGServiceModule(request);
				if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){					
					
					logger.debug("Execute ILOG Service VerifyCustomer Success !! ");
					
					if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(response.getResultCode())){
						xrulesVerM.setVerCusResultCode(response.getResultCode());
						xrulesVerM.setVerCusResult(response.getResultDesc());
						xrulesVerM.setVerCusUpdateBy(userM.getUserName());
						xrulesVerM.setVerCusUpdateDate(new Timestamp(new Date().getTime()));
					}else{
						if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerCusResultCode())){
							xrulesVerM.setVerCusResultCode(null);
							xrulesVerM.setVerCusResult(null);
							xrulesVerM.setVerCusUpdateBy(userM.getUserName());
							xrulesVerM.setVerCusUpdateDate(new Timestamp(new java.util.Date().getTime()));
						}
					}
					
					/* Set Policy rules*/
					PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
//					if(xrulesPolicy==null){
//						 xrulesPolicy=new PLXRulesPolicyRulesDataM();
//						 xrulesPolicy.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
//						 xrulesVerM.getvXRulesPolicyRulesDataM().add(xrulesPolicy);
//					}
					xrulesPolicyM.setResultCode(xrulesVerM.getVerCusResultCode());
					xrulesPolicyM.setResultDesc(xrulesVerM.getVerCusResult());	
					xrulesPolicyM.setUpdateBy(userM.getUserName());					 
					/*End Set Policy Rules */
										
				}else{
					logger.error("Execute ILOG Fail !! throw ERROR");					
					throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
				}
			}
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGVerifyHR(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service VerifyHR ServiceID "+PLXrulesConstant.ModuleService.VERIFY_HR);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.VERIFY_HR, userM);
			XrulesResponseDataM response = null;			
			if(OrigUtil.isEmptyString(xrulesVerM.getVerHRResultCode())
					|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerHRResultCode())
							|| PLXrulesConstant.ResultCode.CODE_REQUIRED.equals(xrulesVerM.getVerHRResultCode())){
				logger.debug("Not Found Result VerHRResultCode Call ILOG !! ");
				response = xRulesService.ILOGServiceModule(request);
				if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
					
					logger.debug("Execute ILOG Service VerifyHR Success !! ");
					if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(response.getResultCode())){
						xrulesVerM.setVerHRResultCode(response.getResultCode());
						xrulesVerM.setVerHRResult(response.getResultDesc());
						xrulesVerM.setVerHRUpdateBy(userM.getUserName());
						xrulesVerM.setVerHRUpdateDate(new Timestamp(new Date().getTime()));
					}else{
						if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerHRResultCode())){
							xrulesVerM.setVerHRResultCode(null);
							xrulesVerM.setVerHRResult(null);
							xrulesVerM.setVerHRUpdateBy(userM.getUserName());
							xrulesVerM.setVerHRUpdateDate(new Timestamp(new Date().getTime()));
						}
					}
					/* Set Policy rules*/
					PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
//					 if(xrulesPolicy==null){
//						 xrulesPolicy=new PLXRulesPolicyRulesDataM();
//						 xrulesPolicy.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
//						 xrulesVerM.getvXRulesPolicyRulesDataM().add(xrulesPolicy);
//					 }
					xrulesPolicyM.setResultCode(xrulesVerM.getVerHRResultCode());
					xrulesPolicyM.setResultDesc(xrulesVerM.getVerHRResult());	
					xrulesPolicyM.setUpdateBy(userM.getUserName());
					/*End Set Policy Rules */
				}else{
					logger.error("Execute ILOG Fail !! throw ERROR");
					throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
				}
			}
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGBeScore(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service BeScore ServiceID "+PLXrulesConstant.ModuleService.BSCORE);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.BSCORE, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service BeScore Success !! ");
				xrulesVerM.setbScoreCode(response.getResultCode());
				xrulesVerM.setbScoreResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_B_SCORE);
//				if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_B_SCORE);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getbScoreCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getbScoreResult());	
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
				
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGApplicationScore(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service ApplicationScore ServiceID "+PLXrulesConstant.ModuleService.APPLICATION_SCORE);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.APPLICATION_SCORE, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service ApplicationScore Success !! ");
				PLApplicationScoreDataM appScore = (PLApplicationScoreDataM) response.getObj();
				if(null == appScore) appScore = new PLApplicationScoreDataM();
				xrulesVerM.setAppScore(appScore.getAppScore());
				xrulesVerM.setAppScoreCutOffResult(appScore.getAppScoreCutOffResult());
				xrulesVerM.setAppScoreCutOffResultCode(appScore.getAppScoreCutOffResultCode());
				xrulesVerM.setAppScoreResult(appScore.getAppScoreResult());
				xrulesVerM.setAppScoreResultCode(appScore.getAppScoreResultCode());
				
				xrulesVerM.setCoarseRiskCode(appScore.getCoarseRiskCode());
				xrulesVerM.setCoarseRiskDesc(appScore.getRiskGrade());
				xrulesVerM.setFineRiskCode(appScore.getFineRiskCode());
				
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APP_SCORE);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_APP_SCORE);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getAppScoreResultCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getAppScoreResult());		
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGFICOScore(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service FicoScore ServiceID "+PLXrulesConstant.ModuleService.FICO_SCORE);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.FICO_SCORE, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service FicoScore Success !! ");
				xrulesVerM.setFicoCode(response.getResultCode());
				xrulesVerM.setFicoResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FICO_SCORE);
//				#septemwi comment
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_FICO_SCORE);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getFicoCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getFicoResult());			
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
				
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGDssKbank(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service Dss Kbank ServiceID "+PLXrulesConstant.ModuleService.DSS);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.DSS, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service Dss Kbank Success !! ");
				xrulesVerM.setWebDssKbankCode(response.getResultCode());
				xrulesVerM.setWebDssKbankResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_DSS_KBANK);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_DSS_KBANK);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getWebDssKbankCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getWebDssKbankResult());	
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGSSO(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service SSO ServiceID "+PLXrulesConstant.ModuleService.SSO);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.SSO, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service SSO Success !! ");
				xrulesVerM.setWebSsoCode(response.getResultCode());
				xrulesVerM.setWebSsoResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SSO);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_SSO);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getWebSsoCode() );
				xrulesPolicyM.setResultDesc(xrulesVerM.getWebSsoResult());	
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
				
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	
	public void ILOGRD(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service RD ServiceID "+PLXrulesConstant.ModuleService.RD);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.RD, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service RD Success !! ");
				xrulesVerM.setWebRdCode(response.getResultCode());
				xrulesVerM.setWebRdResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_RD);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_RD);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getWebRdCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getWebRdResult());
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGTOT(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service TOT ServiceID "+PLXrulesConstant.ModuleService.TOT);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.TOT, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service TOT Success !! ");
				xrulesVerM.setWebTotCode(response.getResultCode());
				xrulesVerM.setWebTotResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_TOT);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_TOT);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getWebTotCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getWebTotResult());
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGPhoneVer(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service TELEPHONE ServiceID "+PLXrulesConstant.ModuleService.TELEPHONE);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.TELEPHONE, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service TELEPHONE Success !! ");
				xrulesVerM.setPhoneVerCode(response.getResultCode());
				xrulesVerM.setPhoneVerResult(response.getResultDesc());
				
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_TOT_NUMBER);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_TOT_NUMBER);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getPhoneVerCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getPhoneVerResult());		
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGYearOfWork(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service YEAR_OF_WORK ServiceID "+PLXrulesConstant.ModuleService.YEAR_OF_WORK);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.YEAR_OF_WORK, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service YEAR_OF_WORK Success !! ");
				xrulesVerM.setWorkingAgeResultCode(response.getResultCode());
				xrulesVerM.setWorkingAgeResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WORK_AGE);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_WORK_AGE);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getWorkingAgeResultCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getWorkingAgeResult());		
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
				
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGOccupation(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service OCCUPATION ServiceID "+PLXrulesConstant.ModuleService.OCCUPATION);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.OCCUPATION, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service OCCUPATION Success !! ");
				xrulesVerM.setOccupationApplicantCode(response.getResultCode());
				xrulesVerM.setOccupationApplicantResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_JOB);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_APPLICANT_JOB);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getOccupationApplicantCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getOccupationApplicantResult());	
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
				
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGChequeReturn(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service ChequeReturn ServiceID "+PLXrulesConstant.ModuleService.RETURN_CHECQE);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.RETURN_CHECQE, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service ChequeReturn Success !! ");
				xrulesVerM.setChequeReturnCode(response.getResultCode());
				xrulesVerM.setChequeReturnResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CHEQUE_RETURN_HISTORY);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_CHEQUE_RETURN_HISTORY);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getChequeReturnCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getChequeReturnResult());	
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGFaultCondition(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service FaultCondition ServiceID "+PLXrulesConstant.ModuleService.HISTORY_BAD_CODITION);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.HISTORY_BAD_CODITION, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service FaultCondition Success !! ");
				xrulesVerM.setFaultConditionCode(response.getResultCode());
				xrulesVerM.setFaultConditionResult(response.getResultDesc());
				/* Set Policy rules*/
				PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WRONG_RESTRICTION_HISTORY);
//				 if(plXrulesPolicyRules==null){
//					 plXrulesPolicyRules=new PLXRulesPolicyRulesDataM();
//					 plXrulesPolicyRules.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_WRONG_RESTRICTION_HISTORY);
//					 xrulesVerM.getvXRulesPolicyRulesDataM().add(plXrulesPolicyRules);
//				 }
				xrulesPolicyM.setResultCode(xrulesVerM.getFaultConditionCode());
				xrulesPolicyM.setResultDesc(xrulesVerM.getFaultConditionResult());	
				xrulesPolicyM.setUpdateBy(userM.getUserName());
				/*End Set Policy Rules */
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	
	public String ILOGRecommendCreditLine(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service RecommendCreditLine ServiceID "+PLXrulesConstant.ModuleService.RECOMMEND_CREDIT_LINE);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		String appDecision = null;
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.RECOMMEND_CREDIT_LINE, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ExcuteRecommendCreditLine(request);
			
			if(null != response){
				logger.debug("Execute ILOG Service RecommendCreditLine Success !! ");
				
				if (XRulesConstant.ModuleDecisionResult.RESULT_C.equals(response.getResultCode())) {
					appDecision = OrigConstant.Action.CANCEL;
				}else if (XRulesConstant.ModuleDecisionResult.RESULT_RJ.equals(response.getResultCode())) {
					appDecision = OrigConstant.Action.REJECT;
				}
				
				PLXRulesRecommendCreditlineDataM recommentCreditLine = (PLXRulesRecommendCreditlineDataM) response.getObj();
				if(null == recommentCreditLine) recommentCreditLine = new PLXRulesRecommendCreditlineDataM();
				
				xrulesVerM.setCreditLineResultCode(recommentCreditLine.getCreditLineResultCode());
				xrulesVerM.setCreditLineResultDesc(recommentCreditLine.getCreditLineResultDesc());
				xrulesVerM.setCreditLineUpdateBy(userM.getUserName());
				xrulesVerM.setCreditLineUpdateDate(new Timestamp(new Date().getTime()));
				
				PLXRulesPolicyRulesDataM creditLineRule = new PLXRulesPolicyRulesDataM();			
				creditLineRule.setPersonalID(personalM.getPersonalID());
				creditLineRule.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE);
				creditLineRule.setResultCode(recommentCreditLine.getCreditLineResultCode());
				creditLineRule.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));				
				creditLineRule.setUpdateBy(userM.getUserName());
				creditLineRule.setResultDesc(recommentCreditLine.getCreditLineResultDesc());
				
				xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE, creditLineRule);
				
				logger.debug("RecommendCreditLine ... " + recommentCreditLine.getRecommendCreditLine());
				logger.debug("ScoringCreditLine ... " + recommentCreditLine.getScoringCreditLine());
				logger.debug("ExceptionCreditLine ... " + recommentCreditLine.getExceptionCreditLine());
				
				applicationM.setRecommentCreditLine(recommentCreditLine.getRecommendCreditLine());
				applicationM.setScoringCreditLine(recommentCreditLine.getScoringCreditLine());
				applicationM.setExceptionCreditLine(recommentCreditLine.getExceptionCreditLine());
				applicationM.setFinalCreditLimit(recommentCreditLine.getRecommendCreditLine());
				//20130118 Sankom add set Customer Group
				applicationM.setCustomerGroup(recommentCreditLine.getCustomerGroup());
				return appDecision;
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	
	public void ILOGIncomeDebt(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service IncomeDebt ServiceID "+PLXrulesConstant.ModuleService.INCOME_DEBT);
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
		PLXRulesDebtBdDataM debtBDM = xrulesVerM.getXRulesDebtBdDataM();
		if (null == debtBDM) {
			debtBDM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(debtBDM);
		}
		debtBDM.setIncludeKEC(true);
		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM, PLXrulesConstant.ModuleService.INCOME_DEBT, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ILOGServiceModule(request);
			
			if(null != response && PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
				logger.debug("Execute ILOG Service IncomeDebt Success !! ");
				IncomeDebtDataM incomeDebtM = (IncomeDebtDataM) response.getObj();
				xrulesVerM.setDEBT_BDResult(incomeDebtM.getDebtBurdenResult());
				xrulesVerM.setDebtBdCode(incomeDebtM.getDebtBurdenCode());
				debtBDM.setDebtBurdentScoreAdjust(incomeDebtM.getDebtBurdentScore());
				
				// Set policy rules
				PLXRulesPolicyRulesDataM policyRulesDebt=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);

				policyRulesDebt.setResultCode(incomeDebtM.getDebtBurdenCode());
				policyRulesDebt.setResultDesc(incomeDebtM.getDebtBurdenResult());			
				policyRulesDebt.setUpdateBy(xrulesVerM.getDebtBdUpdateBy());
				 
				PLXRulesPolicyRulesDataM policyRulesIncome = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);

				policyRulesIncome.setResultCode(incomeDebtM.getTotalIncomeCode());
				policyRulesIncome.setResultDesc(incomeDebtM.getTotalIncomeResult());			
				policyRulesIncome.setUpdateBy(xrulesVerM.getDebtBdUpdateBy());
				// End set policy rules
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	
	public void ILOGKECOverideRules(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service KECOverideRules ");
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
				
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM,null, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ExecuteKECOverideRules(request);
			
			if(null != response){
				logger.debug("Execute ILOG Service KECOverideRules Success !! ");
				PLXRulesKECOverideResultDataM kecOrResult = (PLXRulesKECOverideResultDataM) response.getObj();
				if (null != kecOrResult) {
					applicationM.setJobType(kecOrResult.getColor());

					xrulesVerM.setSummaryOverideRuleCode(kecOrResult.getSummaryOrResultCode());
					xrulesVerM.setSummaryOverideRuleResult(kecOrResult.getSummaryOrResult());
					
					logger.debug("RecommendResultCode ..." + kecOrResult.getRecommendResultCode());
					logger.debug("RecommendResult ..." + kecOrResult.getRecommendResult());
					
					xrulesVerM.setRecommendResult(kecOrResult.getRecommendResultCode());
				}
				xrulesVerM.setSummaryOverideRuleUpdateBy(userM.getUserName());
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	
	public void ILOGDCIKECOverideRules(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service KECOverideRules ");
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
				
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM,null, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ExecuteKECOverideRules(request);
			
			if(null != response){
				logger.debug("Execute ILOG Service KECOverideRules Success !! ");
				PLXRulesKECOverideResultDataM kecOrResult = (PLXRulesKECOverideResultDataM) response.getObj();
				if (null != kecOrResult) {
					applicationM.setJobType(kecOrResult.getColor());

					xrulesVerM.setSummaryOverideRuleCode(kecOrResult.getSummaryOrResultCode());
					xrulesVerM.setSummaryOverideRuleResult(kecOrResult.getSummaryOrResult());
					
					logger.debug("RecommendResultCode ..." + kecOrResult.getRecommendResultCode());
					logger.debug("RecommendResult ..." + kecOrResult.getRecommendResult());
					
					xrulesVerM.setRecommendResult(kecOrResult.getRecommendResultCode());
					if (OrigUtil.isEmptyString(kecOrResult.getColor())) {
						applicationM.setJobType("G");
						xrulesVerM.setSummaryOverideRuleCode("AC");
						xrulesVerM.setSummaryOverideRuleResult("ACCEPT");
						xrulesVerM.setRecommendResult("AC");
					}			
					
				}
				xrulesVerM.setSummaryOverideRuleUpdateBy(userM.getUserName());
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
	public void ILOGVCKECOverideRules(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		logger.debug("Execute ILOG Service KECOverideRules ");
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}	
				
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
		try{
			ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesRequestDataM request = xrulesTool.MapXrulesRequestDataM(applicationM,null, userM);
			XrulesResponseDataM response = null;
			response = xRulesService.ExecuteKECOverideRules(request);
			
			if(null != response){
				logger.debug("Execute ILOG Service KECOverideRules Success !! ");
				PLXRulesKECOverideResultDataM kecOrResult = (PLXRulesKECOverideResultDataM) response.getObj();
				if (null != kecOrResult) {
					applicationM.setJobType(kecOrResult.getColor());

					xrulesVerM.setSummaryOverideRuleCode(kecOrResult.getSummaryOrResultCode());
					xrulesVerM.setSummaryOverideRuleResult(kecOrResult.getSummaryOrResult());
					
					logger.debug("RecommendResultCode ..." + kecOrResult.getRecommendResultCode());
					logger.debug("RecommendResult ..." + kecOrResult.getRecommendResult());
					
					xrulesVerM.setRecommendResult(kecOrResult.getRecommendResultCode());
					if (OrigUtil.isEmptyString(kecOrResult.getColor())) {
						applicationM.setJobType("G");
						xrulesVerM.setSummaryOverideRuleCode("AC");
						xrulesVerM.setSummaryOverideRuleResult("ACCEPT");
						xrulesVerM.setRecommendResult("AC");
					}			
					
				}
				xrulesVerM.setSummaryOverideRuleUpdateBy(userM.getUserName());
			}else{
				logger.error("Execute ILOG Fail !! throw ERROR");
				throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
			}			
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}
	}
}
