package com.eaf.orig.ulo.pl.app.utility;

//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.HashMap;

import org.apache.log4j.Logger;

//import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.orig.ulo.pl.model.app.personal.PLPersonalInfoDataM;
//import com.eaf.xrules.shared.constant.XRulesConstant;
//import com.eaf.xrules.ulo.pl.constant.PLXrulesConstant;
//import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
//import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
//import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
//import com.ilog.service.model.response.ILOGResult;
//import com.ilog.service.model.response.ILOGServiceResponse;
//
public class ILOGMapData {
	
	static Logger logger = Logger.getLogger(ILOGMapData.class);
//	
//	public void map (int action ,PLApplicationDataM applicationM, UserDetailM userM,HashMap<String, String> result,ILOGServiceResponse response) throws Exception{
//		ILOGResult[] results = response.getResults();
//		for(ILOGResult ilogResult : results){
//			switch (Integer.valueOf(ilogResult.getServiceID())) {
//				case PLXrulesConstant.ModuleService.VERIFY_CUSTOMER:	
//					ILOGVerifyCustomer(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.VERIFY_HR:	
//					ILOGVerifyHR(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.BSCORE:	
//					ILOGBeScore(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.APPLICATION_SCORE:	
//					ILOGApplicationScore(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.FICO_SCORE:	
//					ILOGFICOScore(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.DSS:	
//					ILOGDssKbank(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.SSO:	
//					ILOGSSO(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.RD:	
//					ILOGRD(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.TOT:	
//					ILOGTOT(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.TELEPHONE:	
//					ILOGPhoneVer(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.YEAR_OF_WORK:	
//					ILOGYearOfWork(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.OCCUPATION:	
//					ILOGOccupation(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.RETURN_CHECQE:	
//					ILOGChequeReturn(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.HISTORY_BAD_CODITION:	
//					ILOGFaultCondition(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.RECOMMEND_CREDIT_LINE:
//					String recommendDecision = ILOGRecommendCreditLine(applicationM, userM, ilogResult);
//					result.put("RECOMMEND_DECISION", recommendDecision);
//					break;
//				case PLXrulesConstant.ModuleService.INCOME_DEBT:	
//					ILOGIncomeDebt(applicationM, userM, ilogResult);
//					break;
//				case PLXrulesConstant.ModuleService.ILOGKECOverideRules:	
//					if(OrigConstant.ILOGAction.DC_ACTION == action){
//						ILOGKECOverideRules(applicationM, userM, ilogResult);
//					}else if(OrigConstant.ILOGAction.DCI_ACTION == action){
//						ILOGDCIKECOverideRules(applicationM, userM, ilogResult);
//					}else if(OrigConstant.ILOGAction.VC_ACTION == action){
//						ILOGVCKECOverideRules(applicationM, userM, ilogResult);
//					}else{
//						ILOGKECOverideRules(applicationM, userM, ilogResult);
//					}
//					break;
//				default:
//					break;
//			}
//		}
//	}
//	
//	public void ILOGVerifyCustomer(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service VerifyCustomer ServiceID "+PLXrulesConstant.ModuleService.VERIFY_CUSTOMER);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());	
//		
//		if(OrigUtil.isEmptyString(xrulesVerM.getVerCusResultCode()) 
//				|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerCusResultCode())
//					    || PLXrulesConstant.ResultCode.CODE_REQUIRED.equals(xrulesVerM.getVerCusResultCode())){
//				
//				if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(response.getResultCode())){
//					xrulesVerM.setVerCusResultCode(response.getResultCode());
//					xrulesVerM.setVerCusResult(response.getResultDesc());
//					xrulesVerM.setVerCusUpdateBy(userM.getUserName());
//					xrulesVerM.setVerCusUpdateDate(new Timestamp(new Date().getTime()));
//				}else{
//					if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerCusResultCode())){
//						xrulesVerM.setVerCusResultCode(null);
//						xrulesVerM.setVerCusResult(null);
//						xrulesVerM.setVerCusUpdateBy(userM.getUserName());
//						xrulesVerM.setVerCusUpdateDate(new Timestamp(new java.util.Date().getTime()));
//					}
//				}
//				
//				PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
//
//				xrulesPolicyM.setResultCode(xrulesVerM.getVerCusResultCode());
//				xrulesPolicyM.setResultDesc(xrulesVerM.getVerCusResult());	
//				xrulesPolicyM.setUpdateBy(userM.getUserName());
//
//		}
//	}
//	
//	public void ILOGVerifyHR(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service VerifyHR ServiceID "+PLXrulesConstant.ModuleService.VERIFY_HR);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}				
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		if(OrigUtil.isEmptyString(xrulesVerM.getVerHRResultCode())
//				|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerHRResultCode())
//						|| PLXrulesConstant.ResultCode.CODE_REQUIRED.equals(xrulesVerM.getVerHRResultCode())){
//				
//				if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(response.getResultCode())){
//					xrulesVerM.setVerHRResultCode(response.getResultCode());
//					xrulesVerM.setVerHRResult(response.getResultDesc());
//					xrulesVerM.setVerHRUpdateBy(userM.getUserName());
//					xrulesVerM.setVerHRUpdateDate(new Timestamp(new Date().getTime()));
//				}else{
//					if(PLXrulesConstant.ResultCode.CODE_WAIVED.equals(xrulesVerM.getVerHRResultCode())){
//						xrulesVerM.setVerHRResultCode(null);
//						xrulesVerM.setVerHRResult(null);
//						xrulesVerM.setVerHRUpdateBy(userM.getUserName());
//						xrulesVerM.setVerHRUpdateDate(new Timestamp(new Date().getTime()));
//					}
//				}
//				
//				PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
//					xrulesPolicyM.setResultCode(xrulesVerM.getVerHRResultCode());
//					xrulesPolicyM.setResultDesc(xrulesVerM.getVerHRResult());	
//					xrulesPolicyM.setUpdateBy(userM.getUserName());
//		}
//	}
//	
//	public void ILOGBeScore(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service BeScore ServiceID "+PLXrulesConstant.ModuleService.BSCORE);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setbScoreCode(response.getResultCode());
//		xrulesVerM.setbScoreResult(response.getResultDesc());
//		
//		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_B_SCORE);
//			xrulesPolicyM.setResultCode(xrulesVerM.getbScoreCode());
//			xrulesPolicyM.setResultDesc(xrulesVerM.getbScoreResult());	
//			xrulesPolicyM.setUpdateBy(userM.getUserName());
//	}
//	
//	public void ILOGApplicationScore(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service ApplicationScore ServiceID "+PLXrulesConstant.ModuleService.APPLICATION_SCORE);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		logger.debug("AppScore : "+response.getAppScore());
//		logger.debug("AppScoreCutOffResult : "+response.getAppScoreCutOffResult());
//		logger.debug("AppScoreCutOffResultCode : "+response.getAppScoreCutOffResultCode());
//		logger.debug("AppScoreResult : "+response.getAppScoreResult());
//		logger.debug("AppScoreResultCode : "+response.getAppScoreResultCode());
//		logger.debug("CoarseRiskCode : "+response.getCoarseRiskCode());
//		logger.debug("RiskGrade : "+response.getRiskGrade());
//		logger.debug("FineRiskCode : "+response.getFineRiskCode());
//		
//		xrulesVerM.setAppScore(response.getAppScore());
//		xrulesVerM.setAppScoreCutOffResult(response.getAppScoreCutOffResult());
//		xrulesVerM.setAppScoreCutOffResultCode(response.getAppScoreCutOffResultCode());
//		xrulesVerM.setAppScoreResult(response.getAppScoreResult());
//		xrulesVerM.setAppScoreResultCode(response.getAppScoreResultCode());
//		
//		xrulesVerM.setCoarseRiskCode(response.getCoarseRiskCode());
//		xrulesVerM.setCoarseRiskDesc(response.getRiskGrade());
//		xrulesVerM.setFineRiskCode(response.getFineRiskCode());
//		
//		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APP_SCORE);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getAppScoreResultCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getAppScoreResult());		
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//			
//	}
//	
//	public void ILOGFICOScore(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service FicoScore ServiceID "+PLXrulesConstant.ModuleService.FICO_SCORE);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}		
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setFicoCode(response.getResultCode());
//		xrulesVerM.setFicoResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FICO_SCORE);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getFicoCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getFicoResult());			
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//	}
//	
//	public void ILOGDssKbank(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service Dss Kbank ServiceID "+PLXrulesConstant.ModuleService.DSS);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setWebDssKbankCode(response.getResultCode());
//		xrulesVerM.setWebDssKbankResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_DSS_KBANK);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getWebDssKbankCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getWebDssKbankResult());	
//		xrulesPolicyM.setUpdateBy(userM.getUserName());			
//	}
//	
//	public void ILOGSSO(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service SSO ServiceID "+PLXrulesConstant.ModuleService.SSO);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setWebSsoCode(response.getResultCode());
//		xrulesVerM.setWebSsoResult(response.getResultDesc());
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SSO);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getWebSsoCode() );
//		xrulesPolicyM.setResultDesc(xrulesVerM.getWebSsoResult());	
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//			
//	}
//	
//	public void ILOGRD(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service RD ServiceID "+PLXrulesConstant.ModuleService.RD);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setWebRdCode(response.getResultCode());
//		xrulesVerM.setWebRdResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_RD);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getWebRdCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getWebRdResult());
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//	}
//	
//	public void ILOGTOT(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service TOT ServiceID "+PLXrulesConstant.ModuleService.TOT);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}		
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setWebTotCode(response.getResultCode());
//		xrulesVerM.setWebTotResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_TOT);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getWebTotCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getWebTotResult());
//		xrulesPolicyM.setUpdateBy(userM.getUserName());		
//	}
//	
//	public void ILOGPhoneVer(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service TELEPHONE ServiceID "+PLXrulesConstant.ModuleService.TELEPHONE);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}		
//
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setPhoneVerCode(response.getResultCode());
//		xrulesVerM.setPhoneVerResult(response.getResultDesc());
//		
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_TOT_NUMBER);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getPhoneVerCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getPhoneVerResult());		
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//	}
//	
//	public void ILOGYearOfWork(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service YEAR_OF_WORK ServiceID "+PLXrulesConstant.ModuleService.YEAR_OF_WORK);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setWorkingAgeResultCode(response.getResultCode());
//		xrulesVerM.setWorkingAgeResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WORK_AGE);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getWorkingAgeResultCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getWorkingAgeResult());		
//		xrulesPolicyM.setUpdateBy(userM.getUserName());			
//	}
//	
//	public void ILOGOccupation(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("Execute ILOG Service OCCUPATION ServiceID "+PLXrulesConstant.ModuleService.OCCUPATION);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}		
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setOccupationApplicantCode(response.getResultCode());
//		xrulesVerM.setOccupationApplicantResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_APPLICANT_JOB);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getOccupationApplicantCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getOccupationApplicantResult());	
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//	}
//	
//	public void ILOGChequeReturn(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service ChequeReturn ServiceID "+PLXrulesConstant.ModuleService.RETURN_CHECQE);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}		
//
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setChequeReturnCode(response.getResultCode());
//		xrulesVerM.setChequeReturnResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CHEQUE_RETURN_HISTORY);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getChequeReturnCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getChequeReturnResult());	
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//		
//	}
//	
//	public void ILOGFaultCondition(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service FaultCondition ServiceID "+PLXrulesConstant.ModuleService.HISTORY_BAD_CODITION);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}
//		
//		logger.debug("Result Code : "+response.getResultCode());
//		logger.debug("Result Desc : "+response.getResultDesc());
//		
//		xrulesVerM.setFaultConditionCode(response.getResultCode());
//		xrulesVerM.setFaultConditionResult(response.getResultDesc());
//
//		PLXRulesPolicyRulesDataM xrulesPolicyM=xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_WRONG_RESTRICTION_HISTORY);
//
//		xrulesPolicyM.setResultCode(xrulesVerM.getFaultConditionCode());
//		xrulesPolicyM.setResultDesc(xrulesVerM.getFaultConditionResult());	
//		xrulesPolicyM.setUpdateBy(userM.getUserName());
//		
//	}
//	
//	public String ILOGRecommendCreditLine(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service RecommendCreditLine ServiceID "+PLXrulesConstant.ModuleService.RECOMMEND_CREDIT_LINE);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		String appDecision = null;
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}
//				
//		if (XRulesConstant.ModuleDecisionResult.RESULT_C.equals(response.getResultCode())) {
//			appDecision = OrigConstant.Action.CANCEL;
//		}else if (XRulesConstant.ModuleDecisionResult.RESULT_RJ.equals(response.getResultCode())) {
//			appDecision = OrigConstant.Action.REJECT;
//		}
//		
//		logger.debug("ResultCode : "+response.getResultCode());
//		logger.debug("ResultDesc : "+response.getResultDesc());
//		
//		logger.debug("CreditLineResultCode : "+response.getCreditLineResultCode());
//		logger.debug("CreditLineResultDesc : "+response.getCreditLineResultDesc());
//		
//		xrulesVerM.setCreditLineResultCode(response.getCreditLineResultCode());
//		xrulesVerM.setCreditLineResultDesc(response.getCreditLineResultDesc());
//		xrulesVerM.setCreditLineUpdateBy(userM.getUserName());
//		xrulesVerM.setCreditLineUpdateDate(new Timestamp(new Date().getTime()));
//		
//		PLXRulesPolicyRulesDataM creditLineRule = new PLXRulesPolicyRulesDataM();			
//		creditLineRule.setPersonalID(personalM.getPersonalID());
//		creditLineRule.setPolicyRulesId(OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE);
//		creditLineRule.setResultCode(response.getCreditLineResultCode());
//		creditLineRule.setUpdateDate(DataFormatUtility.parseTimestamp(new Date()));				
//		creditLineRule.setUpdateBy(userM.getUserName());
//		creditLineRule.setResultDesc(response.getCreditLineResultDesc());
//		
//		xrulesVerM.setPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CREDIT_LINE, creditLineRule);
//
//		logger.debug("ScoringCreditLine : "+response.getScoringCreditLine());
//		logger.debug("RecommendCreditLine : "+response.getRecommendCreditLine());
//		logger.debug("ExceptionCreditLine : "+response.getExceptionCreditLine());
//		
//		applicationM.setRecommentCreditLine(response.getRecommendCreditLine());
//		applicationM.setFinalCreditLimit(response.getRecommendCreditLine());
//		applicationM.setScoringCreditLine(response.getScoringCreditLine());
//		applicationM.setExceptionCreditLine(response.getExceptionCreditLine());
//
//		applicationM.setCustomerGroup(response.getCustomerGroup());
//		return appDecision;
//			
//	}
//	
//	public void ILOGIncomeDebt(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("MAP ILOG Service IncomeDebt ServiceID "+PLXrulesConstant.ModuleService.INCOME_DEBT);
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		PLXRulesDebtBdDataM debtBDM = xrulesVerM.getXRulesDebtBdDataM();
//		if (null == debtBDM) {
//			debtBDM = new PLXRulesDebtBdDataM();
//			xrulesVerM.setXRulesDebtBdDataM(debtBDM);
//		}
//		debtBDM.setIncludeKEC(true);
//		
//		logger.debug("DebtBurdenResult : "+response.getDebtBurdenResult());
//		logger.debug("DebtBurdenCode : "+response.getDebtBurdenCode());
//		logger.debug("DebtBurdentScore : "+response.getDebtBurdentScore());
//		
//		xrulesVerM.setDEBT_BDResult(response.getDebtBurdenResult());
//		xrulesVerM.setDebtBdCode(response.getDebtBurdenCode());
//		debtBDM.setDebtBurdentScoreAdjust(response.getDebtBurdentScore());	
//		
//		PLXRulesPolicyRulesDataM policyRulesDebt = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);
//			policyRulesDebt.setResultCode(response.getDebtBurdenCode());
//			policyRulesDebt.setResultDesc(response.getDebtBurdenResult());			
//			policyRulesDebt.setUpdateBy(xrulesVerM.getDebtBdUpdateBy());
//		 
//		PLXRulesPolicyRulesDataM policyRulesIncome = xrulesVerM.getPLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);
//			policyRulesIncome.setResultCode(response.getTotalIncomeCode());
//			policyRulesIncome.setResultDesc(response.getTotalIncomeResult());			
//			policyRulesIncome.setUpdateBy(xrulesVerM.getDebtBdUpdateBy());
//		
//	}
//	
//	public void ILOGKECOverideRules(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("Execute ILOG Service KECOverideRules ");
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		
//		logger.debug("Color : "+response.getColor());
//		logger.debug("SummaryOrResultCode : "+response.getSummaryOrResultCode());
//		logger.debug("SummaryOrResultCode : "+response.getSummaryOrResultCode());
//		logger.debug("RecommendResultCode : "+response.getRecommendResultCode());
//		
//		applicationM.setJobType(response.getColor());
//
//		xrulesVerM.setSummaryOverideRuleCode(response.getSummaryOrResultCode());
//		xrulesVerM.setSummaryOverideRuleResult(response.getSummaryOrResult());					
//		xrulesVerM.setRecommendResult(response.getRecommendResultCode());
//		xrulesVerM.setSummaryOverideRuleUpdateBy(userM.getUserName());
//		
//	}
//	
//	public void ILOGDCIKECOverideRules(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("Execute ILOG Service KECOverideRules ");
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		
//		logger.debug("Color : "+response.getColor());
//		logger.debug("SummaryOrResultCode : "+response.getSummaryOrResultCode());
//		logger.debug("SummaryOrResultCode : "+response.getSummaryOrResultCode());
//		logger.debug("RecommendResultCode : "+response.getRecommendResultCode());
//		
//		applicationM.setJobType(response.getColor());
//
//		xrulesVerM.setSummaryOverideRuleCode(response.getSummaryOrResultCode());
//		xrulesVerM.setSummaryOverideRuleResult(response.getSummaryOrResult());					
//		xrulesVerM.setRecommendResult(response.getRecommendResultCode());
//		xrulesVerM.setSummaryOverideRuleUpdateBy(userM.getUserName());
//		
//		if (OrigUtil.isEmptyString(response.getColor())) {
//			applicationM.setJobType("G");
//			xrulesVerM.setSummaryOverideRuleCode("AC");
//			xrulesVerM.setSummaryOverideRuleResult("ACCEPT");
//			xrulesVerM.setRecommendResult("AC");
//		}	
//		
//	}
//	
//	public void ILOGVCKECOverideRules(PLApplicationDataM applicationM,UserDetailM userM,ILOGResult response) throws Exception{
//		logger.debug("Execute ILOG Service KECOverideRules ");
//		if(null == applicationM){
//			applicationM = new PLApplicationDataM();
//		}
//		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//		if(null == xrulesVerM){
//			xrulesVerM = new PLXRulesVerificationResultDataM();
//			personalM.setXrulesVerification(xrulesVerM);
//		}	
//		
//		logger.debug("Color : "+response.getColor());
//		logger.debug("SummaryOrResultCode : "+response.getSummaryOrResultCode());
//		logger.debug("SummaryOrResultCode : "+response.getSummaryOrResultCode());
//		logger.debug("RecommendResultCode : "+response.getRecommendResultCode());
//		
//		applicationM.setJobType(response.getColor());
//
//		xrulesVerM.setSummaryOverideRuleCode(response.getSummaryOrResultCode());
//		xrulesVerM.setSummaryOverideRuleResult(response.getSummaryOrResult());					
//		xrulesVerM.setRecommendResult(response.getRecommendResultCode());
//		xrulesVerM.setSummaryOverideRuleUpdateBy(userM.getUserName());
//		
//		if (OrigUtil.isEmptyString(response.getColor())) {
//			applicationM.setJobType("G");
//			xrulesVerM.setSummaryOverideRuleCode("AC");
//			xrulesVerM.setSummaryOverideRuleResult("ACCEPT");
//			xrulesVerM.setRecommendResult("AC");
//		}		
//		
//	}
	
}
