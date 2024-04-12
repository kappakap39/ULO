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
import com.eaf.inf.batch.ulo.notification.model.EmailTemplateDataM;

public class NotificationNotReceiveTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(NotificationNotReceiveTask.class);
	String WARE_HOUSE_SUB_DOC_STATUS_NOT_IN_WAREHOUS= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_SUB_DOC_STATUS_NOT_IN_WAREHOUS");
	String NOTIFICATION_TYPE_NOT_RECEIVE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_NOTIFICATION_TYPE_NOT_RECEIVE");
	String NOT_RECEIVE_GROUP_CODE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_GROUP_CODE_NOT_RECEIVE");
	String PARAM_CODE_WH_WAIT_DOC_DAY= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_PARAM_CODE_WH_WAIT_DOC_DAY");
	String PARAM_CODE_WH_INTERVAL_NOT_RECEIVE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_PARAM_CODE_WH_INTERVAL_NOT_RECEIVE");
	String TEMPLATE_ID_NOT_RECEIVE= InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_TEMPLATE_ID_NOT_RECEIVE");
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects =  new ArrayList<TaskObjectDataM>();
		try {
			NotificationDAO dao = NotificationFactory.getNotificationDAO();
			EmailTemplateDataM notReceiveEmailTemplate  =  dao.getDMEmailTemplateInfo(TEMPLATE_ID_NOT_RECEIVE);
			ArrayList<DMNotificationDataM> dmNotifications =  dao.selectNotificationDM(NOTIFICATION_TYPE_NOT_RECEIVE,NOT_RECEIVE_GROUP_CODE);
			Date APPLICATION_DATE = dao.getApplicationDate();
			String WH_WAIT_DOC_DAY = dao.getDMGeneralParam(PARAM_CODE_WH_WAIT_DOC_DAY);
			String WH_INTERVAL_NOT_RECEIVE = dao.getDMGeneralParam(PARAM_CODE_WH_INTERVAL_NOT_RECEIVE);
			HashMap<String, ArrayList<DMNotificationDataM>> dmNotificationMapList = new HashMap<String, ArrayList<DMNotificationDataM>>();
			if(!InfBatchUtil.empty(dmNotifications)){
				for(DMNotificationDataM dmNotification : dmNotifications){
					logger.debug("dmNotification.getApplicationGroupId()>>>"+dmNotification.getApplicationGroupId());
					logger.debug("dmNotification.getDmId()>>>"+dmNotification.getDmId());
					logger.debug("Round>>"+dmNotification.getRoundOfNotification());
					if(dmNotification.getRoundOfNotification()==0){
						NotificationWareHouse.notificationWareHouseProcess(dmNotification,WH_WAIT_DOC_DAY,APPLICATION_DATE,dmNotification.getLastDecisionDate(),dmNotificationMapList);						
					}else if(dmNotification.getRoundOfNotification()>0){
						NotificationWareHouse.notificationWareHouseProcess(dmNotification,WH_INTERVAL_NOT_RECEIVE,APPLICATION_DATE,dmNotification.getCorresPondLogCreateDate(),dmNotificationMapList);
					}	
				}
			}
			 logger.debug("dmNotificationMapList>>"+dmNotificationMapList.size());
			ArrayList<String> sendToEmails = new ArrayList<>(dmNotificationMapList.keySet());
			if(!InfBatchUtil.empty(sendToEmails)){
				for(String sendEmail :sendToEmails){
					 logger.debug("sendEmail key not receive>>"+sendEmail);
					TaskObjectDataM taskObject = new TaskObjectDataM();
					HashMap<String, Object> hData = new HashMap<String, Object>();
					ArrayList<DMNotificationDataM> dmNotificationList = dmNotificationMapList.get(sendEmail);
					hData.put(sendEmail, dmNotificationList);
					hData.put(TEMPLATE_ID_NOT_RECEIVE, notReceiveEmailTemplate);
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
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			NotifyRequest notifyRequest = new NotifyRequest();
			notifyRequest.setRequestObject(task);
			notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION_WARE_HOUSE_NOT_RECEIVE);
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
