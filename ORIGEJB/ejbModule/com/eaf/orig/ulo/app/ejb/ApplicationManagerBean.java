package com.eaf.orig.ulo.app.ejb;

import java.util.ArrayList;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.bpm.rest.client.BPMClientException;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationDAO;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.OrigReasonDAO;
import com.eaf.orig.ulo.app.dao.OrigReasonLogDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.ejb.view.ApplicationManager;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.orig.bpm.workflow.model.BPMInstance;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;
import com.orig.bpm.workflow.service.model.BPMApplicationLog;

@Stateless
@Local(ApplicationManager.class)
@LocalBean
public class ApplicationManagerBean implements ApplicationManager {
	private static transient Logger logger = Logger.getLogger(ApplicationManagerBean.class);
    public ApplicationManagerBean() {
        super();
    }
    public void saveApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM){
    	try{
    		String userId = userM.getUserName();
    		ORIGDAOFactory.getApplicationGroupDAO(userId,applicationGroup.getTransactionId()).saveUpdateOrigApplicationGroupM(applicationGroup);    	
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
    	}
    }
	@Override
	public void createApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM) {				
		try{
			String userId = userM.getUserName();
			ORIGDAOFactory.getApplicationGroupDAO(userId).saveUpdateOrigApplicationGroupM(applicationGroup);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
	
	public void cancelApplication(String applicationGroupId,ArrayList<String> cancelUniqueIds,ApplicationReasonDataM applicationReason,UserDetailM userM) {
		String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
		logger.debug("DECISION_FINAL_DECISION_CANCEL >> "+DECISION_FINAL_DECISION_CANCEL);
		try{
			String userId = userM.getUserName();
			OrigApplicationDAO origApplication = ORIGDAOFactory.getApplicationDAO(userId);
			OrigReasonDAO origReason = ORIGDAOFactory.getReasonDAO(userId);
			OrigReasonLogDAO origReasonLog = ORIGDAOFactory.getReasonLogDAO(userId);
			if(!Util.empty(cancelUniqueIds)){
				for (String cancelUniqueId : cancelUniqueIds) {
					logger.debug("cancelUniqueId : "+cancelUniqueId);
					origApplication.updateFinalAppDecision(cancelUniqueId,DECISION_FINAL_DECISION_CANCEL);
					origReason.deleteOrigReasonM(cancelUniqueId);
					ArrayList<ReasonDataM> reasons = applicationReason.getReasons();
					logger.debug("reasons : "+reasons);
					if(!Util.empty(reasons)){
						for(ReasonDataM reason : reasons){
							reason.setApplicationRecordId(cancelUniqueId);
							reason.setApplicationGroupId(applicationGroupId);
							origReason.createOrigReasonM(reason);
						}
					}
					if (!Util.empty(reasons)) {
						for(ReasonDataM reason : reasons) {
							ReasonLogDataM reasonDataLog = new ReasonLogDataM();
								reasonDataLog.setApplicationRecordId(cancelUniqueId);
								reasonDataLog.setApplicationGroupId(applicationGroupId);
								reasonDataLog.setReasonType(reason.getReasonType());
								reasonDataLog.setReasonCode(reason.getReasonCode());
								reasonDataLog.setRole(reason.getRole());
								reasonDataLog.setRemark(reason.getRemark());
								reasonDataLog.setCreateBy(reason.getCreateBy());
								reasonDataLog.setCreateDate(reason.getCreateDate());
							origReasonLog.createOrigReasonLogM(reasonDataLog);
						}
					}
				}
			}else{
				//Defect UAT 3309 #Ia null application cancel app
				ArrayList<ReasonDataM> reasons = applicationReason.getReasons();
				logger.debug("reasons : "+reasons);
				if(!Util.empty(reasons)){
					for(ReasonDataM reason : reasons){
						reason.setApplicationGroupId(applicationGroupId);
						origReason.createOrigReasonM(reason);
					}
				}
				if (!Util.empty(reasons)) {
					for(ReasonDataM reason : reasons) {
						ReasonLogDataM reasonDataLog = new ReasonLogDataM();
							reasonDataLog.setApplicationGroupId(applicationGroupId);
							reasonDataLog.setReasonType(reason.getReasonType());
							reasonDataLog.setReasonCode(reason.getReasonCode());
							reasonDataLog.setRole(reason.getRole());
							reasonDataLog.setRemark(reason.getRemark());
							reasonDataLog.setCreateBy(reason.getCreateBy());
							reasonDataLog.setCreateDate(reason.getCreateDate());
						origReasonLog.createOrigReasonLogM(reasonDataLog);
					}
				}
			}
		}catch(ApplicationException e){
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
	
	public void updateFraudRemark(String applicationGroupId,String fraudRemark,String userId) {
		OrigApplicationGroupDAO origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
		try {
//			#rawi comment change logic updateFraudRemark.
//			ApplicationGroupDataM applicationGroup = origApplicationGroup.loadOrigApplicationGroupM(applicationGroupId);
//			applicationGroup.setFraudRemark(fraudRemark);			
//			origApplicationGroup.saveUpdateOrigApplicationGroupM(applicationGroup);
			origApplicationGroup.updateFraudRemark(applicationGroupId, fraudRemark, userId);
		} catch (ApplicationException e) {
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
	@Override
	public void reprocessApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM,String logMessage){
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		BPMInstance bpmInstance = null;
		try{
			String userId = userM.getUserName();
			ORIGDAOFactory.getApplicationGroupDAO(userId).saveUpdateOrigApplicationGroupM(applicationGroup);
			ModuleFactory.getOrigComparisionDataDAO(userId).deleteOrigComparisionDataMatchSrc(applicationGroup.getApplicationGroupId(),CompareDataM.SoruceOfData.TWO_MAKER,applicationGroup.getLifeCycle());
			BPMMainFlowProxy bpmMainFlowProxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			if(!SystemConfig.containsGeneralParam("WIP_JOBSTATE_END",applicationGroup.getJobState())){
				bpmInstance = bpmMainFlowProxy.terminateProcess(applicationGroup.getInstantId());
				if (BpmProxyConstant.RestAPIResult.ERROR.equalsIgnoreCase(bpmInstance.getResultCode())) {
					throw new BPMClientException(bpmInstance.getResultDesc());
				}
			}
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
				workflowRequest.setApplicationGroup(applicationGroup);
				workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				workflowRequest.setUserId(userM.getUserName());
			WorkflowResponseDataM createworkflowResponse = workflowManager.createWorkflowTask(workflowRequest);
			if(BpmProxyConstant.RestAPIResult.ERROR.equals(createworkflowResponse.getResultCode())){
				throw new Exception(createworkflowResponse.getResultDesc());
			}
			ModuleFactory.getInfBatchLogDAO().blockInfBatchLogReprocess(applicationGroup.getApplicationGroupId(),applicationGroup.getLifeCycle(),logMessage);
			//ORIGDAOFactory.getReprocessLogDAO().createOrigReprocessLog(reprocessLog);

			BPMApplicationLog appLog = new BPMApplicationLog();
				appLog.setAction(BpmProxyConstant.WorkflowAction.REPROCESS);
				appLog.setAppGroupId(applicationGroup.getApplicationGroupId());
				appLog.setAppStatus(applicationGroup.getApplicationStatus());
				appLog.setJobState(applicationGroup.getJobState());
				appLog.setUsername(userM.getUserName());
				appLog.setToRole(userM.getCurrentRole());
				appLog.setSubmitByRole(userM.getCurrentRole());
				appLog.setSpecialCondition("REPROCESS");
			WorkflowResponseDataM workflowResponse = workflowManager.addAppHistoryLog(appLog);
			logger.debug("workflowResponse.getResultCode >> "+workflowResponse.getResultCode());
			logger.debug("workflowResponse.getResultDesc >> "+workflowResponse.getResultDesc());
			if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())){
				throw new Exception(workflowResponse.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
}
