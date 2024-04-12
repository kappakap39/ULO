package com.eaf.core.ulo.common.task.api;

import java.util.ArrayList;

import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public interface TaskInf {
	public interface State{
		public static final String RUNNING = "RUNNING";
		public static final String SHUTDOWN = "SHUTDOWN";
	}
	public interface Status{
		public static final String SUCCESS = "00";
		public static final String ERROR = "-1";
	}
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException;
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception;
}
