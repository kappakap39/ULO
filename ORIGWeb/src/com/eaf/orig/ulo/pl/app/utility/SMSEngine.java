package com.eaf.orig.ulo.pl.app.utility;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.inf.log.model.ServiceReqRespDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.ejb.ORIGContactManager;
import com.eaf.orig.shared.model.SMSDataM;
import com.eaf.orig.shared.model.SMSPrepareDataM;
import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.kbank.sms.CardLinkElement;
import com.kbank.sms.SMSRequestElement;
import com.kbank.sms.SMSResponseElement;
import com.kbank.sms.SMSServiceWSProxy;

public class SMSEngine {
	
	static Logger logger = Logger.getLogger(SMSEngine.class);
	
	public void send(PLOrigContactDataM origContactM){		
		
		logger.debug("SMSEngine.send()..");
		
		ServiceEmailSMSQLogM logM = new ServiceEmailSMSQLogM();
    	PLOrigSMSUtil smsUtil = new PLOrigSMSUtil();    	
		try{
			logM.setServiceType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
			logM.setContactLogID(origContactM.getContactLogId());
			SMSPrepareDataM smsPrepareM = null;
			
//			Septem Modify check ManualFLAG = 'Y'
			if(OrigConstant.FLAG_Y.equals(origContactM.getManualFLAG())){
				smsPrepareM = origContactM.getSmsM();
			}else{
				smsPrepareM = smsUtil.prepareSMSData(origContactM.getApplicationRecordId(), origContactM.getTemplateName());
			}
			    	
			if(OrigConstant.FLAG_Y.equals(smsPrepareM.getEnableFlag()) 
					&& !OrigUtil.isEmptyString(smsPrepareM.getMobile()) 
						&& !OrigUtil.isEmptyString(smsPrepareM.getMessage())){
    		
	    		SMSDataM smsM = smsUtil.prepareSMSMessage(smsPrepareM);
	    			origContactM.setSms(smsM);
	    			
	    		serviceProcess(origContactM, logM);
	    	} 
		}catch (Exception e) {
			logger.fatal("ERROR : ",e);      		
			ORIGContactManager bean = PLORIGEJBService.getORIGContactManager();	
			logM.setServiceType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
			logM.setContactLogID(origContactM.getContactLogId());
    		logM.setLogType(OrigConstant.EmailSMSQLog.LOG_TYPE_ERROR);
			logM.setLogDesc(OrigConstant.FAIL + ":" + e.getMessage());
				bean.createServiceEmailSMSQLog(logM);
				
			origContactM.setSendStatus(OrigConstant.FLAG_N);
				bean.createOrigContractLog(origContactM);
		}
	}
	
