package com.eaf.service.rest.controller.sms;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.service.notify.inf.NotifyHelper;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;

@RestController
@RequestMapping("/service/sms")
public class SmsServiceController {
	private static transient Logger logger = Logger.getLogger(SmsServiceController.class);
	@RequestMapping(value="/send",method={RequestMethod.POST,RequestMethod.PUT})
    public @ResponseBody ResponseEntity<SMSResponse> send(@RequestBody SMSRequest smsRequest){
		logger.debug(smsRequest);	
		SMSResponse smsResponse = NotifyHelper.send(smsRequest);
		return ResponseEntity.ok(smsResponse);
	}
}
