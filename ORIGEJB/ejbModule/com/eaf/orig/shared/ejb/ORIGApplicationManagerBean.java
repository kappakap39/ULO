package com.eaf.orig.shared.ejb;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ejb.EJBException;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.ava.bpm.util.ResultCodeConstant;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.ias.shared.model.UserM;
import com.eaf.img.wf.service.ejb.IMGWFServiceManager;
import com.eaf.orig.logon.dao.OrigLogOnDAO;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.scheduler.dao.Scheduler.SchedulerDAO;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.OrigApplicationLogMDAO;
import com.eaf.orig.shared.dao.OrigApplicationMDAO;
import com.eaf.orig.shared.dao.OrigLoanMDAO;
import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
import com.eaf.orig.shared.dao.OrigMasterUserDetailDAO;
import com.eaf.orig.shared.dao.OrigSmsLogDAO;
import com.eaf.orig.shared.dao.OrigVehicleMDAO;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ApplicationLogDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.SMSDataM;
import com.eaf.orig.shared.model.ServiceDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.xrules.dao.XRulesVerificationResultDAO;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.ejb.view.GeneratorManager;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.pl.app.dao.UpdateApplicationStatusDAO;
import com.eaf.orig.ulo.pl.app.utility.SendEmail;
import com.eaf.orig.ulo.pl.app.utility.SendSMS;
import com.eaf.orig.wf.shared.ORIGWFConstant;
import com.eaf.orig.wf.shared.model.ApplicationMsgM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.proxy.BpmProxyModule;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.proxy.BpmWorkflow;
import com.orig.bpm.workflow.model.BPMApplication;
import com.orig.bpm.workflow.model.BPMInstance;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;

/**
 * Bean implementation class for Enterprise Bean: ORIGApplicationManager
 */
//@Stateless
public class ORIGApplicationManagerBean implements ORIGApplicationManager {
	
	@EJB ORIGGeneratorManager generatorManager;
	
    Logger logger = Logger.getLogger(ORIGApplicationManagerBean.class);
    private Connection connWorkFlow = null;
    private javax.ejb.SessionContext mySessionCtx;

    /**
     * getSessionContext
     */
    public javax.ejb.SessionContext getSessionContext() {
        return mySessionCtx;
    }

    /**
     * setSessionContext
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) {
        mySessionCtx = ctx;
    }

    /**
     * ejbCreate
     */
    public void ejbCreate() throws javax.ejb.CreateException {
    }

    /**
     * ejbActivate
     */
    public void ejbActivate() {
    }

    /**
     * ejbPassivate
     */
    public void ejbPassivate() {
    }

