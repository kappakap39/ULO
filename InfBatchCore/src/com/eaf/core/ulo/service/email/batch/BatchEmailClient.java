package com.eaf.core.ulo.service.email.batch;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.mf.dao.exception.UploadManualFileException;
import com.eaf.orig.ulo.email.model.EmailRequest;

public class BatchEmailClient extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(BatchEmailClient.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException,UploadManualFileException{
		InfBatchResponseDataM batchResponse = new InfBatchResponseDataM();
		try{
			String EMAIL_CLIENT_FROM = InfBatchProperty.getInfBatchConfig("EMAIL_CLIENT_FROM");
			String EMAIL_CLIENT_SEND_TO = InfBatchProperty.getInfBatchConfig("EMAIL_CLIENT_SEND_TO");
			String EMAIL_CLIENT_CONTENT = InfBatchProperty.getGeneralParam("EMAIL_CLIENT_CONTENT");
			String EMAIL_CLIENT_SUBJECT = InfBatchProperty.getGeneralParam("EMAIL_CLIENT_SUBJECT");
			EmailClient emailClient = new EmailClient();
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setFrom(EMAIL_CLIENT_FROM);
			emailRequest.setTo(EMAIL_CLIENT_SEND_TO);
			emailRequest.setSubject(EMAIL_CLIENT_SUBJECT);
			emailRequest.setContent(EMAIL_CLIENT_CONTENT);
			emailClient.send(emailRequest);		
			batchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			batchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			batchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return batchResponse;
	}
	
}
