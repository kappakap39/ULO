package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.NotePadDataM;

public interface OrigNotepadDAO {
	
	public void createOrigNotePadM(NotePadDataM notepadM)throws ApplicationException;
	public void deleteOrigNotePadM(String applicationGroupId, String notePadId)throws ApplicationException;
	public ArrayList<NotePadDataM> loadOrigNotePadM(String applicationGroupId)throws ApplicationException;	 
	public ArrayList<NotePadDataM> loadOrigNotePadM(String applicationGroupId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigNotePadM(NotePadDataM notepadM)throws ApplicationException;
	public void deleteNotInKeyNotePad(ArrayList<NotePadDataM> notepadMList, 
			String applicationGroupId) throws ApplicationException;
	public void deleteNotInKeyNotePad(ArrayList<NotePadDataM> notepadMList, 
			String applicationGroupId,Connection conn) throws ApplicationException;
}
