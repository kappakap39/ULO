package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ContactLogDataM;

public interface OrigContactLogDAO {
	
	public void createOrigContactLogM(ContactLogDataM contactLogM)throws ApplicationException;
	public void deleteOrigContactLogM(String applicationGroupId, String contactLogId)throws ApplicationException;
	public ArrayList<ContactLogDataM> loadOrigContactLogM(String applicationGroupId)throws ApplicationException;	 
	public void saveUpdateOrigContactLogM(ContactLogDataM contactLogM)throws ApplicationException;
	public void deleteNotInKeyContactLog(ArrayList<ContactLogDataM> contactLogList, 
			String applicationGroupId) throws ApplicationException;
}
