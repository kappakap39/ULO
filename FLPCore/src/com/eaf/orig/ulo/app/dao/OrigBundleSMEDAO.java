package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public interface OrigBundleSMEDAO {
	
	public void createOrigBundleSMEM(BundleSMEDataM bundleSME)throws ApplicationException;
	public void deleteOrigBundleSMEM(String applicationRecordId)throws ApplicationException;	
	public BundleSMEDataM loadOrigBundleSMEM(String applicationRecordId)throws ApplicationException;	
	public BundleSMEDataM loadOrigBundleSMEM(String applicationRecordId,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigBundleSMEM(BundleSMEDataM bundleSME)throws ApplicationException;
	
}
