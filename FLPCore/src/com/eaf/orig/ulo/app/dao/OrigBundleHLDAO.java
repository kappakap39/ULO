package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BundleHLDataM;

public interface OrigBundleHLDAO {
	
	public void createOrigBundleHLM(BundleHLDataM bundleHLDataM)throws ApplicationException;
	public void deleteOrigBundleHLM(String applicationRecordId)throws ApplicationException;	
	public BundleHLDataM loadOrigBundleHLM(String applicationRecordId)throws ApplicationException;	 
	public BundleHLDataM loadOrigBundleHLM(String applicationRecordId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigBundleHLM(BundleHLDataM bundleHLDataM)throws ApplicationException;
	
}
