package com.eaf.inf.batch.ulo.notification;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;

public class InfBatchNotificationEOD extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchNotificationEOD.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
//			ProcessTaskDataM processTask = new ProcessTaskDataM("NOTIFICATION_EOD_TASK");
//			ProcessTaskManager processTaskManager = new ProcessTaskManager();
//				processTaskManager.run(processTask);
//			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
			ProcessTaskDataM branchSummaryTask = new ProcessTaskDataM("NOTIFICATION_EOD_BRANCH_SUMMARY_TASK");
			ProcessTaskManager branchSummaryTaskManager = new ProcessTaskManager();
			branchSummaryTaskManager.run(branchSummaryTask);
//			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, branchSummaryTask);
			
			ProcessTaskDataM rejectNCBTask = new ProcessTaskDataM("NOTIFICATION_EOD_REJECT_NCB_TASK");
			ProcessTaskManager rejectNCBTaskManager = new ProcessTaskManager();
				rejectNCBTaskManager.run(rejectNCBTask);
//			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, rejectNCBTask);
			
			ArrayList<ProcessTaskDataM> processTasks = new ArrayList<ProcessTaskDataM>();
			processTasks.add(branchSummaryTask);
			processTasks.add(rejectNCBTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTasks);
				
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
