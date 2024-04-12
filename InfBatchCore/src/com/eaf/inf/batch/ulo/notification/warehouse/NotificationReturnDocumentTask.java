package com.eaf.inf.batch.ulo.notification.warehouse;

import java.sql.Date;
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
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.DMNotificationDataM;

public class NotificationReturnDocumentTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(NotificationReturnDocumentTask.class);
	String NOTIFICATION_TYPE_NOT_RETURN  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_NOTIFICATION_TYPE_NOT_RETURN");
	String GROUP_CODE_NOT_RETURN  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_GROUP_CODE_NOT_RETURN");
	String PARAM_CODE_WH_BORROW_DOC_DAY  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_PARAM_CODE_WH_BORROW_DOC_DAY");
	String PARAM_CODE_WH_INTERVAL_RETURN_DOC  =InfBatchProperty.getInfBatchConfig("NOTIFICATION_WARE_HOUSE_PARAM_CODE_WH_INTERVAL_RETURN_DOC");

	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects =  new ArrayList<TaskObjectDataM>();
		try {
			NotificationDAO dao = NotificationFactory.getNotificationDAO();
			ArrayList<DMNotificationDataM> dmNotifications =  dao.selectReturnNotificationDM(NOTIFICATION_TYPE_NOT_RETURN,GROUP_CODE_NOT_RETURN);
			Date APPLICATION_DATE = dao.getApplicationDate();
			String WH_BORROW_DOC_DAY = dao.getDMGeneralParam(PARAM_CODE_WH_BORROW_DOC_DAY);
			String WH_INTERVAL_RETURN_DOC = dao.getDMGeneralParam(PARAM_CODE_WH_INTERVAL_RETURN_DOC);
			if(!InfBatchUtil.empty(dmNotifications)){
				for(DMNotificationDataM dmNotification : dmNotifications){
					TaskObjectDataM taskobj  = new TaskObjectDataM();
					logger.debug(">>.dmNotification.getRoundOfNotification()>>"+dmNotification.getRoundOfNotification());
					logger.debug(">>.dmNotification.getDmId()>>"+dmNotification.getDmId());
					if(dmNotification.getRoundOfNotification()==0){						
						taskobj = NotificationWareHouse.notificationReturnProcess(dmNotification,-NotificationWareHouse.toInteger(WH_BORROW_DOC_DAY),APPLICATION_DATE);	
					}else if(dmNotification.getRoundOfNotification()>0){
						int notiRound =dmNotification.getRoundOfNotification();
						int addDays = notiRound*NotificationWareHouse.toInteger(WH_INTERVAL_RETURN_DOC);
						logger.debug(">>notiRound*addDays>>"+notiRound+"*"+WH_INTERVAL_RETURN_DOC+"="+addDays);
						taskobj =NotificationWareHouse.notificationReturnProcess(dmNotification,addDays,APPLICATION_DATE);
					}		
					logger.debug("taskobj >> "+taskobj);
					if(!InfBatchUtil.empty(taskobj)){
						taskObjects.add(taskobj);
					}
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
			if(!InfBatchUtil.empty(task)){
				NotifyController controller = new NotifyController();
				NotifyRequest notifyRequest = new NotifyRequest();
				notifyRequest.setRequestObject(task);
				notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION_WARE_HOUSE_RETURN);
				NotifyResponse notifyResponse = controller.notify(notifyRequest, null);
				taskExecute.setResultCode(notifyResponse.getStatusCode());
				taskExecute.setResultDesc(notifyResponse.getStatusDesc());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
