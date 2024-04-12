package com.eaf.core.ulo.common.inf;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.FileUtil;
import com.eaf.core.ulo.common.util.GenerateFileUtil;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;


public abstract class InfBatchHelper implements InfBatch{
	private static transient Logger logger = Logger.getLogger(InfBatchHelper.class);
	public static String BATCH_ID = InfBatchConstant.ReportParam.BATCH_ID;
	public static String MODULE_ID = InfBatchConstant.ReportParam.MODULE_ID;
	public static String OUTPUT_PATH = InfBatchConstant.ReportParam.OUTPUT_PATH;
	public static String OUTPUT_NAME = InfBatchConstant.ReportParam.OUTPUT_NAME;
	public static String REPORT_TEMPLATE = InfBatchConstant.ReportParam.REPORT_TEMPLATE;
	public static String REPORT_ON_REQUEST = InfBatchConstant.ReportParam.REPORT_ON_REQUEST;
	public static String TASK_ID = InfBatchConstant.ReportParam.TASK_ID;
	public static String GENERATE = InfBatchConstant.ReportParam.GENERATE;
	public static String GENERATE_TYPE = InfBatchConstant.ReportParam.GENERATE_TYPE;
	public static String FILE_ENCODING = InfBatchConstant.ReportParam.FILE_ENCODING;
	public static String FORCE_BACKUP = InfBatchConstant.ReportParam.FORCE_BACKUP;
	
	public InfResultDataM generateFile(GenerateFileDataM generateFile) throws Exception{
		String generateId = generateFile.getGenerateId();
		InfResultDataM infResult = new InfResultDataM();
		GenerateFileInf generateFileInf = GenerateFileUtil.getGenerateFileInf(generateId);
		if(null != generateFileInf){
			infResult = generateFileInf.create(generateFile);
		}
		return infResult;
	}
	
	public InfResultDataM generateFile(GenerateFileDataM generateFile,Connection conn) throws Exception{
		String generateId = generateFile.getGenerateId();
		InfResultDataM infResult = new InfResultDataM();
		GenerateFileInf generateFileInf = GenerateFileUtil.getGenerateFileInf(generateId);
		if(null != generateFileInf){
			infResult = generateFileInf.create(generateFile,conn);
		}
		return infResult;
	}
	
	public GenerateFileDataM getGenerateFileParamData(String batchId, String generateType, String formatDate) throws Exception{
		GenerateFileDataM generateFile = new GenerateFileDataM();
			String moduleId = InfBatchProperty.getInfBatchConfig(batchId+"_"+ MODULE_ID);
			generateFile.setUniqueId(moduleId);
			generateFile.setGenerateId(generateType+"_"+GENERATE);
			generateFile.setFileOutputPath(PathUtil.getPath(batchId, OUTPUT_PATH));
			generateFile.setFileOutputName(FileUtil.getBatchOutputParamFileName(batchId, formatDate));
			generateFile.setEncode(InfBatchProperty.getInfBatchConfig(batchId+"_"+FILE_ENCODING));
			logger.info("PATH >> "+generateFile.getFileOutputPath());
			logger.info("FILE_NAME >> "+generateFile.getFileOutputName());
			logger.info("FILE_PATH >> "+generateFile.getFileOutputPath()+File.separator+generateFile.getFileOutputName());
		return generateFile;
	}
	
	public GenerateFileDataM getGenerateFileData(String batchId, String generateType, String formatDate) throws Exception{
		GenerateFileDataM generateFile = new GenerateFileDataM();
			String moduleId = InfBatchProperty.getInfBatchConfig(batchId+"_"+ MODULE_ID);
			generateFile.setUniqueId(moduleId);
			generateFile.setGenerateId(generateType+"_"+GENERATE);
			generateFile.setFileOutputPath(PathUtil.getPath(batchId, OUTPUT_PATH));
			generateFile.setFileOutputName(FileUtil.getBatchOutputFileName(batchId, formatDate));
			generateFile.setEncode(InfBatchProperty.getInfBatchConfig(batchId+"_"+FILE_ENCODING));
			logger.info("PATH >> "+generateFile.getFileOutputPath());
			logger.info("FILE_NAME >> "+generateFile.getFileOutputName());
			logger.info("FILE_PATH >> "+generateFile.getFileOutputPath()+File.separator+generateFile.getFileOutputName());
		return generateFile;
	}
	
	public GenerateFileDataM getGenerateTextFileData(String batchId, String generateType, String formatDate) throws Exception{
		GenerateFileDataM generateFile = new GenerateFileDataM();
			String moduleId = InfBatchProperty.getInfBatchConfig(batchId+"_"+ MODULE_ID);
			generateFile.setUniqueId(moduleId);
			generateFile.setGenerateId(generateType+"_"+GENERATE);
			generateFile.setFileOutputPath(PathUtil.getPath(batchId, OUTPUT_PATH));
			generateFile.setFileOutputName(FileUtil.getTextOutputFileName(batchId, formatDate));
			generateFile.setEncode(InfBatchProperty.getInfBatchConfig(batchId+"_"+FILE_ENCODING));
			logger.info("PATH >> "+generateFile.getFileOutputPath());
			logger.info("FILE_NAME >> "+generateFile.getFileOutputName());
			logger.info("FILE_PATH >> "+generateFile.getFileOutputPath()+File.separator+generateFile.getFileOutputName());
		return generateFile;
	}
	
