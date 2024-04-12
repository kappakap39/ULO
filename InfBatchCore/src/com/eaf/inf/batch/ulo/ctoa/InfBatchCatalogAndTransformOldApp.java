package com.eaf.inf.batch.ulo.ctoa;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
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
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;


public class InfBatchCatalogAndTransformOldApp extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchCatalogAndTransformOldApp.class); 
	String CATALOG_AND_TRANSFORM_OLD_APP_MODULE_ID = InfBatchProperty.getInfBatchConfig("CATALOG_AND_TRANSFORM_OLD_APP_MODULE_ID");
	String INTERFACE_STATUS_NOT_SEND = InfBatchProperty.getInfBatchConfig("INTERFACE_STATUS_NOT_SEND");
	CatalogAndTransformOldAppDAO ctoaDAO = CatalogAndTransformOldAppFactory.getCatalogAndTransformOldAppDAO();
	InfDAO infDAO = InfFactory.getInfDAO();
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException 
	{
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		String mode = infBatchRequest.getSystemDate();
		logger.info("mode = " + mode);
		
		try 
		{
			String responseString = "";
			ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.CATALOG_AND_TRANSFORM_OLD_APP_TASK);
			
			if(Util.empty(mode) || mode.equals("CAT"))
			{
				if(!ctoaDAO.isPendingJobExist())
				{
					clearBackupJsonFile();
					
					logger.info("Loading Old ApplicationGroupId List...");
					ArrayList<String> oldAppGroupIdList = ctoaDAO.loadOldAppGroupToCatalog();
					
					//Catalog old application data
					cataLogOldAppBatch(oldAppGroupIdList, infBatchResponse);
				}
				else
				{
					String pje = "Pending jobs exist - Skip catalog." ;
					logger.info(pje);
					responseString += pje;
				}
			}
			
			if(Util.empty(mode) || mode.equals("TRAN"))
			{
				//Transform old application data
				trasnformOldApp(infBatchResponse);
			}
			
			infBatchResponse.setResultDesc(responseString);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} 
		catch (Exception e) 
		{
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public void trasnformOldApp(InfBatchResponseDataM infBatchResponse) throws Exception
	{
		int corePoolSize = Integer.parseInt(InfBatchProperty.getInfBatchConfig("CATALOG_AND_TRANSFORM_OLD_APP_THREAD_CORE_POOL_SIZE"));
		int maxPoolSize = Integer.parseInt(InfBatchProperty.getInfBatchConfig("CATALOG_AND_TRANSFORM_OLD_APP_THREAD_MAX_POOL_SIZE"));
		
		logger.info("Run trasnformOldApp task...");
		ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.TRANSFORM_OLD_APP_TASK);
		processTask.setCorePoolSize(corePoolSize);
		processTask.setMaxPoolSize(maxPoolSize);
		ProcessTaskManager processTaskManager = new ProcessTaskManager();
		processTaskManager.run(processTask);
		InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
	}
	
	public void clearBackupJsonFile() throws Exception
	{
		String outputPath = InfBatchProperty.getInfBatchConfig("DELETE_OLD_APP_FILE_BACKUP_PATH");
		
		try 
		{
			FileUtils.cleanDirectory(new File(outputPath));
			logger.info("Clear directory " + outputPath + " Completed.");
		} catch (Exception e) 
		{
			logger.error("Fail to clearBackupJsonFile", e);
			throw e;
		}
	}
	
	public void cataLogOldAppBatch(ArrayList<String> appGroupIdList, InfBatchResponseDataM infBatchResponse) throws Exception{
		Connection conn = InfBatchObjectDAO.getConnection();
		InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
		
		try{
			conn.setAutoCommit(false);
			infBatchLog.setInterfaceCode(CATALOG_AND_TRANSFORM_OLD_APP_MODULE_ID);
			
			if(appGroupIdList.size() > 0)
			{
				try
				{
					//Catalog
					ctoaDAO.catalogAppGroup(appGroupIdList);
					
					//Audit InfBatchLog
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
					infBatchLog.setLogMessage("Catalog count : " + appGroupIdList.size());
				}
				catch(Exception e)
				{
					logger.fatal("ERROR ", e);
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_ERROR);
					infBatchLog.setLogMessage(e.getMessage());
				}	
			}
			else
			{
				infBatchLog.setInterfaceStatus(INTERFACE_STATUS_NOT_SEND);
				infBatchLog.setLogMessage("Not found any eligible old applications.");
			}
			
			infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
			infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
			infDAO.insertInfBatchLog(infBatchLog, conn);
			conn.commit();
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
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
