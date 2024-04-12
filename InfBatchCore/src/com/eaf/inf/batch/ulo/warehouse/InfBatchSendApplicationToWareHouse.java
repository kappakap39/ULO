package com.eaf.inf.batch.ulo.warehouse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.warehouse.model.CreateWarehouseDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchSendApplicationToWareHouse extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchSendApplicationToWareHouse.class);
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_COMPLETE");
	String INTERFACE_STATUS_ERROR = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_ERROR");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		ProcessTaskDataM processTask = new ProcessTaskDataM("CREATE_WAREHOUSE_TASK");
		try{
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		insertInfBatchLog(processTask.getTaskExecutes());
		return infBatchResponse;
	}
	
	private void insertInfBatchLog(ArrayList<TaskExecuteDataM> taskExecutes){
	   	Connection conn = InfBatchObjectDAO.getConnection();	
	   	try {
	   		InfDAO dao = InfFactory.getInfDAO();
	   		conn.setAutoCommit(false);
	   		if(!InfBatchUtil.empty(taskExecutes)){
				for(TaskExecuteDataM taskExecute : taskExecutes){
					CreateWarehouseDataM createWarehouse = (CreateWarehouseDataM)taskExecute.getResponseObject();
					if(!InfBatchUtil.empty(createWarehouse)){
		   				InfBatchLogDataM infBatchLogDataM = new InfBatchLogDataM();
		   					infBatchLogDataM.setInterfaceCode(createWarehouse.getInterfaceCode());
		   					infBatchLogDataM.setApplicationGroupId(createWarehouse.getApplicationGroupId());
		   					if(InfBatchConstant.ResultCode.SUCCESS.equals(taskExecute.getResultCode())){
		   						infBatchLogDataM.setInterfaceStatus(INTERFACE_STATUS_COMPLETE);
		   					}else{
		   						infBatchLogDataM.setInterfaceStatus(INTERFACE_STATUS_ERROR);
		   					}
			   				infBatchLogDataM.setRefId(createWarehouse.getApplicationGroupNo());
			   				infBatchLogDataM.setLifeCycle(createWarehouse.getMaxLifeCycle());
			   				infBatchLogDataM.setInterfaceDate(InfBatchProperty.getTimestamp());
			   				infBatchLogDataM.setCreateDate(InfBatchProperty.getTimestamp());
		   				dao.insertInfBatchLog(infBatchLogDataM, conn);
					}
				}		
	   		}
	   		conn.commit();
		}catch (Exception e) {
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
			}			
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			
		}
    }
}
