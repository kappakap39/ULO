package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;

public interface OrigIdentifyQuestionDAO {
	
	public void createOrigIdentifyQuestionM(IdentifyQuestionDataM identifyQuestionM)throws ApplicationException;
	public void createOrigIdentifyQuestionM(IdentifyQuestionDataM identifyQuestionM,Connection conn)throws ApplicationException;
	public void deleteOrigIdentifyQuestionM(String verResultId, String identifyQuestionId)throws ApplicationException;
	public ArrayList<IdentifyQuestionDataM> loadOrigIdentifyQuestionM(String verResultId)throws ApplicationException;	 
	public void saveUpdateOrigIdentifyQuestionM(IdentifyQuestionDataM identifyQuestionM)throws ApplicationException;
	public void saveUpdateOrigIdentifyQuestionM(IdentifyQuestionDataM identifyQuestionM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyIdentifyQuestion(ArrayList<IdentifyQuestionDataM> identifyQuestionList, 
			String verResultId) throws ApplicationException;
	public void deleteNotInKeyIdentifyQuestion(ArrayList<IdentifyQuestionDataM> identifyQuestionList, 
			String verResultId,Connection conn) throws ApplicationException;
	ArrayList<IdentifyQuestionDataM> loadOrigIdentifyQuestionM(String verResultId, Connection conn) throws ApplicationException;
}
