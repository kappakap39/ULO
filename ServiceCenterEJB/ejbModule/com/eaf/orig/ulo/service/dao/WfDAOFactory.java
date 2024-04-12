package com.eaf.orig.ulo.service.dao;

public class WfDAOFactory {
	public static WfApplicationGroupDAO getApplicationGroupDAO(){
		return new WfApplicationGroupDAOImpl();
	}
}
