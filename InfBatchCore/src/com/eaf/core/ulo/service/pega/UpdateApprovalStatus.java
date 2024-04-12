package com.eaf.core.ulo.service.pega;

//import java.sql.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
//import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequestDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusData;
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.commons.lang.StringUtils;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class UpdateApprovalStatus {
	static transient Logger logger = Logger.getLogger(UpdateApprovalStatus.class);	
//	static String JOB_STATE_DE2_APPROVE = InfBatchProperty.getGeneralParam("JOB_STATE_DE2_APPROVE");
//	static String JOB_STATE_REJECTED = InfBatchProperty.getGeneralParam("JOB_STATE_DE2_REJECT");	
//	static String JOB_STATE_AFTER_CARDLINK = InfBatchProperty.getGeneralParam("JOB_STATE_AFTER_CARDLINK");
//	static String JOB_STATE_CANCELLED = InfBatchProperty.getGeneralParam("JOB_STATE_CANCELLED");
//	static String UPDATE_APPROVAL_STATUS_MAX_LIFE_CYCLE_HIGH = InfBatchProperty.getGeneralParam("UP_AP_ST_MAX_LIFE_CYCLE_HIGH");	
//	static String UPDATE_APPROVAL_STATUS_MAX_LIFE_CYCLE_LOW = InfBatchProperty.getGeneralParam("UP_AP_ST_MAX_LIFE_CYCLE_LOW");
//	static String UPDATE_APPROVAL_STATUS_DIFF_DAY = InfBatchProperty.getGeneralParam("UP_AP_ST_DIFF_DAY");
	static String FLAG_YES = InfBatchConstant.FLAG_YES;
	static String FLAG_NO = InfBatchConstant.FLAG_NO;	
	public static UpdateApprovalStatusData validateUpdateApprovalStatus(ApplicationGroupDataM applicationGroup){
		UpdateApprovalStatusData updateApprovalStatusData = new UpdateApprovalStatusData();
		updateApprovalStatusData.setCaseId(applicationGroup.getApplicationGroupNo());	
		String isVetoEligible = applicationGroup.getIsVetoEligible();
		String jobState = applicationGroup.getJobState();
		logger.debug("isVetoEligible : "+isVetoEligible);
		int maxLifeCycle = applicationGroup.getMaxLifeCycle();			
		logger.debug("jobState : "+jobState);
		logger.debug("maxLifeCycle : "+maxLifeCycle);
		if(InfBatchProperty.containsGeneralParam("JOB_STATE_DE2_APPROVE",jobState)){
			updateApprovalStatusData.setIsClose(FLAG_YES);
			updateApprovalStatusData.setIsVetoEligible(FLAG_NO);				
		}else if(InfBatchProperty.containsGeneralParam("JOB_STATE_DE2_REJECT",jobState)){
			updateApprovalStatusData.setIsClose(FLAG_NO);
			if(FLAG_YES.equals(isVetoEligible)){	
				updateApprovalStatusData.setIsVetoEligible(FLAG_YES);
			}else{
				updateApprovalStatusData.setIsVetoEligible(FLAG_NO);
			}
		}
		logger.debug("validateUpdateApprovalStatus : "+updateApprovalStatusData);
		return updateApprovalStatusData;		
	}
	
//	public UpdateApprovalStatusData notifyIM(UpdateApprovalStatusRequestDataM updateApprovalStatusRequest){
//		UpdateApprovalStatusData updateApprovalStatusResponse = new UpdateApprovalStatusData();
//		Date FIRST_FINAL_DECISION_DATE = null;
//		String isVetoEligible = updateApprovalStatusRequest.getIsVetoEligible();
//		int maxLifeCycle = 1;		
//		for(ApplicationDataM applicationItem : updateApprovalStatusRequest.getApplications()){
//			if(maxLifeCycle < applicationItem.getLifeCycle()){
//				maxLifeCycle = applicationItem.getLifeCycle();
//			}else{
//				FIRST_FINAL_DECISION_DATE = applicationItem.getFinalAppDecisionDate();
//			}
//		}		
//		if(JOB_STATE_DE2_APPROVE_SUBMIT.equals(updateApprovalStatusRequest.getJobState()) 
//			|| JOB_STATE_AFTER_CARDLINK.equals(updateApprovalStatusRequest.getJobState())
//				|| JOB_STATE_CANCELLED.equals(updateApprovalStatusRequest.getJobState())){
//			updateApprovalStatusResponse.setIsClose(FLAG_YES);
//			updateApprovalStatusResponse.setSendToEDWS(FLAG_YES);	
//		}else if(JOB_STATE_REJECTED.equals(updateApprovalStatusRequest.getJobState())){
//			if(FLAG_NO.equals(isVetoEligible)){
//				updateApprovalStatusResponse.setIsClose(FLAG_YES);
//				updateApprovalStatusResponse.setSendToEDWS(FLAG_YES);		
//			}else{
//				long DIFF_TIME = updateApprovalStatusRequest.getApplicationDate().getTime() - FIRST_FINAL_DECISION_DATE.getTime();
//				long DIFF_DAY = DIFF_TIME/(1000*60*60*24);					
//				if(DIFF_DAY > Integer.parseInt(UPDATE_APPROVAL_STATUS_DIFF_DAY) || maxLifeCycle == Integer.parseInt(UPDATE_APPROVAL_STATUS_MAX_LIFE_CYCLE_HIGH)){
//					updateApprovalStatusResponse.setIsClose(FLAG_YES);
//					updateApprovalStatusResponse.setSendToEDWS(FLAG_YES);
//				}
//			}
//		}
//		logger.debug("CaseID : "+updateApprovalStatusResponse.getCaseId());
//		logger.debug("isClose : "+updateApprovalStatusResponse.getIsClose());
//		logger.debug("setSendToEDWS : "+updateApprovalStatusResponse.getSendToEDWS());		
//		return updateApprovalStatusResponse;
//	}
	
//	public void mapNotifyApplicationRequest(ApplicationGroupDataM applicationGroup ,UpdateApprovalStatusRequestDataM request){
//		Date applicationDate = InfBatchProperty.getDate();
//		request.setApplicationGroupNo(applicationGroup.getApplicationGroupNo());
//		request.setApplicationDate(applicationDate);
//		request.setApplications(applicationGroup.getApplications());
//		request.setIsVetoEligible(applicationGroup.getIsVetoEligible());
//		request.setJobState(applicationGroup.getJobState());
//	}
	
//	public String convertToCvsContent(UpdateApprovalStatusResponseDataM updateApprovalStatusResponse){
//		List<String> notifyList = new ArrayList<>();
//			notifyList.add(updateApprovalStatusResponse.getCaseId());
//			notifyList.add(updateApprovalStatusResponse.getIsClose());
//			notifyList.add(updateApprovalStatusResponse.getIsVetoEligible());
//		String CvsContent = StringUtils.join(notifyList, ",");		
//		return CvsContent;
//	}
}
