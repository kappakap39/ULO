package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;

public interface OrigDocumentCheckListDAO {
	
	public void createOrigDocumentCheckListM(DocumentCheckListDataM docuCheckListM)throws ApplicationException;
	public void deleteOrigDocumentCheckListM(String applicationGroupId, String docCheckListId)throws ApplicationException;
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListM(String applicationGroupId)throws ApplicationException;	
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListM(String applicationGroupId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigDocumentCheckListM(DocumentCheckListDataM docuCheckListM)throws ApplicationException;
	public void saveUpdateOrigDocumentCheckListM(DocumentCheckListDataM docuCheckListM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyDocumentCheckList(ArrayList<DocumentCheckListDataM> docuCheckList, 
			String applicationGroupId) throws ApplicationException;
	public void deleteNotInKeyDocumentCheckList(ArrayList<DocumentCheckListDataM> docuCheckList, 
			String applicationGroupId,Connection conn) throws ApplicationException;
	void createOrigDocumentCheckListM(DocumentCheckListDataM docuCheckListM,
			Connection conn) throws ApplicationException;
	void deleteOrigDocumentCheckListM(String appGroupId, String personalId,
			String docCheckListId) throws ApplicationException;
	
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualM(String applicationGroupId)throws ApplicationException;	
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualM(String applicationGroupId,Connection conn)throws ApplicationException;
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualAdditionalM(String applicationGroupId)throws ApplicationException;	
	public ArrayList<DocumentCheckListDataM> loadOrigDocumentCheckListManualAdditionalM(String applicationGroupId,Connection conn)throws ApplicationException;
}
