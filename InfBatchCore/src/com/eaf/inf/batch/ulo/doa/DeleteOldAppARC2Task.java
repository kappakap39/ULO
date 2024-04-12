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
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.ctoa.ApplicationGroupCatDataM;


public class DeleteOldAppARC2Task extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(DeleteOldAppARC2Task.class);
	DeleteOldAppDAO doaDAO = DeleteOldAppFactory.getDeleteOldAppDAO();
	String DELETE_OLD_APP_CUT_OFF = InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_CUT_OFF");
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try
		{
			//Load Catalog appGroup with ARC_STATUS = 1
			ArrayList<ApplicationGroupCatDataM> appGroupCatList = DeleteOldAppFactory.getDeleteOldAppDAO().loadOldAppToDelete();
    		
			for(ApplicationGroupCatDataM appGroupCat : appGroupCatList)
			{
				TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(appGroupCat.getApplicationGroupId());
				taskObject.setObject(appGroupCat);
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
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setUniqueId(taskObject.getUniqueId());
			ApplicationGroupCatDataM appGroupCat = (ApplicationGroupCatDataM) taskObject.getObject();
			
			//Update Arc Status to 2
			updateArcStatusComplete(appGroupCat);
			
			taskExecute.setResponseObject(appGroupCat);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e1.getLocalizedMessage());
			}
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e.getLocalizedMessage());
			}
		}
		return taskExecute;
	}
	
	private void updateArcStatusComplete(ApplicationGroupCatDataM appGroupCat) 
			throws Exception
	{
		Connection conn = InfBatchObjectDAO.getConnection();
		conn.setAutoCommit(false);
		
		ArrayList<String> appGroupIdList = new ArrayList<String>();
		
		if(		
			!Util.empty(appGroupCat.getPurgeDateBPMDBS())
			//&& !Util.empty(appGroupCat.getPurgeDateImageFile())
			//&& !Util.empty(appGroupCat.getPurgeDateIMApp())
			&& !Util.empty(appGroupCat.getPurgeDateOLData())
			&& !Util.empty(appGroupCat.getPurgeDateOrigApp())
			&& !Util.empty(appGroupCat.getPurgeDateResDB())
		)
		{appGroupIdList.add(appGroupCat.getApplicationGroupId());}
		
		try
		{
			doaDAO.updateArcStatusComplete(conn, appGroupIdList);
			conn.commit();
		}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				try 
				{
					conn.rollback();
				} 
				catch (SQLException e1) 
				{
					logger.fatal("ERROR",e1);
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
