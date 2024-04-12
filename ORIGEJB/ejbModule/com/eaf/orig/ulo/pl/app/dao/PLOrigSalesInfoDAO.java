package com.eaf.orig.ulo.pl.app.dao;

import java.util.Locale;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;

public interface PLOrigSalesInfoDAO {
	
	public void saveUpdatePLOrigSaleInfo (PLSaleInfoDataM saleInfoM, String appRecId) throws PLOrigApplicationException;
	public PLSaleInfoDataM loadPLOrigSaleInfoDataM (String appRecId) throws PLOrigApplicationException;
	public PLSaleInfoDataM loadPLOrigSaleInfoDataM_IncreaseDecrease (String appRecId) throws PLOrigApplicationException;
	public String getBranchDecription(String branchCode, Locale localeID) throws PLOrigApplicationException; //Praisan 20120517
}
