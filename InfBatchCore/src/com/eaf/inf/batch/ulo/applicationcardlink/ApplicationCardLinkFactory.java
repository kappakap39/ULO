package com.eaf.inf.batch.ulo.applicationcardlink;


public class ApplicationCardLinkFactory {

	public static CloseApplicationCardLinkDAO getCloseApplicationCardLinkDAO() {
		return new CloseApplicationCardLinkDAOImpl();
	}
}
