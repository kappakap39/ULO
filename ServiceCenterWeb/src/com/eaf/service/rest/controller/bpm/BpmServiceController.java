package com.eaf.service.rest.controller.bpm;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.rest.model.BPMUserRequest;
import com.eaf.service.rest.model.BPMUserResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

@RestController
@RequestMapping("/service/bpm")
public class BpmServiceController {
	private static transient Logger logger = Logger.getLogger(BpmServiceController.class);
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<BPMUserResponse> addUser(@RequestBody BPMUserRequest userRequest){
		String BPM_HOST = ServiceCache.getProperty("BPM_HOST");
		String BPM_PORT = ServiceCache.getProperty("BPM_PORT");
		String BPM_USER_ID = ServiceCache.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = ServiceCache.getProperty("BPM_PASSWORD");
		logger.debug("BPM_HOST >> "+BPM_HOST);
		logger.debug("BPM_PORT >> "+BPM_PORT);
		logger.debug("BPM_USER_ID >> "+BPM_USER_ID);
		logger.debug("BPM_PASSWORD >> "+BPM_PASSWORD);
		BPMUserResponse serviceResponse = new BPMUserResponse();
		try{
			WorkflowManager manager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
				String USER_NAME = userRequest.getUsername();
			logger.debug("USER_NAME >> "+USER_NAME);
			BpmProxyConstant.BPMGroup group = null;
			WorkflowResponseDataM workflowResponse = manager.assignGroupsToUser(USER_NAME,group);
			serviceResponse.setResultCode(workflowResponse.getResultCode());
			serviceResponse.setResultDesc(workflowResponse.getResultDesc());
		}catch(Exception e){			 
			logger.fatal("ERROR",e);
			serviceResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceResponse.setResultDesc(e.getLocalizedMessage());
		}
		return ResponseEntity.ok(serviceResponse);
	}
	
	@RequestMapping(value="/closeApplicationCardlink", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<WorkflowResponseDataM> closeApplicationCardlink(@RequestBody WorkflowRequestDataM workflowRequest) throws Exception{
		String BPM_HOST = ServiceCache.getProperty("BPM_HOST");
		String BPM_PORT = ServiceCache.getProperty("BPM_PORT");
		String BPM_USER_ID = ServiceCache.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = ServiceCache.getProperty("BPM_PASSWORD");
		logger.debug("BPM_HOST >> "+BPM_HOST);
		logger.debug("BPM_PORT >> "+BPM_PORT);
		logger.debug("BPM_USER_ID >> "+BPM_USER_ID);
		logger.debug("BPM_PASSWORD >> "+BPM_PASSWORD);
		WorkflowResponseDataM workflowResponse = new WorkflowResponseDataM();
		try{
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			workflowResponse = workflowManager.completeWorkflowTask(workflowRequest,null);
			logger.debug("workflowResponse >> "+workflowResponse.getResultCode());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			workflowResponse.setResultCode(BpmProxyConstant.RestAPIResult.ERROR);
			workflowResponse.setResultDesc(e.getLocalizedMessage());
		}
		return ResponseEntity.ok(workflowResponse);
	}
	@RequestMapping(value="/removeUser", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<BPMUserResponse> removeUser(@RequestBody List<BPMUserRequest> bpmUserRequest){
		String BPM_HOST = ServiceCache.getProperty("BPM_HOST");
		String BPM_PORT = ServiceCache.getProperty("BPM_PORT");
		String BPM_USER_ID = ServiceCache.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = ServiceCache.getProperty("BPM_PASSWORD");
		logger.debug("BPM_HOST >> "+BPM_HOST);
		logger.debug("BPM_PORT >> "+BPM_PORT);
		logger.debug("BPM_USER_ID >> "+BPM_USER_ID);
		logger.debug("BPM_PASSWORD >> "+BPM_PASSWORD);
		BPMUserResponse serviceResponse = new BPMUserResponse();
		try{
			WorkflowManager manager = new WorkflowManager(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
			if(null!=bpmUserRequest){
				for(BPMUserRequest userRequest : bpmUserRequest){
					String userName = userRequest.getUsername();
					logger.debug("userName : "+userName);
					WorkflowResponseDataM workflowResponse = manager.removeUserInGroup(userName);
					serviceResponse.setResultCode(workflowResponse.getResultCode());
					serviceResponse.setResultDesc(workflowResponse.getResultDesc());
				}
			}
		}catch(Exception e){			 
			logger.fatal("ERROR",e);
			serviceResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceResponse.setResultDesc(e.getLocalizedMessage());
		}
		return ResponseEntity.ok(serviceResponse);
	}
}
