package com.eaf.inf.batch.ulo.warehouse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;

public class DisposeWareHouseTask extends InfBatchObjectDAO  implements TaskInf{
	private static transient Logger logger = Logger.getLogger(DisposeWareHouseTask.class);

	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try {
			 DisposeWareHouseDAO dao =  WarehouseFactory.getDisposeWareHouseDAO();
			 taskObjects = dao.selectDisposeDMDocId();
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw new TaskException(e.getLocalizedMessage());
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException {
		TaskExecuteDataM taskExcecute = new TaskExecuteDataM();
		Connection  conn =  getConnection(InfBatchServiceLocator.DM_DB);		
		try {
			TaskObjectDataM taskObject = task.getTaskObject();
			String dmId = taskObject.getUniqueId();
			conn.setAutoCommit(false);
			if(!InfBatchUtil.empty(dmId)){
				 DisposeWareHouseDAO dao =  WarehouseFactory.getDisposeWareHouseDAO();
				 dao.disposeWareHouseProcess(dmId,conn);
			}
			conn.commit();
			taskExcecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			taskExcecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExcecute.setResultDesc(e.getLocalizedMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
				taskExcecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExcecute.setResultDesc(e.getLocalizedMessage());
			}			
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				taskExcecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExcecute.setResultDesc(e.getLocalizedMessage());
			}
			
		}
		return taskExcecute;
	}
}
