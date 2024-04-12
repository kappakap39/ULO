package com.eaf.inf.batch.ulo.letter.approve;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.letter.approve.dao.ApproveLetterFactory;
import com.eaf.inf.batch.ulo.letter.approve.model.ApproveLetterDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;


public class ApproveLetterTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(ApproveLetterTask.class);
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try{
			ArrayList<ApproveLetterDataM> approveLetters = ApproveLetterFactory.getApproveLetterDAO().getApproveLetterData();
			for(ApproveLetterDataM approveLetter : approveLetters){
				TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(approveLetter.getApplicationRecordId());
				taskObject.setObject(approveLetter);
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
			ApproveLetterDataM approveLetter = (ApproveLetterDataM)taskObject.getObject();
			
			InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
				infBatchLog.setApplicationRecordId(approveLetter.getApplicationRecordId());
				infBatchLog.setInterfaceCode(approveLetter.getModuleId());
				infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
				infBatchLog.setRefId(approveLetter.getLetterNo());
			taskExecute.setResponseObject(approveLetter);
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
