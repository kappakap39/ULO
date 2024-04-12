package com.eaf.inf.batch.ulo.memo.core;

import java.io.File;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.inf.batch.ulo.memo.dao.InstructionMemoFactory;
import com.eaf.inf.batch.ulo.memo.excel.ExcelInstructionMemo;
import com.eaf.inf.batch.ulo.memo.excel.ExcelInstructionMemoClose;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoDataM;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoOutputDataM;

public class InstructionMemoTemplate extends InfBatchHelper{	
	private static transient Logger logger = Logger.getLogger(InstructionMemoTemplate.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
//		String applicationGroupId = "2052";
//		createInstructionMemoOpen(applicationGroupId);
//		createInstructionMemoClose(applicationGroupId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public static InstructionMemoOutputDataM createInstructionMemoOpen(String applicationGroupId){
		InstructionMemoOutputDataM output = new InstructionMemoOutputDataM();
		boolean generateResult = false;
		try{
			InstructionMemoDataM instructionMemo = InstructionMemoFactory.getInstructionMemoDAO().getInstructionMemo(applicationGroupId);
			if (instructionMemo != null) {
				String INSTRUCTION_MEMO_OPEN_TEMPLATE_PATH = PathUtil.getPath("INSTRUCTION_MEMO_OPEN_TEMPLATE_PATH");
				String INSTRUCTION_MEMO_OPEN_OUTPUT_PATH = PathUtil.getPath("INSTRUCTION_MEMO_OPEN_OUTPUT_PATH");
				INSTRUCTION_MEMO_OPEN_OUTPUT_PATH += applicationGroupId + File.separator;
				FileControl.mkdir(INSTRUCTION_MEMO_OPEN_OUTPUT_PATH);
				output.setOutputPath(INSTRUCTION_MEMO_OPEN_OUTPUT_PATH);
				String DDMMYYY = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.DDMMYYYY);
				String INSTRUCTION_MEMO_OPEN_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("INSTRUCTION_MEMO_OPEN_OUTPUT_NAME").replace("DDMMYYYY",DDMMYYY);
					   INSTRUCTION_MEMO_OPEN_OUTPUT_PATH += INSTRUCTION_MEMO_OPEN_OUTPUT_NAME;
				logger.info("INSTRUCTION_MEMO_OPEN_TEMPLATE_PATH >> "+INSTRUCTION_MEMO_OPEN_TEMPLATE_PATH);
				logger.info("INSTRUCTION_MEMO_OPEN_OUTPUT_PATH >> "+INSTRUCTION_MEMO_OPEN_OUTPUT_PATH);
				output.setOutputFile(INSTRUCTION_MEMO_OPEN_OUTPUT_PATH);
				ExcelInstructionMemo xlsMapper = new ExcelInstructionMemo(INSTRUCTION_MEMO_OPEN_TEMPLATE_PATH, INSTRUCTION_MEMO_OPEN_OUTPUT_PATH,instructionMemo);
				generateResult = xlsMapper.export();
			}
		}catch(Exception e){
			logger.fatal("ERROR ", e);
			generateResult = false;
		}
		logger.debug("generateResult >> "+generateResult);
		output.setGenerateResult(generateResult);
		return output;
	}
	public static InstructionMemoOutputDataM createInstructionMemoClose(String applicationGroupId){
		InstructionMemoOutputDataM output = new InstructionMemoOutputDataM();
		boolean generateResult = false;
		try{
			InstructionMemoDataM instructionMemo = InstructionMemoFactory.getInstructionMemoCloseDAO().getInstructionCloseMemo(applicationGroupId);
			if(instructionMemo != null){
				String INSTRUCTION_MEMO_CLOSE_TEMPLATE_PATH = InfBatchProperty.getInfBatchConfig("INSTRUCTION_MEMO_CLOSE_TEMPLATE_PATH");
				String INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH = InfBatchProperty.getInfBatchConfig("INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH");
				INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH += applicationGroupId + File.separator;
				FileControl.mkdir(INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH);
				output.setOutputPath(INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH);
				String DDMMYYY = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.DDMMYYYY);
				String INSTRUCTION_MEMO_OPEN_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("INSTRUCTION_MEMO_CLOSE_OUTPUT_NAME").replace("DDMMYYYY",DDMMYYY);
					INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH += INSTRUCTION_MEMO_OPEN_OUTPUT_NAME;
				output.setOutputFile(INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH);
				logger.info("INSTRUCTION_MEMO_CLOSE_TEMPLATE_PATH >> "+INSTRUCTION_MEMO_CLOSE_TEMPLATE_PATH);
				logger.info("INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH >> "+INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH);
				ExcelInstructionMemoClose xlsMapper = new ExcelInstructionMemoClose(INSTRUCTION_MEMO_CLOSE_TEMPLATE_PATH,INSTRUCTION_MEMO_CLOSE_OUTPUT_PATH,instructionMemo);
				generateResult = xlsMapper.export();
			}
		}catch(Exception e){
			logger.fatal("ERROR ", e);
			generateResult = false;
		}
		logger.debug("generateResult >> "+generateResult);
		output.setGenerateResult(generateResult);
		return output;
	}
}
