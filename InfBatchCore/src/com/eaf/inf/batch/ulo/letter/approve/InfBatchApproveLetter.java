package com.eaf.inf.batch.ulo.letter.approve;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ProcessTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.letter.approve.model.ApproveLetterDataM;
import com.eaf.inf.batch.ulo.letter.history.dao.LetterHistoryFactory;
import com.eaf.inf.batch.ulo.letter.history.model.LetterHistoryDataM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchApproveLetter extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchApproveLetter.class); 
	String APPROVE_LETTER_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_FILE_ENCODING");
	String GENERIC_TEMPLATE = InfBatchProperty.getInfBatchConfig("GENERIC_TEMPLATE");
	private static int MAX_TERM = 60;
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try {
			 ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.APPROVE_LETTER_TASK);
			 ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
			ArrayList<TaskExecuteDataM> taskExecuteList = processTask.getTaskExecutes();
			
			writeTextFiles(taskExecuteList, infBatchResponse);
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public void writeTextFiles(ArrayList<TaskExecuteDataM> taskExecuteList, InfBatchResponseDataM infBatchResponse) throws Exception{
		File file = null;
		Connection conn = InfBatchObjectDAO.getConnection();
		try{
			conn.setAutoCommit(false);
			
			String FILE_PATH = PathUtil.getPath("APPROVE_LETTER_OUTPUT_PATH");
			String OUTPUT_FILE_NAME = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_OUTPUT_NAME");
			String EMPTY_OUTPUT_FILE_NAME = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_OUTPUT_NAME_EMPTY");
			
			String APPROVE_LETTER_HEADER_RECTYPE = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_HEADER_RECTYPE");
			String APPROVE_LETTER_DETAIL_RECTYPE = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_DETAIL_RECTYPE");
			String APPROVE_LETTER_TAILER_RECTYPE = InfBatchProperty.getInfBatchConfig("APPROVE_LETTER_TAILER_RECTYPE");
			
			String LETTER_CHANNEL_PAPER = InfBatchProperty.getInfBatchConfig("LETTER_CHANNEL_PAPER");
			String LETTER_CHANNEL_EMAIL = InfBatchProperty.getInfBatchConfig("LETTER_CHANNEL_EMAIL");
			
			String EDOC_EMAIL_TEMPLATE_APPROVE_CUSTOMER = InfBatchProperty.getInfBatchConfig("EDOC_EMAIL_TEMPLATE_APPROVE_CUSTOMER");
			String EDOC_EMAIL_TEMPLATE_APPROVE_SELLER = InfBatchProperty.getInfBatchConfig("EDOC_EMAIL_TEMPLATE_APPROVE_SELLER");
			
			String KPL = InfBatchProperty.getInfBatchConfig("ORG_KPL");
			String LANGUAGE_TH = InfBatchProperty.getInfBatchConfig("LANGUAGE_TH");
			String LANGUAGE_OTHER = InfBatchProperty.getInfBatchConfig("LANGUAGE_OTHER");
			
			String KPL_LETTER_TEMPLATE_APPROVE_GENERIC = InfBatchProperty.getInfBatchConfig("KPL_LETTER_TEMPLATE_APPROVE_GENERIC");
			String KPL_LETTER_TEMPLATE_APPROVE_PAYROLL = InfBatchProperty.getInfBatchConfig("KPL_LETTER_TEMPLATE_APPROVE_PAYROLL");
			
			String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.yyyy_MM_dd);
			
			String RJ_LETTER_PREFIX_NO_TH = InfBatchProperty.getGeneralParam("RJ_LETTER_PREFIX_NO_TH");
			String KPL_AP_LETTER_PREFIX_NO = InfBatchProperty.getGeneralParam("KPL_AP_LETTER_PREFIX_NO");
			
			//logger.debug("FILE_PATH >> "+FILE_PATH);
			//FileControl.mkdir(FILE_PATH);
			
			//String CONTEXT = "";
			//String header = "";
			//String detail = "";
			//String tailer = "";
			int totalRecord = 0;
			
			//Header
			//header = APPROVE_LETTER_HEADER_RECTYPE + "|" + DATE;
			
			InfDAO dao = InfFactory.getInfDAO();
			for(TaskExecuteDataM taskExecute : taskExecuteList){
				ApproveLetterDataM approveLetter = (ApproveLetterDataM)taskExecute.getResponseObject();
				String TEMPLATE = approveLetter.getTemplateType();
				String letterNo = Util.empty(approveLetter.getLetterNo()) ? "" : 
					approveLetter.getLetterNo().replace(RJ_LETTER_PREFIX_NO_TH, KPL_AP_LETTER_PREFIX_NO);
				
				logger.debug("TEMPLATE : "+TEMPLATE);
				if(!InfBatchUtil.empty(TEMPLATE)){
					String detail = "";
					DecimalFormat FORMAT = new DecimalFormat("#,##0.00");
					FORMAT.setRoundingMode(RoundingMode.HALF_EVEN);

					try{
						//Build EDOCGEN text for KPL Approve
						String emailPrimary = approveLetter.getEmailPrimary();
						
						String letterChannel = ((!MConstant.FLAG.YES.equals(approveLetter.getLetterChannel())) && !Util.empty(emailPrimary)) ? LETTER_CHANNEL_EMAIL : LETTER_CHANNEL_PAPER ;
						//Defect#3429 - Change send method to PAPER Only
						//CR267 - use paper and email
						//String letterChannel = LETTER_CHANNEL_PAPER;
						
						int term = approveLetter.getTerm();
						BigDecimal loanAmt = approveLetter.getLoanAmt();
						BigDecimal intRate = approveLetter.getRateAmount();
						BigDecimal installmentAmt = approveLetter.getInstallmentAmount();
						String LANGUAGE = LANGUAGE_TH;
						String letterTemplate = (MConstant.FLAG_Y.equals(approveLetter.getPayrollFlag())) ? KPL_LETTER_TEMPLATE_APPROVE_PAYROLL : KPL_LETTER_TEMPLATE_APPROVE_GENERIC;
					
						//Detail
						StringBuilder body = new StringBuilder();
						body.append(APPROVE_LETTER_DETAIL_RECTYPE + "|");
						body.append(DATE + "|");
						body.append(letterNo + "|");
						body.append(approveLetter.getCisNo() + "|");
						body.append(approveLetter.getApplicationGroupNo() + "|");
						//body.append(approveLetter.getOrgId() + "|"); //PD_TP_DSC
						body.append(approveLetter.getProductNameThEn() + "|");
						body.append(FormatUtil.displayText(approveLetter.getName()) + "|");
						body.append(FormatUtil.displayText(approveLetter.getAddress1()) + "|");
						body.append(FormatUtil.displayText(approveLetter.getAddress2()) + "|");
						body.append(letterTemplate + "|"); //KPL14 Generic, KPL15 Payroll
						body.append("{SEND_METHOD}" + "|"); //1 = Generate PDF and put in Sharepoint ,3 = Generate PDF and send via Email
						body.append("{EMAIL_PRIMARY}" + "|"); //Email From
						body.append("{EDOC_EMAIL_TEMPLATE}" + "|"); //Email Template - TPL05 = Approve Customer , TPL06 = Approve Channel
						body.append("{LANGUAGE}" + "|"); //Language
						body.append("{ATTACH_FILE}" + "|"); //Attachment 0 = no attachment , 1 has attachment
						body.append("{MASTER_OR_COPY}" + "|"); //M = Master , C = Copy
						body.append(FormatUtil.display(loanAmt) + "|");
						body.append(term + "|");
						body.append(installmentAmt + "|");
						body.append(FormatUtil.display(intRate) + "|");
						body.append(FormatUtil.displayText(approveLetter.getAccountNo()) + "|");
						body.append(approveLetter.getFirstInstallmentMonthYear() + "|");
						body.append(approveLetter.getFirstInstallmentDay() + "|");
						body.append(DATE + "|");
						
						//Recursive Method append PRD_NO_X, PNP_AMT_X, INSTLMNT_AMT_X, PYMT_INT_AMT_X, PYMT_PNP_AMT_X, OTSND_BAL_X
						StringBuilder paymentBody = new StringBuilder();
						TotalList ttl = calcLoanRecursive(paymentBody, term, 0,  installmentAmt, loanAmt, intRate, new TotalList());
						
						body.append(ttl.getTotalInstallmentAmt() + "|");
						body.append(ttl.getTotalTon() + "|");
						body.append(ttl.getTotalDok());
						
						body.append(paymentBody.toString());
						
						//Append remaining pipes of empty set
						for(; term < 60 ; term++)
						{
							body.append("||||||");
						}
						
						detail += body.toString();
						logger.debug("Add ending pipe");
						detail += "|";
						
						//Write data to LETTER_HISTORY
						LetterHistoryDataM letterHistory = new LetterHistoryDataM();
						letterHistory.setLetterNo(letterNo);
						letterHistory.setAppGroupNo(approveLetter.getApplicationGroupNo());
						letterHistory.setIdNo(approveLetter.getIdNo());
						letterHistory.setFirstName(approveLetter.getThFirstName());
						letterHistory.setMidName((Util.empty(approveLetter.getThMidName())) ? "-" : approveLetter.getThMidName());
						letterHistory.setLastName(approveLetter.getThLastName());
						
						letterHistory.setDe2SubmitDate(FormatUtil.toDate(approveLetter.getFinalAppDecisionDate(), FormatUtil.EN));
						letterHistory.setLetterType("01"); //01 Approve , 02 Reject
						letterHistory.setLetterTemplate(letterTemplate);
						letterHistory.setLetterContent(detail);
						
						letterHistory.setSellerEmailAddress(null);
						letterHistory.setSellerEmailTemplate(EDOC_EMAIL_TEMPLATE_APPROVE_SELLER);
						letterHistory.setSellerSendMethod(LETTER_CHANNEL_EMAIL);
						letterHistory.setSellerLanguage(null);
						letterHistory.setSellerAttachFile(null);
						
						letterHistory.setCustomerSendMethod(letterChannel);
						letterHistory.setCustomerEmailTemplate(EDOC_EMAIL_TEMPLATE_APPROVE_CUSTOMER);
						letterHistory.setCustomerEmailAddress(emailPrimary);
						letterHistory.setCustomerLanguage(LANGUAGE);
						letterHistory.setCustomerAttachFile(LETTER_CHANNEL_EMAIL.equals(letterChannel) ? "1" : "0");
						letterHistory.setCustomerResendCount(0);
						
						//Always send to customer
						letterHistory.setSendFlag(MConstant.FLAG.YES);
						letterHistory.setCustomerSendFlag(MConstant.FLAG.YES);
						
						//Batch REJECT_LETTER_PDF will change this flag afterward
						letterHistory.setSellerSendFlag(MConstant.FLAG.NO);
						
						LetterHistoryFactory.getLetterHistoryDAO().createLetterHistory(letterHistory, conn);
						
						//Insert to batch log
						InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setApplicationRecordId(approveLetter.getApplicationRecordId());
						infBatchLog.setInterfaceCode(approveLetter.getModuleId());
						infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
						infBatchLog.setRefId(letterNo);
						infBatchLog.setSystem01(LETTER_CHANNEL_EMAIL.equals(letterChannel) ? "EMAIL" : "PAPER");
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						dao.insertInfBatchLog(infBatchLog, conn);
						
						totalRecord++;
						
					}catch(Exception e){
						logger.debug("ERROR ",e);
						InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
							infBatchLog.setApplicationRecordId(approveLetter.getApplicationRecordId());
							infBatchLog.setInterfaceCode(approveLetter.getModuleId());
							infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_ERROR);
							infBatchLog.setRefId(letterNo);
							infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
							infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						dao.insertInfBatchLog(infBatchLog, conn);
					}
				}
			}
			
			//Tailer
			//tailer = "\n" + APPROVE_LETTER_TAILER_RECTYPE + "|" + totalRecord;
			
			//CONTEXT = header + detail + tailer;
			
			/*if(!InfBatchUtil.empty(CONTEXT))
			{
				CONTEXT = CONTEXT.replaceAll("\n", System.lineSeparator());
				OUTPUT_FILE_NAME = OUTPUT_FILE_NAME.replace("YYYYMMDD", DATE);
				
				file = new File(FILE_PATH+File.separator+OUTPUT_FILE_NAME);
				FileUtils.writeStringToFile(file, CONTEXT,APPROVE_LETTER_FILE_ENCODING);
			}else
			{
				EMPTY_OUPUT_FILE_NAME = EMPTY_OUPUT_FILE_NAME.replace("YYYYMMDD", DATE);
				file = new File(FILE_PATH+File.separator+EMPTY_OUPUT_FILE_NAME);
				FileUtils.writeStringToFile(file, CONTEXT,APPROVE_LETTER_FILE_ENCODING);
			}
			logger.debug("KPL APPROVE - EDOCGEN OUTPUT FILE : "+file);*/
			
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR",e1);
			}
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		System.gc();
	}
	
	public static TotalList calcLoanRecursive(StringBuilder builder, int term, int cycle, BigDecimal installmentAmt, BigDecimal loanAmount,
			BigDecimal intRate, TotalList totalList)
	{
		if(cycle < term)
		{
			System.out.println("cycle : " + cycle);
				
			//PRD_NO_1 - cycle+1
			//PNP_AMT_1 - yodTon
			//INSTLMNT_AMT_1 - paypermonth
			//PYMT_INT_AMT_1 - dok
			//PYMT_PNP_AMT_1 - ton
			//OTSND_BAL_1 - tonkongkang
				
			BigDecimal yodTon = loanAmount;
			BigDecimal dok = loanAmount.multiply((intRate.divide(BigDecimal.valueOf(1200),8, RoundingMode.HALF_UP)));
			dok = dok.setScale(2, BigDecimal.ROUND_HALF_UP);	
			//If installmentAmt greater than loanAmount -> installmentAmt = loanAmount + dok
			if(installmentAmt.compareTo(loanAmount) > 0)
			{
				installmentAmt = loanAmount.add(dok);
			}
				
			BigDecimal ton = installmentAmt.subtract(dok);
		
			BigDecimal tonKongKang = loanAmount.subtract(ton);
			tonKongKang.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			if(yodTon.compareTo(BigDecimal.ZERO) > 0)
			{
				builder.append("|" + (cycle+1) + "|");
				builder.append(yodTon.setScale(2, BigDecimal.ROUND_HALF_UP) + "|");
				builder.append(installmentAmt.setScale(2, BigDecimal.ROUND_HALF_UP) + "|");
				builder.append(dok.setScale(2, BigDecimal.ROUND_HALF_UP) + "|");
				builder.append(ton.setScale(2, BigDecimal.ROUND_HALF_UP) + "|");
				builder.append(tonKongKang.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			else
			{
				builder.append("|" + (cycle+1) + "|0|0|0|0|0");
			}
				
			//System.out.println(builder.toString());
			
			totalList.setTotalInstallmentAmt(totalList.getTotalInstallmentAmt().add(installmentAmt).setScale(2, BigDecimal.ROUND_HALF_UP));
			totalList.setTotalTon(totalList.getTotalTon().add(ton).setScale(2, BigDecimal.ROUND_HALF_UP));
			totalList.setTotalDok(totalList.getTotalDok().add(dok).setScale(2, BigDecimal.ROUND_HALF_UP));
				
			cycle++;
			calcLoanRecursive(builder, term, cycle, installmentAmt, tonKongKang, intRate, totalList);
			
		}
		return totalList;
	}
	
	public class TotalList
	{
		private BigDecimal totalInstallmentAmt;
		private BigDecimal totalTon;
		private BigDecimal totalDok;
		
		public TotalList()
		{
			totalInstallmentAmt = new BigDecimal(0);
			totalTon = new BigDecimal(0);
			totalDok = new BigDecimal(0);
		}
		
		public BigDecimal getTotalInstallmentAmt() {
			return totalInstallmentAmt;
		}
		public void setTotalInstallmentAmt(BigDecimal totalInstallmentAmt) {
			this.totalInstallmentAmt = totalInstallmentAmt;
		}
		public BigDecimal getTotalTon() {
			return totalTon;
		}
		public void setTotalTon(BigDecimal totalTon) {
			this.totalTon = totalTon;
		}
		public BigDecimal getTotalDok() {
			return totalDok;
		}
		public void setTotalDok(BigDecimal totalDok) {
			this.totalDok = totalDok;
		}
	}
	
}
