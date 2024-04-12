package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;

public interface PLOrigNCBDocumentHistoryDAO {
	
	public void SaveNCB_DOCUMENT_HISTORY(Vector<PLNCBDocDataM> ncbDocVect, UserDetailM userM, String consentRefNo) throws PLOrigApplicationException;

}
