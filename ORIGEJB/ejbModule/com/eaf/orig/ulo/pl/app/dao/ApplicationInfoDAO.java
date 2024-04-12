package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public interface ApplicationInfoDAO {
	public PLApplicationDataM LoadAppInfo(String appRecID) throws PLOrigApplicationException;
	public void LoadAppInfo(PLApplicationDataM applicationM ,String appRecID) throws PLOrigApplicationException;
}
