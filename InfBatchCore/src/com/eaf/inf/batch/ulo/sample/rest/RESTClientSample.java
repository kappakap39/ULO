package com.eaf.inf.batch.ulo.sample.rest;

//import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
//import com.eaf.core.ulo.common.rest.RESTClient;
//import com.eaf.orig.ulo.model.ia.ImageDocumentRequest;

public class RESTClientSample extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(RESTClientSample.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
//		try{
//			ImageDocumentRequest imageDocumentRequest = new ImageDocumentRequest();
//				imageDocumentRequest.setQr1("QR1");
//				imageDocumentRequest.setQr2("QR2");
//				imageDocumentRequest.setScanDate(new java.sql.Date(new Date().getTime()));
//			RESTClient restClient = new RESTClient();
//				restClient.executeRESTCall("http://localhost:9082/ORIGWeb/rest/ia/service/create",imageDocumentRequest);
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//		}		
		return infBatchResponse;
	}
	public static void main(String[] args) {
		InfBatchManager.init();
		InfBatchRequestDataM infBatchRequest = new InfBatchRequestDataM();
		try{
			RESTClientSample restClientSample = new RESTClientSample();
			restClientSample.processAction(infBatchRequest);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
}
