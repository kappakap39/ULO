package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.DocumentVerificationDataM;

public interface OrigDocVerificationDAO {
	
	public void createOrigDocumentVerificationM(DocumentVerificationDataM docVerificationM)throws ApplicationException;
	public void createOrigDocumentVerificationM(DocumentVerificationDataM docVerificationM,Connection conn)throws ApplicationException;
	public void deleteOrigDocumentVerificationM(String verResultId, String documentVerId)throws ApplicationException;
	public ArrayList<DocumentVerificationDataM> loadOrigDocumentVerificationM(String verResultId)throws ApplicationException;
	public ArrayList<DocumentVerificationDataM> loadOrigDocumentVerificationM(String verResultId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigDocumentVerificationM(DocumentVerificationDataM docVerificationM)throws ApplicationException;
	public void saveUpdateOrigDocumentVerificationM(DocumentVerificationDataM docVerificationM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyDocumentVerification(ArrayList<DocumentVerificationDataM> docVerificationList, 
			String verResultId) throws ApplicationException;
	public void deleteNotInKeyDocumentVerification(ArrayList<DocumentVerificationDataM> docVerificationList, 
			String verResultId,Connection conn) throws ApplicationException;
}
