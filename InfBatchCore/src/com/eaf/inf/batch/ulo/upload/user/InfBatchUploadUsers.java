package com.eaf.inf.batch.ulo.upload.user;

import java.util.ArrayList;
import java.util.List;

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
import com.eaf.mf.dao.exception.UploadManualFileException;
import com.eaf.ulo.cache.controller.RefreshCacheController;

public class InfBatchUploadUsers extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchUploadUsers.class);

	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException,	UploadManualFileException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM("UPLOAD_USERS_TASK");
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			
			//Refresh Cache User,Employee
			List<String> cacheNames = new ArrayList<>();
			ArrayList<String>  cacheProps = SystemConstant.getArrayListConstant("REFRESH_CACHE_MS024");
			for(String cacheProp : cacheProps){
				cacheNames.add(cacheProp);
			}
			RefreshCacheController.execute(cacheNames);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
}
