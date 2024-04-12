package com.eaf.inf.batch.ulo.upload.user.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.inf.NotifyInf;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.core.ulo.service.notify.model.ServiceResultDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;
import com.eaf.service.rest.model.KmobileRequest;
import com.eaf.service.rest.model.KmobileResponse;
import com.eaf.service.rest.model.SMSRequest;
import com.eaf.service.rest.model.SMSResponse;
import com.eaf.service.rest.model.ServiceResponse;

public class UploadUserController {
	private static transient Logger logger = Logger.getLogger(UploadUserController.class);
	public static NotifyResponse notify(NotifyRequest notifyRequest){
		logger.debug("NotifyController.notify >> "+notifyRequest);
		NotifyResponse notifyResponse = new NotifyResponse();
		try{
			notifyResponse.setStatusCode(InfBatchConstant.ResultCode.SUCCESS);
			NotifyInf notify = getNotify(notifyRequest);
			boolean requiredNotify = notify.requiredNotify();
			logger.debug("notify >> "+notify);
			logger.debug("requiredNotify >> "+requiredNotify);
			if(null != notify && requiredNotify){
				RecipientTypeDataM recipient = notify.getRecipient(notifyRequest);
				logger.debug("recipient >> "+recipient);
				notify.init(notifyRequest,recipient);		
				NotifyTransactionDataM notifyTransaction = notify.processNotifyTransaction();
				logger.debug("notifyTransaction >> "+notifyTransaction);
				if(notify.validationNotifyTransactionResult(notifyRequest)){
					if(null != notifyTransaction){
						 ArrayList<NotifyTransactionResultDataM> transactions = notifyTransaction.getTransactions();
						 logger.debug("transactions >> "+transactions);
						 if(null != transactions){
							 for (NotifyTransactionResultDataM transactionResult : transactions) {
								 try{								 
									logger.debug("transactionResult >> "+transactionResult);
									notify.preNotifyTransactionResult(transactionResult);
									ArrayList<NotifyTemplateDataM> notifyTemplates = notify.getNotifyTemplate(transactionResult);
									logger.debug("notifyTemplates >> "+notifyTemplates);
									//if(null != notifyTemplates){
									if(!InfBatchUtil.empty(notifyTemplates)){
										for (NotifyTemplateDataM notifyTemplate : notifyTemplates) {
											String templateType = notifyTemplate.getTemplateType();		
											logger.debug("templateType >> "+templateType);
											if(TemplateBuilderConstant.TemplateType.EMAIL.equals(templateType)){
												EmailRequest emailRequest = notify.getEmailRequest(transactionResult,notifyTemplate);
												if(!InfBatchUtil.empty(emailRequest.getTo())&&emailRequest.getTo().length>0){
													EmailResponse emailResponse = notify.sendEmail(emailRequest);
													ServiceResultDataM serviceResult = new ServiceResultDataM();	
														serviceResult.setTemplateType(templateType);
														serviceResult.setRequestObject(emailRequest);
														serviceResult.setResponseObject(emailResponse);
													transactionResult.add(serviceResult);
												}else{
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
													notifyResponse.setStatusDesc("Template does not match condition");
												}
											}else if(TemplateBuilderConstant.TemplateType.SMS.equals(templateType)){
												SMSRequest smsRequest = notify.getSMSRequest(transactionResult,notifyTemplate);
												if(!InfBatchUtil.empty(smsRequest.getMobileNoElement())){
													SMSResponse smsResponse = notify.sendSMS(smsRequest);
													ServiceResultDataM serviceResult = new ServiceResultDataM();	
														serviceResult.setTemplateType(templateType);
														serviceResult.setRequestObject(smsRequest);
														serviceResult.setResponseObject(smsResponse);
													transactionResult.add(serviceResult);
												}else{
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
													notifyResponse.setStatusDesc("Template does not match condition");
												}
											}else if(TemplateBuilderConstant.TemplateType.K_MOBILE.equals(templateType)){
												KmobileRequest kMobileRequest = notify.getSMSKMobileRequest(transactionResult, notifyTemplate);
												if(!Util.empty(kMobileRequest.getMobileNo())){
													KmobileResponse kMobileResponse = notify.sendSMSKMobile(kMobileRequest);
													ServiceResultDataM serviceResult = new ServiceResultDataM();	
														serviceResult.setTemplateType(templateType);
														serviceResult.setRequestObject(kMobileRequest);
														serviceResult.setResponseObject(kMobileResponse);
													transactionResult.add(serviceResult);
												}else{
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
													notifyResponse.setStatusDesc("Template does not match condition");
												}
											}
										}
										ArrayList<ServiceResultDataM> serviceResults = transactionResult.getServiceResults();
										for(ServiceResultDataM serviceResult : serviceResults){
											Object object = serviceResult.getResponseObject();
											if(object instanceof EmailResponse){
												EmailResponse emailResponse = (EmailResponse)object;
												if(EmailResponse.Status.FAIL.equals(emailResponse.getStatusCode())){
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.FAIL);
													notifyResponse.setStatusDesc(emailResponse.getStatusDesc());
												}else if(EmailResponse.Status.WARNING.equals(emailResponse.getStatusCode())){
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
													notifyResponse.setStatusDesc(emailResponse.getStatusDesc());
												}
											}else if(object instanceof SMSResponse){
												SMSResponse smsResponse = (SMSResponse)object;
												if(ServiceResponse.Status.SYSTEM_EXCEPTION.equals(smsResponse.getStatusCode())){
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.FAIL);
													notifyResponse.setStatusDesc(smsResponse.getStatusDesc());
												}else if(ServiceResponse.Status.WARNING.equals(smsResponse.getStatusCode())){
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
													notifyResponse.setStatusDesc(smsResponse.getStatusDesc());
												}
											}else if(object instanceof KmobileResponse){
												KmobileResponse kMobileResponse = (KmobileResponse)object;
												if(ServiceResponse.Status.SYSTEM_EXCEPTION.equals(kMobileResponse.getStatus())){
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.FAIL);
													notifyResponse.setStatusDesc(kMobileResponse.getMsgDesc());
												}else if(ServiceResponse.Status.WARNING.equals(kMobileResponse.getStatus())){
													notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
													notifyResponse.setStatusDesc(kMobileResponse.getMsgDesc());
												}
											}
										}
									}else{
										notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
										notifyResponse.setStatusDesc("Template was not found");
									}
									notify.postNotifyTransactionResult(transactionResult);
									
//									ArrayList<ServiceResultDataM> serviceResults = transactionResult.getServiceResults();
//									logger.debug("serviceResults size : "+serviceResults.size());
//									for(ServiceResultDataM serviceResult : serviceResults){
//										Object object = serviceResult.getResponseObject();
//										if(object instanceof EmailResponse){
//											EmailResponse emailResponse = (EmailResponse)object;
//											if(EmailResponse.Status.FAIL.equals(emailResponse.getStatusCode())){
//												notifyResponse.setStatusCode(InfBatchConstant.ResultCode.FAIL);
//												notifyResponse.setStatusDesc(emailResponse.getStatusDesc());
//												logger.debug("emailResponse.getStatusDesc() >> "+emailResponse.getStatusDesc());
//											}
//										}else if(object instanceof SMSResponse){
//											SMSResponse smsResponse = (SMSResponse)object;
//											if(ServiceResponse.Status.SYSTEM_EXCEPTION.equals(smsResponse.getStatusCode())){
//												notifyResponse.setStatusCode(InfBatchConstant.ResultCode.FAIL);
//												notifyResponse.setStatusDesc(smsResponse.getStatusDesc());
//											}
//										}else if(object instanceof KmobileResponse){
//											KmobileResponse kMobileResponse = (KmobileResponse)object;
//											if(ServiceResponse.Status.SYSTEM_EXCEPTION.equals(kMobileResponse.getStatus())){
//												notifyResponse.setStatusCode(InfBatchConstant.ResultCode.FAIL);
//												notifyResponse.setStatusDesc(kMobileResponse.getMsgDesc());
//											}
//										}
//									}
								 }catch(Exception e){
									 logger.fatal("ERROR ",e);
									 notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
									 notifyResponse.setStatusDesc(e.getLocalizedMessage());
								 }
							}
						 }else{
							notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
							notifyResponse.setStatusDesc("Transaction is null");
						 }
					}else{
						notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
						notifyResponse.setStatusDesc("Notify transaction is null");
					}
				}else{
					//notifyResponse.setStatusCode(InfBatchConstant.ResultCode.SUCCESS);
					notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
					notifyResponse.setStatusDesc("Recipient was not found");
				}
			}else{
				notifyResponse.setStatusCode(InfBatchConstant.ResultCode.WARNING);
				notifyResponse.setStatusDesc("Condition not match");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			notifyResponse.setStatusCode(InfBatchConstant.ResultCode.FAIL);
			notifyResponse.setStatusDesc(e.getLocalizedMessage());
		}
		return notifyResponse; 
	}
	public static NotifyInf getNotify(NotifyRequest notifyRequest) throws Exception{
		NotifyInf notifyInf = null;
		String className = InfBatchProperty.getInfBatchConfig(notifyRequest.getNotifyId());
		logger.debug("className >> "+className);
		logger.debug("notifyRequest.getNotifyId() >>"+notifyRequest.getNotifyId());
		notifyInf =(NotifyInf)Class.forName(className).newInstance();
		return notifyInf;
	}
}

