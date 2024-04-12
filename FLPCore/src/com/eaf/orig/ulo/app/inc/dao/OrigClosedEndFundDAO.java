package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ClosedEndFundDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public interface OrigClosedEndFundDAO {
	
	public void createOrigClosedEndFundM(ClosedEndFundDataM clsEndFundM)throws ApplicationException;
	public void deleteOrigClosedEndFundM(String incomeId, String clsEndFundId)throws ApplicationException;
	public ArrayList<ClosedEndFundDataM> loadOrigClosedEndFundM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<ClosedEndFundDataM> loadOrigClosedEndFundM(String incomeId, String incomeType,Connection conn)throws ApplicationException;
	public void saveUpdateOrigClosedEndFundM(ClosedEndFundDataM clsEndFundM)throws ApplicationException;
	public void deleteNotInKeyClosedEndFund(ArrayList<IncomeCategoryDataM> clsEndFundList, 
			String incomeId) throws ApplicationException;
}
