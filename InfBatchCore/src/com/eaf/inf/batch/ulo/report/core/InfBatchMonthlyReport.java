package com.eaf.inf.batch.ulo.report.core;

import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ExecuteTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;

public class InfBatchMonthlyReport extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchMonthlyReport.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String batchId = infBatchRequest.getBatchId();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(batchId);
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.REPORT_TASK);
			ExecuteTaskManager executeTask = new ExecuteTaskManager(processTask);
				List<String> taskIds = getTaskIds(batchId);
				for(String taskId : taskIds){
					executeTask.execute(getTask(getTaskId(taskId), getGenerateType(taskId)));
				}
			executeTask.shutdown();
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
