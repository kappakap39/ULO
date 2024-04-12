package com.eaf.inf.batch.ulo.cardlink.result.dao;


public class CardLinkResultDAOFactory {
	public static CardLinkResultDAO getCardLinkResultDAO(){
		return new CardLinkResultDAOImpl();
	}
}
