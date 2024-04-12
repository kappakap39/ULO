package com.eaf.core.ulo.common.dao;

public class SystemConfigFactory {
	public static SystemConfigDAO getConfigDAO(){
		return new SystemConfigDAOImpl();
	}
}
