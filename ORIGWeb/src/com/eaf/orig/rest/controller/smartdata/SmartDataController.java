package com.eaf.orig.rest.controller.smartdata;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eaf.orig.rest.controller.smartdata.model.SmartImageRequest;
import com.eaf.orig.rest.controller.smartdata.model.SmartImageResponse;
import com.eaf.orig.rest.controller.smartdata.service.SmartDataService;
import com.eaf.service.common.constant.ServiceConstant;



@Controller
public class SmartDataController {
	static final transient Logger logger = Logger.getLogger(SmartDataController.class);
	@Autowired
	private SmartDataService smartService;	
	@RequestMapping(value="/smartdata/api/data",method={ RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public SmartImageResponse getImages(@RequestBody SmartImageRequest request)throws IOException{	
		logger.debug("request : "+request);
		SmartImageResponse response = new SmartImageResponse();
		try{
			response.setImages(smartService.mapImageSplitByTemplateId(request.getImages(), request.getTemplateId()));
			response.setFields(smartService.getFieldByTemplateId(request.getTemplateId()));
			response.setResultCode(ServiceConstant.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			response.setResultCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
		}
		return response;		
	}
}
