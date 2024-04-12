package com.eaf.dm.rest.controller.cache;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.factory.DMModuleFactory;
import com.eaf.orig.ulo.dm.dao.DMDocDAO;
import com.eaf.orig.ulo.dm.service.DocumentManageService;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.ServiceResponse;

@RestController
@RequestMapping("/service/dm")
public class DMServiceController {	
	private static transient Logger logger = Logger.getLogger(DMServiceController.class);
	@RequestMapping(value="/create/{appGroupId}",method={RequestMethod.POST})
    public @ResponseBody ResponseEntity<ServiceResponse> create(@PathVariable("appGroupId") String applicationGroupId){
		logger.debug("applicationGroupId >> "+applicationGroupId);
		ServiceResponse serviceResponse = new ServiceResponse();
		ServiceCenterController serviceController = new ServiceCenterController();
		serviceController.init(OrigServiceLocator.WAREHOUSE_DB);
		ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
		try{			
			DocumentManageService dmService = new DocumentManageService();
			DocumentManagementDataM  docManagementDataM = dmService.createDMProcess(applicationGroupId);
			
			ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(ServiceUtil.generateServiceReqResId());
			serviceLogRequest.setRefCode(docManagementDataM.getRefNo2());
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject("{appGroupId:"+applicationGroupId+"}");
			serviceLogRequest.setServiceId(DocumentManageService.serviceId);
			serviceLogRequest.setUniqueId(docManagementDataM.getDmId());
			serviceLogRequest.setUserId(docManagementDataM.getUpdateBy());
			serviceController.createLog(serviceLogRequest);
			
			DMDocDAO dmDocDao = DMModuleFactory.getDMDocDAO();
			dmDocDao.saveTableDMDoc(docManagementDataM,false);
			serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			
			
			serviceLogResponse.setServiceReqRespId(ServiceUtil.generateServiceReqResId());
			serviceLogResponse.setRefCode(docManagementDataM.getRefNo2());
			serviceLogResponse.setActivityType(ServiceConstant.OUT);
			serviceLogResponse.setServiceId(DocumentManageService.serviceId);
			serviceLogResponse.setUniqueId(docManagementDataM.getDmId());
			serviceLogResponse.setUserId(docManagementDataM.getUpdateBy());
			serviceLogResponse.setRespCode(ServiceResponse.Status.SUCCESS);
			
		}catch(Exception e){
			logger.debug("ERROR ",e);
			serviceResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceResponse.setStatusDesc(e.getLocalizedMessage());
			serviceLogResponse.setRespCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceLogResponse.setRespDesc(e.getLocalizedMessage());
		}
		serviceController.createLog(serviceLogResponse);
		
		return ResponseEntity.ok(serviceResponse);
	}
}
