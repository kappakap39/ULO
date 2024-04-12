package com.eaf.service.common.dao;

public class ServiceFactory {
	public static ServiceDAO getServiceDAO(){
		return new ServiceDAOImpl();
	}
	public static ServiceDAO getServiceDAO(int dbType){
		return new ServiceDAOImpl(dbType);
	}
}
