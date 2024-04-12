package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.AccountDataM;

public interface OrigPersonalAccountDAO {
	
	public void createOrigPersonalAccountM(AccountDataM accountM)throws ApplicationException;
	public void deleteOrigPersonalAccountM(String personalId, String accountId)throws ApplicationException;		
	public ArrayList<AccountDataM> loadOrigPersonalAccountM(String personalID)throws ApplicationException;
	public ArrayList<AccountDataM> loadOrigPersonalAccountM(String personalID,Connection conn)throws ApplicationException;
	public void saveUpdateOrigPersonalAccountM(AccountDataM accountM)throws ApplicationException;
	public void deleteNotInKeyAccount(ArrayList<AccountDataM> accountList, String personalID)
			throws ApplicationException;
	public String loadAccountName(String personalID,String accountNo)throws ApplicationException;
}
