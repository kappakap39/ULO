package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.TaweesapDataM;

public interface OrigTaweesapDAO {
	
	public void createOrigTaweesapM(TaweesapDataM taweesapM)throws ApplicationException;
	public void deleteOrigTaweesapM(String incomeId, String taweesapId)throws ApplicationException;
	public ArrayList<TaweesapDataM> loadOrigTaweesapM(String incomeId, String incomeType)throws ApplicationException;
	public ArrayList<TaweesapDataM> loadOrigTaweesapM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigTaweesapM(TaweesapDataM taweesapM)throws ApplicationException;
	public void deleteNotInKeyTaweesap(ArrayList<IncomeCategoryDataM> taweesapList, 
			String incomeId) throws ApplicationException;
}
