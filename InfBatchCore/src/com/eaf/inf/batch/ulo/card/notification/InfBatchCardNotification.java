package com.eaf.inf.batch.ulo.card.notification;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
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

public class InfBatchCardNotification extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchCardNotification.class);
	String BATCH_CONFIG_ID_CARD_NOTIFICATION = InfBatchProperty.getInfBatchConfig("BATCH_CONFIG_ID_CARD_NOTIFICATION");
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		ArrayList<CardNotificationDataM> cardNotifications = new ArrayList<CardNotificationDataM>();
		try{
			NotificationDAO notificationDAO = NotificationFactory.getNotificationDAO();
			ArrayList<CardParamDataM> cardParams = notificationDAO.getCardParams();
			HashMap<String,RunningParamM> runningParamData = notificationDAO.getRunningParam();
			if(!Util.empty(cardParams)){
				for(CardParamDataM cardParam: cardParams){
					if(!Util.empty(cardParam.getValue1())){
						String paramId = cardParam.getValue1();
						logger.debug("paramId : "+paramId);
						RunningParamM runningParam = runningParamData.get(paramId);
						if(!Util.empty(runningParam)){
							double valueFrom = runningParam.getValueFrom();
							double valueTo = runningParam.getValueTo();
							double cardUsage = runningParam.getValue1();
							double warnningValue = runningParam.getValue2();
							double remainder = valueTo - ((valueFrom)+cardUsage);
							if(warnningValue >= remainder){
								CardNotificationDataM cardNotification = new CardNotificationDataM();
								if(!Util.empty(cardParam.getValue2()) && !Util.empty(runningParamData.get(cardParam.getValue2()))){
									String paramId2 = cardParam.getValue2();
									RunningParamM runningParam2 = runningParamData.get(paramId2);
									double valueFrom2 = runningParam2.getValueFrom();
									double valueTo2 = runningParam2.getValueTo();
									double cardUsage2 = runningParam2.getValue1();
									double warnningValue2 = runningParam2.getValue2();
									double remainder2 =valueTo2 - ((valueFrom2-1)+cardUsage2);
									if((valueFrom2==0&&valueTo2==0)){
										cardNotification.setCardParamId(paramId);
										cardNotification.setResult(FormatUtil.display(remainder,"#,###"));
										cardNotification.setValueFrom(FormatUtil.display(valueFrom,"#,###"));
										cardNotification.setValueTo(FormatUtil.display(valueTo,"#,###"));
										cardNotification.setValue1(FormatUtil.display(cardUsage,"#,###"));
										cardNotification.setCardTypeDesc(runningParam.getParamDesc());
										cardNotification.setParamCode(cardParam.getParamCode());
										cardNotifications.add(cardNotification);
									}
								}else{
									cardNotification.setCardParamId(paramId);
									cardNotification.setResult(FormatUtil.display(remainder,"#,###"));
									cardNotification.setValueFrom(FormatUtil.display(valueFrom,"#,###"));
									cardNotification.setValueTo(FormatUtil.display(valueTo,"#,###"));
									cardNotification.setValue1(FormatUtil.display(cardUsage,"#,###"));
									cardNotification.setCardTypeDesc(runningParam.getParamDesc());
									cardNotification.setParamCode(cardParam.getParamCode());
										cardNotifications.add(cardNotification);
								}
							}
						}
					}
				}
			}
			logger.debug("cardNotifications : "+cardNotifications);
			if(!Util.empty(cardNotifications)){
				NotifyRequest notifyRequest = new NotifyRequest();
				notifyRequest.setRequestObject(cardNotifications);
				notifyRequest.setNotifyId(NotifyConstant.Notify.CARD_NOTIFICATION_PROCESS);
				NotifyResponse notifyResponse = NotifyController.notify(notifyRequest, null);
				if(InfBatchConstant.ResultCode.SUCCESS.equals(notifyResponse.getStatusCode())){
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				}else{
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(notifyResponse.getStatusDesc());
				}
			}else{
				infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			}
			InfBatchResultController.setExecuteResultData(infBatchResponse);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
}