    /**
     * ejbRemove
     */
    public void ejbRemove() {
    }

    
    public boolean deSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndiWF, String providerUrlEXT,
            String jndiEXT, String providerUrlIMG, String jndiIMG) {
//        String result = "";
//        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
            String applicationNo = applicationDataM.getApplicationNo();
            String requestID = applicationDataM.getRequestID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            logger.debug("requestID " + requestID);
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            /*if (guarantorIDVect == null) {
                return false;
            }
            if (applicationNo == null || ("").equals(applicationNo)) {
                //Set Office CDE
                String officeCode = userM.getDefaultOfficeCode();
                if (officeCode == null) {
                    officeCode = "";
                }
                Vector persoanlInfoVect = applicationDataM.getPersonalInfoVect();
                for (int j = 0; j < persoanlInfoVect.size(); j++) {
                    PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) persoanlInfoVect.get(j);

                    personalInfoDataM.setOfficeCode(officeCode);
                }
            }*/
            //ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
            //applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF
            if (applicationNo == null || ("").equals(applicationNo)) {
                if (requestID != null && !("").equals(requestID)) {
                    completeIMGApplication(applicationDataM, userM, "", "", "", providerUrlIMG, jndiIMG);
                    //Update appRecordID and status
                    applicationMDAO.updateApprecordForIMG(applicationDataM.getAppRecordID(), requestID);
                    applicationMDAO.updateStatusForIMG(requestID);
                }
                 startBusinessProcess(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.CREATE, "", "", providerUrlWF, jndiWF);
            } else {
                if (ORIGWFConstant.JobState.DE_INITIAL_STATE.equals(applicationDataM.getJobState())
                        || ORIGWFConstant.JobState.DE_REWORK_STATE.equals(applicationDataM.getJobState())
                        || ORIGWFConstant.JobState.DE_PENDING_STATE.equals(applicationDataM.getJobState())) {
                    completeApplication(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.SAVE_DRAFT, "", "", providerUrlWF, jndiWF);
                } else {
                    cancelClaimApplication(applicationDataM, providerUrlWF, jndiWF);
                } 
            }
            //result =
            // applicationMDAO.selectApplicationNo(applicationDataM.getAppRecordID());
        } catch (Exception e) {
            //result = false;
//            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return true;
    }

    public boolean deSubmitApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String customerType,
            String appType, String providerUrlEXT, String jndiEXT, String providerUrlIMG, String jndiIMG, ServiceDataM serviceDataM) {
//        String result = "";
//        String message = "";
        try {
        	logger.debug("deSubmitApplication >>> ");
//            String role = (String) userM.getRoles().elementAt(0);
            String appRecordID = applicationDataM.getAppRecordID();
            String applicationNo = applicationDataM.getApplicationNo();
            String requestID = applicationDataM.getRequestID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            logger.debug("requestID " + requestID);
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            
//            Comment By Rawi Songchaisin What Guarantor...
//            if (guarantorIDVect == null) {
//                return false;
//            }
            
            if (applicationNo == null || ("").equals(applicationNo)) {
                //Set Office CDE
                String officeCode = userM.getDefaultOfficeCode();
                if (officeCode == null) {
                    officeCode = "";
                }
                Vector persoanlInfoVect = applicationDataM.getPersonalInfoVect();
                for (int j = 0; j < persoanlInfoVect.size(); j++) {
                    PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) persoanlInfoVect.get(j);

                    personalInfoDataM.setOfficeCode(officeCode);
                }
            }
//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            String decision = ORIGWFConstant.ApplicationDecision.SUBMIT;
            logger.debug("applicationDataM.getDeDecision() = " + applicationDataM.getDeDecision());
            if (ORIGWFConstant.ApplicationDecision.DE_PENDING.equals(applicationDataM.getDeDecision())) {
                decision = ORIGWFConstant.ApplicationDecision.DE_PENDING;
            }
            if (ORIGWFConstant.ApplicationDecision.SUBMIT.equals(decision)) {
                //Calculate Approval group
                EjbUtil ejbUtil = new EjbUtil();
                VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();

                if(applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
                    LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
                    if (loanDataM.getCostOfCarPrice()!=null && loanDataM.getCostOfDownPayment()!=null){
	                    BigDecimal downPercent = ejbUtil.calculatePercent(loanDataM.getCostOfCarPrice(), loanDataM.getCostOfDownPayment());
	                    BigDecimal installment;
	                    if ("Y".equals(loanDataM.getBalloonFlag())) {
	                        installment = loanDataM.getBalloonInstallmentAmt();
	                    } else {
	                        installment = loanDataM.getInstallment1();
	                    }
	                    ejbUtil.calculateApprovalGroup(userM, appRecordID, vehicleDataM.getCar(), customerType, loanDataM.getCostOfFinancialAmt(), appType,
	                            installment, downPercent);
                    }
                }
                
                logger.debug("decision = " + decision);
                if (applicationDataM.getIsException() && !ORIGWFConstant.ApplicationDecision.DE_PENDING.equals(decision)) {
                    decision = ORIGWFConstant.ApplicationDecision.SUBMIT_WITH_EXCEPTION;
                }
                //	        		
                //	        		//Send SMS or Email
                //	    	        String sendEmailResult = null;
                //	    	        UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
                //	    	        Vector cmrUserVect =
                // utilityDAO.getCMRUser(loanDataM.getMarketingCode());
                //	    			if(cmrUserVect.size() > 0){
                //	    				UserDetailM userDetailM;
                //	    				for(int i=0; i<cmrUserVect.size(); i++){
                //	    					userDetailM = (UserDetailM) cmrUserVect.get(i);
                //	    			        if(OrigConstant.CHANNEL_EMAIL.equals(userDetailM.getCommunicate_channel())){//set
                // send email
                //	    						EmailM emailM = new EmailM();
                //	    						SendEmail sendEmail = new SendEmail();
                //	    						OrigMasterGenParamDAO genParamDAO =
                // OrigMasterDAOFactory.getOrigMasterGenParamDAO();
                //	    						GeneralParamM generalParamM =
                // genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_SUBJECT_XCMR,
                // OrigConstant.BUSINESS_CLASS_AL);
                //	    						
                //	    						emailM.setTo(userDetailM.getEmail());
                //	    						emailM.setContent(sendEmail.getMessageForXCMR(applicationDataM));
                //	    						emailM.setSubject(generalParamM.getParamValue());
                //	    						sendEmail.setEmail(emailM);
                //	    						sendEmailResult = sendEmail.sendEmail(serviceDataM);
                //	    						logger.debug("sendEmailResult = "+sendEmailResult);
                //	    					}else{
                //	    						SendSMS sendSMS = new SendSMS();
                //	    						SMSDataM dataM = sendSMS.getSMSData(applicationDataM,
                // userDetailM.getMobilePhone(), serviceDataM, role);
                //	    						String line = sendSMS.sendSMS(dataM, serviceDataM);
                //	    						logger.debug("line = "+line);
                //	    					}
                //	    				}
                //	    			}
                //	    			
                //	        	}
            }
            logger.debug("decision = " + decision);
            //Call WF for complete application
            if (applicationNo == null || ("").equals(applicationNo)) {
                if (requestID != null && !("").equals(requestID)) {
                    completeIMGApplication(applicationDataM, userM, "", "", "", providerUrlIMG, jndiIMG);
                    //Update appRecordID
                    applicationMDAO.updateApprecordForIMG(applicationDataM.getAppRecordID(), requestID);
                    applicationMDAO.updateStatusForIMG(requestID);
                }
                logger.debug("startBusinessProcess >>> ");
                startBusinessProcess(applicationDataM, userM, decision, "", "", providerUrlWF, jndi);
            } else {
            	logger.debug("completeApplication >>> ");
                completeApplication(applicationDataM, userM, decision, "", "", providerUrlWF, jndi);
            }
            //result =
            // applicationMDAO.selectApplicationNo(applicationDataM.getAppRecordID());
        } catch (Exception e) {
            //result = false;
//            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return true;
    }

    public ApplicationDataM loadApplicationDataM(ApplicationDataM applicationDataM, String appRecordID, String providerUrlEXT, String jndiEXT) {
        try {
            if (applicationDataM == null) {
                applicationDataM = new ApplicationDataM();
            }
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            applicationDataM = applicationMDAO.loadModelOrigApplicationM(appRecordID, 0, providerUrlEXT, jndiEXT);
        } catch (Exception e) {
            logger.error("Load Application error", e);
            throw new EJBException("Exception in loadApplicationDataM >> ", e);
        }
        return applicationDataM;
    }

    public ApplicationDataM loadApplicationDataMForNCBFirst(ApplicationDataM applicationDataM, String appRecordID, String providerUrlEXT, String jndiEXT) {
        try {
            if (applicationDataM == null) {
                applicationDataM = new ApplicationDataM();
            }
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            applicationDataM = applicationMDAO.loadModelOrigApplicationM(appRecordID, 0, providerUrlEXT, jndiEXT);
        } catch (Exception e) {
            logger.error("Load Application error", e);
            throw new EJBException("Exception in loadApplicationDataM >> ", e);
        }
        return applicationDataM;
    }

    public ApplicationDataM loadApplicationDataMForDrawDown(ApplicationDataM applicationDataM, String appRecordID) {
        try {
            if (applicationDataM == null) {
                applicationDataM = new ApplicationDataM();
            }
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            applicationDataM = applicationMDAO.loadModelOrigApplicationMForDrawDown(appRecordID);
        } catch (Exception e) {
            logger.error("Load Application error", e);
            throw new EJBException("Exception in loadApplicationDataM >> ", e);
        }
        return applicationDataM;
    }

    public void completeApplication(ApplicationDataM applicationM, UserDetailM userM, String decision, String message, String errrorMsg, String providerUrlWF,
            String jndi) throws Exception {
        logger.debug("Call Complete Application ");
        //Remove WPS
//        if (applicationM.getAiid() == null || "".equalsIgnoreCase(applicationM.getAiid())) {
//            logger.debug("Aiid->" + applicationM.getAiid() + "  call cliam complete applciation");
//            claimCompleteApplication(applicationM.getAppRecordID(), applicationM.getApplicationStatus(), applicationM.getJobState(), new Timestamp(new Date()
//                    .getTime()), userM, decision, providerUrlWF, jndi);
//        } else {
//            ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndi);
//            ApplicationMsgM appMsgM = new ApplicationMsgM();
//            logger.debug("decision->" + decision);
//            logger.debug("AppRecordID->" + applicationM.getAppRecordID());
//            logger.debug("AppStatus->" + applicationM.getApplicationStatus());
//            logger.debug("JobState->" + applicationM.getJobState());
//            logger.debug("message->" + message);
//            logger.debug("Aiid->" + applicationM.getAiid());
//            logger.debug("userM.getUserName()->" + userM.getUserName());
//
//            appMsgM.setApplicationDecision(decision);
//            appMsgM.setApplicationRecordID(applicationM.getAppRecordID());
//            appMsgM.setApplicationStatus(applicationM.getApplicationStatus());
//            appMsgM.setJobState(applicationM.getJobState());
//            appMsgM.setMessage(message);
//            appMsgM.setErrorMessage(errrorMsg);
//            appMsgM.setUserName(userM.getUserName());
//            appMsgM.setUpdateDate(applicationM.getClaimDate());
//            //call business process
//            OrigManager.completeActivity(applicationM.getAiid(), appMsgM);
//        }
        //OrigManager.cancelClaimApplication(applicationM.getAiid());
        
        /**
         * Call ComplateJob BpmProxy 20110920 
         * */
        	
        	 	this.connWorkFlow = getWorkFlowConnection();    	
          	 	String role = (String) userM.getRoles().elementAt(0);
        	 	
        	 	logger.debug("...[ completeApplication ].. Connection >>> "+this.connWorkFlow);
        	 	logger.debug("...[ completeApplication ].. decision->" + decision);
        	 	logger.debug("...[ completeApplication ].. AppRecordID->" + applicationM.getAppRecordID());
        	    logger.debug("...[ completeApplication ].. AppStatus->" + applicationM.getApplicationStatus());
        	 	logger.debug("...[ completeApplication ].. role->" + role);
        	 	
        	 	WorkflowResponse response = BpmProxyService.getBpmProxyModule().ComplateJob(applicationM, userM.getUserName(),role, decision ,applicationM.getOwner(), applicationM.getPtID(),this.connWorkFlow);
        	 	
        	 	logger.debug("...[ completeApplication ].. response code ->" + response.getResultCode());
        	 	logger.debug("...[ completeApplication ].. response desc ->" + response.getResultDesc());
        	 	
        	 	if(ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())
        	 							&& BpmProxyConstant.WorkflowProcessType.PROCESS_TYPE_MAINPROCESS.equalsIgnoreCase(applicationM.getPtType())){
	        	 	UpdateApplicationStatusDAO updateApplicationDAO = ORIGDAOFactory.getUpdateApplicationStatusDAO();
	        	 	updateApplicationDAO.updateAppStatus(response);
        	 	}
    }

    public void completeIMGApplication(ApplicationDataM applicationM, UserDetailM userM, String decision, String message, String errrorMsg,
            String providerUrlWF, String jndi) throws Exception {
        IMGWFServiceManager IMGManager = ORIGEJBService.getImageWFServiceManagerHome(providerUrlWF, jndi);
        ApplicationMsgM appMsgM = new ApplicationMsgM();
        logger.debug("decision->" + decision);
        logger.debug("AppRecordID->" + applicationM.getAppRecordID());
        logger.debug("AppStatus->" + applicationM.getApplicationStatus());
        logger.debug("JobState->" + applicationM.getJobState());
        logger.debug("message->" + message);
        logger.debug("Aiid->" + applicationM.getAiid());
        logger.debug("userM.getUserName()->" + userM.getUserName());

        appMsgM.setApplicationDecision(decision);
        appMsgM.setApplicationRecordID(applicationM.getAppRecordID());
        appMsgM.setApplicationStatus(applicationM.getApplicationStatus());
        appMsgM.setJobState(applicationM.getJobState());
        appMsgM.setMessage(message);
        appMsgM.setErrorMessage(errrorMsg);
        appMsgM.setUserName(userM.getUserName());
        appMsgM.setUpdateDate(applicationM.getClaimDate());

        //call business process
        IMGManager.completeActivity(applicationM.getAiid(), appMsgM);
    }

    public void cancelClaimApplication(ApplicationDataM applicationM, String providerUrlWF, String jndi) throws Exception {
    	//Remove WPS
//        ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndi);
//        logger.debug("Aiid->" + applicationM.getAiid());
//
//        //call business process
//        if (applicationM.getAiid() != null && !"".equals(applicationM.getAiid())) {
//            OrigManager.cancelClaimApplication(applicationM.getAiid());
//            //Update application log
//            OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
//            logMDAO.createModelOrigApplicationLogM(getApplicationLogDataMForCancelClaim(applicationM));
//        }
    	 /**
         * Call CancleClaimFlow BpmProxy 20110920 
         * */
        	
        	 	this.connWorkFlow = getWorkFlowConnection();    	

        	 	logger.debug("...[ cancelClaimApplication ].. Connection >>> "+this.connWorkFlow);
        	 	
        	 	WorkflowResponse response = BpmProxyService.getBpmProxyModule().CancleClaimFlow(applicationM, "", "",this.connWorkFlow);
        	 	
        	 	logger.debug("...[ cancelClaimApplication ].. response code ->" + response.getResultCode());
        	 	logger.debug("...[ cancelClaimApplication ].. response desc ->" + response.getResultDesc());
        	 	
        	 	 if(ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())
							&& BpmProxyConstant.WorkflowProcessType.PROCESS_TYPE_MAINPROCESS.equalsIgnoreCase(applicationM.getPtType())){	
			          OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
			          logMDAO.createModelOrigApplicationLogM(getApplicationLogDataMForCancelClaim(applicationM));
        	 	 }
    }
    public WorkflowResponse cancleclaim(ApplicationDataM applicationM, String providerUrlWF, String jndi){
    	try {
		    	this.connWorkFlow = getWorkFlowConnection();    	
		
			 	logger.debug("...[ cancleclaim ].. Connection >>> "+this.connWorkFlow);
			 	
			 	WorkflowResponse response = BpmProxyService.getBpmProxyModule().CancleClaimFlow(applicationM, "", "",this.connWorkFlow);
			 	
			 	logger.debug("...[ cancleclaim ].. response code ->" + response.getResultCode());
			 	logger.debug("...[ cancleclaim ].. response desc ->" + response.getResultDesc());
			 	return response;
			 	
    	}catch (Exception e) {
			throw new EJBException(e.getMessage());
		}
    }
    public void claimCompleteApplication(String appRecordID, String appStatus, String jobState, Timestamp claimDate, UserDetailM userM, String decision,
            String providerUrlWF, String jndi) throws EJBException,Exception {
    	//Remove WPS
//        ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndi);
//        ApplicationMsgM appMsgM = new ApplicationMsgM();
//        logger.debug("decision->" + decision);
//        logger.debug("AppRecordID->" + appRecordID);
//        logger.debug("AppStatus->" + appStatus);
//        logger.debug("JobState->" + jobState);
//
//        appMsgM.setApplicationDecision(decision);
//        appMsgM.setApplicationRecordID(appRecordID);
//        appMsgM.setApplicationStatus(appStatus);
//        appMsgM.setJobState(jobState);
//        appMsgM.setUserName(userM.getUserName());
//        appMsgM.setUpdateDate(claimDate);
//
//        //call business process
//        OrigManager.claimCompleteActivity(appMsgM);
    	
        /**
         * Call ClaimFlow BpmProxy 20110920 
         * */
			 	ApplicationDataM applicationM = new ApplicationDataM();
			 	applicationM.setAppRecordID(appRecordID);
		    			
	        	 	this.connWorkFlow = getWorkFlowConnection();    	

	        	 	String role = (String) userM.getRoles().elementAt(0);
	        	 	
	        	 	logger.debug("...[ claimCompleteApplication ].. Connection >>> "+this.connWorkFlow);
	        	 	logger.debug("...[ claimCompleteApplication ].. decision->" + decision);
	        	 	logger.debug("...[ claimCompleteApplication ].. AppRecordID->" + applicationM.getAppRecordID());	  
	        	 	logger.debug("...[ claimCompleteApplication ].. role->" + role);
	        	 	
	        	 	WorkflowResponse response = BpmProxyService.getBpmProxyModule().ComplateJob(applicationM, userM.getUserName(),role, decision,applicationM.getOwner(), applicationM.getPtID(),this.connWorkFlow);
	        	 	
	        	 	logger.debug("...[ claimCompleteApplication ].. response code ->" + response.getResultCode());
	        	 	logger.debug("...[ claimCompleteApplication ].. response desc ->" + response.getResultDesc());
	        	 if(ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())
									&& BpmProxyConstant.WorkflowProcessType.PROCESS_TYPE_MAINPROCESS.equalsIgnoreCase(applicationM.getPtType())){	
	        	 	UpdateApplicationStatusDAO updateApplicationDAO = ORIGDAOFactory.getUpdateApplicationStatusDAO();
	        	 	updateApplicationDAO.updateAppStatus(response);
	        	 }

    }
    
    public WorkflowResponse claimApplication(String appRecordID, String appStatus, String jobState, UserDetailM userM, String decision,
            String providerUrlWF, String jndi){   
    	
    	try{
        /**
         * Call ClaimFlow BpmProxy 20110920 
         * */
			 	ApplicationDataM appM = new ApplicationDataM();
		 		appM.setAppRecordID(appRecordID);
		    			
        	 	this.connWorkFlow = getWorkFlowConnection();    	
        	 	
        	 	String role = (String) userM.getRoles().elementAt(0);
        	 	
        	 	logger.debug("...[ claimApplication ].. Connection >>> "+this.connWorkFlow);        	 
        	 	logger.debug("...[ claimApplication ].. role->" + role);
        	 	
        	 	WorkflowResponse response = BpmProxyService.getBpmProxyModule().ClaimFlow(appM, role, userM.getUserName(), "",this.connWorkFlow);
        	 	
        	 	logger.debug("...[ claimApplication ].. response code ->" + response.getResultCode());
        	 	logger.debug("...[ claimApplication ].. response desc ->" + response.getResultDesc());
        	 	return response;
        	 	
    	}catch (Exception e) {
			throw new EJBException(e.getMessage());
		}	
    }
    private ApplicationLogDataM getApplicationLogDataMForCancelClaim(ApplicationDataM applicationDataM) {
        ApplicationLogDataM applicationLogDataM = new ApplicationLogDataM();
        applicationLogDataM.setAction(ORIGWFConstant.ApplicationDecision.SAVE_DRAFT);
        applicationLogDataM.setApplicationRecordID(applicationDataM.getAppRecordID());
        applicationLogDataM.setApplicationStatus(applicationDataM.getApplicationStatus());
        applicationLogDataM.setJobState(applicationDataM.getJobState());
        applicationLogDataM.setClaimDate(applicationDataM.getClaimDate());
        logger.debug("applicationDataM.getUpdateBy() = " + applicationDataM.getUpdateBy());
        applicationLogDataM.setUpdateBy(applicationDataM.getUpdateBy());
        return applicationLogDataM;
    }

    public void startBusinessProcess(ApplicationDataM applicationM, UserDetailM userM, String decision, String message, String errrorMsg, String providerUrlWF,
            String jndi) throws Exception {
    	//Remove WPS
//        ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndi);
//        ApplicationMsgM appMsgM = new ApplicationMsgM();
//        logger.debug("decision->" + decision);
//        logger.debug("AppRecordID->" + applicationM.getAppRecordID());
//        logger.debug("AppStatus->" + applicationM.getApplicationStatus());
//        logger.debug("JobState->" + applicationM.getJobState());
//        logger.debug("message->" + message);
//        logger.debug("Aiid->" + applicationM.getAiid());
//
//        appMsgM.setApplicationDecision(decision);
//        appMsgM.setApplicationRecordID(applicationM.getAppRecordID());
//        appMsgM.setApplicationStatus(applicationM.getApplicationStatus());
//        appMsgM.setJobState(applicationM.getJobState());
//        appMsgM.setMessage(message);
//        appMsgM.setErrorMessage(errrorMsg);
//        appMsgM.setUserName(userM.getUserName());
//        appMsgM.setUpdateDate(applicationM.getClaimDate());
//
//        //call business process
//        OrigManager.startBusinessProcess(appMsgM);
    /**
     * Call StartFlow BpmProxy 20110920 
     * */
    		this.connWorkFlow = getWorkFlowConnection();    		
   		
    	 	String role = (String) userM.getRoles().elementAt(0);
    	 	
    	 	logger.debug("...[ startBusinessProcess ].. Connection >>> "+this.connWorkFlow);
    	 	logger.debug("...[ startBusinessProcess ].. decision->" + decision);
    	 	logger.debug("...[ startBusinessProcess ].. AppRecordID->" + applicationM.getAppRecordID());
    	    logger.debug("...[ startBusinessProcess ].. AppStatus->" + applicationM.getApplicationStatus());
    	 	logger.debug("...[ startBusinessProcess ].. role->" + role);    	 		
  
    	 			
	    	WorkflowResponse response = BpmProxyService.getBpmProxyModule().StartFlow(applicationM, userM.getUserName(),role, decision, applicationM.getOwner(), BpmProxyConstant.WorkflowProcessTemplate.WF_PROCESS_TEMPLATE_ID_ULO0001,this.connWorkFlow);
	    	 	
	    	logger.debug("...[ startBusinessProcess ].. response code ->" + response.getResultCode());
	    	logger.debug("...[ startBusinessProcess ].. response desc ->" + response.getResultDesc());
	    	 	
	    	 	if(ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){
		    	 	UpdateApplicationStatusDAO updateApplicationDAO = ORIGDAOFactory.getUpdateApplicationStatusDAO();
		    	 	updateApplicationDAO.updateAppStatus(response);
	    	 	}

    }
    
    public void startBusinessParallelProcess(ApplicationDataM applicationM, UserDetailM userM, String decision ) throws Exception {

    		this.connWorkFlow = getWorkFlowConnection();    		
   		
    	 	String role = (String) userM.getRoles().elementAt(0);
    	 	
    	 	logger.debug("...[ startBusinessParallelProcess ].. Connection >>> "+this.connWorkFlow);
    	 	logger.debug("...[ startBusinessParallelProcess ].. decision->" + decision);
    	 	logger.debug("...[ startBusinessParallelProcess ].. Jobid  ->" + applicationM.getJobID());
    	 	logger.debug("...[ startBusinessParallelProcess ].. AppRecordID->" + applicationM.getAppRecordID());
    	    logger.debug("...[ startBusinessParallelProcess ].. AppStatus->" + applicationM.getApplicationStatus());
    	 	logger.debug("...[ startBusinessParallelProcess ].. role->" + role);    	 		
  
    	 			
	    	WorkflowResponse response = BpmProxyService.getBpmProxyModule().StartParallelFlow(applicationM, userM.getUserName(),role, decision, applicationM.getOwner(), BpmProxyConstant.WorkflowProcessTemplate.WF_PROCESS_TEMPLATE_ID_ULO0001_NCB,this.connWorkFlow);
	    	 	
	    	logger.debug("...[ startBusinessParallelProcess ].. response code ->" + response.getResultCode());
	    	logger.debug("...[ startBusinessParallelProcess ].. response desc ->" + response.getResultDesc());

    }
    
    public void reOpenBusinessProcess(ApplicationDataM applicationM, UserDetailM userM, String decision, String message, String errrorMsg, String providerUrlWF,
            String jndi) throws Exception {

    /**
     * Call StartFlow BpmProxy 20110920 
     * */
    		this.connWorkFlow = getWorkFlowConnection();
    		
    	 	String role = (String) userM.getRoles().elementAt(0);
    	 	
    	 	logger.debug("...[ reOpenBusinessProcess ].. Connection >>> "+this.connWorkFlow);
    	 	logger.debug("...[ reOpenBusinessProcess ].. decision->" + decision);
    	 	logger.debug("...[ reOpenBusinessProcess ].. AppRecordID->" + applicationM.getAppRecordID());
    	    logger.debug("...[ reOpenBusinessProcess ].. AppStatus->" + applicationM.getApplicationStatus());
    	 	logger.debug("...[ reOpenBusinessProcess ].. role->" + role);  
    	 		
	    	 	WorkflowResponse response = BpmProxyService.getBpmProxyModule().ReopenFlow(applicationM, userM.getUserName(),role, decision, userM.getUserName(),BpmProxyConstant.WorkflowProcessTemplate.WF_PROCESS_TEMPLATE_ID_ULO0001,this.connWorkFlow);
	    	 	
	    	 	logger.debug("...[ reOpenBusinessProcess ].. response code ->" + response.getResultCode());
	    	 	logger.debug("...[ reOpenBusinessProcess ].. response desc ->" + response.getResultDesc());
	    	 	
		    	 	if(ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){
			    	 	UpdateApplicationStatusDAO updateApplicationDAO = ORIGDAOFactory.getUpdateApplicationStatusDAO();
			    	 	updateApplicationDAO.updateAppStatus(response);
		    	 	}

    }
	public Connection getWorkFlowConnection(){
		try{
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);  
		}catch(Exception e){ 
		    logger.fatal("Connection is null",e);
		}
		return null;
	}
    public boolean logonOrigApp(String username , String ipAddress ,String clientName){
        boolean result = false;
        try {
            OrigLogOnDAO logonDAO = ORIGDAOFactory.getOrigLogOnDAO();
            result = logonDAO.logonOrigApp(username,ipAddress,clientName);
        }catch(Exception e){
            logger.error("logonOrigApp error", e);
            throw new EJBException("Exception in logonOrigApp >> ", e);
        }
        return result;
    }

    public boolean CancelLogonOrig(String username,String ipAddress,String clientName){
        boolean result = false;
        try {
            OrigLogOnDAO logonDAO = ORIGDAOFactory.getOrigLogOnDAO();
            	result = logonDAO.cancelLogon(username,ipAddress,clientName);
        } catch (Exception e) {
            logger.error("logonOrigApp error", e);
            throw new EJBException("Exception in logonOrigApp >> ", e);
        }
        return result;
    }

    public String cmrSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        String result = "";
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
            String applicationNo = applicationDataM.getApplicationNo();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            if (applicationNo == null || ("").equals(applicationNo)) {
                //Set Office CDE
                String officeCode = userM.getDefaultOfficeCode();
                if (officeCode == null) {
                    officeCode = "";
                }
                Vector persoanlInfoVect = applicationDataM.getPersonalInfoVect();
                for (int j = 0; j < persoanlInfoVect.size(); j++) {
                    PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) persoanlInfoVect.get(j);

                    personalInfoDataM.setOfficeCode(officeCode);
                }
            }

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF
            if (applicationNo == null || ("").equals(applicationNo)) {
                startBusinessProcess(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.INITIATE, "", "", providerUrlWF, jndi);
            } else {
//            	Test Start Parallel
//              cancelClaimApplication(applicationDataM, providerUrlWF, jndi);
            	startBusinessParallelProcess(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.PRE_SUBMIT);
            }
            //result =
            // applicationMDAO.selectApplicationNo(applicationDataM.getAppRecordID());
        } catch (Exception e) {
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public String cmrSubmitApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        String result = "";
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
            String applicationNo = applicationDataM.getApplicationNo();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            if (applicationNo == null || ("").equals(applicationNo)) {
                //Set Office CDE
                String officeCode = userM.getDefaultOfficeCode();
                if (officeCode == null) {
                    officeCode = "";
                }
                Vector persoanlInfoVect = applicationDataM.getPersonalInfoVect();
                for (int j = 0; j < persoanlInfoVect.size(); j++) {
                    PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) persoanlInfoVect.get(j);

                    personalInfoDataM.setOfficeCode(officeCode);
                }
            }
