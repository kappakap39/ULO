package com.eaf.inf.batch.user.profile;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.batch.core.dao.BatchUserDetailDAO;
import com.eaf.batch.core.dao.BatchUserDetailDAOImpl;
import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public class InfBatchIASUserTask  implements TaskInf{
	private static transient Logger logger = Logger.getLogger(InfBatchUserDetailTask.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> tasks = new ArrayList<TaskObjectDataM>();
		try{
			BatchUserDetailDAO userDetailDAO = new BatchUserDetailDAOImpl();
			String USER_DATE_EXPIRE = InfBatchProperty.getGeneralParam("USER_DATE_EXPIRE");
			logger.debug("USER_DATE_EXPIRE : "+USER_DATE_EXPIRE);
			List<String> expireUsers = userDetailDAO.getexpireUsers(USER_DATE_EXPIRE);
			if(null!=expireUsers){
				for(String userId : expireUsers){
					TaskObjectDataM task = new TaskObjectDataM();
					task.setUniqueId(userId);
					tasks.add(task);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new TaskException(e);
		}
		return tasks;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			String uniqueId = taskObject.getUniqueId();
			logger.debug("userId : "+uniqueId);
			BatchUserDetailDAO userDetailDAO = new BatchUserDetailDAOImpl();
			userDetailDAO.deleteUserDetail(uniqueId);
			userDetailDAO.deleteIasUser(uniqueId);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}

}
