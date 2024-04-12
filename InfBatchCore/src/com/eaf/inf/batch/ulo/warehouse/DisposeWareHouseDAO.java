package com.eaf.inf.batch.ulo.warehouse;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public interface DisposeWareHouseDAO {
	public void disposeWareHouseProcess(String dmId, Connection conn) throws InfBatchException;
	public ArrayList<TaskObjectDataM>  selectDisposeDMDocId() throws InfBatchException;
}
