package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.BundleKLDataM;

public interface OrigBundleKLDAO {
	
	public void createOrigBundleKLM(BundleKLDataM bundleKLDataM)throws ApplicationException;
	public void deleteOrigBundleKLM(String applicationRecordId)throws ApplicationException;	
	public BundleKLDataM loadOrigBundleKLM(String applicationRecordId)throws ApplicationException;	 
	public BundleKLDataM loadOrigBundleKLM(String applicationRecordId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigBundleKLM(BundleKLDataM bundleKLDataM)throws ApplicationException;
	
}
