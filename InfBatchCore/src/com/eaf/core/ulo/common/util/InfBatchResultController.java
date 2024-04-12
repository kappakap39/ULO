package com.eaf.core.ulo.common.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.inf.batch.ulo.letter.reject.InfBatchResult;
import com.eaf.inf.batch.ulo.letter.reject.InfBatchResultHelper;

public class InfBatchResultController {
	private static transient Logger logger = Logger.getLogger(InfBatchResultController.class);
	
	public static void logResultData(InfBatchResponseDataM infBatchResponse){
		String summaryMessage = "";
		String batchType = InfBatchProperty.getBatchType(infBatchResponse.getBatchId());
		logger.debug("batchType : "+batchType);
		logger.debug("resultCode : "+infBatchResponse.getResultCode());
		logger.debug("resultDesc : "+infBatchResponse.getResultDesc());
		if(!InfBatchUtil.empty(batchType)){
			ArrayList<String> SUMMARY_LOG_MESSAGE_REPORT_BATCH_TYPE = InfBatchProperty.getListInfBatchConfig("SUMMARY_LOG_MESSAGE_REPORT_BATCH_TYPE");
			ArrayList<String> SUMMARY_LOG_MESSAGE_INTERFACE_BATCH_TYPE = InfBatchProperty.getListInfBatchConfig("SUMMARY_LOG_MESSAGE_INTERFACE_BATCH_TYPE");
			ArrayList<String> SUMMARY_LOG_MESSAGE_EMAIL_SMS_BATCH_TYPE = InfBatchProperty.getListInfBatchConfig("SUMMARY_LOG_MESSAGE_EMAIL_SMS_BATCH_TYPE");
			if(SUMMARY_LOG_MESSAGE_REPORT_BATCH_TYPE.contains(batchType)){
				if(InfBatchConstant.ResultCode.SUCCESS.equals(infBatchResponse.getResultCode())){
					summaryMessage = InfBatchProperty.getInfBatchConfig("SUMMARY_LOG_MESSAGE_REPORT_SUCCESS");
					summaryMessage = summaryMessage.replace("{BATCH_ID}", infBatchResponse.getBatchId());
					summaryMessage = summaryMessage.replace("{EXECUTE_TOTAL}", String.valueOf(infBatchResponse.getTotalTask()));
					summaryMessage = summaryMessage.replace("{EXECUTE_SUCCESS}", String.valueOf(infBatchResponse.getTotalSuccess()));
					summaryMessage = summaryMessage.replace("{EXECUTE_ERROR}", String.valueOf(infBatchResponse.getTotalFail()));
				}else{
					summaryMessage = InfBatchProperty.getInfBatchConfig("SUMMARY_LOG_MESSAGE_REPORT_FAIL");
					summaryMessage = summaryMessage.replace("{BATCH_ID}", infBatchResponse.getBatchId());
					summaryMessage = summaryMessage.replace("{RESULT_DESC}", String.valueOf(infBatchResponse.getResultDesc()));
				}
			}else if(SUMMARY_LOG_MESSAGE_INTERFACE_BATCH_TYPE.contains(batchType)){
				String RESULT_STATUS = "";
				if(InfBatchConstant.ResultCode.SUCCESS.equals(infBatchResponse.getResultCode())){
					RESULT_STATUS = InfBatchConstant.StatusDesc.SUCCESS;
				}else{
					RESULT_STATUS = InfBatchConstant.StatusDesc.FAIL;
				}
				summaryMessage = InfBatchProperty.getInfBatchConfig("SUMMARY_LOG_MESSAGE_INTERFACE");
				summaryMessage = summaryMessage.replace("{BATCH_ID}", infBatchResponse.getBatchId());
				summaryMessage = summaryMessage.replace("{RESULT_STATUS}", RESULT_STATUS);
			}else if(SUMMARY_LOG_MESSAGE_EMAIL_SMS_BATCH_TYPE.contains(batchType)){
				summaryMessage = InfBatchProperty.getInfBatchConfig("SUMMARY_LOG_MESSAGE_EMAIL_SMS_SUCCESS");
				summaryMessage = summaryMessage.replace("{BATCH_ID}", infBatchResponse.getBatchId());
				summaryMessage = summaryMessage.replace("{EXECUTE_FOUND}", String.valueOf(infBatchResponse.getTotalTask()));
				summaryMessage = summaryMessage.replace("{EXECUTE_WARNING}", String.valueOf(infBatchResponse.getTotalWarning()));
				summaryMessage = summaryMessage.replace("{EXECUTE_TOTAL}", String.valueOf(infBatchResponse.getTotalValid()));
				summaryMessage = summaryMessage.replace("{EXECUTE_SUCCESS}", String.valueOf(infBatchResponse.getTotalSuccess()));
				summaryMessage = summaryMessage.replace("{EXECUTE_ERROR}", String.valueOf(infBatchResponse.getTotalFail()));
			}
			logger.info(summaryMessage);
		}else{
			logger.error("batchType is null");
		}
	}
	
	public static void setExecuteTaskResultData(InfBatchResponseDataM infBatchResponse,ArrayList<ProcessTaskDataM> processTasks){
		int size = 0;
		int success = 0;
		int error = 0;
		int warning = 0;
		if(!InfBatchUtil.empty(processTasks)){
			for(ProcessTaskDataM processTask : processTasks){
				ArrayList<TaskExecuteDataM> taskExecutes = processTask.getTaskExecutes();
				if(!InfBatchUtil.empty(taskExecutes)){
					for(TaskExecuteDataM taskExecute : taskExecutes){
						if(InfBatchConstant.ResultCode.FAIL.equals(taskExecute.getResultCode())){
							logger.info("error taskId : "+taskExecute.getUniqueId());
							logger.info("error description : "+taskExecute.getResultDesc());
							error++;
						}else if(null!=taskExecute&&InfBatchConstant.ResultCode.WARNING.equals(taskExecute.getResultCode())){
							warning++;
							logger.info("warning description : "+taskExecute.getResultDesc());
						}else{
							success++;
						}
						size++;
					}
				}
			}
			if(size == 0){
				logger.warn("no task execute");
			}
		}else{
			logger.warn("no task execute");
		}
		infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		infBatchResponse.setTotalTask(size);
		infBatchResponse.setTotalSuccess(success);
		infBatchResponse.setTotalFail(error);
		infBatchResponse.setTotalWarning(warning);
		infBatchResponse.setTotalValid(success + error);
	}
	
	public static void setExecuteTaskResultData(InfBatchResponseDataM infBatchResponse,ProcessTaskDataM processTask){
		InfBatchResult infBatchResult = null;
		try{					
			String className = processTask.getConfigClassName();
			infBatchResult = (InfBatchResult) Class.forName(className).newInstance();
			infBatchResult.setExecuteTaskResultData(infBatchResponse, processTask);
		}catch(Exception e){
		}
		if(infBatchResult == null){
			infBatchResult = new InfBatchResultHelper();
			infBatchResult.setExecuteTaskResultData(infBatchResponse, processTask);
		}		
	}
	
	public static void setExecuteResultData(InfBatchResponseDataM infBatchResponse){
		infBatchResponse.setTotalTask(1);
		if(InfBatchConstant.ResultCode.FAIL.equals(infBatchResponse.getResultCode())){
			logger.info("error description : "+infBatchResponse.getResultDesc());
			infBatchResponse.setTotalSuccess(0);
			infBatchResponse.setTotalFail(1);
		}else{
			infBatchResponse.setTotalSuccess(1);
			infBatchResponse.setTotalFail(0);
		}
	}
}
