package com.eaf.inf.batch.ulo.ctoa;

import com.eaf.inf.batch.ulo.ctoa.CatalogAndTransformOldAppDAO;

public class CatalogAndTransformOldAppFactory {
	public static CatalogAndTransformOldAppDAO getCatalogAndTransformOldAppDAO(){
		return new CatalogAndTransformOldAppDAOImpl();
	}
}
