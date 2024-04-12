package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM.CallerScreen;

public class FicoVerifyWebsite extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoVerifyWebsite.class);
	FicoApplication ficoApplication = new FicoApplication();
	String VALIDATION_STATUS_COMPLETED = SystemConstant.getConstant("VALIDATION_STATUS_COMPLETED");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String FICO_DECISION_POINT_DV1 =SystemConstant.getConstant("FICO_DECISION_POINT_DV1");	
	public Object processAction() {
		logger.debug("<<<<<<<<<<<<< CAll FICO FicoVerifyWebsite >>>>>>>>>>>> ");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalApplicant= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		if(!Util.empty(personalApplicant)){
			VerificationResultDataM verificationResultM = personalApplicant.getVerificationResult();
			if(!Util.empty(verificationResultM)){
				if(VALIDATION_STATUS_COMPLETED.equals(verificationResultM.getVerWebResultCode())) {
					FicoRequest ficoRequest = new FicoRequest();
					ficoRequest.setApplicationGroup(applicationGroup);
					ficoRequest.setFicoFunction(FICO_DECISION_POINT_DV1);
					ficoRequest.setRoleId(FormControl.getFormRoleId(request));
					FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest,CallerScreen.WEB_VER_POPUP);
					String ficoResponseResult = ficoResponse.getResultCode();
					logger.debug("ficoResponseResult >> "+ficoResponseResult);
					ficoApplication.setResultCode(ficoResponseResult);
					ficoApplication.setResultDesc(ficoResponse.getResultDesc());
					if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){
						String decisionAction = FicoApplicationUtil.getAppGroupActionDecision(applicationGroup);
						logger.debug("DecisionAction "+decisionAction);
						if(!Util.empty(decisionAction) && FINAL_APP_DECISION_REJECT.equals(decisionAction)){
							ficoApplication.setDecision(FINAL_APP_DECISION_REJECT);
						} 
					}
				}
			}
		}
		return ficoApplication;
	}
}
