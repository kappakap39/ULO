package com.eaf.inf.batch.ulo.report.excel;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.ProcessingTimeDataM;
import com.eaf.inf.batch.ulo.report.model.SlaDataM;
import com.eaf.inf.batch.ulo.report.model.TaskReportData;

public class TaskReR006 implements TaskInf {
	private static transient Logger logger = Logger.getLogger(TaskReR006.class);
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception{
		ArrayList<SlaDataM> ReR006 = new ArrayList<SlaDataM>();
		String taskId = task.getTaskId();
		logger.debug("taskId : " + taskId);
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		TaskObjectDataM taskObject = task.getTaskObject();
		TaskReportData<ProcessingTimeDataM> taskReportData = (TaskReportData)taskObject.getObject();
		if(taskReportData != null){
			InfReportJobDataM infReportJob = taskReportData.getReportJob();
			String APP_TYPE = taskReportData.getAppType();
			logger.debug("APP_TYPE : " + APP_TYPE);
			if("SLA3".equals(taskId)){
				if(infReportJob == null){
					ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA3(APP_TYPE);
				}else{
					ReR006 = ReportFileFactory.getReportFileDAO().getSLA3(infReportJob);
				}
			}else if("SLA4".equals(taskId)){
				if(infReportJob == null){
					ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA4(APP_TYPE);
				}else{
					ReR006 = ReportFileFactory.getReportFileDAO().getSLA4(infReportJob);
				}
			}else if("SLA1".equals(taskId)){
				if(infReportJob == null){
					ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA1(APP_TYPE);
				}else{
					ReR006 = ReportFileFactory.getReportFileDAO().getSLA1(infReportJob);
				}
			}else if("SLA2".equals(taskId)){
				if(infReportJob == null){
					ReR006 = ReportFileFactory.getReportFileDAO().getReportSLA2(APP_TYPE);
				}else{
					ReR006 = ReportFileFactory.getReportFileDAO().getSLA2(infReportJob);
				}
			}
		}
		taskExecute.setUniqueId(taskId);
		taskExecute.setResponseObject(ReR006);
		return taskExecute;
	}
}
