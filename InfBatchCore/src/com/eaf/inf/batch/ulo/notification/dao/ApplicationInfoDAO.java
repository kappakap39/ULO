package com.eaf.inf.batch.ulo.notification.dao;

import java.util.List;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplication;

public interface ApplicationInfoDAO {
	public List<NotifyApplication> loadNotifyApplication(String applicationGroupId) throws InfBatchException;
}
