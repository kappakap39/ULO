package com.eaf.orig.ulo.pl.app.utility;

import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ILOGTool {
	static Logger logger = Logger.getLogger(ILOGTool.class);
	
	public void ModuleExecuteRules(PLApplicationDataM applicationM, UserDetailM userM) throws Exception {
		logger.debug("ModuleExecuteRules ... ");
		try{
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xruleVerM = personalM.getXrulesVerification();
			if(null == xruleVerM){
				xruleVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xruleVerM);
			}			
			PLXRulesDebtBdDataM debtBDM = xruleVerM.getXRulesDebtBdDataM();
			if (null == debtBDM) {
				debtBDM = new PLXRulesDebtBdDataM();
				xruleVerM.setXRulesDebtBdDataM(debtBDM);
			}
			ILOGExecuteRule ILOG = new ILOGExecuteRule();
			if(!OrigUtil.isEmptyString(xruleVerM.getbScoreCode())){
				ILOG.ILOGBeScore(applicationM, userM);
			}			
			if(!OrigUtil.isEmptyString(xruleVerM.getAppScoreResultCode())){
				ILOG.ILOGApplicationScore(applicationM, userM);
			}
			if(!OrigUtil.isEmptyString(xruleVerM.getFicoCode())){
				ILOG.ILOGFICOScore(applicationM, userM);
			}			
						
			if(!OrigUtil.isEmptyString(xruleVerM.getDebtBdCode())){
				ILOG.ILOGIncomeDebt(applicationM, userM);
			}			
			if(!OrigUtil.isEmptyString(xruleVerM.getSummaryOverideRuleCode())){
				ILOG.ILOGKECOverideRules(applicationM, userM);
			}		
		}catch(Exception e){
			logger.error("ModuleDCDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}	
	}
	
	public String ModuleDCDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision, String docRefID) throws Exception {
		logger.debug("ModuleDCDecision ... ");
		try{			
			ILOGExecuteRule ILOG = new ILOGExecuteRule();
				ILOG.ILOGVerifyCustomer(applicationM, userM);
				ILOG.ILOGVerifyHR(applicationM, userM);
				ILOG.ILOGBeScore(applicationM, userM);
				ILOG.ILOGApplicationScore(applicationM, userM);
				ILOG.ILOGFICOScore(applicationM, userM);
				ILOG.ILOGDssKbank(applicationM, userM);
				ILOG.ILOGSSO(applicationM, userM);
				ILOG.ILOGRD(applicationM, userM);
				ILOG.ILOGTOT(applicationM, userM);
				ILOG.ILOGPhoneVer(applicationM, userM);
				ILOG.ILOGYearOfWork(applicationM, userM);
				ILOG.ILOGOccupation(applicationM, userM);
				ILOG.ILOGChequeReturn(applicationM, userM);
				ILOG.ILOGFaultCondition(applicationM, userM);
	
				String recommendDecision = ILOG.ILOGRecommendCreditLine(applicationM, userM);
				logger.debug("RecommendCreditLine Decision >> " + recommendDecision);
	
				if (!OrigUtil.isEmptyString(recommendDecision)) {
					decision = recommendDecision;
				}
	
				ILOG.ILOGIncomeDebt(applicationM, userM);
				ILOG.ILOGKECOverideRules(applicationM, userM);
				
			ORIGLogic origLogic = new ORIGLogic();
			String appDecision = origLogic.LogicDCDecision(applicationM, userM, decision, docRefID);
			logger.debug("LogicDCDecision ... " + appDecision);
			return appDecision; 
		} catch (Exception e) {
			logger.error("ModuleDCDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}		
	}

	public String ModuleVCDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision, String docRefID) throws Exception {
		logger.debug("ModuleVCDecision ... ");
		try{			
			ILOGExecuteRule ILOG = new ILOGExecuteRule();
				ILOG.ILOGVerifyCustomer(applicationM, userM);
				ILOG.ILOGVerifyHR(applicationM, userM);
				ILOG.ILOGBeScore(applicationM, userM);
				ILOG.ILOGApplicationScore(applicationM, userM);
				ILOG.ILOGFICOScore(applicationM, userM);
				ILOG.ILOGDssKbank(applicationM, userM);
				ILOG.ILOGSSO(applicationM, userM);
				ILOG.ILOGRD(applicationM, userM);
				ILOG.ILOGTOT(applicationM, userM);
				ILOG.ILOGPhoneVer(applicationM, userM);
				ILOG.ILOGYearOfWork(applicationM, userM);
				ILOG.ILOGOccupation(applicationM, userM);
				ILOG.ILOGChequeReturn(applicationM, userM);
				ILOG.ILOGFaultCondition(applicationM, userM);
	
				String recommendDecision = ILOG.ILOGRecommendCreditLine(applicationM, userM);
				logger.debug("RecommendCreditLine Decision >> " + recommendDecision);
	
				if (!OrigUtil.isEmptyString(recommendDecision)) {
					decision = recommendDecision;
				}
	
				ILOG.ILOGIncomeDebt(applicationM, userM);
				ILOG.ILOGVCKECOverideRules(applicationM, userM);

			ORIGLogic origLogic = new ORIGLogic();
			String appDecision = origLogic.LogicVCDecision(applicationM, userM, decision ,docRefID);
			logger.debug("LogicVCDecision ... " + appDecision);

			return appDecision;
		} catch (Exception e) {
			logger.error("ModuleVCDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}
	}

	public String ModuleDCIDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision ,String docRefID) throws Exception {
		logger.debug("ModuleDCIDecision ... ");
		try{
			ILOGExecuteRule ILOG = new ILOGExecuteRule();
				ILOG.ILOGVerifyCustomer(applicationM, userM);
				ILOG.ILOGVerifyHR(applicationM, userM);
				ILOG.ILOGBeScore(applicationM, userM);
				
				ILOG.ILOGApplicationScore(applicationM, userM);
				ILOG.ILOGFICOScore(applicationM, userM);
				ILOG.ILOGDssKbank(applicationM, userM);
				ILOG.ILOGSSO(applicationM, userM);
				ILOG.ILOGRD(applicationM, userM);
				ILOG.ILOGTOT(applicationM, userM);
				ILOG.ILOGPhoneVer(applicationM, userM);
				ILOG.ILOGYearOfWork(applicationM, userM);
				ILOG.ILOGOccupation(applicationM, userM);
				ILOG.ILOGChequeReturn(applicationM, userM);
				ILOG.ILOGFaultCondition(applicationM, userM);
	
				String recommendDecision = ILOG.ILOGRecommendCreditLine(applicationM, userM);
				logger.debug("RecommendCreditLine Decision >> " + recommendDecision);
	
				if (!OrigUtil.isEmptyString(recommendDecision)) {
					decision = recommendDecision;
				}
	
				ILOG.ILOGIncomeDebt(applicationM, userM);
				ILOG.ILOGDCIKECOverideRules(applicationM, userM);
			
			ORIGLogic origLogic = new ORIGLogic();
			String appDecision = origLogic.LogicDCIDecision(applicationM, userM, decision, docRefID);
			logger.debug("ModuleDCIDecision ... " + appDecision);

			return appDecision;
		} catch (Exception e) {
			logger.error("ModuleDCIDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}
	}
	
	public String ModuleDE_SUPDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision, String docRefID) throws Exception {
		logger.debug("ModuleDE_SUPDecision ... ");
		try {
			ORIGLogic origLogic = new ORIGLogic();
			String appDecision = origLogic.LogicDE_SUPDecision(applicationM, userM, decision, docRefID);			
			return appDecision;
		} catch (Exception e) {
			logger.error("ModuleDE_SUPDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}
	}
}
