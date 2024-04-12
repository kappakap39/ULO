package com.eaf.inf.batch.ulo.letter.reject;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.letter.reject.controller.RejectLetterPDFController;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterFactory;
import com.eaf.inf.batch.ulo.letter.reject.dao.RejectLetterPDFDAO;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.google.gson.Gson;

public class RejectLetterPDFTask  extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(RejectLetterPDFTask.class);

	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		try {
			//Case Customer
			RejectLetterPDFDAO dao = RejectLetterFactory.getRejectLetterPDFDAO();
			ArrayList<TaskObjectDataM> customerTaskObjects = dao.selectRejectLetterPDFInfo();
			if(!InfBatchUtil.empty(customerTaskObjects)){
				taskObjects.addAll(customerTaskObjects);
			}
			//Case Seller
			ArrayList<TaskObjectDataM> sellerTaskObjects = dao.selectSellerRejectLetterPDFInfo();
			if(!InfBatchUtil.empty(sellerTaskObjects)){
				taskObjects.addAll(sellerTaskObjects);
			}
//			logger.debug("taskObjects>>>"+new Gson().toJson(taskObjects));
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			RejectLetterPDFDataM rejectLetterPDFDataM =(RejectLetterPDFDataM)task.getTaskObject().getObject();
			RejectLetterPDFController rejectPDFController =  new RejectLetterPDFController();
			taskExecute = rejectPDFController.create(rejectLetterPDFDataM);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
