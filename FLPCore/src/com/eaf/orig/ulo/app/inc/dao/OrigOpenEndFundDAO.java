package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDataM;

public interface OrigOpenEndFundDAO {
	
	public void createOrigOpenEndFundM(OpenedEndFundDataM openEndFundM)throws ApplicationException;
	public void deleteOrigOpenEndFundM(String incomeId, String opnEndFundId)throws ApplicationException;
	public ArrayList<OpenedEndFundDataM> loadOrigOpenEndFundM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<OpenedEndFundDataM> loadOrigOpenEndFundM(String incomeId, String incomeType,Connection conn)throws ApplicationException;
	public void saveUpdateOrigOpenEndFundM(OpenedEndFundDataM openEndFundM)throws ApplicationException;
	public void deleteNotInKeyOpenEndFund(ArrayList<IncomeCategoryDataM> openEndFundMList, 
			String incomeId) throws ApplicationException;
}
