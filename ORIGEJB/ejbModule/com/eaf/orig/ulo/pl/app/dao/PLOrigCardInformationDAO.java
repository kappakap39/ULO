package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;

public interface PLOrigCardInformationDAO {
	
	public void saveUpdateOrigCard (PLCardDataM cardM, String personalID, PLApplicationDataM appM) throws PLOrigApplicationException;
	public PLCardDataM loadOrigCard (String personalId, String busClassId) throws PLOrigApplicationException;
	public PLCardDataM loadOrigCard_IncreaseDecrease (String personalId, PLApplicationDataM currentAppM) throws PLOrigApplicationException;
	public String loadCardId(String accId) throws PLOrigApplicationException;
}
