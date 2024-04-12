package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.AuditTrailDataM;

public interface OrigAuditTrailDAO {
	
	public void createOrigAuditTrailM(AuditTrailDataM auditTrailDataM)throws ApplicationException;
	public void createOrigAuditTrailM(AuditTrailDataM auditTrailDataM,Connection conn)throws ApplicationException;
	public ArrayList<AuditTrailDataM> loadOrigAuditTrailM(String applicationGroupId)throws ApplicationException;	
	public ArrayList<AuditTrailDataM> loadOrigAuditTrailM(String applicationGroupId,Connection conn)throws ApplicationException;
	ArrayList<AuditTrailDataM> loadAllOrigAuditTrailM(String applicationGroupId, Connection conn) throws ApplicationException;	
	
}
