package com.eaf.inf.batch.ulo.sumtables;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.creditshield.dao.CreditShieldDAO;
import com.eaf.inf.batch.ulo.creditshield.dao.CreditShieldDAOFactory;
import com.eaf.inf.batch.ulo.orsum.dao.ORSummaryDAO;
import com.eaf.inf.batch.ulo.orsum.dao.ORSummaryDAOFactory;

public class RefreshSummaryTableTask extends InfBatchObjectDAO implements TaskInf {
	private static transient Logger logger = Logger.getLogger(RefreshSummaryTableTask.class);
	private static final String refreshList = InfBatchProperty.getInfBatchConfig("REFRESH_SUMMARY_TABLE_REFRESH_LIST");
	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		List<String> list = Arrays.asList(refreshList.split(","));
		for (String item:list) {
			TaskObjectDataM task = new TaskObjectDataM();
			task.setUniqueId(item.trim().toUpperCase());
			taskObjects.add(task);
		}
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		TaskObjectDataM taskObject = task.getTaskObject();
		String refreshItem = taskObject.getUniqueId();
		taskExecute.setUniqueId(refreshItem);
		logger.debug("refreshItem >> "+refreshItem);
		Connection conn = getConnection();
		Date currentDate = InfBatchProperty.getDate();
		try{
			 conn.setAutoCommit(false);
			 if ("REFRESH_OR".equals(refreshItem)) {
				 ORSummaryDAO dao = ORSummaryDAOFactory.getORSummaryDAO();
				 dao.refreshOrSummary(null, conn);
				 dao.accumOrPeriodSummary(conn);
			 }
			 else if ("REFRESH_CREDIT_SHIELD".equals(refreshItem)) {
				 CreditShieldDAO dao = CreditShieldDAOFactory.getCreditShieldDAO();
				 dao.refreshCreditShieldSummary(currentDate, conn);
			 }
			 conn.commit();
			 taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
			conn.rollback();	
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc(e.getLocalizedMessage());
			}
		}
		return taskExecute;
	}

}
