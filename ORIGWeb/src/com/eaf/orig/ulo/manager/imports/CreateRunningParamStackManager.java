package com.eaf.orig.ulo.manager.imports;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.orig.common.dao.OrigImportDAO;
import com.eaf.orig.common.dao.OrigImportDAOImpl;
import com.eaf.orig.common.db.control.TransactionHelper;
import com.eaf.orig.ulo.model.membership.RunningParamStackDataM;

public class CreateRunningParamStackManager  extends TransactionHelper {
	@Override
	public void preTransaction(Object objectModel, Connection conn) throws Exception {
		HashMap<String, Object> objectModelHm = (HashMap<String, Object>)objectModel;
		OrigImportDAO importDao = new OrigImportDAOImpl();
		String paramType = (String)objectModelHm.get("PARAM_TYPE");
		importDao.deleteRunningParamStack(conn, paramType);
	}

	@Override
	public void processTransaction(Object objectModel, Connection conn) throws Exception {
		HashMap<String, Object> objectModelHm = (HashMap<String, Object>)objectModel;
		String userName = (String)objectModelHm.get("USER_ID");
		OrigImportDAO importDao = new OrigImportDAOImpl(userName);
		ArrayList<RunningParamStackDataM>  lsitRunningParamStackDataM = (ArrayList<RunningParamStackDataM>)objectModelHm.get("PARAM_LIST");
		importDao.createRunningParamStackM(lsitRunningParamStackDataM, conn);
		
	}

}
