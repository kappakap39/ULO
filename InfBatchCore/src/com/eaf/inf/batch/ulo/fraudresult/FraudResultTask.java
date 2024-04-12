package com.eaf.inf.batch.ulo.fraudresult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.inf.batch.ulo.fraudresult.model.FraudResultDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.model.FullFraudResultRequestDataM;
import com.eaf.service.module.model.FullFraudResultResponseDataM;
import com.eaf.service.rest.model.ServiceResponse;


public class FraudResultTask implements TaskInf {
	private static transient Logger logger = Logger.getLogger(FraudResultTask.class);
	private static final String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	private String inputFilename = InfBatchProperty.getInfBatchConfig("FULL_FRAUD_INPUT_NAME");
	
	/**
	 * Reading fraud result file into array of TaskObjectDataM
	 */
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		FileReader reader = null;
		BufferedReader buffer =null;
		try {
			// Construct actual input file name
			inputFilename = inputFilename.replaceFirst("yyyyMMdd", InfBatchFraudResult.systemDate);
			String inputPath = PathUtil.getPath("FULL_FRAUD_INPUT_PATH");
			StringBuilder pathBuilder = new StringBuilder(inputPath).append(File.separator).append(inputFilename);
			String path = pathBuilder.toString();
			logger.debug("path>>"+path);
			
			// Read contents from input file to array of TaskObjectDataM
			reader = new FileReader(path);
			buffer =new BufferedReader(reader);
			String sReadline;
			int index = 0;
			while((sReadline=buffer.readLine())!=null){
				int countPipe = sReadline.length() - sReadline.replace("|", "").length();
				logger.debug("countPipe : "+countPipe);
				if(sReadline.contains("|")) {								
					String[] listData = sReadline.split("\\|");
					
					String applicationGroupNo = getValue(0,listData);
					String product =getValue(1,listData);
					String systemFlag =getValue(2,listData);
					String result =getValue(3,listData);
					String resubmitFlag =getValue(4,listData);
					String additionalInfo =getValue(5,listData);
					String asOfDate =getValue(6,listData);
					
					FraudResultDataM fraudResult = new FraudResultDataM();
					fraudResult.setApplicationGroupNo(applicationGroupNo);
					fraudResult.setProduct(product);
					fraudResult.setSystemFlag(systemFlag);
					fraudResult.setResult(result);
					fraudResult.setResubmitFlag(resubmitFlag);
					fraudResult.setAdditionalInfo(additionalInfo);
					fraudResult.setAsOfDate(asOfDate);
					
					TaskObjectDataM queueObject = new TaskObjectDataM();
					queueObject.setUniqueId(applicationGroupNo);
					queueObject.setObject(fraudResult);
					taskObjects.add(queueObject);
				}
				index++;
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
		java.sql.Connection conn = null;

		try{
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
			conn.setAutoCommit(false);
			
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setResponseObject(taskObject.getObject());
			logger.debug("taskId : "+taskId);
			
			// Prepare request object 
			FraudResultDataM fraudResult = (FraudResultDataM)taskObject.getObject();
			FullFraudResultRequestDataM fullFraudResultRequest = new FullFraudResultRequestDataM();
			int lifeCycle = 1;
			//Get AppGroup lifeCycle
			OrigApplicationGroupDAO origApplicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
			String appGroupId = origApplicationGroupDAO.getApplicationGroupIdByApplicationGroupNo(fraudResult.getApplicationGroupNo());
			lifeCycle = origApplicationGroupDAO.getLifeCycle(appGroupId);
			
			fullFraudResultRequest.setApplicationGroupNo(fraudResult.getApplicationGroupNo());
			fullFraudResultRequest.setResult(fraudResult.getResult());
			fullFraudResultRequest.setProduct(fraudResult.getProduct());
			fullFraudResultRequest.setAsOfDate(fraudResult.getAsOfDate());
			
			InfDAO infBatchLogDAO = InfFactory.getInfDAO();
			InfBatchLogDataM infBatchLogDataM = infBatchLogDAO.getFullFraudInfBatchLogByRefId(fraudResult.getApplicationGroupNo(), lifeCycle);
			if (null == infBatchLogDataM || !InfBatchConstant.STATUS_COMPLETE.equals(infBatchLogDataM.getInterfaceStatus())) {
				// Invoke proxy to call out
				ProcessActionResponse processResponse = requestDecision(fullFraudResultRequest);
				String resultCode = processResponse.getResultCode();						
				logger.debug("Full Fraud resultCode : "+resultCode);
				
				// Stamp transaction to INF_BATCH_LOG
				InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
				infBatchLog.setCreateBy(SYSTEM_USER_ID);
				infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("FULL_FRAUD_MODULE_ID"));
				infBatchLog.setRefId(fraudResult.getApplicationGroupNo());
				infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
				infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
				infBatchLog.setLifeCycle(lifeCycle);
				if(ServiceResponse.Status.SUCCESS.equals(resultCode))
					{infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);}
				else
					{
						infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_ERROR);
						infBatchLog.setLogMessage(processResponse.getErrorMsg());
					}
				infBatchLogDAO.insertInfBatchLog(infBatchLog, conn);
			}
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			conn.rollback();
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return taskExecute;
	}

	/**
	 * Call decision service
	 * 
	 * @param fullFraudResultRequest
	 * @return
	 */
	private ProcessActionResponse requestDecision(FullFraudResultRequestDataM fullFraudResultRequest){
		String URL = SystemConfig.getProperty("FULL_FRAUD_SERVICE_URL");
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setServiceId(ServiceConstant.ServiceId.FullFraudResult);
				serviceRequest.setUserId(SYSTEM_USER_ID);
				serviceRequest.setEndpointUrl(URL);
				serviceRequest.setUniqueId(fullFraudResultRequest.getApplicationGroupNo());
				serviceRequest.setRefId(fullFraudResultRequest.getApplicationGroupNo());
				serviceRequest.setObjectData(fullFraudResultRequest);
				
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
			if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
				FullFraudResultResponseDataM fullFraudResultResponse =(FullFraudResultResponseDataM)serivceResponse.getObjectData();
				if(ServiceResponse.Status.SUCCESS.equals(serivceResponse.getStatusCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
				}else{
					processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR+" : when get object data");
				}
			}else{
				processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				if(serivceResponse != null && serivceResponse.getErrorInfo() != null)
				{processActionResponse.setErrorMsg(serivceResponse.getErrorInfo().getErrorDesc());}
				else
				{processActionResponse.setErrorMsg(ServiceResponse.StatusDesc.ERROR);}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setErrorMsg(e.getLocalizedMessage());
		}
		return processActionResponse;
	}

	private String getValue(int index,String[] result){
		if(result.length<=index) return "";
			return result[index].trim();
	}
}

