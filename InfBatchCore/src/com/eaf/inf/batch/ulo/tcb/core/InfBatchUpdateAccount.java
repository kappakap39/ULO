package com.eaf.inf.batch.ulo.tcb.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.email.EmailClient;
import com.eaf.orig.ulo.email.model.EmailRequest;
import com.eaf.orig.ulo.email.model.EmailResponse;

public class InfBatchUpdateAccount extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchUpdateAccount.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		String taskResultsDesc = "";
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM("UPDATE_ACCOUNT_TASK");
			ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
			
			ArrayList<TaskExecuteDataM> taskExecuteList = processTask.getTaskExecutes();
			for(TaskExecuteDataM task : taskExecuteList)
			{
				if(!Util.empty(task.getResultDesc()))
				{taskResultsDesc = taskResultsDesc + task.getResultDesc() + "<br/>";}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		
		//Send Email if ResultDesc is not NULL
		if(!Util.empty(taskResultsDesc))
		{
			sendWarningEmailUpdateAccount(taskResultsDesc);
		}
		
		return infBatchResponse;
	}
	
	public static void sendWarningEmailUpdateAccount(String content)
	{
		String EMAIL_ADDRESS_FROM = InfBatchProperty.getGeneralParam("EMAIL_POLICY_PO");
		String EMAIL_TO = InfBatchProperty.getInfBatchConfig("UPDATE_ACCOUNT_WARNING_EMAIL_TO");
		
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setTo(EMAIL_TO);
		emailRequest.setFrom(EMAIL_ADDRESS_FROM);
		emailRequest.setSubject("UPDATE_ACCOUNT_TASK Error Report # " + FormatUtil.display(InfBatchProperty.getDate(), FormatUtil.EN, "dd-MM-yyyy"));
		emailRequest.setContent(content);
		emailRequest.setSentDate(InfBatchProperty.getTimestamp());
		
		logger.debug("sendEmailTo : " + emailRequest.getEmailToString());
		logger.debug("Id : " + emailRequest.getUniqueId().getId());

		EmailResponse emailResponse = new EmailResponse();
		try 
		{
			EmailClient emailClient = new EmailClient();
			emailResponse = emailClient.send(emailRequest);
			logger.debug("emailResponse : " + emailResponse.getStatusCode() + " - " + emailResponse.getStatusDesc());
		} 
		catch (Exception e) 
		{
			logger.fatal("ERROR ", e);
		}
	}

}
