package com.eaf.inf.batch.ulo.sumtables;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;

public class InfBatchSummaryTable extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchSummaryTable.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		 try {
			ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.REFRESH_SUMMARY_TABLE_TASK);
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}

}
