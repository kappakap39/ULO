package com.eaf.inf.batch.ulo.applicationcardlink;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.Util;

public class InfBatchCloseApplicationCardLink  extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchCloseApplicationCardLink.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM("CLOSE_APPLICATION_CARD_LINK_TASK");
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
				
			//Add closeApp task for MLP (Generate text file for MLP)
			ProcessTaskDataM processTaskMLP = new ProcessTaskDataM("CLOSE_APPLICATION_CARD_LINK_TASK_MLP");
			ProcessTaskManager processTaskManagerMLP = new ProcessTaskManager();
				processTaskManagerMLP.run(processTaskMLP);
			
			//Merge task results of closeApp FLP and MLP
			if(!Util.empty(processTaskMLP.getTaskExecutes()))
			{
				for(TaskExecuteDataM mlpTask : processTaskMLP.getTaskExecutes())
				{
					processTask.put(mlpTask);
				}
			}
			
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
