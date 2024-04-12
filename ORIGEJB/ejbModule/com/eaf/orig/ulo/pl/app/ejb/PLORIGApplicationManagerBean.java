package com.eaf.orig.ulo.pl.app.ejb;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.ejb.EJBException;
//import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.model.todolist.UserWorkQueueDataM;
import com.ava.bpm.model.todolist.WfJobDataM;
//import com.ava.bpm.util.BpmConstant;
import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.inf.log.model.HistoryDataM;
import com.eaf.orig.logs.dao.LogDAO;
import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.ERROR;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigMasterGenParamDAO;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountCardDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigAccountLogDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationImageDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationLogDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationPointDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigNCBDocumentHistoryDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigPersonalInfoDAO;
import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonDAO;
//import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.ORIGMapModelModule;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigApplicationUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailSMSUtil;
//import com.eaf.orig.ulo.pl.app.utility.Performance;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountLogDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationActionDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationLogDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonLogDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportDataM;
import com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDedupDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

//@Stateless(mappedName = "PLORIGApplicationManagerBean")
public class PLORIGApplicationManagerBean implements PLORIGApplicationManager, PLORIGRemoteApplicationManager {

	private Connection connWorkFlow = null;
	Logger log = Logger.getLogger(PLORIGApplicationManagerBean.class);
	
//	Performance perf = new Performance("PLORIGApplicationManagerBean",Performance.Module.SAVE_MANAGER_BEAN);
	
	public PLORIGApplicationManagerBean() {
		super();
	}

