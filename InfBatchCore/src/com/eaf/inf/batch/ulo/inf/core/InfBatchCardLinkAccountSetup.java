package com.eaf.inf.batch.ulo.inf.core;

import java.io.File;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;

public class InfBatchCardLinkAccountSetup extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchCardLinkAccountSetup.class);
	private String ERROR_FILE = InfBatchProperty.getInfBatchConfig("ERROR_FILE");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String CARDLINK_ACCOUNT_SETUP_VALIDATE_FLAG = InfBatchProperty.getInfBatchConfig("CARDLINK_ACCOUNT_SETUP_VALIDATE_FLAG");
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		String BATCH_ID = infBatchRequest.getBatchId();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(BATCH_ID);
		try{
			GenerateFileDataM generateFileData = getGenerateFileParamData(BATCH_ID, getGenerateType(BATCH_ID), Formatter.Format.yyMMDD);
			InfResultDataM infResult = generateFile(generateFileData);
			infBatchResponse.setResultCode(infResult.getResultCode());
			infBatchResponse.setResultDesc(infResult.getResultDesc());
			if(CARDLINK_ACCOUNT_SETUP_VALIDATE_FLAG.equals(FLAG_YES)){
				long errorFileSize = getFileSize(generateFileData.getFileOutputPath()+File.separator+generateFileData.getFileOutputName()+ERROR_FILE);
				if(!Util.empty(generateFileData)&&errorFileSize!=0){
					NotifyRequest notifyRequest = new NotifyRequest();
					notifyRequest.setRequestObject(generateFileData);
					notifyRequest.setNotifyId(NotifyConstant.Notify.CARDLINK_ACCOUNT_SETUP_PROCESS);
					NotifyResponse notifyResponse = NotifyController.notify(notifyRequest, null);
					if(InfBatchConstant.ResultCode.SUCCESS.equals(notifyResponse.getStatusCode())){
						infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
					}else{
						infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
						infBatchResponse.setResultDesc(notifyResponse.getStatusDesc());
					}
				}else{
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		return infBatchResponse;
	}
	
	public static long getFileSize(String filename) {
	      File file = new File(filename);
	      if (!file.exists() || !file.isFile()) {
	         System.out.println("File doesn't exist");
	         return -1;
	      }
	      return file.length();
	   }
}
