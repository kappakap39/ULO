package com.eaf.orig.ulo.app.factory;

import com.eaf.orig.shared.key.dao.DMUniqueIDGeneratorDAO;
import com.eaf.orig.shared.key.dao.DMUniqueIDGeneratorDAOImpl;
import com.eaf.orig.ulo.dm.dao.DMApplicationGroupDAO;
import com.eaf.orig.ulo.dm.dao.DMApplicationGroupDAOImpl;
import com.eaf.orig.ulo.dm.dao.DMDocDAO;
import com.eaf.orig.ulo.dm.dao.DMDocDAOImpl;
import com.eaf.orig.ulo.dm.dao.DMImportExcelDAO;
import com.eaf.orig.ulo.dm.dao.DMImportExcelDAOImpl;
import com.eaf.orig.ulo.dm.dao.DMSubDocDAO;
import com.eaf.orig.ulo.dm.dao.DMSubDocDAOImpl;
import com.eaf.orig.ulo.dm.dao.DMTransactionDAO;
import com.eaf.orig.ulo.dm.dao.DMTransactionDAOImpl;

public class DMModuleFactory {
	public static DMDocDAO getDMDocDAO(){
		return new DMDocDAOImpl();
	}
	public static DMTransactionDAO getDMTransactionDAO(){
		return new DMTransactionDAOImpl();
	}
	public static DMSubDocDAO getDMSubDoc(){
		return new DMSubDocDAOImpl();
	}
	public static DMUniqueIDGeneratorDAO getDmUniqueIDGeneratorDAO(){
		return new DMUniqueIDGeneratorDAOImpl();
	}
	public static DMImportExcelDAO getDMImportExcelDAO(){
		return new DMImportExcelDAOImpl();
	}
	public static DMApplicationGroupDAO getDMApplicationGroupDAO(){
		return new DMApplicationGroupDAOImpl();
	}
}
