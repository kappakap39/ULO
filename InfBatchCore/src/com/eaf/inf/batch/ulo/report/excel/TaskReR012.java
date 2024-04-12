package com.eaf.inf.batch.ulo.report.excel;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.report.dao.ReportFileDAO;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.ProcessingTimeDataM;
import com.eaf.inf.batch.ulo.report.model.TaskReportData;

public class TaskReR012 implements TaskInf{
  private static transient Logger logger = Logger.getLogger(TaskReR012.class);
  public ArrayList<TaskObjectDataM> getTaskObjects()throws TaskException{
    return null;
  }
  @SuppressWarnings({"unchecked","rawtypes"})
  public TaskExecuteDataM onTask(TaskDataM task)throws TaskException, Exception{
    ArrayList<ProcessingTimeDataM> processTimes = new ArrayList<ProcessingTimeDataM>();
    ReportFileDAO reportDAO = ReportFileFactory.getReportFileDAO();
    String taskId = task.getTaskId();
    logger.debug("taskId : " + taskId);
    TaskExecuteDataM taskExecute = new TaskExecuteDataM();
    TaskObjectDataM taskObject = task.getTaskObject();
    TaskReportData<ProcessingTimeDataM> taskReportData = (TaskReportData)taskObject.getObject();
		if (taskReportData != null) {
			InfReportJobDataM infReportJob = taskReportData.getReportJob();
			if ("R012_SUMMARY".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012Summary();
				} else {
					processTimes = reportDAO.getReportR012SummaryC(infReportJob);
				}
			}
			else if ("R012_TOP_IA".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("IA", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "IA", "TOP");
				}
			} else if ("R012_BOTTOM_IA".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("IA", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "IA", "BOTTOM");
				}
			}
			else if ("R012_TOP_DE1.1".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DE1_1", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DE1_1", "TOP");
				}
			} else if ("R012_BOTTOM_DE1.1".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DE1_1", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DE1_1", "BOTTOM");
				}
			} else if ("R012_TOP_DE1.2".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DE1_2", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DE1_2", "TOP");
				}
			} else if ("R012_BOTTOM_DE1.2".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DE1_2", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DE1_2", "BOTTOM");
				}
			} else if ("R012_TOP_DV".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DV", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DV", "TOP");
				}
			} else if ("R012_BOTTOM_DV".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DV", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DV", "BOTTOM");
				}
			} else if ("R012_TOP_DE2".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DE2", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DE2", "TOP");
				}
			} else if ("R012_BOTTOM_DE2".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("DE2", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "DE2", "BOTTOM");
				}
			} else if ("R012_TOP_VT".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("VT", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "VT", "TOP");
				}
			} else if ("R012_BOTTOM_VT".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("VT", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "VT", "BOTTOM");
				}
			} else if ("R012_TOP_CA".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("CA", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "CA", "TOP");
				}
			} else if ("R012_BOTTOM_CA".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("CA", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "CA", "BOTTOM");
				}
			} else if ("R012_TOP_FU".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("FU", "TOP");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "FU", "TOP");
				}
			} else if ("R012_BOTTOM_FU".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012_Top_Bottom("FU", "BOTTOM");
				} else {
					processTimes = reportDAO.getReportR012_Top_BottomC(infReportJob, "FU", "BOTTOM");
				}
			} else if ("R012_PROCESSING_TIME".equals(taskId)) {
				if (infReportJob == null) {
					processTimes = reportDAO.getReportR012();
				} else {
					processTimes = reportDAO.getProcessingTime(infReportJob);
				}
			}
		}
    taskExecute.setUniqueId(taskId);
    taskExecute.setResponseObject(processTimes);
    return taskExecute;
  }
}
