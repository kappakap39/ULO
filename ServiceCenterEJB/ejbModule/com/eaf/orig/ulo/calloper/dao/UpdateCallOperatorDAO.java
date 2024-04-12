package com.eaf.orig.ulo.calloper.dao;

import com.eaf.service.common.exception.ServiceControlException;

public interface UpdateCallOperatorDAO {
	public void updateCallOperator(String applicationGroupId,String CallOperator) throws ServiceControlException;
}
