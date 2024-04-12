package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;

public interface OrigCardLinkCustomerDAO {
	
	public void createOrigCardlinkCustomerM(CardlinkCustomerDataM cardLinkCustM)throws ApplicationException;
	public void deleteOrigCardlinkCustomerM(String personalID, String cardlinkCustId)throws ApplicationException;	
	public ArrayList<CardlinkCustomerDataM> loadOrigCardlinkCustomerM(String personalID)throws ApplicationException;
	public ArrayList<CardlinkCustomerDataM> loadOrigCardlinkCustomerM(String personalID,Connection conn)throws ApplicationException;
	public void saveUpdateOrigCardlinkCustomerM(CardlinkCustomerDataM cardLinkCustM)throws ApplicationException;
	public void deleteNotInKeyCardlinkCustomer(ArrayList<CardlinkCustomerDataM> cardLinkCustList, String personalId) throws ApplicationException;
		
}
