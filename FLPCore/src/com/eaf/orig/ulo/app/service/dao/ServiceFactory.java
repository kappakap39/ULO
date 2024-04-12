package com.eaf.orig.ulo.app.service.dao;

public class ServiceFactory {
	public static ServiceDAO getServiceDAO(){
		return new ServiceDAOImpl();
	}
}
