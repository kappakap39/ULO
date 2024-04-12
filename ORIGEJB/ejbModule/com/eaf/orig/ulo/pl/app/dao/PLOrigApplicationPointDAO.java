package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLOrigApplicationPointDAO {
	public void deletePoint(String appRecId) throws PLOrigApplicationException;
}
