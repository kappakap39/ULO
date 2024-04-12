package com.eaf.orig.ulo.pl.app.utility;

import com.eaf.orig.shared.dao.EjbUtilException;

public interface PLBPMUtilityDAO {
	public String getUserTimeRemain(String role,String appRecId) throws EjbUtilException ;
	public String getSystemTimeRemain(String bussClass,String appRecId) throws EjbUtilException ;
}
