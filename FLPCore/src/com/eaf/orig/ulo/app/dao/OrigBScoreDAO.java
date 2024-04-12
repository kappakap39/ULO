package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BScoreDataM;
import com.eaf.orig.ulo.model.app.NCBDocumentDataM;

public interface OrigBScoreDAO {
	
	public void createOrigBScoreM(BScoreDataM bScoreM)throws ApplicationException;
	public void deleteOrigBScoreM(String personalId, String bScoreId)throws ApplicationException;		
	public ArrayList<BScoreDataM> loadOrigBScoreM(String personalID)throws ApplicationException;
	public ArrayList<BScoreDataM> loadOrigBScoreM(String personalID,Connection conn)throws ApplicationException;
	public void saveUpdateOrigBScoreM(BScoreDataM bScoreM)throws ApplicationException;
	public void deleteNotInKeyBScore(ArrayList<BScoreDataM> bScoreList, String personalID)
			throws ApplicationException;
}
