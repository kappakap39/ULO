package com.eaf.inf.batch.ulo.report.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.GenerateFileUtil;

public class ReportTask implements TaskInf{
	private static transient Logger logger = Logger.getLogger(ReportTask.class);

	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		return null;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException {
		logger.debug("start report onTask >> "+task.getTaskId());
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
			taskExecute.setUniqueId(task.getTaskId());
		TaskObjectDataM taskObject = task.getTaskObject();
		Object object = taskObject.getObject();
		if(object instanceof GenerateFileDataM){
			GenerateFileDataM generateFile = (GenerateFileDataM)taskObject.getObject();
			String reportId = generateFile.getUniqueId();
			String generateId = generateFile.getGenerateId();
			logger.debug("reportId >> "+reportId);
			logger.debug("generateId >> "+generateId);
			try{
				GenerateFileInf generateFileInf = GenerateFileUtil.getGenerateFileInf(generateId);
				if(null != generateFileInf){
					InfResultDataM infResult = generateFileInf.create(generateFile);
					taskExecute.setResultCode(infResult.getResultCode());
					taskExecute.setResultDesc(infResult.getResultDesc());
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e.getLocalizedMessage());
			}
		}
		logger.debug("end report onTask >> "+task.getTaskId());
		return taskExecute;
	}

}
