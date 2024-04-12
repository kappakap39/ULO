package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLOrigListBoxMasterUtilityDAO {
	
	public Vector<ORIGCacheDataM> loadByChoice_No(String Field_ID) throws PLOrigApplicationException; 

}
