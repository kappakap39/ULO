package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationLogDataM;
import com.eaf.orig.ulo.pl.model.app.search.PLSearchHistoryActionLogDataM;

public interface PLOrigApplicationLogDAO {
	
	public void saveUpdateOrigApplicationLog (Vector<PLApplicationLogDataM> appLogVect, String appRecId) throws PLOrigApplicationException;
	public Vector<PLApplicationLogDataM> loadOrigApplicationLog (String appRecId) throws PLOrigApplicationException;
	public void saveApplicationLog (PLApplicationLogDataM appLogM, PLApplicationDataM appM) throws PLOrigApplicationException;
	public void saveTableApplicationLog (PLApplicationLogDataM appLogM) throws PLOrigApplicationException;
	public Vector<PLSearchHistoryActionLogDataM> loadHistoryActionLog (String appRecId) throws PLOrigApplicationException;
	public String getPreviousAppStatus(String appId)throws PLOrigApplicationException;
}
