package com.eaf.orig.ulo.control.util;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
import com.google.gson.Gson;

public class AuthorizedApplicationAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AuthorizedApplicationAction.class);
	String FRAUD_EXCEPTION_ROLE = SystemConstant.getConstant("FRAUD_EXCEPTION_ROLE");
	String WIP_JOBSTATE_FRAUD = SystemConfig.getGeneralParam("WIP_JOBSTATE_FRAUD");
	String DUPLICATE_APP_EXCEPTION_ROLE = SystemConfig.getGeneralParam("DUPLICATE_APP_EXCEPTION_ROLE");
	String WIP_JOBSTATE_DUPLICATE_APP = SystemConfig.getGeneralParam("WIP_JOBSTATE_DUPLICATE_APP");
	String WIP_JOBSTATE_CANCEL = SystemConfig.getGeneralParam("WIP_JOBSTATE_CANCEL");
	String BUTTON_ACTION_SAVE = SystemConstant.getConstant("BUTTON_ACTION_SAVE");
	String BUTTON_ACTION_CLOSE = SystemConstant.getConstant("BUTTON_ACTION_CLOSE");
	public static final String CLAIM = "CLAIM";
	public static final String BUTTON_ACTION = "BUTTON_ACTION";	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.AUTHORIZED_APPLICATION);
		try{
			String processAuthResult = processAuth(request,BUTTON_ACTION);
			return responseData.success(processAuthResult);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	public String processAuth(HttpServletRequest request,String processAction,String jobState) throws Exception{
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
		logger.debug("jobState >> "+jobState);
		logger.debug("FRAUD_EXCEPTION_ROLE >> "+FRAUD_EXCEPTION_ROLE);
		logger.debug("WIP_JOBSTATE_FRAUD >> "+WIP_JOBSTATE_FRAUD);
		logger.debug("WIP_JOBSTATE_DUPLICATE_APP >> "+WIP_JOBSTATE_DUPLICATE_APP);
		logger.debug("roleId >> "+roleId);
		logger.debug("buttonAction >> "+buttonAction);
		applicationGroup.setCallCompleteTaskFlag(MConstant.FLAG_Y);
		if(!Util.empty(jobState)){
			 if(WIP_JOBSTATE_CANCEL.contains(jobState)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType()) ){
				 cancelFlag = MConstant.FLAG_Y;
				 applicationGroup.setCancelFlag(MConstant.FLAG_Y);
				 applicationGroup.setCallCompleteTaskFlag(MConstant.FLAG_N);
			 }else if(WIP_JOBSTATE_FRAUD.contains(jobState)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){
				if((!FRAUD_EXCEPTION_ROLE.contains(roleId)&&(BUTTON_ACTION_SAVE.equals(buttonAction)||BUTTON_ACTION_CLOSE.equals(buttonAction)))
						|| (CLAIM.equals(processAction)&&!FRAUD_EXCEPTION_ROLE.contains(roleId))){
						fraudFlag = MConstant.FLAG_Y;
						applicationGroup.setFraudFlag(MConstant.FLAG_Y);
				}

			 }else if(WIP_JOBSTATE_DUPLICATE_APP.contains(jobState)&&!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",flowControl.getActionType())){				
				if((!DUPLICATE_APP_EXCEPTION_ROLE.contains(roleId)&&(BUTTON_ACTION_SAVE.equals(buttonAction)||BUTTON_ACTION_CLOSE.equals(buttonAction)))
						|| CLAIM.equals(processAction)){
					blockedFlag= MConstant.FLAG_Y;
					applicationGroup.setBlockedFlag(MConstant.FLAG_Y);
				}
				applicationGroup.setCallCompleteTaskFlag(MConstant.FLAG_N);
			 } 
		}
		HashMap<String,String> hData = new HashMap<String, String>();
		hData.put("fraudFlag", fraudFlag);
		hData.put("blockedFlag", blockedFlag);
		hData.put("cancelFlag", cancelFlag);
		Gson gson = new Gson();
		String jsonData = gson.toJson(hData);		
		logger.debug("fraudFlag >> "+fraudFlag);
		logger.debug("blockedFlag >> "+blockedFlag);
		logger.debug("callCompleteTaskFlag >> "+applicationGroup.getCallComplateTaskFlag());
		return jsonData;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String processAuth(HttpServletRequest request,String processAction) throws Exception{
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		logger.debug("applicationGroupId >> "+applicationGroupId);
		SQLQueryEngine Query = new SQLQueryEngine();
		HashMap rowResult =	Query.LoadModule("SELECT JOB_STATE FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ?",applicationGroupId);
		String jobState = SQLQueryEngine.display(rowResult,"JOB_STATE");
		return processAuth(request,processAction,jobState);
	}
}
