package com.eaf.orig.ulo.app.view.util.uloOrigApp.dao;

import java.util.HashMap;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
public interface UloOrigAppDAO {
	public String getActionApp(String APPLICATION_GROUP_ID,String JOB_STATE)throws ApplicationException;
	public String getApplicationStatus(String APPLICATION_GROUP_ID,String JOB_STATE)throws ApplicationException;
	public HashMap<String, String> getCountSupCardInfoByPersonalId(String personalId) throws ApplicationException;
}
