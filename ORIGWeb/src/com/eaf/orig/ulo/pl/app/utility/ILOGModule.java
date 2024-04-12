package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class ILOGModule {
	
	static Logger logger = Logger.getLogger(ILOGModule.class);
	
	public String ModuleDCDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision, String docRefID) throws Exception {
		logger.debug("ModuleDCDecision()..");
		try{
			ILOGControl control = new ILOGControl();	 		
			HashMap<String, String> result = control.execute(OrigConstant.ILOGAction.DC_ACTION, applicationM, userM);			
			if(null == result){
				throw new Exception("ERROR: ModuleDCDecision");
			}
			
			String recommendDecision = result.get("RECOMMEND_DECISION");
			logger.debug("RecommendCreditLine Decision >> " + recommendDecision);

			if (!OrigUtil.isEmptyString(recommendDecision)) {
				decision = recommendDecision;
			}
			
			ORIGLogic origLogic = new ORIGLogic();
			String appDecision = origLogic.LogicDCDecision(applicationM, userM, decision, docRefID);
			logger.debug("LogicDCDecision ... " + appDecision);
			
			return appDecision; 
		} catch (Exception e) {
			logger.error("ModuleDCDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}	
	}
	
	public String ModuleDCIDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision, String docRefID) throws Exception {
		logger.debug("ModuleDCIDecision()..");
		try{
			ILOGControl control = new ILOGControl();			
			HashMap<String, String> result = control.execute(OrigConstant.ILOGAction.DCI_ACTION, applicationM, userM);			
			if(null == result){
				throw new Exception("ERROR: ModuleDCIDecision");
			}
			
			String recommendDecision = result.get("RECOMMEND_DECISION");
			logger.debug("RecommendCreditLine Decision >> " + recommendDecision);

			if (!OrigUtil.isEmptyString(recommendDecision)) {
				decision = recommendDecision;
			}
			
			ORIGLogic origLogic = new ORIGLogic();
			String appDecision = origLogic.LogicDCIDecision(applicationM, userM, decision, docRefID);
			logger.debug("ModuleDCIDecision ... " + appDecision);
			
			return appDecision; 
		} catch (Exception e) {
			logger.error("ModuleDCIDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}	
	}
	
	public String ModuleVCDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision, String docRefID) throws Exception {
		logger.debug("ModuleVCDecision()..");
		try{
			ILOGControl control = new ILOGControl();			
			HashMap<String, String> result = control.execute(OrigConstant.ILOGAction.VC_ACTION, applicationM, userM);			
			if(null == result){
				throw new Exception("ERROR: ModuleVCDecision");
			}
			
			String recommendDecision = result.get("RECOMMEND_DECISION");
			logger.debug("RecommendCreditLine Decision >> " + recommendDecision);

			if (!OrigUtil.isEmptyString(recommendDecision)) {
				decision = recommendDecision;
			}
			
			ORIGLogic origLogic = new ORIGLogic();
			String appDecision = origLogic.LogicVCDecision(applicationM, userM, decision, docRefID);
			logger.debug("ModuleVCDecision ... " + appDecision);
			
			return appDecision; 
		} catch (Exception e) {
			logger.error("ModuleVCDecision Execute ILOG Not Success!!  throw ERROR");
			throw new Exception(e.getMessage());
		}	
	}
	
}
