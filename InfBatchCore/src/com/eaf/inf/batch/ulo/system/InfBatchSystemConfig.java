package com.eaf.inf.batch.ulo.system;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;

public class InfBatchSystemConfig extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchSystemConfig.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			email();
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		return infBatchResponse;
	}
	private static void email(){
		try{
			EmailClient emailClient = new EmailClient();
			EmailRequest emailRequest = new EmailRequest();
				emailRequest.setFrom("flpwf.noreply@kasikornbank.com");
				emailRequest.setTo("flpwf.noreply@gmail.com,weeraya@avalant.co.th,tanida@avalant.co.th,rawi.songchaisin@avalant.co.th");
				emailRequest.setSubject("[FLP] - TEST SEND EMAIL FROM KBANK SERVICE.");
				emailRequest.setContent("HELLO WORLD");
			System.out.println(emailRequest);
			EmailResponse emailResponse = emailClient.send(emailRequest);
			System.out.println(emailResponse);
			System.out.println("Send Email Status : "+emailResponse.getStatusCode());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
