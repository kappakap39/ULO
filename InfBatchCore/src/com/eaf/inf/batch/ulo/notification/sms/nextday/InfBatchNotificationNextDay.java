package com.eaf.inf.batch.ulo.notification.sms.nextday;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.mf.dao.exception.UploadManualFileException;

public class InfBatchNotificationNextDay extends InfBatchHelper{
	int CORE_POOL_SIZE = Integer.parseInt(InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_CORE_POOL_SIZE"));
	int MAX_POOL_SIZE = Integer.parseInt(InfBatchProperty.getInfBatchConfig("NOTIFICATION_SMS_NEXT_DAY_MAX_POOL_SIZE"));
	private static transient Logger logger = Logger.getLogger(InfBatchNotificationNextDay.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException,	UploadManualFileException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM("NOTIFICATION_SMS_NEXT_DAY_TASK");
			processTask.setCorePoolSize(CORE_POOL_SIZE);
			processTask.setMaxPoolSize(MAX_POOL_SIZE);
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
			processTaskManager.run(processTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
	 	}catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
