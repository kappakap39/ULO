package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleHLDataM;

public interface PLOrigBundleHLDAO {
	
	public void saveUpdateBundleHL(PLBundleHLDataM bundleHLM, String appRecId) throws PLOrigApplicationException;
	public PLBundleHLDataM loadBundleHL(String appRecId) throws PLOrigApplicationException;

}
