package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;

public interface PLOrigNCBDocumentDAO {	
	public void updateSaveNCBDocument(Vector<PLNCBDocDataM> ncbDocVect, String personalID) throws PLOrigApplicationException;
	public Vector<PLNCBDocDataM> loadNCBDocument(String personalId) throws PLOrigApplicationException;
	public void deleteORIG_NCB_DOCUMENT(String personalID) throws PLOrigApplicationException;
}
