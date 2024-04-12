package com.eaf.orig.ulo.app.view.util.fico;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.engine.NotifyDataM;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.model.fico.FicoApplication;
import com.google.gson.Gson;

public class FicoApplicationAction implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(FicoApplicationAction.class);
	public static final String FICO_IMPLEMENT_TYPE = "Fico";
	String FICO_DECISION_ACTION = SystemConstant.getConstant("FICO_DECISION_ACTION");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.FICO_APPLICATION_ACTION);
		
		String functionId = request.getParameter("functionId");
		String ficoImplementActionId = getFicoImplementActionId(functionId,request);
		logger.debug("functionId >> "+functionId);
		logger.debug("ficoImplementActionId >> "+ficoImplementActionId);
		FicoApplication ficoApplication = new FicoApplication();
		ficoApplication.setFunctionId(functionId);
		ficoApplication.setFicoImplementActionId(ficoImplementActionId);
		if(!Util.empty(ficoImplementActionId)){
			try{
				ProcessActionInf ficoProcessAction = ImplementControl.getProcessAction(FICO_IMPLEMENT_TYPE,ficoImplementActionId);
				if(null != ficoProcessAction){
					ficoProcessAction.init(request);
					Object ficoObject = ficoProcessAction.processAction();
					if(ficoObject instanceof FicoApplication){
						FicoApplication objectFicoApplication = (FicoApplication)ficoObject;
						mapFicoApplication(ficoApplication, objectFicoApplication);
						if(Util.empty(ficoApplication.getResultCode())){
							ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.NO_ACTION);
						}
					}
				}else{
					ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.NO_ACTION);
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
				ficoApplication.setResultDesc(e.getLocalizedMessage());
			}
		}else{
			ficoApplication.setResultCode(FicoApplicationUtil.ResultCode.NO_ACTION);
		}
		logger.debug("ficoApplication >> "+ficoApplication);
		ArrayList<String> successFico = new ArrayList<String>();
			successFico.add(FicoApplicationUtil.ResultCode.NO_ACTION);
			successFico.add(FicoApplicationUtil.ResultCode.SUCCESS);
		String ficoApplicationResult = ficoApplication.getResultCode();
		logger.debug("ficoApplicationResult >> "+ficoApplicationResult);
		if(!successFico.contains(ficoApplicationResult)){
			if(FicoApplicationUtil.ResultCode.BUSINESS_EXCEPTION.equals(ficoApplicationResult)){
				String errorMsg = ficoApplication.getResultDesc();
				logger.error("errorMsg >> "+errorMsg);
				ficoApplication.setErrorMsg(errorMsg);
			}else{
				NotifyDataM notify = NotifyForm.errorMsg(ficoApplication.getResultDesc());
				String errorMsg = notify.getNotifyMsg();
				logger.error("errorMsg >> "+errorMsg);
				ficoApplication.setErrorMsg(errorMsg);
			}
		}
		return responseData.success(new Gson().toJson(ficoApplication));
	}
	
	public void mapFicoApplication(FicoApplication ficoApplication,FicoApplication objectFicoApplication){
		ficoApplication.setFicoId(objectFicoApplication.getFicoId());
		ficoApplication.setDecision(objectFicoApplication.getDecision());
		ficoApplication.setIncomeScreenFlag(objectFicoApplication.getIncomeScreenFlag());
		ficoApplication.setDocCompleteFlag(objectFicoApplication.getDocCompleteFlag());
		ficoApplication.setResultCode(objectFicoApplication.getResultCode());
		ficoApplication.setResultDesc(objectFicoApplication.getResultDesc());
		ficoApplication.setDiffRequestFlag(objectFicoApplication.getDiffRequestFlag());
		ficoApplication.setCallEscalateFlag(objectFicoApplication.getCallEscalateFlag());
	}
	public String getFicoImplementActionId(String functionId,HttpServletRequest request){
		String roleId = FormControl.getFormRoleId(request);
		String ficoId = functionId;
		if(FICO_DECISION_ACTION.equals(functionId)){
			ficoId = SystemConstant.getConstant("FICO_IMPLEMENT_ACTION_"+roleId);
		}
		return ficoId;
	}
}
