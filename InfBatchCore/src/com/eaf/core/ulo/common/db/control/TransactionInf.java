package com.eaf.core.ulo.common.db.control;

import java.sql.Connection;

import com.eaf.orig.profile.model.UserDetailM;

public interface TransactionInf {
	public void init(int eventType,UserDetailM user);
	public void preTransaction(Object objectModel,Connection conn) throws Exception;
	public void processTransaction(Object objectModel,Connection conn) throws Exception;
	public void postTransaction(Object objectModel,Connection conn) throws Exception;
	public int getEventType();
	public UserDetailM getUser();
}
