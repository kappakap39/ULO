package com.eaf.inf.batch.ulo.warehouse;

import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public interface SendApplicationWarehouseDAO {
	public ArrayList<TaskObjectDataM> selectApplicationToWarehouse(String[] jobStates) throws InfBatchException;
	public ArrayList<TaskObjectDataM> selectAllApplicationToWarehouse() throws InfBatchException;
}
