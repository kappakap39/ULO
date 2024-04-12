package com.eaf.inf.batch.ulo.delete.ncb;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;

public class DeleteNCBTask extends InfBatchObjectDAO implements TaskInf {
	private static transient Logger logger = Logger.getLogger(DeleteNCBTask.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			DeleteNCBDAO dao = NCBFactory.getDeleteNCBDAO();
			taskObjects = dao.selectDeleteTrackingCode();
		}catch(Exception e){
			logger.debug("ERROR ",e);
			throw new TaskException(e.getLocalizedMessage());
		}
		return taskObjects;
	}
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception{
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		TaskObjectDataM taskObject = task.getTaskObject();
		String trackingCode = taskObject.getUniqueId();
		logger.debug("trackingCode >> "+trackingCode);
		Connection conn = getConnection();
		try{
			 conn.setAutoCommit(false);
			 if(!InfBatchUtil.empty(trackingCode)){
			 	DeleteNCBDAO dao = NCBFactory.getDeleteNCBDAO();
			 	ArrayList<String> deleteAccountReportIDs = dao.selectDeleteAccountReportID(trackingCode, conn);
			 	if(!InfBatchUtil.empty(deleteAccountReportIDs)) {
			 		dao.deleteNCBAccountRuleReport(deleteAccountReportIDs, conn);
			 	}
			 	dao.deleteNCBAccountReport(trackingCode, conn);
				dao.deleteNCBDTable(trackingCode,conn);
			 }	
			 conn.commit();
			 taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
			conn.rollback();	
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e.getLocalizedMessage());
			}
		}
		return taskExecute;
	}

}
