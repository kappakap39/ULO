package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM;

public interface PLOrigAuditTrailDAO {
	
	public void saveUpdateOrigAuditTrail (Vector<PLAuditTrailDataM> auditTrailVect, String appLogId) throws PLOrigApplicationException;
	public Vector<PLAuditTrailDataM> loadOrigAuditTrail (String appLogId) throws PLOrigApplicationException;
	public Vector<PLAuditTrailDataM> loadOrigAuditTrailByRoleAppRecId (String appRecID) throws PLOrigApplicationException;
}
