package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public interface OrigAdditionalServiceMapDAO {
	
	public void createOrigAdditionalServiceMapM(String loanId, String serviceId)throws ApplicationException;
	public boolean checkMapExists(String loanId, String serviceId)throws ApplicationException;
	public void deleteOrigAdditionalServiceMapM(String loanId, String serviceId)throws ApplicationException;
	public ArrayList<String> loadOrigAdditionalServicesM(String loanId)throws ApplicationException;	 
	public ArrayList<String> loadOrigAdditionalServicesM(String loanId,Connection conn)throws ApplicationException;	 
	public void deleteNotInKeyAdditionalService(ArrayList<String> serviceList,String loanId,Connection conn) throws ApplicationException;
	public void saveUpdateOrigAdditionalServiceM(String loanId, String serviceId) throws ApplicationException;
	public void saveUpdateOrigAdditionalServiceM(String loanId, String serviceId,Connection conn) throws ApplicationException;
	void deleteNotInKeyAdditionalService(ArrayList<String> serviceList,
			String loanId) throws ApplicationException;
}
