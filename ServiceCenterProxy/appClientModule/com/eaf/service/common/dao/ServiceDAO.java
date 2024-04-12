package com.eaf.service.common.dao;

import java.sql.Date;

import com.eaf.orig.ulo.model.app.CVRSValidationResultDataM;
import com.eaf.service.common.model.CISRefDataObject;
import com.eaf.service.common.model.ServiceLogData;
import com.eaf.service.common.model.ServiceReqRespDataM;
import com.eaf.service.common.model.ServiceRequestDataM;

public interface ServiceDAO {
	public void createLog(ServiceReqRespDataM reqRespM) throws Exception;
	public ServiceRequestDataM getRequestResponseData(String serviceReqResId) throws Exception;
	public ServiceLogData getServiceData(String refCode,String serviceId, String taskId) throws Exception;
	public CISRefDataObject getServiceData(String serviceId, String appId,CISRefDataObject serviceCISCustomer) throws Exception;
	public boolean sendDummyToPegaFlag(String applicationGroupId,int lifeCycle) throws Exception;
	public Date getAppCurrentDate()  throws Exception;
	public void createCVRSValidationResultTable (CVRSValidationResultDataM cvrsValidationResult) throws Exception;
	public String deleteCVRSValidationResultTable (String personalId) throws Exception;
	public String getCardType(String cardCode, String cardLevel, String product) throws Exception;
}
