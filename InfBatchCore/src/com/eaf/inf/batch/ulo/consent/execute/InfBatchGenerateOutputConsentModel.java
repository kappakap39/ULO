package com.eaf.inf.batch.ulo.consent.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.inf.batch.ulo.consent.dao.ConsentDAO;
import com.eaf.inf.batch.ulo.consent.dao.ConsentDAOFactory;
import com.eaf.inf.batch.ulo.consent.model.GenerateConsentModelDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchGenerateOutputConsentModel extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchGenerateOutputConsentModel.class);
	String GENERATE_CONSENT_MODEL_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_OUTPUT_NAME");
	String GENERATE_CONSENT_MODEL_RECORD_RECTYPE = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_RECORD_RECTYPE");
	String GENERATE_CONSENT_MODEL_TOTAL_RECTYPE = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_TOTAL_RECTYPE");
	String GENERATE_CONSENT_MODEL_HEADER_RECTYPE = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_HEADER_RECTYPE");
	String GENERATE_CONSENT_MODEL_SOURCE_APP_ID = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_SOURCE_APP_ID");
	String GENERATE_CONSENT_MODEL_FILE_SEQ_NUM = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_FILE_SEQ_NUM");
	String GENERATE_CONSENT_MODEL_FILE_TYPE = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_FILE_TYPE");
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String GENERATE_CONSENT_MODEL_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_FILE_ENCODING");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String batchId = infBatchRequest.getBatchId();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(batchId);
		Writer writer = null;
		try{
			String GENERATE_CONSENT_MODEL_OUTPUT_PATH = PathUtil.getPath(batchId, OUTPUT_PATH);
			FileControl.mkdir(GENERATE_CONSENT_MODEL_OUTPUT_PATH);
			String fileName = GENERATE_CONSENT_MODEL_OUTPUT_NAME.replace("yyyymmdd",Formatter.display(InfBatchProperty.getDate(),Formatter.EN,"yyyyMMdd"));
			String fileOutputPath = GENERATE_CONSENT_MODEL_OUTPUT_PATH+File.separator+fileName;
			Date appDate = InfBatchProperty.getDate();
			String stringDateFormat = "yyyy-MM-dd'T'HH:mm:ssZZ";
	        FastDateFormat fastDateFormat = FastDateFormat.getInstance(stringDateFormat);
			logger.debug("fileOutputPath >> "+fileOutputPath);
			logger.debug("appDate >> "+appDate);
			ConsentDAO consentDAO = ConsentDAOFactory.getConsentDAO(); 
			ArrayList<GenerateConsentModelDataM> generateConsentModels = consentDAO.getOutputConsentModel();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputPath), GENERATE_CONSENT_MODEL_FILE_ENCODING));
			writer.write(GENERATE_CONSENT_MODEL_HEADER_RECTYPE+"|"+fastDateFormat.format(new java.util.Date())+"|"+InfBatchUtil.convertDate(appDate, InfBatchConstant.SYSTEM_DATE_FORMAT)+"|"+GENERATE_CONSENT_MODEL_SOURCE_APP_ID+"|"+GENERATE_CONSENT_MODEL_FILE_TYPE+"|"+GENERATE_CONSENT_MODEL_FILE_SEQ_NUM);
			writer.write(System.getProperty("line.separator"));
			if(!InfBatchUtil.empty(generateConsentModels)){
				InfDAO dao = InfFactory.getInfDAO();
				for(GenerateConsentModelDataM generateConsentModel:generateConsentModels){
					StringBuilder text = new StringBuilder();
					logger.debug("generateConsentModel.getSetID() >> "+generateConsentModel.getSetId());
						text.append(GENERATE_CONSENT_MODEL_RECORD_RECTYPE)
							.append("|").append(Formatter.displayText(generateConsentModel.getConsentRefNo()))
							.append("|").append(Formatter.displayText(generateConsentModel.getConsentModelFlag()));
						logger.debug("text >> "+text);
					writer.write(text.toString());
					writer.write(System.getProperty("line.separator"));				
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(generateConsentModel.getApplicationGroupId());
						infBatchLog.setRefId(generateConsentModel.getSetId());
						infBatchLog.setCreateBy(SYSTEM_USER_ID);
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODEL_MODULE_ID"));
						infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
					dao.insertInfBatchLog(infBatchLog);
				}
			}
			int totalRecord = generateConsentModels.size();
			logger.debug("totalRecord >> "+totalRecord);
			writer.write(GENERATE_CONSENT_MODEL_TOTAL_RECTYPE+"|"+String.valueOf(totalRecord));
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}finally{
			if(null != writer){
				try{
					writer.close();
				}catch(Exception e){
					logger.fatal("ERROR",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		return infBatchResponse;
	}

}
