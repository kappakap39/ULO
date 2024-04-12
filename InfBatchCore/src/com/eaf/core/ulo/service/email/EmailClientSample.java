package com.eaf.core.ulo.service.email;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.ulo.cache.constant.CacheConstant;

public class EmailClientSample {
	public static void main(String[] args) {
		try{
			InfBatchManager.init(CacheConstant.Runtime.JAVA);
		}catch(Exception e){
			e.printStackTrace();
		}
		EmailRequest emailRequest  = new EmailRequest();
		EmailClient emailClient = new EmailClient();
		emailClient.send(emailRequest);
	}
}