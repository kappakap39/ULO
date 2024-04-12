package com.eaf.orig.ulo.app.view.util.fico;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;

public class FicoDE2 extends ProcessActionHelper  {
	private static transient Logger logger = Logger.getLogger(FicoDE2.class);
	String FICO_DECISION_POINT_DE2 =SystemConstant.getConstant("FICO_DECISION_POINT_DE2");
	String FICO_DECISION_POINT_FI =SystemConstant.getConstant("FICO_DECISION_POINT_FI");
	String[] JOBSTATE_REJECT = SystemConstant.getArrayConstant("JOBSTATE_REJECT");

	public Object processAction() {
		logger.debug("FicoDE2.processAction()..");
		ArrayList<String> JOBSTATE_REJECTS = new ArrayList<String>(Arrays.asList(JOBSTATE_REJECT));
		FicoApplication ficoApplication = new FicoApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String JOB_STATE = applicationGroup.getJobState();
		logger.debug(">>>JOB_STATE>>"+JOB_STATE);
		logger.debug(">>>JOBSTATE_REJECT>>"+JOBSTATE_REJECT.toString());
		try{
			if(JOBSTATE_REJECTS.contains(JOB_STATE)){
				FicoRequest ficoRequest = new FicoRequest();
					ficoRequest.setApplicationGroup(applicationGroup);
					ficoRequest.setFicoFunction(FICO_DECISION_POINT_FI);
					ficoRequest.setRoleId(FormControl.getFormRoleId(request));
				ficoApplication.setFicoId(FICO_DECISION_POINT_DE2);
				FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest);
				String ficoResponseResult = ficoResponse.getResultCode();
				logger.debug("ficoResponseResult >> "+ficoResponseResult);
				ficoApplication.setResultCode(ficoResponseResult);
				ficoApplication.setResultDesc(ficoResponse.getResultDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			ficoApplication.setResultDesc(e.getLocalizedMessage());
		}	
		return ficoApplication;
	}
}
