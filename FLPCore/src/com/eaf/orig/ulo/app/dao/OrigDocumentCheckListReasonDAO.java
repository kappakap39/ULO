package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;

public interface OrigDocumentCheckListReasonDAO {
	
	public void createOrigDocumentCheckListReasonM(DocumentCheckListReasonDataM docuChkListReasonM)throws ApplicationException;
	public void createOrigDocumentCheckListReasonM(DocumentCheckListReasonDataM docuChkListReasonM,Connection conn)throws ApplicationException;
	public void deleteOrigDocumentCheckListReasonM(String docChkListId, String docCheckListReasonId)throws ApplicationException;
	public ArrayList<DocumentCheckListReasonDataM> loadOrigDocumentCheckListReasonM(String docChkListId)throws ApplicationException;
	public ArrayList<DocumentCheckListReasonDataM> loadOrigDocumentCheckListReasonM(String docChkListId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigDocumentCheckListReasonM(DocumentCheckListReasonDataM docuChkListReasonM)throws ApplicationException;
	public void deleteNotInKeyDocumentCheckListReason(ArrayList<DocumentCheckListReasonDataM> docuCheckReasonList, 
			String docChkListId) throws ApplicationException;
	void saveUpdateOrigDocumentCheckListReasonM(
			DocumentCheckListReasonDataM docuChkListReasonM, Connection conn)
			throws ApplicationException;
	void deleteNotInKeyDocumentCheckListReason(
			ArrayList<DocumentCheckListReasonDataM> docuCheckReasonList,
			String docChkListId, Connection conn) throws ApplicationException;
	void deleteOrigDocumentCheckListReasonM(String docChkListId,
			String docCheckListReasonId, Connection conn)
			throws ApplicationException;
}
