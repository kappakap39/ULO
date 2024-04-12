package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;


public interface PLOrigPersonalAddressDAO {
	public void updateSavePersonalAddress(Vector<PLAddressDataM> addrVect, String personalID) throws PLOrigApplicationException;
	public Vector<PLAddressDataM> loadPersonalAddress(String personalId) throws PLOrigApplicationException;
	public Vector<PLAddressDataM> loadPersonalAddress_IncreaseDecrease(String personalId, PLApplicationDataM currentAppM) throws PLOrigApplicationException;
	public void deletePersonalAddress (String personalId) throws PLOrigApplicationException;
}
