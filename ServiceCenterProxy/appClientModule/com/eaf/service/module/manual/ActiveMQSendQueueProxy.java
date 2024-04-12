package com.eaf.service.module.manual;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.tempuri.KBKInstinctFraudCheckSoapProxy;
import org.tempuri.RequestOnline;
import org.tempuri.ResponseOnline;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.common.ResponseData;
import com.eaf.service.common.activemq.model.SendQueue;
import com.eaf.service.common.activemq.service.BaseSmartServeCreateCaseService;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.inf.ServiceControl;
import com.eaf.service.common.inf.ServiceControlHelper;
import com.eaf.service.common.model.CISRefDataObject;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestTransaction;
import com.eaf.service.common.model.ServiceResponseTransaction;
import com.eaf.service.common.model.ServiceTransaction;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.ActiveMQSendQueueRequestDataM;
import com.eaf.service.module.model.CIS1048O01RequestDataM;
import com.eaf.service.module.model.KBKInstinctFraudCheckRequestDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;
import com.kasikornbank.eai.cbs1215i01.CBS1215I01Response;
import com.kasikornbank.eai.cbs1215i01.__doServiceResponse_CBS1215I01Response_KBankHeader;
import com.kasikornbank.eai.cis1048o01.CIS1048O01Response;
import com.kasikornbank.eai.cis1048o01.CIS1048O01SoapProxy;
import com.kasikornbank.eai.cis1048o01.CIS1048O01_Type;
import com.kasikornbank.eai.cis1048o01.__doServiceResponse_CIS1048O01Response_KBankHeader;

public class ActiveMQSendQueueProxy extends ServiceControlHelper implements ServiceControl{
	private static transient Logger logger = Logger.getLogger(ActiveMQSendQueueProxy.class);
	
	public final static String serviceId = "ActiveMQSendQueue";
	
//	public static class requestConstant{
//		public static String fraudCheckFlag="F";
//		public static String legacyName="E-APP";
//		
//	}
	
	
	public static class responseConstant{
		public static class Status{
			public static String SUCCESS="00";
		}
		
		
	}
	
	@Override
	public ServiceRequestTransaction requestTransaction() {
		
		ActiveMQSendQueueRequestDataM requetData = (ActiveMQSendQueueRequestDataM)serviceRequest.getObjectData();	
		
//		ServiceUtil.display(ServiceApplicationDate.getTimestamp(),ServiceUtil.EN,"dd-MM-yyyy HH:mm:ss");
//		
//		DateFormat dl = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String body ="";
		ApplicationGroupDataM  applicationGroupDataM = requetData.getApplicationGroup();
		try {
			BaseSmartServeCreateCaseService service =new BaseSmartServeCreateCaseService();
			
			//ApplicationGroupDataM  applicationGroupDataM = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(requetData.getApplicationGroupId());
			body= service.createCase(applicationGroupDataM,"", null, null, serviceRequest.getUserId(),serviceRequest.getServiceReqResId());
		} catch (ApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SendQueue sendQueue=new SendQueue();
		sendQueue.setBody(body);
		sendQueue.setApplicationGroupId(applicationGroupDataM.getApplicationGroupId());
		sendQueue.setUserId(serviceRequest.getUserId());
		
		ServiceRequestTransaction requestTransaction = new ServiceRequestTransaction();
		requestTransaction.serviceInfo(ServiceConstant.OUT, sendQueue, serviceRequest);
		return requestTransaction;

	}
	@Override
	public ServiceTransaction serviceTransaction(Object requestServiceObject) {
		logger.debug("serviceTransaction..");
		ServiceTransaction serviceTransaction = new ServiceTransaction();
		try{
			SendQueue requestObject = (SendQueue)requestServiceObject;
			
			BaseSmartServeCreateCaseService service =new BaseSmartServeCreateCaseService();
			String responseObject=service.sendMessage(requestObject.getApplicationGroupId(), null, null, requestObject.getUserId(), requestObject.getBody(), null);
			
			serviceTransaction.setServiceTransactionObject(responseObject);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceTransaction.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceTransaction.setErrorInfo(error(ErrorData.ErrorType.CONNECTION_ERROR,e));
		}
		return serviceTransaction;
	}
	@Override
	public ServiceResponseTransaction responseTransaction(ServiceTransaction serviceTransaction){
		logger.debug("responseTransaction..");
		ServiceResponseTransaction responseTransaction = new ServiceResponseTransaction();
		String responseObject = (String)serviceTransaction.getServiceTransactionObject();
		
		if(ServiceResponse.Status.SUCCESS.equals(serviceTransaction.getStatusCode()) && !ServiceUtil.empty(responseObject)){
			try{
				
				serviceResponse.setObjectData(responseObject);
				serviceResponse.setStatusCode(responseConstant.Status.SUCCESS);
			}catch(Exception e){
				logger.fatal("ERROR",e);			
				serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
				serviceResponse.setErrorInfo(error(ErrorData.ErrorType.SYSTEM_ERROR,e));
			}
		}else{
			serviceResponse.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			serviceResponse.setErrorInfo(serviceTransaction.getErrorInfo());
		}
		responseTransaction.serviceInfo(ServiceConstant.IN, responseObject, serviceResponse);
		return responseTransaction;
	}
}
