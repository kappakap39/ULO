package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;

public interface PLOrigDocumentCheckListReasonDAO {	
	public void SaveUpdateOrigDocumentCheckListReason (Vector<PLDocumentCheckListReasonDataM> docChkReasonVect, String docChkListID) throws PLOrigApplicationException;
	public Vector<PLDocumentCheckListReasonDataM> LoadOrigDocumentCheckListReason(String docChkListID , String docCode) throws PLOrigApplicationException;
}
