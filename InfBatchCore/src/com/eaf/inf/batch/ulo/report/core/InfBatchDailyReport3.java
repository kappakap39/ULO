package com.eaf.inf.batch.ulo.report.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ExecuteTaskManager;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.inf.batch.ulo.report.dao.ReportFileDAO;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;

public class InfBatchDailyReport3 extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchDailyReport2.class);
	int CORE_POOL_SIZE = Integer.parseInt(InfBatchProperty.getInfBatchConfig("DAILY_REPORT2_CORE_POOL_SIZE"));
	int MAX_POOL_SIZE = Integer.parseInt(InfBatchProperty.getInfBatchConfig("DAILY_REPORT2_MAX_POOL_SIZE"));
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		logger.debug("InfBatchDailyReport2.processAction");
		String batchId = infBatchRequest.getBatchId();
		String systemDate = infBatchRequest.getSystemDate();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			//ReportFileDAO reportFileDao = ReportFileFactory.getReportFileDAO();
			//reportFileDao.callPrepareReportData();
			ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.REPORT_TASK);
			processTask.setCorePoolSize(CORE_POOL_SIZE);
			processTask.setMaxPoolSize(MAX_POOL_SIZE);
			logger.debug("executeTask start for report ");
			ExecuteTaskManager executeTask = new ExecuteTaskManager(processTask);
				List<String> taskIds = getTaskIds(batchId);
				for(String taskId : taskIds){
					executeTask.execute(getTask(getTaskId(taskId), getGenerateType(taskId), systemDate));
				}	
			logger.debug("executeTask end for report");
			
			logger.debug("executeTask start for report sequence");
			TaskInf taskInf = null;
			TaskExecuteDataM taskExecuteDataM = null;
			String className = InfBatchProperty.getInfBatchConfig(InfBatchConstant.task.REPORT_TASK);
			List<String> taskIdsSEQ = getTaskIds(batchId+"_SEQUENCE_TASK");
			for(String taskId : taskIdsSEQ){
				try{			        		    	
					taskInf = (TaskInf)Class.forName(className).newInstance();			        		    	
				}catch(Exception e){
					logger.fatal("ERROR ",e);
				}	
				if(null != taskInf){
					taskExecuteDataM = taskInf.onTask(getTask(getTaskId(taskId), getGenerateType(taskId), systemDate));
					ArrayList<TaskExecuteDataM> taskExecutes = processTask.getTaskExecutes();
					taskExecutes.add(taskExecuteDataM);
				}
			}	
			logger.debug("executeTask end for report sequence");
			executeTask.shutdown();	
			
			logger.debug("executeTask start for report on request");
			executeTask = new ExecuteTaskManager(processTask);
			InfBatchReportOnRequest reportOnRequest = new InfBatchReportOnRequest();
				reportOnRequest.generateReportOnRequest(executeTask);
			executeTask.shutdown();
			logger.debug("executeTask end for report on request");
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
