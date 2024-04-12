package com.eaf.orig.ulo.service.ejb;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.eaf.orig.ulo.app.dao.CapportTransactionDAO;
import com.eaf.orig.ulo.app.dao.LoanSetupDAO;
import com.eaf.orig.ulo.app.dao.LoanSetupStampDutyDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationDAO;
import com.eaf.orig.ulo.app.dao.OrigApplicationImageDAO;
import com.eaf.orig.ulo.app.dao.OrigDocumentCheckListDAO;
import com.eaf.orig.ulo.app.dao.OrigDocumentRelationDAO;
import com.eaf.orig.ulo.app.dao.OrigPersonalInfoDAO;
import com.eaf.orig.ulo.app.dao.OrigReasonDAO;
import com.eaf.orig.ulo.app.dao.OrigReasonLogDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.CapportTransactionDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.LoanSetupDataM;
import com.eaf.orig.ulo.model.app.LoanSetupStampDutyDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM.PersonalType;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.service.ejb.view.ServiceCenterManager;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.module.model.FullFraudInfoDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.orig.bpm.workflow.model.BPMInstance;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;
import com.orig.bpm.workflow.service.model.BPMApplicationLog;

@Stateless
@Local(ServiceCenterManager.class)
@LocalBean
public class ServiceCenterManagerBean implements ServiceCenterManager {
	private static transient Logger logger = Logger.getLogger(ServiceCenterManagerBean.class);
	@Override
	public void saveApplication(ApplicationGroupDataM applicationGroup,String userId,boolean updateDM) throws EJBException{
		try{
			String applicationGroupId = applicationGroup.getApplicationGroupId();
			logger.debug("applicationGroupId : "+applicationGroupId);
			ORIGDAOFactory.getApplicationGroupDAO(userId).saveUpdateOrigApplicationGroupM(applicationGroup);
			int lifeCycle = applicationGroup.getMaxLifeCycle();
			if(applicationGroup.isClearCompareData()){
				ModuleFactory.getOrigComparisionDataDAO(userId)
				.deleteComparisonDataNotMatchUniqueId(applicationGroupId, CompareDataM.SoruceOfData.TWO_MAKER, null,lifeCycle);
			}
			logger.debug("updateDM : "+updateDM);
//			if(updateDM){
//				CallDmService.creatDMService(applicationGroupId,applicationGroup.getApplicationGroupNo(),applicationGroup.getWebScanUser());
//			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EJBException(e);
		}
	}
	@Override
	public void updateImageDocument(ApplicationGroupDataM applicationGroup,String userId,boolean updateDM) throws EJBException{
		try{
			String applicationGroupId = applicationGroup.getApplicationGroupId();
			logger.debug("applicationGroupId >> "+applicationGroupId);
			ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
			if(!Util.empty(applicationImages)){
				OrigApplicationImageDAO dao =  ORIGDAOFactory.getApplicationImageDAO(userId);
				dao.deleteNotInKeyApplicationImage(applicationImages, applicationGroupId);			
				for(ApplicationImageDataM applicationImage : applicationImages){
					dao.createOrigApplicationImageM(applicationImage);
				}
			}			
			ArrayList<DocumentCheckListDataM> documentCheckLists = applicationGroup.getDocumentCheckLists();
			OrigDocumentCheckListDAO daoDocCheckList = ORIGDAOFactory.getDocumentCheckListDAO(userId);
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO(userId);
			if(!Util.empty(documentCheckLists)){
				for(DocumentCheckListDataM documentCheckList : documentCheckLists){
					daoDocCheckList.createOrigDocumentCheckListM(documentCheckList);					
				}	
				docRelationDAO.deleteOrigDocumentRelation(documentCheckLists,applicationGroup.getApplicationGroupId());
				daoDocCheckList.deleteNotInKeyDocumentCheckList(documentCheckLists, applicationGroupId);
			}
			logger.debug("updateDM : "+updateDM);
//			if(updateDM){
//				CallDmService.creatDMService(applicationGroupId,applicationGroup.getApplicationGroupNo(),applicationGroup.getWebScanUser());
//			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new EJBException(e);
		}
	}
	@Override
	public void cancelApplication(String applicationGroupId,ArrayList<String> cancelUniqueIds,ApplicationReasonDataM applicationReason,String userId) throws EJBException{
		String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
		logger.debug("DECISION_FINAL_DECISION_CANCEL >> "+DECISION_FINAL_DECISION_CANCEL);
		try{
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
			}
		}catch(ApplicationException e){
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
	@Override
	public void savePersonalCisData(PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields,String applicationGroupId,int lifeCycle,String userId)throws EJBException{
		try{
			OrigPersonalInfoDAO personalInfoDAO = ORIGDAOFactory.getPersonalInfoDAO(userId);
			personalInfoDAO.saveUpdateCis(personalInfo, comparisonFields, applicationGroupId, CompareDataM.SoruceOfData.CIS, lifeCycle);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
	@Override
	public void savePersonalCisFailed(PersonalInfoDataM personalInfo,String applicationGroupId,int lifeCycle,String userId){
		try{
			OrigPersonalInfoDAO personalInfoDAO = ORIGDAOFactory.getPersonalInfoDAO(userId);
			personalInfoDAO.saveUpdateCisFailed(personalInfo,applicationGroupId,CompareDataM.SoruceOfData.CIS,lifeCycle);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
	
	@Override
	public void saveApplicationAndLoanSetup(ApplicationGroupDataM applicationGroup, String userId, boolean updateDM, FullFraudInfoDataM fullFraudInfo) throws EJBException{
		try {
			saveApplication(applicationGroup, userId, updateDM);
			
			// Get main applicant
			PersonalInfoDataM mainPersonalInfo = applicationGroup.getPersonalInfo(PersonalType.APPLICANT);
			if (null == mainPersonalInfo) {
				EJBException ejbException = new EJBException("Cannot find main applicant of applicationGroupNo: " + applicationGroup.getApplicationGroupNo());
				logger.error(ejbException);
				throw ejbException;
			}
		
			// Get work address
			String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
			String mailingAddressType = mainPersonalInfo.getMailingAddress();
			String mailingAddressId = null;
			if (ADDRESS_TYPE_WORK.equals(mailingAddressType)) {
				AddressDataM workAddress = mainPersonalInfo.getAddress(mailingAddressType);
				if (null == workAddress) {
					EJBException ejbException = new EJBException("Cannot find address of type " + mailingAddressType);
					logger.error(ejbException);
					throw ejbException;
				}
				mailingAddressId = workAddress.getAddressId();
			}
		
			// Get credit limit, stamp duty
			String kplProduct = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
			String[] approveStatus = new String[1];
			approveStatus[0] = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");
			ArrayList<ApplicationDataM> applicationSegment = applicationGroup.filterApplicationDecision(kplProduct, approveStatus);
		
			//TODO: Currently, we assume only 1 KPL can be approve
			BigDecimal creditLimit = null;
			BigDecimal stampDuty = null;
			String amtCapportName = null;
			String appCapportName = null;
			String applicationGroupId = null;
			String applicationRecordId = null;
			LoanDataM loan = null;
			logger.debug("Number of KPL with Approve status = " + applicationSegment.size());
			if (applicationSegment.size() > 0) {
				ApplicationDataM application = applicationSegment.get(0);
				loan = application.getLoan();
				if (null == loan) {
					EJBException ejbException = new EJBException("Loan is null ");
					logger.error(ejbException);
					throw ejbException;
				}
				creditLimit = loan.getLoanAmt();
				stampDuty = loan.getStampDuty();
				//amtCapportName = loan.getAmountCapportName();
				//appCapportName = loan.getApplicationCapportName();
				amtCapportName = loan.getAmountCapPortName();
				appCapportName = loan.getApplicationCapPortName();
				applicationGroupId = application.getApplicationGroupId();
				applicationRecordId = application.getApplicationRecordId();
			}
			else {
				EJBException ejbException = new EJBException("No KPL with approve status");
				logger.error(ejbException);
				throw ejbException;
			}

			// Prepare current date
			Date currentDate = new Date();
			Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			int dd = cal.get(Calendar.DAY_OF_MONTH);
			int mm = cal.get(Calendar.MONTH);
			int yy = cal.get(Calendar.YEAR);
			int periodNo = (dd <= 15) ? 1 : 2;
			int periodFrom = 1;
			int periodTo = 15;
			if (periodNo != 1) {
				periodFrom = 16;
				periodTo = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
		
			// Add claim record for stamp duty
			LoanSetupStampDutyDataM loanSetupStampDutyDataM = new LoanSetupStampDutyDataM();
			LoanSetupDataM loanSetupDataM = new LoanSetupDataM();
			loanSetupDataM.setApplicationGroupNo(applicationGroup.getApplicationGroupNo());
			loanSetupDataM.setCisNo(mainPersonalInfo.getCisNo());
			loanSetupDataM.setSubmitDate(currentTimestamp);
			loanSetupDataM.setThMidName(mainPersonalInfo.getThMidName());
			loanSetupDataM.setThName(mainPersonalInfo.getThName());
			loanSetupDataM.setThSurname(mainPersonalInfo.getThLastName());
			loanSetupDataM.setThTitle(mainPersonalInfo.getThTitleDesc());
			loanSetupDataM.setTypeOfStampDuty();
			loanSetupDataM.setLoanId((loan != null) ? loan.getLoanId() : null);
			loanSetupDataM.pendingSetup();
			logger.debug("loanSetupDataM for stamp duty = " + loanSetupDataM.toString());
			LoanSetupDAO loanSetupDAO = ORIGDAOFactory.getLoanSetupDAO(userId);
			loanSetupDAO.createLoanSetup(loanSetupDataM);
			loanSetupStampDutyDataM.setClaimId(loanSetupDataM.getClaimId());
			if ( null != mailingAddressId && !ApplicationUtil.eApp( applicationGroup.getSource() ) ) {
				// Add claim record for mailing address
				loanSetupDataM.setAddressId(mailingAddressId);
				loanSetupDataM.setTypeOfMailingAddress();
				loanSetupDataM.pendingSetup();
				logger.debug("loanSetupDataM for mailing address = " + loanSetupDataM.toString());
				loanSetupDAO.createLoanSetup(loanSetupDataM);
			}

			// Add stamp duty record
			String requesterName = ServiceCache.getGeneralParam("LOAN_SETUP_STAMP_DUTY_REQ_NAME");
			String requesterPosition = ServiceCache.getGeneralParam("LOAN_SETUP_STAMP_DUTY_REQ_POS");
			loanSetupStampDutyDataM.setPeriodNo(BigDecimal.valueOf(periodNo));
			loanSetupStampDutyDataM.setPeriodDateFrom(BigDecimal.valueOf(periodFrom));
			loanSetupStampDutyDataM.setPeriodDateTo(BigDecimal.valueOf(periodTo));
			loanSetupStampDutyDataM.setPeriodMonth(BigDecimal.valueOf(mm));
			loanSetupStampDutyDataM.setPeriodYear(BigDecimal.valueOf(yy));
			loanSetupStampDutyDataM.setStampDutyFee(stampDuty);
			loanSetupStampDutyDataM.setFinalLoanAmt(creditLimit);
			loanSetupStampDutyDataM.setRequesterName(requesterName);
			loanSetupStampDutyDataM.setRequesterPosition(requesterPosition);
			loanSetupStampDutyDataM.setLoanId((loan != null) ? loan.getLoanId() : null );
			logger.debug("loanSetupStampDutyDataM = " + loanSetupStampDutyDataM.toString());
			LoanSetupStampDutyDAO loanSetupStampDutyDAO = ORIGDAOFactory.getLoanSetupStampDutyDAO(userId);;
			loanSetupStampDutyDAO.createLoanSetupStampDuty(loanSetupStampDutyDataM);
		
			// Add Capport transaction
			CapportTransactionDAO capportTranDAO = ORIGDAOFactory.getCapportTransactionDAO(userId);
			CapportTransactionDataM capportTranDataM;
			if (null != amtCapportName) {
				capportTranDataM = new CapportTransactionDataM();
				capportTranDataM.setApplicationGroupId(applicationGroupId);
				capportTranDataM.setApplicationRecordId(applicationRecordId);
				capportTranDataM.setAmtGrantType();
				capportTranDataM.setApproveAmt(creditLimit);
				capportTranDataM.setAdjustFlag("N");
				List<String> capportNames = Arrays.asList(amtCapportName.split(","));
				for (String capportName:capportNames) {
					capportName = capportName.trim();
					capportTranDataM.setCapportName(capportName);
					logger.debug("capportTranDataM for amount cap = " + capportTranDataM);
					capportTranDAO.createCapportTransaction(capportTranDataM);
				}
			}
			if (null != appCapportName) {
				capportTranDataM = new CapportTransactionDataM();
				capportTranDataM.setApplicationGroupId(applicationGroupId);
				capportTranDataM.setApplicationRecordId(applicationRecordId);
				capportTranDataM.setAppGrantType();
				capportTranDataM.setApproveApp(BigDecimal.ONE);
				capportTranDataM.setAdjustFlag("N");
				List<String> capportNames = Arrays.asList(appCapportName.split(","));
				for (String capportName:capportNames) {
					capportName = capportName.trim();
					capportTranDataM.setCapportName(capportName);
					logger.debug("capportTranDataM for application cap = " + capportTranDataM);
					capportTranDAO.createCapportTransaction(capportTranDataM);
				}
			}
		}
		catch (Exception e) {
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());			
		}
	}
	
	@Override
	public void reprocessApplication(ApplicationGroupDataM applicationGroup, String username, String userRole, String logMessage) {
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		BPMInstance bpmInstance = null;
		try {
			String userId = username;
			ORIGDAOFactory.getApplicationGroupDAO(userId).saveUpdateOrigApplicationGroupM(applicationGroup);
			ModuleFactory.getOrigComparisionDataDAO(userId).deleteOrigComparisionDataMatchSrc(applicationGroup.getApplicationGroupId(),CompareDataM.SoruceOfData.TWO_MAKER,applicationGroup.getLifeCycle());
			BPMMainFlowProxy bpmMainFlowProxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			if(!SystemConfig.containsGeneralParam("WIP_JOBSTATE_END",applicationGroup.getJobState())) {
				bpmInstance = bpmMainFlowProxy.terminateProcess(applicationGroup.getInstantId());
				if(BpmProxyConstant.RestAPIResult.ERROR.equalsIgnoreCase(bpmInstance.getResultCode())) {
					throw new BPMClientException(bpmInstance.getResultDesc());
				}
			}
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(username);
			WorkflowResponseDataM createworkflowResponse = workflowManager.createWorkflowTask(workflowRequest);
			if(BpmProxyConstant.RestAPIResult.ERROR.equals(createworkflowResponse.getResultCode())) {
				throw new Exception(createworkflowResponse.getResultDesc());
			}
			ModuleFactory.getInfBatchLogDAO().blockInfBatchLogReprocess(applicationGroup.getApplicationGroupId(),applicationGroup.getLifeCycle(),logMessage);
			//ORIGDAOFactory.getReprocessLogDAO().createOrigReprocessLog(reprocessLog);

			BPMApplicationLog appLog = new BPMApplicationLog();
			appLog.setAction(BpmProxyConstant.WorkflowAction.REPROCESS);
			appLog.setAppGroupId(applicationGroup.getApplicationGroupId());
			appLog.setAppStatus(applicationGroup.getApplicationStatus());
			appLog.setJobState(applicationGroup.getJobState());
			appLog.setUsername(username);
			appLog.setToRole(userRole);
			appLog.setSubmitByRole(userRole);
			appLog.setSpecialCondition("REPROCESS");
			WorkflowResponseDataM workflowResponse = workflowManager.addAppHistoryLog(appLog);
			logger.debug("workflowResponse.getResultCode >> "+workflowResponse.getResultCode());
			logger.debug("workflowResponse.getResultDesc >> "+workflowResponse.getResultDesc());
			if(BpmProxyConstant.RestAPIResult.ERROR.equals(workflowResponse.getResultCode())) {
				throw new Exception(workflowResponse.getResultDesc());
			}
		} catch(Exception e) {
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
	}
	
}