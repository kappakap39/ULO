package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;

public interface OrigHRVerificationDAO {
	
	public void createOrigHRVerificationM(HRVerificationDataM hRVerificationM)throws ApplicationException;
	public void createOrigHRVerificationM(HRVerificationDataM hRVerificationM,Connection conn)throws ApplicationException;
	public void deleteOrigHRVerificationM(String verResultId, String hrVerifyId)throws ApplicationException;
	public ArrayList<HRVerificationDataM> loadOrigHRVerificationM(String verResultId)throws ApplicationException;	 
	public ArrayList<HRVerificationDataM> loadOrigHRVerificationM(String verResultId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigHRVerificationM(HRVerificationDataM hRVerificationM)throws ApplicationException;
	public void saveUpdateOrigHRVerificationM(HRVerificationDataM hRVerificationM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyHRVerification(ArrayList<HRVerificationDataM> hRVerificationList, 
			String verResultId) throws ApplicationException;
	public void deleteNotInKeyHRVerification(ArrayList<HRVerificationDataM> hRVerificationList, 
			String verResultId,Connection conn) throws ApplicationException;
}
