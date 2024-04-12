package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLBundleCCDataM;

public interface PLOrigBundleCCDAO {

	public void saveUpdateBundleCC(PLBundleCCDataM bundleCCM, String appRecId) throws PLOrigApplicationException;
	public PLBundleCCDataM loadBundleCC(String appRecId) throws PLOrigApplicationException;
	
}
