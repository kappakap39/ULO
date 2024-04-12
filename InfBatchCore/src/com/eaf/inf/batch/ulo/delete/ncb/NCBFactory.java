package com.eaf.inf.batch.ulo.delete.ncb;


public class NCBFactory {
	public static DeleteNCBDAO getDeleteNCBDAO() {
		return new DeleteNCBDAOImpl();
	}
}
