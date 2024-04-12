package com.eaf.inf.batch.ulo.doa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


public class DeleteOldAppIMGTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(DeleteOldAppIMGTask.class);
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
		logger.debug("taskObjects size : "+taskObjects.size());
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
			
			//Backup Old Image Files
			backupOldImageFile(appGroupCat);
			
			logger.debug("DeleteOldAppIMG " + appGroupCat.getApplicationGroupNo() + " Done.");
			
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
				conn.commit();
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
	
	private void backupOldImageFile(ApplicationGroupCatDataM appGroupCat) 
			throws Exception {
		String DELETE_IMG_FILE_ROOT_PATH = InfBatchProperty.getInfBatchConfig("DELETE_IMG_FILE_ROOT_PATH");
		String DELETE_IMG_FILE_BACKUP_PATH = InfBatchProperty.getInfBatchConfig("DELETE_IMG_FILE_BACKUP_PATH");
		DeleteOldAppDAO doaDAO = DeleteOldAppFactory.getDeleteOldAppDAO();
		Connection conn = InfBatchObjectDAO.getConnection();
		conn.setAutoCommit(false);
		
		ArrayList<String> appGroupIdList = new ArrayList<String>();
		
		if(Util.empty(appGroupCat.getPurgeDateImageFile()))
		{appGroupIdList.add(appGroupCat.getApplicationGroupId());}
		
		try
		{
			if(appGroupIdList.size() > 0)
			{
				Pattern pattern = Pattern.compile(".*/QR-[0-9]{5}");
				
				//Load file list from IMGPATH of table IM_IMAGE and use Java IO to delete them
				//ArrayList<String> IMImagePathList = doaDAO.getIMImageCatPathList(appGroupIdList);
				 
				ArrayList<String>  hashIdList = doaDAO.getIMImageCatPathList(appGroupIdList);
				
				//Get path from IM
				ArrayList<String>  IMImagePathList = doaDAO.getIMImagePathList(hashIdList);
				 
				for(String path : IMImagePathList)
				{
					if(!Util.empty(path))
					{
						try
						{
							logger.debug("relocate file - " + DELETE_IMG_FILE_ROOT_PATH + path);
							Matcher matcher = pattern.matcher(DELETE_IMG_FILE_ROOT_PATH + path);
							
							if (matcher.find())
							{
								Path folderToMove = Paths.get(matcher.group(0));
								Path destinationPath = Paths.get(DELETE_IMG_FILE_BACKUP_PATH + folderToMove.toString().substring(DELETE_IMG_FILE_ROOT_PATH.length()));
								//Files.createDirectories(destinationPath);
								logger.debug("folderToMove = " + folderToMove.toString());
								logger.debug("Destination path = " + destinationPath.toString());
								//Files.move(folderToMove, destinationPath, StandardCopyOption.REPLACE_EXISTING);
								Files.createSymbolicLink(folderToMove, destinationPath);
							}
							else
							{
								logger.debug("Source path is invalid - " + path);
							}
						}
						catch(Exception e)
						{
							logger.error("Fail to relocate image - " + path , e);
						}
					}
				}
				
				doaDAO.updatePurgeDate(conn, "PURGE_DATE_IMAGE_FILE", "APPLICATION_GROUP_ID" , appGroupIdList);
				conn.commit();
				logger.debug("Delete Old Image File Done...");
			}
			else
			{
				logger.debug("No eligible Old Image File to delete.");
			}
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
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
			}
		}
	}
	
}
