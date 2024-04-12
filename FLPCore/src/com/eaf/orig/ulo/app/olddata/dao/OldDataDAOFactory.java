package com.eaf.orig.ulo.app.olddata.dao;


public class OldDataDAOFactory {

	public static OldDataDAO getOldDataDAO()
	{
		return new OldDataDAOImpl();
	}
}
