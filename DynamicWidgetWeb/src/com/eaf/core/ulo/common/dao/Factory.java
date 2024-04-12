package com.eaf.core.ulo.common.dao;

public class Factory {
	public static UserDetailDAO getUserDetailDAO(){
		return new UserDetailDAOImpl();
	}
}
