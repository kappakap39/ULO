package com.eaf.inf.batch.ulo.letter.reject.allapp.task;

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
import com.eaf.inf.batch.ulo.letter.reject.allapp.controller.RejectLetterPDFController;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterPDFDAO;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFConfigDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFRequestDataM;

public class RejectLetterPDFAllAppTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(RejectLetterPDFAllAppTask.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException{
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try{
			RejectLetterPDFDAO dao = RejectLetterFactory.getRejectLetterPDFDAO();
			RejectLetterPDFRequestDataM rejectLetterPdfRequest = dao.loadRejectLetterPDFRequest();
			ArrayList<RejectLetterPDFDataM> rejectLetterPDFs = rejectLetterPdfRequest.getRejectLetterPDFs();
			HashMap<String, String> priorityMap = rejectLetterPdfRequest.getPriorityMap();
			RejectLetterPDFConfigDataM config = new RejectLetterPDFConfigDataM();
				config.setPriorityMap(priorityMap);
			logger.debug("config : "+config);
			for(RejectLetterPDFDataM rejectLetterPDF : rejectLetterPDFs){
				TaskObjectDataM taskObj = new TaskObjectDataM();
					taskObj.setObject(rejectLetterPDF);
					taskObj.setUniqueId(rejectLetterPDF.getLetterNo());
					taskObj.setConfiguration(config);
				taskObjects.add(taskObj);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new TaskException(e);
		}
		return taskObjects;
	}
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception{
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			RejectLetterPDFDataM rejectLetterPdf =(RejectLetterPDFDataM)task.getTaskObject().getObject();
			RejectLetterPDFConfigDataM config = (RejectLetterPDFConfigDataM)task.getTaskObject().getConfiguration();
			RejectLetterPDFController rejectPDFController =  new RejectLetterPDFController();
			taskExecute = rejectPDFController.create(rejectLetterPdf,config);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
