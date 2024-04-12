package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleALDataM;

public interface PLOrigBundleALDAO {
	
	public void saveUpdateBundleAL(PLBundleALDataM bundleAlM, String appRecId) throws PLOrigApplicationException;
	public PLBundleALDataM loadBundleAL(String appRecId) throws PLOrigApplicationException;

}
