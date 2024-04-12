package com.eaf.inf.batch.ulo.tcb.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.inf.batch.ulo.tcb.core.UpdateListTaskObject;

public interface TcbDAO {
	public ArrayList<TaskObjectDataM> loadCISNo() throws InfBatchException;
	public ArrayList<TaskObjectDataM>  loadAccountNoFromDIH(UpdateListTaskObject taskObject) throws InfBatchException;
	public void updateAccountNo(UpdateListTaskObject data) throws InfBatchException;
	public void updateKPLTopUPAccountNo(HashMap<String, String> data) throws InfBatchException;
}
