package com.eaf.service.common.inf;

import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;


public interface ServiceControl {
	public ServiceRequestTransaction requestTransaction() throws Exception;
	public ServiceTransaction serviceTransaction(Object requestServiceObject);
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction);
	public ServiceRequestDataM getServiceRequest();
	public ServiceResponseDataM getServiceResponse();
	public void perServiceTransaction(ServiceRequestDataM serviceRequest,ServiceResponseDataM serviceResponse);
	public void postServiceTransaction(ServiceRequestTransaction requestTransaction,ServiceResponseTransaction responseTransaction);
	public void setEndpointUrl(String endpointUrl);
	public String getEndpointUrl();
}
