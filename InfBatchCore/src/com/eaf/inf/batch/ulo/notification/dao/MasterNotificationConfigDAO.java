package com.eaf.inf.batch.ulo.notification.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.notification.model.NotificationCriteriaDataM;

public interface MasterNotificationConfigDAO {
	public ArrayList<String> getRejectReasons(String templateCode,String saleChannel,String recommendChannel,String sendTime,ArrayList<String> notificationTypes) throws InfBatchException;
	public void getNotificationPriority(NotificationCriteriaDataM criteria,HashMap<String,String> priorityMap, Connection conn) throws InfBatchException;
	public void getNotificationSeller(NotificationCriteriaDataM criteria,HashMap<String,String> sellerMap, Connection conn) throws InfBatchException;
}