//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF for complete application
            if (applicationNo == null || ("").equals(applicationNo)) {
                startBusinessProcess(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.PRE_SUBMIT, "", "", providerUrlWF, jndi);
            } else {
                completeApplication(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.PRE_SUBMIT, "", "", providerUrlWF, jndi);
            }
            //result =
            // applicationMDAO.selectApplicationNo(applicationDataM.getAppRecordID());
        } catch (Exception e) {
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean uwSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT, String customerType, String appType) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();

//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
//            Comment By Rawi Songchaisin 
//            if (guarantorIDVect == null) {
//                return false;
//            }

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Calculate Approval group
            EjbUtil ejbUtil = new EjbUtil();
            VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
            
            if(applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
                LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
                //loanDataM.setInternalCkecker(loanDataM.getInternalCheckTmp());
                
                //In case Auto Loan calculate approval group
                if(OrigConstant.ORG_AUTO_LOAN.equals(OrigUtil.getOrgID(applicationDataM.getBusinessClassId()))){
                    BigDecimal downPercent = ejbUtil.calculatePercent(loanDataM.getCostOfCarPrice(), loanDataM.getCostOfDownPayment());
                    BigDecimal installment;
                    if ("Y".equals(loanDataM.getBalloonFlag())) {
                        installment = loanDataM.getBalloonInstallmentAmt();
                    } else {
                        installment = loanDataM.getInstallment1();
                    }
                    ejbUtil.calculateApprovalGroup(userM, appRecordID, vehicleDataM.getCar(), customerType, loanDataM.getCostOfFinancialAmt(), appType, installment,
                            downPercent);
                }
            }

            //Call WF
            cancelClaimApplication(applicationDataM, providerUrlWF, jndi);

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean uwSubmitApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT, String customerType, String appType, ServiceDataM serviceDataM) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            //LoanDataM loanDataM = (LoanDataM)
            // applicationDataM.getLoanVect().elementAt(0);
            if (ORIGWFConstant.ApplicationDecision.APPROVE.equals(applicationDataM.getUwDecision())
                    || ORIGWFConstant.ApplicationDecision.CANCEL.equals(applicationDataM.getUwDecision())
                    || ORIGWFConstant.ApplicationDecision.REJECT.equals(applicationDataM.getUwDecision())) {

                //Set Credit Approval and Internal Checker
                //loanDataM.setCreditApproval(loanDataM.getInternalCheckTmp());
                //loanDataM.setInternalCkecker(loanDataM.getInternalCheckTmp());
            }
            
            //Generate Card No.
            if(applicationDataM!=null && ORIGWFConstant.ApplicationDecision.APPROVE.equals(applicationDataM.getUwDecision())){
                Vector vCardM = applicationDataM.getCardInformation();
                //UniqueIDGeneratorDAO uniqueIDDAO = ORIGDAOFactory.getUniqueIDGeneratorDAO2();
                ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
    			String cardNo = "";
                for(int i=0; i<vCardM.size(); i++){
                    CardInformationDataM cardM= (CardInformationDataM)vCardM.elementAt(i);
                    //cardNo = uniqueIDDAO.getCardNo(cardM);
                    cardNo = generatorManager.generateCardNo(cardM);
                    logger.debug("Generate Card No.="+cardNo);
                    cardM.setCardNo(cardNo);
                }
            }

