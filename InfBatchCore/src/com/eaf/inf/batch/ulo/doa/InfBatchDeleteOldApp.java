package com.eaf.inf.batch.ulo.doa;

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
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;

public class InfBatchDeleteOldApp extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchDeleteOldApp.class);
	InfDAO infDAO = InfFactory.getInfDAO();
	DeleteOldAppDAO doaDAO = DeleteOldAppFactory.getDeleteOldAppDAO();
	int corePoolSize = Integer.parseInt(InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_THREAD_CORE_POOL_SIZE"));
	int maxPoolSize = Integer.parseInt(InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_THREAD_MAX_POOL_SIZE"));
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try 
		{
			logger.info("processAction...");
			String deleteMode = infBatchRequest.getBatchId().replace("DELETE_OLD_APP_", "");
			logger.info("DeleteMode = " + deleteMode);
			
			ProcessTaskDataM processTask = null;
			logger.info("corePoolSize = " + corePoolSize);
			logger.info("maxPoolSize = " + maxPoolSize);
			 
			if("DB01".equals(deleteMode))
			{
				//Delete IIB(OL_DATA), ODM(RESDB) Data
				processTask = new ProcessTaskDataM(InfBatchConstant.task.DELETE_OLD_APP_DB01_TASK);
				processTask.setCorePoolSize(corePoolSize);
				processTask.setMaxPoolSize(maxPoolSize);
				DeleteOldAppProcessTaskManager deleteOldAppProcessTaskManager = new DeleteOldAppProcessTaskManager();
				deleteOldAppProcessTaskManager.run(processTask);
			}
			else if("DB03".equals(deleteMode))
			{
				//Backup BPM
				//logger.info("clone BPM task...");
				//doaDAO.callCloneTaskData();
				//logger.info("clone BPM task done.");
				
				//Clear Old task (Not related to target set)
				logger.info("clear Non Related Old BPM task...");
				clearNonRelatedOldBPMTask();
				logger.info("clear Non Related Old BPM task done.");
				
				//Delete ULO(ORIG_APP), BPM(BPMDBS), IM Data(IM_APP)
				processTask = new ProcessTaskDataM(InfBatchConstant.task.DELETE_OLD_APP_DB03_TASK);
				processTask.setCorePoolSize(corePoolSize);
				processTask.setMaxPoolSize(maxPoolSize);
				DeleteOldAppProcessTaskManager deleteOldAppProcessTaskManager = new DeleteOldAppProcessTaskManager();
				deleteOldAppProcessTaskManager.run(processTask);
			}
			else if("IMG".equals(deleteMode))
			{
				//Delete Old Image File
				processTask = new ProcessTaskDataM(InfBatchConstant.task.DELETE_OLD_APP_IMG_TASK);
				processTask.setCorePoolSize(corePoolSize);
				processTask.setMaxPoolSize(maxPoolSize);
				ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			}
			else if("MLP".equals(deleteMode))
			{
				//Delete Old MLP Data
				processTask = new ProcessTaskDataM(InfBatchConstant.task.DELETE_OLD_APP_MLP_TASK);
				processTask.setCorePoolSize(corePoolSize);
				processTask.setMaxPoolSize(maxPoolSize);
				DeleteOldAppProcessTaskManager deleteOldAppMLPProcessTaskManager = new DeleteOldAppProcessTaskManager();
				deleteOldAppMLPProcessTaskManager.run(processTask);
			}
			else if("ARC2".equals(deleteMode))
			{
				//Change ARC_STATUS to 2
				processTask = new ProcessTaskDataM(InfBatchConstant.task.DELETE_OLD_APP_ARC2_TASK);
				processTask.setCorePoolSize(corePoolSize);
				processTask.setMaxPoolSize(maxPoolSize);
				ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			}
			else
			{
				throw new Exception("Invalid deleteMode " + infBatchRequest.getSystemDate());
			}
			 
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public void clearNonRelatedOldBPMTask() throws Exception
	{
		ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.DELETE_OLD_APP_UNUSED_BPM_TASK);
		processTask.setCorePoolSize(corePoolSize);
		processTask.setMaxPoolSize(maxPoolSize);
		ProcessTaskManager processTaskManager = new ProcessTaskManager();
		processTaskManager.run(processTask);
	}

}
