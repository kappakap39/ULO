package com.eaf.orig.ulo.app.inc.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;

public interface OrigBankStatementDetailDAO {
	
	public void createOrigBankStatementDetailM(BankStatementDetailDataM bankStatementDetailM)throws ApplicationException;
	public void deleteOrigBankStatementDetailM(String bankStatementId, String bankStatementDetailId)throws ApplicationException;
	public ArrayList<BankStatementDetailDataM> loadOrigBankStatementDetailM(String bankStatementId)throws ApplicationException;	 
	public void saveUpdateOrigBankStatementDetailM(BankStatementDetailDataM bankStatementDetailM)throws ApplicationException;
	public void deleteNotInKeyBankStatementDetail(ArrayList<BankStatementDetailDataM> bankStatementDtlList, 
			String bankStatementId) throws ApplicationException;
}
