package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;

public interface OrigIdentifyQuestionSetDAO {
	
	public void createOrigIdentifyQuestionSetM(IdentifyQuestionSetDataM identifyQuestionSetM)throws ApplicationException;
	public void createOrigIdentifyQuestionSetM(IdentifyQuestionSetDataM identifyQuestionSetM,Connection conn)throws ApplicationException;
	public void deleteOrigIdentifyQuestionSetM(String verResultId, String questionSetId)throws ApplicationException;
	public ArrayList<IdentifyQuestionSetDataM> loadOrigIdentifyQuestionSetM(String verResultId)throws ApplicationException;	 
	public ArrayList<IdentifyQuestionSetDataM> loadOrigIdentifyQuestionSetM(String verResultId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigIdentifyQuestionSetM(IdentifyQuestionSetDataM identifyQuestionSetM)throws ApplicationException;
	public void saveUpdateOrigIdentifyQuestionSetM(IdentifyQuestionSetDataM identifyQuestionSetM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyIdentifyQuestionSet(ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList, 
			String verResultId) throws ApplicationException;
	public void deleteNotInKeyIdentifyQuestionSet(ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList, 
			String verResultId,Connection conn) throws ApplicationException;
}
