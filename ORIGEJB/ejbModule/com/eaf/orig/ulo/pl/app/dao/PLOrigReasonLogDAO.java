package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLReasonLogDataM;

public interface PLOrigReasonLogDAO {
	
	public void saveUpdateOrigReasonLog (Vector<PLReasonLogDataM> reasonLogM, String appLogId) throws PLOrigApplicationException;
	public Vector<PLReasonLogDataM> loadOrigReasonLog (String appLogId) throws PLOrigApplicationException;
	public Vector<String> loadDisplayReasonLog (String appLogId) throws PLOrigApplicationException;
	public String reasonFromRejectCancel (String appRecId) throws PLOrigApplicationException;
}
