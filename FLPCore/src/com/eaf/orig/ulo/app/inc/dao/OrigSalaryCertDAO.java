package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;

public interface OrigSalaryCertDAO {
	
	public void createOrigSalaryCertM(SalaryCertDataM salaryCertM)throws ApplicationException;
	public void deleteOrigSalaryCertM(String incomeId, String salaryCertId)throws ApplicationException;
	public ArrayList<SalaryCertDataM> loadOrigSalaryCertM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<SalaryCertDataM> loadOrigSalaryCertM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigSalaryCertM(SalaryCertDataM salaryCertM)throws ApplicationException;
	public void deleteNotInKeySalaryCert(ArrayList<IncomeCategoryDataM> salaryCertList, 
			String incomeId) throws ApplicationException;
}
