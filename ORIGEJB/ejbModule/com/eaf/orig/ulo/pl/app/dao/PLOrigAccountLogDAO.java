package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAccountLogDataM;

public interface PLOrigAccountLogDAO {
	
	public void saveAccLog (PLAccountLogDataM accLogM) throws PLOrigApplicationException;
	public Vector<PLAccountLogDataM> loadAccLog (String accId) throws PLOrigApplicationException;
	public Vector<PLAccountLogDataM> loadAccLogByCard (String accId) throws PLOrigApplicationException;
	public Vector<PLAccountLogDataM> loadAccLogByCust (String accId) throws PLOrigApplicationException;
	public Vector<PLAccountLogDataM> loadAccLogSortAsc (String accId) throws PLOrigApplicationException;
	public String[] loadLogFirstRow (String accId) throws PLOrigApplicationException;
	public String deCodeCardNo(String cardNo) throws PLOrigApplicationException;

}