//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            
//            Remove By Rawi Songchaisin
//            if (guarantorIDVect == null) {
//                return false;
//            }
            
            //	        if(ORIGWFConstant.ApplicationDecision.ESCALATE.equals(applicationDataM.getUwDecision())){
            //	        	//Calculate Approval group
            //		        EjbUtil ejbUtil = new EjbUtil();
            //		        VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
            //		        
            //	        	BigDecimal downPercent =
            // ejbUtil.calculatePercent(loanDataM.getCostOfCarPrice(),
            // loanDataM.getCostOfDownPayment());
            //	        	BigDecimal installment;
            //	        	if("Y".equals(loanDataM.getBalloonFlag())){
            //	        		installment = loanDataM.getBalloonInstallmentAmt();
            //	        	}else{
            //	        		installment = loanDataM.getInstallment1();
            //	        	}
            //	        	ejbUtil.calculateApprovalGroup(userM, appRecordID,
            // vehicleDataM.getCar(), customerType,
            // loanDataM.getCostOfFinancialAmt(), appType, installment,
            // downPercent);
            //	        	
            //	        }

            String decision = applicationDataM.getUwDecision();
            if (applicationDataM.getIsException()) {
                decision = ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION;
            }
//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF for complete application
            if (OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION.equals(decision)) {
                //Update Application Status to NEW ,jobstate UW NEW ST0400
//            	Remove Cancleclaim to Conplete application
//                applicationMDAO.updateStatus(applicationDataM);
//                cancelClaimApplicationForXUW(applicationDataM, providerUrlWF, jndi);
            	completeApplication(applicationDataM, userM, decision, "", "", providerUrlWF, jndi);
            } else {
                completeApplication(applicationDataM, userM, decision, "", "", providerUrlWF, jndi);
            }
            //Send SMS or Email
            //	        String sendEmailResult = null;
            //	        UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
            //	        Vector cmrUserVect =
            // utilityDAO.getCMRUser(loanDataM.getMarketingCode());
            //			if(cmrUserVect.size() > 0){
            //				UserDetailM userDetailM;
            //				for(int i=0; i<cmrUserVect.size(); i++){
            //					userDetailM = (UserDetailM) cmrUserVect.get(i);
            //			        if(OrigConstant.CHANNEL_EMAIL.equals(userDetailM.getCommunicate_channel())){//set
            // send email
            //						EmailM emailM = new EmailM();
            //						SendEmail sendEmail = new SendEmail();
            //						OrigMasterGenParamDAO genParamDAO =
            // OrigMasterDAOFactory.getOrigMasterGenParamDAO();
            //						GeneralParamM generalParamM;
            //						
            //						if(applicationDataM.getIsException()){
            //							generalParamM =
            // genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_SUBJECT_XCMR,
            // OrigConstant.BUSINESS_CLASS_AL);
            //							emailM.setContent(sendEmail.getMessageForXCMR(applicationDataM));
            //						}else{
            //							generalParamM =
            // genParamDAO.loadModelOrigMasterGenParamM(OrigConstant.ParamCode.MAIL_SUBJECT_UW,
            // OrigConstant.BUSINESS_CLASS_AL);
            //							emailM.setContent(sendEmail.getMessageForUW(applicationDataM));
            //						}
            //						emailM.setTo(userDetailM.getEmail());
            //						emailM.setSubject(generalParamM.getParamValue());
            //						sendEmail.setEmail(emailM);
            //						sendEmailResult = sendEmail.sendEmail(serviceDataM);
            //						logger.debug("sendEmailResult = "+sendEmailResult);
            //					}else{
            //		// SendSMS sendSMS = new SendSMS();
            //		// SMSDataM dataM = sendSMS.getSMSData(applicationDataM,
            // userDetailM.getMobilePhone(), role);
            //		// String line = "";
            //		// line = sendSMS.sendSMS(dataM);
            //		// logger.debug("line = "+line);
            //					}
            //				}
            //			}

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean uwReopenApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String applicationNo = applicationDataM.getApplicationNo();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            
            //Generate Card No.
            if(applicationDataM!=null && ORIGWFConstant.ApplicationDecision.APPROVE.equals(applicationDataM.getUwDecision())){
                Vector vCardM = applicationDataM.getCardInformation();
                //UniqueIDGeneratorDAO uniqueIDDAO = ORIGDAOFactory.getUniqueIDGeneratorDAO2();
                ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
                String cardNo = "";
                for(int i=0; i<vCardM.size(); i++){
                    CardInformationDataM cardM= (CardInformationDataM)vCardM.elementAt(i);
                    if(cardM!=null && (cardM.getCardNo()==null || "".equals(cardM.getCardNo())) ){
                        //cardNo = uniqueIDDAO.getCardNo(cardM);
                        cardNo = generatorManager.generateCardNo(cardM);
                    	logger.debug("Generate Card No.="+cardNo);
                        cardM.setCardNo(cardNo);
                    }
                }
            }
            
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            
//            Comment By Rawi Songchaisin
//            if (guarantorIDVect == null) {
//                return false;
//            }
//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            if (applicationDataM.getIsException()) {
                applicationDataM.setUwDecision(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
                applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
            }
            //Call WF for reOpenBusinessProcess
            //startBusinessProcess(applicationDataM, userM, applicationDataM.getUwDecision(), "", "", providerUrlWF, jndi);
            
            reOpenBusinessProcess(applicationDataM, userM, applicationDataM.getUwDecision(), "", "", providerUrlWF, jndi);
            

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean uwReAssignApplication(String appRecordID, String appStatus, String jobState, UserDetailM userM, String providerUrlWF, String jndi) {
        boolean result = true;
        String message = "";
        try {
            logger.debug("Application Record ID " + appRecordID);
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            //Update data
            applicationMDAO.updateReassignApplication(appRecordID, userM.getUserName());

            //Call WF for complete application
            claimCompleteApplication(appRecordID, appStatus, jobState, new Timestamp(System.currentTimeMillis()), userM,
                    ORIGWFConstant.ApplicationDecision.REASSIGN, providerUrlWF, jndi);

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public int uwSetPriority(String appRecordID, String priority, String userName) {
        int result = 0;
        String message = "";
        try {
            logger.debug("Set Priorlity : appRecordID : = " + appRecordID + " : " + priority);
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            applicationMDAO.updateSetPriority(appRecordID, priority, userName);

        } catch (Exception e) {
            result = 0;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean uwDraftReopenApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
            String role = (String) userM.getRoles().elementAt(0);
//            String applicationNo = applicationDataM.getApplicationNo();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            
            if (guarantorIDVect == null) {
                return false;
            }
            
//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF for complete application
            //startBusinessProcess(applicationDataM, userM,
            // applicationDataM.getUwDecision(), "", "", providerUrlWF, jndi);

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean pdSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF
            if (ORIGWFConstant.JobState.PD_APPROVED_STATE.equals(applicationDataM.getJobState())) {
                completeApplication(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.SAVE_DRAFT, "", "", providerUrlWF, jndi);
            } else {
                cancelClaimApplication(applicationDataM, providerUrlWF, jndi);
            }

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean pdSubmitApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            String decision = applicationDataM.getPdDecision();
            if (decision == null || ("").equals(decision)) {
                if (applicationDataM.getIsCompleteDoc()) {
                    decision = ORIGWFConstant.ApplicationDecision.COMPLETE_DOC;
                } else {
                    decision = ORIGWFConstant.ApplicationDecision.INCOMPLETE_DOC;
                }
            }

            //Call WF for complete application
            completeApplication(applicationDataM, userM, decision, "", "", providerUrlWF, jndi);

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public Vector loadCarDetail(String coClientID) {
        Vector resultVt = null;
        try {
            OrigVehicleMDAO vehicleMDAO = ORIGDAOFactory.getOrigVehicleMDAO();
            OrigLoanMDAO loanMDAO = ORIGDAOFactory.getOrigLoanMDAO();
            resultVt = vehicleMDAO.loadModelVehicleDataMByCoClientID(coClientID);

        } catch (Exception e) {
            logger.error("Load Application error", e);
            throw new EJBException("Exception in loadApplicationDataM >> ", e);
        }
        return resultVt;
    }

    public boolean saveCarDetail(Vector carDetailVt, String idNo) {
        try {
            StringBuffer vehicleIdBf = new StringBuffer("");
            VehicleDataM vehicleM = null;
            OrigVehicleMDAO vehicleMDAO = ORIGDAOFactory.getOrigVehicleMDAO();
            if (carDetailVt != null && carDetailVt.size() > 0) {
                for (int i = 0; i < carDetailVt.size(); i++) {
                    vehicleM = (VehicleDataM) carDetailVt.get(i);
                    vehicleM.setIdNo(idNo);
                    logger.debug("Vehicle id is " + vehicleM.getVehicleID());
                    vehicleMDAO.saveUpdateModelVehicleDataMByVehicleId(vehicleM);
                    vehicleIdBf.append("'" + String.valueOf(vehicleM.getVehicleID()) + "',");
                }
                vehicleMDAO.deleteNewVehicleDataMByIdNo(idNo, String.valueOf(vehicleIdBf.substring(0, vehicleIdBf.length() - 1)));
            } else {
                vehicleMDAO.deleteNewVehicleDataMByIdNo(idNo, "");
            }
        } catch (Exception e) {
            logger.error("saveCarDetail error", e);
            throw new EJBException("Exception in saveCarDetail >> ", e);
        }
        return true;
    }

    public boolean proposalSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
            String appNo = applicationDataM.getApplicationNo();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Check Client Group
            PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) applicationDataM.getPersonalInfoVect().elementAt(0);
            if (personalInfoDataM.getClientGroup() != null && !("").equals(personalInfoDataM.getClientGroup())) {
                UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
                StringBuffer name = new StringBuffer("");
                name.append(utilityDAO.getTitleDescription(personalInfoDataM.getThaiTitleName()));
                name.append(personalInfoDataM.getThaiFirstName());
                name.append(" ");
                name.append(personalInfoDataM.getThaiLastName());
//                boolean isNewClientGroup = applicationEXTManager.checkClientGroup(personalInfoDataM.getClientGroup(), userM.getUserName(), name.toString());
                /*
                 * if(isNewClientGroup){ Tablel }
                 */
            }

            //Call WF
            if (appNo == null || ("").equals(appNo)) {
                startBusinessProcess(applicationDataM, userM, ORIGWFConstant.ApplicationDecision.CREATE_PROPOSAL, "", "", providerUrlWF, jndi);
            } else {
                cancelClaimApplication(applicationDataM, providerUrlWF, jndi);
            }

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in proposalSaveApplication >> ", e);
        }
        return result;
    }

    public boolean proposalSubmitApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
            String appNo = applicationDataM.getApplicationNo();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Check Client Group
            PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) applicationDataM.getPersonalInfoVect().elementAt(0);
            if (personalInfoDataM.getClientGroup() != null && !("").equals(personalInfoDataM.getClientGroup())) {
                UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
                StringBuffer name = new StringBuffer("");
                name.append(utilityDAO.getTitleDescription(personalInfoDataM.getThaiTitleName()));
                name.append(personalInfoDataM.getThaiFirstName());
                name.append(" ");
                name.append(personalInfoDataM.getThaiLastName());
//                boolean isNewClientGroup = applicationEXTManager.checkClientGroup(personalInfoDataM.getClientGroup(), userM.getUserName(), name.toString());
                /*
                 * if(isNewClientGroup){ Tablel }
                 */
            }

            //Call WF for complete application
            if (appNo == null || ("").equals(appNo)) {
                startBusinessProcess(applicationDataM, userM, applicationDataM.getUwDecision(), "", "", providerUrlWF, jndi);
            } else {
                completeApplication(applicationDataM, userM, applicationDataM.getUwDecision(), "", "", providerUrlWF, jndi);
            }

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in proposalSubmitApplication >> ", e);
        }
        return result;
    }

    public ApplicationDataM loadCarDetailDataM(ApplicationDataM applicationDataM, String vehicleID) {
        try {
            if (applicationDataM == null) {
                applicationDataM = new ApplicationDataM();
            }
            OrigVehicleMDAO vehicleMDAO = ORIGDAOFactory.getOrigVehicleMDAO();
            VehicleDataM vehicleDataM = null;
            vehicleDataM = vehicleMDAO.loadModelVehicleDataMByVehicleID(vehicleID);
            vehicleDataM.setDrawDownStatus("PROGRESS");
            applicationDataM.setVehicleDataM(vehicleDataM);
            Vector loanVt = new Vector();
            loanVt.add(vehicleDataM.getLoanDataM());
            applicationDataM.setLoanVect(loanVt);
            applicationDataM.setLoanType(vehicleDataM.getLoanDataM().getLoanType());
        } catch (Exception e) {
            logger.error("Load Application error", e);
            throw new EJBException("Exception in loadApplicationDataM >> ", e);
        }
        return applicationDataM;
    }

    public boolean xcmrSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);
            //Call WF
            cancelClaimApplication(applicationDataM, providerUrlWF, jndi);
        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public boolean xcmrSubmitApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF for complete application
            completeApplication(applicationDataM, userM, applicationDataM.getXcmrDecision(), "", "", providerUrlWF, jndi);

        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return result;
    }

    public String selectApplicationNo(String appRecordID) {
        try {
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            String applicationNo = applicationMDAO.selectApplicationNo(appRecordID);
            return applicationNo;
        } catch (Exception e) {
            logger.error("Error selectApplicationNo : ", e);
            throw new EJBException("Exception in selectApplicationNo >> ", e);
        }
    }

    public void autoCancelApplication(String appRecordID, String jobstate, String appStatus) {
        logger.info(">>> autoCancelApplication : " + appRecordID + " jobstate : " + jobstate + " appStatus : " + appStatus);
        try {
            UserDetailM userM = new UserDetailM();
            userM.setUserName("System");
            claimCompleteApplication(appRecordID, appStatus, jobstate, new Timestamp(new Date().getTime()), userM,
                    ORIGWFConstant.ApplicationDecision.AUTO_CANCEL, OrigConstant.getWfProviderUrl(), OrigConstant.getWfJNDI());
        } catch (Exception e) {
            logger.error("Error autoCancelApplication : ", e);
            //throw new EJBException("Exception in autoCancelApplication >> ");
        }
    }

    public void deleteOldSchedulerTask(String schedName) {
        logger.info(">>> deleteOldSchedulerTask : " + schedName);
        try {
            SchedulerDAO scheduleDAO = ORIGDAOFactory.getSchedulerDAO();
            scheduleDAO.deleteOldTask(schedName);
        } catch (Exception e) {
            logger.error("Error deleteOldSchedulerTask : ", e);
            throw new EJBException("Exception in deleteOldSchedulerTask >> ", e);
        }
    }

    public String generateApplicationNo(ApplicationDataM prmApplicationDataM){
        String applicationNo = "";
        try {
            //applicationNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getApplicationNo(prmApplicationDataM);
        	ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
        	applicationNo = generatorManager.generateApplicationNo(prmApplicationDataM);
        } catch (Exception e) {
            logger.fatal(e.getMessage());
            throw new EJBException("Exception in generate Applicatio no >> ", e);
        }
        return applicationNo;
    }

    public ApplicationDataM ncbSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndiWF,
            String providerUrlEXT, String jndiEXT, String providerUrlIMG, String jndiIMG) {
        ApplicationDataM result = null;
        String message = "";
        try {
            String role = (String) userM.getRoles().elementAt(0);
            String applicationNo = applicationDataM.getApplicationNo();
            String requestID = applicationDataM.getRequestID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            logger.debug("requestID " + requestID);
            //first id
            if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                if (applicationDataM.getCmrFirstId() == null) {
                    applicationDataM.setCmrFirstId(userM.getUserName());
                }

            } else if (ORIGWFConstant.JobState.DE_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                    || ORIGWFConstant.JobState.DE_INITIAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                if (applicationDataM.getDeFirstId() == null) {
                    applicationDataM.setDeFirstId(userM.getUserName());
                }
            } else if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                    || ORIGWFConstant.JobState.UW_NEW_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                if (applicationDataM.getUwFirstId() == null) {
                    applicationDataM.setUwFirstId(userM.getUserName());
                }
            }
            //-------------------
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            if (applicationNo == null || ("").equals(applicationNo)) {
                //Set Office CDE
                String officeCode = userM.getDefaultOfficeCode();
                if (officeCode == null) {
                    officeCode = "";
                }
                Vector persoanlInfoVect = applicationDataM.getPersonalInfoVect();
                for (int j = 0; j < persoanlInfoVect.size(); j++) {
                    PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) persoanlInfoVect.get(j);

                    personalInfoDataM.setOfficeCode(officeCode);
                }
            }
            /*ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);*/
            //Call WF not Call Workflow
            if (!isApplicationExistInWorkflow(applicationDataM.getAppRecordID(), role, providerUrlWF, jndiWF)) {
                logger.debug("Appliccation not found in WF Initiate Workflow");
                //set appstatus
                applicationDataM.setApplicationStatus(ORIGWFConstant.ApplicationStatus.DRAFT);
                //set jobstate
                if (requestID != null && !("").equals(requestID)) {
                    completeIMGApplication(applicationDataM, userM, "", "", "", providerUrlIMG, jndiIMG);
                    //Update appRecordID and status
                    applicationMDAO.updateApprecordForIMG(applicationDataM.getAppRecordID(), requestID);
                    applicationMDAO.updateStatusForIMG(requestID);
                    //20080204 Sankom
                    //clear aiid image
                    applicationDataM.setAiid(null);
                }
                String decision = "";
                if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                    decision = ORIGWFConstant.ApplicationDecision.INITIATE;
                } else if (ORIGWFConstant.JobState.DE_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                        || ORIGWFConstant.JobState.DE_INITIAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                    decision = ORIGWFConstant.ApplicationDecision.CREATE;
                } else if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                        || ORIGWFConstant.JobState.UW_NEW_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                    decision = ORIGWFConstant.ApplicationDecision.CREATE_PROPOSAL;
                }
                logger.debug("NCB start Process decison " + decision);
                startBusinessProcess(applicationDataM, userM, decision, "", "", providerUrlWF, jndiWF);
                
            } else {
                logger.debug("Appliccation   found in WF ");
                /*
                 * if(ORIGWFConstant.JobState.DE_INITIAL_STATE.equals(applicationDataM.getJobState()) ||
                 * ORIGWFConstant.JobState.DE_REWORK_STATE.equals(applicationDataM.getJobState()) ||
                 * ORIGWFConstant.JobState.DE_PENDING_STATE.equals(applicationDataM.getJobState())){
                 * completeApplication(applicationDataM, userM,
                 * ORIGWFConstant.ApplicationDecision.SAVE_DRAFT, "", "",
                 * providerUrlWF, jndiWF); }else{
                 * cancelClaimApplication(applicationDataM, providerUrlWF,
                 * jndiWF); }
                 */
            }

        } catch (Exception e) {
            result = applicationDataM;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in deSaveApplication >> ", e);
        }
        return applicationDataM;
    }

    public String getAppRecordForCreateDrawDown(String personalID) {
        String appRecordID = null;
        try {
            UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
            appRecordID = utilityDAO.getAppRecordForCreateDrawDown(personalID);
        } catch (Exception e) {
            //result = false;
//            String message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in ncbSaveApplication >> ", e);
        }
        return appRecordID;
    }

    public PersonalInfoDataM getVerificationForCreateDrawDown(PersonalInfoDataM prmPersonalInfoDataM) {
        try {
            UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
            String result = utilityDAO.getPersonIdAndAppRecordIDForCreateDrawDown(prmPersonalInfoDataM.getIdNo());
            String[] results = result.split(",");
            logger.debug("results = " + results);
            if (results.length > 1) {
                XRulesVerificationResultDAO xRuesVerificationDAO = ORIGDAOFactory.getXRulesVerificationResultDAO();
                XRulesVerificationResultDataM prmXRuesVerficationResultDataM = xRuesVerificationDAO.loadModelXRulesVerificationResultM(prmPersonalInfoDataM.getPersonalID());
                prmPersonalInfoDataM.setXrulesVerification(prmXRuesVerficationResultDataM);
                prmPersonalInfoDataM.setCustomerTypeTmp(results[2]);
            }

            return prmPersonalInfoDataM;
        } catch (Exception e) {
            //result = false;
//            String message = e.toString();
            logger.error("getVerificationForCreateDrawDown error", e);
            throw new EJBException("Exception in getVerificationForCreateDrawDown >> ", e);
        }
    }

    public boolean isApplicationExistInWorkflow(String applicationRecordId,String role, String providerUrlWF, String jndiWF) {
        boolean appExistInMainFlow = false;
        try {
        	//** Remove WPS
//            ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndiWF);
//            appExistInMainFlow = OrigManager.isExistingInMainApplicationFlow(applicationRecordId);
        	this.connWorkFlow = getWorkFlowConnection();
        	BpmProxyModule bpmProxyModule = BpmProxyService.getBpmProxyModule();
        	appExistInMainFlow = bpmProxyModule.isExistingInMainApplicationFlow(applicationRecordId,role , this.connWorkFlow );
        	
        	logger.debug("appExistInMainFlow >> "+appExistInMainFlow);
        	
        } catch (Exception e) {
            logger.error("IsApplicaitonExistInWorkflow Error:", e);
        }
        return appExistInMainFlow;
    }

    public boolean sendSMSAndEmail(ApplicationDataM applicationDataM, ServiceDataM serviceDataM, UserDetailM userLogon) {
        //Send SMS or Email

        try {
            String sendEmailResult = null;
            UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
            if(applicationDataM.getLoanVect()!=null && applicationDataM.getLoanVect().size()>0){
	            LoanDataM loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
	            logger.debug("loanDataM.getMarketingCode() = " + loanDataM.getMarketingCode());
	            Vector sendtoUserVect = utilityDAO.getCMRUser(loanDataM.getMarketingCode());
	            logger.debug("cmrUserVect = " + sendtoUserVect);
	            //if (sendtoUserVect.size() > 0) {
	
	                //Get User Name
	                String cmrOwner = "";
	                UserDetailM userDetailM;
	                for (int i = 0; i < sendtoUserVect.size(); i++) {
	                    userDetailM = (UserDetailM) sendtoUserVect.get(i);
	                    if (userDetailM.isIs_valid()) {
	                        cmrOwner = userDetailM.getUserName();
	                        break;
	                    }
	                }
	                //If
	                //  if
	                // (OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(applicationDataM.getAppDecision())
	                //    ||
	                // OrigConstant.ApplicationDecision.NOT_OVERIDED_POLICY.equals(applicationDataM.getAppDecision()))
	                // {
	                //Add new Email
	                //   UserDetailM userdetailM =
	                // OrigMasterDAOFactory.getOrigMasterUserDetailDAO().selectOrigMasterUserDetailM(userLogon.getUserName());
	                // }
	                String sendRole;
	                String subject;
	                if (ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_DE.equals(applicationDataM.getAppDecision())
	                        || ORIGWFConstant.ApplicationDecision.NOT_OVERRIDE_CAMPAIGN_TO_UW.equals(applicationDataM.getAppDecision())
	                        || ORIGWFConstant.ApplicationDecision.OVERRIDE_CAMPAIGN.equals(applicationDataM.getAppDecision())) {
	                    subject = OrigConstant.ParamCode.MAIL_SUBJECT_XCMR;
	                    sendRole = OrigConstant.ROLE_XCMR;
	                    UserDetailM userInteralCheckerDetailM = OrigMasterDAOFactory.getOrigMasterUserDetailDAO().selectOrigMasterUserDetailM(
	                            applicationDataM.getUwLastId());
	                    if (userInteralCheckerDetailM != null) {
	                        sendtoUserVect.add(userInteralCheckerDetailM);
	                    }
	                } else if (OrigConstant.ApplicationDecision.OVERIDED_POLICY.equals(applicationDataM.getAppDecision())
	                        || OrigConstant.ApplicationDecision.NOT_OVERIDED_POLICY.equals(applicationDataM.getAppDecision())) {
	                    subject = OrigConstant.ParamCode.MAIL_SUBJECT_XUW;
	                    sendRole = OrigConstant.ROLE_XUW;
	                    UserDetailM userInteralCheckerDetailM = OrigMasterDAOFactory.getOrigMasterUserDetailDAO().selectOrigMasterUserDetailM(
	                            applicationDataM.getUwLastId());
	                    if (userInteralCheckerDetailM != null) {
	                        sendtoUserVect.add(userInteralCheckerDetailM);
	                    }
	                }else if (ORIGWFConstant.ApplicationDecision.SUBMIT.equals(applicationDataM.getAppDecision())) {
	                    logger.debug("DE SUBMIT = "+applicationDataM.getAppDecision());
	                    subject = OrigConstant.ParamCode.MAIL_SUBJECT_DE;
	                    sendRole = OrigConstant.ROLE_DE;
	                } else {
	                    subject = OrigConstant.ParamCode.MAIL_SUBJECT_UW;
	                    sendRole = OrigConstant.ROLE_UW;
	                    logger.debug("UW  Decison "+applicationDataM.getAppDecision());
	                    if (OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION.equals(applicationDataM.getAppDecision())) {
	                        Vector vXUWUser = ORIGDAOFactory.getUtilityDAO().getAllUserNameXUW();
	                        if (vXUWUser != null) {
	                            for(int j=0;j<vXUWUser.size();j++){
	                            String xuwUser= (String) vXUWUser.get(j); 
	                            logger.debug("XUW User "+xuwUser);
	                            UserDetailM userXUWDetailM = OrigMasterDAOFactory.getOrigMasterUserDetailDAO().selectOrigMasterUserDetailM(
	                                    xuwUser);
	                            if (userXUWDetailM != null) {
	                                sendtoUserVect.add(userXUWDetailM);
	                            }
	                            } 
	                        }
	                    }
	                }
	                for (int i = 0; i < sendtoUserVect.size(); i++) {
	                    userDetailM = (UserDetailM) sendtoUserVect.get(i);
	
	                    //ORIGUtility utility = new ORIGUtility();
	                    //ServiceDataM serviceDataM = utility.getServiceDataM();
	                    if (OrigConstant.CHANNEL_EMAIL.equals(userDetailM.getCommunicate_channel())) {//set
	                                                                                                  // //
	                                                                                                  // //
	                                                                                                  // //
	                                                                                                  // send
	                                                                                                  // //
	                                                                                                  // //
	                                                                                                  // //email
	                        logger.debug("Type Email");
	                        EmailM emailM = new EmailM();
	                        SendEmail sendEmail = new SendEmail();
//	                        OrigMasterGenParamDAO genParamDAO = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
	                        //Add Load Subject XUW
//	                        GeneralParamM generalParamM = genParamDAO.loadModelOrigMasterGenParamM(subject, OrigConstant.BUSINESS_CLASS_AL);
	                        emailM.setTo(userDetailM.getEmail());
	                        emailM.setContent(sendEmail.getContent(applicationDataM, cmrOwner, sendRole));
	                        emailM.setSubject(sendEmail.getSubject(applicationDataM, cmrOwner, sendRole));
	                        sendEmail.setEmail(emailM);
	                        sendEmailResult = sendEmail.sendEmail(serviceDataM);
	                        logger.debug("sendEmailResult = " + sendEmailResult);
	
	                    } else {
	                        logger.debug("Type SMS");
	                        SendSMS sendSMS = new SendSMS();
	                        String role = (String) userLogon.getRoles().elementAt(0);
	
	                        SMSDataM dataM = sendSMS.getSMSData(applicationDataM, userDetailM.getMobilePhone(), serviceDataM, role, cmrOwner, sendRole);
	                        String line = sendSMS.sendSMS(dataM, serviceDataM);
	                        logger.debug("line = " + line);
	
	                        SMSDataM dataM2 = sendSMS.getSMSDataForSmsLog(dataM, userLogon.getUserName(), userDetailM);
	                        if (line == null || ("").equals(line)) {
	                            dataM2.setStatus("Fail");
	                        } else {
	                            dataM2.setStatus("Success");
	                        }
	                        OrigSmsLogDAO origSmsLogDAO = ORIGDAOFactory.getOrigSmsLogDAO();
	                        origSmsLogDAO.createModelSmsLogM(applicationDataM.getAppRecordID(), dataM2);
	                    }
	                }
            }
        } catch (Exception e) {
            //result = false;
//            String message = e.toString();
            logger.error("sendSMSAndEmail error", e);
            throw new EJBException("Exception in sendSMSAndEmail >> ", e);
        }
        return true;
    }

    public boolean saveUserDetail(UserM userM) {
        boolean appExistInMainFlow = false;
        try {

            OrigMasterUserDetailDAO detailDAO = OrigMasterDAOFactory.getOrigMasterUserDetailDAO();
            detailDAO.saveUserM(userM);

        } catch (Exception e) {
            logger.error("IsApplicaitonExistInWorkflow Error:", e);
        }
        return appExistInMainFlow;
    }

    public ApplicationDataM preScoreSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndiWF,
            String providerUrlEXT, String jndiEXT, String providerUrlIMG, String jndiIMG) {
        ApplicationDataM result = null;
        String message = "";
        try {
            String role = (String) userM.getRoles().elementAt(0);
            String applicationNo = applicationDataM.getApplicationNo();
            String requestID = applicationDataM.getRequestID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            logger.debug("requestID " + requestID);
            //first id
            if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                if (applicationDataM.getCmrFirstId() == null) {
                    applicationDataM.setCmrFirstId(userM.getUserName());
                }

            } else if (ORIGWFConstant.JobState.DE_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                    || ORIGWFConstant.JobState.DE_INITIAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                if (applicationDataM.getDeFirstId() == null) {
                    applicationDataM.setDeFirstId(userM.getUserName());
                }
            } else if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                    || ORIGWFConstant.JobState.UW_NEW_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                if (applicationDataM.getUwFirstId() == null) {
                    applicationDataM.setUwFirstId(userM.getUserName());
                }
            }
            //-------------------
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
            if (applicationNo == null || ("").equals(applicationNo)) {
                //Set Office CDE
                String officeCode = userM.getDefaultOfficeCode();
                if (officeCode == null) {
                    officeCode = "";
                }
                Vector persoanlInfoVect = applicationDataM.getPersonalInfoVect();
                for (int j = 0; j < persoanlInfoVect.size(); j++) {
                    PersonalInfoDataM personalInfoDataM = (PersonalInfoDataM) persoanlInfoVect.get(j);

                    personalInfoDataM.setOfficeCode(officeCode);
                }
            }
