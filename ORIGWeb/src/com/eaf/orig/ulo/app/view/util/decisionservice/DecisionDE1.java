package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.ibm.websphere.ce.cm.DuplicateKeyException;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class DecisionDE1 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionDE1.class);
	String DECISION_SERVICE_POINT_DE1 =SystemConstant.getConstant("DECISION_SERVICE_POINT_DE1");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String FINAL_APP_DECISION_APPROVE = SystemConstant.getConstant("FINAL_APP_DECISION_APPROVE");
	String JOBSTATE_REQUIRE_VERIFY_INCOME = SystemConstant.getConstant("JOBSTATE_REQUIRE_VERIFY_INCOME"); 
	String FRAUD_EXCEPTION_ROLE = SystemConstant.getConstant("FRAUD_EXCEPTION_ROLE");
	String WIP_JOBSTATE_FRAUD = SystemConfig.getGeneralParam("WIP_JOBSTATE_FRAUD");
	String DUPLICATE_APP_EXCEPTION_ROLE = SystemConfig.getGeneralParam("DUPLICATE_APP_EXCEPTION_ROLE");
	String WIP_JOBSTATE_DUPLICATE_APP = SystemConfig.getGeneralParam("WIP_JOBSTATE_DUPLICATE_APP");
	String WIP_JOBSTATE_CANCEL = SystemConfig.getGeneralParam("WIP_JOBSTATE_CANCEL");
	String BUTTON_ACTION_SAVE = SystemConstant.getConstant("BUTTON_ACTION_SAVE");
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	String BUTTON_ACTION_CLOSE = SystemConstant.getConstant("BUTTON_ACTION_CLOSE");
	
	//KPL Additional 
	//[Add getRecommendLoanAmt - getRequestLoanAmt Comparing]
	private String PRODUCT_K_PERSONAL_LOAN =SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	private String RECOMMEND_DECISION_APPROVED =SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	private String VALIDATION_STATUS_REQUIRED = SystemConstant.getConstant("VALIDATION_STATUS_REQUIRED");
	//[Add more constant]
	String SEND_TO_FU_DECISION = SystemConstant.getConstant("SEND_TO_FU_DECISION");
	String DECISION_SERVICE_REJECTED = SystemConstant.getConstant("DECISION_SERVICE_REJECTED");	
	String DECISION_SERVICE_APPPROVE = SystemConstant.getConstant("DECISION_SERVICE_APPPROVE");	
	String DECISION_SERVICE_PROCEED = SystemConstant.getConstant("DECISION_SERVICE_PROCEED");
	
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	private ArrayList<String> WIP_JOBSTATE_DE2 = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_DE2");
	private ArrayList<String> JOB_STATE_DE2_APPROVE_SUBMIT = SystemConfig.getArrayListGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
	private ArrayList<String> JOB_STATE_EAPP_SYSTEM = SystemConfig.getArrayListGeneralParam("JOB_STATE_EAPP_SYSTEM");
	private ArrayList<String> JOB_STATE_FULL_FRAUD = SystemConfig.getArrayListGeneralParam("JOB_STATE_FULL_FRAUD");
	private ArrayList<String> WIP_JOBSTATE_CA = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_CA");
	private ArrayList<String> WIP_JOBSTATE_VT = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_VT");
	private ArrayList<String> JOB_STATE_DV_SAVING_PLUS = SystemConfig.getArrayListGeneralParam("JOB_STATE_DV_SAVING_PLUS");
	private ArrayList<String> WIP_JOBSTATE_FRAUD_LIST = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_FRAUD");
	private ArrayList<String> JOB_STATE_KEC_LOWINCOME = SystemConfig.getArrayListGeneralParam("JOB_STATE_KEC_LOWINCOME");
	private String CJD_SOURCE = SystemConfig.getGeneralParam("CJD_SOURCE");
	
	private String ERROR_SUBMIT_CLOSE_INTERVAL_1 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_1");
	private String ERROR_SUBMIT_CLOSE_INTERVAL_2 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_2");
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");

	public Object processAction() {
		logger.debug("decision processDE1 processAction() ");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		DecisionApplication decisionApplication = new DecisionApplication();
		decisionApplication.setDecisionId(DECISION_SERVICE_POINT_DE1);
		String jobState = applicationGroup.getJobState();
		String buttonAction = request.getParameter("buttonAction");
		String BUTTON_ACTION = "BUTTON_ACTION";
		try {
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction)) {
				String appGroupNo = applicationGroup.getApplicationGroupNo();
				String idNo = "";
				ArrayList<String> products = applicationGroup.getProducts();
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				
				logger.debug("appGroupNo = " + appGroupNo);
				logger.debug("idNo = " + idNo);
				logger.debug("products = " + products.toString());
				
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteOldSubmitIATimestamp(appGroupNo, idNo, SUBMIT_IA_BLOCK_TIME);
				try {	
					utilDao.insertSubmitIATimestamp(appGroupNo, idNo);

					ArrayList<String> jobStates = new ArrayList<>();
					jobStates.addAll(WIP_JOBSTATE_DE2);
					jobStates.addAll(JOB_STATE_DE2_APPROVE_SUBMIT);
					jobStates.addAll(JOB_STATE_EAPP_SYSTEM);
					jobStates.addAll(JOB_STATE_FULL_FRAUD);
					jobStates.addAll(WIP_JOBSTATE_CA);
					jobStates.addAll(WIP_JOBSTATE_VT);
					jobStates.addAll(JOB_STATE_DV_SAVING_PLUS);
					jobStates.addAll(WIP_JOBSTATE_FRAUD_LIST);
					jobStates.addAll(JOB_STATE_KEC_LOWINCOME);
					if(utilDao.isAppWaitForApprove(idNo, appGroupNo, jobStates, products, CJD_SOURCE)) {
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(ERROR_SUBMIT_APP_WAIT_FOR_APPROVE);
						DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
						return decisionApplication;
					}
				} catch(Exception e) {
					if(e instanceof DuplicateKeyException) {
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(ERROR_SUBMIT_CLOSE_INTERVAL_1 + SUBMIT_IA_BLOCK_TIME + ERROR_SUBMIT_CLOSE_INTERVAL_2);
						return decisionApplication;
					} else {
						throw e;
					}
				}
			}
			
			logger.debug("jobState >> "+jobState);
			logger.debug("buttonAction >> "+buttonAction);
			if(!SystemConstant.lookup("JOBSTATE_REQUIRE_VERIFY_INCOME",jobState)){
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
				requestDecision.setApplicationGroup(applicationGroup);
				requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DE1);
				requestDecision.setRoleId(FormControl.getFormRoleId(request));
				requestDecision.setUserId(userM.getUserName());
				String transactionId = (String)request.getSession().getAttribute("transactionId");
				requestDecision.setTransactionId(transactionId);
				DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(requestDecision);
				String responseResult = responseData.getResultCode();
				logger.debug("responseResult >> "+responseResult);
				decisionApplication.setResultCode(responseResult);	
				decisionApplication.setResultDesc(responseData.getResultDesc());
				decisionApplication.setErrorType(responseData.getErrorType());
				if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult) && !"DECISION_SERVICE_BTN".equals(buttonAction)){
					HashMap<String,String> responeStatus = processAuth(request,BUTTON_ACTION);
					String fraudFlag = responeStatus.get("fraudFlag");
					String blockedFlag = responeStatus.get("blockedFlag");
					if(MConstant.FLAG_Y.equals(fraudFlag) || MConstant.FLAG_Y.equals(blockedFlag)){
						decisionApplication.setFraudFlag(fraudFlag);
						decisionApplication.setBlockedFlag(blockedFlag);
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);
						return decisionApplication;
					}
					
					//KPL Additional : KPL DiffReq & SavingPlus Logic
					if(KPLUtil.isKPL(applicationGroup))
					{
						boolean diffRequestFlag = DecisionApplicationUtil.diffRequestFlag(applicationGroup);
						logger.debug("DecisionDE1 - diffRequestFlag : " + diffRequestFlag);
						ApplicationDataM kplApp = KPLUtil.getKPLApplication(applicationGroup);
						if(diffRequestFlag)
						{
							VerifyUtil.setVerificationResult(applicationGroup, VALIDATION_STATUS_REQUIRED);
							decisionApplication.setDiffRequestFlag(MConstant.FLAG_Y);
							VerifyUtil.VerifyResult.setRequiredVerifyCustomer(applicationGroup);
							
							if(kplApp.getRecommendDecision() != null &&
							   kplApp.getRecommendDecision().equals(DECISION_SERVICE_APPPROVE))
							{
								kplApp.setRecommendDecision(kplApp.getPreviousRecommendDecision());
								kplApp.setFinalAppDecision(DecisionServiceUtil.getFinalAppDiffReq(kplApp.getPreviousRecommendDecision()));
							}
							
							logger.debug("Diff - RecommendDecision : " + kplApp.getRecommendDecision());
							logger.debug("Diff - FinalAppDecision : " + kplApp.getFinalAppDecision());
							applicationGroup.setDecisionAction(null);
						}
						//Saving Plus Flag Logic
						else if(DECISION_SERVICE_APPPROVE.equals(kplApp.getRecommendDecision())
						   && KPLUtil.isSavingPlus(kplApp.getSavingPlusFlag()))
						{
							if(!KPLUtil.haveKbankSavingDoc(applicationGroup))
							{
								logger.debug("SavingPlusFlag checked + not have KbankSavingDoc = SEND TO PEGA");
								KPLUtil.addFollowSavingPlusDocReason(applicationGroup); //create SavingPlus follow-up detail
								applicationGroup.setDecisionAction("FUSP");
								decisionApplication.setSavingPlusFlag(MConstant.FLAG.ACTIVE);
							}
							else
							{
								//Sent to DV
								applicationGroup.setDecisionAction(DECISION_SERVICE_APPPROVE);
							}
						}
						
						//Save application
						ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
					}else if(DecisionApplicationUtil.isKEC(applicationGroup)){
										
							boolean lowIncomeFlag = DecisionApplicationUtil.foundLowIncomeFlag(applicationGroup);
							
							logger.debug("DecisionDE1 - lowIncomeFlag : " + lowIncomeFlag);
							if(lowIncomeFlag){
								VerifyUtil.setVerificationResult(applicationGroup, VALIDATION_STATUS_REQUIRED);
								VerifyUtil.VerifyResult.setRequiredVerifyCustomer(applicationGroup);
								//set decisionaction = proceed in lowincome case
								applicationGroup.setDecisionAction(DECISION_SERVICE_PROCEED);
		
								decisionApplication.setLowIncomeFlag(MConstant.FLAG_Y);
								//Save Application
								ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
							}
					}
					processDecisionAction(decisionApplication,applicationGroup);
				} else if(!DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)) {
					/* change to use method from DecisionApplicationUtil
					String idNo = "";
					if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
						idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
					} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
						idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
					}
					UtilityDAO utilDao = new UtilityDAOImpl();
					utilDao.deleteErrorSubmitIATimestamp(idNo);
					*/
					DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
				}
				if(DecisionApplicationUtil.isApplyTypeError(applicationGroup.getApplyTypeStatus())){
					decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
					decisionApplication.setResultDesc(MessageErrorUtil.getText("ERROR_APPLY_TYPE"));
				}
			}
			deleteOldSubmitDE1Timestamp(applicationGroup.getApplicationGroupNo());
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}
	private void processDecisionAction(DecisionApplication decisionApplication,ApplicationGroupDataM applicationGroup){
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		decisionApplication.setDecisionId(DECISION_SERVICE_POINT_DE1);		
		logger.debug("DECISION_SERVICE_POINT_DE1 >> "+DECISION_SERVICE_POINT_DE1);
		logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());
		logger.debug("getLastDecision >> "+applicationGroup.getLastDecision());
		String lastDecision = applicationGroup.getLastDecision();
		if(!FINAL_APP_DECISION_REJECT.equals(lastDecision) && !FINAL_APP_DECISION_APPROVE.equals(lastDecision)) {
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
			if(null == verificationResult){
				verificationResult = new VerificationResultDataM();
				personalInfo.setVerificationResult(verificationResult);
			}
						
			String REQUIRE_INCOME_FLAG = verificationResult.getRequiredVerIncomeFlag();
			logger.debug("REQUIRE_INCOME_FLAG >> "+REQUIRE_INCOME_FLAG);
			
			if(MConstant.FLAG_Y.equals(REQUIRE_INCOME_FLAG) && !rejectAllApplicationDecision(applicationGroup))
			{
				String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
				String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
				logger.debug("BPM_HOST >> "+BPM_HOST);
				logger.debug("BPM_PORT >> "+BPM_PORT);	
				try{
					Integer instanceId = applicationGroup.getInstantId();
					logger.debug("instanceId >> "+instanceId);
					ORIGForm.auditForm(request);
					ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup, userM);
					WorkflowManager workflow = new WorkflowManager(BPM_HOST,BPM_PORT);
					WorkflowResponseDataM response = workflow.updateDE1_2JobStateCaseRequiredIncomeVer(instanceId, userM.getUserName(), applicationGroup.getApplicationGroupId());
					String workflowResult = response.getResultCode();
					logger.debug("workflowResult >> "+workflowResult);
					if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResult)){
						String jobState = response.getJobState();
						String status = response.getStatus();
						logger.debug("jobState >> "+jobState);
						logger.debug("status >> "+status);
						applicationGroup.setJobState(jobState);
						applicationGroup.setApplicationStatus(status);
						decisionApplication.setIncomeScreenFlag(MConstant.FLAG_Y);
						
						//clear block idno
						/* change to use method from DecisionApplicationUtil						
  						String idNo = "";
						if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
							idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
						} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
							idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
						}
						UtilityDAO utilDao = new UtilityDAOImpl();
						utilDao.deleteErrorSubmitIATimestamp(idNo);
						*/
						DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
					}else{
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
					}
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
					decisionApplication.setResultDesc(e.getLocalizedMessage());
				}
				applicationGroup.setDecisionAction(null);
			}
		}
	}

	public HashMap<String,String> processAuth(HttpServletRequest request,String processAction) throws Exception{
		String CLAIM = "CLAIM";
		String buttonAction = request.getParameter("buttonAction");
		logger.debug("processAction : "+processAction);
		logger.debug("buttonAction : "+buttonAction);
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute(SessionControl.FlowControl);
		String roleId = flowControl.getRole();
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		logger.debug("applicationGroupId >> "+applicationGroupId);
		String fraudFlag = MConstant.FLAG_N;
		String blockedFlag = MConstant.FLAG_N;
		String cancelFlag = MConstant.FLAG_N;
		SQLQueryEngine Query = new SQLQueryEngine();
		HashMap rowResult =	Query.LoadModule("SELECT JOB_STATE FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ?",applicationGroupId);
		String jobState = SQLQueryEngine.display(rowResult,"JOB_STATE");
		logger.debug("jobState >> "+jobState);
		logger.debug("FRAUD_EXCEPTION_ROLE >> "+FRAUD_EXCEPTION_ROLE);
		logger.debug("WIP_JOBSTATE_FRAUD >> "+WIP_JOBSTATE_FRAUD);
		logger.debug("WIP_JOBSTATE_DUPLICATE_APP >> "+WIP_JOBSTATE_DUPLICATE_APP);
		logger.debug("roleId >> "+roleId);
		logger.debug("buttonAction >> "+buttonAction);
		if(!Util.empty(jobState)){
			 if(WIP_JOBSTATE_CANCEL.contains(jobState)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType()) ){
				 cancelFlag = MConstant.FLAG_Y;
				 applicationGroup.setCancelFlag(MConstant.FLAG_Y);
			 }else if(WIP_JOBSTATE_FRAUD.contains(jobState)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
				if((!FRAUD_EXCEPTION_ROLE.contains(roleId)&&(BUTTON_ACTION_SAVE.equals(buttonAction)||BUTTON_ACTION_SUBMIT.equals(buttonAction)||BUTTON_ACTION_CLOSE.equals(buttonAction)))
						|| (CLAIM.equals(processAction)&&!FRAUD_EXCEPTION_ROLE.contains(roleId))){
						fraudFlag = MConstant.FLAG_Y;
						applicationGroup.setFraudFlag(MConstant.FLAG_Y);
				}				
			 }else if(WIP_JOBSTATE_DUPLICATE_APP.contains(jobState)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){				
				if((!DUPLICATE_APP_EXCEPTION_ROLE.contains(roleId)&&(BUTTON_ACTION_SAVE.equals(buttonAction)||BUTTON_ACTION_SUBMIT.equals(buttonAction)||BUTTON_ACTION_CLOSE.equals(buttonAction)))
						|| CLAIM.equals(processAction)){
					blockedFlag= MConstant.FLAG_Y;
					applicationGroup.setBlockedFlag(MConstant.FLAG_Y);
				}				
			 } 
		}
		HashMap<String,String> hData = new HashMap<String, String>();
		hData.put("fraudFlag", fraudFlag);
		hData.put("blockedFlag", blockedFlag);
		hData.put("cancelFlag", cancelFlag);
