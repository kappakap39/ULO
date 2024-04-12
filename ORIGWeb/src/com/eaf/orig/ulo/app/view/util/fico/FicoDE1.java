package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class FicoDE1 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoDE1.class);
	String FICO_DECISION_POINT_DE1 =SystemConstant.getConstant("FICO_DECISION_POINT_DE1");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String FINAL_APP_DECISION_APPROVE = SystemConstant.getConstant("FINAL_APP_DECISION_APPROVE");
	String JOBSTATE_REQUIRE_VERIFY_INCOME = SystemConstant.getConstant("JOBSTATE_REQUIRE_VERIFY_INCOME"); 
	public Object processAction() {
		logger.debug("FicoDE1.processAction()..");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FicoApplication ficoApplication = new FicoApplication();
		String jobState = applicationGroup.getJobState();
		logger.debug("jobState >> "+jobState);
		if(!SystemConstant.lookup("JOBSTATE_REQUIRE_VERIFY_INCOME",jobState)){
			FicoRequest ficoRequest = new FicoRequest();
				ficoRequest.setApplicationGroup(applicationGroup);
				ficoRequest.setFicoFunction(FICO_DECISION_POINT_DE1);
				ficoRequest.setRoleId(FormControl.getFormRoleId(request));
			FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest);		
			String ficoResponseResult = ficoResponse.getResultCode();
			logger.debug("ficoResponseResult >> "+ficoResponseResult);
			ficoApplication.setResultCode(ficoResponseResult);	
			ficoApplication.setResultDesc(ficoResponse.getResultDesc());
			if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){
				processFicoAction(ficoApplication,applicationGroup);
			}
		}
		return ficoApplication;
	}
	private void processFicoAction(FicoApplication ficoApplication,ApplicationGroupDataM applicationGroup){
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		ficoApplication.setFicoId(FICO_DECISION_POINT_DE1);		
		logger.debug("FICO_DECISION_POINT_DE1 >> "+FICO_DECISION_POINT_DE1);
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
			if(MConstant.FLAG_Y.equals(REQUIRE_INCOME_FLAG)){
				String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
				String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
				logger.debug("BPM_HOST >> "+BPM_HOST);
				logger.debug("BPM_PORT >> "+BPM_PORT);	
				try{
					Integer instanceId = applicationGroup.getInstantId();
					logger.debug("instanceId >> "+instanceId);
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
						ficoApplication.setIncomeScreenFlag(MConstant.FLAG_Y);
					}else{
						ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
					}
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
					ficoApplication.setResultDesc(e.getLocalizedMessage());
				}
				applicationGroup.setDecisionAction(null);
			}
		}
	}
}
