package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLFinancialInfoDataM;

public interface PLOrigFinancialInfoDAO {
	
	public void updateSaveFinancialInfo(Vector<PLFinancialInfoDataM> financialVect, String personalID) throws PLOrigApplicationException;
	public Vector<PLFinancialInfoDataM> loadFinancialInfo(String personalId) throws PLOrigApplicationException;

}
