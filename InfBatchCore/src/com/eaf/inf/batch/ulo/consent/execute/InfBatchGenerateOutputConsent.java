package com.eaf.inf.batch.ulo.consent.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

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
import com.eaf.inf.batch.ulo.consent.model.GenerateConsentDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchGenerateOutputConsent extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchGenerateOutputConsent.class);
	String GENERATE_CONSENT_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_OUTPUT_NAME");
	String GENERATE_CONSENT_RECORD_RECTYPE = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_RECORD_RECTYPE");
	String GENERATE_CONSENT_TOTAL_RECTYPE = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_TOTAL_RECTYPE");
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String GENERATE_CONSENT_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_FILE_ENCODING");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String batchId = infBatchRequest.getBatchId();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(batchId);
		Writer writer = null;
		try{
			String GENERATE_CONSENT_OUTPUT_PATH = PathUtil.getPath(batchId, OUTPUT_PATH);
			FileControl.mkdir(GENERATE_CONSENT_OUTPUT_PATH);
			String fileName = GENERATE_CONSENT_OUTPUT_NAME.replace("yyyymmdd",Formatter.display(InfBatchProperty.getDate(),Formatter.EN,"yyyyMMdd"));
			String fileOutputPath = GENERATE_CONSENT_OUTPUT_PATH+File.separator+fileName;
			logger.debug("fileOutputPath >> "+fileOutputPath);
			ConsentDAO consentDAO = ConsentDAOFactory.getConsentDAO(); 
			ArrayList<GenerateConsentDataM> generateConsents = consentDAO.getOutputConsent();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputPath), GENERATE_CONSENT_FILE_ENCODING));
			if(!InfBatchUtil.empty(generateConsents)){
				InfDAO dao = InfFactory.getInfDAO();
				for(GenerateConsentDataM generateConsent:generateConsents){
					StringBuilder text = new StringBuilder();
					logger.debug("generateConsent.getSetID() >> "+generateConsent.getSetID());
						text.append(GENERATE_CONSENT_RECORD_RECTYPE)
							.append("|").append(Formatter.displayText(generateConsent.getSetID()))
							.append("|").append(Formatter.displayText(generateConsent.getConsentRefNo()))
							.append("|").append(Formatter.displayText(generateConsent.getBranchNo()))
							.append("|").append(Formatter.displayText(generateConsent.getApplicantType()));
						logger.debug("text >> "+text);
					writer.write(text.toString());
					writer.write(System.getProperty("line.separator"));
					String ncbDocHistoryId = generateConsent.getNcbDocHistoryId();
					logger.debug("ncbDocHistoryId : "+ncbDocHistoryId);
					consentDAO.updateConsentGenDate(ncbDocHistoryId);					
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(generateConsent.getApplicationGroupId());
						infBatchLog.setRefId(generateConsent.getSetID());
						infBatchLog.setCreateBy(SYSTEM_USER_ID);
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MODULE_ID"));
						infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
					dao.insertInfBatchLog(infBatchLog);
				}
			}
			int totalRecord = generateConsents.size();
			logger.debug("totalRecord >> "+totalRecord);
			writer.write(GENERATE_CONSENT_TOTAL_RECTYPE+"|"+String.valueOf(totalRecord));
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
