package com.eaf.inf.batch.ulo.report.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.api.ExecuteTaskManager;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;

public class InfBatchReportOnRequest extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchReportOnRequest.class);
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			ProcessTaskDataM processTask = new ProcessTaskDataM(InfBatchConstant.task.REPORT_TASK);
			ExecuteTaskManager executeTask = new ExecuteTaskManager(processTask);
				generateReportOnRequest(executeTask);
			executeTask.shutdown();
			InfBatchResultController.setExecuteTaskResultData(infBatchResponse, processTask);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public void generateReportOnRequest(ExecuteTaskManager executeTask) throws Exception{
		int RE_R004_COUNT = 1;
		int RE_R006_COUNT = 1;
		int RE_R012_COUNT = 1;
		
		ArrayList<InfReportJobDataM> infReportJobList = ReportFileFactory.getReportFileDAO().getInfReportJob();
		for(InfReportJobDataM infReportJob : infReportJobList){
			String REPORT_TYPE = infReportJob.getReportType();
			String taskId = getTaskId(REPORT_TYPE+"_"+REPORT_ON_REQUEST);
			String generateType = getGenerateType(REPORT_TYPE+"_"+REPORT_ON_REQUEST);
			if(InfBatchConstant.RE_R004.class.getSimpleName().equals(REPORT_TYPE)){
				executeTask.execute(getTask(taskId, generateType, infReportJob, RE_R004_COUNT++));
			}else if(InfBatchConstant.RE_R006.class.getSimpleName().equals(REPORT_TYPE)){
				executeTask.execute(getTask(taskId, generateType, infReportJob, RE_R006_COUNT++));
			}else if(InfBatchConstant.RE_R012.equals(REPORT_TYPE)){
				executeTask.execute(getTask(taskId, generateType, infReportJob, RE_R012_COUNT++));
			}
		}
	}
}
