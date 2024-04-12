package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentRelationDataM;

public interface OrigDocumentRelationDAO {
	
	public void createOrigDocumentRelationM(DocumentRelationDataM docRelationM)throws ApplicationException;
	public void deleteOrigDocumentRelationM(String docCheckListId, String refId, String refLevel)throws ApplicationException;	
	public ArrayList<DocumentRelationDataM> loadOrigDocumentRelationM(String docCheckListId)throws ApplicationException;
	public ArrayList<DocumentRelationDataM> loadOrigDocumentRelationM(String docCheckListId,Connection conn)throws ApplicationException;
	public ArrayList<DocumentRelationDataM> loadOrigDocumentRelationM(String refId,String refLevel) throws ApplicationException;
	public void saveUpdateOrigDocumentRelationM(DocumentRelationDataM docRelationM)throws ApplicationException;
	public void deleteNotInKeyDocumentRelation(ArrayList<DocumentRelationDataM> docRelationList, String refId, String refLevel)throws ApplicationException;
	public void deleteOrigDocumentRelation(String docCheckListId)throws ApplicationException;
	public void deleteOrigDocumentRelation(ArrayList<DocumentCheckListDataM> docCheckLists,String applicationGroupId)throws ApplicationException;
	public String loadOrigDocumentRelationMByPersonalId(String personalId,String DOC_CODE,String REF_LEVEL)throws ApplicationException;
	void createOrigDocumentRelationM(DocumentRelationDataM docRelationM,
			Connection conn) throws ApplicationException;
	void saveUpdateOrigDocumentRelationM(DocumentRelationDataM docRelationM,
			Connection conn) throws ApplicationException;
	void deleteOrigDocumentRelation(String docCheckListId, Connection conn)
			throws ApplicationException;
}
