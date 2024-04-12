package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

public interface ReAssignDAO {
	public  ArrayList<String> getUserRole(ArrayList<String> roles) throws Exception;
	public boolean checkUserLogOnAndInboxFlag(String userName) throws Exception;
	ArrayList<String> getActiveInboxUserRole(ArrayList<String> roles)
			throws Exception;
}
