package com.eaf.service.module.manual;

import org.apache.log4j.Logger;

import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;

public class SampleWebServiceClient extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(SampleWebServiceClient.class);
	@Override
	public ServiceRequestTransaction requestTransaction() {
		return null;
	}
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		return null;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction){
		return null;
	}
}