	@Override
	public PLApplicationDataM loadPLApplicationDataM(String appRecId) {
		log.info("[loadPLApplicationDataM].. AppRecordID " + appRecId);
		PLApplicationDataM applicationM = null;
		try{
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			applicationM = applicationDAO.loadOrigApplication(appRecId);
			if(!OrigUtil.isEmptyString(applicationM.getProjectCode())){
				applicationM.setProjectCodeCreditLine(applicationDAO.loadCreditLine(applicationM.getProjectCode()));
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return (null == applicationM) ? new PLApplicationDataM(): applicationM;
	}

	@Override
	public PLApplicationDataM loadAppliationAppNo(String appNo) {
		PLApplicationDataM applicationM = null;
		try {
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			applicationM = applicationDAO.loadOrigApplicationAppNo(appNo);
			if(!OrigUtil.isEmptyString(applicationM.getProjectCode())){
				applicationM.setProjectCodeCreditLine(applicationDAO.loadCreditLine(applicationM.getProjectCode()));
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return (null == applicationM) ? new PLApplicationDataM(): applicationM;
	}
	
	@Override
	public void savePLApplicationDataM(PLApplicationDataM applicationM, UserDetailM userM) {
		try{
//			perf.init("savePLApplicationDataM", applicationM.getAppRecordID(), applicationM.getTransactionID(), userM);
			
			boolean checkExisting = false;
			applicationM.setCreateBy(userM.getUserName());
			applicationM.setUpdateBy(userM.getUserName());

			String role = userM.getCurrentRole();
			
//			perf.track(Performance.Action.SAVE_APPLICATION, Performance.START);		
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updateSaveOrigApplication(applicationM, userM);
//			perf.track(Performance.Action.SAVE_APPLICATION, Performance.END);			
			log.debug("Application Status >> "+applicationM.getApplicationStatus());	
				
			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			checkExisting = this.ValidateExistMainWorkflow( applicationM.getAppRecordID(), role, this.getWorkFlowConnection());
			
			log.debug("[savePLApplicationDataM]..Action " + applicationM.getAppDecision()+" isExistingWorkflow " + checkExisting);
			
//			perf.track(Performance.Action.SAVE_WORKFLOW, Performance.START);	
			if(checkExisting){
				if(OrigConstant.Action.SAVE_DRAFT.equalsIgnoreCase(applicationM.getAppDecision())){
					log.debug("[savePLApplicationDataM].. CancelClaim ");
					doUpdatePriority(applicationM, userM);
					this.cancelClaimApplication(applicationM, applicationM.getAppDecision(), userM);
				}else{
					log.debug("[savePLApplicationDataM].. Complete Application ");
					this.completeApplication(applicationM, userM);
				}					
			}else{
				if (OrigUtil.isEmptyString(applicationM.getAppDecision()) 
						|| OrigConstant.Action.SAVE_DRAFT.equalsIgnoreCase(applicationM.getAppDecision())) {
//					ORIGCacheUtil origCache = new ORIGCacheUtil();
//					String startFlowAction = origCache.getGeneralParamValue(OrigConstant.GeneralParamCode.START_FLOW_ACTION, applicationM.getBusinessClassId());
//					applicationM.setAppDecision(startFlowAction);
				}
				this.startBusinessProcess(applicationM, userM, applicationM.getAppDecision());
			}
			
//			perf.track(Performance.Action.SAVE_WORKFLOW, Performance.END);	
			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public String createApplicationICDC(PLApplicationDataM applicationM, UserDetailM userM) {
		String idNo = null;
		try {
			applicationM.setCreateBy(userM.getUserName());
			applicationM.setUpdateBy(userM.getUserName());
			PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			idNo = personM.getIdNo();
			//No need to insert idNo to database
			personM.setIdNo(null);
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updateSaveOrigApplication(applicationM, userM);
			log.debug("Application Status >> "+applicationM.getApplicationStatus());	
				
			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			
			if (OrigUtil.isEmptyString(applicationM.getAppDecision()) 
					|| OrigConstant.Action.SAVE_DRAFT.equalsIgnoreCase(applicationM.getAppDecision())) {
//				ORIGCacheUtil origCache = new ORIGCacheUtil();
//				String startFlowAction = origCache.getGeneralParamValue(OrigConstant.GeneralParamCode.START_FLOW_ACTION, applicationM.getBusinessClassId());
//				applicationM.setAppDecision(startFlowAction);
			}
			this.startBusinessProcess(applicationM, userM, applicationM.getAppDecision());
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return idNo;
	}

	private boolean ValidateExistMainWorkflow(String appRecordId, String role, Connection conn) throws Exception{
		BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowDataM workflowM = new WorkflowDataM();
			workflowM.setAppRecordID(appRecordId);
			workflowM.setUserRole(role);
		boolean isExisting = bpmWorkflow.ValidateExistMainWorkflow(workflowM, conn);
//		log.info("[ValidateExistMainWorkflow] isExisting " + isExisting);
		return isExisting;
	}

	private void startBusinessProcess(PLApplicationDataM applicationM, UserDetailM userM, String action) throws Exception {

		this.connWorkFlow = getWorkFlowConnection();

		String role = userM.getCurrentRole();

		log.debug("[startBusinessProcess].. action " + action+" AppRecordID = "+applicationM.getAppRecordID()+" AppStatus = "+applicationM.getApplicationStatus()+" role = "+role+" AppDate = "+applicationM.getAppDate());

		applicationM.setAppDecision(action);

		WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);

		BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
		WorkflowResponse response = bpmWorkflow.StartFlow(workflowM, this.connWorkFlow);

		log.debug("[startBusinessProcess].. response code " + response.getResultCode()+" response desc = "+response.getResultDesc());

		if (ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())) {
			
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updatePLApplicationStatus(response , userM);
			
			saveApplicationLOG(applicationM, response, userM);
			
			applicationM.setApplicationStatus(response.getAppStatus());
			applicationM.setJobState(response.getToAtid());
		}

	}

	private void cancelClaimApplication(PLApplicationDataM applicationM,String action, UserDetailM userM) throws Exception{

		WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);

		BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
		bpmWorkflow.SetAppInfo(workflowM, this.getWorkFlowConnection());

		WorkflowResponse response = bpmWorkflow.CancleClaimFlow(workflowM, this.getWorkFlowConnection());

		response.setToAtid(applicationM.getJobState());
		response.setUserName(applicationM.getUpdateBy());

		saveApplicationLOG(applicationM, response, userM);
		
		this.SetRoleDecision(applicationM, userM);
		
	}

	public void cancelClaimWithoutSaveLog(PLApplicationDataM plApplicationM, UserDetailM userM){
		this.connWorkFlow = getWorkFlowConnection();
		try{
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(plApplicationM, userM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
				bpmWorkflow.CancleClaimFlow(workflowM, this.connWorkFlow);
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	private Connection getWorkFlowConnection() {
		try {
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		} catch (Exception e) {
			log.fatal("Connection is null" + e.getMessage());
		}
		return null;
	}
	
	private PLApplicationLogDataM getApplicationLOG(PLApplicationDataM applicationM, UserDetailM userM){
		PLApplicationLogDataM applicationLogM = new PLApplicationLogDataM();		
		try{			
			applicationLogM.setAction(applicationM.getAppDecision());
			applicationLogM.setActionDesc(applicationM.getAppDecision());	
			applicationLogM.setApplicationRecordID(applicationM.getAppRecordID());
			applicationLogM.setApplicationStatus(applicationM.getApplicationStatus());
			applicationLogM.setCreateBy(userM.getUserName());
			applicationLogM.setJobState(applicationM.getJobState());
			applicationLogM.setClaimDate(new Timestamp(new Date().getTime()));
			applicationLogM.setLifeCycle(applicationM.getLifeCycle());
			if(0 == applicationLogM.getLifeCycle()){
				applicationLogM.setLifeCycle(1);
			}
			applicationLogM.setUpdateBy(userM.getUserName());			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return applicationLogM;
	}
	
	private PLApplicationLogDataM getApplicationLOGDay5(PLApplicationDataM applicationM, UserDetailM userM){
		PLApplicationLogDataM applicationLogM = new PLApplicationLogDataM();		
		try{			
			applicationLogM.setAction(applicationM.getAppDecision());
			applicationLogM.setActionDesc(OrigConstant.Action.SUBMIT_CASH_DAY5);	
			applicationLogM.setApplicationRecordID(applicationM.getAppRecordID());
			applicationLogM.setApplicationStatus(applicationM.getApplicationStatus());
			applicationLogM.setCreateBy(userM.getUserName());
			applicationLogM.setJobState(applicationM.getJobState());
			applicationLogM.setClaimDate(new Timestamp(new Date().getTime()));
			applicationLogM.setLifeCycle(applicationM.getLifeCycle());
			if(0 == applicationLogM.getLifeCycle()){
				applicationLogM.setLifeCycle(1);
			}
			applicationLogM.setUpdateBy(userM.getUserName());			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return applicationLogM;
	}
	
	private PLApplicationLogDataM getApplicationLOG(WorkflowResponse response, PLApplicationDataM applicationM, UserDetailM userM){
		PLApplicationLogDataM applicationLogM = new PLApplicationLogDataM();
		try{			
			applicationLogM.setAction(applicationM.getAppDecision());
			if(OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
				applicationLogM.setActionDesc("Save");
			}else{
				applicationLogM.setActionDesc(applicationM.getAppDecision());
			}
			applicationLogM.setApplicationRecordID(applicationM.getAppRecordID());
			if(!OrigUtil.isEmptyString(response.getAppStatus())){
				applicationLogM.setApplicationStatus(response.getAppStatus());
			}else{
				applicationLogM.setApplicationStatus(applicationM.getApplicationStatus());
			}
			applicationLogM.setCreateBy(response.getUserName());
			applicationLogM.setJobState(response.getToAtid());
			if(null != response.getClaimDate()) {
				applicationLogM.setClaimDate(new Timestamp(response.getClaimDate().getTime()));
			}
			applicationLogM.setLifeCycle(applicationM.getLifeCycle());
			if(0 == applicationLogM.getLifeCycle()){
				applicationLogM.setLifeCycle(1);
			}
			if(response.getClaimDate() == null){
				applicationLogM.setClaimDate(null);
			}else{
				applicationLogM.setClaimDate(new Timestamp(response.getClaimDate().getTime()));
			}
			applicationLogM.setUpdateBy(response.getUserName());
			
			applicationLogM.setReasonLogVect(getReasonLOG(response, applicationM, userM));
			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return applicationLogM;
	}
	
	private Vector<PLReasonLogDataM> getReasonLOG(WorkflowResponse response,PLApplicationDataM applicationM, UserDetailM userM) throws Exception{
		
		Vector<PLReasonLogDataM> reasonLogVect = new Vector<PLReasonLogDataM>();

		if(!OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getSubmitType())){ 
			Vector<PLReasonDataM> reasonVect = margeReasonM(applicationM.getReasonVect(), userM);						
			if (!OrigUtil.isEmptyVector(reasonVect)){				
				
				log.debug("getReasonLOG() reasonVect.size "+reasonVect.size());	
				
				PLReasonLogDataM reasonLogM = null;
				for(PLReasonDataM reasonM :reasonVect){
					reasonLogM = new PLReasonLogDataM();	
					reasonLogM.setCreateBy(response.getUserName());
					reasonLogM.setReasonCode(reasonM.getReasonCode());
					reasonLogM.setReasonType(reasonM.getReasonType());
					
					log.debug("getReasonLOG() Type >> "+reasonM.getReasonType());
					log.debug("getReasonLOG() Code >> "+reasonM.getReasonCode());
					
					reasonLogM.setRole(reasonM.getRole());
					reasonM.setCreateBy(response.getUserName());
					reasonM.setUpdateBy(response.getUserName());
					reasonLogM.setRemark(reasonM.getRemark());
					
					reasonLogVect.add(reasonLogM);					
				}				
				
				PLOrigReasonDAO origReasonDAO = PLORIGDAOFactory.getPLOrigReasonDAO();
					origReasonDAO.updateSaveOrigReason(reasonVect, applicationM.getAppRecordID(), userM.getCurrentRole());
			}
		}
		return reasonLogVect;
	}
	
	private Vector<PLReasonDataM> margeReasonM(Vector<PLReasonDataM> reasonVect,UserDetailM userM)throws Exception{
		Vector<PLReasonDataM> data = new Vector<PLReasonDataM>(); 
		String role = userM.getCurrentRole();
		if(null != reasonVect){
			for(PLReasonDataM reasonM : reasonVect){
				if(null != reasonM){
					if(null != role && role.equals(reasonM.getRole()) 
							&& !OrigConstant.FLAG_Y.equals(reasonM.getLoadFLAG())){
						if(!equalsReason(reasonM, data)){
							data.add(reasonM);
						}
					}
				}
			}
		}
		return data;
	}
	
	public boolean equalsReason(PLReasonDataM reasonM ,Vector<PLReasonDataM> data){
		if(null != data){
			for(PLReasonDataM dataM : data){
				if(dataM.getReasonCode().equals(reasonM.getReasonCode())
						&& dataM.getReasonType().equals(reasonM.getReasonType())){
					return true;
				}
			}
		}
		return false;
	}
	
	public WorkflowResponse claimApplication(PLApplicationDataM plApplicationM, UserDetailM userM) {
		try{
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(plApplicationM, userM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ClaimFlow(workflowM, this.getWorkFlowConnection());
			return response;
			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	private WorkflowResponse ClaimApplication(PLApplicationDataM applicationM, UserDetailM userM ,Connection conn) {
		try{
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
			workflowM.setCloseConn(false);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ClaimFlow(workflowM,conn);
			return response;			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	public WorkflowResponse claimApplication(String appRecId, String role, String userName){
		try{
			this.connWorkFlow = getWorkFlowConnection();
			
//			log.debug("[claimApplication].. role " + role);
//			log.debug("[claimApplication].. UserName " + userName);

			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(appRecId, role, userName);

			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ClaimFlow(workflowM, this.connWorkFlow);

//			log.debug("[claimApplication].. response code " + response.getResultCode());
//			log.debug("[claimApplication].. response desc " + response.getResultDesc());

			return response;
		} catch (Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public void completeApplication(PLApplicationDataM applicationM, UserDetailM userM) {
		try{
			this.connWorkFlow = getWorkFlowConnection();
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM, applicationM.getPtID());
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ComplateJob(workflowM, this.connWorkFlow);
			
			response.setClaimDate(applicationM.getClaimDate());
			
			applicationM.setApplicationStatus(response.getAppStatus());
			applicationM.setJobState(response.getToAtid());
			
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updatePLApplicationStatus(response ,userM);
			
			saveApplicationLOG(applicationM, response, userM);
			this.SetRoleDecision(applicationM, userM);
			
		}catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	private void CompleteApplication(PLApplicationDataM applicationM, UserDetailM userM,Connection conn) {
		try{
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM, applicationM.getPtID());
			workflowM.setCloseConn(false);
			
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ComplateJob(workflowM, conn);
			
			response.setClaimDate(applicationM.getClaimDate());
			
			applicationM.setApplicationStatus(response.getAppStatus());
			applicationM.setJobState(response.getToAtid());
			
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updatePLApplicationStatus(response ,userM);
			
			saveApplicationLOG(applicationM, response, userM);
			SetRoleDecision(applicationM, userM);
			
		}catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	private void CompleteApplicationForNCB(PLApplicationDataM applicationM, UserDetailM userM ,Connection conn) {
		try{			
			
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM, applicationM.getPtID());
			workflowM.setUserID(OrigConstant.SYSTEM);
			workflowM.setCloseConn(false);
			
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ComplateJob(workflowM, conn);
			
			response.setClaimDate(applicationM.getClaimDate());
			
			applicationM.setApplicationStatus(response.getAppStatus());
			applicationM.setJobState(response.getToAtid());
			
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			applicationDAO.updatePLApplicationStatus(response , userM);
			saveApplicationLOG(applicationM, response, userM);
			
			applicationM.setCbDecision(applicationM.getAppDecision());
			this.SetRoleDecision(applicationM, userM);
			
		}catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	private void doUpdatePriority(PLApplicationDataM applicationM, UserDetailM userDetailM) throws Exception{
		PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			applicationDAO.updatePriority(applicationM);
		int priority = Integer.parseInt(applicationM.getPriority());
		updatePriorityWF(applicationM, userDetailM, priority);
	}
	
	@Override
	public String updateSetPriority(PLApplicationDataM applicationM, UserDetailM userDetailM){
		try{
//			log.debug("[updateSetPriority]");
			if(!OrigUtil.isEmptyObject(applicationM)){
				
				WorkflowDataM workflowM = new WorkflowDataM();
				BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
				
				workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userDetailM);
				WorkflowResponse wfResponse = bpmWorkflow.getWorkQueueDataM(workflowM, this.getWorkFlowConnection());
				WfJobDataM wfJboM = wfResponse.getWfJobM();
				
				//check claim
				if(wfJboM.getActivityState().equals("2")){					
					int priority = 0;
					priority = Integer.parseInt(applicationM.getPriority());
					PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
					int returnRow = applicationDAO.updatePriority(applicationM);
	
					if(returnRow == 0){
						return applicationM.getApplicationNo();
					}else{
						updatePriorityWF(applicationM, userDetailM, priority);
					}
				
				}else{
					return applicationM.getApplicationNo();
				}
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return null;
	}

	private void updatePriorityWF(PLApplicationDataM applicationM, UserDetailM userDetailM, int priority){
		this.connWorkFlow = getWorkFlowConnection();
		try {
//			log.debug("updatePriorityWF");
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userDetailM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			workflowM.setPriority(priority);
			bpmWorkflow.SetPriorityWorkQueue(workflowM, this.connWorkFlow);
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	public void claimAndCompleteApplicationVect(Vector<PLApplicationDataM> appDataVect, UserDetailM userDetailM){
		try{
			if (!OrigUtil.isEmptyVector(appDataVect)){
				WorkflowResponse workFlow =null;
				for(PLApplicationDataM appDataM : appDataVect){
					workFlow = claimApplication(appDataM, userDetailM);

					appDataM.setPtID(workFlow.getPtid());
					appDataM.setJobID(workFlow.getJobId());
					appDataM.setPtType(workFlow.getPtType());
					appDataM.setClaimDate(new Timestamp(workFlow.getClaimDate().getTime()));
					appDataM.setApplicationStatus(workFlow.getAppStatus());
					
					PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
						origDAO.updateSaveOrigApplication(appDataM, userDetailM);

					completeApplication(appDataM, userDetailM);
				}
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	public void confirmReject(Vector<PLApplicationDataM> appDataVect, UserDetailM userDetailM){
		try{
			if(!OrigUtil.isEmptyVector(appDataVect)){
				for (int i = 0; i < appDataVect.size(); i++){
					PLApplicationDataM appDataM = appDataVect.get(i);
					if(!OrigUtil.isEmptyString(appDataM.getReopenFlag()) && OrigConstant.FLAG_Y.equals(appDataM.getReopenFlag())){
						appDataM.setAppDecision(OrigConstant.Action.REJECT_SKIP_DF);
					} else {
						appDataM.setAppDecision(OrigConstant.Action.CONFIRM_REJECT);
					}
					appDataVect.remove(i);
					appDataVect.add(i, appDataM);
				}
				claimAndCompleteApplicationVect(appDataVect, userDetailM);
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	public void unblockApplication(Vector<PLApplicationDataM> appDataVect, UserDetailM userDetailM){
		try{
			if(!OrigUtil.isEmptyVector(appDataVect)){
				
				BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();	
				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				
				for(PLApplicationDataM applicationM : appDataVect){
					
					log.debug("Process AppRecID >> "+applicationM.getAppRecordID());
					log.debug("Action >> "+applicationM.getAppDecision());
					
					WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userDetailM);
					WorkflowResponse response = bpmWf.ClaimFlow(workflowM, this.getWorkFlowConnection());
					
					if(null == response || !ResultCodeConstant.RESULT_CODE_SUCCESS.equals(response.getResultCode())){
						log.debug("ERROR : Claim Application Not Complete !!");
						if(null != response){
							log.debug("MESSAGE : "+response.getResultDesc());
						}
						throw new EJBException("ERROR : Claim Application Not Complete !!");
					}
					
					applicationM.setPtID(response.getPtid());
					applicationM.setJobID(response.getJobId());
					applicationM.setPtType(response.getPtType());
					
					if(response.getClaimDate() != null){
						applicationM.setClaimDate(new Timestamp (response.getClaimDate().getTime()));
					}
					completeApplication(applicationM, userDetailM);
					SetRoleDecision(applicationM, userDetailM);
					
					updateBlockFlag(applicationM ,userDetailM);
					
					if(!OrigUtil.isEmptyString(applicationM.getFinalAppDecision())){
						applicationDAO.updateFinalAppDecision(applicationM);
					}
					
					if(OrigConstant.FLAG_Y.equals(applicationM.getUnLock())){
						PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
//						#septtemwi comment
//						PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
//						if(OrigUtil.isEmptyObject(xrulesVerM)){
//							xrulesVerM = new PLXRulesVerificationResultDataM();
//							personalM.setXrulesVerification(xrulesVerM);
//						}
//						Vector<PLXRulesDedupDataM> deDupVect = xrulesVerM.getVXRulesDedupDataM();	
						if(null != personalM.getPersonalID()){
							XRulesRemoteDAOUtilManager xrulesBean = PLORIGEJBService.getXRulesDAOUtilManager();		
								xrulesBean.ClearDeDupResult(personalM.getPersonalID());
						}
					}
					
				}
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	private void updateBlockFlag(PLApplicationDataM applicationM ,UserDetailM userM) {
		try {
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updateBlockFlag(applicationM ,userM);
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public String reassingOrReallocateOrSendbackApplication(PLApplicationDataM appM, UserDetailM userM, String action,
										String reassignTo, String reassignType) {

		PLApplicationActionDataM actionM = appM.getApplicationActionM();
		appM.setAppDecision(actionM.getAction());
		try{
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(appM, userM);
			workflowM.setReassignTo(actionM.getAssignTo());
			workflowM.setReassignType(actionM.getAssignType());

			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ReassignJob(workflowM, this.getWorkFlowConnection());

			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			int retrunRows = applicationDAO.updatePLApplicationStatus(response ,userM);
			
			if(retrunRows == 0){
				return appM.getApplicationNo();
			}else{
				saveApplicationLOG(appM, response, userM);
			}

		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
		return null;
	}

	@Override
	public void reIssueCardNo(PLAccountCardDataM accCardM, UserDetailM userM){
		try{
			UtilityDAO utilDAO = new UtilityDAOImpl();	
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();	
			PLApplicationDataM applicationM = applicationDAO.loadAppInFo(utilDAO.getAppRecordID(accCardM.getApplicationNo()));
				applicationM.setAppDecision(OrigConstant.Action.RE_ISSUE_CARD_NO);
				applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			
			this.connWorkFlow = getWorkFlowConnection();			
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
			BpmWorkflow workflow = BpmProxyService.getBpmWorkflow();
				workflow.ForceChangeProcessState(workflowM, this.connWorkFlow);
			
			PLApplicationLogDataM applicationLOG = getApplicationLOG(applicationM, userM);			
			PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
				applicationLogDAO.saveApplicationLog(applicationLOG, applicationM);	
				
			PLOrigAccountCardDAO accountCardDAO = PLORIGDAOFactory.getPLOrigAccountCardDAO();
				accountCardDAO.reIssueCardNo(accCardM, userM);
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	private void saveApplicationLOG(PLApplicationDataM applicationM, WorkflowResponse response,UserDetailM userM){
		try{			
			PLApplicationLogDataM applicationLOG = getApplicationLOG(response, applicationM, userM);
			
			PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
				applicationLogDAO.saveApplicationLog(applicationLOG, applicationM);
			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void claimAndCompleteApplication(PLApplicationDataM applicationM, UserDetailM userM){
		try{
			WorkflowResponse workFlow = new WorkflowResponse();
			workFlow = claimApplication(applicationM, userM);

			applicationM.setPtID(workFlow.getPtid());
			applicationM.setJobID(workFlow.getJobId());
			applicationM.setPtType(workFlow.getPtType());
			applicationM.setClaimDate(new Timestamp(workFlow.getClaimDate().getTime()));
			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			
			completeApplication(applicationM, userM);

			SetRoleDecision(applicationM, userM);

		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public void claimSaveAndCompleteApplication(PLApplicationDataM applicationM, UserDetailM userM){
		try{
			WorkflowResponse workFlow = new WorkflowResponse();
			workFlow = claimApplication(applicationM, userM);

			applicationM.setPtID(workFlow.getPtid());
			applicationM.setJobID(workFlow.getJobId());
			applicationM.setPtType(workFlow.getPtType());
			applicationM.setClaimDate(new Timestamp(workFlow.getClaimDate().getTime()));
			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));			
			
			completeApplication(applicationM, userM);
			
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();			
				applicationDAO.updateSaveOrigApplication(applicationM, userM);
				
			SetRoleDecision(applicationM, userM);

		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void claimAndCompleteApplicationWithOutRole(PLApplicationDataM applicationM, UserDetailM userM) {
		
		try{
			WorkflowResponse workFlow = new WorkflowResponse();
			workFlow = ClaimAppWithOutRole(applicationM, userM);

			applicationM.setPtID(workFlow.getPtid());
			applicationM.setJobID(workFlow.getJobId());
			applicationM.setPtType(workFlow.getPtType());
			applicationM.setClaimDate(new Timestamp(workFlow.getClaimDate().getTime()));
			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			
			completeApplication(applicationM, userM);
			
			if(!OrigUtil.isEmptyString(applicationM.getFinalAppDecision())){
				PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				origDAO.updateFinalAppDecision(applicationM);
			}

		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public WorkflowResponse ClaimAppWithOutRole(PLApplicationDataM appDataM,UserDetailM userM) {
		try {
			
//			log.debug("[ClaimAppWithOutRole].. UserName " + userM.getUserName());
			
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(appDataM, userM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ClaimFlow(workflowM, this.getWorkFlowConnection());
			
//			log.debug("[ClaimAppWithOutRole].. response code " + response.getResultCode());
//			log.debug("[ClaimAppWithOutRole] .. response desc " + response.getResultDesc());
			
			return response;
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	public String SetButtonStatus(WorkflowDataM workflowM, UserDetailM userM){		
		try {
			BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWf.SetToDoListOnJob(workflowM.getTdID(), userM.getUserName(), workflowM.getStatus(), this.getWorkFlowConnection());
			UserWorkQueueDataM userWorkQueueM = response.getUserWorkQueueM();
			if (userWorkQueueM == null) userWorkQueueM = new UserWorkQueueDataM();
			return userWorkQueueM.getJobOnFlag();
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void PullWorkflowJob(WorkflowDataM workflowM, UserDetailM userM) {
		try{
			this.connWorkFlow = getWorkFlowConnection();
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
				bpmWorkflow.PullJob(workflowM.getTdID(), userM.getUserName(), workflowM.getTotalJob(), this.connWorkFlow);
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	private void LogicApplicationRole(PLApplicationDataM applicationM, UserDetailM userM){
		try{
			String role = userM.getCurrentRole();
			if(!OrigUtil.isEmptyString(role)){				
				if (OrigConstant.ROLE_IMG.equals(role)){
					return;
				}	

				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();	
					applicationDAO.LoadRoleDecision(applicationM);
				
//				ORIGLogic origLogic = new ORIGLogic();				
//					origLogic.SetRoleDecision(role, applicationM, userM);
						
					applicationDAO.updateRoleDecision(applicationM);			
					
			}	
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	private void SetRoleDecision (PLApplicationDataM applicationM, UserDetailM userM){
		try{
			String role = userM.getCurrentRole();
			if(!OrigUtil.isEmptyString(role)){				
				if (OrigConstant.ROLE_IMG.equals(role)){
					return;
				}			
//				ORIGLogic origLogic = new ORIGLogic();				
//					origLogic.SetRoleDecision(role, applicationM, userM);
				//save
				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
					applicationDAO.updateRoleDecision(applicationM);			
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void reOpenFlow(PLApplicationDataM applicationM, UserDetailM userM){
		this.connWorkFlow = getWorkFlowConnection();
		try{			
			applicationM.setOwner(userM.getUserName());
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse wfResponse = bpmWorkflow.ReopenFlow(workflowM, this.connWorkFlow);

			applicationM.setApplicationStatus(wfResponse.getAppStatus());
			applicationM.setJobState(wfResponse.getToAtid());
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();

				applicationDAO.updateSaveOrigApplication(applicationM, userM);
				applicationDAO.updatePLApplicationStatus(wfResponse ,userM);
				applicationDAO.updateReOpenFlag(applicationM);
				applicationDAO.updateFinalAppDecision(applicationM); //Update final app decision
				
			saveApplicationLOG(applicationM, wfResponse, userM);
			SetRoleDecision(applicationM, userM);
			
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	public String reWorkApplication(PLApplicationDataM appM, UserDetailM userDetailM) {

		try {
//			log.debug("reWorkApplication");

			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(appM, userDetailM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ClaimFlow(workflowM, this.getWorkFlowConnection());

			appM.setPtID(response.getPtid());
			appM.setJobID(response.getJobId());
			appM.setPtType(response.getPtType());
			appM.setClaimDate(new Timestamp(response.getClaimDate().getTime()));

			// capport
//			PLOrigApplicationUtil appUtil = new PLOrigApplicationUtil();
//			String capportNo = appUtil.getCreditLineCapportNo(appM); // get credit line capport number from ILog
			
//			if (!OrigUtil.isEmptyString(capportNo)) {
//				CapportGroupDataM capGroupM = PLORIGEJBService.getORIGDAOUtilLocal().getCapportGroupDetails(capportNo);
//				if(!OrigUtil.isEmptyString(capGroupM.getCapportGroupId())){
//					PLORIGDAOFactory.getPLOrigCapportDAO().updateCapportUsed(capportNo, appM.getAppRecordID(), appM.getBusinessClassId(), OrigConstant.capportType.DECREASE, appM.getUpdateBy());
//				}
//			}
			completeApplication(appM, userDetailM);
			
			//delete application point where life cycle = max
			PLOrigApplicationPointDAO plorigApplicationPointDAO = PLORIGDAOFactory.getPLOrigApplicationPointDAO();
			plorigApplicationPointDAO.deletePoint(appM.getAppRecordID());
//			log.debug("appM.getJobState() = "+appM.getJobState());

			PLORIGDAOFactory.getPLOrigApplicationDAO().updateFinalAppDecision(appM); //Update final app decision
			
			//call PL/SQL to process account and card for rework
			PLORIGDAOFactory.getPLOrigAccountCardDAO().processAccountCardForRework(appM, userDetailM);
			return null;

		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void updateAppClaimCompleteForNCB(PLApplicationDataM applicationM, UserDetailM userM, String consentRefNo, String TrackingCode) {
		OrigObjectDAO origObjDAO = new OrigObjectDAO();
		try{				
				Connection connWorkFlow = origObjDAO.getConnection(OrigServiceLocator.WORKFLOW_DB);
				WorkflowDataM workflowM = new WorkflowDataM();
				workflowM.setCloseConn(false);
				workflowM.setAppRecordID(applicationM.getAppRecordID());
				
				BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
				Timestamp qDate = bpmWorkflow.getStartQueueInRole(OrigConstant.ROLE_CB, workflowM, connWorkFlow);

				applicationM.setCreateBy(userM.getUserName());
				applicationM.setUpdateBy(userM.getUserName());
				applicationM.setAppDecision(OrigConstant.Action.REQUEST_CB);
				applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
				
				log.debug("Complete Job State STC0401 TO STC0402 ... ");				
				this.ClaimCompleteForNCB(applicationM, userM ,connWorkFlow);		

				PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
				PLXRulesVerificationResultDataM xruleVerM = personalM.getXrulesVerification();
				if (OrigUtil.isEmptyObject(xruleVerM)) {
					xruleVerM = new PLXRulesVerificationResultDataM();
					personalM.setXrulesVerification(xruleVerM);
				}

				xruleVerM.setNCBConsentRefNo(consentRefNo);

				// set update date
				xruleVerM.setNcbUpdateBy(userM.getUserName());
				
				xruleVerM.setNCBConsentRefNoDate(new java.sql.Timestamp(new Date().getTime()));
				xruleVerM.setNcbUpdateDate(new java.sql.Timestamp(new Date().getTime()));
				xruleVerM.setNcbRQapprover(userM.getUserName());
				xruleVerM.setNCBTrackingCode(TrackingCode);

				applicationM.setAppDecision(OrigConstant.Action.WAIT_RESULT);
				applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));

				// claim STC0402 TO STC0403
				WorkflowResponse response = ClaimApplication(applicationM,userM,connWorkFlow);
				applicationM.setPtID(response.getPtid());
				applicationM.setApplicationStatus(response.getAppStatus());
				applicationM.setPtType(response.getPtType());
				applicationM.setJobID(response.getJobId());

				log.debug("Save Application State STC0402 ...");
				
				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
					applicationDAO.saveAppMForNCB(applicationM, userM);
				
				log.debug("Complete Job State STC0402 TO STC0403 ...");
				
				CompleteApplicationForNCB(applicationM, userM , connWorkFlow);				

				String trackingCode = xruleVerM.getNCBTrackingCode();
				
				log.debug("Save NCB Data Request LOG ...");				
				NCBServiceManager ncbBean = PLORIGEJBService.getNCBServiceManager();
					ncbBean.saveUpdateNCBDataRequest(trackingCode, NCBConstant.LOG_ACTIVITY_IN, applicationM.getApplicationNo(),userM.getUserName(), qDate);
				
//				Save Log NCB Doc History ... #CR Consent Image Alignment	
				if(null != personalM){
					Vector<PLNCBDocDataM> ncbDocVect = personalM.getNcbDocVect();
					if(!OrigUtil.isEmptyVector(ncbDocVect)){
						log.debug("Save Log NCB Doc History ...");
						PLOrigNCBDocumentHistoryDAO ncbDocDAO = PLORIGDAOFactory.getPLOrigNCBDocumentHistoryDAO();
							ncbDocDAO.SaveNCB_DOCUMENT_HISTORY(ncbDocVect, userM, consentRefNo);
					}
				}	
				
		}catch(Exception e){
			log.fatal("Exception ",e);
			try{
				origObjDAO.closeConnection(connWorkFlow);	
			}catch (Exception ex) {
				log.fatal("Exception ",ex);
			}
			throw new EJBException(e.getMessage());
		}finally{
			try{
				origObjDAO.closeConnection(connWorkFlow);	
			}catch (Exception e) {
				log.fatal("Exception ",e);
			}
		}
	}
	
	@Override
	public PLResponseImportDataM importCreditLineData(String sessionId, PLAttachmentHistoryDataM attachmentM,
										Vector<PLImportCreditLineDataM> importCreditLineVect,
													UserDetailM userM){
		
		PLResponseImportDataM importResponseM = null;
		try {
			PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().saveUpdateModelOrigAttachmentHistoryM(attachmentM);
			PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().saveTable_ORIG_CREDIT_LINE_IMPORT_MASTER(sessionId, attachmentM.getAttachId(), userM);
			PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().saveTable_ORIG_CREDIT_LINE_IMPORT_DETAIL( importCreditLineVect);
			PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().processAutoIncreaseDecrease(sessionId);
			
			importResponseM = PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().loadResultAutoIncreaseDecrease(sessionId);
				PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().delete_ORIG_CREDIT_LINE_IMPORT_MASTER(sessionId);
			return importResponseM;
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public boolean importCreditLineDataOnly(String sessionId, PLAttachmentHistoryDataM attachmentM
							,Vector<PLImportCreditLineDataM> importCreditLineVect,UserDetailM userM) {
		
		try {
			PLORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().saveUpdateModelOrigAttachmentHistoryM(attachmentM);
			PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().saveTable_ORIG_CREDIT_LINE_IMPORT_MASTER(sessionId, attachmentM.getAttachId(), userM);
			PLORIGDAOFactory.getPLOrigImportCreditLineDataDAO().saveTable_ORIG_CREDIT_LINE_IMPORT_DETAIL( importCreditLineVect);
			return true;
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	public void sendEmailSMS(PLApplicationDataM applicationM, String role) {
//		PLOrigEmailSMSUtil emailSMSUtil = new PLOrigEmailSMSUtil();
//		try{
//			emailSMSUtil.sendEmailSMS(applicationM, role);
//		}catch(Exception e){
//			log.fatal("Exception ",e);
//			throw new EJBException(e.getMessage());
//		}
	}

	@Override
	public void SaveDeplicateApplication(PLApplicationDataM applicationM, UserDetailM userM) {
		try {
			log.debug("[SaveDeplicateApplication] Save Block Main Application !");
			applicationM.setAppDecision(OrigConstant.Action.BLOCK);
			applicationM.setBlockFlag(OrigConstant.BLOCK_FLAG);
			this.savePLApplicationDataM(applicationM, userM);

			log.debug("[SaveDeplicateApplication] Save Block Deplicate Application !");

			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if (null == xrulesVerM) {
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			Vector<PLXRulesDedupDataM> dedupVect = xrulesVerM.getVXRulesDedupDataM();
			WorkflowResponse wfResponse = null;
			WorkflowDataM workflowM = null;
			PLApplicationDataM objAppM = null;
			
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			
//			ORIGLogic origLogic = new ORIGLogic();	
						
			OrigMasterGenParamDAO origGenDAO = PLORIGDAOFactory.getMasterGenParamDAO();
			String param = origGenDAO.GetParamBusClassGroup("DUPLICATE_IGNORE_JOBSTATE", applicationM.getBusinessClassId());	
			
			if (!OrigUtil.isEmptyObject(dedupVect)) {
				for (PLXRulesDedupDataM dedupDataM : dedupVect) {
					if (PLXrulesConstant.DeplicateType.DEPLICATE_INPROCESS.equals(dedupDataM.getDeDupType())) {
							workflowM = new WorkflowDataM();
							workflowM = ORIGMapModelModule.mappModelWorkflowDataM(dedupDataM, userM);
							wfResponse = bpmWorkflow.getWorkQueueDataM(workflowM, this.getWorkFlowConnection());
							WfJobDataM wfJobM = wfResponse.getWfJobM();
							if (null == wfJobM){
								wfJobM = new WfJobDataM();
							}
							if (OrigConstant.PROCESS_BLOCK.equals(wfJobM.getProcessState())) {
								log.debug("This Application Live in Block State!!");
								continue;
							}
							
//							if (BpmConstant.ACTIVITY_READY.equals(wfJobM.getActivityState())
//										&& !origLogic.IgnoreJobState(param, wfJobM.getAtID())){
//								log.debug("Complete Job Block This Application");								
//								objAppM = new PLApplicationDataM();
//								objAppM = this.loadPLApplicationDataM(dedupDataM.getApplicationRecordId());
//								
//								ORIGMapModelModule.MapModelPLApplicationDataM(objAppM,
//												wfJobM, BpmProxyConstant.WorkflowProcessType.PROCESS_TYPE_MAINPROCESS,
//													OrigConstant.Action.BLOCK, OrigConstant.BLOCK_FLAG);
//								this.savePLApplicationDataM(objAppM, userM);
//								
//							}else{
//								log.debug("Update Block This Application");
//								this.UpdateBlockApplication(dedupDataM, userM);
//							}
					}
				}
			}
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public void SaveDeplicatePOApplication (PLApplicationDataM applicationM, UserDetailM userM) {
		try {
			log.debug("[SaveDeplicateApplication] Save Block Main Application ! appRecordId = "+applicationM.getAppRecordID());
			applicationM.setAppDecision(OrigConstant.Action.REOPEN_BLOCK);
			applicationM.setReopenFlag(OrigConstant.FLAG_Y);
			applicationM.setLifeCycle(applicationM.getLifeCycle()+1);
			applicationM.setBlockFlag(OrigConstant.BLOCK_FLAG);
			this.reOpenFlow(applicationM, userM);

			log.debug("[SaveDeplicateApplication] Save Block Deplicate Application !");

			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if (null == xrulesVerM) {
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			Vector<PLXRulesDedupDataM> dedupVect = xrulesVerM.getVXRulesDedupDataM();
			WorkflowResponse wfResponse = null;
			WorkflowDataM workflowM = null;
			PLApplicationDataM objAppM = null;
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			
//			ORIGLogic origLogic = new ORIGLogic();	
			
			OrigMasterGenParamDAO origGenDAO = PLORIGDAOFactory.getMasterGenParamDAO();
			String param = origGenDAO.GetParamBusClassGroup("DUPLICATE_IGNORE_JOBSTATE", applicationM.getBusinessClassId());
			
			if (!OrigUtil.isEmptyObject(dedupVect)) {
				for (PLXRulesDedupDataM dedupDataM : dedupVect) {
					if (PLXrulesConstant.DeplicateType.DEPLICATE_INPROCESS.equals(dedupDataM.getDeDupType())) {
							workflowM = new WorkflowDataM();
							workflowM = ORIGMapModelModule.mappModelWorkflowDataM(dedupDataM, userM);
							wfResponse = bpmWorkflow.getWorkQueueDataM(workflowM, this.getWorkFlowConnection());
							WfJobDataM wfJobM = wfResponse.getWfJobM();
							if (null == wfJobM){
								wfJobM = new WfJobDataM();
							}
							if (OrigConstant.PROCESS_BLOCK.equals(wfJobM.getProcessState())) {
								log.debug("This Application Live in Block State!!");
								continue;
							}

//							if (BpmConstant.ACTIVITY_READY.equals(wfJobM.getActivityState())
//												&& !origLogic.IgnoreJobState(param, wfJobM.getAtID())) {
//								log.debug("Complete Job Block This Application");
//								objAppM = new PLApplicationDataM();
//								objAppM = this.loadPLApplicationDataM(dedupDataM.getApplicationRecordId());
//								ORIGMapModelModule.MapModelPLApplicationDataM(objAppM,
//												wfJobM, BpmProxyConstant.WorkflowProcessType.PROCESS_TYPE_MAINPROCESS,
//												OrigConstant.Action.BLOCK, OrigConstant.BLOCK_FLAG);
//								this.savePLApplicationDataM(objAppM, userM);
//							}else{
//								log.debug("Update Block This Application");
//								this.UpdateBlockApplication(dedupDataM, userM);
//							}
					}
				}
			}
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public void SaveCancelPOApplication (PLApplicationDataM applicationM, UserDetailM userM) {
		try {
			log.debug("[SaveCancelPOApplication] Save Cancel Main Application ! appRecordId = "+applicationM.getAppRecordID());
			applicationM.setAppDecision(OrigConstant.Action.REOPEN_WITH_CANCEL);
			applicationM.setReopenFlag(OrigConstant.FLAG_Y);
			applicationM.setLifeCycle(applicationM.getLifeCycle()+1);
			applicationM.setBlockFlag(OrigConstant.FLAG_C);
			this.reOpenFlow(applicationM, userM);

		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	private void UpdateBlockApplication(PLXRulesDedupDataM dedupDataM, UserDetailM userM) throws Exception{
		PLApplicationDataM applicationM = new PLApplicationDataM();
		PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			applicationM.setAppRecordID(dedupDataM.getApplicationRecordId());
			applicationM.setBlockFlag(OrigConstant.BLOCK_FLAG);
			applicationM.setUpdateBy(userM.getUserName());
		origDAO.updateBlockFlag(applicationM,userM);
	}

	@Override
	public void reIssueCustNo(PLAccountDataM accM, UserDetailM userM){
		try{				
			UtilityDAO utilDAO = new UtilityDAOImpl();	
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();	
			PLApplicationDataM applicationM = applicationDAO.loadAppInFo(utilDAO.getAppRecordID(accM.getApplicationNo()));
				applicationM.setAppDecision(OrigConstant.Action.RE_ISSUE_CUSTOMER_NO);
				applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			
			this.connWorkFlow = getWorkFlowConnection();			
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
			BpmWorkflow workflow = BpmProxyService.getBpmWorkflow();
				workflow.ForceChangeProcessState(workflowM, this.connWorkFlow);
			
			PLApplicationLogDataM applicationLOG = getApplicationLOG(applicationM, userM);			
			PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
				applicationLogDAO.saveApplicationLog(applicationLOG, applicationM);
			
			PLOrigAccountDAO accountDAO = PLORIGDAOFactory.getPLOrigAccountDAO();
			String custNo = accountDAO.reissueCustNo(accM.getAccountId());
			// set accLogM
			PLAccountLogDataM accLogM = new PLAccountLogDataM();
				accLogM.setAccId(accM.getAccountId());
				accLogM.setAction(OrigConstant.cardMaintenance.RE_ISSUE_CUST_NO);
				accLogM.setCreateBy(userM.getUserName());
				accLogM.setOldValue(accM.getCardlinkCustNo());
				accLogM.setNewValue(custNo);
			// saveLog
			PLOrigAccountLogDAO accountLOGDAO = PLORIGDAOFactory.getPLorigAccountLogDAO();
				accountLOGDAO.saveAccLog(accLogM);			
			//update cardLinkStatus
			accountDAO.updateCardLinkStatus(accM.getAccountId(), OrigConstant.cardLinkStatus.RE_ISSUED, userM.getUserName());

		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void SaveFollowDocument(PLApplicationImageDataM appImgM, String appRecID) {
//		log.debug("[SaveFollowDocument] appRecID " + appRecID);
		try{
			PLOrigApplicationDAO appDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				appDAO.UpdateRetrieveNewImage(appRecID);
			PLOrigApplicationImageDAO imageDAO = PLORIGDAOFactory.getPLOrigApplicationImageDAO();
				imageDAO.SaveUpdateFollowImage(appImgM, appRecID);
			
			WorkflowDataM workflowM = new WorkflowDataM();
				workflowM.setAppRecordID(appRecID);
				workflowM.setUserID(OrigConstant.SYSTEM);
				workflowM.setAppStatus(OrigConstant.ApplicationStatus.RECEIVED_DOCUMENT);
				
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();	
			WorkflowResponse response = bpmWorkflow.getWorkQueueDataM(workflowM, this.getWorkFlowConnection());
				WfJobDataM wfJobM = response.getWfJobM();
				if (null == wfJobM) wfJobM = new WfJobDataM();
//			log.debug("RoleID >> "+wfJobM.getRoleID());
//			log.debug("ActivityType >> "+wfJobM.getActivityType());
			if(OrigConstant.ROLE_FU.equals(wfJobM.getRoleID())
					&& OrigConstant.ActivityState.IQ.equals(wfJobM.getActivityType())){
				BpmWorkflow bpmProxy = BpmProxyService.getBpmWorkflow();
					bpmProxy.ChangeStatusWorkflow(workflowM, this.getWorkFlowConnection());
				appDAO.UpdateFollowApplicationStatus(appRecID, workflowM.getAppStatus());
			}
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void saveCashDay5PLApplicationDataM(PLApplicationDataM applicationM, UserDetailM userM){		
		try{
			PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				origDAO.updateSaveOrigApplication(applicationM, userM);
			PLOrigAccountDAO accountDAO = PLORIGDAOFactory.getPLOrigAccountDAO();
				accountDAO.updateCASHDAY1_STATUS(applicationM.getApplicationNo(), OrigConstant.cardLinkStatus.NOT_SENT, userM.getUserName());			
			PLApplicationLogDataM applicationLOG = getApplicationLOGDay5(applicationM, userM);			
			PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
				applicationLogDAO.saveApplicationLog(applicationLOG, applicationM);			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void pullJobBundling(PLApplicationDataM applicationM, UserDetailM userM) {
		
		applicationM.setCreateBy(userM.getUserName());
		applicationM.setUpdateBy(userM.getUserName());
		WorkflowResponse response = null;
		
		try{			
			PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
					origDAO.updateSaveOrigApplication(applicationM, userM);

			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM, applicationM.getPtID());
			
			response = bpmWorkflow.ClaimFlow(workflowM,this.getWorkFlowConnection());
			
			if(null != response && ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){
			
				response = bpmWorkflow.ComplateJob(workflowM,this.getWorkFlowConnection());
	
				response.setClaimDate(applicationM.getClaimDate());
	
				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
	
				// Update application status to model application
				if(OrigUtil.isEmptyString(response.getAppStatus())) {
					applicationM.setApplicationStatus(applicationDAO.loadApplicationStatus(applicationM.getAppRecordID()));
					response.setAppStatus(applicationM.getApplicationStatus());
				}else{
					applicationM.setApplicationStatus(response.getAppStatus());
				}
				
				if(ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){
					applicationDAO.updatePLApplicationStatus(response ,userM);
					saveApplicationLOG(applicationM, response, userM);
					SetRoleDecision(applicationM, userM);
				}
				
			}else{
				log.error("ERROR : Cannot Claim Application !! "+applicationM.getAppRecordID());
				throw new EJBException(ERROR.MSG_CANNOT_CLAIM);
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void claimCompleteAndSaveLogApplication(PLApplicationDataM appDataM, UserDetailM userM){
		try{
			WorkflowResponse workFlow = new WorkflowResponse();
			workFlow = claimApplication(appDataM, userM);
			appDataM.setPtID(workFlow.getPtid());
			appDataM.setJobID(workFlow.getJobId());
			appDataM.setPtType(workFlow.getPtType());
			if (null != workFlow.getClaimDate()) {
				appDataM.setClaimDate(new Timestamp(workFlow.getClaimDate().getTime()));
			}
			completeApplication(appDataM, userM);
			saveApplicationLOG(appDataM, workFlow, userM);
			SetRoleDecision(appDataM, userM);
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}

	}

	@Override
	public void createAppBundling(PLApplicationDataM applicationM, UserDetailM userM) {
		try {
//			boolean checkExisting = false;
			applicationM.setCreateBy(userM.getUserName());
			applicationM.setUpdateBy(userM.getUserName());

//			String role = userM.getCurrentRole();
//			log.debug("[createAppBundling]..role " + role);

			PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				origDAO.updateSaveOrigApplication(applicationM, userM);

//			log.debug("[createAppBundling].. AppDecision " + applicationM.getAppDecision());
//			log.debug("[createAppBundling].. ApplicationStatus " + applicationM.getApplicationStatus());
//			log.debug("[createAppBundling]..Save PLApplication Success!");

			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));

//			log.debug("[createAppBundling]..Action " + applicationM.getAppDecision());
//			log.debug("[createAppBundling]..isExistingWorkflow " + checkExisting);
			
			if(OrigConstant.Action.START_BUNDLING_CLAIM.equals(applicationM.getAppDecision())){
				applicationM.setOwner(userM.getUserName());
			}
			
//			log.debug("[createAppBundling].. get Owner ="+applicationM.getOwner());	
//			log.debug("[createAppBundling].. Not Existing Workflow Logic ");		
			
			startBusinessProcess(applicationM, userM, applicationM.getAppDecision());

			if (OrigConstant.BusClass.FCP_KEC_CC.equals(applicationM.getBusinessClassId())
						||OrigConstant.BusClass.FCP_KEC_CG.equals(applicationM.getBusinessClassId())) {

				applicationM.setAppDecision(OrigConstant.Action.PULL);
				applicationM.setOwner(userM.getUserName());
				applicationM.setApplicationStatus(null);
				
//				log.debug("[createAppBundling].. AppDecision " + applicationM.getAppDecision());
//				log.debug("[createAppBundling].. ApplicationStatus " + applicationM.getApplicationStatus());
				
				applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
//				log.debug("[createAppBundling]..Save PLApplication Success!");
				
				this.connWorkFlow = getWorkFlowConnection();
				BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
				WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM, applicationM.getPtID());
	
					WorkflowResponse response = bpmWorkflow.ComplateJob(workflowM, this.connWorkFlow);

					response.setClaimDate(applicationM.getClaimDate());
					
					PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
					
					// Update application status to model application
					if(response.getAppStatus() == null || "".equals(response.getAppStatus())){
						applicationM.setApplicationStatus(applicationDAO.loadApplicationStatus(applicationM.getAppRecordID()));
						response.setAppStatus(applicationM.getApplicationStatus());
					}else{
						applicationM.setApplicationStatus(response.getAppStatus());
					}
					
					if (ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())) {
						applicationDAO.updatePLApplicationStatus(response , userM);
						saveApplicationLOG(applicationM, response , userM);
						SetRoleDecision(applicationM, userM);
						
						applicationM.setJobState(response.getToAtid());
					}
	
			}
		
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void saveBlockCancelApplication(PLApplicationDataM applicationM, UserDetailM userM){
		try{
			log.debug("saveApplication()...AppRecordID >> "+applicationM.getAppRecordID());			
			savePLApplicationDataM(applicationM, userM);
			
			Vector<String> cencleVect = applicationM.getBlockAppRecId();
			WorkflowDataM workflowM = null;
			UserDetailM cUserM = null;
			
			PLApplicationDataM cApplicationM = null;
			Vector<PLApplicationLogDataM> cAppLogVect = null;
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			
			if(!OrigUtil.isEmptyVector(cencleVect)){
				BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();
				for(String appRecID : cencleVect){

					log.debug("saveBlockCancelApplication()...appRecID >> "+appRecID);
					
					workflowM = new WorkflowDataM();
					workflowM.setAppRecordID(appRecID);
					String lastOwner = bpmWf.GetLastBlockOwner(workflowM,getWorkFlowConnection());
					
					if(OrigUtil.isEmptyString(lastOwner)){
						lastOwner = userM.getUserName();
					}
					
					cApplicationM = applicationDAO.loadAppInFo(appRecID);
					
					cApplicationM.setAppRecordID(appRecID);
					cApplicationM.setBlockFlag(OrigConstant.FLAG_C);
					cApplicationM.setAppDecision(OrigConstant.Action.CANCEL);
					cApplicationM.setUpdateBy(userM.getUserName());
					cApplicationM.setApplicationStatus(null);
					cApplicationM.setOwner(lastOwner);
					
					cAppLogVect = new Vector<PLApplicationLogDataM>();
					PLApplicationLogDataM bappLogM = new PLApplicationLogDataM();
						bappLogM.setApplicationRecordID(appRecID);
						bappLogM.setUpdateBy(userM.getUserName());
						bappLogM.setCreateBy(userM.getUserName());
						cAppLogVect.add(bappLogM);
					
					cApplicationM.setApplicationLogVect(cAppLogVect);
					
					cUserM = new UserDetailM();
					cUserM.setUserName(lastOwner);				
					
					cApplicationM.setFinalAppDecision(OrigConstant.Action.CANCEL);
					cApplicationM.setFinalAppDecisionBy(cUserM.getUserName());
					cApplicationM.setFinalAppDecisionDate(new Timestamp(new java.util.Date().getTime()));
					
					cApplicationM.setAppInfo(OrigUtil.getApplicatonXML(cApplicationM));	
					
					doSaveCancleApplication(cApplicationM,userM);
					
				}
			}			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	private void doSaveCancleApplication(PLApplicationDataM applicationM,UserDetailM userM) throws Exception{
		PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
		
		BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();
		WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
		WorkflowResponse response = bpmWf.ClaimFlow(workflowM, this.getWorkFlowConnection());
		
		if(null == response || !ResultCodeConstant.RESULT_CODE_SUCCESS.equals(response.getResultCode())){
			log.debug("ERROR : Claim Application Not Complete !!");
			if(null != response){
				log.debug("MESSAGE : "+response.getResultDesc());
			}
			throw new EJBException("ERROR : Claim Application Not Complete !!");
		}
		
		applicationM.setPtID(response.getPtid());
		applicationM.setJobID(response.getJobId());
		applicationM.setPtType(response.getPtType());
		
		if(response.getClaimDate() != null){
			applicationM.setClaimDate(new Timestamp (response.getClaimDate().getTime()));
		}
		completeApplication(applicationM, userM);
		SetRoleDecision(applicationM, userM);
		
		updateBlockFlag(applicationM ,userM);
		
		if(!OrigUtil.isEmptyString(applicationM.getFinalAppDecision())){
			applicationDAO.updateFinalAppDecision(applicationM);
		}
	}
	
	@Override
	public void SaveRetrieveOldNcbData(PLApplicationDataM appM,UserDetailM userDetailM, String consentRefNo, String Result,String TrackingCode) {
		log.info("SaveRetrieveOldNcbData TrackingCode >> "+TrackingCode);
		try{	
			if(!OrigUtil.isEmptyVector(appM.getPersonalInfoVect())){
				PLOrigPersonalInfoDAO personalDAO = PLORIGDAOFactory.getPLOrigPersonalInfoDAO();
					personalDAO.SaveUpdateNcbORIGPersonalInfo(appM.getPersonalInfoVect(), appM.getAppRecordID());	
			}		
		}catch(Exception e){
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void CancelApplication(PLApplicationDataM applicationM, UserDetailM userM){		
		try{			
			this.connWorkFlow = getWorkFlowConnection();
			
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ForceChangeProcessState(workflowM, this.connWorkFlow);
			
			log.debug("getAccountID() >> "+applicationM.getAccountID());
			
			if(!OrigConstant.FLAG_Y.equals(applicationM.getICDCFlag())){
				PLOrigAccountDAO accountDAO = PLORIGDAOFactory.getPLOrigAccountDAO();
					accountDAO.setInActiveAccount(applicationM.getAccountID(), userM);				
				PLOrigAccountCardDAO accountCardDAO = PLORIGDAOFactory.getPLOrigAccountCardDAO();
					accountCardDAO.setInActiveAccountCard(applicationM.getAccountID(), userM);
			}
			
			applicationM.setApplicationStatus(response.getAppStatus());
			applicationM.setJobState(response.getToAtid());
			applicationM.setUpdateBy(userM.getUserName());
			applicationM.setUpdateDate(new Timestamp(new Date().getTime()));
			
			response.setAppRecordID(applicationM.getAppRecordID());	
				response.setUserName(userM.getUserName());
			
			//update appstatus
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updatePLApplicationStatus(response ,userM);
			
			saveApplicationLOG(applicationM, response, userM);
			
			//update final decision
			applicationDAO.updateFinalAppDecision(applicationM);
			//update role decision
			SetRoleDecision(applicationM, userM);
			
		}catch(Exception e){
			log.fatal("[ERROR].. ", e);
			throw new EJBException(e.getMessage());
		}
		
	}
	@Override
	public void createApplicationManual(PLApplicationDataM applicationM, UserDetailM userM) {
		// Create Application
		try{
			applicationM.setCreateBy(userM.getUserName());
			applicationM.setUpdateBy(userM.getUserName());

			String role = userM.getCurrentRole();

			log.debug("[createAppManual]..role " + role);

			PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			origDAO.updateSaveOrigApplication(applicationM, userM);

			// Create Account in case of Approve Override and Exception
//			log.debug("[createAppManual].. AppDecision " + applicationM.getAppDecision());
//			log.debug("[createAppManual].. ApplicationStatus " + applicationM.getApplicationStatus());

			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));

			applicationM.setOwner(userM.getUserName());

//			log.debug("[createAppManual].. get Owner ="+applicationM.getOwner());	
//			log.debug("[createAppManual].. Not Existing Workflow Logic ");	
			
			startBusinessProcess(applicationM, userM, applicationM.getAppDecision());

		}catch(Exception e){
			log.fatal("[createAppBundling].. Error ", e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void SaveApplication(PLApplicationDataM applicationM,UserDetailM userM) {
		try{
			applicationM.setCreateBy(userM.getUserName());
			applicationM.setUpdateBy(userM.getUserName());

			PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				origDAO.updateSaveOrigApplication(applicationM, userM);
			applicationM.setAppInfo(OrigUtil.getApplicatonXML(applicationM));
			
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
				bpmWorkflow.SetPriorityWorkQueue(workflowM, this.getWorkFlowConnection());
				bpmWorkflow.SetAppInfo(workflowM, this.getWorkFlowConnection());
		}catch(Exception e){
			log.fatal("[savePLApplicationDataM].. Error ", e);
			throw new EJBException(e.getMessage());
		}
	}
	
	
	 @Override
	 public boolean LogOrigLogon(LogonDataM logonM,String action,String description,Timestamp time){
        boolean result = false;
        try{
        	LogDAO logDAO = ORIGDAOFactory.getLogDao();
            	result = logDAO.logs(logonM ,action, description,time);
        }catch(Exception e){
            log.fatal("Exception ", e);
            throw new EJBException("Exception in Log Logon >> ", e);
        }
        return result;
	 }

	@Override
	public WorkflowResponse ClaimWorkQueueApplication(String appRecID,UserDetailM userM) {
		try{
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(appRecID, userM);
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			return bpmWorkflow.ClaimFlowWorkQueue(workflowM, this.getWorkFlowConnection());
		}catch(Exception e){
			log.fatal("Exception >> ",e);
			throw new EJBException("Exception ClaimWorkQueueApplication >> "+e.getMessage());
		}
	}

	private void ClaimCompleteForNCB(PLApplicationDataM applicationM, UserDetailM userM ,Connection conn) {
		try{
			WorkflowResponse workFlow = new WorkflowResponse();
			workFlow = ClaimApplication(applicationM,userM,conn);
			applicationM.setPtID(workFlow.getPtid());
			applicationM.setJobID(workFlow.getJobId());
			applicationM.setPtType(workFlow.getPtType());
			if (null != workFlow.getClaimDate()) {
				applicationM.setClaimDate(new Timestamp(workFlow.getClaimDate().getTime()));
			}			
			CompleteApplication(applicationM, userM,conn);			
		} catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void completeApplicationWithOutSaveLog(PLApplicationDataM appM, UserDetailM userM) {
		try{
			this.connWorkFlow = getWorkFlowConnection();
			
			log.debug("[completeApplication].. Action " + appM.getAppDecision() +" AppRecordID = "+appM.getAppRecordID()+" AppStatus = "+appM.getApplicationStatus()+" Process Type = "+appM.getPtType());
			
			WorkflowDataM workflowM = ORIGMapModelModule.mappingModelWorkFlow(appM, userM, appM.getPtID());
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			WorkflowResponse response = bpmWorkflow.ComplateJob(workflowM, this.connWorkFlow);
			
			response.setClaimDate(appM.getClaimDate());
			
			appM.setApplicationStatus(response.getAppStatus());
			appM.setJobState(response.getToAtid());
			
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updatePLApplicationStatus(response , userM);
			
			SetRoleDecision(appM, userM);
			
		}catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public PLApplicationDataM LoadNCBImageDataM(String appRecID){
		log.debug("LoadNCBImageDataM AppRecID >> "+appRecID);
		try{
			PLOrigApplicationDAO origDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			return origDAO.LoadNCBImageDataM(appRecID);
		}catch (Exception e) {
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}

	@Override
	public void AllocateApplication(PLApplicationDataM applicationM, UserDetailM userM) {
		WorkflowResponse response = null;
		WorkflowDataM workflowM = null;
		try{			
			workflowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM,userM,applicationM.getPtID());
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
			
			response = bpmWorkflow.ClaimFlow(workflowM,this.getWorkFlowConnection());
			
			if(null != response && ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){
				
				applicationM.setPtID(response.getPtid());
				applicationM.setJobID(response.getJobId());
				applicationM.setPtType(response.getPtType());
				applicationM.setClaimDate(new Timestamp(response.getClaimDate().getTime()));
				
				workflowM = ORIGMapModelModule.mappingModelWorkFlowReAssign(applicationM,userM);
				
				response = bpmWorkflow.ComplateJob(workflowM,this.getWorkFlowConnection());
				
				response.setClaimDate(applicationM.getClaimDate());
				
				applicationM.setApplicationStatus(response.getAppStatus());
				applicationM.setJobState(response.getToAtid());
				
				PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
					applicationDAO.updatePLApplicationStatus(response ,userM);
				
				PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
					applicationLogDAO.saveTableApplicationLog(ORIGMapModelModule.MapModelApplicationLOG(response, applicationM));
					
				this.LogicApplicationRole(applicationM, userM);
				
			}else{
				log.error("ERROR : Cannot Claim Application !! "+applicationM.getAppRecordID());
				throw new EJBException(ERROR.MSG_CANNOT_CLAIM);
			}
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}		
	}
	@Override
	public void saveHistoryDataM(HistoryDataM historyM) {
		try{
			LogDAO logDAO = ORIGDAOFactory.getLogDao();
				logDAO.saveHistoryDataM(historyM);
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public PLApplicationDataM getAppInfo(String appRecID){
		try{
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
			return applicationDAO.loadAppInFo(appRecID);
		}catch(Exception e){
			log.fatal("ERROR : "+e.getLocalizedMessage());
			throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public void ReAssignApplication(PLApplicationDataM applicationM,UserDetailM userM, String reAssignTo) {
		WorkflowResponse response = null;
		WorkflowDataM workflowM = null;
		try{	
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();								
			workflowM = ORIGMapModelModule.mappingModelWorkFlowReAssign(applicationM,userM);
				workflowM.setReassignTo(reAssignTo);
				workflowM.setReassignType(BpmProxyConstant.WorkflowReassignJobType.REASSIGN_JOB_TYPE_IQ);
							
			response = bpmWorkflow.ReassignJob(workflowM,this.getWorkFlowConnection());
			
			response.setClaimDate(applicationM.getClaimDate());
			
			applicationM.setApplicationStatus(response.getAppStatus());
			applicationM.setJobState(response.getToAtid());
			
			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updatePLApplicationStatus(response ,userM);
			
			PLOrigApplicationLogDAO applicationLogDAO = PLORIGDAOFactory.getPLOrigApplicationLogDAO();
				applicationLogDAO.saveTableApplicationLog(ORIGMapModelModule.MapModelApplicationLOG(response,applicationM));
				
			this.LogicApplicationRole(applicationM, userM);
			
		}catch(Exception e){
			log.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}	
	}

	@Override
	public Vector getPolicyRejectReasonVt(PLApplicationDataM applicationM, UserDetailM userM) {
		StringBuilder failPolicys = new StringBuilder("");
		Vector reasonVt = null;
		try{
			PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			if(personM != null){
				PLXRulesVerificationResultDataM xrulesVer = personM.getXrulesVerification();
				if(xrulesVer != null){
					Vector<PLXRulesPolicyRulesDataM> policyRulesVt = xrulesVer.getvXRulesPolicyRulesDataM();
					if(policyRulesVt != null && policyRulesVt.size() > 0){
						for(int i=0;i<policyRulesVt.size();i++){
							PLXRulesPolicyRulesDataM policyResultM = (PLXRulesPolicyRulesDataM)policyRulesVt.get(i);
							if(OrigConstant.ORIG_RESULT_FAIL.equals(policyResultM.getResultCode())){
								if(failPolicys.length() > 0){
									failPolicys.append(",'" + policyResultM.getPolicyRulesId()+"'");
								}else{
									failPolicys.append("'" + policyResultM.getPolicyRulesId()+"'");
								}
							}
						}
					}
				}
			}
			if(failPolicys.length() > 0){				
				Vector<RulesDetailsDataM> rulesDetailVt = PLORIGDAOFactory.getPLOrigRuleDAO().getRulesDetailsVt(failPolicys.toString());
				if(rulesDetailVt != null && rulesDetailVt.size() > 0){
					log.debug("@@@@@ failRuleDetail size:" + rulesDetailVt.size());
					reasonVt = new Vector();
					for(int i=0;i<rulesDetailVt.size();i++){
						PLReasonDataM reasonM = new PLReasonDataM();
						RulesDetailsDataM rulesDetailM = (RulesDetailsDataM)rulesDetailVt.get(i);
						reasonM.setApplicationRecordId(applicationM.getAppRecordID());
						reasonM.setRole(userM.getCurrentRole());
						reasonM.setReasonType(OrigConstant.fieldId.REJECT_REASON);
						reasonM.setReasonCode(rulesDetailM.getRejectReasonCode());
						reasonVt.add(reasonM);
					}
				}
			}
		}catch(Exception e){
			throw new EJBException(e.getMessage());
		}
		return reasonVt;
	}
	
	@Override
	public void saveFuApplication(PLApplicationDataM applicationM,UserDetailM userM) {
		try{
			applicationM.setCreateBy(userM.getUserName());
			applicationM.setUpdateBy(userM.getUserName());

			PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
				applicationDAO.updateSaveOrigApplication(applicationM, userM);
				
			log.debug("[saveFuApplication].. CancelClaim ");
			doUpdatePriority(applicationM, userM);
			cancelClaimApplication(applicationM, applicationM.getAppDecision(), userM);
							
			WorkflowDataM workflowM = new WorkflowDataM();
				workflowM.setAppRecordID(applicationM.getAppRecordID());
				workflowM.setUserID(userM.getUserName());
				workflowM.setAppStatus(applicationM.getApplicationStatus());
				
			BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();	
				bpmWorkflow.ChangeStatusWorkflow(workflowM, this.getWorkFlowConnection());
			
		}catch(Exception e){
			log.fatal("ERROR ",e);
			throw new EJBException(e.getMessage());
		}
	}
	
}
