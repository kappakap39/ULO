package com.eaf.inf.batch.ulo.inf.core;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;

public class InfBatchCardLinkCashDay1 extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchCardLinkCashDay1.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		String BATCH_ID = infBatchRequest.getBatchId();
			infBatchResponse.setBatchId(BATCH_ID);
		try{
			GenerateFileDataM generateFileData = getGenerateFileData(BATCH_ID, getGenerateType(BATCH_ID), Formatter.Format.yyMMDD);
			InfResultDataM infResult = generateFile(generateFileData);
			infBatchResponse.setResultCode(infResult.getResultCode());
			infBatchResponse.setResultDesc(infResult.getResultDesc());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		return infBatchResponse;
	}
}
