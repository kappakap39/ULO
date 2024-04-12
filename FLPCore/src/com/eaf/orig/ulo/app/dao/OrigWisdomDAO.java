package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.WisdomDataM;

public interface OrigWisdomDAO {
	
	public void createOrigWisdomM(WisdomDataM wisdomM)throws ApplicationException;
	public void deleteOrigWisdomM(String cardID)throws ApplicationException;	
	public WisdomDataM loadOrigWisdomM(String cardID)throws ApplicationException;	 
	public WisdomDataM loadOrigWisdomM(String cardID,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigWisdomM(WisdomDataM wisdomM)throws ApplicationException;
	
}
