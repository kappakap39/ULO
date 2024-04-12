package com.eaf.inf.batch.ulo.notification.warehouse;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
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
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;

public class NotificationIncompleteTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(NotificationIncompleteTask.class);
	String NOTIFICATION_TYPE_INCOMPLETE  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_NOTIFICATION_TYPE_INCOMPLETE");
	String GROUP_CODE_INCOMPLETE  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_GROUP_CODE_INCOMPLETE");
	String PARAM_CODE_WH_WARNING_BEFORE_DUEDATE  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_PARAM_CODE_WH_WARNING_BEFORE_DUEDATE");
	String PARAM_CODE_WH_INTERVAL_INCOMPLETE_DOC  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_PARAM_CODE_WH_INTERVAL_INCOMPLETE_DOC");
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects =  new ArrayList<TaskObjectDataM>();
		try {
			NotificationDAO dao = NotificationFactory.getNotificationDAO();
			ArrayList<DMNotificationDataM> dmNotifications =  dao.selectNotificationDM(NOTIFICATION_TYPE_INCOMPLETE,GROUP_CODE_INCOMPLETE);
			Date APPLICATION_DATE = dao.getApplicationDate();
			String WH_WARNING_BEFORE_DUEDATE = dao.getDMGeneralParam(PARAM_CODE_WH_WARNING_BEFORE_DUEDATE);
			String WH_INTERVAL_INCOMPLETE_DOC = dao.getDMGeneralParam(PARAM_CODE_WH_INTERVAL_INCOMPLETE_DOC);
			HashMap<String, ArrayList<DMNotificationDataM>> dmNotificationMapList = new HashMap<String, ArrayList<DMNotificationDataM>>();
			
			logger.debug("dmNotifications>>"+dmNotifications.size());
			if(!InfBatchUtil.empty(dmNotifications)){
				for(DMNotificationDataM dmNotification : dmNotifications){
					logger.debug("dmId>>"+dmNotification.getDmId());
					logger.debug("Round>>"+dmNotification.getRoundOfNotification());
					if(dmNotification.getRoundOfNotification()==0){
						NotificationWareHouse.notificationWareHouseProcess(dmNotification,WH_WARNING_BEFORE_DUEDATE,APPLICATION_DATE,dmNotification.getLastDecisionDate(),dmNotificationMapList);						
					}else if(dmNotification.getRoundOfNotification()>0){
						NotificationWareHouse.notificationWareHouseProcess(dmNotification,WH_INTERVAL_INCOMPLETE_DOC,APPLICATION_DATE,dmNotification.getCorresPondLogCreateDate(),dmNotificationMapList);
					}	
				}
			}
			 
			ArrayList<String> sendToEmails = new ArrayList<>(dmNotificationMapList.keySet());
			if(!InfBatchUtil.empty(sendToEmails)){
				for(String sendEmail :sendToEmails){
					TaskObjectDataM taskObject = new TaskObjectDataM();
					HashMap<String, Object> hData = new HashMap<String, Object>();
					ArrayList<DMNotificationDataM> dmNotification = dmNotificationMapList.get(sendEmail);
					hData.put(sendEmail, dmNotification);
					taskObject.setObject(hData);
					taskObject.setUniqueId(sendEmail);
					taskObjects.add(taskObject);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			throw new TaskException(e.getLocalizedMessage());
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			NotifyController controller = new NotifyController();
			NotifyRequest notifyRequest = new NotifyRequest();
				notifyRequest.setRequestObject(task);
				notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION_WARE_HOUSE_INCOMPLETE);
			NotifyResponse notifyResponse = controller.notify(notifyRequest, null);
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
