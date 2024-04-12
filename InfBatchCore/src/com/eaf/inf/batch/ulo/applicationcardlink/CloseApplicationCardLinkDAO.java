package com.eaf.inf.batch.ulo.applicationcardlink;

import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public interface CloseApplicationCardLinkDAO {
	public ArrayList<TaskObjectDataM> selectApplicationCardlink() throws InfBatchException;
	public ArrayList<TaskObjectDataM> selectApplicationCardlinkMLP() throws InfBatchException;

}
