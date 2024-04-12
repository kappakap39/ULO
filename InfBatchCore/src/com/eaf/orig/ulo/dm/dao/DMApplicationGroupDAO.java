package com.eaf.orig.ulo.dm.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.dm.dao.exception.DMException;
import com.eaf.orig.ulo.model.dm.DMApplicationInfoDataM;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public interface DMApplicationGroupDAO {
	public void setApplicationInfo(DocumentManagementDataM dmManageDataM) throws DMException;	
	public ArrayList<DMApplicationInfoDataM>  getProduct(String applicationGroupID) throws DMException;		
}
