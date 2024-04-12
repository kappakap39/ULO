package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ReasonDataM;

public interface OrigReasonDAO {
	
	public void createOrigReasonM(ReasonDataM reasonM)throws ApplicationException;
	public void deleteOrigReasonM(String applicationRecordId)throws ApplicationException;
	public ArrayList<ReasonDataM> loadOrigReasonM(String applicationRecordId)throws ApplicationException;	
	public ArrayList<ReasonDataM> loadOrigReasonM(String applicationRecordId,Connection conn)throws ApplicationException;
	public void deleteOrigReasonMByAppGroupId(String applicationGroupId)throws ApplicationException;
	public ArrayList<ReasonDataM> loadOrigReasonMByAppGroupId(String applicationGroupId)throws ApplicationException;
	public ArrayList<ReasonDataM> loadOrigReasonMByAppGroupId(String applicationGroupId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigReasonM(ReasonDataM reasonM)throws ApplicationException;
}
