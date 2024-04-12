package com.eaf.inf.batch.ulo.doa;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.ctoa.ApplicationGroupCatDataM;
import com.eaf.orig.shared.service.OrigServiceLocator;


public class DeleteOldAppDB01Task extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(DeleteOldAppDB01Task.class);
	DeleteOldAppDAO doaDAO = DeleteOldAppFactory.getDeleteOldAppDAO();
	
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
		logger.info("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		Thread.currentThread().setName("DeleteOldApp" + Thread.currentThread().getId());
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		Connection conn = null;
		ArrayList<String> appGroupIdList = new ArrayList<String>();
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setUniqueId(taskObject.getUniqueId());
			ApplicationGroupCatDataM appGroupCat = (ApplicationGroupCatDataM) taskObject.getObject();
			appGroupIdList.add(appGroupCat.getApplicationGroupId());
			
			//Delete Old IIB Data
			deleteOldIIBData(appGroupCat);
			
			//Delete Old ODM Data
			deleteOldODM(appGroupCat);
			
			logger.debug("DeleteOldAppDB01 " + appGroupCat.getApplicationGroupNo() + " Done.");
			
			taskExecute.setResponseObject(null);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
			try {
				//Update ARC_STATUS = F
				conn = getConnection();
				doaDAO.updateArcStatusFail(conn, e.getMessage(), appGroupIdList);
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
	
	private void deleteOldIIBData(ApplicationGroupCatDataM appGroupCat) 
			throws Exception {
		if(Util.empty(appGroupCat.getPurgeDateOLData()))
		{
			Connection olConn = ThreadLocalConnection.getOlConn();
			Connection conn = ThreadLocalConnection.getConn();
			olConn.setAutoCommit(false);
			conn.setAutoCommit(false);
			
			ArrayList<String> applicationGroupNoList = new ArrayList<String>();
			
			applicationGroupNoList.add(appGroupCat.getApplicationGroupNo());
			
			try
			{
				//Delete old data from OL_NCB_45_DAY, OL_SERVICE_CACHE
				//doaDAO.deleteUtil1(olConn,"OL_NCB_45_DAY", "QR_NO", applicationGroupNoList);
				doaDAO.deleteUtil1(olConn,"OL_SERVICE_CACHE", "KEY1", applicationGroupNoList);
				olConn.commit();
				
				//Update ORIG_APPLICATION_GROUP_CAT.PURGE_DATE_OL_DATA
				doaDAO.updatePurgeDate(conn, "PURGE_DATE_OL_DATA", "APPLICATION_GROUP_NO" , applicationGroupNoList);
				conn.commit();
				
				logger.debug("Delete OldIIBData " + appGroupCat.getApplicationGroupNo() + " Done...");
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				try 
				{
					olConn.rollback();
					conn.rollback();
				} 
				catch (SQLException e1) 
				{
					logger.fatal("ERROR",e1);
				}
				throw new InfBatchException(e.getMessage());
			}
		}
	}
	
	private void deleteOldODM(ApplicationGroupCatDataM appGroupCat)
			throws Exception {
		if(Util.empty(appGroupCat.getPurgeDateResDB()))
		{
			//Delete Data on RESDB Schema
			Connection resDBConn = ThreadLocalConnection.getResDBConn();
			Connection conn = ThreadLocalConnection.getConn();
			resDBConn.setAutoCommit(false);
			conn.setAutoCommit(false);
			
			ArrayList<String> appGroupNoList = new ArrayList<String>();
			
			appGroupNoList.add(appGroupCat.getApplicationGroupNo());
			
			//Get EXECUTION_ID = ORIG_APP SERVICE_REQ_RESP.SERVICE_DATA + TRANSACTION_ID
			ArrayList<String> executionIdList = doaDAO.getExecutionIdList(conn, appGroupNoList);
			//logger.debug("executionIdList count = " + executionIdList.size());
			try
			{
				//Delete old data from EXECUTION_TRACES
				doaDAO.deleteUtil1(resDBConn,"EXECUTION_TRACES", "EXECUTION_ID", executionIdList, false);
				resDBConn.commit();
				
				//Update ORIG_APPLICATION_GROUP_CAT.PURGE_DATE_RESDB
				doaDAO.updatePurgeDate(conn, "PURGE_DATE_RESDB", "APPLICATION_GROUP_NO" , appGroupNoList);
				conn.commit();
				
				logger.debug("Delete OldODMData " + appGroupCat.getApplicationGroupNo() + " Done...");
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				try 
				{
					resDBConn.rollback();
					conn.rollback();
				} 
				catch (SQLException e1) 
				{
					logger.fatal("ERROR",e1);
				}
				throw new InfBatchException(e.getMessage());
			}
		}
	}
	
}
