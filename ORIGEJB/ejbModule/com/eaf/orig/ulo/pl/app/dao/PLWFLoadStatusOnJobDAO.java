package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLWFDAOException;

public interface PLWFLoadStatusOnJobDAO {
	
	public String loadStatusOnJob (String userName, String TDID) throws PLWFDAOException;

}
