package com.eaf.orig.ulo.app.dao;

public class LookupDataFactory {
	public static LookupDataDAO getLookupDataDAO(){
		return new LookupDataDAOImpl();
	}
}
