package com.eaf.inf.batch.ulo.debt.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
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
import com.eaf.inf.batch.ulo.debt.dao.DebtDAO;
import com.eaf.inf.batch.ulo.debt.dao.DebtDAOFactory;
import com.eaf.inf.batch.ulo.debt.model.GenerateDebtDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchGenerateOutputDebt extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchGenerateOutputDebt.class);
	String GENERATE_DEBT_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("GENERATE_DEBT_OUTPUT_NAME");
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String GENERATE_DEBT_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("GENERATE_DEBT_FILE_ENCODING");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String batchId = infBatchRequest.getBatchId();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(batchId);
		Writer writer = null;
		try{
			String GENERATE_DEBT_OUTPUT_PATH = PathUtil.getPath(batchId, OUTPUT_PATH);
			FileControl.mkdir(GENERATE_DEBT_OUTPUT_PATH);
			String fileName = GENERATE_DEBT_OUTPUT_NAME.replace("yyyymmdd",Formatter.display(InfBatchProperty.getDate(),Formatter.EN,"yyyyMMdd"));
			String fileOutputPath = GENERATE_DEBT_OUTPUT_PATH+File.separator+fileName;
			logger.debug("fileOutputPath >> "+fileOutputPath);
			DebtDAO debtDAO = DebtDAOFactory.getDebtDAO(); 
			ArrayList<GenerateDebtDataM> generateDebts = debtDAO.getOutputDebt();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputPath), GENERATE_DEBT_FILE_ENCODING));
			String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.yyyy_MM_dd);
			writer.write(DATE);
			writer.write(System.getProperty("line.separator"));
			if(!InfBatchUtil.empty(generateDebts)){
				InfDAO dao = InfFactory.getInfDAO();
				for(GenerateDebtDataM generateDebt:generateDebts){
					BigDecimal verifiedIncome = ( null == generateDebt.getVerifiedIncome() ? BigDecimal.ZERO : generateDebt.getVerifiedIncome() );
					BigDecimal otherDebtAmount = ( null == generateDebt.getOtherDebtAmount() ? BigDecimal.ZERO : generateDebt.getOtherDebtAmount() );
					BigDecimal approveAmount = ( null == generateDebt.getApproveAmount() ? BigDecimal.ZERO : generateDebt.getApproveAmount() );
					
					StringBuilder text = new StringBuilder();
					logger.debug("generateConsent.getApplicationGroupNo() >> "+generateDebt.getApplicationGroupNo());
						text.append(Formatter.displayText(generateDebt.getApplicationGroupNo()))
							.append(",").append(Formatter.displayText(generateDebt.getCisNo()))
							.append(",").append(Formatter.displayText(generateDebt.getIdNo()))
							.append(",").append(Formatter.displayText(generateDebt.getConsentRefNo()))
							.append(",").append(Formatter.displayText(String.valueOf(verifiedIncome.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(String.valueOf(otherDebtAmount.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(String.valueOf(approveAmount.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(generateDebt.getProduct()))
							.append(",").append(Formatter.displayText(generateDebt.getSourceOfApp()));
						logger.debug("text >> "+text);
					writer.write(text.toString());
					writer.write(System.getProperty("line.separator"));
				
					InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationGroupId(generateDebt.getApplicationGroupId());
						infBatchLog.setRefId(generateDebt.getApplicationGroupNo());
						infBatchLog.setCreateBy(SYSTEM_USER_ID);
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("GENERATE_DEBT_MODULE_ID"));
						infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
						infBatchLog.setLifeCycle(generateDebt.getLifeCycle());
					dao.insertInfBatchLog(infBatchLog);
				}
			}
			int totalRecord = generateDebts.size();
			logger.debug("totalRecord >> "+totalRecord);
			writer.write(String.valueOf(totalRecord));
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
