package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLKYCDataM;

public interface PLOrigKYCDAO {

	public void saveUpdate(PLKYCDataM kycM, String appRecId) throws PLOrigApplicationException;
	public PLKYCDataM loadKYC(String appRecId) throws PLOrigApplicationException;
	
}
