package com.eaf.orig.ulo.pl.app.dao;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;

public interface PLOrigCashTransferDAO {
	
	public void updateSaveCashTransfer(PLCashTransferDataM cashTransferM, String appRecId) throws PLOrigApplicationException;
	public PLCashTransferDataM loadCashTransfer(String appRecID) throws PLOrigApplicationException;

}
