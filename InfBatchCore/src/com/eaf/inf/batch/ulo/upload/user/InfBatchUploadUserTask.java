package com.eaf.inf.batch.ulo.upload.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.UserM;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.shared.constant.OrigConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.eaf.service.rest.model.ServiceResponse;

public class InfBatchUploadUserTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(InfBatchUploadUserTask.class);
	private static final String IAS_SERVICE_CREATE_USER_URL = SystemConfig.getProperty("IAS_SERVICE_CREATE_USER_URL");
		
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			List<UserM> userM = InfFactory.getInfDAO().loadTableImpUser();
			if(!InfBatchUtil.empty(userM)){
				for(UserM user : userM){
					TaskObjectDataM taskObject = new TaskObjectDataM();
						taskObject.setUniqueId(user.getUserName());
						taskObject.setObject(user);
					taskObjects.add(taskObject);
				}
			}
			logger.debug("taskObjects size: "+taskObjects.size());
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new TaskException(e);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			String uniqueId = taskObject.getUniqueId();
			UserM tempUser = (UserM) taskObject.getObject();
			String roleIdN = "";
			String roleIdO = "";
			logger.debug("userId : "+uniqueId);
			logger.debug("tempUser : "+tempUser);
			IASServiceRequest serviceRequest = new IASServiceRequest();
			serviceRequest.setUser(tempUser);
			serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);		
			serviceRequest.setUserName(tempUser.getUserName());
			RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
				@Override
				protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
			        if(connection instanceof HttpsURLConnection ){
			            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
							@Override
							public boolean verify(String arg0, SSLSession arg1) {
								return true;
							}
						});
			        }
					super.prepareConnection(connection, httpMethod);
				}
			});
			ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_CREATE_USER_URL
				,serviceRequest,IASServiceResponse.class);
			IASServiceResponse serviceResponse = responseEntity.getBody();
			logger.debug("serviceResponse.getResponseCode() : "+serviceResponse.getResponseCode());
			logger.debug("serviceResponse.getResponseDesc() : "+serviceResponse.getResponseDesc());
			if(ServiceResponse.Status.SUCCESS.equals(serviceResponse.getResponseCode())){
				if(tempUser.getRoles() != null && tempUser.getRoles().size() != 0) {
					Vector  vRoles = tempUser.getRoles();
					for(int i=0;i< vRoles.size();i++){
						RoleM roleM = (RoleM)vRoles.get(i);	
						roleIdN = roleM.getRoleID();
					}
				}
				
				UserM user = InfFactory.getInfDAO().loadTableUsers(tempUser.getUserName());
				RoleM role = InfFactory.getInfDAO().getROLEByUserName(tempUser.getUserName());
				roleIdO = role.getRoleID();
				
				if((roleIdO.compareTo(roleIdN)!=0) || (InfBatchConstant.ACTIVE_STATUS.ENABLED.equals(user.getStatus()) && InfBatchConstant.ACTIVE_STATUS.DISABLE.equals(tempUser.getStatus()))){
					WorkflowResponseDataM workflowResponse = processReAllocate(tempUser);
					logger.debug("workflowResponse.getResultCode() : "+workflowResponse.getResultCode());
					logger.debug("workflowResponse.getResultDesc() : "+workflowResponse.getResultDesc());
					if(InfBatchConstant.BPM_RESULT_CODE.SUCCESS.equals(workflowResponse.getResultCode())){
						taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
						//Delete IMP_USER
						InfFactory.getInfDAO().deleteTableIMP_USER(tempUser);
					}else{
						taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
						taskExecute.setResultDesc(workflowResponse.getResultDesc());
					}
				}else{
					taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
					//Delete IMP_USER
					InfFactory.getInfDAO().deleteTableIMP_USER(tempUser);
				}
				
			}else{
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(serviceResponse.getResponseDesc());
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		logger.debug("taskExecute : "+taskExecute.getResultCode());
		return taskExecute;
	}
	public WorkflowResponseDataM processReAllocate(UserM userM) throws Exception{
		WorkflowRequestDataM workflowRequest  = new WorkflowRequestDataM();
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String USER_NAME = SystemConfig.getProperty("BPM_USER_ID");
		String PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,USER_NAME,PASSWORD);	
		WorkflowResponseDataM workflowResponse = new WorkflowResponseDataM();
		RoleM role = InfFactory.getInfDAO().getROLEByUserName(userM.getUserName());
		try{
			workflowRequest.setUserId(userM.getUserName());
			workflowRequest.setActionUser(userM.getUserName());
			workflowRequest.setActionRole(role.getRoleName());
			workflowResponse = workflowManager.reAlocateTaskByUserName(workflowRequest);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return workflowResponse;
	}
}
