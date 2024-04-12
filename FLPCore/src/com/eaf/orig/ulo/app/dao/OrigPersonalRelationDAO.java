package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;

public interface OrigPersonalRelationDAO {
	
	public void createOrigPersonalRelationM(PersonalRelationDataM personalRelationM)throws ApplicationException;
	public void deleteOrigPersonalRelationM(String personalId, String refId, String relationLevel)throws ApplicationException;	
	public ArrayList<PersonalRelationDataM> loadOrigPersonalRelationM(String personalId)throws ApplicationException;
	public ArrayList<PersonalRelationDataM> loadOrigPersonalRelationM(String personalId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigPersonalRelationM(PersonalRelationDataM personalRelationM)throws ApplicationException;
	public void deleteNotInKeyPersonalRelation(ArrayList<PersonalRelationDataM> personalRelationList, String personalId)
			throws ApplicationException;
}
