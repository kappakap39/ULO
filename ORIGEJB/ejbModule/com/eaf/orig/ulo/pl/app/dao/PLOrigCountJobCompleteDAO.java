package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLOrigCountJobCompleteDAO {
	
	public int countJobComplete(String userName) throws PLOrigApplicationException;

}
