package com.eaf.inf.batch.ulo.letter.reject;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.letter.reject.controller.RejectLetterController;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterDAO;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterRequestDataM;

public class RejectLetterTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(RejectLetterTask.class);
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try {
			RejectLetterDAO dao = RejectLetterFactory.getRejectLetterDAO();
			dao.resetLetterNOSequences();
			//HashMap<String, RejectLetterDataM> hRejectLetter=dao.selectRejectLetterInfo();
			RejectLetterRequestDataM rejectLetterRequest = dao.loadRejectLetter();
			HashMap<String,String> priorityMap = rejectLetterRequest.getPriorityMap();
			HashMap<String,String> sellerMap = rejectLetterRequest.getSellerMap();
			logger.debug("priorityMap : "+priorityMap);
			logger.debug("sellerMap : "+sellerMap);
			RejectLetterConfigDataM config = new RejectLetterConfigDataM();
				config.setPriorityMap(priorityMap);
				config.setSellerMap(sellerMap);
			HashMap<String, RejectLetterDataM> hRejectLetter = rejectLetterRequest.getRejectLetterMap();
			if(!InfBatchUtil.empty(hRejectLetter)){
				ArrayList<String> keyMappings = new ArrayList<String>(hRejectLetter.keySet());
				for(String keyMapping :keyMappings){
					RejectLetterDataM rejectLetterDataM = hRejectLetter.get(keyMapping); 
					TaskObjectDataM taskObjectDataM = new TaskObjectDataM();
						taskObjectDataM.setUniqueId(rejectLetterDataM.getApplicationType());
						taskObjectDataM.setUniqueType(rejectLetterDataM.getApplicationType());
						taskObjectDataM.setObject(rejectLetterDataM);
						taskObjectDataM.setConfiguration(config);
					taskObjects.add(taskObjectDataM);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		logger.debug("taskObjects size >> "+taskObjects.size());
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = null;
		try{
			RejectLetterDataM rejectLetterDataM = (RejectLetterDataM)task.getTaskObject().getObject();
			RejectLetterConfigDataM config = (RejectLetterConfigDataM)task.getTaskObject().getConfiguration();
            RejectLetterController rejectLetterController = new RejectLetterController();
            taskExecute = rejectLetterController.create(rejectLetterDataM,config);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
