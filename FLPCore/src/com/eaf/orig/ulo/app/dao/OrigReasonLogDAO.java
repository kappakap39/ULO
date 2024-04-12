package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;

public interface OrigReasonLogDAO {
	
	public void createOrigReasonLogM(ReasonLogDataM reasonLogM)throws ApplicationException;
	public void deleteOrigReasonLogM(String applicationRecordId, String reasonLogId)throws ApplicationException;
	public ArrayList<ReasonLogDataM> loadOrigReasonLogM(String applicationRecordId)throws ApplicationException;
	public ArrayList<ReasonLogDataM> loadOrigReasonLogMByAppGroupId(String applicationGroupId)throws ApplicationException;
	public void saveUpdateOrigReasonLogM(ReasonLogDataM reasonLogM)throws ApplicationException;
}
