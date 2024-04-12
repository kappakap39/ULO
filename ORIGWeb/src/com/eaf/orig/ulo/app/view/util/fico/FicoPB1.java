package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;

public class FicoPB1 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoPB1.class);
	String FICO_DECISION_POINT_PB1 =SystemConstant.getConstant("FICO_DECISION_POINT_PB1");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	public Object processAction() {
		logger.debug("FicoPB1.processAction()..");
		FicoApplication ficoApplication = new FicoApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		FicoRequest ficoRequest = new FicoRequest();
		ficoRequest.setApplicationGroup(applicationGroup);
		ficoRequest.setFicoFunction(FICO_DECISION_POINT_PB1);	
		ficoRequest.setRoleId(FormControl.getFormRoleId(request));
		FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest);
		String ficoResponseResult = ficoResponse.getResultCode();
		logger.debug("ficoResponseResult >> "+ficoResponseResult);
		ficoApplication.setResultCode(ficoResponseResult);	
		ficoApplication.setResultDesc(ficoResponse.getResultDesc());
		if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){
			ficoApplication.setFicoId(FICO_DECISION_POINT_PB1);
			logger.debug("FICO_DECISION_POINT_PB1 >> "+FICO_DECISION_POINT_PB1);
			logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());	
		}
		return ficoApplication;
	}
}
