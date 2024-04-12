package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportDataM;

public interface PLOrigImportCreditLineDataDAO {
	public void saveTable_ORIG_CREDIT_LINE_IMPORT_MASTER (String sessionId, String attachmentId, UserDetailM userM)throws PLOrigApplicationException;
	public void saveTable_ORIG_CREDIT_LINE_IMPORT_DETAIL (Vector<PLImportCreditLineDataM> importCreditLineVect) throws PLOrigApplicationException;
	public void processAutoIncreaseDecrease(String sessionId) throws PLOrigApplicationException;
	public PLResponseImportDataM loadResultAutoIncreaseDecrease (String sessionId) throws PLOrigApplicationException;
	public void delete_ORIG_CREDIT_LINE_IMPORT_MASTER(String sessionId) throws PLOrigApplicationException;
}
