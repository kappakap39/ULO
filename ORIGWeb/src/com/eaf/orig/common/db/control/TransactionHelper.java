package com.eaf.orig.common.db.control;

import java.sql.Connection;

import com.eaf.orig.profile.model.UserDetailM;

public abstract class TransactionHelper implements TransactionInf{
	private int eventType;
	private UserDetailM user;
	@Override
	public void init(int eventType, UserDetailM user) {
		this.eventType = eventType;
		this.user = user;
	}
	@Override
	public void postTransaction(Object objectModel, Connection conn)throws Exception {
		
	}
	@Override
	public void preTransaction(Object objectModel, Connection conn)throws Exception {
		
	}
	@Override
	public int getEventType() {
		return eventType;
	}
	@Override
	public UserDetailM getUser() {
		return user;
	}
}
