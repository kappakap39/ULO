package com.eaf.inf.batch.ulo.eapp.fraudresult;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.avalant.wm.model.WorkManagerRequest;
import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.inf.batch.ulo.eapp.fraudresult.model.EAppFraudResultM;
import com.eaf.inf.batch.ulo.fraudresult.dao.FraudResultDAOFactory;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.ibm.ws.management.application.client.util;

public class EAppFraudResultTask extends InfBatchObjectDAO implements TaskInf {

	private static transient Logger logger = Logger.getLogger(EAppFraudResultTask.class);
	private final static String EAPP_FRAUD_RESULT_PREFIX_INPUT_NAME = InfBatchProperty.getInfBatchConfig("EAPP_FRAUD_RESULT_PREFIX_INPUT_NAME");
	private final static String EAPP_FRAUD_RESULT_FILE_TYPE = InfBatchProperty.getInfBatchConfig("EAPP_FRAUD_RESULT_FILE_TYPE");
	private final static String FILE_DECODE_TYPE_BOM = InfBatchProperty.getInfBatchConfig("FILE_DECODE_TYPE_BOM");
    private final static String defaultEncoding = InfBatchProperty.getInfBatchConfig("DEFAULT_ENCODING");
    private static final String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
    private static final String JOB_STATE_CANCELLED = SystemConfig.getGeneralParam("JOB_STATE_CANCELLED");
    
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		BufferedReader buffer =null;
		InputStreamReader reader = null;
		InputStream inputStream = null;
		try {
			String inputPath = PathUtil.getPath("EAPP_FRAUD_RESULT_INPUT_PATH");
			
			File dir = new File(inputPath);
			File[] foundFiles = dir.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.startsWith(EAPP_FRAUD_RESULT_PREFIX_INPUT_NAME);
			    }
			});
			
			for (File file : foundFiles) {
				logger.debug("file " + file);
				if(FILE_DECODE_TYPE_BOM.equals(EAPP_FRAUD_RESULT_FILE_TYPE)){
					inputStream = new FileInputStream(file);
					BOMInputStream bOMInputStream = new BOMInputStream(inputStream);
				    ByteOrderMark bom = bOMInputStream.getBOM();
				    String charsetName = bom == null ? defaultEncoding : bom.getCharsetName();
				    reader = new InputStreamReader(new BufferedInputStream(bOMInputStream), charsetName);
				}
				else{
					reader = new FileReader(file);
				}
				
				buffer =new BufferedReader(reader);
				String sReadline;
				while((sReadline=buffer.readLine())!=null){
					if(sReadline.contains("|")){		
						String[] fileData = sReadline.split("\\|");
						String applicationNo = getValue(0, fileData);
						String applicationType = getValue(1, fileData);
						String isSystemFlag = getValue(2, fileData);
						String fraudResult = getValue(3, fileData);
						String isAbleReSubmitFlag = getValue(4, fileData);
						String additionalInfo = getValue(5, fileData);
						String asOfdate = getValue(6, fileData);
						
						logger.debug("applicationNo : " + applicationNo);
						logger.debug("applicationType : " + applicationType);
						logger.debug("isSystemFlag : " + isSystemFlag);
						logger.debug("fraudResult : " + fraudResult);
						logger.debug("isAbleReSubmitFlag : " + isAbleReSubmitFlag);
						logger.debug("additionalInfo : " + additionalInfo);
						logger.debug("asOfdate : " + asOfdate);
						
						String jobState = FraudResultDAOFactory.getFraudResultDAO().selectJobStateByApplicationGroupNo(applicationNo);
						if(!InfBatchUtil.empty(jobState)){
							logger.debug("jobState : " + jobState);
						}
						if(!JOB_STATE_CANCELLED.equals(jobState)){
							EAppFraudResultM fraudResultM = new EAppFraudResultM();
							fraudResultM.setApplicationNo(applicationNo);
							fraudResultM.setFraudResult(fraudResult);
							fraudResultM.setSystemFlag(isSystemFlag);
							fraudResultM.setResubmitFlag(isAbleReSubmitFlag);
							fraudResultM.setAdditionalInfo(additionalInfo);
							fraudResultM.setAsOfDate(asOfdate);
								
							TaskObjectDataM queueObject = new TaskObjectDataM();
							queueObject.setUniqueId(applicationNo);
							queueObject.setObject(fraudResultM);
							taskObjects.add(queueObject);
						}
					}
				}
			}  
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new TaskException(e);
		}finally{
			try{
				if(null!=buffer){
					buffer.close();
				}
				if(null!=reader){
					reader.close();
				}
				if(null!=inputStream){
					inputStream.close();
				}
			}catch(Exception e2){
				logger.fatal("ERROR",e2);
			}
		}
		logger.debug("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}
	
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		String taskId = task.getTaskId();
		String WM_ACTION_FULLFRAUD = SystemConstant.getConstant("WM_ACTION_FULLFRAUD");
		String WORK_MANAGER_URL = SystemConfig.getProperty("WORK_MANAGER_URL");
		String interfaceStatus = InfBatchConstant.STATUS_COMPLETE;
    	String logMessage = "";
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setResponseObject(taskObject.getObject());
			logger.debug("taskId : "+taskId);
			EAppFraudResultM fraudResultM = (EAppFraudResultM)taskObject.getObject();
			String applicationNo = fraudResultM.getApplicationNo();
			logger.debug("applicationNo : "+applicationNo);
			if(!InfBatchUtil.empty(applicationNo)){
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
				
				String endPointUrl = WORK_MANAGER_URL;
				HttpHeaders httpHeaderReq = new HttpHeaders();
		        httpHeaderReq.setContentType(MediaType.APPLICATION_JSON);
		
		        Gson gson = new Gson();
		         
	    		WorkManagerRequest body = new WorkManagerRequest();
	    		body.setWmFn(WM_ACTION_FULLFRAUD);
	    		body.setRefCode(applicationNo);
	    		body.setTaskData(gson.toJson(fraudResultM));
	    		logger.debug("body->"+gson.toJson(body));
	    		HttpEntity<String> requestEntity = new HttpEntity<String>(gson.toJson(body),httpHeaderReq);
	    	
		    	logger.debug("endPointUrl : "+endPointUrl);	
		    	ResponseEntity<ProcessResponse> responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,ProcessResponse.class);
		    	ProcessResponse processResponse = responseEntity.getBody();
		    	logger.debug("response >> "+processResponse);
				if(ServiceResponse.Status.SUCCESS.equals(processResponse.getStatusCode())){
					taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				}
				else{
					taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
					taskExecute.setResultDesc(responseEntity.getBody().getData());
					interfaceStatus = InfBatchConstant.STATUS_ERROR;
					logMessage = responseEntity.getBody().getData();			
				}
				// Insert Inf_batch_log
				InfDAO infBatchLogDAO = InfFactory.getInfDAO();
				InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
				infBatchLog.setCreateBy(SYSTEM_USER_ID);
				infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("EAPP_FRAUD_RESULT_MODULE_ID"));
				infBatchLog.setRefId(applicationNo);
				infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
				infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
				infBatchLog.setInterfaceStatus(interfaceStatus);
				if (InfBatchConstant.STATUS_ERROR.equals( interfaceStatus ) ) {
					infBatchLog.setLogMessage( logMessage );
				}
				infBatchLogDAO.insertInfBatchLog(infBatchLog, conn);
				conn.commit();
			}else{
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc("Application No is Null.");
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
			}
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return taskExecute;
	}
		
	private String getValue(int index,String[] result){
		if(result.length<=index){
			return "";
		}
		return result[index].trim();
	}
}
