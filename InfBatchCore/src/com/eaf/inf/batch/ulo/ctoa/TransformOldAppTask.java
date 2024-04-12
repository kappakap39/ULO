package com.eaf.inf.batch.ulo.ctoa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;


public class TransformOldAppTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(TransformOldAppTask.class);
	String CATALOG_AND_TRANSFORM_OLD_APP_MODULE_ID = InfBatchProperty.getInfBatchConfig("CATALOG_AND_TRANSFORM_OLD_APP_MODULE_ID");
	CatalogAndTransformOldAppDAO ctoaDAO = CatalogAndTransformOldAppFactory.getCatalogAndTransformOldAppDAO();
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try
		{
			logger.info("Loading applicationGroupId List to transform...");
			ArrayList<String> tbtAppGroupIdList = ctoaDAO.loadAppToTransform();
			logger.info("Trasnform task count : " + tbtAppGroupIdList.size());
			
    		for(String tbtAppGroupId : tbtAppGroupIdList)
    		{
				TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(tbtAppGroupId);
				taskObject.setObject(tbtAppGroupId);
				taskObjects.add(taskObject);
    		}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		ArrayList<String> oldAppGroupIdList = new ArrayList<String>();
		
		try
		{
			TaskObjectDataM taskObject = task.getTaskObject();
			String appGroupId = (String) taskObject.getObject();
			oldAppGroupIdList.add(appGroupId);
			
			//Catalog appGroup
			ctoaDAO.transformAppGroup(oldAppGroupIdList);
			logger.debug("Transform appGroupId " + appGroupId + "Complete...");
			
			taskExecute.setResponseObject(null);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}
		catch(Exception e)
		{
			//Update ARC_STATUS = F
			ctoaDAO.updateArcStatusSL("F", oldAppGroupIdList, e.getMessage());
			
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}

}
