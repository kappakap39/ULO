package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.model.InfBackupLogM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface ORIGManualImportDAO {
	public void processManualImportData(String plSQLfunction, String fileName) throws PLOrigApplicationException;
	public void createInterfaceBackupLog(InfBackupLogM infBackupM, String userName) throws PLOrigApplicationException;
	public void deleteInterfaceBackupLog(int infBackupId) throws PLOrigApplicationException;
	public void deleteInterfaceBackupLog(ORIGCacheDataM infCacheM) throws PLOrigApplicationException;
	public Vector<InfBackupLogM> loadInterfaceBackupLogOverTime(String moduleId, int days) throws PLOrigApplicationException;
	public void createBLobInfImport(String fileFullName, String moduleId, String userName) throws PLOrigApplicationException;
	public void clearBLobInfImport(String moduleId, String userName)throws PLOrigApplicationException;
}