//		Gson gson = new Gson();
//		String jsonData = gson.toJson(hData);		
		logger.debug("fraudFlag >> "+fraudFlag);
		logger.debug("blockedFlag >> "+blockedFlag);
		return hData;
	}
	
	private boolean rejectAllApplicationDecision(ApplicationGroupDataM applicationGroup){
		String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
		boolean rejectAction = false;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		int applicationSize = applications.size();
		int rejectSize = 0;
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(!Util.empty(DECISION_FINAL_DECISION_REJECT)&&!Util.empty(application.getFinalAppDecision())&&DECISION_FINAL_DECISION_REJECT.contains(application.getFinalAppDecision())){
					rejectSize++;
				}
			}
		}
		logger.debug(">>>applicationSize>>>"+applicationSize);
		logger.debug(">>>rejectCancelSize>>>"+rejectSize);
		if(applicationSize == rejectSize){
			rejectAction = true;
		}
		return rejectAction;		
	}
	
	private void deleteOldSubmitDE1Timestamp(String appGroupNo) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append(" DELETE FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			sql.append(" WHERE IDNO IN ( ");
			sql.append("         SELECT OP.IDNO ");
			sql.append("         FROM ORIG_APPLICATION_GROUP AG ");
			sql.append("         JOIN ORIG_PERSONAL_INFO OP ON AG.APPLICATION_GROUP_ID = OP.APPLICATION_GROUP_ID ");
			sql.append("         WHERE AG.APPLICATION_GROUP_ID = ? ");
			sql.append(" ) ");
			
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appGroupNo);

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
