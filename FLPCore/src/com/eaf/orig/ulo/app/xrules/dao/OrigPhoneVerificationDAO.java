package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;

public interface OrigPhoneVerificationDAO {
	public void createOrigPhoneVerificationM(PhoneVerificationDataM phoneVerificationM,Connection conn)throws ApplicationException;
	public void createOrigPhoneVerificationM(PhoneVerificationDataM phoneVerificationM)throws ApplicationException;
	public void deleteOrigPhoneVerificationM(String verResultId, String phoneVerifyId)throws ApplicationException;
	public ArrayList<PhoneVerificationDataM> loadOrigPhoneVerificationM(String verResultId)throws ApplicationException;	 
	public ArrayList<PhoneVerificationDataM> loadOrigPhoneVerificationM(String verResultId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigPhoneVerificationM(PhoneVerificationDataM phoneVerificationM)throws ApplicationException;
	public void saveUpdateOrigPhoneVerificationM(PhoneVerificationDataM phoneVerificationM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyPhoneVerification(ArrayList<PhoneVerificationDataM> phoneVerificationList, 
			String verResultId) throws ApplicationException;
	public void deleteNotInKeyPhoneVerification(ArrayList<PhoneVerificationDataM> phoneVerificationList, 
			String verResultId,Connection conn) throws ApplicationException;
}
