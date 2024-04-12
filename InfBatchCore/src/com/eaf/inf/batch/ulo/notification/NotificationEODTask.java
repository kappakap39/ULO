package com.eaf.inf.batch.ulo.notification;

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
import com.eaf.inf.batch.ulo.notification.condition.NotificationEODDataM;
import com.eaf.inf.batch.ulo.notification.config.model.NotificationEODConfigDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.NotificationEODRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class NotificationEODTask  extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(NotificationEODTask.class);
	String SEND_TO_TYPE_FIX = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_FIX");
	String SEND_TO_TYPE_RECOMMEND=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_RECOMMEND");
	String SEND_TO_TYPE_SELLER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_SELLER");
	String SEND_TO_TYPE_MANAGER=InfBatchProperty.getInfBatchConfig("NOTIFICATION_SEND_TO_TYPE_MANAGER");
	String NOTIFICATION_EMAIL_BRANCH_SUMMARY=InfBatchProperty.getInfBatchConfig("NOTIFICATION_EMAIL_TEMPLATE_ID_BRANCH_SUMMARY");
  
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException{
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
			NotificationEODRequestDataM notificationEodRequest = notificationDAO.getNotificationEodRequest();
			HashMap<String,ArrayList<NotificationEODDataM>> notificationData = notificationEodRequest.getNotificationData();
			HashMap<String, ArrayList<VCEmpInfoDataM>> managerData = notificationEodRequest.getManagerData();
			HashMap<String,ArrayList<String>> reasonData = notificationEodRequest.getReasonData();
				if(null==reasonData){
					reasonData = new HashMap<String, ArrayList<String>>();
				}
			logger.debug("reasonData : "+reasonData);
			logger.debug("managerData : "+managerData);
//			NotificationEODConfigDataM notificationEodConfig = new NotificationEODConfigDataM();
//				notificationEodConfig.setReasonData(reasonData);
//			logger.debug("notificationEodConfig : "+notificationEodConfig);
			if(!InfBatchUtil.empty(notificationData)){
				for(String eodNotificationId : notificationData.keySet()){
					logger.debug("eodNotificationId : "+eodNotificationId);
					if(InfBatchUtil.empty(eodNotificationId)) continue;
					String[] keyNames = eodNotificationId.split("_");
					String sendTo = keyNames[1];
					logger.debug("sendTo : "+sendTo);
					ArrayList<VCEmpInfoDataM> managerInfos = new ArrayList<VCEmpInfoDataM>();
					if(!InfBatchUtil.empty(managerData)){
						managerInfos = managerData.get(eodNotificationId);
						if(null==managerInfos){
							managerInfos = new ArrayList<VCEmpInfoDataM>();
						}
					}
//					NotificationEODConfigDataM notificationEodConfig = new NotificationEODConfigDataM();
//					notificationEodConfig.setReasonData(reasonData);
//					notificationEodConfig.setManagerInfos(managerInfos);
//					logger.debug("managerInfos : "+notificationEodConfig.getManagerInfos());
//					TaskObjectDataM taskObject = new TaskObjectDataM();
//						taskObject.setUniqueType(sendTo);
//						taskObject.setUniqueId(eodNotificationId);
//						taskObject.setObject(notificationData.get(eodNotificationId));
//						taskObject.setConfiguration(notificationEodConfig);
//					logger.debug("taskObject : "+taskObject);
//					taskObjects.add(taskObject);
					logger.debug("managerInfos : "+managerInfos);
					if(!InfBatchUtil.empty(managerInfos)){
						for(VCEmpInfoDataM vcEmp : managerInfos){
							NotificationEODConfigDataM notificationEodConfig = new NotificationEODConfigDataM();
								notificationEodConfig.setReasonData(reasonData);
								notificationEodConfig.setVcEmpManager(vcEmp);
							TaskObjectDataM taskObject = new TaskObjectDataM();
								taskObject.setUniqueType(sendTo);
								taskObject.setUniqueId(eodNotificationId);
								taskObject.setObject(notificationData.get(eodNotificationId));
								taskObject.setConfiguration(notificationEodConfig);
							logger.debug("taskObject : "+taskObject);
							taskObjects.add(taskObject);
						}
					}else{
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
