package com.eaf.inf.batch.ulo.notification.warehouse;

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

public class InfBatchNotificationWarehouse extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchNotificationWarehouse.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				ProcessTaskDataM incompleteTask = new ProcessTaskDataM(InfBatchConstant.task.NOTIFICATION_WARE_HOUSE_INCOMPLETE_TASK);
				processTaskManager.run(incompleteTask);
				
				ProcessTaskDataM notReceiveTask = new ProcessTaskDataM(InfBatchConstant.task.NOTIFICATION_WARE_HOUSE_NOT_RECEIVE_TASK);
				processTaskManager.run(notReceiveTask);
				
				ProcessTaskDataM returnTask = new ProcessTaskDataM(InfBatchConstant.task.NOTIFICATION_WARE_HOUSE_RETURN_TASK);
				processTaskManager.run(returnTask);
			ArrayList<ProcessTaskDataM> processTasks = new ArrayList<>();
				processTasks.add(incompleteTask);
				processTasks.add(notReceiveTask);
				processTasks.add(returnTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTasks);			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
