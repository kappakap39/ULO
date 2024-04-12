package com.eaf.inf.batch.ulo.tcb.dao;

public class TcbFactory {
	public static TcbDAO getTcbDAO(){
		return new TcbDAOImpl();
	}
}
