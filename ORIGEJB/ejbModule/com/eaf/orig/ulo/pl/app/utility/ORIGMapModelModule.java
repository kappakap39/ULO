package com.eaf.orig.ulo.pl.app.utility;

import java.sql.Timestamp;

import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.model.todolist.WfJobDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationLogDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDedupDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.model.WorkflowDataM;

public class ORIGMapModelModule {
	public static WorkflowDataM mappingModelWorkFlow (PLApplicationDataM appM, UserDetailM userM) throws Exception{
		WorkflowDataM workflowM = new WorkflowDataM();		
		workflowM.setAppRecordID(appM.getAppRecordID());
		workflowM.setJobID(appM.getJobID());
		workflowM.setOwner(appM.getOwner());
		workflowM.setUserID(userM.getUserName());
		workflowM.setUserRole(userM.getCurrentRole());
		workflowM.setAction(appM.getAppDecision());
		workflowM.setBusClass(appM.getBusinessClassId());
		workflowM.setGroupAllocateID(appM.getJobType());
		//Sankom Level Id from util
		UtilityDAO utilDAO = new UtilityDAOImpl();
		String allocationType = "A";
		if(OrigConstant.Action.ESCALATE.equals(appM.getAppDecision())){
			allocationType="E";
		} 
		workflowM.setLevelID(utilDAO.getWFDLAGroup(appM.getJobType(), allocationType, appM.getLevelID() ));
		workflowM.setAppInfo(appM.getAppInfo());
		workflowM.setAppStatus(appM.getApplicationStatus());
		try {
			workflowM.setPriority(OrigUtil.stringToInt(appM.getPriority()));
		} catch (Exception e) {
			workflowM.setPriority(0);
			e.printStackTrace();
		}
		workflowM.setAtid(appM.getJobState());
		workflowM.setBlockFlag(appM.getBlockFlag());
		workflowM.setAppDate(appM.getAppDate());
		return workflowM;
	}
	
	public static  WorkflowDataM mappingModelWorkFlow(PLApplicationDataM appM, UserDetailM userM , String ptId) throws Exception{		
		WorkflowDataM workflowM = new WorkflowDataM();		
		workflowM.setAppRecordID(appM.getAppRecordID());
		workflowM.setJobID(appM.getJobID());
		workflowM.setOwner(appM.getOwner());
		workflowM.setUserID(userM.getUserName());
		workflowM.setUserRole(userM.getCurrentRole());
		workflowM.setAction(appM.getAppDecision());
		workflowM.setPtID(ptId);
		workflowM.setBusClass(appM.getBusinessClassId());
		workflowM.setGroupAllocateID(appM.getJobType());
		//Sankom Level Id from util
		UtilityDAO utilDAO = new UtilityDAOImpl();
		String allocationType="A";
		if(OrigConstant.Action.ESCALATE.equals(appM.getAppDecision())){
			allocationType="E";
		} 
		workflowM.setLevelID(utilDAO.getWFDLAGroup(appM.getJobType(), allocationType, appM.getLevelID() ));
		workflowM.setAppInfo(appM.getAppInfo());
		workflowM.setAppStatus(appM.getApplicationStatus());
		workflowM.setPriority(OrigUtil.stringToInt(appM.getPriority()));
		workflowM.setAtid(appM.getJobState());
		workflowM.setBlockFlag(appM.getBlockFlag());
		workflowM.setAppDate(appM.getAppDate());
		return workflowM;
	}
	
