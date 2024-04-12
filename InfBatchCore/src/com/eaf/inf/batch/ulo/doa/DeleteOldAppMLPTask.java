package com.eaf.inf.batch.ulo.doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.ctoa.CatalogAndTransformOldAppDAO;
import com.eaf.inf.batch.ulo.ctoa.CatalogAndTransformOldAppFactory;


public class DeleteOldAppMLPTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(DeleteOldAppMLPTask.class);
	DeleteOldAppDAO doaDAO = DeleteOldAppFactory.getDeleteOldAppDAO();
	CatalogAndTransformOldAppDAO ctoaDAO = CatalogAndTransformOldAppFactory.getCatalogAndTransformOldAppDAO();
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try
		{
			//Load list of old MLP Application
			HashMap<String,String> appGroupInfoList = DeleteOldAppFactory.getDeleteOldAppDAO().loadOldAppToDeleteMLP();
    		
			Iterator<Entry<String, String>> it = appGroupInfoList.entrySet().iterator();
		    while (it.hasNext()) 
		    {
		        Entry<String, String> pair = (Entry<String, String>)it.next();
		        
		        TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(pair.getKey().toString());
				taskObject.setObject(pair);
				taskObjects.add(taskObject);
		        
		        it.remove();
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
		try
		{
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setUniqueId(taskObject.getUniqueId());
			String appGroupId = taskObject.getUniqueId();
			
			@SuppressWarnings("unchecked")
			Entry<String, String> pair = (Entry<String, String>) taskObject.getObject();
			
			deleteOldMLPData(pair.getKey(), pair.getValue());
			
			logger.debug("DeleteOldAppMLP " + appGroupId + " Done.");
			
			taskExecute.setResponseObject(null);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
	
	private void deleteOldMLPData(String appGroupId, String appGroupNo) 
			throws Exception {
		
		if(!Util.empty(appGroupId))
		{
			Connection mlpConn = null;
			try
			{
				mlpConn = ThreadLocalConnection.getMLPConn();
				mlpConn.setAutoCommit(false);
				
				HashMap<String, PreparedStatement> psHm = ThreadLocalConnection.getConnPsHm();
				
				ArrayList<String> applicationGroupIdList = new ArrayList<String>();
				ArrayList<String> applicationGroupNoList = new ArrayList<String>();
					
					applicationGroupIdList.add(appGroupId);
					applicationGroupNoList.add(appGroupNo);
					
						ArrayList<String> personalIdList = doaDAO.selectUtilP(psHm.get("personalId"), "ORIG_PERSONAL_INFO", "PERSONAL_ID", applicationGroupIdList);
						ArrayList<String> applicationRecordIdList = doaDAO.selectUtilP(psHm.get("applicationRecordId"), "ORIG_APPLICATION", "APPLICATION_RECORD_ID", applicationGroupIdList);
						ArrayList<String> cardLinkRefNoList = doaDAO.selectUtilP(psHm.get("cardLinkRefNo"), "ORIG_APPLICATION", "CARDLINK_REF_NO", applicationGroupIdList);
						ArrayList<String> loanIdList = doaDAO.selectUtilP(psHm.get("loanId"), "ORIG_LOAN", "LOAN_ID", applicationRecordIdList);
						ArrayList<String> cardIdList = doaDAO.selectUtilP(psHm.get("cardId"), "ORIG_CARD", "CARD_ID", loanIdList);

					//Delete from leaf to root
					doaDAO.deleteUtil1_P(psHm,"NCB_INFO", personalIdList, false);
							doaDAO.deleteUtil1_P(psHm,"ORIG_WISDOM_INFO", cardIdList, false);
						doaDAO.deleteUtil1_P(psHm,"ORIG_CARD", loanIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_CARDLINK_CUSTOMER", personalIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_CARDLINK_CARD", personalIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PERSONAL_ADDRESS", personalIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PERSONAL_RELATION", personalIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_ADDITIONAL_SERVICE", personalIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PERSONAL_INFO", applicationGroupIdList, false);
					
						doaDAO.deleteUtil1_P(psHm,"ORIG_ADDITIONAL_SERVICE_MAP", loanIdList, false);
						doaDAO.deleteUtil1_P(psHm,"ORIG_LOAN_PRICING", loanIdList, false);
						doaDAO.deleteUtil1_P(psHm,"ORIG_CASH_TRANSFER", loanIdList, false);
						doaDAO.deleteUtil1_P(psHm,"ORIG_LOAN", applicationRecordIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_PAYMENT_METHOD", personalIdList, false); //ORIG_LOAN FK
					doaDAO.deleteUtil1_P(psHm,"INF_CARDLINK_RESULT", cardLinkRefNoList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION", applicationGroupIdList, false);
					
					doaDAO.deleteUtil1_P(psHm,"ORIG_SALE_INFO", applicationGroupIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_REFERENCE_PERSON", applicationGroupIdList, false);					
					doaDAO.deleteUtil1_P(psHm,"ORIG_REASON_LOG", applicationGroupIdList, false);
					doaDAO.deleteUtil1_P(psHm,"ORIG_REASON", applicationGroupIdList, false);
					
					//Delete in batch log using both key
					doaDAO.deleteUtil1_P(psHm.get("DEL_INF_BATCH_LOG_AG"),psHm.get("SEL_INF_BATCH_LOG_AG"),"INF_BATCH_LOG", applicationGroupIdList, false);
					doaDAO.deleteUtil1_P(psHm.get("DEL_INF_BATCH_LOG_AP"),psHm.get("SEL_INF_BATCH_LOG_AP"),"INF_BATCH_LOG", applicationRecordIdList, false);
					
					doaDAO.deleteUtil1_P(psHm,"SERVICE_REQ_RESP", applicationGroupNoList, false);
					
					//Last one to Delete
					doaDAO.deleteUtil1_P(psHm,"ORIG_APPLICATION_GROUP", applicationGroupIdList, false);
					
					mlpConn.commit();
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				try 
				{
					mlpConn.rollback();
					logger.fatal("ERROR ", e);
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
