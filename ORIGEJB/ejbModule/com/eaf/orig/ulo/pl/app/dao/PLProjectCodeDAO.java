package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;

public interface PLProjectCodeDAO{
	
	public PLProjectCodeDataM Loadtable(String projectcode) throws PLOrigApplicationException;
	public PLProjectCodeDataM Loadtable(String projectcode,String productfeature ,String busClassID,String exception_project) throws PLOrigApplicationException;
}
