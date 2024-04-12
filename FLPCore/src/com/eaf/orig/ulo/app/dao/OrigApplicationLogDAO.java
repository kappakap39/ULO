package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationLogDataM;

public interface OrigApplicationLogDAO {
	
	public void createOrigApplicationLogM(ApplicationLogDataM applicationLogDataM)throws ApplicationException;
	public void deleteOrigApplicationLogM(String applicationGroupId, String appLogId)throws ApplicationException;
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(String applicationGroupId)throws ApplicationException;
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(String applicationGroupId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigApplicationLogM(ApplicationLogDataM applicationLogDataM)throws ApplicationException;
	public Date getDE2SubmitDate(String applicationGroupId,int lifeCycle)throws ApplicationException;
	public Date getDateOfSearch(String applicationGroupId,int lifeCycle)throws ApplicationException;
}
