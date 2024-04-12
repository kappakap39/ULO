package com.eaf.orig.ulo.app.view.util.uloOrigApp;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.uloOrigApp.dao.UloOrigAppDAO;
import com.eaf.orig.ulo.app.view.util.uloOrigApp.dao.UloOrigAppDAOImpl;

public class UloOrigAppProxy{	
	public String getActionApplication(String APPLICATION_GROUP_ID,String JOB_STATE) throws ApplicationException{
		UloOrigAppDAO dih = new UloOrigAppDAOImpl();
		return dih.getActionApp(APPLICATION_GROUP_ID, JOB_STATE);
	}
	public String getApplicationStatus(String APPLICATION_GROUP_ID,String JOB_STATE) throws ApplicationException{
		UloOrigAppDAO dih = new UloOrigAppDAOImpl();
		return dih.getApplicationStatus(APPLICATION_GROUP_ID, JOB_STATE);
	}
}