	public void serviceProcess(PLOrigContactDataM origContactM,ServiceEmailSMSQLogM logM) throws Exception{
			
		logger.debug("SMSEngine.serviceProcess()..");
		
    	PLOrigEmailSMSUtil util = new PLOrigEmailSMSUtil();
    		
		logM.setServiceType(OrigConstant.EmailSMS.CONTACT_TYPE_SMS);
		logM.setContactLogID(origContactM.getContactLogId());
	
		SMSDataM smsM = origContactM.getSms();
	
		SMSRequestElement smsRequest = new SMSRequestElement();
		String[] mobil = smsM.getNumber().split(",");
		smsRequest.setMobileNoElement(mobil); //10digit start with 0, 11 digit start with 66
		smsRequest.setPriority(0); //0 or 1
		
		String cliendId = "KBank"; //default value
		GeneralParamProperties clientParamM = util.getGeneralParamDetails(OrigConstant.GeneralParamCode.SMS_CLIENT_ID);
		if(clientParamM != null) cliendId = clientParamM.getParamvalue();
		smsRequest.setClientId(cliendId);
		
		CardLinkElement cardLinkElement = new CardLinkElement();
		cardLinkElement.setMsg(smsM.getContent());
		cardLinkElement.setTemplateId(600); //600
		cardLinkElement.setSmsLanguage(OrigConstant.NATION_THAI); // TH or EN or HX
		
		String departmentCode = "PL-KLOP"; //default value
		GeneralParamProperties departCodeParamM = util.getGeneralParamDetails(OrigConstant.GeneralParamCode.SMS_DEPARTMENT_CODE);
		if(departCodeParamM != null) departmentCode = departCodeParamM.getParamvalue();
		
		cardLinkElement.setDepartmentCode(departmentCode); //Product Code – given by owner of SMS Server
		//cardLinkElement.setMessageType(null); //Hexadecimal string
		
		smsRequest.setCardLinkElement(cardLinkElement);
		
		ServiceReqRespDataM servReqRespM = create(origContactM);    		
		SMSResponseElement smsResponseM = null;   
		
		try{
			servReqRespM.setActivityType(OrigConstant.ServiceActivityType.OUT);
			servReqRespM.setContentMsg(smsRequest.toString());
			PLORIGEJBService.getORIGLogManager().SaveLogServiceReqResp(servReqRespM);
			
    		SMSServiceWSProxy proxy = new SMSServiceWSProxy();
    			proxy.setEndpoint(ORIGConfig.SMS_SERVICE_URL);
			smsResponseM =  proxy.sendSMS(smsRequest);
			
			if(null != smsResponseM){
				servReqRespM.setActivityType(OrigConstant.ServiceActivityType.IN);
				servReqRespM.setRespCode(String.valueOf(smsResponseM.getResponseCode()));
				servReqRespM.setRespDesc(smsResponseM.getResponseDetail());
				servReqRespM.setContentMsg(smsResponseM.toString());
				PLORIGEJBService.getORIGLogManager().SaveLogServiceReqResp(servReqRespM);
			}else{
				throw new Exception("No Response SMS Service!!");
			}    			
		}catch(Exception e){
			logger.fatal("ERROR : ",e);    		
			servReqRespM.setActivityType(OrigConstant.ServiceActivityType.IN);
			servReqRespM.setRespDesc(e.getMessage());
			servReqRespM.setContentMsg(null);
			PLORIGEJBService.getORIGLogManager().SaveLogServiceReqResp(servReqRespM);        			
			throw new Exception(e.getMessage());
		}
		
		if(null != smsResponseM){
    		if(smsResponseM.getResponseCode() != OrigConstant.EmailSMS.RESPONSE_CODE_SUCCESS && 
    				smsResponseM.getResponseCode() != OrigConstant.EmailSMS.RESPONSE_CODE_QUEUE){ 
    			throw new Exception(smsResponseM.getResponseDetail());
    		}else{
    	    	ORIGContactManager bean = PLORIGEJBService.getORIGContactManager();    
    			logM.setLogType(OrigConstant.EmailSMSQLog.LOG_TYPE_SUCCESS);
    			logM.setLogDesc(smsResponseM.getResponseDetail());
    				bean.createServiceEmailSMSQLog(logM);
    				
    			if(!OrigUtil.isEmptyString(origContactM.getApplicationRecordId())){
        			origContactM.setSendStatus(OrigConstant.FLAG_Y);
        				bean.createOrigContractLog(origContactM);
    			}        			
    		}
		}
    		
	}
	
	public ServiceReqRespDataM create(PLOrigContactDataM origContactM){
		ServiceReqRespDataM serviceM = new ServiceReqRespDataM();
		ServiceReqRespTool serviceTool = new ServiceReqRespTool();
		String reqID = serviceTool.GenerateReqResNo();
			serviceM.setTransId(origContactM.getTransactionId());
			serviceM.setServiceId(OrigConstant.ServiceLogID.SMS001);
			serviceM.setReqRespId(reqID);
			serviceM.setAppId(origContactM.getApplicationRecordId());
			serviceM.setRefCode(origContactM.getApplicationRecordId());
			serviceM.setCreateBy(origContactM.getCreateBy());
		return serviceM;
	}
}
