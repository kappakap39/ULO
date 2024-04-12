package com.eaf.orig.ulo.service.app.submit.dao;

import java.util.ArrayList;

import com.eaf.service.common.exception.ServiceControlException;

public interface SubmitApplicationDAO {
	public boolean isContainCheckList(ArrayList<String> reasonIds ,String documentType,String fixedZoneFlag)throws ServiceControlException;
	public String getDocumentReasonType(ArrayList<String> reasonIds,String documentCode, boolean fixedZoneFlag) throws ServiceControlException;
}
