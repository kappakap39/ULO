package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

public interface OrigSaleInfoDAO {
	
	public void createOrigSaleInfoM(SaleInfoDataM saleInfoM)throws ApplicationException;
	public void deleteOrigSaleInfoM(String applicationGroupId, String salesInfoId)throws ApplicationException;
	public ArrayList<SaleInfoDataM> loadOrigSaleInfoM(String applicationGroupId)throws ApplicationException;
	public ArrayList<SaleInfoDataM> loadOrigSaleInfoM(String applicationGroupId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigSaleInfoM(SaleInfoDataM saleInfoM)throws ApplicationException;
	public void deleteNotInKeySaleInfo(ArrayList<SaleInfoDataM> saleInfoList, 
			String applicationGroupId) throws ApplicationException;

}
