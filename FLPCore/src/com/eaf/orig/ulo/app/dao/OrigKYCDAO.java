package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.KYCDataM;

public interface OrigKYCDAO {
	
	public void createOrigKYCM(KYCDataM kycM)throws ApplicationException;
	public void deleteOrigKYCM(String personalID)throws ApplicationException;	
	public KYCDataM loadOrigKYCM(String personalID)throws ApplicationException;	 
	public KYCDataM loadOrigKYCM(String personalID,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigKYCM(KYCDataM kycM)throws ApplicationException;
	
}
