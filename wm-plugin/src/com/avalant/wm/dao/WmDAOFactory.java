package com.avalant.wm.dao;

public class WmDAOFactory {

	public static WmDAO getWMDAO(){
		return new WmDAOImpl();
	}
}
