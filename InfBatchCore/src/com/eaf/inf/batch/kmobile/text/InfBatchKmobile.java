package com.eaf.inf.batch.kmobile.text;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.util.FileUtil;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.mf.dao.exception.UploadManualFileException;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchKmobile extends InfBatchHelper{
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
		private static Logger logger = Logger.getLogger(InfBatchKmobile.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException,UploadManualFileException {
		// TODO Auto-generated method stub
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		String BATCH_ID = infBatchRequest.getBatchId();
		try {
			GenerateFileDataM generateFileData = getGenerateTextFileParamData(BATCH_ID, getGenerateType(BATCH_ID), Formatter.Format.YYYYMMDD);
			InfResultDataM infResult = generateFile(generateFileData);
			infBatchResponse.setResultCode(infResult.getResultCode());
			infBatchResponse.setResultDesc(infResult.getResultDesc());
			
			//InfDAO dao = InfFactory.getInfDAO();
			//InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
			//infBatchLog.setCreateBy(SYSTEM_USER_ID);
			//infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
			//infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
			//infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("BATCH_KMOBILE_MODULE_ID"));
			//infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
			//dao.insertInfBatchLog(infBatchLog);
			
		} catch (Exception e) {
		logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
		
}
