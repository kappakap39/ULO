package com.eaf.service.common.api;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Vector;

public interface ServiceSQLQueryEngineDAO {
	public void LoadModule(ServiceSQLDataM sqlM,Vector<HashMap> vModuleList,HashMap Module,String TYPE) throws ServiceException;
	public void LoadModuleEAFModel(ServiceSQLDataM sqlM,Vector<HashMap> vModuleList,HashMap Module,String TYPE) throws ServiceException;
	public void LoadModuleEAFModel(ServiceSQLDataM sqlM,Vector<HashMap> vModuleList,HashMap Module,String TYPE,Connection conn) throws ServiceException;
	public void LoadModule(ServiceSQLDataM sqlM,Vector<HashMap> vModuleList,HashMap Module,String TYPE,Connection conn) throws ServiceException;
	public Date getApplicationDate() throws ServiceException;
}
