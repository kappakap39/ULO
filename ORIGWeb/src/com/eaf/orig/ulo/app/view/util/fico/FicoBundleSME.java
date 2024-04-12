package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM.CallerScreen;

public class FicoBundleSME extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoBundleSME.class);
	FicoApplication ficoApplication = new FicoApplication();
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	String FICO_DECISION_POINT_DV1 =SystemConstant.getConstant("FICO_DECISION_POINT_DV1");	
	public Object processAction() {
		logger.debug("<<<<<<<<<<<<< CAll FICO FicoBundleSME >>>>>>>>>>>> ");
		EntityFormHandler EntityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();		
		FicoRequest ficoRequest = new FicoRequest();
			ficoRequest.setApplicationGroup(applicationGroup);
			ficoRequest.setFicoFunction(FICO_DECISION_POINT_DV1);	
			ficoRequest.setRoleId(FormControl.getFormRoleId(request));
		FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest,CallerScreen.SME_POPUP);
		String ficoResponseResult = ficoResponse.getResultCode();
		logger.debug("ficoResponseResult >> "+ficoResponseResult);
		ficoApplication.setResultCode(ficoResponseResult);
		ficoApplication.setResultDesc(ficoResponse.getResultDesc());
		if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){
			logger.debug("DecisionAction "+applicationGroup.getDecisionAction());
			if(FINAL_APP_DECISION_REJECT.equals(applicationGroup.getDecisionAction())){
				ficoApplication.setDecision(FINAL_APP_DECISION_REJECT);
			}
		}
		return ficoApplication;
	}
}
