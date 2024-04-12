package com.eaf.inf.batch.ulo.warehouse;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.warehouse.model.CreateWarehouseDataM;

public class CreateWareHouseTask implements TaskInf{
	private static transient Logger logger = Logger.getLogger(CreateWareHouseTask.class);
	String[] CREATE_WAREHOUSE_JOBSTATE_CONDITION = InfBatchProperty.getArrayInfBatchConfig("CREATE_WAREHOUSE_JOBSTATE_CONDITION");
	String 	CREATE_WAREHOUSE_FLAG = InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_FLAG");	
	String 	SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			SendApplicationWarehouseDAO dao = WarehouseFactory.getSendApplicationWarehouseDAO();
			if("ALL".equals(CREATE_WAREHOUSE_FLAG)){
				taskObjects = dao.selectAllApplicationToWarehouse();
			}else{
				taskObjects = dao.selectApplicationToWarehouse(CREATE_WAREHOUSE_JOBSTATE_CONDITION);	
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new TaskException(e.getLocalizedMessage());
		}
		logger.debug("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}	
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		String taskId = task.getTaskId();
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setResponseObject(taskObject.getObject());
			logger.debug("taskId >> "+taskId);
		
			CreateWarehouseDataM createWarehouse=(CreateWarehouseDataM)taskObject.getObject();
			
			CallDmService.creatDMService(createWarehouse.getApplicationGroupId(),createWarehouse.getApplicationGroupNo(),SYSTEM_USER_ID);	
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
