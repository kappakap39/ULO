package com.eaf.inf.batch.ulo.letter.edocgen;

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
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryFactory;
import com.eaf.inf.batch.ulo.letter.history.model.LetterHistoryDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;


public class EDocGenTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(EDocGenTask.class);
	String EDOCGEN_MODULE_ID = InfBatchProperty.getInfBatchConfig("EDOCGEN_MODULE_ID");
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try{
			ArrayList<LetterHistoryDataM> letterHistorys = LetterHistoryFactory.getLetterHistoryDAO().getLetterHistoryData();
			for(LetterHistoryDataM letterHistory : letterHistorys){
				TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(letterHistory.getLetterNo());
				taskObject.setObject(letterHistory);
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
			LetterHistoryDataM letterHistory = (LetterHistoryDataM)taskObject.getObject();
			taskExecute.setResponseObject(letterHistory);
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

}
