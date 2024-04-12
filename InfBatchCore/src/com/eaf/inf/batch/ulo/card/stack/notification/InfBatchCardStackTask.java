package com.eaf.inf.batch.ulo.card.stack.notification;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.db.control.DatabaseController;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.card.stack.manager.RunningParamStackDataManager;
import com.eaf.inf.batch.ulo.card.stack.notification.dao.RunningParamStackDAO;
import com.eaf.inf.batch.ulo.card.stack.notification.dao.RunningParamStackFactory;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackRequestDataM;

public class InfBatchCardStackTask extends InfBatchObjectDAO implements TaskInf {
	private static transient Logger logger = Logger.getLogger(InfBatchCardStackTask.class);

	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try {
			RunningParamStackDAO runningParamStackDAO = RunningParamStackFactory.getRunningParamStackDAO();
			ArrayList<RunningParamStackRequestDataM> listRunningParamStackRequestDataM = runningParamStackDAO.getCardParams();
			if (!InfBatchUtil.empty(listRunningParamStackRequestDataM)) {
				for (RunningParamStackRequestDataM runningParamStackRequestDataM : listRunningParamStackRequestDataM) {
					TaskObjectDataM taskObject = new TaskObjectDataM();
					taskObject.setUniqueId(runningParamStackRequestDataM.getParamType());
					taskObject.setObject(runningParamStackRequestDataM);
					taskObjects.add(taskObject);
				}
			}
		} catch (InfBatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try {
			DatabaseController databaseCtrl = new DatabaseController();
			TaskObjectDataM taskObjectDataM = task.getTaskObject();
			RunningParamStackRequestDataM runningParamStackRequestDataM = (RunningParamStackRequestDataM) taskObjectDataM.getObject();
			
			databaseCtrl.processControl(runningParamStackRequestDataM, new RunningParamStackDataManager());
			
			NotifyRequest notifyRequest = new NotifyRequest();
			notifyRequest.setRequestObject(task);
			notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_RUNNING_PARAM_STACK_PROCESS);
			NotifyResponse notifyResponse = NotifyController.notify(notifyRequest, null);
			taskExecute.setResultCode(notifyResponse.getStatusCode());
			taskExecute.setResultDesc(notifyResponse.getStatusDesc());
			
			
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}

}
