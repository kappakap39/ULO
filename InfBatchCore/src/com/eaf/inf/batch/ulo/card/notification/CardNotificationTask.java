package com.eaf.inf.batch.ulo.card.notification;

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
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.card.notification.model.CardNotificationDataM;
import com.eaf.inf.batch.ulo.card.notification.model.CardParamDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.orig.master.shared.model.RunningParamM;

public class CardNotificationTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(CardNotificationTask.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		RunningParamM runningParamM = null;
		double valueFrom;
		double valueTo;
		double valueAlert;
		double cardUsage;
		double remainder;
		String paramId = null;
		try{
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
			ArrayList<CardParamDataM> listCardParam = notificationDAO.getCardParams();
			HashMap<String, RunningParamM> mapRunningParam = notificationDAO.getRunningParam();
			logger.debug("CARD_NOTIFICATION.....");
			logger.debug("CARD NOTIFICATION.....listCardParam.size() :: "+listCardParam.size());
			logger.debug("mapRunningParam :: "+mapRunningParam.size());
			for(CardParamDataM cardParamM: listCardParam){
				if(!Util.empty(cardParamM.getValue1())){
					paramId = cardParamM.getValue1();
					logger.debug("PARAM_ID :: 1>> "+paramId);
					runningParamM = mapRunningParam.get(paramId);
					if(!Util.empty(runningParamM)){
						valueFrom = runningParamM.getValueFrom();
						valueTo = runningParamM.getValueTo();
						cardUsage = runningParamM.getValue1();
						valueAlert = runningParamM.getValue2();
						remainder = valueTo-(valueFrom+cardUsage);
						logger.debug("WARNNING PARAMETER :: "+valueAlert);
						logger.debug("REMAINDER :: "+remainder);
						if(valueAlert>=remainder){
							logger.debug("WARNNING LEVEL 1");
							CardNotificationDataM cardNotification = new CardNotificationDataM();
							TaskObjectDataM  taskObject = new TaskObjectDataM();
							if(!Util.empty(cardParamM.getValue2())){
								logger.debug("HAS NEXT WARNNING VALUE");
								paramId = cardParamM.getValue2();
								runningParamM = mapRunningParam.get(paramId);
								valueFrom = runningParamM.getValueFrom();
								valueTo = runningParamM.getValueTo();
								cardUsage = runningParamM.getValue1();
								valueAlert = runningParamM.getValue2();
								remainder = valueTo-(valueFrom+cardUsage);
								logger.debug("NEXT VALUE WARNNING PARAMETER :: "+valueAlert);
								logger.debug("NEXT VALUE REMAINDER :: "+remainder);
								if(valueAlert>=remainder){
									logger.debug("WARNNING LEVEL 2");
									cardNotification.setCardParamId(paramId);
									cardNotification.setResult(String.valueOf(remainder));
									cardNotification.setValueFrom(String.valueOf(valueFrom));
									cardNotification.setValueTo(String.valueOf(valueTo));
									cardNotification.setValue1(String.valueOf(cardUsage));
									taskObject.setUniqueId(cardNotification.getCardParamId());
									taskObject.setObject(cardNotification);
									taskObjects.add(taskObject);
								}
							}else{
								cardNotification.setCardParamId(paramId);
								cardNotification.setResult(String.valueOf(remainder));
								cardNotification.setValueFrom(String.valueOf(valueFrom));
								cardNotification.setValueTo(String.valueOf(valueTo));
								cardNotification.setValue1(String.valueOf(cardUsage));
								taskObject.setUniqueId(cardNotification.getCardParamId());
								taskObject.setObject(cardNotification);
								taskObjects.add(taskObject);
							}
						}
					}
				}
				logger.debug("==============================");
			}
			
			/*ArrayList<CardNotificationDataM> cardNotifications = dao.selectCardNotification();
			if(!InfBatchUtil.empty(cardNotifications)){
				for(CardNotificationDataM cardNotification : cardNotifications){
					TaskObjectDataM  taskObject = new TaskObjectDataM();
					logger.debug("CardParamId : "+cardNotification.getCardParamId());
					taskObject.setUniqueId(cardNotification.getCardParamId());
					taskObject.setObject(cardNotification);
					taskObjects.add(taskObject);
				}
			}*/
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			NotifyRequest notifyRequest = new NotifyRequest();
			notifyRequest.setRequestObject(task);
			notifyRequest.setNotifyId(NotifyConstant.Notify.CARD_NOTIFICATION_PROCESS);
			NotifyResponse notifyResponse = NotifyController.notify(notifyRequest, null);
			taskExecute.setResultCode(notifyResponse.getStatusCode());
			taskExecute.setResultDesc(notifyResponse.getStatusDesc());
		}catch(Exception e){
			logger.fatal("ERROR",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
