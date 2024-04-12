package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

public interface OrigReferencePersonDAO {
	
	public void createOrigReferencePersonM(ReferencePersonDataM referencePersonM)throws ApplicationException;
	public void deleteOrigReferencePersonM(String applicationGroupId, int seq)throws ApplicationException;
	public ArrayList<ReferencePersonDataM> loadOrigReferencePersonM(String applicationGroupId)throws ApplicationException;	
	public ArrayList<ReferencePersonDataM> loadOrigReferencePersonM(String applicationGroupId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigReferencePersonM(ReferencePersonDataM referencePersonM)throws ApplicationException;
	public void deleteNotInKeyReferencePerson(ArrayList<ReferencePersonDataM> referencePersonList,
			String applicationGroupId)throws ApplicationException;
}
