package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.ulo.pl.app.dao.PLOrigListBoxMasterUtilityDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public class LoadListBoxMasterUtility {

	private static Logger log = Logger.getLogger(LoadListBoxMasterUtility.class);
	
	public Vector<ORIGCacheDataM> loadByChoice_No(String Field_ID) throws PLOrigApplicationException{
		
		PLOrigListBoxMasterUtilityDAO plOrigListBoxMasterUtilityDAO = PLORIGDAOFactory.getPLOrigListBoxMasterUtilityDAO();
		Vector<ORIGCacheDataM> cacheVect = plOrigListBoxMasterUtilityDAO.loadByChoice_No(Field_ID);
		if (cacheVect == null && cacheVect.size() < 0) {
			cacheVect = new Vector<ORIGCacheDataM>();
		}
		
		return cacheVect;
	}
	
}
