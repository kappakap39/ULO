package com.eaf.orig.ulo.app.view.util.fico;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM.CallerScreen;

public class FicoFI extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(FicoFI.class);
	String FICO_DECISION_POINT_FI =SystemConstant.getConstant("FICO_DECISION_POINT_FI");
	public Object processAction() {
		logger.debug("FicoFI.processAction()..");
		FicoApplication ficoApplication = new FicoApplication();
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			String callEscalateFlag =applicationGroup.getCalledEscalateFlag();
			FicoRequest ficoRequest = new FicoRequest();
			ficoRequest.setApplicationGroup(applicationGroup);
			ficoRequest.setFicoFunction(FICO_DECISION_POINT_FI);
			ficoRequest.setRoleId(FormControl.getFormRoleId(request));
			logger.debug("applicationGroup.getCallEscalateFlag() >> "+applicationGroup.getCalledEscalateFlag());	
			FicoResponse ficoResponse = FicoApplicationUtil.requestFico(ficoRequest,MConstant.FLAG_N.equals(callEscalateFlag)?CallerScreen.CA_SUMMARY_UPDATE_OR:null);
			String ficoResponseResult = ficoResponse.getResultCode();
			logger.debug("ficoResponseResult >> "+ficoResponseResult);
			ficoApplication.setResultCode(ficoResponseResult);	
			ficoApplication.setResultDesc(ficoResponse.getResultDesc());
			if(FicoApplicationUtil.ResultCode.SUCCESS.equals(ficoResponseResult)){			
				ficoApplication.setFicoId(FICO_DECISION_POINT_FI);
				logger.debug("FICO_DECISION_POINT_FI >> "+FICO_DECISION_POINT_FI);
				logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());		
				if(MConstant.FLAG_N.equals(applicationGroup.getCalledEscalateFlag())){
					ficoApplication.setCallEscalateFlag(MConstant.FLAG_Y);
				}else{
					ficoApplication.setCallEscalateFlag(MConstant.FLAG_N);
				}
				logger.debug("ficoApplication.getCallEscalateFlag() >> "+ficoApplication.getCallEscalateFlag());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			ficoApplication.setResultDesc(e.getLocalizedMessage());
		}
		return ficoApplication;
	}
}
