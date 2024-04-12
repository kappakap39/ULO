package com.avalant.wm.proxy;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.websphere.asynchbeans.WorkManager;

public class WorkManagerProxy {
	public static WorkManager lookup() throws NamingException {
		InitialContext ctx  = new InitialContext();
		WorkManager wm = (WorkManager) ctx.lookup("wm/eapp");
		return wm;
	}
	
	public static WorkManager lookupCJD() throws NamingException {
		InitialContext ctx  = new InitialContext();
		WorkManager wm = (WorkManager) ctx.lookup("wm/cjd");
		return wm;
	}
}
