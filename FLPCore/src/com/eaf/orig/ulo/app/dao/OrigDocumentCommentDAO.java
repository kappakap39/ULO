package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;

public interface OrigDocumentCommentDAO {
	public void createOrigDocumentCommentM(DocumentCommentDataM documentComment) throws ApplicationException;
	public void deleteOrigDocumentCommentM(String applicationGroupId, String notePadId) throws ApplicationException;
	public ArrayList<DocumentCommentDataM> loadOrigDocumentCommentM(String applicationGroupId) throws ApplicationException;
	public ArrayList<DocumentCommentDataM> loadOrigDocumentCommentM(String applicationGroupId,Connection conn) throws ApplicationException;
	public void saveUpdateOrigDocumentCommentM(DocumentCommentDataM documentCommment) throws ApplicationException;
	public void deleteNotInKeyDocumentComment(ArrayList<DocumentCommentDataM> documentComment,String applicationGroupId) throws ApplicationException;
}
