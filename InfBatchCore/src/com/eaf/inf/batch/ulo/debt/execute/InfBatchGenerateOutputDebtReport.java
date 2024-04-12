package com.eaf.inf.batch.ulo.debt.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.debt.dao.DebtDAO;
import com.eaf.inf.batch.ulo.debt.dao.DebtDAOFactory;
import com.eaf.inf.batch.ulo.debt.model.GenerateDebtReportDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchGenerateOutputDebtReport extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchGenerateOutputDebtReport.class);
	String GENERATE_DEBTREPORT_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("GENERATE_DEBTREPORT_OUTPUT_NAME");
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String GENERATE_DEBTREPORT_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("GENERATE_DEBTREPORT_FILE_ENCODING");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String batchId = infBatchRequest.getBatchId();
		String systemDate = infBatchRequest.getSystemDate();
        Date systemDateD = InfBatchProperty.getDate();
        if(!Util.empty(systemDate)){
            systemDateD = InfBatchUtil.convertDate(systemDate, InfBatchConstant.SYSTEM_DATE_FORMAT);
        }
        
        InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
            infBatchResponse.setBatchId(batchId);
        Writer writer = null;
        try{
            String GENERATE_DEBTREPORT_OUTPUT_PATH = PathUtil.getPath(batchId, OUTPUT_PATH);
            FileControl.mkdir(GENERATE_DEBTREPORT_OUTPUT_PATH);
            String fileName = GENERATE_DEBTREPORT_OUTPUT_NAME.replace("yyyymmdd",Formatter.display(systemDateD,Formatter.EN,"yyyyMMdd"));
			String fileOutputPath = GENERATE_DEBTREPORT_OUTPUT_PATH+File.separator+fileName;
			logger.debug("fileOutputPath >> "+fileOutputPath);
			DebtDAO debtDAO = DebtDAOFactory.getDebtDAO(); 
			ArrayList<GenerateDebtReportDataM> generateDebtReports = debtDAO.getOutputDebtReport(systemDate);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputPath), GENERATE_DEBTREPORT_FILE_ENCODING));
		
			if(!InfBatchUtil.empty(generateDebtReports)){
				InfDAO dao = InfFactory.getInfDAO();
				for(GenerateDebtReportDataM generateDebtReport:generateDebtReports){
					BigDecimal verifiedIncome = ( null == generateDebtReport.getVerifiedIncome() ? BigDecimal.ZERO : generateDebtReport.getVerifiedIncome() );
					BigDecimal otherDebtAmount = ( null == generateDebtReport.getOtherDebtAmount() ? BigDecimal.ZERO : generateDebtReport.getOtherDebtAmount() );
					BigDecimal approveAmount = ( null == generateDebtReport.getApproveAmount() ? BigDecimal.ZERO : generateDebtReport.getApproveAmount() );
					BigDecimal commercialAmount = ( null == generateDebtReport.getCommercialAmount() ? BigDecimal.ZERO : generateDebtReport.getCommercialAmount() );
					BigDecimal consumerAmount = ( null == generateDebtReport.getConsumerAmount() ? BigDecimal.ZERO : generateDebtReport.getConsumerAmount() );
					BigDecimal totalDebtAmount = ( null == generateDebtReport.getTotalDebtAmount() ? BigDecimal.ZERO : generateDebtReport.getTotalDebtAmount() );
					
					StringBuilder text = new StringBuilder();
					StringBuilder textDate = new StringBuilder();
					logger.debug("generateConsent.getApplicationGroupNo() >> "+generateDebtReport.getApplicationNo());
						text.append(Formatter.displayText(generateDebtReport.getApplicationNo()))
							.append(",").append(Formatter.displayText(generateDebtReport.getCisNo()))
							.append(",").append(Formatter.displayText(generateDebtReport.getIdNo()))
							.append(",").append(Formatter.displayText(generateDebtReport.getConsentRefNo()))
							.append(",").append(Formatter.displayText(String.valueOf(verifiedIncome.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(String.valueOf(otherDebtAmount.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(String.valueOf(approveAmount.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(String.valueOf(commercialAmount.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(String.valueOf(consumerAmount.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(String.valueOf(totalDebtAmount.setScale(2, BigDecimal.ROUND_HALF_UP))))
							.append(",").append(Formatter.displayText(generateDebtReport.getProductType()))
							.append(",").append(Formatter.displayText(generateDebtReport.getSourceOfApp()));
						textDate.append(",").append(Formatter.display(generateDebtReport.getDataDate(), Formatter.EN, Formatter.Format.yyyy_MM_dd));
					logger.debug("text >> "+text);
					writer.write(text.toString());
					writer.write(textDate.toString());
					writer.write(System.getProperty("line.separator"));
				
				}
				InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
					infBatchLog.setRefId(systemDate);
					infBatchLog.setCreateBy(SYSTEM_USER_ID);
					infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
					infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
					infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("GENERATE_DEBTREPORT_MODULE_ID"));
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
				dao.insertInfBatchLog(infBatchLog);
			}
			
			//delete bot debt amount data date > 90
			debtDAO.deleteBotDebtAmount(systemDate);
			
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
