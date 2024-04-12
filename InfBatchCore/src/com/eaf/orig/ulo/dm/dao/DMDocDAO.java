package com.eaf.orig.ulo.dm.dao;

import com.eaf.orig.ulo.dm.dao.exception.DMException;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public interface DMDocDAO {
	public void saveTableDMDoc(DocumentManagementDataM docManageM,boolean autoCommitFlag) throws Exception;	
	public DocumentManagementDataM selectTableDMDoc(String dmId) throws DMException;	
	
}
