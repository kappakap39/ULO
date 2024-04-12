package com.ava.flp.eapp.submitapplication.action;

import java.util.ArrayList;

import javax.ejb.EJBException;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.submitapplication.model.ESubmitApplicationObject;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigReasonDAO;
import com.eaf.orig.ulo.app.dao.OrigReasonLogDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;

public class EProcessActionCancel extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(EProcessActionCancel.class);
	private final static String BPM_DECISION_CANCEL = SystemConstant.getConstant("BPM_DECISION_CANCEL");
	private final static String JOBSTATE_CANCEL = SystemConstant.getConstant("JOBSTATE_CANCEL");
	private final static String APPLICATION_STATIC_CANCELLED = SystemConstant.getConstant("APPLICATION_STATIC_CANCELLED");
	private final static String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	
	@Override
	public Object processAction() {
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		String CANCEL_REASON_CANCEL_FROM_TABLET = SystemConstant.getConstant("CANCEL_REASON_CANCEL_FROM_TABLET");
		String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
		
		try{
			ESubmitApplicationObject submitEAppObject = (ESubmitApplicationObject)objectForm;
//			ApplicationGroup eApplicationGroup = submitEAppObject.getApplicationGroup();
//			ApplicationGroupDataM uloApplicationGroup = new EAppModelMapper().mapUloModel(null, eApplicationGroup);
			ApplicationGroupDataM uloApplicationGroup = (ApplicationGroupDataM) submitEAppObject.getApplicationGroup();
			String userId = submitEAppObject.getUserId();
			
			String transactionId = uloApplicationGroup.getTransactionId();
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			logger.debug("process cancel for " + uloApplicationGroup.getApplicationGroupNo());
			
			trace.create("EProcessCancel");
			
			EAppAction.mapBranchData(uloApplicationGroup);
			EAppAction.mapSaleData(uloApplicationGroup);
			EAppAction.mapCashTransferAccountType(uloApplicationGroup);
			EAppAction.mapCardAdditionalInfo(uloApplicationGroup);
			
			uloApplicationGroup.setLastDecision(BPM_DECISION_CANCEL);
			uloApplicationGroup.setLastDecisionDate(ApplicationDate.getDate());
			uloApplicationGroup.setJobState(JOBSTATE_CANCEL);
			uloApplicationGroup.setApplicationStatus(APPLICATION_STATIC_CANCELLED);
			
			ArrayList<ApplicationDataM> applications = uloApplicationGroup.filterApplicationLifeCycle();
			ArrayList<String> cancelUniqueIds = new ArrayList<String>();
			if(!Util.empty(applications)){
				for (ApplicationDataM application : applications) {
					application.setFinalAppDecision(DECISION_FINAL_DECISION_CANCEL);
					application.setFinalAppDecisionDate(ApplicationDate.getDate());
					application.setFinalAppDecisionBy(userId);
					cancelUniqueIds.add(application.getApplicationRecordId());
				}
			}
			logger.debug("cancelUniqueIds : "+cancelUniqueIds);
			
			ReasonDataM reasonM = new ReasonDataM();
			reasonM.setCreateBy(userId);
			reasonM.setUpdateBy(userId);
			reasonM.setReasonCode(CANCEL_REASON_CANCEL_FROM_TABLET);
			reasonM.setReasonOthDesc("");
			reasonM.setReasonType(REASON_TYPE_CANCEL);
			reasonM.setRemark("");
			
			
			ApplicationReasonDataM applicationReason = mapApplicationReason(reasonM);
			EAppAction.saveApplication(uloApplicationGroup, userId, "CANCEL");
			EAppAction.mapCisData(uloApplicationGroup, userId);
			cancelApplication(uloApplicationGroup.getApplicationGroupId(),cancelUniqueIds,applicationReason,userId);
			
			String action = "Cancel";
			EAppAction.workflowLog(uloApplicationGroup, uloApplicationGroup.getSourceUserId(), action);
			
			trace.end("EProcessCancel");
			trace.trace();
		}
		catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(EAppAction.error(e));
		}
		return processResponse;
	}
	
	public static ApplicationReasonDataM mapApplicationReason(ReasonDataM reasonM){
		String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
		ArrayList<ReasonDataM> reasons = new ArrayList<>();
		if(null != reasonM){
			ReasonDataM reasonAppM = new ReasonDataM();
				reasonAppM.setCreateBy(reasonM.getCreateBy());
				reasonAppM.setUpdateBy(reasonM.getUpdateBy());
				reasonAppM.setReasonCode(reasonM.getReasonCode());
				reasonAppM.setReasonOthDesc(reasonM.getReasonOthDesc());
				reasonAppM.setReasonType(REASON_TYPE_CANCEL);
				reasonAppM.setRemark(reasonM.getRemark());	
				reasons.add(reasonAppM);
		}
		ApplicationReasonDataM applicationReason = new ApplicationReasonDataM();
			applicationReason.setReasons(reasons);
		return applicationReason;
	}
	
	public void cancelApplication(String applicationGroupId,ArrayList<String> cancelUniqueIds,ApplicationReasonDataM applicationReason,String userId) {
//		String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
//		logger.debug("DECISION_FINAL_DECISION_CANCEL >> "+DECISION_FINAL_DECISION_CANCEL);
		try{
//			OrigApplicationDAO origApplication = ORIGDAOFactory.getApplicationDAO(userId);
			OrigReasonDAO origReason = ORIGDAOFactory.getReasonDAO(userId);
			OrigReasonLogDAO origReasonLog = ORIGDAOFactory.getReasonLogDAO(userId);
			if(!Util.empty(cancelUniqueIds)){
				for (String cancelUniqueId : cancelUniqueIds) {
					logger.debug("cancelUniqueId : "+cancelUniqueId);
//					origApplication.updateFinalAppDecision(cancelUniqueId,DECISION_FINAL_DECISION_CANCEL);
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
}
