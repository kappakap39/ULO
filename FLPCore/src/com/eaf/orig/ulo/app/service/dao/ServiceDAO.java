package com.eaf.orig.ulo.app.service.dao;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public interface ServiceDAO {
	public String getServiceResult(String serviceId, String appId,String activityType) throws ApplicationException;
}
