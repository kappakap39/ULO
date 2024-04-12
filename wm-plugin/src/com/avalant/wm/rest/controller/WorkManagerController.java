package com.avalant.wm.rest.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.avalant.wm.lookup.LookUpWMManager;
import com.avalant.wm.model.WmTask;
import com.avalant.wm.model.WmTaskData;
import com.avalant.wm.model.WorkManagerRequest;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;

@RestController
@RequestMapping("/service/work")
public class WorkManagerController {
	private static transient Logger logger = Logger.getLogger(WorkManagerController.class);
	
	private static final String DUPLICATE_APPLICATION_EXCEPTION_MESSAGE = "unique constraint (ORIG_APP.WM_TASK_U01) violated";
	
	@RequestMapping(value="/create",method={RequestMethod.POST})
    public @ResponseBody ResponseEntity<ProcessResponse> create(@RequestBody WorkManagerRequest wmRequest){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
		try{
			logger.debug("wmRequest : " + wmRequest.toString());
			
			WmTask task = new WmTask();
			task.setWmFunc(wmRequest.getWmFn());
			task.setRefId(wmRequest.getRefId());
			task.setRefCode(wmRequest.getRefCode());
//			task.setTaskData(wmRequest.getTaskData());
			WmTaskData taskData = new WmTaskData();
			taskData.setTaskData(wmRequest.getTaskData());
			task.setData(taskData);
			
			LookUpWMManager.getWmManager().saveWorkTask(task);
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			ErrorData errorData = error(e);
			if(e.getMessage().contains(DUPLICATE_APPLICATION_EXCEPTION_MESSAGE)) {
				errorData.setErrorInformation(MessageErrorUtil.getText("DUPLICATE_APPLICATION_ERROR"));				
			}
			processResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(errorData);
			processResponse.setData(e.getMessage());
		}
		return ResponseEntity.ok(processResponse);
	}
	
	@RequestMapping(value="/create2",method={RequestMethod.POST})
    public @ResponseBody ResponseEntity<ProcessResponse> create2(@ModelAttribute WorkManagerRequest wmRequest){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceConstant.Status.SUCCESS);
		try{
			logger.debug("wmRequest : " + wmRequest.toString());
			
			WmTask task = new WmTask();
			task.setWmFunc(wmRequest.getWmFn());
			task.setRefId(wmRequest.getRefId());
			task.setRefCode(wmRequest.getRefCode());
//			task.setTaskData(wmRequest.getTaskData());
			WmTaskData taskData = new WmTaskData();
			taskData.setTaskData(wmRequest.getTaskData());
			task.setData(taskData);
			
			LookUpWMManager.getWmManager().saveWorkTask(task);
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			ErrorData errorData = error(e);
			if(e.getMessage().contains(DUPLICATE_APPLICATION_EXCEPTION_MESSAGE)) {
				errorData.setErrorInformation(MessageErrorUtil.getText("DUPLICATE_APPLICATION_ERROR"));				
			}
			processResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(errorData);
			processResponse.setData(e.getMessage());
		}
		return ResponseEntity.ok(processResponse);
	}
	
	public static ErrorData error(Exception e){	
		ErrorData errorData = new ErrorData();
		errorData.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
		errorData.setErrorInformation(e.getLocalizedMessage());
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		errorData.setErrorCode(ErrorData.ErrorType.SYSTEM_ERROR);
		errorData.setErrorDesc(e.getMessage());
		return errorData;
	}
}