//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);
            //Call WF not Call Workflow 
            if (!isApplicationExistInWorkflow(applicationDataM.getAppRecordID(), role, providerUrlWF, jndiWF)) {
                logger.debug("Appliccation not found in WF Initiate Workflow");
                //set appstatus
                applicationDataM.setApplicationStatus(ORIGWFConstant.ApplicationStatus.DRAFT);
                //set jobstate
                if (requestID != null && !("").equals(requestID)) {
                    completeIMGApplication(applicationDataM, userM, "", "", "", providerUrlIMG, jndiIMG);
                    //Update appRecordID and status
                    applicationMDAO.updateApprecordForIMG(applicationDataM.getAppRecordID(), requestID);
                    applicationMDAO.updateStatusForIMG(requestID);
                    //20080204 Sankom
                    //clear aiid image
                    applicationDataM.setAiid(null);
                }
                String decision = "";
                if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                    decision = ORIGWFConstant.ApplicationDecision.INITIATE;
                } else if (ORIGWFConstant.JobState.DE_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                        || ORIGWFConstant.JobState.DE_INITIAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                    decision = ORIGWFConstant.ApplicationDecision.CREATE;
                } else if (ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())
                        || ORIGWFConstant.JobState.UW_NEW_STATE.equalsIgnoreCase(applicationDataM.getJobState())) {
                    decision = ORIGWFConstant.ApplicationDecision.CREATE_PROPOSAL;
                }
                logger.debug("PreScore start Process decison " + decision);
                startBusinessProcess(applicationDataM, userM, decision, "", "", providerUrlWF, jndiWF);
            } else {
                logger.debug("Appliccation found in WF ");
                /*
                 * if(ORIGWFConstant.JobState.DE_INITIAL_STATE.equals(applicationDataM.getJobState()) ||
                 * ORIGWFConstant.JobState.DE_REWORK_STATE.equals(applicationDataM.getJobState()) ||
                 * ORIGWFConstant.JobState.DE_PENDING_STATE.equals(applicationDataM.getJobState())){
                 * completeApplication(applicationDataM, userM,
                 * ORIGWFConstant.ApplicationDecision.SAVE_DRAFT, "", "",
                 * providerUrlWF, jndiWF); }else{
                 * cancelClaimApplication(applicationDataM, providerUrlWF,
                 * jndiWF); }
                 */
            }

        } catch (Exception e) {
            result = applicationDataM;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in prescoreSaveApplication >> ", e);
        }
        return applicationDataM;
    }

    public ApplicationDataM pdReverseApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi,
            String providerUrlEXT, String jndiEXT) {

        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String applicationNo = applicationDataM.getApplicationNo();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.WITHDRAW);
            applicationDataM.setJobState(ORIGWFConstant.JobState.PD_WITHDREW_STATE);
            applicationDataM.setApplicationStatus(ORIGWFConstant.ApplicationStatus.WITHDREW);
            applicationDataM.setPdDecision(ORIGWFConstant.ApplicationDecision.WITHDRAW);
            applicationDataM.setPdLastId(userM.getUserName());
            applicationMDAO.updateStatusForReverse(applicationDataM);
            // Vector guarantorIDVect =
            // applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM,
            // role);
            // if (guarantorIDVect == null) {
            //  return false;
            // }
            OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
            ApplicationLogDataM applicationLogDataM = new ApplicationLogDataM();
            applicationLogDataM.setAction(ORIGWFConstant.ApplicationDecision.WITHDRAW);
            applicationLogDataM.setApplicationRecordID(applicationDataM.getAppRecordID());
            applicationLogDataM.setApplicationStatus(applicationDataM.getApplicationStatus());
            applicationLogDataM.setJobState(applicationDataM.getJobState());
            logger.debug("applicationDataM.getUpdateBy() = " + applicationDataM.getUpdateBy());
            applicationLogDataM.setUpdateBy(applicationDataM.getUpdateBy());
            logMDAO.createModelOrigApplicationLogM(applicationLogDataM);
            // ApplicationEXTManager applicationEXTManager =
            // ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
            //  applicationEXTManager.saveUpdateApplication(applicationDataM,
            // guarantorIDVect);

            // if (applicationDataM.getIsException()) {
            //    applicationDataM.setUwDecision(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
            //    applicationDataM.setAppDecision(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION);
            //}
            //Call WF for complete application
            //startBusinessProcess(applicationDataM, userM,
            // applicationDataM.getUwDecision(), "", "", providerUrlWF, jndi);

        } catch (Exception e) {
            //result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in pdReverseApplication >> ", e);
        }
        return applicationDataM;
    }

    public boolean manualCancelApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT, String customerType, String appType, ServiceDataM serviceDataM) {
        logger.debug("Manual Cancel application");
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);
//            Remove guarantorIDVect Check  
//            if (guarantorIDVect == null) {
//                return false;
//            }
            claimCompleteApplication(applicationDataM.getAppRecordID(), applicationDataM.getApplicationStatus(), applicationDataM.getJobState(), new Timestamp(
                    new Date().getTime()), userM, ORIGWFConstant.ApplicationDecision.AUTO_CANCEL, OrigConstant.getWfProviderUrl(), OrigConstant.getWfJNDI());
        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Manual Cancel application action error", e);
            throw new EJBException("Exception in Manuaal cancel Application >> ", e);
        }
        return result;
    }

    public boolean xuwSaveApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
