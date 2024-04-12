package com.eaf.inf.batch.ulo.lotusnote;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.ulo.cache.controller.RefreshCacheController;

public class InfBatchLotusNoteDSA extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchLotusNoteDSA.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String CACHE_DSA_SALE_INFO = SystemConstant.getConstant("CACHE_DSA_SALE_INFO");
		logger.debug("CACHE_DSA_SALE_INFO : " + CACHE_DSA_SALE_INFO);
		String batchId = infBatchRequest.getBatchId();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(batchId);
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.LOTUS_NOTE_DSA_TASK);
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);

			//after success refresh this cache with new value 
			RefreshCacheController.execute(CACHE_DSA_SALE_INFO);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
