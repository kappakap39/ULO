package com.eaf.inf.batch.ulo.applicationcardlink;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.FileUtil;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;

public class CloseCardLinkTaskMLP  implements TaskInf{
	private static transient Logger logger = Logger.getLogger(CloseCardLinkTaskMLP.class);
	String CLOSE_APPLICATION_CARD_LINK_URL = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_URL");
	String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String CLOSE_APPLICATION_CARD_LINK_ENCODING = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_ENCODING");
	String CLOSE_APPLICATION_CARD_LINK_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("CLOSE_APPLICATION_CARD_LINK_OUTPUT_NAME");
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			CloseApplicationCardLinkDAO dao = ApplicationCardLinkFactory.getCloseApplicationCardLinkDAO();
			taskObjects = dao.selectApplicationCardlinkMLP();
		}catch(Exception e){
			logger.debug("ERROR",e);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task){
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();		
		TaskObjectDataM taskObject = task.getTaskObject();
		String text = taskObject.getObject().toString();
		try{
			if(!InfBatchUtil.empty(text))
			{
				//Generate file for MLP
				String DATE = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
				File outputFile = new File(PathUtil.getPath("CLOSE_APPLICATION_CARD_LINK_OUTPUT_PATH") + CLOSE_APPLICATION_CARD_LINK_OUTPUT_NAME.replace("YYYYMMDD", DATE));
				FileUtils.writeStringToFile(outputFile, text, CLOSE_APPLICATION_CARD_LINK_ENCODING);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
