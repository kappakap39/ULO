package com.eaf.inf.batch.ulo.letter.reject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
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
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterLogDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterSortingContent;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchRejectLetter  extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchRejectLetter.class);
	
	String REJECT_LETTER_OUTPUT_NAME_PAPER_T1 = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T1");
	String REJECT_LETTER_OUTPUT_NAME_EMAIL_T1 = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_EMAIL_T1");
	String REJECT_LETTER_OUTPUT_NAME_PAPER_T2 = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T2");
	String REJECT_LETTER_OUTPUT_NAME_EMAIL_T2 = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_EMAIL_T2");
	
	String REJECT_LETTER_OUTPUT_NAME_PAPER_T1_EMPTY = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T1_EMPTY");
	String REJECT_LETTER_OUTPUT_NAME_EMAIL_T1_EMPTY = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_EMAIL_T1_EMPTY");
	String REJECT_LETTER_OUTPUT_NAME_PAPER_T2_EMPTY = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T2_EMPTY");
	String REJECT_LETTER_OUTPUT_NAME_EMAIL_T2_EMPTY = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_EMAIL_T2_EMPTY");
	
	String REJECT_LETTER_INTERFACE_STATUS_ERROR = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_INTERFACE_STATUS_ERROR");
	
	String REJECT_LETTER_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_FILE_ENCODING");
	
	//KPL Additional
	String REJECT_LETTER_OUTPUT_NAME_PAPER_T3 = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T3");
	String REJECT_LETTER_OUTPUT_NAME_EMAIL_T3 = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_EMAIL_T3");
	String REJECT_LETTER_OUTPUT_NAME_PAPER_T3_EMPTY = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_PAPER_T3_EMPTY");
	String REJECT_LETTER_OUTPUT_NAME_EMAIL_T3_EMPTY = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_OUTPUT_NAME_EMAIL_T3_EMPTY");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		 try {
