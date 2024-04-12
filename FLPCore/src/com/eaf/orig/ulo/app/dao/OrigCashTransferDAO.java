package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public interface OrigCashTransferDAO {
	
	public void createOrigCashTransferM(CashTransferDataM cashTransferM)throws ApplicationException;
	public void deleteOrigCashTransferM(String loanID, String transferId)throws ApplicationException;	
	public ArrayList<CashTransferDataM> loadOrigCashTransferM(String loanID)throws ApplicationException;	 
	public ArrayList<CashTransferDataM> loadOrigCashTransferM(String loanID,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigCashTransferM(CashTransferDataM cashTransferM)throws ApplicationException;
	public void deleteNotInKeyCashTransfer(ArrayList<CashTransferDataM> cashTransferList,
			String loanID) throws ApplicationException;
}
