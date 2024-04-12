package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLReferencePersonDataM;

public interface PLOrigReferencePersonDAO {

	public void updateSaveReferencePerson(PLReferencePersonDataM refPersonM , String appRecId) throws PLOrigApplicationException;
	public PLReferencePersonDataM loadReferencePerson(String refPersonId)  throws PLOrigApplicationException;
	
}
