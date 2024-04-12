package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleSMEDataM;

public interface PLOrigBundleSMEDAO {
	
	public void saveUpdateBundleSME(PLBundleSMEDataM bundleSMSM, String appRecId) throws PLOrigApplicationException;
	public PLBundleSMEDataM loadBundleSME(String appRecId) throws PLOrigApplicationException;

}
