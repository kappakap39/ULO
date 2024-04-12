package com.eaf.orig.ulo.dm.dao;

import com.eaf.orig.ulo.dm.dao.exception.DMException;

public interface DMImportExcelDAO {
	public void createDmWithdrawalAuth(String userNo,String updateBy) throws DMException;	
	public void deleteAllDmWithdrawalAuthEmployee() throws DMException;	
}
