package com.eaf.inf.batch.ulo.card.stack.notification.dao;


public class RunningParamStackFactory {
	public static RunningParamStackDAO getRunningParamStackDAO(){
		return new RunningParamStackDAOImpl();
	}
	
}
