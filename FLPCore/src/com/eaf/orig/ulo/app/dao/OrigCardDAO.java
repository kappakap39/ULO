package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CardDataM;

public interface OrigCardDAO {
	
	public void createOrigCardM(CardDataM cardM)throws ApplicationException;
	public void deleteOrigCardM(String loanID, String cardId)throws ApplicationException;	
	public CardDataM loadOrigCardM(String loanID)throws ApplicationException;	
	public CardDataM loadOrigCardM(String loanID,Connection conn)throws ApplicationException;	 
	public CardDataM loadOrigCardMGroup(String loanID)throws ApplicationException;	 
	public void saveUpdateOrigCardM(CardDataM cardM)throws ApplicationException;
	
}