//			 ProcessTaskDataM processTask = new ProcessTaskDataM("REJECT_LETTER_TASK");
			 ProcessTaskDataM processTask = new ProcessTaskDataM("REJECT_LETTER_ALL_APP_TASK");
			 ProcessTaskManager processTaskManager = new ProcessTaskManager();
				processTaskManager.run(processTask);
				
			ArrayList<TaskExecuteDataM> taskExecuteDataMList = processTask.getTaskExecutes();
			Vector<RejectLetterInterfaceResponse> rejectResponseList = sortTaskContent(taskExecuteDataMList);
			
			generatePostContent(rejectResponseList);
			writeTextFiles(rejectResponseList, infBatchResponse);
			createInfBatchLog(taskExecuteDataMList,rejectResponseList);
			int total = taskExecuteDataMList.size();
			int success = 0;
			int warning = 0;
			int error = 0;
			for(TaskExecuteDataM taskExecute : taskExecuteDataMList){
				if(InfBatchConstant.ResultCode.SUCCESS.equals(taskExecute.getResultCode())){
					success++;
				}else if(InfBatchConstant.ResultCode.WARNING.equals(taskExecute.getResultCode())){
					warning++;
				}else{
					error++;
				}
			}
			infBatchResponse.setTotalSuccess(success);
			infBatchResponse.setTotalFail(error);
			infBatchResponse.setTotalTask(total);
			infBatchResponse.setTotalWarning(warning);
			infBatchResponse.setTotalValid(success+error);
			
			processTask.setConfigClassName(InfBatchProperty.getInfBatchConfig("REJECT_LETTER_RESULT"));			
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	private Vector<RejectLetterInterfaceResponse> sortTaskContent(ArrayList<TaskExecuteDataM>  taskExecuteDataMList)throws Exception{
		Vector<RejectLetterInterfaceResponse> responseList = new Vector<RejectLetterInterfaceResponse>();
		if(!InfBatchUtil.empty(taskExecuteDataMList)){
			for(TaskExecuteDataM taskExecute : taskExecuteDataMList){
				ArrayList<RejectLetterInterfaceResponse> rejectResponseList = (ArrayList<RejectLetterInterfaceResponse>)taskExecute.getResponseObject();
				if(!InfBatchUtil.empty(rejectResponseList)){
					for(RejectLetterInterfaceResponse rejectResponse : rejectResponseList){
						if(!InfBatchUtil.empty(rejectResponse)){
							responseList.add(rejectResponse);
						}
					}
				}
			}
		}
		if(!InfBatchUtil.empty(responseList)){
			Collections.sort(responseList, new Comparator<RejectLetterInterfaceResponse>() {
				 @Override
		         public int compare(RejectLetterInterfaceResponse reject1, RejectLetterInterfaceResponse reject2) {
					 String zipcode1 = "";
					 String zipcode2 = "";
					 RejectLetterSortingContent sorting1 = reject1.getRejectLetterSortingContent();
					 if(!InfBatchUtil.empty(sorting1)){
						 if(!InfBatchUtil.empty(sorting1.getZipcode())) {
							 zipcode1 = sorting1.getZipcode();
						 }
					 }
					 RejectLetterSortingContent sorting2 = reject2.getRejectLetterSortingContent();
					 if(!InfBatchUtil.empty(sorting2)){
						 if(!InfBatchUtil.empty(sorting2.getZipcode())) {
							 zipcode2 = sorting2.getZipcode();
						 }
					 }
					 return zipcode1.compareTo(zipcode2);
				 }
		    });
		}
		return responseList;
	}
	private void generatePostContent(Vector<RejectLetterInterfaceResponse> rejectResponseList) {
		String REJECT_LETTER_DUMMY_FIELD = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_DUMMY_FIELD");
		RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
		for(RejectLetterInterfaceResponse rejectResponse : rejectResponseList){
			try {
				if(!InfBatchUtil.empty(rejectResponse))
				{
					
					String language = rejectResponse.getLanguageFlag();
					String letterNo = (!Util.empty(rejectResponse.getContentT3())) ? rejectResponse.getContentT3() : dao.generateLetterNo(language) ;
					
					String content1 = rejectResponse.getContentT1();
					String content2 = rejectResponse.getContentT2();
					String newContent1 = "";
					String newContent2 = "";
					
					if(!InfBatchUtil.empty(content1)){
						newContent1 = RejectLetterUtil.getReplaceDummyContent(content1, REJECT_LETTER_DUMMY_FIELD, letterNo);
					}
					if(!InfBatchUtil.empty(newContent1)){
						rejectResponse.setContentT1(newContent1);
					}
					if(!InfBatchUtil.empty(content2)){
						newContent2 = RejectLetterUtil.getReplaceDummyContent(content2, REJECT_LETTER_DUMMY_FIELD, letterNo);
					}
					if(!InfBatchUtil.empty(newContent2)){
						rejectResponse.setContentT2(newContent2);
					}
					
					//KPL Additional
					//String content3 = rejectResponse.getContentT3();
					//content3 already set LETTER_NO
					
					rejectResponse.setLetterNo(letterNo);
					RejectLetterLogDataM rejectLetterLogDataM = rejectResponse.getRejectLetterLogDataM();
					if(!InfBatchUtil.empty(rejectLetterLogDataM)){
						rejectLetterLogDataM.setLetterNo(letterNo);
						//insertInfBatchLog(rejectLetterLogDataM);
					}
					
				}
			}
			catch (Exception e) 
			{
				rejectResponse.setResultCode(InfBatchConstant.StatusDesc.FAIL);
			}
		}
	}
	
	private void insertInfBatchLog(RejectLetterLogDataM rejectLetterLogDataM){
	   	 Connection conn = InfBatchObjectDAO.getConnection();	
	   	 try {
	   		 InfDAO dao = InfFactory.getInfDAO();
	   		 conn.setAutoCommit(false);
	   		 ArrayList<String> aplicationRecordIds = rejectLetterLogDataM.getAplicationRecordIds();
	   		 if(!InfBatchUtil.empty(aplicationRecordIds)){
	   			 for(String applicationRecordId : aplicationRecordIds){
	   				 InfBatchLogDataM infBatchLogDataM = new InfBatchLogDataM();
	   				 infBatchLogDataM.setApplicationRecordId(applicationRecordId);
	   				 infBatchLogDataM.setInterfaceCode(rejectLetterLogDataM.getInterfaceCode());
	   				 infBatchLogDataM.setInterfaceStatus(rejectLetterLogDataM.getInterfaceStatus());
	   				 infBatchLogDataM.setSystem01(rejectLetterLogDataM.getLetterType());
	   				 infBatchLogDataM.setSystem02(rejectLetterLogDataM.getLanguage());
	   				 infBatchLogDataM.setSystem03(rejectLetterLogDataM.getSendTo());
	   				 infBatchLogDataM.setSystem06(rejectLetterLogDataM.getTemplateId());
	   				 infBatchLogDataM.setRefId(rejectLetterLogDataM.getLetterNo());
	   				 infBatchLogDataM.setApplicationGroupId(rejectLetterLogDataM.getApplicationGroupId());
	   				 infBatchLogDataM.setLifeCycle(rejectLetterLogDataM.getLifeCycle());
	   				 infBatchLogDataM.setInterfaceDate(InfBatchProperty.getTimestamp());
	   				 infBatchLogDataM.setCreateDate(InfBatchProperty.getTimestamp());
	   				 dao.insertInfBatchLog(infBatchLogDataM, conn);
	   			 }
	   		 }
	   		 conn.commit();
			}catch (Exception e) {
				logger.fatal("ERROR",e);
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
	    }
	
	private void writeTextFiles(ArrayList<TaskExecuteDataM>  taskExecuteDataMList,InfBatchResponseDataM infBatchResponse){
		OutputStreamWriter writer_letter_paper_T1 = null;
		OutputStreamWriter writer_letter_email_T1 = null;
		OutputStreamWriter writer_letter_paper_T2 = null;
		OutputStreamWriter writer_letter_email_T2 = null;
		try {
			String REJECT_LETTER_OUTPUT_PATH_T1 = PathUtil.getPath("REJECT_LETTER_OUTPUT_PATH_T1");
			String REJECT_LETTER_OUTPUT_PATH_T2 = PathUtil.getPath("REJECT_LETTER_OUTPUT_PATH_T2");
			FileControl.mkdir(REJECT_LETTER_OUTPUT_PATH_T1);
			FileControl.mkdir(REJECT_LETTER_OUTPUT_PATH_T2);
			if(!InfBatchUtil.empty(taskExecuteDataMList)){
				StringBuilder letterEmailT1 = new StringBuilder("");
				StringBuilder letterPaperT1 = new StringBuilder("");
				StringBuilder letterEmailT2 = new StringBuilder("");
				StringBuilder letterPaperT2 = new StringBuilder("");
				for(TaskExecuteDataM taskExecute : taskExecuteDataMList){
					ArrayList<RejectLetterInterfaceResponse> rejectResponseList = (ArrayList<RejectLetterInterfaceResponse>)taskExecute.getResponseObject();
					if(!InfBatchUtil.empty(rejectResponseList)){
						for(RejectLetterInterfaceResponse rejectResponse : rejectResponseList){
							logger.debug("RejectResponse : "+rejectResponse);
							if(!InfBatchUtil.empty(rejectResponse)){
								logger.debug("LetterType : "+rejectResponse.getLetterType());
								if(!InfBatchUtil.empty(rejectResponse.getContentT1())){
									if(RejectLetterUtil.EMAIL.equals(rejectResponse.getLetterType())){
										letterEmailT1.append(rejectResponse.getContentT1());
									}else if(RejectLetterUtil.PAPER.equals(rejectResponse.getLetterType())){
										letterPaperT1.append(rejectResponse.getContentT1());
									}
								}
								if(!InfBatchUtil.empty(rejectResponse.getContentT2())){
									if(RejectLetterUtil.EMAIL.equals(rejectResponse.getLetterType())){
										letterEmailT2.append(rejectResponse.getContentT2());
									}else if(RejectLetterUtil.PAPER.equals(rejectResponse.getLetterType())){
										letterPaperT2.append(rejectResponse.getContentT2());
									}
								}	
							}														
						}
					}
				}
				
				String emailOutputpath_T1 = "";
				if(!InfBatchUtil.empty(letterEmailT1.toString())){
					emailOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T1);
					writer_letter_email_T1 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
					writer_letter_email_T1.write(letterEmailT1.toString());
					writer_letter_email_T1.write("99");					
				}else{
					emailOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T1_EMPTY);
					writer_letter_email_T1 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
				}
				logger.debug("emailOutputpath_T1 >> "+emailOutputpath_T1);
				
				String paperOutputpath_T1 = "";
				if(!InfBatchUtil.empty(letterPaperT1.toString())){
					paperOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T1);
					writer_letter_paper_T1 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
					writer_letter_paper_T1.write(letterPaperT1.toString());
					writer_letter_paper_T1.write("99");
				}else{
					paperOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T1_EMPTY);
					writer_letter_paper_T1 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
				}
				logger.debug("paperOutputpath_T1 >> "+paperOutputpath_T1);
				
				String emailOutputpath_T2 = "";
				if(!InfBatchUtil.empty(letterEmailT2.toString())){
					emailOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T2);
					writer_letter_email_T2 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
					writer_letter_email_T2.write(letterEmailT2.toString());
					writer_letter_email_T2.write("99");
				}else{
					emailOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T2_EMPTY);
					writer_letter_email_T2 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
				}
				logger.debug("emailOutputpath_T2 >> "+emailOutputpath_T2);
				
				String paperOutputpath_T2 = "";
				if(!InfBatchUtil.empty(letterPaperT2.toString())){
					paperOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T2);
					writer_letter_paper_T2 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
					writer_letter_paper_T2.write(letterPaperT2.toString());
					writer_letter_paper_T2.write("99");
				}else{
					paperOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T2_EMPTY);
					writer_letter_paper_T2 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
				}
				logger.debug("paperOutputpath_T2 >> "+paperOutputpath_T2);
				
				infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());			
		}finally{
			if(null != writer_letter_paper_T1){
				try{
					writer_letter_paper_T1.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
			if(null != writer_letter_email_T1){
				try{
					writer_letter_email_T1.close();
				}catch(Exception e){
					logger.fatal("ERROR",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
			if(null != writer_letter_paper_T2){
				try{
					writer_letter_paper_T2.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
			if(null != writer_letter_email_T2){
				try{
					writer_letter_email_T2.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
		}
	}
	private void writeTextFiles(Vector<RejectLetterInterfaceResponse> rejectResponseList,InfBatchResponseDataM infBatchResponse){
		OutputStreamWriter writer_letter_paper_T1 = null;
		OutputStreamWriter writer_letter_email_T1 = null;
		OutputStreamWriter writer_letter_paper_T2 = null;
		OutputStreamWriter writer_letter_email_T2 = null;
		
		//KPL Additional
		//OutputStreamWriter writer_letter_paper_T3 = null;
		//OutputStreamWriter writer_letter_email_T3 = null;
		
		try {
			String REJECT_LETTER_OUTPUT_PATH_T1 = PathUtil.getPath("REJECT_LETTER_OUTPUT_PATH_T1");
			String REJECT_LETTER_OUTPUT_PATH_T2 = PathUtil.getPath("REJECT_LETTER_OUTPUT_PATH_T2");
			//String REJECT_LETTER_OUTPUT_PATH_T3 = PathUtil.getPath("REJECT_LETTER_OUTPUT_PATH_T3");
			FileControl.mkdir(REJECT_LETTER_OUTPUT_PATH_T1);
			FileControl.mkdir(REJECT_LETTER_OUTPUT_PATH_T2);
			//FileControl.mkdir(REJECT_LETTER_OUTPUT_PATH_T3);
			StringBuilder letterEmailT1 = new StringBuilder("");
			StringBuilder letterPaperT1 = new StringBuilder("");
			StringBuilder letterEmailT2 = new StringBuilder("");
			StringBuilder letterPaperT2 = new StringBuilder("");
			//StringBuilder letterEmailT3 = new StringBuilder("");
			//StringBuilder letterPaperT3 = new StringBuilder("");
			for(RejectLetterInterfaceResponse rejectResponse : rejectResponseList){
				try {
					logger.debug("RejectResponse : "+rejectResponse);
					if(!InfBatchUtil.empty(rejectResponse)){
						logger.debug("LetterType : "+rejectResponse.getLetterType());
						if(!InfBatchUtil.empty(rejectResponse.getContentT1())){
							if(RejectLetterUtil.EMAIL.equals(rejectResponse.getLetterType())){
								letterEmailT1.append(rejectResponse.getContentT1());
							}else if(RejectLetterUtil.PAPER.equals(rejectResponse.getLetterType())){
								letterPaperT1.append(rejectResponse.getContentT1());
							}
						}
						if(!InfBatchUtil.empty(rejectResponse.getContentT2())){
							if(RejectLetterUtil.EMAIL.equals(rejectResponse.getLetterType())){
								letterEmailT2.append(rejectResponse.getContentT2());
							}else if(RejectLetterUtil.PAPER.equals(rejectResponse.getLetterType())){
								letterPaperT2.append(rejectResponse.getContentT2());
							}
						}	
						//KPL Additional - Cancelled - Already write to DB in GenerateAllAppInterfaceTemplate3.java
						/*
						 * if(!InfBatchUtil.empty(rejectResponse.getContentT3())){
							if(RejectLetterUtil.EMAIL.equals(rejectResponse.getLetterType())){
								letterEmailT3.append(rejectResponse.getContentT3());
							}else if(RejectLetterUtil.PAPER.equals(rejectResponse.getLetterType())){
								letterPaperT3.append(rejectResponse.getContentT3());
							}
						}
						*/
					}	
				} catch (Exception e) {
					rejectResponse.setResultCode(InfBatchConstant.StatusDesc.FAIL);
				}
			}
		
			
			String emailOutputpath_T1 = "";
			if(!InfBatchUtil.empty(letterEmailT1.toString())){
				emailOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T1);
				writer_letter_email_T1 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
				writer_letter_email_T1.write(letterEmailT1.toString());
				writer_letter_email_T1.write("99");					
			}else{
				emailOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T1_EMPTY);
				writer_letter_email_T1 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
			}
			logger.debug("emailOutputpath_T1 >> "+emailOutputpath_T1);
			
			String paperOutputpath_T1 = "";
			if(!InfBatchUtil.empty(letterPaperT1.toString())){
				paperOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T1);
				writer_letter_paper_T1 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
				writer_letter_paper_T1.write(letterPaperT1.toString());
				writer_letter_paper_T1.write("99");
			}else{
				paperOutputpath_T1 = REJECT_LETTER_OUTPUT_PATH_T1 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T1_EMPTY);
				writer_letter_paper_T1 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T1),REJECT_LETTER_FILE_ENCODING);
			}
			logger.debug("paperOutputpath_T1 >> "+paperOutputpath_T1);
			
			String emailOutputpath_T2 = "";
			if(!InfBatchUtil.empty(letterEmailT2.toString())){
				emailOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T2);
				writer_letter_email_T2 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
				writer_letter_email_T2.write(letterEmailT2.toString());
				writer_letter_email_T2.write("99");
			}else{
				emailOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_EMAIL_T2_EMPTY);
				writer_letter_email_T2 = new OutputStreamWriter(new FileOutputStream(emailOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
			}
			logger.debug("emailOutputpath_T2 >> "+emailOutputpath_T2);
			
			String paperOutputpath_T2 = "";
			if(!InfBatchUtil.empty(letterPaperT2.toString())){
				paperOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T2);
				writer_letter_paper_T2 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
				writer_letter_paper_T2.write(letterPaperT2.toString());
				writer_letter_paper_T2.write("99");
			}else{
				paperOutputpath_T2 = REJECT_LETTER_OUTPUT_PATH_T2 +File.separator+RejectLetterUtil.generateRejectLetterFileName(REJECT_LETTER_OUTPUT_NAME_PAPER_T2_EMPTY);
				writer_letter_paper_T2 = new OutputStreamWriter(new FileOutputStream(paperOutputpath_T2),REJECT_LETTER_FILE_ENCODING);
			}
			logger.debug("paperOutputpath_T2 >> "+paperOutputpath_T2);
			
			//No text output for T3 (KPL) - output data already write to ORIG_APP.LETTER_HISTORY
			
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());			
		}finally{
			if(null != writer_letter_paper_T1){
				try{
					writer_letter_paper_T1.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
			if(null != writer_letter_email_T1){
				try{
					writer_letter_email_T1.close();
				}catch(Exception e){
					logger.fatal("ERROR",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
			if(null != writer_letter_paper_T2){
				try{
					writer_letter_paper_T2.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
			if(null != writer_letter_email_T2){
				try{
					writer_letter_email_T2.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
				}
			}
		}
	}
	
    private void insertInfBatchLog(ArrayList<TaskExecuteDataM>  taskExecuteDataMList){
   	 Connection conn = InfBatchObjectDAO.getConnection();	
   	 try {
   		InfDAO dao = InfFactory.getInfDAO();
   		conn.setAutoCommit(false);
   		if(!InfBatchUtil.empty(taskExecuteDataMList)){
			for(TaskExecuteDataM taskExecute :taskExecuteDataMList){
				ArrayList<RejectLetterInterfaceResponse>  rejectResponseList =(ArrayList<RejectLetterInterfaceResponse>)taskExecute.getResponseObject();
				if(!InfBatchUtil.empty(rejectResponseList)){
					for(RejectLetterInterfaceResponse rejectResponse : rejectResponseList){
						ArrayList<String> aplicationRecordIds =(ArrayList<String>)rejectResponse.getInterfaceObject();
				   		 if(!InfBatchUtil.empty(aplicationRecordIds)){
				   			 for(String applicationRecordId : aplicationRecordIds){
				   				 InfBatchLogDataM infBatchLogDataM = new InfBatchLogDataM();
				   				 infBatchLogDataM.setApplicationRecordId(applicationRecordId);
				   				 infBatchLogDataM.setInterfaceCode(rejectResponse.getInterfaceCode());
				   				 infBatchLogDataM.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_ERROR);
				   				 infBatchLogDataM.setRefId(rejectResponse.getLetterNo());
				   				 infBatchLogDataM.setInterfaceDate(InfBatchProperty.getTimestamp());
				   				 infBatchLogDataM.setCreateDate(InfBatchProperty.getTimestamp());
				   				 dao.insertInfBatchLog(infBatchLogDataM, conn);
				   			 }
				   		 }
					}
				}
			}		
   		}
   		 conn.commit();
		}catch (Exception e) {
			logger.fatal("ERROR ",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
			}			
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			
		}
    }
    private void createWriteFailedLog(InfBatchResponseDataM infBatchResponse,ArrayList<TaskExecuteDataM>  taskExecuteDataMList){
    	if(InfBatchConstant.ResultCode.FAIL.equals(infBatchResponse.getResultCode())){
    		insertInfBatchLog(taskExecuteDataMList);
    	}
    }
    private void createInfBatchLog(ArrayList<TaskExecuteDataM>  taskExecuteDataMList,Vector<RejectLetterInterfaceResponse> rejectResponseList){
    	Connection conn = InfBatchObjectDAO.getConnection();
       	 try {
       		InfDAO dao = InfFactory.getInfDAO();
       		conn.setAutoCommit(false);
       		// Fail
       		if(!InfBatchUtil.empty(taskExecuteDataMList)){
    			for(TaskExecuteDataM taskExecute :taskExecuteDataMList){
    				if(taskExecute.getResultCode().equals(InfBatchConstant.ResultCode.FAIL)
    						||taskExecute.getResultCode().equals(InfBatchConstant.ResultCode.WARNING)){
    					ArrayList<InfBatchLogDataM> infBatchLogs = (ArrayList<InfBatchLogDataM>)taskExecute.getFailObject();
    					if(!InfBatchUtil.empty(infBatchLogs)){
    						for(InfBatchLogDataM infBatchLog : infBatchLogs){
    							if(!InfBatchUtil.empty(infBatchLog)){
    								dao.insertInfBatchLog(infBatchLog,conn);
    							}
    						}
    					}
//    					RejectLetterDataM rejectLetter = (RejectLetterDataM)taskExecute.getFailObject();
//    					String errorMessage = taskExecute.getResultDesc();
//    					if(!InfBatchUtil.empty(rejectLetter)){
//    						ArrayList<RejectLetterApplicationDataM> applications = rejectLetter.getRejectLetterApplications();
//    						if(!InfBatchUtil.empty(applications)){
//    							logger.debug("applications size : "+applications.size());
//    							for(RejectLetterApplicationDataM application : applications){
//    								 InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
//	    								 infBatchLog.setApplicationRecordId(application.getApplicationRecordId());
//	    								 infBatchLog.setInterfaceCode(REJECT_LETTER_INTERFACE_CODE);
//	    								 infBatchLog.setInterfaceStatus(REJECT_LETTER_INTERFACE_STATUS_ERROR);
//	    								 infBatchLog.setApplicationGroupId(rejectLetter.getApplicationGroupId());
//	    								 infBatchLog.setLifeCycle(rejectLetter.getLifeCycle());
//	    								 infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
//	    								 infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
//	    								 infBatchLog.setLogMessage(errorMessage);
//					   				 //dao.insertInfBatchLog(infBatchLog, conn);
//	    							 if(!rejectLetterBatchLog.exist(infBatchLog)){
//	    								 rejectLetterBatchLog.addInfBatchLog(infBatchLog);
//	    							 }
//    							}
//    						}
//    					}
    				}
    			}
       		}
       		// Success
       		for(RejectLetterInterfaceResponse rejectResponse : rejectResponseList){
       			RejectLetterLogDataM rejectLetterLogDataM = rejectResponse.getRejectLetterLogDataM();
				if(!InfBatchUtil.empty(rejectLetterLogDataM)){
					ArrayList<String> aplicationRecordIds = rejectLetterLogDataM.getAplicationRecordIds();
			   		 if(!InfBatchUtil.empty(aplicationRecordIds)){
			   			 for(String applicationRecordId : aplicationRecordIds){
			   				 InfBatchLogDataM infBatchLogDataM = new InfBatchLogDataM();
				   				 infBatchLogDataM.setApplicationRecordId(applicationRecordId);
				   				 infBatchLogDataM.setInterfaceCode(rejectLetterLogDataM.getInterfaceCode());
				   				 infBatchLogDataM.setInterfaceStatus(rejectLetterLogDataM.getInterfaceStatus());
				   				 infBatchLogDataM.setSystem01(rejectLetterLogDataM.getLetterType());
				   				 infBatchLogDataM.setSystem02(rejectLetterLogDataM.getLanguage());
				   				 infBatchLogDataM.setSystem03(rejectLetterLogDataM.getSendTo());
				   				 infBatchLogDataM.setSystem05(rejectLetterLogDataM.getSubInterfaceCode());
				   				 infBatchLogDataM.setSystem06(rejectLetterLogDataM.getTemplateId());
				   				 infBatchLogDataM.setRefId(rejectLetterLogDataM.getLetterNo());
				   				 infBatchLogDataM.setApplicationGroupId(rejectLetterLogDataM.getApplicationGroupId());
				   				 infBatchLogDataM.setLifeCycle(rejectLetterLogDataM.getLifeCycle());
				   				 infBatchLogDataM.setInterfaceDate(InfBatchProperty.getTimestamp());
				   				 infBatchLogDataM.setCreateDate(InfBatchProperty.getTimestamp());
			   				 dao.insertInfBatchLog(infBatchLogDataM, conn);
			   			 }
			   		 }
				}
       		}
       		 conn.commit();
    		}catch (Exception e) {
    			logger.fatal("ERROR ",e);
    			try {
    				conn.rollback();
    			} catch (SQLException e1) {
    				logger.fatal("ERROR ",e1);
    			}			
    		}finally{
    			try{
    				InfBatchObjectDAO.closeConnection(conn);
    			}catch(Exception e){
    				logger.fatal("ERROR ",e);
    			}
    		}
  	}
}
