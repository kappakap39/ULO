package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;


public interface PLOrigDocumentCheckListDAO {	
	public void SaveUpdateOrigDocumentCheckList (Vector<PLDocumentCheckListDataM> docCheckListM, String appRecId) throws PLOrigApplicationException;
	public Vector<PLDocumentCheckListDataM> LoadOrigDocumentCheckList (String appRecId) throws PLOrigApplicationException;
	public Vector<String> loadTrackingDoclistName(String appRecId) throws PLOrigApplicationException;
}
