package com.eaf.orig.ulo.calloper.dao;

public class UpdateCallOperatorFactory {
	public static UpdateCallOperatorDAO getCallOperatorDAO(){
		return new UpdateCallOperatorDAOImpl();
	}
}
