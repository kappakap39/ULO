package com.eaf.inf.batch.ulo.memo.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public class InstructionMemoTask implements TaskInf{
	private static transient Logger logger = Logger.getLogger(InstructionMemoTask.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		for(int uniqueId = 0; uniqueId<=100; uniqueId++){
			TaskObjectDataM queueObject = new TaskObjectDataM();
			queueObject.setUniqueId(String.valueOf(uniqueId));
			taskObjects.add(queueObject);
		}
		return taskObjects;
	}
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException {
		TaskExecuteDataM taskExcecute = new TaskExecuteDataM();
		String taskId = task.getTaskId();
		TaskObjectDataM taskObject = task.getTaskObject();
		String uniqueId = taskObject.getUniqueId();
		logger.debug("taskId >> "+taskId);
		logger.debug("uniqueId >> "+uniqueId);
		return taskExcecute;
	}

}
