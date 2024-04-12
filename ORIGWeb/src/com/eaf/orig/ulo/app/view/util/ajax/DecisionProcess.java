package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class DecisionProcess implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(DecisionProcess.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DECISION_PROCESS);
		try{
			UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
			ORIGFormHandler origForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");	
			ApplicationGroupDataM applicationGroup = origForm.getObjectForm();
			logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());
			logger.debug("getLastDecision >> "+applicationGroup.getLastDecision());
				PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if(null == verificationResult){
					verificationResult = new VerificationResultDataM();
					personalInfo.setVerificationResult(verificationResult);
				}
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
							verificationResult.setRequiredVerIncomeFlag(MConstant.FLAG_Y);
						}
					}catch(Exception e){
						logger.fatal("ERROR ",e);
					}
					applicationGroup.setDecisionAction(null);
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
 
}