//            String role = (String) userM.getRoles().elementAt(0);
//            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
//            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
//            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);
            //Call WF
            cancelClaimApplicationForXUW(applicationDataM, providerUrlWF, jndi);
        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in xuwSaveApplication >> ", e);
        }
        return result;
    }

    public boolean xuwSubmitApplication(ApplicationDataM applicationDataM, UserDetailM userM, String providerUrlWF, String jndi, String providerUrlEXT,
            String jndiEXT) {
        boolean result = true;
        String message = "";
        try {
            String role = (String) userM.getRoles().elementAt(0);
            String appRecordID = applicationDataM.getAppRecordID();
            logger.debug("Application Record ID " + applicationDataM.getAppRecordID());
            OrigApplicationMDAO applicationMDAO = ORIGDAOFactory.getOrigApplicationMDAO();
            Vector guarantorIDVect = applicationMDAO.saveUpdateModelOrigApplicationM(applicationDataM, role);

//            ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//            applicationEXTManager.saveUpdateApplication(applicationDataM, guarantorIDVect);

            //Call WF for complete application
            //Decision SendtoInbox
            // String decision=ORIGWFConstant.ApplicationDecision.
            // completeApplication(applicationDataM, userM,
            // applicationDataM.getXcmrDecision(), "", "", providerUrlWF, jndi);
            //Call WF
            
//			Remove Cancle Claim
//            applicationMDAO.updateStatus(applicationDataM);
//            cancelClaimApplicationForXUW(applicationDataM, providerUrlWF, jndi);
            
            completeApplication(applicationDataM, userM, applicationDataM.getXuwDecision(), "", "", providerUrlWF, jndi);
            
        } catch (Exception e) {
            result = false;
            message = e.toString();
            logger.error("Save application action error", e);
            throw new EJBException("Exception in xuwSaveApplication >> ", e);
        }
        return result;
    }

    public void cancelClaimApplicationForXUW(ApplicationDataM applicationM, String providerUrlWF, String jndi) throws Exception {
//    	Remove WPS
//        ORIGWFServiceManager OrigManager = ORIGEJBService.getORIGWFServiceManagerHome(providerUrlWF, jndi);
//        logger.debug("Aiid->" + applicationM.getAiid());
//
//        //call business process
//        if (applicationM.getAiid() != null && !"".equals(applicationM.getAiid())) {
//            OrigManager.cancelClaimApplication(applicationM.getAiid());
//            //Update application log
//            OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
//            logMDAO.createModelOrigApplicationLogM(getApplicationLogDataMForCancelClaimForXUW(applicationM));
//        }
    	
    	this.connWorkFlow = getWorkFlowConnection();    	

	 	logger.debug("...[ cancelClaimApplicationForXUW ].. Connection >>> "+this.connWorkFlow);
	 	
	 	WorkflowResponse response = BpmProxyService.getBpmProxyModule().CancleClaimFlow(applicationM, "", "",this.connWorkFlow);
	 	
	 	logger.debug("...[ cancelClaimApplicationForXUW ].. response code ->" + response.getResultCode());
	 	logger.debug("...[ cancelClaimApplicationForXUW ].. response desc ->" + response.getResultDesc());
	 	
	 	 if(ResultCodeConstant.RESULT_CODE_SUCCESS.equalsIgnoreCase(response.getResultCode())){	
	           OrigApplicationLogMDAO logMDAO = ORIGDAOFactory.getOrigApplicationLogMDAO();
	           logMDAO.createModelOrigApplicationLogM(getApplicationLogDataMForCancelClaimForXUW(applicationM));
	 	 }
    	
    	
    }

    private ApplicationLogDataM getApplicationLogDataMForCancelClaimForXUW(ApplicationDataM applicationDataM) {
        ApplicationLogDataM applicationLogDataM = new ApplicationLogDataM();
        applicationLogDataM.setAction(applicationDataM.getAppDecision());
        applicationLogDataM.setApplicationRecordID(applicationDataM.getAppRecordID());
        applicationLogDataM.setApplicationStatus(applicationDataM.getApplicationStatus());
        applicationLogDataM.setJobState(applicationDataM.getJobState());
        applicationLogDataM.setClaimDate(applicationDataM.getClaimDate());
        logger.debug("applicationDataM.getUpdateBy() = " + applicationDataM.getUpdateBy());
        applicationLogDataM.setUpdateBy(applicationDataM.getUpdateBy());
        return applicationLogDataM;
    }
    
    public PersonalInfoDataM loadModelPersonal(PersonalInfoDataM prmPersonalInfoDataM){
    	try{
	    	PersonalInfoDataM personalInfoDataM = ORIGDAOFactory.getOrigApplicationCustomerMDAO().loadModelOrigApplicationCustomerM(prmPersonalInfoDataM); 	
	    	return personalInfoDataM;
    	}catch (Exception e) {
			throw new EJBException(e.getMessage());
		}
    }
    public WorkflowResponse cancleclaimByUserId(String userId){
    	try{	    			 	
    		BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
		 		return bpmWorkflow.CancleClaimByUserId(userId,getWorkFlowConnection());	
    	}catch(Exception e){
			throw new EJBException(e.getMessage());
		}
    }

	@Override
	public String LogonOrigApplication(String username){
        try{
            OrigLogOnDAO logonDAO = ORIGDAOFactory.getOrigLogOnDAO();
            return logonDAO.logonOrigApp(username);
        }catch(Exception e){
            logger.error("LogonOrigApplication error", e);
            throw new EJBException("Exception in LogonOrigApplication >> ", e);
        }
	}

	@Override
	public void LogonUserDetail(String username) {
        try{
            OrigLogOnDAO logonDAO = ORIGDAOFactory.getOrigLogOnDAO();
            logonDAO.LogonUserDetail(username);
        }catch(Exception e){
            logger.error("LogonUserDetail error", e);
            throw new EJBException("Exception in LogonUserDetail >> ", e);
        }
	}

	@Override
	public void setUserLogonFlag(String userName, String flag) {
		try{
            OrigLogOnDAO logonDAO = ORIGDAOFactory.getOrigLogOnDAO();
            logonDAO.setUserLogonFlag(userName, flag);
        }catch(Exception e){
            logger.error("setUserLogonFlag error", e);
            throw new EJBException(e.getMessage());
        }
	}
	
	@Override
	public ApplicationGroupDataM startBPMFlow(String username) throws EJBException{
		ApplicationGroupDataM appGroup = new ApplicationGroupDataM();
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		try {
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
		} catch (Exception e1) {
			throw new EJBException(e1.getMessage());
		};
		try{
			
			//Prepare data
			GeneratorManager generatorManager = ORIGServiceProxy.getGeneratorManager();
			String appNo = generatorManager.generateNextSequenceID("TEMP_APP_NO");//Temporarily use only
			String appGroupId = generatorManager.generateNextSequenceID(OrigConstant.SeqNames.ORIG_APPLICATION_GROUP_PK);
			appGroup.setApplicationGroupId(appGroupId);
			appGroup.setApplicationGroupNo(appNo);
			appGroup.setCreateBy(username);
			appGroup.setUpdateBy(username);


			//Start BPM flow
			BPMApplication app = new BPMApplication();
			app.setApplicationNo(appNo);
			app.setAppGroupId(appGroupId);
			BPMInstance instance = proxy.startNormalApplication(app);
			if(BpmProxyConstant.RestAPIResult.ERROR.equalsIgnoreCase(instance.getResultCode())){
				throw new IllegalStateException(instance.getResultDesc());
			}
			else if(instance.getPiid() == null || instance.getPiid() == 0){
				throw new IllegalStateException("Cannot get Instance ID from BPM Flow!");
			}
			appGroup.setInstantId(instance.getPiid());			

			//Start saving app
			OrigApplicationGroupDAO dao = com.eaf.orig.ulo.app.dao.ORIGDAOFactory.getApplicationGroupDAO(username);
			dao.createAppGroupToStartFlow(appGroup);//Temporarily use only
		}catch(Exception e){
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		}
		return appGroup;
	}
	
	
    
}
