package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.NCBDocumentDataM;

public interface OrigNCBDocumentDAO {
	
	public void createOrigNCBDocumentM(NCBDocumentDataM ncbDocumentM)throws ApplicationException;
	public void deleteOrigNCBDocumentM(String personalId, String ncbDocumentId)throws ApplicationException;	
	public ArrayList<NCBDocumentDataM> loadOrigNCBDocumentM(String personalID)throws ApplicationException;	 
	public void saveUpdateOrigNCBDocumentM(NCBDocumentDataM ncbDocumentM)throws ApplicationException;
	public void deleteNotInKeyNCBDocument(ArrayList<NCBDocumentDataM> ncbDocList, String personalID)
			throws ApplicationException;
}
