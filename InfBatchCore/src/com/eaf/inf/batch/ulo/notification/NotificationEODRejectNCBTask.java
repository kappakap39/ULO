package com.eaf.inf.batch.ulo.notification;

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
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationEODConfigDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.NotificationEODRequestDataM;

public class NotificationEODRejectNCBTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(NotificationEODRejectNCBTask.class);
	ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException{
		try{
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
			NotificationEODRequestDataM notificationEodRequest = notificationDAO.getNotificationEodRejectNCBRequest();
			HashMap<String,ArrayList<NotificationEODDataM>> notificationData = notificationEodRequest.getNotificationData();
			HashMap<String,ArrayList<String>> reasonData = notificationEodRequest.getReasonData();
				if(null==reasonData){
					reasonData = new HashMap<String, ArrayList<String>>();
				}
			logger.debug("reasonData : "+reasonData);
			if(!InfBatchUtil.empty(notificationData)){
				for(String eodNotificationId : notificationData.keySet()){
					logger.debug("eodNotificationId : "+eodNotificationId);
					if(InfBatchUtil.empty(eodNotificationId)) continue;
					String[] keyNames = eodNotificationId.split("_");
					String sendTo = keyNames[1];
					logger.debug("sendTo : "+sendTo);
					NotificationEODConfigDataM notificationEodConfig = new NotificationEODConfigDataM();
					notificationEodConfig.setReasonData(reasonData);
					TaskObjectDataM taskObject = new TaskObjectDataM();
						taskObject.setUniqueType(sendTo);
						taskObject.setUniqueId(eodNotificationId);
						taskObject.setObject(notificationData.get(eodNotificationId));
						taskObject.setConfiguration(notificationEodConfig);
					logger.debug("taskObject : "+taskObject);
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
			TaskObjectDataM taskObject = task.getTaskObject();
			logger.debug("taskObject : "+taskObject);
			ArrayList<NotificationEODDataM> notificationEods = (ArrayList<NotificationEODDataM>)taskObject.getObject();
			String notificationType = notificationEods.get(0).getNotificationType();
			String notifyId = NotifyConstant.Notify.NOTIFY_NOTIFICATION_EOD+"_TASK_"+notificationType;
			logger.debug("notifyId : "+notifyId);
			NotifyRequest notifyRequest = new NotifyRequest();
				notifyRequest.setRequestObject(task);
				notifyRequest.setNotifyId(notifyId); 
//				notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION_EOD);
			NotifyResponse notifyResponse = NotifyController.notify(notifyRequest, null);
			logger.debug("notifyResponse : "+notifyResponse);
			taskExecute.setUniqueId(taskObject.getUniqueId());
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
