package com.avalant.wm.rest.controller.cache;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.service.rest.model.ServiceResponse;
//import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.controller.RefreshCacheController;

@RestController
@RequestMapping("/service/cache")
public class WMCacheController {
	private static transient Logger logger = Logger.getLogger(WMCacheController.class);
	@RequestMapping(value="/refresh/{cacheName}",method={RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT})
    public @ResponseBody ResponseEntity<ServiceResponse> refresh(@PathVariable("cacheName") String cacheName){
		logger.info("cacheName >> "+cacheName);
		ServiceResponse serviceResponse = new ServiceResponse();
		try{
			RefreshCacheController.refresh(cacheName);
			serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceResponse.setStatusDesc(e.getLocalizedMessage());
		}
		return ResponseEntity.ok(serviceResponse);
	}
}
