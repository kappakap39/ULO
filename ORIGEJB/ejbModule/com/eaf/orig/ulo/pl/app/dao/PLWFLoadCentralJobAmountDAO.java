package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLWFDAOException;

public interface PLWFLoadCentralJobAmountDAO {

	public int loadCentralJob (String TDID, String ATID2) throws PLWFDAOException;
}
