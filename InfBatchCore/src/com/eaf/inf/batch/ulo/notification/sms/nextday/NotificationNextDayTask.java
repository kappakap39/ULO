package com.eaf.inf.batch.ulo.notification.sms.nextday;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
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
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;

public class NotificationNextDayTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(NotificationNextDayTask.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
			HashMap<String,NotificationInfoDataM> notificationData  = notificationDAO.getNotificationInfoNextDay();
			if(!InfBatchUtil.empty(notificationData)){
				ArrayList<String> keyList = new ArrayList<String>(notificationData.keySet());
				for(String keyName : keyList){
					TaskObjectDataM taskObject = new TaskObjectDataM();
						taskObject.setUniqueId(keyName);
						taskObject.setObject(notificationData.get(keyName));
					taskObjects.add(taskObject);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new TaskException(e);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			NotifyRequest notifyRequest = new NotifyRequest();
				notifyRequest.setRequestObject(task);
				notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_SMS_NEXT_DAY);
			NotifyResponse notifyResponse = NotifyController.notify(notifyRequest, null);
			taskExecute.setResultCode(notifyResponse.getStatusCode());
			taskExecute.setResultDesc(notifyResponse.getStatusDesc());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
