package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CardlinkCardDataM;

public interface OrigCardLinkCardDAO {
	
	public void createOrigCardlinkCardM(CardlinkCardDataM cardLinkCardM)throws ApplicationException;
	public void deleteOrigCardlinkCardM(String personalID, String cardlinkCardId)throws ApplicationException;	
	public ArrayList<CardlinkCardDataM> loadOrigCardlinkCardM(String personalID)throws ApplicationException;	
	public ArrayList<CardlinkCardDataM> loadOrigCardlinkCardM(String personalID,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigCardlinkCardM(CardlinkCardDataM cardLinkCardM)throws ApplicationException;
	public void deleteNotInKeyCardlinkCard(ArrayList<CardlinkCardDataM> cardLinkCardList, String personalId) throws ApplicationException;
	
}
