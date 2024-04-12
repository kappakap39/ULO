package com.eaf.inf.batch.user.profile;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.mf.dao.exception.UploadManualFileException;

public class InfBatchIASUser extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchIASUser.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException,UploadManualFileException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM("PURGE_USER_INACTIVE_TASK");
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			InfBatchResultController.setExecuteResultData(infBatchResponse);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}		
		return infBatchResponse;
	}
}
