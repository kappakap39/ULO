package com.eaf.inf.batch.ulo.warehouse;


public class WarehouseFactory {
	public static SendApplicationWarehouseDAO getSendApplicationWarehouseDAO() {
		return new SendApplicationWarehouseDAOImpl();
	}
	public static DisposeWareHouseDAO getDisposeWareHouseDAO() {
		return new DisposeWareHouseDAOImpl();
	}
}
