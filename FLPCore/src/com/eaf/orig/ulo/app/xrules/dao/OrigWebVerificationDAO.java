package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;

public interface OrigWebVerificationDAO {
	public void createOrigWebVerificationM(WebVerificationDataM webVerificationM,Connection conn)throws ApplicationException;
	public void createOrigWebVerificationM(WebVerificationDataM webVerificationM)throws ApplicationException;
	public void deleteOrigWebVerificationM(String verResultId, String webVerifyId)throws ApplicationException;
	public ArrayList<WebVerificationDataM> loadOrigWebVerificationM(String verResultId)throws ApplicationException;	
	public ArrayList<WebVerificationDataM> loadOrigWebVerificationM(String verResultId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigWebVerificationM(WebVerificationDataM webVerificationM)throws ApplicationException;
	public void saveUpdateOrigWebVerificationM(WebVerificationDataM webVerificationM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyWebVerification(ArrayList<WebVerificationDataM> webVerificationList, 
			String verResultId) throws ApplicationException;
	public void deleteNotInKeyWebVerification(ArrayList<WebVerificationDataM> webVerificationList, 
			String verResultId,Connection conn) throws ApplicationException;
}
