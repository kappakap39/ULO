package com.eaf.service.rest.controller.checkproductdup.action;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.app.submit.dao.SubmitApplicationManager;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.eaf.service.common.model.decisions.DecisionServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.DecisionServiceProxy;
import com.eaf.service.module.model.FullFraudInfoDataM;
import com.eaf.service.rest.controller.followup.FollowUpResultUtil;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;

public class CheckProductDupAction extends ProcessActionHelper{
	private static Logger logger = Logger.getLogger(CheckProductDupAction.class);
	
	@Override
	public Object processAction()
	{
		
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
		
		return processActionResponse;
	}
}
