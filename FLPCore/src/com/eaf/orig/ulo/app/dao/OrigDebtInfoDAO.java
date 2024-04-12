package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;

public interface OrigDebtInfoDAO {
	
	public void createOrigDebtInfoM(DebtInfoDataM debtInfoM)throws ApplicationException;
	public void deleteOrigDebtInfoM(String personalId, String debtId)throws ApplicationException;
	public ArrayList<DebtInfoDataM> loadOrigDebtInfoM(String personalId)throws ApplicationException;	 
	public ArrayList<DebtInfoDataM> loadOrigDebtInfoM(String personalId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigDebtInfoM(DebtInfoDataM debtInfoM)throws ApplicationException;
	public void deleteNotInKeyDebtInfo(ArrayList<DebtInfoDataM> debtList, 
			String personalId) throws ApplicationException;
}
