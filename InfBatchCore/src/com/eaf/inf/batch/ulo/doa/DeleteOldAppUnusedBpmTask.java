package com.eaf.inf.batch.ulo.doa;

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
import com.eaf.inf.batch.ulo.ctoa.CatalogAndTransformOldAppDAO;
import com.eaf.inf.batch.ulo.ctoa.CatalogAndTransformOldAppFactory;
import com.eaf.orig.shared.service.OrigServiceLocator;

public class DeleteOldAppUnusedBpmTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(DeleteOldAppUnusedBpmTask.class);
	DeleteOldAppDAO doaDAO = DeleteOldAppFactory.getDeleteOldAppDAO();
	CatalogAndTransformOldAppDAO ctoaDAO = CatalogAndTransformOldAppFactory.getCatalogAndTransformOldAppDAO();
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try
		{
			//Get old BPM Instance over DELETE_OLD_APP_CUT_OFF days
			String DELETE_OLD_APP_CUT_OFF = InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_CUT_OFF");
			ArrayList<Integer> oldUnusedinstantIdList = doaDAO.loadUnusedInstantXDaysUp(Integer.parseInt(DELETE_OLD_APP_CUT_OFF));
			
			for(Integer  oldUnusedinstantId : oldUnusedinstantIdList)
			{
				TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(oldUnusedinstantId.toString());
				taskObject.setObject(oldUnusedinstantId);
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
		Connection bpmConn = null;
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setUniqueId(taskObject.getUniqueId());
			Integer oldUnusedinstantId = (Integer) taskObject.getObject();
			ArrayList<Integer> oldUnusedinstantIdList = new ArrayList<Integer>();
			oldUnusedinstantIdList.add(oldUnusedinstantId);
			
			bpmConn = InfBatchObjectDAO.getConnection(OrigServiceLocator.BPM);
			bpmConn.setAutoCommit(false);
			
			if(oldUnusedinstantIdList.size() > 0)
			{
				doaDAO.call_LSW_BPD_INSTANCE_DELETE(bpmConn, oldUnusedinstantIdList);
				bpmConn.commit();
			}
			
			taskExecute.setResponseObject(null);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
			try {
				bpmConn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e1.getLocalizedMessage());
			}
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(bpmConn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e.getLocalizedMessage());
			}
		}
		return taskExecute;
	}
}