	public static  WorkflowDataM mappingModelWorkFlowReAssign(PLApplicationDataM applicationM, UserDetailM userM) throws Exception{		
		WorkflowDataM workflowM = new WorkflowDataM();		
			workflowM.setAppRecordID(applicationM.getAppRecordID());
			workflowM.setJobID(applicationM.getJobID());
			workflowM.setOwner(applicationM.getOwner());
			workflowM.setUserID(userM.getUserName());
			workflowM.setUserRole(userM.getCurrentRole());
			workflowM.setAction(applicationM.getAppDecision());
			workflowM.setBusClass(applicationM.getBusinessClassId());
			
//			#septemwi comment
//			workflowM.setGroupAllocateID(appM.getJobType());
//			UtilityDAO utilDAO = new UtilityDAOImpl();
//			String allocationType="A";
//			if(OrigConstant.Action.ESCALATE.equals(appM.getAppDecision())){
//				allocationType="E";
//			} 
//			workflowM.setLevelID(utilDAO.getWFDLAGroup(appM.getJobType(), allocationType, appM.getLevelID() ));
			
			UtilityDAO utilDAO = ObjectDAOFactory.getUtilityDAO();
				utilDAO.getWfLastData(applicationM.getAppRecordID(), workflowM);
				
			workflowM.setAppInfo(applicationM.getAppInfo());
			workflowM.setAppStatus(applicationM.getApplicationStatus());
			workflowM.setPriority(OrigUtil.stringToInt(applicationM.getPriority()));
			workflowM.setAtid(applicationM.getJobState());
			workflowM.setBlockFlag(applicationM.getBlockFlag());
			workflowM.setAppDate(applicationM.getAppDate());
		return workflowM;
	}
	public static  WorkflowDataM mappingModelParallelFlow(PLApplicationDataM appM, UserDetailM userM ,String action) throws Exception{		
		WorkflowDataM workflowM = new WorkflowDataM();		
		workflowM.setAppRecordID(appM.getAppRecordID());
		workflowM.setJobID(appM.getJobID());
		workflowM.setOwner(appM.getOwner());
		workflowM.setUserID(userM.getUserName());
		workflowM.setUserRole(userM.getCurrentRole());	
		workflowM.setAction(action);
		workflowM.setPtID(appM.getPtID());
		workflowM.setAtid(appM.getJobState());
		workflowM.setBusClass(appM.getBusinessClassId());
		workflowM.setGroupAllocateID(appM.getJobType());
		//Sankom Level Id from util
		UtilityDAO utilDAO = new UtilityDAOImpl();
		String allocationType="A";
		if(OrigConstant.Action.ESCALATE.equals(appM.getAppDecision())){
			allocationType="E";
		} 
		workflowM.setLevelID(utilDAO.getWFDLAGroup(appM.getJobType(), allocationType, appM.getLevelID()));
		workflowM.setAppInfo(appM.getAppInfo());
		workflowM.setAppStatus(appM.getApplicationStatus());
		workflowM.setPriority(OrigUtil.stringToInt(appM.getPriority()));
		workflowM.setAtid(appM.getJobState());
		workflowM.setBlockFlag(appM.getBlockFlag());
		workflowM.setAppDate(appM.getAppDate());
		return workflowM;
	}	
	public static WorkflowDataM mappModelWorkflowDataM(PLXRulesDedupDataM dedupDataM,UserDetailM userM){
		WorkflowDataM workflowM = new WorkflowDataM();
		workflowM.setAppRecordID(dedupDataM.getApplicationRecordId());
		workflowM.setUserID(userM.getUserName());
		return workflowM;
	}
	public static  WorkflowDataM mappingModelWorkFlow (String appRecId ,String role ,String userName) {		
		WorkflowDataM workflowM = new WorkflowDataM();		
		workflowM.setAppRecordID(appRecId);
		workflowM.setUserID(userName);
		workflowM.setUserRole(role);
		return workflowM;
	}
	public static  WorkflowDataM mappingModelWorkFlow (String appRecID ,UserDetailM userM){		
		WorkflowDataM workflowM = new WorkflowDataM();		
		workflowM.setAppRecordID(appRecID);
		workflowM.setUserID(userM.getUserName());
		workflowM.setUserRole(userM.getCurrentRole());
		return workflowM;
	}
	public static void MapModelPLApplicationDataM(PLApplicationDataM appM ,WfJobDataM wfJobM,String ptType,String appDecision,String blockFlag){
		appM.setJobID(wfJobM.getJobID());
		appM.setPtID(wfJobM.getPtID());
		appM.setPtType(BpmProxyConstant.WorkflowProcessType.PROCESS_TYPE_MAINPROCESS);						
		appM.setAppDecision(appDecision);
		appM.setBlockFlag(blockFlag);		
		appM.setAppInfo(OrigUtil.getApplicatonXML(appM));
	}
	public static PLApplicationLogDataM MapModelApplicationLOG(WorkflowResponse response, PLApplicationDataM applicationM){
		PLApplicationLogDataM appLogM = new PLApplicationLogDataM();		
		
		appLogM.setAction(applicationM.getAppDecision());
		if(OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
			appLogM.setActionDesc("Save");
		}else{
			appLogM.setActionDesc(applicationM.getAppDecision());
		}
		appLogM.setApplicationRecordID(applicationM.getAppRecordID());
		if(!OrigUtil.isEmptyString(response.getAppStatus())){
			appLogM.setApplicationStatus(response.getAppStatus());
		}else{
			appLogM.setApplicationStatus(applicationM.getApplicationStatus());
		}
		appLogM.setCreateBy(response.getUserName());
		appLogM.setJobState(response.getToAtid());
		if(null != response.getClaimDate()) {
			appLogM.setClaimDate(new Timestamp(response.getClaimDate().getTime()));
		}
		appLogM.setLifeCycle(applicationM.getLifeCycle());
		if(0 == appLogM.getLifeCycle()){
			appLogM.setLifeCycle(1);
		}
		if(response.getClaimDate() == null){
			appLogM.setClaimDate(null);
		}else{
			appLogM.setClaimDate(new Timestamp(response.getClaimDate().getTime()));
		}
		appLogM.setUpdateBy(response.getUserName());
		return appLogM;
	}
}