	public GenerateFileDataM getGenerateTextFileParamData(String batchId, String generateType, String formatDate) throws Exception{
		GenerateFileDataM generateFile = new GenerateFileDataM();
			String moduleId = InfBatchProperty.getInfBatchConfig(batchId+"_"+ MODULE_ID);
			generateFile.setUniqueId(moduleId);
			generateFile.setGenerateId(generateType+"_"+GENERATE);
			generateFile.setFileOutputPath(PathUtil.getPath(batchId, OUTPUT_PATH));
			generateFile.setFileOutputName(FileUtil.getTextOutputParamFileName(batchId, formatDate));
			generateFile.setEncode(InfBatchProperty.getInfBatchConfig(batchId+"_"+FILE_ENCODING));
			logger.info("PATH >> "+generateFile.getFileOutputPath());
			logger.info("FILE_NAME >> "+generateFile.getFileOutputName());
			logger.info("FILE_PATH >> "+generateFile.getFileOutputPath()+File.separator+generateFile.getFileOutputName());
		return generateFile;
	}

	//for normal report 
	public TaskDataM getTask(String taskId, String generateType) throws Exception{
		GenerateFileDataM generateFile = new GenerateFileDataM();
			generateFile.setUniqueId(taskId);
			generateFile.setGenerateId(generateType+"_"+GENERATE);
			generateFile.setFileTemplate(FileUtil.getFileTemplate(taskId));
			generateFile.setFileOutputPath(PathUtil.getPath(taskId, OUTPUT_PATH));
			generateFile.setReportPath(PathUtil.getJasperPath());
			generateFile.setFileOutputName(FileUtil.getReportOutputFileName(taskId));
		TaskObjectDataM taskObject = new TaskObjectDataM();
			taskObject.setObject(generateFile);
		TaskDataM task = new TaskDataM();
			task.setTaskId(taskId);
			task.setTaskObject(taskObject);
		return task;
	}
	
	//for BOT report 
	public TaskDataM getTask(String taskId, String generateType, String systemDate) throws Exception{
		GenerateFileDataM generateFile = new GenerateFileDataM();
			generateFile.setUniqueId(taskId);
			generateFile.setGenerateId(generateType+"_"+GENERATE);
			generateFile.setFileTemplate(FileUtil.getFileTemplate(taskId));
			generateFile.setFileOutputPath(PathUtil.getPath(taskId, OUTPUT_PATH));
			generateFile.setReportPath(PathUtil.getJasperPath());
			generateFile.setFileOutputName(FileUtil.getReportOutputFileName(taskId));
			generateFile.setSystemDate(InfBatchUtil.convertDate(systemDate, InfBatchConstant.SYSTEM_DATE_FORMAT));
		TaskObjectDataM taskObject = new TaskObjectDataM();
			taskObject.setObject(generateFile);
		TaskDataM task = new TaskDataM();
			task.setTaskId(taskId);
			task.setTaskObject(taskObject);
	return task;
	}
	
	//for report on request
	public TaskDataM getTask(String taskId, String generateType, InfReportJobDataM infReportJob, int orderNo) throws Exception{
		GenerateFileDataM generateFile = new GenerateFileDataM();
			generateFile.setOrderNo(orderNo);
			generateFile.setUniqueId(taskId);
			generateFile.setGenerateId(generateType+"_"+GENERATE);
			generateFile.setFileTemplate(FileUtil.getFileTemplate(taskId));
			generateFile.setFileOutputPath(PathUtil.getPath(taskId, OUTPUT_PATH));
			generateFile.setReportPath(PathUtil.getJasperPath());
			generateFile.setFileOutputName(FileUtil.getReportOutputFileName(taskId));
			generateFile.setObject(infReportJob);
		TaskObjectDataM taskObject = new TaskObjectDataM();
			taskObject.setObject(generateFile);
		TaskDataM task = new TaskDataM();
			task.setTaskId(taskId);
			task.setTaskObject(taskObject);
		return task;
	}
	
	public static List<String> getTaskIds(String batchId){
		List<String> taskIds = InfBatchProperty.getListInfBatchConfig(batchId+"_"+BATCH_ID);
		return taskIds;
	}
	
	public static String getTaskId(String batchId){
		String taskId = InfBatchProperty.getInfBatchConfig(batchId+"_"+TASK_ID);
		return taskId;
	}
	
	public static String getGenerateType(String batchId){
		String batchType = InfBatchProperty.getInfBatchConfig(batchId+"_"+GENERATE_TYPE);
		return batchType;
	}
	
	@Override
	public void postProcessAction(InfBatchRequestDataM infBatchRequest,InfBatchResponseDataM infBatchResponse) throws InfBatchException {
		String batchId = infBatchRequest.getBatchId();
		String forceBackUpFlag = InfBatchProperty.getInfBatchConfig(batchId+"_"+FORCE_BACKUP);
		if(InfBatchConstant.ResultCode.SUCCESS.equals(infBatchResponse.getResultCode())||InfBatchConstant.FLAG_YES.equals(forceBackUpFlag)){
			FileUtil.backUpBatchFile(batchId);
		}
	}
	
	@Override
	public void preProcessAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String batchId = infBatchRequest.getBatchId();
		FileUtil.clearBatchFile(batchId);
	}
}
