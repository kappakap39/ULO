package com.eaf.orig.ulo.app.dao;

public class WorkflowDAOFactory {
	public static InboxDAO getInboxDAO(){
		return new InboxDAOImpl();
	}
}
